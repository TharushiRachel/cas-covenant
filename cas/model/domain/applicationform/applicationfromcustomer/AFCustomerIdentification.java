package com.itechro.cas.model.domain.applicationform.applicationfromcustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants.CustomerIdentificationType;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_CUSTOMER_IDENTIFICATION")
public class AFCustomerIdentification extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_CUS_IDENTIFICATION")
    @SequenceGenerator(name = "SEQ_T_AF_CUS_IDENTIFICATION", sequenceName = "SEQ_T_AF_CUS_IDENTIFICATION", allocationSize = 1)
    @Column(name = "AF_IDENTIFICATION_ID")
    private Integer afIdentificationID;

    @Column(name = "IDENTIFICATION_ID")
    private Integer identificationID;

    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AF_CUSTOMER_ID")
    private AFCustomer afCustomer;

    @Enumerated(EnumType.STRING)
    @Column(name = "IDENTIFICATION_TYPE")
    private CustomerIdentificationType identificationType;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getAfIdentificationID() {
        return afIdentificationID;
    }

    public void setAfIdentificationID(Integer afIdentificationID) {
        this.afIdentificationID = afIdentificationID;
    }

    public Integer getIdentificationID() {
        return identificationID;
    }

    public void setIdentificationID(Integer identificationID) {
        this.identificationID = identificationID;
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
}
