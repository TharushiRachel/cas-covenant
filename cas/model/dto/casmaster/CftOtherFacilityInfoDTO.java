package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.CftOtherFacilityInformation;

import java.io.Serializable;

public class CftOtherFacilityInfoDTO implements Serializable {

    private Integer cftOtherFacilityInfoID;

    private Integer creditFacilityTemplateID;

    private String otherFacilityInfoName;

    private String description;

    private String otherFacilityInfoCode;

    private DomainConstants.InputFieldValueType otherFacilityInfoFieldType;

    private String defaultValue;

    private Integer displayOrder;

    private AppsConstants.YesNo mandatory;

    private AppsConstants.Status status;

    private String outputCode;

    private boolean currency;

    private boolean percentage;

    public CftOtherFacilityInfoDTO() {
    }

    public CftOtherFacilityInfoDTO(CftOtherFacilityInformation cftOtherFacilityInformation) {
        this(cftOtherFacilityInformation, null, false, false);
    }

    public CftOtherFacilityInfoDTO(CftOtherFacilityInformation cftOtherFacilityInformation, String outputCode, boolean currency, boolean percentage) {
        this.cftOtherFacilityInfoID = cftOtherFacilityInformation.getCftOtherFacilityInfoID();
        this.creditFacilityTemplateID = cftOtherFacilityInformation.getCreditFacilityTemplate().getCreditFacilityTemplateID();
        this.otherFacilityInfoName = cftOtherFacilityInformation.getOtherFacilityInfoName();
        this.description = cftOtherFacilityInformation.getDescription();
        this.otherFacilityInfoCode = cftOtherFacilityInformation.getOtherFacilityInfoCode();
        this.otherFacilityInfoFieldType = cftOtherFacilityInformation.getOtherFacilityInfoFieldType();
        this.defaultValue = cftOtherFacilityInformation.getDefaultValue();
        this.displayOrder = cftOtherFacilityInformation.getDisplayOrder();
        this.mandatory = cftOtherFacilityInformation.getMandatory();
        this.status = cftOtherFacilityInformation.getStatus();
        this.outputCode = outputCode;
        this.currency = currency;
        this.percentage = percentage;
    }

    public Integer getCftOtherFacilityInfoID() {
        return cftOtherFacilityInfoID;
    }

    public void setCftOtherFacilityInfoID(Integer cftOtherFacilityInfoID) {
        this.cftOtherFacilityInfoID = cftOtherFacilityInfoID;
    }

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public String getOtherFacilityInfoName() {
        return otherFacilityInfoName;
    }

    public void setOtherFacilityInfoName(String otherFacilityInfoName) {
        this.otherFacilityInfoName = otherFacilityInfoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherFacilityInfoCode() {
        return otherFacilityInfoCode;
    }

    public void setOtherFacilityInfoCode(String otherFacilityInfoCode) {
        this.otherFacilityInfoCode = otherFacilityInfoCode;
    }

    public DomainConstants.InputFieldValueType getOtherFacilityInfoFieldType() {
        return otherFacilityInfoFieldType;
    }

    public void setOtherFacilityInfoFieldType(DomainConstants.InputFieldValueType otherFacilityInfoFieldType) {
        this.otherFacilityInfoFieldType = otherFacilityInfoFieldType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getOutputCode() {
        return outputCode;
    }

    public void setOutputCode(String outputCode) {
        this.outputCode = outputCode;
    }

    public boolean isCurrency() {
        return currency;
    }

    public void setCurrency(boolean currency) {
        this.currency = currency;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "CftOtherFacilityInfoDTO{" +
                "cftOtherFacilityInfoID=" + cftOtherFacilityInfoID +
                ", creditFacilityTemplateID=" + creditFacilityTemplateID +
                ", otherFacilityInfoName='" + otherFacilityInfoName + '\'' +
                ", description='" + description + '\'' +
                ", otherFacilityInfoCode='" + otherFacilityInfoCode + '\'' +
                ", otherFacilityInfoFieldType=" + otherFacilityInfoFieldType +
                ", defaultValue='" + defaultValue + '\'' +
                ", displayOrder=" + displayOrder +
                ", mandatory=" + mandatory +
                ", status=" + status +
                ", outputCode='" + outputCode + '\'' +
                ", currency=" + currency +
                ", percentage=" + percentage +
                '}';
    }
}
