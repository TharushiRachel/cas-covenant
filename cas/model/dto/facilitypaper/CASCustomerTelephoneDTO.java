package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.CASCustomerTelephone;

import java.io.Serializable;

public class CASCustomerTelephoneDTO implements Serializable {

    private Integer fpCustomerTelephoneID;

    private Integer casCustomerID;

    private String contactNumber;

    private String description;

    private AppsConstants.Status status;

    public CASCustomerTelephoneDTO() {
    }

    public CASCustomerTelephoneDTO(CASCustomerTelephone CASCustomerTelephone) {
        this.fpCustomerTelephoneID = CASCustomerTelephone.getCasCustomerTelephoneID();
        this.casCustomerID = CASCustomerTelephone.getCASCustomer().getCasCustomerID();
        this.contactNumber = CASCustomerTelephone.getContactNumber();
        this.description = CASCustomerTelephone.getDescription();
        this.status = CASCustomerTelephone.getStatus();
    }

    public Integer getFpCustomerTelephoneID() {
        return fpCustomerTelephoneID;
    }

    public void setFpCustomerTelephoneID(Integer fpCustomerTelephoneID) {
        this.fpCustomerTelephoneID = fpCustomerTelephoneID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CASCustomerTelephoneDTO{" +
                "fpCustomerTelephoneID=" + fpCustomerTelephoneID +
                ", casCustomerID=" + casCustomerID +
                ", contactNumber='" + contactNumber + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
