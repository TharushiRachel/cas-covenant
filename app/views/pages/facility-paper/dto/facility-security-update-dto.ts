export class FacilitySecurityUpdateDto {
  facilitySecurityID;
  facilityID;
  securityDetail;
  securityCode;
  facilitySecurityType;
  securityAmount;
  cashAmount;
  securityCurrency;
  status;
  isCommonSecurity;
  isCashSecurity;
  addedFacilityIDs;
  removedFacilityIDs;
  facilityFacilitySecurityDTOS;

  constructor(dto) {
    dto = dto || {};
    this.facilitySecurityID = dto.facilitySecurityID || '';
    this.facilityID = dto.facilityID || '';
    this.securityCode = dto.securityCode || '';
    this.facilitySecurityType = dto.facilitySecurityType || '';
    this.securityDetail = dto.securityDetail || '';
    this.securityAmount = dto.securityAmount || '';
    this.cashAmount = dto.cashAmount || '';
    this.securityCurrency = dto.securityCurrency || '';
    this.status = dto.status || 'ACT';
    this.isCommonSecurity = dto.isCommonSecurity || 'N';
    this.isCashSecurity = dto.isCashSecurity || 'N';
    this.addedFacilityIDs = dto.addedFacilityIDs || [];
    this.removedFacilityIDs = dto.removedFacilityIDs || [];
    this.facilityFacilitySecurityDTOS = dto.facilityFacilitySecurityDTOS || []
  }
}
