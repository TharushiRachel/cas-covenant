import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {Constants} from "../../../../../../../core/setting/constants";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {ApplicationService} from "../../../../../../../core/service/application/application.service";
import {Router} from "@angular/router";
import * as _ from "lodash";
import {AppUtils} from "../../../../../../../shared/app.utils";
import {FacilityPaperTransferService} from "../../../../services/facility-paper-transfer.service";

@Component({
  selector: 'app-fp-transfer-to-user',
  templateUrl: './fp-transfer-to-user.component.html',
  styleUrls: ['./fp-transfer-to-user.component.scss']
})
export class FpTransferToUserComponent implements OnInit, OnDestroy {

  content: any = {};
  componentForm: FormGroup;
  groupValChange: boolean = false;
  assignedUser: any = {};
  formError: any = {};
  upmGroups: any = {};
  groupOptions: any[] = [];
  userDetails = [];
  userOption: any[] = [];
  fStatus: any = '';
  rStatus: any = '';

  onUpmGroupChangeSub = new Subscription();
  onUpmGroupCodeChangeSub = new Subscription();
  onUserDetailFromBranchAuthortiyChangeSub = new Subscription();
  onUserNameChangeSub = new Subscription();
  onFPDirectReturnUsersListSub = new Subscription();

  facilityStatus = Constants.facilityPaperStatusToAuthorityLevel;
  facilityPaperStatusConst = Constants.facilityPaperStatusConst;
  facilityRoutingStatusConst = Constants.facilityRoutingStatusConst;
  forwardTypeConst = Constants.ForwardTypeConst;

  constructor(
    public  mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private facilityPaperTransferService: FacilityPaperTransferService,
    public applicationService: ApplicationService,
    private router: Router,
  ) {
  }

  ngOnInit() {

    this.getUpmGroups();

    this.formError = {
      comment: {},
      group: {},
      user: {}
    };

    this.onUpmGroupChangeSub = this.facilityPaperTransferService.onUpmGroupChange
      .subscribe((upmGroupList: any) => {
        this.groupOptions = [];
        this.upmGroups = upmGroupList;
        _.forEach(this.upmGroups, group => {
          this.groupOptions.push({
            value: group.groupCode,
            label: group.referenceName
          })
        });
      });

    this.componentForm = this.createComponentForm();

    this.onUpmGroupCodeChangeSub.unsubscribe();
    this.onUpmGroupCodeChangeSub = this.componentForm.controls.group.valueChanges
      .subscribe((value: any) => {
        if (value) {
          this.groupValChange = true;
          this.getUserDetailListFormBranchAuthorityLevel(value);
        }
        this.componentForm.controls.user.reset();
        this.componentForm.updateValueAndValidity();
      });

    this.onUserDetailFromBranchAuthortiyChangeSub = this.facilityPaperTransferService.onUserDetailFromBranchAuthorityChange
      .subscribe((data: any) => {
        let userList = [];
        this.userOption = [];
        this.userDetails = data;
        _.forEach(_.sortBy(this.userDetails, ['firstName']), user => {
          if (!_.isNull(user.userID)) {
            userList.push({
              value: user.userID,
              label: user.userID ? user.firstName + '  ' + user.lastName : "No Users"
            })
          }
        });
        this.userOption = userList;
      });

    this.onUserNameChangeSub = this.componentForm.controls.user.valueChanges
      .subscribe((value: any) => {
        if (value) {
          this.assignedUser = AppUtils.getFacilityPaperAssignedUserFromUserID(this.userDetails, value);
        }
      });

    this.onUserNameChangeSub = this.componentForm.valueChanges.subscribe((form) => {
      this.formError = AppUtils.getFormErrors(this.componentForm, this.formError);
    });
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
      comment: ['', [Validators.required, Validators.maxLength(4000)]],
      group: ['', [Validators.required]],
      user: ['', [Validators.required]],
    })
  }

  isValid() {
    return this.componentForm.valid
  }

  getUpmGroups() {

    let loggedInUserWorkFlowRQ = {
      loggedInUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
      workFlowTemplateID: this.content.facilityPaper.workflowTemplateID
    };

    if (this.content.facilityPaper.workflowTemplateID != null) {
      this.facilityPaperTransferService.getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(loggedInUserWorkFlowRQ);
    }
  }

  getUserDetailListFormBranchAuthorityLevel(groupCode) {
    this.facilityPaperTransferService.getEligibleUsers(
      this.content.facilityPaper,
      groupCode);
  }

  transfer() {

    let actionMessage = `Transferred to ${this.assignedUser.firstName ? this.assignedUser.lastName ? this.assignedUser.firstName.concat(' ').concat(this.assignedUser.lastName) : this.assignedUser.firstName : (this.assignedUser.lastName ? this.assignedUser.lastName : null)}`;
    let assignUserDisplayName = this.assignedUser.firstName ? this.assignedUser.lastName ? this.assignedUser.firstName.concat(' ').concat(this.assignedUser.lastName) : this.assignedUser.firstName : (this.assignedUser.lastName ? this.assignedUser.lastName : null);
    let upmGroupCode = this.componentForm.controls.group.value ? this.componentForm.controls.group.value : this.assignedUser.upmGroupCode;

    let data = {
      facilityPaperID: this.content.facilityPaper.facilityPaperID,
      actionMessage: actionMessage,
      fpRefNumber: this.content.facilityPaper.fpRefNumber,
      assignUserID: this.assignedUser.userID,
      assignUserUpmID: this.assignedUser.userID,
      assignADUserID: this.assignedUser.adUserID,
      assignUserSolID: this.assignedUser.divCode,
      assignUserDisplayName: assignUserDisplayName,
      assignUserUpmGroupCode: upmGroupCode,
      assignUser: this.assignedUser.adUserID ? this.assignedUser.adUserID : null,
      updatedByUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      facilityPaperStatus: this.facilityPaperStatusConst.IN_PROGRESS,
      routingStatus: this.facilityRoutingStatusConst.NEXT,
      forwardType: this.forwardTypeConst.DIRECT_USER,
      fpCommentDTO: {
        comment: this.componentForm.controls.comment.value,
        isUsersOnly: 'N',
        isDivisionOnly: 'N',
        isPublic: 'Y',
        createdUserID: this.applicationService.getLoggedInUserUserID(),
        createdUser: this.applicationService.getLoggedInUserUserName(),
        createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
        createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
        createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
        assignedUserID: this.assignedUser.userID,
        assignedUser: this.assignedUser.adUserID,
        assignedUserDisplayName: assignUserDisplayName,
        assignedUserSolID: this.assignedUser.divCode,
        actionMessage: actionMessage,
        currentFacilityPaperStatus: this.facilityPaperStatusConst.IN_PROGRESS,
      },
    };

    this.facilityPaperTransferService.updateFacilityPaper(AppUtils.trim(data));
    this.mdbModalRef.hide();
    this.router.navigate(['/facility-paper-transfer']);
  }
}
