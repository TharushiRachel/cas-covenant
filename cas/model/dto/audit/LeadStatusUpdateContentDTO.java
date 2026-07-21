package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class LeadStatusUpdateContentDTO extends BaseContentDTO {

    @SerializedName("LEAD ID")
    private Integer leadID;

    @SerializedName("LEAD REF NUMBER")
    private String leadRefNumber;

    @SerializedName("LEAD NAME")
    private String leadName;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("ASSIGN USER NAME")
    private String assignUserName;

    @SerializedName("LEAD STATUS")
    private String leadStatus;


    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAssignUserName() {
        return assignUserName;
    }

    public void setAssignUserName(String assignUserName) {
        this.assignUserName = assignUserName;
    }

    public String getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }

    public String getLeadRefNumber() {
        return leadRefNumber;
    }

    public void setLeadRefNumber(String leadRefNumber) {
        this.leadRefNumber = leadRefNumber;
    }
}
