package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerSettledCreditSummaryResponse implements Serializable {

    @JsonProperty("CONS_SETTLED_SUMMARY_VER4")
    private List<ConsSettledSummaryResponse> consSettledSummaryResponses;

    @JsonProperty("CONS_SETTLED_SUMMARY_VER4")
    public List<ConsSettledSummaryResponse> getConsSettledSummaryResponses() {
        return consSettledSummaryResponses;
    }

    @JsonProperty("CONS_SETTLED_SUMMARY_VER4")
    public void setConsSettledSummaryResponses(List<ConsSettledSummaryResponse> consSettledSummaryResponses) {
        this.consSettledSummaryResponses = consSettledSummaryResponses;
    }
}
