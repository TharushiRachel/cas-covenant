package com.itechro.cas.model.dto.integration.response.AccountStat.responses.accountstatistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class CustomerAccountStatisticResponse implements Serializable {

    @JsonProperty("accStatResp")
    private List<AccStatResponse> accStatResponses;

    @JsonProperty("accStatResp")
    public List<AccStatResponse> getAccStatResponses() {
        return accStatResponses;
    }

    @JsonProperty("accStatResp")
    public void setAccStatResponses(List<AccStatResponse> accStatResponses) {
        this.accStatResponses = accStatResponses;
    }
}
