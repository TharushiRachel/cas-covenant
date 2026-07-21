package com.itechro.cas.model.dto.integration.response.cribreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class CribResponseDTO implements Serializable {

    @JsonProperty("workflowState")
    private String workflowState;

    @JsonProperty("workflowId")
    private String workflowId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("requestId")
    private String requestId;

    @ToString.Exclude
    @JsonProperty("data")
    private Object data;
}