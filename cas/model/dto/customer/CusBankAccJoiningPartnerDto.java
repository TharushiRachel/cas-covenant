package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.CusBankAccJoiningPartner;

import java.io.Serializable;

public class CusBankAccJoiningPartnerDto implements Serializable {

    private Integer cusBankAccJoiningPartnerID;

    private Integer customerBankDetailsID;

    private String customerFinacleID;

    private String customerNICNumber;

    private boolean primaryCustomer;

    private AppsConstants.Status status;

    public CusBankAccJoiningPartnerDto() {
    }
    public CusBankAccJoiningPartnerDto(CusBankAccJoiningPartner accJoiningPartner) {
        this.cusBankAccJoiningPartnerID = accJoiningPartner.getCusBankAccJoiningPartnerID();
        this.customerBankDetailsID = accJoiningPartner.getCustomerBankDetail().getCustomerBankDetailsID();
        this.customerFinacleID = accJoiningPartner.getCustomerFinacleID();
        this.customerNICNumber = accJoiningPartner.getCustomerNICNumber();
        this.primaryCustomer = accJoiningPartner.getPrimaryCustomer().getBoolVal();
        this.status = accJoiningPartner.getStatus();
    }

    public Integer getCusBankAccJoiningPartnerID() {
        return cusBankAccJoiningPartnerID;
    }

    public void setCusBankAccJoiningPartnerID(Integer cusBankAccJoiningPartnerID) {
        this.cusBankAccJoiningPartnerID = cusBankAccJoiningPartnerID;
    }

    public Integer getCustomerBankDetailsID() {
        return customerBankDetailsID;
    }

    public void setCustomerBankDetailsID(Integer customerBankDetailsID) {
        this.customerBankDetailsID = customerBankDetailsID;
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

    public boolean isPrimaryCustomer() {
        return primaryCustomer;
    }

    public void setPrimaryCustomer(boolean primaryCustomer) {
        this.primaryCustomer = primaryCustomer;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
