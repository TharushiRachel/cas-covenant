package com.itechro.cas.service.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.customer.*;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.customer.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.WebAuditUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerBuilder.class);

    private CustomerDao customerDao;

    private Customer customer;

    private CustomerDTO updateDTO;

    private CredentialsDTO credentialsDTO;

    private WebAuditService webAuditService;

    private WebAuditJDBCDao webAuditJDBCDao;

    private List<WebAuditDTO> webAuditDTOs;

    private Date date;

    public CustomerBuilder(CustomerDTO customerDTO, CredentialsDTO credentialsDTO) {
        this.updateDTO = customerDTO;
        this.credentialsDTO = credentialsDTO;
        date = new Date();
        this.webAuditDTOs = new ArrayList<>();
    }

    public CustomerBuilder initialize() {
        boolean isNewCustomer = updateDTO.getCustomerID() == null;

        if (isNewCustomer) {
            customer = new Customer();
            customer.setCreatedBy(credentialsDTO.getUserName());
            customer.setCreatedDate(date);

        } else {
            customer = this.customerDao.getOne(updateDTO.getCustomerID());
            customer.setModifiedBy(credentialsDTO.getUserName());
            customer.setModifiedDate(date);
        }

        LOG.info("END: Initialize customer details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerBuilder buildCustomerBasicDetail() throws AppsException {

        customer.setCustomerFinancialID(updateDTO.getCustomerFinancialID());
        customer.setCustomerName(updateDTO.getCustomerName());
        customer.setEmailAddress(updateDTO.getEmailAddress());
        customer.setSecondaryEmailAddress(updateDTO.getSecondaryEmailAddress());
        if (StringUtils.isNotBlank(updateDTO.getDateOfBirth())) {
            customer.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(updateDTO.getDateOfBirth()));
        }
        customer.setCivilStatus(updateDTO.getCivilStatus());
        customer.setTitle(updateDTO.getTitle());
        customer.setStatus(updateDTO.getStatus());
        LOG.info("END: Update Base customer details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerBuilder buildAddress() {
        for (CustomerAddressDTO customerAddressDTO : updateDTO.getCustomerAddressDTOList()) {
            boolean isNewAddress = customerAddressDTO.getCustomerAddressID() == null;
            CustomerAddress customerAddress = null;
            CustomerAddressDTO previousDTO = null;

            if (isNewAddress) {
                customerAddress = new CustomerAddress();
                customerAddress.setCreatedBy(credentialsDTO.getUserName());
                customerAddress.setCreatedDate(date);
                customerAddress.setCustomer(customer);
                customer.getCustomerAddresses().add(customerAddress);
            } else {
                customerAddress = customer.getCustomerAddressByID(customerAddressDTO.getCustomerAddressID());
                previousDTO = new CustomerAddressDTO(customerAddress);
                customerAddress.setModifiedBy(credentialsDTO.getUserName());
                customerAddress.setModifiedDate(date);
            }
            customerAddress.setAddressType(customerAddressDTO.getAddressType());
            customerAddress.setAddress1(customerAddressDTO.getAddress1());
            customerAddress.setAddress2(customerAddressDTO.getAddress2());
            customerAddress.setCity(customerAddressDTO.getCity());
            customerAddress.setStatus(customerAddressDTO.getStatus());

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerAddressAudit(new CustomerAddressDTO(customerAddress),
                    previousDTO, credentialsDTO, date, isNewAddress, webAuditJDBCDao);
            webAuditDTOs.add(webAuditDTO);
        }

        LOG.info("END: Update customer address details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerBuilder buildTelephone() {
        for (CustomerTelephoneDTO customerTelephoneDTO : updateDTO.getCustomerTelephoneDTOList()) {
            boolean isNewTelephone = customerTelephoneDTO.getCustomerTelephoneID() == null;
            CustomerTelephone customerTelephone = null;
            CustomerTelephoneDTO previousDTO = null;
            if (isNewTelephone) {
                customerTelephone = new CustomerTelephone();
                customerTelephone.setCreatedBy(credentialsDTO.getUserName());
                customerTelephone.setCreatedDate(date);
                customerTelephone.setCustomer(customer);
                customer.getCustomerTelephones().add(customerTelephone);
            } else {
                customerTelephone = customer.getCustomerTelephoneByID(customerTelephoneDTO.getCustomerTelephoneID());
                previousDTO = new CustomerTelephoneDTO(customerTelephone);
                customerTelephone.setModifiedBy(credentialsDTO.getUserName());
                customerTelephone.setModifiedDate(date);
            }
            customerTelephone.setDescription(customerTelephoneDTO.getDescription());
            customerTelephone.setContactNumber(customerTelephoneDTO.getContactNumber());
            customerTelephone.setStatus(customerTelephoneDTO.getStatus());

            //Audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerTelephoneAudit(new CustomerTelephoneDTO(customerTelephone),
                    previousDTO, credentialsDTO, date, isNewTelephone, webAuditJDBCDao);
            webAuditDTOs.add(webAuditDTO);

        }
        LOG.info("END: Update customer telephone details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerBuilder buildIdentification() {
        for (CustomerIdentificationDTO customerIdentificationDTO : updateDTO.getCustomerIdentificationDTOList()) {
            boolean isNewIdentification = customerIdentificationDTO.getIdentificationID() == null;
            CustomerIdentification customerIdentification = null;
            CustomerIdentificationDTO previousDTO = null;

            if (isNewIdentification) {
                customerIdentification = new CustomerIdentification();
                customerIdentification.setCreatedBy(credentialsDTO.getUserName());
                customerIdentification.setCreatedDate(date);
                customerIdentification.setCustomer(customer);
                customer.getCustomerIdentifications().add(customerIdentification);
            } else {
                customerIdentification = customer.getCustomerIdentificationByID(customerIdentificationDTO.getIdentificationID());
                previousDTO = new CustomerIdentificationDTO(customerIdentification);
                customerIdentification.setModifiedBy(credentialsDTO.getUserName());
                customerIdentification.setModifiedDate(date);
            }
            customerIdentification.setIdentificationNumber(customerIdentificationDTO.getIdentificationNumber());
            customerIdentification.setIdentificationType(customerIdentificationDTO.getIdentificationType());
            customerIdentification.setStatus(customerIdentificationDTO.getStatus());

            //Audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerIdentificationAudit(new CustomerIdentificationDTO(customerIdentification),
                    previousDTO, credentialsDTO, date, isNewIdentification, webAuditJDBCDao);
            webAuditDTOs.add(webAuditDTO);

        }
        LOG.info("END: Update customer identification details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerBuilder buildBankDetail() {
        for (CustomerBankDetailsDTO customerBankDetailsDTO : updateDTO.getCustomerBankDetailsDTOList()) {
            boolean isNewCustomerBankDetailsDTO = customerBankDetailsDTO.getCustomerBankDetailsID() == null;
            CustomerBankDetail customerBankDetail = null;
            CustomerBankDetailsDTO previousDTO = null;

            if (isNewCustomerBankDetailsDTO) {
                customerBankDetail = new CustomerBankDetail();
                customerBankDetail.setCreatedBy(credentialsDTO.getUserName());
                customerBankDetail.setCreatedDate(date);
                customerBankDetail.setCustomer(customer);
                customer.getCustomerBankDetails().add(customerBankDetail);
            } else {
                customerBankDetail = customer.getCustomerBankDetailsByID(customerBankDetailsDTO.getCustomerBankDetailsID());
                previousDTO = new CustomerBankDetailsDTO(customerBankDetail);
                customerBankDetail.setModifiedBy(credentialsDTO.getUserName());
                customerBankDetail.setModifiedDate(date);
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
            customerBankDetail.setStatus(customerBankDetailsDTO.getStatus());

            for (CusBankAccJoiningPartnerDto accJoiningPartnerDto : customerBankDetailsDTO.getJoiningPartnerList()) {
                boolean isNewPartner = accJoiningPartnerDto.getCusBankAccJoiningPartnerID() == null;
                CusBankAccJoiningPartner bankAccJoiningPartner = null;
                CusBankAccJoiningPartnerDto previousPartnerDTO = null;
                if (isNewPartner) {
                    bankAccJoiningPartner = new CusBankAccJoiningPartner();
                    bankAccJoiningPartner.setCreatedBy(credentialsDTO.getUserName());
                    bankAccJoiningPartner.setCreatedDate(date);
                    customerBankDetail.addCusBankAccJoiningPartner(bankAccJoiningPartner);
                } else {
                    bankAccJoiningPartner = customerBankDetail.getCusBankAccJoiningPartnerByID(accJoiningPartnerDto.getCusBankAccJoiningPartnerID());
                    previousPartnerDTO = new CusBankAccJoiningPartnerDto(bankAccJoiningPartner);
                    bankAccJoiningPartner.setModifiedBy(credentialsDTO.getUserName());
                    bankAccJoiningPartner.setModifiedDate(date);
                }
                bankAccJoiningPartner.setCustomerFinacleID(accJoiningPartnerDto.getCustomerFinacleID());
                bankAccJoiningPartner.setCustomerNICNumber(accJoiningPartnerDto.getCustomerNICNumber());
                bankAccJoiningPartner.setPrimaryCustomer(AppsConstants.YesNo.valueOf(accJoiningPartnerDto.isPrimaryCustomer()));
                bankAccJoiningPartner.setStatus(accJoiningPartnerDto.getStatus());

                //Audit
                WebAuditDTO webAuditDTO = WebAuditUtils.constructCusBankAccJoiningPartnerAudit(new CusBankAccJoiningPartnerDto(bankAccJoiningPartner),
                        previousPartnerDTO, credentialsDTO, date, isNewPartner, webAuditJDBCDao);
                webAuditDTOs.add(webAuditDTO);
            }

            //Audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerBankDetailsAudit(new CustomerBankDetailsDTO(customerBankDetail),
                    previousDTO, credentialsDTO, date, isNewCustomerBankDetailsDTO, webAuditJDBCDao);
            webAuditDTOs.add(webAuditDTO);
        }
        LOG.info("END: Update customer bank details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerBuilder buildWebAudit() {
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

    public void setWebAuditJDBCDao(WebAuditJDBCDao webAuditJDBCDao) {
        this.webAuditJDBCDao = webAuditJDBCDao;
    }

}
