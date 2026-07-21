package com.itechro.cas.service.applicationform.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.*;
import com.itechro.cas.model.domain.customer.*;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AFCustomerBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(AFCustomerBuilder.class);

    private CredentialsDTO credentialsDTO;

    private Date date;

    private Customer customer;

    private AFCustomer afCustomer;

    public AFCustomerBuilder(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public AFCustomerBuilder buildInitialAFCustomer() throws AppsException {
        this.afCustomer = new AFCustomer();
        this.afCustomer.setCustomerID(customer.getCustomerID());
        this.afCustomer.setCustomerName(customer.getCustomerName());
        this.afCustomer.setCustomerFinancialID(customer.getCustomerFinancialID());
        this.afCustomer.setEmailAddress(customer.getEmailAddress());
        this.afCustomer.setSecondaryEmailAddress(customer.getSecondaryEmailAddress());
        this.afCustomer.setDateOfBirth(customer.getDateOfBirth());
        this.afCustomer.setCivilStatus(customer.getCivilStatus());
        this.afCustomer.setStatus(AppsConstants.Status.ACT);
        this.afCustomer.setCreatedBy(credentialsDTO.getUserName());
        this.afCustomer.setCreatedDate(date);
        LOG.info("END : Application Form Customer Initiated");
        return this;
    }

    public AFCustomerBuilder buildAfCustomerAddress() {

        for (CustomerAddress customerAddress : customer.getCustomerAddresses()) {
            if (customerAddress.getStatus() == AppsConstants.Status.ACT) {
                AFCustomerAddress afCustomerAddress = new AFCustomerAddress();
                afCustomerAddress.setCustomerAddressID(customerAddress.getCustomerAddressID());
                afCustomerAddress.setCustomerID(customerAddress.getCustomer().getCustomerID());
                afCustomerAddress.setAddressType(customerAddress.getAddressType());
                afCustomerAddress.setAddress1(customerAddress.getAddress1());
                afCustomerAddress.setAddress2(customerAddress.getAddress2());
                afCustomerAddress.setCity(afCustomerAddress.getCity());
                afCustomerAddress.setStatus(customerAddress.getStatus());
                afCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
                afCustomerAddress.setCreatedDate(date);
                afCustomer.addAfCustomerAddress(afCustomerAddress);
            }
        }
        LOG.info("END : Application Form Customer Addresses Added");
        return this;
    }

    public AFCustomerBuilder buildAfCustomerTelephones() {

        for (CustomerTelephone customerTelephone : customer.getCustomerTelephones()) {
            if (customerTelephone.getStatus() == AppsConstants.Status.ACT) {
                AFCustomerTelephone afCustomerTelephone = new AFCustomerTelephone();
                afCustomerTelephone.setCustomerTelephoneID(customerTelephone.getCustomerTelephoneID());
                afCustomerTelephone.setCustomerID(customerTelephone.getCustomer().getCustomerID());
                afCustomerTelephone.setContactNumber(customerTelephone.getContactNumber());
                afCustomerTelephone.setDescription(customerTelephone.getDescription());
                afCustomerTelephone.setStatus(customerTelephone.getStatus());
                afCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                afCustomerTelephone.setCreatedDate(date);
                afCustomer.addAfCustomerTelephone(afCustomerTelephone);
            }
        }
        LOG.info("END : Application Form Customer Telephone Added");
        return this;
    }

    public AFCustomerBuilder buildAFCustomerIdentifications() {

        for (CustomerIdentification customerIdentification : customer.getCustomerIdentifications()) {
            if (customerIdentification.getStatus() == AppsConstants.Status.ACT) {
                AFCustomerIdentification afCustomerIdentification = new AFCustomerIdentification();
                afCustomerIdentification.setIdentificationNumber(customerIdentification.getIdentificationNumber());
                afCustomerIdentification.setIdentificationType(customerIdentification.getIdentificationType());
                afCustomerIdentification.setCustomerID(customerIdentification.getCustomer().getCustomerID());
                afCustomerIdentification.setIdentificationID(customerIdentification.getIdentificationID());
                afCustomerIdentification.setStatus(customerIdentification.getStatus());
                afCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                afCustomerIdentification.setCreatedDate(date);
                afCustomer.addAfCustomerIdentifications(afCustomerIdentification);
            }
        }
        LOG.info("END : Application Form Customer Identification Details Added");
        return this;
    }


    public AFCustomerBuilder buildAFBankDetails() {

        for (CustomerBankDetail bankDetail : customer.getCustomerBankDetails()) {
            if (bankDetail.getStatus() == AppsConstants.Status.ACT) {
                AFCustomerBankDetail afCustomerBankDetail = new AFCustomerBankDetail();
                afCustomerBankDetail.setCustomerBankDetailsID(bankDetail.getCustomerBankDetailsID());
                afCustomerBankDetail.setCustomerID(bankDetail.getCustomer().getCustomerID());
                afCustomerBankDetail.setBankAccountNumber(bankDetail.getBankAccountNumber());
                afCustomerBankDetail.setBankAccountType(bankDetail.getBankAccountType());
                afCustomerBankDetail.setBranchCode(bankDetail.getBranchCode());
                afCustomerBankDetail.setAccountCLSFlag(bankDetail.getAccountCLSFlag());
                afCustomerBankDetail.setAccSince(bankDetail.getAccSince());
                afCustomerBankDetail.setSchmCode(bankDetail.getSchmCode());
                afCustomerBankDetail.setSchmType(bankDetail.getSchmType());
                afCustomerBankDetail.setAccountCurrencyCode(bankDetail.getAccountCurrencyCode());
                afCustomerBankDetail.setAccountStatus(bankDetail.getAccountStatus());
                afCustomerBankDetail.setStatus(bankDetail.getStatus());
                afCustomerBankDetail.setCreatedBy(credentialsDTO.getUserName());
                afCustomerBankDetail.setCreatedDate(date);
                afCustomer.addAfCustomerBankDetails(afCustomerBankDetail);
            }
        }
        LOG.info("END : Application Form Customer Bank Details Added");
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AFCustomer getAfCustomer() {
        return afCustomer;
    }

}
