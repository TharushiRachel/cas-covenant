package lk.sampath.cas_covenant.dto;

import java.util.Date;
import lk.sampath.cas_covenant.entity.CustomerCovenant;
import lk.sampath.cas_covenant.enums.CovenantApplicableType;
import lk.sampath.cas_covenant.enums.CovenantStatus;
import lk.sampath.cas_covenant.enums.CovenantStatusOnDisbursement;
import lk.sampath.cas_covenant.enums.YesNoStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerCovenantDTO {

  private Integer customerCovenantId;

  private String requestUUID;

  private String customerFinancialID;

  private String facilityPaperRefNumber;

  private Integer facilityPaperId;

  private String covenant_Code;

  private String covenant_Description;

  private String covenant_Frequency;

  private Date covenant_Due_Date;

  private CovenantStatus status;

  private String createdUserDisplayName;

  private Date createdDate;

  private String createdBy;

  private CovenantStatusOnDisbursement disbursementType;

  private String noFrequencyDueDate;

  private YesNoStatus isExists;

  private String complianceStatus;

  private Integer displayOrder;

  private CovenantApplicableType applicableType;

  private String modifiedBy;

  private Date lastModifiedDate;

  public CustomerCovenantDTO(CustomerCovenant customerCovenant) {
    this.customerCovenantId = customerCovenant.getCustomerCovenantId();
    this.requestUUID = customerCovenant.getRequestUUID();
    this.customerFinancialID = customerCovenant.getCustomerFinancialID();
    this.facilityPaperRefNumber = customerCovenant.getFacilityPaper().getFpRefNumber();
    this.facilityPaperId = customerCovenant.getFacilityPaper().getFacilityPaperID();
    this.covenant_Code = customerCovenant.getCovenant_Code();
    this.covenant_Description = customerCovenant.getCovenant_Description();
    this.covenant_Frequency = customerCovenant.getCovenant_Frequency();
    this.covenant_Due_Date = customerCovenant.getCovenant_Due_Date();
    this.status = customerCovenant.getStatus();
    this.createdUserDisplayName = customerCovenant.getCreatedUserDisplayName();
    this.createdDate = customerCovenant.getCreatedDate();
    this.createdBy = customerCovenant.getCreatedBy();
    this.disbursementType = customerCovenant.getDisbursementType();
    this.noFrequencyDueDate = customerCovenant.getNoFrequencyDueDate();
    this.isExists = customerCovenant.getIsExists();
    this.complianceStatus = customerCovenant.getComplianceStatus();
    this.displayOrder = customerCovenant.getDisplayOrder();
    this.applicableType = customerCovenant.getApplicableType();
    this.modifiedBy = customerCovenant.getModifiedBy();
    this.lastModifiedDate = customerCovenant.getLastModifiedDate();
  }
}
