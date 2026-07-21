package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CAS_CUSTOMER_ADDRESS")
public class CASCustomerAddress extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUSTOMER_ADDRESS")
    @SequenceGenerator(name = "SEQ_T_CAS_CUSTOMER_ADDRESS", sequenceName = "SEQ_T_CAS_CUSTOMER_ADDRESS", allocationSize = 1)
    @Column(name = "CAS_CUSTOMER_ADDRESS_ID")
    private Integer casCustomerAddressID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

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

    public Integer getCasCustomerAddressID() {
        return casCustomerAddressID;
    }

    public void setCasCustomerAddressID(Integer casCustomerAddressID) {
        this.casCustomerAddressID = casCustomerAddressID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
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

    @Override
    public String toString() {
        return "CASCustomerAddress{" +
                "casCustomerAddressID=" + casCustomerAddressID +
                ", CASCustomer=" + CASCustomer +
                ", addressType='" + addressType + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                '}';
    }
}
