export class CustomerIdentificationUpdateDto {
  identificationID;
  customerID;
  identificationType;
  identificationNumber;
  status;

  constructor(dto){
    dto = dto || {};
    this.identificationID = dto.customerID || '';
    this.customerID = dto.customerID || '';
    this.identificationType = dto.identificationType || '';
    this.identificationNumber = dto.identificationNumber || '';
    this.status = dto.status || '';
  }
}
