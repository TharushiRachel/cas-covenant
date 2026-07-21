package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_FP_SECURITY_SUMMERY")
public class FPSecuritySummery extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_SECURITY_SUMMERY")
    @SequenceGenerator(name = "SEQ_T_FP_SECURITY_SUMMERY", sequenceName = "SEQ_T_FP_SECURITY_SUMMERY", allocationSize = 1)
    @Column(name = "FP_SECURITY_SUMMERY_ID")
    private Integer fpSecuritySummeryID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "COMPANY_CASH")
    private BigDecimal companyCash;

    @Column(name = "GROUP_CASH")
    private BigDecimal groupCash;

    @Column(name = "COMPANY_PROPERTY")
    private BigDecimal companyProperty;

    @Column(name = "GROUP_PROPERTY")
    private BigDecimal groupProperty;

    @Column(name = "COMPANY_SUB_TOTAL_ONE")
    private BigDecimal companySubTotalOne;

    @Column(name = "GROUP_SUB_TOTAL_ONE")
    private BigDecimal groupSubTotalOne;

    @Column(name = "COMPANY_INVOICE_RECEIVABLE")
    private BigDecimal companyInvoiceReceivables;

    @Column(name = "GROUP_INVOICE_RECEIVABLE")
    private BigDecimal groupInvoiceReceivables;

    @Column(name = "COMPANY_CORPORATE_GUARANTEES")
    private BigDecimal companyCorporateGuarantees;

    @Column(name = "GROUP_CORPORATE_GUARANTEES")
    private BigDecimal groupCorporateGuarantees;

    @Column(name = "COMPANY_LEASE_INDENTURE")
    private BigDecimal companyLeaseIndenture;

    @Column(name = "GROUP_LEASE_INDENTURE")
    private BigDecimal groupLeaseIndenture;

    @Column(name = "COMPANY_SUB_TOTAL_TWO")
    private BigDecimal companySubTotalTwo;

    @Column(name = "GROUP_SUB_TOTAL_TWO")
    private BigDecimal groupSubTotalTwo;

    @Column(name = "COMPANY_CLEAN")
    private BigDecimal companyClean;

    @Column(name = "GROUP_CLEAN")
    private BigDecimal groupClean;

    @Column(name = "COMPANY_TOTAL")
    private BigDecimal companyTotal;

    @Column(name = "GROUP_TOTAL")
    private BigDecimal groupTotal;

    @Column(name = "LIMIT_SUMMERY")
    private String limitSummery;

    @Enumerated(EnumType.STRING)
    @Column(name = "SUMMARY_TYPE")
    private DomainConstants.FacilitySecuritySummaryType facilitySecuritySummaryType;

    @Column(name = "COMPANY_SUB_TOTAL_THREE")
    private BigDecimal companySubTotalThree;

    @Column(name = "GROUP_SUB_TOTAL_THREE")
    private BigDecimal groupSubTotalThree;

    @Column(name = "COMPANY_SUB_TOTAL_FOUR")
    private BigDecimal companySubTotalFour;

    @Column(name = "GROUP_SUB_TOTAL_FOUR")
    private BigDecimal groupSubTotalFour;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "fpSecuritySummary")
    private Set<FPSecuritySummaryTopic> fpSecuritySummaryTopics;

    public Integer getFpSecuritySummeryID() {
        return fpSecuritySummeryID;
    }

    public void setFpSecuritySummeryID(Integer fpSecuritySummeryID) {
        this.fpSecuritySummeryID = fpSecuritySummeryID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public BigDecimal getCompanyCash() {
        return companyCash;
    }

    public void setCompanyCash(BigDecimal companyCash) {
        this.companyCash = companyCash;
    }

    public BigDecimal getGroupCash() {
        return groupCash;
    }

    public void setGroupCash(BigDecimal groupCash) {
        this.groupCash = groupCash;
    }

    public BigDecimal getCompanyProperty() {
        return companyProperty;
    }

    public void setCompanyProperty(BigDecimal companyProperty) {
        this.companyProperty = companyProperty;
    }

    public BigDecimal getGroupProperty() {
        return groupProperty;
    }

    public void setGroupProperty(BigDecimal groupProperty) {
        this.groupProperty = groupProperty;
    }

    public BigDecimal getCompanySubTotalOne() {
        return companySubTotalOne;
    }

    public void setCompanySubTotalOne(BigDecimal companySubTotalOne) {
        this.companySubTotalOne = companySubTotalOne;
    }

    public BigDecimal getGroupSubTotalOne() {
        return groupSubTotalOne;
    }

    public void setGroupSubTotalOne(BigDecimal groupSubTotalOne) {
        this.groupSubTotalOne = groupSubTotalOne;
    }

    public BigDecimal getCompanyInvoiceReceivables() {
        return companyInvoiceReceivables;
    }

    public void setCompanyInvoiceReceivables(BigDecimal companyInvoiceReceivables) {
        this.companyInvoiceReceivables = companyInvoiceReceivables;
    }

    public BigDecimal getGroupInvoiceReceivables() {
        return groupInvoiceReceivables;
    }

    public void setGroupInvoiceReceivables(BigDecimal groupInvoiceReceivables) {
        this.groupInvoiceReceivables = groupInvoiceReceivables;
    }

    public BigDecimal getCompanyCorporateGuarantees() {
        return companyCorporateGuarantees;
    }

    public void setCompanyCorporateGuarantees(BigDecimal companyCorporateGuarantees) {
        this.companyCorporateGuarantees = companyCorporateGuarantees;
    }

    public BigDecimal getGroupCorporateGuarantees() {
        return groupCorporateGuarantees;
    }

    public void setGroupCorporateGuarantees(BigDecimal groupCorporateGuarantees) {
        this.groupCorporateGuarantees = groupCorporateGuarantees;
    }

    public BigDecimal getCompanyLeaseIndenture() {
        return companyLeaseIndenture;
    }

    public void setCompanyLeaseIndenture(BigDecimal companyLeaseIndenture) {
        this.companyLeaseIndenture = companyLeaseIndenture;
    }

    public BigDecimal getGroupLeaseIndenture() {
        return groupLeaseIndenture;
    }

    public void setGroupLeaseIndenture(BigDecimal groupLeaseIndenture) {
        this.groupLeaseIndenture = groupLeaseIndenture;
    }

    public BigDecimal getCompanySubTotalTwo() {
        return companySubTotalTwo;
    }

    public void setCompanySubTotalTwo(BigDecimal companySubTotalTwo) {
        this.companySubTotalTwo = companySubTotalTwo;
    }

    public BigDecimal getGroupSubTotalTwo() {
        return groupSubTotalTwo;
    }

    public void setGroupSubTotalTwo(BigDecimal groupSubTotalTwo) {
        this.groupSubTotalTwo = groupSubTotalTwo;
    }

    public BigDecimal getCompanyClean() {
        return companyClean;
    }

    public void setCompanyClean(BigDecimal companyClean) {
        this.companyClean = companyClean;
    }

    public BigDecimal getGroupClean() {
        return groupClean;
    }

    public void setGroupClean(BigDecimal groupClean) {
        this.groupClean = groupClean;
    }

    public String getLimitSummery() {
        return limitSummery;
    }

    public void setLimitSummery(String limitSummery) {
        this.limitSummery = limitSummery;
    }

    public DomainConstants.FacilitySecuritySummaryType getFacilitySecuritySummaryType() {
        return facilitySecuritySummaryType;
    }

    public void setFacilitySecuritySummaryType(DomainConstants.FacilitySecuritySummaryType facilitySecuritySummaryType) {
        this.facilitySecuritySummaryType = facilitySecuritySummaryType;
    }

    public BigDecimal getCompanyTotal() {
        return companyTotal;
    }

    public void setCompanyTotal(BigDecimal companyTotal) {
        this.companyTotal = companyTotal;
    }

    public BigDecimal getGroupTotal() {
        return groupTotal;
    }

    public void setGroupTotal(BigDecimal groupTotal) {
        this.groupTotal = groupTotal;
    }

    public Set<FPSecuritySummaryTopic> getFpSecuritySummaryTopics() {
        if (fpSecuritySummaryTopics == null) {
            fpSecuritySummaryTopics = new HashSet<>();
        }
        return fpSecuritySummaryTopics;
    }

    public List<FPSecuritySummaryTopic> getOrderedFpSecuritySummaryTopics() {
        return getFpSecuritySummaryTopics().stream().sorted(new Comparator<FPSecuritySummaryTopic>() {
            @Override
            public int compare(FPSecuritySummaryTopic o1, FPSecuritySummaryTopic o2) {
                return o1.getDisplayOrder().compareTo(o2.getDisplayOrder());
            }
        }).collect(Collectors.toList());
    }

    public void setFpSecuritySummaryTopics(Set<FPSecuritySummaryTopic> fpSecuritySummaryTopics) {
        this.fpSecuritySummaryTopics = fpSecuritySummaryTopics;
    }

    public void addFpSecuritySummaryTopic(FPSecuritySummaryTopic fpSecuritySummaryTopic) {
        fpSecuritySummaryTopic.setFpSecuritySummary(this);
        this.getFpSecuritySummaryTopics().add(fpSecuritySummaryTopic);
    }


    public BigDecimal getCompanySubTotalThree() {
        return companySubTotalThree;
    }

    public void setCompanySubTotalThree(BigDecimal companySubTotalThree) {
        this.companySubTotalThree = companySubTotalThree;
    }

    public BigDecimal getGroupSubTotalThree() {
        return groupSubTotalThree;
    }

    public void setGroupSubTotalThree(BigDecimal groupSubTotalThree) {
        this.groupSubTotalThree = groupSubTotalThree;
    }

    public BigDecimal getCompanySubTotalFour() {
        return companySubTotalFour;
    }

    public void setCompanySubTotalFour(BigDecimal companySubTotalFour) {
        this.companySubTotalFour = companySubTotalFour;
    }

    public BigDecimal getGroupSubTotalFour() {
        return groupSubTotalFour;
    }

    public void setGroupSubTotalFour(BigDecimal groupSubTotalFour) {
        this.groupSubTotalFour = groupSubTotalFour;
    }
}
