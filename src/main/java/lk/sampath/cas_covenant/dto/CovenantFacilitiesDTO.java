package lk.sampath.cas_covenant.dto;

import java.math.BigDecimal;
import lk.sampath.cas_covenant.entity.FacilityCovenant;
import lk.sampath.cas_covenant.entity.FacilityCovenantFacilities;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CovenantFacilitiesDTO {

  private Integer facilityCovenantId;

  private Integer applicationCovenantId;

  private Integer facilityID;

  private Integer creditFacilityTemplateID;

  private String creditFacilityName;

  private String facilityRefCode;

  private String facilityCurrency;

  private BigDecimal facilityAmount;

  private Integer displayOrder;

  public CovenantFacilitiesDTO(FacilityCovenantFacilities facilityCovenantFacilities) {
    this.facilityCovenantId = facilityCovenantFacilities.getFacilityCovenantId();
    this.applicationCovenantId = facilityCovenantFacilities.getApplicationLevelCovenant().getApplicationCovenantId();
    this.facilityID = facilityCovenantFacilities.getFacilityID();
    this.creditFacilityTemplateID = facilityCovenantFacilities.getCreditFacilityTemplateID();
    this.creditFacilityName = facilityCovenantFacilities.getCreditFacilityName();
    this.facilityRefCode = facilityCovenantFacilities.getFacilityRefCode();
    this.facilityCurrency = facilityCovenantFacilities.getFacilityCurrency();
    this.facilityAmount = facilityCovenantFacilities.getFacilityAmount();
    this.displayOrder = facilityCovenantFacilities.getDisplayOrder();
  }
}
