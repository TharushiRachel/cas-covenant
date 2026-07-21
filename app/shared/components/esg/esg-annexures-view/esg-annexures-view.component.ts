import { Component, Input, Output, OnInit, EventEmitter } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { AlertService } from "src/app/core/service/common/alert.service";
import { EsgService } from "src/app/core/service/common/esg.service";
import { Constants } from "src/app/core/setting/constants";
import { ConfirmationDialogComponent } from "../../confirmation-dialog/confirmation-dialog.component";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Component({
  selector: "app-esg-annexures-view",
  templateUrl: "./esg-annexures-view.component.html",
  styleUrls: ["./esg-annexures-view.component.scss"],
})
export class EsgAnnexuresViewComponent implements OnInit {
  @Input() facilityPaper: any;
  @Input() applicationForm: any;
  @Input() isFacilityPaper: boolean = false;
  @Input() mandatoryAnnexures: any[] = [];
  @Input() existingAnnexures: any[] = [];
  @Output() loadAnnexureData = new EventEmitter<any>();

  modalRef: MDBModalRef;

  constructor(
    private readonly mdbModalService: MDBModalService,
    private readonly alertService: AlertService,
    private readonly applicationService: ApplicationService,
    private readonly esgService: EsgService
  ) {}

  ngOnInit() {
    if (this.canChangeAnnexures()) {
      this.updateSelectedAnnexures();
    }
    this.reorderedAnnexures();
  }

  isNewAnnexure(annexure: any) {
    return annexure.recordStatus === Constants.annexStatusConst.NEW;
  }

  canChangeAnnexures() {
    return (
      !this.isRiskUser() &&
      this.applicationService.getLoggedInUserUPMGroupCode() <= 50 &&
      this.isAllowedUser() &&
      this.isAllowedPaper() &&
      this.isESGNotApproved()
    );
  }

  updateSelectedAnnexures() {
    if (this.mandatoryAnnexures.length > 0) {
      this.mandatoryAnnexures.forEach((mAnnexure) => {
        if (
          !this.existingAnnexures.some(
            (a: any) => a.annexureId === mAnnexure.annexureId
          )
        ) {
          this.esgService
            .getAnnexureByID(mAnnexure.annexureId)
            .then((annexure: any) => {
              this.existingAnnexures.push({
                ...annexure,
                recordStatus: Constants.annexStatusConst.NEW,
                isDisabled: false,
              });
              this.reorderedAnnexures();
            });
        }
      });
    }
  }

  reorderedAnnexures() {
    this.existingAnnexures = [
      ...this.sortAnnexures(
        this.existingAnnexures.filter(
          (item) => item.isMandatory === Constants.yesNoConst.Y
        )
      ),
      ...this.sortAnnexures(
        this.existingAnnexures.filter(
          (item) => item.isMandatory === Constants.yesNoConst.N
        )
      ),
    ];
  }

  sortAnnexures(dataList: any[]): any[] {
    return dataList
      .map((a: any) => ({
        ...a,
        annexureIndex: this.extractRomanNumeral(a.name),
      }))
      .sort((a: any, b: any) => a.annexureIndex - b.annexureIndex);
  }

  extractRomanNumeral(text: string): number {
    let splitTxt: string[] = text.split(" ");
    let romanNumeral: any = splitTxt.length > 0 ? splitTxt[1] : "";

    return romanNumeral !== "" &&
      Constants.annexureNumberConst[romanNumeral] !== undefined &&
      Constants.annexureNumberConst[romanNumeral] !== null
      ? Constants.annexureNumberConst[romanNumeral]
      : 0;
  }

  saveOrUpdateAnnexure(payload: any) {
    let annexure: any = payload.annexure;
    let answers: any[] = payload.answers.map((a: any) => ({
      ...a,
      ...this.getCommonPayload(annexure),
    }));

    if (annexure.recordStatus === Constants.annexStatusConst.NEW) {
      this.addAnnexure(answers);
    } else {
      this.updateAnnexure(annexure.annexureId, answers);
    }
  }

