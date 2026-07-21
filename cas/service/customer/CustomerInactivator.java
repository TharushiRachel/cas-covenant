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

public class CustomerInactivator {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerBuilder.class);

    private CustomerDao customerDao;

    private Customer customer;

    private CustomerDTO updateDTO;

    private CredentialsDTO credentialsDTO;

    private WebAuditService webAuditService;

    private WebAuditJDBCDao webAuditJDBCDao;

    private List<WebAuditDTO> webAuditDTOs;

    private Date date;

    public CustomerInactivator(CustomerDTO customerDTO, CredentialsDTO credentialsDTO) {
        this.updateDTO = customerDTO;
        this.credentialsDTO = credentialsDTO;
        date = new Date();
        this.webAuditDTOs = new ArrayList<>();
    }

    public CustomerInactivator initialize() {

        if (updateDTO.getCustomerID() != null) {
            customer = this.customerDao.getOne(updateDTO.getCustomerID());
            customer.setModifiedBy(credentialsDTO.getUserName());
            customer.setModifiedDate(date);
        }
        LOG.info("END: Refresh initial customer details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerInactivator updateCustomerBasicDetail() throws AppsException {
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
        LOG.info("END: Refresh Base customer details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerInactivator inactiveAddress() {
        for (CustomerAddress customerAddress : customer.getCustomerAddresses()) {

            CustomerAddressDTO previousDTO = new CustomerAddressDTO(customerAddress);
            customerAddress.setStatus(AppsConstants.Status.INA);
//            customer.removeAddress(customerAddress);
            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerAddressAudit(new CustomerAddressDTO(customerAddress),
                    previousDTO, credentialsDTO, date, false, webAuditJDBCDao);
            webAuditDTOs.add(webAuditDTO);
        }

        LOG.info("END: Inactivate customer address details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerInactivator inactivateTelephone() {
        for (CustomerTelephone customerTelephone : customer.getCustomerTelephones()) {

            CustomerTelephoneDTO previousDTO = new CustomerTelephoneDTO(customerTelephone);
            customerTelephone.setStatus(AppsConstants.Status.INA);
//            customer.removeTelephones(customerTelephone);

            //Audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerTelephoneAudit(new CustomerTelephoneDTO(customerTelephone),
                    previousDTO, credentialsDTO, date, false, webAuditJDBCDao);
            webAuditDTOs.add(webAuditDTO);

        }
        LOG.info("END: Inactivate customer telephone details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerInactivator inactivateIdentification() {
        for (CustomerIdentification customerIdentification : customer.getCustomerIdentifications()) {

            CustomerIdentificationDTO previousDTO = new CustomerIdentificationDTO(customerIdentification);
            customerIdentification.setStatus(AppsConstants.Status.INA);
//            customer.removeCustomerIdentification(customerIdentification);

            //Audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerIdentificationAudit(new CustomerIdentificationDTO(customerIdentification),
                    previousDTO, credentialsDTO, date, false, webAuditJDBCDao);
            webAuditDTOs.add(webAuditDTO);

        }
        LOG.info("END: Inactivate customer identification details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerInactivator inactivateBankDetail() {
        for (CustomerBankDetail customerBankDetail : customer.getCustomerBankDetails()) {

            CustomerBankDetailsDTO previousDTO = new CustomerBankDetailsDTO(customerBankDetail);
            customerBankDetail.setStatus(AppsConstants.Status.INA);
//            customer.removeCustomerBankDetail(customerBankDetail);

            for (CusBankAccJoiningPartner accJoiningPartner : customerBankDetail.getCusBankAccJoiningPartnerList()) {

                CusBankAccJoiningPartnerDto previousPartnerDTO = new CusBankAccJoiningPartnerDto(accJoiningPartner);
                accJoiningPartner.setStatus(AppsConstants.Status.INA);
//                customerBankDetail.removeCusBankAccJoiningPartner(accJoiningPartner);


                //Audit
                WebAuditDTO webAuditDTO = WebAuditUtils.constructCusBankAccJoiningPartnerAudit(new CusBankAccJoiningPartnerDto(accJoiningPartner),
                        previousPartnerDTO, credentialsDTO, date, false, webAuditJDBCDao);
                webAuditDTOs.add(webAuditDTO);
            }

            //Audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerBankDetailsAudit(new CustomerBankDetailsDTO(customerBankDetail),
                    previousDTO, credentialsDTO, date, false, webAuditJDBCDao);
            webAuditDTOs.add(webAuditDTO);
        }
        LOG.info("END: Inactivate customer bank details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return this;
    }

    public CustomerInactivator buildWebAudit() {
        if (!webAuditDTOs.isEmpty()) {
            webAuditService.saveWebAudit(webAuditDTOs, credentialsDTO);
        }
        return this;
    }

    public Customer getCustomer() {
        return customer;
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
