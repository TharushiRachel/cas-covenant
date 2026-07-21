package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CustomerRatingsContentDTO extends BaseContentDTO{

    @SerializedName("CUSTOMER ID")
    private Integer customerID;

    @SerializedName("EXISTING FACILITIES ROA")
    private Double existingFacilitiesROA;

    @SerializedName("PROPOSED FACILITIES ROA")
    private Double proposedFacilitiesROA;

    @SerializedName("RISK GRADING")
    private String riskGrading;

    @SerializedName("RISK SCORE")
    private Double riskScore;

    @SerializedName("RAM SCORE")
    private Double ramScore;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Double getExistingFacilitiesROA() {
        return existingFacilitiesROA;
    }

    public void setExistingFacilitiesROA(Double existingFacilitiesROA) {
        this.existingFacilitiesROA = existingFacilitiesROA;
    }

    public Double getProposedFacilitiesROA() {
        return proposedFacilitiesROA;
    }

    public void setProposedFacilitiesROA(Double proposedFacilitiesROA) {
        this.proposedFacilitiesROA = proposedFacilitiesROA;
    }

    public String getRiskGrading() {
        return riskGrading;
    }

    public void setRiskGrading(String riskGrading) {
        this.riskGrading = riskGrading;
    }

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public Double getRamScore() {
        return ramScore;
    }

    public void setRamScore(Double ramScore) {
        this.ramScore = ramScore;
    }
}
