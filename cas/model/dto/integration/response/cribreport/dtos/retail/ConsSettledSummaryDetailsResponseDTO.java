package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsSettledSummaryDetailsResponse;

import java.io.Serializable;

public class ConsSettledSummaryDetailsResponseDTO implements Serializable {

    private String currency;
    private String cfType;
    private String totalNoOfCreditFacilitiesBRW;
    private String totalAmountGrantedBTW;
    private String totalNoOFCreditFacilitiesGRT;
    private String totalAmountGrantedGRT;

    public ConsSettledSummaryDetailsResponseDTO() {
    }

    public ConsSettledSummaryDetailsResponseDTO(ConsSettledSummaryDetailsResponse consSettledSummaryDetailsResponse) {
        this.currency = consSettledSummaryDetailsResponse.getCurrency();
        this.cfType = consSettledSummaryDetailsResponse.getCfType();
        this.totalNoOfCreditFacilitiesBRW = consSettledSummaryDetailsResponse.getTotalNoOfCreditFacilitiesBRW();
        this.totalAmountGrantedBTW = consSettledSummaryDetailsResponse.getTotalAmountGrantedBTW();
        this.totalNoOFCreditFacilitiesGRT = consSettledSummaryDetailsResponse.getTotalNoOFCreditFacilitiesGRT();
        this.totalAmountGrantedGRT = consSettledSummaryDetailsResponse.getTotalAmountGrantedGRT();
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

    public String getTotalAmountGrantedBTW() {
        return totalAmountGrantedBTW;
    }

    public void setTotalAmountGrantedBTW(String totalAmountGrantedBTW) {
        this.totalAmountGrantedBTW = totalAmountGrantedBTW;
    }

    public String getTotalNoOFCreditFacilitiesGRT() {
        return totalNoOFCreditFacilitiesGRT;
    }

    public void setTotalNoOFCreditFacilitiesGRT(String totalNoOFCreditFacilitiesGRT) {
        this.totalNoOFCreditFacilitiesGRT = totalNoOFCreditFacilitiesGRT;
    }

    public String getTotalAmountGrantedGRT() {
        return totalAmountGrantedGRT;
    }

    public void setTotalAmountGrantedGRT(String totalAmountGrantedGRT) {
        this.totalAmountGrantedGRT = totalAmountGrantedGRT;
    }
}
