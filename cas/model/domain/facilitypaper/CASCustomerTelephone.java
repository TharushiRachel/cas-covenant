package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CAS_CUSTOMER_TELEPHONE")
public class CASCustomerTelephone extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUSTOMER_TELEPHONE")
    @SequenceGenerator(name = "SEQ_T_CAS_CUSTOMER_TELEPHONE", sequenceName = "SEQ_T_CAS_CUSTOMER_TELEPHONE", allocationSize = 1)
    @Column(name = "CAS_CUSTOMER_TELEPHONE_ID")
    private Integer casCustomerTelephoneID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCasCustomerTelephoneID() {
        return casCustomerTelephoneID;
    }

    public void setCasCustomerTelephoneID(Integer casCustomerTelephoneID) {
        this.casCustomerTelephoneID = casCustomerTelephoneID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
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
        return "CASCustomerTelephone{" +
                "casCustomerTelephoneID=" + casCustomerTelephoneID +
                ", CASCustomer=" + CASCustomer +
                ", contactNumber='" + contactNumber + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
