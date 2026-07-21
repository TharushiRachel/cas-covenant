package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsSettledSummaryResponse implements Serializable {

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Ownership")
    private String ownership;

    @JsonProperty("Total_no_of_credit_facilities_yr1")
    private String totalNoOfCreditFacilitiesYr1;

    @JsonProperty("Total_Amt_Granted_yr1")
    private String totalAmountGrantedYear1;

    @JsonProperty("Total_no_of_credit_facilities_yr2")
    private String totalNoOfCreditFacilitiesYr2;

    @JsonProperty("Total_Amt_Granted_yr2")
    private String totalAmountGrantedYear2;

    @JsonProperty("Total_no_of_credit_facilities_yr3")
    private String totalNoOfCreditFacilitiesYr3;

    @JsonProperty("Total_Amt_Granted_yr3")
    private String totalAmountGrantedYear3;

    @JsonProperty("Total_no_of_credit_facilities_yr4")
    private String totalNoOfCreditFacilitiesYr4;

    @JsonProperty("Total_Amt_Granted_yr4")
    private String totalAmountGrantedYear4;

    @JsonProperty("Total_no_of_credit_facilities_yr5")
    private String totalNoOfCreditFacilitiesYr5;

    @JsonProperty("Total_Amt_Granted_yr5")
    private String totalAmountGrantedYear5;

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
    public String getTotalNoOfCreditFacilitiesYr1() {
        return totalNoOfCreditFacilitiesYr1;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr1")
    public void setTotalNoOfCreditFacilitiesYr1(String totalNoOfCreditFacilitiesYr1) {
        this.totalNoOfCreditFacilitiesYr1 = totalNoOfCreditFacilitiesYr1;
    }

    @JsonProperty("Total_Amt_Granted_yr1")
    public String getTotalAmountGrantedYear1() {
        return totalAmountGrantedYear1;
    }

    @JsonProperty("Total_Amt_Granted_yr1")
    public void setTotalAmountGrantedYear1(String totalAmountGrantedYear1) {
        this.totalAmountGrantedYear1 = totalAmountGrantedYear1;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr2")
    public String getTotalNoOfCreditFacilitiesYr2() {
        return totalNoOfCreditFacilitiesYr2;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr2")
    public void setTotalNoOfCreditFacilitiesYr2(String totalNoOfCreditFacilitiesYr2) {
        this.totalNoOfCreditFacilitiesYr2 = totalNoOfCreditFacilitiesYr2;
    }

    @JsonProperty("Total_Amt_Granted_yr2")
    public String getTotalAmountGrantedYear2() {
        return totalAmountGrantedYear2;
    }

    @JsonProperty("Total_Amt_Granted_yr2")
    public void setTotalAmountGrantedYear2(String totalAmountGrantedYear2) {
        this.totalAmountGrantedYear2 = totalAmountGrantedYear2;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr3")
    public String getTotalNoOfCreditFacilitiesYr3() {
        return totalNoOfCreditFacilitiesYr3;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr3")
    public void setTotalNoOfCreditFacilitiesYr3(String totalNoOfCreditFacilitiesYr3) {
        this.totalNoOfCreditFacilitiesYr3 = totalNoOfCreditFacilitiesYr3;
    }

    @JsonProperty("Total_Amt_Granted_yr3")
    public String getTotalAmountGrantedYear3() {
        return totalAmountGrantedYear3;
    }

    @JsonProperty("Total_Amt_Granted_yr3")
    public void setTotalAmountGrantedYear3(String totalAmountGrantedYear3) {
        this.totalAmountGrantedYear3 = totalAmountGrantedYear3;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr4")
    public String getTotalNoOfCreditFacilitiesYr4() {
        return totalNoOfCreditFacilitiesYr4;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr4")
    public void setTotalNoOfCreditFacilitiesYr4(String totalNoOfCreditFacilitiesYr4) {
        this.totalNoOfCreditFacilitiesYr4 = totalNoOfCreditFacilitiesYr4;
    }

    @JsonProperty("Total_Amt_Granted_yr4")
    public String getTotalAmountGrantedYear4() {
        return totalAmountGrantedYear4;
    }

    @JsonProperty("Total_Amt_Granted_yr4")
    public void setTotalAmountGrantedYear4(String totalAmountGrantedYear4) {
        this.totalAmountGrantedYear4 = totalAmountGrantedYear4;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr5")
    public String getTotalNoOfCreditFacilitiesYr5() {
        return totalNoOfCreditFacilitiesYr5;
    }

    @JsonProperty("Total_no_of_credit_facilities_yr5")
    public void setTotalNoOfCreditFacilitiesYr5(String totalNoOfCreditFacilitiesYr5) {
        this.totalNoOfCreditFacilitiesYr5 = totalNoOfCreditFacilitiesYr5;
    }

    @JsonProperty("Total_Amt_Granted_yr5")
    public String getTotalAmountGrantedYear5() {
        return totalAmountGrantedYear5;
    }

    @JsonProperty("Total_Amt_Granted_yr5")
    public void setTotalAmountGrantedYear5(String totalAmountGrantedYear5) {
        this.totalAmountGrantedYear5 = totalAmountGrantedYear5;
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
