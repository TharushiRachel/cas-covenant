import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../../../../../core/setting/constants";
import {Subscription} from "rxjs";
import {FacilityPaperReviewService} from "../../../../services/facility-paper-review.service";
import {CurrencyPipe} from "@angular/common";
import {PageSize} from "../../../../../../../core/dto/page.size";
import {ApplicationService} from "../../../../../../../core/service/application/application.service";
import {UrlEncodeService} from "../../../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../../core/setting/commons.settings";
import {Pagination} from "../../../../../../../core/dto/pagination";

@Component({
  selector: 'app-own-approved-paper-summary',
  templateUrl: './own-approved-paper-summary.component.html',
  styleUrls: ['./own-approved-paper-summary.component.scss']
})
export class OwnApprovedPaperSummaryComponent implements OnInit, OnDestroy {

  uniquePageName = 'ownApprovedPaperSummary-#feTKD';

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_ASSIGNED_USER_ID)
  selectedAssignUserID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_REVIEW)
  selectedFacilityPaperIDForReview;

  onStatusListChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();
  onFacilityPapersChangeSub = new Subscription();
  searchForm: FormGroup;
  paperReviewStatusConst = Constants.paperReviewStatusConst;
  paperReviewStatus = Constants.paperReviewStatus;
  ownApprovedFacilityPapers: any[] = [];
  pageSize = new PageSize();

  optionsSelect: any = [
    {value: this.paperReviewStatusConst.ACTION_REQUIRED, label: this.paperReviewStatus.ACTION_REQUIRED},
    {value: this.paperReviewStatusConst.REJECTED, label: this.paperReviewStatus.REJECTED},
    {value: this.paperReviewStatusConst.APPROVED, label: this.paperReviewStatus.APPROVED},
    {value: this.paperReviewStatusConst.REPLIED, label: this.paperReviewStatus.REPLIED},
  ];

  constructor(
    private formBuilder: FormBuilder,
    private facilityPaperReviewService: FacilityPaperReviewService,
    private currencyPipe: CurrencyPipe,
    private applicationService: ApplicationService,
    private urlEncodeService: UrlEncodeService,
    private router: Router) {
  }

  ngOnInit() {
    this.searchForm = this.createSearchForm();
    this.facilityPaperReviewService.getOwnApprovedFacilityPapers({paperReviewStatusList: [this.paperReviewStatusConst.REJECTED]});

    this.onFacilityPapersChangeSub = this.facilityPaperReviewService.onOwnApprovedFPChange.subscribe(response => {
      this.ownApprovedFacilityPapers = [];
      this.ownApprovedFacilityPapers = response.pageData;
      this.pageSize.length = response.totalNoOfRecords;
      this.pageSize.pageIndex = response.currentPageNo - 1;
    });

  }

  ngOnDestroy() {
    this.onFacilityPapersChangeSub.unsubscribe();
    this.onSearchFormChangeSub.unsubscribe();
    this.onStatusListChangeSub.unsubscribe();
  }

  clearSearch() {
    this.searchForm.reset({
      customerName: '',
      fpRefNumber: '',
      paperReviewStatusList: [this.paperReviewStatusConst.REJECTED]
    }, {onlySelf: false, emitEvent: true});

    this.facilityPaperReviewService.searchOwnApprovedFacilityPapers({
      ...this.searchForm.getRawValue(),
      paperReviewStatusList: [this.paperReviewStatusConst.REJECTED]
    });
  }

  doSearch() {
    this.facilityPaperReviewService.searchOwnApprovedFacilityPapers(this.searchForm.getRawValue());
  }

  createSearchForm() {
    let statusList = [this.paperReviewStatusConst.REJECTED];
    return this.formBuilder.group({
      customerName: '',
      fpRefNumber: '',
      paperReviewStatusList: [statusList]
    });
  }

  viewFacilityPapers(facilityPaper: any) {
    if (facilityPaper.facilityPaperID == null) {
      this.selectedFacilityPaperIDForReview = null;
    } else {
      this.selectedFacilityPaperIDForReview = this.urlEncodeService.encode(facilityPaper.facilityPaperID);
    }
    this.router.navigate(['/facility-review/paper-template']);
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '');
  }

  onPageEvent(event: any) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPaperReviewService.searchOwnApprovedFacilityPapers({}, new Pagination(event));
  }

  getSearchData() {
    let searchObj = Object.assign({}, this.searchForm.getRawValue(), {currentAssignedUserID: this.applicationService.getLoggedInUserUserID()});
    return searchObj;
  }

}
