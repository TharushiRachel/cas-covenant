import { Component, OnDestroy, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import * as _ from "lodash";
import { FacilityPaperAddEditService } from "../../../../services/facility-paper-add-edit.service";
import { AppUtils } from "../../../../../../../shared/app.utils";
import { Constants } from "../../../../../../../core/setting/constants";
import { ApplicationService } from "../../../../../../../core/service/application/application.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-fp-approve",
  templateUrl: "./fp-approve.component.html",
  styleUrls: ["./fp-approve.component.scss"],
})
export class FpApproveComponent implements OnInit, OnDestroy {
  content: any = {};
  componentForm: FormGroup;
  groupValChange: boolean = false;
  showDirectAdvanceOptions: boolean = false;
  isDirect: boolean = false;
  assignedUser: any = {};
  formError: any = {};
  upmGroups: any = {};
  groupOptions: any[] = [];
  directUserOptions: any[] = [];
  directUsers: any[] = [];
  userDetails = [];
  userOption: any[] = [];
  fStatus: any = "";
  rStatus: any = "";

  onUpmGroupChangeSub = new Subscription();
  onUpmGroupCodeChangeSub = new Subscription();
  onUserDetailFromBranchAuthortiyChangeSub = new Subscription();
  onUserNameChangeSub = new Subscription();
  onFPDirectReturnUsersListSub = new Subscription();

  facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityRoutigStatus = Constants.facilityRoutingStatus;
  facilityReturnType = Constants.facilityReturnType;

  constructor(
    public mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private facilityPaperAddEditService: FacilityPaperAddEditService,
    public applicationService: ApplicationService,
    private router: Router
  ) {}

  ngOnInit() {
    if (this.content.facilityPaperStatus == this.facilityStatus.CANCEL) {
      this.facilityPaperAddEditService.getFPDirectReturnUsersList(
        this.content.facilityPaper
      );
      this.showDirectAdvanceOptions = true;
    }

    this.getUpmGroups();

    this.formError = {
      comment: {},
      group: {},
      user: {},
    };

    this.onUpmGroupChangeSub =
      this.facilityPaperAddEditService.onUpmGroupChange.subscribe(
        (upmGroupList: any) => {
          this.upmGroups = upmGroupList;
        }
      );

    _.forEach(this.upmGroups, (group) => {
      this.groupOptions.push({
        value: group.groupCode,
        label: group.referenceName,
      });
    });
    this.componentForm = this.createComponentForm();

    switch (this.content.facilityPaperStatus) {
      case this.facilityStatus.APPROVED:
      case this.facilityStatus.REJECTED:
        this.componentForm.controls.group.setValidators(null);
        this.componentForm.controls.user.setValidators(null);
        break;
      case this.facilityStatus.IN_PROGRESS:
      case this.facilityStatus.CANCEL:
        this.componentForm.controls.group.setValidators([Validators.required]);
        this.componentForm.controls.user.setValidators([Validators.required]);
        break;
    }

    this.onUpmGroupCodeChangeSub.unsubscribe();
    this.onUpmGroupCodeChangeSub =
      this.componentForm.controls.group.valueChanges.subscribe((value: any) => {
        if (value) {
          this.groupValChange = true;
          this.getUserDetailListFormBranchAuthorityLevel(value);
        }
        this.componentForm.controls.user.reset();
        this.componentForm.updateValueAndValidity();
      });

    this.onUserDetailFromBranchAuthortiyChangeSub =
      this.facilityPaperAddEditService.onUserDetailFromBrachAuthorityChange.subscribe(
        (data: any) => {
          let userList = [];
          this.userOption = [];
          this.userDetails = data;
          _.forEach(_.sortBy(this.userDetails, ["firstName"]), (user) => {
            if (!_.isNull(user.userID)) {
              userList.push({
                value: user.userID,
                label: user.userID
                  ? user.firstName + "  " + user.lastName
                  : "No Users",
              });
            }
          });
          this.userOption = userList;
        }
      );

    this.onUserNameChangeSub =
      this.componentForm.controls.user.valueChanges.subscribe((value: any) => {
        if (value) {
          this.assignedUser = AppUtils.getFacilityPaperAssignedUserFromUserID(
            this.userDetails,
            value
          );
        }
      });

    this.onUserNameChangeSub =
      this.componentForm.controls.type.valueChanges.subscribe((value: any) => {
        if (value == this.facilityReturnType.DIRECT) {
          this.componentForm.controls.directReturnUser.setValidators([
            Validators.required,
          ]);
          this.componentForm.controls.directReturnUser.reset();

          this.componentForm.controls.group.setValidators(null);
          this.componentForm.controls.group.reset();

          this.componentForm.controls.user.setValidators(null);
          this.componentForm.controls.user.reset();

          this.assignedUser = {};
          this.isDirect = true;
          this.componentForm.updateValueAndValidity();
        } else {
          this.componentForm.controls.group.setValidators([
            Validators.required,
          ]);
          this.componentForm.controls.group.reset();

          this.componentForm.controls.user.setValidators([Validators.required]);
          this.componentForm.controls.user.reset();

          this.componentForm.controls.directReturnUser.setValidators(null);
          this.componentForm.controls.directReturnUser.reset();

          this.assignedUser = {};
          this.isDirect = false;
          this.componentForm.updateValueAndValidity();
        }
      });

    this.onUserNameChangeSub =
      this.componentForm.controls.directReturnUser.valueChanges.subscribe(
        (assignUserID: any) => {
          if (assignUserID) {
            let user = _.find(
              this.directUsers,
              (user) => user.assignUserID == assignUserID
            );
            this.assignedUser = {};
            this.assignedUser.userID = user.assignUserID;
            this.assignedUser.adUserID = user.assignUser;
            this.assignedUser.firstName = user.assignUserDisplayName;
            this.assignedUser.upmGroupCode = user.assignUserUpmGroupCode;
          }
        }
      );

    this.onUserNameChangeSub = this.componentForm.valueChanges.subscribe(
      (form) => {
        this.formError = AppUtils.getFormErrors(
          this.componentForm,
          this.formError
        );
      }
    );
  }

