package com.itechro.cas.model.dto.acae.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ACAEAcctBalByAcctRQ {

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("acctNum")
    private String acctNum;
}
