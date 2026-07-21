import { Component, OnInit, OnChanges, OnDestroy, SimpleChanges, } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PageSize } from 'src/app/core/dto/page.size';
import { Constants } from 'src/app/core/setting/constants';
import { IMyOptions } from 'ng-uikit-pro-standard';
import { ACAEStatusInquiryService } from '../../services/acae-status-inquiry.service';
import { ChipsModule } from 'primeng/chips';
import { AlertService } from 'src/app/core/service/common/alert.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Pagination } from 'src/app/core/dto/pagination';

@Component({
  selector: 'app-acae-details-status-inquiry',
  templateUrl: './acae-details-status-inquiry.component.html',
  styleUrls: ['./acae-details-status-inquiry.component.scss']
})
export class ACAEDetailsStatusInquiryComponent implements OnInit, OnChanges, OnDestroy {

  constructor(
    private formBuilder: FormBuilder,
    private acaeStatusInquiryService: ACAEStatusInquiryService,
    private alertService: AlertService
  ) { }

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'yyyy-mm-dd',
    minYear: 1950,
    maxYear: 2050,
    showTodayBtn: true,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  solIdList = "";
  solIdListResub = [];
  searchedACAEList: any = [
    {
      "accountNumber": "000100003589",
      "customerName": "M N V DE SILVA",
      "recievedDate": "05-03-2022",
      "attended": "edit",
    }
  ];
  vFromDate: any;
  vToDate: any;

  tFromDate: any;
  tToDate: any;

  dateRangeData: any = [];
  dateRangeDataPageData: any = [];
  isDateRangeSearch: boolean;

  solIdData = [];
  isSOLIdsSearch: boolean;

  toBeResubmittedData:any = [];
  isToBeResubmittedSearch: boolean;

  inquiryByDateRangeForm: FormGroup;
  inquiryBySolIdForm: FormGroup;
  toBeResubmittedForm: FormGroup;

  toBeResubmittedPageData: any = [];

  pageSize = new PageSize();
  pageIndex = 0;
  acaeReportTypeConst = Constants.acaeReportTypeConst;
  dateRangePaginationData = new Pagination({
    pageSize: 10,
    pageIndex: 0
  })

  toBeResubmittedPaginationData = new Pagination({
    pageSize: 10,
    pageIndex: 0
  })


  ngOnInit() {
    this.inquiryByDateRangeForm = this.formBuilder.group({
      fromDate: null,
      toDate: null,
    });
    this.dateRangePaginationData = new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: this.pageSize.pageIndex
    })
    // this.inquiryByDateRangeForm = this.createInquiryByDateRangeForm();
    this.inquiryBySolIdForm = this.formBuilder.group({
      solIdList: []
    });

    this.toBeResubmittedForm = this.formBuilder.group({
      reportType: null,
      fromDate: null,
      toDate: null,
      solIdListResub: [],
    });
  }

  clearDateRangeSearch = () => {
    this.inquiryByDateRangeForm.reset({
      fromDate: null,
      toDate: null,
    });
  }

  clearToBeResubmitted = () => {
    this.solIdListResub = []
    this.toBeResubmittedForm.reset({
      reportType: null,
      fromDate: null,
      toDate: null,
      solIdListResub: [],
    });
  }

  clearSolIdsSearch = () => {
    this.inquiryBySolIdForm.reset({
      solIdList: []
    });
  }

  dateRangeSearch() {
    let { fromDate, toDate } = this.inquiryByDateRangeForm.getRawValue();
    this.vFromDate = fromDate;
    this.vToDate = toDate;
    if (fromDate == null) {
      this.alertService.showToaster("Please specify the fromDate!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
      return;
    }
    if (toDate == null) {
      this.alertService.showToaster("Please specify the toDate!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
      return;
    }
    let request = {
      "fromDate": fromDate ? fromDate : this.vFromDate,
      "toDate": toDate ? toDate : this.vToDate,
    }
    this.acaeStatusInquiryService.getInquiryByDateRange(request, this.dateRangePaginationData).subscribe((response: any) => {
      if(response){
        this.dateRangeData = response["pageData"]?response["pageData"]:[];
        this.dateRangeDataPageData = response;
        this.isDateRangeSearch = true;
      }
    });
  }


  onDateRangePageEvent(event: any) {
    this.dateRangePaginationData.pageSize = event.pageSize;
    this.dateRangePaginationData.pageIndex = event.pageIndex + 1;
    this.dateRangeSearch();
  }

  solIdsSearch() {
    let { solIdList } = this.inquiryBySolIdForm.getRawValue();
    let solIds = "";
    if (solIdList.length > 0) {
      solIds = solIdList.join(',');
    } else {
      this.alertService.showToaster("Please enter sol Id(s)", SETTINGS.TOASTER_MESSAGES.error)
    }
    let request = {
      "solIdList": solIds,
    }
    this.acaeStatusInquiryService.getInquiryBySolIds(request).then((res: any) => {
      if(res){
        this.solIdData = res;
        this.isSOLIdsSearch = true;
      }
    });
  }

  toBeResubmittedSearch() {
    let { reportType, fromDate, toDate, solIdListResub } = this.toBeResubmittedForm.getRawValue();
    let solIds = "";
    if (reportType == null) {
      this.alertService.showToaster("Please select the reportType!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
      return;
    }
    if (fromDate == null) {
      this.alertService.showToaster("Please specify the fromDate!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
      return;
    }
    if (toDate == null) {
      this.alertService.showToaster("Please specify the toDate!",
        SETTINGS.TOASTER_MESSAGES.error, { timeOut: Constants.toastMessageTimeout.MEDIUM });
      return;
    }
    if (solIdListResub.length > 0) {
      solIds = solIdListResub.join(',');
    } else {
      this.alertService.showToaster("Please enter sol Id(s)", SETTINGS.TOASTER_MESSAGES.error)
    }
    if (solIdListResub.length > 0) {
      solIds = solIdListResub.join(',');
    }
    let request = {
      "reportType": reportType,
      "fromDate": fromDate,
      "toDate": toDate,
      "solIdList": solIds
    }
    this.acaeStatusInquiryService.getInquiryByResubmittedACAE(request, this.toBeResubmittedPaginationData).then((res: any) => {
      if(res){
        this.toBeResubmittedData = res["pageData"]?res["pageData"]:[];
        this.toBeResubmittedPageData = res;
        this.isToBeResubmittedSearch = true;
      }
    });;
  }

  onToBeResubmittedPageEvent = (event: any) => {
    this.toBeResubmittedPaginationData.pageSize = event.pageSize;
    this.toBeResubmittedPaginationData.pageIndex = event.pageIndex + 1;
    this.toBeResubmittedSearch();
  }

  isSearchDataEntered() {
  }

  clearSearch() {
  }

  ngOnChanges(changes: SimpleChanges): void {

  }
  ngOnDestroy(): void {
  }

}
