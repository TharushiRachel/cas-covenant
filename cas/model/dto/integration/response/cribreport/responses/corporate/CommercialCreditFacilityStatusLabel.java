package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialCreditFacilityStatusLabel implements Serializable {

    @JsonProperty("MONTHYEAR")
    private String monthYear;

    @JsonProperty("MONTHYEAR")
    public String getMonthYear() {
        return monthYear;
    }

    @JsonProperty("MONTHYEAR")
    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
}
