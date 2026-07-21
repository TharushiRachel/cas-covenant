package com.itechro.cas.model.dto.acae.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ACAEAcctBalByAcctDataDTO {

    @JsonProperty("acctType")
    private String acctType;

    @JsonProperty("acctNum")
    private String acctNum;

    @JsonProperty("lienAmount")
    private String lienAmount;

    @JsonProperty("reservedAmount")
    private String reservedAmount;

    @JsonProperty("balance")
    private String balance;

    @JsonProperty("currency")
    private String currency;
}

