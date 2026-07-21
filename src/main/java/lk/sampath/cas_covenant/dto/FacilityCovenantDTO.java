package lk.sampath.cas_covenant.dto;

import java.util.Date;
import java.util.List;
import lk.sampath.cas_covenant.entity.FacilityCovenant;
import lk.sampath.cas_covenant.enums.CovenantApplicableType;
import lk.sampath.cas_covenant.enums.CovenantStatus;
import lk.sampath.cas_covenant.enums.CovenantStatusOnDisbursement;
import lk.sampath.cas_covenant.enums.YesNoStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacilityCovenantDTO {

  private Integer applicationCovenantId;

  private String requestUUID;

  private String customerFinacleID;

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

  private String accountId;

  private Integer displayOrder;

  private CovenantApplicableType applicableType;

  private List<CovenantFacilitiesDTO> covenantFacilities;

  public FacilityCovenantDTO(FacilityCovenant facilityCovenant) {
    this.applicationCovenantId = facilityCovenant.getApplicationCovenantId();
    this.requestUUID = facilityCovenant.getRequestUUID();
    this.customerFinacleID = facilityCovenant.getCustomerFinacleID();
    this.facilityPaperId = facilityCovenant.getFacilityPaper().getFacilityPaperID();
    this.covenant_Code = facilityCovenant.getCovenant_Code();
    this.covenant_Description = facilityCovenant.getCovenant_Description();
    this.covenant_Frequency = facilityCovenant.getCovenant_Frequency();
    this.covenant_Due_Date = facilityCovenant.getCovenant_Due_Date();
    this.status = facilityCovenant.getStatus();
    this.createdUserDisplayName = facilityCovenant.getCreatedUserDisplayName();
    this.createdDate = facilityCovenant.getCreatedDate();
    this.createdBy = facilityCovenant.getCreatedBy();
    this.disbursementType = facilityCovenant.getDisbursementType();
    this.noFrequencyDueDate = facilityCovenant.getNoFrequencyDueDate();
    this.isExists = facilityCovenant.getIsExists();
    this.complianceStatus = facilityCovenant.getComplianceStatus();
    this.accountId = facilityCovenant.getAccountId();
    this.displayOrder = facilityCovenant.getDisplayOrder();
    this.applicableType = facilityCovenant.getApplicableType();

    if (facilityCovenant.getFacilityCovenantFacilitiesSet() != null) {
      this.covenantFacilities =
          facilityCovenant.getFacilityCovenantFacilitiesSet().stream()
              .map(CovenantFacilitiesDTO::new)
              .toList();
    }
  }
}
