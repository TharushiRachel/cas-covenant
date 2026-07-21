package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants.CustomerIdentificationType;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.dto.customer.CustomerIdentificationDTO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "T_CUSTOMER_IDENTIFICATION")
public class CustomerIdentification extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOMER_IDENTIFICATION")
    @SequenceGenerator(name = "SEQ_T_CUSTOMER_IDENTIFICATION", sequenceName = "SEQ_T_CUSTOMER_IDENTIFICATION", allocationSize = 1)
    @Column(name = "IDENTIFICATION_ID")
    private Integer identificationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "IDENTIFICATION_TYPE")
    private CustomerIdentificationType identificationType;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getIdentificationID() {
        return identificationID;
    }

    public void setIdentificationID(Integer identificationID) {
        this.identificationID = identificationID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public boolean equalsDTO(CustomerIdentificationDTO customerIdentificationDTO) {
        if (customerIdentificationDTO == null) {
            return false;
        }

        if (StringUtils.isNotEmpty(customerIdentificationDTO.getIdentificationNumber())) {
            return customerIdentificationDTO.getIdentificationNumber().equals(this.getIdentificationNumber());
        }

        return false;
    }
}
