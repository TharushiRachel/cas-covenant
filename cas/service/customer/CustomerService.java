package com.itechro.cas.service.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.dao.customer.*;
import com.itechro.cas.dao.customer.jdbc.CustomerJdbcDao;
import com.itechro.cas.dao.facilitypaper.CasCustomerDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.dao.lead.LeadDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import com.itechro.cas.model.domain.customer.*;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.covenants.CustomerCovenantDetailsDTO;
import com.itechro.cas.model.dto.covenants.CustomerCovenantResponseDTO;
import com.itechro.cas.model.dto.customer.*;
import com.itechro.cas.model.dto.customer.request.CustomerCovenantDetailsReqDTO;
import com.itechro.cas.model.dto.customer.request.CustomerCovenantUpdateDTO;
import com.itechro.cas.model.dto.customer.response.CovenantDetailResDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperSummaryDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperSummarySearchRQ;
import com.itechro.cas.model.dto.integration.request.FacilityDetailLoadRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerAccountStatRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerTranDetCashFlowRQ;
import com.itechro.cas.model.dto.integration.response.CustomerFacilityDetailResponseDTO;
import com.itechro.cas.model.dto.integration.response.CustomerStatistics.ExecuteFinacleScriptCustomDataResponseDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.WebAuditUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerJdbcDao customerJdbcDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CasCustomerDao casCustomerDao;

    @Autowired
    private LeadDao leadDao;

    @Autowired
    private CustomerAddressDao customerAddressDao;

    @Autowired
    private CustomerTelephoneDao customerTelephoneDao;

    @Autowired
    private CustomerIdentificationDao customerIdentificationDao;

    @Autowired
    private CustomerBankDetailsDao customerBankDetailsDao;

    @Autowired
    private FacilityPaperJdbcDao facilityPaperJdbcDao;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private WebAuditService webAuditService;

    @Autowired
    private WebAuditJDBCDao webAuditJDBCDao;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private FacilityPaperDao facilityPaperDao;

    @Autowired
    private CustomerEvaluationDao customerEvaluationDao;

    @Autowired
    private SecurityService securityService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerDTO getCustomerByID(Integer customerID) {

        Customer customer = customerDao.getOne(customerID);
        return new CustomerDTO(customer);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerDTO getActiveCustomerByFincaleID(String finacleID) {
        Customer customer = customerDao.findByCustomerFinancialIDAndStatus(finacleID, AppsConstants.Status.ACT);
        return new CustomerDTO(customer);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<CustomerDTO> getPagedCustomerDTO(CustomerSearchRQ searchRQ) {
        return customerJdbcDao.getPagedCustomerDTO(searchRQ);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Page<CustomerDTO> getPagedCustomersForJoiningParties(CustomerSearchRQ searchRQ, CredentialsDTO credentialsDTO) throws AppsException {

        Page<CustomerDTO> customerDTOPage = new Page<>();
        LOG.info("START: Get paged Customers for Joining Parties :{} by :{}", searchRQ, credentialsDTO.getUserName());

        if (
                (StringUtils.isNotBlank(searchRQ.getCustomerFinancialID()))
                        || (StringUtils.isNotBlank(searchRQ.getBankAccountNumber()))
                        || (StringUtils.isNotBlank(searchRQ.getIdentificationNumber()))
                        || (searchRQ.getIdentificationType() != null && StringUtils.isNotEmpty(searchRQ.getIdentificationType().name()))
        ) {
            //This is for new search request for customer if not in the DB and then save the customer
            //This search mapping is to resolve pagination search and regular search request conflict

            SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
            searchCustomerRQ.setIdentificationNumber(searchRQ.getIdentificationNumber());
            searchCustomerRQ.setBankAccountNumber(searchRQ.getBankAccountNumber());
            searchCustomerRQ.setCustomerFinancialID(searchRQ.getCustomerFinancialID());
            if (searchRQ.getIdentificationType() != null) {
                searchCustomerRQ.setIdentificationType(searchRQ.getIdentificationType().name());
            }

            CustomerDTO customerDTO = this.customerJdbcDao.getCustomerDTOBy(searchCustomerRQ);
            if (customerDTO == null || customerDTO.getCustomerFinancialID() == null) {
                LOG.info("START: Search Customer for Joining Parties from bank :{} by :{}", searchRQ, credentialsDTO);
                try {
                    CustomerDTO response = this.integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);
                    this.updateCustomerDTO(response, credentialsDTO);
                } catch (Exception e) {
                    LOG.error("ERROR: Customer couldn't find on Finacle. {}", searchRQ, e);
                }
            }
        }
        customerDTOPage = customerJdbcDao.getPagedCustomerDTO(searchRQ);
        LOG.info("END: Get paged Customers for Joining Parties :{} by :{}", searchRQ, credentialsDTO.getUserName());
        return customerDTOPage;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerDTO saveOrUpdateCustomerDTO(CustomerDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Customer :{} by :{}", updateDTO, credentialsDTO.getUserName());
        CustomerBuilder builder = new CustomerBuilder(updateDTO, credentialsDTO);
        builder.setCustomerDao(customerDao);
        builder.setWebAuditService(webAuditService);
        builder.setWebAuditJDBCDao(webAuditJDBCDao);

        boolean isNewCustomer = (updateDTO.getCustomerID() == null);
        CustomerDTO previousDTO = null;
        if (!isNewCustomer) {
            previousDTO = this.getCustomerByID(updateDTO.getCustomerID());
        }

        Customer customer = builder.initialize()
                .buildCustomerBasicDetail()
                .buildAddress()
                .buildBankDetail()
                .buildIdentification()
                .buildTelephone()
                .buildWebAudit()
                .getCustomer();

        customer = this.customerDao.saveAndFlush(customer);
        updateDTO = new CustomerDTO(customer);

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerAudit(updateDTO, previousDTO, credentialsDTO, new Date(), isNewCustomer);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update Customer :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return updateDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CustomerDTO updateCustomerDTO(CustomerDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: update Customer :{} by :{}", updateDTO, credentialsDTO.getUserName());
        UpdateCustomerBuilder builder = new UpdateCustomerBuilder(updateDTO, credentialsDTO);
        builder.setCustomerDao(customerDao);
        builder.setWebAuditService(webAuditService);

        builder.setCasCustomerDao(casCustomerDao);
        builder.setLeadDao(leadDao);

        Customer customer = builder.initialize()
                .updateCustomerBasicDetail()
                .updateAddress()
                .updateTelephone()
                .updateIdentification()
                .updateBankDetail()
                .getCustomer();

        customer = this.customerDao.saveAndFlush(customer);
        updateDTO = new CustomerDTO(customer);

        LOG.info("END: update Customer :{} by :{}", updateDTO, credentialsDTO.getUserName());
        return updateDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerDTO updateCustomerAdditionInfoDTO(CustomerAdditionInfoDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Update Customer addition Info :{} by :{}", updateDTO, credentialsDTO.getUserName());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerID(updateDTO.getCustomerID());
        customerDTO.setCustomerTelephoneDTOList(updateDTO.getCustomerTelephoneDTOList());
        customerDTO.setCustomerAddressDTOList(updateDTO.getCustomerAddressDTOList());
        customerDTO.setCustomerIdentificationDTOList(updateDTO.getCustomerIdentificationDTOList());
        customerDTO.setCustomerBankDetailsDTOList(updateDTO.getCustomerBankDetailsDTOList());

        CustomerBuilder builder = new CustomerBuilder(customerDTO, credentialsDTO);
        builder.setCustomerDao(customerDao);

        Customer customer = builder.initialize()
                .buildAddress()
                .buildBankDetail()
                .buildIdentification()
                .buildTelephone()
                .getCustomer();

//        customer = this.customerDao.saveAndFlush(customer);
        LOG.info("END: Update Customer addition Info :{} by :{}", updateDTO, credentialsDTO.getUserName());

        return new CustomerDTO(customer);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerAddressDTO getCustomerAddressByID(Integer customerAddressID) {

        CustomerAddress customerAddress = customerAddressDao.getOne(customerAddressID);
        return new CustomerAddressDTO(customerAddress);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CustomerAddressDTO saveOrUpdateCustomerAddressDTO(CustomerAddressDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update CustomerAddress :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        CustomerAddress customerAddress = null;
        boolean isNewCustomerAddress = updateDTO.getCustomerAddressID() == null;

        if (isNewCustomerAddress) {
            customerAddress = new CustomerAddress();
            customerAddress.setCreatedBy(credentialsDTO.getUserName());
            customerAddress.setCreatedDate(date);

        } else {
            customerAddress = this.customerAddressDao.getOne(updateDTO.getCustomerAddressID());
            customerAddress.setModifiedBy(credentialsDTO.getUserName());
            customerAddress.setModifiedDate(date);
        }

        customerAddress.setAddressType(updateDTO.getAddressType());
        customerAddress.setAddress1(updateDTO.getAddress1());
        customerAddress.setAddress2(updateDTO.getAddress2());
        customerAddress.setCity(updateDTO.getCity());
        customerAddress.setStatus(updateDTO.getStatus());

        customerAddress = this.customerAddressDao.saveAndFlush(customerAddress);
        updateDTO = new CustomerAddressDTO(customerAddress);
        LOG.info("END: Save update CustomerAddress :{} by :{}", updateDTO, credentialsDTO.getUserName());

        return updateDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerTelephoneDTO getCustomerTelephoneByID(Integer customerTelephoneID) {

        CustomerTelephone customerTelephone = customerTelephoneDao.getOne(customerTelephoneID);
        return new CustomerTelephoneDTO(customerTelephone);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public CustomerTelephoneDTO saveOrUpdateCustomerTelephoneDTO(CustomerTelephoneDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Customer Telephone :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        CustomerTelephone customerTelephone = null;
        boolean isNewCustomerTelephone = updateDTO.getCustomerTelephoneID() == null;

        if (isNewCustomerTelephone) {
            customerTelephone = new CustomerTelephone();
            customerTelephone.setCreatedBy(credentialsDTO.getUserName());
            customerTelephone.setCreatedDate(date);

        } else {
            customerTelephone = this.customerTelephoneDao.getOne(updateDTO.getCustomerTelephoneID());
            customerTelephone.setModifiedBy(credentialsDTO.getUserName());
            customerTelephone.setModifiedDate(date);
        }

        customerTelephone.setDescription(updateDTO.getDescription());
        customerTelephone.setContactNumber(updateDTO.getContactNumber());
        customerTelephone.setStatus(updateDTO.getStatus());

        customerTelephone = this.customerTelephoneDao.saveAndFlush(customerTelephone);
        updateDTO = new CustomerTelephoneDTO(customerTelephone);
        LOG.info("END: Save update Customer Telephone :{} by :{}", updateDTO, credentialsDTO.getUserName());

        return updateDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerIdentificationDTO getCustomerIdentificationByID(Integer identificationID) {

        CustomerIdentification customerIdentification = customerIdentificationDao.getOne(identificationID);
        return new CustomerIdentificationDTO(customerIdentification);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerIdentificationDTO saveOrUpdateCustomerIdentificationDTO(CustomerIdentificationDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Customer Identification :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        CustomerIdentification customerIdentification = null;
        boolean isNewCustomerIdentification = updateDTO.getIdentificationID() == null;

        if (isNewCustomerIdentification) {
            customerIdentification = new CustomerIdentification();
            customerIdentification.setCreatedBy(credentialsDTO.getUserName());
            customerIdentification.setCreatedDate(date);

        } else {
            customerIdentification = this.customerIdentificationDao.getOne(updateDTO.getIdentificationID());
            customerIdentification.setModifiedBy(credentialsDTO.getUserName());
            customerIdentification.setModifiedDate(date);
        }

        customerIdentification.setIdentificationNumber(updateDTO.getIdentificationNumber());
        customerIdentification.setIdentificationType(updateDTO.getIdentificationType());
        customerIdentification.setStatus(updateDTO.getStatus());

        customerIdentification = this.customerIdentificationDao.saveAndFlush(customerIdentification);
        updateDTO = new CustomerIdentificationDTO(customerIdentification);
        LOG.info("END: Save update Customer Identification :{} by :{}", updateDTO, credentialsDTO.getUserName());

        return updateDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerBankDetailsDTO getCustomerBankDetailsByID(Integer bankDetailsID) {

        CustomerBankDetail customerBankDetail = customerBankDetailsDao.getOne(bankDetailsID);
        return new CustomerBankDetailsDTO(customerBankDetail);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerBankDetailsDTO saveOrUpdateCustomerBankDetailsDTO(CustomerBankDetailsDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Customer bank details :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        CustomerBankDetail customerBankDetail = null;
        boolean isNewCustomerBankDetails = updateDTO.getCustomerBankDetailsID() == null;

        if (isNewCustomerBankDetails) {
            customerBankDetail = new CustomerBankDetail();
            customerBankDetail.setCreatedBy(credentialsDTO.getUserName());
            customerBankDetail.setCreatedDate(date);

        } else {
            customerBankDetail = this.customerBankDetailsDao.getOne(updateDTO.getCustomerBankDetailsID());
            customerBankDetail.setModifiedBy(credentialsDTO.getUserName());
            customerBankDetail.setModifiedDate(date);
        }

        customerBankDetail.setBankAccountNumber(updateDTO.getBankAccountNumber());
        customerBankDetail.setBranchCode(updateDTO.getBranchCode());
        customerBankDetail.setBankAccountType(updateDTO.getBankAccountType());
        customerBankDetail.setAccountCLSFlag(updateDTO.getAccountCLSFlag());
        customerBankDetail.setAccSince(updateDTO.getAccSince());
        customerBankDetail.setSchmCode(updateDTO.getSchmCode());
        customerBankDetail.setSchmType(updateDTO.getSchmType());
        customerBankDetail.setAccountCurrencyCode(updateDTO.getAccountCurrencyCode());
        customerBankDetail.setAccountStatus(updateDTO.getAccountStatus());
        customerBankDetail.setStatus(updateDTO.getStatus());

        for (CusBankAccJoiningPartnerDto accJoiningPartnerDto : updateDTO.getJoiningPartnerList()) {
            boolean isNewPartner = accJoiningPartnerDto.getCusBankAccJoiningPartnerID() == null;
            CusBankAccJoiningPartner bankAccJoiningPartner = null;
            if (isNewPartner) {
                bankAccJoiningPartner = new CusBankAccJoiningPartner();
                bankAccJoiningPartner.setCreatedBy(credentialsDTO.getUserName());
                bankAccJoiningPartner.setCreatedDate(date);
                customerBankDetail.addCusBankAccJoiningPartner(bankAccJoiningPartner);
            } else {
                bankAccJoiningPartner = customerBankDetail.getCusBankAccJoiningPartnerByID(accJoiningPartnerDto.getCusBankAccJoiningPartnerID());
                bankAccJoiningPartner.setModifiedBy(credentialsDTO.getUserName());
                bankAccJoiningPartner.setModifiedDate(date);
            }
            bankAccJoiningPartner.setCustomerFinacleID(accJoiningPartnerDto.getCustomerFinacleID());
            bankAccJoiningPartner.setCustomerNICNumber(accJoiningPartnerDto.getCustomerNICNumber());
            bankAccJoiningPartner.setPrimaryCustomer(AppsConstants.YesNo.valueOf(accJoiningPartnerDto.isPrimaryCustomer()));
            bankAccJoiningPartner.setStatus(accJoiningPartnerDto.getStatus());
        }

        customerBankDetail = this.customerBankDetailsDao.saveAndFlush(customerBankDetail);
        updateDTO = new CustomerBankDetailsDTO(customerBankDetail);
        LOG.info("END: Save update Customer bank details :{} by :{}", updateDTO, credentialsDTO.getUserName());

        return updateDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Customer360DTO searchCustomerFrom360(SearchCustomerRQ searchCustomerRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Search Customer360 :{} by :{}", searchCustomerRQ, credentialsDTO.getUserName());

        Boolean isCustomerDirectSearchEnabled = systemParameterService.isCustomerDirectSearchFromBankEnabled();
        CustomerDTO customerDTO = null;

        if (isCustomerDirectSearchEnabled) {
            CustomerDTO response = this.integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);
            LOG.info("INFO : Customer load from Bank CustomerDTO : {}", response);
            if (response == null) {
                LOG.error("ERROR: Customer couldn't find on Finacle. {}", searchCustomerRQ);
                return new Customer360DTO("Customer couldn't find on Finacle.");
            }

            if (response.getCustomerFinancialID() == null) {
                LOG.error("ERROR: Invalid customer response from Finacle. {} : {}", searchCustomerRQ, response);
                return new Customer360DTO("Invalid customer response from Finacle.");
            }

            customerDTO = this.updateCustomerDTO(response, credentialsDTO);
        } else {
            customerDTO = this.customerJdbcDao.getCustomerDTOBy(searchCustomerRQ);
            if (customerDTO == null || customerDTO.getCustomerFinancialID() == null) {
                CustomerDTO response = this.integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);
                LOG.info("INFO : Customer load from Bank CustomerDTO : {}", response);
                if (response == null) {
                    LOG.error("ERROR: Customer couldn't find on Finacle. {}", searchCustomerRQ);
                    return new Customer360DTO("Customer couldn't find on Finacle.");
                }

               else if (response.getCustomerFinancialID() == null) {
                    LOG.error("ERROR: Invalid customer response from Finacle. {} : {}", searchCustomerRQ, response);
                    return new Customer360DTO("Invalid customer response from Finacle.");
                }
               else {
                    customerDTO = this.updateCustomerDTO(response, credentialsDTO);
                }

            } else {
                LOG.info("INFO : Customer Retrieved from CAS DB CustomerDTO : {}", customerDTO);
            }
        }

        Customer customer = this.customerDao.getOne(customerDTO.getCustomerID());
        FacilityPaperSummarySearchRQ fpSummarySearchRQ = new FacilityPaperSummarySearchRQ();
        fpSummarySearchRQ.setCustomerID(customerDTO.getCustomerID());
        fpSummarySearchRQ.setPage(1);
        fpSummarySearchRQ.setRows(5);
        List<FacilityPaperSummaryDTO> facilityPaperSummaryList = facilityPaperJdbcDao.getCustomerFacilityPaperSummaryList(customerDTO.getCustomerID());

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomer360SearchAudit(searchCustomerRQ, credentialsDTO, new Date());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Search Customer360 :{} by :{}", searchCustomerRQ, credentialsDTO.getUserName());
        return new Customer360DTO(customer, facilityPaperSummaryList);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<FacilityPaperSummaryDTO> getPagedFacilityPaperDTO(FacilityPaperSummarySearchRQ searchRQ) {
        return facilityPaperJdbcDao.getPagedFacilityPaperDTO(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerFacilityDetailResponseDTO loadFacilityDetailList(SearchCustomerRQ searchCustomerRQ, CredentialsDTO credentialsDTO) throws AppsException {

        FacilityDetailLoadRQ facilityDetailLoadRQ = new FacilityDetailLoadRQ();
        facilityDetailLoadRQ.setAccountNumber(searchCustomerRQ.getBankAccountNumber());
        CustomerFacilityDetailResponseDTO response = this.integrationService.getFacilityDetailsByCustomerAccountNumber(facilityDetailLoadRQ, credentialsDTO);
        return response;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CustomerDTO> getActiveCustomers() throws AppsException {
        List<CustomerDTO> activeCustomerList = new ArrayList<>();

        List<Customer> allCustomerList = customerDao.findAll();

        if (!(allCustomerList.isEmpty())) {
            for (Customer customer : allCustomerList) {
                if (customer.getCustomerID() != null) {
                    if (customer.getStatus() == AppsConstants.Status.ACT) {
                        activeCustomerList.add(new CustomerDTO(customer));
                    }
                }
            }
        }
        return activeCustomerList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CustomerDTO> getCustomerDTOListByIDs(List<Integer> customerIDs) {

        List<CustomerDTO> customerDTOList = new ArrayList<>();

        for (Integer customerID : customerIDs) {
            try {
                Customer customer = customerDao.getOne(customerID);
                CustomerDTO customerDTO = new CustomerDTO(customer);
                customerDTOList.add(customerDTO);
            } catch (Exception e) {
                LOG.info("ERROR: Customer ID :{} not found", customerID, e);
            }
        }
        return customerDTOList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Customer360DTO refreshCustomerDetailFromBank(SearchCustomerRQ searchCustomerRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Customer refresh: {} by {}", searchCustomerRQ, credentialsDTO.getUserID());

        CustomerDTO localCustomerDTO = this.customerJdbcDao.getCustomerDTOBy(searchCustomerRQ);
        Customer customer = null;
        List<FacilityPaperSummaryDTO> facilityPaperSummaryList = null;
        if (localCustomerDTO != null) {
            CustomerDTO customerDTOResponse = this.integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);

            if (customerDTOResponse == null) {
                LOG.error("ERROR: Customer couldn't find on Finacle. {}", searchCustomerRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_CUSTOMER_CANNOT_FIND);
            }

            if (customerDTOResponse.getCustomerFinancialID() == null) {
                LOG.error("ERROR: Invalid customer response from Finacle. {} : {}", searchCustomerRQ, customerDTOResponse);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_INVALID_CUSTOMER_RESPONSE);
            }

            if (!customerDTOResponse.getCustomerFinancialID().equals(localCustomerDTO.getCustomerFinancialID())) {
                LOG.error("ERROR: Invalid customer Finacle Id Does not match  : Local {} : Response : {}", localCustomerDTO, customerDTOResponse);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_INVALID_CUSTOMER_RESPONSE);
            }

            customerDTOResponse.setCustomerID(localCustomerDTO.getCustomerID());

            UpdateCustomerBuilder builder = new UpdateCustomerBuilder(customerDTOResponse, credentialsDTO);
            builder.setCustomerDao(customerDao);
            builder.setWebAuditService(webAuditService);

            builder.setCasCustomerDao(casCustomerDao);
            builder.setLeadDao(leadDao);

            customer = builder.initialize()
                    .updateCustomerBasicDetail()
                    .updateAddress()
                    .updateTelephone()
                    .updateIdentification()
                    .updateBankDetail()
                    .getCustomer();

            customer = this.customerDao.saveAndFlush(customer);

            WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerAudit(new CustomerDTO(customer), localCustomerDTO, credentialsDTO, new Date(), false);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            FacilityPaperSummarySearchRQ fpSummarySearchRQ = new FacilityPaperSummarySearchRQ();
            fpSummarySearchRQ.setCustomerID(customer.getCustomerID());
            fpSummarySearchRQ.setPage(1);
            fpSummarySearchRQ.setRows(5);
            facilityPaperSummaryList = facilityPaperJdbcDao.getCustomerFacilityPaperSummaryList(customer.getCustomerID());

        } else {
            LOG.warn("WARN: customer360 no customer found for refresh {}", searchCustomerRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_CUSTOMER_CANNOT_FIND);
        }
        return new Customer360DTO(customer, facilityPaperSummaryList);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ExecuteFinacleScriptCustomDataResponseDTO getTranDetForCashFlow(CustomerAccountStatRQ customerAccountStatRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LocalDate currentDate = LocalDate.now();
        LocalDate previousYearMonth = currentDate.minusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String previousYearMonthString = previousYearMonth.format(formatter);

        CustomerTranDetCashFlowRQ customerTranDetCashFlowRQ = new CustomerTranDetCashFlowRQ();
        customerTranDetCashFlowRQ.setRequestId(customerAccountStatRQ.getCumm() + LocalDateTime.now());
        customerTranDetCashFlowRQ.setCifId(customerAccountStatRQ.getCumm());
        customerTranDetCashFlowRQ.setAsAtDate(previousYearMonthString);
        customerTranDetCashFlowRQ.setNoOfMonths(this.integrationService.getNoOfMonthsPerYear());

        ExecuteFinacleScriptCustomDataResponseDTO response = this.integrationService.getTransactionDetailsForCashFlow(customerTranDetCashFlowRQ, credentialsDTO);

        return response;
    }


//    public boolean doesCustomerExist(String customerFinancialID) {
//        Customer customer = customerDao.findByCustomerFinancialID(customerFinancialID);
//        return customer != null; // Returns true if customer is not null
//    }

    //customer covenant save
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public List<CustomerCovenant> saveCustomerCovenant(CustomerCovenantReqDTO customerCovenantReqDTO, CredentialsDTO credentialsDTO) throws AppsException {
//        List<CustomerCovenant> customerCovenantList = new ArrayList<>();
//        Date date = new Date();
//
//        List<CustomerCovenant> existingCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(customerCovenantReqDTO.getCasReference());
//
//        existingCovenants.stream()
//                .filter(covenant -> covenant.getDisplayOrder() == null)
//                .forEach(covenant -> {
//                    covenant.setDisplayOrder(0);
//                    customerCovenantDao.save(covenant);
//                });
//
//        // Determine the maximum display order
//        int maxDisplayOrder = existingCovenants.stream()
//                .mapToInt(CustomerCovenant::getDisplayOrder)
//                .max()
//                .orElse(0);
//
//        for (CustomerCovenantDetailsDTO customerCovenantDetailsDTO : customerCovenantReqDTO.getCovenantDetails()) {
//
//            CustomerCovenant customerCovenant = new CustomerCovenant();
//            customerCovenant.setRequestUUID(this.covenantRequestUuid);
//            customerCovenant.setCreatedBy(credentialsDTO.getUserName());
//            customerCovenant.setCreatedUserDisplayName(customerCovenantReqDTO.getCreatedUserDisplayName());
//            customerCovenant.setCreatedDate(date);
//            customerCovenant.setCustomerFinancialID(customerCovenantReqDTO.getCustId());
//            customerCovenant.setDisbursementType(customerCovenantReqDTO.getDisbursementType());
//            customerCovenant.setApplicableType(customerCovenantDetailsDTO.getApplicableType());
//
//            Customer customer = customerDao.findCustomerByFinancialID(customerCovenantReqDTO.getCustId());
//            if (customer == null) {
//                Customer newCustomer = new Customer();
//                newCustomer.setCustomerFinancialID(customerCovenantReqDTO.getCustId());
//                customer = customerDao.save(newCustomer);
//
//                customerCovenant.setCustomerFinancialID(newCustomer.getCustomerFinancialID());
//            } else {
//                customerCovenant.setCustomerFinancialID(customer.getCustomerFinancialID());
//            }
//
//            FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(customerCovenantReqDTO.getCasReference());
//
//            if (facilityPaper == null) {
//                FacilityPaper newFacilityPaper = new FacilityPaper();
//                newFacilityPaper.setFpRefNumber(customerCovenantReqDTO.getCasReference());
//               newFacilityPaper.setFacilityPaperID(customerCovenantReqDTO.getFacilityPaperID());
//                facilityPaper = facilityPaperDao.save(newFacilityPaper);
//            }
//
//            customerCovenant.setFacilityPaper(facilityPaper);
//
//            customerCovenant.setCovenant_Code(customerCovenantDetailsDTO.getCovenant_Code());
//            customerCovenant.setCovenant_Description(customerCovenantDetailsDTO.getCovenant_Description());
//            customerCovenant.setCovenant_Frequency(customerCovenantDetailsDTO.getCovenant_Frequency());
//            customerCovenant.setDisbursementType(customerCovenantDetailsDTO.getDisbursementType());
//            customerCovenant.setCovenant_Due_Date(customerCovenantDetailsDTO.getCovenant_Due_Date());
//            customerCovenant.setNoFrequencyDueDate(customerCovenantDetailsDTO.getNoFrequencyDueDate());
//            customerCovenant.setApplicableType(customerCovenantDetailsDTO.getApplicableType());
//
//            customerCovenant.setStatus(AppsConstants.CovenantStatus.Active);
//            customerCovenant.setIsExists(AppsConstants.YesNo.N);
//            customerCovenant.setDisplayOrder(++maxDisplayOrder);
//
//            customerCovenantList.add(customerCovenant);
//
//
//        }
//        LOG.info("Covenant save", customerCovenantList);
//
//        return customerCovenantDao.saveAll(customerCovenantList);
//    }
//
//    public CustomerCovenant updateCustomerCovenant(CustomerCovenantUpdateDTO customerCovenantUpdateDTO){
//
//        Date date = new Date();
//
//        CustomerCovenant customerCovenantDb = customerCovenantDao.findById(customerCovenantUpdateDTO.getCustomerCovenantId()).orElseThrow(() ->new RuntimeException("Customer covenant with " + customerCovenantUpdateDTO.getCustomerCovenantId() + " does not exist"));
//
//        for(CustomerCovenantDetailsDTO customerCovenantDetailsDTO : customerCovenantUpdateDTO.getCovenantDetails()){
//            customerCovenantDb.setRequestUUID(customerCovenantUpdateDTO.getRequestUUID());
//            customerCovenantDb.setCustomerFinancialID(customerCovenantUpdateDTO.getCustId());
//            customerCovenantDb.setDisbursementType(customerCovenantUpdateDTO.getDisbursementType());
//
////            Customer customer = customerDao.findByCustomerFinancialID(customerCovenantUpdateDTO.getCustId());
////            customerCovenantDb.setCustomerFinancialID(customer.getCustomerFinancialID());
//
//            FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(customerCovenantUpdateDTO.getCasReference());
//            customerCovenantDb.setFacilityPaper(facilityPaper);
//
//            customerCovenantDb.setCreatedDate(date);
//
//            customerCovenantDb.setCovenant_Code(customerCovenantDetailsDTO.getCovenant_Code());
//            customerCovenantDb.setCovenant_Description(customerCovenantDetailsDTO.getCovenant_Description());
//            customerCovenantDb.setCovenant_Frequency(customerCovenantDetailsDTO.getCovenant_Frequency());
//            customerCovenantDb.setCovenant_Due_Date(customerCovenantDetailsDTO.getCovenant_Due_Date());
//            customerCovenantDb.setDisbursementType(customerCovenantDetailsDTO.getDisbursementType());
//            customerCovenantDb.setNoFrequencyDueDate(customerCovenantDetailsDTO.getNoFrequencyDueDate());
//            customerCovenantDb.setApplicableType(customerCovenantDetailsDTO.getApplicableType());
//
//            customerCovenantDb.setCreatedUserDisplayName(customerCovenantUpdateDTO.getCreatedUserDisplayName());
//        }
//
//
//        customerCovenantDb.setStatus(AppsConstants.CovenantStatus.Active);
//        return customerCovenantDao.saveAndFlush(customerCovenantDb);
//    }
//
//
//
//
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public List<CustomerCovenantResponseDTO> getCovenantList(String fpRefNumber, CredentialsDTO credentialsDTO) throws AppsException {
//        List<CustomerCovenant> customerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);
//
////        if (customerCovenants == null) {
////            customerCovenants = new ArrayList<>();
////        }
//
//        List<CustomerCovenantResponseDTO> customerCovenantResponseDTOList = new ArrayList<>();
//
////        if(customerCovenants.isEmpty()){
////            customerCovenants = customerCovenants.stream()
////                    .sorted(Comparator.comparing(
////                            CustomerCovenant::getDisplayOrder))
////                    .collect(Collectors.toList());
////        }
//
//
//        for(CustomerCovenant customerCovenant: customerCovenants){
//            CustomerCovenantResponseDTO customerCovenantResponseDTO = new CustomerCovenantResponseDTO();
//            customerCovenantResponseDTO.setCustomerCovenantId(customerCovenant.getCustomerCovenantId());
//            customerCovenantResponseDTO.setRequestUUID(customerCovenant.getRequestUUID());
//            customerCovenantResponseDTO.setCustomerFinancialID(customerCovenant.getCustomerFinancialID());
//            customerCovenantResponseDTO.setFpRefNumber(customerCovenant.getFacilityPaper().getFacilityPaperNumber());
//            customerCovenantResponseDTO.setDisbursementType(customerCovenant.getDisbursementType());
//
//            customerCovenantResponseDTO.setCovenant_Code(customerCovenant.getCovenant_Code());
//            customerCovenantResponseDTO.setCovenant_Description(customerCovenant.getCovenant_Description());
//            customerCovenantResponseDTO.setCovenant_Frequency(customerCovenant.getCovenant_Frequency());
//            customerCovenantResponseDTO.setCovenant_Due_Date(customerCovenant.getCovenant_Due_Date());
//            customerCovenantResponseDTO.setCreatedBy(customerCovenant.getCreatedBy());
//            customerCovenantResponseDTO.setCreatedUserDisplayName(customerCovenant.getCreatedUserDisplayName());
//            customerCovenantResponseDTO.setCreatedDate(customerCovenant.getCreatedDate());
//            customerCovenantResponseDTO.setStatus(customerCovenant.getStatus());
//            customerCovenantResponseDTO.setWorkClass(String.valueOf(securityService.getUserUPMDetails(customerCovenant.getCreatedBy()).getApplicationSecurityClass()));
//            customerCovenantResponseDTO.setNoFrequencyDueDate(customerCovenant.getNoFrequencyDueDate());
//            customerCovenantResponseDTO.setIsExists(customerCovenant.getIsExists());
//            customerCovenantResponseDTO.setComplianceStatus(customerCovenant.getComplianceStatus());
//            customerCovenantResponseDTO.setDisplayOrder(customerCovenant.getDisplayOrder());
//            customerCovenantResponseDTO.setApplicableType(customerCovenant.getApplicableType());
//
//            customerCovenantResponseDTOList.add(customerCovenantResponseDTO);
//        }
//
//        customerCovenantResponseDTOList.sort(Comparator.comparing(CustomerCovenantResponseDTO::getDisplayOrder));
//
//        return customerCovenantResponseDTOList;
//    }
//
//
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public CovenantDetailResDTO getCovenantResponse(String fpRefNumber, CredentialsDTO credentialsDTO) throws Exception {
//        List<CustomerCovenant> customerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);
//
//        CovenantDetailResDTO response = new CovenantDetailResDTO();
//        response.setCasReference(fpRefNumber);
//
//        if (!customerCovenants.isEmpty()) {
//            CustomerCovenant firstCovenant = customerCovenants.get(0);
//
//                response.setRequestUUID(firstCovenant.getRequestUUID());
//                response.setCustId(firstCovenant.getCustomerFinancialID());
//
//                List<CustomerCovenantDetailsReqDTO> covenantDetails = new ArrayList<>();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Define date format
//
//                for (CustomerCovenant covenant : customerCovenants) {
//                    if("Active".equals(covenant.getStatus().toString()) && covenant.getDisbursementType().equals(AppsConstants.CovenantStatusOnDisbursement.POST)){
//                    CustomerCovenantDetailsReqDTO detail = new CustomerCovenantDetailsReqDTO();
//                    detail.setCovenant_Code(covenant.getCovenant_Code());
//                    detail.setCovenant_Frequency(covenant.getCovenant_Frequency());
//                    //detail.setCovenant_Due_Date(dateFormat.format(covenant.getCovenant_Due_Date()));
//                    detail.setCovenant_Description(covenant.getCovenant_Description());
//
////                    if ("C_OTH".equals(covenant.getCovenant_Code())) {
////                        detail.setCovenant_Description(covenant.getCovenant_Description());
////                    }
////                    else {
////                        detail.setCovenant_Description("");
////                    }
//
//                    if(covenant.getCovenant_Due_Date() != null){
//                        detail.setCovenant_Due_Date(dateFormat.format(covenant.getCovenant_Due_Date()));
//                    } else {
//                        detail.setCovenant_Due_Date(null);
//                    }
//                    covenantDetails.add(detail);
//                }
//                response.setREL(covenantDetails);
//            }
//
////            getCovenantDetailsFromFinacle(firstCovenant.getCustomerFinancialID(), fpRefNumber, credentialsDTO);
//
//        }
//        LOG.info("END : GET covenant details {}", response);
//        return response;
//    }
//
//
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public Integer deleteCovenant(Integer customerCovenantId, String createdUserDisplayName, CredentialsDTO credentialsDTO) throws AppsException {
//        CustomerCovenant customerCovenant = customerCovenantDao.findById(customerCovenantId).orElseThrow(() -> new AppsException("Covenant with " + customerCovenantId + " does not exist."));
//        Date date = new Date();
//        customerCovenant.setStatus(AppsConstants.CovenantStatus.Inactive);
//        customerCovenant.setCreatedDate(date);
//        customerCovenant.setCreatedBy(credentialsDTO.getUserName());
//        customerCovenant.setCreatedUserDisplayName(createdUserDisplayName);
//        return customerCovenant.getCustomerCovenantId();
//    }
//
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public List<CustomerEvaluationListDTO> getCustomerEvaluationListById(String customerId, CredentialsDTO credentialsDTO) throws AppsException {
//        List<CustomerEvaluationListDTO> evaluationList = new ArrayList<>();
//
//        List<CustomerEvaluationListDTO> listTwo = customerJdbcDao.getCustomerEvaluationListById(customerId);
//
//        for (CustomerEvaluationListDTO customerEvaluationDTO : listTwo) {
//            evaluationList.add(customerEvaluationDTO);
//        }
//
//        LOG.info("END: Service Response", evaluationList);
//        return evaluationList;
//
//    }

    //evaluation display in upc template page
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CustomerEvaluationDTO> getCustomerEvaluationById(String customerId, Integer customerEvaluationId, CredentialsDTO credentialsDTO) throws AppsException {
        List<CustomerEvaluationDTO> evaluationList = new ArrayList<>();

        List<CustomerEvaluationDTO> listTwo = customerJdbcDao.getCustomerEvaluationById(customerId, customerEvaluationId);

        LOG.info("listTwo {}", listTwo);

        HashMap<Integer, CustomerEvaluationDTO> mapEva = new HashMap<Integer, CustomerEvaluationDTO>();
        List<CustomerEvaluationDTO> listFnl = new ArrayList<CustomerEvaluationDTO>();
        for (CustomerEvaluationDTO ce : listTwo) {
            mapEva.put(ce.getEvaluationElementId(), ce);
        }
        for (CustomerEvaluationDTO ce : listTwo) {
            LOG.info("Parent Id ", ce.getParentId());
            if (ce.getParentId() == 0) {
                listFnl.add(ce);
            } else {
                if (mapEva.get(ce.getParentId()).getLstEva() == null) {
                    mapEva.get(ce.getParentId()).setLstEva(new ArrayList<CustomerEvaluationDTO>());

                }
                mapEva.get(ce.getParentId()).getLstEva().add(ce);

                LOG.info("function data {}", mapEva.get(ce.getParentId()).getLstEva());
            }
        }

        for (CustomerEvaluationDTO customerEvaluationDTO : listTwo) {
            evaluationList.add(customerEvaluationDTO);
            //boolean answered = customerEvaluationDTO.Answered();
        }

        return listFnl;
    }


    //new
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEvaluationAnswersDTO getAnswers(SearchCustomerEvaluationRQ searchRQ) {
        Page<CustomerEvaluationAnswersDTO> customerEvaluationAnswersDTOPage = customerJdbcDao.getCustomerEvaluationDashboardDTO(searchRQ);
        ArrayList<CustomerEvaluationAnswersDTO> data = new ArrayList<>(customerEvaluationAnswersDTOPage.getPageData());
        if (!data.isEmpty()) {
            return data.get(0);
        } else {
            return null;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CustomerEvaluationScoreDTO> getCustomerEvaluationDetails(String customerId, Integer customerEvaluationId, CredentialsDTO credentialsDTO) throws AppsException {
        List<CustomerEvaluationScoreDTO> evaluationList = new ArrayList<>();

        List<CustomerEvaluationScoreDTO> listTwo = customerJdbcDao.getCustomerEvaluationDetails(customerId, customerEvaluationId);

        for (CustomerEvaluationScoreDTO customerEvaluationDTO : listTwo) {
            evaluationList.add(customerEvaluationDTO);
        }

        LOG.info("END: Service Response", evaluationList);
        return evaluationList;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CustomerEvaluation> getCustomerEvaluationList() {

        return customerEvaluationDao.findAll();
    }

}
