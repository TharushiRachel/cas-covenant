import { Component, OnInit } from "@angular/core";
import { Subject } from "rxjs";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { ApplicationService } from "../../../../../../../core/service/application/application.service";
import { Constants } from "../../../../../../../core/setting/constants";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";

@Component({
  selector: "app-fp-return-to-initiated-user",
  templateUrl: "./fp-return-to-agent.component.html",
  styleUrls: ["./fp-return-to-agent.component.scss"],
})
export class FpReturnToAgentComponent implements OnInit {
  content: any = {};
  formError: {};
  componentForm: FormGroup;
  heading: string;
  message: string;
  action: Subject<any> = new Subject<any>();
  facilityStatus = Constants.facilityPaperStatusConst;
  facilityRoutingStatusConst = Constants.facilityRoutingStatusConst;
  facilityRoutingStatus = Constants.facilityRoutingStatus;
  forwardTypeConst = Constants.ForwardTypeConst;

  constructor(
    public mdbModalRef: MDBModalRef,
    public applicationService: ApplicationService,
    private formBuilder: FormBuilder,
    private facilityPaperAddEditService: FacilityPaperAddEditService
  ) {}

  ngOnInit() {
    this.formError = {
      comment: {},
    };

    this.componentForm = this.createComponentForm();
  }

  createComponentForm() {
    return this.formBuilder.group({
      comment: ["", [Validators.required, Validators.maxLength(4000)]],
    });
  }

  isValid(facilityPaperStatus) {
    if (
      facilityPaperStatus === this.facilityStatus.APPROVED ||
      facilityPaperStatus === this.facilityStatus.REJECTED
    ) {
      return true;
    } else {
      return this.componentForm.valid;
    }
  }

  onNoClick(): void {
    this.action.next(false);
    this.mdbModalRef.hide();
  }

  returnToAgent(): void {
    let actionMessage = "";
    switch (this.content.routingStatus) {
      case this.facilityRoutingStatus.BACK:
        actionMessage = `Return to agent by ${this.applicationService.getLoggedInUserDisplayName()}`;
        break;
      case null:
        break;
    }

    let returnData = Object.assign(
      {},
      {
        facilityPaperID: this.content.facilityPaper.facilityPaperID,
        facilityPaperStatus: this.facilityStatus.CANCEL,
        comment: this.componentForm.controls.comment.value,
        fpCommentDTO: {
          comment: this.componentForm.controls.comment.value,
          isUsersOnly: "N",
          isDivisionOnly: "N",
          isPublic: "Y",
          createdUserID: this.applicationService.getLoggedInUserUserID(),
          createdUser: this.applicationService.getLoggedInUserUserName(),
          createdUserDisplayName:
            this.applicationService.getLoggedInUserDisplayName(),
          createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
          createdUserUpmCode:
            this.applicationService.getLoggedInUserUPMGroupCode(),
          actionMessage: actionMessage,
          currentFacilityPaperStatus: this.facilityStatus.CANCEL,
        },
        forwardType: this.forwardTypeConst.DIRECT_USER,
        routingStatus: this.facilityRoutingStatusConst.BACK,
        updatedByUserDisplayName:
          this.applicationService.getLoggedInUserDisplayName(),
        actionMessage: actionMessage,
        fpRefNumber: this.content.facilityPaper.fpRefNumber,
        isFPCancelToAgent: "Y",
      }
    );

    this.facilityPaperAddEditService.updateFacilityPaper(returnData, false);
    this.action.next(true);
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.action.unsubscribe();
  }
}
