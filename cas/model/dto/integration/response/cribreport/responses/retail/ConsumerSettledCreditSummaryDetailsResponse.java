package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerSettledCreditSummaryDetailsResponse implements Serializable {


    @JsonProperty("CONS_SETTLED_SUMMARY_DETAILS_VER1")
    private List<ConsSettledSummaryDetailsResponse> consSettledSummaryDetailsResponseList;

    @JsonProperty("CONS_SETTLED_SUMMARY_DETAILS_VER1")
    public List<ConsSettledSummaryDetailsResponse> getConsSettledSummaryDetailsResponseList() {
        return consSettledSummaryDetailsResponseList;
    }

    @JsonProperty("CONS_SETTLED_SUMMARY_DETAILS_VER1")
    public void setConsSettledSummaryDetailsResponseList(List<ConsSettledSummaryDetailsResponse> consSettledSummaryDetailsResponseList) {
        this.consSettledSummaryDetailsResponseList = consSettledSummaryDetailsResponseList;
    }
}
