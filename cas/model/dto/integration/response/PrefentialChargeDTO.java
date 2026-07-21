package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PrefentialChargeDTO implements Serializable {

    @JsonProperty("acctName")
    private Object acctName;

    @JsonProperty("acct_id")
    private Object acct_id;

    @JsonProperty("cif")
    private Object cif;

    @JsonProperty("acid")
    private Object acid;

    @JsonProperty("menuId")
    private Object menuId;

    @JsonProperty("regType")
    private Object regType;

    @JsonProperty("event")
    private Object event;

    @JsonProperty("chrgCrncy")
    private Object chrgCrncy;

    @JsonProperty("minAmt")
    private Object minAmt;

    @JsonProperty("maxAmt")
    private Object maxAmt;

    @JsonProperty("chrgSubType")
    private Object chrgSubType;

    @JsonProperty("chrgMethod")
    private Object chrgMethod;

    @JsonProperty("pcntAmt")
    private Object pcntAmt;

    @JsonProperty("expiryDate")
    private Object expiryDate;
}
