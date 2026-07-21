package com.itechro.cas.model.dto.integration.request.customerstatistic;

import java.io.Serializable;

public class CustomerTranDetCashFlowRQ implements Serializable {

    private String requestId;

    private String cifId;

    private String asAtDate;

    private String noOfMonths;

    public CustomerTranDetCashFlowRQ() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCifId() {
        return cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    public String getAsAtDate() {
        return asAtDate;
    }

    public void setAsAtDate(String asAtDate) {
        this.asAtDate = asAtDate;
    }

    public String getNoOfMonths() {
        return noOfMonths;
    }

    public void setNoOfMonths(String noOfMonths) {
        this.noOfMonths = noOfMonths;
    }

    @Override
    public String toString() {
        return "CustomerTranDetCashFlowRQ{" +
                "requestId='" + requestId + '\'' +
                ", cifId='" + cifId + '\'' +
                ", asAtDate='" + asAtDate + '\'' +
                ", noOfMonths='" + noOfMonths + '\'' +
                '}';
    }
}
