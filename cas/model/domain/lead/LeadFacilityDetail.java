package com.itechro.cas.model.domain.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "T_LEAD_FACILITY_DETAIL")
public class LeadFacilityDetail extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_LEAD_FACILITY_DETAIL")
    @SequenceGenerator(name = "SEQ_T_LEAD_FACILITY_DETAIL", sequenceName = "SEQ_T_LEAD_FACILITY_DETAIL", allocationSize = 1)
    @Column(name = "LEAD_FACILITY_DETAIL_ID")
    private Integer leadFacilityDetailID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAD_ID")
    private Lead lead;

    @Column(name = "FACILITY_TEMPLATE_ID")
    private Integer facilityTemplateID;

    @Column(name = "FACILITY_TEMPLATE_NAME")
    private String facilityTemplateName;

    @Column(name = "CREDIT_FACILITY_TYPE")
    private String creditFacilityType;

    @Column(name = "CREDIT_FACILITY_TYPE_ID")
    private Integer creditFacilityTypeID;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "FACILITY_CURRENCY")
    private String facilityCurrency;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getLeadFacilityDetailID() {
        return leadFacilityDetailID;
    }

    public void setLeadFacilityDetailID(Integer leadFacilityDetailID) {
        this.leadFacilityDetailID = leadFacilityDetailID;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public Integer getFacilityTemplateID() {
        return facilityTemplateID;
    }

    public void setFacilityTemplateID(Integer facilityTemplateID) {
        this.facilityTemplateID = facilityTemplateID;
    }

    public String getFacilityTemplateName() {
        return facilityTemplateName;
    }

    public void setFacilityTemplateName(String facilityTemplateName) {
        this.facilityTemplateName = facilityTemplateName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getCreditFacilityType() {
        return creditFacilityType;
    }

    public void setCreditFacilityType(String creditFacilityType) {
        this.creditFacilityType = creditFacilityType;
    }

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
    }

    public String getFacilityCurrency() {
        return facilityCurrency;
    }

    public void setFacilityCurrency(String facilityCurrency) {
        this.facilityCurrency = facilityCurrency;
    }
}
