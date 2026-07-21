import { Component, OnInit } from "@angular/core";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { EnvironmentalRiskAnnexureService } from "../../services/environmental-risk-annexure.service";
import { Router } from "@angular/router";
import { Constants } from "src/app/core/setting/constants";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { ViewQuestionsModalComponent } from "../view-questions-modal/view-questions-modal.component";
import { CompareDataModalComponent } from "../compare-data-modal/compare-data-modal.component";

@Component({
  selector: "app-environmental-risk-annexure",
  templateUrl: "./environmental-risk-annexure.component.html",
  styleUrls: ["./environmental-risk-annexure.component.scss"],
})
export class EnvironmentalRiskAnnexureComponent implements OnInit {
  tempAnnexes: any[] = [];
  masterAnnexes: any[] = [];
  annexes: any[] = [];

  approveStatusConst: any = Constants.approveStatusConst;

  modalRef: MDBModalRef;
  constructor(
    private readonly router: Router,
    private readonly applicationService: ApplicationService,
    private readonly mdbModalService: MDBModalService,
    private readonly environmentalRiskAnnexureService: EnvironmentalRiskAnnexureService
  ) {}

  ngOnInit() {
    this.getAnnexes();
  }

  getAnnexes() {
    this.environmentalRiskAnnexureService.getAnnexes().then((res: any) => {
      this.annexes = this.prepareAnnexes(res);
    });
  }

  prepareAnnexes(response: any) {
    this.tempAnnexes =
      response !== null && response.tempAnnexes ? response.tempAnnexes : [];

    this.masterAnnexes =
      response !== null && response.masterAnnexes
        ? response.masterAnnexes.filter(
            (ma: any) => ma.status === Constants.statusConst.ACT
          )
        : [];

    let result: any[] = this.masterAnnexes;

    this.tempAnnexes.forEach((element: any) => {
      if (result.some((r: any) => r.annexureId == element.parentId)) {
        result = result.map((r: any) =>
          r.annexureId == element.parentId
            ? {
                ...r,
                pendingRec: element,
                approvedStatus: Constants.approveStatusConst.PENDING,
              }
            : r
        );
      } else {
        result.push({
          ...element,
          pendingRec: null,
          approvedStatus: Constants.approveStatusConst.PENDING,
        });
      }
    });

    return this.reorderedAnnexures(result);
  }

  isPendingAnnex(annex: any) {
    return (
      annex.approvedStatus == Constants.approveStatusConst.PENDING &&
      annex.actionStatus !== Constants.annexStatusConst.DRAFT
    );
  }

  isDraftAnnex(annex: any) {
    return annex.actionStatus === Constants.annexStatusConst.DRAFT;
  }

  isInactiveAnnex(annex: any) {
    if (annex !== null && annex.pendingRec) {
      return annex.pendingRec.status == Constants.statusConst.INA;
    }
    return annex.status == Constants.statusConst.INA;
  }

  isAuthorizeEnabled(annex: any) {
    if (this.isInactiveAnnex(annex) && this.isValidUser(annex)) {
      return true;
    } else if (
      this.isPendingAnnex(annex) &&
      this.isValidUser(annex) &&
      !annex.pendingRec
    ) {
      return true;
    } else {
      return false;
    }
  }

  isValidUser(annex: any): boolean {
    annex = annex.pendingRec !== undefined && annex.pendingRec !== null ? annex.pendingRec : annex;
    
    let isValid: boolean = false;
    let loggedInUserName: string =
      this.applicationService.getLoggedInUserUserName();
    if (annex.createdBy && annex.modifiedBy) {
      isValid = annex.modifiedBy != loggedInUserName;
    } else if (annex.createdBy && !annex.modifiedBy) {
      isValid = annex.createdBy != loggedInUserName;
    }

    return isValid;
  }

