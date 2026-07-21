package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.depositdetails;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.depositdetails.DepositDetail;

import java.io.Serializable;

public class DepositDetailDTO implements Serializable {

    private String foracid;

    private String accountType;

    private String accountCurrencyCode;

    private String sanclim;

    private String clrBalAmt;

    public DepositDetailDTO() {
    }

    public DepositDetailDTO(DepositDetail depositDetail) {
        this.foracid = depositDetail.getForacid();
        this.accountType = depositDetail.getAccountType();
        this.accountCurrencyCode = depositDetail.getAccountCurrencyCode();
        this.sanclim = depositDetail.getSanclim();
        this.clrBalAmt = depositDetail.getClrBalAmt();
    }


    public String getForacid() {
        return foracid;
    }

    public void setForacid(String foracid) {
        this.foracid = foracid;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountCurrencyCode() {
        return accountCurrencyCode;
    }

    public void setAccountCurrencyCode(String accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public String getSanclim() {
        return sanclim;
    }

    public void setSanclim(String sanclim) {
        this.sanclim = sanclim;
    }

    public String getClrBalAmt() {
        return clrBalAmt;
    }

    public void setClrBalAmt(String clrBalAmt) {
        this.clrBalAmt = clrBalAmt;
    }

    @Override
    public String toString() {
        return "DepositDetailDTO{" +
                "foracid='" + foracid + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountCurrencyCode='" + accountCurrencyCode + '\'' +
                ", sanclim='" + sanclim + '\'' +
                ", clrBalAmt='" + clrBalAmt + '\'' +
                '}';
    }
}
