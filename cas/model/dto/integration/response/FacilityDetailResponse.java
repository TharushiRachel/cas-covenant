package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "facilityName",
        "foracid",
        "sanctLim",
        "clrBalAmt",
        "acctCrncyCode",
        "custId",
        "subClassificationUser",
        "schemeCode",
        "schemeDesc",
        "clean_emer_advn",
        "acct_poa_as_rec_type",
        "cust_reltn_code"

})
public class FacilityDetailResponse implements Serializable {

    @JsonProperty("custId")
    private String customerID;

    @JsonProperty("facilityName")
    private String facilityName;

    @JsonProperty("foracid")
    private String accountNo;

    @JsonProperty("sanctLim")
    private String sanctionLimit;

    @JsonProperty("clrBalAmt")
    private String clearBalanceAmount;

    @JsonProperty("acctCrncyCode")
    private String accountCurrencyCode;

    @JsonProperty("subClassificationUser")
    private String subClassificationUser;

    @JsonProperty("schemeCode")
    private String schemeCode;

    @JsonProperty("schemeDesc")
    private String schemeDesc;

    @JsonProperty("clean_emer_advn")
    private String cleanEmerAdvn;

    @JsonProperty("acct_poa_as_rec_type")
    private String acctPoaAsEwcType;

    @JsonProperty("cust_reltn_code")
    private String customerReltnCode;

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

    @Override
    public String toString() {
        return "FacilityDetailResponse{" +
                "customerID='" + customerID + '\'' +
                ", facilityName='" + facilityName + '\'' +
                ", accountNo=" + accountNo +
                ", sanctionLimit='" + sanctionLimit + '\'' +
                ", clearBalanceAmount='" + clearBalanceAmount + '\'' +
                ", accountCurrencyCode='" + accountCurrencyCode + '\'' +
                ", subClassificationUser='" + subClassificationUser + '\'' +
                ", schemeCode='" + schemeCode + '\'' +
                ", schemeDesc='" + schemeDesc + '\'' +
                ", cleanEmerAdvn='" + cleanEmerAdvn + '\'' +
                ", acctPoaAsEwcType='" + acctPoaAsEwcType + '\'' +
                ", customerReltnCode='" + customerReltnCode + '\'' +
                '}';
    }
}
