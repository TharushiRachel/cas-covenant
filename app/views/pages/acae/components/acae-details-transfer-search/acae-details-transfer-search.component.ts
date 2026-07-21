import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ACAEDetailsTransferSearchService } from '../../services/acae-details-transfer-search.service';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { IMyOptions, MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';
import { animate, transition, trigger } from '@angular/animations';
import { ACAEPaperTransferModelComponent } from '../acae-paper-details/acae-paper-transfer-model/acae-paper-transfer-model.component';
import { ACAESharedService } from '../../services/acae-shared.service';
import { ACAEPaperService } from '../../services/acae-paper.service';
import { ACAEPaperDetailsComponent } from '../acae-paper-details/acae-paper-details.component';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-acae-details-transfer-search',
  templateUrl: './acae-details-transfer-search.component.html',
  styleUrls: ['./acae-details-transfer-search.component.scss'],
  animations: [
    trigger('detailExpand', [
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class AcaeDetailsTransferSearchComponent implements OnInit {

  acaeTransferSearchForm: FormGroup;
  acaeTransferData = [];
  expanded: Boolean = false;
  isTransferBtnEnable: Boolean = false;
  showActive: string = '';
  userSelectModel: MDBModalRef;
  viewOnly: boolean = true;
  acaeDetailEditModelRef: MDBModalRef;
  solId: string = "";
  fromDate = "";
  toDate = "";


  constructor(
    private formBuilder: FormBuilder,
    private acaeDetailsTransferSearchService: ACAEDetailsTransferSearchService,
    private alertService: AlertService,
    private acaePaperService: ACAEPaperService,
    private ref: ChangeDetectorRef,
    private mdbModalService: MDBModalService,
    private acaeSharedService: ACAESharedService
  ) { }

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy-mm-dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  ngOnInit() {
    this.acaeTransferSearchForm = this.createAcaeTransferSearchForm();
    this.acaeSharedService.triggerRefreshACAETransferGrid$.subscribe(() => {
      this.acaeTransferData = []
      this.acaeTransferSearchForm.get('solId').reset('');
      this.acaeTransferSearchForm.get('fromDate').reset(null);
      this.acaeTransferSearchForm.get('toDate').reset(null);
    });
  }

  toggleRow(userNo: any, refNo: any) {
    let data = []
    data = this.acaeTransferData

    if (data[userNo]['refRecords'][refNo]['isExpanded']) {
      data[userNo]['refRecords'][refNo]['isExpanded'] = false
    } else {
      data[userNo]['refRecords'][refNo]['isExpanded'] = true
    }

    this.acaeTransferData = data;
    this.ref.detectChanges();
  }

  openModalACAEDetails(item: any): void {
    this.viewOnly = true;
    let dataRQ = {
      "accountName": item.accName,
      "accountNumber": item.accNo,
      "receivedDate": item.receivedDate,
      "refNumber": item.refNumber,
      "viewOnly": this.viewOnly,
    }
    const initialState = {
      recordSize: 0,
      recordIndex: item.recordIndex,
      list: [{ tag: "Count", value: item }],
      gridData: dataRQ,
      condition: "transferOption",
      acaeStatusNo: this.acaeSharedService.getACAEStatusNo('TRANSFER_TO_ME'),
    };
    this.acaeDetailEditModelRef = this.mdbModalService.show(ACAEPaperDetailsComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      ignoreBackdropClick: true,
      class: "modal-width-95-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "12",
        content: {
          initialState: initialState,
          acaeDetailEditModelRef: this.acaeDetailEditModelRef
        },
      },
    });
  }

  createAcaeTransferSearchForm() {
    return this.formBuilder.group({
      solId: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
    });
  }

  clearSearch() {
    this.acaeTransferSearchForm.reset({
      solId: '',
      fromDate: '',
      toDate: '',
    });
  }

  created() {
    return this.formBuilder.group({
      solId: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
    });
  }

  doSearch() {
    let { solId, fromDate, toDate } = this.acaeTransferSearchForm.getRawValue();
    if (solId == "" || solId == null || fromDate == "" || fromDate == null || toDate == "" || toDate == null) {
      this.alertService.showToaster("All fields required !", SETTINGS.TOASTER_MESSAGES.error);
      return;
    }
    if(typeof solId === 'number'){
      this.alertService.showToaster("Solid should number !", SETTINGS.TOASTER_MESSAGES.error);
      return;
    }

    let acaeRecordsForTransferRQ = {
      "solId": solId,
      "fromDate": fromDate,
      "toDate": toDate
    }
    this.solId = solId
    this.isTransferBtnEnable = true;
    this.acaeDetailsTransferSearchService.getACAERecordsForTransferService(acaeRecordsForTransferRQ).subscribe((response: any) => {
      if (response) {
        this.acaeTransferData = response;
        this.ref.detectChanges();
      }
    }, (error) => {
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    });
  }

  doSelectedACAERecords() {
    let selectedData = []
    this.acaeTransferData.map((data: any, index: number) => (
      data['refRecords'].map((data: any, index: number) => (
        data["acaeRecords"].map((data: any, index: number) => {
          if (data.isSelected === true) {
            selectedData.push(data)
          }
        })
      ))
    ))
    return selectedData;
  }

  openTransferOptionModel() {
    if (this.doSelectedACAERecords().length === 0) {
      this.alertService.showToaster("Please select records to transfer !", SETTINGS.TOASTER_MESSAGES.error)
      return;
    }
    const initialState = {
      "status": 'transferOption',
      "acaeRecordList": this.doSelectedACAERecords(),
      "acaeTransferSearchForm": this.acaeTransferSearchForm,
      "solId": this.solId
    };
    this.userSelectModel = this.mdbModalService.show(ACAEPaperTransferModelComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Director Details",
        content: {
          initialState: initialState,
        },
      },
    });
    this.userSelectModel.content.clearGridAction.subscribe((data: any) => {
      if (data) {
        this.acaeTransferData = [];
        this.clearSearch();
      }
    })
  }

  onSelectACAERecords($event: any, userNo: any, refNo: any, rec: any) {
    if ($event) {
      $event.preventDefault();
      $event.stopPropagation();
    }
    if (rec['isSelected']) {
      this.acaeTransferData[userNo]["refRecords"][refNo]['acaeRecords'][rec.recordNo]['isSelected'] = false
    } else {
      this.acaeTransferData[userNo]["refRecords"][refNo]['acaeRecords'][rec.recordNo]['isSelected'] = true
    }
    this.ref.detectChanges();
  }

  onSelectAll($event: any, userNo: any, refNo: any, item: any) {
    if ($event) {
      $event.preventDefault();
      $event.stopPropagation();
    }

    if (item['isSelectAll']) {

      this.acaeTransferData[userNo]["refRecords"][refNo]['isSelectAll'] = false
      this.acaeTransferData[userNo]["refRecords"][refNo]['acaeRecords'].map((data: any, index: number) => {
        data.isSelected = false;
      })

    } else {
      this.acaeTransferData[userNo]["refRecords"][refNo]['isSelectAll'] = true
      this.acaeTransferData[userNo]["refRecords"][refNo]['acaeRecords'].map((data: any, index: number) => {
        data.isSelected = true;
      })
    }
    this.ref.detectChanges();
  }
}