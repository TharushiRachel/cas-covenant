package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CftVitalInfo;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityVitalInfoData;

import java.io.Serializable;

public class FacilityVitalInfoDataDTO implements Serializable {

    private Integer facilityVitalInfoDataID;

    private Integer facilityID;

    private Integer cftVitalInfoID;

    private String vitalInfoName;

    private String vitalInfoData;

    private AppsConstants.YesNo mandatory;

    private Integer displayOrder;

    private AppsConstants.Status status;

    public FacilityVitalInfoDataDTO() {
    }

    public FacilityVitalInfoDataDTO(FacilityVitalInfoData facilityVitalInfoData) {
        this.facilityVitalInfoDataID = facilityVitalInfoData.getFacilityVitalInfoDataID();
        this.facilityID = facilityVitalInfoData.getFacility().getFacilityID();
        this.cftVitalInfoID = facilityVitalInfoData.getCftVitalInfoID();
        this.vitalInfoName = facilityVitalInfoData.getVitalInfoName();
        this.displayOrder= facilityVitalInfoData.getDisplayOrder();
        this.vitalInfoData = facilityVitalInfoData.getVitalInfoData();
        this.mandatory = facilityVitalInfoData.getMandatory();
        this.status = facilityVitalInfoData.getStatus();
    }

    public FacilityVitalInfoDataDTO(CftVitalInfo cftVitalInfo, Integer facilityID) {
        this.facilityVitalInfoDataID = null;
        this.facilityID = facilityID;
        this.cftVitalInfoID = cftVitalInfo.getCftVitalInfoID();
        this.vitalInfoName = cftVitalInfo.getVitalInfoName();
        this.mandatory = cftVitalInfo.getMandatory();
        this.displayOrder = cftVitalInfo.getDisplayOrder();
        this.status = cftVitalInfo.getStatus();
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

    @Override
    public String toString() {
        return "FacilityVitalInfoDataDTO{" +
                "facilityVitalInfoDataID=" + facilityVitalInfoDataID +
                ", facilityID=" + facilityID +
                ", vitalInfoName='" + vitalInfoName + '\'' +
                ", vitalInfoData='" + vitalInfoData + '\'' +
                ", mandatory=" + mandatory +
                ", status=" + status +
                '}';
    }
}
