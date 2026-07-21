export class CreditFacilityTemplateUpdateDto {

  creditFacilityTemplateID;
  creditFacilityTypeID;
  creditFacilityName;
  facilityTypeName;
  description;
  maxFacilityAmount;
  minFacilityAmount;
  showPurpose;
  showRepayment;
  showCondition;
  showRemark;
  showRentalData;
  showCalculator;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;
  showInLead:boolean;

  constructor(creditFacilityTemplateDto) {
    creditFacilityTemplateDto = creditFacilityTemplateDto || {};
    this.creditFacilityTemplateID = creditFacilityTemplateDto.creditFacilityTemplateID || '';
    this.creditFacilityTypeID = creditFacilityTemplateDto.creditFacilityTypeID;
    this.creditFacilityName = creditFacilityTemplateDto.creditFacilityName || '';
    this.facilityTypeName = creditFacilityTemplateDto.facilityTypeName || '';
    this.description = creditFacilityTemplateDto.description || '';
    this.maxFacilityAmount = creditFacilityTemplateDto.maxFacilityAmount || '';
    this.minFacilityAmount = creditFacilityTemplateDto.minFacilityAmount || '';
    this.status = creditFacilityTemplateDto.status || 'ACT';
    this.showPurpose = creditFacilityTemplateDto.showPurpose ? (creditFacilityTemplateDto.showPurpose == 'Y') : true;
    this.showRepayment = creditFacilityTemplateDto.showRepayment ? (creditFacilityTemplateDto.showRepayment == 'Y') : true;
    this.showCondition = creditFacilityTemplateDto.showCondition ? (creditFacilityTemplateDto.showCondition == 'Y') : true;
    this.showRemark = creditFacilityTemplateDto.showRemark ? (creditFacilityTemplateDto.showRemark == 'Y') : true;
    this.showCalculator = creditFacilityTemplateDto.showCalculator ? (creditFacilityTemplateDto.showCalculator == 'Y') : false;
    this.showRentalData = creditFacilityTemplateDto.showRentalData ? (creditFacilityTemplateDto.showRentalData == 'Y') : false;
    this.approveStatus = creditFacilityTemplateDto.approveStatus || 'PENDING';
    this.approvedDateStr = creditFacilityTemplateDto.approvedDateStr || '';
    this.approvedBy = creditFacilityTemplateDto.approvedBy || '';
    this.createdDateStr = creditFacilityTemplateDto.createdDateStr || '';
    this.createdBy = creditFacilityTemplateDto.createdBy || '';
    this.modifiedDateStr = creditFacilityTemplateDto.modifiedDateStr || '';
    this.modifiedBy = creditFacilityTemplateDto.modifiedBy || '';
    this.showInLead = creditFacilityTemplateDto.showInLead ? (creditFacilityTemplateDto.showInLead === 'Y') : false;
  }

}
