package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.lead.LeadFacilityDetail;

import java.io.Serializable;
import java.math.BigDecimal;

public class LeadFacilityDetailEmailDTO implements Serializable {

    private Integer leadFacilityDetailID;

    private Integer leadID;

    private Integer facilityTemplateID;

    private String facilityTemplateName;

    private String creditFacilityType;

    private Integer creditFacilityTypeID;

    private String amount;

    private String facilityCurrency;

    private String description;

    private AppsConstants.Status status;

    public LeadFacilityDetailEmailDTO() {
    }

    public LeadFacilityDetailEmailDTO(LeadFacilityDetail leadFacilityDetail) {
        this.leadFacilityDetailID = leadFacilityDetail.getLeadFacilityDetailID();
        this.leadID = leadFacilityDetail.getLead().getLeadID();
        this.facilityTemplateID = leadFacilityDetail.getFacilityTemplateID();
        this.facilityTemplateName = leadFacilityDetail.getFacilityTemplateName();
        this.creditFacilityType = leadFacilityDetail.getCreditFacilityType();
        this.creditFacilityTypeID = leadFacilityDetail.getCreditFacilityTypeID();
        this.amount = String.valueOf(leadFacilityDetail.getAmount());
        this.facilityCurrency = leadFacilityDetail.getFacilityCurrency();
        this.description = leadFacilityDetail.getDescription();
        this.status = leadFacilityDetail.getStatus();
    }

    public Integer getLeadFacilityDetailID() {
        return leadFacilityDetailID;
    }

    public void setLeadFacilityDetailID(Integer leadFacilityDetailID) {
        this.leadFacilityDetailID = leadFacilityDetailID;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    @Override
    public String toString() {
        return "LeadFacilityDetailEmailDTO{" +
                "leadFacilityDetailID=" + leadFacilityDetailID +
                ", leadID=" + leadID +
                ", facilityTemplateID=" + facilityTemplateID +
                ", facilityTemplateName='" + facilityTemplateName + '\'' +
                ", creditFacilityType='" + creditFacilityType + '\'' +
                ", creditFacilityTypeID=" + creditFacilityTypeID +
                ", amount='" + amount + '\'' +
                ", facilityCurrency='" + facilityCurrency + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

}
