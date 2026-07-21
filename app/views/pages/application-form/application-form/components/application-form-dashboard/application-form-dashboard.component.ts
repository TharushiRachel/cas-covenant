import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs/Rx";
import { PageSize } from "../../../../../../core/dto/page.size";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Constants } from "../../../../../../core/setting/constants";
import { ApplicationFormService } from "../../services/application-form.service";
import {
  CacheSearchData,
  SearchDataCacheService,
} from "../../../../../../core/service/common/search-data-cache.service";
import { UrlEncodeService } from "../../../../../../core/service/application/url-encode.service";
import { Router } from "@angular/router";
import { Pagination } from "../../../../../../core/dto/pagination";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "../../../../../../core/setting/commons.settings";
//import {MyLeadCountService} from "../../../../../core/service/leed/my-lead-count.service";
import { IMyOptions } from "ng-uikit-pro-standard";
import { ApplicationService } from "../../../../../../core/service/application/application.service";
import { CacheService } from "../../../../../../core/service/data/cache.service";
import { AppUtils } from "../../../../../../shared/app.utils";
import { isEmpty } from "lodash";

@Component({
  selector: "app-application-form-dashboard",
  templateUrl: "./application-form-dashboard.component.html",
  styleUrls: ["./application-form-dashboard.component.scss"],
})
export class ApplicationFormDashboardComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;
  uniquePageName = "ApplicationFormDashboardComponent-#343rta";
  pageSize = new PageSize();
  allBankOptions: any = {};
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  onApplicationFormChangeSub: Subscription = new Subscription();
  onApplicationFormCountsChangeSub: Subscription = new Subscription();

  applicationFormStatusConst = Constants.applicationFormStatusConst;
  applicationFormDashboardStatusConst =
    Constants.applicationFormDashboardStatusConst;
  applicationFormStatus = Constants.applicationFormStatus;
  applicationFormDashboardStatus = Constants.applicationFormDashboardStatus;

  applicationFormInboxStatusConst = Constants.applicationFormInboxStatusConst;
  applicationFormInProgressStatusConst =
    Constants.applicationFormInProgressStatusConst;
  applicationFormDeclinedStatusConst =
    Constants.applicationFormDeclinedStatusConst;
  applicationFormReturnedStatusConst =
    Constants.applicationFormReturnedStatusConst;
  applicationFormAcceptedStatusConst =
    Constants.applicationFormAcceptedStatusConst;

  status: String = this.applicationFormDashboardStatusConst.INBOX;
  applicationForms: any = [];
  applicationFormPage: any = [];
  hasInboxLoaded = false;

  applicationFormCounts: any = {
    inboxApplicationForm: 0,
    inProgressApplicationForm: 0,
    acceptedApplicationForm: 0,
    declinedApplicationForm: 0,
    returnedApplicationForm: 0,
    paperApprovedApplicationForm: 0,
    dashboardTimePeriodDays: 0,
  };

  tableColumns: any = [
    "Application Form Reference",
    "Facility Paper Reference",
    "Lead Reference",
    "Customer Name",
    "Branch Code",
    "Created By",
    "Created Date",
    "Assigned User",
    "Status",
  ];

  fsTypes: any[] = [
    { id: 1, type: "Lease", code: "L" },
    { id: 2, type: "Samachara Loan", code: "SL" },
    { id: 3, type: "Other", code: "OT" },
    { id: 4, type: "Sevana Housing Loan", code: "SHL" },
    { id: 5, type: "Professional Loan", code: "PL" },
    { id: 6, type: "Medical Officers Loan", code: "MOL" },
    {
      id: 7,
      type: "Green Energy & SME Development Fund",
      code: "G&S",
    },
    { id: 8, type: "Insurance Loan", code: "IL" },
    {
      id: 9,
      type: "Restructure / Reschedule of schematized facilities",
      code: "RSS",
    },
    { id: 10, type: "TOD", code: "TOD" },
    { id: 11, type: "OneOff", code: "O" },
  ];

  constructor(
    private applicationFormService: ApplicationFormService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router,
    private cacheService: CacheService,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.applicationFormService
      .getApplicationFormCounts({
        loggedInUserId: this.applicationService.getLoggedInUserUserName(),
        loggedInUserBranchCode:
          this.applicationService.getLoggedInUserDivCode(),
        loginUpmAccessLevel:
          this.applicationService.getLoggedInUserUPMGroupCode(),
      })
      .then((data: any) => {
         if (!this.hasInboxLoaded) {
            this.applicationFormCounts = data;
            this.loadInitiateDashboardPage();
            this.hasInboxLoaded = true;
         }

      });
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
    this.onApplicationFormCountsChangeSub.unsubscribe();
  }

  getBranchName(branchCode) {
    if (branchCode) {
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
    } else {
      return "-";
    }
  }

  onPageEvent(event, applicationFormDashboardSubStatus) {
    this.pageSize.pageSize = event.pageSize;
    this.applicationFormService
      .getApplicationFormByStatus(
        {
          loggedInUserId: this.applicationService.getLoggedInUserUserName(),
          loggedInUserBranchCode:
            this.applicationService.getLoggedInUserDivCode(),
          loginUpmAccessLevel:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          applicationFormDashboardStatus: this.status,
          applicationFormDashboardSubStatus: applicationFormDashboardSubStatus,
        },
        new Pagination(event)
      )
      .then((data: any) => {
        this.applicationForms[applicationFormDashboardSubStatus] =
          data.pageData;
        this.applicationFormPage[applicationFormDashboardSubStatus] = data;
      });
  }

  loadApplicationForm(applicationFormID) {
    if (applicationFormID == null) {
      this.selectedApplicationFormID = null;
    } else {
      this.selectedApplicationFormID =
        this.urlEncodeService.encode(applicationFormID);
    }
    this.router.navigate(["/application-form/add-edit"]);
  }

  loadInitiateDashboardPage() {
    this.loadSubStatusData(
      this.applicationFormDashboardStatusConst.INBOX,
      this.applicationFormInboxStatusConst.DRAFT,
      this.applicationFormCounts.inboxApplicationForm
    );
    this.loadSubStatusData(
      this.applicationFormDashboardStatusConst.INBOX,
      this.applicationFormInboxStatusConst.RETURNED_TO_ME,
      this.applicationFormCounts.inboxApplicationForm
    );
    this.loadSubStatusData(
      this.applicationFormDashboardStatusConst.INBOX,
      this.applicationFormInboxStatusConst.RECEIVED_TO_ME,
      this.applicationFormCounts.inboxApplicationForm
    );
  }

  loadSubStatusData(
    applicationFormDashboardStatus,
    applicationFormDashboardSubStatus,
    subStatusCount
  ) {
    if (subStatusCount != 0) {
      this.applicationFormService
        .getApplicationFormByStatus(
          {
            loggedInUserId: this.applicationService.getLoggedInUserUserName(),
            loggedInUserBranchCode:
              this.applicationService.getLoggedInUserDivCode(),
            loginUpmAccessLevel:
              this.applicationService.getLoggedInUserUPMGroupCode(),
            applicationFormDashboardStatus: applicationFormDashboardStatus,
            applicationFormDashboardSubStatus:
              applicationFormDashboardSubStatus,
          },
          new Pagination({
            pageSize: this.pageSize.pageSize,
            pageIndex: 0,
          })
        )
        .then((data: any) => {
          this.applicationForms[applicationFormDashboardSubStatus] =
            data.pageData;
          this.applicationFormPage[applicationFormDashboardSubStatus] = data;
        });
    } else {
      this.applicationForms[applicationFormDashboardSubStatus] = [];
      this.applicationFormPage[applicationFormDashboardSubStatus] = [];
    }
  }

  loadPageData(applicationFormDashboardStatus) {
    this.applicationForms = [];
    this.applicationFormPage = [];

    switch (applicationFormDashboardStatus) {
      case this.applicationFormDashboardStatusConst.INBOX: {
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.INBOX,
          this.applicationFormInboxStatusConst.DRAFT,
          this.applicationFormCounts.inboxApplicationForm
        );
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.INBOX,
          this.applicationFormInboxStatusConst.RETURNED_TO_ME,
          this.applicationFormCounts.inboxApplicationForm
        );
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.INBOX,
          this.applicationFormInboxStatusConst.RECEIVED_TO_ME,
          this.applicationFormCounts.inboxApplicationForm
        );
        break;
      }
      case this.applicationFormDashboardStatusConst.IN_PROGRESS: {
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.IN_PROGRESS,
          this.applicationFormInProgressStatusConst.IN_PROGRESS,
          this.applicationFormCounts.inProgressApplicationForm
        );
        break;
      }
      case this.applicationFormDashboardStatusConst.ACCEPTED: {
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.ACCEPTED,
          this.applicationFormAcceptedStatusConst.PAPER_CREATED,
          this.applicationFormCounts.acceptedApplicationForm
        );
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.ACCEPTED,
          this.applicationFormAcceptedStatusConst.PAPER_RETURNED,
          this.applicationFormCounts.acceptedApplicationForm
        );
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.ACCEPTED,
          this.applicationFormAcceptedStatusConst.PAPER_DECLINED,
          this.applicationFormCounts.acceptedApplicationForm
        );
        break;
      }
      case this.applicationFormDashboardStatusConst.DECLINED: {
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.DECLINED,
          this.applicationFormDeclinedStatusConst.DECLINED_BY_ME,
          this.applicationFormCounts.declinedApplicationForm
        );
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.DECLINED,
          this.applicationFormDeclinedStatusConst.DECLINED_BY_OTHERS,
          this.applicationFormCounts.declinedApplicationForm
        );
        break;
      }
      case this.applicationFormDashboardStatusConst.RETURNED: {
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.RETURNED,
          this.applicationFormReturnedStatusConst.RETURNED_BY_ME,
          this.applicationFormCounts.returnedApplicationForm
        );
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.RETURNED,
          this.applicationFormReturnedStatusConst.RETURNED_BY_OTHERS,
          this.applicationFormCounts.returnedApplicationForm
        );
        break;
      }
      case this.applicationFormDashboardStatusConst.PAPER_APPROVED: {
        this.loadSubStatusData(
          this.applicationFormDashboardStatusConst.PAPER_APPROVED,
          this.applicationFormDashboardStatusConst.PAPER_APPROVED,
          this.applicationFormCounts.paperApprovedApplicationForm
        );
        break;
      }
    }
    this.status = applicationFormDashboardStatus;
  }

  getFsTypeCode(fsType: any) {
    var code: string = "";

    if (fsType) {
      var typeRow: any = this.fsTypes.find(
        (pt: any) => pt.type.toLowerCase() == fsType.toLowerCase()
      );
      if (typeRow) {
        code = typeRow.code;
      }
    }

    return code;
  }

  isESGPaper(item: any) {
    return item && item.isESGPaper === Constants.yesNoConst.Y;
  }
}
