import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDBModalRef} from "ng-uikit-pro-standard";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import * as _ from "lodash";
import {Subject, Subscription} from "rxjs";
import {ApplicationFormAddEditService} from "../../../../services/application-form-add-edit.service";
import {AppUtils} from "../../../../../../../../shared/app.utils";
import {Constants} from "../../../../../../../../core/setting/constants";
import {CacheService} from "../../../../../../../../core/service/data/cache.service";
import {MasterDataService} from "../../../../../../../../core/service/data/master-data.service";
import {ApplicationService} from "../../../../../../../../core/service/application/application.service";

@Component({
  selector: 'app-apf-forward-application-form',
  templateUrl: './apf-forward-application-form.component.html',
  styleUrls: ['./apf-forward-application-form.component.scss']
})
export class ApfForwardApplicationFormComponent implements OnInit, OnDestroy {
  action: Subject<any> = new Subject<any>();
  upmGroups: any = {};
  content: any = {};
  heading: any = '';
  applicationForm: any = '';
  onUpmGroupChangeSub = new Subscription();
  onUserNameChangeSub = new Subscription();
  onSelectOptionsChangeSub = new Subscription();
  onUpmGroupCodeChangeSub = new Subscription();
  onUserDetailFromBranchAuthorityChangeSub = new Subscription();
  userGroupOptions: any[] = [];
  departmentGroupOptions: any[] = [];
  componentForm: FormGroup;
  formError: any = {};
  groupValChange: boolean = false;
  selectedUpmGroup: any = {};
  selectedDepartment: any = {};
  userOption: any[] = [];
  userDetails = [];
  assignedUser: any = {};
  status;
  branchLevelSolIdLimit;
  defaultApplicationFormForwardUPMGroups;
  applicationFormCurrentStatusConst = Constants.applicationFormCurrentStatusConst;
  applicationFormForwardType = Constants.applicationFormForwardType;
  forwardType = Constants.applicationFormForwardType.DIRECT;

  constructor(
    public  mdbModalRef: MDBModalRef,
    private formBuilder: FormBuilder,
    private applicationFormAddEditService: ApplicationFormAddEditService,
    private cacheService: CacheService,
    private masterDataService: MasterDataService,
    private applicationService: ApplicationService
  ) {
  }

