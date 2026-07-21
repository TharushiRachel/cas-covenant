export class AFSecurityUpdateDto {
  securityID;
  securityDetail;
  securityCode;
  securityAmount;
  cashAmount;
  securityCurrency;
  status;
  isCommonSecurity;
  isCashSecurity;
  afFacilitySecurityDTOS;

  constructor(dto) {
    dto = dto || {};
    this.securityID = dto.securityID || '';
    this.securityCode = dto.securityCode || '';
    this.securityDetail = dto.securityDetail || '';
    this.securityAmount = dto.securityAmount || '';
    this.cashAmount = dto.cashAmount || '';
    this.securityCurrency = dto.securityCurrency || '';
    this.status = dto.status || 'ACT';
    this.isCommonSecurity = dto.isCommonSecurity || 'N';
    this.isCashSecurity = dto.isCashSecurity || 'N';
    this.afFacilitySecurityDTOS = dto.afFacilitySecurityDTOS || []
  }
}
