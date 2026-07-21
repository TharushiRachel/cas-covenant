package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.FPSecuritySummaryTopic;
import com.itechro.cas.util.DecimalCalculator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FPSecuritySummaryTopicDTO implements Serializable {

    private Integer fpSecuritySummaryTopicID;

    private Integer fpSecuritySummaryID;

    private Integer facilityPaperID;

    private Integer displayOrder;

    private String securityType;

    private DomainConstants.SecuritySummaryTypeGroupType securityTypeGroup;

    private BigDecimal companyValue;

    private Double companyPercentage;

    private BigDecimal groupValue;

    private Double groupPercentage;

    private AppsConstants.Status status;

    public FPSecuritySummaryTopicDTO() {
    }

    public FPSecuritySummaryTopicDTO(FPSecuritySummaryTopic fpSecuritySummaryTopic) {
        this.fpSecuritySummaryTopicID = fpSecuritySummaryTopic.getFpSecuritySummaryTopicID();
        this.fpSecuritySummaryID = fpSecuritySummaryTopic.getFpSecuritySummary().getFpSecuritySummeryID();
        this.facilityPaperID = fpSecuritySummaryTopic.getFacilityPaperID();
        this.displayOrder = fpSecuritySummaryTopic.getDisplayOrder();
        this.securityType = fpSecuritySummaryTopic.getSecurityType();
        this.securityTypeGroup = fpSecuritySummaryTopic.getSecurityTypeGroup();
        this.companyValue = fpSecuritySummaryTopic.getCompanyValue();
        this.companyPercentage = fpSecuritySummaryTopic.getCompanyPercentage();
        this.groupValue = fpSecuritySummaryTopic.getGroupValue();
        this.groupPercentage = fpSecuritySummaryTopic.getGroupPercentage();
        this.status = fpSecuritySummaryTopic.getStatus();
    }

    public Integer getFpSecuritySummaryTopicID() {
        return fpSecuritySummaryTopicID;
    }

    public void setFpSecuritySummaryTopicID(Integer fpSecuritySummaryTopicID) {
        this.fpSecuritySummaryTopicID = fpSecuritySummaryTopicID;
    }

    public Integer getFpSecuritySummaryID() {
        return fpSecuritySummaryID;
    }

    public void setFpSecuritySummaryID(Integer fpSecuritySummaryID) {
        this.fpSecuritySummaryID = fpSecuritySummaryID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public DomainConstants.SecuritySummaryTypeGroupType getSecurityTypeGroup() {
        return securityTypeGroup;
    }

    public void setSecurityTypeGroup(DomainConstants.SecuritySummaryTypeGroupType securityTypeGroup) {
        this.securityTypeGroup = securityTypeGroup;
    }

    public BigDecimal getCompanyValue() {
        if (companyValue == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return companyValue;
    }

    public void setCompanyValue(BigDecimal companyValue) {
        this.companyValue = companyValue;
    }

    public Double getCompanyPercentage() {
        if (companyPercentage == null) {
            this.companyPercentage = new Double("0.0");
        }
        return companyPercentage;
    }

    public void setCompanyPercentage(Double companyPercentage) {
        this.companyPercentage = companyPercentage;
    }

    public BigDecimal getGroupValue() {
        if (groupValue == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return groupValue;
    }

    public void setGroupValue(BigDecimal groupValue) {
        this.groupValue = groupValue;
    }

    public Double getGroupPercentage() {
        if (groupPercentage == null) {
            this.groupPercentage = new Double("0.0");
        }
        return groupPercentage;
    }

    public void setGroupPercentage(Double groupPercentage) {
        this.groupPercentage = groupPercentage;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public BigDecimal getGroupPercentage(BigDecimal total) {
        if (total != null && !total.equals(DecimalCalculator.getDefaultZero())) {
            return DecimalCalculator.multiply(DecimalCalculator.divide(this.getGroupValue(), total), 100).setScale(2, RoundingMode.CEILING);
        } else {
            return DecimalCalculator.getDefaultZero();
        }
    }

    public BigDecimal getCompanyPercentage(BigDecimal total) {
        if (total != null && !total.equals(DecimalCalculator.getDefaultZero())) {
            return DecimalCalculator.multiply(DecimalCalculator.divide(this.getCompanyValue(), total), 100).setScale(2, RoundingMode.CEILING);
        } else {
            return DecimalCalculator.getDefaultZero();
        }
    }

    @Override
    public String toString() {
        return "FPSecuritySummaryTopicDTO{" +
                "fpSecuritySummaryTopicID=" + fpSecuritySummaryTopicID +
                ", fpSecuritySummaryID=" + fpSecuritySummaryID +
                ", facilityPaperID=" + facilityPaperID +
                ", displayOrder=" + displayOrder +
                ", securityType='" + securityType + '\'' +
                ", securityTypeGroup='" + securityTypeGroup + '\'' +
                ", companyValue=" + companyValue +
                ", companyPercentage=" + companyPercentage +
                ", groupValue=" + groupValue +
                ", groupPercentage=" + groupPercentage +
                ", status=" + status +
                '}';
    }
}
