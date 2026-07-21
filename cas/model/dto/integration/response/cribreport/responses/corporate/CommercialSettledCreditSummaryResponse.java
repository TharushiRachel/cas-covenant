package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialSettledCreditSummaryResponse implements Serializable {

    @JsonProperty("COMM_SETTLED_SUMMARY_VER4")
    private List<CommercialSettledSummaryResponse> commercialSettledSummaryResponses;

    @JsonProperty("COMM_SETTLED_SUMMARY_VER4")
    public List<CommercialSettledSummaryResponse> getCommercialSettledSummaryResponses() {
        return commercialSettledSummaryResponses;
    }

    @JsonProperty("COMM_SETTLED_SUMMARY_VER4")
    public void setCommercialSettledSummaryResponses(List<CommercialSettledSummaryResponse> commercialSettledSummaryResponses) {
        this.commercialSettledSummaryResponses = commercialSettledSummaryResponses;
    }
}
