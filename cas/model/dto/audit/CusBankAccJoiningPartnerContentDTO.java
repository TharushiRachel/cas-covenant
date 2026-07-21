package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CusBankAccJoiningPartnerContentDTO extends BaseContentDTO {

    @SerializedName("CB ACC JOIN PARTNER ID")
    private Integer cusBankAccJoiningPartnerID;

    @SerializedName("CUSTOMER BANK DETAILS ID")
    private Integer customerBankDetailID;

    @SerializedName("CUSTOMER FINANCIAL ID")
    private String customerFinacleID;

    @SerializedName("CUSTOMER NIC NUMBER")
    private String customerNICNumber;

    @SerializedName("CUSTOMER ID")
    private Integer customerID;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("PRIMARY CUSTOMER")
    private String primaryCustomer;

    @SerializedName("STATUS")
    private String status;

    public Integer getCusBankAccJoiningPartnerID() {
        return cusBankAccJoiningPartnerID;
    }

    public void setCusBankAccJoiningPartnerID(Integer cusBankAccJoiningPartnerID) {
        this.cusBankAccJoiningPartnerID = cusBankAccJoiningPartnerID;
    }

    public Integer getCustomerBankDetailID() {
        return customerBankDetailID;
    }

    public void setCustomerBankDetailID(Integer customerBankDetailID) {
        this.customerBankDetailID = customerBankDetailID;
    }

    public String getCustomerFinacleID() {
        return customerFinacleID;
    }

    public void setCustomerFinacleID(String customerFinacleID) {
        this.customerFinacleID = customerFinacleID;
    }

    public String getCustomerNICNumber() {
        return customerNICNumber;
    }

    public void setCustomerNICNumber(String customerNICNumber) {
        this.customerNICNumber = customerNICNumber;
    }

    public String getPrimaryCustomer() {
        return primaryCustomer;
    }

    public void setPrimaryCustomer(String primaryCustomer) {
        this.primaryCustomer = primaryCustomer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
