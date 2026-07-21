import { Component, OnDestroy, OnInit } from "@angular/core";
import { CoveringApprovalSharedService } from "../../services/covering-approval-shared.service";
import * as moment from "moment";
import { DatePipe } from "@angular/common";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Constants } from "src/app/core/setting/constants";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { CoveringApprovalService } from "../../services/covering-approval.service";
import { AppUtils } from "src/app/shared/app.utils";
import { Router } from "@angular/router";
import { CommonForwardComponent } from "src/app/shared/components/common-forward/common-forward.component";
import * as _ from "lodash";
import { BehaviorSubject, Subject, Subscription } from "rxjs";
import { CovAccountStatisticsComponent } from "../cov-personal-customer-stat-wrapper/components/cov-account-statistics/cov-account-statistics.component";
import { CovAdvancesDetailsComponent } from "../cov-personal-customer-stat-wrapper/components/cov-advances-details/cov-advances-details.component";
import { CovDepositsDetailsComponent } from "../cov-personal-customer-stat-wrapper/components/cov-deposits-details/cov-deposits-details.component";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { CacheService } from "src/app/core/service/data/cache.service";
import { CovAccountsDetailsComponent } from "../cov-personal-customer-stat-wrapper/components/cov-accounts-details/cov-accounts-details.component";
import { style } from "@angular/animations";
import { CommentWithViewOptionsDialogComponent } from "src/app/shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component";
import { CovSubmitComponent } from "../cov-submit/cov-submit.component";

