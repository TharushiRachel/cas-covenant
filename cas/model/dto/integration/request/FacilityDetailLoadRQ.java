package com.itechro.cas.model.dto.integration.request;

import java.io.Serializable;

public class FacilityDetailLoadRQ implements Serializable {

    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
