package com.itechro.cas.model.dto.integration.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ACAEAcctClassificationDataRQ {

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("accountId")
    private String accountId;
}