  ngOnDestroy(): void {
    this.onUpmGroupChangeSub.unsubscribe();
    this.onUpmGroupCodeChangeSub.unsubscribe();
    this.onUserDetailFromBranchAuthortiyChangeSub.unsubscribe();
    this.onUserNameChangeSub.unsubscribe();
    this.onFPDirectReturnUsersListSub.unsubscribe();
  }

  createComponentForm() {
    return this.formBuilder.group({
      comment: ["", [Validators.required, Validators.maxLength(4000)]],
      group: [""],
      user: [""],
      directReturnUser: [""],
      type: [this.facilityReturnType.DIRECT],
    });
  }

  isValid() {
    return this.componentForm.valid;
  }

  getUpmGroups() {
    let loggedInUserWorkFlowRQ = {
      loggedInUserUpmGroupCode:
        this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
      workFlowTemplateID: this.content.facilityPaper.workflowTemplateID,
    };

    if (this.content.facilityPaper.workflowTemplateID != null) {
      this.facilityPaperAddEditService.getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(
        loggedInUserWorkFlowRQ
      );
    }
  }

  getUserDetailListFormBranchAuthorityLevel(groupCode) {
    let data = Object.assign(
      {},
      { solId: this.content.facilityPaper.branchCode },
      { roleId: groupCode },
      { appCode: "" }
    );
    this.facilityPaperAddEditService.getEligibleUsers(
      this.content.facilityPaper.createdBy,
      this.content.facilityPaper.currentAssignUser,
      data
    );
  }

