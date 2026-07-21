export class SectionSubSectionUpdateDto {
  upcSectionID;
  upcSectionName;
  upcSectionDescription;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;

  constructor(upcSectionDto) {
    upcSectionDto = upcSectionDto || {};
    this.upcSectionID= upcSectionDto.upcSectionID || '';
    this.upcSectionName= upcSectionDto.upcSectionName || '';
    this.upcSectionDescription= upcSectionDto.upcSectionDescription || '';
    this.status= upcSectionDto.status || 'ACT';
    this.approveStatus= upcSectionDto.approveStatus || 'PENDING';
    this.approvedDateStr= upcSectionDto.approvedDateStr || '';
    this.approvedBy= upcSectionDto.approvedBy || '';
    this.createdDateStr = upcSectionDto.createdDateStr || '';
    this.createdBy = upcSectionDto.createdBy || '';
    this.modifiedDateStr = upcSectionDto.modifiedDateStr || '';
    this.modifiedBy = upcSectionDto.modifiedBy || '';
  }
}
