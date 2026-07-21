package com.itechro.cas.model.domain.applicationform.applicationfromcustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "T_AF_CUSTOMER")
public class AFCustomer extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_CUSTOMER")
    @SequenceGenerator(name = "SEQ_T_AF_CUSTOMER", sequenceName = "SEQ_T_AF_CUSTOMER", allocationSize = 1)
    @Column(name = "AF_CUSTOMER_ID")
    private Integer afCustomerID;

    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @Column(name = "CUSTOMER_FINANCIAL_ID")
    private String customerFinancialID;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "SECONDARY_EMAIL_ADDRESS")
    private String secondaryEmailAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "CIVIL_STATUS")
    private String civilStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afCustomer")
    private Set<AFCustomerAddress> afCustomerAddresses;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afCustomer")
    private Set<AFCustomerTelephone> afCustomerTelephones;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afCustomer")
    private Set<AFCustomerIdentification> afCustomerIdentifications;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afCustomer")
    private Set<AFCustomerBankDetail> afCustomerBankDetails;

    public Integer getAfCustomerID() {
        return afCustomerID;
    }

    public void setAfCustomerID(Integer afCustomerID) {
        this.afCustomerID = afCustomerID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerFinancialID() {
        return customerFinancialID;
    }

    public void setCustomerFinancialID(String customerFinancialID) {
        this.customerFinancialID = customerFinancialID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSecondaryEmailAddress() {
        return secondaryEmailAddress;
    }

    public void setSecondaryEmailAddress(String secondaryEmailAddress) {
        this.secondaryEmailAddress = secondaryEmailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Set<AFCustomerAddress> getAfCustomerAddresses() {
        if (afCustomerAddresses == null) {
            this.afCustomerAddresses = new HashSet<>();
        }
        return afCustomerAddresses;
    }

    public void setAfCustomerAddresses(Set<AFCustomerAddress> afCustomerAddresses) {
        this.afCustomerAddresses = afCustomerAddresses;
    }

    public void addAfCustomerAddress(AFCustomerAddress afCustomerAddress) {
        afCustomerAddress.setAfCustomer(this);
        this.getAfCustomerAddresses().add(afCustomerAddress);
    }

    public List<AFCustomerAddress> getOrderedAFCustomerAddress() {
        return getAfCustomerAddresses().stream().sorted(new Comparator<AFCustomerAddress>() {
            @Override
            public int compare(AFCustomerAddress o1, AFCustomerAddress o2) {
                return o1.getAfCustomerAddressID().compareTo(o2.getAfCustomerAddressID());
            }
        }).collect(Collectors.toList());
    }

    public Set<AFCustomerTelephone> getAfCustomerTelephones() {
        if (afCustomerTelephones == null) {
            this.afCustomerTelephones = new HashSet<>();
        }
        return afCustomerTelephones;
    }

    public void setAfCustomerTelephones(Set<AFCustomerTelephone> afCustomerTelephones) {
        this.afCustomerTelephones = afCustomerTelephones;
    }


    public List<AFCustomerTelephone> getOrderedAFCustomerTelephones() {
        return getAfCustomerTelephones().stream().sorted(new Comparator<AFCustomerTelephone>() {
            @Override
            public int compare(AFCustomerTelephone o1, AFCustomerTelephone o2) {
                return o1.getAfCustomerTelephoneID().compareTo(o2.getAfCustomerTelephoneID());
            }
        }).collect(Collectors.toList());
    }

    public void addAfCustomerTelephone(AFCustomerTelephone afCustomerTelephone) {
        afCustomerTelephone.setAfCustomer(this);
        this.getAfCustomerTelephones().add(afCustomerTelephone);
    }

    public Set<AFCustomerIdentification> getAfCustomerIdentifications() {
        if (afCustomerIdentifications == null) {
            this.afCustomerIdentifications = new HashSet<>();
        }
        return afCustomerIdentifications;
    }


    public void setAfCustomerIdentifications(Set<AFCustomerIdentification> afCustomerIdentifications) {
        this.afCustomerIdentifications = afCustomerIdentifications;
    }

    public void addAfCustomerIdentifications(AFCustomerIdentification afCustomerIdentification) {
        afCustomerIdentification.setAfCustomer(this);
        this.getAfCustomerIdentifications().add(afCustomerIdentification);
    }


    public List<AFCustomerIdentification> getOrderedAFCustomerIdentification() {
        return getAfCustomerIdentifications().stream().sorted(new Comparator<AFCustomerIdentification>() {
            @Override
            public int compare(AFCustomerIdentification o1, AFCustomerIdentification o2) {
                return o1.getAfIdentificationID().compareTo(o2.getAfIdentificationID());
            }
        }).collect(Collectors.toList());
    }


    public Set<AFCustomerBankDetail> getAfCustomerBankDetails() {
        if (afCustomerBankDetails == null) {
            this.afCustomerBankDetails = new HashSet<>();
        }
        return afCustomerBankDetails;
    }

    public void setAfCustomerBankDetails(Set<AFCustomerBankDetail> afCustomerBankDetails) {
        this.afCustomerBankDetails = afCustomerBankDetails;
    }

    public void addAfCustomerBankDetails(AFCustomerBankDetail afCustomerBankDetail) {
        afCustomerBankDetail.setAfCustomer(this);
        this.getAfCustomerBankDetails().add(afCustomerBankDetail);
    }

    public List<AFCustomerBankDetail> getOrderedAFCustomerBankDetails() {
        return getAfCustomerBankDetails().stream().sorted(new Comparator<AFCustomerBankDetail>() {
            @Override
            public int compare(AFCustomerBankDetail o1, AFCustomerBankDetail o2) {
                return o1.getAfCustomerBankDetailsID().compareTo(o2.getAfCustomerBankDetailsID());
            }
        }).collect(Collectors.toList());
    }
}
