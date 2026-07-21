package com.itechro.cas.model.dto.integration.response;

import com.itechro.cas.model.dto.integration.ws.response.AccountDataWSDTO;

import java.io.Serializable;

public class AccountDataDTO implements Serializable {

    private String foracid;

    private String accountName;

    private String schemeCode;

    private String accountSince;

    public AccountDataDTO() {
    }

    public AccountDataDTO(AccountDataWSDTO accountDataWSDTO) {
        this.foracid = accountDataWSDTO.getForacid();
        this.accountName = accountDataWSDTO.getAccName();
        this.schemeCode = accountDataWSDTO.getSchemeCode();
        this.accountSince = accountDataWSDTO.getAccSince();
    }

    public String getForacid() {
        return foracid;
    }

    public void setForacid(String foracid) {
        this.foracid = foracid;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getAccountSince() {
        return accountSince;
    }

    public void setAccountSince(String accountSince) {
        this.accountSince = accountSince;
    }
}
