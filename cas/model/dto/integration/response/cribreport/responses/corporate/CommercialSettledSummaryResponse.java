package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialSettledSummaryResponse implements Serializable {

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Ownership")
    private String ownership;

    @JsonProperty("Total_no_of_credit_facilities_yr1")
    private String totalNoOfCreditFacilityYear1;

    @JsonProperty("Total_Amt_Granted_yr1")
    private String totalAmtGrantedYear1;

    @JsonProperty("Total_no_of_credit_facilities_yr2")
    private String totalNoOfCreditFacilityYear2;

    @JsonProperty("Total_Amt_Granted_yr2")
    private String totalAmtGrantedYear2;

    @JsonProperty("Total_no_of_credit_facilities_yr3")
    private String totalNoOfCreditFacilityYear3;

    @JsonProperty("Total_Amt_Granted_yr3")
    private String totalAmtGrantedYear3;

    @JsonProperty("Total_no_of_credit_facilities_yr4")
    private String totalNoOfCreditFacilityYear4;

    @JsonProperty("Total_Amt_Granted_yr4")
    private String totalAmtGrantedYear4;

    @JsonProperty("Total_no_of_credit_facilities_yr5")
    private String totalNoOfCreditFacilityYear5;

    @JsonProperty("Total_Amt_Granted_yr5")
    private String totalAmtGrantedYear5;

    @JsonProperty("RP")
    private String rp;

    @JsonProperty("STYR1")
    private String styr1;

    @JsonProperty("STYR2")
    private String styr2;

    @JsonProperty("STYR3")
    private String styr3;

    @JsonProperty("STYR4")
    private String styr4;

    @JsonProperty("STYR5")
    private String styr5;

    @JsonProperty("IsColoringNeeded")
    private String isColoringNeeded;

    @JsonProperty("Currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("Currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("Ownership")
    public String getOwnership() {
        return ownership;
    }

    @JsonProperty("Ownership")
    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr1")
    public String getTotalNoOfCreditFacilityYear1() {
        return totalNoOfCreditFacilityYear1;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr1")
    public void setTotalNoOfCreditFacilityYear1(String totalNoOfCreditFacilityYear1) {
        this.totalNoOfCreditFacilityYear1 = totalNoOfCreditFacilityYear1;
    }

    @JsonProperty("Total_Amt_Granted_yr1")
    public String getTotalAmtGrantedYear1() {
        return totalAmtGrantedYear1;
    }

    @JsonProperty("Total_Amt_Granted_yr1")
    public void setTotalAmtGrantedYear1(String totalAmtGrantedYear1) {
        this.totalAmtGrantedYear1 = totalAmtGrantedYear1;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr2")
    public String getTotalNoOfCreditFacilityYear2() {
        return totalNoOfCreditFacilityYear2;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr2")
    public void setTotalNoOfCreditFacilityYear2(String totalNoOfCreditFacilityYear2) {
        this.totalNoOfCreditFacilityYear2 = totalNoOfCreditFacilityYear2;
    }

    @JsonProperty("Total_Amt_Granted_yr2")
    public String getTotalAmtGrantedYear2() {
        return totalAmtGrantedYear2;
    }

    @JsonProperty("Total_Amt_Granted_yr2")
    public void setTotalAmtGrantedYear2(String totalAmtGrantedYear2) {
        this.totalAmtGrantedYear2 = totalAmtGrantedYear2;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr3")
    public String getTotalNoOfCreditFacilityYear3() {
        return totalNoOfCreditFacilityYear3;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr3")
    public void setTotalNoOfCreditFacilityYear3(String totalNoOfCreditFacilityYear3) {
        this.totalNoOfCreditFacilityYear3 = totalNoOfCreditFacilityYear3;
    }

    @JsonProperty("Total_Amt_Granted_yr3")
    public String getTotalAmtGrantedYear3() {
        return totalAmtGrantedYear3;
    }

    @JsonProperty("Total_Amt_Granted_yr3")
    public void setTotalAmtGrantedYear3(String totalAmtGrantedYear3) {
        this.totalAmtGrantedYear3 = totalAmtGrantedYear3;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr4")
    public String getTotalNoOfCreditFacilityYear4() {
        return totalNoOfCreditFacilityYear4;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr4")
    public void setTotalNoOfCreditFacilityYear4(String totalNoOfCreditFacilityYear4) {
        this.totalNoOfCreditFacilityYear4 = totalNoOfCreditFacilityYear4;
    }

    @JsonProperty("Total_Amt_Granted_yr4")
    public String getTotalAmtGrantedYear4() {
        return totalAmtGrantedYear4;
    }

    @JsonProperty("Total_Amt_Granted_yr4")
    public void setTotalAmtGrantedYear4(String totalAmtGrantedYear4) {
        this.totalAmtGrantedYear4 = totalAmtGrantedYear4;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr5")
    public String getTotalNoOfCreditFacilityYear5() {
        return totalNoOfCreditFacilityYear5;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr5")
    public void setTotalNoOfCreditFacilityYear5(String totalNoOfCreditFacilityYear5) {
        this.totalNoOfCreditFacilityYear5 = totalNoOfCreditFacilityYear5;
    }

    @JsonProperty("Total_Amt_Granted_yr5")
    public String getTotalAmtGrantedYear5() {
        return totalAmtGrantedYear5;
    }

    @JsonProperty("Total_Amt_Granted_yr5")
    public void setTotalAmtGrantedYear5(String totalAmtGrantedYear5) {
        this.totalAmtGrantedYear5 = totalAmtGrantedYear5;
    }

    @JsonProperty("RP")
    public String getRp() {
        return rp;
    }

    @JsonProperty("RP")
    public void setRp(String rp) {
        this.rp = rp;
    }

    @JsonProperty("STYR1")
    public String getStyr1() {
        return styr1;
    }

    @JsonProperty("STYR1")
    public void setStyr1(String styr1) {
        this.styr1 = styr1;
    }

    @JsonProperty("STYR2")
    public String getStyr2() {
        return styr2;
    }

    @JsonProperty("STYR2")
    public void setStyr2(String styr2) {
        this.styr2 = styr2;
    }

    @JsonProperty("STYR3")
    public String getStyr3() {
        return styr3;
    }

    @JsonProperty("STYR3")
    public void setStyr3(String styr3) {
        this.styr3 = styr3;
    }

    @JsonProperty("STYR4")
    public String getStyr4() {
        return styr4;
    }

    @JsonProperty("STYR4")
    public void setStyr4(String styr4) {
        this.styr4 = styr4;
    }

    @JsonProperty("STYR5")
    public String getStyr5() {
        return styr5;
    }

    @JsonProperty("STYR5")
    public void setStyr5(String styr5) {
        this.styr5 = styr5;
    }

    @JsonProperty("IsColoringNeeded")
    public String getIsColoringNeeded() {
        return isColoringNeeded;
    }

    @JsonProperty("IsColoringNeeded")
    public void setIsColoringNeeded(String isColoringNeeded) {
        this.isColoringNeeded = isColoringNeeded;
    }
}
