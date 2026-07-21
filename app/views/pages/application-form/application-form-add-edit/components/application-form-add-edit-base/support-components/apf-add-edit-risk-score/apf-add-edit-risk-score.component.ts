import { Component, Input, OnDestroy, OnInit } from "@angular/core";
import { ApplicationFormAddEditService } from "../../../../services/application-form-add-edit.service";
import { Constants } from "src/app/core/setting/constants";
import { Subscription } from "rxjs";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";
import { SETTINGS } from "src/app/core/setting/commons.settings";

@Component({
  selector: "app-apf-add-edit-risk-score",
  templateUrl: "./apf-add-edit-risk-score.component.html",
  styleUrls: ["./apf-add-edit-risk-score.component.scss"],
})
export class ApfAddEditRiskScoreComponent implements OnInit, OnDestroy {
  applicationForm: any;
  modalRef: MDBModalRef;
  riskCategories: any[] = [];

  selectorList: any[] = [];
  prevSelectedCategories: any[] = [];
  selectedCategories: any[] = [];
  isRiskApproved: boolean = false;
  approvedESGScore: string = "";
  selectedScoreItem: any = "";
  isLoading: boolean = true;

  onApplicationFormChangeSub = new Subscription();
  onAFESGRiskScoreChangeSub = new Subscription();

  constructor(
    private readonly applicationService: ApplicationService,
    private readonly applicationFormService: ApplicationFormAddEditService,
    private readonly mdbModalService: MDBModalService
  ) {}

  ngOnInit() {
    this.onApplicationFormChangeSub =
      this.applicationFormService.onApplicationFormChange.subscribe(
        (data: any) => {
          if (data) {
            this.applicationForm = data;
            this.isRiskApproved = data.isESGApproved === Constants.yesNoConst.Y;
            this.approvedESGScore = data.approvedESGScore
              ? data.approvedESGScore
              : "";
          }
        }
      );

    this.onAFESGRiskScoreChangeSub =
      this.applicationFormService.onAFESGRiskScoreChange.subscribe(
        (res: any) => {
          if (res && res.length > 0) {
            this.selectedCategories = res
              ? res.sort((a: any, b: any) => a.riskDataId - b.riskDataId)
              : [];
            this.prevSelectedCategories = res
              ? res.sort((a: any, b: any) => a.riskDataId - b.riskDataId)
              : [];
          } else {
            this.selectedCategories = [];
            this.prevSelectedCategories = [];
          }
        }
      );

    this.applicationFormService
      .getEnvironmentalRiskCategories()
      .then((res: any[]) => {
        if (res.length > 0) {
          this.riskCategories = res.map((r: any) => ({
            ...r,
            categoryParentId: r.parentId,
          }));
          this.prepareSelectors();
        }

        this.isLoading = false;
      });
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
    this.onAFESGRiskScoreChangeSub.unsubscribe();
  }

  prepareSelectors() {
    this.selectorList.push({
      id: this.selectorList.length + 1,
      label: "Risk Category",
      value:
        this.selectedCategories.length > 0
          ? this.selectedCategories[0].riskCategoryId
          : "",
      options: this.getCategoryOptions(0),
      disabled: this.isUserAllowAction()
        ? this.selectedCategories.length > 1
        : true,
    });
    if (this.selectedCategories.length > 0) {
      this.selectedCategories.forEach((element: any, index: number) => {
        let selectedItem: any = this.riskCategories.find(
          (r: any) => r.riskCategoryId === element.riskCategoryId
        );

        let parentItem: any = this.riskCategories.find(
          (r: any) => r.riskCategoryId === element.categoryParentId
        );

        if (selectedItem && index !== 0) {
          let newId: number = this.selectorList.length + 1;
          this.selectorList.push({
            id: newId,
            label: `${parentItem ? parentItem.description : ""} Risk Category`,
            value: element.riskCategoryId,
            options: this.getCategoryOptions(element.categoryParentId),
            disabled: this.isUserAllowAction()
              ? this.selectedCategories.length > index + 1
              : true,
          });

          if (selectedItem.score !== null) {
            this.selectedScoreItem = selectedItem;
          }
        }
      });
    }
  }

