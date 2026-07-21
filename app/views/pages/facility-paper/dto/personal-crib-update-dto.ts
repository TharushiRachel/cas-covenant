export class PersonalCribUpdateDto {
  casCustomerCribDetailsID;
  casCustomerID;
  supportingDocID;
  documentName;
  cribStatus;
  cribIssueDateStr;
  docStorageDTO;
  remarks;
  status;

  constructor(dto) {
    dto = dto || {};
    this.casCustomerCribDetailsID = dto.casCustomerCribDetailsID || '';
    this.casCustomerID = dto.casCustomerID || '';
    this.supportingDocID = dto.supportingDocID || '';
    this.documentName = dto.documentName || '';
    this.cribStatus = dto.cribStatus || '';
    this.cribIssueDateStr = dto.cribIssueDateStr || '';
    this.docStorageDTO = dto.docStorageDTO || '';
    this.remarks = dto.remark || '';
    this.status = dto.status || 'ACT'
  }
}
