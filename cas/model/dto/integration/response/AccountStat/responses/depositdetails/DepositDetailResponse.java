package com.itechro.cas.model.dto.integration.response.AccountStat.responses.depositdetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class DepositDetailResponse implements Serializable {

    @JsonProperty("ClrBal")
    private List<DepositDetail> depositDetails;

    @JsonProperty("ClrBal")
    public List<DepositDetail> getDepositDetails() {
        return depositDetails;
    }

    @JsonProperty("ClrBal")
    public void setDepositDetails(List<DepositDetail> depositDetails) {
        this.depositDetails = depositDetails;
    }

}
