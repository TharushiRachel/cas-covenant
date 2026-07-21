package com.itechro.cas.model.dto.customer;

import java.io.Serializable;

public class CustomerCribResponseRQ implements Serializable {

    private String nic;

    private String brn;

    public CustomerCribResponseRQ() {
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getBrn() {
        return brn;
    }

    public void setBrn(String brn) {
        this.brn = brn;
    }
}
