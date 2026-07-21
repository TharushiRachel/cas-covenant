import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import * as _ from "lodash";
import {AppUtils} from "../../../../../shared/app.utils";
import {Constants} from "../../../../../core/setting/constants";
import {WorkingTemplateDto} from "../../dto/working-template-dto";
import {WorkflowTemplateAddEditService} from "../../services/workflow-template-add-edit.service";
import {SETTINGS} from "../../../../../core/setting/commons.settings";
import {ApplicationService} from "../../../../../core/service/application/application.service";

@Component({
  selector: 'app-workflow-template-add-edit',
  templateUrl: './workflow-template-add-edit.component.html',
  styleUrls: ['./workflow-template-add-edit.component.scss']
})
export class WorkflowTemplateAddEditComponent implements OnInit, OnDestroy {

  componentForm: FormGroup;
  formErrors: any;
  selectedUpdateDTO: WorkingTemplateDto = new WorkingTemplateDto({});
  onSelectedItemChange: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();
  isValidWorkflow = true;
  isDirtyWorkflow = false;
  workflowRoutingList: any[] = [];

  pageType: string = 'new';

  optionsSelect = [
    {value: 'ACT', label: 'Active'},
    {value: 'INA', label: 'Inactive'},
  ];

  approveStatus = Constants.approveStatusConst;
  masterDataPrivilege = SETTINGS.PRIVILEGES;

  systemPrivileges: any = {};
  privilegeCategories: any = [];
  checkedPrivilegeIDs: any = [];

  constructor(private formBuilder: FormBuilder,
              private addEditService: WorkflowTemplateAddEditService,
              private applicationService: ApplicationService) {
  }

  ngOnInit() {

    this.formErrors = {
      workFlowTemplateName: {},
      code: {},
      description: {},
      status: {}
    };

    this.onSelectedItemChange = this.addEditService.onSelectedItemChange
      .subscribe(item => {
        if (_.isEmpty(item)) {
          this.pageType = 'new';
          this.selectedUpdateDTO = new WorkingTemplateDto({});
        } else {
          this.pageType = 'edit';
          this.selectedUpdateDTO = new WorkingTemplateDto(item);
          this.isValidWorkflow = true;
          this.isDirtyWorkflow = false;
        }
        this.workflowRoutingList = this.selectedUpdateDTO.workFlowTemplateDataDTOList;
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
      workFlowTemplateName: [this.selectedUpdateDTO.workFlowTemplateName, [Validators.required, Validators.maxLength(50)]],
      code: [this.selectedUpdateDTO.code, [Validators.required, Validators.maxLength(50)]],
      description: [this.selectedUpdateDTO.description, [Validators.maxLength(50)]],
      status: [{value: this.selectedUpdateDTO.status, disabled: this.pageType == 'new'}, [Validators.required]]
    });
  }

  isValid() {
    return this.componentForm.valid && this.isValidWorkflow;
  }

  isDirty() {
    return this.componentForm.dirty || this.isDirtyWorkflow;
  }


  isApproveOrRejectValid() {
    return this.selectedUpdateDTO.approveStatus == Constants.approveStatusConst.PENDING && this.pageType == 'edit' && !this.isModifiedOrCreatedByLoggedInUser();
  }

  workflowUpdates($event) {
    if ($event) {
      let list: any[] = [];

      _.forEach($event.workflowRoutingData, wfrd => {
        list.push(wfrd);
      });
      this.workflowRoutingList = list;
      this.isDirtyWorkflow = $event.isWorkflowRoutingDirty;
      this.isValidWorkflow = $event.isWorkflowRoutingValid;

    }
  }

  saveUpdate() {
    let saveData = Object.assign({}, this.selectedUpdateDTO, this.componentForm.getRawValue(),
      {
        approveStatus: this.selectedUpdateDTO.approveStatus,
        approvedBy: this.selectedUpdateDTO.approvedBy,
        approvedDateStr: this.selectedUpdateDTO.approvedDateStr
      }
    );
    saveData.workFlowTemplateDataDTOList = this.workflowRoutingList;

    this.addEditService.saveUpdateItem(saveData);
  }

  approve() {
    let data = Object.assign({},
      {approveRejectDataID: this.selectedUpdateDTO.workFlowTemplateID},
      {approveStatus: this.approveStatus.APPROVED});
    this.addEditService.approveOrRejectWorkFlowTemplate(data);
  }

  reject() {

    let data = Object.assign({},
      {approveRejectDataID: this.selectedUpdateDTO.workFlowTemplateID},
      {approveStatus: this.approveStatus.REJECTED});
    this.addEditService.approveOrRejectWorkFlowTemplate(data);
  }

  isModifiedOrCreatedByLoggedInUser() {
    return this.selectedUpdateDTO.modifiedBy ? this.selectedUpdateDTO.modifiedBy == this.applicationService.getLoggedInUserUserName() : this.selectedUpdateDTO.createdBy ? this.selectedUpdateDTO.createdBy == this.applicationService.getLoggedInUserUserName() : false;
  }

  tabPressDisabled(event) {
    AppUtils.tabPressDisabled(event);
  }

}
