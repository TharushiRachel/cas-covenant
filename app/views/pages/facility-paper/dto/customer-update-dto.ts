export class CustomerUpdateDto{
  customerName;
  customerAddressDTOList;
  customerTelephoneDTOList;
  customerIdentificationDTOList;
  customerBankDetailsDTOList;

  constructor(dto){
    dto = dto || {};
    this.customerName = dto.customerName || '';
    this.customerAddressDTOList = dto.customerAddressDTOList || [];
    this.customerTelephoneDTOList = dto.customerTelephoneDTOList || [];
    this.customerIdentificationDTOList = dto.customerIdentificationDTOList || [];
    this.customerBankDetailsDTOList = dto.customerBankDetailsDTOList || [];
  }
}
