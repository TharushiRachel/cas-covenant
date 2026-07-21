package com.itechro.cas.model.dto.acae.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ACAEStatByAcctDataDTO {
    @JsonProperty("CustId")
    private String CustId;

    @JsonProperty("StartDate")
    private String StartDate;

    @JsonProperty("HighBal")
    private String HighBal;

    @JsonProperty("LowBal")
    private String LowBal;

    @JsonProperty("AvgBal")
    private String AvgBal;

    @JsonProperty("TotalDr")
    private String TotalDr;

    @JsonProperty("TotalCr")
    private String TotalCr;

    @JsonProperty("CheqRet")
    private String CheqRet;

    @JsonProperty("FloatAmt")
    private String FloatAmt;
}
