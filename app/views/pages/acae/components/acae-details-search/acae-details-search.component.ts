import {
  Component,
  OnInit,
  OnChanges,
  OnDestroy,
  SimpleChanges,
} from "@angular/core";
import { FormGroup, FormBuilder } from "@angular/forms";
import { Subscription } from "rxjs";
import { PageSize } from "src/app/core/dto/page.size";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { ACAEService } from "../../services/acae-base.service";
import { Pagination } from "src/app/core/dto/pagination";
import { ACAEPaperService } from "../../services/acae-paper.service";
import { IMyOptions, MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ACAEPaperDetailsComponent } from "../acae-paper-details/acae-paper-details.component";

@Component({
  selector: "app-acae-details-search",
  templateUrl: "./acae-details-search.component.html",
  styleUrls: ["./acae-details-search.component.scss"],
})
export class ACAEDetailsSearchComponent implements OnInit, OnChanges, OnDestroy {
  acaeCustomerDetails: any;
  acaeOutstandingDetail: any;
  acaeRelatedAccountDetail: any;
  acaeLoanAccountDetail: any;
  acaeUserCommentDetail: any;
  isAcaeSearch: boolean;
  constructor(
    private formBuilder: FormBuilder,
    private acaePaperService: ACAEPaperService,
    private applicationService: ApplicationService,
    private acaeBaseService: ACAEService,
    private mdbModalService: MDBModalService
  ) { }
  searchedACAEList: any = [
    {
      accountNumber: "000100003589",
      customerName: "M N V DE SILVA",
      recievedDate: "05-03-2022",
      attended: "edit",
    },
  ];

  acaeStatusLOV: any = [
    { value: 0, label: "New" },
    { value: 1, label: "Forwarded" },
    { value: 3, label: "Rejected" },
    { value: 4, label: "Approved" },
    ...(this.applicationService.getLoggedInUserUPMGroupCode() === 50
      ? [
        {
          value: 6,
          label: "Forwarded To me",
        },
      ]
      : []),
    { value: 7, label: "To be resubmitted" },
    { value: 9, label: "Rejected before 31/12/2017" },
  ];

  acaeSearchForm: FormGroup;
  pageSize = new PageSize();
  pageIndex = 0;
  modalRef: MDBModalRef;
  acaeSearchData = [];
  onACAEStatusDetailChangeSub: Subscription = new Subscription();
  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy/mm/dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  ngOnChanges(changes: SimpleChanges): void { }
  ngOnDestroy(): void {
  }

  ngOnInit() {
    this.acaeSearchForm = this.createAcaeSearchForm();
  }

  createAcaeSearchForm() {
    return this.formBuilder.group({
      acaeStatus: [""],
      accountNumber: [""],
      customerName: [""],
      recievedDateStr: [""],
    });
  }

  onValueUpdate(arg0: number) { }

  keyPressNumbers(event) { }

  async doSearch() {
    this.acaeSearchData = [];
    let { acaeStatus, accountNumber, customerName, recievedDateStr } =
      this.acaeSearchForm.getRawValue();

    let data = {
      acaeStatus: acaeStatus,
    };
    await this.acaeBaseService.getACAEListByStatusService(data, new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));

    let dataArray: any = []

    if (dataArray.length > 0) {
      dataArray.forEach(function (element) {
        element.ACAESTATUS = acaeStatus;
      });
    }

    setTimeout(() => this.isAcaeSearch = true, 1000);
    this.acaeSearchData = dataArray.filter((obj) => {
      return obj.ACAESTATUS == acaeStatus || obj.ACCT_NAME == customerName || obj.ACCT_NO == accountNumber || obj.RECEIVED_DATE == recievedDateStr;
    })
  }

  clearSearch() {
    this.acaeSearchForm.reset({
      acaeStatus: '',
      accountNumber: '',
      customerName: '',
      recievedDateStr: '',
    }, { onlySelf: false, emitEvent: true });
  }

  openModalACAEDetails(item: any): void {
    let dataRQ = {
      "accountName": item.ACCT_NAME,
      "accountNumber": item.ACCT_NO,
      "receivedDate": item.RECEIVED_DATE,
      "refNumber": item.REF_NUM,
    }
    const initialState = {
      list: [{ tag: "Count", value: item }],
      gridData: dataRQ,
    };
    this.modalRef = this.mdbModalService.show(ACAEPaperDetailsComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      ignoreBackdropClick: true,
      class: "modal-width-95-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "Add/Edit Director Details",
        content: {
          initialState: initialState,
        },
      },
    });
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
  }

  isSearchDataEntered(): boolean {

    if ((this.acaeSearchForm.value.acaeStatus) || (this.acaeSearchForm.value.customerName) ||
      (this.acaeSearchForm.value.accountNumber) || (this.acaeSearchForm.value.recievedDateStr)) {

      return false
    } else {

      return true
    }

  }
}
