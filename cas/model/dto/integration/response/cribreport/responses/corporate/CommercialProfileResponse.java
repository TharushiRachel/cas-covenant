package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialProfileResponse implements Serializable {

    @JsonProperty("COMMERCIAL_DETAILS_VER4")
    private List<CommercialDetailsResponse> commercialDetailsResponses;

    @JsonProperty("COMMERCIAL_DETAILS_VER4")
    public List<CommercialDetailsResponse> getCommercialDetailsResponses() {
        return commercialDetailsResponses;
    }

    @JsonProperty("COMMERCIAL_DETAILS_VER4")
    public void setCommercialDetailsResponses(List<CommercialDetailsResponse> commercialDetailsResponses) {
        this.commercialDetailsResponses = commercialDetailsResponses;
    }
}
