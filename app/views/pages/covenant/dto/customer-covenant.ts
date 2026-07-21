/**
 * Mirrors backend CustomerCovenantDTO
 * (lk.sampath.cas_covenant.dto.CustomerCovenantDTO)
 */
export class CustomerCovenant {
  customerCovenantId: number;
  requestUUID: string;
  customerFinancialID: string;
  facilityPaperRefNumber: string;
  facilityPaperId: number;
  covenant_Code: string;
  covenant_Description: string;
  covenant_Frequency: string;
  covenant_Due_Date: Date | string;
  status: string;
  createdUserDisplayName: string;
  createdDate: Date | string;
  createdBy: string;
  disbursementType: string;
  noFrequencyDueDate: string;
  isExists: string;
  complianceStatus: string;
  displayOrder: number;
  applicableType: string;
  modifiedBy: string;
  lastModifiedDate: Date | string;

  constructor(customerCovenant?: any) {
    customerCovenant = customerCovenant || {};
    this.customerCovenantId = customerCovenant.customerCovenantId || null;
    this.requestUUID = customerCovenant.requestUUID || customerCovenant.RequestUUID || "";
    this.customerFinancialID =
      customerCovenant.customerFinancialID ||
      customerCovenant.custId ||
      "";
    this.facilityPaperRefNumber =
      customerCovenant.facilityPaperRefNumber ||
      customerCovenant.casReference ||
      "";
    this.facilityPaperId = customerCovenant.facilityPaperId || null;
    this.covenant_Code = customerCovenant.covenant_Code || "";
    this.covenant_Description = customerCovenant.covenant_Description || "";
    this.covenant_Frequency = customerCovenant.covenant_Frequency || "";
    this.covenant_Due_Date = customerCovenant.covenant_Due_Date || "";
    this.status = customerCovenant.status || "";
    this.createdUserDisplayName = customerCovenant.createdUserDisplayName || "";
    this.createdDate = customerCovenant.createdDate || "";
    this.createdBy = customerCovenant.createdBy || "";
    this.disbursementType = customerCovenant.disbursementType || "";
    this.noFrequencyDueDate = customerCovenant.noFrequencyDueDate || "";
    this.isExists = customerCovenant.isExists || "";
    this.complianceStatus = customerCovenant.complianceStatus || "";
    this.displayOrder = customerCovenant.displayOrder || null;
    this.applicableType = customerCovenant.applicableType || "";
    this.modifiedBy = customerCovenant.modifiedBy || "";
    this.lastModifiedDate = customerCovenant.lastModifiedDate || "";
  }
}
