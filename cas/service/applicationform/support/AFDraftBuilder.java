package com.itechro.cas.service.applicationform.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.applicationform.AFCustomerDao;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.dao.applicationform.jdbc.ApplicationFormJdbcDao;
import com.itechro.cas.dao.casmaster.AFTopicConfigDao;
import com.itechro.cas.dao.casmaster.AFTopicDao;
import com.itechro.cas.dao.casmaster.WorkFlowTemplateDao;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.customer.jdbc.CustomerJdbcDao;
import com.itechro.cas.dao.lead.LeadDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.AppsRuntimeException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomer;
import com.itechro.cas.model.domain.casmaster.AFTopic;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.dto.applicationform.*;
import com.itechro.cas.model.dto.customer.CustomerCribLiabilityDTO;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.customer.SearchCustomerRQ;
import com.itechro.cas.model.dto.lead.LeadIdentificationDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.ApplicationFormService;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AFDraftBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(AFDraftBuilder.class);

    private static final String AGENT_LEAD_FACILITY_DEFAULT_WF_TEMPLATE_NAME = "DEFAULT_001";

    private ApplicationForm applicationForm;

    private DraftApplicationFormRQ draftApplicationFormRQ;

    private CredentialsDTO credentialsDTO;

    private Date date;

    private ApplicationFormService applicationFormService;

    private IntegrationService integrationService;

    private CustomerService customerService;

    private CustomerDao customerDao;

    private AFCustomerDao afCustomerDao;

    private ApplicationFormDao applicationFormDao;

    private AFTopicDao afTopicDao;

    private AFTopicConfigDao afTopicConfigDao;

    private WorkFlowTemplateDao workFlowTemplateDao;

    private CustomerJdbcDao customerJdbcDao;

    private LeadDao leadDao;

    private ApplicationFormJdbcDao applicationFormJdbcDao;

    public AFDraftBuilder(DraftApplicationFormRQ draftApplicationFormRQ, CredentialsDTO credentialsDTO) {
        this.draftApplicationFormRQ = draftApplicationFormRQ;
        this.credentialsDTO = credentialsDTO;
        this.date = new Date();
    }

    public AFDraftBuilder buildBaseApplicationForm() throws AppsException {

        boolean isNew = draftApplicationFormRQ.getApplicationFormID() == null;
        if (isNew) {
            applicationForm = new ApplicationForm();
            applicationForm.setAfRefNumber(this.applicationFormService.getApplicationFormRef());
            applicationForm.setCreatedDate(date);
            applicationForm.setCreatedBy(credentialsDTO.getUserName());
            applicationForm.setCreatedUserDisplayName(draftApplicationFormRQ.getCreatedUserDisplayName());
            applicationForm.setCreatedUserID(draftApplicationFormRQ.getCreatedUserID());
            applicationForm.setFormType(draftApplicationFormRQ.getFormType());
        } else {
            applicationForm = applicationFormDao.getOne(draftApplicationFormRQ.getApplicationFormID());
            applicationForm.setModifiedDate(date);
            applicationForm.setModifiedBy(credentialsDTO.getUserName());
        }

        applicationForm.setLeadID(draftApplicationFormRQ.getLeadID());
        applicationForm.setFsType(draftApplicationFormRQ.getFsType());
        applicationForm.setBranchCode(draftApplicationFormRQ.getBranchCode());
        applicationForm.setCurrentApplicationFormStatus(draftApplicationFormRQ.getCurrentApplicationFormStatus());
        applicationForm.setAssignUserID(draftApplicationFormRQ.getAssignUserID());
        applicationForm.setAssignUser(draftApplicationFormRQ.getAssignUser());
        applicationForm.setAssignUserDisplayName(draftApplicationFormRQ.getAssignUserDisplayName());
        applicationForm.setAssignUserUpmID(draftApplicationFormRQ.getAssignUserUpmID());
        applicationForm.setAssignUserDivCode(draftApplicationFormRQ.getAssignUserDivCode());
        applicationForm.setAssignUserUpmGroupCode(draftApplicationFormRQ.getAssignUserUpmGroupCode());

        WorkFlowTemplate workFlowTemplate = this.workFlowTemplateDao.findByCodeAndStatusAndApproveStatus(
                AGENT_LEAD_FACILITY_DEFAULT_WF_TEMPLATE_NAME,
                AppsConstants.Status.ACT,
                DomainConstants.MasterDataApproveStatus.APPROVED
        );

        if (workFlowTemplate == null) {
            LOG.info("ERROR  workflow template is not defined for {}", draftApplicationFormRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_AGENT_FACILITY_DEFAULT_WF_NOT_FOUND);
        }
        applicationForm.setWorkFlowTemplate(workFlowTemplate);
        LOG.info("START : Build Base Application Form : {} : {}", draftApplicationFormRQ, credentialsDTO.getUserID());
        return this;
    }

    public AFDraftBuilder buildComment() throws AppsException {
        for (AFCommentDTO afCommentDTO : draftApplicationFormRQ.getAfCommentDTOList()) {
            boolean isNewComment = afCommentDTO.getCommentID() == null;
            if (isNewComment) {
                AFComment afComment = new AFComment();
                afComment.setActionMessage("Draft Application From by " + draftApplicationFormRQ.getAssignUserDisplayName());
                afComment.setUserComment(afCommentDTO.getComment());
                afComment.setCurrentApplicationFormStatus(afCommentDTO.getCurrentApplicationFormStatus());

                afComment.setCreatedUserID(afCommentDTO.getCreatedUserID());
                afComment.setCreatedUser(afCommentDTO.getCreatedUser());
                afComment.setCreatedUserDisplayName(afCommentDTO.getCreatedUserDisplayName());
                afComment.setCreatedUserDivCode(afCommentDTO.getCreatedUserDivCode());
                afComment.setCreatedUserUpmCode(afCommentDTO.getCreatedUserUpmCode());

                afComment.setIsPublic(afCommentDTO.getIsPublic());
                afComment.setIsUsersOnly(afCommentDTO.getIsUsersOnly());
                afComment.setIsDivisionOnly(afCommentDTO.getIsDivisionOnly());

                afComment.setStatus(AppsConstants.Status.ACT);
                afComment.setCreatedBy(credentialsDTO.getUserName());
                afComment.setCreatedDate(date);
                applicationForm.addAFComment(afComment);
            }
        }
        LOG.info("START : Build Comments Application Form : {} : {}", draftApplicationFormRQ, credentialsDTO.getUserID());
        return this;
    }

