package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CftVitalInfo;

import java.io.Serializable;

public class CftVitalInfoDTO implements Serializable {

    private Integer cftVitalInfoID;

    private Integer creditFacilityTemplateID;

    private String vitalInfoName;

    private AppsConstants.YesNo mandatory;

    private Integer displayOrder;

    private AppsConstants.Status status;

    public CftVitalInfoDTO() {
    }

    public CftVitalInfoDTO(CftVitalInfo cftVitalInfo) {
        this.cftVitalInfoID = cftVitalInfo.getCftVitalInfoID();
        this.creditFacilityTemplateID = cftVitalInfo.getCreditFacilityTemplate().getCreditFacilityTemplateID();
        this.vitalInfoName = cftVitalInfo.getVitalInfoName();
        this.mandatory = cftVitalInfo.getMandatory();
        this.displayOrder = cftVitalInfo.getDisplayOrder();
        this.status = cftVitalInfo.getStatus();
    }

    public Integer getCftVitalInfoID() {
        return cftVitalInfoID;
    }

    public void setCftVitalInfoID(Integer cftVitalInfoID) {
        this.cftVitalInfoID = cftVitalInfoID;
    }

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public String getVitalInfoName() {
        return vitalInfoName;
    }

    public void setVitalInfoName(String vitalInfoName) {
        this.vitalInfoName = vitalInfoName;
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
