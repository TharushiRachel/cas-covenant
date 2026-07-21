package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class RCTResponse implements Serializable {

    @JsonProperty("StatusCode")
    private String StatusCode;

    @JsonProperty("Results")
    private List<RCTProductGroup> Results;
}
