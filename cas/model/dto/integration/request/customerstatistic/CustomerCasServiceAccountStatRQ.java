package com.itechro.cas.model.dto.integration.request.customerstatistic;

import java.io.Serializable;

public class CustomerCasServiceAccountStatRQ extends CustomerAccountStatRQ implements Serializable {

    private String fromdate;

    private String todate;

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    @Override
    public String toString() {
        return "CustomerCasServiceAccountStatRQ{" +
                "fromdate='" + fromdate + '\'' +
                ", todate='" + todate + '\'' +
                '}' + " " + super.toString();
    }
}
