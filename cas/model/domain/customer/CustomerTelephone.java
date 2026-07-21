package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.dto.customer.CustomerTelephoneDTO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "T_CUSTOMER_TELEPHONE")
public class CustomerTelephone extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOMER_TELEPHONE")
    @SequenceGenerator(name = "SEQ_T_CUSTOMER_TELEPHONE", sequenceName = "SEQ_T_CUSTOMER_TELEPHONE", allocationSize = 1)
    @Column(name = "CUSTOMER_TELEPHONE_ID")
    private Integer customerTelephoneID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCustomerTelephoneID() {
        return customerTelephoneID;
    }

    public void setCustomerTelephoneID(Integer customerTelephoneID) {
        this.customerTelephoneID = customerTelephoneID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public boolean equalsDTO(CustomerTelephoneDTO customerTelephoneDTO) {
        if (customerTelephoneDTO == null) {
            return false;
        }

        if (StringUtils.isNotEmpty(customerTelephoneDTO.getContactNumber())
                && StringUtils.isNotEmpty(customerTelephoneDTO.getDescription())
        ) {
            return customerTelephoneDTO.getContactNumber().equals(this.getContactNumber())
                    && customerTelephoneDTO.getDescription().equals(this.getDescription());
        }

        if (StringUtils.isNotEmpty(customerTelephoneDTO.getContactNumber())) {
            return customerTelephoneDTO.getContactNumber().equals(this.getContactNumber());
        }

        return false;
    }
}
