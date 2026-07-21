package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialSettledSummaryDetailsResponse;

import java.io.Serializable;

public class CommercialSettledSummaryDetailsResponseDTO implements Serializable {

    private String currency;

    private String cfType;

    private String totalNoOfCreditFacilitiesBRW;

    private String totalAmtGrantedBRW;

    private String totalNoOfCreditFacilitiesGRT;

    private String totalAmtGrantedGRT;

    public CommercialSettledSummaryDetailsResponseDTO() {
    }


    public CommercialSettledSummaryDetailsResponseDTO(CommercialSettledSummaryDetailsResponse commercialSettledSummaryDetailsResponse) {
        if (commercialSettledSummaryDetailsResponse != null) {
            this.currency = commercialSettledSummaryDetailsResponse.getCurrency();
            this.cfType = commercialSettledSummaryDetailsResponse.getCfType();
            this.totalNoOfCreditFacilitiesBRW = commercialSettledSummaryDetailsResponse.getTotalNoOfCreditFacilitiesBRW();
            this.totalAmtGrantedBRW = commercialSettledSummaryDetailsResponse.getTotalAmtGrantedBRW();
            this.totalNoOfCreditFacilitiesGRT = commercialSettledSummaryDetailsResponse.getTotalNoOfCreditFacilitiesGRT();
            this.totalAmtGrantedGRT = commercialSettledSummaryDetailsResponse.getTotalAmtGrantedGRT();
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCfType() {
        return cfType;
    }

    public void setCfType(String cfType) {
        this.cfType = cfType;
    }

    public String getTotalNoOfCreditFacilitiesBRW() {
        return totalNoOfCreditFacilitiesBRW;
    }

    public void setTotalNoOfCreditFacilitiesBRW(String totalNoOfCreditFacilitiesBRW) {
        this.totalNoOfCreditFacilitiesBRW = totalNoOfCreditFacilitiesBRW;
    }

    public String getTotalAmtGrantedBRW() {
        return totalAmtGrantedBRW;
    }

    public void setTotalAmtGrantedBRW(String totalAmtGrantedBRW) {
        this.totalAmtGrantedBRW = totalAmtGrantedBRW;
    }

    public String getTotalNoOfCreditFacilitiesGRT() {
        return totalNoOfCreditFacilitiesGRT;
    }

    public void setTotalNoOfCreditFacilitiesGRT(String totalNoOfCreditFacilitiesGRT) {
        this.totalNoOfCreditFacilitiesGRT = totalNoOfCreditFacilitiesGRT;
    }

    public String getTotalAmtGrantedGRT() {
        return totalAmtGrantedGRT;
    }

    public void setTotalAmtGrantedGRT(String totalAmtGrantedGRT) {
        this.totalAmtGrantedGRT = totalAmtGrantedGRT;
    }
}