  ngOnInit() {

    this.applicationForm = this.content.applicationForm;
    this.status = this.content.status;
    this.branchLevelSolIdLimit = this.masterDataService.getSystemParameter(Constants.systemParamKey.BRANCH_SOL_ID_LIMIT);

    let loggedInUserWorkFlowRQ = {
      loggedInUserUpmGroupCode: this.applicationService.getLoggedInUserUPMGroupCode(),
      loggedInUserSolID: this.applicationService.getLoggedInUserDivCode(),
      workFlowTemplateID: this.applicationForm.workflowTemplateID
    };

    this.applicationFormAddEditService.getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(loggedInUserWorkFlowRQ);

    this.formError = {
      comment: {},
      departmentGroup: {},
      userGroup: {},
      group: {},
      user: {}
    };

    this.onUpmGroupChangeSub = this.applicationFormAddEditService.onUpmGroupChange
      .subscribe((upmGroupList: any) => {
        this.upmGroups = upmGroupList;
        this.userGroupOptions = [];
        _.forEach(this.upmGroups, group => {
          this.userGroupOptions.push({
            value: group.groupCode,
            label: group.referenceName
          })
        });
      });


    _.forEach(this.cacheService.getData(Constants.masterDataKey.CAS_BRANCHES), branch => {
      if (!_.isNull(branch.branchCode)) {

        this.departmentGroupOptions.push({
          value: branch.branchCode,
          label: branch.branchName ? branch.branchName : "No Departments"
        });
        // if (branch.branchCode > this.branchLevelSolIdLimit) {
        //   this.departmentGroupOptions.push({
        //     value: branch.branchCode,
        //     label: branch.branchName ? branch.branchName : "No Departments"
        //   })
        // } else if (branch.branchCode == this.applicationService.getLoggedInUserBranchcode()) {
        //   this.departmentGroupOptions.push({
        //     value: branch.branchCode,
        //     label: branch.branchName
        //   })
        // }
      }
    });

    this.componentForm = this.createComponentForm();

    this.onUpmGroupCodeChangeSub.unsubscribe();
    this.onUpmGroupCodeChangeSub = this.componentForm.controls.group.valueChanges
      .subscribe((value: any) => {
        if (value) {
          this.groupValChange = true;
          if (this.forwardType == this.applicationFormForwardType.DIRECT) {
            this.getUserDetailListFormBranchAuthorityLevel(value);
          }
          this.selectedUpmGroup = _.find(this.userGroupOptions, (b) => b.value == value);
        }
        this.componentForm.controls.user.reset();
        this.componentForm.updateValueAndValidity();
      });

    this.onUpmGroupCodeChangeSub = this.componentForm.controls.departmentGroup.valueChanges
      .subscribe((value: any) => {
        if (value) {
          this.componentForm.controls.group.reset();
          this.selectedDepartment = _.find(this.departmentGroupOptions, (b) => b.value == value);
          this.componentForm.updateValueAndValidity();
        }
      });

    this.onUserDetailFromBranchAuthorityChangeSub = this.applicationFormAddEditService.onUserDetailFromBranchAuthorityChange
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

    this.onSelectOptionsChangeSub = this.componentForm.controls.forwardType.valueChanges
      .subscribe((value: any) => {

        this.forwardType = value;

        switch (value) {
          case this.applicationFormForwardType.DIRECT : {
            this.componentForm.controls.group.setValidators([Validators.required]);
            this.componentForm.controls.group.reset();

            this.componentForm.controls.user.setValidators([Validators.required]);
            this.componentForm.controls.user.reset();

            this.componentForm.controls.departmentGroup.setValidators(null);
            this.componentForm.controls.departmentGroup.reset();

            this.componentForm.controls.userGroup.setValidators(null);
            this.componentForm.controls.userGroup.reset();

            let userOptions = this.userGroupOptions;
            this.userGroupOptions = userOptions.filter((data) => {
              return data.value !== this.applicationFormForwardType.CLUSTER
            });
            break;
          }

          case this.applicationFormForwardType.CLUSTER : {
            this.componentForm.controls.group.setValidators(null);
            this.componentForm.controls.group.reset();

            this.componentForm.controls.user.setValidators(null);
            this.componentForm.controls.user.reset();

            this.componentForm.controls.departmentGroup.setValidators([Validators.required]);
            this.componentForm.controls.departmentGroup.setValue(this.applicationService.getLoggedInUserDivCode());

            this.componentForm.controls.userGroup.setValidators([Validators.required]);
            this.componentForm.controls.userGroup.reset();

            this.userGroupOptions.push({
              value: this.applicationFormForwardType.CLUSTER,
              label: 'Higher Levels'
            });

            this.componentForm.controls.departmentGroup.disable();

            break;
          }

          default : {
            this.componentForm.controls.group.setValidators([Validators.required]);
            this.componentForm.controls.group.reset();

            this.componentForm.controls.user.setValidators([Validators.required]);
            this.componentForm.controls.user.reset();

            this.componentForm.controls.departmentGroup.setValidators(null);
            this.componentForm.controls.departmentGroup.reset();

            this.componentForm.controls.userGroup.setValidators(null);
            this.componentForm.controls.userGroup.reset();
          }
        }
        this.componentForm.updateValueAndValidity();
      });

    this.onUpmGroupCodeChangeSub = this.componentForm.controls.userGroup.valueChanges
      .subscribe((value: any) => {
        if (value) {
          let enableDepartmentGroup = value.includes(this.applicationFormForwardType.CLUSTER);
          if (enableDepartmentGroup) {
            this.componentForm.controls.departmentGroup.enable();
          } else {
            this.componentForm.controls.departmentGroup.setValue(this.applicationService.getLoggedInUserDivCode());
            this.componentForm.controls.departmentGroup.disable();
          }
        }
      });
  }

  createComponentForm() {
    return this.formBuilder.group({
      comment: ['', [Validators.required, Validators.maxLength(4000)]],
      group: [''],
      user: [''],
      departmentGroup: [''],
      userGroup: [''],
      forwardType: [this.applicationFormForwardType.DIRECT, [Validators.required]],
    })
  }

  getUserDetailListFormBranchAuthorityLevel(groupCode) {
    let data = Object.assign({},
      {solId: this.content.applicationForm.branchCode},
      {roleId: groupCode},
      {appCode: ''}
    );
    this.applicationFormAddEditService.getEligibleUsers(
      this.content.applicationForm.createdBy,
      this.content.applicationForm.currentAssignUser,
      data);
  }

