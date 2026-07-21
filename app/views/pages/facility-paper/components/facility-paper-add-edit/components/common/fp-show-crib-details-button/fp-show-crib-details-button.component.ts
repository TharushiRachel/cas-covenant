import { Component, Input, OnInit } from "@angular/core";
import * as _ from "lodash";
import { ShowCribHistoryComponent } from "../../../../../../../../shared/components/show-crib-history/show-crib-history.component";
import { AppUtils } from "../../../../../../../../shared/app.utils";
import { SETTINGS } from "../../../../../../../../core/setting/commons.settings";
import { ShowCribDetailsComponent } from "../../../../../../../../shared/components/show-crib-details/show-crib-details.component";
import { CribDetailsSaveDTO } from "../../../../../../../../shared/dto/CribDetailsSaveDTO";
import { Constants } from "../../../../../../../../core/setting/constants";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { CasCribServiceService } from "../../../../../../../../core/service/data/cas-crib-service.service";
import { AlertService } from "../../../../../../../../core/service/common/alert.service";
import { FacilityPaperAddEditService } from "../../../../../services/facility-paper-add-edit.service";
import { ApplicationService } from "../../../../../../../../core/service/application/application.service";

@Component({
  selector: "app-fp-show-crib-details-button",
  templateUrl: "./fp-show-crib-details-button.component.html",
  styleUrls: ["./fp-show-crib-details-button.component.scss"],
})
export class FpShowCribDetailsButtonComponent implements OnInit {
  modalRef: MDBModalRef;
  @Input("customer") customer: any = {};
  @Input("facilityPaper") facilityPaper: any;
  customerIdentificationDTOList = [];

  statusConst = Constants.statusConst;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  customerIdentificationType = Constants.customerIdentificationType;

  constructor(
    private mdbModalService: MDBModalService,
    private casCribServiceService: CasCribServiceService,
    private alertService: AlertService,
    private applicationService: ApplicationService,
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.customerIdentificationDTOList = _.filter(
      this.customer.casCustomerIdentificationDTOList,
      (identity) => identity.status == this.statusConst.ACT
    );
  }

  viewCustomerCribDetails(customer) {
    let nic = null;
    let brcNo = null;
    let identificationDetails = null;
    if (
      this.customerIdentificationDTOList &&
      this.customerIdentificationDTOList.length > 0
    ) {
      for (let i = 0; i < this.customerIdentificationDTOList.length; i++) {
        if (this.customerIdentificationDTOList[i].identificationType == "NIC") {
          nic = this.customerIdentificationDTOList[i].identificationNumber
            ? this.customerIdentificationDTOList[i].identificationNumber
            : null;
          identificationDetails = this.customerIdentificationDTOList[i];
          break;
        }
        // else if (this.customerIdentificationDTOList[i].identificationType == 'BRC') {
        //   brcNo = this.customerIdentificationDTOList[i].identificationNumber ? this.customerIdentificationDTOList[i].identificationNumber : null;
        //   identificationDetails = this.customerIdentificationDTOList[i];
        //   break;
        // }
      }
      if (!_.isEmpty(identificationDetails)) {
        this.modalRef = this.mdbModalService.show(ShowCribHistoryComponent, {
          backdrop: true,
          keyboard: true,
          focus: true,
          show: false,
          ignoreBackdropClick: true,
          class: "modal-dialog-scrollable",
          containerClass: "",
          animated: false,
          data: {
            heading: "Crib Report",
            content: identificationDetails,

            facilityPaperID: this.facilityPaper.facilityPaperID,
          },
        });
      }

      if (AppUtils.isNic(nic)) {
        let retailCribRQ = {
          identificationType: this.customerIdentificationType.NIC,
          identificationNumber: nic,
          customerName: customer.customerName
            ? customer.customerName.toUpperCase()
            : "",
        };

        this.modalRef.content.action.subscribe((isYes: any) => {
          if (isYes) {
            this.casCribServiceService
              .getRetailCribReport(retailCribRQ)
              .then((response: any) => {
                if (response) {
                  this.openModalShowCribDetails(
                    response,
                    identificationDetails
                  );
                }
              })
              .catch((e) => {
                this.alertService.showToaster(
                  e,
                  SETTINGS.TOASTER_MESSAGES.error
                );
              });
          }
        });
      } else {
        let corporateCribRQ = {
          identificationType: this.customerIdentificationType.BRC,
          identificationNumber: brcNo,
          REGNo: brcNo,
          customerName: customer.customerName
            ? customer.customerName.toUpperCase()
            : "",
        };

        this.modalRef.content.action.subscribe((isYes: any) => {
          if (isYes) {
            this.casCribServiceService
              .getCorporateCribReport(corporateCribRQ)
              .then((response: any) => {
                if (response) {
                  this.openModalShowCribDetails(
                    response,
                    identificationDetails
                  );
                }
              })
              .catch((e) => {
                this.alertService.showToaster(
                  e,
                  SETTINGS.TOASTER_MESSAGES.error
                );
              });
          }
        });
      }

      this.modalRef.content.viewReportAction.subscribe((data: any) => {
        if (!_.isEmpty(data)) {
          this.openModalShowCribDetails(data, identificationDetails);
        }
      });
    }
  }

  openModalShowCribDetails(htmlContent: any, identificationDetails) {
    const initialState = {
      list: [{ tag: "Count", value: this.facilityPaper }],
    };
    this.modalRef = this.mdbModalService.show(ShowCribDetailsComponent, {
      initialState,
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-100-p modal-dialog-scrollable min-height-550",
      containerClass: "right",
      animated: false,
      data: {
        htmlContent: htmlContent,
        isEditEnabled: this.isAbleToEdit(),
      },
    });
    this.modalRef.content.actionClickSave.subscribe(
      (data: CribDetailsSaveDTO) => {
        if (!_.isEmpty(data)) {
          let cribReportSaveDto = data;
          cribReportSaveDto.facilityPaperID =
            this.facilityPaper.facilityPaperID;
          cribReportSaveDto.status = this.statusConst.ACT;
          cribReportSaveDto.savedUserDisplayName =
            this.applicationService.getLoggedInUserDisplayName();
          cribReportSaveDto.savedUserDivCode =
            this.applicationService.getLoggedInUserDivCode();
          cribReportSaveDto.casCustomerID = identificationDetails.casCustomerID;
          cribReportSaveDto.identificationType =
            identificationDetails.identificationType;
          cribReportSaveDto.identificationNo =
            identificationDetails.identificationNumber;
          this.facilityPaperAddEditService.saveOrUpdateCribReport(data);
        }
      }
    );
  }

  showCribReportButton() {
    return (
      _.filter(
        this.customer.casCustomerIdentificationDTOList,
        (identity) =>
          identity.status == this.statusConst.ACT &&
          identity.identificationNumber &&
          (identity.identificationType ==
            Constants.customerIdentificationTypeConst.NIC ||
            identity.identificationType ==
              Constants.customerIdentificationTypeConst.BRC)
      ).length > 0
    );
  }

  isEqualLoginAndAssignUser() {
    return (
      this.facilityPaper.currentAssignUserID ==
      this.applicationService.getLoggedInUserUserID()
    );
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

  isAbleToEdit() {
    return (
      this.isEqualLoginAndAssignUser() &&
      !this.isApproveStatus() &&
      !this.isRejected()
    );
  }
}
