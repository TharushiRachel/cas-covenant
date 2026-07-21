export class CustomerAddressUpdateDto {
  customerAddressID;
  customerId;
  addressType;
  address1;
  address2;
  city;
  status;

  constructor(dto){
    dto = dto || {};
    this.customerAddressID = dto.customerAddressID || '';
    this.customerId = dto.customerId || '';
    this.addressType = dto.addressType || '';
    this.address1 = dto.address1 || '';
    this.address2 = dto.address2 || '';
    this.city = dto.city || '';
    this.status = dto.status || '';
  }

}
