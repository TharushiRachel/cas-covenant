export class LeadFacilityDetailUpdateDto {

  leadFacilityDetailID;
  leadID;
  facilityTemplateID;
  facilityTemplateName;
  creditFacilityType;
  amount;
  facilityCurrency;
  description;
  status;

  constructor(leadFacilityUpdateDto) {
    leadFacilityUpdateDto = leadFacilityUpdateDto || {};
    this.leadFacilityDetailID = leadFacilityUpdateDto.leadFacilityDetailID || '';
    this.leadID = leadFacilityUpdateDto.leadID || '';
    this.facilityTemplateID = leadFacilityUpdateDto.facilityTemplateID || '';
    this.facilityTemplateName = leadFacilityUpdateDto.facilityTemplateName || '';
    this.creditFacilityType = leadFacilityUpdateDto.creditFacilityType || '';
    this.amount = leadFacilityUpdateDto.amount || '';
    this.facilityCurrency = leadFacilityUpdateDto.facilityCurrency || '';
    this.description = leadFacilityUpdateDto.description || '';
    this.status = leadFacilityUpdateDto.status || '';
  }
}
