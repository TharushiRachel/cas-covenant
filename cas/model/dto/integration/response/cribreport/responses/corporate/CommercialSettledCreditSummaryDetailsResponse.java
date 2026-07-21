package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialSettledCreditSummaryDetailsResponse implements Serializable {

    @JsonProperty("COMM_SETTLED_SUMMARY_DETAILS_VER1")
    private List<CommercialSettledSummaryDetailsResponse> commercialSettledSummaryDetailsResponses;

    @JsonProperty("COMM_SETTLED_SUMMARY_DETAILS_VER1")
    public List<CommercialSettledSummaryDetailsResponse> getCommercialSettledSummaryDetailsResponses() {
        return commercialSettledSummaryDetailsResponses;
    }

    @JsonProperty("COMM_SETTLED_SUMMARY_DETAILS_VER1")
    public void setCommercialSettledSummaryDetailsResponses(List<CommercialSettledSummaryDetailsResponse> commercialSettledSummaryDetailsResponses) {
        this.commercialSettledSummaryDetailsResponses = commercialSettledSummaryDetailsResponses;
    }
}
