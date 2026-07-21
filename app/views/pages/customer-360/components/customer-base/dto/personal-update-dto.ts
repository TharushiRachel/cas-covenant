export class PersonalUpdateDto {
  customerID;
  customerFinancialID;
  title;
  customerName;
  dateOfBirth;
  civilStatus;
  emailAddress;
  secondaryEmailAddress;
  customerIdentificationDTOList;
  customerTelephoneDTOList;
  customerAddressDTOList;
  customerBankDetailsDTOList;

  constructor(dto) {
    dto = dto || {};
    this.customerID = dto.customerID || '';
    this.customerFinancialID = dto.customerFinancialID || '';
    this.title = dto.title || '';
    this.customerName = dto.customerName || '';
    this.dateOfBirth = dto.dateOfBirth || '';
    this.civilStatus = dto.civilStatus || '';
    this.emailAddress = dto.emailAddress || '';
    this.secondaryEmailAddress = dto.secondaryEmailAddress || '';
    this.customerIdentificationDTOList = dto.customerIdentificationDTOList || [];
    //this.customerTelephoneDTOList = dto.customerTelephoneDTOList || [];
    this.customerAddressDTOList = dto.customerAddressDTOList || [];
    this.customerBankDetailsDTOList = dto.customerBankDetailsDTOList || [];
  }
}
