package com.itechro.cas.model.domain.facilitypaper;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_GROUP_EXPOSURE_DETAIL")
public class GroupExposureDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_GROUP_EXPOSURE")
    @SequenceGenerator(name = "SEQ_T_GROUP_EXPOSURE", sequenceName = "SEQ_T_GROUP_EXPOSURE", allocationSize = 1)
    @Column(name = "GROUP_EXPOSURE_ID")
    private Integer groupExposureID;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private String customerID;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "FUNDED_LIMIT_TOTAL")
    private BigDecimal fundedLimitTotal;

    @Column(name = "FUNDED_LIMIT_OUTSTANDING")
    private BigDecimal fundedOutstanding;

    @Column(name = "NON_FUNDED_LIMIT_TOTAL")
    private BigDecimal nonFundedLimitTotal;

    @Column(name = "NON_FUNDED_LIMIT_OUTSTANDING")
    private BigDecimal nonFundedOutstanding;

    @Column(name = "PARENT_CUST_ID")
    private String parentCustID;

    @Column(name = "RELATIONSHIP_CODE")
    private String relationshipCode;

    @Column(name = "TOTAL_SANCTION_LIMIT")
    private BigDecimal totalSanctionLimit;

    @Column(name = "OUTSTANDING_AMOUNT")
    private BigDecimal outstandingAmount;

    @Column(name = "LIEN_AMOUNT")
    private BigDecimal lienAmount;

    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "IS_SELECTED")
    private Integer isSelected;

    @Column(name = "IS_PRIMARY")
    private Integer isPrimary;
}
