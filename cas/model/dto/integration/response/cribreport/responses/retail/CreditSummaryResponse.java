package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditSummaryResponse implements Serializable {

    @JsonProperty("CURRENCY_VER4")
    private List<CurrencyResponse> currencyResponseList;

    @JsonProperty("CURRENCY_VER4")
    public List<CurrencyResponse> getCurrencyResponseList() {
        return currencyResponseList;
    }

    @JsonProperty("CURRENCY_VER4")
    public void setCurrencyResponseList(List<CurrencyResponse> currencyResponseList) {
        this.currencyResponseList = currencyResponseList;
    }
}
