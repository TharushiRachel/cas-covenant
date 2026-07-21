export class CftSupportingDocUpdateDto{

  cftSupportingDocID;
  creditFacilityTemplateID;
  documentName;
  supportingDocID;
  mandatory;
  status;

  constructor(cftSupportingDocDto){
    cftSupportingDocDto = cftSupportingDocDto || {};
    this.cftSupportingDocID = cftSupportingDocDto.cftSupportingDocID || '';
    this.creditFacilityTemplateID = cftSupportingDocDto.creditFacilityTemplateID || '';
    this.supportingDocID = cftSupportingDocDto.supportingDocID || '';
    this.mandatory = cftSupportingDocDto.mandatory || '';
    this.status = cftSupportingDocDto.status || '';
    this.documentName = cftSupportingDocDto.documentName || '';
  }
}