  addAnnexure(payload: any) {
    this.esgService
      .saveAnnexureAnswers(payload)
      .then((res: any[]) => {
        this.loadAnnexureData.next(1);
        this.alertService.showToaster(
          "Annexure has been saved succssfully.",
          SETTINGS.TOASTER_MESSAGES.success
        );
      })
      .catch(() => {
        this.alertService.showToaster(
          "Failed to save annexure.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  updateAnnexure(annexureId: number, answers: any[]) {
    let payload = {
      answers: answers,
      annexureID: annexureId,
      ...this.getPaperIdObj(),
    };
    this.esgService
      .updateAnnexureAnswers(payload)
      .then((res: any[]) => {
        this.loadAnnexureData.next(1);
        this.alertService.showToaster(
          "Annexure has been saved succssfully.",
          SETTINGS.TOASTER_MESSAGES.success
        );
      })
      .catch(() => {
        this.alertService.showToaster(
          "Failed to save annexure.",
          SETTINGS.TOASTER_MESSAGES.error
        );
      });
  }

  onEditAnnexure(annexure: any) {
    this.existingAnnexures = this.existingAnnexures.map((ea: any) => ({
      ...ea,
      recordStatus: Constants.annexStatusConst.UPDATE,
      isDisabled: ea.annexureId !== annexure.annexureId,
    }));
    this.esgService.onESGChange.next(this.existingAnnexures);
  }

  onDeleteAnnexure(annexure: any): void {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      animated: true,
      data: {
        heading: "Confirm Delete Annexure",
        message: `Do you want to delete annexure "${
          annexure.name !== null ? annexure.name : "Untitled"
        }"?`,
      },
    });

    this.modalRef.content.action.subscribe((isYes: boolean) => {
      if (isYes) {
        let payload: any = {
          annexureId: annexure.annexureId,
          ...this.getPaperIdObj(),
        };
        this.esgService
          .deleteAnnexure(payload)
          .then(() => {
            let updatedAnnexures = this.existingAnnexures.filter(
              (ea: any) => ea.annexureId !== annexure.annexureId
            );
            this.esgService.onESGChange.next(updatedAnnexures);
            this.alertService.showToaster(
              "Annexure has been removed succssfully.",
              SETTINGS.TOASTER_MESSAGES.success
            );
          })
          .catch((err) => {
            this.alertService.showToaster(
              "Failed to delete annexure.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          });
      }
    });
  }

  onRefreshAnnexure(annexure: any): void {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      animated: true,
      data: {
        heading: "Confirm Refresh Annexure",
        message: `Do you want to refresh annexure "${
          annexure.name !== null ? annexure.name : "Untitiled"
        }"? All previously filled answers will be lost.`,
      },
    });

    this.modalRef.content.action.subscribe((isYes: boolean) => {
      if (isYes) {
        let payload: any = {
          annexureId: annexure.annexureId,
          ...this.getPaperIdObj(),
        };

        this.esgService
          .refreshAnnexure(payload)
          .then((response) => {
            if (response !== null) {
              let updatedAnnexures = this.existingAnnexures.map((ea: any) =>
                ea.annexureId === annexure.annexureId ? response : ea
              );
              this.esgService.onESGChange.next(updatedAnnexures);
            }
            this.alertService.showToaster(
              "Annexure has been refreshed succssfully.",
              SETTINGS.TOASTER_MESSAGES.success
            );
          })
          .catch((err) => {
            this.alertService.showToaster(
              "Failed to refresh annexure.",
              SETTINGS.TOASTER_MESSAGES.error
            );
          });
      }
    });
  }

  getCommonPayload(annexure: any) {
    return {
      ...this.getPaperIdObj(),
      createdDate: new Date(),
      createdBy: this.applicationService.getLoggedInUserUserName(),
      modifiedDate: new Date(),
      modifiedBy: this.applicationService.getLoggedInUserUserName(),
      status: annexure.status,
      annexureName: annexure.annexureName,
      annexureDescription: annexure.annexureDescription,
      isMandatory: annexure.isMandatory,
      allowRiskEdit: annexure.allowRiskEdit,
      createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
    };
  }

  getPaperIdObj() {
    return {
      applicationFormID: !this.isFacilityPaper
        ? this.applicationForm.applicationFormID
        : null,
      facilityPaperID: this.isFacilityPaper
        ? this.facilityPaper.facilityPaperID
        : null,
    };
  }

  isAllowedPaper() {
    if (this.isFacilityPaper) {
      return (
        this.facilityPaper.currentFacilityPaperStatus !==
          Constants.facilityPaperStatusConst.APPROVED &&
        this.facilityPaper.currentFacilityPaperStatus !==
          Constants.facilityPaperStatusConst.REJECTED
      );
    }

    return (
      this.applicationForm.currentApplicationFormStatus !==
        Constants.applicationFormCurrentStatusConst.PAPER_CREATED &&
      this.applicationForm.currentApplicationFormStatus !==
        Constants.applicationFormCurrentStatusConst.DECLINED
    );
  }

  isAllowedUser() {
    if (this.isFacilityPaper) {
      return (
        this.facilityPaper.currentAssignUser ===
        this.applicationService.getLoggedInUserUserName()
      );
    }
    return (
      this.applicationForm.assignUserID ==
      this.applicationService.getLoggedInUserUserID()
    );
  }

  isRiskUser(): boolean {
    return (
      this.applicationService.getLoggedInUserDivCode().toString() ===
      SETTINGS.ESG_DIV_CODE.toString()
    );
  }

  canAnnexureChange(annexure: any) {
    if (this.isRiskUser()) {
      return (
        (annexure.createdUserDivCode === SETTINGS.ESG_DIV_CODE.toString() ||
          annexure.allowRiskEdit === Constants.yesNoConst.Y) &&
        this.isAllowedUser() &&
        this.isAllowedPaper() &&
        annexure.recordStatus !== Constants.annexStatusConst.NEW
      );
    }

    return (
      this.applicationService.getLoggedInUserUPMGroupCode() <= 50 &&
      this.isAllowedUser() &&
      this.isAllowedPaper() &&
      annexure.createdUserDivCode !== SETTINGS.ESG_DIV_CODE.toString() &&
      this.isESGNotApproved() &&
      annexure.recordStatus !== Constants.annexStatusConst.NEW
    );
  }

  isESGNotApproved(): boolean {
    if (this.isFacilityPaper) {
      return this.facilityPaper.isESGApproved === Constants.yesNoConst.N;
    }
    return this.applicationForm.isESGApproved === Constants.yesNoConst.N;
  }

  onActionCancel(annexure: any) {
    let updatedAnnexures: any = [];
    if (annexure.recordStatus === Constants.annexStatusConst.NEW) {
      updatedAnnexures = this.existingAnnexures.filter(
        (ea: any) => ea.annexureId !== annexure.annexureId
      );
    } else {
      updatedAnnexures = this.existingAnnexures.map((ea: any) => ({
        ...ea,
        isDisabled:
          ea.annexureId === annexure.annexureId ? true : ea.isDisabled,
      }));
    }

    this.esgService.onESGChange.next(updatedAnnexures);
  }
}
