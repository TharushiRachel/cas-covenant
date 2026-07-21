package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ACAEAcctClassificationDTO {
    @JsonProperty("status")
    private String status;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("successResponse")
    private List<ACAEAcctClassificationDataDTO> successResponse;
}
