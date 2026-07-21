import { Component, OnDestroy, OnInit } from "@angular/core";
import { Constants } from "../../../../../../../../../core/setting/constants";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Subscription } from "rxjs";
import { ApplicationFormAddEditService } from "../../../../../services/application-form-add-edit.service";
import * as _ from "lodash";
import { ApfAddEditJoiningPartnersComponent } from "../../../support-components/apf-add-edit-joining-partners/apf-add-edit-joining-partners.component";
import { ConfirmationDialogComponent } from "../../../../../../../../../shared/components/confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: "app-apf-basic-information",
  templateUrl: "./apf-basic-information.component.html",
  styleUrls: ["./apf-basic-information.component.scss"],
})
export class ApfBasicInformationComponent implements OnInit, OnDestroy {
  modalRef: MDBModalRef;
  onApplicationFormChangeSub = new Subscription();
  applicationForm: any = {};
  primaryBasicDetail: any = {};
  joiningBasicDetails: any = [];
  basicDetails = [];
  selectedBasicInformationMap = [];
  selectedTabIndex: number = 0;
  basicInformationTypeConst = Constants.basicInformationTypeConst;
  basicInformationType = Constants.basicInformationType;
  yesNoConst = Constants.yesNoConst;
  analyticsDecision: any = null;
  constructor(
    private mdbModalService: MDBModalService,
    private applicationFormAddEditService: ApplicationFormAddEditService,
  ) {}

  ngOnInit() {
    this.onApplicationFormChangeSub =
      this.applicationFormAddEditService.onApplicationFormChange.subscribe(
        (res: any) => {
          if (!_.isEmpty(res)) {
            this.applicationForm = res;
            if (
              this.applicationForm !== null &&
              this.applicationForm.analyticsDecision !== null &&
              this.analyticsDecision === null
            ) {
              this.analyticsDecision = this.applicationForm.analyticsDecision;
            }
            _.forEach(
              this.applicationForm.basicInformationDTOList,
              (basicInformation) => {
                this.selectedBasicInformationMap[
                  basicInformation.basicInformationID
                ] = basicInformation;
              },
            );
          }
        },
      );

    this.onApplicationFormChangeSub =
      this.applicationFormAddEditService.onApplicationFormBasicDetailsChange.subscribe(
        (res: any) => {
          if (!_.isEmpty(res)) {
            this.applicationForm = res;
            this.joiningBasicDetails = [];
            this.basicDetails = [];
            this.basicDetails = res.basicInformationDTOList;

            _.forEach(
              this.applicationForm.basicInformationDTOList,
              (basicInformation) => {
                this.selectedBasicInformationMap[
                  basicInformation.basicInformationID
                ] = basicInformation;
              },
            );
          }
        },
      );
  }

  ngOnDestroy(): void {
    this.onApplicationFormChangeSub.unsubscribe();
  }

  saveOrUpdateBasicDetails($event, basicInformation) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    this.modalRef = this.mdbModalService.show(
      ApfAddEditJoiningPartnersComponent,
      {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: false,
        ignoreBackdropClick: true,
        class: "modal-width-85-p modal-dialog-scrollable",
        containerClass: "",
        animated: false,
        data: {
          heading: `Basic Information`,
          content: {
            applicationForm: this.applicationForm,
            basicInformation: basicInformation,
          },
        },
      },
    );
  }

  removeBasicDetails($event, item) {
    if ($event) {
      $event.stopPropagation();
      $event.preventDefault();
    }

    if (!_.isEmpty(item)) {
      item.status = Constants.statusConst.INA;

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
          heading: "Confirm Remove Joining Parties",
          message: `Do you want to remove this party ?`,
        },
      });
      this.modalRef.content.action.subscribe((isYes: any) => {
        if (isYes) {
          this.applicationFormAddEditService.saveOrUpdateAFBasicDetails(item);
          this.selectedTabIndex = 0;
        }
      });
    }
  }

  setTabIndex($event) {
    this.selectedTabIndex = $event;
  }

  getActionName() {
    return this.basicDetails.length === 0
      ? "Add Basic Details"
      : "Add Joining Parties";
  }

  isAbleToEdit() {
    return this.applicationFormAddEditService.isAbleToEdit();
  }

  isNonFinacleCustomer() {
    return (
      !this.applicationForm.basicInformationDTOList &&
      this.applicationForm.basicInformationDTOList.length > 0 &&
      this.applicationForm.basicInformationDTOList[0].afCustomerDTO &&
      this.applicationForm.basicInformationDTOList[0].afCustomerDTO
        .customerFinancialID
    );
  }
}
