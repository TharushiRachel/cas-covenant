export class CribDetailsSaveDTO {
  customerCribReportID;
  casCustomerID;
  cribStatus;
  cribDateStr;
  remark;
  status;
  reportContent;
  identificationType;
  identificationNo;
  facilityPaperID;
  savedUserDivCode;
  savedUserDisplayName;

  constructor(dto) {
    dto = dto || {};
    this.customerCribReportID = dto.customerCribReportID || '';
    this.casCustomerID = dto.casCustomerID || '';
    this.cribStatus = dto.cribStatus || '';
    this.cribDateStr = dto.cribDate || '';
    this.remark = dto.remarks || '';
    this.status = dto.status || '';
    this.reportContent = dto.reportContent || '';
    this.identificationType = dto.identificationType || '';
    this.identificationNo = dto.identificationNo || '';
    this.facilityPaperID = dto.facilityPaperID || '';
    this.savedUserDisplayName = dto.savedUserDisplayName || '';
    this.savedUserDivCode = dto.savedUserDivCode || '';
  }
}

