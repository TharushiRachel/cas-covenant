package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_FACILITY_INTEREST_RATE")
public class FacilityInterestRate extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_INTEREST_RATE")
    @SequenceGenerator(name = "SEQ_T_FACILITY_INTEREST_RATE", sequenceName = "SEQ_T_FACILITY_INTEREST_RATE", allocationSize = 1)
    @Column(name = "FACILITY_INTEREST_RATE_ID")
    private Integer facilityInterestRateID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Column(name = "CFT_INTEREST_RATE_ID")
    private Integer cftInterestRateID;

    @Column(name = "RATE_NAME")
    private String rateName;

    @Column(name = "RATE_CODE")
    private String rateCode;

    @Column(name = "USER_COMMENT")
    private String userComment;

    @Column(name = "VALUE")
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_DEFAULT")
    private AppsConstants.YesNo isDefault;

    @Enumerated(EnumType.STRING)
    @Column(name = "INTEREST_RATING_SUB_CATEGORY")
    private DomainConstants.InterestRatingSubCategory interestRatingSubCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_EDITABLE")
    private AppsConstants.YesNo isEditable;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFacilityInterestRateID() {
        return facilityInterestRateID;
    }

    public void setFacilityInterestRateID(Integer facilityInterestRateID) {
        this.facilityInterestRateID = facilityInterestRateID;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Integer getCftInterestRateID() {
        return cftInterestRateID;
    }

    public void setCftInterestRateID(Integer cftInterestRateID) {
        this.cftInterestRateID = cftInterestRateID;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
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

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
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
