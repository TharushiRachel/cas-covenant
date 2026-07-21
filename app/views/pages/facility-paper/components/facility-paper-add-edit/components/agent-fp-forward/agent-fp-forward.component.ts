import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { Constants } from "../../../../../../../core/setting/constants";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { ApplicationService } from "../../../../../../../core/service/application/application.service";
import { AppUtils } from "../../../../../../../shared/app.utils";
import { Router } from "@angular/router";

@Component({
  selector: "app-agent-fp-forward",
  templateUrl: "./agent-fp-forward.component.html",
  styleUrls: ["./agent-fp-forward.component.scss"],
})
export class AgentFpForwardComponent implements OnInit, OnDestroy {
  content: any = {};
  componentForm: FormGroup;
  assignedUser: any = {};
  formError: {};
  onUserNameChangeSub = new Subscription();
  onADUserChangeSub = new Subscription();

  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityRoutingStatusConst = Constants.facilityRoutingStatusConst;
  forwardTypeConst = Constants.ForwardTypeConst;

  constructor(
    public mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    public applicationService: ApplicationService,
    private router: Router
  ) {}

  ngOnInit() {
    this.formError = {
      comment: {},
    };

    this.componentForm = this.createComponentForm();

    this.onUserNameChangeSub = this.componentForm.valueChanges.subscribe(
      (form) => {
        this.formError = AppUtils.getFormErrors(
          this.componentForm,
          this.formError
        );
      }
    );

    if (
      this.content.facilityPaper.currentFacilityPaperStatus ==
        this.facilityPaperStatusConst.DRAFT ||
      this.content.facilityPaper.currentFacilityPaperStatus ==
        this.facilityPaperStatusConst.CANCEL
    ) {
      let agentSupervisorADUserID =
        this.applicationService.getAgentSupervisorADUserID();
      this.onADUserChangeSub = this.applicationService
        .getUpmDetailsByAdUserIdAndAppCode(agentSupervisorADUserID)
        .subscribe((response: any) => {
          if (response.userID) {
            this.assignedUser = response;
          } else {
            // this.assignedUser.userID = 1;
            // this.assignedUser.adUserID = 'SYSTEM';
            // this.assignedUser.firstName = 'Itechro';
            // this.assignedUser.lastName = 'Admin';
          }
        });
    }
  }

  ngOnDestroy(): void {
    this.onUserNameChangeSub.unsubscribe();
    this.onADUserChangeSub.unsubscribe();
  }

  createComponentForm() {
    return this.formBuilder.group({
      comment: ["", [Validators.required, Validators.maxLength(4000)]],
    });
  }

  isValid() {
    return this.componentForm.valid;
  }

  forward() {
    let actionMessage = `Forwarded to ${
      this.assignedUser.firstName
        ? this.assignedUser.firstName + " " + this.assignedUser.lastName
        : null
    }`;
    let assignUserDisplayName;

    assignUserDisplayName = this.assignedUser.firstName
      ? this.assignedUser.firstName + " " + this.assignedUser.lastName
      : " ";
    let data = Object.assign(
      {},
      {
        facilityPaperID: this.content.facilityPaper.facilityPaperID,
        facilityPaperStatus: this.content.facilityPaperStatus,
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
          currentFacilityPaperStatus: this.content.facilityPaperStatus,
        },
        routingStatus: this.facilityRoutingStatusConst.NEXT,
        forwardType: this.forwardTypeConst.DIRECT_USER,
        assignUserID: this.assignedUser.userID,
        assignADUserID: this.assignedUser.adUserID,
        assignUserDisplayName: assignUserDisplayName,
        updatedByUserDisplayName:
          this.applicationService.getLoggedInUserDisplayName(),
        assignUserUpmID: this.assignedUser.userID,
        assignUserUpmGroupCode: this.assignedUser.applicationSecurityClass,
        assignUser: this.assignedUser.adUserID
          ? this.assignedUser.adUserID
          : null,
        assignUserEmailAddress: this.assignedUser.email,
        authorityLevel: this.assignedUser.daLevel,
        actionMessage: actionMessage,
        fpRefNumber: this.content.facilityPaper.fpRefNumber,
      }
    );
    this.facilityPaperAddEditService.updateFacilityPaper(data, true);
    this.mdbModalRef.hide();
    // this.router.navigate(['/my-facility-papers']);
  }
}
