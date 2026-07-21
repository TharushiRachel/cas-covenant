package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import com.itechro.cas.model.domain.casmaster.CreditFacilityType;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_AF_FACILITY")
public class AFFacility extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_FACILITY")
    @SequenceGenerator(name = "SEQ_T_AF_FACILITY", sequenceName = "SEQ_T_AF_FACILITY", allocationSize = 1)
    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "FACILITY_REF_CODE")
    private String facilityRefCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TYPE_ID")
    private CreditFacilityType creditFacilityType;

    @Column(name = "FACILITY_CURRENCY")
    private String facilityCurrency;

    @Column(name = "DISBURSEMENT_ACC_NUM")
    private String disbursementAccNumber;

    @Column(name = "PARENT_REC_ID")
    private Integer parentFacilityID;

    @Column(name = "FACILITY_AMOUNT")
    private BigDecimal facilityAmount;

    @Column(name = "EXISTING_AMOUNT")
    private BigDecimal existingAmount;

    @Column(name = "ORIGINAL_AMOUNT")
    private BigDecimal originalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_COOPERATE")
    private AppsConstants.YesNo isCooperate;

    @Column(name = "OUTSTANDING_AMOUNT")
    private BigDecimal outstandingAmount;

    @Column(name = "SECTOR_ID")
    private Integer sectorID;

    @Column(name = "SUB_SECTOR_ID")
    private Integer subSectorID;

    @Column(name = "CASH_FLOW_GENERATION_SECTOR_ID")
    private Integer cashFlowGenerationSectorID;

    @Column(name = "PURPOSE_OF_ADVANCE")
    private String purposeOfAdvance;

    @Column(name = "PURPOSE")
    private String purpose;

    @Column(name = "FACILITY_TYPE")
    private String facilityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ONE_OFF")
    private AppsConstants.YesNo isOneOff;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_DIRECT_FACILITY")
    private AppsConstants.YesNo directFacility;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_SERIES_OF_LOANS")
    private AppsConstants.YesNo seriesOfLoans;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_REVOLVING")
    private AppsConstants.YesNo revolving;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_REDUCTION")
    private AppsConstants.YesNo reduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ENHANCEMENT")
    private AppsConstants.YesNo enhancement;

    @Column(name = "REPAYMENT")
    private String repayment;

    @Column(name = "CONDITION")
    private String condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_NEW")
    private AppsConstants.YesNo isNew;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "REMARK")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afFacility")
    private Set<AFFacilitySecurity> afFacilitySecurities;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afFacility")
    private Set<AFFacilityVitalInfoData> afFacilityVitalInfoData;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afFacility")
    private Set<AFFacilityInterestRate> afFacilityInterestRates;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afFacility")
    private Set<AFFacilityDocument> afFacilityDocuments;

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public String getFacilityRefCode() {
        return facilityRefCode;
    }

    public void setFacilityRefCode(String facilityRefCode) {
        this.facilityRefCode = facilityRefCode;
    }

    public CreditFacilityTemplate getCreditFacilityTemplate() {
        return creditFacilityTemplate;
    }

    public void setCreditFacilityTemplate(CreditFacilityTemplate creditFacilityTemplate) {
        this.creditFacilityTemplate = creditFacilityTemplate;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }

    public CreditFacilityType getCreditFacilityType() {
        return creditFacilityType;
    }

    public void setCreditFacilityType(CreditFacilityType creditFacilityType) {
        this.creditFacilityType = creditFacilityType;
    }

    public String getFacilityCurrency() {
        return facilityCurrency;
    }

    public void setFacilityCurrency(String facilityCurrency) {
        this.facilityCurrency = facilityCurrency;
    }

    public String getDisbursementAccNumber() {
        return disbursementAccNumber;
    }

    public void setDisbursementAccNumber(String disbursementAccNumber) {
        this.disbursementAccNumber = disbursementAccNumber;
    }

    public Integer getParentFacilityID() {
        return parentFacilityID;
    }

    public void setParentFacilityID(Integer parentFacilityID) {
        this.parentFacilityID = parentFacilityID;
    }

    public BigDecimal getFacilityAmount() {
        return facilityAmount;
    }

    public void setFacilityAmount(BigDecimal facilityAmount) {
        this.facilityAmount = facilityAmount;
    }

    public BigDecimal getExistingAmount() {
        return existingAmount;
    }

    public void setExistingAmount(BigDecimal existingAmount) {
        this.existingAmount = existingAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public AppsConstants.YesNo getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(AppsConstants.YesNo isCooperate) {
        this.isCooperate = isCooperate;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public Integer getSubSectorID() {
        return subSectorID;
    }

    public void setSubSectorID(Integer subSectorID) {
        this.subSectorID = subSectorID;
    }

    public Integer getCashFlowGenerationSectorID() {
        return cashFlowGenerationSectorID;
    }

    public void setCashFlowGenerationSectorID(Integer cashFlowGenerationSectorID) {
        this.cashFlowGenerationSectorID = cashFlowGenerationSectorID;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public AppsConstants.YesNo getIsOneOff() {
        return isOneOff;
    }

    public void setIsOneOff(AppsConstants.YesNo isOneOff) {
        this.isOneOff = isOneOff;
    }

    public AppsConstants.YesNo getDirectFacility() {
        return directFacility;
    }

    public void setDirectFacility(AppsConstants.YesNo directFacility) {
        this.directFacility = directFacility;
    }

    public AppsConstants.YesNo getSeriesOfLoans() {
        return seriesOfLoans;
    }

    public void setSeriesOfLoans(AppsConstants.YesNo seriesOfLoans) {
        this.seriesOfLoans = seriesOfLoans;
    }

    public AppsConstants.YesNo getRevolving() {
        return revolving;
    }

    public void setRevolving(AppsConstants.YesNo revolving) {
        this.revolving = revolving;
    }

    public AppsConstants.YesNo getReduction() {
        return reduction;
    }

    public void setReduction(AppsConstants.YesNo reduction) {
        this.reduction = reduction;
    }

    public AppsConstants.YesNo getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(AppsConstants.YesNo enhancement) {
        this.enhancement = enhancement;
    }

    public String getRepayment() {
        return repayment;
    }

    public void setRepayment(String repayment) {
        this.repayment = repayment;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public AppsConstants.YesNo getIsNew() {
        return isNew;
    }

    public void setIsNew(AppsConstants.YesNo isNew) {
        this.isNew = isNew;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Set<AFFacilitySecurity> getAfFacilitySecurities() {
        if (afFacilitySecurities == null) {
            this.afFacilitySecurities = new HashSet<>();
        }
        return afFacilitySecurities;
    }

    public void setAfFacilitySecurities(Set<AFFacilitySecurity> afFacilitySecurities) {
        this.afFacilitySecurities = afFacilitySecurities;
    }

    public List<AFFacilitySecurity> getOrderedSecurities() {
        return getAfFacilitySecurities().stream().sorted(new Comparator<AFFacilitySecurity>() {
            @Override
            public int compare(AFFacilitySecurity o1, AFFacilitySecurity o2) {
                return o1.getFacilitySecurityID().compareTo(o2.getFacilitySecurityID());
            }
        }).collect(Collectors.toList());
    }

    public void addAFFacilitySecurity(AFFacilitySecurity afFacilitySecurity) {
        afFacilitySecurity.setAfFacility(this);
        this.getAfFacilitySecurities().add(afFacilitySecurity);
    }

    public Set<AFFacilityVitalInfoData> getAfFacilityVitalInfoData() {
        if (afFacilityVitalInfoData == null) {
            afFacilityVitalInfoData = new HashSet<>();
        }
        return afFacilityVitalInfoData;
    }

    public List<AFFacilityVitalInfoData> getOrderedAfFacilityVitalInfoData() {
        return getAfFacilityVitalInfoData().stream().sorted(new Comparator<AFFacilityVitalInfoData>() {
            @Override
            public int compare(AFFacilityVitalInfoData o1, AFFacilityVitalInfoData o2) {
                return o1.getFacilityVitalInfoDataID().compareTo(o2.getFacilityVitalInfoDataID());
            }
        }).collect(Collectors.toList());
    }

    public void setAfFacilityVitalInfoData(Set<AFFacilityVitalInfoData> afFacilityVitalInfoData) {
        this.afFacilityVitalInfoData = afFacilityVitalInfoData;
    }

    public void addAfFacilityVitalInfoData(AFFacilityVitalInfoData afFacilityVitalInfoData) {
        afFacilityVitalInfoData.setAfFacility(this);
        this.getAfFacilityVitalInfoData().add(afFacilityVitalInfoData);
    }

    public AFFacilityVitalInfoData getAFFacilityVitalInfoDataByID(Integer facilityVitalInfoDataID) {
        return this.getAfFacilityVitalInfoData().stream().
                filter(afFacilityVitalInfoData -> {
                    return facilityVitalInfoDataID.equals(afFacilityVitalInfoData.getFacilityVitalInfoDataID());
                }).findFirst().orElse(null);
    }

    public AFFacilityVitalInfoData getAFFacilityVitalInfoDataByCftVitalInfoID(Integer cftVitalInfoDataID) {
        return this.getAfFacilityVitalInfoData().stream().
                filter(afFacilityVitalInfoData -> {
                    return cftVitalInfoDataID.equals(afFacilityVitalInfoData.getCftVitalInfoID());
                }).findFirst().orElse(null);
    }

    public Set<AFFacilityInterestRate> getAfFacilityInterestRates() {
        if (afFacilityInterestRates == null) {
            afFacilityInterestRates = new HashSet<>();
        }
        return afFacilityInterestRates;
    }

    public List<AFFacilityInterestRate> getOrderedAfFacilityInterestRates() {
        return getAfFacilityInterestRates().stream().sorted(new Comparator<AFFacilityInterestRate>() {
            @Override
            public int compare(AFFacilityInterestRate o1, AFFacilityInterestRate o2) {
                return o1.getFacilityInterestRateID().compareTo(o2.getFacilityInterestRateID());
            }
        }).collect(Collectors.toList());
    }

    public void setAfFacilityInterestRates(Set<AFFacilityInterestRate> afFacilityInterestRates) {
        this.afFacilityInterestRates = afFacilityInterestRates;
    }

    public void addAfFacilityInterestRates(AFFacilityInterestRate afFacilityInterestRate) {
        afFacilityInterestRate.setAfFacility(this);
        this.getAfFacilityInterestRates().add(afFacilityInterestRate);
    }

    public AFFacilityInterestRate getAFFacilityInterestRateByID(Integer afFacilityInterestRateID) {
        return this.getAfFacilityInterestRates().stream().
                filter(afFacilityInterestRate -> {
                    return afFacilityInterestRateID.equals(afFacilityInterestRate.getFacilityInterestRateID());
                }).findFirst().orElse(null);
    }

    public AFFacilityInterestRate getAFFacilityInterestRateByCFTInterestRateID(Integer cftInterestRateID) {
        return this.getAfFacilityInterestRates().stream().
                filter(facilityInterestRate -> {
                    return cftInterestRateID.equals(facilityInterestRate.getCftInterestRateID());
                }).findFirst().orElse(null);
    }

    public BigDecimal getFacilityActualAmount() {
        if (getIsNew() == AppsConstants.YesNo.N) {
            return getOutstandingAmount();
        } else {
            return getFacilityAmount();
        }
    }

    public Set<AFFacilityDocument> getAfFacilityDocuments() {
        if (afFacilityDocuments == null) {
            this.afFacilityDocuments = new HashSet<>();
        }
        return afFacilityDocuments;
    }

    public void setAfFacilityDocuments(Set<AFFacilityDocument> afFacilityDocuments) {
        this.afFacilityDocuments = afFacilityDocuments;
    }

    public AFFacilityDocument getAFFacilityDocumentsByID(Integer afFacilityDocumentID) {
        return this.getAfFacilityDocuments().stream().
                filter(facilityDocument -> {
                    return afFacilityDocumentID.equals(facilityDocument.getAfFacilityDocumentID());
                }).findFirst().orElse(null);
    }

    public void addAFFacilityDocuments(AFFacilityDocument afFacilityDocument) {
        afFacilityDocument.setAfFacility(this);
        this.getAfFacilityDocuments().add(afFacilityDocument);
    }

    public List<AFFacilityDocument> getOrderedAfFacilityDocuments() {
        return getAfFacilityDocuments().stream().sorted(new Comparator<AFFacilityDocument>() {
            @Override
            public int compare(AFFacilityDocument o1, AFFacilityDocument o2) {
                return o1.getAfFacilityDocumentID().compareTo(o2.getAfFacilityDocumentID());
            }
        }).collect(Collectors.toList());
    }

}
