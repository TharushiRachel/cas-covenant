package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.CftOtherFacilityInformation;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityOtherFacilityInformation;

import java.io.Serializable;

public class FacilityOtherFacilityInformationDTO implements Serializable {

    private Integer facilityOtherFacilityInformationID;

    private Integer facilityID;

    private Integer cftOtherFacilityInfoID;

    private String otherFacilityInfoName;

    private String otherInfoData;

    private String otherFacilityInfoCode;

    private DomainConstants.InputFieldValueType otherFacilityInfoFieldType;

    private String defaultValue;

    private Integer displayOrder;

    private AppsConstants.YesNo mandatory;

    private AppsConstants.Status status;

    private String outputCode;

    private boolean currency;

    private boolean percentage;

    public FacilityOtherFacilityInformationDTO() {

    }

    public FacilityOtherFacilityInformationDTO(FacilityOtherFacilityInformation facilityOtherFacilityInformation) {
        this(facilityOtherFacilityInformation, null, false, false);
    }

    public FacilityOtherFacilityInformationDTO(FacilityOtherFacilityInformation facilityOtherFacilityInformation, String outputCode, boolean currency, boolean percentage) {
        facilityOtherFacilityInformationID = facilityOtherFacilityInformation.getFacilityOtherFacilityInformationID();
        if (facilityOtherFacilityInformation.getFacility() != null) {
            this.facilityID = facilityOtherFacilityInformation.getFacility().getFacilityID();
        }
        this.cftOtherFacilityInfoID = facilityOtherFacilityInformation.getCftOtherFacilityInfoID();
        this.otherFacilityInfoName = facilityOtherFacilityInformation.getOtherFacilityInfoName();
        this.otherInfoData = facilityOtherFacilityInformation.getOtherInfoData();
        this.otherFacilityInfoCode = facilityOtherFacilityInformation.getOtherFacilityInfoCode();
        this.otherFacilityInfoFieldType = facilityOtherFacilityInformation.getOtherFacilityInfoFieldType();
        this.defaultValue = facilityOtherFacilityInformation.getDefaultValue();
        this.displayOrder = facilityOtherFacilityInformation.getDisplayOrder();
        this.mandatory = facilityOtherFacilityInformation.getMandatory();
        this.status = facilityOtherFacilityInformation.getStatus();
        this.outputCode = outputCode;
        this.currency = currency;
        this.percentage = percentage;
    }

    public FacilityOtherFacilityInformationDTO(CftOtherFacilityInformation cftOtherFacilityInformation, Integer facilityID) {
        this.facilityID = facilityID;
        this.cftOtherFacilityInfoID = cftOtherFacilityInformation.getCftOtherFacilityInfoID();
        this.otherFacilityInfoName = cftOtherFacilityInformation.getOtherFacilityInfoName();
        this.otherFacilityInfoCode = cftOtherFacilityInformation.getOtherFacilityInfoCode();
        this.otherFacilityInfoFieldType = cftOtherFacilityInformation.getOtherFacilityInfoFieldType();
        this.displayOrder = cftOtherFacilityInformation.getDisplayOrder();
        this.defaultValue = cftOtherFacilityInformation.getDefaultValue();
        this.mandatory = cftOtherFacilityInformation.getMandatory();
        this.status = cftOtherFacilityInformation.getStatus();
    }

    public Integer getFacilityOtherFacilityInformationID() {
        return facilityOtherFacilityInformationID;
    }

    public void setFacilityOtherFacilityInformationID(Integer facilityOtherFacilityInformationID) {
        this.facilityOtherFacilityInformationID = facilityOtherFacilityInformationID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getCftOtherFacilityInfoID() {
        return cftOtherFacilityInfoID;
    }

    public void setCftOtherFacilityInfoID(Integer cftOtherFacilityInfoID) {
        this.cftOtherFacilityInfoID = cftOtherFacilityInfoID;
    }

    public String getOtherFacilityInfoName() {
        return otherFacilityInfoName;
    }

    public void setOtherFacilityInfoName(String otherFacilityInfoName) {
        this.otherFacilityInfoName = otherFacilityInfoName;
    }

    public String getOtherInfoData() {
        return otherInfoData;
    }

    public void setOtherInfoData(String otherInfoData) {
        this.otherInfoData = otherInfoData;
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
        return "FacilityOtherFacilityInformationDTO{" +
                "facilityOtherFacilityInformationID=" + facilityOtherFacilityInformationID +
                ", facilityID=" + facilityID +
                ", cftOtherFacilityInfoID=" + cftOtherFacilityInfoID +
                ", otherFacilityInfoName='" + otherFacilityInfoName + '\'' +
                ", otherInfoData='" + otherInfoData + '\'' +
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
