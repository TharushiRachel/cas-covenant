import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { AlertService } from "src/app/core/service/common/alert.service";
import { EsgService } from "src/app/core/service/common/esg.service";
import { SETTINGS } from "src/app/core/setting/commons.settings";
import { EsgAnnexureSelectorsCommonComponent } from "src/app/shared/components/esg/esg-annexure-selectors-common/esg-annexure-selectors-common.component";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { Constants } from "src/app/core/setting/constants";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { EsgAnnexureAttachmentComponent } from "src/app/shared/components/esg/esg-annexure-attachment/esg-annexure-attachment.component";
import { ShowEsgInstructionsComponent } from "src/app/shared/components/show-esg-instructions/show-esg-instructions.component";
import { SaveRiskOpinionReplyComponent } from "src/app/shared/components/esg/save-risk-opinion-reply/save-risk-opinion-reply.component";
import { Subscription } from "rxjs";
import { EsgConfirmScoreComponent } from "src/app/shared/components/esg/esg-confirm-score/esg-confirm-score.component";

@Component({
  selector: "app-fp-esg-wrapper",
  templateUrl: "./fp-esg-wrapper.component.html",
  styleUrls: ["./fp-esg-wrapper.component.scss"],
})
export class FpEsgWrapperComponent implements OnInit, OnDestroy {
  @Input("facilityPaper") facilityPaper: any;
  modalRef: MDBModalRef;
  availableAnnexureList: any[] = [];
  existingAnnexures: any[] = [];
  attachments: any[] = [];
  attachmentsList: any[] = [];
  mandatoryAnnexures: any[] = [];
  opinions: any[] = [];

  facilityPaperID: number;
  isEsgPaper: boolean = false;
  isAnswersFromAppForm: boolean = false;
  isOpinionLoading: boolean = false;
  isLoadingAnnexure: boolean = false;

  onESGRiskOpinionChangeSub = new Subscription();
  onESGChangeSub = new Subscription();
  onFacilityPaperChangeSub = new Subscription();
  onAnnexuresChangeSub = new Subscription();

  constructor(
    private readonly mdbModalService: MDBModalService,
    private readonly facilityPaperAddEditService: FacilityPaperAddEditService,
    private readonly alertService: AlertService,
    private readonly esgService: EsgService,
    private readonly applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.facilityPaperAddEditService.getFPEnvironmentalRiskOpinion(
      this.facilityPaper.facilityPaperID
    );

    this.onFacilityPaperChangeSub =
      this.facilityPaperAddEditService.onFacilityPaperChange.subscribe(
        (data: any) => {
          if (data.facilityPaperID) {
            this.facilityPaperID = data.facilityPaperID;
            this.isEsgPaper = data.isEsgPaper === Constants.yesNoConst.Y;
          }
        }
      );

    this.onAnnexuresChangeSub = this.esgService.onAnnexuresChange.subscribe(
      (annexures: any[]) => {
        this.availableAnnexureList = annexures;
      }
    );

    this.onESGChangeSub = this.esgService.onESGChange.subscribe(
      (annexures: any[]) => {
        this.existingAnnexures = annexures;
      }
    );

    this.onESGRiskOpinionChangeSub =
      this.facilityPaperAddEditService.onESGRiskOpinionChange.subscribe(
        (res: any[]) => {
          this.isOpinionLoading = true;
          this.opinions = res.length > 0 ? res : [];
          this.isOpinionLoading = false;
        }
      );

    this.loadAnnexureData();
  }

  ngOnDestroy(): void {
    this.onFacilityPaperChangeSub.unsubscribe();
    this.onESGChangeSub.unsubscribe();
    this.onESGRiskOpinionChangeSub.unsubscribe();
    this.onAnnexuresChangeSub.unsubscribe();
  }

