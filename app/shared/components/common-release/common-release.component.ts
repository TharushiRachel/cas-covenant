import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, Subscription} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Constants} from "../../../core/setting/constants";
import {MDBModalRef} from "ng-uikit-pro-standard";
import {CacheService} from "../../../core/service/data/cache.service";
import {MasterDataService} from "../../../core/service/data/master-data.service";
import {ApplicationService} from "../../../core/service/application/application.service";
import {AppUtils} from "../../app.utils";
import * as _ from "lodash";
import {isEmpty} from "lodash";

@Component({
  selector: 'app-common-release',
  templateUrl: './common-release.component.html',
  styleUrls: ['./common-release.component.scss']
})
export class CommonReleaseComponent implements OnInit, OnDestroy {
  action: Subject<any> = new Subject<any>();
  userGroups: any = [];
  assistants: any[] = [];
  content: any = {};
  heading: any = '';
  message: any = '';
  status;
  onUpmGroupChangeSub = new Subscription();
  onFormChangeSub = new Subscription();
  forwardType = Constants.ForwardTypeConst.SAME_SOL_USER_GROUP;
  defaultWorkflowUpmGroupCode = Constants.defaultWorkflowUpmGroupCode;
  defaultWorkflowUpmGroupsName = Constants.defaultWorkflowUpmGroupsName;
  defaultApplicationFormForwardUPMGroups;
  componentForm: FormGroup;
  userGroupOptions: any[];
  allBankOptions: any = {};

  constructor(
    public  mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private cacheService: CacheService,
    private masterDataService: MasterDataService,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {

    this.assistants = this.cacheService.getData(Constants.masterDataKey.CAS_APPLICATION_USER_ASSISTANTS);
    this.status = this.content.status;
    let loggedInUserWorkFlowRQ = {
      loggedInUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
      workFlowTemplateID: this.content.workflowTemplateID
    };

    this.applicationService.getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(loggedInUserWorkFlowRQ).then(response => {
      this.userGroups = [];
      this.userGroupOptions = [];
      this.userGroups = response;
      _.forEach(this.userGroups, group => {
        this.userGroupOptions.push({
          value: group.groupCode,
          label: group.referenceName
        })
      });
    });

    this.componentForm = this.createComponentForm();

    this.onFormChangeSub = this.componentForm.controls.isDivisionOnly.valueChanges.subscribe(data => {
      if (data) {
        this.componentForm.get('isUsersOnly').setValue(false);
        this.componentForm.get('isPublic').setValue(false);
      } else {
        this.componentForm.get('isPublic').setValue(true);
      }
    });
  }

  release() {

    let userGroupNames = [];
    let loggedInUserBranchCode = this.applicationService.getLoggedInUserDivCode();
    let loggedInUserBranchName = this.getBranchName(loggedInUserBranchCode);
    let actionMessage;
    let assignDepartmentDTOList = [];

    this.defaultApplicationFormForwardUPMGroups = this.masterDataService.getSystemParameter(Constants.systemParamKey.DEFAULT_APPLICATION_FORM_FORWARDING_UPM_GROUPS);
    this.defaultApplicationFormForwardUPMGroups.split(',').forEach(data => {
      let userGroup = _.find(this.userGroupOptions, (b) => b.value == data);
      assignDepartmentDTOList.push({
        divCode: loggedInUserBranchCode,
        departmentName: loggedInUserBranchName,
        userGroupUPMCode: userGroup.value,
        userGroupName: userGroup.label
      });
      userGroupNames.push(userGroup.label);
    });

    if (this.assistants && this.assistants.length > 0) {
      assignDepartmentDTOList.push({
        divCode: loggedInUserBranchCode,
        departmentName: loggedInUserBranchName,
        userGroupUPMCode: this.defaultWorkflowUpmGroupCode.ASSISTANT,
        userGroupName: this.defaultWorkflowUpmGroupsName.ASSISTANT
      });
      userGroupNames.push(this.defaultWorkflowUpmGroupsName.ASSISTANT);
    }

    actionMessage = `Released to ${this.getUserGroupNames(userGroupNames)} user group${userGroupNames.length > 1 ? "s" : ""} of ${loggedInUserBranchName}`;

    let remarkData = {
      comment: this.componentForm.controls.comment.value,
      createdUserID: this.applicationService.getLoggedInUserUserID(),
      createdUser: this.applicationService.getLoggedInUserUserName(),
      createdUserDisplayName: this.applicationService.getLoggedInUserDisplayName(),
      createdUserDivCode: this.applicationService.getLoggedInUserDivCode(),
      createdUserUpmCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      isUsersOnly: this.componentForm.controls.isUsersOnly.value ? 'Y' : 'N',
      isDivisionOnly: this.componentForm.controls.isDivisionOnly.value ? 'Y' : 'N',
      isPublic: this.componentForm.controls.isPublic.value ? 'Y' : 'N',
    };

    let saveObj = Object.assign({}, {assignedUser: null},
      {
        actionMessage: actionMessage,
        forwardType: this.forwardType,
        assignDepartmentCode: loggedInUserBranchCode,
      },
      {assignDepartmentDTOList: assignDepartmentDTOList},
      {
        remarkData: {
          ...remarkData,
          actionMessage,
          assignDepartmentSolID: loggedInUserBranchCode,
        }
      });

    this.action.next(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }

  createComponentForm() {
    return this.formBuilder.group({
      comment: [null],//as a request no need to comment on release
      isUsersOnly: [false],
      isDivisionOnly: [false],
      isPublic: [true],
    })
  }

  isValid() {
    return this.componentForm.valid
  }

  ngOnDestroy(): void {
    this.onUpmGroupChangeSub.unsubscribe();
    this.onFormChangeSub.unsubscribe();
  }

  getUserGroupNames(userGroupNames) {
    if (userGroupNames.length > 0) {
      return userGroupNames.toString();
    }
    return ""
  }

  getBranchName(branchCode) {
    this.allBankOptions = this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES);
    let branch = AppUtils.getBranchFromBranchCode(this.allBankOptions, branchCode);

    if (!isEmpty(branch)) {
      return branch.branchName;
    }
    return branchCode;
  }

}
