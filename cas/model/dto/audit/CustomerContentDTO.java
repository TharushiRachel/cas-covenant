package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CustomerContentDTO extends BaseContentDTO {

    @SerializedName("CUSTOMER ID")
    private Integer customerID;

    @SerializedName("CUSTOMER FINANCIAL ID")
    private String customerFinancialID;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("CUSTOMER TITLE")
    private String customerTitle;

    @SerializedName("EMAIL ADDRESS")
    private String emailAddress;

    @SerializedName("SECONDARY EMAIL ADDRESS")
    private String secondaryEmailAddress;

    @SerializedName("IDENTIFICATION TYPE")
    private String identificationType;

    @SerializedName("DATE OF BIRTH")
    private String dateOfBirth;

    @SerializedName("CIVIL STATUS")
    private String civilStatus;

    @SerializedName("STATUS")
    private String status;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerFinancialID() {
        return customerFinancialID;
    }

    public void setCustomerFinancialID(String customerFinancialID) {
        this.customerFinancialID = customerFinancialID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getSecondaryEmailAddress() {
        return secondaryEmailAddress;
    }

    public void setSecondaryEmailAddress(String secondaryEmailAddress) {
        this.secondaryEmailAddress = secondaryEmailAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
