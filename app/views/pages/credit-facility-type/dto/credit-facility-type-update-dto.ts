export class CreditFacilityTypeUpdateDto{

  creditFacilityTypeID;
  facilityTypeName;
  description;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;

  constructor(creditFacilityTypeDto){

    creditFacilityTypeDto = creditFacilityTypeDto || {};
    this.creditFacilityTypeID = creditFacilityTypeDto.creditFacilityTypeID || '';
    this.facilityTypeName = creditFacilityTypeDto.facilityTypeName || '';
    this.description = creditFacilityTypeDto.description || '';
    this.status = creditFacilityTypeDto.status || 'ACT';
    this.approveStatus = creditFacilityTypeDto.approveStatus || 'PENDING';
    this.approvedDateStr = creditFacilityTypeDto.approvedDateStr || '';
    this.approvedBy = creditFacilityTypeDto.approvedBy || '';
    this.createdDateStr = creditFacilityTypeDto.createdDateStr || '';
    this.createdBy = creditFacilityTypeDto.createdBy || '';
    this.modifiedDateStr = creditFacilityTypeDto.modifiedDateStr || '';
    this.modifiedBy = creditFacilityTypeDto.modifiedBy || '';
  }
}
