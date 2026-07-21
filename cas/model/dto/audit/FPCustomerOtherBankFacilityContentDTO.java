package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class FPCustomerOtherBankFacilityContentDTO extends BaseContentDTO {

    @SerializedName("FP OTR CUS BANK FACILITY ID")
    private Integer fpCusOtherBankFacilityID;

    @SerializedName("FP CUSTOMER ID")
    private Integer fpCustomerID;

    @SerializedName("FP CUSTOMER NAME")
    private String CustomerName;

    @SerializedName("BANK NAME")
    private String bankName;

    @SerializedName("BRANCH NAME")
    private String branchName;

    @SerializedName("CURRENCY")
    private String currency;

    @SerializedName("FACILITY AMOUNT")
    private BigDecimal facilityAmount;

    @SerializedName("EXISTING AMOUNT")
    private BigDecimal existingAmount;

    @SerializedName("ORIGINAL AMOUNT")
    private BigDecimal originalAmount;

    @SerializedName("OUTSTANDING AMOUNT")
    private BigDecimal outstandingAmount;

    @SerializedName("FACILITY TYPE")
    private String facilityType;

    @SerializedName("DISBURSEMENT DATE")
    private String disbursementDate;

    @SerializedName("MATURITY DATE")
    private String maturityDate;

    @SerializedName("SECURITIES")
    private String securities;

    @SerializedName("STATUS")
    private String status;

    public Integer getFpCusOtherBankFacilityID() {
        return fpCusOtherBankFacilityID;
    }

    public void setFpCusOtherBankFacilityID(Integer fpCusOtherBankFacilityID) {
        this.fpCusOtherBankFacilityID = fpCusOtherBankFacilityID;
    }

    public Integer getFpCustomerID() {
        return fpCustomerID;
    }

    public void setFpCustomerID(Integer fpCustomerID) {
        this.fpCustomerID = fpCustomerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
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

    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getSecurities() {
        return securities;
    }

    public void setSecurities(String securities) {
        this.securities = securities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}
