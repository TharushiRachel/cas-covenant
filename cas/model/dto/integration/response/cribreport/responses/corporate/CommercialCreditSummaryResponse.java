package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialCreditSummaryResponse implements Serializable {

    @JsonProperty("CURRENCY_VER4")
    private List<CurrencyResponse> currencyResponses;

    @JsonProperty("CURRENCY_VER4")
    public List<CurrencyResponse> getCurrencyResponses() {
        return currencyResponses;
    }

    @JsonProperty("CURRENCY_VER4")
    public void setCurrencyResponses(List<CurrencyResponse> currencyResponses) {
        this.currencyResponses = currencyResponses;
    }
}
