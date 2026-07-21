import { Component, HostListener, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ApfCreateApplicationFormComponent } from "../../../application-form-create/components/support-components/apf-create-application-form/apf-create-application-form.component";
import * as _ from "lodash";
import { Subscription } from "rxjs";
import { ApplicationFormAddEditService } from "../../services/application-form-add-edit.service";
import { Constants } from "../../../../../../core/setting/constants";
import { LocalStorage } from "ngx-webstorage";
import { SETTINGS } from "../../../../../../core/setting/commons.settings";
import { ApplicationService } from "../../../../../../core/service/application/application.service";
import { AppUtils } from "../../../../../../shared/app.utils";
import { Router } from "@angular/router";
import { UrlEncodeService } from "../../../../../../core/service/application/url-encode.service";
import { ApfReturnApplicationFormComponent } from "./support-components/apf-return-application-form/apf-return-application-form.component";
import { PrivilegeService } from "../../../../../../core/service/authentication/privilege.service";
import { ApplicationFormCopyDialogComponent } from "../../../../../../shared/components/application-form-copy-dialog/application-form-copy-dialog.component";
import { CommonReleaseComponent } from "../../../../../../shared/components/common-release/common-release.component";
import { CommonForwardComponent } from "../../../../../../shared/components/common-forward/common-forward.component";
import { CommonAttendComponent } from "../../../../../../shared/components/common-attend/common-attend.component";
import { CommentWithViewOptionsDialogComponent } from "../../../../../../shared/components/comment-with-view-options-dialog/comment-with-view-options-dialog.component";
import { InformationDialogComponent } from "src/app/shared/components/information-dialog/information-dialog.component";
import { EsgService } from "src/app/core/service/common/esg.service";

@Component({
  selector: "app-application-form-create-base",
  templateUrl: "./application-form-add-edit-base.component.html",
  styleUrls: ["./application-form-add-edit-base.component.scss"],
})
export class ApplicationFormAddEditBaseComponent implements OnInit, OnDestroy {
  @LocalStorage(SETTINGS.STORAGE.SELECTED_APPLICATION_FORM_ID)
  selectedApplicationFormID;

  @LocalStorage(SETTINGS.STORAGE.SELECTED_FACILITY_PAPER_ID)
  selectedFacilityPaperID;

  masterDataPrivilege = SETTINGS.PRIVILEGES;
  applicationFormStatusConst = Constants.applicationFormCurrentStatusConst;
  applicationFormStatusChangeHeading =
    Constants.applicationFormStatusChangeHeading;
  applicationFormStatus = Constants.applicationFormCurrentStatus;
  applicationFormActionStatus = Constants.applicationFormActionStatus;
  onApplicationFormChangeSub = new Subscription();
  onApplicationFormESGChangeSub = new Subscription();
  onAFESGRiskScoreChangeSub = new Subscription();
  modalRef: MDBModalRef;
  selectedTabIndex: any = 0;
  applicationForm: any = {};
  hasPrivilegeToCopy = false;
  scrWidth: any;

  cribTabIndex = 0;
  basicIndex = 1;
  liabilitiesIndex = 2;
  documentIndex = 3;
  commentIndex = 4;
  esgIndex = 5;
  lpsIndex = 6;
  riskRateIndex = 7;
  facilitiesIndex = 8;
  securitiesIndex = 9;
  repaymentIndex = 10;
  assetsIndex = 11;
  executiveSummaryIndex = 12;

  visibility = false;

  @HostListener("window:resize", ["$event"])
  getScreenSize(event?) {
    this.scrWidth = window.innerWidth;
  }
  mandatoryAnnexureList: any[] = [];
  completedAnnexures: any[] = [];
  selectedRiskCategories: any[] = [];
  isESGPaper: boolean = false;

  onESGChangeSub: Subscription = new Subscription();
  onAnnexuresChangeSub: Subscription = new Subscription();

  constructor(
    private readonly mdbModalService: MDBModalService,
    private readonly applicationFormAddEditService: ApplicationFormAddEditService,
    public applicationService: ApplicationService,
    private readonly router: Router,
    private readonly urlEncodeService: UrlEncodeService,
    private readonly privilegeService: PrivilegeService,
    private readonly esgService: EsgService
  ) {}

