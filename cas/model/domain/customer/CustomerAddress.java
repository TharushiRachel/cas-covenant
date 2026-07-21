package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.dto.customer.CustomerAddressDTO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "T_CUSTOMER_ADDRESS")
public class CustomerAddress extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOMER_ADDRESS")
    @SequenceGenerator(name = "SEQ_T_CUSTOMER_ADDRESS", sequenceName = "SEQ_T_CUSTOMER_ADDRESS", allocationSize = 1)
    @Column(name = "CUSTOMER_ADDRESS_ID")
    private Integer customerAddressID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

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

    public Integer getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Integer customerAddressID) {
        this.customerAddressID = customerAddressID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public boolean equalsDTO(CustomerAddressDTO customerAddressDTO) {
        if (customerAddressDTO == null) {
            return false;
        }

        if (StringUtils.isNotEmpty(customerAddressDTO.getAddress1())
                && StringUtils.isNotEmpty(customerAddressDTO.getAddress2())
                && StringUtils.isNotEmpty(customerAddressDTO.getCity()) && StringUtils.isNotEmpty(customerAddressDTO.getAddressType())) {
            return customerAddressDTO.getAddress1().equals(this.getAddress1())
                    && customerAddressDTO.getAddress2().equals(this.getAddress2())
                    && customerAddressDTO.getCity().equals(this.getCity())
                    && customerAddressDTO.getAddressType().equals(this.getAddressType());
        }

        if (StringUtils.isNotEmpty(customerAddressDTO.getAddress1())
                && StringUtils.isNotEmpty(customerAddressDTO.getAddress2())
                && StringUtils.isNotEmpty(customerAddressDTO.getCity())) {
            return customerAddressDTO.getAddress1().equals(this.getAddress1())
                    && customerAddressDTO.getAddress2().equals(this.getAddress2())
                    && customerAddressDTO.getCity().equals(this.getCity());
        }

        if (StringUtils.isNotEmpty(customerAddressDTO.getAddress1())
                && StringUtils.isNotEmpty(customerAddressDTO.getAddress2())) {
            return customerAddressDTO.getAddress1().equals(this.getAddress1())
                    && customerAddressDTO.getAddress2().equals(this.getAddress2());
        }

        if (StringUtils.isNotEmpty(customerAddressDTO.getAddress1())
                && StringUtils.isNotEmpty(customerAddressDTO.getCity())) {
            return customerAddressDTO.getAddress1().equals(this.getAddress1())
                    && customerAddressDTO.getCity().equals(this.getCity());
        }

        if (StringUtils.isNotEmpty(customerAddressDTO.getAddress1())) {
            return customerAddressDTO.getAddress1().equals(this.getAddress1());
        }

        return false;
    }
}
