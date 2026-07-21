import { Component, OnInit } from "@angular/core";
import { SaveDeviationComponent } from "../../modal/save-deviation/save-deviation.component";
import { MDBModalRef, MDBModalService } from "ng-uikit-pro-standard";
import { Constants } from "src/app/core/setting/constants";
import { DeviationService } from "../../services/deviation.service";
import { ApplicationService } from "src/app/core/service/application/application.service";
import { ConfirmationDialogComponent } from "src/app/shared/components/confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: "app-deviations",
  templateUrl: "./deviations.component.html",
  styleUrls: ["./deviations.component.scss"],
})
export class DeviationsComponent implements OnInit {
  tableColumns: any = ["Type Name", "Description", "Status", "Action"];
  deviations: any[] = [];
  status = Constants.status;
  statusConst = Constants.statusConst;
  modalRef: MDBModalRef;
  loggedInUserName: string = "";
  constructor(
    private readonly deviationService: DeviationService,
    public mdbModalService: MDBModalService,
    private readonly applicationService: ApplicationService,
  ) {}

  ngOnInit() {
    this.loggedInUserName = this.applicationService.getLoggedInUserUserName();
    this.deviationService.getDeviations().then((res: any[]) => {
      this.deviations = res !== null && res.length > 0 ? res : [];
    });
  }

  openAddEditModal(deviation?: any) {
    this.modalRef = this.mdbModalService.show(SaveDeviationComponent, {
      backdrop: true,
      keyboard: true,
      focus: true,
      show: false,
      ignoreBackdropClick: true,
      class: "modal-width-50-p",
      containerClass: "",
      animated: false,
      data: {
        deviation:
          deviation !== undefined && deviation !== null
            ? deviation
            : {
                deviationId: 0,
                deviationType: "",
                description: "",
                status: Constants.statusConst.ACT,
              },
      },
    });

    this.modalRef.content.action.subscribe((res: any[]) => {
      if (res !== null) {
        this.deviations = res !== null && res.length > 0 ? res : [];
      }
    });
  }

  handleAuthorized(deviation: any, approveStatus: any) {
    let request: any = {
      ...deviation,
      approveStatus: approveStatus,
    };

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
        heading:
          approveStatus === Constants.approveStatusConst.APPROVED
            ? "Confirm Approve Deviation"
            : "Confirm Reject Deviation",
        message: `Do you want to ${approveStatus === Constants.approveStatusConst.APPROVED ? "approve" : "reject"} this deviation?`,
      },
    });
    this.modalRef.content.action.subscribe((isYes: any) => {
      if (isYes) {
        this.deviationService.authorizeDeviation(request).then((res: any[]) => {
          if (res !== null) {
            this.deviations = res !== null && res.length > 0 ? res : [];
          }
        });
      }
    });
  }

  isValidUser(deviation: any): boolean {
    var isValid: boolean = false;

    if (deviation.createdBy && deviation.modifiedBy) {
      isValid = deviation.modifiedBy != this.loggedInUserName;
    } else if (deviation.createdBy && !deviation.modifiedBy) {
      isValid = deviation.createdBy != this.loggedInUserName;
    }

    return isValid && deviation.isTempRecord;
  }

  isPendingExist(deviation: any): boolean {
    return this.deviations.some(
      (d: any) => d.parentId === deviation.deviationId && d.isTempRecord,
    );
  }
}
