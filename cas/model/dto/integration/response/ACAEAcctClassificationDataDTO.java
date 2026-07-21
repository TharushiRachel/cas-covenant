package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ACAEAcctClassificationDataDTO {

    @JsonProperty("facilityName")
    private String facilityName;

    @JsonProperty("foracId")
    private String foracId;

    @JsonProperty("sanctLim")
    private String sanctLim;

    @JsonProperty("clrBalAmt")
    private String clrBalAmt;

    @JsonProperty("acctCrncyCode")
    private String acctCrncyCode;

    @JsonProperty("subClassificationUser")
    private String subClassificationUser;
}
