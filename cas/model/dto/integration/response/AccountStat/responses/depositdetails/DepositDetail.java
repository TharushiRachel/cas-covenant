package com.itechro.cas.model.dto.integration.response.AccountStat.responses.depositdetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DepositDetail implements Serializable {

    @JsonProperty("foracid")
    private String foracid;

    @JsonProperty("accttype")
    private String accountType;

    @JsonProperty("accCrncyCode")
    private String accountCurrencyCode;

    @JsonProperty("sanclim")
    private String sanclim;

    @JsonProperty("clrBalAmt")
    private String clrBalAmt;

    @JsonProperty("foracid")
    public String getForacid() {
        return foracid;
    }

    @JsonProperty("foracid")
    public void setForacid(String foracid) {
        this.foracid = foracid;
    }

    @JsonProperty("accttype")
    public String getAccountType() {
        return accountType;
    }

    @JsonProperty("accttype")
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @JsonProperty("accCrncyCode")
    public String getAccountCurrencyCode() {
        return accountCurrencyCode;
    }

    @JsonProperty("accCrncyCode")
    public void setAccountCurrencyCode(String accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    @JsonProperty("sanclim")
    public String getSanclim() {
        return sanclim;
    }

    @JsonProperty("sanclim")
    public void setSanclim(String sanclim) {
        this.sanclim = sanclim;
    }

    @JsonProperty("clrBalAmt")
    public String getClrBalAmt() {
        return clrBalAmt;
    }

    @JsonProperty("clrBalAmt")
    public void setClrBalAmt(String clrBalAmt) {
        this.clrBalAmt = clrBalAmt;
    }
}
