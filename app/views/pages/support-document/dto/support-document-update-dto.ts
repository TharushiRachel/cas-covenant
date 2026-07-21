export class SupportDocumentUpdateDto{

  supportingDocID;
  documentName;
  description;
  supportDocumentType;
  status;
  approveStatus;
  approvedDateStr;
  approvedBy;
  createdDateStr;
  createdBy;
  modifiedDateStr;
  modifiedBy;

  constructor(supportDocumentUpdateDto){

    supportDocumentUpdateDto = supportDocumentUpdateDto || {}
    this.supportingDocID = supportDocumentUpdateDto.supportingDocID || '';
    this.documentName = supportDocumentUpdateDto.documentName || '';
    this.description = supportDocumentUpdateDto.description || '';
    this.supportDocumentType = supportDocumentUpdateDto.supportDocumentType ||'';
    this.status = supportDocumentUpdateDto.status || 'ACT';
    this.approveStatus  =  supportDocumentUpdateDto.approveStatus || 'PENDING';
    this.approvedDateStr = supportDocumentUpdateDto.approvedDateStr || '';
    this.approvedBy = supportDocumentUpdateDto.approvedBy || '';
    this.createdDateStr = supportDocumentUpdateDto.createdDateStr || '';
    this.createdBy = supportDocumentUpdateDto.createdBy || '';
    this.modifiedDateStr = supportDocumentUpdateDto.modifiedDateStr || '';
    this.modifiedBy = supportDocumentUpdateDto.modifiedBy || '';
  }

}
