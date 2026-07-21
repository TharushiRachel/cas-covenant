package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class LimitNodeResponse implements Serializable {

    @JsonProperty("status")
    private String status;

    @JsonProperty("reqId")
    private String reqId;

    @JsonProperty("nodeDetails")
    private LimitNodeDetailsResponse nodeDetails;
}
