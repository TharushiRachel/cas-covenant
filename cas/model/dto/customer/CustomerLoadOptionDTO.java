package com.itechro.cas.model.dto.customer;

import java.io.Serializable;

public class CustomerLoadOptionDTO implements Serializable {
    
    private boolean loadAddressDetail;
    
    private boolean loadTelephoneDetail;
    
    private boolean loadBankDetail;
    
    private boolean loadIdentificationDetail;

    public boolean isLoadAddressDetail() {
        return loadAddressDetail;
    }

    public void loadAddressDetail() {
        this.loadAddressDetail = true;
    }

    public boolean isLoadTelephoneDetail() {
        return loadTelephoneDetail;
    }

    public void loadTelephoneDetail() {
        this.loadTelephoneDetail = true;
    }

    public boolean isLoadBankDetail() {
        return loadBankDetail;
    }

    public void loadBankDetail() {
        this.loadBankDetail = true;
    }

    public boolean isLoadIdentificationDetail() {
        return loadIdentificationDetail;
    }

    public void loadIdentificationDetail() {
        this.loadIdentificationDetail = true;
    }
}
