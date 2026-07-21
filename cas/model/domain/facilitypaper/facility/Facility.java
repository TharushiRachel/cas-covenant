package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import com.itechro.cas.model.domain.casmaster.CreditFacilityType;
import com.itechro.cas.model.domain.casmaster.FacilityCustomInfoData;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.model.dto.facilitypaper.UPCTemplateCommentHistoryDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_FACILITY")
public class Facility extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY")
    @SequenceGenerator(name = "SEQ_T_FACILITY", sequenceName = "SEQ_T_FACILITY", allocationSize = 1)
    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "FACILITY_REF_CODE")
    private String facilityRefCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ANNUAL")
    private AppsConstants.YesNo isAnnual;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ADDITIONAL")
    private AppsConstants.YesNo isAdditional;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_TERMS_AMENDED")
    private AppsConstants.YesNo isTermsAmended;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_RESTRUCTURE")
    private AppsConstants.YesNo isReStructure;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_RESCHEDULE")
    private AppsConstants.YesNo isReSchedule;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "REMARK")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    @Column(name = "LOAN_LIMIT_ID")
    private String loanLimitId;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityVitalInfoData> facilityVitalInfoData;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityDocument> facilityDocuments;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityInterestRate> facilityInterestRates;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityPurposeOfAdvance> facilityPurposeOfAdvances;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private FacilityRepayment facilityRepayment;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityFacilitySecurity> facilityFacilitySecurities;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityOtherFacilityInformation> facilityOtherFacilityInformations;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityRentalInformation> facilityRentalInformation;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityCustomInfoData> facilityCustomInfoData;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityCustomInfoData> facilityCustomFacilityInformations;

    @Transient
    private List<UPCTemplateCommentHistoryDTO> fusTraceList;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facility")
    private Set<FacilityCovenantFacilities> facilityCovenantFacilities;

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

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public CreditFacilityType getCreditFacilityType() {
        return creditFacilityType;
    }

    public void setCreditFacilityType(CreditFacilityType creditFacilityType) {
        this.creditFacilityType = creditFacilityType;
    }

    public FacilityRepayment getFacilityRepayment() {
        return facilityRepayment;
    }

    public void setFacilityRepayment(FacilityRepayment facilityRepayment) {
        this.facilityRepayment = facilityRepayment;
    }

    public Integer getParentFacilityID() {
        return parentFacilityID;
    }

    public void setParentFacilityID(Integer parentFacilityID) {
        this.parentFacilityID = parentFacilityID;
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

    public BigDecimal getFacilityAmount() {
        if (facilityAmount == null) {
            facilityAmount = DecimalCalculator.getDefaultZero();
        }
        return facilityAmount;
    }

    public void setFacilityAmount(BigDecimal facilityAmount) {
        this.facilityAmount = facilityAmount;
    }

    public BigDecimal getExistingAmount() {
        if (existingAmount == null) {
            existingAmount = DecimalCalculator.getDefaultZero();
        }
        return existingAmount;
    }

    public void setExistingAmount(BigDecimal existingAmount) {
        this.existingAmount = existingAmount;
    }

    public BigDecimal getOriginalAmount() {
        if (originalAmount == null) {
            originalAmount = DecimalCalculator.getDefaultZero();
        }
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
        if (outstandingAmount == null) {
            outstandingAmount = DecimalCalculator.getDefaultZero();
        }
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

    public Set<FacilityVitalInfoData> getFacilityVitalInfoData() {
        if (facilityVitalInfoData == null) {
            facilityVitalInfoData = new HashSet<>();
        }
        return facilityVitalInfoData;
    }

    public void setFacilityVitalInfoData(Set<FacilityVitalInfoData> facilityVitalInfoData) {
        this.facilityVitalInfoData = facilityVitalInfoData;
    }

    public FacilityVitalInfoData getFacilityVitalInfoDataByID(Integer facilityVitalInfoDataID) {
        return this.getFacilityVitalInfoData().stream().
                filter(facilityVitalInfoData -> {
                    return facilityVitalInfoDataID.equals(facilityVitalInfoData.getFacilityVitalInfoDataID());
                }).findFirst().orElse(null);
    }

    public FacilityVitalInfoData getFacilityVitalInfoDataByCftVitalInfoID(Integer cftVitalInfoDataID) {
        return this.getFacilityVitalInfoData().stream().
                filter(facilityVitalInfoData -> {
                    return cftVitalInfoDataID.equals(facilityVitalInfoData.getCftVitalInfoID());
                }).findFirst().orElse(null);
    }

    public Set<FacilityCustomInfoData> getFacilityCustomInfoData() {
        if(facilityCustomInfoData == null){
            facilityCustomInfoData = new HashSet<>();
        }
        return facilityCustomInfoData;
    }

    public void setFacilityCustomInfoData(Set<FacilityCustomInfoData> facilityCustomInfoData) {
        this.facilityCustomInfoData = facilityCustomInfoData;
    }

    public FacilityCustomInfoData getFacilityCustomInfoDataByID(Integer facilityCustomInfoDataID){
        return this.getFacilityCustomInfoData().stream()
                .filter(facilityCustomInfoData ->{
                    return facilityCustomInfoDataID.equals(facilityCustomInfoData.getFacilityCustomInfoDataID());
                }).findFirst().orElse(null);
    }

    public FacilityCustomInfoData getFacilityCustomInfoDataByCftCustomFacilityInfoID(Integer cftCustomFacilityInfoID){
        return this.getFacilityCustomInfoData().stream()
                .filter(facilityCustomInfoData ->{
                    return cftCustomFacilityInfoID.equals(facilityCustomInfoData.getCftCustomFacilityInfoID());
                }).findFirst().orElse(null);
    }

    public void addFacilityCustomInfoData(FacilityCustomInfoData facilityCustomInfoData){
        facilityCustomInfoData.setFacility(this);
        this.getFacilityCustomInfoData().add(facilityCustomInfoData);
    }

    public void addFacilityVitalInfoData(FacilityVitalInfoData facilityVitalInfoData) {
        facilityVitalInfoData.setFacility(this);
        this.getFacilityVitalInfoData().add(facilityVitalInfoData);
    }

    public Set<FacilityDocument> getFacilityDocuments() {
        if (facilityDocuments == null) {
            facilityDocuments = new HashSet<>();
        }
        return facilityDocuments;
    }

    public void setFacilityDocuments(Set<FacilityDocument> facilityDocuments) {
        this.facilityDocuments = facilityDocuments;
    }

    public void addFacilityDocuments(FacilityDocument facilityDocument) {
        facilityDocument.setFacility(this);
        this.getFacilityDocuments().add(facilityDocument);
    }

    public FacilityDocument getFacilityDocumentsByID(Integer facilityDocumentID) {
        return this.getFacilityDocuments().stream().
                filter(facilityDocument -> {
                    return facilityDocumentID.equals(facilityDocument.getFacilityDocumentID());
                }).findFirst().orElse(null);
    }

    public Set<FacilityInterestRate> getFacilityInterestRates() {
        if (facilityInterestRates == null) {
            facilityInterestRates = new HashSet<>();
        }
        return facilityInterestRates;
    }

    public void setFacilityInterestRates(Set<FacilityInterestRate> facilityInterestRates) {
        this.facilityInterestRates = facilityInterestRates;
    }

    public List<FacilityInterestRate> getOrderedFacilityInterestRates() {
        return getFacilityInterestRates().stream().sorted(new Comparator<FacilityInterestRate>() {
            @Override
            public int compare(FacilityInterestRate o1, FacilityInterestRate o2) {
                return o1.getFacilityInterestRateID().compareTo(o2.getFacilityInterestRateID());
            }
        }).collect(Collectors.toList());
    }

    public void addFacilityInterestRate(FacilityInterestRate facilityInterestRate) {
        facilityInterestRate.setFacility(this);
        this.getFacilityInterestRates().add(facilityInterestRate);
    }

    public FacilityInterestRate getFacilityInterestRateByID(Integer facilityInterestRateID) {
        return this.getFacilityInterestRates().stream().
                filter(facilityInterestRate -> {
                    return facilityInterestRateID.equals(facilityInterestRate.getFacilityInterestRateID());
                }).findFirst().orElse(null);
    }

    public FacilityInterestRate getFacilityInterestRateByCFTInterestRateID(Integer cftInterestRateID) {
        return this.getFacilityInterestRates().stream().
                filter(facilityInterestRate -> {
                    return cftInterestRateID.equals(facilityInterestRate.getCftInterestRateID());
                }).findFirst().orElse(null);
    }

    public Set<FacilityPurposeOfAdvance> getFacilityPurposeOfAdvances() {
        if (facilityPurposeOfAdvances == null) {
            facilityPurposeOfAdvances = new HashSet<>();
        }
        return facilityPurposeOfAdvances;
    }

    public void setFacilityPurposeOfAdvances(Set<FacilityPurposeOfAdvance> facilityPurposeOfAdvances) {
        this.facilityPurposeOfAdvances = facilityPurposeOfAdvances;
    }

    public void addFacilityPurposeOfAdvance(FacilityPurposeOfAdvance facilityPurposeOfAdvance) {
        facilityPurposeOfAdvance.setFacility(this);
        this.getFacilityPurposeOfAdvances().add(facilityPurposeOfAdvance);
    }

    public FacilityPurposeOfAdvance getFacilityPurposeOfAdvanceByID(Integer facilityPurposeOfAdvanceID) {
        return this.getFacilityPurposeOfAdvances().stream().
                filter(facilityPurposeOfAdvance -> {
                    return facilityPurposeOfAdvanceID.equals(facilityPurposeOfAdvance.getFacilityPurposeOfAdvanceID());
                }).findFirst().orElse(null);
    }

    public Set<FacilityFacilitySecurity> getFacilityFacilitySecurities() {
        if (facilityFacilitySecurities == null) {
            this.facilityFacilitySecurities = new HashSet<>();
        }
        return facilityFacilitySecurities;
    }

    public void setFacilityFacilitySecurities(Set<FacilityFacilitySecurity> facilityFacilitySecurities) {
        this.facilityFacilitySecurities = facilityFacilitySecurities;
    }

    public void removeFacilityFacilitySecurities(List<FacilityFacilitySecurity> removeSet) {
        if (removeSet != null && removeSet.size() > 0) {
            this.getFacilityFacilitySecurities().removeAll(removeSet);
        }
    }

    public void removeFacilityFacilitySecurity(FacilityFacilitySecurity facilityFacilitySecurity) {
        if (facilityFacilitySecurity != null) {
            this.getFacilityFacilitySecurities().remove(facilityFacilitySecurity);
        }
    }

    public void addFacilityFacilitySecurity(FacilityFacilitySecurity facilityFacilitySecurity) {
        this.getFacilityFacilitySecurities().add(facilityFacilitySecurity);
    }


    public BigDecimal getFacilityActualAmount() {
        if (getIsNew() == AppsConstants.YesNo.N) {
            return getOutstandingAmount();
        } else {
            return getFacilityAmount();
        }
    }

    public Set<FacilityOtherFacilityInformation> getFacilityOtherFacilityInformations() {
        if (facilityOtherFacilityInformations == null) {
            this.facilityOtherFacilityInformations = new HashSet<>();
        }
        return facilityOtherFacilityInformations;
    }

    public void setFacilityOtherFacilityInformations(Set<FacilityOtherFacilityInformation> facilityOtherFacilityInformations) {
        this.facilityOtherFacilityInformations = facilityOtherFacilityInformations;
    }

    public FacilityOtherFacilityInformation getFacilityOtherInfoDataByID(Integer facilityOtherFacilityInformationID) {
        return this.getFacilityOtherFacilityInformations().stream().
                filter(facilityOtherInfoData -> {
                    return facilityOtherFacilityInformationID.equals(facilityOtherInfoData.getFacilityOtherFacilityInformationID());
                }).findFirst().orElse(null);
    }

    public FacilityOtherFacilityInformation getFacilityOtherInfoByCftFacilityInformationId(Integer cftOtherFacilityInformationID) {
        return this.getFacilityOtherFacilityInformations().stream().
                filter(facilityOtherInfoData -> {
                    return cftOtherFacilityInformationID.equals(facilityOtherInfoData.getCftOtherFacilityInfoID());
                }).findFirst().orElse(null);
    }

    public List<FacilityOtherFacilityInformation> getOrderedFacilityOtherFacilityInformations() {
        return getFacilityOtherFacilityInformations().stream().sorted(new Comparator<FacilityOtherFacilityInformation>() {
            @Override
            public int compare(FacilityOtherFacilityInformation o1, FacilityOtherFacilityInformation o2) {
                return o1.getDisplayOrder().compareTo(o2.getDisplayOrder());
            }
        }).collect(Collectors.toList());
    }

    public FacilityOtherFacilityInformation getFacilityOtherFacilityInformationDTOByOtherFacilityInfoCode(String otherFacilityInfoCode) {
        return this.getFacilityOtherFacilityInformations().stream().
                filter(facilityOtherInfoData -> {
                    return facilityOtherInfoData.getOtherFacilityInfoCode().equals(otherFacilityInfoCode);
                }).findFirst().orElse(null);
    }

    public void addOtherFacilityInformation(FacilityOtherFacilityInformation facilityOtherFacilityInformation) {
        facilityOtherFacilityInformation.setFacility(this);
        this.getFacilityOtherFacilityInformations().add(facilityOtherFacilityInformation);
    }

    public Set<FacilityRentalInformation> getFacilityRentalInformation() {
        if (facilityRentalInformation == null) {
            this.facilityRentalInformation = new HashSet<>();
        }
        return facilityRentalInformation;
    }

    public void setFacilityRentalInformation(Set<FacilityRentalInformation> facilityRentalInformation) {
        this.facilityRentalInformation = facilityRentalInformation;
    }

    public FacilityRentalInformation getFacilityRentalInformationByID(Integer facilityRentalInformationID) {
        return this.getFacilityRentalInformation().stream().
                filter(facilityRentalInfoData -> {
                    return facilityRentalInformationID.equals(facilityRentalInfoData.getFacilityRentalInformationID());
                }).findFirst().orElse(null);
    }

    public void addFacilityRentalInformation(FacilityRentalInformation facilityRentalInformation) {
        facilityRentalInformation.setFacility(this);
        this.getFacilityRentalInformation().add(facilityRentalInformation);
    }

    public List<FacilityRentalInformation> getOrderedFacilityRentalInformation() {
        return getFacilityRentalInformation().stream().sorted(Comparator.comparing(FacilityRentalInformation::getDisplayOrder)).collect(Collectors.toList());
    }

    public Set<FacilityCustomInfoData> getFacilityCustomFacilityInformations() {
        if (facilityCustomFacilityInformations == null) {
            this.facilityCustomFacilityInformations = new HashSet<>();
        }
        return facilityCustomFacilityInformations;
    }

    public Set<FacilityCovenantFacilities> getFacilityCovenantFacilities() {
        return facilityCovenantFacilities;
    }

    public void setFacilityCovenantFacilities(Set<FacilityCovenantFacilities> facilityCovenantFacilities) {
        this.facilityCovenantFacilities = facilityCovenantFacilities;
    }

    public AppsConstants.YesNo getIsAnnual() {
        return isAnnual;
    }

    public void setIsAnnual(AppsConstants.YesNo isAnnual) {
        this.isAnnual = isAnnual;
    }

    public AppsConstants.YesNo getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(AppsConstants.YesNo isAdditional) {
        this.isAdditional = isAdditional;
    }

    public AppsConstants.YesNo getIsTermsAmended() {
        return isTermsAmended;
    }

    public void setIsTermsAmended(AppsConstants.YesNo isTermsAmended) {
        this.isTermsAmended = isTermsAmended;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setLoanLimitId(String loanLimitId){this.loanLimitId = loanLimitId;}
    public String getLoanLimitId(){
        return this.loanLimitId;
    }

    public AppsConstants.YesNo getIsReStructure() {
        return isReStructure;
    }

    public void setIsReStructure(AppsConstants.YesNo isReStructure) {
        this.isReStructure = isReStructure;
    }

    public AppsConstants.YesNo getIsReSchedule() {
        return isReSchedule;
    }

    public void setIsReSchedule(AppsConstants.YesNo isReSchedule) {
        this.isReSchedule = isReSchedule;
    }

    public List<UPCTemplateCommentHistoryDTO> getFusTraceList() {
        return fusTraceList;
    }

    public void setFusTraceList(List<UPCTemplateCommentHistoryDTO> fusTraceList) {
        this.fusTraceList = fusTraceList;
    }

}
