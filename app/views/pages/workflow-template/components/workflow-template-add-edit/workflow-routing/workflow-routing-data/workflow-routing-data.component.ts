import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {WorkflowRoutingDataDTO} from "../../../../dto/workflowRoutingDataDTO";
import {WorkflowTemplateAddEditService} from "../../../../services/workflow-template-add-edit.service";
import {Subscription} from "rxjs/Rx";
import * as _ from "lodash";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppUtils} from "../../../../../../../shared/app.utils";

@Component({
  selector: 'app-workflow-routing-data',
  templateUrl: './workflow-routing-data.component.html',
  styleUrls: ['./workflow-routing-data.component.scss']
})
export class WorkflowRoutingDataComponent implements OnInit, OnChanges, OnDestroy {


  formErrors: any;

  constructor(private wfTAddEditService: WorkflowTemplateAddEditService,
              private formBuilder: FormBuilder) {
    this.formErrors = {
      upmGroupID: {},
    };
  }

  @Output('removeEvent') removeEvent = new EventEmitter();
  @Output('changeData') changeData = new EventEmitter();


  @Input("wfrDataDTO")
  wfrDataDTO: WorkflowRoutingDataDTO;
  @Input("indexOfList")
  indexOfList: number;
  upmGroupDTO: any;

  componentForm: FormGroup;
  selectedUPMGroup: any = {};
  upmGroupList: any = [];
  upmGroupMap: any = {};
  activeUpmGroupList: any[] = [];
  optionUpmGroupList: any[] = [];
  optionUpmGroupMap: any = {};
  onUpmGroupListChangeSub: Subscription = new Subscription();
  onFormValueChangeSub: Subscription = new Subscription();
  onFormValueChange: Subscription = new Subscription();

  ngOnInit() {

    this.componentForm = this.createRoleForm();
    this.onUpmGroupListChangeSub = this.wfTAddEditService.onApprovedUPMGroupListChange.subscribe(dataList => {
      if (dataList) {
        this.upmGroupList = dataList;
        this.upmGroupList = dataList;
        _.forEach(dataList, upmGroup => {
          if (upmGroup.status == 'ACT' || this.wfrDataDTO.upmGroupID == upmGroup.upmGroupID) {
            this.activeUpmGroupList.push(upmGroup);
            this.optionUpmGroupList.push({"value": upmGroup.upmGroupID, "label": upmGroup.groupCode});
          }
          this.upmGroupMap[upmGroup.upmGroupID] = upmGroup;
          this.selectedUPMGroup = this.upmGroupMap[this.wfrDataDTO.upmGroupID] || {};
        });
      }
    });
    this.onFormValueChangeSub = this.componentForm.valueChanges
      .subscribe(form => {
        this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
        this.selectedUPMGroup = this.upmGroupMap[form.upmGroupID] || {};
        this.updateWftRoutingData();
      });
    this.componentForm.updateValueAndValidity({onlySelf: true, emitEvent: false})
  }


  createRoleForm() {
    return this.formBuilder.group({
      upmGroupID: [this.wfrDataDTO.upmGroupID, [Validators.required]]
    });
  }

  updateWftRoutingData() {
    let updateData = {
      indexOfList: this.indexOfList,
      wfrDataDTO: Object.assign({}, this.wfrDataDTO, {upmGroupID: this.selectedUPMGroup.upmGroupID}, {displayOrder: this.indexOfList}),
      isFormValid: this.componentForm.valid,
      isFormDirty: this.componentForm.dirty,

    };
    this.changeData.emit(updateData);
  }


  ngOnChanges(changes: SimpleChanges): void {
    if (changes["wfrDataDTO"]) {
      this.upmGroupDTO = this.wfrDataDTO.upmGroupDTO;
      this.onFormValueChange.unsubscribe();
      // this.onFormValueChange = this.componentForm.valueChanges.subscribe((form) => {
      //   this.formErrors = AppUtils.getFormErrors(this.componentForm, this.formErrors);
      // });
    }
  }

  ngOnDestroy(): void {
    this.onUpmGroupListChangeSub.unsubscribe();
    this.onFormValueChangeSub.unsubscribe();
  }

  updateSelectedItem($event) {
    this.selectedUPMGroup = {};
  }

  remove($event) {
    let removeData = {
      indexOfList: this.indexOfList,
      wfrDataDTO: this.wfrDataDTO
    };

    this.removeEvent.emit(removeData);

  }


}
