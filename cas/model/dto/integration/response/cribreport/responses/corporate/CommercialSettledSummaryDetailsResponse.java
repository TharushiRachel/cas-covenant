package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialSettledSummaryDetailsResponse {

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("CF_Type")
    private String cfType;

    @JsonProperty("Total_no_of_credit_facilities_BRW")
    private String totalNoOfCreditFacilitiesBRW;

    @JsonProperty("Total_Amt_Granted_BRW")
    private String totalAmtGrantedBRW;

    @JsonProperty("Total_no_of_credit_facilities_GRT")
    private String totalNoOfCreditFacilitiesGRT;

    @JsonProperty("Total_Amt_Granted_GRT")
    private String totalAmtGrantedGRT;

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
    public String getTotalAmtGrantedBRW() {
        return totalAmtGrantedBRW;
    }

    @JsonProperty("Total_Amt_Granted_BRW")
    public void setTotalAmtGrantedBRW(String totalAmtGrantedBRW) {
        this.totalAmtGrantedBRW = totalAmtGrantedBRW;
    }

    @JsonProperty("Total_no_of_credit_facilities_GRT")
    public String getTotalNoOfCreditFacilitiesGRT() {
        return totalNoOfCreditFacilitiesGRT;
    }

    @JsonProperty("Total_no_of_credit_facilities_GRT")
    public void setTotalNoOfCreditFacilitiesGRT(String totalNoOfCreditFacilitiesGRT) {
        this.totalNoOfCreditFacilitiesGRT = totalNoOfCreditFacilitiesGRT;
    }

    @JsonProperty("Total_Amt_Granted_GRT")
    public String getTotalAmtGrantedGRT() {
        return totalAmtGrantedGRT;
    }

    @JsonProperty("Total_Amt_Granted_GRT")
    public void setTotalAmtGrantedGRT(String totalAmtGrantedGRT) {
        this.totalAmtGrantedGRT = totalAmtGrantedGRT;
    }
}
