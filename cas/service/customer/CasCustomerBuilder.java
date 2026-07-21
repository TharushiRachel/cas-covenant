package com.itechro.cas.service.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.facilitypaper.CasCustomerDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.dto.customer.*;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerUpdateRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class CasCustomerBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(CasCustomerBuilder.class);

    private CasCustomerDao casCustomerDao;

    private CustomerDao customerDao;

    private IntegrationService integrationService;

    private CustomerService customerService;

    private CASCustomer casCustomer;

    private CASCustomerUpdateRQ casCustomerUpdateRQ;

    private SearchCustomerRQ searchCustomerRQ;

    private CustomerDTO customerDTOResponse;

    private CredentialsDTO credentialsDTO;

    private Date date;

    public CasCustomerBuilder(CASCustomerUpdateRQ casCustomerUpdateRQ, CredentialsDTO credentialsDTO) {
        this.casCustomerUpdateRQ = casCustomerUpdateRQ;
        this.credentialsDTO = credentialsDTO;
        date = new Date();
    }

    public CasCustomerBuilder searchCustomer() throws AppsException {
        try {
            customerDTOResponse = this.integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);
            customerService.updateCustomerDTO(customerDTOResponse, credentialsDTO);
            if (customerDTOResponse == null) {
                LOG.error("ERROR: Customer couldn't find on Finacle. {}", searchCustomerRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_CUSTOMER_CANNOT_FIND);
            }
            if (customerDTOResponse.getCustomerFinancialID() == null) {
                LOG.error("ERROR: Invalid customer response from Finacle. {} : {}", searchCustomerRQ, customerDTOResponse);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_INVALID_CUSTOMER_RESPONSE);
            }
        } catch (Exception e) {
            LOG.error("ERROR: Customer couldn't find on Finacle. {}", searchCustomerRQ, e);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_CUSTOMER_CANNOT_FIND);
        }

        LOG.info("END: Initialize cas customer details :{} by :{}", casCustomerUpdateRQ, credentialsDTO.getUserName());
        return this;
    }


    public CasCustomerBuilder initialize() {
        casCustomer = this.casCustomerDao.getOne(casCustomerUpdateRQ.getCasCustomerID());
        casCustomer.setModifiedBy(credentialsDTO.getUserName());
        casCustomer.setModifiedDate(date);
        LOG.info("END: Initialize cas customer details :{} by :{}", casCustomerUpdateRQ, credentialsDTO.getUserName());
        return this;
    }

    public CasCustomerBuilder updateBuildCustomerBasicDetail() throws AppsException {

        casCustomer.setCustomer(customerDao.findByCustomerFinancialIDAndStatus(customerDTOResponse.getCustomerFinancialID(), AppsConstants.Status.ACT));
        casCustomer.setCasCustomerName(customerDTOResponse.getCustomerName());
        casCustomer.setEmailAddress(customerDTOResponse.getEmailAddress());
        casCustomer.setSecondaryEmailAddress(customerDTOResponse.getSecondaryEmailAddress());
        if (StringUtils.isNotBlank(customerDTOResponse.getDateOfBirth())) {
            casCustomer.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(customerDTOResponse.getDateOfBirth()));
        }
        casCustomer.setCivilStatus(customerDTOResponse.getCivilStatus());
        casCustomer.setTitle(customerDTOResponse.getTitle());
        casCustomer.setStatus(customerDTOResponse.getStatus());
        LOG.info("END: Update Base customer details :{} by :{}", customerDTOResponse, credentialsDTO.getUserName());
        return this;
    }

    public CasCustomerBuilder updateAddress() {

        for (CASCustomerAddress casCustomerAddress : casCustomer.getCASCustomerAddressSet()) {
            casCustomerAddress.setStatus(AppsConstants.Status.INA);
        }

        for (CustomerAddressDTO customerAddressDTO : customerDTOResponse.getCustomerAddressDTOList()) {
            CASCustomerAddress casCustomerAddress = null;
            casCustomerAddress = new CASCustomerAddress();
            casCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
            casCustomerAddress.setCreatedDate(date);
            casCustomerAddress.setAddressType(customerAddressDTO.getAddressType());
            casCustomerAddress.setAddress1(customerAddressDTO.getAddress1());
            casCustomerAddress.setAddress2(customerAddressDTO.getAddress2());
            casCustomerAddress.setCity(customerAddressDTO.getCity());
            casCustomerAddress.setStatus(AppsConstants.Status.ACT);
            casCustomer.addCasCustomerAddress(casCustomerAddress);

        }

        LOG.info("END: Update cas customer address details :{} by :{}", customerDTOResponse, credentialsDTO.getUserName());
        return this;
    }

    public CasCustomerBuilder updateTelephone() {

        for (CASCustomerTelephone casCustomerTelephone : casCustomer.getCASCustomerTelephoneSet()) {
            casCustomerTelephone.setStatus(AppsConstants.Status.INA);
        }

        for (CustomerTelephoneDTO customerTelephoneDTO : customerDTOResponse.getCustomerTelephoneDTOList()) {
            CASCustomerTelephone casCustomerTelephone = new CASCustomerTelephone();
            casCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
            casCustomerTelephone.setCreatedDate(date);
            casCustomerTelephone.setDescription(customerTelephoneDTO.getDescription());
            casCustomerTelephone.setContactNumber(customerTelephoneDTO.getContactNumber());
            casCustomerTelephone.setStatus(AppsConstants.Status.ACT);
            casCustomer.addCasCustomerTelephone(casCustomerTelephone);
        }

        LOG.info("END: Update cas customer telephone details :{} by :{}", customerDTOResponse, credentialsDTO.getUserName());
        return this;
    }

    public CasCustomerBuilder updateIdentification() {

        for (CASCustomerIdentification casCustomerIdentification : casCustomer.getCASCustomerIdentificationSet()) {
            casCustomerIdentification.setStatus(AppsConstants.Status.INA);
        }

        for (CustomerIdentificationDTO customerIdentificationDTO : customerDTOResponse.getCustomerIdentificationDTOList()) {
            CASCustomerIdentification casCustomerIdentification = new CASCustomerIdentification();
            casCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
            casCustomerIdentification.setCreatedDate(date);
            casCustomerIdentification.setIdentificationNumber(customerIdentificationDTO.getIdentificationNumber());
            casCustomerIdentification.setIdentificationType(customerIdentificationDTO.getIdentificationType());
            casCustomerIdentification.setStatus(AppsConstants.Status.ACT);
            casCustomer.addCasCustomerIdentification(casCustomerIdentification);
        }

        LOG.info("END: Update cas customer identification details :{} by :{}", customerDTOResponse, credentialsDTO.getUserName());
        return this;
    }

    public CasCustomerBuilder updateBankDetail() {

        for (CASCustomerBankDetail casCustomerBankDetail : casCustomer.getCASCustomerBankDetailSet()) {
            casCustomerBankDetail.setStatus(AppsConstants.Status.INA);
        }

        for (CustomerBankDetailsDTO customerBankDetailsDTO : customerDTOResponse.getCustomerBankDetailsDTOList()) {
            CASCustomerBankDetail casCustomerBankDetail = null;

            casCustomerBankDetail = new CASCustomerBankDetail();
            casCustomerBankDetail.setCreatedBy(credentialsDTO.getUserName());
            casCustomerBankDetail.setCreatedDate(date);
            casCustomerBankDetail.setBankAccountNumber(customerBankDetailsDTO.getBankAccountNumber());
            casCustomerBankDetail.setBankAccountType(customerBankDetailsDTO.getBankAccountType());
            casCustomerBankDetail.setBranchCode(customerBankDetailsDTO.getBranchCode());
            casCustomerBankDetail.setAccountCLSFlag(customerBankDetailsDTO.getAccountCLSFlag());
            casCustomerBankDetail.setAccSince(customerBankDetailsDTO.getAccSince());
            casCustomerBankDetail.setSchmCode(customerBankDetailsDTO.getSchmCode());
            casCustomerBankDetail.setSchmType(customerBankDetailsDTO.getSchmType());
            casCustomerBankDetail.setAccountCurrencyCode(customerBankDetailsDTO.getAccountCurrencyCode());
            casCustomerBankDetail.setAccountStatus(customerBankDetailsDTO.getAccountStatus());
            casCustomerBankDetail.setStatus(AppsConstants.Status.ACT);
            casCustomer.addCasCustomerBankDetail(casCustomerBankDetail);

        }

        LOG.info("END: create cas customer bank details :{} by :{}", customerDTOResponse, credentialsDTO.getUserName());
        return this;
    }

    public CASCustomer getCasCustomer() {
        return casCustomerDao.saveAndFlush(casCustomer);
    }

    public void setCasCustomerDao(CasCustomerDao casCustomerDao) {
        this.casCustomerDao = casCustomerDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setIntegrationService(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setCasCustomer(CASCustomer casCustomer) {
        this.casCustomer = casCustomer;
    }

    public void setCasCustomerUpdateRQ(CASCustomerUpdateRQ casCustomerUpdateRQ) {
        this.casCustomerUpdateRQ = casCustomerUpdateRQ;
    }

    public void setSearchCustomerRQ(CASCustomerUpdateRQ casCustomerUpdateRQ) {
        if (searchCustomerRQ == null) {
            this.searchCustomerRQ = new SearchCustomerRQ();
        }
        this.searchCustomerRQ.setIdentificationNumber(casCustomerUpdateRQ.getIdentificationNumber());
        this.searchCustomerRQ.setIdentificationType(casCustomerUpdateRQ.getIdentificationNumber());
    }

    public void setCustomerDTOResponse(CustomerDTO customerDTOResponse) {
        this.customerDTOResponse = customerDTOResponse;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