  isValid() {
    return this.componentForm.valid
  }

  forward() {
    let assignUserDisplayName = this.assignedUser.firstName ? this.assignedUser.lastName ? this.assignedUser.firstName.concat(' ').concat(this.assignedUser.lastName) : this.assignedUser.firstName : (this.assignedUser.lastName ? this.assignedUser.lastName : null);
    let actionMessage;

    let saveObj;
    if (this.forwardType == this.applicationFormForwardType.DIRECT) {
      actionMessage = `${this.applicationService.getLoggedInUserDisplayName()} forwarded to ${this.assignedUser.firstName ? this.assignedUser.lastName ? this.assignedUser.firstName.concat(' ').concat(this.assignedUser.lastName) : this.assignedUser.firstName : (this.assignedUser.lastName ? this.assignedUser.lastName : null)}`;

      saveObj = {
        assignUserID: this.assignedUser.userID,
        assignUser: this.assignedUser.adUserID,
        assignADUserID: this.assignedUser.adUserID,
        assignUserDisplayName: assignUserDisplayName,
        assignUserUpmID: this.assignedUser.userID,
        assignUserUpmGroupCode: this.componentForm.controls.group.value ? this.componentForm.controls.group.value : this.assignedUser.upmGroupCode,
        remark: this.componentForm.controls.comment.value,
        actionMessage: actionMessage,
        applicationFormForwardType: this.forwardType
      };
    } else {

      let assignDepartmentCode = this.componentForm.controls.departmentGroup.value;
      let afAssignDepartmentDTOList = [];
      let userGroupNames = [];

      let department = _.find(this.departmentGroupOptions, (b) => b.value == assignDepartmentCode);
      let assignGroupUPMGroup = this.componentForm.controls.userGroup.value;

      assignGroupUPMGroup.forEach((data: any) => {
        if (data == this.applicationFormForwardType.CLUSTER) {
          this.defaultApplicationFormForwardUPMGroups = this.masterDataService.getSystemParameter(Constants.systemParamKey.DEFAULT_APPLICATION_FORM_FORWARDING_UPM_GROUPS);
          this.defaultApplicationFormForwardUPMGroups.split(',').forEach(data => {
            let userGroup = _.find(this.userGroupOptions, (b) => b.value == data);
            afAssignDepartmentDTOList.push({
              solID: department.value,
              departmentName: department.label,
              userGroupUPMCode: userGroup.value,
              userGroupName: userGroup.label
            });
            userGroupNames.push(userGroup.label);
          })
        } else {
          let userGroup = _.find(this.userGroupOptions, (b) => b.value == data);
          afAssignDepartmentDTOList.push({
            solID: department.value,
            departmentName: department.label,
            userGroupUPMCode: userGroup.value,
            userGroupName: userGroup.label
          });
          userGroupNames.push(userGroup.label);
        }
      });

      actionMessage = `${this.applicationService.getLoggedInUserDisplayName()} forwarded to ${this.getUserGroupNames(userGroupNames)} user group${userGroupNames.length > 1 ? "s" : ""} of ${this.selectedDepartment.label}`;

      saveObj = {
        assignDepartmentCode: assignDepartmentCode,
        afAssignDepartmentDTOList: _.uniqBy(afAssignDepartmentDTOList, (i) => i.userGroupUPMCode),
        remark: this.componentForm.controls.comment.value,
        actionMessage: actionMessage,
        assignUserDisplayName: this.selectedDepartment.label,
        applicationFormForwardType: this.forwardType
      };
    }

    this.action.next(AppUtils.trim(saveObj));
    this.mdbModalRef.hide();
  }

  ngOnDestroy(): void {
    this.onUpmGroupChangeSub.unsubscribe();
    this.onUserNameChangeSub.unsubscribe();
    this.onUpmGroupCodeChangeSub.unsubscribe();
    this.onUserDetailFromBranchAuthorityChangeSub.unsubscribe();
    this.onSelectOptionsChangeSub.unsubscribe();
  }

  getUserGroupNames(userGroupNames) {
    if (userGroupNames.length > 0) {
      return userGroupNames.toString();
    }
    return ""
  }


}
