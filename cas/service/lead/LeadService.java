package com.itechro.cas.service.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.dao.casmaster.SupportingDocDao;
import com.itechro.cas.dao.customer.CustomerBankDetailsDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.lead.*;
import com.itechro.cas.dao.lead.jdbc.DigitalApplicationJdbcDao;
import com.itechro.cas.dao.lead.jdbc.LeadJdbcDao;
import com.itechro.cas.dao.lead.jdbc.LeadRefProc;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.applicationform.AFStatusHistory;
import com.itechro.cas.model.domain.facilitypaper.FPStatusHistory;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.domain.lead.LeadComment;
import com.itechro.cas.model.domain.lead.LeadDocument;
import com.itechro.cas.model.domain.lead.LeadFacilityDetail;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.lead.*;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.integration.request.BranchAuthorityLevelRQ;
import com.itechro.cas.model.dto.integration.request.UmpDetailLoadByAdIdRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.dto.lead.*;
import com.itechro.cas.model.dto.lead.crm.*;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.lead.command.LeadModificationContext;
import com.itechro.cas.service.lead.command.LeadStatusModifier;
import com.itechro.cas.service.lead.support.LeadStatusHandler;
import com.itechro.cas.service.lead.support.leadstatustransit.*;
import com.itechro.cas.service.notification.LeadNotificationService;
import com.itechro.cas.service.storage.StorageService;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.WebAuditUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.customer.CustomerIdentificationDao;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.customer.CustomerIdentification;
import com.itechro.cas.config.CasProperties;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeadService {

    private static final Logger LOG = LoggerFactory.getLogger(LeadService.class);

    @Autowired
    private LeadJdbcDao leadJdbcDao;

    @Autowired
    private LeadDao leadDao;

    @Autowired
    private CustomerBankDetailsDao customerBankDetailsDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private DocStorageDao docStorageDao;

    @Autowired
    private SupportingDocDao supportingDocDao;

    @Autowired
    private LeadActionDao leadActionDao;

    @Autowired
    private WebAuditService webAuditService;

    @Autowired
    private LeadDocumentDao leadDocumentDao;

    @Autowired
    private LeadRefProc leadRefProc;

    @Autowired
    private WebAuditJDBCDao webAuditJDBCDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerIdentificationDao customerIdentificationDao;

    @Autowired
    private CasProperties casProperties;

    @Autowired
    IntegrationService integrationService;

    @Autowired
    LeadNotificationService leadNotificationService;

    @Autowired
    private LeadStatusModifier leadStatusModifier;

    @Autowired
    private LeadAppCodeDao leadAppCodeDao;

    @Autowired
    private ApplicationFormDao applicationFormDao;

    @Autowired
    private FacilityPaperDao facilityPaperDao;

    @Autowired
    private DigitalApplicationDetailDao digitalApplicationDetailDao;
    @Autowired
    private DigitalApplicationTagDao digitalApplicationTagDao;
    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private ComprehensiveFacilityDao comprehensiveFacilityDao;

    @Autowired
    private DigitalApplicationJdbcDao jdbcDao;

    private final Object guard = new Object();

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public LeadDTO getLeadDTOByID(Integer leadID) {

        Lead lead = leadDao.getOne(leadID);

        LOG.info("Lead fetched by ID : {}", CalendarUtil.getCRMFormattedDateTime(lead.getCreatedDate()));
        return new LeadDTO(lead);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public LeadDTO getLeadDTOByRefNumber(String leadRefNumber) throws AppsException {
        Lead lead = this.leadDao.getByLeadRefNumber(leadRefNumber);

        if (lead == null || lead.getLeadID() == null) {
            LOG.info("ERROR : Invalid lead reference : {}", leadRefNumber);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_LEAD_REFERENCE);
        }
        return new LeadDTO(lead);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<LeadDTO> getPagedLeadDTO(LeadSearchRQ searchRQ, CredentialsDTO credentialsDTO) throws AppsException {
        return leadJdbcDao.getPagedLeadDTO(searchRQ, credentialsDTO, false);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<LeadDTO> getCustomerPagedLeadDTO(LeadSearchRQ searchRQ, CredentialsDTO credentialsDTO) throws AppsException {
        return leadJdbcDao.getPagedLeadDTO(searchRQ, credentialsDTO, true);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Long getBranchPendingLeadCount(LeadSearchRQ searchRQ) throws AppsException {
        return leadJdbcDao.getBranchPendingLeadCount(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LeadDTO saveOrUpdateLeadDTO(LeadDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save update Lead :{} by :{}", updateDTO, credentialsDTO.getUserName());
        Date date = new Date();
        LeadDTO previousDTO = null;
        LeadDTO updateLeadDTO = null;
        Lead lead = null;
        boolean isNewLead = updateDTO.getLeadID() == null;

        updateDTO.setIdentificationNumber(updateDTO.getIdentificationNumber().trim().toUpperCase());
        LOG.info("identificationNumber :{}", updateDTO.getIdentificationNumber());

        CustomerIdentification customerIdentification = customerIdentificationDao.findByIdentificationNumberAndStatus(updateDTO.getIdentificationNumber(), AppsConstants.Status.ACT);
        if (customerIdentification != null) {
            updateDTO.setCustomerID(customerIdentification.getCustomer().getCustomerID());
        }

        LOG.info("customerID :{}", updateDTO.getCustomerID());

        LeadDTO currentLeadDTOWithinThreeMonth = this.leadJdbcDao.getLeadDTOByLastWithinThreeMonth(updateDTO);

        if (isNewLead) {
            if (currentLeadDTOWithinThreeMonth == null || currentLeadDTOWithinThreeMonth.getCreatedBy().equals(credentialsDTO.getUserName())) {
                lead = new Lead();
            } else {
                return currentLeadDTOWithinThreeMonth;
            }

            lead.setCreatedBy(credentialsDTO.getUserName());
            lead.setCreatedDate(date);
            lead.setLeadRefNumber(this.getLeadRefCode());
            lead.setLeadStatus(DomainConstants.LeadStatus.PENDING);

            if (credentialsDTO.isAgent()) {
                lead.setLeadType(DomainConstants.LeadType.EXTERNAL);
                lead.setAssignUserID(updateDTO.getAssignUserID());
            } else {
                lead.setLeadType(DomainConstants.LeadType.INTERNAL);
                lead.setAssignUserID(credentialsDTO.getUserName());
            }

            lead.setSubmitted(AppsConstants.YesNo.N);

        } else {
            lead = this.leadDao.getOne(updateDTO.getLeadID());
            if (!lead.getIdentificationNumber().equals(updateDTO.getIdentificationNumber())) {
                if (currentLeadDTOWithinThreeMonth != null && !currentLeadDTOWithinThreeMonth.getCreatedBy().equals(credentialsDTO.getUserName())) {
                    return currentLeadDTOWithinThreeMonth;
                }
            }
            previousDTO = this.getLeadDTOByID(updateDTO.getLeadID());

            lead.setModifiedBy(credentialsDTO.getUserName());
            lead.setModifiedDate(date);
            lead.setLeadStatus(updateDTO.getLeadStatus());
            lead.setAssignUserID(updateDTO.getAssignUserID());
        }

        Customer customer = customerDao.getOne(updateDTO.getCustomerID() == null ? 0 : updateDTO.getCustomerID());
        lead.setCustomer(customer);
        lead.setCustomerID(updateDTO.getCustomerID());
        lead.setFsType(updateDTO.getLeadFsType());
        lead.setAddress(updateDTO.getAddress());
        lead.setName(updateDTO.getName());
        lead.setIsExistingCustomer(updateDTO.getIsExistingCustomer());
        lead.setTypeOfBusiness(updateDTO.getTypeOfBusiness());
        lead.setDesignation(updateDTO.getDesignation());
        lead.setContactPerson(updateDTO.getContactPerson());
        lead.setLeadCreationType(updateDTO.getLeadCreationType());
        lead.setEmail(updateDTO.getEmail());
        lead.setAccountNumber(updateDTO.getAccountNumber());
        lead.setCustomerBankAccountNumber(updateDTO.getAccountNumber());
        lead.setCivilStatus(updateDTO.getCivilStatus());
        lead.setLeadName(updateDTO.getLeadName());
        lead.setCreatedUserDisplayName(updateDTO.getCreatedUserDisplayName());
        lead.setAssignUserDisplayName(updateDTO.getAssignUserDisplayName());

//        lead.setCustomerID(updateDTO.getCustomerID());
        if (StringUtils.isNotBlank(updateDTO.getDateOfBirthStr())) {
            lead.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(updateDTO.getDateOfBirthStr()));
        }
        lead.setIdentificationNumber(updateDTO.getIdentificationNumber());
        lead.setIdentificationType(updateDTO.getIdentificationType());
        lead.setMobileNo(updateDTO.getMobileNo());
        lead.setMobileNo(updateDTO.getMobileNo());
        lead.setNationality(updateDTO.getNationality());
        lead.setBranchCode(updateDTO.getBranchCode());
        lead.setBranchName(updateDTO.getBranchName());

        for (LeadFacilityDetailDTO facilityDetailDTO : updateDTO.getLeadFacilityDetailDTOList()) {
            boolean isNewFacility = facilityDetailDTO.getLeadFacilityDetailID() == null;
            LeadFacilityDetail facilityDetail = null;
            if (isNewFacility) {
                facilityDetail = new LeadFacilityDetail();
                facilityDetail.setCreatedBy(credentialsDTO.getUserName());
                facilityDetail.setCreatedDate(date);
                facilityDetail.setLead(lead);
                lead.getLeadFacilityDetails().add(facilityDetail);
            } else {
                facilityDetail = lead.getLeadFacilityDetailByID(facilityDetailDTO.getLeadFacilityDetailID());
                facilityDetail.setCreatedBy(credentialsDTO.getUserName());
                facilityDetail.setCreatedDate(date);
            }
            facilityDetail.setFacilityTemplateName(facilityDetailDTO.getFacilityTemplateName());
            facilityDetail.setCreditFacilityType(facilityDetailDTO.getCreditFacilityType());
            facilityDetail.setCreditFacilityTypeID(facilityDetailDTO.getCreditFacilityTypeID());
            facilityDetail.setFacilityTemplateID(facilityDetailDTO.getFacilityTemplateID());
            facilityDetail.setAmount(facilityDetailDTO.getAmount());
            facilityDetail.setFacilityCurrency(facilityDetailDTO.getFacilityCurrency());
            facilityDetail.setDescription(facilityDetailDTO.getDescription());
            facilityDetail.setStatus(facilityDetailDTO.getStatus());
        }

        lead = this.leadDao.saveAndFlush(lead);
        updateLeadDTO = new LeadDTO(lead);
        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructLeadAudit(updateLeadDTO, previousDTO, credentialsDTO, date, isNewLead, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save update Lead :{} by :{}", updateDTO, credentialsDTO.getUserName());

        updateDTO = new LeadDTO(lead);
        return updateDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LeadDTO submitLead(LeadDTO leadDTO, CredentialsDTO credentialsDTO) {
        LOG.info("START: Submit Lead :{} by :{}", leadDTO, credentialsDTO.getUserName());

        Lead lead = this.leadDao.getOne(leadDTO.getLeadID());
        lead.setSubmitted(AppsConstants.YesNo.Y);
        lead.setAssignUserID(null);
        lead.setModifiedBy(credentialsDTO.getUserName());
        lead.setModifiedDate(new Date());

        lead = this.leadDao.saveAndFlush(lead);

        LOG.info("END: Submit Lead :{} by :{}", leadDTO, credentialsDTO.getUserName());

        return new LeadDTO(lead);
    }
//
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public LeadDTO updateLeadDTOStatus(LeadStatusUpdateRQ leadStatusUpdateRQ, CredentialsDTO credentialsDTO) throws AppsException {
//        LOG.info("START: Update Lead status :{} by :{}", leadStatusUpdateRQ, credentialsDTO.getUserName());
//        Date date = new Date();
//        Lead lead = null;
//        LeadDTO previousDTO = null;
//        LeadDTO updateLeadDTO = null;
//
//        lead = this.leadDao.getOne(leadStatusUpdateRQ.getLeadID());
//        previousDTO = new LeadDTO(lead);
//
//
//        LeadAction leadAction = new LeadAction();
//        leadAction.setActionedBy(credentialsDTO.getUserName());
//        leadAction.setActionedByDisplayName(leadStatusUpdateRQ.getActionedByDisplayName());
//        leadAction.setAction(leadStatusUpdateRQ.getAction());
//        leadAction.setActionedTimestamp(date);
//        leadAction.setFromLeadStatus(lead.getLeadStatus());
//        leadAction.setToLeadStatus(leadStatusUpdateRQ.getLeadStatus());
//        leadAction.setAssignUserID(leadStatusUpdateRQ.getAssignUserID());
//        leadAction.setRemark(leadStatusUpdateRQ.getRemark());
//
//        lead.addLeadAction(leadAction);
//        lead.setLeadStatus(leadStatusUpdateRQ.getLeadStatus());
//        if (leadStatusUpdateRQ.getLeadStatus() == DomainConstants.LeadStatus.RETURNED) {
//            leadStatusUpdateRQ.setAssignUserID(null);
//        }
//        lead.setAssignUserID(leadStatusUpdateRQ.getAssignUserID());
//
//        if (lead.isExternalLead()) {
//            lead.setAllowFinacleData(AppsConstants.YesNo.valueOf(leadStatusUpdateRQ.isAllowFinacleData()));
//            lead.setAllowCrib(AppsConstants.YesNo.valueOf(leadStatusUpdateRQ.isAllowCrib()));
//            lead.setAllowKalypto(AppsConstants.YesNo.valueOf(leadStatusUpdateRQ.isAllowKalypto()));
//            lead.setCustomerBankAccountNumber(leadStatusUpdateRQ.getCustomerBankAccountNumber());
//
////            if (StringUtils.isNotEmpty(leadStatusUpdateRQ.getCustomerBankAccountNumber())) {
////                List<CustomerBankDetail> bankDetails = this.customerBankDetailsDao.findAllByBankAccountNumberAndStatus(
////                        leadStatusUpdateRQ.getCustomerBankAccountNumber(),
////                        AppsConstants.Status.ACT
////                );
////                if (bankDetails.isEmpty()) {
////                    try {
////                        SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
////                        searchCustomerRQ.setBankAccountNumber(leadStatusUpdateRQ.getCustomerBankAccountNumber());
////                        customerService.searchCustomerFrom360(searchCustomerRQ, credentialsDTO);
////                    } catch (Exception e) {
////                        LOG.error("ERROR : Customer couldn't find on Finacle. {}", leadStatusUpdateRQ, e);
////                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FINACLE_CUSTOMER_CANNOT_FIND);
////                    }
////                    bankDetails = this.customerBankDetailsDao.findAllByBankAccountNumberAndStatus(
////                            leadStatusUpdateRQ.getCustomerBankAccountNumber(),
////                            AppsConstants.Status.ACT
////                    );
////                    if (bankDetails.isEmpty()) {
////                        LOG.info("ERROR : Customer bank details not found for external lead : {}", leadStatusUpdateRQ);
////                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_EXTERNAL_LEAD_BANK_ACCOUNT);
////                    }
////                }
////                lead.setCustomerID(bankDetails.get(bankDetails.size() - 1).getCustomer().getCustomerID());
////            }
////        } else {
////            lead.setAllowFinacleData(AppsConstants.YesNo.valueOf(leadStatusUpdateRQ.isAllowFinacleData()));
////            lead.setAllowCrib(AppsConstants.YesNo.valueOf(leadStatusUpdateRQ.isAllowCrib()));
////            lead.setAllowKalypto(AppsConstants.YesNo.valueOf(leadStatusUpdateRQ.isAllowKalypto()));
////        }
//
//        if (StringUtils.isNotEmpty(leadStatusUpdateRQ.getCustomerBankAccountNumber())) {
//            lead.setCustomerBankAccountNumber(leadStatusUpdateRQ.getCustomerBankAccountNumber());
//            lead.setAccountNumber(leadStatusUpdateRQ.getCustomerBankAccountNumber());
//        }
//
//        lead.setModifiedBy(credentialsDTO.getUserName());
//        lead.setModifiedDate(date);
//
//        lead = this.leadDao.saveAndFlush(lead);
//        updateLeadDTO = new LeadDTO(lead);
//        //Audit
//        WebAuditDTO webAuditDTO = WebAuditUtils.constructLeadStatusAudit(updateLeadDTO, previousDTO, credentialsDTO, date, webAuditJDBCDao);
//        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);
//
//        LeadDTO updatedLeadDTO = new LeadDTO(lead);
//        LOG.info("END: Update Lead status :{} by :{}", updatedLeadDTO, credentialsDTO.getUserName());
//
//        return updatedLeadDTO;
//    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LeadDTO updateLeadDTOStatus(LeadStatusUpdateRQ leadStatusUpdateRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Update Lead status :{} by :{}", leadStatusUpdateRQ, credentialsDTO.getUserName());

        Date date = new Date();
        Lead lead = null;
        LeadDTO previousDTO = null;
        LeadDTO updateLeadDTO = null;

        lead = this.leadDao.getOne(leadStatusUpdateRQ.getLeadID());
        previousDTO = new LeadDTO(lead);


        LeadStatusHandler leadStatusHandler = null;

        LeadModificationContext context = new LeadModificationContext();
        context.setBranchLoadResponseListDTO(integrationService.getBranchList(credentialsDTO));
        context.setLeadStatusUpdateRQ(leadStatusUpdateRQ);
        context.setCredentialsDto(credentialsDTO);
        context.setLeadDto(previousDTO);
        LOG.info("START: setLeadDto :{} ", context.getLeadDto());

        context.setLead(lead);
        context.setDate(new Date());


        if (lead.getCustomerID() != null) {
            Customer customerDTO = customerDao.getOne(lead.getCustomerID());
            context.setCustomer(customerDTO);
        }

        UmpDetailLoadByAdIdRQ rq = new UmpDetailLoadByAdIdRQ();
        rq.setAdUserID(context.getLead().getCreatedBy());
        rq.setAppCode(this.casProperties.getApplicationCode());
        UpmDetailResponse res = integrationService.getUpmDetailsByAdUserIdAndAppCode(rq);

        if (res != null) {
            context.setCreatedUserResponse(res);
        }
//
//        CrmLoginRequest crmLoginRequest = new CrmLoginRequest();
//        crmLoginRequest.setUserName("admin");
//        crmLoginRequest.setPassword("keres@05");
//
//        CrmLoginResponse response = null;

        switch (leadStatusUpdateRQ.getLeadStatus()) {
            case SUBMITTED:
                leadStatusHandler = new LeadSubmittedHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                leadStatusHandler.recordLeadAction();
                leadStatusHandler.updateLead();
                leadStatusHandler.transitStatus();

                BranchAuthorityLevelRQ branchAuthorityLevelRQ = new BranchAuthorityLevelRQ();
                branchAuthorityLevelRQ.setRoleId(String.valueOf(this.casProperties.getManagerWorkClass()));
                branchAuthorityLevelRQ.setSolId(context.getLeadDto().getBranchCode());
                branchAuthorityLevelRQ.setAppCode(this.casProperties.getApplicationCode());

                context.setUpmUsers50(integrationService.getUserDetailListFormBranchAuthorityLevel(branchAuthorityLevelRQ, context.getCredentialsDto()));

                leadStatusHandler.sendEmailNotificationSubmitted(context);

                //response = performCrmLogin(crmLoginRequest);
                addLeadDetailsToCrmRequest(leadStatusUpdateRQ.getLeadID());

                break;

            case APPROVED:
                leadStatusHandler = new LeadApprovedHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                ((LeadApprovedHandler) leadStatusHandler).setCustomerBankDetailsDao(customerBankDetailsDao);
                ((LeadApprovedHandler) leadStatusHandler).setCustomerService(customerService);
                leadStatusHandler.recordLeadAction();
                leadStatusHandler.updateLead();
                leadStatusHandler.transitStatus();

                //response = performCrmLogin(crmLoginRequest);
                addLeadDetailsToCrmRequest(leadStatusUpdateRQ.getLeadID());

                break;

            case ACCEPTED:
                leadStatusHandler = new LeadAcceptHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                ((LeadAcceptHandler) leadStatusHandler).setCustomerBankDetailsDao(customerBankDetailsDao);
                ((LeadAcceptHandler) leadStatusHandler).setCustomerService(customerService);
                leadStatusHandler.recordLeadAction();
                leadStatusHandler.updateLead();
                leadStatusHandler.transitStatus();
                leadStatusHandler.sendEmailNotificationAccepted(context);

                addLeadDetailsToCrmRequest(leadStatusUpdateRQ.getLeadID());
                break;

            case CLOSED:
                leadStatusHandler = new LeadCloseHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                leadStatusHandler.recordLeadAction();
                leadStatusHandler.updateLead();
                leadStatusHandler.transitStatus();

                addLeadDetailsToCrmRequest(leadStatusUpdateRQ.getLeadID());
                break;

            case RETURNED:
                leadStatusHandler = new LeadReturnHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                leadStatusHandler.recordLeadAction();
                leadStatusHandler.updateLead();
                leadStatusHandler.transitStatus();
                leadStatusHandler.sendEmailNotificationReturned(context);

                addLeadDetailsToCrmRequest(leadStatusUpdateRQ.getLeadID());
                break;

            case PAPER_CREATED:
                leadStatusHandler = new LeadPaperCreatedHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                leadStatusHandler.recordLeadAction();
                leadStatusHandler.transitStatus();

                addLeadDetailsToCrmRequest(leadStatusUpdateRQ.getLeadID());
                break;

            case APPLICATION_CREATED:
                leadStatusHandler = new LeadApplicationCreatedHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                leadStatusHandler.recordLeadAction();
                leadStatusHandler.updateLead();
                leadStatusHandler.transitStatus();
                leadStatusHandler.sendEmailNotificationAccepted(context);

                addLeadDetailsToCrmRequest(leadStatusUpdateRQ.getLeadID());
                break;

            case DECLINED:
                leadStatusHandler = new LeadDeclineHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                leadStatusHandler.recordLeadAction();
                leadStatusHandler.updateLead();
                leadStatusHandler.transitStatus();
                leadStatusHandler.sendEmailNotificationDeclined(context);

                addLeadDetailsToCrmRequest(leadStatusUpdateRQ.getLeadID());
                break;

            default:
                leadStatusHandler = new LeadDeclineHandler(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
                leadStatusHandler.transitStatus();
        }

        Customer customer = customerDao.getOne(lead.getCustomerID() == null ? 0 : lead.getCustomerID());
        lead.setCustomer(customer);
        lead.setCustomerID(lead.getCustomerID());
        lead.setApplicationFormID(leadStatusUpdateRQ.getApplicationFormID());

        leadStatusModifier.execute(context);

        lead = this.leadDao.saveAndFlush(lead);
        updateLeadDTO = new LeadDTO(lead);
        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructLeadStatusAudit(updateLeadDTO, previousDTO, credentialsDTO, date, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LeadDTO updatedLeadDTO = new LeadDTO(lead);

        LOG.info("END: Update Lead status :{} by :{}", updateLeadDTO, credentialsDTO.getUserName());

        return updatedLeadDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LeadDTO uploadLeadDocument(LeadDocumentUploadRQ leadDocumentUploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload lead document :{} by :{}", leadDocumentUploadRQ, credentialsDTO.getUserName());
        Date date = new Date();
        Lead lead = this.leadDao.getOne(leadDocumentUploadRQ.getLeadID());
        LeadDocumentDTO previousLeadDocDTO = null;
        LeadDocumentDTO updateLeadDocDTO = null;
        DocStorageDTO docStorageDTO = null;

        // ========== lead comp documents ==========

        boolean isNewDocument = leadDocumentUploadRQ.getLeadDocumentID() == null;
        if (isNewDocument) {
            if (leadDocumentUploadRQ.getDocStorageDTO().getDocument() == null) {
                throw new IllegalArgumentException("File is required for new Lead Document");
            }

            if (leadDocumentUploadRQ.getDocStorageDTO() == null) {
                leadDocumentUploadRQ.setDocStorageDTO(new DocStorageDTO());
            }
            leadDocumentUploadRQ.getDocStorageDTO().setDocument(leadDocumentUploadRQ.getDocStorageDTO().getDocument());
        }else{
            if(leadDocumentUploadRQ.getDocStorageDTO().getDocument() == null){
                byte[] data = storageService.downloadDocumentByStorageID(leadDocumentUploadRQ.getDocStorageDTO().getDocStorageID());
                leadDocumentUploadRQ.getDocStorageDTO().setDocument(data);
            }
        }


        // ========== lead comp documents ==========

        if (leadDocumentUploadRQ.getDocStorageDTO() != null) {
            leadDocumentUploadRQ.getDocStorageDTO().setDescription("LEAD: " + lead.getLeadName() + "");
            docStorageDTO = this.storageService.saveUpdateDocument(leadDocumentUploadRQ.getDocStorageDTO(), credentialsDTO);
        } else {
            LOG.error("Lead Document data null:{}", leadDocumentUploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_LEAD_LEAD_DOCUMENT_NULL_SUPPORT_DOC);
        }

        LeadDocument leadDocument = null;
        boolean isNewLeadDoc = leadDocumentUploadRQ.getLeadDocumentID() == null;

        if (isNewLeadDoc) {
            leadDocument = new LeadDocument();
            leadDocument.setCreatedBy(credentialsDTO.getUserName());
            leadDocument.setCreatedDate(date);
            leadDocument.setUploadedDivCode(leadDocumentUploadRQ.getUploadedDivCode());
            leadDocument.setUploadedUserDisplayName(leadDocumentUploadRQ.getUploadedUserDisplayName());
            lead.addLeadDocument(leadDocument);
        } else {
            previousLeadDocDTO = this.getLeadDocDTOByID(leadDocumentUploadRQ.getLeadDocumentID());
            leadDocument = lead.getLeadDocumentByID(leadDocumentUploadRQ.getLeadDocumentID());
            leadDocument.setModifiedBy(credentialsDTO.getUserName());
            leadDocument.setModifiedDate(date);
        }
        leadDocument.setRemark(leadDocumentUploadRQ.getRemark());
        leadDocument.setSupportingDoc(this.supportingDocDao.getOne(leadDocumentUploadRQ.getSupportingDocID()));
        leadDocument.setStatus(leadDocumentUploadRQ.getStatus());

        if (docStorageDTO != null) {
            leadDocument.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Lead Document data null:{}", leadDocumentUploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_LEAD_LEAD_DOCUMENT_NULL_SUPPORT_DOC);
        }

        this.leadDao.saveAndFlush(lead);

        //Audit for LeadDocument
        updateLeadDocDTO = new LeadDocumentDTO(leadDocument);
        WebAuditDTO webAuditDTO = WebAuditUtils.constructLeadDocumentAudit(updateLeadDocDTO, previousLeadDocDTO, credentialsDTO, date, isNewLeadDoc, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LeadDTO updateLeadDTO = new LeadDTO(lead);

        LOG.info("END: Upload lead document :{} by :{}", leadDocumentUploadRQ, credentialsDTO.getUserName());
        return updateLeadDTO;
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public LeadDocumentDTO getLeadDocDTOByID(Integer leadDocID) {

        LeadDocument leadDocument = leadDocumentDao.getOne(leadDocID);
        return new LeadDocumentDTO(leadDocument);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getLeadRefCode() throws AppsException {
        String ref = null;
        synchronized (guard) {
            ref = leadRefProc.executeFunction();
        }
        return ref;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public LeadFacilityPaperStatusDetailRS getFacilityPaperHistoryForLead(LeadDTO searchRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Search Facility Paper Status history for the lead :{} by :{}", searchRQ, credentialsDTO.getUserName());
        return leadJdbcDao.getFacilityPaperHistoryForLead(searchRQ.getLeadID());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LeadDashboardCountDTO getLeadDashboardCount(LeadDashboardRQ searchRQ) {
        Integer dashboardTimePeriodDays = 0;
        LOG.info("START: getLeadDashboardCount: {} ", searchRQ);

        dashboardTimePeriodDays = casProperties.getDashboardTimePeriodDays();
        LOG.info("getDashboardTimePeriodDays: {} ", dashboardTimePeriodDays);

        LeadDashboardCountDTO dashboardCounts = new LeadDashboardCountDTO();

        dashboardCounts = leadJdbcDao.getLeadDashboardCount(searchRQ, dashboardTimePeriodDays);
        dashboardCounts.setDashboardTimePeriodDays(dashboardTimePeriodDays);

        LOG.info("dashboardCounts: {} ", dashboardCounts);

        LOG.info("END: getLeadDashboardCount: {} ", searchRQ);

        return dashboardCounts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<LeadDashboardDTO> getPagedLeadDashboard(LeadDashboardRQ leadDashboardRQ) {
        Integer dashboardTimePeriodDays = 0;
        LOG.info("START: getPagedLeadDashboard: {} ", leadDashboardRQ);

        dashboardTimePeriodDays = casProperties.getDashboardTimePeriodDays();
        LOG.info("getDashboardTimePeriodDays: {} ", dashboardTimePeriodDays);

        return leadJdbcDao.getPagedLeadDashboardDTO(leadDashboardRQ, dashboardTimePeriodDays);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LeadDTO saveOrUpdateLeadComment(LeadCommentDTO leadCommentDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save or Update Lead User Comment : {} by: {}", leadCommentDTO, credentialsDTO.getUserName());

        Lead lead = leadDao.getOne(leadCommentDTO.getLeadID());
        LeadComment leadComment;
        Date date = new Date();
        boolean isNew = leadCommentDTO.getCommentID() == null;
        if (isNew) {
            leadComment = new LeadComment();
            leadComment.setCreatedBy(credentialsDTO.getUserName());
            leadComment.setCreatedDate(date);
        } else {
            leadComment = lead.getLeadCommentByID(leadCommentDTO.getCommentID());
            leadComment.setModifiedBy(credentialsDTO.getUserName());
            leadComment.setModifiedDate(date);
        }
        leadComment.setStatus(leadCommentDTO.getStatus());
        leadComment.setCreatedUserID(leadCommentDTO.getCreatedUserID());
        leadComment.setCreatedUser(leadCommentDTO.getCreatedUser());
        leadComment.setCreatedUserDisplayName(leadCommentDTO.getCreatedUserDisplayName());
        leadComment.setCreatedUserDivCode(leadCommentDTO.getCreatedUserDivCode());
        leadComment.setCreatedUserUpmCode(leadCommentDTO.getCreatedUserUpmCode());

        leadComment.setUserComment(leadCommentDTO.getComment());
        leadComment.setActionMessage(leadCommentDTO.getActionMessage());

        leadComment.setIsDivisionOnly(leadCommentDTO.getIsDivisionOnly());
        leadComment.setIsUsersOnly(leadCommentDTO.getIsUsersOnly());
        leadComment.setIsPublic(leadCommentDTO.getIsPublic());
        leadComment.setCurrentLeadStatus(leadCommentDTO.getCurrentLeadStatus());
        lead.addLeadComment(leadComment);

        lead = leadDao.saveAndFlush(lead);

        LOG.info("END: Save or Update Lead User Comment : {} by: {}", leadCommentDTO, credentialsDTO.getUserName());

        //  LeadLoadOptionsDTO leadLoadOptionsDTO = new LeadLoadOptionsDTO();
        //  leadLoadOptionsDTO.loadComments();

        return new LeadDTO(lead);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public LeadDTO deactivateLeadSupportingDoc(LeadDocumentDTO leadDocumentDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the Lead supporting document: {} by: {}", leadDocumentDTO, credentialsDTO.getUserName());
        Lead lead = leadDao.getOne(leadDocumentDTO.getLeadID());
        LeadDocument leadDocument = null;
        if (leadDocumentDTO.getSupportingDocDTO() != null) {
            leadDocument = leadDocumentDao.getOne(leadDocumentDTO.getLeadDocumentID());
            leadDocument.setStatus(AppsConstants.Status.INA);
            leadDocument.setModifiedBy(credentialsDTO.getUserName());
            leadDocument.setModifiedDate(new Date());
            lead.addLeadDocument(leadDocument);
        }

        lead = leadDao.saveAndFlush(lead);
        LOG.info("END: Deactivate the Lead supporting document by: {}", credentialsDTO.getUserName());
        return new LeadDTO(lead);
    }

    @Transactional(readOnly = true, rollbackFor = AppsException.class)
    public CrmLoginResponse performCrmLogin() throws AppsException {

        CrmLoginRequest crmLoginRequest = new CrmLoginRequest();
        crmLoginRequest.setUserName(integrationService.getCrmUserName());
        crmLoginRequest.setPassword(integrationService.getCrmPassword());

        LOG.info("START: performCrmLogin for user: {} ", crmLoginRequest.getUserName());

        CrmLoginResponse response = integrationService.performCrmLogin(crmLoginRequest);

        LOG.info("END: performCrmLogin for user: {} ", crmLoginRequest.getUserName());
        return response;
    }

    @Transactional(readOnly = true, rollbackFor = AppsException.class)
    public CrmSaveResponseDTO addLeadDetailsToCrmRequest(Integer leadID) throws AppsException {
        LOG.info("START: addLeadDetailsToCrmRequest for leadID: {} by: {}", leadID);

        CrmSaveResponseDTO crmSaveResponseDTO = null;

        if (leadID != null) {
            LeadAppCode leadAppCode = leadAppCodeDao.findByLeadId(leadID);

            if (leadAppCode == null) {
                LOG.info("No LeadAppCode found for leadID: {}", leadID);
                return null; // Or handle this case as per your application's requirements
            }

            if (Objects.equals(leadID, leadAppCode.getLeadId())) {
                CrmLoginResponse response = performCrmLogin();

                Lead lead = leadDao.findById(leadID).orElseThrow(() -> new AppsException("Lead not found for ID: " + leadID));
                LOG.info("Lead details: {} ", lead);

                ApplicationForm applicationForm = applicationFormDao.findByLeadID(leadID);
                FacilityPaper facilityPaper = facilityPaperDao.findByLeadID(leadID);

                LOG.info("Lead ID from Lead : {} LeadAppCode: {} ", lead.getLeadID(), leadAppCode.getLeadId());

                if (lead.getLeadID().equals(leadAppCode.getLeadId())) {

                    LOG.info("Preparing LeadCrmDTO for CRM update for leadID: {}", leadID);


                    LeadCrmDTO leadCrmDTO = new LeadCrmDTO();
                    leadCrmDTO.setItemId(leadAppCode.getAppRef());
                    leadCrmDTO.setItemType("Lead");
                    leadCrmDTO.setProcessMode("Update");

                    LeadCrmObjectDTO objectData = new LeadCrmObjectDTO();

                    if (applicationForm != null && facilityPaper == null) {

                        if (applicationForm.getCurrentApplicationFormStatus().equals(DomainConstants.ApplicationFormStatus.DECLINED)) {
                            objectData.setLea_ex2_73("Declined");
                        } else {
                            objectData.setLea_ex2_73(String.format("Application Form - %s", applicationForm.getCurrentApplicationFormStatus()));
                        }

                        String afActionMessage = applicationForm.getAfStatusHistorySet().stream()
                                .filter(Objects::nonNull)
                                .max(Comparator.comparing(AFStatusHistory::getStatusHistoryID))
                                .map(AFStatusHistory::getActionMessage)
                                .orElse(null);
                        objectData.setLea_ex2_116(afActionMessage);

                        Date date = applicationForm.getModifiedDate() != null ? applicationForm.getModifiedDate() : applicationForm.getCreatedDate();
                        objectData.setLea_ex2_74(CalendarUtil.getCRMFormattedDateTime(date));

                        objectData.setLea_ex2_88(applicationForm.getAssignUser());
                        objectData.setLea_ex2_91(applicationForm.getAssignUserDisplayName());
                    } else if (applicationForm != null && facilityPaper != null) {

                        if (facilityPaper.getCurrentFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.REJECTED)) {
                            objectData.setLea_ex2_73("Declined");
                        } else if (facilityPaper.getCurrentFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.APPROVED)) {
                            objectData.setLea_ex2_73("Approved");
                        } else {
                            objectData.setLea_ex2_73(String.format("Facility Paper - %s", facilityPaper.getCurrentFacilityPaperStatus()));
                        }

                        String fpActionMessage = facilityPaper.getFpStatusHistorySet().stream()
                                .filter(Objects::nonNull)
                                .max(Comparator.comparing(FPStatusHistory::getFpStatusHistoryID))
                                .map(FPStatusHistory::getActionMessage)
                                .orElse(null);
                        objectData.setLea_ex2_116(fpActionMessage);

                        Date date = facilityPaper.getModifiedDate() != null ? facilityPaper.getModifiedDate() : facilityPaper.getCreatedDate();
                        objectData.setLea_ex2_74(CalendarUtil.getCRMFormattedDateTime(date));
                        //objectData.setLea_ex2_74(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getModifiedDate()));
                        objectData.setLea_ex2_88(facilityPaper.getCurrentAssignUser());
                        objectData.setLea_ex2_91(facilityPaper.getAssignUserDisplayName());
                    } else {

                        if (lead.getLeadStatus().equals(DomainConstants.LeadStatus.DECLINED)) {
                            objectData.setLea_ex2_73("Declined");
                        } else {
                            objectData.setLea_ex2_73(String.format("Lead - %s", lead.getLeadStatus()));
                        }

                        String leadActionMessage = lead.getLeadActions().stream()
                                .filter(Objects::nonNull)
                                .max(Comparator.comparing(LeadAction::getLeadActionID))
                                .map(LeadAction::getAction)
                                .orElse(null);
                        objectData.setLea_ex2_116(leadActionMessage);
                        Date date = lead.getModifiedDate() != null ? lead.getModifiedDate() : new Date();
                        objectData.setLea_ex2_74(CalendarUtil.getCRMFormattedDateTime(date));
                        objectData.setLea_ex2_88(lead.getAssignUserID());
                        objectData.setLea_ex2_91(lead.getAssignUserDisplayName());
                    }

                    leadCrmDTO.setObjectData(objectData);

                    LOG.info("LeadCrmDTO object : {}", leadCrmDTO);

                    crmSaveResponseDTO = integrationService.saveLeadCrmObject(leadCrmDTO, response.getAccess_token());
                } else {
                    LOG.info("No CRM update required for leadID: {} as it is not synced with CRM", leadID);
                }

            }

        }
        LOG.info("END: addLeadDetailsToCrmRequest for leadID: {} by: {}", leadID);
        return crmSaveResponseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public String getDigitalizedApplication(DigitalizedApplicationRequestDTO req) throws AppsException {

        String content = "";

        try {
            String templateName = "DigitalForm";

            String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                    + "leadDA" + File.separator + "html" + File.separator + templateName + ".html";

            SDFacilityPaperPreviewDTO previewDTO = jdbcDao.fetchPreviewByCompLeadId(req.getCompLeadId(), req.getCompPartyIds());

            List<FacilityDTO> facilities = jdbcDao.fetchFacilitiesByCompLeadId(req.getCompLeadId());
            previewDTO.setFacilityDetails(facilities);


            Context context = new Context();
            Map<String, Object> params = new HashMap<>();
            params.put("fpDTO", previewDTO);
            context.setVariables(params);
            content = this.templateEngine.process(templatePath, context);

        }
        catch (Exception ex) {
            LOG.error("Error generating Digitalized Application for compLeadId={}", req.getCompLeadId(), ex);
            content = "";
        }

        return content;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public DigitalApplicationDTO saveOrUpdateDigitalApplication(DigitalApplicationReq digitalApplicationReq, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START:  Digital Application Content Save: {} by: {}", digitalApplicationReq, credentialsDTO.getUserName());

        DigitalApplicationDTO response;

        try {

            DigitalApplicationFormDetail digitalApplicationFormDetail = digitalApplicationDetailDao.findById(digitalApplicationReq.getDigitalApplicationID())
                    .orElse(new DigitalApplicationFormDetail());

            digitalApplicationFormDetail.setLeadID(digitalApplicationReq.getLeadID());
            digitalApplicationFormDetail.setDigitalApplicationID(digitalApplicationReq.getDigitalApplicationID());
            digitalApplicationFormDetail.setDocumentContent(digitalApplicationReq.getDocumentContent());
            digitalApplicationFormDetail.setDocumentStatus(digitalApplicationReq.getDocumentStatus());
            digitalApplicationFormDetail = digitalApplicationDetailDao.saveAndFlush(digitalApplicationFormDetail);


            try {
                List<Long> ids = digitalApplicationReq.getCompPartyIds();
                if (ids != null && !ids.isEmpty()) {
                    Long compLeadIdL = digitalApplicationReq.getCompLeadId() == null
                            ? null
                            : digitalApplicationReq.getCompLeadId().longValue();
                    jdbcDao.markPartiesAsDigitalAppCreated(compLeadIdL, ids);
                }
            } catch (Exception flagEx) {
                LOG.warn("Failed to mark parties as digital application created: {}", flagEx.getMessage());

            }

            response = new DigitalApplicationDTO(digitalApplicationFormDetail);

        } catch (Exception e) {
            LOG.info("Failed Digital Application Content Save:", e);
            throw new AppsException("Failed to save digital application details.");
        }
        LOG.info("END:  Digital Application Content Save: {} by: {}", response, credentialsDTO.getUserName());

        return response;
    }

    /*
     * Get In Principle Sanction Letter Content for the template
     * */
    @Transactional(readOnly = true)
    public String getInPrincipleSanctionLetterContent(InPrincipalSanctionLetterRQ inPrincipalSanctionLetterRQ) throws AppsException {

        String content = "";
        String templateName = "InPrincipleSanctionLetter";
        try {

            String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                    + "leadDA" + File.separator + "html" + File.separator + templateName + ".html";

            InPrincipleSanctionLetterDTO inPrincipleSanctionLetterDTO = new InPrincipleSanctionLetterDTO();

            if(inPrincipalSanctionLetterRQ != null && inPrincipalSanctionLetterRQ.getCompFacilityId() != null) {
                Optional<ComprehensiveFacility> comprehensiveFacility = comprehensiveFacilityDao.findById(inPrincipalSanctionLetterRQ.getCompFacilityId());


                if (comprehensiveFacility.isPresent()) {
                    inPrincipleSanctionLetterDTO = new InPrincipleSanctionLetterDTO(comprehensiveFacility.get());
                }

                inPrincipleSanctionLetterDTO.setLeadRefNo(inPrincipalSanctionLetterRQ.getLeadRefNo());
                inPrincipleSanctionLetterDTO.setDate(inPrincipalSanctionLetterRQ.getDecisionDate());

                if(inPrincipalSanctionLetterRQ.getInPrincipalPartDetails() !=null && !inPrincipalSanctionLetterRQ.getInPrincipalPartDetails().isEmpty()){
                    for(InPrincipalPartDetails inPrincipalPartDetail : inPrincipalSanctionLetterRQ.getInPrincipalPartDetails()){
                        inPrincipleSanctionLetterDTO.addInprincipalParty(inPrincipalPartDetail);
                    }
                }
            }

            Context context = new Context();
            Map<String, Object> params = new HashMap<>();
            params.put("lead", inPrincipleSanctionLetterDTO);
            context.setVariables(params);
            content = this.templateEngine.process(templatePath, context);
        }
        catch (Exception ex){
            content = "";
            LOG.info("ERROR Security Document Content: ", ex);
            String err_message = "An error occurred while fetching template.";
            if (ex.getCause() != null && !ex.getCause().getMessage().isEmpty()) {
                if (ex.getCause().getMessage().contains("The system cannot find the file specified")) {
                    err_message = "There are no template found for ".concat(templateName.replaceAll("-", " "));
                }
            }
            throw new AppsException(err_message);
        }

        return content;
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public boolean deleteDigitalApplication(Integer applicationId, CredentialsDTO credentialsDTO) throws AppsException {
        try {
            DigitalApplicationFormDetail entity =
                    digitalApplicationDetailDao.findById(applicationId)
                            .orElseThrow(() -> new AppsException("Application not found: " + applicationId));

            entity.setDocumentStatus(AppsConstants.DigitalApplicationStatus.DELETE);
            // set modifiedBy/date if needed
            digitalApplicationDetailDao.saveAndFlush(entity);
            return true;
        } catch (Exception e) {
            LOG.error("Failed to soft delete digital application:", e);
            throw new AppsException("Failed to soft delete digital application");
        }
    }




}
