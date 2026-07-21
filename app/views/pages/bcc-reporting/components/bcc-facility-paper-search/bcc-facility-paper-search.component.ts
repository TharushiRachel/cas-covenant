import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IMyOptions } from 'ng-uikit-pro-standard';
import { LocalStorage } from 'ngx-webstorage';
import { Subscription } from 'rxjs';
import { PageSize } from 'src/app/core/dto/page.size';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';
import { BccFacilityPaperService } from '../../services/bcc-facility-paper.service';
import { SearchDataCacheService } from 'src/app/core/service/common/search-data-cache.service';
import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';
import { Router } from '@angular/router';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { PrivilegeService } from 'src/app/core/service/authentication/privilege.service';
import { AppUtils } from 'src/app/shared/app.utils';
import { isEmpty } from 'lodash';
import { Pagination } from 'src/app/core/dto/pagination';
import * as _ from 'lodash';

@Component({
  selector: 'app-bcc-facility-paper-search',
  templateUrl: './bcc-facility-paper-search.component.html',
  styleUrls: ['./bcc-facility-paper-search.component.scss']
})
export class BccFacilityPaperSearchComponent implements OnInit, OnDestroy {

  uniquePageName = 'BccFacilityPapersComponent-#343uIa';
  facilityPaperStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityRoutingStatus = Constants.facilityRoutingStatus;
  bccFacilityPaperStatusConst = Constants.bccFacilityPaperStatusConst;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_BCC_REPORTING)
  selectedFacilityPaperID;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  hasPrivilegeToView = false;
  hasPrivilegeToGenerateBCCPaper = false;


  tableColumns = ['Customer Name', 'Ref Number', 'Account Number', 'Branch', 'Approved On', 'Approved By', 'Status'];

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

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.bccReportingService.searchFacilityPapers(this.getSearchData(), new Pagination(event));
  }

  clearSearch() {
    this.searchForm.reset({
      fpRefNumber: '',
      currentAssignUser: '',
      intiatedUserName: '',
      leadRefNumber: '',
      customerName: '',
      bankAccountID: '',
      createdFromDateStr: '',
      createdToDateStr: '',
      branchCode:'',
      facilityPaperStatus: this.bccFacilityPaperStatusConst.APPROVED
    }, {onlySelf: false, emitEvent: true});

    this.myDatePickerOptions = {
      dateFormat: 'dd/mm/yyyy',
      showTodayBtn: false,
      closeAfterSelect: true,
      firstDayOfWeek: 'mo',
    };
    this.selectedFacilityPapers = [];
  }

  doSearch() {

    this.onSelectFacilityPaperChangeSub = this.bccReportingService.onSelectedFacilityPaperChange
    .subscribe(data => {
      if (!_.isEmpty(data)) {
        this.selectedFacilityPapers = this.bccReportingService.selectedFacilityPapers;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
        }
     
    });

    this.bccReportingService.searchFacilityPapers(this.getSearchData(), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));

    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }

  loadFacilityPaper(facilityPaperID) {
    this.selectedFacilityPaperID = this.urlEncodeService.encode(facilityPaperID);
    this.router.navigate(['/bcc-reporting/bcc-facility-paper']);
  }

  isAbleToNavigateBCC(facilityPaper) {
    // this if user has privilege to generate bcc paper or ( user with view privilege and already bcc paper created) 
    return this.hasPrivilegeToGenerateBCCPaper || (this.hasPrivilegeToView && facilityPaper.isBccCreated == Constants.yesNoConst.Y);
  }

  getColor(facilityStatus) {
    switch (facilityStatus) {
      case this.bccFacilityPaperStatusConst.IN_PROGRESS:
        return 'light-blue';
      case this.bccFacilityPaperStatusConst.APPROVED:
        return 'pink';
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

  isSearchDataEntered(): boolean {

    if ((this.searchForm.value.fpRefNumber) || (this.searchForm.value.currentAssignUser) ||
        (this.searchForm.value.intiatedUserName) || (this.searchForm.value.leadRefNumber) ||
        (this.searchForm.value.customerName) || (this.searchForm.value.bankAccountID) || 
        (this.searchForm.value.createdFromDateStr) || (this.searchForm.value.createdToDateStr) || 
        (this.searchForm.value.branchCode))  {

      return false
    } else {

      return true
    }

  }

  keyPressNumbers(event) {

    var charCode = (event.which) ? event.which : event.keyCode;
    // Only Numbers 0-9
    if ((charCode < 48 || charCode > 57)) {

      return false;
    } else {
      return true;
    }
  }

}
