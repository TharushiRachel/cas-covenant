export class FpOtherBankUpdateDto {
  fpCustomerOtherBankFacilityID;
  casCustomerID;
  bankName;
  branchName;
  facilityAmount;
  outstandingAmount;
  facilityType;
  disbursementDate;
  maturityDate;
  securities;

  constructor(dto) {
    dto = dto || {};
    this.fpCustomerOtherBankFacilityID = dto.fpCustomerOtherBankFacilityID || '';
    this.casCustomerID = dto.casCustomerID || '';
    this.bankName = dto.bankName || '';
    this.branchName = dto.branchName || '';
    this.facilityAmount = dto.facilityAmount || '';
    this.outstandingAmount = dto.outstandingAmount || '';
    this.facilityType = dto.facilityType || '';
    this.disbursementDate = dto.disbursementDateStr || '';
    this.maturityDate = dto.maturityDateStr || '';
    this.securities = dto.securities || '';
  }
}
