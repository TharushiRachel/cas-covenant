export class LeadDocumentUpdateDto{

  leadDocumentID;
  leadID;
  supportingDocID;
  supportingDocDTO
  docStorageDTO;
  remark;
  status;

  constructor(leadDocumentUpdateDto){
    leadDocumentUpdateDto = leadDocumentUpdateDto || {};
    this.leadDocumentID = leadDocumentUpdateDto.leadDocumentID || '';
    this.leadID = leadDocumentUpdateDto.leadID;
    this.supportingDocID = leadDocumentUpdateDto.supportingDocID || '';
    this.docStorageDTO = leadDocumentUpdateDto.docStorageDTO || '';
    this.remark = leadDocumentUpdateDto.remark || '';
    this.status = leadDocumentUpdateDto.status || '';
    this.supportingDocDTO = leadDocumentUpdateDto.supportingDocDTO || '';
  }

}
