export class CustomerRatingsDto {
  customerRatingsID;
  customerID;
  casCustomerID;
  existingFacilitiesROA;
  proposedFacilitiesROA;
  riskGrading;
  riskScore;
  ramScore;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;

  constructor(dto){
    dto = dto || {};
    this.customerRatingsID = dto.customerRatingsID || '';
    this.customerID = dto.customerID || '';
    this.casCustomerID = dto.casCustomerID || '';
    this.existingFacilitiesROA = dto.existingFacilitiesROA || '';
    this.proposedFacilitiesROA = dto.proposedFacilitiesROA || '';
    this.riskGrading = dto.riskGrading || '';
    this.riskScore = dto.riskScore || '';
    this.ramScore = dto.ramScore || '';
	  this.createdDateStr = dto.createdDateStr || '';
	  this.createdBy = dto.createdBy || '';
	  this.modifiedDateStr = dto.modifiedDateStr || '';
    this.modifiedBy = dto.modifiedBy || '';
  }

}
