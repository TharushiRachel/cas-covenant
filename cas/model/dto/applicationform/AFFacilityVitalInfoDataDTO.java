package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFFacilityVitalInfoData;

import java.io.Serializable;

public class AFFacilityVitalInfoDataDTO implements Serializable {

    private Integer facilityVitalInfoDataID;

    private Integer facilityID;

    private Integer cftVitalInfoID;

    private String vitalInfoName;

    private String vitalInfoData;

    private AppsConstants.YesNo mandatory;

    private Integer displayOrder;

    private AppsConstants.Status status;

    public AFFacilityVitalInfoDataDTO() {
    }

    public AFFacilityVitalInfoDataDTO(AFFacilityVitalInfoData afFacilityVitalInfoData) {
        this.facilityVitalInfoDataID = afFacilityVitalInfoData.getFacilityVitalInfoDataID();
        this.facilityID = afFacilityVitalInfoData.getAfFacility().getFacilityID();
        this.cftVitalInfoID = afFacilityVitalInfoData.getCftVitalInfoID();
        this.vitalInfoName = afFacilityVitalInfoData.getVitalInfoName();
        this.vitalInfoData = afFacilityVitalInfoData.getVitalInfoData();
        this.mandatory = afFacilityVitalInfoData.getMandatory();
        this.displayOrder = afFacilityVitalInfoData.getDisplayOrder();
        this.status = afFacilityVitalInfoData.getStatus();
    }

    public Integer getFacilityVitalInfoDataID() {
        return facilityVitalInfoDataID;
    }

    public void setFacilityVitalInfoDataID(Integer facilityVitalInfoDataID) {
        this.facilityVitalInfoDataID = facilityVitalInfoDataID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getCftVitalInfoID() {
        return cftVitalInfoID;
    }

    public void setCftVitalInfoID(Integer cftVitalInfoID) {
        this.cftVitalInfoID = cftVitalInfoID;
    }

    public String getVitalInfoName() {
        return vitalInfoName;
    }

    public void setVitalInfoName(String vitalInfoName) {
        this.vitalInfoName = vitalInfoName;
    }

    public String getVitalInfoData() {
        return vitalInfoData;
    }

    public void setVitalInfoData(String vitalInfoData) {
        this.vitalInfoData = vitalInfoData;
    }

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
