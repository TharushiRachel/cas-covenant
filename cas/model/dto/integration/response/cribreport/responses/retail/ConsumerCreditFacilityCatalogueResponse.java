package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerCreditFacilityCatalogueResponse implements Serializable {

    @JsonProperty("CONSUMER_CREDIT_DETAILS_CATALOGUE_VER4")
    private List<ConsumerCreditDetailsCatalogueResponse> consumerCreditDetailsCatalogueResponses;

    @JsonProperty("CONSUMER_CREDIT_DETAILS_CATALOGUE_VER4")
    public List<ConsumerCreditDetailsCatalogueResponse> getConsumerCreditDetailsCatalogueResponses() {
        return consumerCreditDetailsCatalogueResponses;
    }

    @JsonProperty("CONSUMER_CREDIT_DETAILS_CATALOGUE_VER4")
    public void setConsumerCreditDetailsCatalogueResponses(List<ConsumerCreditDetailsCatalogueResponse> consumerCreditDetailsCatalogueResponses) {
        this.consumerCreditDetailsCatalogueResponses = consumerCreditDetailsCatalogueResponses;
    }
}