  loadAnnexureData(event?: any): void {
    this.isLoadingAnnexure = true;
    this.esgService
      .getAnnexureByPaperID({
        applicationFormID: null,
        facilityPaperID: this.facilityPaper.facilityPaperID,
      })
      .then((res) => {
        const filtered: any[] = (res || []).filter(
          (a) => a.status === Constants.statusConst.ACT
        );
        this.existingAnnexures = filtered.map((ea: any) => ({
          ...ea,
          recordStatus: Constants.annexStatusConst.SUBMITTED,
          isDisabled: true,
        }));

        const submittedIds = this.existingAnnexures.map((a) => a.annexureId);

        const availableAnnexures = this.availableAnnexureList.filter(
          (a) => !submittedIds.includes(a.annexureId)
        );

        this.mandatoryAnnexures = availableAnnexures.filter(
          (data: any) => data.isMandatory === Constants.yesNoConst.Y
        );
        this.isLoadingAnnexure = false;
      })
      .catch(() => {
        this.isLoadingAnnexure = false;
        this.alertService.showToaster(
          "Failed to load annexure data",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  openAddAnnexureModal($event?: Event): void {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    const submittedIds = this.existingAnnexures.map((a) => a.annexureId);
    const availableAnnexures = this.availableAnnexureList.filter(
      (a) =>
        !submittedIds.includes(a.annexureId) &&
        a.isMandatory === Constants.yesNoConst.N
    );

    this.modalRef = this.mdbModalService.show(
      EsgAnnexureSelectorsCommonComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-45-p modal-margin-center",
        data: { availableAnnexures },
      }
    );

    this.modalRef.content.action.subscribe((selectedAnnexureId: number) => {
      if (selectedAnnexureId) {
        this.isLoadingAnnexure = true;
        this.esgService
          .getAnnexureByID(selectedAnnexureId)
          .then((annexure: any) => {
            this.existingAnnexures.push({
              ...annexure,
              recordStatus: Constants.annexStatusConst.NEW,
              isDisabled: false,
            });
            setTimeout(() => {
              this.scrollToTarget();
            }, 1000);
          })
          .finally(() => {
            this.isLoadingAnnexure = false;
          });
      }
    });
  }

  openAttachmentModal(mode: "add" | "edit", attachment?: any): void {
    this.modalRef = this.mdbModalService.show(EsgAnnexureAttachmentComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-45-p modal-margin-center",
      data: {
        mode,
        attachment, // null for add
        applicationFormID: null,
        facilityPaperID: this.facilityPaperID,
      },
    });

    this.modalRef.content.action.subscribe(() => {
      this.loadAttachments(); // refresh list after add/edit
    });
  }

  openAddAttachmentModal($event: any): void {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }
    this.openAttachmentModal("add");
  }

  loadAttachments(): void {
    const payload = {
      applicationFormID: null,
      facilityPaperID: this.facilityPaperID,
    };

    this.esgService
      .getEsgAttachments(payload)
      .then((res) => {
        this.attachments = (res || []).filter(
          (a) => a.status === Constants.statusConst.ACT
        );
      })
      .catch(() => {
        this.alertService.showToaster(
          "Failed to load attachments",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  // For Edit
  openEditAttachmentModal(attachment: any): void {
    this.openAttachmentModal("edit", attachment);
  }

  isDraftFromApplicationForm() {
    return (
      this.facilityPaper &&
      this.facilityPaper.applicationFormID !== null &&
      this.facilityPaper.applicationFormID
    );
  }

  openViewInstructions() {
    this.modalRef = this.mdbModalService.show(ShowEsgInstructionsComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      animated: true,
      data: {},
    });

    this.modalRef.content.action.subscribe((result: any) => {});
  }

  isRiskUser(): boolean {
    return (
      this.applicationService.getLoggedInUserDivCode().toString() ===
      SETTINGS.ESG_DIV_CODE.toString()
    );
  }

  showOpinionButton() {
    return (
      this.facilityPaper &&
      this.facilityPaper.isESGPaper === Constants.yesNoConst.Y &&
      this.facilityPaper.isESGApproved === Constants.yesNoConst.N &&
      !this.opinions.some((o: any) => o.riskOpinionReply == null) &&
      this.isRiskUser() &&
      this.isAllowedUser() &&
      this.isAllowedPaper()
    );
  }

  isAllowedPaper() {
    return (
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.APPROVED &&
      this.facilityPaper.currentFacilityPaperStatus !==
        Constants.facilityPaperStatusConst.REJECTED
    );
  }

  isAllowedUser() {
    return (
      this.facilityPaper.currentAssignUser ===
      this.applicationService.getLoggedInUserUserName()
    );
  }

  addEditOpinion(item?: any) {
    this.modalRef = this.mdbModalService.show(SaveRiskOpinionReplyComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-70-p",
      animated: false,
      data: {
        heading: "Save Risk Opinion",
        html: item && item.opinion ? item.opinion : "",
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      let payload: any = {
        riskOpinionId: item && item.riskOpinionId ? item.riskOpinionId : 0,
        facilityPaperId: this.facilityPaperID,
        opinion: result,
        reply: "",
      };

      this.facilityPaperAddEditService
        .saveEnvironmentalRiskOpinion(payload)
        .then((res: any) => {
          this.modalRef.hide();
        });
    });
  }

  addEditReply(item?: any) {
    this.modalRef = this.mdbModalService.show(SaveRiskOpinionReplyComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-70-p",
      animated: false,
      data: {
        heading: "Save Risk Reply",
        html: item && item.reply ? item.reply : "",
      },
    });

    this.modalRef.content.action.subscribe((result: any) => {
      let payload: any = {
        riskReplyId: item.riskReplyId ? item.riskReplyId : 0,
        riskOpinionId: item.riskOpinionId ? item.riskOpinionId : 0,
        facilityPaperId: this.facilityPaperID,
        opinion: "",
        reply: result,
      };
      this.facilityPaperAddEditService
        .saveEnvironmentalRiskReply(payload)
        .then((res: any) => {
          this.modalRef.hide();
        });
    });
  }

  isShowApproveButton() {
    return (
      this.facilityPaper.isESGPaper == Constants.yesNoConst.Y &&
      this.facilityPaper.isESGApproved === Constants.yesNoConst.N &&
      this.isRiskUser() &&
      this.isAllowedUser() &&
      this.isAllowedPaper()
    );
  }

  isShowEditButton() {
    return (
      this.facilityPaper.isESGPaper == Constants.yesNoConst.Y &&
      this.facilityPaper.isESGApproved === Constants.yesNoConst.Y &&
      this.isRiskUser() &&
      this.isAllowedUser() &&
      this.isAllowedPaper()
    );
  }

  handleApproveStatus(status: number) {
    this.modalRef = this.mdbModalService.show(EsgConfirmScoreComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      containerClass: "",
      animated: false,
      data: {
        isApproval: status == 1,
      },
    });

    this.modalRef.content.action.subscribe((request: any) => {
      if (request.isApprove) {
        this.facilityPaperAddEditService.approvedFPEnvironmentalRisk({
          ...request,
          facilityPaperId: this.facilityPaper.facilityPaperID,
        });
      }
    });
  }

  handleDeleteOpinion(item: any) {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      containerClass: "",
      animated: false,
      data: {
        heading: "Confirm ESG Risk Opinion Deletion",
        message: `Do you want to remove this selected esg risk opinion?`,
      },
    });

    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        let request: any = {
          riskOpinionId: item.riskOpinionId,
          facilityPaperId: this.facilityPaper.facilityPaperID,
        };
        this.facilityPaperAddEditService.removeEnvironmentalRiskOpinion(
          request
        );
      }
    });
  }

  isAttachmentEnabled() {
    let loggedInUserDiv: string = this.applicationService
      .getLoggedInUserDivCode()
      .toString();

    let UPMGroupCode: number = parseInt(
      this.applicationService.getLoggedInUserUPMGroupCode()
    );

    if (loggedInUserDiv === SETTINGS.ESG_DIV_CODE.toString()) {
      if (this.isAllowedPaper() && this.isAllowedUser()) {
        return true;
      }
      return false;
    } else {
      if (UPMGroupCode <= 50 && this.isAllowedPaper() && this.isAllowedUser()) {
        return true;
      }
      return false;
    }
  }

  canAddAnnexure() {
    const submittedIds = this.existingAnnexures.map((a) => a.annexureId);

    const availableAnnexures = this.availableAnnexureList.filter(
      (a) => !submittedIds.includes(a.annexureId)
    );

    if (!this.isRiskUser()) {
      return (
        this.applicationService.getLoggedInUserUPMGroupCode() <= 50 &&
        !this.existingAnnexures.some((ea: any) => ea.isDisabled === false) &&
        availableAnnexures.length > 0 &&
        this.facilityPaper.isESGApproved === Constants.yesNoConst.N &&
        this.isAllowedUser() &&
        this.isAllowedPaper()
      );
    }

    return (
      !this.existingAnnexures.some((ea: any) => ea.isDisabled === false) &&
      availableAnnexures.length > 0 &&
      this.isAllowedUser() &&
      this.isAllowedPaper()
    );
  }

  scrollToTarget() {
    const element = document.getElementById("annexureEnd");
    if (element) {
      const elementPosition =
        element.getBoundingClientRect().top + window.pageYOffset;
      const scrollPosition = elementPosition / 2;

      window.scrollTo({
        top: scrollPosition,
        behavior: "smooth",
      });
    }
  }
}