  submitForApprove() {
    let actionMessage = "";

    switch (this.content.facilityPaperStatus) {
      case this.facilityStatus.APPROVED:
        this.fStatus = "APPROVED";
        actionMessage = `Approved by ${this.applicationService.getLoggedInUserDisplayName()}`;
        break;
      case this.facilityStatus.IN_PROGRESS:
        this.fStatus = "IN_PROGRESS";
        break;
      case this.facilityStatus.REJECTED:
        this.fStatus = "REJECTED";
        actionMessage = `Declined by ${this.applicationService.getLoggedInUserDisplayName()}`;
        break;
      case this.facilityStatus.CANCEL:
        this.fStatus = "CANCEL";
        break;
    }

    switch (this.content.routingStatus) {
      case this.facilityRoutigStatus.RECOMMENDED:
        this.rStatus = "RECOMMENDED";
        break;
      case this.facilityRoutigStatus.NEXT:
        this.rStatus = "NEXT";
        actionMessage = `Forwarded to ${
          this.assignedUser.firstName
            ? this.assignedUser.lastName
              ? this.assignedUser.firstName
                  .concat(" ")
                  .concat(this.assignedUser.lastName)
              : this.assignedUser.firstName
            : this.assignedUser.lastName
            ? this.assignedUser.lastName
            : null
        }`;
        break;
      case this.facilityRoutigStatus.BACK:
        this.rStatus = "BACK";
        actionMessage = `Return to ${
          this.assignedUser.firstName
            ? this.assignedUser.lastName
              ? this.assignedUser.firstName
                  .concat(" ")
                  .concat(this.assignedUser.lastName)
              : this.assignedUser.firstName
            : this.assignedUser.lastName
            ? this.assignedUser.lastName
            : null
        }`;
        break;
      case null:
        this.rStatus = null;
        break;
    }

    let upmID;
    let upmGroupCode;
    let assignUserDisplayName;
    if (
      this.fStatus == this.facilityPaperStatusConst.APPROVED ||
      this.fStatus == this.facilityPaperStatusConst.REJECTED
    ) {
      this.assignedUser.userID =
        this.applicationService.getLoggedInUserUserID();
      this.assignedUser.adUserID =
        this.applicationService.getLoggedInUserUserName();
      assignUserDisplayName =
        this.applicationService.getLoggedInUserDisplayName();
      upmID = this.applicationService.getLoggedInUserUserID();
      upmGroupCode = this.applicationService.getLoggedInUserUPMGroupCode();
    } else if (
      this.fStatus == this.facilityPaperStatusConst.IN_PROGRESS ||
      this.fStatus == this.facilityPaperStatusConst.CANCEL
    ) {
      assignUserDisplayName = this.assignedUser.firstName
        ? this.assignedUser.lastName
          ? this.assignedUser.firstName
              .concat(" ")
              .concat(this.assignedUser.lastName)
          : this.assignedUser.firstName
        : this.assignedUser.lastName
        ? this.assignedUser.lastName
        : null;
      upmID = this.assignedUser.userID;
      upmGroupCode = this.componentForm.controls.group.value
        ? this.componentForm.controls.group.value
        : this.assignedUser.upmGroupCode;
    }

    let approveData = Object.assign(
      {},
      { facilityPaperID: this.content.facilityPaper.facilityPaperID },
      { facilityPaperStatus: this.fStatus },
      { comment: this.componentForm.controls.comment.value },
      { routingStatus: this.rStatus },
      { assignUserID: this.assignedUser.userID },
      { assignADUserID: this.assignedUser.adUserID },
      { assignUserDisplayName: assignUserDisplayName },
      {
        updatedByUserDisplayName:
          this.applicationService.getLoggedInUserDisplayName(),
      },
      { upmID: upmID },
      { upmGroupCode: upmGroupCode },
      {
        assignUser: this.assignedUser.adUserID
          ? this.assignedUser.adUserID
          : null,
      },
      { assignUserEmailAddress: this.assignedUser.email },
      { authorityLevel: this.assignedUser.daLevel },
      { workflowLevel: this.upmGroups.workFlowLevel },
      { actionMessage: actionMessage },
      { fpRefNumber: this.content.facilityPaper.fpRefNumber }
    );

    this.facilityPaperAddEditService.updateFacilityPaper(
      AppUtils.trim(approveData),
      true
    );
    this.mdbModalRef.hide();
    // this.router.navigate(['/my-facility-papers']);
  }
}
