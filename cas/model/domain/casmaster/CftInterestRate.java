package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;


@Entity
@Table(name = "T_CFT_INTEREST_RATE")
public class CftInterestRate extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CFT_INTEREST_RATE")
    @SequenceGenerator(name = "SEQ_T_CFT_INTEREST_RATE", sequenceName = "SEQ_T_CFT_INTEREST_RATE", allocationSize = 1)
    @Column(name = "CFT_INTEREST_RATE_ID")
    private Integer cftInterestRateID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @Column(name = "RATE_NAME")
    private String rateName;

    @Column(name = "RATE_CODE")
    private String rateCode;

    @Column(name = "VALUE")
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_DEFAULT")
    private AppsConstants.YesNo isDefault;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "INTEREST_RATING_SUB_CATEGORY")
    private DomainConstants.InterestRatingSubCategory interestRatingSubCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_EDITABLE")
    private AppsConstants.YesNo isEditable;

    public Integer getCftInterestRateID() {
        return cftInterestRateID;
    }

    public void setCftInterestRateID(Integer cftInterestRateID) {
        this.cftInterestRateID = cftInterestRateID;
    }

    public CreditFacilityTemplate getCreditFacilityTemplate() {
        return creditFacilityTemplate;
    }

    public void setCreditFacilityTemplate(CreditFacilityTemplate creditFacilityTemplate) {
        this.creditFacilityTemplate = creditFacilityTemplate;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public AppsConstants.YesNo getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(AppsConstants.YesNo isDefault) {
        this.isDefault = isDefault;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.InterestRatingSubCategory getInterestRatingSubCategory() {
        return interestRatingSubCategory;
    }

    public void setInterestRatingSubCategory(DomainConstants.InterestRatingSubCategory interestRatingSubCategory) {
        this.interestRatingSubCategory = interestRatingSubCategory;
    }

    public AppsConstants.YesNo getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(AppsConstants.YesNo isEditable) {
        this.isEditable = isEditable;
    }
}