@Component({
  selector: "app-ca-request-approval",
  templateUrl: "./ca-request-approval.component.html",
  styleUrls: ["./ca-request-approval.component.scss"],
})
export class CaRequestApprovalComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_COV_ID)
  selectedCovID;
  toDate = moment().subtract(1, "months").endOf("month").format("DD-MM-YYYY");
  fromDate = moment()
    .subtract(12, "months")
    .startOf("month")
    .format("DD-MM-YYYY");
  customerDetails: any = {};

  tableColumns: any = [
    "Date",
    "Customer Name",
    "NIC/BR",
    "Ref No",
    "Account Number",
    "Branch",
    "Account Manager",
    "Cheque Number",
    "Transaction Amount",
    " ",
  ];
  transactionDetails: any;
  commentDetails: any;
  basicInfoDetails: any;
  covAppStatusHistory: any;
  date: string;
  modalRef: MDBModalRef;
  covStatusConst = Constants.covCurrentStatusConst;
  covStatusChangeHeading = Constants.covStatusChangeHeading;
  covActionStatus = Constants.covActionStatus;
  coveringApprovalStatusConst = Constants.coveringApprovalStatusConst;

  allBranches = [];
  results: Subject<any>;

  coveringApprovalForm: any = {};

  selectAll: boolean = false;
  details: any[] = [];
  selectedBasicInformationMap = [];
  onCOVFormChangeSub = new Subscription();
  onCOVFormChangeSubs = new Subscription();
  onCoveringApprovalChangeSub = new Subscription();
  onCoveringApprovalChangesSub = new Subscription();

  coveringApproval: any = {};
  upmDetails: any = {};
  testPending: any = {};

  accessLevelOfCurrentAssignUser: any;

  constructor(
    private coveringApprovalSharedService: CoveringApprovalSharedService,
    private datePipe: DatePipe,
    private mdbModalService: MDBModalService,
    public applicationService: ApplicationService,
    private coveringApprovalService: CoveringApprovalService,
    private router: Router,
    private cacheService: CacheService
  ) { }

  ngOnInit() {
    this.allBranches = this.cacheService.getData(
      Constants.masterDataKey.CAS_BRANCHES
    );
    this.results = new BehaviorSubject(this.allBranches);


    this.onCoveringApprovalChangeSub =
      this.coveringApprovalService.onCoveringApprovalChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.coveringApproval = data;
            if (
              this.coveringApproval.covAppBasicInfoDTOList &&
              this.coveringApproval.covAppBasicInfoDTOList.length > 0
            ) {
              this.basicInfoDetails =
                this.coveringApproval.covAppBasicInfoDTOList[0];

              this.customerDetails.cumm =
                this.basicInfoDetails.customerFinancialID;
              this.customerDetails.accno = this.basicInfoDetails.accountNumber;
              this.customerDetails.userId =
                this.applicationService.getLoggedInUserUserID();
              this.customerDetails.aduser =
                this.applicationService.getLoggedInUserUserName();
              this.customerDetails.refId =
                this.applicationService.getLoggedInUserUserName();
              this.customerDetails.fromdate = this.fromDate;
              this.customerDetails.todate = this.toDate;
              this.customerDetails = AppUtils.trim(this.customerDetails);
       

              this.applicationService.getUserUPMDetailsList({
                "userID": this.basicInfoDetails.accounManager,
                "appCode": 'CAS'
              }).subscribe((response: any) => {
                if (response && response.userID != null) {
                  // Response is valid, and userID is not null
                  this.upmDetails = response;
                  this.upmDetails.adUserID = response.firstName +' '+ response.lastName;
                  //console.log("important details", response);
              
                } else if (response && response.userID === null) {
                  // Response is valid but userID is null
                  this.upmDetails = { 
                    userID: this.basicInfoDetails.accounManager ,//040mgr
                    adUserID: this.basicInfoDetails.accounManager,//040mgr
                    divCode: this.basicInfoDetails.accounManager.replace(/MGR$/, ''),//040
                    solID: this.basicInfoDetails.accounManager.replace(/MGR$/, ''),//040

                  };
                  //console.log("this.upmDetails.userID", this.upmDetails.divCode);
              
                } else {
                  // Response itself is null
                  //console.log("No response received: response is null");
                }
              });
            } else {
              console.warn("covAppBasicInfoDTOList is empty or undefined");
            }
          } else {
            this.coveringApproval =
              this.coveringApprovalSharedService.getCoveringApprovalForm();
            if (
              this.coveringApproval.covAppBasicInfoDTOList &&
              this.coveringApproval.covAppBasicInfoDTOList.length > 0
            ) {
              this.basicInfoDetails =
                this.coveringApproval.covAppBasicInfoDTOList[0];
              this.customerDetails.cumm =
                this.basicInfoDetails.customerFinancialID;
              this.customerDetails.accno = this.basicInfoDetails.accountNumber;
              this.customerDetails.userId =
                this.applicationService.getLoggedInUserUserID();
              this.customerDetails.aduser =
                this.applicationService.getLoggedInUserUserName();
              this.customerDetails.refId =
                this.applicationService.getLoggedInUserUserName();
              this.customerDetails.fromdate = this.fromDate;
              this.customerDetails.todate = this.toDate;
              this.customerDetails = AppUtils.trim(this.customerDetails);
            } else {
              console.warn("covAppBasicInfoDTOList is empty or undefined");
            }
          }
        }
      );

    if (this.isEqualLoginAndAssignUser()) {
      this.onCoveringApprovalChangesSub =
        this.coveringApprovalService.onCoveringApprovalPendingChange.subscribe(
          (data: any) => {
            if (!_.isEmpty(data)) {
              this.coveringApprovalForm = data;
            }
          }
        );
    }

    // this.onCoveringApprovalChangeSub = this.coveringApprovalService.onCoveringApprovalPendingChange
    // .subscribe(data => {
    //   this.testPending = data;
    //   console.log("testPending",this.testPending);
    // })

    let testAccountNumber = Object.assign({
      accountNumber: this.basicInfoDetails.accountNumber,
    });

    //   this.coveringApprovalService.getPendingCoveringApprovals(testAccountNumber);
  }

  ngOnDestroy(): void {
    this.selectedCovID = null;
    this.onCoveringApprovalChangeSub.unsubscribe();
    this.coveringApprovalSharedService.clearCoveringApprovalForm();
  }
  formatDate(date: string): string {
    return this.datePipe.transform(date, "mediumDate");
  }
  selectAllRecords() {
    this.details.forEach((detail) => (detail.selected = this.selectAll));
  }

  //Unauthorized or Dispute
  async UnauthCOV($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    let returnUserList: any;
    let filterUserList: any;
    let loggedInUserWorkFlowRQ = {
      loggedInUserUpmGroupCode:
        this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
    };
    filterUserList = this.applicationService
      .getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(
        loggedInUserWorkFlowRQ
      )
      .then((response) => {

        returnUserList = response;
      });
    this.modalRef = this.mdbModalService.show(CommonForwardComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        showUsersOnlyOption: true,
        showDivisionOnlyOption: true,
        heading: "Unauthorized Covering Approval",
        actionMessage: `${this.covStatusChangeHeading[status]}`,
        isForward: status == this.covStatusConst.IN_PROGRESS,
        isReturn: status == this.covStatusConst.CANCEL,
        commentCacheKey:
          this.coveringApproval.covAppRefNumber +
          this.covActionStatus[status] +
          this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          branchCode: this.basicInfoDetails.branchCode,
          createdUser: this.basicInfoDetails.createdBy,
          currentAssignUser: this.coveringApproval.currentAssignUser,
          covAppId: this.coveringApproval.covAppId,
          relatedDivCodes: [this.basicInfoDetails.branchCode],
        },
      },
    });
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let covStatusChangeRQ = {
          covAppId: this.basicInfoDetails.covAppId,
          currentStatus: status,
          currentAssignUserId: data.assignedUser.userID,
          covAppRefNumber: this.coveringApproval.covAppRefNumber,
          actionMessage: data.actionMessage,
          forwardType: data.forwardType,
          isAuthorized: false,
          covAppCommentDTO: {
            userComment: data.remarkData.comment,
            createdUserID: data.remarkData.createdUserID,
            createdUser: data.remarkData.createdUser,
            createdUserDisplayName: data.remarkData.createdUserDisplayName,
            createdUserDivCode: data.remarkData.createdUserDivCode,
            createdUserUpmCode: data.remarkData.createdUserUpmCode,
            isUsersOnly: "N",
            isDivisionOnly: "N",
            isPublic: "Y",
          },
        };
        if (data.assignedUser) {
          covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
            assignUserDisplayName: data.assignedUser.assignUserDisplayName,
            currentAssignUserAD: data.assignedUser.adUserID,
            currentAssignUserDivCode: data.assignedUser.divCode,
            assignUserUpmId: data.assignedUser.userID,
            assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
            currentAssignUser: data.assignedUser.adUserID,
            updatedUserId: this.applicationService.getLoggedInUserUserID()
          });
        } else {
          covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
            assignUserID: null,
            assignADUserID: null,
            assignUser: null,
            assignUserDisplayName: null,
            assignUserUpmID: null,
            assignedUserDivCode: null,
            assignUserUpmGroupCode: null,
          });
        }
        this.coveringApprovalService
          .updateCOVStatus(AppUtils.trim(covStatusChangeRQ))
          .subscribe((res: any) => {
            this.router.navigate(["/covering-approval/dashboard"]);
          });
      }
    });
  }

  //submit 
  async submitCOV($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    this.modalRef = this.mdbModalService.show(CovSubmitComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        showUsersOnlyOption: true,
        showDivisionOnlyOption: true,
        coveringApproval: this.coveringApproval,
        heading:
          `${this.covStatusChangeHeading[status]}` + " Covering Approval",
        actionMessage: `${this.covStatusChangeHeading[status]}`,
        isForward: status == this.covStatusConst.IN_PROGRESS,
        isReturn: status == this.covStatusConst.CANCEL,
        commentCacheKey:
          this.coveringApproval.covAppRefNumber +
          this.covActionStatus[status] +
          this.applicationService.getLoggedInUserUserID(),
        content: {
          branchCode: this.basicInfoDetails.branchCode,
          createdUser: this.basicInfoDetails.createdBy,
          currentAssignUser: this.coveringApproval.currentAssignUser,
          covAppId: this.coveringApproval.covAppId,
          relatedDivCodes: [this.basicInfoDetails.branchCode],
        },
      },
    });
    this.modalRef.content.submitComment.subscribe((comment: string) => {
      let covStatusChangeRQ = {
        covAppId: this.basicInfoDetails.covAppId,
        currentStatus: status,
        currentAssignUserId: this.upmDetails.userID,
        covAppRefNumber: this.coveringApproval.covAppRefNumber,
        actionMessage: "Forwarded to "+ this.upmDetails.adUserID,
        forwardType: "DIRECT_USER",
        isAuthorized: false,
        covAppCommentDTO: {
          userComment: comment,
          createdUserId: this.applicationService.getLoggedInUserUserID(),
          createdUser: this.applicationService.getLoggedInUserUserName(),
          createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          isUsersOnly: "N",
          isDivisionOnly: "N",
          isPublic: "Y",
        },
      };
      
      covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
        assignUserDisplayName: this.upmDetails.adUserID,
        currentAssignUserAD: this.upmDetails.adUserID,
        currentAssignUserDivCode: this.upmDetails.divCode,
        assignUserUpmId: this.upmDetails.userID,
        assignUserUpmGroupCode: "50",
        currentAssignUser: this.upmDetails.adUserID,
        updatedUserId: this.applicationService.getLoggedInUserUserID()
      });
      // console.log("upm details from submit",this.upmDetails);
      // console.log("AppUtils.trim(covStatusChangeRQ)",AppUtils.trim(covStatusChangeRQ));
      this.coveringApprovalService
        .updateCOVStatus(AppUtils.trim(covStatusChangeRQ))
        .subscribe((res: any) => {
          this.router.navigate(["/covering-approval/dashboard"]);
        });
  
    })
    
