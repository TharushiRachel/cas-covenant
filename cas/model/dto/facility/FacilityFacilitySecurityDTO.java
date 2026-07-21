package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityFacilitySecurity;

import java.io.Serializable;
import java.math.BigDecimal;

public class FacilityFacilitySecurityDTO implements Serializable {

    private Integer facilitySecuritySecurityID;

    private Integer facilityID;

    private Integer facilitySecurityID;

    private String facilityRefCode;

    private String creditFacilityName;

    private BigDecimal facilityAmount;

    private String facilityCurrency;

    private AppsConstants.YesNo isAddedFacility;

    private AppsConstants.YesNo isCashSecurity;

    private BigDecimal facilitySecurityAmount;

    private AppsConstants.Status status;

    public FacilityFacilitySecurityDTO() {
    }


    public FacilityFacilitySecurityDTO(FacilityFacilitySecurity facilityFacilitySecurity) {
        this.facilitySecuritySecurityID = facilityFacilitySecurity.getFacilitySecuritySecurityID();
        this.facilityID = facilityFacilitySecurity.getFacility().getFacilityID();
        this.facilityRefCode = facilityFacilitySecurity.getFacility().getFacilityRefCode();
        this.creditFacilityName = facilityFacilitySecurity.getFacility().getCreditFacilityTemplate().getCreditFacilityName();
        this.facilitySecurityID = facilityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID();
        this.facilitySecurityAmount = facilityFacilitySecurity.getFacilitySecurityAmount();
        this.facilityAmount = facilityFacilitySecurity.getFacility().getFacilityAmount();
        this.facilityCurrency = facilityFacilitySecurity.getFacility().getFacilityCurrency();
        this.status = facilityFacilitySecurity.getStatus();
        this.isCashSecurity = facilityFacilitySecurity.getIsCashSecurity();
    }

    public Integer getFacilitySecuritySecurityID() {
        return facilitySecuritySecurityID;
    }

    public void setFacilitySecuritySecurityID(Integer facilitySecuritySecurityID) {
        this.facilitySecuritySecurityID = facilitySecuritySecurityID;
    }

    public BigDecimal getFacilitySecurityAmount() {
        return facilitySecurityAmount;
    }

    public void setFacilitySecurityAmount(BigDecimal facilitySecurityAmount) {
        this.facilitySecurityAmount = facilitySecurityAmount;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getFacilitySecurityID() {
        return facilitySecurityID;
    }

    public void setFacilitySecurityID(Integer facilitySecurityID) {
        this.facilitySecurityID = facilitySecurityID;
    }

    public String getFacilityRefCode() {
        return facilityRefCode;
    }

    public void setFacilityRefCode(String facilityRefCode) {
        this.facilityRefCode = facilityRefCode;
    }

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
    }

    public AppsConstants.YesNo getIsAddedFacility() {
        return isAddedFacility;
    }

    public void setIsAddedFacility(AppsConstants.YesNo isAddedFacility) {
        this.isAddedFacility = isAddedFacility;
    }

    public BigDecimal getFacilityAmount() {
        return facilityAmount;
    }

    public void setFacilityAmount(BigDecimal facilityAmount) {
        this.facilityAmount = facilityAmount;
    }

    public String getFacilityCurrency() {
        return facilityCurrency;
    }

    public void setFacilityCurrency(String facilityCurrency) {
        this.facilityCurrency = facilityCurrency;
    }

    public AppsConstants.YesNo getIsCashSecurity() {
        return isCashSecurity;
    }

    public void setIsCashSecurity(AppsConstants.YesNo isCashSecurity) {
        this.isCashSecurity = isCashSecurity;
    }
}