  viewPendingInfo(item: any) {
    this.modalRef = this.mdbModalService.show(CompareDataModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "Compare Pending Authorization",
        message: "",
        selectedAnnex: item,
      },
    });
    this.modalRef.content.action.subscribe((action: any) => {
      if (action !== "C") {
        let data: any = item.pendingRec !== null ? item.pendingRec : item;
        let payload: any = {
          ...data,
          approvedStatus:
            action === "A"
              ? Constants.approveStatusConst.APPROVED
              : Constants.approveStatusConst.REJECTED,
        };

        this.environmentalRiskAnnexureService
          .approveRejectAnnex(payload)
          .then((result: any) => {
            if (result && (result.tempAnnexes || result.masterAnnexes)) {
              this.annexes = this.prepareAnnexes(result);
            }
          });
      }
    });
  }

  approveRejectAnnex(item: any, approvedStatus: any) {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Confirm Authorization",
        message: `Do you want to ${
          approvedStatus == Constants.approveStatusConst.APPROVED
            ? "approve"
            : "reject"
        } this annexure?`,
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        let data: any = item.pendingRec !== null ? item.pendingRec : item;
        let payload: any = {
          ...data,
          approvedStatus: approvedStatus,
        };

        this.environmentalRiskAnnexureService
          .approveRejectAnnex(payload)
          .then((result: any) => {
            if (result && (result.tempAnnexes || result.masterAnnexes)) {
              this.annexes = this.prepareAnnexes(result);
            }
          });
      }
    });
  }

  removeAnnex(item: any) {
    this.modalRef = this.mdbModalService.show(ConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center",
      containerClass: "",
      animated: true,
      data: {
        heading: "Confirm Deletion",
        message: `Do you want to remove this annexure?`,
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        if (item.actionStatus === Constants.annexStatusConst.DRAFT) {
          this.environmentalRiskAnnexureService
            .deleteTempAnnexeById(item.annexureId)
            .then((result: any) => {
              if (result && (result.tempAnnexes || result.masterAnnexes)) {
                this.annexes = this.prepareAnnexes(result);
              }
            });
        } else {
          let payload: any = {
            ...item,
            status: Constants.statusConst.INA,
            actionStatus: Constants.annexStatusConst.DELETE,
            questions: item.questions.map((q: any) => ({
              ...q,
              status: Constants.statusConst.INA,
              actionStatus: Constants.annexStatusConst.DELETE,
              answers: q.answers.map((qa: any) => ({
                ...qa,
                status: Constants.statusConst.INA,
                actionStatus: Constants.annexStatusConst.DELETE,
              })),
            })),
          };
          this.environmentalRiskAnnexureService
            .saveTempAnnex(payload)
            .then((result: any) => {
              if (result && (result.tempAnnexes || result.masterAnnexes)) {
                this.annexes = this.prepareAnnexes(result);
              }
            });
        }
      }
    });
  }

  addEditAnnexure(item?: any) {
    if (item !== null) {
      this.environmentalRiskAnnexureService.onAnnexureChange.next(item);
      this.router.navigate(["/environmental-risk-annexure/add-edit-annexure"], {
        queryParams: {
          annexureId: item ? item.annexureId : 0,
          status: item ? item.actionStatus : "",
        },
      });
    } else {
      this.environmentalRiskAnnexureService.onAnnexureChange.next(null);
      this.router.navigate(["/environmental-risk-annexure/add-edit-annexure"]);
    }
  }

  viewQuestions(item: any) {
    this.modalRef = this.mdbModalService.show(ViewQuestionsModalComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-60-p",
      containerClass: "",
      animated: true,
      data: {
        heading: "",
        message: "",
        annex: item,
      },
    });
    this.modalRef.content.action.subscribe((res: any) => {});
  }

  isActionEnabled(annex: any) {
    let loggedInUserName: string =
      this.applicationService.getLoggedInUserUserName();
    if (annex.actionStatus === Constants.annexStatusConst.DRAFT) {
      return (
        annex.modifiedBy == loggedInUserName ||
        annex.createdBy == loggedInUserName
      );
    }

    return true;
  }
  
  reorderedAnnexures(dataList: any[]) {
    return [
      ...this.sortAnnexures(
        dataList.filter((item) => item.isMandatory === Constants.yesNoConst.Y)
      ),
      ...this.sortAnnexures(
        dataList.filter((item) => item.isMandatory === Constants.yesNoConst.N)
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
}
