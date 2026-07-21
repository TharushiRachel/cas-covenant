package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CAS_CUSTOMER_IDENTIFICATION")
public class CASCustomerIdentification extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUS_IDENTIFICATION")
    @SequenceGenerator(name = "SEQ_T_CAS_CUS_IDENTIFICATION", sequenceName = "SEQ_T_CAS_CUS_IDENTIFICATION", allocationSize = 1)
    @Column(name = "CAS_CUS_IDENTIFICATION_ID")
    private Integer casCustomerIdentificationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

    @Enumerated(EnumType.STRING)
    @Column(name = "IDENTIFICATION_TYPE")
    private DomainConstants.CustomerIdentificationType identificationType;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCasCustomerIdentificationID() {
        return casCustomerIdentificationID;
    }

    public void setCasCustomerIdentificationID(Integer casCustomerIdentificationID) {
        this.casCustomerIdentificationID = casCustomerIdentificationID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
    }

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
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
