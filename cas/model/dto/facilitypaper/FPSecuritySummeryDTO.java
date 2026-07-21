package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.FPSecuritySummaryTopic;
import com.itechro.cas.model.domain.facilitypaper.FPSecuritySummery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FPSecuritySummeryDTO implements Serializable {

    private Integer fpSecuritySummeryID;

    private Integer facilityPaperID;

    private BigDecimal companyCash;

    private BigDecimal groupCash;

    private BigDecimal companyProperty;

    private BigDecimal groupProperty;

    private BigDecimal companySubTotalOne;

    private BigDecimal groupSubTotalOne;

    private BigDecimal companyInvoiceReceivables;

    private BigDecimal groupInvoiceReceivables;

    private BigDecimal companyCorporateGuarantees;

    private BigDecimal groupCorporateGuarantees;

    private BigDecimal companyLeaseIndenture;

    private BigDecimal groupLeaseIndenture;

    private BigDecimal companySubTotalTwo;

    private BigDecimal groupSubTotalTwo;

    private BigDecimal companyClean;

    private BigDecimal groupClean;

    private BigDecimal companyTotal;

    private BigDecimal groupTotal;

    private String limitSummery;

    private DomainConstants.FacilitySecuritySummaryType facilitySecuritySummaryType;

    private List<FPSecuritySummaryTopicDTO> fpSecuritySummaryTopicDTOS;

    private BigDecimal companySubTotalThree;

    private BigDecimal groupSubTotalThree;

    private BigDecimal companySubTotalFour;

    private BigDecimal groupSubTotalFour;


    public FPSecuritySummeryDTO() {
    }

    public FPSecuritySummeryDTO(FPSecuritySummery fpSecuritySummery) {
        this.fpSecuritySummeryID = fpSecuritySummery.getFpSecuritySummeryID();
        if (fpSecuritySummery.getFacilityPaper() != null) {
            this.facilityPaperID = fpSecuritySummery.getFacilityPaper().getFacilityPaperID();
        }

        this.companyCash = fpSecuritySummery.getCompanyCash();
        this.groupCash = fpSecuritySummery.getGroupCash();
        this.companyProperty = fpSecuritySummery.getCompanyProperty();
        this.groupProperty = fpSecuritySummery.getGroupProperty();
        this.companySubTotalOne = fpSecuritySummery.getCompanySubTotalOne();
        this.groupSubTotalOne = fpSecuritySummery.getGroupSubTotalOne();
        this.companyInvoiceReceivables = fpSecuritySummery.getCompanyInvoiceReceivables();
        this.groupInvoiceReceivables = fpSecuritySummery.getGroupInvoiceReceivables();
        this.companyCorporateGuarantees = fpSecuritySummery.getCompanyCorporateGuarantees();
        this.groupCorporateGuarantees = fpSecuritySummery.getGroupCorporateGuarantees();
        this.companyLeaseIndenture = fpSecuritySummery.getCompanyLeaseIndenture();
        this.groupLeaseIndenture = fpSecuritySummery.getGroupLeaseIndenture();
        this.companySubTotalTwo = fpSecuritySummery.getCompanySubTotalTwo();
        this.groupSubTotalTwo = fpSecuritySummery.getGroupSubTotalTwo();
        this.companyClean = fpSecuritySummery.getCompanyClean();
        this.groupClean = fpSecuritySummery.getGroupClean();
        this.companyTotal = fpSecuritySummery.getCompanyTotal();
        this.groupTotal = fpSecuritySummery.getGroupTotal();
        this.limitSummery = fpSecuritySummery.getLimitSummery();
        this.facilitySecuritySummaryType = fpSecuritySummery.getFacilitySecuritySummaryType();
        this.companySubTotalThree = fpSecuritySummery.getCompanySubTotalThree();
        this.groupSubTotalThree = fpSecuritySummery.getGroupSubTotalThree();
        this.companySubTotalFour = fpSecuritySummery.getCompanySubTotalFour();
        this.groupSubTotalFour = fpSecuritySummery.getGroupSubTotalFour();

        for (FPSecuritySummaryTopic fpSecuritySummaryTopic : fpSecuritySummery.getOrderedFpSecuritySummaryTopics()) {
            if (fpSecuritySummaryTopic.getStatus() == AppsConstants.Status.ACT) {
                this.getFpSecuritySummaryTopicDTOS().add(new FPSecuritySummaryTopicDTO(fpSecuritySummaryTopic));
            }
        }

    }

    public Integer getFpSecuritySummeryID() {
        return fpSecuritySummeryID;
    }

    public void setFpSecuritySummeryID(Integer fpSecuritySummeryID) {
        this.fpSecuritySummeryID = fpSecuritySummeryID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
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

    public List<FPSecuritySummaryTopicDTO> getFpSecuritySummaryTopicDTOS() {
        if (fpSecuritySummaryTopicDTOS == null) {
            fpSecuritySummaryTopicDTOS = new ArrayList<>();
        }
        return fpSecuritySummaryTopicDTOS;
    }

    public void setFpSecuritySummaryTopicDTOS(List<FPSecuritySummaryTopicDTO> fpSecuritySummaryTopicDTOS) {
        this.fpSecuritySummaryTopicDTOS = fpSecuritySummaryTopicDTOS;
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

    @Override
    public String toString() {
        return "FPSecuritySummeryDTO{" +
                "fpSecuritySummeryID=" + fpSecuritySummeryID +
                ", facilityPaperID=" + facilityPaperID +
                ", companyCash=" + companyCash +
                ", groupCash=" + groupCash +
                ", companyProperty=" + companyProperty +
                ", groupProperty=" + groupProperty +
                ", companySubTotalOne=" + companySubTotalOne +
                ", groupSubTotalOne=" + groupSubTotalOne +
                ", companyInvoiceReceivables=" + companyInvoiceReceivables +
                ", groupInvoiceReceivables=" + groupInvoiceReceivables +
                ", companyCorporateGuarantees=" + companyCorporateGuarantees +
                ", groupCorporateGuarantees=" + groupCorporateGuarantees +
                ", companyLeaseIndenture=" + companyLeaseIndenture +
                ", groupLeaseIndenture=" + groupLeaseIndenture +
                ", companySubTotalTwo=" + companySubTotalTwo +
                ", groupSubTotalTwo=" + groupSubTotalTwo +
                ", companyClean=" + companyClean +
                ", groupClean=" + groupClean +
                ", companyTotal=" + companyTotal +
                ", groupTotal=" + groupTotal +
                ", limitSummery='" + limitSummery + '\'' +
                ", facilitySecuritySummaryType=" + facilitySecuritySummaryType +
                ", companySubTotalThree=" + companySubTotalThree +
                ", groupSubTotalThree=" + groupSubTotalThree +
                ", companySubTotalFour=" + companySubTotalFour +
                ", groupSubTotalFour=" + groupSubTotalFour +
                '}';
    }
}
