package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityFacilitySecurity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilitySecurity;
import com.itechro.cas.util.DecimalCalculator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FacilitySecurityDTO implements Serializable {

    private Integer facilitySecurityID;

    private Integer facilityPaperID;

    private Integer facilityID;

    private String securityCode;

    private String securityDetail;

    private BigDecimal securityAmount;

    private BigDecimal cashAmount;

    private String securityCurrency;

    private AppsConstants.Status status;

    private AppsConstants.YesNo isCommonSecurity;

    private AppsConstants.YesNo isCashSecurity;

    private List<FacilityDTO> facilityDTOS;

    private List<FacilityFacilitySecurityDTO> facilityFacilitySecurityDTOS;

    private BigDecimal facilitySecurityAmount;

    public FacilitySecurityDTO() {
    }

    public FacilitySecurityDTO(FacilitySecurity facilitySecurity) {
        this.facilitySecurityID = facilitySecurity.getFacilitySecurityID();

        if (facilitySecurity.getSecurityCode() != null) {
            this.securityCode = facilitySecurity.getSecurityCode();
        }

        if (facilitySecurity.getOrderedFacilitiesFacilitySecurities() != null) {
            for (FacilityFacilitySecurity facilityFacilitySecurity : facilitySecurity.getOrderedFacilitiesFacilitySecurities()) {
                if (facilityFacilitySecurity.getStatus() == AppsConstants.Status.ACT) {
                    FacilityDTO facilityDTO = new FacilityDTO(facilityFacilitySecurity.getFacility());
                    facilityDTO.setFacilitySecurityAmount(facilityFacilitySecurity.getFacilitySecurityAmount());
                    this.getFacilityDTOS().add(facilityDTO);
                    this.getFacilityFacilitySecurityDTOS().add(new FacilityFacilitySecurityDTO(facilityFacilitySecurity));
                }
            }
        }

        this.securityDetail = facilitySecurity.getSecurityDetail();
        this.securityAmount = facilitySecurity.getSecurityAmount();
        this.cashAmount = facilitySecurity.getCashAmount();
        this.securityCurrency = facilitySecurity.getSecurityCurrency();
        this.status = facilitySecurity.getStatus();
        this.isCommonSecurity = facilitySecurity.getIsCommonSecurity();
        this.isCashSecurity = facilitySecurity.getIsCashSecurity();
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

    public String getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(String securityDetail) {
        this.securityDetail = securityDetail;
    }

    public BigDecimal getSecurityAmount() {
        if (securityAmount == null){
            securityAmount = DecimalCalculator.getDefaultZero();
        }
        return securityAmount;
    }

    public void setSecurityAmount(BigDecimal securityAmount) {
        this.securityAmount = securityAmount;
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

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public AppsConstants.YesNo getIsCommonSecurity() {
        return isCommonSecurity;
    }

    public void setIsCommonSecurity(AppsConstants.YesNo isCommonSecurity) {
        this.isCommonSecurity = isCommonSecurity;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public List<FacilityDTO> getFacilityDTOS() {
        if (facilityDTOS == null) {
            facilityDTOS = new ArrayList<>();
        }
        return facilityDTOS;
    }

    public void setFacilityDTOS(List<FacilityDTO> facilityDTOS) {
        this.facilityDTOS = facilityDTOS;
    }

    public List<FacilityFacilitySecurityDTO> getFacilityFacilitySecurityDTOS() {
        if (facilityFacilitySecurityDTOS == null) {
            this.facilityFacilitySecurityDTOS = new ArrayList<>();
        }
        return facilityFacilitySecurityDTOS;
    }

    public void setFacilityFacilitySecurityDTOS(List<FacilityFacilitySecurityDTO> facilityFacilitySecurityDTOS) {
        this.facilityFacilitySecurityDTOS = facilityFacilitySecurityDTOS;
    }

    public BigDecimal getFacilitySecurityAmount() {
        if (facilitySecurityAmount == null){
            facilitySecurityAmount = DecimalCalculator.getDefaultZero();
        }
        return facilitySecurityAmount;
    }

    public void setFacilitySecurityAmount(BigDecimal facilitySecurityAmount) {
        this.facilitySecurityAmount = facilitySecurityAmount;
    }

    public BigDecimal getCashAmount() {
        if (cashAmount == null){
            cashAmount = DecimalCalculator.getDefaultZero();
        }
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public AppsConstants.YesNo getIsCashSecurity() {
        return isCashSecurity;
    }

    public void setIsCashSecurity(AppsConstants.YesNo isCashSecurity) {
        this.isCashSecurity = isCashSecurity;
    }

    @Override
    public String toString() {
        return "FacilitySecurityDTO{" +
                "facilitySecurityID=" + facilitySecurityID +
                ", facilityPaperID=" + facilityPaperID +
                ", facilityID=" + facilityID +
                ", securityCode='" + securityCode + '\'' +
                ", securityDetail='" + securityDetail + '\'' +
                ", securityAmount=" + securityAmount +
                ", cashAmount=" + cashAmount +
                ", securityCurrency='" + securityCurrency + '\'' +
                ", status=" + status +
                ", isCommonSecurity=" + isCommonSecurity +
                ", isCashSecurity=" + isCashSecurity +
                ", facilityDTOS=" + facilityDTOS +
                ", facilityFacilitySecurityDTOS=" + facilityFacilitySecurityDTOS +
                ", facilitySecurityAmount=" + facilitySecurityAmount +
                '}';
    }
}
