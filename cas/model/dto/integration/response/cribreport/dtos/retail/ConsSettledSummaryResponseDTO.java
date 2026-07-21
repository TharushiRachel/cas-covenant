package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsSettledSummaryResponse;

import java.io.Serializable;

public class ConsSettledSummaryResponseDTO implements Serializable {

    private String currency;
    private String ownership;
    private String totalNoOfCreditFacilitiesYr1;
    private String totalAmountGrantedYear1;
    private String totalNoOfCreditFacilitiesYr2;
    private String totalAmountGrantedYear2;
    private String totalNoOfCreditFacilitiesYr3;
    private String totalAmountGrantedYear3;
    private String totalNoOfCreditFacilitiesYr4;
    private String totalAmountGrantedYear4;
    private String totalNoOfCreditFacilitiesYr5;
    private String totalAmountGrantedYear5;
    private String rp;
    private String styr1;
    private String styr2;
    private String styr3;
    private String styr4;
    private String styr5;
    private String isColoringNeeded;

    public ConsSettledSummaryResponseDTO() {
    }


    public ConsSettledSummaryResponseDTO(ConsSettledSummaryResponse consSettledSummaryResponse) {

        this.currency = consSettledSummaryResponse.getCurrency();
        this.ownership = consSettledSummaryResponse.getOwnership();
        this.totalNoOfCreditFacilitiesYr1 = consSettledSummaryResponse.getTotalNoOfCreditFacilitiesYr1();
        this.totalAmountGrantedYear1 = consSettledSummaryResponse.getTotalAmountGrantedYear1();
        this.totalNoOfCreditFacilitiesYr2 = consSettledSummaryResponse.getTotalNoOfCreditFacilitiesYr2();
        this.totalAmountGrantedYear2 = consSettledSummaryResponse.getTotalAmountGrantedYear2();
        this.totalNoOfCreditFacilitiesYr3 = consSettledSummaryResponse.getTotalNoOfCreditFacilitiesYr3();
        this.totalAmountGrantedYear3 = consSettledSummaryResponse.getTotalAmountGrantedYear3();
        this.totalNoOfCreditFacilitiesYr4 = consSettledSummaryResponse.getTotalNoOfCreditFacilitiesYr4();
        this.totalAmountGrantedYear4 = consSettledSummaryResponse.getTotalAmountGrantedYear4();
        this.totalNoOfCreditFacilitiesYr5 = consSettledSummaryResponse.getTotalNoOfCreditFacilitiesYr5();
        this.totalAmountGrantedYear5 = consSettledSummaryResponse.getTotalAmountGrantedYear5();
        this.rp = consSettledSummaryResponse.getRp();
        this.styr1 = consSettledSummaryResponse.getStyr1();
        this.styr2 = consSettledSummaryResponse.getStyr2();
        this.styr3 = consSettledSummaryResponse.getStyr3();
        this.styr4 = consSettledSummaryResponse.getStyr4();
        this.styr5 = consSettledSummaryResponse.getStyr5();
        this.isColoringNeeded = consSettledSummaryResponse.getIsColoringNeeded();

    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getTotalNoOfCreditFacilitiesYr1() {
        return totalNoOfCreditFacilitiesYr1;
    }

    public void setTotalNoOfCreditFacilitiesYr1(String totalNoOfCreditFacilitiesYr1) {
        this.totalNoOfCreditFacilitiesYr1 = totalNoOfCreditFacilitiesYr1;
    }

    public String getTotalAmountGrantedYear1() {
        return totalAmountGrantedYear1;
    }

    public void setTotalAmountGrantedYear1(String totalAmountGrantedYear1) {
        this.totalAmountGrantedYear1 = totalAmountGrantedYear1;
    }

    public String getTotalNoOfCreditFacilitiesYr2() {
        return totalNoOfCreditFacilitiesYr2;
    }

    public void setTotalNoOfCreditFacilitiesYr2(String totalNoOfCreditFacilitiesYr2) {
        this.totalNoOfCreditFacilitiesYr2 = totalNoOfCreditFacilitiesYr2;
    }

    public String getTotalAmountGrantedYear2() {
        return totalAmountGrantedYear2;
    }

    public void setTotalAmountGrantedYear2(String totalAmountGrantedYear2) {
        this.totalAmountGrantedYear2 = totalAmountGrantedYear2;
    }

    public String getTotalNoOfCreditFacilitiesYr3() {
        return totalNoOfCreditFacilitiesYr3;
    }

    public void setTotalNoOfCreditFacilitiesYr3(String totalNoOfCreditFacilitiesYr3) {
        this.totalNoOfCreditFacilitiesYr3 = totalNoOfCreditFacilitiesYr3;
    }

    public String getTotalAmountGrantedYear3() {
        return totalAmountGrantedYear3;
    }

    public void setTotalAmountGrantedYear3(String totalAmountGrantedYear3) {
        this.totalAmountGrantedYear3 = totalAmountGrantedYear3;
    }

    public String getTotalNoOfCreditFacilitiesYr4() {
        return totalNoOfCreditFacilitiesYr4;
    }

    public void setTotalNoOfCreditFacilitiesYr4(String totalNoOfCreditFacilitiesYr4) {
        this.totalNoOfCreditFacilitiesYr4 = totalNoOfCreditFacilitiesYr4;
    }

    public String getTotalAmountGrantedYear4() {
        return totalAmountGrantedYear4;
    }

    public void setTotalAmountGrantedYear4(String totalAmountGrantedYear4) {
        this.totalAmountGrantedYear4 = totalAmountGrantedYear4;
    }

    public String getTotalNoOfCreditFacilitiesYr5() {
        return totalNoOfCreditFacilitiesYr5;
    }

    public void setTotalNoOfCreditFacilitiesYr5(String totalNoOfCreditFacilitiesYr5) {
        this.totalNoOfCreditFacilitiesYr5 = totalNoOfCreditFacilitiesYr5;
    }

    public String getTotalAmountGrantedYear5() {
        return totalAmountGrantedYear5;
    }

    public void setTotalAmountGrantedYear5(String totalAmountGrantedYear5) {
        this.totalAmountGrantedYear5 = totalAmountGrantedYear5;
    }

    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    public String getStyr1() {
        return styr1;
    }

    public void setStyr1(String styr1) {
        this.styr1 = styr1;
    }

    public String getStyr2() {
        return styr2;
    }

    public void setStyr2(String styr2) {
        this.styr2 = styr2;
    }

    public String getStyr3() {
        return styr3;
    }

    public void setStyr3(String styr3) {
        this.styr3 = styr3;
    }

    public String getStyr4() {
        return styr4;
    }

    public void setStyr4(String styr4) {
        this.styr4 = styr4;
    }

    public String getStyr5() {
        return styr5;
    }

    public void setStyr5(String styr5) {
        this.styr5 = styr5;
    }

    public String getIsColoringNeeded() {
        return isColoringNeeded;
    }

    public void setIsColoringNeeded(String isColoringNeeded) {
        this.isColoringNeeded = isColoringNeeded;
    }
}