  ngOnInit() {
    this.hasPrivilegeToCopy = this.privilegeService.hasPrivilege(
      this.masterDataPrivilege.ICAS_SETTINGS_APPLICATION_FROM_COPY_ENABLED
    );

    this.onApplicationFormChangeSub =
      this.applicationFormAddEditService.onApplicationFormChange.subscribe(
        (data: any) => {
          if (!_.isEmpty(data)) {
            this.applicationForm = data;
            this.isESGPaper =
              this.applicationForm &&
              this.applicationForm.isESGPaper === Constants.yesNoConst.Y;
          }
        }
      );

    this.loadAnnexureData(this.applicationForm.applicationFormID);
    this.onAnnexuresChangeSub = this.esgService.onAnnexuresChange.subscribe(
      (annexures: any[]) => {
        this.mandatoryAnnexureList = annexures.filter(
          (a: any) => a.isMandatory == Constants.yesNoConst.Y
        );
      }
    );

    this.onESGChangeSub = this.esgService.onESGChange.subscribe(
      (annexures: any[]) => {
        if (annexures.length > 0) {
          this.completedAnnexures = annexures;
        } else {
          this.completedAnnexures = [];
        }
      }
    );

    this.onAFESGRiskScoreChangeSub =
      this.applicationFormAddEditService.onAFESGRiskScoreChange.subscribe(
        (res: any) => {
          if (res && res.length > 0) {
            this.selectedRiskCategories = res;
            this.isESGPaper = true;
          } else {
            this.isESGPaper = false;
            this.selectedRiskCategories = [];
          }
        }
      );
  }

  loadAnnexureData(applicationFormID: number) {
    this.esgService.getAnnexureList().then(() => {
      return this.esgService.getAnnexureByPaperID({
        applicationFormID,
        facilityPaperID: null,
      });
    });
  }

  ngOnDestroy(): void {
    this.selectedApplicationFormID = null;
    this.onApplicationFormChangeSub.unsubscribe();
    this.onAFESGRiskScoreChangeSub.unsubscribe();
    this.onAnnexuresChangeSub.unsubscribe();
    this.onESGChangeSub.unsubscribe();
  }

  onTabSelect($event) {
    this.selectedTabIndex = $event;
  }

  isTabSelected(index: any) {
    return this.selectedTabIndex == index;
  }

