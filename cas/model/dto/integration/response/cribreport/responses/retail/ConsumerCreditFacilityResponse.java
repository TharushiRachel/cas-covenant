package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerCreditFacilityResponse implements Serializable {

    @JsonProperty("MASTER_CREDIT_DETAILS_VER4")
    private MasterCreditDetailsResponse masterCreditDetailsResponse;

    @JsonProperty("MASTER_CREDIT_DETAILS_VER4")
    public MasterCreditDetailsResponse getMasterCreditDetailsResponse() {
        return masterCreditDetailsResponse;
    }

    @JsonProperty("MASTER_CREDIT_DETAILS_VER4")
    public void setMasterCreditDetailsResponse(MasterCreditDetailsResponse masterCreditDetailsResponse) {
        this.masterCreditDetailsResponse = masterCreditDetailsResponse;
    }
}
