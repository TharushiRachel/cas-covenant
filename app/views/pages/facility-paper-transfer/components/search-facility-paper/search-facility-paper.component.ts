import {Component, OnDestroy, OnInit} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {MyFacilityPaperCountService} from "../../../../../core/service/facility-paper/my-facility-paper-count.service";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {AppUtils} from "../../../../../shared/app.utils";
import {isEmpty} from "lodash";
import {FacilityPaperTransferSearchService} from "../../services/facility-paper-transfer-search.service";

@Component({
  selector: 'app-search-facility-paper',
  templateUrl: './search-facility-paper.component.html',
  styleUrls: ['./search-facility-paper.component.scss']
})
export class SearchFacilityPaperComponent implements OnInit, OnDestroy {

  facilityPaperStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityRoutigStatus = Constants.facilityRoutingStatus;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_TRANSFER_FACILITY_PAPER_ID)
  selectedFacilityPaperToTransferID;

  uniquePageName = 'SearchFacilityPaperComponent-#ayer789777';

  tableColumns = ['Ref Number', 'Account Number', 'Branch', 'Created On', 'Assigned User', 'Status'];

  pageSize = new PageSize();

  searchForm: FormGroup;
  facilityPaper: any;
  onSelectFacilityPaperChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();

  allBankOptions: any = {};
  optionsSelect: any = [
    {value: this.facilityPaperStatusConst.IN_PROGRESS, label: this.facilityPaperStatus.IN_PROGRESS},
    {value: this.facilityPaperStatusConst.REJECTED, label: this.facilityPaperStatus.REJECTED},
    {value: this.facilityPaperStatusConst.APPROVED, label: this.facilityPaperStatus.APPROVED},
    {value: this.facilityPaperStatusConst.DRAFT, label: this.facilityPaperStatus.DRAFT},
    {value: this.facilityPaperStatusConst.CANCEL, label: this.facilityPaperStatus.CANCEL},
  ];

  constructor(
    private facilityPaperTransferSearchService: FacilityPaperTransferSearchService,
    private searchDataCacheService: SearchDataCacheService,
    private formBuilder: FormBuilder,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private myFacilityPaperCountService: MyFacilityPaperCountService,
    private cacheService: CacheService,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {

    this.onSelectFacilityPaperChangeSub = this.facilityPaperTransferSearchService.onSelectedFacilityPaperChange
      .subscribe((data: any) => {
        if (data) {
          this.facilityPaper = data;
        }
      });

    this.searchForm = this.formBuilder.group({
      fpRefNumber: ['', [Validators.required]],
      loginUpmAccessLevel: [this.applicationService.getLoggedInUserUPMGroupCode()],
      loggedInUserBranchCode: [this.applicationService.getLoggedInUserDivCode()],
    });
  }

  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.getSearchData()
    });
    this.onSearchFormChangeSub.unsubscribe();
    this.onSelectFacilityPaperChangeSub.unsubscribe();
  }

  getSearchData() {
    return this.searchForm.getRawValue();
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.facilityPaperTransferSearchService.searchFacilityPapers(this.getSearchData());
  }

  clearSearch() {
    this.searchForm.reset({
      fpRefNumber: '',
      loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
    }, {onlySelf: false, emitEvent: false});
    this.facilityPaper = {};
  }

  doSearch() {
    this.facilityPaper = null;
    this.facilityPaperTransferSearchService.searchFacilityPapers(this.getSearchData());
  }

  loadFacilityPaper(facilityPaperID) {
    this.selectedFacilityPaperToTransferID = this.urlEncodeService.encode(facilityPaperID);
    this.router.navigate(['/facility-paper-transfer/transfer']);
  }

  getColor(facilityStatus) {
    switch (facilityStatus) {
      case this.facilityPaperStatusConst.IN_PROGRESS:
        return 'light-blue';
      case this.facilityPaperStatusConst.APPROVED:
        return 'pink';
      case this.facilityPaperStatusConst.CANCEL:
        return 'orange';
      case this.facilityPaperStatusConst.REJECTED:
        return 'amber';
      case this.facilityPaperStatusConst.DRAFT:
        return 'mdb-color';
      default:
        return 'cyan';
    }
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

  isNotEmpty(data) {
    return !isEmpty(data);
  }
}