  createApplicationForm() {
    this.modalRef = this.mdbModalService.show(
      ApfCreateApplicationFormComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-60-p",
        containerClass: "",
        animated: false,
      }
    );

    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
      }
    });
  }

  getColour(facilityStatus) {
    switch (facilityStatus) {
      case this.applicationFormStatusConst.DRAFT:
        return { color: "#ffbb33a6" };
      case this.applicationFormStatusConst.PAPER_CREATED:
      case this.applicationFormStatusConst.IN_PROGRESS:
        return { color: "#0099cc94" };
      case this.applicationFormStatusConst.RETURNED:
        return { color: "#007e338a" };
      case this.applicationFormStatusConst.DECLINED:
        return { color: "#cc0000a6" };
    }
  }

  createFacilityPaper($event, status) {
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
          showUsersOnlyOption: false,
          showDivisionOnlyOption: false,
          commentCacheKey:
            this.applicationForm.afRefNumber +
            this.applicationFormActionStatus[status] +
            this.applicationService.getLoggedInUserUserID(),
          actionName: this.applicationFormActionStatus[status],
          heading: this.applicationFormActionStatus[status],
          message: `Do you want to create Facility Paper from this Application Form ?`,
        },
      }
    );

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let facilityPaperGenerateRQ = {
          ...this.applicationForm,
          currentAssignUserID: this.applicationService.getLoggedInUserUserID(),
          currentAssignADUserID:
            this.applicationService.getLoggedInUserUserName(),
          currentAssignUser: this.applicationService.getLoggedInUserUserName(),
          assignUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          currentAssignUserDivCode:
            this.applicationService.getLoggedInUserDivCode(),
          assignUserUpmID: this.applicationService.getLoggedInUserUserID(),
          assignUserUpmGroupCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          afCommentDTO: {
            createdUserID: this.applicationService.getLoggedInUserUserID(),
            createdUser: this.applicationService.getLoggedInUserUserName(),
            createdUserDisplayName:
              this.applicationService.getLoggedInUserDisplayName(),
            createdUserDivCode:
              this.applicationService.getLoggedInUserDivCode(),
            createdUserUpmCode:
              this.applicationService.getLoggedInUserUPMGroupCode(),
            comment: data.comment,
            isUsersOnly: data.isUsersOnly ? "Y" : "N",
            isDivisionOnly: data.isDivisionOnly ? "Y" : "N",
            isPublic: data.isPublic ? "Y" : "N",
          },
        };
        this.applicationFormAddEditService
          .draftFacilityPaperByApplicationForm(
            AppUtils.trim(facilityPaperGenerateRQ)
          )
          .subscribe((res: any) => {
            if (res.facilityPaperID) {
              this.selectedFacilityPaperID = this.urlEncodeService.encode(
                res.facilityPaperID
              );
              this.router.navigate(["/facility-paper/edit"]);
            }
          });
      }
    });
  }

  async changeStatusApplicationForm($event: any, status: any) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let returnUserList: any;
    if (status == this.applicationFormStatusConst.RETURNED) {
      returnUserList =
        await this.applicationFormAddEditService.getAFReturnUsersList(
          this.applicationForm
        );
    }

    if (this.isESGFwdCheck(status)) {
      let isScoreSelected: boolean = this.isAFRiskScoreSelected();
      let isMandatoryAnnexSelected: boolean = this.isAFCompleteMandatoryAnnex();

      if (isScoreSelected && isMandatoryAnnexSelected) {
        this.handleForward(returnUserList, status);
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
            heading: "Application Form ESG Requirement",
            message: !isScoreSelected
              ? "This paper does not have select the risk score."
              : !isMandatoryAnnexSelected
              ? "This paper does not complete the mandatory annexures."
              : "This paper does not have select the risk score or complete mandatory annexures.",
            showConfirm: false,
          },
        });
        this.modalRef.content.action.subscribe((result: any) => {});
      }
    } else {
      this.handleForward(returnUserList, status);
    }
  }

  handleForward(returnUserList: any[], status: any) {
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
          `${this.applicationFormStatusChangeHeading[status]}` +
          " Application Form",
        actionMessage: `${this.applicationFormStatusChangeHeading[status]}`,
        isForward: status == this.applicationFormStatusConst.IN_PROGRESS,
        isReturn: status == this.applicationFormStatusConst.RETURNED,
        isESG: false,
        commentCacheKey:
          this.applicationForm.afRefNumber +
          this.applicationFormActionStatus[status] +
          this.applicationService.getLoggedInUserUserID(),
        content: {
          returnUserList: returnUserList ? returnUserList : [],
          branchCode: this.applicationForm.branchCode,
          createdUser: this.applicationForm.createdBy,
          currentAssignUser: this.applicationForm.assignUser,
          workflowTemplateID: this.applicationForm.workflowTemplateID,
          relatedDivCodes: [this.applicationForm.branchCode],
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        this.handleChangeStatusAF(data, status);
      }
    });
  }

  attendApplicationForm($event, status) {
    let actionMessage = "Attended to this application Form";
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
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Confirm Your Attending",
        message: "Do you want to attend to this Application Form ?",
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let applicationFormStatusChangeRQ = {
          applicationFormID: this.applicationForm.applicationFormID,
          afRefNumber: this.applicationForm.afRefNumber,
          assignDepartmentCode: data.assignDepartmentCode,
          afAssignDepartmentDTOList: data.assignDepartmentDTOList,
          actionMessage: actionMessage,
          afCommentDTO: { ...data.remarkData, actionMessage },
          applicationFormStatus: status,
          forwardType: data.forwardType,
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
        };

        if (data.assignedUser) {
          applicationFormStatusChangeRQ = Object.assign(
            {},
            applicationFormStatusChangeRQ,
            {
              assignUserID: data.assignedUser.userID,
              assignADUserID: data.assignedUser.adUserID,
              assignUser: data.assignedUser.adUserID,
              assignUserDisplayName: data.assignedUser.assignUserDisplayName,
              assignUserUpmID: data.assignedUser.userID,
              assignUserDivCode: data.assignedUser.DivCode,
              assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
            }
          );
        } else {
          applicationFormStatusChangeRQ = Object.assign(
            {},
            applicationFormStatusChangeRQ,
            {
              assignUserID: null,
              assignADUserID: null,
              assignUser: null,
              assignUserDisplayName: null,
              assignUserUpmID: null,
              assignedUserDivCode: null,
              assignUserUpmGroupCode: null,
            }
          );
        }
        this.applicationFormAddEditService.updateApplicationFormStatus(
          AppUtils.trim(applicationFormStatusChangeRQ)
        );
      }
    });
  }

  returnApplicationForm($event, status) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(
      ApfReturnApplicationFormComponent,
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
          heading: "Return",
          content: {
            applicationForm: this.applicationForm,
            status: status,
          },
        },
      }
    );

    this.modalRef.content.action.subscribe((result: any) => {
      if (result) {
        let applicationFormStatusChangeRQ = {
          ...this.applicationForm,
          ...result,
          forwardType: Constants.ForwardTypeConst.DIRECT_USER,
          applicationFormStatus: status,
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
        };
        /*this.applicationFormAddEditService.updateApplicationFormStatus(AppUtils.trim(applicationFormStatusChangeRQ));
        this.router.navigate(['/application-forms/dashboard']);*/
        this.applicationFormAddEditService
          .updateAFStatus(AppUtils.trim(applicationFormStatusChangeRQ))
          .subscribe((res: any) => {
            this.router.navigate(["/application-forms/dashboard"]);
          });
        //this.router.navigate(['/application-form/inbox']);
      }
    });
  }

  declinePaper($event, status) {
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
          showUsersOnlyOption: false,
          commentCacheKey:
            this.applicationForm.afRefNumber +
            this.applicationFormActionStatus[status] +
            this.applicationService.getLoggedInUserUserID(),
          actionName: this.applicationFormActionStatus[status],
          heading:
            this.applicationFormActionStatus[status] + " Application Form",
          message: `Do you want to ${this.applicationFormActionStatus[status]} this Application Form ?`,
        },
      }
    );
    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let applicationFormStatusChangeRQ = {
          ...this.applicationForm,
          remark: data.comment,
          assignUserID: this.applicationService.getLoggedInUserUserID(),
          assignADUserID: this.applicationService.getLoggedInUserUserName(),
          assignUser: this.applicationService.getLoggedInUserUserName(),
          assignUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          actionMessage: `${this.applicationService.getLoggedInUserDisplayName()} declined`,
          upmID: this.applicationService.getLoggedInUserUserID(),
          upmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          applicationFormStatus: status,
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          afCommentDTO: {
            createdUserID: this.applicationService.getLoggedInUserUserID(),
            createdUser: this.applicationService.getLoggedInUserUserName(),
            createdUserDisplayName:
              this.applicationService.getLoggedInUserDisplayName(),
            createdUserDivCode:
              this.applicationService.getLoggedInUserDivCode(),
            createdUserUpmCode:
              this.applicationService.getLoggedInUserUPMGroupCode(),
            comment: data.comment,
            isUsersOnly: data.isUsersOnly ? "Y" : "N",
            isDivisionOnly: data.isDivisionOnly ? "Y" : "N",
            isPublic: data.isPublic ? "Y" : "N",
          },
        };

        this.applicationFormAddEditService
          .updateAFStatus(AppUtils.trim(applicationFormStatusChangeRQ))
          .subscribe((res: any) => {
            this.router.navigate(["/application-forms/dashboard"]);
          });
      }
    });
  }

  releaseApplicationForm($event, status) {
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
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        message: "Do you want to release this Application Form ?",
        content: {
          status: status,
          workflowTemplateID: this.applicationForm.workflowTemplateID,
        },
      },
    });

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let applicationFormStatusChangeRQ = {
          applicationFormID: this.applicationForm.applicationFormID,
          afRefNumber: this.applicationForm.afRefNumber,
          assignDepartmentCode: data.assignDepartmentCode,
          afAssignDepartmentDTOList: data.assignDepartmentDTOList,
          actionMessage: data.actionMessage,
          afCommentDTO: data.remarkData,
          applicationFormStatus: status,
          forwardType: data.forwardType,
          updatedByUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
        };
        /* this.applicationFormAddEditService.updateApplicationFormStatus(AppUtils.trim(applicationFormStatusChangeRQ));
         this.router.navigate(['/application-forms/dashboard']);*/

        this.applicationFormAddEditService
          .updateAFStatus(AppUtils.trim(applicationFormStatusChangeRQ))
          .subscribe((res: any) => {
            this.router.navigate(["/application-forms/dashboard"]);
          });
        //this.router.navigate(['/application-form/inbox']);
      }
    });
  }

  copy($event) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(
      ApplicationFormCopyDialogComponent,
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
          applicationForm: this.applicationForm,
          heading: "Copy Application Form From - ",
          message: "Do you want to copy this Application Form ?",
        },
      }
    );

    this.modalRef.content.action.subscribe((data: any) => {
      if (data) {
        let applicationFormReplicateRQ = {
          ...this.applicationForm,
          originalApplicationFormID: this.applicationForm.applicationFormID,
          workflowTemplateID: data.workflowTemplateID,
          branchCode: data.branchCode,
          assignUserID: this.applicationService.getLoggedInUserUserID(),
          createdUserID: this.applicationService.getLoggedInUserUserID(),
          assignADUserID: this.applicationService.getLoggedInUserUserName(),
          assignUser: this.applicationService.getLoggedInUserUserName(),
          assignUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          assignUserUpmID: this.applicationService.getLoggedInUserUserID(),
          assignUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          upmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          applicationFormStatus: this.applicationFormStatusConst.DRAFT,
        };
        this.applicationFormAddEditService.replicateApplicationForm(
          AppUtils.trim(applicationFormReplicateRQ)
        );
      }
    });
  }

  showForwardButton(status) {
    switch (status) {
      case this.applicationFormStatusConst.DRAFT:
      case this.applicationFormStatusConst.IN_PROGRESS:
      case this.applicationFormStatusConst.RETURNED:
        return true;
      default:
        return false;
    }
  }

  showAttendButton(status) {
    switch (status) {
      case this.applicationFormStatusConst.IN_PROGRESS:
      case this.applicationFormStatusConst.RETURNED: {
        return (
          this.isAssignedForLoggedInUserUPMGroup() &&
          this.applicationForm.assignDepartmentCode ==
            this.applicationService.getLoggedInUserDivCode()
        );
      }

      default:
        return false;
    }
  }

  showReleaseButton(status) {
    switch (status) {
      case this.applicationFormStatusConst.IN_PROGRESS:
      case this.applicationFormStatusConst.RETURNED:
        return true;
      default:
        return false;
    }
  }

  showCreateFacilityPaperButton(status) {
    switch (status) {
      case this.applicationFormStatusConst.DRAFT:
      case this.applicationFormStatusConst.IN_PROGRESS:
      case this.applicationFormStatusConst.RETURNED:
        return true;
      default:
        return false;
    }
  }

  showReturnButton(status) {
    switch (status) {
      case this.applicationFormStatusConst.IN_PROGRESS:
      case this.applicationFormStatusConst.RETURNED:
        return true;
      default:
        return false;
    }
  }

  showDeclineButton(status) {
    switch (status) {
      case this.applicationFormStatusConst.DECLINED:
      case this.applicationFormStatusConst.PAPER_CREATED:
        return false;
      default:
        return true;
    }
  }

  showCopyApplicationButton() {
    return this.hasPrivilegeToCopy;
  }

  isDirectlyAssigned() {
    return (
      this.applicationForm.assignUserID ==
      this.applicationService.getLoggedInUserUserID()
    );
  }

  isAssignedForLoggedInUserUPMGroup() {
    if (this.applicationForm.afAssignDepartmentDTOList) {
      let assignUserGroup = _.find(
        this.applicationForm.afAssignDepartmentDTOList,
        (b) =>
          b.userGroupUPMCode ==
          this.applicationService.getLoggedInUserUPMGroupCode()
      );
      return assignUserGroup && assignUserGroup.userGroupUPMCode;
    } else {
      return false;
    }
  }

  isApplicationAssignedForLoggedInUserBranch() {
    if (this.applicationForm.assignDepartmentCode) {
      return (
        this.applicationForm.assignDepartmentCode ==
        this.applicationService.getLoggedInUserDivCode()
      );
    } else {
      return false;
    }
  }

  forwardToESG($event: any, status: any) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    let isScoreSelected: boolean = this.isAFRiskScoreSelected();
    let isMandatoryAnnexSelected: boolean = this.isAFCompleteMandatoryAnnex();

    if (isScoreSelected && isMandatoryAnnexSelected) {
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
          heading: "Application Form Forwarding to ESG",
          actionMessage: `${this.applicationFormStatusChangeHeading[status]}`,
          isForward: true,
          isReturn: false,
          isESG: true,
          commentCacheKey:
            this.applicationForm.afRefNumber +
            this.applicationFormActionStatus[status] +
            this.applicationService.getLoggedInUserUserID(),
          content: {
            returnUserList: [],
            branchCode: this.applicationForm.branchCode,
            createdUser: this.applicationForm.createdBy,
            currentAssignUser: this.applicationForm.assignUser,
            workflowTemplateID: this.applicationForm.workflowTemplateID,
            relatedDivCodes: [this.applicationForm.branchCode],
          },
        },
      });

      this.modalRef.content.action.subscribe((data: any) => {
        if (data) {
          this.handleChangeStatusAF(data, status);
        }
      });
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
          heading: "Application Form ESG Requirement",
          message: !isScoreSelected
            ? "This paper does not have select the risk score."
            : !isMandatoryAnnexSelected
            ? "This paper does not have complete the mandatory annexures."
            : "This paper does not have select the risk score or complete the mandatory annexures.",
          showConfirm: false,
        },
      });
      this.modalRef.content.action.subscribe((result: any) => {});
    }
  }

  handleChangeStatusAF(data: any, status: any) {
    let applicationFormStatusChangeRQ = {
      applicationFormID: this.applicationForm.applicationFormID,
      afRefNumber: this.applicationForm.afRefNumber,
      assignDepartmentCode: data.assignDepartmentCode,
      afAssignDepartmentDTOList: data.assignDepartmentDTOList,
      actionMessage: data.actionMessage,
      afCommentDTO: data.remarkData,
      applicationFormStatus: status,
      forwardType: data.forwardType,
      updatedByUserDisplayName:
        this.applicationService.getLoggedInUserDisplayName(),
    };
    if (data.assignedUser) {
      applicationFormStatusChangeRQ = Object.assign(
        {},
        applicationFormStatusChangeRQ,
        {
          assignUserID: data.assignedUser.userID,
          assignADUserID: data.assignedUser.adUserID,
          assignUser: data.assignedUser.adUserID,
          assignUserDisplayName: data.assignedUser.assignUserDisplayName,
          assignUserUpmID: data.assignedUser.userID,
          assignUserDivCode: data.assignedUser.divCode,
          assignUserUpmGroupCode: data.assignedUser.assignUserUpmGroupCode,
        }
      );
    } else {
      applicationFormStatusChangeRQ = Object.assign(
        {},
        applicationFormStatusChangeRQ,
        {
          assignUserID: null,
          assignADUserID: null,
          assignUser: null,
          assignUserDisplayName: null,
          assignUserUpmID: null,
          assignedUserDivCode: null,
          assignUserUpmGroupCode: null,
        }
      );
    }
    this.applicationFormAddEditService
      .updateAFStatus(AppUtils.trim(applicationFormStatusChangeRQ))
      .subscribe((res: any) => {
        this.router.navigate(["/application-forms/dashboard"]);
      });
  }

  showESGForwardButton(status: any) {
    if (
      this.isDirectlyAssigned() &&
      this.isNotRiskUser() &&
      this.applicationService.getLoggedInUserUPMGroupCode() == 50 &&
      (status === this.applicationFormStatusConst.DRAFT ||
        status === this.applicationFormStatusConst.IN_PROGRESS ||
        status === this.applicationFormStatusConst.RETURNED)
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

  isESGFwdCheck(status: any) {
    let wc: number = this.applicationService.getLoggedInUserUPMGroupCode()
      ? parseInt(this.applicationService.getLoggedInUserUPMGroupCode())
      : 0;
    return (
      wc <= 50 &&
      this.isNotRiskUser() &&
      this.isESGPaper &&
      (status == this.applicationFormStatusConst.DRAFT ||
        status == this.applicationFormStatusConst.IN_PROGRESS)
    );
  }

  isAFRiskScoreSelected() {
    if (this.selectedRiskCategories) {
      return this.selectedRiskCategories.some(
        (d: any) => d.score && d.score !== null
      );
    }
    return false;
  }

  isAFCompleteMandatoryAnnex() {
    let unCompleteAnnexes: any[] = [];
    this.mandatoryAnnexureList.forEach((element: any) => {
      let mandatoryAnnexure: any = this.completedAnnexures.find(
        (a: any) => a.annexureId == element.annexureId
      );

      if (
        mandatoryAnnexure === undefined ||
        mandatoryAnnexure === null ||
        mandatoryAnnexure.questions.some(
          (q: any) => q.answers.length == 0 && !q.userAnswer
        )
      ) {
        unCompleteAnnexes.push(element);
      }
    });

    return unCompleteAnnexes.length == 0;
  }
}
