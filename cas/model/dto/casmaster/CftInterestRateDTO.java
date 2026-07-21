package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.CftInterestRate;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class CftInterestRateDTO implements Serializable {

    private Integer cftInterestRateID;

    private Integer creditFacilityTemplateID;

    private String rateName;

    private String rateCode;

    private Double value;

    private AppsConstants.YesNo isDefault;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private DomainConstants.InterestRatingSubCategory interestRatingSubCategory;

    private AppsConstants.YesNo isEditable;

    public CftInterestRateDTO() {
    }

    public CftInterestRateDTO(CftInterestRate cftInterestRate) {

        this.cftInterestRateID = cftInterestRate.getCftInterestRateID();
        this.creditFacilityTemplateID = cftInterestRate.getCreditFacilityTemplate().getCreditFacilityTemplateID();
        this.rateName = cftInterestRate.getRateName();
        this.rateCode = cftInterestRate.getRateCode();
        this.value = cftInterestRate.getValue();
        this.isDefault = cftInterestRate.getIsDefault();
        this.approveStatus = cftInterestRate.getApproveStatus();
        this.status = cftInterestRate.getStatus();

        if (cftInterestRate.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(cftInterestRate.getApprovedDate());
        }
        this.approveStatus = cftInterestRate.getApproveStatus();
        this.approvedBy = cftInterestRate.getApprovedBy();
        this.interestRatingSubCategory = cftInterestRate.getInterestRatingSubCategory();
        this.isEditable = cftInterestRate.getIsEditable();

    }

    public Integer getCftInterestRateID() {
        return cftInterestRateID;
    }

    public void setCftInterestRateID(Integer cftInterestRateID) {
        this.cftInterestRateID = cftInterestRateID;
    }

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
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

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
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

    @Override
    public String toString() {
        return "CftInterestRateDTO{" +
                "cftInterestRateID=" + cftInterestRateID +
                ", creditFacilityTemplateID=" + creditFacilityTemplateID +
                ", rateName='" + rateName + '\'' +
                ", rateCode='" + rateCode + '\'' +
                ", value=" + value +
                ", isDefault=" + isDefault +
                ", status=" + status +
                ", approveStatus=" + approveStatus +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", interestRatingSubCategory=" + interestRatingSubCategory +
                ", isEditable=" + isEditable +
                '}';
    }
}
