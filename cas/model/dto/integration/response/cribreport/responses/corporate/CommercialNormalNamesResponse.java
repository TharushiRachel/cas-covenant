package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialNormalNamesResponse implements Serializable {

    @JsonProperty("NAMES_VER4")
    private List<NamesResponse> namesResponses;

    @JsonProperty("NAMES_VER4")
    public List<NamesResponse> getNamesResponses() {
        return namesResponses;
    }

    @JsonProperty("NAMES_VER4")
    public void setNamesResponses(List<NamesResponse> namesResponses) {
        this.namesResponses = namesResponses;
    }
}
