export interface CribResponse {
  cribDetailsID: number;
  facilityPaperID: number;
  supportingDocID: number;
  documentName: string;
  docStorageDTO: {
    docStorageID: number;
    description: string;
    fileName: string;
    document: any;
    lastUpdatedDateStr: string;
  };
  cribStatus: string;
  cribIssueDate: any;
  cribIssueDateStr: string;
  remark: string;
  uploadedUserDisplayName: string;
  uploadedDivCode: string;
  status: string;
  fullName: string;
  gender: string;
  identificationType: string;
  identificationNumber: string;
  createdDateStr: string;
  createdBy: string;
  isSystem: string;
  docStorageID: number;
  modifiedDateStr: string;
  customerType: string;
  isReportUpdated: boolean;
  reportName: string;
  report: any;
  inquiryReason: string;
}
