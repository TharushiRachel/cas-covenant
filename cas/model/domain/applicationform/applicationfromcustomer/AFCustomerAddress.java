package com.itechro.cas.model.domain.applicationform.applicationfromcustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_CUSTOMER_ADDRESS")
public class AFCustomerAddress extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_CUSTOMER_ADDRESS")
    @SequenceGenerator(name = "SEQ_T_AF_CUSTOMER_ADDRESS", sequenceName = "SEQ_T_AF_CUSTOMER_ADDRESS", allocationSize = 1)
    @Column(name = "AF_CUSTOMER_ADDRESS_ID")
    private Integer afCustomerAddressID;

    @Column(name = "CUSTOMER_ADDRESS_ID")
    private Integer customerAddressID;

    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AF_CUSTOMER_ID")
    private AFCustomer afCustomer;

    @Column(name = "ADDRESS_TYPE")
    private String addressType;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "CITY")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getAfCustomerAddressID() {
        return afCustomerAddressID;
    }

    public void setAfCustomerAddressID(Integer afCustomerAddressID) {
        this.afCustomerAddressID = afCustomerAddressID;
    }

    public Integer getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Integer customerAddressID) {
        this.customerAddressID = customerAddressID;
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

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
