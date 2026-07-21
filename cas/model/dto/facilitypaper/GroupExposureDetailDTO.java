package com.itechro.cas.model.dto.facilitypaper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.domain.facilitypaper.GroupExposureDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupExposureDetailDTO implements Serializable {

    private Integer groupExposureID;

    private String customerID;

    private String customerName;

    private BigDecimal fundedTotalLimit;

    private BigDecimal fundedOutstandingAmount;

    private BigDecimal nonFundedTotalLimit;

    private BigDecimal nonFundedOutstandingAmount;

    private String parentCustID;

    private Object relationshipCode;

    private BigDecimal totalSanctionLimit;
    
    private BigDecimal  outstandingAmount;

    private BigDecimal  lienAmount;

    private Integer facilityID;

    @JsonProperty("isSelected")
    private Integer isSelected;

    @JsonProperty("isPrimary")
    private Integer isPrimary;

    public GroupExposureDetailDTO(GroupExposureDetail exposureDetail) {
        this.groupExposureID = exposureDetail.getGroupExposureID();
        this.customerID = exposureDetail.getCustomerID();
        this.customerName = exposureDetail.getCustomerName();
        this.fundedTotalLimit = exposureDetail.getFundedLimitTotal();
        this.fundedOutstandingAmount = exposureDetail.getFundedOutstanding();
        this.nonFundedTotalLimit = exposureDetail.getNonFundedLimitTotal();
        this.nonFundedOutstandingAmount = exposureDetail.getNonFundedOutstanding();
        this.parentCustID = exposureDetail.getParentCustID();
        this.relationshipCode = exposureDetail.getRelationshipCode();
        this.totalSanctionLimit = exposureDetail.getTotalSanctionLimit();
        this.outstandingAmount = exposureDetail.getOutstandingAmount();
        this.lienAmount = exposureDetail.getLienAmount();
        this.facilityID = exposureDetail.getFacilityID();
        this.isSelected = exposureDetail.getIsSelected();
        this.isPrimary = exposureDetail.getIsPrimary();
    }
}
