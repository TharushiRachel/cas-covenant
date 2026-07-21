package com.itechro.cas.model.domain.applicationform.applicationfromcustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.customer.CustomerTelephone;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_CUSTOMER_TELEPHONE")
public class AFCustomerTelephone extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_CUSTOMER_TELEPHONE")
    @SequenceGenerator(name = "SEQ_T_AF_CUSTOMER_TELEPHONE", sequenceName = "SEQ_T_AF_CUSTOMER_TELEPHONE", allocationSize = 1)
    @Column(name = "AF_CUSTOMER_TELEPHONE_ID")
    private Integer afCustomerTelephoneID;

    @Column(name = "CUSTOMER_TELEPHONE_ID")
    private Integer customerTelephoneID;

    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AF_CUSTOMER_ID")
    private AFCustomer afCustomer;

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public AFCustomerTelephone() {
    }

    public AFCustomerTelephone(CustomerTelephone customerTelephone) {
        this.customerTelephoneID = customerTelephone.getCustomerTelephoneID();
        this.customerID = customerTelephone.getCustomer().getCustomerID();
        this.contactNumber = customerTelephone.getContactNumber();
        this.description = customerTelephone.getDescription();
        this.status = customerTelephone.getStatus();
    }


    public Integer getAfCustomerTelephoneID() {
        return afCustomerTelephoneID;
    }

    public void setAfCustomerTelephoneID(Integer afCustomerTelephoneID) {
        this.afCustomerTelephoneID = afCustomerTelephoneID;
    }

    public Integer getCustomerTelephoneID() {
        return customerTelephoneID;
    }

    public void setCustomerTelephoneID(Integer customerTelephoneID) {
        this.customerTelephoneID = customerTelephoneID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public AFCustomer getAfCustomer() {
        return afCustomer;
    }

    public void setAfCustomer(AFCustomer afCustomer) {
        this.afCustomer = afCustomer;
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
}
