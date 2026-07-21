import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs/Rx";
import { PageSize } from "../../../../../core/dto/page.size";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Constants } from "../../../../../core/setting/constants";
import { LeadsService } from "../../services/leads.service";
import {
  CacheSearchData,
  SearchDataCacheService,
} from "../../../../../core/service/common/search-data-cache.service";
import { UrlEncodeService } from "../../../../../core/service/application/url-encode.service";
import { Router } from "@angular/router";
import { Pagination } from "../../../../../core/dto/pagination";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "../../../../../core/setting/commons.settings";
import { MyLeadCountService } from "../../../../../core/service/leed/my-lead-count.service";
import { IMyOptions } from "ng-uikit-pro-standard";
import { ApplicationService } from "../../../../../core/service/application/application.service";
import { isEmpty } from "lodash";
import { CacheService } from "src/app/core/service/data/cache.service";
import { AppUtils } from "src/app/shared/app.utils";

@Component({
  selector: "app-lead-dashboard",
  templateUrl: "./lead-dashboard.component.html",
  styleUrls: ["./lead-dashboard.component.scss"],
})
export class LeadDashboardComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_LEAD_ID)
  selectedLeadID;

  uniquePageName = "LeadDashboardComponent-#343rta";
  pageSize = new PageSize();
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  onLeadChangeSub: Subscription = new Subscription();
  onLeadCountsChangeSub: Subscription = new Subscription();
  leadStatusConst = Constants.leadStatusConst;
  leadStatus = Constants.leadStatus;

  leadCreationType = Constants.leadCreationTypeConst;

  leadDashboardStatus = Constants.leadDashboardStatus;
  leadDashboardStatusConst = Constants.leadDashboardStatusConst;
  leadInboxStatusConst = Constants.leadInboxStatusConst;
  leadInProgressStatusConst = Constants.leadInProgressStatusConst;
  leadDeclinedStatusConst = Constants.leadDeclinedStatusConst;
  leadReturnedStatusConst = Constants.leadReturnedStatusConst;
  leadAcceptedStatusConst = Constants.leadAcceptedStatusConst;

  status: String = this.leadDashboardStatusConst.INBOX;

  leads: any = [];
  leadsPage: any = [];
  allBankOptions: any = {};
  hasInboxLoaded = false;

  leadCounts: any = {
    inboxLead: 0,
    inProgressLead: 0,
    acceptedLead: 0,
    declinedLead: 0,
    returnedLead: 0,
    paperApprovedLead: 0,
    dashboardTimePeriodDays: 0,
  };

  tableColumns: any = [
    "Lead Name",
    "Customer Name",
    "Lead Reference",
    "Application Form Reference",
    "Facility Paper Reference",
    "NIC Number",
    "Branch Code",
    "Created By",
    "Created Date",
    "Assigned User",
    "Status",
  ];

  constructor(
    private leadsService: LeadsService,
    private searchDataCacheService: SearchDataCacheService,
    private urlEncodeService: UrlEncodeService,
    private formBuilder: FormBuilder,
    private router: Router,
    private cacheService: CacheService,
    private myLeadCountService: MyLeadCountService,
    private applicationService: ApplicationService,
  ) { }

  ngOnInit() {
    this.onLeadCountsChangeSub = this.leadsService.onLeadCountsChange.subscribe(
      (data) => {
        if (!this.hasInboxLoaded) {
          this.leadCounts = data;
          this.loadInitiateDashboardPage();
          this.hasInboxLoaded = true;
        }
      },
    );
  }

  ngOnDestroy(): void {
    this.onLeadChangeSub.unsubscribe();
    this.onLeadCountsChangeSub.unsubscribe();
  }

  onPageEvent(event, leadDashboardSubStatus) {
    this.pageSize.pageSize = event.pageSize;
    this.leadsService
      .getLeadByStatus(
        {
          loggedInUserId: this.applicationService.getLoggedInUserUserName(),
          loggedInUserBranchCode:
            this.applicationService.getLoggedInUserDivCode(),
          loginUpmAccessLevel:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          leadDashboardStatus: this.status,
          leadDashboardSubStatus: leadDashboardSubStatus,
        },
        new Pagination(event),
      )
      .then((data: any) => {
        this.leads[leadDashboardSubStatus] = data.pageData;
        this.leadsPage[leadDashboardSubStatus] = data;
      });
  }

  loadLead(lead: any) {
    if (lead.id == null) {
      this.selectedLeadID = null;
    } else {
      this.selectedLeadID = this.urlEncodeService.encode(lead.id);
    }
    if (this.isComprehensive(lead)) {
      this.router.navigate(["/leads/comprehensive-create"], {
        queryParams: {
          selectedLeadID: this.urlEncodeService.encode(lead.id),
        }, replaceUrl: true
      });
    } else {
      this.router.navigate(["/leads/add-edit"]);
    }
  }

  loadInitiateDashboardPage() {
    this.loadSubStatusData(
      this.leadDashboardStatusConst.INBOX,
      this.leadInboxStatusConst.DRAFT,
      this.leadCounts.inboxLead,
    );
    this.loadSubStatusData(
      this.leadDashboardStatusConst.INBOX,
      this.leadInboxStatusConst.RETURNED_TO_ME,
      this.leadCounts.inboxLead,
    );
    this.loadSubStatusData(
      this.leadDashboardStatusConst.INBOX,
      this.leadInboxStatusConst.RECEIVED_TO_ME,
      this.leadCounts.inboxLead,
    );
  }

  loadSubStatusData(
    leadDashboardStatus,
    leadDashboardSubStatus,
    subStatusCount,
  ) {
    if (subStatusCount != 0) {
      this.leadsService
        .getLeadByStatus(
          {
            loggedInUserId: this.applicationService.getLoggedInUserUserName(),
            loggedInUserBranchCode:
              this.applicationService.getLoggedInUserDivCode(),
            loginUpmAccessLevel:
              this.applicationService.getLoggedInUserUPMGroupCode(),
            leadDashboardStatus: leadDashboardStatus,
            leadDashboardSubStatus: leadDashboardSubStatus,
          },
          new Pagination({
            pageSize: this.pageSize.pageSize,
            pageIndex: 0,
          }),
        )
        .then((data: any) => {
          this.leads[leadDashboardSubStatus] = data.pageData;
          this.leadsPage[leadDashboardSubStatus] = data;
        });
    } else {
      this.leads[leadDashboardSubStatus] = [];
      this.leadsPage[leadDashboardSubStatus] = [];
    }
  }

  loadPageData(leadDashboardStatus) {
    this.leads = [];
    this.leadsPage = [];

    switch (leadDashboardStatus) {
      case this.leadDashboardStatusConst.INBOX: {
        this.loadSubStatusData(
          this.leadDashboardStatusConst.INBOX,
          this.leadInboxStatusConst.DRAFT,
          this.leadCounts.inboxLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.INBOX,
          this.leadInboxStatusConst.RETURNED_TO_ME,
          this.leadCounts.inboxLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.INBOX,
          this.leadInboxStatusConst.RECEIVED_TO_ME,
          this.leadCounts.inboxLead,
        );
        break;
      }
      case this.leadDashboardStatusConst.IN_PROGRESS: {
        this.loadSubStatusData(
          this.leadDashboardStatusConst.IN_PROGRESS,
          this.leadInProgressStatusConst.IN_PROGRESS,
          this.leadCounts.inProgressLead,
        );
        break;
      }
      case this.leadDashboardStatusConst.ACCEPTED: {
        this.loadSubStatusData(
          this.leadDashboardStatusConst.ACCEPTED,
          this.leadAcceptedStatusConst.APPLICATION_CREATED,
          this.leadCounts.acceptedLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.ACCEPTED,
          this.leadAcceptedStatusConst.APPLICATION_RETURNED,
          this.leadCounts.acceptedLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.ACCEPTED,
          this.leadAcceptedStatusConst.APPLICATION_DECLINED,
          this.leadCounts.acceptedLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.ACCEPTED,
          this.leadAcceptedStatusConst.PAPER_CREATED,
          this.leadCounts.acceptedLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.ACCEPTED,
          this.leadAcceptedStatusConst.PAPER_RETURNED,
          this.leadCounts.acceptedLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.ACCEPTED,
          this.leadAcceptedStatusConst.PAPER_DECLINED,
          this.leadCounts.acceptedLead,
        );
        break;
      }
      case this.leadDashboardStatusConst.DECLINED: {
        this.loadSubStatusData(
          this.leadDashboardStatusConst.DECLINED,
          this.leadDeclinedStatusConst.DECLINED_BY_ME,
          this.leadCounts.declinedLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.DECLINED,
          this.leadDeclinedStatusConst.DECLINED_BY_OTHERS,
          this.leadCounts.declinedLead,
        );
        break;
      }
      case this.leadDashboardStatusConst.RETURNED: {
        this.loadSubStatusData(
          this.leadDashboardStatusConst.RETURNED,
          this.leadReturnedStatusConst.RETURNED_BY_ME,
          this.leadCounts.returnedLead,
        );
        this.loadSubStatusData(
          this.leadDashboardStatusConst.RETURNED,
          this.leadReturnedStatusConst.RETURNED_BY_OTHERS,
          this.leadCounts.returnedLead,
        );
        break;
      }
      case this.leadDashboardStatusConst.PAPER_APPROVED: {
        this.loadSubStatusData(
          this.leadDashboardStatusConst.PAPER_APPROVED,
          this.leadDashboardStatusConst.PAPER_APPROVED,
          this.leadCounts.paperApprovedLead,
        );
        break;
      }
    }
    this.status = leadDashboardStatus;
  }

  getBranchName(branchCode) {
    if (branchCode) {
      this.allBankOptions = this.cacheService.getData(
        Constants.masterDataKey.CAS_BRANCHES,
      );
      let branch = AppUtils.getBranchFromBranchCode(
        this.allBankOptions,
        branchCode,
      );
      if (!isEmpty(branch)) {
        return branch.branchName + " - " + branch.branchCode;
      }
      return branchCode;
    } else {
      return "-";
    }
  }

  isComprehensive(lead: any) {
    return (
      lead !== null &&
      lead.isCompLead !== null &&
      lead.isCompLead === Constants.yesNoConst.Y
    );
  }
}
