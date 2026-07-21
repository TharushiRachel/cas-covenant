package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.dto.customer.CustomerAddressDTO;
import com.itechro.cas.model.dto.customer.CustomerBankDetailsDTO;
import com.itechro.cas.model.dto.customer.CustomerIdentificationDTO;
import com.itechro.cas.model.dto.customer.CustomerTelephoneDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_CUSTOMER")
public class Customer extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOMER")
    @SequenceGenerator(name = "SEQ_T_CUSTOMER", sequenceName = "SEQ_T_CUSTOMER", allocationSize = 1)
    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @Column(name = "CUSTOMER_FINANCIAL_ID")
    private String customerFinancialID;

    @Enumerated(EnumType.STRING)
    @Column(name = "TITLE")
    private DomainConstants.Title title;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customer")
    private Set<CustomerAddress> customerAddresses;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customer")
    private Set<CustomerTelephone> customerTelephones;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customer")
    private Set<CustomerIdentification> customerIdentifications;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customer")
    private Set<CustomerBankDetail> customerBankDetails;

//    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customer")
//    private Set<CustomerCovenant> customerCovenants;

//    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customer")
//    private Set<ApplicationLevelCovenant> applicationLevelCovenantSet;

//    public Set<ApplicationLevelCovenant> getApplicationLevelCovenantSet() {
//        return applicationLevelCovenantSet;
//    }
//
//    public void setApplicationLevelCovenantSet(Set<ApplicationLevelCovenant> applicationLevelCovenantSet) {
//        this.applicationLevelCovenantSet = applicationLevelCovenantSet;
//    }

//    public void setApplicationLevelCovenantSet(Set<ApplicationLevelCovenant> applicationLevelCovenantSet) {
//        this.applicationLevelCovenantSet = applicationLevelCovenantSet;
//    }

//    public Set<CustomerCovenant> getCustomerCovenants() {
//        return customerCovenants;
//    }
//
//    public void setCustomerCovenants(Set<CustomerCovenant> customerCovenants) {
//        this.customerCovenants = customerCovenants;
//    }

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

    public Set<CustomerAddress> getCustomerAddresses() {
        if (customerAddresses == null) {
            customerAddresses = new HashSet<>();
        }
        return customerAddresses;
    }

    public void setCustomerAddresses(Set<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    public void addCustomerAddress(CustomerAddress customerAddress) {
        customerAddress.setCustomer(this);
        this.getCustomerAddresses().add(customerAddress);
    }

    public CustomerAddress getCustomerAddressByID(Integer customerAddressID) {
        return this.getCustomerAddresses().stream().
                filter(customerAddress -> {
                    return customerAddressID.equals(customerAddress.getCustomerAddressID());
                }).findFirst().orElse(null);
    }

    public Set<CustomerTelephone> getCustomerTelephones() {
        if (customerTelephones == null) {
            customerTelephones = new HashSet<>();
        }
        return customerTelephones;
    }

    public void setCustomerTelephones(Set<CustomerTelephone> customerTelephones) {
        this.customerTelephones = customerTelephones;
    }

    public void addCustomerTelephone(CustomerTelephone customerTelephone) {
        customerTelephone.setCustomer(this);
        this.getCustomerTelephones().add(customerTelephone);
    }

    public CustomerTelephone getCustomerTelephoneByID(Integer customerTelephoneID) {
        return this.getCustomerTelephones().stream().
                filter(customerTelephone -> {
                    return customerTelephoneID.equals(customerTelephone.getCustomerTelephoneID());
                }).findFirst().orElse(null);
    }

    public Set<CustomerIdentification> getCustomerIdentifications() {
        if (customerIdentifications == null) {
            customerIdentifications = new HashSet<>();
        }
        return customerIdentifications;
    }

    public void setCustomerIdentifications(Set<CustomerIdentification> customerIdentifications) {
        this.customerIdentifications = customerIdentifications;
    }

    public void addCustomerIdentification(CustomerIdentification customerIdentification) {
        customerIdentification.setCustomer(this);
        this.getCustomerIdentifications().add(customerIdentification);
    }

    public CustomerIdentification getCustomerIdentificationByID(Integer identificationID) {
        return this.getCustomerIdentifications().stream().
                filter(customerIdentification -> {
                    return identificationID.equals(customerIdentification.getIdentificationID());
                }).findFirst().orElse(null);
    }

    public Set<CustomerBankDetail> getCustomerBankDetails() {
        if (customerBankDetails == null) {
            customerBankDetails = new HashSet<>();
        }
        return customerBankDetails;
    }

    public void setCustomerBankDetails(Set<CustomerBankDetail> customerBankDetails) {
        this.customerBankDetails = customerBankDetails;
    }

    public void addCustomerBankDetails(CustomerBankDetail customerBankDetail) {
        customerBankDetail.setCustomer(this);
        this.getCustomerBankDetails().add(customerBankDetail);
    }

    public CustomerBankDetail getCustomerBankDetailsByID(Integer bankDetailsID) {
        return this.getCustomerBankDetails().stream().
                filter(customerBankDetails -> {
                    return bankDetailsID.equals(customerBankDetails.getCustomerBankDetailsID());
                }).findFirst().orElse(null);
    }

    public void removeAddress(CustomerAddress customerAddress) {
        if (customerAddress != null) {
            this.getCustomerAddresses().remove(customerAddress);
        }
    }

    public void removeTelephones(CustomerTelephone customerTelephone) {
        if (customerTelephone != null) {
            this.getCustomerTelephones().remove(customerTelephone);
        }
    }

    public void removeCustomerIdentification(CustomerIdentification customerIdentification) {
        if (customerIdentification != null) {
            this.getCustomerIdentifications().remove(customerIdentification);
        }
    }

    public void removeCustomerBankDetail(CustomerBankDetail customerBankDetail) {
        if (customerBankDetail != null) {
            this.getCustomerBankDetails().remove(customerBankDetail);
        }
    }

    public CustomerAddress getCustomerAddressByDTO(CustomerAddressDTO customerAddressDTO) {
        return this.getCustomerAddresses().stream().
                filter(customerAddress -> {
                    return customerAddress.equalsDTO(customerAddressDTO);
                }).findFirst().orElse(null);
    }

    public CustomerTelephone getCustomerTelephoneByDTO(CustomerTelephoneDTO customerTelephoneDTO) {
        return this.getCustomerTelephones().stream().
                filter(customerTelephone -> {
                    return customerTelephone.equalsDTO(customerTelephoneDTO);
                }).findFirst().orElse(null);
    }

    public CustomerIdentification getCustomerIdentificationByDTO(CustomerIdentificationDTO customerIdentificationDTO) {
        return this.getCustomerIdentifications().stream().
                filter(customerIdentification -> {
                    return customerIdentification.equalsDTO(customerIdentificationDTO);
                }).findFirst().orElse(null);
    }

    public CustomerBankDetail getCustomerBankDetailsByDTO(CustomerBankDetailsDTO bankDetailsDTO) {
        return this.getCustomerBankDetails().stream().
                filter(customerBankDetail -> {
                    return customerBankDetail.equalsDTO(bankDetailsDTO);
                }).findFirst().orElse(null);
    }

    public DomainConstants.Title getTitle() {
        return title;
    }

    public void setTitle(DomainConstants.Title title) {
        this.title = title;
    }

//    public Set<CovAppBasicInfo> getCoveringApprovalSet() {
//        return coveringApprovalSet;
//    }
//
//    public void setCoveringApprovalSet(Set<CovAppBasicInfo> coveringApprovalSet) {
//        this.coveringApprovalSet = coveringApprovalSet;
//    }
}
