package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFFacilitySecurity;

import java.io.Serializable;
import java.math.BigDecimal;

public class AFFacilitySecurityDTO implements Serializable {

    private Integer facilitySecurityID;

    private Integer facilityID;

    private Integer securityID;

    private String facilityRefCode;

    private String creditFacilityName;

    private BigDecimal facilityAmount;

    private String facilityCurrency;

    private AppsConstants.YesNo isAddedFacility;

    private AppsConstants.YesNo isCashSecurity;

    private BigDecimal facilitySecurityAmount;

    private AppsConstants.Status status;

    public AFFacilitySecurityDTO() {
    }

    public AFFacilitySecurityDTO(AFFacilitySecurity afFacilitySecurity) {
        this.facilitySecurityID = afFacilitySecurity.getFacilitySecurityID();
        this.facilityID = afFacilitySecurity.getAfFacility().getFacilityID();
        this.securityID = afFacilitySecurity.getAfSecurity().getSecurityID();
        this.facilityRefCode = afFacilitySecurity.getAfFacility().getFacilityRefCode();
        this.status = afFacilitySecurity.getStatus();
        this.isCashSecurity = afFacilitySecurity.getIsCashSecurity();
        this.facilityAmount = afFacilitySecurity.getAfFacility().getFacilityAmount();
    }

    public Integer getFacilitySecurityID() {
        return facilitySecurityID;
    }

    public void setFacilitySecurityID(Integer facilitySecurityID) {
        this.facilitySecurityID = facilitySecurityID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getSecurityID() {
        return securityID;
    }

    public void setSecurityID(Integer securityID) {
        this.securityID = securityID;
    }

    public BigDecimal getFacilitySecurityAmount() {
        return facilitySecurityAmount;
    }

    public void setFacilitySecurityAmount(BigDecimal facilitySecurityAmount) {
        this.facilitySecurityAmount = facilitySecurityAmount;
    }

    public AppsConstants.YesNo getIsCashSecurity() {
        return isCashSecurity;
    }

    public void setIsCashSecurity(AppsConstants.YesNo isCashSecurity) {
        this.isCashSecurity = isCashSecurity;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
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

    public AppsConstants.YesNo getIsAddedFacility() {
        return isAddedFacility;
    }

    public void setIsAddedFacility(AppsConstants.YesNo isAddedFacility) {
        this.isAddedFacility = isAddedFacility;
    }

    @Override
    public String toString() {
        return "AFFacilitySecurityDTO{" +
                "facilitySecurityID=" + facilitySecurityID +
                ", facilityID=" + facilityID +
                ", securityID=" + securityID +
                ", facilityRefCode='" + facilityRefCode + '\'' +
                ", creditFacilityName='" + creditFacilityName + '\'' +
                ", facilityAmount=" + facilityAmount +
                ", facilityCurrency='" + facilityCurrency + '\'' +
                ", isAddedFacility=" + isAddedFacility +
                ", isCashSecurity=" + isCashSecurity +
                ", facilitySecurityAmount=" + facilitySecurityAmount +
                ", status=" + status +
                '}';
    }
}