//    public AFDraftBuilder buildBasicInformation() throws AppsException {
//
//        for (BasicInformationDTO basicInformationDTO : draftApplicationFormRQ.getBasicInformationDTOList()) {
//            boolean isNewBasicInformation = basicInformationDTO.getBasicInformationID() == null;
//            if (isNewBasicInformation) {
//                BasicInformation basicInformation = new BasicInformation();
//                basicInformation.setIdentificationNo(basicInformationDTO.getIdentificationNo());
//
//                String identificationType = basicInformationDTO.getIdentificationType().toString();
//                if (identificationType.contains("NIC")){
//                    basicInformation.setIdentificationType(DomainConstants.CustomerIdentificationType.NIC);
//                } else {
//                    basicInformation.setIdentificationType(basicInformationDTO.getIdentificationType());
//                }
//                basicInformation.setPrimaryInformation(AppsConstants.YesNo.Y);
//                basicInformation.setType(basicInformationDTO.getType());
//                basicInformation.setStatus(AppsConstants.Status.ACT);
//                basicInformation.setCreatedDate(date);
//                basicInformation.setCreatedBy(credentialsDTO.getUserName());
//
//                SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
//                searchCustomerRQ.setIdentificationNumber(basicInformationDTO.getIdentificationNo());
//                searchCustomerRQ.setIdentificationType(basicInformationDTO.getIdentificationType().name());
//
//                try {
//                    CustomerDTO customerDTO = integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);
//
//                    Customer customer;
//                    if (customerDTO != null) {
//                        customerDTO = customerService.updateCustomerDTO(customerDTO, credentialsDTO);
//                        LOG.info("INFO : Customer DTO : Form CAS DB after Updating with Finacle Customer : {} : Finacle  Customer ID : {}", customerDTO, customerDTO.getCustomerFinancialID());
//                        customer = customerDao.getOne(customerDTO.getCustomerID());
//                    } else {
//                        customer = new Customer(); // to Proceed without Finacle Customer
//                        LOG.warn("WARN : Application From Proceed without Finacle Customer : {} ", searchCustomerRQ);
//                    }
//
//                    AFCustomerBuilder afCustomerBuilder = new AFCustomerBuilder(credentialsDTO);
//                    afCustomerBuilder.setCustomer(customer);
//                    afCustomerBuilder.setDate(date);
//
//                    AFCustomer afCustomer = afCustomerBuilder.buildInitialAFCustomer()
//                            .buildAfCustomerAddress()
//                            .buildAfCustomerTelephones()
//                            .buildAFCustomerIdentifications()
//                            .buildAFBankDetails()
//                            .getAfCustomer();
//
//                    basicInformation.setCustomerID(customer.getCustomerID());
//                    basicInformation.setAfCustomer(afCustomerDao.saveAndFlush(afCustomer));
//                } catch (Exception e) {
//                    LOG.error("ERROR : Application Form Set Finacle Customer with CAS Customer : {}", searchCustomerRQ, e);
//                }
//
//                for (AFCribReportDTO afCribReportDTO : basicInformationDTO.getAfCribReportDTOList()) {
//                    boolean isNewCribReport = afCribReportDTO.getCribReportID() == null;
//                    if (isNewCribReport) {
//                        AFCribReport afCribReport = new AFCribReport();
//                        if (StringUtils.isNotBlank(afCribReportDTO.getCribDateStr())) {
//                            afCribReport.setCribDate(CalendarUtil.getDefaultParsedDateOnly(afCribReportDTO.getCribDateStr()));
//                        }
//                        afCribReport.setCribStatus(afCribReportDTO.getCribStatus());
//                        afCribReport.setIdentificationNo(afCribReportDTO.getIdentificationNo());
//                        afCribReport.setIdentificationType(afCribReportDTO.getIdentificationType());
//                        afCribReport.setRemark(afCribReportDTO.getRemark());
//                        afCribReport.setReportContent(afCribReportDTO.getReportContent());
//                        afCribReport.setStatus(AppsConstants.Status.ACT);
//                        afCribReport.setCreatedBy(credentialsDTO.getUserName());
//                        afCribReport.setCreatedDate(date);
//                        basicInformation.addCribReport(afCribReport);
//
//                        if (afCribReportDTO.getCribStatus() != DomainConstants.CribStatusType.SKIP_CRIB_REPORT) {
//                            List<CustomerCribLiabilityDTO> customerCribLiabilityDTOS = customerJdbcDao.getCustomerCribLiabilitiesList(afCribReportDTO);
//                            for (CustomerCribLiabilityDTO customerCribLiabilityDTO : customerCribLiabilityDTOS) {
//                                AFFinancialObligation afFinancialObligation = new AFFinancialObligation();
//                                afFinancialObligation.setIsCribRecord(AppsConstants.YesNo.Y);
//                                afFinancialObligation.setCustomerCribLiabilityID(customerCribLiabilityDTO.getCustomerCribLiabilityID());
//                                afFinancialObligation.setCustomerCribResponseID(customerCribLiabilityDTO.getCustomerCribResponseID());
//                                afFinancialObligation.setIdentificationType(customerCribLiabilityDTO.getIdentificationType());
//                                afFinancialObligation.setIdentificationNumber(customerCribLiabilityDTO.getIdentificationNumber());
//                                afFinancialObligation.setFinancialInstitution(customerCribLiabilityDTO.getFinancialInstitution());
//                                afFinancialObligation.setOriginalAmount(new BigDecimal(customerCribLiabilityDTO.getOriginalAmount().replaceAll(",", "")));
//                                afFinancialObligation.setPresentOutstanding(new BigDecimal(customerCribLiabilityDTO.getPresentOutstanding().replaceAll(",", "")));
//                                afFinancialObligation.setArrears(new BigDecimal(customerCribLiabilityDTO.getArrears().replaceAll(",", "")));
//                                afFinancialObligation.setSignedAs(customerCribLiabilityDTO.getSignedAs());
//                                afFinancialObligation.setSecurities(customerCribLiabilityDTO.getSecurities());
//                                afFinancialObligation.setModifiedUserDisplayName(customerCribLiabilityDTO.getModifiedUserDisplayName());
//                                afFinancialObligation.setStatus(customerCribLiabilityDTO.getStatus());
//                                afFinancialObligation.setCreatedBy(credentialsDTO.getUserName());
//                                afFinancialObligation.setCreatedDate(date);
//                                basicInformation.addAFFinancialObligation(afFinancialObligation);
//                            }
//                        }
//                    }
//                }
//                applicationForm.addBasicInformation(basicInformation);
//            }
//        }
//        LOG.info("START : Build Basic Information Application Form : {} : {}", draftApplicationFormRQ, credentialsDTO.getUserID());
//        return this;
//    }

    public AFDraftBuilder buildBasicInformation() throws AppsException {

        for (BasicInformationDTO basicInformationDTO : draftApplicationFormRQ.getBasicInformationDTOList()) {

            boolean isNewBasicInformation = basicInformationDTO.getBasicInformationID() == null;

            if (isNewBasicInformation) {

                // =========================================
                // HANDLE COMP LEAD IDENTIFICATIONS
                // =========================================
                if (draftApplicationFormRQ.getLeadID() != null) {

                    Lead lead = leadDao.findById(draftApplicationFormRQ.getLeadID())
                            .orElseThrow(() -> new AppsRuntimeException(
                                    "Lead not found for the given Lead ID : "
                                            + draftApplicationFormRQ.getLeadID()));

                    if (AppsConstants.YesNo.Y.toString().equals(lead.getIsCompLead())) {

                        //Get comp parties
                        List<CompPartyDataDTO> parties = applicationFormJdbcDao.findPartyByLeadId(draftApplicationFormRQ.getLeadID());

                        if (parties != null && !parties.isEmpty()) {
                            String leadPurpose = parties.get(0).getLeadPurpose();
                            LOG.info("INFO : Lead Purpose for Comp Lead : {} : Lead Purpose : {}", draftApplicationFormRQ.getLeadID(), leadPurpose);
                            List<LeadIdentificationDTO> leadIdentificationDTOList = new ArrayList<>();

                            if (leadPurpose.equals("INDIVIDUAL")) {
                                LOG.info("INFO : Processing Comp Lead as Individual for Lead ID : {} : Total Parties : {}", draftApplicationFormRQ.getLeadID(), parties.size());
                                Integer partyId = parties.get(0).getCompPartyId();
                                leadIdentificationDTOList = leadDao.findIdentificationsByPartyId(partyId);

                                addBasicInformationToApplication(leadIdentificationDTOList, basicInformationDTO, true);

                            } else if (leadPurpose.equals("LIMITED_LIABILITY")) {
                                LOG.info("INFO : Processing Comp Lead as LLC for Lead ID : {} : Total Parties : {}", draftApplicationFormRQ.getLeadID(), parties.size());
                                boolean isPrimary = false;
                                for (CompPartyDataDTO partyDataDTO : sortBorrowerParties(parties)) {
                                    if (!isPrimary) {
                                        isPrimary = partyDataDTO.getPartyType().equals("0");
                                        leadIdentificationDTOList = leadDao.findIdentificationsByPartyId(partyDataDTO.getCompPartyId());
                                        addBasicInformationToApplication(leadIdentificationDTOList, basicInformationDTO, true);
                                    }
                                }
                            } else {
                                LOG.info("INFO : Processing Comp Lead as Company for Lead ID : {} : Total Parties : {}", draftApplicationFormRQ.getLeadID(), parties.size());
                                boolean isPrimary = false;
                                for (CompPartyDataDTO partyDataDTO : parties) {
                                    leadIdentificationDTOList = leadDao.findIdentificationsByPartyId(partyDataDTO.getCompPartyId());
                                    if (!isPrimary) {
                                        isPrimary = partyDataDTO.getPartyType().equals("0");
                                        addBasicInformationToApplication(leadIdentificationDTOList, basicInformationDTO, true);
                                    }else {
                                        addBasicInformationToApplication(leadIdentificationDTOList, basicInformationDTO, false);
                                    }

                                }
                            }

                            // Skip default flow since comp lead handled
                            continue;
                        }
                    }
                }

                // =========================================
                // NORMAL BASIC INFORMATION FLOW
                // =========================================

                BasicInformation basicInformation = new BasicInformation();
                basicInformation.setIdentificationNo(basicInformationDTO.getIdentificationNo());
                basicInformation.setIdentificationType(basicInformationDTO.getIdentificationType());
                basicInformation.setPrimaryInformation(AppsConstants.YesNo.Y);
                basicInformation.setType(basicInformationDTO.getType());
                basicInformation.setStatus(AppsConstants.Status.ACT);
                basicInformation.setCreatedDate(date);
                basicInformation.setCreatedBy(credentialsDTO.getUserName());

                SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
                searchCustomerRQ.setIdentificationNumber(
                        basicInformationDTO.getIdentificationNo());
                searchCustomerRQ.setIdentificationType(
                        basicInformationDTO.getIdentificationType().name());

                try {

                    CustomerDTO customerDTO =
                            integrationService.getCustomerDetailFromBank(
                                    searchCustomerRQ, credentialsDTO);

                    Customer customer;

                    if (customerDTO != null) {
                        customerDTO =
                                customerService.updateCustomerDTO(
                                        customerDTO, credentialsDTO);

                        customer =
                                customerDao.getOne(customerDTO.getCustomerID());
                    } else {
                        customer = new Customer();
                    }

                    AFCustomerBuilder afCustomerBuilder =
                            new AFCustomerBuilder(credentialsDTO);

                    afCustomerBuilder.setCustomer(customer);
                    afCustomerBuilder.setDate(date);

                    AFCustomer afCustomer =
                            afCustomerBuilder.buildInitialAFCustomer()
                                    .buildAfCustomerAddress()
                                    .buildAfCustomerTelephones()
                                    .buildAFCustomerIdentifications()
                                    .buildAFBankDetails()
                                    .getAfCustomer();

                    basicInformation.setCustomerID(customer.getCustomerID());
                    basicInformation.setAfCustomer(
                            afCustomerDao.saveAndFlush(afCustomer));

                } catch (Exception e) {
                    LOG.error("ERROR : Application Form Customer Integration : {}",
                            searchCustomerRQ, e);
                }

                applicationForm.addBasicInformation(basicInformation);
            }
        }

        LOG.info("START : Build Basic Information Application Form : {} : {}",
                draftApplicationFormRQ, credentialsDTO.getUserID());

        return this;
    }

    private CustomerDTO getCustomerData(LeadIdentificationDTO leadIdentificationDTO){

        CustomerDTO customerDTO = new CustomerDTO();

        String identificationType = leadIdentificationDTO.getIdentificationType();

        SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
        searchCustomerRQ.setIdentificationNumber(
                leadIdentificationDTO.getIdentificationNumber());
        if (identificationType.contains("NIC")) {
            searchCustomerRQ.setIdentificationType(DomainConstants.CustomerIdentificationType.NIC.toString());
        } else {
            searchCustomerRQ.setIdentificationType(leadIdentificationDTO.getIdentificationType());
        }

        try {
             customerDTO = integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);

        } catch (Exception e) {
            LOG.error("ERROR : Customer Check Failed : {}", searchCustomerRQ, e);
        }

        return customerDTO;
    }

    private BasicInformation prepareBasicInformation(BasicInformationDTO basicInformationDTO, LeadIdentificationDTO leadIdentificationDTO, boolean isPrimary){
        BasicInformation basicInformation = new BasicInformation();
        basicInformation.setIdentificationNo(leadIdentificationDTO.getIdentificationNumber());
        String identificationType = leadIdentificationDTO.getIdentificationType();
        if (identificationType.contains("NIC")) {
            basicInformation.setIdentificationType(
                    DomainConstants.CustomerIdentificationType.NIC);
        } else {
            basicInformation.setIdentificationType(
                    DomainConstants.CustomerIdentificationType
                            .valueOf(
                                    leadIdentificationDTO
                                            .getIdentificationType()));
        }

        if (isPrimary) {
            basicInformation.setPrimaryInformation(AppsConstants.YesNo.Y);
        } else {
            basicInformation.setPrimaryInformation(AppsConstants.YesNo.N);
        }
        basicInformation.setType(basicInformationDTO.getType());
        basicInformation.setStatus(AppsConstants.Status.ACT);
        basicInformation.setCreatedDate(date);
        basicInformation.setCreatedBy(credentialsDTO.getUserName());

        return basicInformation;
    }

    private List<CompPartyDataDTO> sortBorrowerParties(List<CompPartyDataDTO> parties){
        return parties.stream().filter(party -> party.getPartyType().equals("0")).collect(Collectors.toList());
    }

    private void addBasicInformationToApplication(List<LeadIdentificationDTO> leadIdentificationDTOList, BasicInformationDTO basicInformationDTO, boolean isPrimary) {

        LOG.info("INFO : Lead Identification List for Lead ID : {} ", draftApplicationFormRQ.getLeadID());

        if (leadIdentificationDTOList == null || leadIdentificationDTOList.isEmpty()) {
            return;
        }

        LOG.info("INFO : Processing Lead Identifications for Lead ID : {} : Total Lead Identifications : {}", draftApplicationFormRQ.getLeadID(), leadIdentificationDTOList.size());

        boolean customerFound = false;

        for (LeadIdentificationDTO leadIdentificationDTO : leadIdentificationDTOList) {

            CustomerDTO customerDTO = getCustomerData(leadIdentificationDTO);

            if (customerDTO != null && customerDTO.getCustomerFinancialID() != null) {

                customerFound = true;

                try {
                    BasicInformation basicInformation = prepareBasicInformation(basicInformationDTO, leadIdentificationDTO, isPrimary);

                    customerDTO = customerService.updateCustomerDTO(customerDTO, credentialsDTO);
                    Customer customer = customerDao.getOne(customerDTO.getCustomerID());

                    AFCustomerBuilder afCustomerBuilder = new AFCustomerBuilder(credentialsDTO);

                    afCustomerBuilder.setCustomer(customer);
                    afCustomerBuilder.setDate(date);

                    AFCustomer afCustomer = afCustomerBuilder.buildInitialAFCustomer()
                                    .buildAfCustomerAddress()
                                    .buildAfCustomerTelephones()
                                    .buildAFCustomerIdentifications()
                                    .buildAFBankDetails()
                                    .getAfCustomer();

                    basicInformation.setCustomerID(customer.getCustomerID());
                    basicInformation.setAfCustomer(afCustomerDao.saveAndFlush(afCustomer));

                    applicationForm.addBasicInformation(basicInformation);

                    break;

                } catch (Exception e) {
                    LOG.error("ERROR : Customer Integration Failed ", e);
                }
            } else {
                LOG.warn("WARN : No Finacle Customer found for Lead Identification : {} ",
                        leadIdentificationDTO);
            }
        }

        if (!customerFound) {
            throw new AppsRuntimeException(
                    "Application Form Cannot Create without Finacle Customer.");
        }
    }


    public AFDraftBuilder buildDefaultTopics() throws AppsException {
        for (AFTopicConfig afTopicConfig : afTopicConfigDao.findAllByStatus(AppsConstants.Status.ACT)) {
            AFTopicData afTopicData = new AFTopicData();
            AFTopic afTopic = afTopicDao.findByTopicCodeAndStatusAndApproveStatus(afTopicConfig.getTopicCode(), AppsConstants.Status.ACT, DomainConstants.MasterDataApproveStatus.APPROVED);
            if (afTopic != null) {
                afTopicData.setStatus(afTopicConfig.getStatus());
                afTopicData.setTopicCode(afTopicConfig.getTopicCode());
                afTopicData.setPage(afTopic.getPage());
                afTopicData.setTopic(afTopic);
                afTopicData.setTopicData(afTopic.getTopicData());
                afTopicData.setCreatedBy(credentialsDTO.getUserName());
                afTopicData.setCreatedDate(new Date());
                applicationForm.addAFTopicData(afTopicData);
            } else {
                LOG.warn("WARN : Unable to find Topic for the configured Topic Code : {} : {}", afTopicConfig.getTopicCode(), credentialsDTO.getUserID());
            }
        }
        LOG.info("START : Build Basic Default Topics Application Form : {} : {}", draftApplicationFormRQ, credentialsDTO.getUserID());
        return this;
    }

    public AFDraftBuilder buildStatusHistory() throws AppsException {

        AFStatusHistory afStatusHistory = new AFStatusHistory();
        afStatusHistory.setActionMessage("Drafted Application Form");
        afStatusHistory.setApplicationFormStatus(draftApplicationFormRQ.getCurrentApplicationFormStatus());
        afStatusHistory.setAssignUserID(draftApplicationFormRQ.getAssignUserID());
        afStatusHistory.setAssignUser(draftApplicationFormRQ.getAssignUser());
        afStatusHistory.setAssignUserDisplayName(draftApplicationFormRQ.getAssignUserDisplayName());
        afStatusHistory.setAssignUserUpmID(draftApplicationFormRQ.getAssignUserUpmID());
        afStatusHistory.setAssignUserUpmGroupCode(draftApplicationFormRQ.getAssignUserUpmGroupCode());
        afStatusHistory.setAssignUserDivCode(draftApplicationFormRQ.getAssignUserDivCode());
        afStatusHistory.setUpdatedUserDisplayName(draftApplicationFormRQ.getAssignUserDisplayName());
        afStatusHistory.setUpdateBy(credentialsDTO.getUserName());
        afStatusHistory.setUpdateDate(date);
        applicationForm.addAFStatusHistory(afStatusHistory);

        LOG.info("START : Build Status History Application Form : {} : {}", draftApplicationFormRQ, credentialsDTO.getUserID());
        return this;
    }

    public void setApplicationFormService(ApplicationFormService applicationFormService) {
        this.applicationFormService = applicationFormService;
    }

    public void setApplicationFormDao(ApplicationFormDao applicationFormDao) {
        this.applicationFormDao = applicationFormDao;
    }

    public void setAfTopicDao(AFTopicDao afTopicDao) {
        this.afTopicDao = afTopicDao;
    }

    public void setAfTopicConfigDao(AFTopicConfigDao afTopicConfigDao) {
        this.afTopicConfigDao = afTopicConfigDao;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setWorkFlowTemplateDao(WorkFlowTemplateDao workFlowTemplateDao) {
        this.workFlowTemplateDao = workFlowTemplateDao;
    }

    public void setIntegrationService(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setAfCustomerDao(AFCustomerDao afCustomerDao) {
        this.afCustomerDao = afCustomerDao;
    }

    public void setCustomerJdbcDao(CustomerJdbcDao customerJdbcDao) {
        this.customerJdbcDao = customerJdbcDao;
    }

    public void setLeadDao(LeadDao leadDao) {
        this.leadDao = leadDao;
    }

    public ApplicationFormJdbcDao getApplicationFormJdbcDao() {
        return applicationFormJdbcDao;
    }

    public void setApplicationFormJdbcDao(ApplicationFormJdbcDao applicationFormJdbcDao) {
        this.applicationFormJdbcDao = applicationFormJdbcDao;
    }
}
