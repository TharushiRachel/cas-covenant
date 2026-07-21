import {
  Component,
  EventEmitter,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
} from "@angular/core";
import { Subscription } from "rxjs";
import { FacilityPaperAddEditService } from "../../../../../services/facility-paper-add-edit.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { Constants } from "../../../../../../../../core/setting/constants";
import { CacheService } from "../../../../../../../../core/service/data/cache.service";
import { AppUtils } from "../../../../../../../../shared/app.utils";
import * as _ from "lodash";
import { LocalStorage } from "ngx-webstorage";
import { UrlEncodeService } from "../../../../../../../../core/service/application/url-encode.service";
import { Router } from "@angular/router";
import { ApplicationService } from "../../../../../../../../core/service/application/application.service";
import { CurrencyService } from "../../../../../../../../core/service/common/currency.service";
import { AlertService } from "../../../../../../../../core/service/common/alert.service";
import { NumberValidator } from "../../../../../../../../shared/validators/number.validator";
import { PrivilegeService } from "../../../../../../../../core/service/authentication/privilege.service";
import * as moment from "moment";

@Component({
  selector: "app-basic-fp-info",
  templateUrl: "./basic-fp-info.component.html",
  styleUrls: ["./basic-fp-info.component.scss"],
})
export class BasicFpInfoComponent implements OnInit, OnChanges, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_CUSTOMER_ID)
  selectedFacilityPaperID;

  @Output("onKaliptoDataLoad") onKaliptoDataLoad = new EventEmitter<any>();

  onPrimaryCustomerChangeSub = new Subscription();
  onFacilityPaperBaseDataChangeSub = new Subscription();
  onFacilityPaperHistoryChange = new Subscription();

  componentForm: FormGroup;
  formErrors: any;
  isDisableEdit: boolean = true;
  isDraft: boolean = false;

  allBankOptions = [];
  branch: any = {};
  allWorkFlowTemplates = [];
  allWorkFlowTemplateMap = {};
  primaryCustomer: any;
  facilityPaper: any;
  hasPrivilegeToViewCommitteeLog = false;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;
  yesNoConst = Constants.yesNoConst;
  bccInquirerWorkClass: any;
  equalLoginUserAndAssignUser = false;
  facilityPaperHistoryList: any = [];
  hasPrivilegeToViewBCCLog: boolean = false;
  loggedInUserWorkClass = "";
  bccAuthorizerWorkClass = "";
  meetingData: any = {
    committeeName: "",
    date: "",
    status: "",
  };

  analyticsDecision: any = null;
  accountNo: any[];
  selectedBankAccountID: string = "";
  showBankAccountDropdown = false;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private cacheService: CacheService,
    private formBuilder: FormBuilder,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private privilegeService: PrivilegeService,
    private applicationService: ApplicationService,
    public currencyService: CurrencyService,
    private alertService: AlertService,
  ) {}

  ngOnInit() {
    this.allBankOptions = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES,
    );
    this.hasPrivilegeToViewCommitteeLog = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_VIEW_COMMITTEE_PAPER,
    );
    this.hasPrivilegeToViewBCCLog = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_VIEW_BCC_PAPER,
    );
    this.bccInquirerWorkClass = this.cacheService.getData(
      Constants.masterDataKey.CAS_BCC_INQUIRER_WORK_CLASS,
    );
    this.allWorkFlowTemplates = this.cacheService.getData(
      Constants.masterDataKey.CAS_WORKFLOW_TEMPLATES,
    );

    this.loggedInUserWorkClass =
      this.applicationService.getLoggedInUserUPMGroupCode();

    this.bccAuthorizerWorkClass = this.cacheService.getData(
      Constants.masterDataKey.CAS_BCC_AUTHORIZER_WORK_CLASS,
    );

    _.forEach(this.allWorkFlowTemplates, (template) => {
      this.allWorkFlowTemplateMap[template.workFlowTemplateID] =
        template.code + " - " + template.workFlowTemplateName;
    });

    this.onFacilityPaperBaseDataChangeSub =
      this.facilityPaperAddEditService.onFacilityPaperBaseDataChange.subscribe(
        (data: any) => {
          if (data) {
            this.facilityPaper = data;

            if (
              this.facilityPaper !== null &&
              this.facilityPaper.analyticsDecision !== null &&
              this.analyticsDecision === null
            ) {
              this.analyticsDecision = this.facilityPaper.analyticsDecision;
            }

            this.primaryCustomer = _.find(
              this.facilityPaper.casCustomerDTOList || [],
              (customer) => {
                return (
                  customer.isPrimary &&
                  customer.status === Constants.statusConst.ACT
                );
              },
            );
            this.isEqualLoginAndAssignUser();

            this.branch = AppUtils.getBranchFromBranchCode(
              this.allBankOptions,
              this.facilityPaper.branchCode,
            );

            console.log(
              "this.facilityPaper.facilityPaperID",
              this.facilityPaper.facilityPaperID,
            );
            this.getCustomerBankAccountDetails(
              this.facilityPaper.facilityPaperID,
            );
          }
          if (this.componentForm) {
            this.componentForm.disable();
            this.onClickCancelEdit();
          }
          this.isDisableEdit = true;
          this.isDraft =
            this.facilityPaperStatusConst.DRAFT ==
            this.facilityPaper.currentFacilityPaperStatus;
        },
      );
    this.componentForm = this.createDirectorDetailForm();

    this.isEqualLoginAndAssignUser();

    this.onFacilityPaperHistoryChange =
      this.facilityPaperAddEditService.onFacilityPaperHistoryChange.subscribe(
        (response: any) => {
          this.facilityPaperHistoryList = response;
        },
      );

    this.setMeetingData();
  }

  createDirectorDetailForm() {
    let leadNumber = this.facilityPaper.leadRefNumber || "";
    let applicationFormRefNumber =
      this.facilityPaper.applicationFormRefNumber || "";
    let proposedFacilitiesROA = this.facilityPaper.proposedFacilitiesROA || "";
    let existingFacilitiesROA = this.facilityPaper.existingFacilitiesROA || "";
    let isCooperate = this.facilityPaper.isCooperate == "Y";

    return this.formBuilder.group({
      leadRefNumber: [{ value: leadNumber, disabled: true }, []],
      applicationFormRefNumber: [
        { value: applicationFormRefNumber, disabled: true },
        [],
      ],
      proposedFacilitiesROA: [
        {
          value: proposedFacilitiesROA
            ? this.currencyService.getFormattedAmount(proposedFacilitiesROA)
            : proposedFacilitiesROA,
          disabled: this.isDisableEdit,
        },
        [Validators.max(100), NumberValidator.isPercentageValueWithMinus],
      ],
      existingFacilitiesROA: [
        {
          value: existingFacilitiesROA
            ? this.currencyService.getFormattedAmount(existingFacilitiesROA)
            : existingFacilitiesROA,
          disabled: this.isDisableEdit,
        },
        [Validators.max(100), NumberValidator.isPercentageValueWithMinus],
      ],
      cooperate: [{ value: isCooperate, disabled: this.isDisableEdit }, []],
    });
  }

  onClickEdit() {
    this.componentForm.enable();
    this.componentForm.controls.leadRefNumber.disable({
      onlySelf: false,
      emitEvent: true,
    });
    this.componentForm.controls.applicationFormRefNumber.disable({
      onlySelf: false,
      emitEvent: true,
    });

    let cooperate = this.componentForm.get("cooperate");
    this.isDraft ? cooperate.enable() : cooperate.disable();
    this.isDisableEdit = false;
  }

  onClickCancelEdit() {
    this.componentForm.reset(
      {
        leadRefNumber: this.facilityPaper.leadRefNumber,
        applicationFormRefNumber: this.facilityPaper.applicationFormRefNumber,
        proposedFacilitiesROA: this.facilityPaper.proposedFacilitiesROA,
        existingFacilitiesROA: this.facilityPaper.existingFacilitiesROA,
        cooperate: this.facilityPaper.isCooperate == "Y",
      },
      { onlySelf: false, emitEvent: true },
    );
    this.componentForm.disable();
    this.isDisableEdit = true;
  }

  onClickUpdateBaseInfo() {
    const {
      leadRefNumber,
      applicationFormRefNumber,
      proposedFacilitiesROA,
      existingFacilitiesROA,
      cooperate,
    } = this.componentForm.getRawValue();
    let updateData = Object.assign({}, this.facilityPaper);
    updateData.leadRefNumber = leadRefNumber;
    updateData.applicationFormRefNumber = applicationFormRefNumber;
    updateData.proposedFacilitiesROA =
      this.currencyService.getAmountFromFormattedString(proposedFacilitiesROA);
    updateData.existingFacilitiesROA =
      this.currencyService.getAmountFromFormattedString(existingFacilitiesROA);
    updateData.isCooperate = cooperate ? "Y" : "N";

    if (updateData.proposedFacilitiesROA > 100) {
      this.alertService.showToaster(
        "Proposed ROA (%) should be less than 100",
        SETTINGS.TOASTER_MESSAGES.error,
      );
      return;
    }

    if (updateData.existingFacilitiesROA > 100) {
      this.alertService.showToaster(
        "Existing ROA (%) should be less than 100",
        SETTINGS.TOASTER_MESSAGES.error,
      );
      return;
    }

    this.facilityPaperAddEditService.saveOrUpdateFacilityPaper(updateData);
  }

  ngOnDestroy(): void {
    this.onPrimaryCustomerChangeSub.unsubscribe();
    this.onFacilityPaperHistoryChange.unsubscribe();
    this.onFacilityPaperBaseDataChangeSub.unsubscribe();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["facilityPaper"]) {
      if (this.componentForm) {
        this.componentForm.disable();
        this.isDisableEdit = true;
      }
    }
  }

  onViewFpAuditClick(facilityPaperID) {
    if (facilityPaperID != null) {
      this.selectedFacilityPaperID =
        this.urlEncodeService.encode(facilityPaperID);
    } else {
      this.selectedFacilityPaperID = null;
    }
    this.router.navigate(["/facility-paper/fp-audit-detail"]);
  }

  isEqualLoginAndAssignUser() {
    if (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    ) {
      return true;
    } else {
      return false;
    }
  }

  isApproveStatus() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.APPROVED
    );
  }

  isRejected() {
    return (
      this.facilityPaper.currentFacilityPaperStatus ==
      this.facilityPaperStatusConst.REJECTED
    );
  }

  loadKalyptoData() {
    this.onKaliptoDataLoad.emit(this.primaryCustomer);
  }

  showFacilityPaperHistory() {
    return this.facilityPaperHistoryList.length > 0;
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES,
    );
    let branch = AppUtils.getBranchFromBranchCode(
      this.allBankOptions,
      branchCode,
    );

    if (!_.isEmpty(branch)) {
      return branch.branchName + " - " + branch.branchCode;
    }
    return branchCode;
  }

  viewLog(logDetail) {
    let showBCCActionLog = false;

    if (this.hasPrivilegeToViewBCCLog) {
      if (
        this.applicationService.getLoggedInUserUPMGroupCode() ==
        this.bccInquirerWorkClass
      ) {
        showBCCActionLog = false;
      } else {
        showBCCActionLog = true;
      }
    } else {
      showBCCActionLog = false;
    }

    if (showBCCActionLog) {
      return true;
    } else {
      if (
        logDetail.updatedUser ==
        this.applicationService.getLoggedInUserUserName()
      ) {
        return true;
      } else if (
        logDetail.isUsersOnly === this.yesNoConst.Y &&
        this.applicationService.getLoggedInUserUserID() ==
          logDetail.assignUserID
      ) {
        return true;
      } else if (
        logDetail.isDivisionOnly === this.yesNoConst.Y &&
        this.applicationService.getLoggedInUserDivCode() ==
          logDetail.assignDepartmentCode
      ) {
        return true;
      } else {
        return logDetail.isPublic === this.yesNoConst.Y;
      }
    }
  }

  alterBCCRejectStatus(log) {
    if (log.assignDepartmentCode == "CA") {
      if (log.facilityPaperStatus == "REJECTED") {
        if (log.updateBy == "BOARD CREDIT COMMITTEE") {
          return false;
        } else {
          return true;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  isBccDecisionShow() {
    return (
      (this.facilityPaper.currentFacilityPaperStatus ==
        Constants.approveStatusConst.APPROVED ||
        this.facilityPaper.currentFacilityPaperStatus ==
          Constants.approveStatusConst.REJECTED ||
        this.bccAuthorizerWorkClass == this.loggedInUserWorkClass) &&
      this.meetingData &&
      this.facilityPaper.isCommittee == Constants.yesNoConst.Y
    );
  }

  setMeetingData() {
    if (this.facilityPaper.approvedDate) {
      this.meetingData = {
        committeeName: this.facilityPaper.assignUserDisplayName,
        date: moment(this.facilityPaper.approvedDate).format("Do MMMM YYYY"),
        status: Constants.approveStatusConst.APPROVED,
      };
    }
    if (this.facilityPaper.rejectedDate) {
      this.meetingData = {
        committeeName: this.facilityPaper.assignUserDisplayName,
        date: moment(this.facilityPaper.rejectedDate).format("Do MMMM YYYY"),
        status: Constants.approveStatusConst.REJECTED,
      };
    }
  }

  getCustomerBankAccountDetails(facilityPaperID: any) {
    this.facilityPaperAddEditService
      .getCustomerBankAccountDetails(facilityPaperID)
      .then((res: any[]) => {
        if (res !== null && res.length > 0) {
          this.accountNo = res.map((d) => ({
            ...d,
          }));
        }
      });
  }

  saveBankAccountID() {
    if (!this.selectedBankAccountID) {
      return;
    }

    const payload = {
      facilityPaperID: this.facilityPaper.facilityPaperID,
      bankAccountID: this.selectedBankAccountID,
    };

    this.facilityPaperAddEditService.updateFacilityPaperType(payload);

    // update UI after save
    this.facilityPaper.bankAccountID = this.selectedBankAccountID;
    this.showBankAccountDropdown = false;
  }

  editBankAccount() {
    this.selectedBankAccountID = this.facilityPaper.bankAccountID;
    this.showBankAccountDropdown = true;
  }

  enableBankAccNoEdit() {
    let loggedInUserWorkClass: number = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode(),
    );
    return (
      (loggedInUserWorkClass === 10 || loggedInUserWorkClass === 50) &&
      this.isEqualLoginAndAssignUser() &&
      !this.isApproveStatus() &&
      !this.isRejected()
    );
  }
}
