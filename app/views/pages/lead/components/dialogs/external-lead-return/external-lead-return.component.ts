import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subject, Subscription } from "rxjs";
import { Constants } from "../../../../../../core/setting/constants";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { LeadAddEditService } from "../../../services/lead-add-edit.service";
import { AppUtils } from "../../../../../../shared/app.utils";
import { ApplicationService } from "../../../../../../core/service/application/application.service";

@Component({
  selector: "app-external-lead-return",
  templateUrl: "./external-lead-return.component.html",
  styleUrls: ["./external-lead-return.component.scss"],
})
export class ExternalLeadReturnComponent implements OnInit, OnDestroy {
  componentForm: FormGroup;
  formErrors: any = {};
  action: Subject<any> = new Subject<any>();

  onFormValueChangeSub = new Subscription();
  onLeadStatusUpdatedSub = new Subscription();

  leadStatusConst = Constants.leadStatusConst;
  leadActionConst = Constants.leadActionConst;

  leadID;
  createdBy;
  createdUserDisplayName;
  users: any[] = [];
  userOption: any[] = [];
  createdFromExternal: boolean = false;
  selectedUser: any = null;

  constructor(
    public mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private leadAddEditService: LeadAddEditService,
    private applicationService: ApplicationService
  ) {
    this.formErrors = {
      returnUser: {},
      remark: {},
    };
  }

  ngOnInit() {
    if (this.createdFromExternal) {
      this.getBranchEnters();
    }
    this.componentForm = this.createCommentForm();

    this.onLeadStatusUpdatedSub =
      this.leadAddEditService.onLeadStatusUpdated.subscribe(() => {
        this.mdbModalRef.hide();
      });

    this.onFormValueChangeSub = this.componentForm.valueChanges.subscribe(
      (form) => {
        this.formErrors = AppUtils.getFormErrors(
          this.componentForm,
          this.formErrors
        );
      }
    );
  }

  ngOnDestroy(): void {
    this.onFormValueChangeSub.unsubscribe();
    this.onLeadStatusUpdatedSub.unsubscribe();
    this.action.unsubscribe();
  }

  createCommentForm() {
    return this.formBuilder.group({
      returnUser: ["", this.createdFromExternal ? [Validators.required] : []],
      remark: ["", [Validators.required, Validators.maxLength(4000)]],
    });
  }

  onReturn() {
    let data = {
      leadID: this.leadID,
      leadStatus: this.leadStatusConst.RETURNED,
      remark: this.componentForm.controls.remark.value,
      assignUserID:
        this.createdFromExternal && this.selectedUser !== null
          ? this.selectedUser.adUserID
          : this.createdBy,
      assignUserDisplayName:
        this.createdFromExternal && this.selectedUser !== null
          ? this.selectedUser.userDisplayName
          : this.createdUserDisplayName,
      actionedByDisplayName:
        this.applicationService.getLoggedInUserDisplayName(),
      action: this.leadActionConst.RETURNED,
    };

    this.leadAddEditService.updateLeadStatusOrAssignee(data);
    this.action.next(true);
    this.mdbModalRef.hide();
  }

  isValid() {
    return this.componentForm.valid;
  }

  getBranchEnters() {
    this.applicationService
      .getUserDetailListFormBranchAuthorityLevel({
        solId: this.applicationService.getLoggedInUserDivCode(),
        roleId: Constants.applicationSecurityWorkClass.ENTERER,
        appCode: "CAS",
      })
      .then((data: any[]) => {
        if (data !== null) {
          this.users = data;
          data.forEach((user) => {
            this.userOption.push({
              value: user.userID,
              label: user.userID
                ? user.firstName + "  " + user.lastName
                : "No Users",
            });
          });
        }
      });
  }

  handleSelectUser(selectedUserId: any) {
    if (selectedUserId !== undefined && selectedUserId !== null) {
      let user: any = this.users.find((u: any) => u.userID === selectedUserId);
      this.selectedUser = user !== undefined && user !== null ? user : null;
      this.selectedUser = {
        ...this.selectedUser,
        userDisplayName:
          this.selectedUser.firstName + " " + this.selectedUser.lastName,
      };
    }
  }
}
