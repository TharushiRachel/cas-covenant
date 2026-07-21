import {Component, OnDestroy, OnInit} from '@angular/core';
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../../core/setting/commons.settings";
import {Constants} from "../../../../../../core/setting/constants";
import {IMyOptions} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup} from "@angular/forms";
import {PageSize} from "../../../../../../core/dto/page.size";
import {FacilityPaperViewService} from "../../../services/facility-paper-view.service";
import {Subscription} from "rxjs";
import {UrlEncodeService} from "../../../../../../core/service/application/url-encode.service";
import {Pagination} from "../../../../../../core/dto/pagination";
import {CurrencyPipe} from "@angular/common";
import {Router} from "@angular/router";
import {FacilityReviewSearchDetails} from "../../../dtos/facility-review-search-details-dto";
import {FacilityPaperReviewService} from "../../../services/facility-paper-review.service";

@Component({
  selector: 'app-facility-list',
  templateUrl: './facility-list.component.html',
  styleUrls: ['./facility-list.component.scss']
})
export class FacilityListComponent implements OnInit, OnDestroy {
  uniquePageName = 'facilityList-#feTbtsdw';

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_ASSIGNED_USER_ID)
  selectedAssignUserID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_REVIEW)
  selectedFacilityPaperIDForReview;

  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityPaperStatus = Constants.facilityPaperStatusToAuthorityLevel;
  searchForm: FormGroup;
  onSearchFormChangeSub = new Subscription();
  onFacilityListChangeSub = new Subscription();
  onStatusListChangeSub = new Subscription();
  pageSize = new PageSize();
  facilityPapers: any[] = [];
  paperReviewStatusList: any[] = [];
  dateRangeConst = Constants.dateRangeConst;
  dateRange = Constants.dateRange;
  paperReviewStatusConst = Constants.paperReviewStatusConst;
  paperReviewStatus = Constants.paperReviewStatus;

  facilityReviewSearchDetails: FacilityReviewSearchDetails = new FacilityReviewSearchDetails({});

  public myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    minYear: 1900
  };

  optionsSelect: any = [
    {value: this.paperReviewStatusConst.ACTION_REQUIRED, label: this.paperReviewStatus.ACTION_REQUIRED},
    {value: this.paperReviewStatusConst.REJECTED, label: this.paperReviewStatus.REJECTED},
    {value: this.paperReviewStatusConst.APPROVED, label: this.paperReviewStatus.APPROVED},
    {value: this.paperReviewStatusConst.REPLIED, label: this.paperReviewStatus.REPLIED},
  ];

  tableColumns: any = [
    '#',
    'Customer',
    'Facility',
    'Facility Date',
    'Facility Amount',
    'No of Days',
    'Proposed',
    'Action'
  ];

  constructor(
    private formBuilder: FormBuilder,
    private facilityPaperViewService: FacilityPaperViewService,
    private facilityPaperReviewService: FacilityPaperReviewService,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private currencyPipe: CurrencyPipe) {
  }

  ngOnInit() {
    this.searchForm = this.createSearchForm();
    this.onSearchFormChangeSub.unsubscribe();
    this.facilityReviewSearchDetails = new FacilityReviewSearchDetails(this.facilityPaperViewService.facilityReviewSearchDetails);

    this.onFacilityListChangeSub = this.facilityPaperViewService.onFacilityPapersChangeSub
      .subscribe(data => {
        this.facilityPapers = data.pageData;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      });

    /*  this.onSearchFormChangeSub = this.searchForm.valueChanges
        .debounceTime(200)
        .distinctUntilChanged()
        .subscribe(form => {
          this.facilityPaperViewService.searchPagedFacilityPapers(this.getSearchData(), new Pagination({
            pageSize: this.pageSize.pageSize,
            pageIndex: 0
          }));
        });*/
  }

  ngOnDestroy(): void {
    this.onSearchFormChangeSub.unsubscribe();
    this.onFacilityListChangeSub.unsubscribe();
    this.onStatusListChangeSub.unsubscribe();
  }

  getSearchData() {
    let searchObj = Object.assign({}, this.searchForm.getRawValue(), {currentAssignedUserID: this.urlEncodeService.decode(this.selectedAssignUserID)}, {...this.facilityReviewSearchDetails});
    return searchObj;
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPaperViewService.searchPagedFacilityPapers(this.getSearchData(), new Pagination(event));
  }


  viewFacilityPapers(facilityPaper): void {
    if (facilityPaper.facilityPaperID == null) {
      this.selectedFacilityPaperIDForReview = null;
    } else {
      this.selectedFacilityPaperIDForReview = this.urlEncodeService.encode(facilityPaper.facilityPaperID);
    }
    this.router.navigate(['/facility-review/paper-template']);

  }

  createSearchForm() {

    let statusList = [];
    this.onStatusListChangeSub = this.facilityPaperReviewService.facilityReviewSearchDetails.subscribe(response => {
      statusList = response.paperReviewStatusList
    });

    return this.formBuilder.group({
      customerName: '',
      fpRefNumber: '',
      paperReviewStatusList: [statusList]
    });
  }

  clearSearch() {
    this.searchForm.reset({
      customerName: '',
      fpRefNumber: '',
      paperReviewStatusList: ''
    }, {onlySelf: false, emitEvent: true})
  }

  doSearch() {
    let searchObj = Object.assign({}, {...this.facilityReviewSearchDetails}, this.searchForm.getRawValue(), {currentAssignedUserID: this.urlEncodeService.decode(this.selectedAssignUserID)});
    this.facilityPaperViewService.searchPagedFacilityPapers(searchObj, new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));
    // this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  getCurrencyFormat(amount) {
    return this.currencyPipe.transform(amount, '', '');
  }

  getBadge(status) {
    switch (status) {
      case this.paperReviewStatusConst.SAVED :
        return "S";

      case this.paperReviewStatusConst.REJECTED:
        return "R";

      case this.paperReviewStatusConst.REPLIED:
        return "R";

      case this.paperReviewStatusConst.APPROVED:
        return "A";

      case this.paperReviewStatusConst.ACTION_REQUIRED:
        return "A";

      default:
        return "";
    }
  }

  getBadgeColor(status) {
    switch (status) {
      case this.paperReviewStatusConst.SAVED :
        return "#33B5E5";

      case this.paperReviewStatusConst.REJECTED:
        return "#F94445";

      case this.paperReviewStatusConst.ACTION_REQUIRED:
      case this.paperReviewStatusConst.REPLIED:
        return "#FFC107";

      case this.paperReviewStatusConst.APPROVED:
        return "#51C953";

      default:
        return "";
    }
  }

  getTooTip(status) {
    switch (status) {
      case this.paperReviewStatusConst.SAVED :
        return "Saved";

      case this.paperReviewStatusConst.REJECTED:
        return "Rejected";

      case this.paperReviewStatusConst.ACTION_REQUIRED:
        return "Action Required";

      case this.paperReviewStatusConst.APPROVED:
        return "Approved";

      case this.paperReviewStatusConst.REPLIED:
        return "Replied";

      default:
        return "";
    }
  }
}
