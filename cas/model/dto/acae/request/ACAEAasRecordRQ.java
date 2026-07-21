package com.itechro.cas.model.dto.acae.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ACAEAasRecordRQ {

    @JsonProperty("reqId")
    private String reqId;

    @JsonProperty("acctNumber")
    private String acctNumber;
}
