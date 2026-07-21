export class AfTopicDto {
  topicID;
  page;
  topic;
  topicCode;
  topicData;
  description;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;
  afTopicUpcSectionDTOList;

  constructor(afTopicDto) {
    afTopicDto = afTopicDto || {};
    this.topicID = afTopicDto.topicID || '';
    this.page = afTopicDto.page || '';
    this.topic = afTopicDto.topic || '';
    this.topicCode = afTopicDto.topicCode || '';
    this.topicData = afTopicDto.topicData || '';
    this.description = afTopicDto.description || '';
    this.status = afTopicDto.status || 'ACT';
    this.approveStatus = afTopicDto.approveStatus || 'PENDING';
    this.approvedDateStr = afTopicDto.approvedDateStr || '';
    this.approvedBy = afTopicDto.approvedBy || '';
    this.createdDateStr = afTopicDto.createdDateStr || '';
    this.createdBy = afTopicDto.createdBy || '';
    this.modifiedDateStr = afTopicDto.modifiedDateStr || '';
    this.modifiedBy = afTopicDto.modifiedBy || '';
    this.afTopicUpcSectionDTOList = afTopicDto.afTopicUpcSectionDTOList || [];
  }
}
