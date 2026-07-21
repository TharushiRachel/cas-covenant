import {
  Component,
  HostListener,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import { FacilityPaperAddEditService } from "../../services/facility-paper-add-edit.service";
import { Subscription } from "rxjs";
import * as _ from "lodash";
import { LocalStorage, SessionStorage } from "ngx-webstorage";
import { SETTINGS } from "../../../../../core/setting/commons.settings";
import { UrlEncodeService } from "../../../../../core/service/application/url-encode.service";
import { Router } from "@angular/router";
import { ApplicationService } from "../../../../../core/service/application/application.service";
import { Constants } from "../../../../../core/setting/constants";
import { AgentFpForwardComponent } from "./components/agent-fp-forward/agent-fp-forward.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { InformationDialogComponent } from "../../../../../shared/components/information-dialog/information-dialog.component";
import { FacilityPaperCopyDialogComponent } from "../../../../../shared/components/facility-paper-copy-dialog/facility-paper-copy-dialog.component";
import { FpReturnToAgentComponent } from "./components/fp-return-to-agent/fp-return-to-agent.component";
import { PrivilegeService } from "../../../../../core/service/authentication/privilege.service";
import { CommonForwardComponent } from "../../../../../shared/components/common-forward/common-forward.component";
import { AppUtils } from "../../../../../shared/app.utils";
import { FpReturnComponent } from "./components/support-components/fp-return/fp-return.component";
import { CommentWithViewOptionsDialogComponent } from "../../../../../shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component";
import { CommonAttendComponent } from "../../../../../shared/components/common-attend/common-attend.component";
import { CommonReleaseComponent } from "../../../../../shared/components/common-release/common-release.component";
import { MasterDataService } from "../../../../../core/service/data/master-data.service";
import { CacheService } from "../../../../../core/service/data/cache.service";
import jsPDF from "jspdf";
import { AlertService } from "src/app/core/service/common/alert.service";
import { FpViewDasComponent } from "./components/fp-das/fp-view-das/fp-view-das.component";
import { ConfirmationDialogComponent } from "../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";
import * as moment from "moment";
import html2pdf from "html2pdf.js";
import { EsgService } from "src/app/core/service/common/esg.service";
import { SDConstants } from "./components/fp-documentation-new/utils";
import { SdConfirmationDialogComponent } from "src/app/shared/components/sd-confirmation-dialog/sd-confirmation-dialog.component";
import { AnalyticsDecision } from "../../../lead/interfaces/Lead-comp-borrower-dto";

@Component({
  selector: "app-facility-paper-add-edit",
  templateUrl: "./facility-paper-add-edit.component.html",
  styleUrls: ["./facility-paper-add-edit.component.scss"],
})
export class FacilityPaperAddEditComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_FP_CUSTOMER_ID)
  selectedFacilityPaperID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_COMMITTEE_INQUIRY_TYPE)
  selectedCommitteeInquiryType;

  @SessionStorage(SETTINGS.STORAGE.SELECTED_CUSTOMER_ID)
  selectedCIFID;

  modalRef: MDBModalRef;
  facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityStatusConst = Constants.facilityPaperStatusConst;
  committeePaperStatusConst = Constants.committeePaperStatusConst;
  facilityPaperStatusChangeHeading = Constants.facilityPaperStatusChangeHeading;
  paperReviewStatusConst = Constants.paperReviewStatusConst;
  facilityRoutigStatus = Constants.facilityRoutingStatus;
  facilityRoutingStatusConst = Constants.facilityRoutingStatusConst;
  leadTypeConst = Constants.leadTypeConst;
  yesNoConst = Constants.yesNoConst;
  onFacilityPaperBaseDataChangeSub = new Subscription();
  onFPaperSecSummeryChangeSub = new Subscription();
  onFacilityPaperUPCDataChangeSub = new Subscription();
  onFacilityPaperTotalExposureChangeSub = new Subscription();
  onFacilityPaperChangeSubs = new Subscription();
  onFacilityPaperChangeSub = new Subscription();
  onCustomerChangeSub = new Subscription();
  onCustomerListChangeSub = new Subscription();
  onFPFacilityChangeSub = new Subscription();
  onChangeTotalExposure = new Subscription();
  onCustomerRatingsChangeSub = new Subscription();
  onChangeAbleToReturnFacilityPaperToAgent = new Subscription();

  onAddEditCreditRiskReplyChange = new Subscription();
  onSaveOrUpdateFpCreditRiskCommentListChange = new Subscription();
  onCreditRiskCommentListChange = new Subscription();
  //  onCommitteePaperStatusChange = new Subscription();

  onDocumentUploadChangeSub: Subscription = new Subscription();
  onCalculateFacilityPaperExposureChangeSub = new Subscription();
  onDirectorDetailUpdateSub: Subscription = new Subscription();
  onShareHolderDetailUpdateSub: Subscription = new Subscription();

  totalExposureResponse: any = {};
  primaryCustomer: any = {};
  kalyptoCustomer: any = {};
  facilityPaper: any = {};
  committeePaper: any = {};
  bccPaper: any = {};
  fpCustomerList = [];
  customerRatingsDTOList = [];
  lis: any = [];
  primaryCustomerID: number = 0;
  userDa: any = {};
  isAbleToReturnFacilityPaperToAgent: boolean = false;
  isDivCodeIgnored: boolean = false;
  equalLoginUserAndAssignUser = false;
  hasPrivilegeToViewFullPaperAsDefault = false;
  hasPrivilegeToEditUPCAsDefault: boolean = false;
  masterDataPrivilege = SETTINGS.PRIVILEGES;
  selectedTabIndex: any = 0;
  // there are two scenarios for the tab view
  //   1: the user has privilege to view for the default view
  //   2: the approved facility paper rejected by the higher authorities
  aboutTabIndex = 0;
  borrowerTabIndex = 1;
  facilitiesTabIndex = 2;
  securityTabIndex = 3;
  walletShareTabIndex = 4;
  upcTabIndex = 5;
  otherDetailsTabIndex = 6;
  commentTabIndex = 7;
  // defaultFullViewTabIndex = 6;
  reviewerCommentTabIndex = 6;
  creditRiskCommentTabIndex = 7;
  esgTabIndex = 8;
  kalyptoDetailsTabIndex = 9;
  customerCovenantTabIndex = 10;
  defaultFullViewTabIndex = 11;
  committeFullViewTabIndex = 11;
  committeeInquiriesTabIndex = 12;
  attachmentsTabIndex: number;
  documentationTabIndex: number;
  deviationsTabIndex: number = 13;

  documentationTabEnableDivCode = "";
  loggedInUserDivCode = "";
  isDocumentationTabVisible = false;

  borrowerTabName = "Borrower(s)";
  scrWidth: any;
  hasPrivilegeToViewBCCPapers = false;
  bccEntererWorkClass = "";
  bccAuthorizerWorkClass = "";
  bccInquirerWorkClass = "";
  loggedInUserWorkClass = "";
  isLoggedInUserBCCEnterer = false;
  isLoggedInUserBCCAuthorizer = false;
  bccPaperStatusConst = Constants.bccPaperStatusConst;
  bccPaperStatus = Constants.bccPaperStatus;
  bccWorkFlowStatus = Constants.bccWorkFlowStatus;
  bccWorkFlowStatusConst = Constants.bccWorkFlowStatusConst;
  approveModalRef: MDBModalRef;

  bccFwdRecipients: any[] = [];
  bccAuthRecipients: any[] = [];

  isCommittee: boolean = false;
  watchlistStatus: boolean = false;
  fpDocumentElementDTOListAll = [];
  onDocumentationTabChange = new Subscription();
  isCommitteeButtonsEnable = false;

  covenantList = [];
  onCovenantTabChange: Subscription = new Subscription();
  facilityCovenantDTOList = [];
  onFacilityCovTabChange: Subscription = new Subscription();
  customerCovenantList = [];
  onCustomerCovenantAddTabChange: any;
  facilityCovenantList = [];
  onFacilityCovenantAddTabChange: Subscription;
  involeduserList;
  financialYearTypes: any[] = ["Jan - Dec", "Apr - Mar"];
  isFinancialYear: boolean = false;
  selectedYear: any;
  @Output("mainFacilityPaper")
  mainFacilityPaper: any = {};
  modalRefForwardConfirmation: MDBModalRef;
  insuranceMessage: string | null;

  securityDocumentVersion: number = 1;

  isEsgPaper: boolean = false;
  mandatoryAnnexureList: any[] = [];
  completedAnnexures: any[] = [];
  selectedRiskCategories: any[] = [];

  sdCount: any = {
    draftedCount: 0,
    submittedCount: 0,
    returnedCount: 0,
  };

  onESGRiskScoreChangeSub: Subscription = new Subscription();
  onESGChangeSub: Subscription = new Subscription();
  onAnnexuresChangeSub: Subscription = new Subscription();
  onFPDocumnetTypeChangeSub: Subscription = new Subscription();
  onFPDocumnetCountChangeSub: Subscription = new Subscription();
  isDeviationTableEditable: boolean = true;
  onDeviationCountChangeSub: Subscription = new Subscription();
  deviationCount:number = 0;

  constructor(
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    private urlEncodeService: UrlEncodeService,
    private router: Router,
    private applicationService: ApplicationService,
    private mdbModalService: MDBModalService,
    private privilegeService: PrivilegeService,
    private masterDataService: MasterDataService,
    private cacheService: CacheService,
    private alertService: AlertService,
    private esgService: EsgService,
  ) {
    this.getScreenSize();
  }

  @HostListener("window:resize", ["$event"])
  getScreenSize(event?) {
    this.scrWidth = window.innerWidth;
  }

  ngOnInit() {
    this.selectedYear = this.getSelectedYearType(this.isFinancialYear);

    this.isDivCodeIgnored = this.masterDataService
      .getSystemParameter(Constants.systemParamKey.DIV_CODE_IGNORED_UPM_GROUPS)
      .split(",", 5)
      .includes(this.applicationService.getLoggedInUserUPMGroupCode()); // If more UPM groups added to the system parameter increase the limit(5) of spitting
    this.facilityPaperAddEditService.getUserDaByUserName(
      this.applicationService.getLoggedInUserUserName(),
    );
    this.hasPrivilegeToViewFullPaperAsDefault =
      this.privilegeService.hasPrivilege(
        this.masterDataPrivilege.ICAS_SETTINGS_VIEW_FULL_PAPER_DEFAULT,
      );

    this.hasPrivilegeToEditUPCAsDefault = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_FACILITY_PAPER_UPC_EDIT,
    );

    this.hasPrivilegeToViewBCCPapers = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_VIEW_BCC_PAPER,
    );
    //console.log("hasPrivilegeToViewBCCPapers", this.hasPrivilegeToViewBCCPapers)
    this.loggedInUserWorkClass =
      this.applicationService.getLoggedInUserUPMGroupCode();

    if (this.hasPrivilegeToViewBCCPapers) {
      this.bccEntererWorkClass = this.cacheService.getData(
        Constants.masterDataKey.CAS_BCC_ENTERER_WORK_CLASS,
      );
      this.bccAuthorizerWorkClass = this.cacheService.getData(
        Constants.masterDataKey.CAS_BCC_AUTHORIZER_WORK_CLASS,
      );
      this.bccInquirerWorkClass = this.cacheService.getData(
        Constants.masterDataKey.CAS_BCC_INQUIRER_WORK_CLASS,
      );
      if (this.bccEntererWorkClass == this.loggedInUserWorkClass) {
        this.isLoggedInUserBCCEnterer = true;
      }
      if (this.bccAuthorizerWorkClass == this.loggedInUserWorkClass) {
        this.isLoggedInUserBCCAuthorizer = true;
      }
    }

    this.onFPFacilityChangeSub =
      this.facilityPaperAddEditService.onChangeLoggedUserName.subscribe(
        (data: any) => {
          if (data != null) {
            this.userDa = data;
          }
        },
      );

    this.onFPFacilityChangeSub =
      this.facilityPaperAddEditService.onFPFacilitiesChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data) && !_.isEmpty(this.facilityPaper)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              facilityDTOList: data.facilityDTOList,
              totalExposureNew: data.totalExposureNew,
              totalDirectExposurePrevious: data.totalDirectExposurePrevious,
              totalDirectExposureNew: data.totalDirectExposureNew,
              totalIndirectExposurePrevious: data.totalIndirectExposurePrevious,
              totalIndirectExposureNew: data.totalIndirectExposureNew,
              totalExposurePrevious: data.totalExposurePrevious,
              addTotalExposureToGroup: data.addTotalExposureToGroup,
              groupTotalDirectExposurePrevious:
                data.groupTotalDirectExposurePrevious,
              groupTotalDirectExposureNew: data.groupTotalDirectExposureNew,
              groupTotalIndirectExposurePrevious:
                data.groupTotalIndirectExposurePrevious,
              groupTotalIndirectExposureNew: data.groupTotalIndirectExposureNew,
              groupTotalExposurePrevious: data.groupTotalExposurePrevious,
              groupTotalExposureNew: data.groupTotalExposureNew,
            });
            this.isFinancialYear =
              this.facilityPaper.isFinancialYear == Constants.yesNoConst.Y;
            this.selectedYear = this.getSelectedYearType(this.isFinancialYear);
          }
        },
      );

    this.onFacilityPaperBaseDataChangeSub =
      this.facilityPaperAddEditService.onFacilityPaperBaseDataChange.subscribe(
        (fpBase: any) => {
          if (!_.isEmpty(fpBase)) {
            this.isCommittee = fpBase.isCommittee == Constants.yesNoConst.Y;
            this.isEsgPaper = fpBase.isESGPaper == Constants.yesNoConst.Y;
            this.isFinancialYear =
              fpBase.isFinancialYear == Constants.yesNoConst.Y;
            this.facilityPaper = Object.assign({}, this.facilityPaper, fpBase, {
              isCooperate: fpBase.isCooperate,
              isCommittee: fpBase.isCommittee ? fpBase.isCommittee : "N",
              isESGPaper:
                fpBase.isESGPaper == Constants.yesNoConst.Y
                  ? Constants.yesNoConst.Y
                  : Constants.yesNoConst.N,
              isFinancialYear: fpBase.isFinancialYear
                ? fpBase.isFinancialYear
                : "N",
            });
          }
        },
      );

    this.onFacilityPaperBaseDataChangeSub =
      this.facilityPaperAddEditService.onFacilityPaperBaseDataChange.subscribe(
        (facilityPaper: any) => {
          if (!_.isEmpty(facilityPaper)) {
            this.getCreditCalculatedFacilitiesESBResponseStatusForPaper(
              facilityPaper,
            );
          }
        },
      );

    this.onFacilityPaperUPCDataChangeSub =
      this.facilityPaperAddEditService.onFpUpcSectionChange.subscribe(
        (upcTemplate: any) => {
          if (!_.isEmpty(upcTemplate)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              fpUpcSectionDataDTOList: upcTemplate.fpUpcSectionDataDTOList,
              upcTemplateName: upcTemplate.upcTemplateName
                ? upcTemplate.upcTemplateName
                : "",
            });
          }
        },
      );

    this.onFacilityPaperTotalExposureChangeSub =
      this.facilityPaperAddEditService.onBaseFacilityPaperChange.subscribe(
        (exposureValue: any) => {
          if (!_.isEmpty(exposureValue)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              // totalExposureNew: exposureValue.totalExposureNew,
              isCommittee: exposureValue.isCommittee
                ? exposureValue.isCommittee
                : Constants.yesNoConst.N,
              isFinancialYear: exposureValue.isFinancialYear
                ? exposureValue.isFinancialYear
                : Constants.yesNoConst.N,
            });
          }
        },
      );

    this.onFacilityPaperChangeSubs =
      this.facilityPaperAddEditService.onFacilityPaperChange.subscribe(
        (facilityPaper: any) => {
          this.primaryCustomer = _.find(
            facilityPaper.casCustomerDTOList || [],
            (customer) => {
              return (
                customer.isPrimary &&
                customer.status === Constants.statusConst.ACT
              );
            },
          );
          this.kalyptoCustomer = this.primaryCustomer;
        },
      );

    this.onChangeTotalExposure =
      this.facilityPaperAddEditService.onBaseFacilityPaperChange.subscribe(
        (data: any) => {
          this.totalExposureResponse = data;
        },
      );

    this.onFacilityPaperChangeSubs =
      this.facilityPaperAddEditService.onFacilityPaperChange.subscribe(
        (fp: any) => {
          if (
            fp.currentFacilityPaperStatus ==
              this.facilityStatusConst.IN_PROGRESS &&
            fp.leadRefNumber &&
            fp.leadID &&
            fp.leadType == this.leadTypeConst.EXTERNAL
          ) {
            this.facilityPaperAddEditService.isAbleToReturnFacilityPaperToAgent(
              this.facilityPaper,
            );
          }
        },
      );

    this.onChangeAbleToReturnFacilityPaperToAgent =
      this.facilityPaperAddEditService.onAbleToReturnFacilityPaperToAgentChange.subscribe(
        (response: any) => {
          this.isAbleToReturnFacilityPaperToAgent = response.booleanValue;
        },
      );

    this.onFPDocumnetTypeChangeSub =
      this.facilityPaperAddEditService.onFPDocumnetType.subscribe(
        (version: number) => {
          this.securityDocumentVersion = version;
          if (this.securityDocumentVersion === 1) {
            this.onDocumentationTabChange =
              this.facilityPaperAddEditService.onDocumentationTabChange.subscribe(
                (data: any) => {
                  if (!_.isEmpty(data) && data.facilityDTOList.length != 0) {
                    this.getSecurityDocumentationElementList(
                      data.facilityDTOList,
                    );
                  }
                },
              );
          }
        },
      );

    this.onFacilityPaperChangeSubs =
      this.facilityPaperAddEditService.onFacilityPaperChange.subscribe(
        (fp: any) => {
          if (this.applicationService.isAgent()) {
            this.aboutTabIndex = 0;
            this.borrowerTabIndex = 1;
            this.facilitiesTabIndex = 2;
            this.securityTabIndex = 3;
            this.walletShareTabIndex = 4;
            this.upcTabIndex = 5;
            this.otherDetailsTabIndex = 6;
            this.commentTabIndex = 7;
            this.kalyptoDetailsTabIndex = 8;
          } else {
            if (
              this.hasPrivilegeToViewFullPaperAsDefault &&
              this.hasPrivilegeToEditUPCAsDefault &&
              fp.currentFacilityPaperStatus == this.facilityStatusConst.APPROVED
            ) {
              this.borrowerTabName = "Customer Statistics";
              this.aboutTabIndex = 0;
              this.borrowerTabIndex = 1;
              this.upcTabIndex = 2;
              this.otherDetailsTabIndex = 3;
              this.commentTabIndex = 4;
              this.creditRiskCommentTabIndex = 5;
              this.esgTabIndex = 6;
              this.kalyptoDetailsTabIndex = 7;
              this.reviewerCommentTabIndex = 8;
              this.customerCovenantTabIndex = 9;
              if (this.isBoardPaper()) {
                this.committeFullViewTabIndex = 10;
              } else {
                this.defaultFullViewTabIndex = 10;
              }

              if (this.showDocumentationTab()) {
                this.documentationTabIndex = 11;
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 12;

                  if (this.showAttachmentTab()) {
                    this.attachmentsTabIndex = 13;
                  }
                } else {
                  if (this.showAttachmentTab()) {
                    this.attachmentsTabIndex = 12;
                  }
                }
              } else {
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 11;

                  if (this.showAttachmentTab()) {
                    this.attachmentsTabIndex = 12;
                  }
                } else {
                  if (this.showAttachmentTab()) {
                    this.attachmentsTabIndex = 11;
                  }
                }
              }
            } else if (
              this.hasPrivilegeToViewFullPaperAsDefault &&
              fp.currentFacilityPaperStatus == this.facilityStatusConst.APPROVED
            ) {
              this.borrowerTabName = "Customer Statistics";
              this.aboutTabIndex = 0;
              this.borrowerTabIndex = 1;
              this.otherDetailsTabIndex = 2;
              this.commentTabIndex = 3;
              this.creditRiskCommentTabIndex = 4;
              this.esgTabIndex = 5;
              this.kalyptoDetailsTabIndex = 6;
              this.reviewerCommentTabIndex = 7;
              this.customerCovenantTabIndex = 8;
              if (this.isBoardPaper()) {
                this.committeFullViewTabIndex = 9;
              } else {
                this.defaultFullViewTabIndex = 9;
              }

              if (this.showDocumentationTab()) {
                this.documentationTabIndex = 10;
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 11;

                  if (this.showAttachmentTab()) {
                    this.attachmentsTabIndex = 12;
                  }
                } else {
                  if (this.showAttachmentTab()) {
                    this.attachmentsTabIndex = 11;
                  }
                }
              } else {
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 10;

                  if (this.showAttachmentTab()) {
                    this.attachmentsTabIndex = 11;
                  }
                } else {
                  if (this.showAttachmentTab()) {
                    this.attachmentsTabIndex = 10;
                  }
                }
              }
            } else if (
              this.hasPrivilegeToViewFullPaperAsDefault &&
              this.hasPrivilegeToEditUPCAsDefault &&
              fp.currentFacilityPaperStatus != this.facilityStatusConst.APPROVED
            ) {
              this.borrowerTabName = "Customer Statistics";
              this.aboutTabIndex = 0;
              this.borrowerTabIndex = 1;
              this.upcTabIndex = 2;
              this.otherDetailsTabIndex = 3;
              this.commentTabIndex = 4;
              this.creditRiskCommentTabIndex = 5;
              this.esgTabIndex = 6;
              this.kalyptoDetailsTabIndex = 7;
              this.customerCovenantTabIndex = 8;

              if (this.isBoardPaper()) {
                this.committeFullViewTabIndex = 9;
              } else {
                this.defaultFullViewTabIndex = 9;
              }

              if (this.showDocumentationTab()) {
                this.documentationTabIndex = 10;
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 11;
                  if (this.showAttachmentTab() == true) {
                    this.attachmentsTabIndex = 12;
                    this.deviationsTabIndex = 13;
                  } else {
                    this.deviationsTabIndex = 12;
                  }
                } else {
                  if (this.showAttachmentTab() == true) {
                    this.attachmentsTabIndex = 11;
                    this.deviationsTabIndex = 12;
                  } else {
                    this.deviationsTabIndex = 11;
                  }
                }
              } else {
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 10;
                  if (this.showAttachmentTab() == true) {
                    this.attachmentsTabIndex = 11;
                    this.deviationsTabIndex = 12;
                  } else {
                    this.deviationsTabIndex = 11;
                  }
                } else {
                  if (this.showAttachmentTab() == true) {
                    this.attachmentsTabIndex = 10;
                    this.deviationsTabIndex = 11;
                  } else {
                    this.deviationsTabIndex = 10;
                  }
                }
              }
            } else if (
              this.hasPrivilegeToViewFullPaperAsDefault &&
              fp.currentFacilityPaperStatus != this.facilityStatusConst.APPROVED
            ) {
              this.borrowerTabName = "Customer Statistics";
              this.aboutTabIndex = 0;
              this.borrowerTabIndex = 1;
              this.otherDetailsTabIndex = 2;
              this.commentTabIndex = 3;
              this.creditRiskCommentTabIndex = 4;
              this.esgTabIndex = 5;
              this.kalyptoDetailsTabIndex = 6;
              this.customerCovenantTabIndex = 7;
              if (this.isBoardPaper()) {
                this.committeFullViewTabIndex = 8;
              } else {
                this.defaultFullViewTabIndex = 8;
              }

              if (this.showDocumentationTab()) {
                this.documentationTabIndex = 9;
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 10;
                  this.attachmentsTabIndex = 11;
                  this.facilitiesTabIndex = 12;
                  this.securityTabIndex = 13;
                  this.walletShareTabIndex = 14;
                  this.upcTabIndex = 15;
                  this.deviationsTabIndex = 16;
                } else {
                  this.attachmentsTabIndex = 10;
                  this.facilitiesTabIndex = 11;
                  this.securityTabIndex = 12;
                  this.walletShareTabIndex = 13;
                  this.upcTabIndex = 14;
                  this.deviationsTabIndex = 15;
                }
              } else {
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 9;
                  this.attachmentsTabIndex = 10;
                  this.facilitiesTabIndex = 11;
                  this.securityTabIndex = 12;
                  this.walletShareTabIndex = 13;
                  this.upcTabIndex = 14;
                  this.deviationsTabIndex = 15;
                } else {
                  this.attachmentsTabIndex = 9;
                  this.facilitiesTabIndex = 10;
                  this.securityTabIndex = 11;
                  this.walletShareTabIndex = 12;
                  this.upcTabIndex = 13;
                  this.deviationsTabIndex = 14;
                }
              }
            } else {
              this.aboutTabIndex = 0;
              this.borrowerTabIndex = 1;
              this.facilitiesTabIndex = 2;
              this.securityTabIndex = 3;
              this.walletShareTabIndex = 4;
              this.upcTabIndex = 5;
              this.otherDetailsTabIndex = 6;
              this.commentTabIndex = 7;
              this.creditRiskCommentTabIndex = 8;
              this.esgTabIndex = 9;
              this.kalyptoDetailsTabIndex = 10;
              this.customerCovenantTabIndex = 11;

              if (this.isBoardPaper()) {
                this.committeFullViewTabIndex = 12;
              } else {
                this.defaultFullViewTabIndex = 12;
              }

              if (this.showDocumentationTab()) {
                this.documentationTabIndex = 13;
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 14;
                  if (this.showAttachmentTab() == true) {
                    this.attachmentsTabIndex = 15;
                    this.deviationsTabIndex = 16;
                  } else {
                    this.deviationsTabIndex = 15;
                  }
                } else {
                  if (this.showAttachmentTab() == true) {
                    this.attachmentsTabIndex = 14;
                    this.deviationsTabIndex = 15;
                  } else {
                    this.deviationsTabIndex = 14;
                  }
                }
              } else {
                if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                  this.committeeInquiriesTabIndex = 13;
                  if (this.showAttachmentTab() == true) {
                    this.attachmentsTabIndex = 14;
                    this.deviationsTabIndex = 15;
                  } else {
                    this.deviationsTabIndex = 14;
                  }
                } else {
                  if (this.showAttachmentTab() == true) {
                    this.attachmentsTabIndex = 13;
                    this.deviationsTabIndex = 14;
                  } else {
                    this.deviationsTabIndex = 13;
                  }
                }
              }
            }
            //BCC
            if (this.hasPrivilegeToViewBCCPapers) {
              this.aboutTabIndex = 0;
              this.attachmentsTabIndex = 1;
              this.commentTabIndex = 2;
              this.otherDetailsTabIndex = 3;
              if (this.isBoardPaper()) {
                this.committeFullViewTabIndex = 4;
              } else {
                this.defaultFullViewTabIndex = 4;
              }
              if (this.facilityPaper.isCommittee === Constants.yesNoConst.Y) {
                this.committeeInquiriesTabIndex = 5;
              }
            }

            if (fp.currentFacilityPaperStatus == "APPROVED") {
              this.documentationTabEnableDivCode = this.cacheService.getData(
                Constants.masterDataKey.CAS_SECURITY_DOCUMENT_SUBMIT_DIV,
              );
              this.loggedInUserDivCode =
                this.applicationService.getLoggedInUserDivCode();
              if (
                this.documentationTabEnableDivCode ==
                  this.loggedInUserDivCode ||
                fp.branchCode == this.loggedInUserDivCode
              ) {
                this.isDocumentationTabVisible = true;
              }
            } else {
              this.isDocumentationTabVisible = false;
            }
          }
        },
      );

    this.onCustomerListChangeSub =
      this.facilityPaperAddEditService.onFpCustomerChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.customerRatingsDTOList = [];
            data.casCustomerDTOList.forEach((customer) => {
              this.customerRatingsDTOList.push(customer.customerRatingsDTOList);
            });

            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              casCustomerDTOList: data.casCustomerDTOList,
            });
          }
        },
      );

    this.onFPaperSecSummeryChangeSub =
      this.facilityPaperAddEditService.onFPaperSecSummeryChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              fpSecuritySummeryDTO: data.fpSecuritySummeryDTO,
            });
          }
        },
      );

    this.onAddEditCreditRiskReplyChange =
      this.facilityPaperAddEditService.onAddEditCreditRiskReplyChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              fpCreditRiskCommentFilterDTO: data.fpCreditRiskCommentFilterDTO,
            });
          }
        },
      );

    this.onSaveOrUpdateFpCreditRiskCommentListChange =
      this.facilityPaperAddEditService.onSaveOrUpdateFpCreditRiskCommentListChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              fpCreditRiskCommentFilterDTO: data.fpCreditRiskCommentFilterDTO,
            });
          }
        },
      );

    this.onAddEditCreditRiskReplyChange =
      this.facilityPaperAddEditService.onAddEditCreditRiskReplyChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              fpCreditRiskCommentFilterDTO: data.fpCreditRiskCommentFilterDTO,
            });
          }
        },
      );

    this.onSaveOrUpdateFpCreditRiskCommentListChange =
      this.facilityPaperAddEditService.onSaveOrUpdateFpCreditRiskCommentListChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              fpCreditRiskCommentFilterDTO: data.fpCreditRiskCommentFilterDTO,
            });
          }
        },
      );

    this.onCreditRiskCommentListChange =
      this.facilityPaperAddEditService.onCreditRiskCommentListChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              fpCreditRiskCommentFilterDTO: data.fpCreditRiskCommentFilterDTO,
            });
          }
        },
      );

    this.onDocumentUploadChangeSub =
      this.facilityPaperAddEditService.onFPUploadDocumentChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            if (data.fpBccList.length > 0) {
              this.facilityPaper.fpBccList[0] = data.fpBccList[0];
            }
            //window.location.reload();
          }
        },
      );

    this.onCreditRiskCommentListChange =
      this.facilityPaperAddEditService.onCreditRiskCommentListChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.facilityPaper = Object.assign({}, this.facilityPaper, {
              fpCreditRiskCommentFilterDTO: data.fpCreditRiskCommentFilterDTO,
            });
          }
        },
      );

    this.onDocumentUploadChangeSub =
      this.facilityPaperAddEditService.onFPUploadDocumentChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            if (data.fpBccList.length > 0) {
              this.facilityPaper.fpBccList[0] = data.fpBccList[0];
            }
            //window.location.reload();
          }
        },
      );

    this.onCalculateFacilityPaperExposureChangeSub =
      this.facilityPaperAddEditService.onCalculateFacilityPaperExposureChange.subscribe(
        (exposure: any) => {
          if (!_.isEmpty(exposure)) {
            if (
              this.facilityPaper.facilityPaperID == exposure.facilityPaperID
            ) {
              this.facilityPaper = Object.assign({}, this.facilityPaper, {
                totalExposureNew: exposure.totalExposureNew,
                totalDirectExposurePrevious:
                  exposure.totalDirectExposurePrevious,
                totalDirectExposureNew: exposure.totalDirectExposureNew,
                totalIndirectExposurePrevious:
                  exposure.totalIndirectExposurePrevious,
                totalIndirectExposureNew: exposure.totalIndirectExposureNew,
                totalExposurePrevious: exposure.totalExposurePrevious,
                addTotalExposureToGroup: exposure.addTotalExposureToGroup,
                groupTotalDirectExposurePrevious:
                  exposure.groupTotalDirectExposurePrevious,
                groupTotalDirectExposureNew:
                  exposure.groupTotalDirectExposureNew,
                groupTotalIndirectExposurePrevious:
                  exposure.groupTotalIndirectExposurePrevious,
                groupTotalIndirectExposureNew:
                  exposure.groupTotalIndirectExposureNew,
                groupTotalExposurePrevious: exposure.groupTotalExposurePrevious,
                groupTotalExposureNew: exposure.groupTotalExposureNew,
              });
            }
          }
        },
      );

    this.onDirectorDetailUpdateSub =
      this.facilityPaperAddEditService.onFPCompanyDirectorsChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            if (data.fpDirectorDetailDTOList > 0) {
              this.facilityPaper.fpDirectorDetailDTOList =
                data.fpDirectorDetailDTOList;
            }
          }
        },
      );

    this.onShareHolderDetailUpdateSub =
      this.facilityPaperAddEditService.onShareHolderDetailsChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            if (data.fpShareHolderDetailDTOList > 0) {
              this.facilityPaper.fpShareHolderDetailDTOList =
                data.fpShareHolderDetailDTOList;
            }
          }
        },
      );

    this.onCovenantTabChange =
      this.facilityPaperAddEditService.onCustomerCovenantTabChange.subscribe(
        (data: any) => {
          if (data != null) {
            this.getCustomerCovenantList();
          }
        },
      );

    this.onCustomerCovenantAddTabChange =
      this.facilityPaperAddEditService.onCustomerCovenantAddTabChange.subscribe(
        (data: any) => {
          if (data != null) {
            this.getCustomerCovenantAddList();
          }
        },
      );

    this.onFacilityCovenantAddTabChange =
      this.facilityPaperAddEditService.onFacilityCovenantAddTabChange.subscribe(
        (data: any) => {
          if (data != null) {
            this.getFacilityCovenantAddList();
          }
        },
      );

    this.onFPDocumnetCountChangeSub =
      this.facilityPaperAddEditService.onFPDocumnetCount.subscribe(
        (data: any) => {
          this.sdCount = data;
        },
      );

    setTimeout(() => {
      console.log("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
      console.log(
        "FP Reference Number                  ==> ",
        this.facilityPaper.fpRefNumber,
      );
      console.log(
        "Total Security Cash Amount           ==> ",
        this.getTotalCashAmount(),
      );
      console.log(
        "Total Exposure New                   ==> ",
        this.facilityPaper.totalExposureNew,
      );
      console.log(
        "Total Exposure New - Cash Amount     ==> ",
        this.facilityPaper.totalExposureNew - this.getTotalCashAmount(),
      );
      console.log(
        "User DA Amount                       ==> ",
        this.userDa.maxAmount,
      );
      console.log(
        this.applicationService.getLoggedInUserDisplayName() +
          " Able to Approve ==> ",
        this.showApproveButton(this.facilityPaper),
      );
      console.log("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }, 5000);

    if (
      this.facilityPaper.assignDepartmentCode == "CA" &&
      this.facilityPaper.currentAssignUser != "BCC"
    ) {
      this.enableCommitteeButtons();
    }

    this.facilityPaperAddEditService
      .getFPUsersInvolved(this.facilityPaper.facilityPaperID)
      .then((data) => {
        if (data) {
          this.involeduserList = data;
        }
      });

    this.selectedYear = this.getSelectedYearType(this.isFinancialYear); //Constants.yesNoConst.Y

    this.onFacilityPaperChangeSub =
      this.facilityPaperAddEditService.onFacilityPaperChange.subscribe(
        (data: any) => {
          if (data) {
            this.facilityPaper = data;
          }
        },
      );

    this.loadAnnexureData(this.facilityPaper.facilityPaperID);

    this.onAnnexuresChangeSub = this.esgService.onAnnexuresChange.subscribe(
      (annexures: any[]) => {
        this.mandatoryAnnexureList = annexures.filter(
          (a: any) => a.isMandatory == Constants.yesNoConst.Y,
        );
      },
    );

    this.onESGChangeSub = this.esgService.onESGChange.subscribe(
      (annexures: any[]) => {
        if (annexures.length > 0) {
          this.completedAnnexures = annexures;
        } else {
          this.completedAnnexures = [];
        }
      },
    );

    this.onESGRiskScoreChangeSub =
      this.facilityPaperAddEditService.onESGRiskScoreChange.subscribe(
        (res: any) => {
          if (res && res.length > 0) {
            this.selectedRiskCategories = res;
            this.isEsgPaper = true;
          } else {
            this.selectedRiskCategories = [];
            this.isEsgPaper = false;
          }
        },
      );

      this.onDeviationCountChangeSub = this.facilityPaperAddEditService.onDeviationCountChange.subscribe((count:number) =>{
        this.deviationCount = count;
      });
  }

  isFPNotEmpty(facilityPaper: any): boolean {
    return Object.keys(facilityPaper).length !== 0;
  }

  enabledAutoApprove(facilityPaper: any): boolean {
    if (this.isFPNotEmpty(facilityPaper)) {

      let analyticsDecision: AnalyticsDecision =
        this.facilityPaper.analyticsDecision;

      let isModelApproved: boolean =
        analyticsDecision !== null &&
        analyticsDecision.decisionStatus === "Approved" &&
        analyticsDecision.channel !== null &&
        analyticsDecision.channel.toLowerCase() === "green";

      let exposureNewCheck: boolean = facilityPaper.totalExposureNew <= 5000000;

      let deviationCountCheck: boolean = this.deviationCount === 0;

      let divCodeCheck: boolean =
        this.applicationService.getLoggedInUserDivCode() ===
        SETTINGS.CCPU_DIV_CODE;

      let upmCodeCheck: boolean =
        parseInt(this.applicationService.getLoggedInUserUPMGroupCode()) ===
        Constants.applicationSecurityWorkClass.MANAGER;

      return (
        isModelApproved &&
        this.isEqualLoginAndAssignUser() &&
        this.showAssignButton(facilityPaper) &&
        exposureNewCheck &&
        divCodeCheck &&
        upmCodeCheck &&
        deviationCountCheck
      );
    }
    return false;
  }

  loadAnnexureData(facilityPaperID: number) {
    this.esgService.getAnnexureList().then(() => {
      return this.esgService.getAnnexureByPaperID({
        applicationFormID: null,
        facilityPaperID,
      });
    });
  }

  isCooperateFacilityPaper() {
    return this.facilityPaper.isCooperate == "Y";
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

  isAgentUser() {
    return this.applicationService.isAgent();
  }

  ngOnDestroy(): void {
    this.facilityPaperAddEditService.onFacilityPaperChange.next({});
    this.onFacilityPaperChangeSubs.unsubscribe();
    this.onCustomerChangeSub.unsubscribe();
    this.onFacilityPaperBaseDataChangeSub.unsubscribe();
    this.onCustomerListChangeSub.unsubscribe();
    this.onFPFacilityChangeSub.unsubscribe();
    this.onChangeTotalExposure.unsubscribe();
    this.onFacilityPaperUPCDataChangeSub.unsubscribe();
    this.onChangeAbleToReturnFacilityPaperToAgent.unsubscribe();
    this.onCustomerRatingsChangeSub.unsubscribe();
    this.onFPaperSecSummeryChangeSub.unsubscribe();
    this.onAddEditCreditRiskReplyChange.unsubscribe();
    this.onSaveOrUpdateFpCreditRiskCommentListChange.unsubscribe();
    this.onCreditRiskCommentListChange.unsubscribe();
    //  this.onCommitteePaperStatusChange.unsubscribe();
    this.onDocumentUploadChangeSub.unsubscribe();
    this.onDocumentationTabChange.unsubscribe();
    this.onCalculateFacilityPaperExposureChangeSub.unsubscribe();
    this.onCovenantTabChange.unsubscribe();
    this.onDirectorDetailUpdateSub.unsubscribe();
    this.onShareHolderDetailUpdateSub.unsubscribe();
    this.onFacilityPaperChangeSub.unsubscribe();
    this.onESGRiskScoreChangeSub.unsubscribe();
    this.onESGChangeSub.unsubscribe();
    this.onAnnexuresChangeSub.unsubscribe();
    this.onFPDocumnetTypeChangeSub.unsubscribe();
    this.onFPDocumnetCountChangeSub.unsubscribe();
    this.onDeviationCountChangeSub.unsubscribe();
  }

  copyFacilityPaper(facilityPaper: any) {
    this.modalRef = this.mdbModalService.show(
      FacilityPaperCopyDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-margin-center modal-width-40-p",
        containerClass: "",
        animated: true,
        data: {
          facilityPaper: facilityPaper,
          heading: "Copy Facility Paper From - ",
          message: "Do you want to copy this paper ?",
        },
      },
    );
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let replicateRQ = {
          workflowTemplateID: data.workflowTemplateID,
          branchCode: data.branchCode,
          originalFacilityPaperID: this.facilityPaper.facilityPaperID,
          assignUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          currentAssignUser: this.applicationService.getLoggedInUserUserName(),
          currentAssignUserID: this.applicationService.getLoggedInUserUserID(),
          currentAuthorityLevel:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          currentAssignUserDivCode:
            this.applicationService.getLoggedInUserDivCode(),
          assignUserUpmGroupCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          assignUserUpmID: this.applicationService.getLoggedInUserUserID(),
        };

        this.facilityPaperAddEditService.replicateFacilityPaper(replicateRQ);
      }
    });
  }

  getCustomerCovenants(facilityPaper: any) {
    var result: any[] = [];

    if (
      facilityPaper.customerCovenantDTOList &&
      facilityPaper.customerCovenantDTOList.length > 0
    ) {
      result = facilityPaper.customerCovenantDTOList.map((fp: any) => ({
        ...fp,
        customer:
          facilityPaper.casCustomerDTOList &&
          facilityPaper.casCustomerDTOList.length > 0
            ? facilityPaper.casCustomerDTOList[0]
            : null,
        facilityPaper: facilityPaper,
      }));
    }

    return result;
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

  goToKalipto(customerData) {
    this.kalyptoCustomer = customerData;
    this.onTabSelect(8);
  }

  onTabSelect($event: any) {
    let tabIndex: number = $event;

    if (
      tabIndex === this.upcTabIndex ||
      tabIndex === this.defaultFullViewTabIndex ||
      tabIndex === this.committeFullViewTabIndex
    ) {
      this.loadUPCData(tabIndex);
    } else {
      this.selectedTabIndex = $event;
    }
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  returnToAgent(
    facilityPaper,
    paperStatus,
    routingStatus?,
    approvalModalTopic?,
  ) {
    if (
      facilityPaper.currentFacilityPaperStatus ==
      this.facilityStatusConst.IN_PROGRESS
    ) {
      this.openModalFpReturnToAgent(
        facilityPaper,
        paperStatus,
        routingStatus,
        approvalModalTopic,
      );
    }
  }

  openModalFpReturnToAgent(
    facilityPaper,
    paperStatus,
    routingStatus?,
    approvalModalTopic?,
  ) {
    const initialState = {
      list: [{ tag: "Count", value: facilityPaper }],
    };
    this.modalRef = this.mdbModalService.show(FpReturnToAgentComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p modal-margin-center",
      containerClass: "right",
      animated: true,
      data: {
        content: {
          facilityPaper: facilityPaper,
          facilityPaperStatus: paperStatus,
          routingStatus: routingStatus,
        },
      },
    });
  }

  attend($event, facilityPaper, status) {
    let actionMessage = "Attended to this Facility Paper";
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    this.modalRef = this.mdbModalService.show(CommonAttendComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-40-p modal-margin-center",
      containerClass: "",
      animated: true,
      data: {
        heading: "Attending Paper : " + facilityPaper.fpRefNumber,
        message: "Do you want to attend this Facility Paper ?",
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let facilityPaperUpdateDTO = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          assignDepartmentCode: data.assignDepartmentCode,
          actionMessage: actionMessage,
          fpCommentDTO: { ...data.remarkData, actionMessage },
          facilityPaperStatus: status,
          forwardType: data.forwardType,
          assignUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          assignUser: this.applicationService.getLoggedInUserUserName(),
          assignADUserID: this.applicationService.getLoggedInUserUserName(),
          assignUserID: this.applicationService.getLoggedInUserUserID(),
          authorityLevel: this.applicationService.getLoggedInUserUserName(),
          assignUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          assignUserUpmGroupCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          assignUserUpmID: this.applicationService.getLoggedInUserUserID(),
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          routingStatus: this.facilityRoutingStatusConst.NEXT,
        };
        this.facilityPaperAddEditService.updateFacilityPaper(
          AppUtils.trim(facilityPaperUpdateDTO),
          false,
        );
      }
    });
  }

  release($event, facilityPaper, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    this.modalRef = this.mdbModalService.show(CommonReleaseComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-40-p modal-margin-center",
      containerClass: "",
      animated: true,
      data: {
        heading: "Releasing Paper : " + facilityPaper.fpRefNumber,
        message: "Do you want to release this Facility Paper?",
        content: {
          status: status,
          workflowTemplateID: this.facilityPaper.workflowTemplateID,
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let facilityPaperUpdateDTO = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          assignDepartmentCode: data.assignDepartmentCode,
          fpAssignDepartmentDTOList: data.assignDepartmentDTOList,
          actionMessage: data.actionMessage,
          fpCommentDTO: data.remarkData,
          forwardType: Constants.ForwardTypeConst.SAME_SOL_USER_GROUP,
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          facilityPaperStatus: status,
        };
        this.facilityPaperAddEditService.updateFacilityPaper(
          AppUtils.trim(facilityPaperUpdateDTO),
          true,
        );
      }
    });
  }

  async changeFacilityPaperStatus(facilityPaper: any, paperStatus: any) {
    let conditionStatus = this.canChangeDraftLevel(facilityPaper);

    if ((await conditionStatus).isInvalid) {
      let message = "";

      if ((await conditionStatus).messages.length > 1) {
        let last = (await conditionStatus).messages.pop();
        message = (await conditionStatus).messages.join(" ,") + " and " + last;
      } else {
        message = (await conditionStatus).messages.join(" ,");
      }

      this.modalRef = this.mdbModalService.show(InformationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Facility Paper Forwarding",
          message: "This paper does not have " + message,
          showConfirm: false,
        },
      });
      this.modalRef.content.action.subscribe((response: any) => {
        if (response) {
          this.openChangesStatusPopUpWindow(facilityPaper, paperStatus, false);
        }
      });
    } else if (this.isESGFwdCheck(paperStatus)) {
      this.handleEsgForward(facilityPaper, paperStatus);
    } else if (this.checkDocumentationCompletion(paperStatus)) {
      this.handleNonDocumentCompletion(facilityPaper, paperStatus);
    } else {
      this.handleOpenChangesStatusPopUp(facilityPaper, paperStatus, false);
    }
  }

  handleEsgForward(facilityPaper: any, paperStatus: any, isEsgFwd?: boolean) {
    let isEsg: boolean =
      isEsgFwd !== undefined && isEsgFwd !== null ? isEsgFwd : false;

    let isScoreSelected: boolean = this.isRiskScoreSelected();
    let isMandatoryAnnexSelected: boolean = this.isCompleteMandatoryAnnex();
    if (isScoreSelected && isMandatoryAnnexSelected) {
      this.handleOpenChangesStatusPopUp(facilityPaper, paperStatus, isEsg);
    } else {
      this.modalRef = this.mdbModalService.show(InformationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Facility Paper ESG Requirement",
          message: !isScoreSelected
            ? "This paper does not have select the risk score."
            : !isMandatoryAnnexSelected
              ? "This paper does not commplete the mandatory annexures."
              : "This paper does not have select the risk score or complete mandatory annexures.",
          showConfirm: false,
        },
      });
      this.modalRef.content.action.subscribe((result: any) => {});
    }
  }

  handleNonDocumentCompletion(facilityPaper: any, paperStatus: any) {
    this.modalRef = this.mdbModalService.show(SdConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Security Document Completion",
        message: this.getSDConfirmationMsg(),
        showConfirm: false,
        isSDConfirmation:
          paperStatus !== Constants.facilityPaperStatusConst.APPROVED,
      },
    });
    this.modalRef.content.action.subscribe((yesNo: any) => {
      if (yesNo) {
        this.openChangesStatusPopUpWindow(facilityPaper, paperStatus, false);
      }
    });
  }

  getSDConfirmationMsg() {
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();
    let msg: string = "The documentation for this paper is incomplete.";

    msg =
      msg +
      `${
        parseInt(loggedInUserWorkClass) <= 50 &&
        this.sdCount.returnedCount !== 0
          ? this.sdCount.returnedCount + " returned, "
          : ""
      } 
      ${
        parseInt(loggedInUserWorkClass) >= 50 &&
        this.sdCount.submittedCount !== 0
          ? this.sdCount.submittedCount + " submitted "
          : ""
      } `;

    if (
      msg.includes("drafted") ||
      msg.includes("returned") ||
      msg.includes("submitted")
    ) {
      msg = `${msg} document(s) need to be attended.`;
    }

    return msg;
  }

  handleOpenChangesStatusPopUp(
    facilityPaper: any,
    paperStatus: any,
    isEsgFwd: boolean,
  ) {
    if (
      this.applicationService.getLoggedInUserUPMGroupCode() ==
      Constants.applicationSecurityWorkClass.MD
    ) {
      if (this.isCommittee) {
        this.onTabSelect(this.committeFullViewTabIndex);
      }
    }

    // let expireStatus = this.isShowInsuranceExpireStatus(facilityPaper)
    //alert(expireStatus)

    //commented for CCF 241233 --------------------------

    let reqbody = { facilityPaperId: facilityPaper.facilityPaperID };
    let expireStatus: boolean = false;

    let custId = this.urlEncodeService.decode(this.selectedCIFID);


    const openStatusPopup = (hasCovenantRecords: boolean) => {
      const covenantHeading = hasCovenantRecords
        ? "Non complied covenant/s identified"
        : undefined;

      this.facilityPaperAddEditService
        .hasExpiredInsurance(reqbody)
        .then((response) => {
          expireStatus =
            response && response.result != null && response.result != undefined
              ? response.result
              : response;

          const hasAnyWarning = expireStatus || hasCovenantRecords;

          if (hasAnyWarning) {
            this.insuranceMessage = expireStatus
              ? "Forwarded with Expired Insurance/Valuation Records"
              : null;

            const warningMessages: string[] = [];
            if (expireStatus) {
              warningMessages.push(
                "Expired Insurance/ Valuation Records Identified.",
              );
            }
            if (hasCovenantRecords) {
              warningMessages.push("Non Complied Covenant/s Identified.");
            }

            const formattedWarningMessage = `<ul>${warningMessages
              .map((message) => `<li>${message}</li>`)
              .join("")}</ul><div style="margin-left: 40px;"><strong>Do you want to continue ?</strong></div>`;

            let heading = "FORWARD CONFIRMATION";
            this.modalRefForwardConfirmation = this.mdbModalService.show(
              ConfirmationDialogComponent,
              {
                backdrop: true,
                keyboard: true,
                focus: true,
                show: false,
                ignoreBackdropClick: true,
                class: "modal-width-30-p modal-margin-center",
                containerClass: "",
                animated: true,
                data: {
                  heading: heading,
                  approveRejectType: paperStatus,
                  message: formattedWarningMessage,
                },
              },
            );

            this.modalRefForwardConfirmation.content.action.subscribe(
              (approveStatus) => {
                if (approveStatus) {
                  this.openChangesStatusPopUpWindow(
                    facilityPaper,
                    paperStatus,
                    isEsgFwd,
                  );
                }
              },
            );
          } else {
            this.insuranceMessage = null;
            this.openChangesStatusPopUpWindow(
              facilityPaper,
              paperStatus,
              isEsgFwd,
            );
          }
        })
        .catch((err) => {
          this.alertService.showToaster(
            "please contact administrator",
            "ERROR",
          );
        });
    };

    if (custId && facilityPaper && facilityPaper.facilityPaperID) {
      this.facilityPaperAddEditService
        .getCovenantDetailsFromFinacle(custId, facilityPaper.facilityPaperID)
        .then((covenantRes: any) => {
          const covenantGroups =
            covenantRes &&
            covenantRes.result &&
            Array.isArray(covenantRes.result.covenant)
              ? covenantRes.result.covenant
              : covenantRes && Array.isArray(covenantRes.covenant)
                ? covenantRes.covenant
                : [];

          const hasCovenantRecords = covenantGroups.some((group: any) => {
            const covenantInq =
              group && Array.isArray(group.covenantInq) ? group.covenantInq : [];

            return covenantInq.some((covenant: any) => {
              const compSt =
                covenant && covenant.compSt != null
                  ? String(covenant.compSt).trim().toUpperCase()
                  : "";
              return compSt === "N";
            });
          });

          openStatusPopup(hasCovenantRecords);
        })
        .catch(() => {
          openStatusPopup(false);
        });
    } else {
      openStatusPopup(false);
    }

    //end

    // this.openChangesStatusPopUpWindow(facilityPaper, paperStatus);
  }

  returnFacilityPaper($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(FpReturnComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "Return",
        content: {
          facilityPaper: this.facilityPaper,
          status: status,
        },
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        let facilityPaperUpdateDTO = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          ...result,
          forwardType: Constants.ForwardTypeConst.DIRECT_USER,
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          facilityPaperStatus: status,
        };
        this.facilityPaperAddEditService.updateFacilityPaper(
          AppUtils.trim(facilityPaperUpdateDTO),
          true,
        );
      }
    });
  }

  declineFacilityPaper($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(
      CommentWithViewOptionsDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: true,
        data: {
          heading: "Decline Facility Paper",
          actionName: "Decline",
          commentCacheKey:
            this.facilityPaper.fpRefNumber +
            this.facilityStatusConst[status] +
            this.applicationService.getLoggedInUserUserID(),
          showUsersOnlyOption: false,
          showDivisionOnlyOption: false,
          content: {
            facilityPaper: this.facilityPaper,
            status: status,
          },
        },
      },
    );

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let actionMessage =
          "Rejected by " + this.applicationService.getLoggedInUserDisplayName();
        let facilityPaperUpdateDTO = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          actionMessage: actionMessage,
          fpCommentDTO: {
            comment: data.comment,
            isUsersOnly: data.isUsersOnly ? "Y" : "N",
            isDivisionOnly: data.isDivisionOnly ? "Y" : "N",
            isPublic: data.isPublic ? "Y" : "N",
            createdUserID: this.applicationService.getLoggedInUserUserID(),
            createdUser: this.applicationService.getLoggedInUserUserName(),
            createdUserDisplayName:
              this.applicationService.getLoggedInUserDisplayName(),
            createdUserDivCode:
              this.applicationService.getLoggedInUserDivCode(),
            createdUserUpmCode:
              this.applicationService.getLoggedInUserUPMGroupCode(),
            actionMessage: actionMessage,
            currentFacilityPaperStatus: status,
          },
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          facilityPaperStatus: status,
        };
        this.facilityPaperAddEditService.updateFacilityPaper(
          AppUtils.trim(facilityPaperUpdateDTO),
          true,
        );
      }
    });
  }

  approveFacilityPaper($event: any, status: any) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    if (this.facilityPaper.isCompLead == Constants.yesNoConst.Y) {
      let returnObj = this.canApproveOrForwordCompFacility(this.facilityPaper);
      let isInvalid: boolean = returnObj.isInvalid;
      if (isInvalid) {
        this.showRequiredSections(returnObj, this.facilityStatusConst.APPROVED);
      } else {
        this.hanldeCheckDocumentCompletion(status);
      }
    } else {
      this.hanldeCheckDocumentCompletion(status);
    }
  }

  hanldeCheckDocumentCompletion(status: any) {
    if (this.checkDocumentationCompletion(status)) {
      this.handleNonDocumentCompletion(this.facilityPaper, status);
    } else {
      this.handleApproveFacilityPaper(status);
    }
  }

  handleApproveFacilityPaper(status: any) {
    let isAutoApproval = this.enabledAutoApprove(this.facilityPaper);
    this.modalRef = this.mdbModalService.show(
      CommentWithViewOptionsDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: true,
        data: {
          heading: isAutoApproval ? "Authorize Facility Paper" : "Approve Facility Paper",
          actionName: isAutoApproval ? "Authorize" : "Approve",
          showUsersOnlyOption: false,
          showDivisionOnlyOption: false,
          content: {
            facilityPaper: this.facilityPaper,
            status: status,
          },
        },
      },
    );

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let upcTemplateName: string =
          this.facilityPaper && this.facilityPaper.upcTemplateName
            ? this.facilityPaper.upcTemplateName
            : "";
        let actionMessage =
          `${isAutoApproval ? 'Authorized by ' : 'Approved by '}` + this.applicationService.getLoggedInUserDisplayName();
        let facilityPaperUpdateDTO = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          actionMessage: actionMessage,
          fpCommentDTO: {
            comment: data.comment,
            isUsersOnly: data.isUsersOnly ? "Y" : "N",
            isDivisionOnly: data.isDivisionOnly ? "Y" : "N",
            isPublic: data.isPublic ? "Y" : "N",
            createdUserID: this.applicationService.getLoggedInUserUserID(),
            createdUser: this.applicationService.getLoggedInUserUserName(),
            createdUserDisplayName:
              this.applicationService.getLoggedInUserDisplayName(),
            createdUserDivCode:
              this.applicationService.getLoggedInUserDivCode(),
            createdUserUpmCode:
              this.applicationService.getLoggedInUserUPMGroupCode(),
            actionMessage: actionMessage,
            currentFacilityPaperStatus: status,
          },
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          assignUserUpmGroupCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          facilityPaperStatus: status,
          isReviewPaper: upcTemplateName.includes("Review")
            ? Constants.yesNoConst.Y
            : Constants.yesNoConst.N,
            isAutoApproval: isAutoApproval ? Constants.yesNoConst.Y : Constants.yesNoConst.N
        };
        this.facilityPaperAddEditService.updateFacilityPaper(
          AppUtils.trim(facilityPaperUpdateDTO),
          true,
        );
        this.facilityPaperAddEditService.getCovenantResponse();
      }
    });
  }

  async canChangeDraftLevel(facilityPaper: any) {
    // let scorecard: any;
    // try {
    //   await this.facilityPaperAddEditService
    //     .getCustomerMaxEvaluationId(this.facilityPaper.facilityPaperID)
    //     .then((data: any) => {
    //       scorecard = data;
    //     });
    // } catch (error) {
    //   return { isInvalid: true, messages: ["An error occurred."] };
    // }

    let creditFacilityList = facilityPaper.facilityDTOList || [];
    let fpUpcSectionDataDTOList = facilityPaper.fpUpcSectionDataDTOList || [];
    let allSecurities: any[] = [];
    creditFacilityList.forEach((facility: any) => {
      facility.facilitySecurityDTOList.forEach((security: any) => {
        allSecurities.push(security);
      });
    });

    let returnObj = { isInvalid: false, messages: [] };

    if (creditFacilityList.length == 0) {
      returnObj.isInvalid = true;
      returnObj.messages.push("Facilities");
    }

    if (allSecurities.length == 0) {
      returnObj.isInvalid = true;
      returnObj.messages.push("Securities");
    }

    // if (fpUpcSectionDataDTOList.length == 0) {
    //   returnObj.isInvalid = true;
    //   returnObj.messages.push("UPC Comments");
    // }

    let customerRatingsList = [];
    var ratingList: any[] = [];
    facilityPaper.casCustomerDTOList.forEach((customer: any) => {
      ratingList.push(customer.customerRatingsDTOList);
    });

    customerRatingsList = _.filter(ratingList || [], (customerRatings) => {
      return customerRatings.length === 0;
    });

    if (customerRatingsList.length != 0) {
      returnObj.isInvalid = true;
      returnObj.messages.push("Customer Ratings");
    }

    var isSecuritySummaryAvailable: boolean = facilityPaper.fpSecuritySummeryDTO
      ? true
      : false;

    if (this.isCommittee) {
      if (isSecuritySummaryAvailable) {
        var isSecuritySummaryGroup: boolean =
          facilityPaper.fpSecuritySummeryDTO.facilitySecuritySummaryType ==
          Constants.facilitySecuritySummaryTypeConst.GROUP;

        if (isSecuritySummaryGroup) {
          if (
            facilityPaper.fpSecuritySummeryDTO.groupTotal !=
            this.getMillionValue(facilityPaper.groupTotalExposureNew)
          ) {
            returnObj.isInvalid = true;
            returnObj.messages.push(
              "Valid group exposure value and security summary group grand total value.",
            );
          }
        } else {
          if (
            facilityPaper.fpSecuritySummeryDTO.companyTotal !=
            this.getMillionValue(facilityPaper.totalExposureNew)
          ) {
            returnObj.isInvalid = true;
            returnObj.messages.push(
              "Valid company exposure value and security summary company grand total value.",
            );
          }
        }
      } else {
        returnObj.isInvalid = true;
        returnObj.messages.push("Security Summary");
      }
    }
    return returnObj;
  }

  async openChangesStatusPopUpWindow(
    facilityPaper: any,
    facilityPaperStatus: any,
    isEsgFwd: boolean,
  ) {
    let returnUserList: any;
    let routingStatus: any;

    if (facilityPaperStatus == this.facilityStatusConst.CANCEL) {
      let facilityPaperDTORQ: any = {
        facilityPaperID: this.facilityPaper.facilityPaperID,
      };
      returnUserList =
        await this.facilityPaperAddEditService.getFPDirectReturnUsersList(
          facilityPaperDTORQ,
        );
      routingStatus = this.facilityRoutingStatusConst.BACK;
    } else {
      routingStatus = this.facilityRoutingStatusConst.NEXT;
    }

    this.modalRef = this.mdbModalService.show(CommonForwardComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "right",
      animated: true,
      data: {
        insuranceMessage: this.insuranceMessage,
        showUsersOnlyOption: true,
        showDivisionOnlyOption: true,
        heading: `${this.facilityPaperStatusChangeHeading[facilityPaperStatus]}` +
          " Facility Paper",
        actionMessage: `${this.facilityPaperStatusChangeHeading[facilityPaperStatus]}`,
        isForward: facilityPaperStatus == this.facilityStatusConst.IN_PROGRESS,
        isReturn: facilityPaperStatus == this.facilityStatusConst.CANCEL,
        isESG: isEsgFwd,
        commentCacheKey:
          this.facilityPaper.fpRefNumber +
          this.facilityStatusConst[facilityPaperStatus] +
          this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          branchCode: facilityPaper.branchCode,
          createdUser: facilityPaper.createdBy,
          currentAssignUser: facilityPaper.currentAssignUser,
          isCommittee: facilityPaper.isCommittee,
          workflowTemplateID: facilityPaper.workflowTemplateID,
          relatedDivCodes: [
            facilityPaper.branchCode,
            facilityPaper.createdUserBranchCode,
            facilityPaper.assignDepartmentCode,
            facilityPaper.currentAssignUserDivCode,
          ],
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let facilityPaperStatusChangeRQ = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          assignDepartmentCode: data.assignDepartmentCode,
          currentAssignUser:
            data.currentAssignUser != null ? data.currentAssignUser : null,
          currentUserCommitteeLevel:
            data.currentUserCommitteeLevel != null
              ? data.currentUserCommitteeLevel
              : null,
          currentCommitteePaperStatus:
            data.currentCommitteePaperStatus != null
              ? data.currentCommitteePaperStatus
              : null,
          fpAssignDepartmentDTOList: data.assignDepartmentDTOList,
          committeePaperDTOList: data.committeePaperDTOList,
          actionMessage: data.actionMessage,
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          fpCommentDTO: {
            ...data.remarkData,
            currentFacilityPaperStatus: facilityPaperStatus,
          },
          facilityPaperStatus: facilityPaperStatus,
          forwardType: data.forwardType,
          routingStatus: routingStatus,
          bccActionComment:
            data.remarkData && data.remarkData.comment
              ? data.remarkData.comment
              : "",
          commentUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
        };

        if (data.assignedUser) {
          facilityPaperStatusChangeRQ = Object.assign(
            {},
            facilityPaperStatusChangeRQ,
            {
              assignUserID: data.assignedUser.userID,
              assignADUserID: data.assignedUser.adUserID,
              assignUser: data.assignedUser.adUserID,
              assignUserDisplayName: data.assignedUser.assignUserDisplayName,
              assignUserUpmID: data.assignedUser.userID,
              assignUserDivCode: data.assignedUser.divCode,
              assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
              authorityLevel: data.assignedUser.adUserID,
              isChangeDocumentStatus: this.isDocumentStatusChanged(
                data.assignedUser,
                facilityPaperStatus,
              ),
            },
          );
        } else {
          facilityPaperStatusChangeRQ = Object.assign(
            {},
            facilityPaperStatusChangeRQ,
            {
              assignUserID: null,
              assignADUserID: null,
              assignUser: null,
              assignUserDisplayName:
                data.assignDepartmentCode == "CA"
                  ? data.assignUserDisplayName
                  : null,
              assignUserUpmID: null,
              assignUserDivCode: null,
              assignUserUpmGroupCode: null,
            },
          );
        }

        if (data.currentAssignUser == "BCC") {
          this.applicationService
            .getUserDetailListFormBranchAuthorityLevel({
              solId: "900",
              roleId: 81,
              appCode: "",
            })
            .then((res: any) => {
              this.bccFwdRecipients = res ? res.map((u: any) => u.email) : [];
            });
          this.submitWithPDF(facilityPaperStatusChangeRQ);
        } else {
          this.facilityPaperAddEditService
            .updateFacilityPaper(
              AppUtils.trim(facilityPaperStatusChangeRQ),
              true,
            )
            .then((res: any) => {
              if (res) {
                if (
                  data.assignDepartmentCode != null &&
                  data.assignDepartmentCode == "CA" &&
                  data.currentAssignUser != "BCC"
                ) {
                  //send CA email
                  var emailSendRequest: any = {
                    facilityPaperID: this.facilityPaper.facilityPaperID,
                    isCommittee: this.facilityPaper.isCommittee,
                    assignDepartmentCode: "CA",
                    isCommitteePaperReturn: false,
                    isForwardToCA: true,
                    committeePaperReturnUser: "",
                    currentPath: "",
                    currentAltLevelID: 0,
                    currentRegLevelID: 0,
                    currentAssignUser: data.currentAssignUser,
                    recentComment:
                      data.remarkData && data.remarkData.comment
                        ? data.remarkData.comment
                        : "",
                    recentCommentedBy:
                      this.applicationService.getLoggedInUserDisplayName(),
                  };
                  this.facilityPaperAddEditService.sendCAEmail(
                    emailSendRequest,
                  );
                }
              }
            });
        }
      }
    });
  }

  openModalFpApprove(
    facilityPaper,
    paperStatus,
    routingStatus?,
    approvalModalTopic?,
  ) {
    const initialState = {
      list: [{ tag: "Count", value: facilityPaper }],
    };
    if (this.applicationService.isAgent()) {
      this.modalRef = this.mdbModalService.show(AgentFpForwardComponent, {
        initialState,
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "right",
        animated: true,
        data: {
          heading: "comming dto",
          content: {
            facilityPaper: facilityPaper,
            facilityPaperStatus: paperStatus,
            routingStatus: routingStatus,
            approvalModalTopic: approvalModalTopic,
          },
        },
      });
    }
  }

  showReturnButton(facilityPaper) {
    if (
      !this.isAbleToReturnFacilityPaperToAgent &&
      (facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.IN_PROGRESS ||
        facilityPaper.currentFacilityPaperStatus ==
          this.facilityStatusConst.CANCEL)
    ) {
      return this.isEqualLoginAndAssignUser();
    } else {
      return false;
    }
  }

  showReturnToAgentButton(facilityPaper) {
    if (
      this.isAbleToReturnFacilityPaperToAgent &&
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.IN_PROGRESS
    ) {
      return this.isEqualLoginAndAssignUser();
    } else {
      return false;
    }
  }

  showForwardButton(facilityPaper) {
    if (
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.IN_PROGRESS ||
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.DRAFT ||
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.CANCEL
    ) {
      return true;
    } else {
      return false;
    }
  }

  showAttendButton(facilityPaper) {
    switch (facilityPaper.currentFacilityPaperStatus) {
      case this.facilityStatusConst.IN_PROGRESS:
      case this.facilityStatusConst.CANCEL: {
        if (this.isDivCodeIgnored) {
          return this.isAssignedForLoggedInUserUPMGroup();
        } else {
          return (
            this.isAssignedForLoggedInUserUPMGroup() &&
            this.facilityPaper.assignDepartmentCode ==
              this.applicationService.getLoggedInUserDivCode()
          );
        }
      }
      default:
        return false;
    }
  }

  showReleaseButton(facilityPaper) {
    switch (facilityPaper.currentFacilityPaperStatus) {
      case this.facilityStatusConst.IN_PROGRESS:
      case this.facilityStatusConst.CANCEL: {
        return this.isEqualLoginAndAssignUser();
      }
      default:
        return false;
    }
  }

  isAssignedForLoggedInUserUPMGroup() {
    if (this.facilityPaper.fpAssignDepartmentList) {
      let assignUserGroup = _.find(
        this.facilityPaper.fpAssignDepartmentList,
        (b) =>
          b.userGroupUPMCode ==
          this.applicationService.getLoggedInUserUPMGroupCode(),
      );
      return assignUserGroup && assignUserGroup.userGroupUPMCode;
    } else {
      return false;
    }
  }

  isPaperAssignedForLoggedInUserBranch() {
    if (this.facilityPaper.assignDepartmentCode) {
      return (
        this.facilityPaper.assignDepartmentCode ==
        this.applicationService.getLoggedInUserDivCode()
      );
    } else {
      return false;
    }
  }

  showAssignButton(facilityPaper) {
    if (
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.IN_PROGRESS ||
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.DRAFT ||
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.CANCEL
    ) {
      return true;
    } else {
      return false;
    }
  }

  isApprovedFacilityPaper(facilityPaper) {
    return (
      facilityPaper.currentFacilityPaperStatus ==
      this.facilityStatusConst.APPROVED
    );
  }

  showDeclineButton(facilityPaper) {
    if (
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.IN_PROGRESS ||
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.DRAFT ||
      facilityPaper.currentFacilityPaperStatus ==
        this.facilityStatusConst.CANCEL
    ) {
      return this.isEqualLoginAndAssignUser();
    } else {
      return false;
    }
  }

  showApproveButton(facilityPaper: any) {
    if (facilityPaper && facilityPaper.isCommittee == Constants.yesNoConst.Y) {
      return (
        this.isEqualLoginAndAssignUser() &&
        (facilityPaper.currentFacilityPaperStatus ==
          this.facilityStatusConst.IN_PROGRESS ||
          facilityPaper.currentFacilityPaperStatus ==
            this.facilityStatusConst.CANCEL) &&
        this.getMillionValue(facilityPaper.groupNetTotalExposureNew) > 0 &&
        (this.getMillionValue(facilityPaper.groupNetTotalExposureNew) <=
          this.getMillionValue(this.userDa.maxAmount) ||
          Constants.unlimitedDALevelsUser.includes(
            this.applicationService.getLoggedInUserUPMGroupCode(),
          ))
      );
    } else {
      return (
        this.isEqualLoginAndAssignUser() &&
        (facilityPaper.currentFacilityPaperStatus ==
          this.facilityStatusConst.IN_PROGRESS ||
          facilityPaper.currentFacilityPaperStatus ==
            this.facilityStatusConst.CANCEL) &&
        facilityPaper.totalExposureNew > 0 &&
        (facilityPaper.totalExposureNew - this.getTotalCashAmount() <=
          this.userDa.maxAmount ||
          Constants.unlimitedDALevelsUser.includes(
            this.applicationService.getLoggedInUserUPMGroupCode(),
          ))
      );
    }

    //toDo need to remove unlimited user DA level validation when committee integrated
  }

  async agentForwardFacilityPaper(
    facilityPaper,
    paperStatus,
    routingStatus?,
    approvalModalTopic?,
  ) {
    let conditionStatus = this.canChangeDraftLevel(facilityPaper);

    if ((await conditionStatus).isInvalid) {
      let message = "";

      if ((await conditionStatus).messages.length > 1) {
        let last = (await conditionStatus).messages.pop();
        message = (await conditionStatus).messages.join(" ,") + " and " + last;
      } else {
        message = (await conditionStatus).messages.join(" ,");
      }

      this.modalRef = this.mdbModalService.show(InformationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading: "Change to in progress",
          message: "Facility paper has " + message,
          showConfirm: true,
        },
      });
      this.modalRef.content.action.subscribe((response: any) => {
        if (response) {
          this.openModalFpApprove(
            facilityPaper,
            paperStatus,
            routingStatus,
            approvalModalTopic,
          );
        }
      });
    } else {
      this.openModalFpApprove(
        facilityPaper,
        paperStatus,
        routingStatus,
        approvalModalTopic,
      );
    }
  }

  getColour(facilityStatus) {
    switch (facilityStatus) {
      case this.facilityStatusConst.DRAFT:
        return { color: "#ffbb33a6" };
      case this.facilityStatusConst.IN_PROGRESS:
        return { color: "#0099cc94" };
      case this.facilityStatusConst.APPROVED:
        return { color: "#007e338a" };
      case this.facilityStatusConst.CANCEL:
        return { color: "#cc000073" };
      case this.facilityStatusConst.REJECTED:
        return { color: "#cc0000a6" };
    }
  }

  getTotalCashAmount() {
    let securityCashMap = {};
    let securityCashTotal = 0;
    if (
      this.facilityPaper.facilityDTOList &&
      this.facilityPaper.facilityDTOList.length > 0
    ) {
      this.facilityPaper.facilityDTOList.forEach((e) => {
        e.facilitySecurityDTOList.forEach((s) => {
          securityCashMap[s.facilitySecurityID] = s.cashAmount;
        });
      });

      for (const [key, value] of Object.entries(securityCashMap)) {
        securityCashTotal = securityCashTotal + +value;
      }
    }

    return Number(securityCashTotal);
  }

  getCreditCalculatedFacilitiesESBResponseStatusForPaper(facilityPaper) {
    if (
      facilityPaper.currentFacilityPaperStatus ==
      this.facilityStatusConst.APPROVED
    ) {
      if (
        _.filter(facilityPaper.facilityDTOList, (data) => {
          return (
            data.creditFacilityTemplateDTO.showCalculator == this.yesNoConst.Y
          );
        }).length > 0
      ) {
        this.facilityPaperAddEditService.getCreditCalculatedFacilitiesESBResponseStatus(
          facilityPaper.facilityPaperID,
        );
      }
    }
  }

  async returnCommitteePaper() {
    let committeePaperStatus = this.committeePaperStatusConst.RETURNED;
    let facilityPaperStatus = this.facilityStatusConst.CANCEL;

    let returnUserList: any;
    let routingStatus: any;

    let facilityPaperDTORQ: any = {
      facilityPaperID: this.facilityPaper.facilityPaperID,
    };

    returnUserList =
      await this.facilityPaperAddEditService.getFPDirectReturnUsersList(
        facilityPaperDTORQ,
      );
    routingStatus = this.facilityRoutingStatusConst.BACK;

    this.modalRef = this.mdbModalService.show(CommonForwardComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "right",
      animated: true,
      data: {
        showUsersOnlyOption: true,
        showDivisionOnlyOption: true,
        heading: "Return Committee Paper",
        actionMessage: "Return",
        isReturn: true,
        //    commentCacheKey: this.facilityPaper.fpRefNumber + this.facilityStatusConst[facilityPaperStatus] + this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          branchCode: this.facilityPaper.branchCode,
          createdUser: this.facilityPaper.createdBy,
          currentAssignUser: this.facilityPaper.currentAssignUser,
          workflowTemplateID: this.facilityPaper.workflowTemplateID,
          relatedDivCodes: [
            this.facilityPaper.branchCode,
            this.facilityPaper.createdUserBranchCode,
            this.facilityPaper.assignDepartmentCode,
            this.facilityPaper.currentAssignUserDivCode,
          ],
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let facilityPaperStatusChangeRQ = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          assignDepartmentCode: data.assignDepartmentCode,
          currentAssignUser:
            data.currentAssignUser != null ? data.currentAssignUser : null,
          currentUserCommitteeLevel:
            data.currentUserCommitteeLevel != null
              ? data.currentUserCommitteeLevel
              : null,
          currentCommitteePaperStatus:
            data.currentCommitteePaperStatus != null
              ? data.currentCommitteePaperStatus
              : null,
          fpAssignDepartmentDTOList: data.assignDepartmentDTOList,
          committeePaperDTOList: data.committeePaperDTOList,
          actionMessage: data.actionMessage,
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          fpCommentDTO: {
            ...data.remarkData,
            currentFacilityPaperStatus: facilityPaperStatus,
          },
          facilityPaperStatus: facilityPaperStatus,
          forwardType: data.forwardType,
          routingStatus: routingStatus,
        };

        if (data.assignedUser) {
          facilityPaperStatusChangeRQ = Object.assign(
            {},
            facilityPaperStatusChangeRQ,
            {
              assignUserID: data.assignedUser.userID,
              assignADUserID: data.assignedUser.adUserID,
              assignUser: data.assignedUser.adUserID,
              assignUserDisplayName: data.assignedUser.assignUserDisplayName,
              assignUserUpmID: data.assignedUser.userID,
              assignUserDivCode: data.assignedUser.divCode,
              assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
              authorityLevel: data.assignedUser.adUserID,
            },
          );
        } else {
          facilityPaperStatusChangeRQ = Object.assign(
            {},
            facilityPaperStatusChangeRQ,
            {
              assignUserID: null,
              assignADUserID: null,
              assignUser: null,
              assignUserDisplayName:
                data.assignDepartmentCode == "CA"
                  ? data.assignUserDisplayName
                  : null,
              assignUserUpmID: null,
              assignUserDivCode: null,
              assignUserUpmGroupCode: null,
            },
          );
        }

        let actionMessage =
          "Returned by " +
          this.applicationService.getLoggedInUserDisplayName() +
          " as a member of " +
          this.committeePaper.committeeType;
        let committeeStatusHistoryDTO = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          committeePaperStatus: committeePaperStatus,
          actionMessage: actionMessage,
          facilityPaperStatus: facilityPaperStatus,
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          committeePaperID:
            this.facilityPaper.committeePaperList[0].committeePaperID,
          pathType: this.facilityPaper.committeePaperList[0].currentPath,
        };

        this.facilityPaperAddEditService
          .updateCommitteeStatusHistory(
            AppUtils.trim(committeeStatusHistoryDTO),
          )
          .subscribe((res: any) => {
            if (res.isUserAtSameLevel == "Y") {
              this.facilityPaperAddEditService.updateFacilityPaper(
                AppUtils.trim(facilityPaperStatusChangeRQ),
                true,
              );
              //send CA email
              var emailSendRequest: any = {
                facilityPaperID: this.facilityPaper.facilityPaperID,
                isCommittee: this.facilityPaper.isCommittee,
                assignDepartmentCode: "CA",
                isCommitteePaperReturn: true,
                isForwardToCA: false,
                committeePaperReturnUser: data.assignedUser.adUserID,
                currentAssignUser: this.committeePaper.committeeType,
                recentComment:
                  data.remarkData && data.remarkData.comment
                    ? data.remarkData.comment
                    : "",
                recentCommentedBy:
                  this.applicationService.getLoggedInUserDisplayName(),
              };
              this.facilityPaperAddEditService.sendCAEmail(emailSendRequest);
            }
            if (res.isUserAtSameLevel == "N") {
              this.approveModalRef = this.mdbModalService.show(
                ConfirmationDialogComponent,
                {
                  backdrop: true,
                  keyboard: true,
                  focus: true,
                  show: false,
                  ignoreBackdropClick: true,
                  class: "modal-width-30-p modal-margin-center ",
                  containerClass: "",
                  animated: true,
                  data: {
                    heading: "Save Comment",
                    message:
                      "Facility paper cannot be returned as it has been moved to the next level. Would you like to save your comment?",
                  },
                },
              );
              this.approveModalRef.content.action.subscribe((isYes: any) => {
                if (isYes) {
                  let actionMessage =
                    "Commented by " +
                    this.applicationService.getLoggedInUserDisplayName() +
                    " as a member of " +
                    this.committeePaper.committeeType;
                  let committeeStatusHistoryDTO = {
                    facilityPaperID: this.facilityPaper.facilityPaperID,
                    committeePaperStatus:
                      this.committeePaperStatusConst.COMMENTED,
                    actionMessage: actionMessage,
                    facilityPaperStatus:
                      this.facilityPaper.currentFacilityPaperStatus,
                    createdUserDisplayName:
                      this.applicationService.getLoggedInUserDisplayName(),
                    committeePaperID: this.committeePaper.committeePaperID,
                    pathType: this.committeePaper.currentPath,
                  };

                  this.facilityPaperAddEditService
                    .updateCommitteeStatusHistory(
                      AppUtils.trim(committeeStatusHistoryDTO),
                    )
                    .subscribe((res: any) => {
                      let fpCommentDTO =
                        facilityPaperStatusChangeRQ.fpCommentDTO;
                      fpCommentDTO.actionMessage = actionMessage;
                      fpCommentDTO.facilityPaperID =
                        this.facilityPaper.facilityPaperID;
                      this.facilityPaperAddEditService.addEditComment(
                        AppUtils.trim(fpCommentDTO),
                      );
                      this.router.navigate(["/my-facility-papers"]);
                    });
                }
              });
            }
          });
      }
    });
  }

  recommendCommitteePaper() {
    let committeePaperStatus = this.committeePaperStatusConst.RECOMMENDED;

    this.modalRef = this.mdbModalService.show(
      CommentWithViewOptionsDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: true,
        data: {
          heading: "Approve Facility Paper",
          actionName: "Approve",
          showUsersOnlyOption: false,
          showDivisionOnlyOption: false,
          content: {
            facilityPaper: this.facilityPaper,
            status: committeePaperStatus,
          },
        },
      },
    );

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let actionMessage =
          "Approved by " +
          this.applicationService.getLoggedInUserDisplayName() +
          " as a member of " +
          this.committeePaper.committeeType;
        let committeeStatusHistoryDTO = {
          facilityPaperID: this.facilityPaper.facilityPaperID,
          committeePaperStatus: committeePaperStatus,
          actionMessage: actionMessage,
          facilityPaperStatus: this.facilityPaper.currentFacilityPaperStatus,
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          committeePaperID: this.committeePaper.committeePaperID,
          pathType: this.committeePaper.currentPath,
        };

        let fpCommentDTO = {
          comment: data.comment,
          facilityPaperID: this.facilityPaper.facilityPaperID,
          isUsersOnly: data.isUsersOnly ? "Y" : "N",
          isDivisionOnly: data.isDivisionOnly ? "Y" : "N",
          isPublic: data.isPublic ? "Y" : "N",
          createdUserID: this.applicationService.getLoggedInUserUserID(),
          createdUser: this.applicationService.getLoggedInUserUserName(),
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          createdUserUpmCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          actionMessage: actionMessage,
          currentFacilityPaperStatus: committeePaperStatus, //Committee paper Status
        };
        this.facilityPaperAddEditService
          .updateCommitteeStatusHistory(
            AppUtils.trim(committeeStatusHistoryDTO),
          )
          .subscribe((res: any) => {
            if (res.isUserAtSameLevel == "Y") {
              let upcTemplateName: string =
                this.facilityPaper && this.facilityPaper.upcTemplateName
                  ? this.facilityPaper.upcTemplateName
                  : "";

              //send CA email
              var emailSendRequest: any = {
                facilityPaperID: this.facilityPaper.facilityPaperID,
                isCommittee: this.facilityPaper.isCommittee,
                assignDepartmentCode: "CA",
                isCommitteePaperReturn: false,
                isForwardToCA: false,
                committeePaperReturnUser: "",
                currentAssignUser: this.committeePaper.committeeType,
                currentPath: this.committeePaper.currentPath,
                currentAltLevelID: this.committeePaper.currentAltLevelID,
                currentRegLevelID: this.committeePaper.currentRegLevelID,
                recentComment: data.comment ? data.comment : actionMessage,
                recentCommentedBy:
                  this.applicationService.getLoggedInUserDisplayName(),
                isReviewPaper: upcTemplateName.includes("Review")
                  ? Constants.yesNoConst.Y
                  : Constants.yesNoConst.N,
                upcTemplateId:
                  this.facilityPaper.upcTemplateID !== null
                    ? this.facilityPaper.upcTemplateID
                    : 0,
              };
              this.facilityPaperAddEditService.sendCAEmail(emailSendRequest);

              this.facilityPaperAddEditService.addEditComment(
                AppUtils.trim(fpCommentDTO),
              );
              this.router.navigate(["/my-facility-papers"]);
            }

            if (res.isUserAtSameLevel == "N") {
              this.approveModalRef = this.mdbModalService.show(
                ConfirmationDialogComponent,
                {
                  backdrop: true,
                  keyboard: true,
                  focus: true,
                  show: false,
                  ignoreBackdropClick: true,
                  class: "modal-width-30-p modal-margin-center ",
                  containerClass: "",
                  animated: true,
                  data: {
                    heading: "Save Comment",
                    message:
                      "Facility paper cannot be approved as it has been moved to the next level. Would you like to save your comment?",
                  },
                },
              );
              this.approveModalRef.content.action.subscribe((isYes: any) => {
                if (isYes) {
                  let actionMessage =
                    "Commented by " +
                    this.applicationService.getLoggedInUserDisplayName() +
                    " as a member of " +
                    this.committeePaper.committeeType;
                  let committeeStatusHistoryDTO = {
                    facilityPaperID: this.facilityPaper.facilityPaperID,
                    committeePaperStatus:
                      this.committeePaperStatusConst.COMMENTED,
                    actionMessage: actionMessage,
                    facilityPaperStatus:
                      this.facilityPaper.currentFacilityPaperStatus,
                    createdUserDisplayName:
                      this.applicationService.getLoggedInUserDisplayName(),
                    committeePaperID: this.committeePaper.committeePaperID,
                    pathType: this.committeePaper.currentPath,
                  };
                  console.log(
                    "committeeStatusHistoryDTO",
                    committeeStatusHistoryDTO,
                  );
                  this.facilityPaperAddEditService
                    .updateCommitteeStatusHistory(
                      AppUtils.trim(committeeStatusHistoryDTO),
                    )
                    .subscribe((res: any) => {
                      fpCommentDTO.actionMessage = actionMessage;
                      fpCommentDTO.facilityPaperID =
                        this.facilityPaper.facilityPaperID;
                      this.facilityPaperAddEditService.addEditComment(
                        AppUtils.trim(fpCommentDTO),
                      );
                      this.router.navigate(["/my-facility-papers"]);
                    });
                }
              });
            }
          });
      }
    });
  }

  showCommitteeButtons(facilityPaper) {
    let committeePaperList = facilityPaper.committeePaperList;
    let committeeStatusHistoryList;
    let committeeStatusHistory;
    if (committeePaperList != undefined && committeePaperList.length != 0) {
      this.committeePaper = _.filter(
        committeePaperList,
        (committeePaper) =>
          committeePaper.currentCommitteePaperStatus !=
          Constants.committeePaperStatusConst.RETURNED,
      )[0];
      if (this.committeePaper != undefined && this.committeePaper.length != 0) {
        if (this.committeePaper.committeeType == "BCC") {
          return false;
        } else {
          committeeStatusHistoryList =
            this.committeePaper.committeeStatusHistoryList;
          committeeStatusHistory = _.filter(
            committeeStatusHistoryList,
            (committeeStatusHistory) =>
              committeeStatusHistory.createdBy ==
                this.applicationService.getLoggedInUserUserName() &&
              committeeStatusHistory.committeePaperStatus ==
                Constants.committeePaperStatusConst.RECOMMENDED &&
              committeeStatusHistory.pathType ==
                this.committeePaper.currentPath,
          );
          if (
            committeeStatusHistory == null ||
            committeeStatusHistory.length == 0
          ) {
            return true;
          } else {
            return false;
          }
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  submitBCCStatus($event: any, bccStatus: any) {
    let fpBccId = null;
    let actionName = "";
    let actionMessage = "";
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    let fpBccList = this.facilityPaper.fpBccList[0];

    if (fpBccList != undefined) {
      fpBccId = fpBccList.fpBccId;
    }

    if (bccStatus == this.bccPaperStatusConst.APPROVED) {
      actionName = "Approved by BCC";
      actionMessage = "Submitted SD Enterer Decision (Approved)";
    }
    if (bccStatus == this.bccPaperStatusConst.DECLINED) {
      actionName = "Declined by BCC";
      actionMessage = "Submitted SD Enterer Decision (Declined)";
    }

    this.modalRef = this.mdbModalService.show(
      CommentWithViewOptionsDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: true,
        data: {
          showUsersOnlyOption: false,
          showDivisionOnlyOption: false,
          actionName: actionName,
          commentCacheKey:
            this.facilityPaper.fpRefNumber +
            this.applicationService.getLoggedInUserUserID() +
            "Commenting",
          heading: actionName,
          // comment: comment
          isMeetingDateEnable: true,
          meetingDate: this.getMeetingDate(),
        },
      },
    );
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        var attachments: any[] =
          this.facilityPaper &&
          this.facilityPaper.fpBccList[0] &&
          this.facilityPaper.fpBccList[0].fpBccDocumentList
            ? this.facilityPaper.fpBccList[0].fpBccDocumentList.map(
                (file: any) => ({
                  fileName: file.docStorageDTO.fileName
                    ? file.docStorageDTO.fileName.replace(".pdf", "")
                    : "Cover Page Or Meeting Minute",
                  fileDataUri: file.docStorageDTO.document,
                }),
              )
            : [];

        this.applicationService
          .getUserDetailListFormBranchAuthorityLevel({
            solId: "800",
            roleId: 82,
            appCode: "",
          })
          .then((res: any) => {
            let fpBccDTO = {
              facilityPaperID: this.facilityPaper.facilityPaperID,
              bccStatus: bccStatus,
              actionMessage: actionMessage,
              createdUserDisplayName:
                this.applicationService.getLoggedInUserDisplayName(),
              approvedUserDisplayName:
                this.applicationService.getLoggedInUserDisplayName(),
              approveStatus: this.bccWorkFlowStatusConst.PENDING,
              fpBccId: fpBccId,
              attachments: attachments,
              bccAuthRecipients: res ? res.map((u: any) => u.email) : [],
              bccActionComment: data.comment,
              commentUserDisplayName:
                this.applicationService.getLoggedInUserDisplayName(),
              bccMeetingDate: data.meetingDate,
            };
            let fpCommentDTO = {
              comment: data.comment,
              facilityPaperID: this.facilityPaper.facilityPaperID,
              isUsersOnly: "N",
              isDivisionOnly: "N",
              isPublic: "Y",
              createdUserID: this.applicationService.getLoggedInUserUserID(),
              createdUser: this.applicationService.getLoggedInUserUserName(),
              createdUserDisplayName:
                this.applicationService.getLoggedInUserDisplayName(),
              createdUserDivCode:
                this.applicationService.getLoggedInUserDivCode(),
              createdUserUpmCode:
                this.applicationService.getLoggedInUserUPMGroupCode(),
              actionMessage: actionMessage,
              currentFacilityPaperStatus: this.bccWorkFlowStatusConst.PENDING, //Committee paper Status
            };
            this.facilityPaperAddEditService
              .updateBccDTO(AppUtils.trim(fpBccDTO))
              .subscribe((res: any) => {
                this.facilityPaperAddEditService.addEditComment(
                  AppUtils.trim(fpCommentDTO),
                );
                this.router.navigate(["/committee-paper/dashboard"]);
              });
          });

        //  this.facilityPaperAddEditService.addEditComment(AppUtils.trim(saveData));
      }
    });
  }

  authorizeBCCStatus($event: any, approveStatus: any) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    let actionName = "";
    let actionMessage = "";
    let fpBccList = this.facilityPaper.fpBccList[0];
    let bccStatusToAuthorize = fpBccList.bccStatus;
    let fpBccId = fpBccList.fpBccId;
    // let fpUsersInvolvedList: any = {};

    if (approveStatus == this.bccWorkFlowStatusConst.APPROVED) {
      actionName = "Authorize";
      actionMessage = "Approved SD Enterer Decision";
    }
    if (approveStatus == this.bccWorkFlowStatusConst.REJECTED) {
      actionName = "Reject";
      actionMessage = "Rejected SD Enterer Decision";
    }

    this.facilityPaperAddEditService
      .getFPUsersInvolved(this.facilityPaper.facilityPaperID)
      .then((data: any) => {
        if (data) {
          this.modalRef = this.mdbModalService.show(
            CommentWithViewOptionsDialogComponent,
            {
              backdrop: true,
              keyboard: true,
              focus: true,
              show: false,
              ignoreBackdropClick: true,
              class: "modal-width-60-p",
              containerClass: "",
              animated: true,
              data: {
                showUsersOnlyOption: false,
                showDivisionOnlyOption: false,
                actionName: actionName,
                commentCacheKey:
                  this.facilityPaper.fpRefNumber +
                  this.applicationService.getLoggedInUserUserID() +
                  "Commenting",
                heading: actionName,
                fpUsersInvolvedList: data
                  ? data.map((d: any) => ({ ...d, isSelected: false }))
                  : [],
                // comment: comment
              },
            },
          );

          this.modalRef.content.action.subscribe((data: any) => {
            if (data) {
              var involedUsers: any[] = data.fpUsersInvolvedList
                ? data.fpUsersInvolvedList.filter(
                    (u: any) => u.isSelected == true,
                  )
                : [];
              var attachments: any[] =
                this.facilityPaper &&
                this.facilityPaper.fpBccList[0] &&
                this.facilityPaper.fpBccList[0].fpBccDocumentList
                  ? this.facilityPaper.fpBccList[0].fpBccDocumentList.map(
                      (file: any) => ({
                        fileName: file.docStorageDTO.fileName
                          ? file.docStorageDTO.fileName.replace(".pdf", "")
                          : "Cover Page Or Meeting Minute",
                        fileDataUri: file.docStorageDTO.document,
                      }),
                    )
                  : [];

              this.applicationService
                .getUserDetailListFormBranchAuthorityLevel({
                  solId: "800",
                  roleId: 81,
                  appCode: "",
                })
                .then((upmRes: any) => {
                  let fpBccDTO = {
                    facilityPaperID: this.facilityPaper.facilityPaperID,
                    bccStatus: bccStatusToAuthorize,
                    actionMessage: actionMessage,
                    approvedUserDisplayName:
                      this.applicationService.getLoggedInUserDisplayName(),
                    fpBccId: fpBccId,
                    approveStatus: approveStatus,
                    attachments: attachments,
                    bccAuthRecipients: upmRes
                      ? upmRes.map((u: any) => u.email)
                      : [],
                    bccAuthRecipientIds: involedUsers.map(
                      (u: any) => u.assignUserID,
                    ),
                    bccActionComment: data.comment,
                    commentUserDisplayName:
                      this.applicationService.getLoggedInUserDisplayName(),
                  };

                  let fpCommentDTO = {
                    comment: data.comment,
                    facilityPaperID: this.facilityPaper.facilityPaperID,
                    isUsersOnly: "N",
                    isDivisionOnly: "N",
                    isPublic: "Y",
                    createdUserID:
                      this.applicationService.getLoggedInUserUserID(),
                    createdUser:
                      this.applicationService.getLoggedInUserUserName(),
                    createdUserDisplayName:
                      this.applicationService.getLoggedInUserDisplayName(),
                    createdUserDivCode:
                      this.applicationService.getLoggedInUserDivCode(),
                    createdUserUpmCode:
                      this.applicationService.getLoggedInUserUPMGroupCode(),
                    actionMessage: actionMessage,
                    currentFacilityPaperStatus: approveStatus, //Committee paper Status
                  };

                  this.facilityPaperAddEditService
                    .updateBccDTO(AppUtils.trim(fpBccDTO))
                    .subscribe((res: any) => {
                      this.facilityPaperAddEditService.addEditComment(
                        AppUtils.trim(fpCommentDTO),
                      );

                      if (
                        approveStatus == this.bccWorkFlowStatusConst.APPROVED
                      ) {
                        //save customer covenants toe finacal
                        this.facilityPaperAddEditService.getCovenantResponse();
                      }

                      this.router.navigate(["/committee-paper/dashboard"]);
                    });
                });
            }
          });
        }
      });
  }

  showBCCEntererButtons(facilityPaper: any) {
    let fpBccList = this.facilityPaper.fpBccList[0];
    if (fpBccList == undefined) {
      return false;
    } else {
      const coverPageExists = this.documentExists(
        facilityPaper.fpBccList[0].fpBccDocumentList,
        "Cover Page",
      );
      const meetingMinuteExists = this.documentExists(
        facilityPaper.fpBccList[0].fpBccDocumentList,
        "Meeting Minute",
      );

      // if (fpBccList.approveStatus == this.bccWorkFlowStatusConst.REJECTED && (coverPageExists && meetingMinuteExists)) {
      //   return true;
      // } else {
      //   if (fpBccList.approveStatus == this.bccWorkFlowStatusConst.DRAFT) {
      //     if (coverPageExists && meetingMinuteExists) {
      //       return true;
      //     } else {
      //       return false;
      //     }
      //   } else {
      //     return false;
      //   }
      // }

      if (coverPageExists) {
        if (fpBccList.approveStatus == this.bccWorkFlowStatusConst.DRAFT) {
          return true;
        } else if (
          fpBccList.approveStatus == this.bccWorkFlowStatusConst.REJECTED
        ) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }

  //check if a document with the given name exists
  documentExists(documentList: any[], documentName: string): boolean {
    return (
      documentList.find(
        (document) =>
          document.documentName === documentName &&
          document.status === "Active",
      ) !== undefined
    );
  }

  showBCCAuthorizerButtons(facilityPaper: any) {
    let fpBccList = facilityPaper.fpBccList[0];
    if (fpBccList.approveStatus == this.bccWorkFlowStatusConst.PENDING) {
      return true;
    } else {
      return false;
    }
  }

  submitWithPDFOld(facilityPaperStatusChangeRQ: any) {
    const element = document.getElementById("filter-full-paper-view-section");

    if (!element) {
      this.alertService.showToaster(
        "An error occurd. please try again later.",
        SETTINGS.TOASTER_MESSAGES.error,
      );
      return;
    }

    var pdfContainer = document.createElement("div");
    pdfContainer.className = "d-flex justify-content-center m-0 p-0";
    pdfContainer.innerHTML = element.innerHTML;

    const updatedContent = pdfContainer;

    updatedContent.querySelectorAll(".btn").forEach((item: Element) => {
      item.remove();
    });

    var pdf = new jsPDF({
      orientation: "p",
      unit: "mm",
      format: [400, 500],
      putOnlyUsedFonts: true,
      compress: true,
    });
    let fileUri: string = "";

    pdf
      .html(updatedContent, {
        callback: async function (pdf) {
          fileUri = pdf.output("datauristring");
        },
        windowWidth: element.offsetWidth,
        width: pdf.internal.pageSize.getWidth(),
        autoPaging: "slice",
        margin: [15, 0, 15, 0],
        fontFaces: [],
        html2canvas: {
          useCORS: true,
          svgRendering: true,
          letterRendering: true,
        },
      })
      .then(
        (res: any) => {
          if (this.bccFwdRecipients.length > 0) {
            var attachments: any[] = [
              {
                fileName:
                  this.facilityPaper && this.facilityPaper.fpRefNumber
                    ? this.facilityPaper.fpRefNumber
                    : "Facility Paper Document",
                fileDataUri: fileUri.replace(
                  "data:application/pdf;filename=generated.pdf;base64,",
                  "",
                ),
              },
            ];
            facilityPaperStatusChangeRQ = Object.assign(
              {},
              facilityPaperStatusChangeRQ,
              {
                attachments: attachments,
                bccAddresses: this.bccFwdRecipients,
              },
            );

            this.facilityPaperAddEditService.updateFacilityPaper(
              AppUtils.trim(facilityPaperStatusChangeRQ),
              true,
            );
          } else {
            this.alertService.showToaster(
              "An error occurd. please try again later.",
              SETTINGS.TOASTER_MESSAGES.error,
            );
          }
        },
        (err: any) => {
          this.alertService.showToaster(
            "An error occurd. please try again later.",
            SETTINGS.TOASTER_MESSAGES.error,
          );
        },
      );
  }

  submitWithPDF(facilityPaperStatusChangeRQ: any) {
    const element = document.getElementById("filter-full-paper-view-section");
    const clonedElement = element.cloneNode(true) as HTMLElement;
    if (!element) {
      this.alertService.showToaster(
        "An error occurd. please try again later.",
        SETTINGS.TOASTER_MESSAGES.error,
      );
      return;
    }

    clonedElement.querySelectorAll(".btn").forEach((item: Element) => {
      item.remove();
    });

    let fileName: string = `${this.facilityPaper.fpRefNumber}.pdf`;
    const preparedContent: string = this.prepareStyleProp(clonedElement);
    const options = {
      margin: [15, 0, 15, 0],
      filename: fileName,
      image: { type: "jpeg", quality: 0.98 },
      html2canvas: {
        scale: 1,
        useCORS: true,
        letterRendering: true,
      },
      jsPDF: {
        unit: "mm",
        format: "a4",
        orientation: "l",
        floatPrecision: "smart",
        hotfixes: ["px_scaling"],
      },
      pagebreak: {
        mode: ["avoid-all", "css", "legacy"],
      },
    };

    const worker = html2pdf().from(preparedContent).set(options).toPdf();
    let fileUri: string = "";
    worker
      .get("pdf")
      .then((pdf: any) => {
        const totalPages = pdf.internal.getNumberOfPages();

        for (let i = 1; i <= totalPages; i++) {
          pdf.setPage(i);
          pdf.setFontSize(10);
          pdf.text(
            `Page ${i} of ${totalPages}`,
            pdf.internal.pageSize.getWidth() - 30,
            pdf.internal.pageSize.getHeight() - 5,
          );
        }

        return worker.outputPdf("datauristring");
      })
      .then((dataUri: string) => {
        fileUri = dataUri;

        if (this.bccFwdRecipients.length > 0) {
          var attachments: any[] = [
            {
              fileName:
                this.facilityPaper && this.facilityPaper.fpRefNumber
                  ? this.facilityPaper.fpRefNumber
                  : "Facility Paper Document",
              fileDataUri: fileUri.replace(
                "data:application/pdf;filename=generated.pdf;base64,",
                "",
              ),
            },
          ];
          facilityPaperStatusChangeRQ = Object.assign(
            {},
            facilityPaperStatusChangeRQ,
            {
              attachments: attachments,
              bccAddresses: this.bccFwdRecipients,
            },
          );

          this.facilityPaperAddEditService.updateFacilityPaper(
            AppUtils.trim(facilityPaperStatusChangeRQ),
            true,
          );
        } else {
          this.alertService.showToaster(
            "BCC paper recipients not found.",
            SETTINGS.TOASTER_MESSAGES.error,
          );
        }
      })
      .catch((err: any) => {
        this.alertService.showToaster(
          "An error occurred. Please try again later.",
          SETTINGS.TOASTER_MESSAGES.error,
        );
      });
  }

  prepareStyleProp(updatedContent: any) {
    let classes: FontSizeProp[] = [
      { prop: ".pdf-content-heading", size: "18px" },
      {
        prop: ".pdf-font-heading",
        size: "16px",
      },
      {
        prop: ".pdf-font-bold",
        size: "16px",
      },
      {
        prop: ".pdf-font-text",
        size: "16px",
      },
      { prop: ".text-1", size: "16px" },
      { prop: ".text-2", size: "16px" },
      { prop: ".text-3", size: "16px" },
      { prop: "li", size: "16px" },
      { prop: "strong", size: "16px" },
      { prop: "td", size: "16px" },
      { prop: "th", size: "16px" },
      { prop: "span", size: "16px" },
      { prop: "p", size: "16px" },
      {
        prop: ".custom-pre-tag-with-spaces",
        size: "16px",
      },
    ];

    classes.forEach((item: FontSizeProp) => {
      updatedContent
        .querySelectorAll(item.prop)
        .forEach((element: HTMLElement) => {
          element.style.setProperty("font-size", item.size, "important");
          element.style.lineHeight = "1.4";
        });
    });

    updatedContent.querySelectorAll("th").forEach((element: HTMLElement) => {
      let propPadding = element.style.getPropertyValue("padding");
      if (!propPadding) {
        element.style.padding = "6px";
      }
    });

    updatedContent.querySelectorAll("td").forEach((element: HTMLElement) => {
      let propPadding = element.style.getPropertyValue("padding");
      let propTextAlign = element.style.getPropertyValue("text-align");
      if (!propPadding) {
        element.style.padding = "6px";
      }
      if (!propTextAlign) {
        element.style.textAlign = "left";
      }
    });

    updatedContent
      .querySelectorAll(".topic")
      .forEach((element: HTMLElement) => {
        element.style.marginTop = "0px";
        element.style.marginBottom = "15px";
      });

    return updatedContent;
  }

  isSameType() {
    var prevType: any =
      this.facilityPaper.isCommittee == Constants.yesNoConst.Y;

    return this.isCommittee == prevType;
  }

  isCommiteeSectionShow(status: any, user: any) {
    var show: boolean = false;

    var currentStatus: any = status;
    var currentUser: any = user;
    var currentUserWC: any =
      this.applicationService.getLoggedInUserUPMGroupCode();

    if (
      (currentStatus == Constants.facilityPaperStatusConst.DRAFT ||
        currentStatus == Constants.facilityPaperStatusConst.CANCEL ||
        currentStatus == Constants.facilityPaperStatusConst.IN_PROGRESS) &&
      (currentUserWC == 10 || currentUserWC == 20 || currentUserWC == 50) &&
      currentUser == this.applicationService.getLoggedInUserUserName() &&
      this.isNotRiskUser()
    ) {
      show = true;
    }

    return show;
  }

  updateFPType() {
    var emptyArr: any[] = [];
    var data: any = {
      ...this.facilityPaper,
      fpDocumentDTOList: emptyArr,
      fpUpcSectionDataDTOList: emptyArr,
      facilityPaperID: this.facilityPaper.facilityPaperID,
      isCommittee: this.isCommittee ? "Y" : "N",
    };
    this.facilityPaperAddEditService.updateFacilityPaperType(data);
  }

  getMillionValue(value: any) {
    return AppUtils.getMillionValue(value);
  }

  isLegalOfficer(): boolean {
    if (
      this.loggedInUserWorkClass ==
      Constants.applicationSecurityWorkClass.LO.toString()
    ) {
      return true;
    } else {
      return false;
    }
  }

  getSecurityDocumentationElementList(approvedFacilityDTOList) {
    this.facilityPaperAddEditService
      .getSecurityDocumentElements(this.facilityPaper.facilityPaperID)
      .then((data: any) => {
        if (!_.isEmpty(data)) {
          for (var item of approvedFacilityDTOList) {
            let fpDocumentElementDTOList = [];
            if (item.isNew == "Y") {
              fpDocumentElementDTOList = _.filter(
                data,
                (element) =>
                  element.creditFacilityName ==
                  item.creditFacilityTemplateDTO.creditFacilityName,
              );
              this.fpDocumentElementDTOListAll.push({
                facilityName: item.creditFacilityTemplateDTO.facilityTypeName,
                creditFacilityName:
                  item.creditFacilityTemplateDTO.creditFacilityName,
                facilityID: item.facilityID,
                facilityRefCode: item.facilityRefCode,
                facilityAmount: item.facilityAmount,
                displayOrder: item.displayOrder,
                creditFacilityTemplateID: item.creditFacilityTemplateID,
                fpDocumentElementDTOList: fpDocumentElementDTOList,
              });
            }
          }
        }
      });
  }

  showAttachmentTab() {
    const loggedInUserId = this.applicationService.getLoggedInUserUserID();

    if (this.facilityPaper.isCommittee === "Y") {
      const userDivisionMatches =
        this.facilityPaper.branchCode ===
        this.applicationService.getLoggedInUserDivCode();
      const userHasHighWorkClass =
        parseInt(this.loggedInUserWorkClass, 10) >=
        Constants.applicationSecurityWorkClass.CM;
      const isUserInvolved =
        !_.isEmpty(this.involeduserList) &&
        this.involeduserList.some(
          (user) => user.assignUserID === loggedInUserId,
        );
      const userDivAndFPCreatedUserDivMatches =
        this.facilityPaper.createdUserBranchCode ===
        this.applicationService.getLoggedInUserDivCode();

      // Check the BCC list
      const bccList = this.facilityPaper.fpBccList[0];
      const hasDocuments =
        bccList &&
        bccList.fpBccDocumentList.length > 0 &&
        bccList.approveStatus === "APPROVED";

      // const hasDocuments = bccList && bccList.fpBccDocumentList.length > 0 &&
      //                      (bccList.approveStatus === "APPROVED" || bccList.approveStatus === "REJECTED");
      // console.log("userDivisionMatches", userDivisionMatches);
      // console.log("userHasHighWorkClass", userHasHighWorkClass);
      // console.log("isUserInvolved", isUserInvolved);
      // console.log("hasDocuments", hasDocuments);

      // Return true only if all conditions are satisfied
      if (
        (userDivisionMatches ||
          userHasHighWorkClass ||
          isUserInvolved ||
          userDivAndFPCreatedUserDivMatches) &&
        hasDocuments
      ) {
        // console.log("true.");
        return true;
      } else {
        return false;
      }
    }

    return false;
  }

  loadDATable($event: any) {
    if ($event) {
      $event.preventDefault();
      $event.stopPropagation();
    }

    this.modalRef = this.mdbModalService.show(FpViewDasComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-100-p audit-modal-margin-center modal-height-DA",
      containerClass: "",
      animated: true,
      data: {
        content: { facilityPaper: this.facilityPaper },
      },
    });
  }

  enableCommitteeButtons() {
    let selectedCommitteeInquiryType = this.urlEncodeService.decode(
      this.selectedCommitteeInquiryType,
    );
    let committeePaperList = this.facilityPaper.committeePaperList;
    let committeeStatusHistoryList;
    let committeeStatusHistory;
    if (committeePaperList != undefined && committeePaperList.length != 0) {
      this.committeePaper = _.filter(
        committeePaperList,
        (committeePaper) =>
          committeePaper.currentCommitteePaperStatus !=
          Constants.committeePaperStatusConst.RETURNED,
      )[0];
    }
    this.facilityPaperAddEditService
      .getCommitteeButtonEnableData()
      .then((data: any) => {
        if (data > 0) {
          if (selectedCommitteeInquiryType == "COM_INQ") {
            this.isCommitteeButtonsEnable = false;
          } else {
            this.isCommitteeButtonsEnable = true;
          }
        } else {
          this.isCommitteeButtonsEnable = false;
        }
      });
  }

  getCustomerCovenantAddList() {
    const covenantList = {
      RequestUUID: sessionStorage.getItem("facilityPaperRefID"),
      type: "C",
    };
    this.facilityPaperAddEditService
      .getCovenantListFromFinacle(covenantList)
      .subscribe((data) => {
        this.customerCovenantList = data;
      });
  }

  getFacilityCovenantAddList() {
    const covenantList = {
      RequestUUID: sessionStorage.getItem("facilityPaperRefID"),
      type: "A",
    };
    this.facilityPaperAddEditService
      .getCovenantListFromFinacle(covenantList)
      .subscribe((data) => {
        this.facilityCovenantList = data;
      });
  }

  getCustomerCovenantList() {
    this.facilityPaperAddEditService
      .getCustomerCovenantListByFpRefNumber()
      .subscribe((data: any) => {
        this.covenantList = data;
      });
  }

  isPendingDocsForauthorize(): boolean {
    let fpBcc = this.facilityPaper.fpBccList[0];
    if (
      fpBcc.approveStatus == this.bccWorkFlowStatusConst.APPROVED &&
      fpBcc.fpBccDocumentList != null
    ) {
      // Check if any object has isApproved set to "N"
      const hasPendingDocs = fpBcc.fpBccDocumentList.some(
        (doc) => doc.isApproved === "N",
      );

      return hasPendingDocs;
    }
    return false;
  }

  getSelectedYearType(type: any) {
    if (type) {
      return this.financialYearTypes[1];
    } else {
      return this.financialYearTypes[0];
    }
  }

  onYearSelectCheckboxChange() {
    if (this.isFinancialYear) {
      this.isFinancialYear = false;
    } else {
      this.isFinancialYear = true;
    }
    this.selectedYear = this.getSelectedYearType(this.isFinancialYear);
    // console.log(this.selectedYear)
  }

  isSameYearType() {
    var prevIsFinancialYearType: any = this.facilityPaper.isFinancialYear
      ? this.facilityPaper.isFinancialYear == Constants.yesNoConst.Y
      : false;

    return this.isFinancialYear == prevIsFinancialYearType;
  }

  updateYearType() {
    var emptyArr: any[] = [];
    var data: any = {
      ...this.facilityPaper,
      fpDocumentDTOList: emptyArr,
      fpUpcSectionDataDTOList: emptyArr,
      facilityPaperID: this.facilityPaper.facilityPaperID,
      isFinancialYear: this.isFinancialYear ? "Y" : "N",
    };

    let heading = "YEAR CHANGE CONFIRMATION";
    this.modalRefForwardConfirmation = this.mdbModalService.show(
      ConfirmationDialogComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center",
        containerClass: "",
        animated: true,
        data: {
          heading: heading,
          approveRejectType: status,
          message: `This Will delete saved guarantee,import and export values. Do you want to continue ?`,
        },
      },
    );

    this.modalRefForwardConfirmation.content.action.subscribe(
      (approveStatus) => {
        if (approveStatus) {
          this.facilityPaperAddEditService.updateFacilityPaperYearType(data);
        } else {
          this.isFinancialYear = !this.isFinancialYear;
        }
      },
    );
  }

  isShowInsuranceExpireStatus(facilityPaper) {
    let reqbody = { facilityPaperId: facilityPaper.facilityPaperID };
    let expireStatus: boolean = false;
    this.facilityPaperAddEditService
      .hasExpiredInsurance(reqbody)
      .then((response) => {
        expireStatus =
          response && response.result != null && response.result != undefined
            ? response.result
            : response;

        return expireStatus;
      })
      .catch((err) => {
        this.alertService.showToaster("please contact administrator", "ERROR");
        return false;
      });
  }

  getMeetingDate() {
    if (this.facilityPaper.approvedDate) {
      return moment(this.facilityPaper.approvedDate).format("YYYY-MM-DD");
    }
    if (this.facilityPaper.rejectedDate) {
      return moment(this.facilityPaper.rejectedDate).format("YYYY-MM-DD");
    }
    return null;
  }

  isEditEnabled() {
    return (
      this.applicationService.getLoggedInUserUPMGroupCode() <= 50 &&
      this.facilityPaper.currentAssignUser ===
        this.applicationService.getLoggedInUserUserName()
    );
  }

  isSameESGStatus() {
    return this.facilityPaper.isESGPaper === this.getCharValue(this.isEsgPaper);
  }

  getCharValue(boolVal: boolean) {
    if (boolVal) {
      return Constants.yesNoConst.Y;
    }
    return Constants.yesNoConst.N;
  }

  isRiskScoreSelected() {
    if (this.selectedRiskCategories) {
      return this.selectedRiskCategories.some(
        (d: any) => d.score && d.score !== null,
      );
    }
    return false;
  }

  isCompleteMandatoryAnnex() {
    let unCompleteAnnexes: any[] = [];

    this.mandatoryAnnexureList.forEach((element: any) => {
      let mandatoryAnnexure: any = this.completedAnnexures.find(
        (a: any) => a.annexureId == element.annexureId,
      );

      if (
        mandatoryAnnexure === undefined ||
        mandatoryAnnexure === null ||
        mandatoryAnnexure.questions.some(
          (q: any) => q.answers.length == 0 && !q.userAnswer,
        )
      ) {
        unCompleteAnnexes.push(element);
      }
    });

    return unCompleteAnnexes.length == 0;
  }

  showESGForwardButton(status: any) {
    if (
      this.isNotRiskUser() &&
      this.isEqualLoginAndAssignUser() &&
      this.applicationService.getLoggedInUserUPMGroupCode() == 50 &&
      (status === this.facilityStatusConst.DRAFT ||
        status === this.facilityStatusConst.IN_PROGRESS ||
        status === this.facilityStatusConst.CANCEL)
    ) {
      return true;
    } else {
      return false;
    }
  }

  isNotRiskUser() {
    return (
      this.applicationService.getLoggedInUserDivCode() &&
      this.applicationService.getLoggedInUserDivCode().toString() !==
        SETTINGS.ESG_DIV_CODE.toString()
    );
  }

  isBoardPaper(): boolean {
    return (
      this.facilityPaper.isCommittee === Constants.yesNoConst.Y &&
      this.facilityPaper.upcTemplateName === "Board Paper"
    );
  }

  handleWalletShareUpdate(isUpdate: boolean) {
    if (isUpdate) {
      this.selectedTabIndex = this.walletShareTabIndex;
    }
  }

  isESGFwdCheck(status: any) {
    let wc: number = this.applicationService.getLoggedInUserUPMGroupCode()
      ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
      : 0;
    return (
      wc <= 50 &&
      this.isNotRiskUser() &&
      this.isEsgPaper &&
      (status == Constants.facilityPaperStatusConst.DRAFT ||
        status == Constants.facilityPaperStatusConst.IN_PROGRESS)
    );
  }

  isSecurityDocumentOldVersionEnabled() {
    if (this.securityDocumentVersion === 1) {
      if (
        this.facilityPaper !== null &&
        this.facilityPaper.currentFacilityPaperStatus ==
          Constants.facilityPaperStatusConst.APPROVED
      ) {
        this.documentationTabEnableDivCode = this.cacheService.getData(
          Constants.masterDataKey.CAS_SECURITY_DOCUMENT_SUBMIT_DIV,
        );
        this.loggedInUserDivCode =
          this.applicationService.getLoggedInUserDivCode();
        if (
          this.documentationTabEnableDivCode == this.loggedInUserDivCode ||
          this.facilityPaper.branchCode == this.loggedInUserDivCode
        ) {
          return true;
        }
      }
    }

    return false;
  }

  isCCPUEnter() {
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();
    let isCCPU =
      this.applicationService.getLoggedInUserDivCode() ===
      SDConstants.CCPU_DIV_CODE;

    return isCCPU && loggedInUserWorkClass <= 50;
  }

  showDocumentationTab() {
    if (this.facilityPaper) {
      let branchCode: any = this.facilityPaper.branchCode;
      let loggedInUserDivCode: any =
        this.applicationService.getLoggedInUserDivCode();
      let loggedInUserWorkClass: any =
        this.applicationService.getLoggedInUserUPMGroupCode();
      let currentFacilityPaperStatus: any =
        this.facilityPaper.currentFacilityPaperStatus;
      let isCCDU = loggedInUserDivCode === SDConstants.CCDU_DIV_CODE;
      let isCCPU = loggedInUserDivCode === SDConstants.CCPU_DIV_CODE;

      if (!isCCDU && !isCCPU && parseInt(loggedInUserWorkClass) <= 50) {
        return (
          currentFacilityPaperStatus ===
            Constants.facilityPaperStatusConst.APPROVED &&
          branchCode === loggedInUserDivCode
        );
      }

      return (
        this.isSecurityDocumentOldVersionEnabled() ||
        this.securityDocumentVersion === 2
      );
    }
    return false;
  }

  isDocumentStatusChanged(assignUser: any, facilityPaperStatus: any) {
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();
    let isCCPU =
      this.applicationService.getLoggedInUserDivCode() ===
      SDConstants.CCPU_DIV_CODE;

    if (
      facilityPaperStatus === Constants.facilityPaperStatusConst.IN_PROGRESS ||
      facilityPaperStatus === Constants.facilityPaperStatusConst.CANCEL
    ) {
      return isCCPU &&
        assignUser.divCode === SDConstants.CCPU_DIV_CODE &&
        parseInt(loggedInUserWorkClass) <= 50 &&
        parseInt(loggedInUserWorkClass) <
          parseInt(assignUser.assignUserUpmGroupCode)
        ? Constants.yesNoConst.Y
        : Constants.yesNoConst.N;
    }

    return Constants.yesNoConst.N;
  }

  checkDocumentationCompletion(paperStatus: any) {
    let isNotCompleted: boolean = false;

    let isCCPU =
      this.applicationService.getLoggedInUserDivCode() ===
      SDConstants.CCPU_DIV_CODE;
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();

    if (
      paperStatus === Constants.facilityPaperStatusConst.IN_PROGRESS ||
      paperStatus === Constants.facilityPaperStatusConst.CANCEL
    ) {
      if (isCCPU && parseInt(loggedInUserWorkClass) === 50) {
        isNotCompleted = this.sdCount.submittedCount !== 0;
      }
    } else {
      if (
        (isCCPU || parseInt(loggedInUserWorkClass) >= 71) &&
        [
          Constants.facilityPaperStatusConst.IN_PROGRESS,
          Constants.facilityPaperStatusConst.APPROVED,
        ].includes(paperStatus)
      ) {
        isNotCompleted = this.sdCount.submittedCount !== 0;
      }
    }

    return isNotCompleted;
  }

  loadUPCData(tabIndex: number) {
    if (
      this.facilityPaper &&
      (this.facilityPaper.fpUpcSectionDataDTOList === null ||
        this.facilityPaper.fpUpcSectionDataDTOList.length === 0)
    ) {
      this.facilityPaperAddEditService
        .getUPCByFacilityPaper(this.facilityPaper)
        .then((data: any[]) => {
          this.facilityPaper = {
            ...this.facilityPaper,
            fpUpcSectionDataDTOList: data !== null ? data : [],
          };

          setTimeout(() => {
            this.selectedTabIndex = tabIndex;
          }, 500);
        })
        .catch((err: any) => {
          this.selectedTabIndex = tabIndex;
        });
    } else {
      this.selectedTabIndex = tabIndex;
    }
  }

  isAllowedPaper() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.REJECTED
    );
  }

  showDeviationTab() {
    let loggedInUserWorkClass: any =
      this.applicationService.getLoggedInUserUPMGroupCode();
    return (
      this.isAllowedPaper() &&
      parseInt(loggedInUserWorkClass) <= 50 &&
      this.isEqualLoginAndAssignUser()
    );
  }

  canApproveOrForwordCompFacility(facilityPaper: any) {
    let creditFacilityList = facilityPaper.facilityDTOList || [];
    let fpUpcSectionDataDTOList = facilityPaper.fpUpcSectionDataDTOList || [];
    let allSecurities: any[] = [];
    creditFacilityList.forEach((facility: any) => {
      facility.facilitySecurityDTOList.forEach((security: any) => {
        allSecurities.push(security);
      });
    });

    let returnObj = { isInvalid: false, messages: [] };

    if (creditFacilityList.length == 0) {
      returnObj.isInvalid = true;
      returnObj.messages.push("Facilities");
    }

    if (allSecurities.length == 0) {
      returnObj.isInvalid = true;
      returnObj.messages.push("Securities");
    }

    // if (fpUpcSectionDataDTOList.length == 0) {
    //   returnObj.isInvalid = true;
    //   returnObj.messages.push("UPC Comments");
    // }

    let customerRatingsList = [];
    var ratingList: any[] = [];
    facilityPaper.casCustomerDTOList.forEach((customer: any) => {
      ratingList.push(customer.customerRatingsDTOList);
    });

    customerRatingsList = _.filter(ratingList || [], (customerRatings) => {
      return customerRatings.length === 0;
    });

    if (customerRatingsList.length != 0) {
      returnObj.isInvalid = true;
      returnObj.messages.push("Customer Ratings");
    }

    var isSecuritySummaryAvailable: boolean = facilityPaper.fpSecuritySummeryDTO
      ? true
      : false;

    if (this.isCommittee) {
      if (isSecuritySummaryAvailable) {
        var isSecuritySummaryGroup: boolean =
          facilityPaper.fpSecuritySummeryDTO.facilitySecuritySummaryType ==
          Constants.facilitySecuritySummaryTypeConst.GROUP;

        if (isSecuritySummaryGroup) {
          if (
            facilityPaper.fpSecuritySummeryDTO.groupTotal !=
            this.getMillionValue(facilityPaper.groupTotalExposureNew)
          ) {
            returnObj.isInvalid = true;
            returnObj.messages.push(
              "Valid group exposure value and security summary group grand total value.",
            );
          }
        } else {
          if (
            facilityPaper.fpSecuritySummeryDTO.companyTotal !=
            this.getMillionValue(facilityPaper.totalExposureNew)
          ) {
            returnObj.isInvalid = true;
            returnObj.messages.push(
              "Valid company exposure value and security summary company grand total value.",
            );
          }
        }
      } else {
        returnObj.isInvalid = true;
        returnObj.messages.push("Security Summary");
      }
    }
    return returnObj;
  }

  showRequiredSections(requiredObj: any, actionType: any) {
    if (requiredObj.isInvalid) {
      let message = "";

      if (requiredObj.messages.length > 1) {
        let last = requiredObj.messages.pop();
        message = requiredObj.messages.join(" ,") + " and " + last;
      } else {
        message = requiredObj.messages.join(" ,");
      }

      this.modalRef = this.mdbModalService.show(InformationDialogComponent, {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-30-p modal-margin-center ",
        containerClass: "",
        animated: true,
        data: {
          heading:
            actionType == this.facilityStatusConst.APPROVED
              ? "Facility Paper Approving"
              : "Facility Paper Forwarding",
          message: "This paper does not have " + message,
          showConfirm: false,
        },
      });
    }
  }
}

export interface FontSizeProp {
  prop: string;
  size: string;
}
