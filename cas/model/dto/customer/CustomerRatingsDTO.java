package com.itechro.cas.model.dto.customer;

import com.itechro.cas.model.domain.customer.CustomerRatings;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class CustomerRatingsDTO implements Serializable {

    private Integer customerRatingsID;

    private Integer customerID;

    private Integer casCustomerID;

    private Double existingFacilitiesROA;

    private Double proposedFacilitiesROA;

    private String riskGrading;

    private Double riskScore;

    private Double ramScore;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;


    public CustomerRatingsDTO(CustomerRatings customerRatings) {
        this.customerRatingsID = customerRatings.getCustomerRatingsID();
        this.customerID = customerRatings.getCustomerID() ;
        this.casCustomerID = customerRatings.getCASCustomer().getCasCustomerID();
        this.existingFacilitiesROA = customerRatings.getExistingFacilitiesROA() ;
        this.proposedFacilitiesROA = customerRatings.getProposedFacilitiesROA() ;
        this.riskGrading = customerRatings.getRiskGrading();
        this.riskScore = customerRatings.getRiskScore() ;
        this.ramScore = customerRatings.getRamScore();
        this.modifiedBy = customerRatings.getModifiedBy() ;
        this.createdBy = customerRatings.getCreatedBy() ;
        if (customerRatings.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateOnly(customerRatings.getCreatedDate());
        }
        if (customerRatings.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(customerRatings.getModifiedDate());
        }
    }
    public CustomerRatingsDTO() {

    }

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

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDate(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getCustomerRatingsID() {
        return customerRatingsID;
    }

    public void setCustomerRatingsID(Integer customerRatingsID) {
        this.customerRatingsID = customerRatingsID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    @Override
    public String toString() {
        return "CustomerRatingsDTO{" +
                " customerRatingsID=" + customerRatingsID +
                ", customerID=" + customerID +
                ", existingFacilitiesROA='" + existingFacilitiesROA + '\'' +
                ", proposedFacilitiesROA=" + proposedFacilitiesROA +
                ", riskGrading='" + riskGrading + '\'' +
                ", riskScore='" + riskScore + '\'' +
                ", ramScore='" + ramScore + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr=" + modifiedDateStr +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
