import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {Constants} from "../../../../../../core/setting/constants";
import {WorkflowRoutingDataDTO} from "../../../dto/workflowRoutingDataDTO";
import * as _ from "lodash";
import {Subscription} from "rxjs";
import {WorkflowTemplateAddEditService} from "../../../services/workflow-template-add-edit.service";

@Component({
  selector: 'app-workflow-routing',
  templateUrl: './workflow-routing.component.html',
  styleUrls: ['./workflow-routing.component.scss']
})
export class WorkflowRoutingComponent implements OnInit, OnDestroy, OnChanges {

  @Input("workflowTemplate")
  workflowTemplate;

  @Output('changeWorkflowRouting') changeWorkflowRouting = new EventEmitter();

  approveStatus = Constants.approveStatus;
  onSelectedItemChange: Subscription = new Subscription();

  removedList: any[] = [];
  workflowRoutingDataArray: any[] = [];
  isWorkflowRoutingValid = true;
  isWorkflowRoutingDirty = false;

  constructor(private addEditService: WorkflowTemplateAddEditService) {
  }

  ngOnInit() {
    this.workflowRoutingDataArray = this.workflowTemplate.getSortedWFRList() || [];
    if (_.isEmpty(this.workflowRoutingDataArray)) {
      let wfrDTO = new WorkflowRoutingDataDTO();
      wfrDTO.workFlowTemplateID = this.workflowTemplate.workFlowTemplateID;
      this.workflowRoutingDataArray.push(wfrDTO)
    }
  }


  ngOnChanges(changes: SimpleChanges): void {
    if (changes["workflowTemplate"]) {
      this.workflowRoutingDataArray = this.workflowTemplate.getSortedWFRList() || [];
      if (_.isEmpty(this.workflowRoutingDataArray)) {
        let wfrDTO = new WorkflowRoutingDataDTO();
        wfrDTO.workFlowTemplateID = this.workflowTemplate.workFlowTemplateID;
        this.workflowRoutingDataArray.push(wfrDTO);
        this.isWorkflowRoutingValid = false;
        this.isWorkflowRoutingDirty = true;
      } else {
        this.isWorkflowRoutingValid = true;
        this.isWorkflowRoutingDirty = false;
      }
    }
  }


  ngOnDestroy(): void {
    this.onSelectedItemChange.unsubscribe();
  }

  removeWFRData($event) {
    this.isWorkflowRoutingValid = true;
    this.workflowRoutingDataArray.splice($event.indexOfList, 1);
    if ($event.wfrDataDTO.workFlowTemplateDataID != null && $event.wfrDataDTO.workFlowTemplateDataID != "") {
      this.removedList.push(Object.assign({}, $event.wfrDataDTO, {removed: true}));
    }
    _.forEach(this.workflowRoutingDataArray, wfrd => {
      if (wfrd.upmGroupID == null || wfrd.upmGroupID == "") {
        this.isWorkflowRoutingValid = false;
      }
    });

    if (_.isEmpty(this.workflowRoutingDataArray)) {
      let wfrDTO = new WorkflowRoutingDataDTO();
      wfrDTO.workFlowTemplateID = this.workflowTemplate.workFlowTemplateID;
      this.workflowRoutingDataArray.push(wfrDTO);
      this.isWorkflowRoutingValid = false;
    }
    this.isWorkflowRoutingDirty = true;
    this.updateWorkflowTemplate();
  }

  changeData($event) {
    this.workflowRoutingDataArray.splice($event.indexOfList, 1, $event.wfrDataDTO);

    this.isWorkflowRoutingDirty = true;
    this.isWorkflowRoutingValid = $event.isFormValid;

    this.updateWorkflowTemplate();
  }


  updateWorkflowTemplate() {
    let workflowRoutingData = [...this.removedList, ...this.workflowRoutingDataArray];
    _.forEach(workflowRoutingData, wfrd => {
      if (wfrd.upmGroupID == null || wfrd.upmGroupID == "") {
        this.isWorkflowRoutingValid = false;
      }
    });

    let updatedRouting = {
        workflowRoutingDataArray: this.workflowRoutingDataArray,
        removedList: this.removedList,
        workflowRoutingData: workflowRoutingData,
        isWorkflowRoutingDirty: this.isWorkflowRoutingDirty,
        isWorkflowRoutingValid: this.isWorkflowRoutingValid
      }
    ;
    this.changeWorkflowRouting.emit(updatedRouting);
  }

  addNewWorkflowRoutingData($event) {
    let wfrDTO = new WorkflowRoutingDataDTO();
    wfrDTO.workFlowTemplateID = this.workflowTemplate.workFlowTemplateID;
    this.workflowRoutingDataArray.push(wfrDTO);
    this.isWorkflowRoutingDirty = true;
    this.isWorkflowRoutingValid = false;
    this.updateWorkflowTemplate();
  }
}
