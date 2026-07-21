import { Component, OnInit } from "@angular/core";
import { AddEditEnvironmentalRiskComponent } from "../add-edit-environmental-risk/add-edit-environmental-risk.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Constants } from "src/app/core/setting/constants";
import { EnvironmentalRiskService } from "../../services/environmental-risk.service";
import { RiskConfirmationDialogComponent } from "../risk-confirmation-dialog/risk-confirmation-dialog.component";

@Component({
  selector: "app-environmental-risk",
  templateUrl: "./environmental-risk.component.html",
  styleUrls: ["./environmental-risk.component.scss"],
})
export class EnvironmentalRiskComponent implements OnInit {
  riskCategories: any[] = [];
  approvedRiskCategories: any[] = [];
  pendingRiskCategories: any[] = [];

  status = Constants.status;
  statusConst = Constants.statusConst;
  approvedStatus = Constants.approveStatus;
  approvedStatusConst = Constants.approveStatusConst;

  modalRef: MDBModalRef;
  loggedInUserName: any = "";

  constructor(
    private readonly environmentalRiskService: EnvironmentalRiskService,
    private readonly mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
    this.getTempCategories();
  }

  getTempCategories() {
    this.environmentalRiskService.getRiskCategories().then((res: any) => {
      this.riskCategories = this.buildTree(this.prepareRiskCategories(res));
    });
  }

  prepareRiskCategories(response: any) {
    this.pendingRiskCategories =
      response && response.tempRiskList ? response.tempRiskList : [];

    this.approvedRiskCategories =
      response && response.masterRiskList ? response.masterRiskList : [];

    let result: any[] = this.approvedRiskCategories;

    this.pendingRiskCategories.forEach((element: any) => {
      if (
        result.some((r: any) => r.riskCategoryId == element.parentCategoryId)
      ) {
        result = result.map((r: any) =>
          r.riskCategoryId == element.parentCategoryId
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
          approvedStatus: Constants.approveStatusConst.PENDING,
        });
      }
    });

    return result;
  }

  buildTree(data: any[], parentId: number | 0 = 0) {
    let result: any[] = data
      .filter((d: any) => d.parentId == parentId)
      .map((d: any) => ({
        ...d,
        children: this.buildTree(data, d.riskCategoryId),
        expanded: true,
      }));

    return result;
  }

  addChildRisk(item: any) {
    if (item) {
      this.addEditRisk(true, false, item);
    }
  }

  editChildRisk(item: any) {
    if (item) {
      this.addEditRisk(false, true, item);
    }
  }

  addEditRisk(isChild: boolean, isEdit: boolean, item?: any) {
    this.modalRef = this.mdbModalService.show(
      AddEditEnvironmentalRiskComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-md modal-width-60-p",
        containerClass: "",
        animated: false,
        data: {
          isChildNode: isChild,
          isEdit: isEdit,
          node: item ? item : null,
        },
      }
    );

    this.modalRef.content.action.subscribe((result: any) => {
      if (result && (result.masterRiskList || result.tempRiskList)) {
        this.riskCategories = this.buildTree(
          this.prepareRiskCategories(result)
        );
      }
    });
  }

  approveRisk(item: any) {
    if (item) {
      this.approveRejectRisk(item, Constants.approveStatusConst.APPROVED);
    }
  }

  rejectRisk(item: any) {
    if (item) {
      this.approveRejectRisk(item, Constants.approveStatusConst.REJECTED);
    }
  }

  viewPendingInfo(item: any) {
    this.modalRef = this.mdbModalService.show(RiskConfirmationDialogComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-30-p modal-margin-center ",
      containerClass: "",
      animated: true,
      data: {
        heading: "Modification Details",
        message: "",
        isModified: true,
        node: item,
      },
    });
    this.modalRef.content.action.subscribe((action: any) => {
      if (action == "Y" || action == "N") {
        let payload: any = {
          ...item.pendingRec,
          approvedStatus:
            action == "Y"
              ? Constants.approveStatusConst.APPROVED
              : Constants.approveStatusConst.REJECTED,
        };

        this.environmentalRiskService
          .approveRejectRiskCategory(payload)
          .then((result: any) => {
            if (result && (result.masterRiskList || result.tempRiskList)) {
              this.riskCategories = this.buildTree(
                this.prepareRiskCategories(result)
              );
            }
          });
      }
    });
  }

  approveRejectRisk(item: any, approvedStatus: any) {
    this.modalRef = this.mdbModalService.show(RiskConfirmationDialogComponent, {
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
        } this category?`,
        isModified: false,
      },
    });
    this.modalRef.content.action.subscribe((action: any) => {
      if (action == "Y") {
        let payload: any = {
          ...item,
          approvedStatus: approvedStatus,
        };

        this.environmentalRiskService
          .approveRejectRiskCategory(payload)
          .then((result: any) => {
            if (result && (result.masterRiskList || result.tempRiskList)) {
              this.riskCategories = this.buildTree(
                this.prepareRiskCategories(result)
              );
            }
          });
      }
    });
  }

  removeRiskCategory(item: any) {
    this.modalRef = this.mdbModalService.show(RiskConfirmationDialogComponent, {
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
        message: `Do you want to remove this category?`,
        isModified: false,
      },
    });
    this.modalRef.content.action.subscribe((action: any) => {
      if (action == "Y") {
        let payload: any = {
          ...item,
          parentCategoryId: item.riskCategoryId,
          status: Constants.statusConst.INA,
        };

        this.environmentalRiskService
          .saveTempRiskCategory(payload)
          .then((result: any) => {
            if (result && (result.masterRiskList || result.tempRiskList)) {
              this.riskCategories = this.buildTree(
                this.prepareRiskCategories(result)
              );
            }
          });
      }
    });
  }
}
