import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import * as _ from "lodash";
import {AppUtils} from "../../../../../shared/app.utils";
import {Constants} from "../../../../../core/setting/constants";
import {UpmGroupAddEditService} from "../../services/upm-group-add-edit.service";
import {UpmGroupDto} from "../../dto/upm-group-dto";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-upm-group-add-edit',
  templateUrl: './upm-group-add-edit.component.html',
  styleUrls: ['./upm-group-add-edit.component.scss']
})
export class UpmGroupAddEditComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any;
  selectedUpdateDTO: UpmGroupDto = new UpmGroupDto({});
  onSelectedItemChange: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();

  pageType: string = 'new';

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];
  optionsWorkflow = [
    {value: '0', label: 'Start'},
    {value: '1', label: 'Level 1'},
    {value: '2', label: 'Level 2'},
    {value: '3', label: 'Level 3'},
    {value: '4', label: 'Level 4'},
    {value: '5', label: 'Level 5'},
    {value: '6', label: 'Level 6'},
    {value: '7', label: 'Level 7'},
    {value: '8', label: 'Level 8'},
    {value: '9', label: 'Level 9'},
  ];

  approveStatus = Constants.approveStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  systemPrivileges: any = {};
  privilegeCategories: any = [];
  checkedPrivilegeIDs: any = [];

  constructor(private formBuilder: FormBuilder,
              private addEditService: UpmGroupAddEditService,
              private applicationService: ApplicationService) {
  }

  ngOnInit() {

    this.formErrors = {
      groupCode: {},
      referenceName: {},
      status: {}
    };

    this.onSelectedItemChange = this.addEditService.onSelectedItemChange
      .subscribe(item => {
        if (_.isEmpty(item)) {
          this.pageType = 'new';
          this.selectedUpdateDTO = new UpmGroupDto({});
        } else {
          this.pageType = 'edit';
          this.selectedUpdateDTO = new UpmGroupDto(item);
        }

        this.componentForm = this.createRoleForm();
        this.onFormValueChange.unsubscribe();
        this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
          this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
        });

      });

  }

  ngOnDestroy(): void {
    this.onSelectedItemChange.unsubscribe();
    this.onFormValueChange.unsubscribe();
  }

  createRoleForm() {
    return this.formBuilder.group({
      groupCode: [this.selectedUpdateDTO.groupCode, [Validators.required, Validators.maxLength(50)]],
      referenceName: [this.selectedUpdateDTO.referenceName, [Validators.maxLength(50)]],
      status: [{value: this.selectedUpdateDTO.status, disabled: this.pageType == 'new'}, [Validators.required]]
    });
  }

  isApproveOrRejectValid() {
    return this.selectedUpdateDTO.approveStatus == Constants.approveStatusConst.PENDING && this.pageType == 'edit' && !this.isModifiedOrCreatedByLoggedInUser();
  }

  isValid() {
    return this.componentForm.valid;
  }

  isDirty() {
    return this.componentForm.dirty;
  }

  saveUpdate() {
    let saveData = Object.assign({}, this.selectedUpdateDTO, this.componentForm.getRawValue(),
      {
        approveStatus: this.selectedUpdateDTO.approveStatus,
        approvedBy: this.selectedUpdateDTO.approvedBy,
        approvedDateStr: this.selectedUpdateDTO.approvedDateStr
      }
    );

    this.addEditService.saveUpdateItem(saveData);
  }


  approve() {
    let data = Object.assign({},
      {approveRejectDataID: this.selectedUpdateDTO.upmGroupID},
      {approveStatus: this.approveStatus.APPROVED});
    this.addEditService.approveOrRejectUpmGroup(data);
  }

  reject() {

    let data = Object.assign({},
      {approveRejectDataID: this.selectedUpdateDTO.upmGroupID},
      {approveStatus: this.approveStatus.REJECTED});
    this.addEditService.approveOrRejectUpmGroup(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.selectedUpdateDTO.modifiedBy ? this.selectedUpdateDTO.modifiedBy == this.applicationService.getLoggedInUserUserName() : this.selectedUpdateDTO.createdBy ? this.selectedUpdateDTO.createdBy == this.applicationService.getLoggedInUserUserName() : false;
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }

}
