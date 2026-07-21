package com.itechro.cas.service.applicationform;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.applicationform.*;
import com.itechro.cas.dao.applicationform.esg.*;
import com.itechro.cas.dao.applicationform.jdbc.AFSecurityJdbcDao;
import com.itechro.cas.dao.applicationform.jdbc.ApplicationFormJdbcDao;
import com.itechro.cas.dao.applicationform.jdbc.ApplicationFormRefProc;
import com.itechro.cas.dao.casmaster.*;
import com.itechro.cas.dao.casmaster.jdbc.MasterDataJdbcDao;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.customer.jdbc.CustomerJdbcDao;
import com.itechro.cas.dao.esg.*;
import com.itechro.cas.dao.facility.FacilityDao;
import com.itechro.cas.dao.facility.FacilitySecurityDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.lead.LeadDao;
import com.itechro.cas.dao.lead.LeadDocumentDao;
import com.itechro.cas.dao.lead.jdbc.LeadJdbcDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.AppsRuntimeException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.advanceAnalytics.AnalyticsDecision;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomer;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomerAddress;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomerTelephone;
import com.itechro.cas.model.domain.applicationform.esg.*;
import com.itechro.cas.model.domain.casmaster.*;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.esg.*;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityOtherFacilityInformation;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityVitalInfoData;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.domain.lead.LeadDocument;
import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import com.itechro.cas.model.dto.applicationform.*;
import com.itechro.cas.model.dto.applicationform.applicationFormCustomer.AFCustomerAddressDTO;
import com.itechro.cas.model.dto.applicationform.applicationFormCustomer.AFCustomerTelephoneDTO;
import com.itechro.cas.model.dto.applicationform.applicationFormCustomer.SearchApplicationFormRQ;
import com.itechro.cas.model.dto.applicationform.esg.*;
import com.itechro.cas.model.dto.customer.CustomerCribLiabilityDTO;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.customer.SearchCustomerRQ;
import com.itechro.cas.model.dto.esg.*;
import com.itechro.cas.model.dto.facility.FacilityOtherFacilityInformationDTO;
import com.itechro.cas.model.dto.facility.FacilityVitalInfoDataDTO;
import com.itechro.cas.model.dto.facilitypaper.FPLoadOptionDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.integration.request.BranchAuthorityLevelRQ;
import com.itechro.cas.model.dto.lead.CompLeadFacilityDTO;
import com.itechro.cas.model.dto.lead.CustomFacilityInfoDTO;
import com.itechro.cas.model.dto.lead.LeadIdentificationDTO;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.command.AFStatusModifier;
import com.itechro.cas.service.applicationform.command.ApplicationFormModificationContext;
import com.itechro.cas.service.applicationform.command.replicate.ApplicationFormReplicateContext;
import com.itechro.cas.service.applicationform.command.replicate.ApplicationFormReplicateExecutor;
import com.itechro.cas.service.applicationform.support.*;
import com.itechro.cas.service.applicationform.support.afstatustransit.AFDeclineStatusHandler;
import com.itechro.cas.service.applicationform.support.afstatustransit.AFDraftStatusHandler;
import com.itechro.cas.service.applicationform.support.afstatustransit.AFInProgressStatusHandler;
import com.itechro.cas.service.applicationform.support.afstatustransit.AFReturnStatusHandler;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.faclititypaper.FacilityPaperService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.lead.LeadService;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.service.storage.StorageService;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.util.WorkGroupUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationFormService {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationFormService.class);

    @Autowired
    private ApplicationFormRefProc applicationFormRefProc;

    @Autowired
    ApplicationFormDao applicationFormDao;

    @Autowired
    AFCommentDao afCommentDao;

    @Autowired
    BasicInformationDao basicInformationDao;

    @Autowired
    ApplicationFormJdbcDao applicationFormJdbcDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private DocStorageDao docStorageDao;

    @Autowired
    private SupportingDocDao supportingDocDao;

    @Autowired
    private AFDocumentDao afDocumentDao;

    @Autowired
    private OwnershipDetailsDao ownershipDetailsDao;

    @Autowired
    private AFFacilityDao afFacilityDao;

    @Autowired
    private CreditFacilityTemplateDao creditFacilityTemplateDao;

    @Autowired
    private AFTopicDao afTopicDao;

    @Autowired
    private AFTopicDataDao afTopicDataDao;

    @Autowired
    private AFSecurityDao afSecurityDao;

    @Autowired
    private AFSecurityJdbcDao afSecurityJdbcDao;

    @Autowired
    private AFFacilitySecurityDao afFacilitySecurityDao;

    @Autowired
    private AFCribReportDao afCribReportDao;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private AFTopicConfigDao afTopicConfigDao;

    @Autowired
    private FacilityPaperService facilityPaperService;

    @Autowired
    private FacilityPaperDao facilityPaperDao;

    @Autowired
    private FacilitySecurityDao facilitySecurityDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AFCustomerDao afCustomerDao;

    @Autowired
    private CustomerJdbcDao customerJdbcDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private WorkFlowTemplateDao workFlowTemplateDao;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private AFFacilityDocumentDao afFacilityDocumentDao;

    @Autowired
    private AFCustomerAddressDao afCustomerAddressDao;

    @Autowired
    private AFCustomerTelephoneDao afCustomerTelephoneDao;

    @Autowired
    private ApplicationFormReplicateExecutor applicationFormReplicateExecutor;

    @Autowired
    MasterDataJdbcDao masterDataJdbcDao;

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private LeadDocumentDao leadDocumentDao;

    @Autowired
    private AFStatusModifier afStatusModifier;

    @Autowired
    private LeadService leadService;

    @Autowired
    private AnnexureDao afAnnexureDao;

    @Autowired
    private AFAnnexureDataDao afAnnexureDataDao;

    @Autowired
    private AFAnnexureQuestionDataDao afAnnexureQuestionDataDao;

    @Autowired
    private AFAnnexureAnswerDataDao afAnnexureAnswerDataDao;

    @Autowired
    private EnvironmentalRiskDao environmentalRiskDao;

    @Autowired
    private EnvironmentalRiskDataDao environmentalRiskDataDao;

    @Autowired
    private AnswerDataDao answerDataDao;

    @Autowired
    private RiskOpinionDao riskOpinionDao;

    @Autowired
    private RiskOpinionReplyDao riskOpinionReplyDao;

    @Autowired
    private EsgDocStorageDao esgDocStorageDao;

    @Autowired
    private LeadJdbcDao leadJdbcDao;

    @Autowired
    private LeadDao leadDao;

    @Autowired
    private CustomFacilityDao customFacilityDao;

    @Autowired
    private FacilityDao facilityDao;

    @Autowired
    private CreditFacilityTypeDao creditFacilityTypeDao;

    private final Object guard = new Object();

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getApplicationFormRef() throws AppsException {
        String ref = null;
        synchronized (guard) {
            ref = applicationFormRefProc.executeFunction();
        }
        return ref;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ApplicationFormDTO draftApplicationForm(DraftApplicationFormRQ draftApplicationFormRQ, CredentialsDTO credentialsDTO) throws Exception {
        LOG.info("START : Draft Application Form : {} : {}", draftApplicationFormRQ, credentialsDTO.getUserID());

        AFDraftBuilder afDraftBuilder = new AFDraftBuilder(draftApplicationFormRQ, credentialsDTO);
        afDraftBuilder.setApplicationFormService(this);
        afDraftBuilder.setWorkFlowTemplateDao(workFlowTemplateDao);
        afDraftBuilder.setApplicationFormDao(applicationFormDao);
        afDraftBuilder.setIntegrationService(integrationService);
        afDraftBuilder.setAfTopicConfigDao(afTopicConfigDao);
        afDraftBuilder.setCustomerJdbcDao(customerJdbcDao);
        afDraftBuilder.setCustomerService(customerService);
        afDraftBuilder.setAfCustomerDao(afCustomerDao);
        afDraftBuilder.setCustomerDao(customerDao);
        afDraftBuilder.setAfTopicDao(afTopicDao);
        afDraftBuilder.setLeadDao(leadDao);
        afDraftBuilder.setApplicationFormJdbcDao(applicationFormJdbcDao);
        ApplicationForm applicationForm = afDraftBuilder.buildBaseApplicationForm()
                .buildComment()
                .buildDefaultTopics()
                .buildBasicInformation()
                .buildStatusHistory()
                .getApplicationForm();

        if (draftApplicationFormRQ.getLeadID()!= null) {
            List<LeadDocument> leadDocuments = leadDocumentDao.findAllByLeadLeadIDAndStatus(draftApplicationFormRQ.getLeadID(), AppsConstants.Status.ACT);
            if (leadDocuments != null) {
                for (LeadDocument leadDocument : leadDocuments) {
                    AFDocument afDocument = new AFDocument();
                    afDocument.setRemark(leadDocument.getRemark());
                    afDocument.setStatus(leadDocument.getStatus());
                    afDocument.setCreatedDate(leadDocument.getCreatedDate());
                    afDocument.setCreatedBy(leadDocument.getCreatedBy());
                    afDocument.setVersion(leadDocument.getVersion());
                    afDocument.setUploadedDivCode(leadDocument.getUploadedDivCode());
                    afDocument.setUploadedUserDisplayName(leadDocument.getUploadedUserDisplayName());
                    afDocument.setDocStorage(leadDocument.getDocStorage());
                    afDocument.setSupportingDoc(leadDocument.getSupportingDoc());
                    applicationForm.addAFDocument(afDocument);
                }
            }
        }

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        //saveBasicInfo(applicationForm, draftApplicationFormRQ, credentialsDTO);


        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadAllData();

        AFFacilityLoadOptionDTO afFacilityLoadOptionDTO = new AFFacilityLoadOptionDTO();
        afFacilityLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfFacilityLoadOptionDTO(afFacilityLoadOptionDTO);

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        leadService.addLeadDetailsToCrmRequest(draftApplicationFormRQ.getLeadID());

        LOG.info("END : Draft Application Form : {} : {}", draftApplicationFormRQ, credentialsDTO.getUserID());
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateApplicationForm(ApplicationFormDTO applicationFormDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : save or Update Application Form : {} : {}", applicationFormDTO, credentialsDTO.getUserID());

        Date date = new Date();
        boolean isNew = applicationFormDTO.getApplicationFormID() == null;
        ApplicationForm applicationForm;
        if (isNew) {
            applicationForm = new ApplicationForm();
            applicationForm.setAfRefNumber(this.getApplicationFormRef());
            applicationForm.setCreatedDate(date);
            applicationForm.setCreatedBy(credentialsDTO.getUserName());
            applicationForm.setCreatedUserDisplayName(applicationFormDTO.getCreatedUserDisplayName());
            applicationForm.setCreatedUserID(applicationFormDTO.getCreatedUserID());
        } else {
            applicationForm = applicationFormDao.getOne(applicationFormDTO.getApplicationFormID());
            applicationForm.setModifiedDate(date);
            applicationForm.setModifiedBy(credentialsDTO.getUserName());
        }

        applicationForm.setBranchCode(applicationFormDTO.getBranchCode());
        applicationForm.setCurrentApplicationFormStatus(applicationFormDTO.getCurrentApplicationFormStatus());

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);
        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadAllData();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        LOG.info("END : Save or Update Application Form : {} : {}", applicationFormDTO, credentialsDTO.getUserID());
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO replicateApplicationForm(ReplicateApplicationFormRQ replicateApplicationFormRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Replicate Application Form : {} : {}", replicateApplicationFormRQ, credentialsDTO.getUserID());

        ApplicationFormReplicateContext context = new ApplicationFormReplicateContext();
        context.setDate(new Date());
        context.setCredentialsDto(credentialsDTO);
        context.setReplicateApplicationFromFormRQ(replicateApplicationFormRQ);
        context.setNewApplicationFormRefNumber(this.getApplicationFormRef());
        context.setWorkflowTemplate(workFlowTemplateDao.getOne(replicateApplicationFormRQ.getWorkflowTemplateID()));
        applicationFormReplicateExecutor.execute(context);
        ApplicationForm applicationForm = applicationFormDao.saveAndFlush(context.getNewApplicationForm());
        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        AFFacilityLoadOptionDTO afFacilityLoadOptionDTO = new AFFacilityLoadOptionDTO();
        afFacilityLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);
        afLoadOptionsDTO.setAfFacilityLoadOptionDTO(afFacilityLoadOptionDTO);
        afLoadOptionsDTO.loadAllData();

        LOG.info("END : Replicate Application Form : {} : {}", replicateApplicationFormRQ, credentialsDTO.getUserID());
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<ApplicationFormDTO> getPagedApplicationForm(ApplicationFormSearchRQ applicationFormSearchRQ) {
        return applicationFormJdbcDao.getPagedApplicationFormDTO(applicationFormSearchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<ApplicationFormPageRSDTO> getInboxPagedApplicationForms(ApplicationFormSearchRQ applicationFormSearchRQ) {
        return applicationFormJdbcDao.getInboxPagedApplicationForms(applicationFormSearchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<ApplicationFormPageRSDTO> getPagedBranchApplicationForm(ApplicationFormSearchRQ applicationFormSearchRQ) {
        return applicationFormJdbcDao.getPagedBranchApplicationForm(applicationFormSearchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<ApplicationFormCopyRSDTO> getCopyPagedApplicationForms(ApplicationFormSearchRQ applicationFormSearchRQ) {
        return applicationFormJdbcDao.getCopyPagedApplicationForms(applicationFormSearchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO getApplicationFormByID(Integer applicationFormID) throws AppsException {
        LOG.info("START : Load Application Form details by ID : {} ", applicationFormID);

        ApplicationForm applicationForm = this.applicationFormDao.getOne(applicationFormID);

        AFLoadOptionsDTO loadOptionDTO = new AFLoadOptionsDTO();

        AFFacilityLoadOptionDTO afFacilityLoadOptionDTO = new AFFacilityLoadOptionDTO();
        afFacilityLoadOptionDTO.loadAllData();
        loadOptionDTO.setAfFacilityLoadOptionDTO(afFacilityLoadOptionDTO);
        loadOptionDTO.loadAllData();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        loadOptionDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        ApplicationFormDTO applicationFormDTO = new ApplicationFormDTO(applicationForm, loadOptionDTO);

        // Retrieve AnswerData entities associated with the application form
        List<AnswerData> answerDataList = answerDataDao.findByApplicationForm_ApplicationFormID(applicationFormID);

        // Map AnswerData entities to AnswerDataDTO objects
        List<AnswerDataDTO> answerDataDTOList = answerDataList.stream()
                .map(AnswerDataDTO::new)
                .collect(Collectors.toList());

        // Add AnswerDataDTO list to the ApplicationFormDTO
        applicationFormDTO.setAnswerDataDTO(answerDataDTOList);

        //set analytics decision
        if (applicationFormDTO.getLeadID() != null) {
            AnalyticsDecisionDTO analyticsDecision = leadJdbcDao.getAnalyticsDecisionByLead(applicationFormDTO.getLeadID());
            if (analyticsDecision != null) {
                applicationFormDTO.setAnalyticsDecision(analyticsDecision);
            }
        }
        LOG.info("END : Load Application Form details by ID : {} ", applicationFormID);
        return applicationFormDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateAFBasicDetails(BasicInformationDTO basicInformationDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Application Form Basic Detail Save Or Update : {} : {}", basicInformationDTO, credentialsDTO.getUserID());

        Date date = new Date();
        ApplicationForm applicationForm;
        if (basicInformationDTO.getApplicationFormID() != null) {
            applicationForm = this.applicationFormDao.getOne(basicInformationDTO.getApplicationFormID());

            boolean isNew = basicInformationDTO.getBasicInformationID() == null;
            BasicInformation basicInformation;
            if (isNew) {
                basicInformation = new BasicInformation();
                basicInformation.setStatus(AppsConstants.Status.ACT);
                basicInformation.setCreatedBy(credentialsDTO.getUserName());
                basicInformation.setCreatedDate(date);
            } else {
                basicInformation = applicationForm.getBasicInformationByID(basicInformationDTO.getBasicInformationID());
                basicInformation.setModifiedBy(credentialsDTO.getUserName());
                basicInformation.setModifiedDate(date);
                if (basicInformationDTO.getStatus() != null) {
                    basicInformation.setStatus(basicInformationDTO.getStatus());
                }
            }

            basicInformation.setTitle(basicInformationDTO.getTitle());
            basicInformation.setType(basicInformationDTO.getType());
            basicInformation.setNameWithInitials(basicInformationDTO.getNameWithInitials());
            basicInformation.setInitialRepresentation(basicInformationDTO.getInitialRepresentation());
            basicInformation.setNameOfBusiness(basicInformationDTO.getNameOfBusiness());
            basicInformation.setRegistrationNo(basicInformationDTO.getRegistrationNo());
            basicInformation.setConstitution(basicInformationDTO.getConstitution());
            if (StringUtils.isNotBlank(basicInformationDTO.getDateOfIncorporateStr())) {
                basicInformation.setDateOfIncorporate(CalendarUtil.getDefaultParsedDateOnly(basicInformationDTO.getDateOfIncorporateStr()));
            }
            if (StringUtils.isNotBlank(basicInformationDTO.getDateOfCommencementStr())) {
                basicInformation.setDateOfCommencement(CalendarUtil.getDefaultParsedDateOnly(basicInformationDTO.getDateOfCommencementStr()));
            }
            if (StringUtils.isNotBlank(basicInformationDTO.getDateOfRegistrationStr())) {
                basicInformation.setDateOfRegistration(CalendarUtil.getDefaultParsedDateOnly(basicInformationDTO.getDateOfRegistrationStr()));
            }
            basicInformation.setNatureOfBusiness(basicInformationDTO.getNatureOfBusiness());
            basicInformation.setNoOfBusinessYears(basicInformationDTO.getNoOfBusinessYears());
            basicInformation.setCitizenship(basicInformationDTO.getCitizenship());
            basicInformation.setPrivateAddress(basicInformationDTO.getPrivateAddress());
            basicInformation.setOfficialAddress(basicInformationDTO.getOfficialAddress());
            basicInformation.setBusinessAddress(basicInformationDTO.getBusinessAddress());
            basicInformation.setTelNumber(basicInformationDTO.getTelNumber());
            basicInformation.setEmailAddress(basicInformationDTO.getEmailAddress());
            if (StringUtils.isNotBlank(basicInformationDTO.getDateOfBirthStr())) {
                basicInformation.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(basicInformationDTO.getDateOfBirthStr()));
            }
            basicInformation.setPlaceOfBirth(basicInformationDTO.getPlaceOfBirth());
            basicInformation.setCivilStatus(basicInformationDTO.getCivilStatus());
            basicInformation.setNationality(basicInformationDTO.getNationality());
            basicInformation.setIdentificationNo(basicInformationDTO.getIdentificationNo());
            basicInformation.setEmployment(basicInformationDTO.getEmployment());
            basicInformation.setEmployer(basicInformationDTO.getEmployer());
            basicInformation.setHighestEduAchievement(basicInformationDTO.getHighestEduAchievement());
            basicInformation.setPosition(basicInformationDTO.getPosition());
            basicInformation.setNoOfYearsEmployment(basicInformationDTO.getNoOfYearsEmployment());
            basicInformation.setIdentificationType(basicInformationDTO.getIdentificationType());
            basicInformation.setCapitalAuthorized(basicInformationDTO.getCapitalAuthorized());
            basicInformation.setCapitalIssued(basicInformationDTO.getCapitalIssued());
            basicInformation.setCapitalPaidUp(basicInformationDTO.getCapitalPaidUp());
            basicInformation.setPrimaryInformation(basicInformationDTO.getPrimaryInformation());
            if (basicInformationDTO.getPrimaryInformation() == AppsConstants.YesNo.Y) {
                applicationForm.setFormType(basicInformationDTO.getType());
            }

            if (basicInformationDTO.getAfCustomerDTO() != null) {

                basicInformation.getAfCustomer().setEmailAddress(basicInformationDTO.getAfCustomerDTO().getEmailAddress());
                if (StringUtils.isNotBlank(basicInformationDTO.getDateOfBirthStr())) {
                    basicInformation.getAfCustomer().setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(basicInformationDTO.getDateOfBirthStr()));
                }
                for (AFCustomerAddressDTO afCustomerAddressDTO : basicInformationDTO.getAfCustomerDTO().getAfCustomerAddressDTOList()) {
                    if (afCustomerAddressDTO.getStatus() == AppsConstants.Status.ACT) {
                        boolean isNewCustomerAddress = afCustomerAddressDTO.getAfCustomerAddressID() == null;
                        AFCustomerAddress afCustomerAddress;
                        if (isNewCustomerAddress) {
                            afCustomerAddress = new AFCustomerAddress();
                            afCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
                            afCustomerAddress.setCreatedDate(date);
                        } else {
                            afCustomerAddress = afCustomerAddressDao.getOne(afCustomerAddressDTO.getAfCustomerAddressID());
                            afCustomerAddress.setModifiedBy(credentialsDTO.getUserName());
                            afCustomerAddress.setCreatedDate(date);
                        }
                        afCustomerAddress.setAddress1(afCustomerAddressDTO.getAddress1());
                        afCustomerAddress.setAddress2(afCustomerAddressDTO.getAddress2());
                        afCustomerAddress.setCity(afCustomerAddressDTO.getCity());
                        afCustomerAddress.setAddressType(afCustomerAddressDTO.getAddressType());
                        afCustomerAddress.setStatus(afCustomerAddressDTO.getStatus());
                        basicInformation.getAfCustomer().addAfCustomerAddress(afCustomerAddress);
                    }
                }

                for (AFCustomerTelephoneDTO afCustomerTelephoneDTO : basicInformationDTO.getAfCustomerDTO().getAfCustomerTelephoneDTOList()) {
                    if (afCustomerTelephoneDTO.getStatus() == AppsConstants.Status.ACT) {
                        boolean isNewTelephoneNO = afCustomerTelephoneDTO.getAfCustomerTelephoneID() == null;
                        AFCustomerTelephone afCustomerTelephone;
                        if (isNewTelephoneNO) {
                            afCustomerTelephone = new AFCustomerTelephone();
                            afCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                            afCustomerTelephone.setCreatedDate(date);
                        } else {
                            afCustomerTelephone = afCustomerTelephoneDao.getOne(afCustomerTelephoneDTO.getAfCustomerTelephoneID());
                            afCustomerTelephone.setModifiedBy(credentialsDTO.getUserName());
                            afCustomerTelephone.setCreatedDate(date);
                        }
                        afCustomerTelephone.setDescription(afCustomerTelephoneDTO.getDescription());
                        afCustomerTelephone.setContactNumber(afCustomerTelephoneDTO.getContactNumber());
                        afCustomerTelephone.setStatus(afCustomerTelephoneDTO.getStatus());
                        basicInformation.getAfCustomer().addAfCustomerTelephone(afCustomerTelephone);
                    }
                }
            }

            applicationForm.addBasicInformation(basicInformation);
        } else {
            LOG.error("ERROR : Invalid Application Form Id : {} Not Found ", basicInformationDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_NOT_FOUND);
        }

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        LOG.info("END : Application Form Basic Detail Save Or Update : {} : {}", basicInformationDTO, credentialsDTO.getUserID());

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO uploadApplicationFormDocument(AFDocumentUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload Application Form document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();

        ApplicationForm applicationForm = this.applicationFormDao.getOne(uploadRQ.getApplicationFormID());
        AFDocument afDocument = null;
        Boolean isNewAFDoc = uploadRQ.getAfDocumentID() == null;
        if (isNewAFDoc) {
            afDocument = new AFDocument();
            afDocument.setCreatedBy(credentialsDTO.getUserName());
            afDocument.setCreatedDate(date);
            afDocument.setUploadedDivCode(uploadRQ.getUploadedDivCode());
            afDocument.setUploadedUserDisplayName(uploadRQ.getUploadedUserDisplayName());
            applicationForm.addAFDocument(afDocument);
        } else {
            afDocument = applicationForm.getAFDocumentByID(uploadRQ.getAfDocumentID());
            afDocument.setModifiedBy(credentialsDTO.getUserName());
            afDocument.setModifiedDate(date);
        }
        if (uploadRQ.getDocStorageDTO() != null) {
            uploadRQ.getDocStorageDTO().setDescription("APPLICATION FORM : " + applicationForm.getAfRefNumber() + "");
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
            afDocument.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Application Form Document data null : {}", uploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_AF_DOCUMENT_NULL_SUPPORT_DOC);
        }

        afDocument.setRemark(uploadRQ.getRemark());
        afDocument.setSupportingDoc(this.supportingDocDao.getOne(uploadRQ.getSupportingDocID()));
        afDocument.setStatus(uploadRQ.getStatus());

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Upload Application Form document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        AFLoadOptionsDTO loadOptionDTO = new AFLoadOptionsDTO();
        loadOptionDTO.loadDocument();
        return new ApplicationFormDTO(applicationForm, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO deactivateApplicationFormSupportingDoc(AFDocumentDTO afDocumentDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the Application Form supporting document: {} by: {}", afDocumentDTO, credentialsDTO.getUserName());
        ApplicationForm applicationForm = applicationFormDao.getOne(afDocumentDTO.getApplicationFormID());
        AFDocument afDocument = null;
        if (afDocumentDTO.getSupportingDocID() != null) {
            afDocument = afDocumentDao.getOne(afDocumentDTO.getAfDocumentID());
            afDocument.setStatus(AppsConstants.Status.INA);
            afDocument.setModifiedBy(credentialsDTO.getUserName());
            afDocument.setModifiedDate(new Date());
            applicationForm.addAFDocument(afDocument);
        }

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Deactivate the Application Form supporting document: {} by: {}", afDocumentDTO, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadDocument();
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateOwnershipDetails(OwnershipDetailsDTO ownershipDetailsDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Application Form Ownership Details Save Or Update : {} : {}", ownershipDetailsDTO, credentialsDTO.getUserID());

        Date date = new Date();
        ApplicationForm applicationForm;
        BasicInformation basicInformation;
        OwnershipDetails ownershipDetails;
        if (ownershipDetailsDTO.getApplicationFormID() != null) {
            applicationForm = this.applicationFormDao.getOne(ownershipDetailsDTO.getApplicationFormID());
            basicInformation = applicationForm.getBasicInformationByID(ownershipDetailsDTO.getBasicInformationID());

            boolean isNew = ownershipDetailsDTO.getOwnershipDetailsID() == null;

            if (isNew) {
                ownershipDetails = new OwnershipDetails();
                ownershipDetails.setStatus(AppsConstants.Status.ACT);
                ownershipDetails.setCreatedBy(credentialsDTO.getUserName());
                ownershipDetails.setCreatedDate(date);
            } else {
                ownershipDetails = this.ownershipDetailsDao.getOne(ownershipDetailsDTO.getOwnershipDetailsID());
                ownershipDetails.setModifiedBy(credentialsDTO.getUserName());
                ownershipDetails.setModifiedDate(date);
                if (ownershipDetailsDTO.getStatus() != null) {
                    ownershipDetails.setStatus(ownershipDetailsDTO.getStatus());
                }
            }

            if (StringUtils.isNotBlank(ownershipDetailsDTO.getDateOfBirthStr())) {
                ownershipDetails.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(ownershipDetailsDTO.getDateOfBirthStr()));
            }

            ownershipDetails.setName(ownershipDetailsDTO.getName());
            ownershipDetails.setAddress(ownershipDetailsDTO.getAddress());
            ownershipDetails.setContactNo(ownershipDetailsDTO.getContactNo());
            ownershipDetails.setIdentificationType(ownershipDetailsDTO.getIdentificationType());
            ownershipDetails.setIdentificationNumber(ownershipDetailsDTO.getIdentificationNumber());
            ownershipDetails.setShareHolding(ownershipDetailsDTO.getShareHolding());
            ownershipDetails.setCivilStatus(ownershipDetailsDTO.getCivilStatus());
            ownershipDetails.setConstitutionType(ownershipDetailsDTO.getConstitutionType());
            if (StringUtils.isNotBlank(ownershipDetailsDTO.getDateOfBirthStr())) {
                ownershipDetails.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(ownershipDetailsDTO.getDateOfBirthStr()));
            }
            ownershipDetails.setCreditCard(ownershipDetailsDTO.getCreditCard());
            ownershipDetails.setApplicationFormID(applicationForm.getApplicationFormID());
            basicInformation.addOwnerShipDetails(ownershipDetails);
            applicationForm.addBasicInformation(basicInformation);

        } else {
            LOG.error("ERROR : Invalid Application Form Id : {} Not Found ", ownershipDetailsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_NOT_FOUND);
        }

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);
        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        LOG.info("END : Application Form Ownership Details Save Or Update : {} : {}", ownershipDetailsDTO, credentialsDTO.getUserID());

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateFinancialObligations(AFFinancialObligationDTO afFinancialObligationDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Application Form Financial Obligation Save Or Update : {} : {}", afFinancialObligationDTO, credentialsDTO.getUserID());
        ApplicationForm applicationForm = applicationFormDao.getOne(afFinancialObligationDTO.getApplicationFormID());
        BasicInformation basicInformation = applicationForm.getBasicInformationByID(afFinancialObligationDTO.getBasicInformationID());
        AFFinancialObligation afFinancialObligation;
        Date date = new Date();
        boolean isNew = afFinancialObligationDTO.getFinancialObligationID() == null;
        if (isNew) {
            afFinancialObligation = new AFFinancialObligation();
            afFinancialObligation.setCreatedBy(credentialsDTO.getUserName());
            afFinancialObligation.setCreatedDate(date);
            afFinancialObligation.setIsCribRecord(AppsConstants.YesNo.N);
        } else {
            afFinancialObligation = basicInformation.getAFFinancialObligationByID(afFinancialObligationDTO.getFinancialObligationID());
            afFinancialObligation.setModifiedUserDisplayName(afFinancialObligationDTO.getModifiedUserDisplayName());
            afFinancialObligation.setModifiedBy(credentialsDTO.getUserName());
            afFinancialObligation.setModifiedDate(date);
        }
        afFinancialObligation.setStatus(afFinancialObligationDTO.getStatus());
        afFinancialObligation.setIdentificationNumber(afFinancialObligationDTO.getIdentificationNumber());
        afFinancialObligation.setIdentificationType(afFinancialObligationDTO.getIdentificationType());
        afFinancialObligation.setArrears(afFinancialObligationDTO.getArrears());
        afFinancialObligation.setFinancialInstitution(afFinancialObligationDTO.getFinancialInstitution());
        afFinancialObligation.setOriginalAmount(afFinancialObligationDTO.getOriginalAmount());
        afFinancialObligation.setPresentOutstanding(afFinancialObligationDTO.getPresentOutstanding());
        afFinancialObligation.setSecurities(afFinancialObligationDTO.getSecurities());
        afFinancialObligation.setSignedAs(afFinancialObligationDTO.getSignedAs());

        basicInformation.addAFFinancialObligation(afFinancialObligation);

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END : Application Form Financial Obligation Save Or Update : {} : {}", afFinancialObligationDTO, credentialsDTO.getUserID());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadFinancialObligations();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateAFFacility(AFFacilityDTO affacilityDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Application form Facility save update : {} : {}", affacilityDTO, credentialsDTO.getUserID());
        AFFacilityBuilder afFacilityBuilder = new AFFacilityBuilder(affacilityDTO, credentialsDTO);
        afFacilityBuilder.setCreditFacilityTemplateDao(creditFacilityTemplateDao);
        afFacilityBuilder.setSystemParameterService(systemParameterService);
        afFacilityBuilder.setApplicationFormDao(applicationFormDao);
        afFacilityBuilder.setAfFacilityDao(afFacilityDao);

        AFFacility afFacility = afFacilityBuilder.loadFacility()
                .buildBaseFacility()
                .buildInterestRate()
                .buildVitalInfo()
                .getAfFacility();

        afFacility = afFacilityDao.saveAndFlush(afFacility);

        //Audit TODO
        LOG.info("END : Application form Facility save update : {} : {}", affacilityDTO, credentialsDTO.getUserID());

        ApplicationForm applicationForm = applicationFormDao.getOne(affacilityDTO.getApplicationFormID());
        AFLoadOptionsDTO loadOption = new AFLoadOptionsDTO();
        loadOption.loadFacilities();

        AFFacilityLoadOptionDTO afFacilityLoadOptionDTO = new AFFacilityLoadOptionDTO();
        afFacilityLoadOptionDTO.loadAllData();
        loadOption.setAfFacilityLoadOptionDTO(afFacilityLoadOptionDTO);

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        loadOption.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, loadOption);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateApplicationFormTopics(AFTopicDataDTO afTopicDataDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Application Form Topics Save Or Update : {} : {}", afTopicDataDTO, credentialsDTO.getUserID());

        Date date = new Date();
        ApplicationForm applicationForm;
        AFTopicData afTopicData;

        if (afTopicDataDTO.getApplicationFormID() != null) {
            applicationForm = this.applicationFormDao.getOne(afTopicDataDTO.getApplicationFormID());

            boolean isNew = afTopicDataDTO.getTopicDataID() == null;

            if (isNew) {
                AFTopic afTopic = afTopicDao.findByApproveStatusAndStatusAndTopicID(DomainConstants.MasterDataApproveStatus.APPROVED, AppsConstants.Status.ACT, afTopicDataDTO.getTopicID());
                afTopicData = new AFTopicData();
                afTopicData.setStatus(AppsConstants.Status.ACT);
                afTopicData.setCreatedBy(credentialsDTO.getUserName());
                afTopicData.setCreatedDate(date);
                afTopicData.setTopic(afTopic);
                afTopicData.setTopicData(afTopicDataDTO.getTopicData());
            } else {
                afTopicData = this.afTopicDataDao.getOne(afTopicDataDTO.getTopicDataID());
                afTopicData.setModifiedBy(credentialsDTO.getUserName());
                afTopicData.setModifiedDate(date);
                if (afTopicDataDTO.getStatus() != null) {
                    afTopicData.setStatus(afTopicDataDTO.getStatus());

                    if (afTopicDataDTO.getStatus() == AppsConstants.Status.INA) {
                        afTopicData.setTopicData(null);// this is for reduce the data load of the database
                    } else {
                        afTopicData.setTopicData(afTopicDataDTO.getTopicData());
                    }
                }
            }
            afTopicData.setRemark(afTopicDataDTO.getRemark());
            afTopicData.setPage(afTopicDataDTO.getPage());
            applicationForm.addAFTopicData(afTopicData);

        } else {
            LOG.error("ERROR : Invalid Application Form Id : {} Not Found ", afTopicDataDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_NOT_FOUND);
        }

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);
        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadAFTopics();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        LOG.info("END : Application Form Topics Save Or Update : {} : {}", afTopicDataDTO, credentialsDTO.getUserID());

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO deactivateApplicationFormTopic(AFTopicDataDTO afTopicDataDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the Application Form Topic: {} by: {}", afTopicDataDTO, credentialsDTO.getUserName());
        ApplicationForm applicationForm = applicationFormDao.getOne(afTopicDataDTO.getApplicationFormID());
        AFTopicData afTopicData = null;
        if (afTopicDataDTO.getTopicDataID() != null) {
            afTopicData = afTopicDataDao.getOne(afTopicDataDTO.getTopicDataID());
            afTopicData.setStatus(AppsConstants.Status.INA);
            afTopicData.setTopicData(null);
            afTopicData.setModifiedBy(credentialsDTO.getUserName());
            afTopicData.setModifiedDate(new Date());
            applicationForm.addAFTopicData(afTopicData);
        }

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Deactivate the Application Form Topic: {} by: {}", afTopicDataDTO, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadAFTopics();
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveUpdateFacilitySecurity(AFSecurityDTO afSecurityDTO, CredentialsDTO credentialsDTO) {

        LOG.info("START: Save or Update application Form Facility security: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        Date date = new Date();
        AFSecurity afSecurity;

        boolean isNewSecurity = afSecurityDTO.getSecurityID() == null;

        if (!isNewSecurity) {
            afSecurity = afSecurityDao.getOne(afSecurityDTO.getSecurityID());
            afSecurity.setModifiedDate(date);
            afSecurity.setModifiedBy(credentialsDTO.getUserName());
        } else {
            afSecurity = new AFSecurity();
            afSecurity.setCreatedDate(date);
            afSecurity.setCreatedBy(credentialsDTO.getUserName());
        }
        afSecurity.setSecurityCode(afSecurityDTO.getSecurityCode());
        afSecurity.setSecurityDetail(afSecurityDTO.getSecurityDetail());
        afSecurity.setSecurityAmount(afSecurityDTO.getSecurityAmount());
        afSecurity.setCashAmount(afSecurityDTO.getCashAmount());
        afSecurity.setSecurityCurrency(afSecurityDTO.getSecurityCurrency());
        afSecurity.setStatus(afSecurityDTO.getStatus());
        if (afSecurityDTO.getIsCommonSecurity() != null) {
            afSecurity.setIsCommonSecurity(afSecurityDTO.getIsCommonSecurity());
        }
        if (afSecurityDTO.getIsCashSecurity() != null) {
            afSecurity.setIsCashSecurity(afSecurityDTO.getIsCashSecurity());
        }

        if (afSecurityDTO.getAfFacilitySecurityDTOS() != null && !afSecurityDTO.getAfFacilitySecurityDTOS().isEmpty()) {
            for (AFFacilitySecurityDTO afFacilitySecurityDTO : afSecurityDTO.getAfFacilitySecurityDTOS()) {

                AFFacilitySecurityDTO existingFacilitySecurityDTO = afSecurityJdbcDao.getFacilityFacilitySecurityDTO(afFacilitySecurityDTO);
                boolean isNew = existingFacilitySecurityDTO == null;
                AFFacilitySecurity afFacilitySecurity = null;
                if (isNew) {
                    afFacilitySecurity = new AFFacilitySecurity();
                } else {
                    afFacilitySecurity = afFacilitySecurityDao.getOne(existingFacilitySecurityDTO.getFacilitySecurityID());
                }
                afFacilitySecurity.setAfSecurity(afSecurity);
                afFacilitySecurity.setAfFacility(afFacilityDao.getOne(afFacilitySecurityDTO.getFacilityID()));

                if (afFacilitySecurityDTO.getFacilitySecurityAmount() != null) {
                    afFacilitySecurity.setFacilitySecurityAmount(afFacilitySecurityDTO.getFacilitySecurityAmount());
                } else {
                    afFacilitySecurity.setFacilitySecurityAmount(DecimalCalculator.getDefaultZero());
                }

                if (afFacilitySecurityDTO.getIsAddedFacility() == AppsConstants.YesNo.N) {
                    afFacilitySecurity.setFacilitySecurityAmount(DecimalCalculator.getDefaultZero());
                }
                afFacilitySecurity.setIsCashSecurity(afFacilitySecurityDTO.getIsCashSecurity());
                afFacilitySecurity.setStatus(afFacilitySecurityDTO.getStatus());
                afSecurity.addAFFacilitySecurities(afFacilitySecurity);
            }
        }

        LOG.info("End: Save or Update application Form Facility security: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        afSecurityDao.saveAndFlush(afSecurity);

        ApplicationForm applicationForm = applicationFormDao.getOne(afSecurityDTO.getApplicationFormID());
        AFLoadOptionsDTO loadOptions = new AFLoadOptionsDTO();
        loadOptions.loadFacilities();
        loadOptions.setAfFacilityLoadOptionDTO(new AFFacilityLoadOptionDTO());
        loadOptions.getAfFacilityLoadOptionDTO().loadAllData();
        return new ApplicationFormDTO(applicationForm, loadOptions);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateCribReports(AFCribReportUpdateRQ afCribReportUpdateRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Save or Update Crib Reports data : {} : {}", afCribReportUpdateRQ, credentialsDTO.getUserID());

        Date date = new Date();
        ApplicationForm applicationForm;
        BasicInformation basicInformation;
        AFCribReport afCribReport;
        applicationForm = applicationFormDao.getOne(afCribReportUpdateRQ.getApplicationFormID());

        if (applicationForm.getApplicationFormID() != null) {

            try {
                basicInformation = basicInformationDao.findByIdentificationNoAndStatusAndApplicationForm_ApplicationFormID(afCribReportUpdateRQ.getIdentificationNo(), AppsConstants.Status.ACT, afCribReportUpdateRQ.getApplicationFormID());

                if (basicInformation == null) {
                    basicInformation = new BasicInformation();
                    basicInformation.setType(applicationForm.getFormType());
                    basicInformation.setPrimaryInformation(AppsConstants.YesNo.N);
                    basicInformation.setIdentificationType(afCribReportUpdateRQ.getIdentificationType());
                    basicInformation.setIdentificationNo(afCribReportUpdateRQ.getIdentificationNo());

                    SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
                    searchCustomerRQ.setIdentificationNumber(afCribReportUpdateRQ.getIdentificationNo());
                    searchCustomerRQ.setIdentificationType(afCribReportUpdateRQ.getIdentificationType().name());

                    try {
                        CustomerDTO customerDTO = integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);
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

                        basicInformation.setAfCustomer(afCustomerDao.saveAndFlush(afCustomer));
                    } catch (Exception e) {
                        LOG.error("ERROR : Application Form Set Finacle Customer with CAS Customer : {}", searchCustomerRQ, e);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CUSTOMER360_CUSTOMER_CANNOT_FIND);
                    }

                    basicInformation.setStatus(AppsConstants.Status.ACT);
                    basicInformation.setCreatedBy(credentialsDTO.getUserName());
                    basicInformation.setCreatedDate(date);
                }
            } catch (Exception e) {
                LOG.error("ERROR : Invalid Basic Information records for Application Form Id : {} ", afCribReportUpdateRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_BASIC_INFORMATION_NOT_FOUND);
            }


            boolean isNewCribReport = afCribReportUpdateRQ.getCribReportID() == null;
            if (isNewCribReport) {
                afCribReport = new AFCribReport();
                afCribReport.setCreatedDate(date);
                afCribReport.setCreatedBy(credentialsDTO.getUserName());
                afCribReport.setStatus(AppsConstants.Status.ACT);
            } else {
                afCribReport = afCribReportDao.getOne(afCribReportUpdateRQ.getCribReportID());
                afCribReport.setModifiedBy(credentialsDTO.getUserName());
                afCribReport.setModifiedDate(date);
                afCribReport.setStatus(afCribReportUpdateRQ.getStatus());
            }
            afCribReport.setIdentificationType(afCribReportUpdateRQ.getIdentificationType());
            afCribReport.setIdentificationNo(afCribReportUpdateRQ.getIdentificationNo());
            afCribReport.setReportContent(afCribReportUpdateRQ.getReportContent());
            afCribReport.setCribStatus(afCribReportUpdateRQ.getCribStatus());
            afCribReport.setRemark(afCribReportUpdateRQ.getRemark());
            if (StringUtils.isNotBlank(afCribReportUpdateRQ.getCribDateStr())) {
                afCribReport.setCribDate(CalendarUtil.getDefaultParsedDateOnly(afCribReportUpdateRQ.getCribDateStr()));
            }

            AFCribReportDTO afCribReportDTO = new AFCribReportDTO();
            afCribReportDTO.setIdentificationNo(afCribReportUpdateRQ.getIdentificationNo());
            afCribReportDTO.setIdentificationType(afCribReportUpdateRQ.getIdentificationType());
            afCribReportDTO.setCribDateStr(afCribReportUpdateRQ.getCribDateStr());

            List<CustomerCribLiabilityDTO> customerCribLiabilityDTOS = customerJdbcDao.getCustomerCribLiabilitiesList(afCribReportDTO);

            for (CustomerCribLiabilityDTO customerCribLiabilityDTO : customerCribLiabilityDTOS) {
                AFFinancialObligation afFinancialObligation = new AFFinancialObligation();
                afFinancialObligation.setIsCribRecord(AppsConstants.YesNo.Y);
                afFinancialObligation.setCustomerCribLiabilityID(customerCribLiabilityDTO.getCustomerCribLiabilityID());
                afFinancialObligation.setCustomerCribResponseID(customerCribLiabilityDTO.getCustomerCribResponseID());
                afFinancialObligation.setIdentificationType(customerCribLiabilityDTO.getIdentificationType());
                afFinancialObligation.setIdentificationNumber(customerCribLiabilityDTO.getIdentificationNumber());
                afFinancialObligation.setFinancialInstitution(customerCribLiabilityDTO.getFinancialInstitution());
                afFinancialObligation.setOriginalAmount(new BigDecimal(customerCribLiabilityDTO.getOriginalAmount().replaceAll(",", "")));
                afFinancialObligation.setPresentOutstanding(new BigDecimal(customerCribLiabilityDTO.getPresentOutstanding().replaceAll(",", "")));
                afFinancialObligation.setArrears(new BigDecimal(customerCribLiabilityDTO.getArrears().replaceAll(",", "")));
                afFinancialObligation.setSignedAs(customerCribLiabilityDTO.getSignedAs());
                afFinancialObligation.setSecurities(customerCribLiabilityDTO.getSecurities());
                afFinancialObligation.setModifiedUserDisplayName(customerCribLiabilityDTO.getModifiedUserDisplayName());
                afFinancialObligation.setStatus(customerCribLiabilityDTO.getStatus());
                afFinancialObligation.setCreatedBy(credentialsDTO.getUserName());
                afFinancialObligation.setCreatedDate(date);
                basicInformation.addAFFinancialObligation(afFinancialObligation);
            }

            basicInformation.addCribReport(afCribReport);
            applicationForm.addBasicInformation(basicInformation);

        } else {
            LOG.error("ERROR : Invalid Application Form Id : {} Not Found ", afCribReportUpdateRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_NOT_FOUND);
        }

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);
        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        LOG.info("END : Save or Update Crib Reports data : {} : {}", afCribReportUpdateRQ, credentialsDTO.getUserID());
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateOptionalCribReports(AFCribReportUpdateRQ afCribReportUpdateRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Save or Update Optional Crib Reports data : {} : {}", afCribReportUpdateRQ, credentialsDTO.getUserID());

        Date date = new Date();
        ApplicationForm applicationForm;
        BasicInformation basicInformation;
        AFCribReport afCribReport;
        applicationForm = applicationFormDao.getOne(afCribReportUpdateRQ.getApplicationFormID());

        if (applicationForm.getApplicationFormID() != null) {

            try {
                basicInformation = basicInformationDao.getOne(afCribReportUpdateRQ.getBasicInformationID());
            } catch (Exception e) {
                LOG.error("ERROR : Invalid Basic Information records for Application Form Id : {} ", afCribReportUpdateRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_BASIC_INFORMATION_NOT_FOUND);
            }

            boolean isNewCribReport = afCribReportUpdateRQ.getCribReportID() == null;
            if (isNewCribReport) {
                afCribReport = new AFCribReport();
                afCribReport.setCreatedDate(date);
                afCribReport.setCreatedBy(credentialsDTO.getUserName());
                afCribReport.setStatus(AppsConstants.Status.ACT);
            } else {
                afCribReport = afCribReportDao.getOne(afCribReportUpdateRQ.getCribReportID());
                afCribReport.setModifiedBy(credentialsDTO.getUserName());
                afCribReport.setModifiedDate(date);
                afCribReport.setStatus(afCribReportUpdateRQ.getStatus());
            }
            afCribReport.setIdentificationType(afCribReportUpdateRQ.getIdentificationType());
            afCribReport.setIdentificationNo(afCribReportUpdateRQ.getIdentificationNo());
            afCribReport.setReportContent(afCribReportUpdateRQ.getReportContent());
            afCribReport.setCribStatus(afCribReportUpdateRQ.getCribStatus());
            afCribReport.setRemark(afCribReportUpdateRQ.getRemark());
            if (StringUtils.isNotBlank(afCribReportUpdateRQ.getCribDateStr())) {
                afCribReport.setCribDate(CalendarUtil.getDefaultParsedDateOnly(afCribReportUpdateRQ.getCribDateStr()));
            }
            AFCribReportDTO afCribReportDTO = new AFCribReportDTO();
            afCribReportDTO.setIdentificationNo(afCribReportUpdateRQ.getIdentificationNo());
            afCribReportDTO.setIdentificationType(afCribReportUpdateRQ.getIdentificationType());
            afCribReportDTO.setCribDateStr(afCribReportUpdateRQ.getCribDateStr());

            List<CustomerCribLiabilityDTO> customerCribLiabilityDTOS = customerJdbcDao.getCustomerCribLiabilitiesList(afCribReportDTO);

            for (CustomerCribLiabilityDTO customerCribLiabilityDTO : customerCribLiabilityDTOS) {
                AFFinancialObligation afFinancialObligation = new AFFinancialObligation();
                afFinancialObligation.setIsCribRecord(AppsConstants.YesNo.Y);
                afFinancialObligation.setCustomerCribLiabilityID(customerCribLiabilityDTO.getCustomerCribLiabilityID());
                afFinancialObligation.setCustomerCribResponseID(customerCribLiabilityDTO.getCustomerCribResponseID());
                afFinancialObligation.setIdentificationType(customerCribLiabilityDTO.getIdentificationType());
                afFinancialObligation.setIdentificationNumber(customerCribLiabilityDTO.getIdentificationNumber());
                afFinancialObligation.setFinancialInstitution(customerCribLiabilityDTO.getFinancialInstitution());
                afFinancialObligation.setOriginalAmount(new BigDecimal(customerCribLiabilityDTO.getOriginalAmount().replaceAll(",", "")));
                afFinancialObligation.setPresentOutstanding(new BigDecimal(customerCribLiabilityDTO.getPresentOutstanding().replaceAll(",", "")));
                afFinancialObligation.setArrears(new BigDecimal(customerCribLiabilityDTO.getArrears().replaceAll(",", "")));
                afFinancialObligation.setSignedAs(customerCribLiabilityDTO.getSignedAs());
                afFinancialObligation.setSecurities(customerCribLiabilityDTO.getSecurities());
                afFinancialObligation.setModifiedUserDisplayName(customerCribLiabilityDTO.getModifiedUserDisplayName());
                afFinancialObligation.setStatus(customerCribLiabilityDTO.getStatus());
                afFinancialObligation.setCreatedBy(credentialsDTO.getUserName());
                afFinancialObligation.setCreatedDate(date);
                basicInformation.addAFFinancialObligation(afFinancialObligation);
            }

            basicInformation.addCribReport(afCribReport);
            applicationForm.addBasicInformation(basicInformation);

        } else {
            LOG.error("ERROR : Invalid Application Form Id : {} Not Found ", afCribReportUpdateRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_NOT_FOUND);
        }

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);
        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadCribReports();
        afBasicInformationLoadOptionDTO.loadCribAttachments();
        afBasicInformationLoadOptionDTO.loadBorrowerGuarantor();
        afBasicInformationLoadOptionDTO.loadFinancialObligations();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        LOG.info("END : Save or Update Optional Crib Reports data : {} : {}", afCribReportUpdateRQ, credentialsDTO.getUserID());
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateCribAttachments(AFCribAttachmentUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload Crib Attachments :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();

        ApplicationForm applicationForm = this.applicationFormDao.getOne(uploadRQ.getApplicationFormID());
        BasicInformation basicInformation = applicationForm.getBasicInformationByID(uploadRQ.getBasicInformationID());
        AFCribAttachment afCribAttachment = null;
        boolean isNew = uploadRQ.getCribAttachmentID() == null;
        if (isNew) {
            afCribAttachment = new AFCribAttachment();
            afCribAttachment.setCreatedBy(credentialsDTO.getUserName());
            afCribAttachment.setCreatedDate(date);
            afCribAttachment.setUploadedDivCode(uploadRQ.getUploadedDivCode());
            afCribAttachment.setUploadedUserDisplayName(uploadRQ.getUploadedUserDisplayName());
        } else {
            afCribAttachment = basicInformation.getAFCribAttachmentByID(uploadRQ.getCribAttachmentID());
            afCribAttachment.setModifiedBy(credentialsDTO.getUserName());
            afCribAttachment.setModifiedDate(date);
        }
        if (uploadRQ.getDocStorageDTO() != null) {
            uploadRQ.getDocStorageDTO().setDescription("APPLICATION FORM : " + applicationForm.getAfRefNumber() + " : " + basicInformation.getIdentificationNo() + " crib attachments");
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
            afCribAttachment.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Crib Attachment Document data null : {}", uploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_AF_DOCUMENT_NULL_SUPPORT_DOC);
        }

        afCribAttachment.setRemark(uploadRQ.getRemark());
        afCribAttachment.setCribStatus(uploadRQ.getCribStatus());
        if (StringUtils.isNotBlank(uploadRQ.getCribDateStr())) {
            afCribAttachment.setCribDate(CalendarUtil.getDefaultParsedDateOnly(uploadRQ.getCribDateStr()));
        }
        afCribAttachment.setSupportingDocID(uploadRQ.getSupportingDocID());
        afCribAttachment.setIdentificationNo(uploadRQ.getIdentificationNo());
        afCribAttachment.setApplicationFormID(uploadRQ.getApplicationFormID());
        afCribAttachment.setIdentificationType(uploadRQ.getIdentificationType());
        afCribAttachment.setStatus(uploadRQ.getStatus());

        basicInformation.addCribAttachments(afCribAttachment);
        applicationForm.addBasicInformation(basicInformation);
        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Upload Crib Attachments :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        AFLoadOptionsDTO loadOptionDTO = new AFLoadOptionsDTO();
        loadOptionDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        loadOptionDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO deactivateAFCribAttachment(AFCribAttachmentUploadRQ afCribAttachmentUploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the Application Form Crib Attachments: {} by: {}", afCribAttachmentUploadRQ, credentialsDTO.getUserName());
        ApplicationForm applicationForm = applicationFormDao.getOne(afCribAttachmentUploadRQ.getApplicationFormID());
        BasicInformation basicInformation = applicationForm.getBasicInformationByID(afCribAttachmentUploadRQ.getBasicInformationID());
        AFCribAttachment afCribAttachment = basicInformation.getAFCribAttachmentByID(afCribAttachmentUploadRQ.getCribAttachmentID());

        afCribAttachment.setModifiedBy(credentialsDTO.getUserName());
        afCribAttachment.setModifiedDate(new Date());
        afCribAttachment.setStatus(AppsConstants.Status.INA);

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Deactivate the Application Form Crib Attachment: {} by: {}", afCribAttachmentUploadRQ, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadCribReports();
        afBasicInformationLoadOptionDTO.loadCribAttachments();
        afBasicInformationLoadOptionDTO.loadBorrowerGuarantor();
        afBasicInformationLoadOptionDTO.loadFinancialObligations();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateOtherBankDetails(AFOtherBankDetailsDTO afOtherBankDetailsDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save or Update Other Bank Details : {} by: {}", afOtherBankDetailsDTO, credentialsDTO.getUserName());
        ApplicationForm applicationForm = applicationFormDao.getOne(afOtherBankDetailsDTO.getApplicationFormID());
        BasicInformation basicInformation = applicationForm.getBasicInformationByID(afOtherBankDetailsDTO.getBasicInformationID());
        AFOtherBankDetails otherBankDetails;
        Date date = new Date();
        boolean isNew = afOtherBankDetailsDTO.getOtherBankDetailsID() == null;
        if (isNew) {
            otherBankDetails = new AFOtherBankDetails();
            otherBankDetails.setCreatedBy(credentialsDTO.getUserName());
            otherBankDetails.setCreatedDate(date);
        } else {
            otherBankDetails = basicInformation.getAfOtherBankDetailsByID(afOtherBankDetailsDTO.getOtherBankDetailsID());
            otherBankDetails.setModifiedBy(credentialsDTO.getUserName());
            otherBankDetails.setModifiedDate(new Date());
        }
        otherBankDetails.setStatus(afOtherBankDetailsDTO.getStatus());
        otherBankDetails.setNameOfBank(afOtherBankDetailsDTO.getNameOfBank());
        otherBankDetails.setNameOfBranch(afOtherBankDetailsDTO.getNameOfBranch());
        otherBankDetails.setAccountNo(afOtherBankDetailsDTO.getAccountNo());
        otherBankDetails.setTypeOfAccount(afOtherBankDetailsDTO.getTypeOfAccount());
        otherBankDetails.setApplicationFormID(afOtherBankDetailsDTO.getApplicationFormID());

        basicInformation.addOtherBankDetails(otherBankDetails);

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Save or Update Other Bank Details : {} by: {}", afOtherBankDetailsDTO, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO removeOtherBankDetails(AFOtherBankDetailsDTO afOtherBankDetailsDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Remove Other Bank Details : {} by: {}", afOtherBankDetailsDTO, credentialsDTO.getUserName());
        ApplicationForm applicationForm = applicationFormDao.getOne(afOtherBankDetailsDTO.getApplicationFormID());
        BasicInformation basicInformation = applicationForm.getBasicInformationByID(afOtherBankDetailsDTO.getBasicInformationID());
        AFOtherBankDetails otherBankDetails = basicInformation.getAfOtherBankDetailsByID(afOtherBankDetailsDTO.getOtherBankDetailsID());

        otherBankDetails.setModifiedBy(credentialsDTO.getUserName());
        otherBankDetails.setModifiedDate(new Date());
        otherBankDetails.setStatus(AppsConstants.Status.INA);

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Remove Other Bank Details : {} by: {}", afOtherBankDetailsDTO, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateBorrowerGuarantor(AFBorrowerGuarantorDTO afBorrowerGuarantorDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Save or Update Borrower Guarantor Details : {} by: {}", afBorrowerGuarantorDTO, credentialsDTO.getUserName());
        ApplicationForm applicationForm = applicationFormDao.getOne(afBorrowerGuarantorDTO.getApplicationFormID());
        BasicInformation basicInformation = applicationForm.getBasicInformationByID(afBorrowerGuarantorDTO.getBasicInformationID());

        AFBorrowerGuarantor afBorrowerGuarantor;
        Date date = new Date();
        boolean isNew = afBorrowerGuarantorDTO.getBorrowerGuarantorID() == null;
        if (isNew) {
            afBorrowerGuarantor = new AFBorrowerGuarantor();
            afBorrowerGuarantor.setCreatedBy(credentialsDTO.getUserName());
            afBorrowerGuarantor.setCreatedDate(date);
        } else {
            afBorrowerGuarantor = basicInformation.getAFBorrowerGuarantorByID(afBorrowerGuarantorDTO.getBorrowerGuarantorID());
            afBorrowerGuarantor.setModifiedBy(credentialsDTO.getUserName());
            afBorrowerGuarantor.setModifiedDate(date);
        }
        afBorrowerGuarantor.setStatus(afBorrowerGuarantorDTO.getStatus());
        afBorrowerGuarantor.setBankAndBranch(afBorrowerGuarantorDTO.getBankAndBranch());
        afBorrowerGuarantor.setAmount(afBorrowerGuarantorDTO.getAmount());
        afBorrowerGuarantor.setBorrowerName(afBorrowerGuarantorDTO.getBorrowerName());
        if (StringUtils.isNotBlank(afBorrowerGuarantorDTO.getDateGrantedStr())) {
            afBorrowerGuarantor.setDateGranted(CalendarUtil.getDefaultParsedDateOnly(afBorrowerGuarantorDTO.getDateGrantedStr()));
        }

        basicInformation.addAfBorrowerGuarantor(afBorrowerGuarantor);
        basicInformation.setIsBorrowerOrGuarantor(afBorrowerGuarantorDTO.getIsBorrowerOrGuarantor());
        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Save or Update Borrower Guarantor Details : {} by: {}", afBorrowerGuarantorDTO, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadCribReports();
        afBasicInformationLoadOptionDTO.loadCribAttachments();
        afBasicInformationLoadOptionDTO.loadBorrowerGuarantor();
        afBasicInformationLoadOptionDTO.loadFinancialObligations();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO removeBorrowerGuarantorDetails(AFBorrowerGuarantorDTO afBorrowerGuarantorDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Remove Borrower or Guarantor Details : {} by: {}", afBorrowerGuarantorDTO, credentialsDTO.getUserName());
        ApplicationForm applicationForm = applicationFormDao.getOne(afBorrowerGuarantorDTO.getApplicationFormID());

        BasicInformation basicInformation = applicationForm.getBasicInformationByID(afBorrowerGuarantorDTO.getBasicInformationID());
        basicInformation.setIsBorrowerOrGuarantor(afBorrowerGuarantorDTO.getIsBorrowerOrGuarantor());

        AFBorrowerGuarantor afBorrowerGuarantor = basicInformation.getAFBorrowerGuarantorByID(afBorrowerGuarantorDTO.getBorrowerGuarantorID());
        afBorrowerGuarantor.setModifiedBy(credentialsDTO.getUserName());
        afBorrowerGuarantor.setModifiedDate(new Date());
        afBorrowerGuarantor.setStatus(AppsConstants.Status.INA);

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Remove Borrower or Guarantor Details : {} by: {}", afBorrowerGuarantorDTO, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadCribReports();
        afBasicInformationLoadOptionDTO.loadCribAttachments();
        afBasicInformationLoadOptionDTO.loadBorrowerGuarantor();
        afBasicInformationLoadOptionDTO.loadFinancialObligations();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateRiskRate(AFRiskRateDTO afRiskRateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save or Update Risk Rate : {} by: {}", afRiskRateDTO, credentialsDTO.getUserName());
        ApplicationForm applicationForm = applicationFormDao.getOne(afRiskRateDTO.getApplicationFormID());
        BasicInformation basicInformation = applicationForm.getBasicInformationByID(afRiskRateDTO.getBasicInformationID());
        AFRiskRate afRiskRate;
        Date date = new Date();
        boolean isNew = afRiskRateDTO.getRiskRateID() == null;
        if (isNew) {
            afRiskRate = new AFRiskRate();
            afRiskRate.setCreatedBy(credentialsDTO.getUserName());
            afRiskRate.setCreatedDate(date);
        } else {
            afRiskRate = basicInformation.getAFRiskRatesByID(afRiskRateDTO.getRiskRateID());
            afRiskRate.setModifiedBy(credentialsDTO.getUserName());
            afRiskRate.setModifiedDate(date);
        }
        afRiskRate.setStatus(afRiskRateDTO.getStatus());
        afRiskRate.setRiskGrading(afRiskRateDTO.getRiskGrading());
        afRiskRate.setScoring(afRiskRateDTO.getScoring());
        afRiskRate.setModel(afRiskRateDTO.getModel());
        afRiskRate.setAssetClassification(afRiskRateDTO.getAssetClassification());
        afRiskRate.setInitiatedBranch(afRiskRateDTO.getInitiatedBranch());
        if (afRiskRateDTO.getLastRatedStr() != null) {
            afRiskRate.setLastRated(CalendarUtil.getDefaultParsedDateOnly(afRiskRateDTO.getLastRatedStr()));
        }
        afRiskRate.setRiskConfirmed(afRiskRateDTO.getRiskConfirmed());
        afRiskRate.setApplicationFormID(afRiskRateDTO.getApplicationFormID());

        basicInformation.addRiskRateDetails(afRiskRate);

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Save or Update Risk Rate : {} by: {}", afRiskRateDTO, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadBasicInformation();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadRiskRates();
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);
        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO draftFacilityPaperByApplicationForm(FacilityPaperGenerateRQ facilityPaperGenerateRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Draft Facility Paper By Application Form : {} : {}", facilityPaperGenerateRQ, credentialsDTO.getUserID());

        Date date = new Date();
        ApplicationForm applicationForm = applicationFormDao.findById(facilityPaperGenerateRQ.getApplicationFormID())
                .orElseThrow(() -> new RuntimeException("Application Form not found with id: " + facilityPaperGenerateRQ.getApplicationFormID()));

        Lead lead = null;
        AnalyticsDecisionDTO analyticsDecision = null;

        if (applicationForm != null && applicationForm.getLeadID() != null) {
            lead = leadDao.findById(applicationForm.getLeadID())
                    .orElseThrow(() -> new RuntimeException("Lead not found with id: " + applicationForm.getLeadID()));

            analyticsDecision = leadJdbcDao.getAnalyticsDecisionByLead(lead.getLeadID());
            LOG.info("Lead details for Facility Paper generation - Lead ID: {}, Analytics Decision: {}", lead.getLeadID(), analyticsDecision);
        }

        applicationForm.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.PAPER_CREATED);
        AFStatusHistory afStatusHistory = new AFStatusHistory();
        afStatusHistory.setApplicationFormStatus(DomainConstants.ApplicationFormStatus.PAPER_CREATED);
        afStatusHistory.setAssignUser(facilityPaperGenerateRQ.getAssignUserDisplayName());
        afStatusHistory.setUpdateBy(facilityPaperGenerateRQ.getCurrentAssignUser());
        afStatusHistory.setUpdatedUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        afStatusHistory.setUpdateDate(date);

        AFComment afComment = new AFComment();
        afComment.setCreatedUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        afComment.setUserComment(facilityPaperGenerateRQ.getAfCommentDTO().getComment());
        afComment.setStatus(AppsConstants.Status.ACT);
        afComment.setCreatedBy(credentialsDTO.getUserName());
        afComment.setCreatedDate(date);
        afComment.setCreatedUserID(facilityPaperGenerateRQ.getCurrentAssignUserID());
        afComment.setCreatedUser(facilityPaperGenerateRQ.getCurrentAssignUser());
        afComment.setCreatedUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        afComment.setCreatedUserDivCode(facilityPaperGenerateRQ.getCurrentAssignUserDivCode());
        afComment.setCreatedUserUpmCode(facilityPaperGenerateRQ.getAssignUserUpmGroupCode());
        afComment.setAssignedUserID(facilityPaperGenerateRQ.getCurrentAssignUserID());
        afComment.setAssignedUser(facilityPaperGenerateRQ.getCurrentAssignUser());
        afComment.setAssignedUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        afComment.setAssignedUserDivCode(facilityPaperGenerateRQ.getCurrentAssignUserDivCode());

        afComment.setIsPublic(AppsConstants.YesNo.Y);
        afComment.setIsUsersOnly(AppsConstants.YesNo.N);
        afComment.setIsDivisionOnly(AppsConstants.YesNo.N);
        afComment.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.PAPER_CREATED);

        FacilityPaperBuilder facilityPaperBuilder = new FacilityPaperBuilder(credentialsDTO, facilityPaperGenerateRQ, date);
        facilityPaperBuilder.setApplicationForm(applicationForm);
        facilityPaperBuilder.setFacilityPaperService(facilityPaperService);
        facilityPaperBuilder.setFacilitySecurityDao(facilitySecurityDao);
        facilityPaperBuilder.setWorkFlowTemplateDao(workFlowTemplateDao);
        facilityPaperBuilder.setSupportingDocDao(supportingDocDao);
        facilityPaperBuilder.setCustomerService(customerService);
        facilityPaperBuilder.setCustomerDao(customerDao);

        FacilityPaper facilityPaper = facilityPaperBuilder.initFacilityPaper()
                .buildCustomers()
                .buildFacilities()
                .buildDocuments()
                .buildComment()
                .buildInitialHistory()
                .getFacilityPaper();

        afStatusHistory.setActionMessage("Facility Paper Ref No : " + facilityPaper.getFpRefNumber() + " created");
        applicationForm.addAFStatusHistory(afStatusHistory);

        afComment.setActionMessage("Facility paper Ref No : " + facilityPaper.getFpRefNumber() + " created");
        applicationForm.addAFComment(afComment);

        ApplicationFormModificationContext context = new ApplicationFormModificationContext();
        context.setBranchLoadResponseListDTO(integrationService.getBranchList(credentialsDTO));
        context.setFacilityPaperGenerateRQ(facilityPaperGenerateRQ);
        context.setCredentialsDto(credentialsDTO);
        context.setDate(new Date());
        context.setApplicationForm(applicationForm);

        List<BasicInformation> basicInformations = basicInformationDao.findAllByApplicationForm_ApplicationFormID(facilityPaperGenerateRQ.getApplicationFormID());

        List<AFCustomer> afCustomers = new ArrayList<>();

        for (BasicInformation basicInformation : basicInformations) {

            AFCustomer afCustomer = afCustomerDao.getOne(basicInformation.getAfCustomer().getAfCustomerID());
            afCustomers.add(afCustomer);
        }
        context.setAFCustomer(afCustomers);

        AFStatusHandler afStatusHandler = null;
        afStatusHandler = new AFStatusHandler(applicationForm, credentialsDTO, null, casProperties);
        afStatusHandler.sendEmailNotificationFacilityPaperCreated(context);

        afStatusModifier.execute(context);

        if (isCompLeadWithAA(lead, analyticsDecision)) {

            LOG.info("Facility paper generated for Comp Lead with Approved AA, hence updating FP reference number with channel code");

            String channel = analyticsDecision.getChannel();

            String channelCode = (channel != null && !channel.isEmpty())
                    ? String.valueOf(Character.toUpperCase(channel.charAt(0)))
                    : "";

            String fpRef = facilityPaper.getFpRefNumber();
            if (fpRef != null && fpRef.length() > 2) {
                String formattedFPRef = "FP" + channelCode + fpRef.substring(2);
                facilityPaper.setFpRefNumber(formattedFPRef);
                LOG.info("Formatted Facility Paper Reference Number: {}", formattedFPRef);
            }
        }

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        applicationFormDao.saveAndFlush(applicationForm);

        //save comprehensive lead facilities
        if (isCompLeadWithAA(lead, analyticsDecision)) {
            this.saveCompLeadFacility(facilityPaper,lead, analyticsDecision, credentialsDTO);
        }

        //update facility paper id in environmental risk data by application form id
        environmentalRiskDataDao.updateRiskDataAFId(facilityPaperGenerateRQ.getApplicationFormID(), facilityPaper.getFacilityPaperID());

        //update facility paper id in esg attachment by application form id
        esgDocStorageDao.updateESGStorageAFId(facilityPaperGenerateRQ.getApplicationFormID(), facilityPaper.getFacilityPaperID());

        //update facility paper id in environmental risk opinion data by application form id
        riskOpinionDao.updateRiskOpinion(facilityPaperGenerateRQ.getApplicationFormID(), facilityPaper.getFacilityPaperID());

        //added to save facility paper id in esg answer data table
        List<AnswerData> answerDataList = answerDataDao.findByApplicationForm_ApplicationFormIDAndStatus(facilityPaperGenerateRQ.getApplicationFormID(), AppsConstants.Status.ACT);
        if (answerDataList != null && !answerDataList.isEmpty()) {
            for (AnswerData answerData : answerDataList) {
                answerData.setFacilityPaper(facilityPaper);
                answerDataDao.save(answerData);
            }
        }

        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadFacilities();
        fpLoadOptionDTO.loadDocument();
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        leadService.addLeadDetailsToCrmRequest(facilityPaper.getLeadID());

        LOG.info("END : Draft Facility Paper By Application Form : {} : {}", facilityPaperGenerateRQ, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }


    private boolean isCompLeadWithAA(Lead lead,  AnalyticsDecisionDTO analyticsDecision) {
        return lead != null && lead.getIsCompLead() != null && lead.getIsCompLead().equals(AppsConstants.YesNo.Y.toString()) && analyticsDecision != null
                && analyticsDecision.getDecision() != null && analyticsDecision.getDecisionStatus() != null
                && analyticsDecision.getDecisionStatus().equals("Approved");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void saveCompLeadFacility(FacilityPaper facilityPaper , Lead lead, AnalyticsDecisionDTO analyticsDecision, CredentialsDTO credentialsDTO) throws AppsException {
        try {

            Date date = new Date();
            //T_COMP_FACILITIES
            List<CompLeadFacilityDTO> facilities = leadJdbcDao.getFacilitiesByLeadId(lead.getLeadID());

            int displayOrder = 1;
            for (CompLeadFacilityDTO f : facilities) {

                //credit facility type
                CreditFacilityType creditFacilityType = creditFacilityTypeDao.findById(f.getCreditFacilityTemplateGroupId())
                        .orElseThrow(() -> new RuntimeException("CreditFacilityType not found with id: " + f.getCreditFacilityTemplateGroupId()));

                CreditFacilityTemplate creditFacilityTemplate = creditFacilityTemplateDao.findById(f.getCreditFacilityTemplateId())
                        .orElseThrow(() -> new RuntimeException("CreditFacilityTemplate not found with id: " + f.getCreditFacilityTemplateId()));

                Map<String, String> kv = new LinkedHashMap<>();
                if (hasText(f.getMake())) {
                    kv.put("MAKE", f.getMake());
                }
                if (hasText(f.getModel())) {
                    kv.put("MODEL", f.getModel());
                }

                //T_CFT_CUSTOM_FACILITY_INFO
                List<CustomFacilityInfoDTO> customFacilityInfoDTOList = leadJdbcDao.getCustomFacilityInfoByTemplateAndCode(f.getCreditFacilityTemplateId());
                List<FacilityVitalInfoDataDTO> vitalFacilityInfoDTOList = leadJdbcDao.getVitalFacilityInfoByTemplateAndCode(f.getCreditFacilityTemplateId());
                List<FacilityOtherFacilityInformationDTO> otherFacilityInfoDTOList = leadJdbcDao.getOtherFacilityInfoByTemplateAndCode(f.getCreditFacilityTemplateId());

                Set<FacilityCustomInfoData> customInfoDataList = new HashSet<>();
                Set<FacilityVitalInfoData> vitalInfoDataList = new HashSet<>();
                Set<FacilityOtherFacilityInformation> otherInfoDataList = new HashSet<>();

                //save Facility
                Facility facility = new Facility();

                facility.setFacilityPaper(facilityPaper);
                facility.setFacilityCurrency("LKR");
                facility.setFacilityAmount(f.getLeaseAmount());
                facility.setIsCooperate(AppsConstants.YesNo.N);
                facility.setFacilityType(creditFacilityType.getFacilityTypeName());
                facility.setIsNew(AppsConstants.YesNo.Y);
                facility.setStatus(AppsConstants.Status.ACT);
                facility.setCreatedBy(credentialsDTO.getUserName());
                facility.setCreatedDate(date);
                facility.setPurpose(f.getFacilityDescription());
                facility.setCreditFacilityType(creditFacilityType);
                facility.setCreditFacilityTemplate(creditFacilityTemplate);
                facility.setDisplayOrder(displayOrder);

                for (CustomFacilityInfoDTO compLeadFacilityDTO : customFacilityInfoDTOList) {
                    // Normalize code and fetch the corresponding value from kv map
                    String code = compLeadFacilityDTO.getCustomFacilityInfoCode();
                    String normalizedCode = code != null ? code.trim().toUpperCase(Locale.ROOT) : "";
                    String valueForCode = kv.get(normalizedCode);

                    AppsConstants.YesNo yn = AppsConstants.YesNo.resolver(compLeadFacilityDTO.getMandatory());
                    AppsConstants.Status status = AppsConstants.Status.resolveStatus(compLeadFacilityDTO.getStatus());

                    FacilityCustomInfoData customInfoData = new FacilityCustomInfoData();

                    customInfoData.setFacility(facility);
                    customInfoData.setCftCustomFacilityInfoID(Math.toIntExact(compLeadFacilityDTO.getCustomFacilityInfoId()));
                    customInfoData.setCustomFacilityInfoName(compLeadFacilityDTO.getCustomFacilityInfoName());
                    customInfoData.setCustomFacilityInfoCode(compLeadFacilityDTO.getCustomFacilityInfoCode());
                    customInfoData.setCustomInfoData(valueForCode);
                    customInfoData.setMandatory(yn);
                    customInfoData.setStatus(status);
                    customInfoData.setCreatedBy(credentialsDTO.getUserName());
                    customInfoData.setCreatedDate(date);
                    customInfoData.setDisplayOrder(compLeadFacilityDTO.getDisplayOrder());

                    customInfoDataList.add(customInfoData);
                }
                facility.setFacilityCustomInfoData(customInfoDataList);

                if (vitalFacilityInfoDTOList != null && !vitalFacilityInfoDTOList.isEmpty()) {
                    for (FacilityVitalInfoDataDTO vitalInfoDataDTO : vitalFacilityInfoDTOList) {
                        FacilityVitalInfoData facilityVitalInfoData = new FacilityVitalInfoData();
                        facilityVitalInfoData.setFacility(facility);
                        facilityVitalInfoData.setCftVitalInfoID(vitalInfoDataDTO.getCftVitalInfoID());
                        facilityVitalInfoData.setVitalInfoName(vitalInfoDataDTO.getVitalInfoName());
                        facilityVitalInfoData.setVitalInfoData("");
                        facilityVitalInfoData.setMandatory(vitalInfoDataDTO.getMandatory());
                        facilityVitalInfoData.setStatus(vitalInfoDataDTO.getStatus());
                        facilityVitalInfoData.setDisplayOrder(vitalInfoDataDTO.getDisplayOrder());
                        facilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                        facilityVitalInfoData.setCreatedDate(date);

                        vitalInfoDataList.add(facilityVitalInfoData);
                    }
                    facility.setFacilityVitalInfoData(vitalInfoDataList);
                }

                if (otherFacilityInfoDTOList != null && !otherFacilityInfoDTOList.isEmpty()) {
                    for (FacilityOtherFacilityInformationDTO otherInfoDataDTO : otherFacilityInfoDTOList) {
                        FacilityOtherFacilityInformation otherFacilityInformation = new FacilityOtherFacilityInformation();
                        otherFacilityInformation.setFacility(facility);
                        otherFacilityInformation.setCftOtherFacilityInfoID(otherInfoDataDTO.getCftOtherFacilityInfoID());
                        otherFacilityInformation.setOtherFacilityInfoName(otherInfoDataDTO.getOtherFacilityInfoName());
                        otherFacilityInformation.setOtherFacilityInfoCode(otherInfoDataDTO.getOtherFacilityInfoCode());
                        if (otherInfoDataDTO.getOtherFacilityInfoCode().equals("CFT_OFI22")) {
                            otherFacilityInformation.setOtherInfoData(f.getLeaseAmount().toString());
                        } else {
                            otherFacilityInformation.setOtherInfoData("");
                        }
                        otherFacilityInformation.setOtherFacilityInfoFieldType(otherInfoDataDTO.getOtherFacilityInfoFieldType());
                        otherFacilityInformation.setDefaultValue(otherInfoDataDTO.getDefaultValue());
                        otherFacilityInformation.setMandatory(otherInfoDataDTO.getMandatory());
                        otherFacilityInformation.setStatus(otherInfoDataDTO.getStatus());
                        otherFacilityInformation.setDisplayOrder(otherInfoDataDTO.getDisplayOrder());
                        otherFacilityInformation.setCreatedBy(credentialsDTO.getUserName());
                        otherFacilityInformation.setCreatedDate(date);

                        otherInfoDataList.add(otherFacilityInformation);
                    }
                    facility.setFacilityOtherFacilityInformations(otherInfoDataList);
                }

                facilityDao.saveAndFlush(facility);
                displayOrder = displayOrder + 1;
            }
        } catch (Exception e) {
            LOG.info("ERROR : SaveCompLeadFacility :: ", e);
        }
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO updateApplicationFormStatus(ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Update Status Application Form : {} : {}", applicationFormStatusChangeRQ, credentialsDTO.getUserID());

        ApplicationFormModificationContext context = new ApplicationFormModificationContext();
        context.setBranchLoadResponseListDTO(integrationService.getBranchList(credentialsDTO));
        context.setApplicationFormStatusChangeRQ(applicationFormStatusChangeRQ);
        context.setCredentialsDto(credentialsDTO);
        context.setDate(new Date());

        ApplicationForm applicationForm = applicationFormDao.getOne(applicationFormStatusChangeRQ.getApplicationFormID());

        List<BasicInformation> basicInformations = basicInformationDao.findAllByApplicationForm_ApplicationFormID(applicationFormStatusChangeRQ.getApplicationFormID());

        List<AFCustomer> afCustomers = new ArrayList<>();

        for (BasicInformation basicInformation : basicInformations) {

            AFCustomer afCustomer = afCustomerDao.getOne(basicInformation.getAfCustomer().getAfCustomerID());
            afCustomers.add(afCustomer);
        }

        context.setApplicationForm(applicationForm);
        context.setAFCustomer(afCustomers);

        AFStatusHandler afStatusHandler = null;

        switch (applicationFormStatusChangeRQ.getApplicationFormStatus()) {


            case DRAFT:
                afStatusHandler = new AFDraftStatusHandler(applicationForm, credentialsDTO, applicationFormStatusChangeRQ, casProperties);
                afStatusHandler.updateApplicationForm();
                afStatusHandler.recordStatusHistory();
                afStatusHandler.transitStatus();

                leadService.addLeadDetailsToCrmRequest(applicationForm.getLeadID());
                break;

            case IN_PROGRESS:
                afStatusHandler = new AFInProgressStatusHandler(applicationForm, credentialsDTO, applicationFormStatusChangeRQ, casProperties);
                afStatusHandler.updateApplicationForm();
                afStatusHandler.recordWorkflowRouting(DomainConstants.ApplicationFormRoutingStatus.NEXT);
                afStatusHandler.recordStatusHistory();
                afStatusHandler.addComment();
                afStatusHandler.transitStatus();

                if (applicationFormStatusChangeRQ.getUpdatedByUserDisplayName().length() > 0) {

                    if (applicationFormStatusChangeRQ.getAssignUserDisplayName() == null) {
                        applicationFormStatusChangeRQ.setAssignUserDisplayName("null");
                    }

                    if (!applicationFormStatusChangeRQ.getUpdatedByUserDisplayName().equals(applicationFormStatusChangeRQ.getAssignUserDisplayName())) {

                        if (applicationFormStatusChangeRQ.getForwardType() == DomainConstants.ForwardType.OTHER_SOL_USER_GROUP ) {

                            BranchAuthorityLevelRQ branchAuthorityLevelRQ = new BranchAuthorityLevelRQ();
                            branchAuthorityLevelRQ.setRoleId(this.casProperties.getManagerWorkClass());
                            branchAuthorityLevelRQ.setSolId(context.getApplicationFormStatusChangeRQ().getAssignDepartmentCode());
                            branchAuthorityLevelRQ.setAppCode(this.casProperties.getApplicationCode());

                            context.setUpmUsers50(integrationService.getUserDetailListFormBranchAuthorityLevel(branchAuthorityLevelRQ , context.getCredentialsDto()));

                        }

                        afStatusHandler.sendEmailNotificationInProgress(context);

                    }
                }

                leadService.addLeadDetailsToCrmRequest(applicationForm.getLeadID());
                break;

            case DECLINED:
                afStatusHandler = new AFDeclineStatusHandler(applicationForm, credentialsDTO, applicationFormStatusChangeRQ, casProperties);
                afStatusHandler.updateApplicationForm();
                afStatusHandler.recordStatusHistory();
                afStatusHandler.addComment();
                afStatusHandler.transitStatus();
                afStatusHandler.sendEmailNotificationDeclined(context);

                leadService.addLeadDetailsToCrmRequest(applicationForm.getLeadID());
                break;

            case RETURNED:
                afStatusHandler = new AFReturnStatusHandler(applicationForm, credentialsDTO, applicationFormStatusChangeRQ, casProperties);
                afStatusHandler.updateApplicationForm();
                afStatusHandler.recordWorkflowRouting(DomainConstants.ApplicationFormRoutingStatus.BACK);
                afStatusHandler.recordStatusHistory();
                afStatusHandler.addComment();
                afStatusHandler.transitStatus();

                if (applicationFormStatusChangeRQ.getForwardType() == DomainConstants.ForwardType.OTHER_SOL_USER_GROUP ) {

                    BranchAuthorityLevelRQ branchAuthorityLevelRQ = new BranchAuthorityLevelRQ();
                    branchAuthorityLevelRQ.setRoleId(this.casProperties.getManagerWorkClass());
                    branchAuthorityLevelRQ.setSolId(context.getApplicationFormStatusChangeRQ().getAssignDepartmentCode());
                    branchAuthorityLevelRQ.setAppCode(this.casProperties.getApplicationCode());

                    context.setUpmUsers50(integrationService.getUserDetailListFormBranchAuthorityLevel(branchAuthorityLevelRQ , context.getCredentialsDto()));

                }
                afStatusHandler.sendEmailNotificationReturned(context);

                leadService.addLeadDetailsToCrmRequest(applicationForm.getLeadID());
                break;

            default:
                afStatusHandler = new AFStatusHandler(applicationForm, credentialsDTO, applicationFormStatusChangeRQ, casProperties);
                afStatusHandler.updateApplicationForm();
                afStatusHandler.recordStatusHistory();
                afStatusHandler.transitStatus();

        }

        afStatusModifier.execute(context);

        applicationForm = this.applicationFormDao.saveAndFlush(applicationForm);
        LOG.info("END: Update Application Form status :{} by :{}", applicationFormStatusChangeRQ, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadAllData();

        AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO = new AFBasicInformationLoadOptionDTO();
        afBasicInformationLoadOptionDTO.loadAllData();

        AFFacilityLoadOptionDTO afFacilityLoadOptionDTO = new AFFacilityLoadOptionDTO();
        afFacilityLoadOptionDTO.loadAllData();

        afLoadOptionsDTO.setAfFacilityLoadOptionDTO(afFacilityLoadOptionDTO);
        afLoadOptionsDTO.setAfBasicInformationLoadOptionDTO(afBasicInformationLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<AFStatusHistoryDTO> getAFReturnUsersList(ApplicationFormDTO applicationFormDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Application Form Return Users List : {} by: {}", applicationFormDTO, credentialsDTO.getUserName());

        return applicationFormJdbcDao.getAFReturnUsersList(applicationFormDTO).stream().filter(element -> {
            return !element.getAssignUser().equals(credentialsDTO.getUserName());
        }).distinct().collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO updateAFFacilityDisplayOrderAndStatus(ApplicationFormFacilitiesDTO applicationFormFacilitiesDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Application Form Update Facility display order by : {}", credentialsDTO.getUserID());
        List<AFFacility> facilities = new ArrayList<>();

        int inactiveCount = 0;
        for (AFFacilityDTO facilityDTO : applicationFormFacilitiesDTO.getFacilityDTOS()) {
            LOG.info("START : Application Form Update Facility display order : {} : {}", facilityDTO, credentialsDTO.getUserID());
            Date date = new Date();
            if (facilityDTO.getFacilityID() != null) {
                AFFacility facility = afFacilityDao.getOne(facilityDTO.getFacilityID());
                facility.setModifiedBy(credentialsDTO.getUserName());
                facility.setModifiedDate(date);

                facility.setDisplayOrder(facilityDTO.getDisplayOrder());
                facility.setStatus(facilityDTO.getStatus());

                if (facilityDTO.getStatus() == AppsConstants.Status.INA) {
                    ++inactiveCount;

                    //The Following for Manage Common Securities when removing the Facilities;
                    for (AFFacilitySecurity afFacilitySecurity : facility.getAfFacilitySecurities()) {
                        afFacilitySecurity.setStatus(AppsConstants.Status.INA);
                        if (afFacilitySecurity.getAfSecurity().getIsCommonSecurity() == AppsConstants.YesNo.Y) {
                            AFSecurity afSecurity = afFacilitySecurity.getAfSecurity();
                            if (afSecurity.getAfFacilitySecurities().stream().filter(fFSecurity -> fFSecurity.getStatus() == AppsConstants.Status.ACT).count() == 1) {
                                afSecurity.setIsCommonSecurity(AppsConstants.YesNo.N);
                                LOG.info("INFO : Common Security Label Removed Nic Name : {} : {} : {}", afSecurity.getSecurityCode(), afSecurity.getSecurityAmount(), credentialsDTO.getUserName());
                            }
                        }
                    }
                }
                facilities.add(facility);
            }
            LOG.info("END : Application Form Update Facility display order : {} : {}", facilityDTO, credentialsDTO.getUserID());
        }

//        if (inactiveCount > 0) {
//            ApplicationForm facilityPaper = this.applicationFormDao.getOne(applicationFormFacilitiesDTO.getApplicationFormID());
//            facilityPaper.setTotalDirectExposurePrevious(null);
//            facilityPaper.setTotalDirectExposureNew(null);
//            facilityPaper.setTotalIndirectExposurePrevious(null);
//            facilityPaper.setTotalIndirectExposureNew(null);
//            facilityPaper.setTotalExposurePrevious(null);
//            facilityPaper.setTotalExposureNew(null);
//        }

        afFacilityDao.saveAll(facilities);

        //Audit
        //TODO WEB Audit
        LOG.info("END : Application Form Update Facility display order by : {}", credentialsDTO.getUserID());

        ApplicationForm applicationForm = applicationFormDao.getOne(applicationFormFacilitiesDTO.getApplicationFormID());
        AFFacilityLoadOptionDTO loadOption = new AFFacilityLoadOptionDTO();
        loadOption.loadAllData();
        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.setAfFacilityLoadOptionDTO(loadOption);
        afLoadOptionsDTO.loadFacilities();

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO uploadAFFacilityDocument(AFFacilityDocumentUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload Application Form facility document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();
        ApplicationForm applicationForm = this.applicationFormDao.getOne(uploadRQ.getApplicationFormID());
        AFFacility afFacility = applicationForm.getActiveFacilityByID(uploadRQ.getAfFacilityID());

        AFFacilityDocument afFacilityDocument = null;
        if (uploadRQ.getFacilityDocumentID() == null) {
            afFacilityDocument = new AFFacilityDocument();
            afFacilityDocument.setCreatedBy(credentialsDTO.getUserName());
            afFacilityDocument.setCreatedDate(date);
            afFacility.addAFFacilityDocuments(afFacilityDocument);
        } else {
            afFacilityDocument = afFacility.getAFFacilityDocumentsByID(uploadRQ.getFacilityDocumentID());
            afFacilityDocument.setModifiedBy(credentialsDTO.getUserName());
            afFacilityDocument.setModifiedDate(date);
        }
        if (uploadRQ.getDocStorageDTO() != null) {
            uploadRQ.getDocStorageDTO().setDescription("FACILITY DOCUMENT: ");
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
            afFacilityDocument.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Application Form Facility Document data null:{}", uploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_DOCUMENT_NULL_FACILITY_DOC);
        }

        afFacilityDocument.setRemark(uploadRQ.getRemark());
        afFacilityDocument.setSupportingDoc(this.supportingDocDao.getOne(uploadRQ.getSupportingDocID()));
        afFacilityDocument.setStatus(uploadRQ.getStatus());
        afFacilityDocument.setCftSupportDocID(uploadRQ.getCftSupportingDocID());
        afFacilityDocument.setMandatory(uploadRQ.getMandatory());

        afFacility = afFacilityDao.saveAndFlush(afFacility);
        LOG.info("END: Upload Application Form Facility document :{} by :{}", uploadRQ, credentialsDTO.getUserName());

        AFLoadOptionsDTO loadOptionDTO = new AFLoadOptionsDTO();
        loadOptionDTO.loadFacilities();
        AFFacilityLoadOptionDTO afFacilityLoadOptionDTO = new AFFacilityLoadOptionDTO();
        afFacilityLoadOptionDTO.loadAllData();
        loadOptionDTO.setAfFacilityLoadOptionDTO(afFacilityLoadOptionDTO);
        return new ApplicationFormDTO(applicationForm, loadOptionDTO);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO deactivateAFFacilityDocuments(AFFacilityDocumentDTO afFacilityDocumentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Application Form Facility documents deactivate : {} : {}", afFacilityDocumentDTO, credentialsDTO.getUserID());

        ApplicationForm applicationForm = applicationFormDao.getOne(afFacilityDocumentDTO.getApplicationFormID());

        AFFacility afFacility = applicationForm.getActiveFacilityByID(afFacilityDocumentDTO.getAfFacilityID());


        AFFacilityDocument afFacilityDocument = afFacility.getAFFacilityDocumentsByID(afFacilityDocumentDTO.getAfFacilityDocumentID());
        afFacilityDocument.setStatus(AppsConstants.Status.INA);
        afFacilityDocument = afFacilityDocumentDao.saveAndFlush(afFacilityDocument);

        LOG.info("END : Application Form Facility documents deactivate : {} : {}", afFacilityDocumentDTO, credentialsDTO.getUserID());

        AFFacilityLoadOptionDTO afFacilityLoadOptionDTO = new AFFacilityLoadOptionDTO();
        afFacilityLoadOptionDTO.loadAllData();

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadFacilities();
        afLoadOptionsDTO.setAfFacilityLoadOptionDTO(afFacilityLoadOptionDTO);

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }


  /*  @Transactional(propagation = Propagation.SUPPORTS)
    public Set<ApplicationFormPageRSDTO> getApplicationFormsForTransfer(ApplicationFormTransferRQ applicationFormTransferRQ) {

        LOG.info("START : Get Application Forms for Transfer : {}", applicationFormTransferRQ);
        Set<String> otherUpmAccessLevels = this.getAllUnderAllowApprovedUPMGroupsForLoginUser(applicationFormTransferRQ.getLoginUpmAccessLevel());
        LOG.info("INFO : Get All Lower UPM Groups (Access Levels) : {}", otherUpmAccessLevels);
        applicationFormTransferRQ.setOtherUpmAccessLevels(new ArrayList<>(otherUpmAccessLevels));

        String branchLevelUPMGroupCodes = systemParameterService.getBranchLevelUPMGroupCodeValues();

        LOG.info("INFO : Get Branch Level UPM Group Codes : {}", branchLevelUPMGroupCodes);

        String[] upmGroups = branchLevelUPMGroupCodes.trim().split(",", 5); // If more UPM groups added to the system parameter increase the limit(5) of spitting

        boolean isBranchLevelPaperTransfer = Arrays.stream(upmGroups).anyMatch(applicationFormTransferRQ.getLoginUpmAccessLevel()::contains);

        LOG.info("INFO : Is Branch Level Application Form  : {}  UPM Group Codes : {} ", isBranchLevelPaperTransfer, upmGroups);

        LOG.info("END : Get Application Forms for Transfer Search RQ : {}", applicationFormTransferRQ);
        return applicationFormJdbcDao.getApplicationFormsForTransfer(applicationFormTransferRQ, isBranchLevelPaperTransfer, upmGroups);
    }*/

    @Transactional(propagation = Propagation.SUPPORTS)
    public Set<ApplicationFormPageRSDTO> getApplicationFormsForTransfer(ApplicationFormTransferRQ applicationFormTransferRQ) throws AppsException {

        LOG.info("START : Get Paged Application Form for Transfer : {}", applicationFormTransferRQ);
        Set<ApplicationFormPageRSDTO> responseList = new HashSet<>();
        Set<String> upmAccessLevels = this.getAllUnderAllowApprovedUPMGroupsForLoginUser(applicationFormTransferRQ.getLoginUpmAccessLevel());
        LOG.info("INFO : Get All Lower UPM Groups (Access Levels) : {}", upmAccessLevels);

        if (systemParameterService.isSameLevelTransferEnabled()) {
            upmAccessLevels.add(applicationFormTransferRQ.getLoginUpmAccessLevel());
            LOG.info("INFO : SAME USER UPM LEVEL TRANSFER_ENABLED : Logged in user Upm Level : {} ", applicationFormTransferRQ.getLoginUpmAccessLevel());
        }
        LOG.info("INFO : Searching All UPM Groups (Access Levels) : {}", upmAccessLevels);

        ApplicationForm applicationForm = applicationFormDao.findByAfRefNumber(applicationFormTransferRQ.getAfRefNumber());
        if (applicationForm == null) {
            LOG.warn("WARN : No Application Form Found for {}", applicationFormTransferRQ.getAfRefNumber());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_NOT_FOUND);
        }

        if (applicationForm.getCurrentApplicationFormStatus().equals(DomainConstants.ApplicationFormStatus.DRAFT)
                || applicationForm.getCurrentApplicationFormStatus().equals(DomainConstants.ApplicationFormStatus.DECLINED)
                || applicationForm.getCurrentApplicationFormStatus().equals(DomainConstants.ApplicationFormStatus.PAPER_CREATED)
        ) {
            LOG.warn("WARN : Invalid Application Form status Found {}", applicationForm.getCurrentApplicationFormStatus());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_TRANSFER_INVALID_STATUS);
        }


        if (applicationFormTransferRQ.getLoginUpmAccessLevel().equals(String.valueOf(WorkGroupUtil.getSUPER_USER()))) {
            LOG.info("INFO : Application Form {} Is transferred by {}", applicationFormTransferRQ.getAfRefNumber(), applicationFormTransferRQ.getLoginUpmAccessLevel());
        } else if (applicationForm.getAssignUserUpmGroupCode() != null && upmAccessLevels.stream().noneMatch(applicationForm.getAssignUserUpmGroupCode()::contains)) {
            LOG.warn("WARN : Application Form {} is not allowed to transfer according to UPM Group {}", applicationFormTransferRQ.getAfRefNumber(), applicationFormTransferRQ.getLoginUpmAccessLevel());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_TRANSFER_INVALID_UPM_GROUP);
        }

        String branchLevelUPMGroupCodes = systemParameterService.getBranchLevelUPMGroupCodeValues();
        String[] upmGroups = branchLevelUPMGroupCodes.trim().split(",", 5);
        boolean isBranchLevelPaperTransfer = Arrays.stream(upmGroups).anyMatch(applicationFormTransferRQ.getLoginUpmAccessLevel()::contains);

        if (isBranchLevelPaperTransfer) {
            LOG.info("INFO : Manager level and bellow paper transfer : {}", applicationFormTransferRQ);

            if (applicationForm.getAssignUserDivCode() != null && !applicationFormTransferRQ.getLoggedInUserBranchCode().equals(applicationForm.getAssignUserDivCode())) {
                LOG.warn("WARN : Application Form {} is not allowed to transfer according to Div Code {}", applicationFormTransferRQ.getAfRefNumber(), applicationFormTransferRQ.getLoggedInUserBranchCode());
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_TRANSFER_INVALID_DIV_CODE);
            }
            if (applicationForm.getAssignDepartmentCode() != null && !applicationFormTransferRQ.getLoggedInUserBranchCode().equals(applicationForm.getAssignDepartmentCode())) {
                LOG.warn("WARN : Application Form {} is not allowed to transfer according to Div Code {}", applicationFormTransferRQ.getAfRefNumber(), applicationFormTransferRQ.getLoggedInUserBranchCode());
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_APPLICATION_FORM_TRANSFER_INVALID_DIV_CODE);
            }
        }

        LOG.info("END : Get Paged Application Form for Transfer Search RQ : {}", applicationFormTransferRQ);
       /* FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCustomer();
        fpLoadOptionDTO.loadFacilities();*/
        responseList.add(new ApplicationFormPageRSDTO(applicationForm));
        return responseList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Set<String> getAllUnderAllowApprovedUPMGroupsForLoginUser(String loggedUpmAccessLevel) {
        LOG.info("START : Get All Lower UPM Groups (Access Levels) : {}", loggedUpmAccessLevel);
        return masterDataJdbcDao.getAllUnderAllowApprovedUPMGroupsForLoginUser(loggedUpmAccessLevel);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO saveOrUpdateAFComment(AFCommentDTO afCommentDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Save or Update Application Form User Comment : {} by: {}", afCommentDTO, credentialsDTO.getUserName());

        ApplicationForm applicationForm = applicationFormDao.getOne(afCommentDTO.getApplicationFormID());
        AFComment afComment;
        Date date = new Date();
        boolean isNew = afCommentDTO.getCommentID() == null;
        if (isNew) {
            afComment = new AFComment();
            afComment.setCreatedBy(credentialsDTO.getUserName());
            afComment.setCreatedDate(date);
        } else {
            afComment = applicationForm.getAFCommentByID(afCommentDTO.getCommentID());
            afComment.setModifiedBy(credentialsDTO.getUserName());
            afComment.setModifiedDate(date);
        }
        afComment.setStatus(afCommentDTO.getStatus());
        afComment.setCreatedUserID(afCommentDTO.getCreatedUserID());
        afComment.setCreatedUser(afCommentDTO.getCreatedUser());
        afComment.setCreatedUserDisplayName(afCommentDTO.getCreatedUserDisplayName());
        afComment.setCreatedUserDivCode(afCommentDTO.getCreatedUserDivCode());
        afComment.setCreatedUserUpmCode(afCommentDTO.getCreatedUserUpmCode());

        afComment.setUserComment(afCommentDTO.getComment());
        afComment.setActionMessage(afCommentDTO.getActionMessage());

        afComment.setIsDivisionOnly(afCommentDTO.getIsDivisionOnly());
        afComment.setIsUsersOnly(afCommentDTO.getIsUsersOnly());
        afComment.setIsPublic(afCommentDTO.getIsPublic());
        afComment.setCurrentApplicationFormStatus(afCommentDTO.getCurrentApplicationFormStatus());
        applicationForm.addAFComment(afComment);

        applicationForm = applicationFormDao.saveAndFlush(applicationForm);

        LOG.info("END: Save or Update Application Form User Comment : {} by: {}", afCommentDTO, credentialsDTO.getUserName());

        AFLoadOptionsDTO afLoadOptionsDTO = new AFLoadOptionsDTO();
        afLoadOptionsDTO.loadComments();

        return new ApplicationFormDTO(applicationForm, afLoadOptionsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApplicationFormDashboardCountDTO getApplicationFormDashboardCount(ApplicationFormDashboardRQ searchRQ) {
        Integer dashboardTimePeriodDays = 0;
        LOG.info("START: getApplicationFormDashboardCount: {} ", searchRQ);

        dashboardTimePeriodDays = casProperties.getDashboardTimePeriodDays();
        LOG.info("getDashboardTimePeriodDays: {} ", dashboardTimePeriodDays);

        ApplicationFormDashboardCountDTO dashboardCounts = new ApplicationFormDashboardCountDTO();

        dashboardCounts =  applicationFormJdbcDao.getApplicationFormDashboardCount(searchRQ, dashboardTimePeriodDays);
        dashboardCounts.setDashboardTimePeriodDays(dashboardTimePeriodDays);

        LOG.info("dashboardCounts: {} ", dashboardCounts);

        LOG.info("END: getLeadDashboardCount: {} ", searchRQ);

        return dashboardCounts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<ApplicationFormDashboardDTO> getPagedApplicationFormDashboard(ApplicationFormDashboardRQ applicationFormDashboardRQ) {
        Integer dashboardTimePeriodDays = 0;
        LOG.info("START: getPagedApplicationFormDashboard: {} ", applicationFormDashboardRQ);

        dashboardTimePeriodDays = casProperties.getDashboardTimePeriodDays();
        LOG.info("getDashboardTimePeriodDays: {} ", dashboardTimePeriodDays);
        Page<ApplicationFormDashboardDTO> result = applicationFormJdbcDao.getPagedApplicationFormDashboardDTO(applicationFormDashboardRQ, dashboardTimePeriodDays);

        for (ApplicationFormDashboardDTO row : result.getPageData()) {

            if (row.getId() != 0){
                ApplicationForm applicationForm = applicationFormDao.getOne(row.getId());

                row.setIsESGPaper(applicationForm.getIsESGPaper());
                if (applicationForm.getApplicationFormID() != 0 && applicationForm.getFsType() != null) {
                    row.setFsType(applicationForm.getFsType());
                }
            }
        }

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<ApplicationFormPageRSDTO> getPagedSearchApplicationForm(SearchApplicationFormRQ applicationFormSearchRQ) throws AppsException{
        return applicationFormJdbcDao.getPagedSearchApplicationForm(applicationFormSearchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<AFAnnexureDataDTO> getAnnexureByApplicationFormID(Integer applicationFormID, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: getAnnexureByApplicationFormID - ApplicationFormService | applicationFormID: {} by: {}", applicationFormID, credentialsDTO.getUserName());
        List<AFAnnexureData> afAnnexureDataList = afAnnexureDataDao.findByApplicationFormApplicationFormID(applicationFormID);
        LOG.info("Fetch Annexure : {}", afAnnexureDataList);
        List<AFAnnexureDataDTO> afAnnexureDTO = afAnnexureDataList.stream().map(AFAnnexureDataDTO::new).collect(Collectors.toList());

        LOG.info("END: getAnnexureByApplicationFormID - ApplicationFormService | afAnnexureDTO: {} by: {}", afAnnexureDTO);

        return afAnnexureDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnnexureDTO getAnnexureByAnnexureId(Integer annexureId, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: getAnnexureByAnnexureId - ApplicationFormService | annexureId: {} by: {}", annexureId, credentialsDTO.getUserName());
        Annexure afAnnexure = afAnnexureDao.findById(annexureId).orElseThrow(() -> new AppsException("Annexure with ID " + annexureId + " not found."));

        AnnexureDTO afAnnexureDTO = new AnnexureDTO(afAnnexure);

        LOG.info("END: getAnnexureByAnnexureId - ApplicationFormService | afAnnexureDTO: {} by: {}", afAnnexureDTO);
        return afAnnexureDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnnexureDTO getAnnexureByAnnexureDataId(Integer annexureDataId, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: getAnnexureByAnnexureDataId - ApplicationFormService | annexureDataId: {} by: {}", annexureDataId, credentialsDTO.getUserName());
        AFAnnexureData afAnnexureData = afAnnexureDataDao.findById(annexureDataId).orElseThrow(() -> new AppsException("Annexure with ID " + annexureDataId + " not found."));

        Annexure afAnnexure = afAnnexureDao.findById(afAnnexureData.getAnnexureId()).orElseThrow(() -> new AppsException("Annexure with ID " + afAnnexureData.getAnnexureId() + " not found."));

        AnnexureDTO afAnnexureDTO = new AnnexureDTO(afAnnexure);

        Map<Integer, List<AFAnnexureAnswerDataDTO>> answerDataMap = afAnnexureData.getAfAnnexureQuestionDataList().stream()
                .flatMap(questionData -> questionData.getAfAnnexureAnswerDataList().stream())
                .map(AFAnnexureAnswerDataDTO::new)
                .collect(Collectors.groupingBy(AFAnnexureAnswerDataDTO::getQuestionId));

        for (AnnexureQuestionDTO questionDTO : afAnnexureDTO.getQuestions()) {
            List<AFAnnexureAnswerDataDTO> answerDataList = answerDataMap.getOrDefault(questionDTO.getQuestionId(), new ArrayList<>());
            questionDTO.setUserAnswer(answerDataList);
        }

        LOG.info("END: getAnnexureByAnnexureDataId - ApplicationFormService | afAnnexureDTO: {} ", afAnnexureDTO);

        return afAnnexureDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AFAnnexureDataDTO saveDataToAnnexure(AFAnnexureDataDTO afAnnexureDataDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: saveDataToAnnexure - ApplicationFormService | afAnnexureDataDTO: {} by: {}", afAnnexureDataDTO, credentialsDTO.getUserName());

        AFAnnexureData afAnnexureData = new AFAnnexureData();

        afAnnexureData.setApplicationForm(
                applicationFormDao.findById(afAnnexureDataDTO.getApplicationFormID())
                        .orElseThrow(() -> new AppsException("Application Form with ID " + afAnnexureDataDTO.getApplicationFormID() + " not found.")));

        afAnnexureData.setAnnexureId(afAnnexureDataDTO.getAnnexureId());
        afAnnexureData.setName(afAnnexureDataDTO.getName());
        afAnnexureData.setDescription(afAnnexureDataDTO.getDescription());
        afAnnexureData.setIsMandatory(afAnnexureDataDTO.getIsMandatory());
        afAnnexureData.setStatus(afAnnexureDataDTO.getStatus());
        afAnnexureData.setCreatedDate(new Date());
        afAnnexureData.setCreatedBy(afAnnexureDataDTO.getCreatedBy());
        afAnnexureData.setCurrentAnnexureStatus(afAnnexureDataDTO.getCurrentAnnexureStatus());

        if (afAnnexureDataDTO.getAfAnnexureQuestionDataDTOList() != null ) {
            List<AFAnnexureQuestionData> afAnnexureQuestionDataList = saveOrUpdateAnnexureQuestionData(afAnnexureData, afAnnexureDataDTO.getAfAnnexureQuestionDataDTOList(), credentialsDTO);
            afAnnexureData.setAfAnnexureQuestionDataList(afAnnexureQuestionDataList);
        }

        afAnnexureData = afAnnexureDataDao.saveAndFlush(afAnnexureData);

        AFAnnexureDataDTO savedAnnexureDataDTO = new AFAnnexureDataDTO(afAnnexureData);

        LOG.info("END: saveDataToAnnexure - ApplicationFormService | savedAnnexureDataDTO: {} by: {}", savedAnnexureDataDTO, credentialsDTO.getUserName());

        return savedAnnexureDataDTO;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AFAnnexureDataDTO updateDataToAnnexure(Integer annexureDataId, AFAnnexureDataDTO afAnnexureDataDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: saveDataToAnnexure - ApplicationFormService | afAnnexureDataDTO: {} by: {}", afAnnexureDataDTO, credentialsDTO.getUserName());

        AFAnnexureData afAnnexureData = afAnnexureDataDao.findById(annexureDataId)
                .orElseThrow(() -> new AppsException("Annexure Data with ID " + annexureDataId + " not found."));

        afAnnexureData.setApplicationForm(
                applicationFormDao.findById(afAnnexureDataDTO.getApplicationFormID())
                        .orElseThrow(() -> new AppsException("Application Form with ID " + afAnnexureDataDTO.getApplicationFormID() + " not found.")));

        afAnnexureData.setAnnexureId(afAnnexureDataDTO.getAnnexureId());
        afAnnexureData.setName(afAnnexureDataDTO.getName());
        afAnnexureData.setDescription(afAnnexureDataDTO.getDescription());
        afAnnexureData.setIsMandatory(afAnnexureDataDTO.getIsMandatory());
        afAnnexureData.setStatus(afAnnexureDataDTO.getStatus());
        afAnnexureData.setCreatedDate(new Date());
        afAnnexureData.setCreatedBy(afAnnexureDataDTO.getCreatedBy());
        afAnnexureData.setCurrentAnnexureStatus(afAnnexureDataDTO.getCurrentAnnexureStatus());

        if (afAnnexureDataDTO.getAfAnnexureQuestionDataDTOList() != null ) {
            List<AFAnnexureQuestionData> afAnnexureQuestionDataList = saveOrUpdateAnnexureQuestionData(afAnnexureData, afAnnexureDataDTO.getAfAnnexureQuestionDataDTOList(), credentialsDTO);
            afAnnexureData.setAfAnnexureQuestionDataList(afAnnexureQuestionDataList);
        }

        afAnnexureData = afAnnexureDataDao.saveAndFlush(afAnnexureData);

        AFAnnexureDataDTO savedAnnexureDataDTO = new AFAnnexureDataDTO(afAnnexureData);

        LOG.info("END: saveDataToAnnexure - ApplicationFormService | savedAnnexureDataDTO: {} by: {}", savedAnnexureDataDTO, credentialsDTO.getUserName());

        return savedAnnexureDataDTO;

    }

    private List<AFAnnexureQuestionData> saveOrUpdateAnnexureQuestionData(AFAnnexureData afAnnexureData, List<AFAnnexureQuestionDataDTO> afAnnexureQuestionDataDTOList, CredentialsDTO credentialsDTO) {

        LOG.info("START: saveOrUpdateAnnexureQuestionData - ApplicationFormService | afAnnexureData: {} by: {}", afAnnexureData, credentialsDTO.getUserName());
        List<AFAnnexureQuestionData> afAnnexureQuestionDataList = new ArrayList<>();

        List<AFAnnexureQuestionData> existingQuestions = afAnnexureQuestionDataDao.findByAfAnnexureDataAnnexureDataId(afAnnexureData.getAnnexureDataId());
        LOG.info("Existing Questions: {}", existingQuestions);

        if(!existingQuestions.isEmpty()){
            afAnnexureQuestionDataDao.deleteAll(existingQuestions);
            LOG.info("Deleted existing questions for annexure data ID: {}", afAnnexureData.getAnnexureDataId());
        }
        for (AFAnnexureQuestionDataDTO afAnnexureQuestionDataDTO : afAnnexureQuestionDataDTOList) {
            AFAnnexureQuestionData afAnnexureQuestionData = new AFAnnexureQuestionData();
            afAnnexureQuestionData.setQuestionId(afAnnexureQuestionDataDTO.getQuestionId());
            afAnnexureQuestionData.setAfAnnexureData(afAnnexureData);
            afAnnexureQuestionData.setQuestion(afAnnexureQuestionDataDTO.getQuestion());
            afAnnexureQuestionData.setAnswerType(afAnnexureQuestionDataDTO.getAnswerType());
            afAnnexureQuestionData.setIsMandatory(afAnnexureQuestionDataDTO.getIsMandatory());
            afAnnexureQuestionData.setDisplayOrder(afAnnexureQuestionDataDTO.getDisplayOrder());
            afAnnexureQuestionData.setCreatedBy(afAnnexureQuestionDataDTO.getCreatedBy());
            afAnnexureQuestionData.setCreatedDate(afAnnexureQuestionDataDTO.getCreatedDate());
            afAnnexureQuestionData.setModifiedBy(afAnnexureQuestionDataDTO.getModifiedBy());
            afAnnexureQuestionData.setModifiedDate(afAnnexureQuestionDataDTO.getModifiedDate());

            if(afAnnexureQuestionDataDTO.getAfAnnexureAnswerDataDTOList() != null){
                List<AFAnnexureAnswerData> afAnnexureAnswerDataList = saveOrUpdateAnnexureAnswerData( afAnnexureQuestionData, afAnnexureQuestionDataDTO.getAfAnnexureAnswerDataDTOList(), credentialsDTO);
                afAnnexureQuestionData.setAfAnnexureAnswerDataList(afAnnexureAnswerDataList);
            }

            afAnnexureQuestionDataList.add(afAnnexureQuestionData);
        }

        LOG.info("END: saveOrUpdateAnnexureQuestionData - ApplicationFormService | afAnnexureQuestionDataList: {} by: {}", afAnnexureQuestionDataList, credentialsDTO.getUserName());
        return afAnnexureQuestionDataList;
    }

    private List<AFAnnexureAnswerData> saveOrUpdateAnnexureAnswerData(AFAnnexureQuestionData afAnnexureQuestionData, List<AFAnnexureAnswerDataDTO> afAnnexureAnswerDataDTOList, CredentialsDTO credentialsDTO) {

        LOG.info("START: saveOrUpdateAnnexureAnswerData - ApplicationFormService | afAnnexureQuestionData: {} by: {}", afAnnexureQuestionData, credentialsDTO.getUserName());
        List<AFAnnexureAnswerData> afAnnexureAnswerDataList = new ArrayList<>();

        List<AFAnnexureAnswerData> existingAnswers = afAnnexureAnswerDataDao.findByAfAnnexureQuestionDataQuestionDataId(afAnnexureQuestionData.getQuestionId());

        if(!existingAnswers.isEmpty()){
            afAnnexureAnswerDataDao.deleteAll(existingAnswers);
            LOG.info("Deleted existing answers for question data ID: {}", afAnnexureQuestionData.getQuestionId());
        }

        for (AFAnnexureAnswerDataDTO afAnnexureAnswerDataDTO : afAnnexureAnswerDataDTOList) {
            AFAnnexureAnswerData afAnnexureAnswerData = new AFAnnexureAnswerData();
            afAnnexureAnswerData.setAnswerId(afAnnexureAnswerDataDTO.getAnswerId());
            afAnnexureAnswerData.setAfAnnexureQuestionData(afAnnexureQuestionData);
            afAnnexureAnswerData.setAnswer(afAnnexureAnswerDataDTO.getAnswer());
            afAnnexureAnswerData.setDisplayOrder(afAnnexureAnswerDataDTO.getDisplayOrder());
            afAnnexureAnswerData.setCreatedBy(afAnnexureAnswerDataDTO.getCreatedBy());
            afAnnexureAnswerData.setCreatedDate(afAnnexureAnswerDataDTO.getCreatedDate());
            afAnnexureAnswerData.setModifiedBy(afAnnexureAnswerDataDTO.getModifiedBy());
            afAnnexureAnswerData.setModifiedDate(afAnnexureAnswerDataDTO.getModifiedDate());

            afAnnexureAnswerDataList.add(afAnnexureAnswerData);
        }

        LOG.info("END: saveOrUpdateAnnexureAnswerData - ApplicationFormService | afAnnexureAnswerDataList: {} by: {}", afAnnexureAnswerDataList, credentialsDTO.getUserName());
        return afAnnexureAnswerDataList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<AFAnnexureListDTO> getAnnexureList(CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: getAnnexureList - ApplicationFormService | by: {}", credentialsDTO.getUserName());

        List<Annexure> afAnnexureList = afAnnexureDao.findAll();
        LOG.info("Fetch Annexure List : {}", afAnnexureList);

        List<AFAnnexureListDTO> afAnnexureDTOList = afAnnexureList.stream()
                .map(annexure -> {
                    AFAnnexureListDTO dto = new AFAnnexureListDTO();
                    dto.setAnnexureId(annexure.getAnnexureId());
                    dto.setName(annexure.getName());
                    dto.setIsMandatory(annexure.getIsMandatory().toString());
                    return dto;
                })
                .collect(Collectors.toList());

        LOG.info("END: getAnnexureList - ApplicationFormService | afAnnexureDTOList: {} by: {}", afAnnexureDTOList, credentialsDTO.getUserName());

        return afAnnexureDTOList;
    }

    public List<EnvironmentalRiskDTO> getEnvironmentalRiskTree(CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: getEnvironmentalRiskTree - ApplicationFormService | by: {}", credentialsDTO.getUserName());

        List<EnvironmentalRisk> allRisks = environmentalRiskDao.findAll();

        return allRisks.stream().map(risk -> {
            EnvironmentalRiskDTO dto = new EnvironmentalRiskDTO();
            dto.setRiskCategoryId(risk.getRiskCategoryId());
            dto.setParentId(risk.getParentId());
            dto.setDescription(risk.getDescription());
            dto.setScore(risk.getScore());
            dto.setType(risk.getType());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<EnvironmentalRiskDataDTO> saveAFEnvironmentalRisk(EnvironmentalRiskDataReq environmentalRiskReq, CredentialsDTO credentialsDTO) throws AppsException {

        List<EnvironmentalRiskDataDTO> response = new ArrayList<>();
        Date date = new Date();

        try {
            if (applicationFormDao.findById(environmentalRiskReq.getApplicationFormId()).isPresent()) {
                ApplicationForm applicationForm = applicationFormDao.findById(environmentalRiskReq.getApplicationFormId()).get();

                //clear categories under application form
                if (!environmentalRiskDataDao.findAllByApplicationForm(applicationForm).isEmpty()) {
                    environmentalRiskDataDao.deleteByApplicationForm(applicationForm);
                }

                for (EnvironmentalRiskDataDTO category : environmentalRiskReq.getCategories()) {
                    EnvironmentalRiskData environmentalRisk = new EnvironmentalRiskData();
                    environmentalRisk.setApplicationForm(applicationForm);
                    environmentalRisk.setRiskCategoryId(category.getRiskCategoryId());
                    environmentalRisk.setCategoryParentId(category.getCategoryParentId());
                    environmentalRisk.setDescription(category.getDescription());
                    environmentalRisk.setScore(category.getScore());
                    environmentalRisk.setType(category.getType());
                    environmentalRisk.setCreatedBy(credentialsDTO.getUserName());
                    environmentalRisk.setCreatedDate(date);

                    environmentalRiskDataDao.save(environmentalRisk);

                    List<EnvironmentalRiskData> environmentalRisks = environmentalRiskDataDao.findAllByApplicationForm(applicationForm);
                    if (environmentalRisks != null && !environmentalRisks.isEmpty()) {
                        response = environmentalRisks.stream().map(EnvironmentalRiskDataDTO::new).collect(Collectors.toList());
                    }
                }

                //update esg status in application form
                if (applicationForm.getIsESGPaper().equals(AppsConstants.YesNo.N)) {
                    applicationForm.setIsESGPaper(AppsConstants.YesNo.Y);
                    applicationForm.setIsESGApproved(AppsConstants.YesNo.N);
                    applicationForm.setModifiedDate(date);
                    applicationForm.setModifiedBy(credentialsDTO.getUserName());

                    applicationFormDao.saveAndFlush(applicationForm);
                }
            } else {
                throw new AppsException("Application Form does not exists.");
            }

        }catch (Exception e){
            LOG.info("ERROR: Failed to save risk category", e);
            throw new AppsException("Failed to save risk category.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<EnvironmentalRiskDataDTO> removeAFEnvironmentalRisk(Integer applicationFormID, CredentialsDTO credentialsDTO) throws AppsException{
        List<EnvironmentalRiskDataDTO> response = new ArrayList<>();
        Date date = new Date();
        try {
            if (applicationFormDao.findById(applicationFormID).isPresent()) {
                ApplicationForm applicationForm = applicationFormDao.findById(applicationFormID).get();

                //clear categories under application form
                if (!environmentalRiskDataDao.findAllByApplicationForm(applicationForm).isEmpty()) {
                    environmentalRiskDataDao.deleteByApplicationForm(applicationForm);
                }

                //update esg status in application form
                if (applicationForm.getIsESGPaper().equals(AppsConstants.YesNo.Y)) {
                    applicationForm.setIsESGPaper(AppsConstants.YesNo.N);
                    applicationForm.setModifiedDate(date);
                    applicationForm.setModifiedBy(credentialsDTO.getUserName());
                    String clear = "";
                    applicationForm.setApprovedESGScore(clear);

                    applicationFormDao.saveAndFlush(applicationForm);
                }
            }
        } catch(Exception e){
            LOG.info("Failed to remove risk data.");
            throw new AppsException("Failed to remove risk data.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationFormDTO approveAFEnvironmentalRisk(ApproveRiskScoreDTO approveRiskScoreDTO, CredentialsDTO credentialsDTO) throws AppsException{
        ApplicationFormDTO response = new ApplicationFormDTO();
        Date date = new Date();
        try {
            if (applicationFormDao.findById(approveRiskScoreDTO.getApplicationFormId()).isPresent()) {
                ApplicationForm applicationForm = applicationFormDao.findById(approveRiskScoreDTO.getApplicationFormId()).get();

                if (applicationForm.getIsESGApproved().equals(AppsConstants.YesNo.N)) {
                    if (approveRiskScoreDTO.getScore() != null && !approveRiskScoreDTO.getScore().isEmpty()){
                        applicationForm.setApprovedESGScore(approveRiskScoreDTO.getScore());
                    }
                    applicationForm.setIsESGApproved(AppsConstants.YesNo.Y);
                } else if (applicationForm.getIsESGApproved().equals(AppsConstants.YesNo.Y)) {
                    applicationForm.setIsESGApproved(AppsConstants.YesNo.N);
                    String clear = "";
                    applicationForm.setApprovedESGScore(clear);
                }
                applicationForm.setModifiedDate(date);
                applicationForm.setModifiedBy(credentialsDTO.getUserName());

                applicationForm = applicationFormDao.saveAndFlush(applicationForm);
                response = new ApplicationFormDTO(applicationForm);
            }
        } catch(Exception e){
            LOG.info("Failed to approved risk data.");
            throw new AppsException("Failed to approved risk data.");
        }

        return response;
    }

    public List<RiskOpinionDTO> getAFEnvironmentalRiskOpinion(Integer applicationFormId) throws AppsException {

        List<RiskOpinionDTO> response = new ArrayList<>();

        try {
            ApplicationForm applicationForm = applicationFormDao.findById(applicationFormId)
                    .orElseThrow(() -> new AppsException("Application Form not found."));

            List<RiskOpinion> riskOpinions = riskOpinionDao.findAllByApplicationForm(applicationForm);
            if (riskOpinions != null && !riskOpinions.isEmpty()) {
                response = riskOpinions.stream().map(RiskOpinionDTO :: new).collect(Collectors.toList());
            }

        } catch (Exception e) {
            LOG.info("Failed to fetch risk opinions: ", e);
            throw new AppsException("Failed to fetch risk opinions.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<RiskOpinionDTO> saveAFEnvironmentalRiskOpinion(RiskOpinionDTO riskOpinionDTO, CredentialsDTO credentialsDTO) throws AppsException {

        List<RiskOpinionDTO> response;
        Date date = new Date();
        try {
            RiskOpinion riskOpinion = riskOpinionDao.findById(riskOpinionDTO.getRiskOpinionId()).orElse(new RiskOpinion());

            if (riskOpinion.getRiskOpinionId() != null && riskOpinion.getRiskOpinionId() > 0) {
                riskOpinion.setModifiedBy(credentialsDTO.getUserName());
                riskOpinion.setModifiedDate(date);
            } else {
                ApplicationForm applicationForm = applicationFormDao.findById(riskOpinionDTO.getApplicationFormId())
                        .orElseThrow(() -> new AppsException("Application Form not found."));
                riskOpinion.setApplicationForm(applicationForm);
                riskOpinion.setCreatedBy(credentialsDTO.getUserName());
                riskOpinion.setCreatedDate(date);
            }
            riskOpinion.setOpinion(riskOpinionDTO.getOpinion());
            riskOpinionDao.saveAndFlush(riskOpinion);

            response = getAFEnvironmentalRiskOpinion(riskOpinionDTO.getApplicationFormId());
        } catch (Exception e) {
            LOG.info("Failed to save risk opinion: ", e);
            throw new AppsException("Failed to save risk opinion.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<RiskOpinionDTO> saveAFEnvironmentalRiskOpinionReply(RiskOpinionReplyDTO riskOpinionReplyDTO, CredentialsDTO credentialsDTO) throws AppsException {

        List<RiskOpinionDTO> response;
        Date date = new Date();
        try {
            RiskOpinion riskOpinion = riskOpinionDao.findById(riskOpinionReplyDTO.getRiskOpinionId())
                    .orElseThrow(() -> new AppsException("Risk opinion not found."));

            RiskOpinionReply riskOpinionReply = riskOpinionReplyDao.findById(riskOpinionReplyDTO.getRiskReplyId()).orElse(new RiskOpinionReply());

            if (riskOpinionReply.getRiskReplyId() != null && riskOpinionReply.getRiskReplyId() > 0) {
                riskOpinionReply.setModifiedBy(credentialsDTO.getUserName());
                riskOpinionReply.setModifiedDate(date);
            } else {
                riskOpinionReply.setRiskOpinion(riskOpinion);
                riskOpinionReply.setCreatedBy(credentialsDTO.getUserName());
                riskOpinionReply.setCreatedDate(date);
            }
            riskOpinionReply.setReply(riskOpinionReplyDTO.getReply());
            riskOpinion.setRiskOpinionReply(riskOpinionReply);

            riskOpinionDao.saveAndFlush(riskOpinion);

            response = getAFEnvironmentalRiskOpinion(riskOpinionReplyDTO.getApplicationFormId());

        } catch (Exception e) {
            LOG.info("Failed to save risk opinion reply: ", e);
            throw new AppsException("Failed to save risk reply.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<RiskOpinionDTO> removeAFEnvironmentalRiskOpinion(RiskOpinionDTO riskOpinionDTO) throws AppsException {

        List<RiskOpinionDTO> response;
        try {
            RiskOpinion riskOpinion = riskOpinionDao.findById(riskOpinionDTO.getRiskOpinionId())
                    .orElseThrow(() -> new AppsException("Risk opinion not found."));

            if (riskOpinion.getRiskOpinionReply() == null) {
                riskOpinionDao.delete(riskOpinion);
                response = getAFEnvironmentalRiskOpinion(riskOpinionDTO.getApplicationFormId());
            } else {
                throw new AppsException("There is a reply for this opinion.");
            }
        } catch (Exception e) {
            LOG.info("Failed to remove risk opinion : ", e);
            throw new AppsException("Failed to remove risk.");
        }

        return response;
    }

//    private List<BasicInformation> saveBasicInfo(ApplicationForm  applicationForm, DraftApplicationFormRQ draftApplicationFormRQ, CredentialsDTO credentialsDTO) throws AppsException {
//        Date date = new Date();
//        List<BasicInformation> savedBasicInformationList = new ArrayList<>();
//        for (BasicInformationDTO basicInformationDTO : draftApplicationFormRQ.getBasicInformationDTOList()) {
//            boolean isNewBasicInformation = basicInformationDTO.getBasicInformationID() == null;
//            if (isNewBasicInformation) {
//                LOG.info("INFO : Building Basic Information for the Application Form : {} , Lead ID : {} ", draftApplicationFormRQ.getAfRefNumber(), draftApplicationFormRQ.getLeadID());
//
//                if (draftApplicationFormRQ != null && draftApplicationFormRQ.getLeadID() != null ) {
//                    LOG.info("INFO : Fetching Lead details for Lead ID : {} ", draftApplicationFormRQ.getLeadID());
//
//                    Lead lead = leadDao.findById(draftApplicationFormRQ.getLeadID())
//                            .orElseThrow(() -> new AppsRuntimeException(
//                                    "Lead not found for the given Lead ID : " + draftApplicationFormRQ.getLeadID()));
//
//                    if (lead.getIsCompLead().equals(AppsConstants.YesNo.Y.toString())) {
//                        LOG.info("is comp lead Y : {} ", draftApplicationFormRQ.getLeadID());
//                        List<LeadIdentificationDTO> leadIdentificationDTOList =
//                                leadDao.findIdentificationsByLeadId(draftApplicationFormRQ.getLeadID());
//                        LOG.info("INFO : leadIdentificationDTOList : {} , Identification Details Count : {} ", draftApplicationFormRQ.getLeadID(), leadIdentificationDTOList.size());
//
//                        if (leadIdentificationDTOList != null && !leadIdentificationDTOList.isEmpty()) {
//                            LOG.info("Inside the for loop");
//                            for (LeadIdentificationDTO leadIdentificationDTO : leadIdentificationDTOList) {
//
//                                    LOG.info("INFO : Lead Identification details matched with the Basic Information Identification Type for Lead ID : {} , Identification Type : {} ", draftApplicationFormRQ.getLeadID(), basicInformationDTO.getIdentificationType());
//
//                                    basicInformationDTO.setIdentificationType(DomainConstants.CustomerIdentificationType.NIC);
//                                    basicInformationDTO.setIdentificationNo(leadIdentificationDTO.getIdentificationNumber());
//                                    break;
//
//                            }
//                        }
//                    }
//                }
//
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
//                basicInformation.setApplicationForm(applicationForm);
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
//                basicInformationDao.save(basicInformation);
//                savedBasicInformationList.add(basicInformation);
//                //savedBasicInformationDTOList.add(new BasicInformationDTO(basicInformation));
//            }
//        }
//
//        LOG.info("START : Build Basic Information Application Form : {} : {}", draftApplicationFormRQ, savedBasicInformationList);
//        return savedBasicInformationList;
//
//    }

    private List<BasicInformation> saveBasicInfo(ApplicationForm applicationForm,
                                                 DraftApplicationFormRQ draftApplicationFormRQ,
                                                 CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();
        List<BasicInformation> savedBasicInformationList = new ArrayList<>();

        for (BasicInformationDTO basicInformationDTO : draftApplicationFormRQ.getBasicInformationDTOList()) {

            boolean isNewBasicInformation = basicInformationDTO.getBasicInformationID() == null;

            if (isNewBasicInformation) {

                LOG.info("INFO : Building Basic Information for the Application Form : {} , Lead ID : {} ",
                        draftApplicationFormRQ.getAfRefNumber(), draftApplicationFormRQ.getLeadID());

// =========================================
// HANDLE COMP LEAD IDENTIFICATIONS
// =========================================
                if (draftApplicationFormRQ != null && draftApplicationFormRQ.getLeadID() != null) {

                    LOG.info("INFO : Fetching Lead details for Lead ID : {} ",
                            draftApplicationFormRQ.getLeadID());

                    Lead lead = leadDao.findById(draftApplicationFormRQ.getLeadID())
                            .orElseThrow(() -> new AppsRuntimeException(
                                    "Lead not found for the given Lead ID : "
                                            + draftApplicationFormRQ.getLeadID()));

                    if (AppsConstants.YesNo.Y.toString().equals(lead.getIsCompLead())) {

                        LOG.info("INFO : Lead is a Comp Lead : {} ",
                                draftApplicationFormRQ.getLeadID());

                        List<LeadIdentificationDTO> leadIdentificationDTOList =
                                leadDao.findIdentificationsByLeadId(
                                        draftApplicationFormRQ.getLeadID());

                        if (leadIdentificationDTOList != null
                                && !leadIdentificationDTOList.isEmpty()) {

                            for (LeadIdentificationDTO leadIdentificationDTO
                                    : leadIdentificationDTOList) {

                                BasicInformation basicInformation =
                                        new BasicInformation();

                                basicInformation.setIdentificationNo(
                                        leadIdentificationDTO.getIdentificationNumber());

                                basicInformation.setIdentificationType(
                                        DomainConstants.CustomerIdentificationType
                                                .valueOf(
                                                        leadIdentificationDTO
                                                                .getIdentificationType()));

                                basicInformation.setPrimaryInformation(
                                        AppsConstants.YesNo.Y);
                                basicInformation.setType(
                                        basicInformationDTO.getType());
                                basicInformation.setStatus(
                                        AppsConstants.Status.ACT);
                                basicInformation.setCreatedDate(date);
                                basicInformation.setCreatedBy(
                                        credentialsDTO.getUserName());
                                basicInformation.setApplicationForm(
                                        applicationForm);

// Save immediately
                                basicInformationDao.save(basicInformation);
                                savedBasicInformationList.add(basicInformation);
                            }

// Skip default creation since already created
                            continue;
                        }
                    }
                }

// =========================================
// NORMAL BASIC INFORMATION FLOW
// =========================================

                BasicInformation basicInformation = new BasicInformation();

                basicInformation.setIdentificationNo(
                        basicInformationDTO.getIdentificationNo());

                String identificationType =
                        basicInformationDTO.getIdentificationType().toString();

                if (identificationType.contains("NIC")) {
                    basicInformation.setIdentificationType(
                            DomainConstants.CustomerIdentificationType.NIC);
                } else {
                    basicInformation.setIdentificationType(
                            basicInformationDTO.getIdentificationType());
                }

                basicInformation.setPrimaryInformation(AppsConstants.YesNo.Y);
                basicInformation.setType(basicInformationDTO.getType());
                basicInformation.setStatus(AppsConstants.Status.ACT);
                basicInformation.setCreatedDate(date);
                basicInformation.setCreatedBy(credentialsDTO.getUserName());
                basicInformation.setApplicationForm(applicationForm);

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

                        LOG.info(
                                "INFO : Customer DTO updated : {} : Financial ID : {}",
                                customerDTO,
                                customerDTO.getCustomerFinancialID());

                        customer =
                                customerDao.getOne(customerDTO.getCustomerID());

                    } else {

                        customer = new Customer();
                        LOG.warn(
                                "WARN : Proceed without Finacle Customer : {} ",
                                searchCustomerRQ);
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

                    LOG.error(
                            "ERROR : Application Form Set Finacle Customer : {}",
                            searchCustomerRQ, e);
                }

                basicInformationDao.save(basicInformation);
                savedBasicInformationList.add(basicInformation);
            }
        }

        LOG.info("END : Build Basic Information Application Form : {} : {}",
                draftApplicationFormRQ,
                savedBasicInformationList.size());

        return savedBasicInformationList;
    }
}
