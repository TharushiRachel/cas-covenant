package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFFacilitySecurity;
import com.itechro.cas.model.domain.applicationform.AFSecurity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AFSecurityDTO implements Serializable {

    private Integer applicationFormID;

    private Integer securityID;

    private String securityCode;

    private String securityDetail;

    private BigDecimal securityAmount;

    private BigDecimal cashAmount;

    private String securityCurrency;

    private AppsConstants.Status status;

    private AppsConstants.YesNo isCommonSecurity;

    private AppsConstants.YesNo isCashSecurity;

    private List<AFFacilitySecurityDTO> afFacilitySecurityDTOS;

    private List<AFFacilityDTO> afFacilityDTOS;

    public AFSecurityDTO() {
    }

    public AFSecurityDTO(AFSecurity afSecurity) {
        this.securityID = afSecurity.getSecurityID();
        this.securityCode = afSecurity.getSecurityCode();
        this.securityDetail = afSecurity.getSecurityDetail();
        this.securityAmount = afSecurity.getSecurityAmount();
        this.cashAmount = afSecurity.getCashAmount();
        this.securityCurrency = afSecurity.getSecurityCurrency();
        this.status = afSecurity.getStatus();
        this.isCommonSecurity = afSecurity.getIsCommonSecurity();
        this.isCashSecurity = afSecurity.getIsCashSecurity();

        for (AFFacilitySecurity afFacilitySecurity : afSecurity.getAfFacilitySecurities()) {
            this.getAfFacilitySecurityDTOS().add(new AFFacilitySecurityDTO(afFacilitySecurity));
        }
    }

    public Integer getSecurityID() {
        return securityID;
    }

    public void setSecurityID(Integer securityID) {
        this.securityID = securityID;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(String securityDetail) {
        this.securityDetail = securityDetail;
    }

    public BigDecimal getSecurityAmount() {
        return securityAmount;
    }

    public void setSecurityAmount(BigDecimal securityAmount) {
        this.securityAmount = securityAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getSecurityCurrency() {
        return securityCurrency;
    }

    public void setSecurityCurrency(String securityCurrency) {
        this.securityCurrency = securityCurrency;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public AppsConstants.YesNo getIsCommonSecurity() {
        return isCommonSecurity;
    }

    public void setIsCommonSecurity(AppsConstants.YesNo isCommonSecurity) {
        this.isCommonSecurity = isCommonSecurity;
    }

    public AppsConstants.YesNo getIsCashSecurity() {
        return isCashSecurity;
    }

    public void setIsCashSecurity(AppsConstants.YesNo isCashSecurity) {
        this.isCashSecurity = isCashSecurity;
    }

    public List<AFFacilitySecurityDTO> getAfFacilitySecurityDTOS() {
        if (afFacilitySecurityDTOS == null) {
            this.afFacilitySecurityDTOS = new ArrayList<>();
        }
        return afFacilitySecurityDTOS;
    }

    public void setAfFacilitySecurityDTOS(List<AFFacilitySecurityDTO> afFacilitySecurityDTOS) {
        this.afFacilitySecurityDTOS = afFacilitySecurityDTOS;
    }

    public List<AFFacilityDTO> getAfFacilityDTOS() {
        if (afFacilityDTOS == null) {
            this.afFacilityDTOS = new ArrayList<>();
        }
        return afFacilityDTOS;
    }

    public void setAfFacilityDTOS(List<AFFacilityDTO> afFacilityDTOS) {
        this.afFacilityDTOS = afFacilityDTOS;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    @Override
    public String toString() {
        return "AFSecurityDTO{" +
                "securityID=" + securityID +
                ", securityCode='" + securityCode + '\'' +
                ", securityDetail='" + securityDetail + '\'' +
                ", securityAmount=" + securityAmount +
                ", cashAmount=" + cashAmount +
                ", securityCurrency='" + securityCurrency + '\'' +
                ", status=" + status +
                ", isCommonSecurity=" + isCommonSecurity +
                ", isCashSecurity=" + isCashSecurity +
                '}';
    }
}
