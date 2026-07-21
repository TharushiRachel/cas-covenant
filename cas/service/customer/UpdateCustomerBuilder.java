package com.itechro.cas.service.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.facilitypaper.CasCustomerDao;
import com.itechro.cas.dao.lead.LeadDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.customer.*;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.customer.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class UpdateCustomerBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerBuilder.class);

    private CustomerDao customerDao;

    private CasCustomerDao casCustomerDao;

    private LeadDao leadDao;

    private Customer customer;

    private CustomerDTO updateDTO;

    private CredentialsDTO credentialsDTO;

    private WebAuditService webAuditService;

    private List<WebAuditDTO> webAuditDTOs;

    private Date date;

    public UpdateCustomerBuilder(CustomerDTO customerDTO, CredentialsDTO credentialsDTO) {
        this.updateDTO = customerDTO;
        this.credentialsDTO = credentialsDTO;
        date = new Date();
        this.webAuditDTOs = new ArrayList<>();
    }

    public UpdateCustomerBuilder initialize() throws AppsException {
        if (updateDTO != null && updateDTO.getCustomerFinancialID() != null) {
            try {
                customer = this.customerDao.findByCustomerFinancialIDAndStatus(updateDTO.getCustomerFinancialID(), AppsConstants.Status.ACT);
            } catch (Exception e) {
//            TODO Remove after DB Cleaned
                List<Customer> customers = this.customerDao.findAllByCustomerFinancialIDAndStatus(updateDTO.getCustomerFinancialID(), AppsConstants.Status.ACT);
                LOG.warn("WARN : DB_CLEAR : START : {} Finacle ID Same : {} Customers Found : DB Clearing Ende", updateDTO.getCustomerFinancialID(), customers.size());
                List<Customer> duplicatedCustomers = new ArrayList<>();

                Customer activeCustomer = customers.stream().max(Comparator.comparing(Customer::getCustomerID)).orElseThrow(NullPointerException::new);
                duplicatedCustomers = customers.stream().filter(customer1 -> !customer1.getCustomerID().equals(activeCustomer.getCustomerID())).collect(Collectors.toList());

                for (Customer duplicatedCustomer : duplicatedCustomers) {

                    Set<CASCustomer> CASCustomers = casCustomerDao.findAllByCustomer_CustomerIDAndStatus(duplicatedCustomer.getCustomerID(), AppsConstants.Status.ACT);
                    for (CASCustomer CASCustomer : CASCustomers) {
                        LOG.warn("INFO : DB_CLEAR : Invalid Customer ID  {} : Valid Customer ID : {} ", CASCustomer.getCustomer().getCustomerID(), activeCustomer.getCustomerID());
                        CASCustomer.setCustomer(activeCustomer);
                    }
                    casCustomerDao.saveAll(CASCustomers);
                    LOG.warn("INFO : DB_CLEAR : FP Customers Updated : {} Records", CASCustomers.size());

                    Set<Lead> leadSet = leadDao.findAllByCustomerID(duplicatedCustomer.getCustomerID());
                    for (Lead lead : leadSet) {
                        lead.setCustomerID(activeCustomer.getCustomerID());
                    }

                    leadDao.saveAll(leadSet);
                    LOG.warn("INFO : DB_CLEAR : Lead Customer ID Updated : {} Records", leadSet.size());

                    duplicatedCustomer.setStatus(AppsConstants.Status.INA);
                    for (CustomerAddress customerAddress : duplicatedCustomer.getCustomerAddresses()) {
                        customerAddress.setStatus(AppsConstants.Status.INA);
                    }
                    for (CustomerIdentification customerIdentification : duplicatedCustomer.getCustomerIdentifications()) {
                        customerIdentification.setStatus(AppsConstants.Status.INA);
                    }
                    for (CustomerTelephone customerTelephone : duplicatedCustomer.getCustomerTelephones()) {
                        customerTelephone.setStatus(AppsConstants.Status.INA);
                    }
                    for (CustomerBankDetail customerBankDetail : duplicatedCustomer.getCustomerBankDetails()) {
                        customerBankDetail.setStatus(AppsConstants.Status.INA);
                    }
                }

                customerDao.saveAll(duplicatedCustomers);
                LOG.warn("INFO : DB_CLEAR : Duplicate {} Customers Inactivated", duplicatedCustomers.size());
                LOG.warn("WARN : DB_CLEAR : END : {} Finacle ID Same : {} Customers Found : DB Clearing Ended", updateDTO.getCustomerFinancialID(), customers.size());
                LOG.warn("ERROR : DB_CLEAR : ", e);
            }

            customer = this.customerDao.findByCustomerFinancialIDAndStatus(updateDTO.getCustomerFinancialID(), AppsConstants.Status.ACT);

            boolean isNewCustomer = customer == null;

            if (isNewCustomer) {
                customer = new Customer();
                LOG.info("INFO: New Customer Created ==> Finacle ID : {} : Customer DTO {}", updateDTO.getCustomerFinancialID(), updateDTO);
                customer.setCreatedBy(credentialsDTO.getUserName());
                customer.setCreatedDate(date);
            } else {
                customer.setModifiedBy(credentialsDTO.getUserName());
                customer.setModifiedDate(date);
                LOG.info("INFO: Customer Update ==> Finacle ID : {} : Customer DTO {}", updateDTO.getCustomerFinancialID(), updateDTO);
            }

        } else {
            LOG.error("ERROR: Invalid customer response from Finacle : Customer DTO {}", updateDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_INVALID_CUSTOMER_RESPONSE);
        }

        LOG.info("END: Initialize customer details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public UpdateCustomerBuilder updateCustomerBasicDetail() throws AppsException {

        customer.setCustomerFinancialID(updateDTO.getCustomerFinancialID());
        customer.setCustomerName(updateDTO.getCustomerName());
        customer.setEmailAddress(updateDTO.getEmailAddress());
        customer.setSecondaryEmailAddress(updateDTO.getSecondaryEmailAddress());
        if (StringUtils.isNotBlank(updateDTO.getDateOfBirth())) {
            customer.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(updateDTO.getDateOfBirth()));
        }
        customer.setCivilStatus(updateDTO.getCivilStatus());
        customer.setTitle(updateDTO.getTitle());
        customer.setStatus(AppsConstants.Status.ACT);
        LOG.info("END: Update Base customer details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public UpdateCustomerBuilder updateAddress() {

        if (customer != null) {
            Set<CustomerAddress> customerAddresses = customer.getCustomerAddresses();
            for (CustomerAddress customerAddress : customerAddresses) {
                customerAddress.setStatus(AppsConstants.Status.INA);
            }
        }

        for (CustomerAddressDTO customerAddressDTO : updateDTO.getCustomerAddressDTOList()) {

            CustomerAddress customerAddress = null;
            customerAddress = customer.getCustomerAddressByDTO(customerAddressDTO);
            boolean isNewAddress = customerAddress == null;

            if (isNewAddress) {
                customerAddress = new CustomerAddress();
                customerAddress.setCreatedBy(credentialsDTO.getUserName());
                customerAddress.setCreatedDate(date);
                customerAddress.setCustomer(customer);
                customer.getCustomerAddresses().add(customerAddress);
            } else {
                customerAddress.setModifiedBy(credentialsDTO.getUserName());
                customerAddress.setModifiedDate(date);
            }
            customerAddress.setAddressType(customerAddressDTO.getAddressType());
            customerAddress.setAddress1(customerAddressDTO.getAddress1());
            customerAddress.setAddress2(customerAddressDTO.getAddress2());
            customerAddress.setCity(customerAddressDTO.getCity());
            customerAddress.setStatus(AppsConstants.Status.ACT);
        }

        LOG.info("END: Update customer address details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public UpdateCustomerBuilder updateTelephone() {

        if (customer != null) {
            Set<CustomerTelephone> customerTelephones = customer.getCustomerTelephones();
            for (CustomerTelephone customerTelephone : customerTelephones) {
                customerTelephone.setStatus(AppsConstants.Status.INA);
            }
        }
        for (CustomerTelephoneDTO customerTelephoneDTO : updateDTO.getCustomerTelephoneDTOList()) {
            CustomerTelephone customerTelephone = null;
            if (StringUtils.isNotEmpty(customerTelephoneDTO.getContactNumber())) {
                customerTelephone = customer.getCustomerTelephoneByDTO(customerTelephoneDTO);
                boolean isNewTelephone = customerTelephone == null;

                if (isNewTelephone) {
                    customerTelephone = new CustomerTelephone();
                    customerTelephone.setCreatedBy(credentialsDTO.getUserName());
                    customerTelephone.setCreatedDate(date);
                    customerTelephone.setCustomer(customer);
                    customer.getCustomerTelephones().add(customerTelephone);
                } else {
                    customerTelephone.setModifiedBy(credentialsDTO.getUserName());
                    customerTelephone.setModifiedDate(date);
                }
                customerTelephone.setDescription(customerTelephoneDTO.getDescription());
                customerTelephone.setContactNumber(customerTelephoneDTO.getContactNumber());
                customerTelephone.setStatus(AppsConstants.Status.ACT);
            }
        }
        LOG.info("END: Update customer telephone details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public UpdateCustomerBuilder updateIdentification() {
        if (customer != null) {
            Set<CustomerIdentification> customerIdentifications = customer.getCustomerIdentifications();
            for (CustomerIdentification customerIdentification : customerIdentifications) {
                customerIdentification.setStatus(AppsConstants.Status.INA);
            }
        }

        for (CustomerIdentificationDTO customerIdentificationDTO : updateDTO.getCustomerIdentificationDTOList()) {
            CustomerIdentification customerIdentification = null;
            if (StringUtils.isNotEmpty(customerIdentificationDTO.getIdentificationNumber())) {
                customerIdentification = customer.getCustomerIdentificationByDTO(customerIdentificationDTO);
                boolean isNewIdentification = customerIdentification == null;

                if (isNewIdentification) {
                    customerIdentification = new CustomerIdentification();
                    customerIdentification.setCreatedBy(credentialsDTO.getUserName());
                    customerIdentification.setCreatedDate(date);
                    customerIdentification.setCustomer(customer);
                    customer.getCustomerIdentifications().add(customerIdentification);
                } else {
                    customerIdentification.setModifiedBy(credentialsDTO.getUserName());
                    customerIdentification.setModifiedDate(date);
                }
                customerIdentification.setIdentificationNumber(customerIdentificationDTO.getIdentificationNumber());
                customerIdentification.setIdentificationType(customerIdentificationDTO.getIdentificationType());
                customerIdentification.setStatus(AppsConstants.Status.ACT);
            }
        }
        LOG.info("END: Update customer identification details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public UpdateCustomerBuilder updateBankDetail() {

        if (customer != null) {
            Set<CustomerBankDetail> customerBankDetails = customer.getCustomerBankDetails();
            for (CustomerBankDetail customerBankDetail : customerBankDetails) {
                customerBankDetail.setStatus(AppsConstants.Status.INA);
            }
        }

        for (CustomerBankDetailsDTO customerBankDetailsDTO : updateDTO.getCustomerBankDetailsDTOList()) {

            CustomerBankDetail customerBankDetail = null;
            if (StringUtils.isNotEmpty(customerBankDetailsDTO.getBankAccountNumber())) {
                customerBankDetail = customer.getCustomerBankDetailsByDTO(customerBankDetailsDTO);
                boolean isNewCustomerBankDetailsDTO = customerBankDetail == null;

                if (isNewCustomerBankDetailsDTO) {
                    customerBankDetail = new CustomerBankDetail();
                    customerBankDetail.setCreatedBy(credentialsDTO.getUserName());
                    customerBankDetail.setCreatedDate(date);
                    customerBankDetail.setCustomer(customer);

                    for (CusBankAccJoiningPartnerDto cusBankAccJoiningPartnerDto : customerBankDetailsDTO.getJoiningPartnerList()) {
                        CusBankAccJoiningPartner cusBankAccJoiningPartner = new CusBankAccJoiningPartner();
                        cusBankAccJoiningPartner.setStatus(AppsConstants.Status.ACT);
                        cusBankAccJoiningPartner.setCustomerFinacleID(cusBankAccJoiningPartnerDto.getCustomerFinacleID());
                        cusBankAccJoiningPartner.setCustomerNICNumber(cusBankAccJoiningPartnerDto.getCustomerNICNumber());
                        cusBankAccJoiningPartner.setPrimaryCustomer(AppsConstants.YesNo.valueOf(cusBankAccJoiningPartnerDto.isPrimaryCustomer()));
                        customerBankDetail.addCusBankAccJoiningPartner(cusBankAccJoiningPartner);
                    }

                } else {
                    customerBankDetail.setModifiedBy(credentialsDTO.getUserName());
                    customerBankDetail.setModifiedDate(date);
                    List<CusBankAccJoiningPartner> cusBankAccJoiningPartnerList = customerBankDetail.getCusBankAccJoiningPartnerList();
                    for (CusBankAccJoiningPartner cusBankAccJoiningPartner : cusBankAccJoiningPartnerList) {
                        cusBankAccJoiningPartner.setStatus(AppsConstants.Status.INA);
                    }

                    for (CusBankAccJoiningPartnerDto cusBankAccJoiningPartnerDto : customerBankDetailsDTO.getJoiningPartnerList()) {
                        CusBankAccJoiningPartner cusBankAccJoiningPartner = null;
                        cusBankAccJoiningPartner = customerBankDetail.getCusBankAccJoiningPartnerByDTO(cusBankAccJoiningPartnerDto);

                        boolean isNewCusBankAccJoiningPartner = cusBankAccJoiningPartner == null;

                        if (isNewCusBankAccJoiningPartner) {
                            cusBankAccJoiningPartner = new CusBankAccJoiningPartner();
                            cusBankAccJoiningPartner.setCreatedDate(date);
                            cusBankAccJoiningPartner.setStatus(AppsConstants.Status.ACT);
                            cusBankAccJoiningPartner.setCreatedBy(credentialsDTO.getUserName());
                        } else {
                            cusBankAccJoiningPartner.setModifiedDate(date);
                            cusBankAccJoiningPartner.setStatus(AppsConstants.Status.ACT);
                            cusBankAccJoiningPartner.setModifiedBy(credentialsDTO.getUserName());
                        }
                        cusBankAccJoiningPartner.setCustomerFinacleID(cusBankAccJoiningPartnerDto.getCustomerFinacleID());
                        cusBankAccJoiningPartner.setCustomerNICNumber(cusBankAccJoiningPartnerDto.getCustomerNICNumber());
                        cusBankAccJoiningPartner.setPrimaryCustomer(AppsConstants.YesNo.valueOf(cusBankAccJoiningPartnerDto.isPrimaryCustomer()));
                        customerBankDetail.addCusBankAccJoiningPartner(cusBankAccJoiningPartner);
                    }
                }
                customerBankDetail.setBankAccountNumber(customerBankDetailsDTO.getBankAccountNumber());
                customerBankDetail.setBankAccountType(customerBankDetailsDTO.getBankAccountType());
                customerBankDetail.setBranchCode(customerBankDetailsDTO.getBranchCode());
                customerBankDetail.setAccountCLSFlag(customerBankDetailsDTO.getAccountCLSFlag());
                customerBankDetail.setAccSince(customerBankDetailsDTO.getAccSince());
                customerBankDetail.setSchmCode(customerBankDetailsDTO.getSchmCode());
                customerBankDetail.setSchmType(customerBankDetailsDTO.getSchmType());
                customerBankDetail.setAccountCurrencyCode(customerBankDetailsDTO.getAccountCurrencyCode());
                customerBankDetail.setAccountStatus(customerBankDetailsDTO.getAccountStatus());
                customerBankDetail.setStatus(AppsConstants.Status.ACT);
                customer.addCustomerBankDetails(customerBankDetail);
            }
        }
        LOG.info("END: Update customer bank details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public UpdateCustomerBuilder buildWebAudit() {
        if (!webAuditDTOs.isEmpty()) {
            webAuditService.saveWebAudit(webAuditDTOs, credentialsDTO);
        }
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setWebAuditService(WebAuditService webAuditService) {
        this.webAuditService = webAuditService;
    }

    public void setCasCustomerDao(CasCustomerDao casCustomerDao) {
        this.casCustomerDao = casCustomerDao;
    }

    public void setLeadDao(LeadDao leadDao) {
        this.leadDao = leadDao;
    }
}
