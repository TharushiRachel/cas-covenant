package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryResponse implements Serializable {

    @JsonProperty("BUREAU_CURRENCY")
    private String bureauCurrency;

    @JsonProperty("OWNERSHIP_TYPE")
    private String ownershipType;

    @JsonProperty("TOTAL_NO_OF_CREDITFACILITIES")
    private String totalNoOfCreditFacilities;

    @JsonProperty("SANCTIONED_AMOUNT")
    private String sanctionedAmount;

    @JsonProperty("TOTAL_OUTSTANDING")
    private String totalOutstanding;


    @JsonProperty("BUREAU_CURRENCY")
    public String getBureauCurrency() {
        return bureauCurrency;
    }

    @JsonProperty("BUREAU_CURRENCY")
    public void setBureauCurrency(String bureauCurrency) {
        this.bureauCurrency = bureauCurrency;
    }

    @JsonProperty("OWNERSHIP_TYPE")
    public String getOwnershipType() {
        return ownershipType;
    }

    @JsonProperty("OWNERSHIP_TYPE")
    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    @JsonProperty("TOTAL_NO_OF_CREDITFACILITIES")
    public String getTotalNoOfCreditFacilities() {
        return totalNoOfCreditFacilities;
    }

    @JsonProperty("TOTAL_NO_OF_CREDITFACILITIES")
    public void setTotalNoOfCreditFacilities(String totalNoOfCreditFacilities) {
        this.totalNoOfCreditFacilities = totalNoOfCreditFacilities;
    }

    @JsonProperty("SANCTIONED_AMOUNT")
    public String getSanctionedAmount() {
        return sanctionedAmount;
    }

    @JsonProperty("SANCTIONED_AMOUNT")
    public void setSanctionedAmount(String sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    @JsonProperty("TOTAL_OUTSTANDING")
    public String getTotalOutstanding() {
        return totalOutstanding;
    }

    @JsonProperty("TOTAL_OUTSTANDING")
    public void setTotalOutstanding(String totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }
}
