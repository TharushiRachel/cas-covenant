import { CurrencyPipe } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import * as _ from 'lodash';
import { CasCustomerService } from 'src/app/core/service/data/cas-customer.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { ConfirmationDialogComponent } from '../../../confirmation-dialog/confirmation-dialog.component';
import { MDBModalRef, MDBModalService } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-customer-kalypto-details',
  templateUrl: './customer-kalypto-details.component.html',
  styleUrls: ['./customer-kalypto-details.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class CustomerKalyptoDetailsComponent implements OnInit, OnChanges, OnDestroy {

  @Input('customerDetails') customerDetails: any = {};
  @Input('mainFacilityPaper') mainFacilityPaper: any = {};

  selectedTabIndex: number = 0;
  generalTabIndex: number = 0;
  detailTabIndex: number = 1;
  colForm: FormGroup;
  rowInfoForm: FormGroup;
  gernalTabObj: any = {};
  tableColumnNameArr: any[] = [];
  tabNameAndFirstColumnArr: any[] = [];
  otherColumnArr: any[] = [];
  styleClassArr: any = []
  isOpenInput: boolean = false;
  isColumnHeadOpen: boolean = false;
  finalArray: any[] = []
  reqRowArr: any[] = [];
  facilityPaperID: string = "";
  isLoading: boolean = false;
  valueArray: any[] = [];
  addedValueArr: any[] = []
  kalyptoData: {} = {};
  initColCount: number = 0;
  kalyptoDataLen: number = 0;
  isAddedNewKalypto: boolean = false;
  isEdited: boolean = false;
  approveModalRef: MDBModalRef;
  content: any;
  isValueGenerate: boolean = false;
  isRefreshLoading: boolean = false;
  isAddedColEditing: boolean = false;
  isRowEditing: boolean = false;

  //button permission
  isSaveBtnDisabled: boolean = false;
  isRefreshBtnDisabled: boolean = false;
  isRowAddBtnDisabled: boolean = false;
  isEditBtnDisabled: boolean = false;

  tempData: {} = {};

  constructor(
    private currencyPipe: CurrencyPipe,
    private formBuilder: FormBuilder,
    private casCustomerService: CasCustomerService,
    private alertService: AlertService,
    private ref: ChangeDetectorRef,
    private mdbModalService: MDBModalService
  ) { }
  ngOnDestroy(): void {
  }
  ngOnChanges(changes: SimpleChanges): void {
  }

  ngOnInit() {
    this.isAddedNewKalyptoAPI()
    this.colForm = this.createColumnForm();
    this.rowInfoForm = this.createRowInfoFrom()
    this.getKalyptoValueAPI();
  }

  isAddedNewKalyptoAPI = () => {
    if (!this.customerDetails["cumm"] || !this.mainFacilityPaper.facilityPaperID) {
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    }
    let dataRQ = {
      "customerId": this.customerDetails["cumm"],
      "facilityId": this.mainFacilityPaper.facilityPaperID,
    }
    this.casCustomerService.isAddedNewKalyptoService(dataRQ).subscribe((response: any) => {
      if (response) {
        this.isAddedNewKalypto = response["result"]
      }
      this.ref.detectChanges();
    }, (error: any) => {
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    })
  }

  startAddedColEditing() {
    this.isAddedColEditing = true;
  }

  stopAddedColEditing() {
    this.isAddedColEditing = false;
    this.isSaveBtnDisabled = false;
    this.isRefreshBtnDisabled = false;
    this.ref.detectChanges()
  }

  getKalyptoValueAPI = () => {
    if (!this.customerDetails["cumm"] || !this.mainFacilityPaper.facilityPaperID) {
      this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
    }
    let request = {
      "customerId": this.customerDetails["cumm"],
      "facilityId": this.mainFacilityPaper.facilityPaperID,
    }
    let data = {}
    this.isLoading = true;
    this.casCustomerService.getKalyptoValueService(request).subscribe({
      next: (response) => {
        if (response) {
          this.kalyptoData = data = Object.assign({}, {
            "gernalTabObj": response["kalyptoRatingEvalDTO"],
            "values": response["values"],
            "periods": response["periods"],
            "message": response["message"],
            "successfullyParse": response["successfullyParse"],
          })
          this.isLoading = false;
          this.ref.detectChanges();
        }
      },
      complete: () => {
        if (!this.isValueGenerate) {
          this.initAddedValueGenerate();
        }
        this.isLoading = false;
        this.ref.detectChanges();
      },
      error: (error) => {
        this.isLoading = false;
        this.ref.detectChanges();
        this.alertService.showToaster("Please contact system administrator", SETTINGS.TOASTER_MESSAGES.error)
      }
    })
  }


  startRowEditing() {
    this.isRowEditing = true;
    this.isSaveBtnDisabled = true;
    this.isRefreshBtnDisabled = true;
    this.isRowAddBtnDisabled = true;
  }

  stopRowEditing() {
    this.isRowEditing = false;
    if (this.isEdited) {
      this.kalyptoData = this.tempData;
    }
    this.isRowAddBtnDisabled = false;
    this.isSaveBtnDisabled = false;
    this.isRefreshBtnDisabled = false;
  }

  initAddedValueGenerate() {
    this.kalyptoData["values"] ? this.kalyptoData["values"].map((row: any, index: number) => {
      this.addedValueArr.push({
        "customerId": row['values'][0]['customerId'],
        "facilityId": row['values'][0]['facilityId'],
        "parameterId": row['values'][0]['parameterId'],
        "parameterName": row['values'][0]['parameterName'],
        "periodId": "",
        "isAddedNew": 1,
        "valueNumberic": 0.00,
        "valuePercentage": "",
        "valueText": "",
      })
    }) : []
    this.isValueGenerate = true;
  }

  createColumnForm() {
    return this.formBuilder.group({
      columnName: [""],
    });
  }

  createRowInfoFrom() {
    return this.formBuilder.group({
      valueNumberic: [""],
    });
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '')
  }

  loadInputColumn() {
    this.isOpenInput = !this.isOpenInput;
    this.isRefreshBtnDisabled = !this.isRefreshBtnDisabled;
    this.isEditBtnDisabled = !this.isEditBtnDisabled;
  }

  clearInputColumn() {
    this.colForm.reset({
      columnName: [""],
    });
    return this.rowInfoForm.reset({
      valueNumberic: "",
    });
  }

  changeRowValue = (k: number, l: number, rec: any, event: any) => {
    let input = event.target.value

    if (isNaN(input)) {
      this.alertService.showToaster('Not a Number!', SETTINGS.TOASTER_MESSAGES.error)
      return;
    } else {
      let rowObj: any = {
        "customerId": rec['customerId'],
        "facilityId": rec['facilityId'],
        "id": rec['id'],
        "isAddedNew": rec['isAddedNew'],
        "parameterId": rec['parameterId'],
        "parameterName": rec['parameterName'],
        "periodId": rec['periodId'],
        "valueNumberic": input ? parseFloat(input).toFixed(2) : 0.00,
        "valuePercentage": 0,
        "valueText": ""
      }
      console.log("rowObj", rowObj)

      this.tempData = {
        ...this.kalyptoData,
        values: this.kalyptoData["values"].map((item, index) =>
          index === k ? {
            ...item, values: item.values.map((innerItem, innerIndex) =>
              innerIndex === l ? rowObj : innerItem
            )
          }
            : item
        )
      };
      this.isEdited = true;
      this.ref.detectChanges();
    }
  }

  changeAddedRowValue = (k: number, rowInfo: any, event: any) => {
    let input = event.target.value
    if (isNaN(input)) {
      this.alertService.showToaster('Not a Number!', SETTINGS.TOASTER_MESSAGES.error)
      return;
    }
    let rowObj: any = {
      "customerId": rowInfo['values'][0]['customerId'],
      "facilityId": rowInfo['values'][0]['facilityId'],
      "isAddedNew": 1,
      "parameterId": rowInfo['values'][0]['parameterId'],
      "parameterName": rowInfo['values'][0]['parameterName'],
      "periodId": "",
      "valueNumberic": input ? parseFloat(input).toFixed(2) : 0.00,
      "valuePercentage": 0,
      "valueText": ""
    }
    this.addedValueArr[k] = rowObj;
    this.isSaveBtnDisabled = false;
    this.ref.detectChanges()
  }

  changeAddedColValue = (i: number, columnName: any, event: any) => {
    this.kalyptoData["periods"][i]["periodName"] = event.target.value
  }

  refreshKalypto() {
    let request = {
      "customerId": this.customerDetails["cumm"],
      "facilityId": this.mainFacilityPaper.facilityPaperID,
    }
    this.isRefreshBtnDisabled = true;
    this.isRefreshLoading = true;
    this.isRowAddBtnDisabled = true;
    this.isEditBtnDisabled = true;
    this.isSaveBtnDisabled = true;
    this.casCustomerService.refreshKalyptoValueService(request).subscribe((response: any) => {
      this.isRefreshBtnDisabled = false;
      this.isRowAddBtnDisabled = false;
      this.isRefreshLoading = false;
      this.isEditBtnDisabled = false;
      this.isSaveBtnDisabled = false;
      if (response) {
        this.kalyptoData = Object.assign({}, {
          "gernalTabObj": response["kalyptoRatingEvalDTO"],
          "values": response["values"],
          "periods": response["periods"],
          "message": response["message"],
          "successfullyParse": response["successfullyParse"],
        })
        this.ref.detectChanges();
        this.alertService.showToaster("Refreshed successfully", SETTINGS.TOASTER_MESSAGES.success)
      }
    }, (error: any) => {
      this.isRefreshBtnDisabled = false;
      this.isRowAddBtnDisabled = false;
      this.isRefreshLoading = false;
      this.isEditBtnDisabled = false;
      this.isSaveBtnDisabled = false;
      this.ref.detectChanges();
      this.alertService.showToaster("Refreshed Failed!", SETTINGS.TOASTER_MESSAGES.error)
    })
  }

  openSaveConformDialog = () => {
    this.approveModalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-30-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "Kalypto Save",
        message: "Do you want to save edited Kalypto Details ?",
      }
    });
    this.approveModalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.saveKalypto();
      }
    });
  }

  saveKalypto = () => {
    let { columnName } = this.colForm.getRawValue();
    if (this.isOpenInput) {
      if (columnName == "") {
        this.alertService.showToaster("Please add Column Details!", SETTINGS.TOASTER_MESSAGES.error)
        return;
      }
    }
    let request = {
      "columnName": columnName,
      "customerId": this.customerDetails["cumm"],
      "facilityId": this.mainFacilityPaper.facilityPaperID,
      "kalyptoAddedValues": this.addedValueArr,
      "isOpenInput": this.isOpenInput,
      "kalyptoValues": this.kalyptoData["values"],
      "kalyptoPeriodValues": this.kalyptoData["periods"]
    }
    this.isSaveBtnDisabled = true;
    this.isRefreshBtnDisabled = true;
    this.isRowAddBtnDisabled = true;
    this.isEditBtnDisabled = true;
    this.casCustomerService.saveEditedKalyptoService(request).subscribe((response: any) => {
      if (response) {
        this.getKalyptoValueAPI();
        this.isAddedNewKalyptoAPI();
        this.alertService.showToaster("Saved successfully", SETTINGS.TOASTER_MESSAGES.success)
      }
      this.isOpenInput = false;
      this.isSaveBtnDisabled = false;
      this.isRefreshBtnDisabled = false;
      this.isRowAddBtnDisabled = false;
      this.isEditBtnDisabled = false;
      this.ref.detectChanges();
    }, (error: any) => {
      this.isOpenInput = false;
      this.isSaveBtnDisabled = false;
      this.isRefreshBtnDisabled = false;
      this.isRowAddBtnDisabled = false;
      this.isEditBtnDisabled = false;
      this.ref.detectChanges();
      this.alertService.showToaster("Saved Failed!", SETTINGS.TOASTER_MESSAGES.error)
    })
  }
}