; 
  }

  //forward
  async changeStatusCOV($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    let returnUserList: any;
    if (status === this.covStatusConst.CANCEL) {
      returnUserList = await this.coveringApprovalService.getCOVReturnUsersList(
        
        this.basicInfoDetails.covAppId
      );
      this.covStatusChangeHeading[status] = "Return";
    }


    this.modalRef = this.mdbModalService.show(CommonForwardComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        showUsersOnlyOption: true,
        showDivisionOnlyOption: true,
        heading:
          `${this.covStatusChangeHeading[status]}` + " Covering Approval",
        actionMessage: `${this.covStatusChangeHeading[status]}`,
        isForward: status == this.covStatusConst.IN_PROGRESS,
        isReturn: status == this.covStatusConst.CANCEL,
        commentCacheKey:
          this.coveringApproval.covAppRefNumber +
          this.covActionStatus[status] +
          this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          branchCode: this.basicInfoDetails.branchCode,
          createdUser: this.basicInfoDetails.createdBy,
          currentAssignUser: this.coveringApproval.currentAssignUser,
          covAppId: this.coveringApproval.covAppId,
          relatedDivCodes: [this.basicInfoDetails.branchCode],
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let covStatusChangeRQ = {
          covAppId: this.basicInfoDetails.covAppId,
          currentStatus: status,
          currentAssignUserId: data.assignedUser.userID,
          covAppRefNumber: this.coveringApproval.covAppRefNumber,
          actionMessage: data.actionMessage,
          forwardType: data.forwardType,
          isAuthorized: false,
          covAppCommentDTO: {
            userComment: data.remarkData.comment,
            createdUserID: data.remarkData.createdUserID,
            createdUser: data.remarkData.createdUser,
            createdUserDisplayName: data.remarkData.createdUserDisplayName,
            createdUserDivCode: data.remarkData.createdUserDivCode,
            createdUserUpmCode: data.remarkData.createdUserUpmCode,
            isUsersOnly: "N",
            isDivisionOnly: "N",
            isPublic: "Y",
          },
        };
        if (data.assignedUser) {
          covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
            assignUserDisplayName: data.assignedUser.assignUserDisplayName,
            currentAssignUserAD: data.assignedUser.adUserID,
            currentAssignUserDivCode: data.assignedUser.divCode,
            assignUserUpmId: data.assignedUser.userID,
            assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
            currentAssignUser: data.assignedUser.adUserID,
            updatedUserId: this.applicationService.getLoggedInUserUserID()
          });
        } else {
          covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
            assignUserID: null,
            assignADUserID: null,
            assignUser: null,
            assignUserDisplayName: null,
            assignUserUpmID: null,
            assignedUserDivCode: null,
            assignUserUpmGroupCode: null,
          });
        }
       // console.log("forwardf",AppUtils.trim(covStatusChangeRQ))
        this.coveringApprovalService
          .updateCOVStatus(AppUtils.trim(covStatusChangeRQ))
          .subscribe((res: any) => {
            this.router.navigate(["/covering-approval/dashboard"]);
          });
      }
    });
  }
  //delete
  async declinePaper($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    let returnUserList: any;
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
          heading: "Delete Covering Approval",
          actionName: this.covActionStatus[status],
          commentCacheKey:
            this.coveringApproval.covAppRefNumber +
            this.covActionStatus[status] +
            this.applicationService.getLoggedInUserUserID(),
          showUsersOnlyOption: false,
          showDivisionOnlyOption: false,
          actionMessage: `${this.covStatusChangeHeading[status]}`,
          coveringApproval: this.coveringApproval,
          message: "Do you want to  this Application Form ?`",
          content: {
            returnUserList: returnUserList ? returnUserList : [],
            branchCode: this.basicInfoDetails.branchCode,
            createdUser: this.basicInfoDetails.createdBy,
            currentAssignUser: this.coveringApproval.currentAssignUser,
            relatedDivCodes: [this.basicInfoDetails.branchCode],
          },
        },
      }
    );
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let actionMessage =
          "Deleted by " + this.applicationService.getLoggedInUserDisplayName();
        let coveringApprovalUpdateDTO = {
          actionMessage: actionMessage,
          assignUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          assignUserUpmGroupCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          assignUserUpmId: this.applicationService.getLoggedInUserUserID(),
          isAuthorized: false,
          covAppCommentDTO: {
            userComment: data.comment,
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
          },
          covAppId: this.basicInfoDetails.covAppId,
          covAppRefNumber: this.coveringApproval.covAppRefNumber,
          currentAssignUser: this.applicationService.getLoggedInUserUserName(),
          currentAssignUserAD:
            this.applicationService.getLoggedInUserUserName(),
          currentAssignUserDivCode:
            this.applicationService.getLoggedInUserDivCode(),
          currentAssignUserId: this.applicationService.getLoggedInUserUserID(),
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserUserID(),
            updatedUserId: this.applicationService.getLoggedInUserUserID(),
          //
          currentStatus: status,
          forwardType: "DIRECT_USER"

          //updatedByUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
        };
        this.coveringApprovalService
          .updateCOVStatus(AppUtils.trim(coveringApprovalUpdateDTO))
          .subscribe((res: any) => { });
        this.router.navigate(["/covering-approval/dashboard"]);
      }
    });
  }
  async changeStatusCOVForSelected($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    const selectedDetails = this.details.filter((detail) => detail.selected);
    if (selectedDetails.length === 0) {
      alert("No records selected");
      return;
    }

    let returnUserList: any;
    this.modalRef = this.mdbModalService.show(CommonForwardComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        showUsersOnlyOption: true,
        showDivisionOnlyOption: true,
        heading:
          `${this.covStatusChangeHeading[status]}` + " Covering Approval",
        actionMessage: `${this.covStatusChangeHeading[status]}`,
        isForward: status == this.covStatusConst.IN_PROGRESS,
        isReturn: status == this.covStatusConst.RETURNED,
        commentCacheKey:
          this.coveringApproval.covAppRefNumber +
          this.covActionStatus[status] +
          this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          branchCode: this.basicInfoDetails.branchCode,
          createdUser: this.basicInfoDetails.createdBy,
          currentAssignUser: this.basicInfoDetails.assignUser,
          relatedDivCodes: [this.basicInfoDetails.branchCode],
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        selectedDetails.forEach((detail) => {
          let covStatusChangeRQ = {
            covAppId: detail.covAppId,
            currentStatus: status,
            currentAssignUserId: detail.currentAssignUserId,
            covAppRefNumber: detail.covAppRefNumber,
            actionMessage: data.actionMessage,
            forwardType: data.forwardType,
            covAppCommentDTO: {
              userComment: data.remarkData.comment,
              createdUserID: data.remarkData.createdUserID,
              createdUser: data.remarkData.createdUser,
              createdUserDisplayName: data.remarkData.createdUserDisplayName,
              createdUserDivCode: data.remarkData.createdUserDivCode,
              createdUserUpmCode: data.remarkData.createdUserUpmCode,
              isUsersOnly: "N",
              isDivisionOnly: "N",
              isPublic: "Y",
            },
          };

          if (data.assignedUser) {
            covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
              assignUserDisplayName: data.assignedUser.assignUserDisplayName,
              currentAssignUserAD: data.assignedUser.adUserID,
              currentAssignUserDivCode: data.assignedUser.divCode,
              assignUserUpmId: data.assignedUser.userID,
              assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
              currentAssignUser: data.assignedUser.adUserID,
              updatedUserId: this.applicationService.getLoggedInUserUserID()
            });
          } else {
            covStatusChangeRQ = Object.assign({}, covStatusChangeRQ, {
              assignUserID: null,
              assignADUserID: null,
              assignUser: null,
              assignUserDisplayName: null,
              assignUserUpmID: null,
              assignedUserDivCode: null,
              assignUserUpmGroupCode: null,
            });
          }

          this.coveringApprovalService
            .updateCOVStatus(AppUtils.trim(covStatusChangeRQ))
            .subscribe((res: any) => { });
        });

        this.router.navigate(["/covering-approval/dashboard"]);
      }
    });
  }

  async changeStatusCOVSelectedApprove($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    this.modalRef = this.mdbModalService.show(CommentWithViewOptionsDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: 'modal-width-60-p',
      containerClass: '',
      animated: true,
      data: {
        heading: "Approved Covering Approval",
        actionName: this.covActionStatus[status],
        showUsersOnlyOption: false,
        showDivisionOnlyOption: false,
        actionMessage: "Approve",

        message: "Do you want to  this Application Form ?`",
        content: {
          status: status,
          coveringApproval: this.coveringApproval
        },
      }
    });

    this.modalRef.content.action.subscribe((data: any) => {
      //console.log("data", data);
      //console.log("coverinmg", this.coveringApproval);
      if (data) {
        //console.log("data1", data);
        let actionMessage = 'Approved by ' + this.applicationService.getLoggedInUserDisplayName();
        let coveringApprovalUpdateDTO = {
          //...this.coveringApprovalForm,
          actionMessage: actionMessage,
          assignUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
          assignUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          assignUserUpmId: this.applicationService.getLoggedInUserUserID(),
          covAppCommentDTO: {
            userComment: data.comment,
            isUsersOnly: data.isUsersOnly ? 'Y' : 'N',
            isDivisionOnly: data.isDivisionOnly ? 'Y' : 'N',
            isPublic: data.isPublic ? 'Y' : 'N',
            createdUserID: this.applicationService.getLoggedInUserUserID(),
            createdUser: this.applicationService.getLoggedInUserUserName(),
            createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
            createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
            createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          },
          covAppId: this.coveringApproval.covAppId,
          covAppRefNumber: this.coveringApproval.covAppRefNumber,
          currentAssignUser: this.applicationService.getLoggedInUserUserName(),
          currentAssignUserAD: this.applicationService.getLoggedInUserUserName(),
          currentAssignUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          currentAssignUserId: this.applicationService.getLoggedInUserUserID(),
          updatedUserId: this.applicationService.getLoggedInUserUserID(),
          currentStatus: status,
          forwardType: "DIRECT_USER",
          isAuthorized: false,

          //updatedByUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
        };
        //console.log("respo", AppUtils.trim(coveringApprovalUpdateDTO));
        this.coveringApprovalService.updateCOVStatus(AppUtils.trim(coveringApprovalUpdateDTO)).subscribe((res: any) => {
        });


        this.router.navigate(['/covering-approval/dashboard']);
      }
    });
  }

  accDetails() {
    this.modalRef = this.mdbModalService.show(CovAccountsDetailsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-90-p modal-top",
      containerClass: "modal-scrollable",
      animated: true,
      data: {
        customerDetails: this.customerDetails,
      },
    });
  }
  accStats() {
    this.modalRef = this.mdbModalService.show(CovAccountStatisticsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-90-p modal-top",
      containerClass: "modal-scrollable",
      animated: true,
      data: {
        customerDetails: this.customerDetails,
      },
    });
  }
  advDetails() {
    this.modalRef = this.mdbModalService.show(CovAdvancesDetailsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-90-p modal-top",
      containerClass: "modal-scrollable",
      animated: true,
      data: {
        customerDetails: this.customerDetails,
      },
    });
  }

  depDetails() {
    this.modalRef = this.mdbModalService.show(CovDepositsDetailsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-90-p modal-top",
      containerClass: "modal-scrollable",
      animated: true,
      data: {
        customerDetails: this.customerDetails,
      },
    });
  }

  isAbleToViewPendingList() {
    //get the assigned user AD user Id
    let ob1 = this.coveringApproval.assignUserUpmGroupCode;

    this.applicationService
      .getUpmDetailsByAdUserIdAndAppCode(ob1)
      .subscribe((response: any) => {
        this.accessLevelOfCurrentAssignUser = response.applicationSecurityClass;
      });
  }
  isEqualLoginAndAssignUserFiftyAbove() {
    this.accessLevelOfCurrentAssignUser =
      this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser >= 50) {
      return true;
    } else {
      return false;
    }
  }

  isEqualLoginAndAssignUserFiftyLower() {
    this.accessLevelOfCurrentAssignUser =
      this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser < 50) {
      return true;
    } else {
      return false;
    }
  }

  isEqualLoginAndAssignUser() {
    this.accessLevelOfCurrentAssignUser =
      this.applicationService.getLoggedInUserUPMGroupCode();
    if (this.accessLevelOfCurrentAssignUser >= 71) {
      return true;
    } else {
      return false;
    }
  }

  showForwardButton(coveringApproval: any): boolean {
    if (
      (coveringApproval &&
        coveringApproval.currentStatus ==
        this.coveringApprovalStatusConst.APPROVED) ||
      coveringApproval.currentStatus ==
      this.coveringApprovalStatusConst.REJECTED
    ) {
      return false;
    } else {
      return true;
    }
  }
  showDeleteButton(coveringApproval: any): boolean {
    if (
      (coveringApproval &&
        coveringApproval.currentStatus ===
        this.coveringApprovalStatusConst.DRAFT) 
    ) {
      return false;
    } else {
      return true;
    }
  }
  showReturnButton(coveringApproval: any): boolean {
    if (
      (coveringApproval &&
        coveringApproval.currentStatus ==
        this.coveringApprovalStatusConst.APPROVED) ||
      coveringApproval.currentStatus ==
      this.coveringApprovalStatusConst.REJECTED
    ) {
      return false;
    } else {
      return true;
    }
  }
  showSubmitButton(coveringApproval: any): boolean {
    if (
      (coveringApproval &&
        coveringApproval.currentStatus ===
        this.coveringApprovalStatusConst.DRAFT ||
        coveringApproval.currentStatus === this.coveringApprovalStatusConst.CANCEL )
    ) {
      return true;
    } else {
      return false;
    }
  }
  isEqualLoginAndAssignUsersTypeCheck() {
    if (Number(this.coveringApproval.currentAssignUserId) === Number(this.applicationService.getLoggedInUserUserID()) || this.coveringApproval.currentAssignUserId === this.applicationService.getLoggedInUserUserID() ||this.coveringApproval.currentAssignUserId === this.applicationService.getLoggedInUserSolID()+"MGR")
     {
      return true;
      
    } else {
      return false;
    }
  }
  isEqualLoginAndAssignUsers() {
    if (this.coveringApproval.currentAssignUserId === this.applicationService.getLoggedInUserUserID() ||this.coveringApproval.currentAssignUserId === this.applicationService.getLoggedInUserSolID()+"MGR")
     {
      return true;
      
    } else {
      return false;
    }
  }

  getColour(coveringApprovalStatus) {
    switch (coveringApprovalStatus) {
      case this.coveringApprovalStatusConst.DRAFT:
        return { color: "#ffbb33a6" };
      case this.coveringApprovalStatusConst.IN_PROGRESS:
        return { color: "#0099cc94" };
      case this.coveringApprovalStatusConst.APPROVED:
        return { color: "#007e338a" };
      case this.coveringApprovalStatusConst.CANCEL:
        return { color: "#cc000073" };
      case this.coveringApprovalStatusConst.REJECTED:
        return { color: "#cc0000a6" };
    }
  }
}
