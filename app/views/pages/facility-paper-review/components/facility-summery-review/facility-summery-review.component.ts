import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../../../core/setting/constants";
import {PageSize} from "../../../../../core/dto/page.size";
import {Subscription} from "rxjs";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {FacilityPaperReviewService} from "../../services/facility-paper-review.service";
import {Pagination} from "../../../../../core/dto/pagination";
import {CurrencyPipe} from "@angular/common";
import {MDBModalRef, MDBModalService} from "ng-uikit-pro-standard";
import {FromToDateDialogComponent} from "../../../../../shared/components/from-to-date-dialog/from-to-date-dialog.component";
import * as _ from "lodash";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-facility-summery-report',
  templateUrl: './facility-summery-review.component.html',
  styleUrls: ['./facility-summery-review.component.scss']
})
export class FacilitySummeryReviewComponent implements OnInit, OnDestroy {

  uniquePageName = 'Report-#feTbtsdw';
  modalRef: MDBModalRef;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_ASSIGNED_USER_ID)
  selectedAssignUserName;

  searchForm: FormGroup;
  facilityReviewSummaries: any[] = [];
  pageSize = new PageSize({pageSize: 25});
  onSearchFormChangeSub = new Subscription();
  onFPReviewSummaryChangeSub: Subscription = new Subscription();
  fromDateAndToDate: any = {};
  status = Constants.status;
  statusConst = Constants.statusConst;
  optionsSelect = Constants.dateRangeOptionsSelect;
  approvedUPMGroupList: any[] = [];
  allowApprovedUPMGroupsForLoginUser: any[] = [];
  paperReviewStatusConst = Constants.paperReviewStatusConst;
  paperReviewStatusSelect: any = [
    {value: Constants.paperReviewStatusConst.ACTION_REQUIRED, label: Constants.paperReviewStatus.ACTION_REQUIRED},
    {value: Constants.paperReviewStatusConst.REPLIED, label: Constants.paperReviewStatus.REPLIED},
    {value: Constants.paperReviewStatusConst.APPROVED, label: Constants.paperReviewStatus.APPROVED},
    {value: Constants.paperReviewStatusConst.REJECTED, label: Constants.paperReviewStatus.REJECTED},
  ];

  constructor(
    private formBuilder: FormBuilder,
    private mdbModalService: MDBModalService,
    private applicationService: ApplicationService,
    private facilityPaperReviewService: FacilityPaperReviewService,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {

    this.searchForm = this.createSearchForm();
    this.onSearchFormChangeSub.unsubscribe();

    this.approvedUPMGroupList = this.facilityPaperReviewService.approvedUPMGroupList;  // This we have All Approved UPM Access levels
    this.allowApprovedUPMGroupsForLoginUser = this.facilityPaperReviewService.allowApprovedUPMGroupsForLoginUser;

    if (this.allowApprovedUPMGroupsForLoginUser.length == 0) {
      this.allowApprovedUPMGroupsForLoginUser = this.approvedUPMGroupList; //FIXME This Temporary
    }

    this.onFPReviewSummaryChangeSub = this.facilityPaperReviewService.onFPSummaryReviewChange
      .subscribe(data => {
        this.facilityReviewSummaries = this.facilityPaperReviewService.facilityPapersReviewSummaryRecords;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

     this.onSearchFormChangeSub = this.searchForm.get('dateRange').valueChanges.subscribe(dateRange => {
       if (dateRange == Constants.dateRangeConst.CUSTOM) {
         this.OpenDateRangeModelModal();
       } else {
         this.fromDateAndToDate = {}
       }
     });

  /*   this.onSearchFormChangeSub = this.searchForm.valueChanges
       .debounceTime(200)
       .distinctUntilChanged()
       .subscribe(form => {
         this.doSearch();
       });*/
  }

  createSearchForm() {
    return this.formBuilder.group({
      approvedUser: '',
      paperReviewStatusList: [[Constants.paperReviewStatusConst.ACTION_REQUIRED, Constants.paperReviewStatusConst.REPLIED]],
      dateRange: Constants.dateRangeConst.LAST_30_DAYS,
      upmGroupCode: '',
    });
  }

  clearSearch() {
    this.searchForm.reset({
      approvedUser: '',
      paperReviewStatusList: [],
      dateRange: Constants.dateRangeConst.LAST_30_DAYS,
      upmGroupCode: '',
    }, {onlySelf: false, emitEvent: true});

    this.doSearch();
  }

  getSearchData() {
    let searchObj = Object.assign({},
      this.searchForm.getRawValue(),
      {...this.fromDateAndToDate},
      {loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode()}
    );
    this.facilityPaperReviewService.facilityReviewSearchDetails.next(searchObj);
    return searchObj;
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPaperReviewService.searchFacilityReviewSummery(this.getSearchData(), new Pagination(event));
  }

  doSearch() {
    let searchObj = Object.assign({}, this.getSearchData(), {...this.fromDateAndToDate});
    if ((searchObj.dateRange !== Constants.dateRangeConst.CUSTOM) || (searchObj.dateRange == Constants.dateRangeConst.CUSTOM && this.fromDateAndToDate.fromDate && this.fromDateAndToDate.toDate)) {
      this.facilityPaperReviewService.searchFacilityReviewSummery(searchObj, new Pagination({
        pageSize: this.pageSize.pageSize,
        pageIndex: 0
      }));
    }
  }

  ngOnDestroy(): void {
    this.onSearchFormChangeSub.unsubscribe();
    this.onFPReviewSummaryChangeSub.unsubscribe();
  }

  viewFacilityPapers(facilitySummary): void {

    this.facilityPaperReviewService.viewFacilities(facilitySummary);
    if (facilitySummary.assignedUserID == null) {
      this.selectedAssignUserName = null;
    } else {
      this.selectedAssignUserName = this.urlEncodeService.encode(facilitySummary.assignedUserID);
    }
    this.router.navigate(['/facility-review/paper-review']);
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '');
  }

  OpenDateRangeModelModal() {

    this.modalRef = this.mdbModalService.show(FromToDateDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-70-p ',
      containerClass: 'right',
      animated: true,
      data: {
        heading: "Select Date Range",
      }
    });
    this.modalRef.content.action.subscribe((response: any) => {
      if (response) {
        this.fromDateAndToDate = response;
        this.doSearch();
      } else {
        this.searchForm.get("dateRange").setValue(Constants.dateRangeConst.LAST_30_DAYS);
      }
    });
  }

  toDate(date) {
    if (!_.isNull(date)) {
      return date.toDateString();
    } else {
      return '';
    }
  }
}
