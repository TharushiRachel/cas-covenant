import {Component, OnDestroy, OnInit} from '@angular/core';
import {Constants} from "../../../../../core/setting/constants";
import {LocalStorage} from "ngx-webstorage";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {PageSize} from "../../../../../core/dto/page.size";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {CacheSearchData, SearchDataCacheService} from "../../../../../core/service/common/search-data-cache.service";
import {UrlEncodeService} from "../../../../../core/service/application/url-encode.service";
import {Router} from "@angular/router";
import {CacheService} from "../../../../../core/service/data/cache.service";
import {ApplicationService} from "../../../../../core/service/application/application.service";
import {Pagination} from "../../../../../core/dto/pagination";
import {AppUtils} from "../../../../../shared/app.utils";
import {isEmpty} from "lodash";
import {BccFacilityPaperService} from "../../services/bcc-facility-paper.service";
import {PrivilegeService} from "../../../../../core/service/authentication/privilege.service";
import { IMyOptions } from 'ng-uikit-pro-standard';

@Component({
  selector: 'app-bcc-facility-papers',
  templateUrl: './bcc-facility-papers.component.html',
  styleUrls: ['./bcc-facility-papers.component.scss']
})
export class BccFacilityPapersComponent implements OnInit, OnDestroy {

  uniquePageName = 'BccFacilityPapersComponent-#343uIa';
  facilityPaperStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityRoutingStatus = Constants.facilityRoutingStatus;
  bccFacilityPaperStatusConst = Constants.bccFacilityPaperStatusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_BCC_REPORTING)
  selectedFacilityPaperID;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  hasPrivilegeToView = false;
  hasPrivilegeToGenerateBCCPaper = false;
  selectedTabIndex: any;

  onSelectedTabIndexChange = new Subscription();

  tableColumns = ['Customer Name', 'Ref Number', 'Account Number', 'Branch', 'Created On', 'Assigned User', 'BCC'];

  pageSize = new PageSize();

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  searchForm: FormGroup;
  selectedFacilityPapers: any = [];
  onSelectFacilityPaperChangeSub = new Subscription();
  onSearchFormChangeSub = new Subscription();

  //facilityPaperStatus = Constants.facilityPaperStatus;

  allBankOptions: any = {};
  optionsSelect: any = [
    {value: this.bccFacilityPaperStatusConst.IN_PROGRESS, label: this.facilityPaperStatus.IN_PROGRESS},
    {value: this.bccFacilityPaperStatusConst.APPROVED, label: this.facilityPaperStatus.APPROVED},
  ];

  isAgent;

  constructor(
    private bccReportingService: BccFacilityPaperService,
    private searchDataCacheService: SearchDataCacheService,
    private formBuilder: FormBuilder,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private cacheService: CacheService,
    private applicationService: ApplicationService,
    private privilegeService: PrivilegeService
  ) {
  }

  ngOnInit() {


    this.isAgent = this.applicationService.isAgent();
    this.hasPrivilegeToView = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_BCC_PAPER_VIEW);
    this.hasPrivilegeToGenerateBCCPaper = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_GENERATE_BCC_PAPER);

    this.onSelectedTabIndexChange = this.bccReportingService.onSelectedTabIndexFromBCCChange.subscribe(res => {
      this.selectedTabIndex = res;      
    });

    // this.onSelectFacilityPaperChangeSub = this.bccReportingService.onSelectedFacilityPaperChange
    //   .subscribe(data => {
    //     this.selectedFacilityPapers = this.bccReportingService.selectedFacilityPapers;
    //     this.pageSize.length = data.totalNoOfRecords;
    //     this.pageSize.pageIndex = data.currentPageNo - 1;

    //     //this.myFacilityPaperCountService.getLoggedUserFacilityPaperCount();
    //   });

    this.searchForm = this.formBuilder.group({
      fpRefNumber: [''],
      currentAssignUser: [''],
      intiatedUserName: [''],
      leadRefNumber: [''],
      customerName: [''],
      bankAccountID: [''],
      createdFromDateStr: [''],
      createdToDateStr: [''],
      branchCode:['',[Validators.required,Validators.pattern('^[0-9]{3}$')]],
      facilityPaperStatus: ['']
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



  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  onTabSelect($event) {   
    this.selectedTabIndex = $event;
    this.bccReportingService.onSelectedTabIndexFromBCCChange.next($event)
  }

  ngAfterViewInit(): void {
    this.onSelectedTabIndexChange = this.bccReportingService.onSelectedTabIndexFromBCCChange.subscribe(res => {
      this.selectedTabIndex = res;      
    });
  }

}
