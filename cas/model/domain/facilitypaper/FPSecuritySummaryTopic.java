package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "T_FP_SECURITY_SUMMARY_TOPIC")
public class FPSecuritySummaryTopic extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FP_SEC_SUM_TOPIC")
    @SequenceGenerator(name = "SEQ_FP_SEC_SUM_TOPIC", sequenceName = "SEQ_FP_SEC_SUM_TOPIC", allocationSize = 1)
    @Column(name = "FP_SECURITY_SUMMARY_TOPIC_ID")
    private Integer fpSecuritySummaryTopicID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FP_SECURITY_SUMMARY_ID")
    private FPSecuritySummery fpSecuritySummary;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperID;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "SECURITY_TYPE")
    private String securityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "SECURITY_TYPE_GROUP")
    private DomainConstants.SecuritySummaryTypeGroupType securityTypeGroup;

    @Column(name = "COMPANY_VALUE")
    private BigDecimal companyValue;

    @Column(name = "COMPANY_PERCENTAGE")
    private Double companyPercentage;

    @Column(name = "GROUP_VALUE")
    private BigDecimal groupValue;

    @Column(name = "GROUP_PERCENTAGE")
    private Double groupPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFpSecuritySummaryTopicID() {
        return fpSecuritySummaryTopicID;
    }

    public void setFpSecuritySummaryTopicID(Integer fpSecuritySummaryTopicID) {
        this.fpSecuritySummaryTopicID = fpSecuritySummaryTopicID;
    }

    public FPSecuritySummery getFpSecuritySummary() {
        return fpSecuritySummary;
    }

    public void setFpSecuritySummary(FPSecuritySummery fpSecuritySummary) {
        this.fpSecuritySummary = fpSecuritySummary;
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
        return companyValue;
    }

    public void setCompanyValue(BigDecimal companyValue) {
        this.companyValue = companyValue;
    }

    public Double getCompanyPercentage() {
        return companyPercentage;
    }

    public void setCompanyPercentage(Double companyPercentage) {
        this.companyPercentage = companyPercentage;
    }

    public BigDecimal getGroupValue() {
        return groupValue;
    }

    public void setGroupValue(BigDecimal groupValue) {
        this.groupValue = groupValue;
    }

    public Double getGroupPercentage() {
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
}
