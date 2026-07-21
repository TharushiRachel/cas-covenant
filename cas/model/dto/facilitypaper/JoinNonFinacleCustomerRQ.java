package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;

import java.io.Serializable;

public class JoinNonFinacleCustomerRQ implements Serializable {

    private Integer facilityPaperID;

    private Integer casCustomerID;

    private AppsConstants.YesNo isPrimary;

    private Integer displayOrder;

    private AppsConstants.Status status;

    private CASCustomerDTO casCustomerDTO;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public AppsConstants.YesNo getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(AppsConstants.YesNo isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public CASCustomerDTO getCasCustomerDTO() {
        return casCustomerDTO;
    }

    public void setCasCustomerDTO(CASCustomerDTO casCustomerDTO) {
        this.casCustomerDTO = casCustomerDTO;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
