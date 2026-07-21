export class WorkflowRoutingDataDTO {

  workFlowTemplateDataID;
  workFlowTemplateID;
  upmGroupID;
  upmGroupDTO;
  nextUPMGroupDTO;
  nextUPMGroupID;
  previousUPMGroupDTO;
  previousUPMGroupID;
  displayOrder;
  removed;

  constructor(workflowRoutingData?) {
    workflowRoutingData = workflowRoutingData || {};

    this.workFlowTemplateDataID = workflowRoutingData.workFlowTemplateDataID || null;
    this.workFlowTemplateID = workflowRoutingData.workFlowTemplateID || null;
    this.upmGroupID = workflowRoutingData.upmGroupID || '';
    this.upmGroupDTO = workflowRoutingData.upmGroupDTO || {};
    this.nextUPMGroupDTO = workflowRoutingData.nextUPMGroupDTO || {};
    this.nextUPMGroupID = workflowRoutingData.nextUPMGroupID || '';
    this.previousUPMGroupDTO = workflowRoutingData.previousUPMGroupDTO || {};
    this.previousUPMGroupID = workflowRoutingData.previousUPMGroupID || '';
    this.displayOrder = workflowRoutingData.displayOrder || 1;
  }

}
