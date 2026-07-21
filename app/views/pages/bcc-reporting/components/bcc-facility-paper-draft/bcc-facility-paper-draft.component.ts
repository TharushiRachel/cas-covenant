import { Component, OnInit } from '@angular/core';
import { BccFacilityPaperService } from '../../services/bcc-facility-paper.service';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { Pagination } from 'src/app/core/dto/pagination';
import { PageSize } from 'src/app/core/dto/page.size';
import { Subscription } from 'rxjs';
import { PrivilegeService } from 'src/app/core/service/authentication/privilege.service';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { Constants } from 'src/app/core/setting/constants';
import { AppUtils } from 'src/app/shared/app.utils';
import { isEmpty } from 'lodash';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { Router } from '@angular/router';
import { LocalStorage } from 'ngx-webstorage';
import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IMyOptions } from 'ng-uikit-pro-standard';
import * as _ from 'lodash';

@Component({
  selector: 'app-bcc-facility-paper-draft',
  templateUrl: './bcc-facility-paper-draft.component.html',
  styleUrls: ['./bcc-facility-paper-draft.component.scss']
})
export class BccFacilityPaperDraftComponent implements OnInit {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID_FOR_BCC_REPORTING)
  selectedFacilityPaperID;

  pageSize = new PageSize();
  selectedFacilityPapers: any = [];
  onSelectFacilityPaperChangeSub = new Subscription();

  searchForm: FormGroup;

  myDatePickerOptions: IMyOptions = {
    dateFormat: 'dd/mm/yyyy',
    showTodayBtn: false,
    closeAfterSelect: true,
    firstDayOfWeek: 'mo',
  };

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  hasPrivilegeToView = false;
  hasPrivilegeToGenerateBCCPaper = false;
  allBankOptions: any = {};
  tableColumns = ['Customer Name', 'Ref Number', 'Account Number', 'Branch', 'Approved On', 'Approved By', 'Status'];


  constructor(
    private bccReportingService: BccFacilityPaperService,
    private applicationService: ApplicationService,
    private cacheService: CacheService,
    private formBuilder: FormBuilder,
    private router: Router,
    private urlEncodeService: UrlEncodeService,
    private privilegeService: PrivilegeService
  ) { }

  ngOnInit() {

    this.hasPrivilegeToView = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_BCC_PAPER_VIEW);
    this.hasPrivilegeToGenerateBCCPaper = this.privilegeService.hasPrivilege(this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_GENERATE_BCC_PAPER);

    this.onSelectFacilityPaperChangeSub = this.bccReportingService.onSelectedFacilityPaperChangeForDraftBCC
    .subscribe(data => {
      if (!_.isEmpty(data)) {
        this.selectedFacilityPapers = data.pageData;
        this.pageSize.length = data.totalNoOfRecords;
        this.pageSize.pageIndex = data.currentPageNo - 1;
      }
      
            
    });

    this.searchForm = this.formBuilder.group({
      fpRefNumber: [''],
      bankAccountID: [''],
      createdFromDateStr: [''],
      createdToDateStr: [''],
    });

    this.bccReportingService.searchFacilityPapersByUserName(this.getSearchData(), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));

  }

  getSearchData() {

    let data = Object.assign(this.searchForm.getRawValue(), {
      currentAssignUser: this.applicationService.getLoggedInUserUserName(),
    }
  );

    return data;
  }

  onPageEvent(event) {
    this.pageSize.pageSize = event.pageSize;
    this.bccReportingService.searchFacilityPapersByUserName(this.getSearchData(), new Pagination(event));
  }

  isAbleToNavigateBCC(facilityPaper) {
    // this if user has privilege to generate bcc paper or ( user with view privilege and already bcc paper created) 
    return this.hasPrivilegeToGenerateBCCPaper || (this.hasPrivilegeToView && facilityPaper.isBccCreated == Constants.yesNoConst.Y);
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName + ' - ' + branch.branchCode;
    }
    return branchCode;
  }

  loadFacilityPaper(facilityPaperID) {
    this.selectedFacilityPaperID = this.urlEncodeService.encode(facilityPaperID);
    this.router.navigate(['/bcc-reporting/bcc-facility-paper']);
  }

  clearSearch() {
    this.searchForm.reset({
      fpRefNumber: '',
      bankAccountID: '',
      createdFromDateStr: '',
      createdToDateStr: '',
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

    this.bccReportingService.searchFacilityPapersByUserName(this.getSearchData(), new Pagination({
      pageSize: this.pageSize.pageSize,
      pageIndex: 0
    }));

    this.searchForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
  }


  isSearchDataEntered(): boolean {

    if ((this.searchForm.value.fpRefNumber) || (this.searchForm.value.bankAccountID) || 
        (this.searchForm.value.createdFromDateStr) || (this.searchForm.value.createdToDateStr))  {

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
