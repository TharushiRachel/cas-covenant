import * as _ from "lodash"

export class WorkingTemplateDto {

  workFlowTemplateID;
  workFlowTemplateName;
  code;
  description;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;
  workFlowTemplateDataDTOList;


  constructor(dto) {
    dto = dto || {};
    this.workFlowTemplateID = dto.workFlowTemplateID || '';
    this.workFlowTemplateName = dto.workFlowTemplateName || '';
    this.code = dto.code || '';
    this.description = dto.description || '';
    this.status = dto.status || 'ACT';
    this.approveStatus = dto.approveStatus || 'PENDING';
    this.approvedDateStr = dto.approvedDateStr || '';
    this.approvedBy = dto.approvedBy || '';
    this.createdDateStr = dto.createdDateStr || '';
    this.createdBy = dto.createdBy || '';
    this.modifiedDateStr = dto.modifiedDateStr || '';
    this.modifiedBy = dto.modifiedBy || '';
    this.workFlowTemplateDataDTOList = dto.workFlowTemplateDataDTOList || [];
  }

  public getSortedWFRList() {
    return _.orderBy(this.workFlowTemplateDataDTOList, ['displayOrder'],['asc'])
  }
}
