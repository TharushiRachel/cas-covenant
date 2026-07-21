import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RoleUpdateDto} from "../../dto/role-update-dto";
import {Subscription} from "rxjs";
import {RoleAddEditService} from "../../services/role-add-edit.service";
import {AppUtils} from "../../../../../shared/app.utils";
import * as _ from 'lodash';
import {Constants} from "../../../../../core/setting/constants";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-role-add-edit',
  templateUrl: './role-add-edit.component.html',
  styleUrls: ['./role-add-edit.component.scss']
})
export class RoleAddEditComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any;
  roleUpdateDTO: RoleUpdateDto = new RoleUpdateDto({});
  onSelectedRoleChange: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();
  approveStatus = Constants.approveStatusConst;
  pageType: string = 'new';
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  systemPrivileges: any = {};
  privilegeCategories: any = [];
  checkedPrivilegeIDs: any = [];
  isPrivilegeChange: any = false;

  constructor(private formBuilder: FormBuilder,
              private roleAddEditService: RoleAddEditService,
              private applicationService: ApplicationService) {
  }

  ngOnInit() {

    this.formErrors = {
      roleName: {},
      upmPrivilegeCode: {},
      status: {}
    };

    this.onSelectedRoleChange = this.roleAddEditService.onSelectedRoleChange
      .subscribe(role => {
        if (_.isEmpty(role)) {
          this.pageType = 'new';
          this.roleUpdateDTO = new RoleUpdateDto({});
        } else {
          this.pageType = 'edit';
          this.roleUpdateDTO = new RoleUpdateDto(role);
        }
        this.isPrivilegeChange = false;
        this.checkedPrivilegeIDs = this.roleUpdateDTO.privileges;

        this.componentForm = this.createRoleForm();
        this.onFormValueChange.unsubscribe();
        this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
          this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
        });

        this.systemPrivileges = this.roleAddEditService.systemPrivileges;
        this.privilegeCategories = _.keys(this.systemPrivileges).sort();
      });

  }

  ngOnDestroy(): void {
    this.onSelectedRoleChange.unsubscribe();
    this.onFormValueChange.unsubscribe();
  }

  createRoleForm() {
    return this.formBuilder.group({
      roleName: [this.roleUpdateDTO.roleName, [Validators.required, Validators.maxLength(200)]],
      upmPrivilegeCode: [this.roleUpdateDTO.upmPrivilegeCode, [Validators.required, Validators.maxLength(50)]],
      status: [{value: this.roleUpdateDTO.status, disabled: this.pageType == 'new'}, [Validators.required]]
    });
  }

  isPrivilegeChecked(privilegeID: any) {
    return _.indexOf(this.checkedPrivilegeIDs, privilegeID) !== -1;
  }

  onCheckboxChange($event, privilegeID) {
    if ($event) {
      $event.preventDefault();
      $event.stopPropagation();
    }

    if (this.isPrivilegeChecked(privilegeID)) {
      this.checkedPrivilegeIDs = _.without(this.checkedPrivilegeIDs, privilegeID);
    } else {
      this.checkedPrivilegeIDs = [...this.checkedPrivilegeIDs, privilegeID];
    }

    this.isPrivilegeChange = true;
  }

  isValid() {
    return this.componentForm.valid && this.checkedPrivilegeIDs.length > 0;
  }

  isDirty() {
    return this.componentForm.dirty || this.isPrivilegeChange;
  }

  saveUpdate() {
    let saveData = Object.assign({}, this.roleUpdateDTO, this.componentForm.getRawValue());
    let existingPrivileges = this.roleUpdateDTO.privileges;
    let added = _.difference(this.checkedPrivilegeIDs, existingPrivileges);
    let removed = _.difference(existingPrivileges, this.checkedPrivilegeIDs);

    saveData.addedPrivileges = added;
    saveData.deletedPrivileges = removed;

    if (this.pageType == 'new') {
      saveData.privileges = this.checkedPrivilegeIDs;
    }

    this.roleAddEditService.saveUpdateRole(saveData);
  }

  isApproveOrRejectValid() {
    return this.roleUpdateDTO.approveStatus == Constants.approveStatusConst.PENDING && this.pageType == 'edit' && !this.isModifiedOrCreatedByLoggedInUser();
  }

  approve() {
    let data = Object.assign({},
      {approveRejectDataID: this.roleUpdateDTO.roleID},
      {approveStatus: this.approveStatus.APPROVED});
    this.roleAddEditService.approveOrRejectRole(data);
  }

  reject() {

    let data = Object.assign({},
      {approveRejectDataID: this.roleUpdateDTO.roleID},
      {approveStatus: this.approveStatus.REJECTED});
    this.roleAddEditService.approveOrRejectRole(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.roleUpdateDTO.modifiedBy ? this.roleUpdateDTO.modifiedBy == this.applicationService.getLoggedInUserUserName() : this.roleUpdateDTO.createdBy ? this.roleUpdateDTO.createdBy == this.applicationService.getLoggedInUserUserName() : false;
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }

}
