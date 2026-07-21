package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialSettledSummaryResponse;

import java.io.Serializable;

public class CommercialSettledSummaryResponseDTO implements Serializable {

    private String currency;

    private String ownership;

    private String totalNoOfCreditFacilityYear1;

    private String totalAmtGrantedYear1;

    private String totalNoOfCreditFacilityYear2;

    private String totalAmtGrantedYear2;

    private String totalNoOfCreditFacilityYear3;

    private String totalAmtGrantedYear3;

    private String totalNoOfCreditFacilityYear4;

    private String totalAmtGrantedYear4;

    private String totalNoOfCreditFacilityYear5;

    private String totalAmtGrantedYear5;

    private String rp;

    private String styr1;

    private String styr2;

    private String styr3;

    private String styr4;

    private String styr5;

    private String isColoringNeeded;

    public CommercialSettledSummaryResponseDTO() {
    }

    public CommercialSettledSummaryResponseDTO(CommercialSettledSummaryResponse commercialSettledSummaryResponse) {
        if (commercialSettledSummaryResponse != null) {
            this.currency = commercialSettledSummaryResponse.getCurrency();
            this.ownership = commercialSettledSummaryResponse.getOwnership();
            this.totalNoOfCreditFacilityYear1 = commercialSettledSummaryResponse.getTotalNoOfCreditFacilityYear1();
            this.totalNoOfCreditFacilityYear2 = commercialSettledSummaryResponse.getTotalNoOfCreditFacilityYear2();
            this.totalNoOfCreditFacilityYear3 = commercialSettledSummaryResponse.getTotalNoOfCreditFacilityYear3();
            this.totalNoOfCreditFacilityYear4 = commercialSettledSummaryResponse.getTotalNoOfCreditFacilityYear4();
            this.totalNoOfCreditFacilityYear5 = commercialSettledSummaryResponse.getTotalNoOfCreditFacilityYear5();
            this.totalAmtGrantedYear1 = commercialSettledSummaryResponse.getTotalAmtGrantedYear1();
            this.totalAmtGrantedYear2 = commercialSettledSummaryResponse.getTotalAmtGrantedYear2();
            this.totalAmtGrantedYear3 = commercialSettledSummaryResponse.getTotalAmtGrantedYear3();
            this.totalAmtGrantedYear4 = commercialSettledSummaryResponse.getTotalAmtGrantedYear4();
            this.totalAmtGrantedYear5 = commercialSettledSummaryResponse.getTotalAmtGrantedYear5();
            this.rp = commercialSettledSummaryResponse.getRp();
            this.styr1 = commercialSettledSummaryResponse.getStyr1();
            this.styr2 = commercialSettledSummaryResponse.getStyr2();
            this.styr3 = commercialSettledSummaryResponse.getStyr3();
            this.styr4 = commercialSettledSummaryResponse.getStyr4();
            this.styr5 = commercialSettledSummaryResponse.getStyr5();
            this.isColoringNeeded = commercialSettledSummaryResponse.getIsColoringNeeded();
        }
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

    public String getTotalNoOfCreditFacilityYear1() {
        return totalNoOfCreditFacilityYear1;
    }

    public void setTotalNoOfCreditFacilityYear1(String totalNoOfCreditFacilityYear1) {
        this.totalNoOfCreditFacilityYear1 = totalNoOfCreditFacilityYear1;
    }

    public String getTotalAmtGrantedYear1() {
        return totalAmtGrantedYear1;
    }

    public void setTotalAmtGrantedYear1(String totalAmtGrantedYear1) {
        this.totalAmtGrantedYear1 = totalAmtGrantedYear1;
    }

    public String getTotalNoOfCreditFacilityYear2() {
        return totalNoOfCreditFacilityYear2;
    }

    public void setTotalNoOfCreditFacilityYear2(String totalNoOfCreditFacilityYear2) {
        this.totalNoOfCreditFacilityYear2 = totalNoOfCreditFacilityYear2;
    }

    public String getTotalAmtGrantedYear2() {
        return totalAmtGrantedYear2;
    }

    public void setTotalAmtGrantedYear2(String totalAmtGrantedYear2) {
        this.totalAmtGrantedYear2 = totalAmtGrantedYear2;
    }

    public String getTotalNoOfCreditFacilityYear3() {
        return totalNoOfCreditFacilityYear3;
    }

    public void setTotalNoOfCreditFacilityYear3(String totalNoOfCreditFacilityYear3) {
        this.totalNoOfCreditFacilityYear3 = totalNoOfCreditFacilityYear3;
    }

    public String getTotalAmtGrantedYear3() {
        return totalAmtGrantedYear3;
    }

    public void setTotalAmtGrantedYear3(String totalAmtGrantedYear3) {
        this.totalAmtGrantedYear3 = totalAmtGrantedYear3;
    }

    public String getTotalNoOfCreditFacilityYear4() {
        return totalNoOfCreditFacilityYear4;
    }

    public void setTotalNoOfCreditFacilityYear4(String totalNoOfCreditFacilityYear4) {
        this.totalNoOfCreditFacilityYear4 = totalNoOfCreditFacilityYear4;
    }

    public String getTotalAmtGrantedYear4() {
        return totalAmtGrantedYear4;
    }

    public void setTotalAmtGrantedYear4(String totalAmtGrantedYear4) {
        this.totalAmtGrantedYear4 = totalAmtGrantedYear4;
    }

    public String getTotalNoOfCreditFacilityYear5() {
        return totalNoOfCreditFacilityYear5;
    }

    public void setTotalNoOfCreditFacilityYear5(String totalNoOfCreditFacilityYear5) {
        this.totalNoOfCreditFacilityYear5 = totalNoOfCreditFacilityYear5;
    }

    public String getTotalAmtGrantedYear5() {
        return totalAmtGrantedYear5;
    }

    public void setTotalAmtGrantedYear5(String totalAmtGrantedYear5) {
        this.totalAmtGrantedYear5 = totalAmtGrantedYear5;
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
