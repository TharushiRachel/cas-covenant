package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.CasCustomerOtherBankFacility;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CASCustomerOtherBankFacilityDTO implements Serializable {

    private Integer casCustomerOtherBankFacilityID;

    private Integer casCustomerID;

    private String bankName;

    private String branchName;

    private String currency;

    private BigDecimal facilityAmount;

    private BigDecimal outstandingAmount;

    private BigDecimal existingAmount;

    private BigDecimal originalAmount;

    private String facilityType;

    private Date disbursementDate;

    private Date maturityDate;

    private String disbursementDateStr;

    private String maturityDateStr;

    private String securities;

    private AppsConstants.Status status;

    public CASCustomerOtherBankFacilityDTO() {
    }

    public CASCustomerOtherBankFacilityDTO(CasCustomerOtherBankFacility casCustomerOtherBankFacility) {
        this.casCustomerOtherBankFacilityID = casCustomerOtherBankFacility.getCasCusOtherBankFacilityID();
        this.casCustomerID = casCustomerOtherBankFacility.getCASCustomer().getCasCustomerID();
        this.bankName = casCustomerOtherBankFacility.getBankName();
        this.branchName = casCustomerOtherBankFacility.getBranchName();
        this.currency = casCustomerOtherBankFacility.getCurrency();
        this.facilityAmount = casCustomerOtherBankFacility.getFacilityAmount();
        this.existingAmount = casCustomerOtherBankFacility.getExistingAmount();
        this.originalAmount = casCustomerOtherBankFacility.getOriginalAmount();
        this.outstandingAmount = casCustomerOtherBankFacility.getOutstandingAmount();
        this.facilityType = casCustomerOtherBankFacility.getFacilityType();
        this.disbursementDate = casCustomerOtherBankFacility.getDisbursementDate();
        this.maturityDate = casCustomerOtherBankFacility.getMaturityDate();
        this.securities = casCustomerOtherBankFacility.getSecurities();
        this.status = casCustomerOtherBankFacility.getStatus();
        if (casCustomerOtherBankFacility.getDisbursementDate() != null) {
            this.disbursementDateStr = CalendarUtil.getDefaultFormattedDateOnly(casCustomerOtherBankFacility.getDisbursementDate());
        }
        if (casCustomerOtherBankFacility.getMaturityDate() != null) {
            this.maturityDateStr = CalendarUtil.getDefaultFormattedDateOnly(casCustomerOtherBankFacility.getMaturityDate());
        }
    }

    public Integer getCasCustomerOtherBankFacilityID() {
        return casCustomerOtherBankFacilityID;
    }

    public void setCasCustomerOtherBankFacilityID(Integer casCustomerOtherBankFacilityID) {
        this.casCustomerOtherBankFacilityID = casCustomerOtherBankFacilityID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getFacilityAmount() {
        return facilityAmount;
    }

    public void setFacilityAmount(BigDecimal facilityAmount) {
        this.facilityAmount = facilityAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public Date getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(Date disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getDisbursementDateStr() {
        return disbursementDateStr;
    }

    public void setDisbursementDateStr(String disbursementDateStr) {
        this.disbursementDateStr = disbursementDateStr;
    }

    public String getMaturityDateStr() {
        return maturityDateStr;
    }

    public void setMaturityDateStr(String maturityDateStr) {
        this.maturityDateStr = maturityDateStr;
    }

    public String getSecurities() {
        return securities;
    }

    public void setSecurities(String securities) {
        this.securities = securities;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public BigDecimal getExistingAmount() {
        return existingAmount;
    }

    public void setExistingAmount(BigDecimal existingAmount) {
        this.existingAmount = existingAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    @Override
    public String toString() {
        return "CASCustomerOtherBankFacilityDTO{" +
                "casCustomerOtherBankFacilityID=" + casCustomerOtherBankFacilityID +
                ", casCustomerID=" + casCustomerID +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", currency='" + currency + '\'' +
                ", facilityAmount=" + facilityAmount +
                ", outstandingAmount=" + outstandingAmount +
                ", existingAmount=" + existingAmount +
                ", originalAmount=" + originalAmount +
                ", facilityType='" + facilityType + '\'' +
                ", disbursementDate=" + disbursementDate +
                ", maturityDate=" + maturityDate +
                ", disbursementDateStr='" + disbursementDateStr + '\'' +
                ", maturityDateStr='" + maturityDateStr + '\'' +
                ", securities='" + securities + '\'' +
                ", status=" + status +
                '}';
    }
}
