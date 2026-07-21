import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs/Rx";
import { FormBuilder, FormGroup } from "@angular/forms";
import { PageSize } from "../../../../../core/dto/page.size";
import { FacilityPapersService } from "../../services/facility-papers.service";
import {
  CacheSearchData,
  SearchDataCacheService,
} from "../../../../../core/service/common/search-data-cache.service";
import { Router } from "@angular/router";
import { Pagination } from "../../../../../core/dto/pagination";
import { Constants } from "../../../../../core/setting/constants";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "../../../../../core/setting/commons.settings";
import { UrlEncodeService } from "../../../../../core/service/application/url-encode.service";
import { MyFacilityPaperCountService } from "../../../../../core/service/facility-paper/my-facility-paper-count.service";
import { AppUtils } from "../../../../../shared/app.utils";
import { CacheService } from "../../../../../core/service/data/cache.service";
import { isEmpty } from "lodash";
import { ApplicationService } from "../../../../../core/service/application/application.service";

@Component({
  selector: "app-facility-papers",
  templateUrl: "./facility-papers.component.html",
  styleUrls: ["./facility-papers.component.scss"],
})
export class FacilityPapersComponent implements OnInit, OnDestroy {
  facilityPaperStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityRoutigStatus = Constants.facilityRoutingStatus;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityPaperReviewStatusConst = Constants.paperReviewStatusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID)
  selectedFacilityPaperID;

  uniquePageName = "FacilityPapersComponent-#ayer789656";

  tableColumns = [
    "Customer Name",
    "Ref Number",
    "Account Number",
    "Branch",
    "Created Date",
    "Assigned User",
    "Status",
  ];

  pageSize = new PageSize();

  searchForm: FormGroup;
  selectedFacilityPapers: any = [];
  onSelectFacilityPaperChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();

  //facilityPaperStatus = Constants.facilityPaperStatus;

  allBankOptions: any = {};
  optionsSelect: any = [
    {
      value: this.facilityPaperStatusConst.IN_PROGRESS,
      label: this.facilityPaperStatus.IN_PROGRESS,
    },
    {
      value: this.facilityPaperStatusConst.REJECTED,
      label: this.facilityPaperStatus.REJECTED,
    },
    {
      value: this.facilityPaperStatusConst.APPROVED,
      label: this.facilityPaperStatus.APPROVED,
    },
    {
      value: this.facilityPaperStatusConst.DRAFT,
      label: this.facilityPaperStatus.DRAFT,
    },
    // {value: this.facilityPaperStatusConst.PENDING, label: this.facilityPaperStatus.PENDING},
    // {value: this.facilityPaperStatusConst.REMOVED, label: this.facilityPaperStatus.REMOVED},
    {
      value: this.facilityPaperStatusConst.CANCEL,
      label: this.facilityPaperStatus.CANCEL,
    },
  ];

  isAgent;

  constructor(
    private facilityPapersService: FacilityPapersService,
    private searchDataCacheService: SearchDataCacheService,
    private formBuilder: FormBuilder,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private myFacilityPaperCountService: MyFacilityPaperCountService,
    private cacheService: CacheService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.isAgent = this.applicationService.isAgent();

    this.onSelectFacilityPaperChangeSub =
      this.facilityPapersService.onselectedFacilityPaperChange.subscribe(
        (data) => {
          this.selectedFacilityPapers =
            this.facilityPapersService.selectedFacilityPapers;
          this.pageSize.length = data.totalNoOfRecords;
          this.pageSize.pageIndex = data.currentPageNo - 1;
          //this.myFacilityPaperCountService.getLoggedUserFacilityPaperCount();
        }
      );

    this.searchForm = this.formBuilder.group({
      fpRefNumber: [""],
      assignUserDisplayName: [""],
      bankAccountID: [""],
      leadRefNumber: [""],
      customerName: [""],
      facilityPaperStatus: [""],
    });

    this.onSearchFormChangeSub = this.searchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe((form) => {
        this.facilityPapersService.searchFacilityPapers(
          AppUtils.trim(this.getSearchData()),
          new Pagination({
            pageSize: this.pageSize.pageSize,
            pageIndex: 0,
          })
        );
      });

    if (this.searchDataCacheService.hasSearchData(this.uniquePageName)) {
      let data: CacheSearchData = this.searchDataCacheService.getSearchData(
        this.uniquePageName
      );
      this.searchForm.setValue(data.searchData, {
        onlySelf: true,
        emitEvent: false,
      });
      this.pageSize = data.pageSize;
      this.facilityPapersService.searchFacilityPapers(
        AppUtils.trim(data.searchData),
        new Pagination({
          pageSize: data.pageSize.pageSize,
          pageIndex: data.pageSize.pageIndex,
        })
      );
    }
  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData(),
    });
    this.onSearchFormChangeSub.unsubscribe();
    this.onSelectFacilityPaperChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPapersService.searchFacilityPapers(
      AppUtils.trim(this.getSearchData()),
      new Pagination(event)
    );
  }

  clearSearch() {
    this.searchForm.reset(
      {
        fpRefNumber: "",
        assignUserDisplayName: "",
        bankAccountID: "",
        leadRefNumber: "",
        customerName: "",
        facilityPaperStatus: "",
      },
      { onlySelf: false, emitEvent: true }
    );
  }

  doSearch() {
    this.searchForm.updateValueAndValidity({
      onlySelf: false,
      emitEvent: true,
    });
  }

  loadFacilityPaper(facilityPaperID) {
    this.selectedFacilityPaperID =
      this.urlEncodeService.encode(facilityPaperID);
    this.router.navigate(["/facility-paper/edit"]);
  }

  getColor(facilityStatus) {
    switch (facilityStatus) {
      // case this.facilityPaperStatusConst.REMOVED:
      //   return 'purple';
      case this.facilityPaperStatusConst.IN_PROGRESS:
        return "light-blue";
      // case this.facilityPaperStatusConst.PENDING:
      //   return 'teal';
      case this.facilityPaperStatusConst.APPROVED:
        return "pink";
      case this.facilityPaperStatusConst.CANCEL:
        return "orange";
      case this.facilityPaperStatusConst.REJECTED:
        return "amber";
      case this.facilityPaperStatusConst.DRAFT:
        return "mdb-color";
      default:
        return "cyan";
    }
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES
    );
    let branch = AppUtils.getBranchFromBranchCode(
      this.allBankOptions,
      branchCode
    );

    if (!isEmpty(branch)) {
      return branch.branchName + " - " + branch.branchCode;
    }
    return branchCode;
  }
}
