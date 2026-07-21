package com.itechro.cas.service.applicationform.command.replicate.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.applicationform.AFCustomerDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.support.AFCustomerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AFCustomerReplicateBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(AFCustomerBuilder.class);

    private CredentialsDTO credentialsDTO;

    private Date date;

    private AFCustomer currentAFCustomer;

    private AFCustomer replicatedAFCustomer;

    private AFCustomerDao afCustomerDao;

    public AFCustomerReplicateBuilder(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public AFCustomerReplicateBuilder buildInitialAFCustomer() throws AppsException {
        this.replicatedAFCustomer = new AFCustomer();
        this.replicatedAFCustomer.setCustomerID(currentAFCustomer.getCustomerID());
        this.replicatedAFCustomer.setCustomerName(currentAFCustomer.getCustomerName());
        this.replicatedAFCustomer.setCustomerFinancialID(currentAFCustomer.getCustomerFinancialID());
        this.replicatedAFCustomer.setEmailAddress(currentAFCustomer.getEmailAddress());
        this.replicatedAFCustomer.setSecondaryEmailAddress(currentAFCustomer.getSecondaryEmailAddress());
        this.replicatedAFCustomer.setDateOfBirth(currentAFCustomer.getDateOfBirth());
        this.replicatedAFCustomer.setCivilStatus(currentAFCustomer.getCivilStatus());
        this.replicatedAFCustomer.setStatus(currentAFCustomer.getStatus());
        this.replicatedAFCustomer.setCreatedBy(credentialsDTO.getUserName());
        this.replicatedAFCustomer.setCreatedDate(date);
        LOG.info("END : Replicate Application Form Customer Initiated");
        return this;
    }

    public AFCustomerReplicateBuilder buildAfCustomerAddress() {

        for (AFCustomerAddress afCustomerAddress : currentAFCustomer.getOrderedAFCustomerAddress()) {
            if (afCustomerAddress.getStatus() == AppsConstants.Status.ACT) {
                AFCustomerAddress replicateAddress = new AFCustomerAddress();
                replicateAddress.setCustomerAddressID(afCustomerAddress.getCustomerAddressID());
                replicateAddress.setCustomerID(afCustomerAddress.getAfCustomer().getCustomerID());
                replicateAddress.setAddressType(afCustomerAddress.getAddressType());
                replicateAddress.setAddress1(afCustomerAddress.getAddress1());
                replicateAddress.setAddress2(afCustomerAddress.getAddress2());
                replicateAddress.setCity(afCustomerAddress.getCity());
                replicateAddress.setStatus(AppsConstants.Status.ACT);
                replicateAddress.setCreatedBy(credentialsDTO.getUserName());
                replicateAddress.setCreatedDate(date);
                replicatedAFCustomer.addAfCustomerAddress(replicateAddress);
            }
        }
        LOG.info("END : Replicate Application Form Customer Addresses");
        return this;
    }

    public AFCustomerReplicateBuilder buildAfCustomerTelephones() {

        for (AFCustomerTelephone afCustomerTelephone : currentAFCustomer.getOrderedAFCustomerTelephones()) {
            if (afCustomerTelephone.getStatus() == AppsConstants.Status.ACT) {
                AFCustomerTelephone replicateCustomerTelephone = new AFCustomerTelephone();
                replicateCustomerTelephone.setCustomerTelephoneID(afCustomerTelephone.getCustomerTelephoneID());
                replicateCustomerTelephone.setCustomerID(afCustomerTelephone.getAfCustomer().getCustomerID());
                replicateCustomerTelephone.setContactNumber(afCustomerTelephone.getContactNumber());
                replicateCustomerTelephone.setDescription(afCustomerTelephone.getDescription());
                replicateCustomerTelephone.setStatus(afCustomerTelephone.getStatus());
                replicateCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                replicateCustomerTelephone.setCreatedDate(date);
                replicatedAFCustomer.addAfCustomerTelephone(replicateCustomerTelephone);
            }
        }
        LOG.info("END : Replicate Application Form Customer Telephone");
        return this;
    }

    public AFCustomerReplicateBuilder buildAFCustomerIdentifications() {

        for (AFCustomerIdentification afCustomerIdentification : currentAFCustomer.getOrderedAFCustomerIdentification()) {
            if (afCustomerIdentification.getStatus() == AppsConstants.Status.ACT) {
                AFCustomerIdentification replicateCustomerIdentification = new AFCustomerIdentification();
                replicateCustomerIdentification.setIdentificationNumber(afCustomerIdentification.getIdentificationNumber());
                replicateCustomerIdentification.setIdentificationType(afCustomerIdentification.getIdentificationType());
                replicateCustomerIdentification.setCustomerID(afCustomerIdentification.getAfCustomer().getCustomerID());
                replicateCustomerIdentification.setIdentificationID(afCustomerIdentification.getIdentificationID());
                replicateCustomerIdentification.setStatus(afCustomerIdentification.getStatus());
                replicateCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                replicateCustomerIdentification.setCreatedDate(date);
                replicatedAFCustomer.addAfCustomerIdentifications(replicateCustomerIdentification);
            }
        }
        LOG.info("END : Replicate Application Form Customer Identification Details");
        return this;
    }


    public AFCustomerReplicateBuilder buildAFBankDetails() {

        for (AFCustomerBankDetail afCustomerBankDetail : currentAFCustomer.getOrderedAFCustomerBankDetails()) {
            if (afCustomerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                AFCustomerBankDetail replicateBankDetail = new AFCustomerBankDetail();
                replicateBankDetail.setCustomerBankDetailsID(afCustomerBankDetail.getCustomerBankDetailsID());
                replicateBankDetail.setCustomerID(afCustomerBankDetail.getAfCustomer().getCustomerID());
                replicateBankDetail.setBankAccountNumber(afCustomerBankDetail.getBankAccountNumber());
                replicateBankDetail.setBankAccountType(afCustomerBankDetail.getBankAccountType());
                replicateBankDetail.setBranchCode(afCustomerBankDetail.getBranchCode());
                replicateBankDetail.setAccountCLSFlag(afCustomerBankDetail.getAccountCLSFlag());
                replicateBankDetail.setAccSince(afCustomerBankDetail.getAccSince());
                replicateBankDetail.setSchmCode(afCustomerBankDetail.getSchmCode());
                replicateBankDetail.setSchmType(afCustomerBankDetail.getSchmType());
                replicateBankDetail.setAccountCurrencyCode(afCustomerBankDetail.getAccountCurrencyCode());
                replicateBankDetail.setAccountStatus(afCustomerBankDetail.getAccountStatus());
                replicateBankDetail.setStatus(afCustomerBankDetail.getStatus());
                replicateBankDetail.setCreatedBy(credentialsDTO.getUserName());
                replicateBankDetail.setCreatedDate(date);
                replicatedAFCustomer.addAfCustomerBankDetails(replicateBankDetail);
            }
        }
        LOG.info("END : Replicate Application Form Customer Bank Details");
        return this;
    }

    public AFCustomerReplicateBuilder saveAfCustomer() {
        replicatedAFCustomer = afCustomerDao.saveAndFlush(replicatedAFCustomer);
        LOG.info("END : Replicate AFCustomer");
        return this;
    }

    public void setAfCustomerDao(AFCustomerDao afCustomerDao) {
        this.afCustomerDao = afCustomerDao;
    }

    public void setCurrentAFCustomer(AFCustomer currentAFCustomer) {
        this.currentAFCustomer = currentAFCustomer;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AFCustomer getReplicatedAFCustomer() {
        return replicatedAFCustomer;
    }
}
