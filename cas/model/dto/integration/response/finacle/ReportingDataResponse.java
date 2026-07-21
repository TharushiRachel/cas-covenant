package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ReportingDataResponse implements Serializable {

    @JsonProperty("acid")
    private String acid;

    @JsonProperty("solId")
    private String solId;

    @JsonProperty("foracid")
    private String foracid;

    @JsonProperty("acctName")
    private String acctName;

    @JsonProperty("custId")
    private String custId;

    @JsonProperty("glSubhead")
    private String glSubhead;

    @JsonProperty("schmCode")
    private String schmCode;

    @JsonProperty("schmDesc")
    private String schmDesc;

    @JsonProperty("productGroup")
    private String productGroup;

    @JsonProperty("acctOpnDate")
    private String acctOpnDate;

    @JsonProperty("acctClsFlg")
    private String acctClsFlg;

    @JsonProperty("acctClsDate")
    private String acctClsDate;

    @JsonProperty("acctCrncyCode")
    private String acctCrncyCode;

    @JsonProperty("sanctLim")
    private String sanctLim;

    @JsonProperty("clrBalAmt")
    private String clrBalAmt;

    @JsonProperty("interestRate")
    private String interestRate;

    @JsonProperty("intTbleCode")
    private String intTbleCode;

    @JsonProperty("prefRate")
    private String prefRate;

    @JsonProperty("iimitB2kId")
    private String iimitB2kId;

    @JsonProperty("sectorCode")
    private String sectorCode;

    @JsonProperty("sectorDesc")
    private String sectorDesc;

    @JsonProperty("riskRating")
    private String riskRating;

    public String getInterestRate() {
        if (interestRate.isEmpty()){
            return "0";
        }
        return interestRate;
    }

    public String getPrefRate() {
        if (prefRate.isEmpty()){
            return "0";
        }
        return prefRate;
    }
}