  handleSelectCategory(selectedId: any, index: number) {
    let selectedItem: any = this.riskCategories.find(
      (r: any) => r.riskCategoryId === selectedId
    );
    if (selectedId && selectedItem) {
      if (selectedItem.score == null) {
        this.selectorList.push({
          id: this.selectorList.length + 1,
          label: `${selectedItem.description} Risk Category`,
          value: "",
          options: this.getCategoryOptions(selectedId),
          disabled: false,
        });

        this.selectorList = this.selectorList.map((s: any) =>
          s.id == index ? { ...s, disabled: true } : s
        );
        this.selectedScoreItem = null;
      } else {
        this.selectedScoreItem = selectedItem;
      }

      let prevItem: any = this.prevSelectedCategories.find(
        (r: any) => r.riskCategoryId === selectedItem.riskCategoryId
      );

      if (
        !this.selectedCategories.some(
          (sc: any) =>
            sc.categoryParentId == selectedItem.categoryParentId ||
            sc.riskCategoryId == selectedItem.riskCategoryId
        )
      ) {
        this.selectedCategories.push(
          prevItem !== undefined && prevItem !== null ? prevItem : selectedItem
        );
      } else {
        this.selectedCategories = this.selectedCategories.filter(
          (sc: any) => sc.categoryParentId != selectedItem.categoryParentId
        );
        this.selectedCategories.push(
          prevItem !== undefined && prevItem !== null ? prevItem : selectedItem
        );
      }
    }
  }

  getCategoryOptions(id: number) {
    return this.riskCategories
      .filter((rc: any) => rc.categoryParentId === id)
      .map((d: any) => ({
        value: d.riskCategoryId,
        label: `${d.description} ${d.score ? ` - (${d.score})` : ""}`,
      }));
  }

  handleSave() {
    let payload: any = {
      applicationFormId: this.applicationForm.applicationFormID,
      categories: this.selectedCategories.map((sc: any) => ({
        ...sc,
        applicationFormId: this.applicationForm.applicationFormID,
      })),
    };
    this.applicationFormService
      .saveEnvironmentalRiskCategory(payload)
      .then((res: any[]) => {
        if (res.length > 0) {
          this.selectedCategories = res;
        }
      });
  }

  handleClear() {
    this.selectorList = [];
    this.selectedCategories = this.prevSelectedCategories;
    this.selectedScoreItem = null;
    this.prepareSelectors();
  }

  handleRemove(item: any) {
    this.selectorList = this.selectorList.filter((s: any) => s.id !== item.id);
    this.selectedCategories = this.selectedCategories.filter(
      (sc: any) => sc.riskCategoryId !== item.value
    );

    this.selectorList = this.selectorList.map((sl: any, idx: number) => ({
      ...sl,
      disabled: this.selectorList.length != idx + 1,
    }));
    this.selectedScoreItem = null;
  }

  isSaveEnabled() {
    return (
      this.selectedCategories.some((sc: any) => sc.score && sc.score != null) &&
      JSON.stringify(this.selectedCategories) !==
        JSON.stringify(this.prevSelectedCategories)
    );
  }

  isRemoveShow(index: number) {
    return index === this.selectorList.length - 1 && index !== 0;
  }

  getRiskGradingBg(score: string) {
    switch (score) {
      case "A":
        return "#dc3545";
      case "B":
        return "#E4D84C";
      case "B+":
        return "#FFBF00";
      case "C":
        return "#00ff00";
      default:
        return "#b7b7b7";
    }
  }

  isRemoveEnabled() {
    return this.isUserAllowAction() && this.prevSelectedCategories.length > 0;
  }

  removeRiskScore() {
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
        heading: "Confirm Risk Score Deletion",
        message: `Do you want to remove this selected risk score?`,
      },
    });

    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.applicationFormService
          .removeAFEnvironmentalRisk(this.applicationForm.applicationFormID)
          .then((res: any[]) => {
            this.selectedCategories = [];
            this.prevSelectedCategories = [];
            this.selectorList = [];
            this.selectedScoreItem = null;
            this.prepareSelectors();
          });
      }
    });
  }

  isUserAllowAction() {
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

  isAllowedPaper() {
    return (
      this.applicationForm.currentApplicationFormStatus !==
        Constants.applicationFormCurrentStatusConst.PAPER_CREATED &&
      this.applicationForm.currentApplicationFormStatus !==
        Constants.applicationFormCurrentStatusConst.DECLINED
    );
  }

  isRiskUser() {
    let loggedInUserDiv: string = this.applicationService
      .getLoggedInUserDivCode()
      .toString();
    return loggedInUserDiv === SETTINGS.ESG_DIV_CODE.toString();
  }

  isAllowedUser() {
    return (
      this.applicationForm.assignUserID ==
      this.applicationService.getLoggedInUserUserID()
    );
  }
}
