package com.itechro.cas.model.dto.integration.response;

import java.io.Serializable;

public class FacilityDetailResponseDTO implements Serializable {

    private String customerID;

    private String facilityName;

    private String accountNo;

    private String sanctionLimit;

    private String clearBalanceAmount ;

    private String accountCurrencyCode;

    private String subClassificationUser;

    private String schemeCode;

    private String schemeDesc;

    private String cleanEmerAdvn;

    private String acctPoaAsEwcType;

    private String customerReltnCode;

    public FacilityDetailResponseDTO(){}

    public FacilityDetailResponseDTO(FacilityDetailResponse facilityDetailResponse){

        this.customerID = facilityDetailResponse.getCustomerID();
        this.facilityName = facilityDetailResponse.getFacilityName();
        this.accountNo = facilityDetailResponse.getAccountNo();
        this.sanctionLimit = facilityDetailResponse.getSanctionLimit();
        this.clearBalanceAmount = facilityDetailResponse.getClearBalanceAmount();
        this.accountCurrencyCode = facilityDetailResponse.getAccountCurrencyCode();
        this.subClassificationUser = facilityDetailResponse.getSubClassificationUser();
        this.schemeCode = facilityDetailResponse.getSchemeCode();
        this.schemeDesc = facilityDetailResponse.getSchemeDesc();
        this.cleanEmerAdvn = facilityDetailResponse.getCleanEmerAdvn();
        this.acctPoaAsEwcType = facilityDetailResponse.getAcctPoaAsEwcType();
        this.customerReltnCode = facilityDetailResponse.getCustomerReltnCode();

    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getForacID() {
        return accountNo;
    }

    public void setForacID(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSanctionLimit() {
        return sanctionLimit;
    }

    public void setSanctionLimit(String sanctionLimit) {
        this.sanctionLimit = sanctionLimit;
    }

    public String getClearBalanceAmount() {
        return clearBalanceAmount;
    }

    public void setClearBalanceAmount(String clearBalanceAmount) {
        this.clearBalanceAmount = clearBalanceAmount;
    }

    public String getAccountCurrencyCode() {
        return accountCurrencyCode;
    }

    public void setAccountCurrencyCode(String accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public String getSubClassificationUser() {
        return subClassificationUser;
    }

    public void setSubClassificationUser(String subClassificationUser) {
        this.subClassificationUser = subClassificationUser;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeDesc() {
        return schemeDesc;
    }

    public void setSchemeDesc(String schemeDesc) {
        this.schemeDesc = schemeDesc;
    }

    public String getCleanEmerAdvn() {
        return cleanEmerAdvn;
    }

    public void setCleanEmerAdvn(String cleanEmerAdvn) {
        this.cleanEmerAdvn = cleanEmerAdvn;
    }

    public String getAcctPoaAsEwcType() {
        return acctPoaAsEwcType;
    }

    public void setAcctPoaAsEwcType(String acctPoaAsEwcType) {
        this.acctPoaAsEwcType = acctPoaAsEwcType;
    }

    public String getCustomerReltnCode() {
        return customerReltnCode;
    }

    public void setCustomerReltnCode(String customerReltnCode) {
        this.customerReltnCode = customerReltnCode;
    }
}
