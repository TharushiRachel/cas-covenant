import { Component, OnDestroy, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { NavigationEnd, Router } from "@angular/router";
import { CreateCoveringApprovalComponent } from '../create-covering-approval/create-covering-approval.component';
import { LocalStorage } from 'ngx-webstorage';
import { SETTINGS } from 'src/app/core/setting/commons.settings';
import { PageSize } from 'src/app/core/dto/page.size';
import { Subscription } from 'rxjs';
import { Constants } from 'src/app/core/setting/constants';
import { CoveringApprovalService } from '../../services/covering-approval.service';
import { UrlEncodeService } from 'src/app/core/service/application/url-encode.service';
import { ApplicationService } from 'src/app/core/service/application/application.service';
import { Pagination } from 'src/app/core/dto/pagination';
import { AppUtils } from 'src/app/shared/app.utils';
import { CoveringApprovalSharedService } from '../../services/covering-approval-shared.service';
import { CacheService } from 'src/app/core/service/data/cache.service';
import { isEmpty } from 'lodash';
import { SearchDataCacheService } from 'src/app/core/service/common/search-data-cache.service';
import { CurrencyPipe } from '@angular/common';
@Component({
  selector: 'app-covering-approval-dashboard',
  templateUrl: './covering-approval-dashboard.component.html',
  styleUrls: ['./covering-approval-dashboard.component.scss']
})
export class CoveringApprovalDashboardComponent implements OnInit, OnDestroy {

  @LocalStorage(SETTINGS.STORAGE.SELECTED_COV_ID)
  selectedCovID;

  uniquePageName = 'CoveringApprovalDashboardComponent-#619cas';
  pageSize = new PageSize();
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  onCoveringApprovalChangeSub: Subscription = new Subscription();
  onCoveringApprovalCountsChangeSub: Subscription = new Subscription();

  onCoveringApprovalDashboardChangeSub: Subscription = new Subscription();

  coveringApprovalStatusConst = Constants.coveringApprovalStatusConst;
  coveringApprovalStatus = Constants.coveringApprovalStatus;

  coveringApprovalDashboardStatus = Constants.coveringApprovalDashboardStatus;
  coveringApprovalDashboardStatusConst = Constants.coveringApprovalDashboardStatusConst;

  coveringApprovalInboxSubStatusConst = Constants.coveringApprovalInboxSubStatusConst;
  coveringApprovalForwardedStatusConst = Constants.coveringApprovalForwardedStatusConst;
  coveringApprovalCancelStatusConst = Constants.coveringApprovalCancelStatusConst;

  coveringApprovalReturnedStatusConst = Constants.coveringApprovalReturnedStatusConst;


  status: String = this.coveringApprovalDashboardStatusConst.INBOX;

  accessLevelOfCurrentAssignUser: any;

  coveringApprovals: any = [];
  coveringApprovalsPage: any = [];
  searchForm: FormGroup;
  testPending: any = [];


  coveringApprovalCounts: any = {
    inboxCovApp: 0,
    inProgressCovApp: 0,
    returnedCovApp: 0,
    approvedCovApp: 0,
    rejectedCovApp: 0,
    dashboardTimePeriodDays: 0
  };
  tableColumns: any = ['Customer Name', 'Ref Number', 'Account Number', 'Branch', 'Date', 'Cheque Number', 'Amount', 'Asssign User'];

  modalRef: MDBModalRef;
  allBankOptions: any = {};

  constructor(
    private mdbModalService: MDBModalService,
    private coveringApprovalService: CoveringApprovalService,
    private urlEncodeService: UrlEncodeService,
    private cacheService: CacheService,
    private router: Router,
    private applicationService: ApplicationService,
    private coveringApprovalSharedService: CoveringApprovalSharedService,
    private searchDataCacheService: SearchDataCacheService,
    private formBuilder: FormBuilder,
    private currencyPipe: CurrencyPipe,

  ) { }

  ngOnInit() {
    this.searchForm = this.formBuilder.group({
      searchControl: ['']
    });
    this.onCoveringApprovalCountsChangeSub = this.coveringApprovalService.onCoveringApprovalCountsChange
      .subscribe(data => {
        this.coveringApprovalCounts = data;
        this.loadInitiateDashboardPage();
      })


    this.loadBranchesFromLocalStorage();
  }

  loadBranchesFromLocalStorage() {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES) || {};
    for (const [key, value] of Object.entries(localStorage)) {
      if (!this.allBankOptions[key]) {
        this.allBankOptions[key] = { branchName: value.split(' - ')[0], branchCode: key };
      }
    }
  }
  ngOnDestroy(): void {
    this.searchDataCacheService.setSearchData(this.uniquePageName, {
      pageSize: Object.assign({}, this.pageSize),
      searchData: this.searchForm ? this.getSearchData() : null // Check if searchForm is defined
    });
    this.onCoveringApprovalChangeSub.unsubscribe();
    this.onCoveringApprovalCountsChangeSub.unsubscribe();
  }
  getSearchData() {
    return this.searchForm ? this.searchForm.getRawValue() : {};
  }
  onPageEvent(event, coveringApprovalDashboardSubStatus) {
    this.pageSize.pageSize = event.pageSize;
    this.coveringApprovalService.getCoveringApprovalByStatus({
      loggedInUserId: this.applicationService.getLoggedInUserUserID(),
      loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
      loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
      coveringApprovalDashboardStatus: this.status,
      coveringApprovalDashboardSubStatus: coveringApprovalDashboardSubStatus,

    }, new Pagination(event))
      .then((data: any) => {
        this.coveringApprovals[coveringApprovalDashboardSubStatus] = data.pageData;
        this.coveringApprovalsPage[coveringApprovalDashboardSubStatus] = data;
      });
  }
  loadCoveringApproval(covAppId, accountNumber) {
    if (covAppId == null) {
      this.selectedCovID = null;
    } else {
      this.selectedCovID = this.urlEncodeService.encode(covAppId);
    }
    let testAccountNumber = Object.assign({
      accountNumber: accountNumber
    });
    this.router.navigate(['/covering-approval/request-approval']);
    if (this.isEqualLoginAndAssignUser()) {
      let pendingCoveringApprovalsPromise = this.coveringApprovalService.getPendingCoveringApprovals(testAccountNumber)
      let coveringApprovalsByIDPromise = this.coveringApprovalService.getCoveringApprovalByID();
  
      Promise.all([pendingCoveringApprovalsPromise, coveringApprovalsByIDPromise])
        .then(([pendingCoveringApprovalsResponse, coveringApprovalByIDResponse]) => {
  
        })
    }
    
    //this.router.navigate(['/covering-approval/request-approval']);
  }

  isEqualLoginAndAssignUser() {
    // if (
    //   this.coveringApproval.assignUserUpmGroupCode ==
    //   this.applicationService.getLoggedInUserUPMGroupCode()
    // ) 
    this.accessLevelOfCurrentAssignUser = this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser >= 71) {
      return true;
    } else {
      return false;
    }

  }

  loadInitiateDashboardPage() {
    this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.INBOX, this.coveringApprovalInboxSubStatusConst.DRAFT, this.coveringApprovalCounts.inboxCovApp);
    this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.INBOX, this.coveringApprovalInboxSubStatusConst.RETURNED_TO_ME, this.coveringApprovalCounts.inboxCovApp);
    this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.INBOX, this.coveringApprovalInboxSubStatusConst.RECEIVED_TO_ME, this.coveringApprovalCounts.inboxCovApp);
  }

  loadSubStatusData(coveringApprovalDashboardStatus, coveringApprovalDashboardSubStatus, subStatusCount) {
    if (subStatusCount != 0) {
      this.coveringApprovalService.getCoveringApprovalByStatus({
        loggedInUserId: this.applicationService.getLoggedInUserUserID(),
        loggedInUserBranchCode: this.applicationService.getLoggedInUserDivCode(),
        loginUpmAccessLevel: this.applicationService.getLoggedInUserUPMGroupCode(),
        coveringApprovalDashboardStatus: coveringApprovalDashboardStatus,
        coveringApprovalDashboardSubStatus: coveringApprovalDashboardSubStatus,

      },
        new Pagination({
          pageSize: this.pageSize.pageSize,
          pageIndex: 0
        })
      ).then((data: any) => {
        this.coveringApprovals[coveringApprovalDashboardSubStatus] = data.pageData;
        this.coveringApprovalsPage[coveringApprovalDashboardSubStatus] = data;
      });
    } else {

      this.coveringApprovals[coveringApprovalDashboardSubStatus] = [];
      this.coveringApprovalsPage[coveringApprovalDashboardSubStatus] = [];
    }

  }

  loadPageData(coveringApprovalDashboardStatus) {
    this.coveringApprovals = [];
    this.coveringApprovalsPage = [];

  
    switch (coveringApprovalDashboardStatus) {
      case this.coveringApprovalDashboardStatusConst.INBOX: {
        this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.INBOX, this.coveringApprovalInboxSubStatusConst.DRAFT, this.coveringApprovalCounts.inboxCovApp);
        this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.INBOX, this.coveringApprovalInboxSubStatusConst.RETURNED_TO_ME, this.coveringApprovalCounts.inboxCovApp);
        this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.INBOX, this.coveringApprovalInboxSubStatusConst.RECEIVED_TO_ME, this.coveringApprovalCounts.inboxCovApp);

        break;
      }
      case this.coveringApprovalDashboardStatusConst.IN_PROGRESS: {
        this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.IN_PROGRESS, this.coveringApprovalForwardedStatusConst.FORWARDED, this.coveringApprovalCounts.inProgressCoveringApproval);
        //this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.IN_PROGRESS,this.coveringApprovalDeclinedStatusConst.DECLINED,this.coveringApprovalCounts.inProgressCoveringApproval);
        // this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.IN_PROGRESS,this.coveringApprovalReturnedStatusConst.RETURNED,this.coveringApprovalCounts.inProgressCoveringApproval);
        break;
      }
      case this.coveringApprovalDashboardStatusConst.APPROVED: {
        this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.APPROVED, this.coveringApprovalForwardedStatusConst.PAPER_APPROVED, this.coveringApprovalCounts.approvedCovApp);
        //this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.APPROVED ,this.coveringApprovalDeclinedStatusConst.DECLINED,this.coveringApprovalCounts.approvedCoveringApproval);


        break;
      }
      case this.coveringApprovalDashboardStatusConst.REJECTED: {
        //this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.REJECTED, this.coveringApprovalForwardedStatusConst.FORWARDED,this.coveringApprovalCounts.rejectedCoveringApproval);
        //this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.REJECTED, this.coveringApprovalDeclinedStatusConst.DECLINED,this.coveringApprovalCounts.rejectedCoveringApproval);
        this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.REJECTED, this.coveringApprovalReturnedStatusConst.NOT_APPROVED, this.coveringApprovalCounts.rejectedCovApp);
        break;
      }
      case this.coveringApprovalDashboardStatusConst.RETURN: {
        //this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.CANCEL ,this.coveringApprovalForwardedStatusConst.FORWARDED, this.coveringApprovalCounts.canceledCoveringApproval);
        this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.RETURN, this.coveringApprovalCancelStatusConst.RETURNED_BY_ME, this.coveringApprovalCounts.returnedCovApp);
        this.loadSubStatusData(this.coveringApprovalDashboardStatusConst.RETURN, this.coveringApprovalCancelStatusConst.RETURNED_BY_OTHERS, this.coveringApprovalCounts.returnedCovApp);
        break;
      }

    }
    this.status = coveringApprovalDashboardStatus;
  }
  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      const branchName = branch.branchName + ' - ' + branch.branchCode;
      localStorage.setItem(branchCode, branchName); // Store branch name in local storage
      return branchName;
    }

    return localStorage.getItem(branchCode) || branchCode;
  }

  openDialog(comment) {
    this.modalRef = this.mdbModalService.show(CreateCoveringApprovalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-45-p modal-margin-center ',
      containerClass: '',
      animated: true,
      data: {
        heading: "Create Covering Approval",
        message: "",
        data: this.coveringApprovals,
        comment: comment,
      }
    });
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let saveData = {
          commentID: comment ? comment.commentID : null,
          comment: data.comment,
          actionMessage: 'Action Message',
          createdUserDisplayname: this.applicationService.getLoggedInUserDisplayName(),
          createdUserID: this.applicationService.getLoggedInUserUserID(),
          createdUser: this.applicationService.getLoggedInUserUserName(),
          createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          isUsersOnly: data.isUsersOnly ? 'Y' : ' N',
          isDivisionOnly: data.isDivisionOnly ? 'Y' : ' N',
          isPublic: data.isPublic ? 'Y' : ' N',
          status: Constants.statusConst.ACT,
          currentCoveringStatus: 'INBOX'
        };
        this.coveringApprovalService.draftApplicationForm(AppUtils.trim(saveData));
      }
    })
  }

  getFormattedValue(amount: any): string | null {
    if (amount != null) {
      // If the amount is a string and contains commas, remove the commas and parse it to a float
      const numericAmount = typeof amount === 'string' ? parseFloat(amount.replace(/,/g, '')) : amount;
  
      // Ensure that the resulting value is a valid number
      if (!isNaN(numericAmount)) {
        // Use the CurrencyPipe to format the number
        return this.currencyPipe.transform(numericAmount, '', ''); // Customize as needed for currency type
      }
    }
    return null; // Return null if the amount is not valid or empty
  }
  

}
