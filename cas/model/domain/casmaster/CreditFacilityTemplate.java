package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import com.itechro.cas.model.domain.facilitypaper.FPDocumentElement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_CREDIT_FACILITY_TEMPLATE")
public class CreditFacilityTemplate extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CREDIT_FACILITY_TEMPLATE")
    @SequenceGenerator(name = "SEQ_T_CREDIT_FACILITY_TEMPLATE", sequenceName = "SEQ_T_CREDIT_FACILITY_TEMPLATE", allocationSize = 1)
    @Column(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private Integer creditFacilityTemplateID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TYPE_ID")
    private CreditFacilityType creditFacilityType;

    @Column(name = "CREDIT_FACILITY_NAME")
    private String creditFacilityName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MAXIMUM_FACILITY_AMOUNT")
    private BigDecimal maxFacilityAmount;

    @Column(name = "MINIMUM_FACILITY_AMOUNT")
    private BigDecimal minFacilityAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_PURPOSE")
    private AppsConstants.YesNo showPurpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_REPAYMENT")
    private AppsConstants.YesNo showRepayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_CONDITION")
    private AppsConstants.YesNo showCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_REMARK")
    private AppsConstants.YesNo showRemark;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_CALCULATOR")
    private AppsConstants.YesNo showCalculator;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_RENTAL_DATA")
    private AppsConstants.YesNo showRentalData;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHOW_IN_LEAD")
    private AppsConstants.YesNo showInLead;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "NEW_FACILITY_EMAIL")
    private String newFacilityEmail;

    @Column(name = "EXISTING_FACILITY_EMAIL")
    private String existingFacilityEmail;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "creditFacilityTemplate")
    private Set<CftVitalInfo> cftVitalInfos;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "creditFacilityTemplate")
    private Set<CftInterestRate> cftInterestRates;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "creditFacilityTemplate")
    private Set<CftSupportingDoc> cftSupportingDocs;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "creditFacilityTemplate")
    private Set<CftOtherFacilityInformation> cftOtherFacilityInformations;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "creditFacilityTemplate")
    private Set<FPDocumentElement> fPDocumentElements;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "creditFacilityTemplate")
    private Set<CftCustomFacilityInfo> cftCustomFacilityInfos;

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public CreditFacilityType getCreditFacilityType() {
        return creditFacilityType;
    }

    public void setCreditFacilityType(CreditFacilityType creditFacilityType) {
        this.creditFacilityType = creditFacilityType;
    }

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMaxFacilityAmount() {
        return maxFacilityAmount;
    }

    public void setMaxFacilityAmount(BigDecimal maxFacilityAmount) {
        this.maxFacilityAmount = maxFacilityAmount;
    }

    public BigDecimal getMinFacilityAmount() {
        return minFacilityAmount;
    }

    public void setMinFacilityAmount(BigDecimal minFacilityAmount) {
        this.minFacilityAmount = minFacilityAmount;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Set<CftVitalInfo> getCftVitalInfos() {
        if (cftVitalInfos == null) {
            cftVitalInfos = new HashSet<>();
        }
        return cftVitalInfos;
    }

    public void setCftVitalInfos(Set<CftVitalInfo> cftVitalInfos) {
        this.cftVitalInfos = cftVitalInfos;
    }

    public Set<CftOtherFacilityInformation> getCftOtherFacilityInformations() {
        if (cftOtherFacilityInformations == null) {
            cftOtherFacilityInformations = new HashSet<>();
        }
        return cftOtherFacilityInformations;
    }

    public List<CftOtherFacilityInformation> getOrderedCftOtherFacilityInformations() {
        return getCftOtherFacilityInformations().stream().sorted(new Comparator<CftOtherFacilityInformation>() {
            @Override
            public int compare(CftOtherFacilityInformation o1, CftOtherFacilityInformation o2) {
                return o1.getDisplayOrder().compareTo(o2.getDisplayOrder());
            }
        }).collect(Collectors.toList());
    }

    public void setCftOtherFacilityInformations(Set<CftOtherFacilityInformation> cftOtherFacilityInformations) {
        this.cftOtherFacilityInformations = cftOtherFacilityInformations;
    }

    public List<CftVitalInfo> getOrderedCftVitalInfos() {
        return getCftVitalInfos().stream().sorted(new Comparator<CftVitalInfo>() {
            @Override
            public int compare(CftVitalInfo o1, CftVitalInfo o2) {
                return o1.getCftVitalInfoID().compareTo(o2.getCftVitalInfoID());
            }
        }).collect(Collectors.toList());
    }

    public CftVitalInfo getCftVitalInfoByID(Integer cftVitalInfoID) {
        return this.getCftVitalInfos().stream().
                filter(cftVitalInfo -> {
                    return cftVitalInfoID.equals(cftVitalInfo.getCftVitalInfoID());
                }).findFirst().orElse(null);
    }

    public Set<CftInterestRate> getCftInterestRates() {
        if (cftInterestRates == null) {
            cftInterestRates = new HashSet<>();
        }
        return cftInterestRates;
    }

    public void setCftInterestRates(Set<CftInterestRate> cftInterestRates) {
        this.cftInterestRates = cftInterestRates;
    }

    public Set<CftSupportingDoc> getCftSupportingDocs() {
        if (cftSupportingDocs == null) {
            cftSupportingDocs = new HashSet<>();
        }
        return cftSupportingDocs;
    }

    public void setCftSupportingDocs(Set<CftSupportingDoc> cftSupportingDocs) {
        this.cftSupportingDocs = cftSupportingDocs;
    }

    public CftInterestRate getCftInterestRateByID(Integer cftInterestRateID) {
        return this.getCftInterestRates().stream().
                filter(cftInterestRate -> {
                    return cftInterestRateID.equals(cftInterestRate.getCftInterestRateID());
                }).findFirst().orElse(null);
    }

    public CftSupportingDoc getCftSupportingDocByID(Integer cftSupportingDocID) {
        return this.getCftSupportingDocs().stream().
                filter(cftInterestRateDoc -> {
                    return cftSupportingDocID.equals(cftInterestRateDoc.getCftSupportingDocID());
                }).findFirst().orElse(null);
    }

    public CftOtherFacilityInformation getOtherFacilityInfoByID(Integer otherFacilityInfoID) {
        return this.getCftOtherFacilityInformations().stream().filter(info -> {
            return otherFacilityInfoID.equals(info.getCftOtherFacilityInfoID());
        }).findFirst().orElse(null);
    }

    public List<CftOtherFacilityInformation> getOrderedOtherFacilityInfo() {
        return getCftOtherFacilityInformations().stream().sorted(new Comparator<CftOtherFacilityInformation>() {
            @Override
            public int compare(CftOtherFacilityInformation o1, CftOtherFacilityInformation o2) {
                return o1.getCftOtherFacilityInfoID().compareTo(o2.getCftOtherFacilityInfoID());
            }
        }).collect(Collectors.toList());
    }

    public Set<CftCustomFacilityInfo> getCftCustomFacilityInfos() {
        if(cftCustomFacilityInfos == null){
            cftCustomFacilityInfos = new HashSet<>();
        }
        return cftCustomFacilityInfos;
    }

    public void setCftCustomFacilityInfos(Set<CftCustomFacilityInfo> cftCustomFacilityInfos) {
        this.cftCustomFacilityInfos = cftCustomFacilityInfos;
    }

    public List<CftCustomFacilityInfo> getOrderedCftCustomFacilityInfos(){
        return getCftCustomFacilityInfos().stream().sorted(new Comparator<CftCustomFacilityInfo>() {
            @Override
            public int compare(CftCustomFacilityInfo o1, CftCustomFacilityInfo o2) {
                return o1.getCftCustomFacilityInfoID().compareTo(o2.getCftCustomFacilityInfoID());
            }
        }).collect(Collectors.toList());
    }
//public List<CftCustomFacilityInfo> getOrderedCftCustomFacilityInfos() {
//    return getCftCustomFacilityInfos()
//            .stream()
//            .sorted(Comparator.comparing(CftCustomFacilityInfo::getCftCustomFacilityInfoID))
//            .collect(Collectors.toList());
//}


    public CftCustomFacilityInfo getCftCustomFacilityInfoByID(Integer cftCustomFacilityInfoID){
        return this.getCftCustomFacilityInfos().stream().filter(cftCustomFacilityInfo -> {
            return cftCustomFacilityInfoID.equals(cftCustomFacilityInfo.getCftCustomFacilityInfoID());
        }).findFirst().orElse(null);
    }

    public AppsConstants.YesNo getShowPurpose() {
        return showPurpose;
    }

    public void setShowPurpose(AppsConstants.YesNo showPurpose) {
        this.showPurpose = showPurpose;
    }

    public AppsConstants.YesNo getShowRepayment() {
        return showRepayment;
    }

    public void setShowRepayment(AppsConstants.YesNo showRepayment) {
        this.showRepayment = showRepayment;
    }

    public AppsConstants.YesNo getShowCondition() {
        return showCondition;
    }

    public void setShowCondition(AppsConstants.YesNo showCondition) {
        this.showCondition = showCondition;
    }

    public AppsConstants.YesNo getShowRemark() {
        return showRemark;
    }

    public void setShowRemark(AppsConstants.YesNo showRemark) {
        this.showRemark = showRemark;
    }

    public AppsConstants.YesNo getShowRentalData() {
        return showRentalData;
    }

    public void setShowRentalData(AppsConstants.YesNo showRentalData) {
        this.showRentalData = showRentalData;
    }

    public AppsConstants.YesNo getShowCalculator() {
        return showCalculator;
    }

    public void setShowCalculator(AppsConstants.YesNo showCalculator) {
        this.showCalculator = showCalculator;
    }

    public Set<FPDocumentElement> getFpDocumentElements() {
        if (fPDocumentElements == null) {
            fPDocumentElements = new HashSet<>();
        }
        return fPDocumentElements;
    }

    public void setFpDocumentElements(Set<FPDocumentElement> fPDocumentElements) {
        this.fPDocumentElements = fPDocumentElements;
    }

    public String getNewFacilityEmail() {
        return newFacilityEmail;
    }

    public void setNewFacilityEmail(String newFacilityEmail) {
        this.newFacilityEmail = newFacilityEmail;
    }

    public String getExistingFacilityEmail() {
        return existingFacilityEmail;
    }

    public void setExistingFacilityEmail(String existingFacilityEmail) {
        this.existingFacilityEmail = existingFacilityEmail;
    }

    public AppsConstants.YesNo getShowInLead() {
        return showInLead;
    }

    public void setShowInLead(AppsConstants.YesNo showInLead) {
        this.showInLead = showInLead;
    }
}
