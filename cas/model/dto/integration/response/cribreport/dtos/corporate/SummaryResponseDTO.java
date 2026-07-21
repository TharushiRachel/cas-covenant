package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.SummaryResponse;

import java.io.Serializable;

public class SummaryResponseDTO implements Serializable {

    private String bureauCurrency;

    private String ownershipType;

    private String totalNoOfCreditFacilities;

    private String sanctionedAmount;

    private String totalOutstanding;

    public SummaryResponseDTO() {
    }

    public SummaryResponseDTO(SummaryResponse summaryResponse) {
        if (summaryResponse != null) {
            this.bureauCurrency = summaryResponse.getBureauCurrency();
            this.ownershipType = summaryResponse.getOwnershipType();
            this.totalNoOfCreditFacilities = summaryResponse.getTotalNoOfCreditFacilities();
            this.sanctionedAmount = summaryResponse.getSanctionedAmount();
            this.totalOutstanding = summaryResponse.getTotalOutstanding();
        }
    }


    public String getBureauCurrency() {
        return bureauCurrency;
    }

    public void setBureauCurrency(String bureauCurrency) {
        this.bureauCurrency = bureauCurrency;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public String getTotalNoOfCreditFacilities() {
        return totalNoOfCreditFacilities;
    }

    public void setTotalNoOfCreditFacilities(String totalNoOfCreditFacilities) {
        this.totalNoOfCreditFacilities = totalNoOfCreditFacilities;
    }

    public String getSanctionedAmount() {
        return sanctionedAmount;
    }

    public void setSanctionedAmount(String sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public String getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(String totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }
}
