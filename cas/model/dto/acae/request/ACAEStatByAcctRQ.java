package com.itechro.cas.model.dto.acae.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ACAEStatByAcctRQ {

    @JsonProperty("RequestId")
    private String RequestId;

    @JsonProperty("Foracid")
    private String Foracid;
}
