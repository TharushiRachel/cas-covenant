package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DishonouredChequeSummResponse implements Serializable {

    @JsonProperty("NO_OF_DC")
    private String noOfDC;

    @JsonProperty("TOT_AMT_OF_DC")
    private String totalAmountOfDC;

    @JsonProperty("NO_OF_DC")
    public String getNoOfDC() {
        return noOfDC;
    }

    @JsonProperty("NO_OF_DC")
    public void setNoOfDC(String noOfDC) {
        this.noOfDC = noOfDC;
    }

    @JsonProperty("TOT_AMT_OF_DC")
    public String getTotalAmountOfDC() {
        return totalAmountOfDC;
    }

    @JsonProperty("TOT_AMT_OF_DC")
    public void setTotalAmountOfDC(String totalAmountOfDC) {
        this.totalAmountOfDC = totalAmountOfDC;
    }
}
