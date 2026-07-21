package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialCreditFacilityCatalogueResponse implements Serializable {

    @JsonProperty("COMMERCIAL_CREDIT_DETAILS_CATALOGUE_VER4")
    private List<CommercialCreditDetailsCatalogueResponse> commercialCreditDetailsCatalogueResponses;

    @JsonProperty("COMMERCIAL_CREDIT_DETAILS_CATALOGUE_VER4")
    public List<CommercialCreditDetailsCatalogueResponse> getCommercialCreditDetailsCatalogueResponses() {
        return commercialCreditDetailsCatalogueResponses;
    }

    @JsonProperty("COMMERCIAL_CREDIT_DETAILS_CATALOGUE_VER4")
    public void setCommercialCreditDetailsCatalogueResponses(List<CommercialCreditDetailsCatalogueResponse> commercialCreditDetailsCatalogueResponses) {
        this.commercialCreditDetailsCatalogueResponses = commercialCreditDetailsCatalogueResponses;
    }
}
