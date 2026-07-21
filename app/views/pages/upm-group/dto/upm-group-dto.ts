export class UpmGroupDto {

  upmGroupID;
  groupCode;
  referenceName;
  workFlowLevel;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;

  constructor(upmGroupDto) {
    upmGroupDto = upmGroupDto || {};
    this.upmGroupID= upmGroupDto.upmGroupID || '';
    this.groupCode= upmGroupDto.groupCode || '';
    this.referenceName= upmGroupDto.referenceName || '';
    this.workFlowLevel= upmGroupDto.workFlowLevel || '';
    this.status= upmGroupDto.status || 'ACT';
    this.approveStatus= upmGroupDto.approveStatus || 'PENDING';
    this.approvedDateStr= upmGroupDto.approvedDateStr || '';
    this.approvedBy= upmGroupDto.approvedBy || '';
    this.createdDateStr = upmGroupDto.createdDateStr || '';
    this.createdBy = upmGroupDto.createdBy || '';
    this.modifiedDateStr = upmGroupDto.modifiedDateStr || '';
    this.modifiedBy = upmGroupDto.modifiedBy || '';
  }
}
