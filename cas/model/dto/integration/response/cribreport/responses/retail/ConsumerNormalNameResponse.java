package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerNormalNameResponse implements Serializable {

    @JsonProperty("NAMES_VER4")
    private List<NameResponse> nameResponses;

    @JsonProperty("NAMES_VER4")
    public List<NameResponse> getNameResponses() {
        return nameResponses;
    }

    @JsonProperty("NAMES_VER4")
    public void setNameResponses(List<NameResponse> nameResponses) {
        this.nameResponses = nameResponses;
    }
}
