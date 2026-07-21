export class CustomerTelephoneUpdateDto {
  customerTelephoneID;
  customerID;
  contactNumber;
  description;
  status;

  constructor(dto){
    dto = dto || {};
    this.customerTelephoneID = dto.customerTelephoneID || '';
    this.customerID = dto.customerID || '';
    this.contactNumber = dto.contactNumber || '';
    this.description = dto.description || '';
    this.status = dto.status || '';
  }
}
