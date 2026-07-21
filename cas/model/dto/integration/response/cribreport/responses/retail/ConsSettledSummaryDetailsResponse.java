package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsSettledSummaryDetailsResponse implements Serializable {

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("CF_Type")
    private String cfType;

    @JsonProperty("Total_no_of_credit_facilities_BRW")
    private String totalNoOfCreditFacilitiesBRW;

    @JsonProperty("Total_Amt_Granted_BRW")
    private String totalAmountGrantedBTW;

    @JsonProperty("Total_no_of_credit_facilities_GRT")
    private String totalNoOFCreditFacilitiesGRT;

    @JsonProperty("Total_Amt_Granted_GRT")
    private String totalAmountGrantedGRT;

    @JsonProperty("Currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("Currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("CF_Type")
    public String getCfType() {
        return cfType;
    }

    @JsonProperty("CF_Type")
    public void setCfType(String cfType) {
        this.cfType = cfType;
    }

    @JsonProperty("Total_no_of_credit_facilities_BRW")
    public String getTotalNoOfCreditFacilitiesBRW() {
        return totalNoOfCreditFacilitiesBRW;
    }

    @JsonProperty("Total_no_of_credit_facilities_BRW")
    public void setTotalNoOfCreditFacilitiesBRW(String totalNoOfCreditFacilitiesBRW) {
        this.totalNoOfCreditFacilitiesBRW = totalNoOfCreditFacilitiesBRW;
    }

    @JsonProperty("Total_Amt_Granted_BRW")
    public String getTotalAmountGrantedBTW() {
        return totalAmountGrantedBTW;
    }

    @JsonProperty("Total_Amt_Granted_BRW")
    public void setTotalAmountGrantedBTW(String totalAmountGrantedBTW) {
        this.totalAmountGrantedBTW = totalAmountGrantedBTW;
    }

    @JsonProperty("Total_no_of_credit_facilities_GRT")
    public String getTotalNoOFCreditFacilitiesGRT() {
        return totalNoOFCreditFacilitiesGRT;
    }

    @JsonProperty("Total_no_of_credit_facilities_GRT")
    public void setTotalNoOFCreditFacilitiesGRT(String totalNoOFCreditFacilitiesGRT) {
        this.totalNoOFCreditFacilitiesGRT = totalNoOFCreditFacilitiesGRT;
    }

    @JsonProperty("Total_Amt_Granted_GRT")
    public String getTotalAmountGrantedGRT() {
        return totalAmountGrantedGRT;
    }

    @JsonProperty("Total_Amt_Granted_GRT")
    public void setTotalAmountGrantedGRT(String totalAmountGrantedGRT) {
        this.totalAmountGrantedGRT = totalAmountGrantedGRT;
    }
}
