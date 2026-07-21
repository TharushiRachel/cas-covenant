package com.itechro.cas.service.faclititypaper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.applicationform.AFTopicUpcSectionDao;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.dao.applicationform.jdbc.ApplicationFormJdbcDao;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.dao.bcc.FPBccDao;
import com.itechro.cas.dao.bcc.FPBccDocumentDao;
import com.itechro.cas.dao.casmaster.*;
import com.itechro.cas.dao.casmaster.jdbc.MasterDataJdbcDao;
import com.itechro.cas.dao.creditschedule.jdbc.CreditScheduleResponseJdbcDao;
import com.itechro.cas.dao.customer.CustomerCovenantDao;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.customer.jdbc.CustomerJdbcDao;
import com.itechro.cas.dao.esg.AnswerDataDao;
import com.itechro.cas.dao.esg.EnvironmentalRiskDataDao;
import com.itechro.cas.dao.esg.RiskOpinionDao;
import com.itechro.cas.dao.esg.RiskOpinionReplyDao;
import com.itechro.cas.dao.facility.FPCustomerEvaluationDao;
import com.itechro.cas.dao.facility.FacilityDao;
import com.itechro.cas.dao.facility.FacilityFacilitySecurityDao;
import com.itechro.cas.dao.facility.FacilitySecurityDao;
import com.itechro.cas.dao.facility.jdbc.FacilityFacilitySecurityJdbcDao;
import com.itechro.cas.dao.facility.jdbc.FacilityJdbcDao;
import com.itechro.cas.dao.facilitypaper.*;
import com.itechro.cas.dao.facilitypaper.jdbc.AgentFacilityPaperJdbcDao;
import com.itechro.cas.dao.facilitypaper.jdbc.FPaperRefProc;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.dao.facilitypaper.securityDocument.SecurityDocumentDetailDao;
import com.itechro.cas.dao.finacle.ExportTurnOversDao;
import com.itechro.cas.dao.finacle.GuaranteeVolumesDao;
import com.itechro.cas.dao.finacle.ImportTurnOversDao;
import com.itechro.cas.dao.lead.LeadDao;
import com.itechro.cas.dao.lead.jdbc.DigitalApplicationJdbcDao;
import com.itechro.cas.dao.lead.jdbc.LeadJdbcDao;
import com.itechro.cas.dao.master.UpcSectionDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.bcc.FPBcc;
import com.itechro.cas.model.domain.bcc.FPBccDocument;
import com.itechro.cas.model.domain.casmaster.*;
import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import com.itechro.cas.model.domain.customer.*;
import com.itechro.cas.model.domain.esg.AnswerData;
import com.itechro.cas.model.domain.esg.EnvironmentalRiskData;
import com.itechro.cas.model.domain.esg.RiskOpinion;
import com.itechro.cas.model.domain.esg.RiskOpinionReply;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.domain.facilitypaper.facility.*;
import com.itechro.cas.model.domain.storage.DocStorage;
import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import com.itechro.cas.model.dto.applicationform.AFTopicDataDTO;
import com.itechro.cas.model.dto.applicationform.ApplicationFormTopicRQ;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.bcc.FPBccDTO;
import com.itechro.cas.model.dto.casmaster.UpcSectionDTO;
import com.itechro.cas.model.dto.casmaster.UpcTemplateDTO;
import com.itechro.cas.model.dto.casmaster.UserDaDTO;
import com.itechro.cas.model.dto.covenants.ApplicationCovenantReqDTO;
import com.itechro.cas.model.dto.covenants.CusApplicableCovenantDTO;
import com.itechro.cas.model.dto.customer.CASCustomerBankDetailsDTO;
import com.itechro.cas.model.dto.customer.CustomerEvaluationDTO;
import com.itechro.cas.model.dto.customer.request.CustomerEvaluationDeleteRequestDTO;
import com.itechro.cas.model.dto.customer.request.CustomerEvaluationIdRequest;
import com.itechro.cas.model.dto.email.CAEmailDTO;
import com.itechro.cas.model.dto.email.EmailAttachment;
import com.itechro.cas.model.dto.email.EmailSendRequest;
import com.itechro.cas.model.dto.esg.*;
import com.itechro.cas.model.dto.email.InquiryEmailDTO;
import com.itechro.cas.model.dto.facility.*;
import com.itechro.cas.model.dto.facility.facilityreview.FacilityReviewDTO;
import com.itechro.cas.model.dto.facility.facilityreview.FacilityReviewSearchRQ;
import com.itechro.cas.model.dto.facilitypaper.*;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.*;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummaryDTO;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummarySearchRQ;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewerCommentDTO;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewerCommentSearchRQ;
import com.itechro.cas.model.dto.facilitypaper.request.*;
import com.itechro.cas.model.dto.facilitypaper.response.ApplicationCovenantListResponseDTO;
import com.itechro.cas.model.dto.facilitypaper.response.CribDetailsResponse;
import com.itechro.cas.model.dto.facilitypaper.securityDocument.SDCountDTO;
import com.itechro.cas.model.dto.facilitypaper.response.WalletShareDTO;
import com.itechro.cas.model.dto.facilitypaper.response.WalletShareFacilityDTO;
import com.itechro.cas.model.dto.finacle.request.ExportTurnoverGetRQ;
import com.itechro.cas.model.dto.finacle.request.ImportTurnoverGetRQ;
import com.itechro.cas.model.dto.finacle.response.ExportTurnoverRS;
import com.itechro.cas.model.dto.finacle.response.ImportTurnoverRS;
import com.itechro.cas.model.dto.integration.request.UpmDetailLoadRQ;
import com.itechro.cas.model.dto.integration.request.finacle.GroupExposureRequest;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseDTO;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseListDTO;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.dto.lead.DigitalApplicationDTO;
import com.itechro.cas.model.dto.lead.DigitalApplicationReq;
import com.itechro.cas.model.dto.lead.LeadFacilityPaperStatusDetailDTO;
import com.itechro.cas.model.dto.lead.LeadStatusUpdateRQ;
import com.itechro.cas.model.dto.master.BooleanValueDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverRqDTO;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.service.customer.CasCustomerBuilder;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.email.CASEmailService;
import com.itechro.cas.service.facility.FacilityBuilder;
import com.itechro.cas.service.facility.FacilityService;
import com.itechro.cas.service.faclititypaper.command.FPStatusAndWFModifier;
import com.itechro.cas.service.faclititypaper.command.FacilityPaperModificationContext;
import com.itechro.cas.service.faclititypaper.creditcalculator.CalculatorService;
import com.itechro.cas.service.faclititypaper.support.FacilityPaperBuilder;
import com.itechro.cas.service.faclititypaper.support.FacilityPaperUtils;
import com.itechro.cas.service.faclititypaper.support.FacilitySecurityBuilder;
import com.itechro.cas.service.faclititypaper.support.NonFinacleCustomerFacilityPaperBuilder;
import com.itechro.cas.service.finacle.FinacleService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.lead.LeadService;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.service.sme.SmeService;
import com.itechro.cas.service.storage.StorageService;
import com.itechro.cas.util.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
//@Service
public class FacilityPaperService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperService.class);

    private static final String AGENT_LEAD_FACILITY_DEFAULT_WF_TEMPLATE_NAME = "DEFAULT_001";

    private final Object guard = new Object();

    @Autowired
    CASCribDetailDao CASCribDetailDao;

    @Autowired
    FacilitySecurityDao facilitySecurityDao;

    @Autowired
    MasterDataJdbcDao masterDataJdbcDao;

    @Autowired
    CreditFacilityTypeDao creditFacilityTypeDao;

    @Autowired
    CommitteePaperDao committeePaperDao;

    @Autowired
    FPBccDao fpBccDao;

    @Autowired
    LeadDao leadDao;

    @Autowired
    LeadService leadService;
    @Autowired
    FacilityJdbcDao facilityJdbcDao;

    @Autowired
    FPSecuritySummaryTopicDao fpSecuritySummaryTopicDao;

    @Autowired
    FacilityFacilitySecurityDao facilityFacilitySecurityDao;

    @Autowired
    FacilityFacilitySecurityJdbcDao facilityFacilitySecurityJdbcDao;

    @Autowired
    ApplicationFormJdbcDao applicationFormJdbcDao;

    @Autowired
    AFTopicUpcSectionDao afTopicUpcSectionDao;

    @Autowired
    IntegrationService integrationService;

    @Autowired
    CustomerService customerService;

    @Autowired
    FpReviewerCommentDao fpReviewerCommentDao;

    @Autowired
    FacilityPaperUtils facilityPaperUtils;

    @Autowired
    CASCustomerCribReportDao casCustomerCribReportDao;

    @Autowired
    CalculatorUtil calculatorUtil;

    @Autowired
    private FPStatusAndWFModifier fpStatusAndWFModifier;

    @Autowired
    private FacilityPaperDao facilityPaperDao;

    @Autowired
    private CreditFacilityTemplateDao creditFacilityTemplateDao;

    @Autowired
    private FacilityDao facilityDao;

    @Autowired
    private UpcTemplateDao upcTemplateDao;

    @Autowired
    private WorkFlowTemplateDao workFlowTemplateDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private DocStorageDao docStorageDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private SupportingDocDao supportingDocDao;

    //    @Autowired
//    private GlobalSupportingDocDao globalSupportingDocDao;
//Testt
    @Autowired
    private UpcSectionDao upcSectionDao;

    @Autowired
    private CasCustomerDao casCustomerDao;

    @Autowired
    private FPaperRefProc fPaperRefProc;

    @Autowired
    private WebAuditService webAuditService;

    @Autowired
    private WebAuditJDBCDao webAuditJDBCDao;

    @Autowired
    private FacilityPaperJdbcDao facilityPaperJdbcDao;

    @Autowired
    private AgentFacilityPaperJdbcDao agentFacilityPaperJdbcDao;

    @Autowired
    private FPDocumentDao fpDocumentDao;

    @Autowired
    private SubCommitteeDao caCommitteeConfigDao;

    @Autowired
    private CALevelDao caLevelDao;

    @Autowired
    private CreditScheduleResponseJdbcDao creditScheduleResponseJdbcDao;

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private FPCustomerEvaluationDao fpCustomerEvaluationDao;

    @Autowired
    private CustomerJdbcDao customerJdbcDao;

    @Autowired
    private ApplicationCovenantDao applicationCovenantDao;

    @Autowired
    private FPCreditRiskDocumentDao fpCreditRiskDocumentDao;

    @Autowired
    private FPSecurityDocumentDao fpSecurityDocumentDao;

    @Autowired
    private FPSecurityDocumentTagDataDao fpSecurityDocumentTagDataDao;

    @Autowired
    private FPDocumentElementDao fpDocumentElementDao;

    @Autowired
    private FacilityCovenantFacilitiesDao facilityCovenantFacilitiesDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FPCreditRiskCommentDao fpCreditRiskCommentDao;

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private CASEmailService casEmailService;

    @Autowired
    private CAUserDao caUserDao;

    @Value("${apps.cas.application.code}")
    private String appCode;

    @Autowired
    private FPBccDocumentDao fpBccDocumentDao;

    @Autowired
    private CribDetailsDao cribDetailsDao;

    @Autowired
    private CribDetailsHistoryDao cribDetailsHistoryDao;

    @Autowired
    GuaranteeVolumesDao guaranteeVolumesDao;

    @Autowired
    ExportTurnOversDao exportTurnOversDao;

    @Autowired
    ImportTurnOversDao importTurnOversDao;

    @Autowired
    private FinacleService finacleService;

    @Autowired
    private GroupExposureDetailDao groupExposureDetailDao;

    private final FacilityPaperService facilityPaperService;

    @Autowired
    private EnvironmentalRiskDataDao environmentalRiskDataDao;

    @Autowired
    private ApplicationFormDao applicationFormDao;

    @Autowired
    private AnswerDataDao answerDataDao;

    @Autowired
    private RiskOpinionDao riskOpinionDao;

    @Autowired
    private RiskOpinionReplyDao riskOpinionReplyDao;

    @Autowired
    private WalletShareDao walletShareDao;

    @Autowired
    private WalletShareFacilityDao walletShareFacilityDao;

    @Autowired
    private WSFacilitySecurityDao wsFacilitySecurityDao;

    @Autowired
    private CustomerCovenantDao customerCovenantDao;

    @Autowired
    private SecurityDocumentDetailDao securityDocumentDetailDao;

    @Autowired
    private SecurityDocumentService securityDocumentService;

    @Autowired
    private FPUpcSectionDataDao fpUpcSectionDataDao;

    @Autowired
    private SmeService smeService;

    @Autowired
    private CompDeviationsDao compDeviationsDao;

    @Autowired
    private LeadJdbcDao leadJdbcDao;

    @Autowired
    private DigitalApplicationJdbcDao digitalApplicationJdbcDao;

    @Autowired
    private CustomerClassificationDao customerClassificationDao;

    @Autowired
    private FPCustomerClassificationDao fpCustomerClassificationDao;

    @Autowired
    private FPMDReviewCommentDao fpmdReviewCommentDao;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public FacilityPaperService(@Lazy FacilityPaperService facilityPaperService) {

        this.facilityPaperService = facilityPaperService;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public FacilityPaperDTO getFacilityPaperDTOByID(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException, IOException, SAXException {
        LOG.info("START : Load facility paper details by ID : {} ", facilityPaperID);
        FacilityPaper facilityPaper = this.facilityPaperDao.getOne(facilityPaperID);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        //loadOptionDTO.loadUpcSectionData();
        loadOptionDTO.loadCustomer();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCustomerTelephone();
        loadOptionDTO.loadCustomerIdentification();
        loadOptionDTO.loadCustomerBankDetail();
        loadOptionDTO.loadCustomerAddress();
        loadOptionDTO.loadCustomerCribDetail();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadCustomerOtherBankFacility();
        loadOptionDTO.loadCustomerDocument();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCreditRiskComments();
        loadOptionDTO.loadApprovedFacilityDocuments();
        loadOptionDTO.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOptionDTO.getFacilityLoadOptionDTO().loadAllData();
        loadOptionDTO.loadCustomerRatings();
        loadOptionDTO.loadShareHolderDetail();
        loadOptionDTO.loadCribDetails();
        loadOptionDTO.loadWalletShares();
        loadOptionDTO.loadLoadMDReviewComments();

        List<Formula> formulaList = new CalculatorUtil().parseXml(calculatorService.getEnvironment(), AppsConstants.FacilityType.LEASE.getType(), AppsConstants.CalculatorType.NORMAL.getType());
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO, formulaList);

        List<AnswerData> answerDataList = answerDataDao.findByFacilityPaper_FacilityPaperID(facilityPaperID);

        // Map AnswerData entities to AnswerDataDTO objects
        List<AnswerDataDTO> answerDataDTOList = answerDataList.stream()
                .map(AnswerDataDTO::new)
                .collect(Collectors.toList());

        // Add AnswerDataDTO list to the ApplicationFormDTO
        facilityPaperDTO.setAnswerDataDTO(answerDataDTOList);


        //Set customer name with initials for security documentation - (This functionality is only available for CCDU Users)
        if (facilityPaperDTO.getCurrentFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.APPROVED)) {
            if (credentialsDTO.getDivCode().equals(integrationService.getSecurityDocumentSubmitDiv())) {
                for (CASCustomerDTO casCustomerDTO : facilityPaperDTO.getCasCustomerDTOList()) {
                    if (casCustomerDTO.getNameWithInitials() == null && casCustomerDTO.getStatus().equals(AppsConstants.Status.ACT)) {
                        //   if (casCustomerDTO.getType() != null && casCustomerDTO.getType().equals(DomainConstants.BasicInformationType.PERSONAL))   {
                        casCustomerDTO.setNameWithInitials(this.customerJdbcDao.getCustomerNameWithInitials(casCustomerDTO.getCasCustomerName()));
                        //   }
                    }
                }
            }
        }

        CreditRiskCommentFilterDTO creditRiskCommentFilterDTO = facilityPaperDTO.getFpCreditRiskCommentFilterDTO();

        if (creditRiskCommentFilterDTO == null) {
            creditRiskCommentFilterDTO = new CreditRiskCommentFilterDTO();
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if ((!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) && (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode()))) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else if (!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else {
            creditRiskCommentFilterDTO.setLoad(false);
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoadHistory(true);
        } else {
            creditRiskCommentFilterDTO.setLoadHistory(false);
        }
        facilityPaperDTO.setFpCreditRiskCommentFilterDTO(creditRiskCommentFilterDTO);

        //Check security document version 1 data count
        int countOfDocuments = facilityPaperDao.getSecurityDocumentCountByFP(facilityPaperID);
        if (countOfDocuments > 0) {
            facilityPaperDTO.setSecurityDocumentVersion(1);
        } else {
            facilityPaperDTO.setSecurityDocumentVersion(2);
        }

        //Update security document count
        SDCountDTO sdCountDTO = new SDCountDTO();
        int draftedCount = securityDocumentDetailDao.getDocumentCountByPaperAndStatus(facilityPaperID, "DRAFT");
        int submittedCount = securityDocumentDetailDao.getDocumentCountByPaperAndStatus(facilityPaperID, "SUBMIT");
        int returnedCount = securityDocumentDetailDao.getDocumentCountByPaperAndStatus(facilityPaperID, "RETURN");
        int deviationCount = facilityPaperDao.getDeviationCount(facilityPaperID);

        sdCountDTO.setDraftedCount(draftedCount);
        sdCountDTO.setReturnedCount(returnedCount);
        sdCountDTO.setSubmittedCount(submittedCount);

        facilityPaperDTO.setSdCount(sdCountDTO);
        facilityPaperDTO.setDeviationCount(deviationCount);

        //set analytics decision
        if (facilityPaperDTO.getLeadID() != null) {
            AnalyticsDecisionDTO analyticsDecision = leadJdbcDao.getAnalyticsDecisionByLead(facilityPaperDTO.getLeadID());
            if (analyticsDecision != null) {
                facilityPaperDTO.setAnalyticsDecision(analyticsDecision);
                facilityPaperDTO.setIsCompLead(AppsConstants.YesNo.Y);
            }
        }

        LOG.info("END : Load facility paper details by ID : {} ", facilityPaperID);

        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO draftAgentFacilityPaper(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {

        WorkFlowTemplate workFlowTemplate = this.workFlowTemplateDao.findByCodeAndStatusAndApproveStatus(
                AGENT_LEAD_FACILITY_DEFAULT_WF_TEMPLATE_NAME,
                AppsConstants.Status.ACT,
                DomainConstants.MasterDataApproveStatus.APPROVED
        );

        if (workFlowTemplate == null) {
            LOG.info("ERROR : Agent lead facility default workflow template is not defined for {}", facilityPaperDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_AGENT_FACILITY_DEFAULT_WF_NOT_FOUND);
        }

        facilityPaperDTO.setWorkflowTemplateID(workFlowTemplate.getWorkFlowTemplateID());
        return this.draftFacilityPaper(facilityPaperDTO, credentialsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO draftFacilityPaperWithNonFinacleCustomer(DraftFPWithNoneFincaleCustomerRQ draftFPWithNoneFincaleCustomerRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Draft facility paper with Non Finacle Customer: {}, user {}", draftFPWithNoneFincaleCustomerRQ, credentialsDTO.getUserID());

        NonFinacleCustomerFacilityPaperBuilder nonFinacleCustomerFacilityPaperBuilder = new NonFinacleCustomerFacilityPaperBuilder();
        nonFinacleCustomerFacilityPaperBuilder.setDate(new Date());
        nonFinacleCustomerFacilityPaperBuilder.setCredentialsDTO(credentialsDTO);
        nonFinacleCustomerFacilityPaperBuilder.setFacilityPaperDao(facilityPaperDao);
        nonFinacleCustomerFacilityPaperBuilder.setFpRefNumber(this.getFPaperRefCode());
        nonFinacleCustomerFacilityPaperBuilder.setWorkFlowTemplateDao(workFlowTemplateDao);
        nonFinacleCustomerFacilityPaperBuilder.setDraftFPWithNoneFincaleCustomerRQ(draftFPWithNoneFincaleCustomerRQ);
        FacilityPaper facilityPaper = nonFinacleCustomerFacilityPaperBuilder
                .initFacilityPaper()
                .buildCasCustomerDetails()
                .buildHistory()
                .getFacilityPaper();

        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadFacilities();
        FacilityLoadOptionDTO facilityLoadOptionDTO = new FacilityLoadOptionDTO();
        facilityLoadOptionDTO.loadAllData();
        fpLoadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOptionDTO);

        FacilityPaperDTO updateFacilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        LOG.info("END : Facility Paper draft with Non Finacle Customer : {} : {}", draftFPWithNoneFincaleCustomerRQ, credentialsDTO.getUserID());
        return updateFacilityPaperDTO;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO draftFacilityPaper(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper draft : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        FacilityPaperDTO previousFacilityPaperDTO = null;
        Date date = new Date();
        boolean isNewFacilityPaper = facilityPaperDTO.getFacilityPaperID() == null;

        facilityPaper = new FacilityPaper();
        facilityPaper.setCreatedBy(credentialsDTO.getUserName());
        facilityPaper.setCreatedDate(date);
        facilityPaper.setCreatedUserDisplayName(facilityPaperDTO.getCreatedUserDisplayName());
        facilityPaper.setOutstandingDate(date); // Default The Outstanding date is created date
        facilityPaper.setCurrentCycle(0);
        facilityPaper.setFpRefNumber(this.getFPaperRefCode());
        facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
        facilityPaper.setCurrentAssignUser(facilityPaperDTO.getCurrentAssignUser());
        facilityPaper.setCurrentAssignADUserID(facilityPaperDTO.getCurrentAssignUser());
        facilityPaper.setCurrentAssignUserID(facilityPaperDTO.getCurrentAssignUserID());
        facilityPaper.setAssignUserDisplayName(facilityPaperDTO.getAssignUserDisplayName());
        facilityPaper.setCurrentAssignUserDivCode(facilityPaperDTO.getCurrentAssignUserDivCode());
        facilityPaper.setAssignDepartmentCode(null);
        facilityPaper.setAssignUserUpmID(facilityPaperDTO.getAssignUserUpmID());
        facilityPaper.setAssignUserUpmGroupCode(facilityPaperDTO.getAssignUserUpmGroupCode());
        facilityPaper.setIsBccCreated(AppsConstants.YesNo.N);
        facilityPaper.setIsCommittee(facilityPaperDTO.getIsCommittee());


        if (facilityPaperDTO.getUpcTemplateID() != null) {
            UpcTemplate upcTemplate = this.upcTemplateDao.getOne(facilityPaperDTO.getUpcTemplateID());
            if (upcTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
                facilityPaper.setUpcTemplate(this.upcTemplateDao.getOne(facilityPaperDTO.getUpcTemplateID()));
            } else {
                LOG.error("ERROR: Cannot applied not approved template, {}", facilityPaperDTO);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_APPLY_NOT_APPROVED_TEMPLATE);
            }
        }

        if (facilityPaperDTO.getWorkflowTemplateID() != null) {
            WorkFlowTemplate workFlowTemplate = this.workFlowTemplateDao.getOne(facilityPaperDTO.getWorkflowTemplateID());
            if (workFlowTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
                facilityPaper.setWorkFlowTemplate(this.workFlowTemplateDao.getOne(facilityPaperDTO.getWorkflowTemplateID()));
            } else {
                LOG.error("ERROR: Cannot applied not approved template, {}", facilityPaperDTO);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_APPLY_NOT_APPROVED_TEMPLATE);
            }
        }

        for (CASCustomerDTO CASCustomerDTO : facilityPaperDTO.getCasCustomerDTOList()) {
            boolean isNewFpCustomer = CASCustomerDTO.getCasCustomerID() == null;
            CASCustomer CASCustomer = null;
            FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
            loadOptionDTO.loadCustomerCribDetail();

            if (isNewFpCustomer) {
                CASCustomer = new CASCustomer();
                CASCustomer.setCreatedBy(credentialsDTO.getUserName());
                CASCustomer.setCreatedDate(date);
                facilityPaper.addCasCustomer(CASCustomer);
            } else {
                CASCustomer = facilityPaper.getCasCustomerByID(CASCustomerDTO.getCasCustomerID());
                CASCustomer.setModifiedBy(credentialsDTO.getUserName());
                CASCustomer.setModifiedDate(date);
            }
            Customer customer = this.customerDao.getOne(CASCustomerDTO.getCustomerID());

            CASCustomer.setCustomer(customer);
            CASCustomer.setTitle(customer.getTitle());
            CASCustomer.setCasCustomerName(customer.getCustomerName());
            CASCustomer.setDateOfBirth(customer.getDateOfBirth());
            CASCustomer.setEmailAddress(customer.getEmailAddress());
            CASCustomer.setSecondaryEmailAddress(customer.getSecondaryEmailAddress());
            CASCustomer.setCivilStatus(customer.getCivilStatus());
            CASCustomer.setIsPrimary(AppsConstants.YesNo.valueOf(CASCustomerDTO.getIsPrimary()));
            CASCustomer.setDisplayOrder(CASCustomerDTO.getDisplayOrder());
            CASCustomer.setStatus(CASCustomerDTO.getStatus());

            for (CustomerAddress customerAddress : customer.getCustomerAddresses()) {
                if (customerAddress.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerAddress CASCustomerAddress = new CASCustomerAddress();
                    CASCustomerAddress.setAddress1(customerAddress.getAddress1());
                    CASCustomerAddress.setAddress2(customerAddress.getAddress2());
                    CASCustomerAddress.setAddressType(customerAddress.getAddressType());
                    CASCustomerAddress.setCity(customerAddress.getCity());
                    CASCustomerAddress.setStatus(customerAddress.getStatus());
                    CASCustomerAddress.setCreatedDate(date);
                    CASCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomer.addCasCustomerAddress(CASCustomerAddress);
                }
            }

            for (CustomerTelephone customerTelephone : customer.getCustomerTelephones()) {
                if (customerTelephone.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerTelephone CASCustomerTelephone = new CASCustomerTelephone();
                    CASCustomerTelephone.setContactNumber(customerTelephone.getContactNumber());
                    CASCustomerTelephone.setDescription(customerTelephone.getDescription());
                    CASCustomerTelephone.setStatus(customerTelephone.getStatus());
                    CASCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerTelephone.setCreatedDate(date);
                    CASCustomer.addCasCustomerTelephone(CASCustomerTelephone);
                }
            }

            for (CustomerIdentification customerIdentification : customer.getCustomerIdentifications()) {
                if (customerIdentification.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerIdentification CASCustomerIdentification = new CASCustomerIdentification();
                    CASCustomerIdentification.setIdentificationNumber(customerIdentification.getIdentificationNumber());
                    CASCustomerIdentification.setIdentificationType(customerIdentification.getIdentificationType());
                    CASCustomerIdentification.setStatus(customerIdentification.getStatus());
                    CASCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerIdentification.setCreatedDate(date);
                    CASCustomer.addCasCustomerIdentification(CASCustomerIdentification);
                }
            }

            for (CustomerBankDetail customerBankDetail : customer.getCustomerBankDetails()) {
                if (customerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerBankDetail CASCustomerBankDetail = new CASCustomerBankDetail();
                    CASCustomerBankDetail.setBankAccountNumber(customerBankDetail.getBankAccountNumber());
                    CASCustomerBankDetail.setAccountCLSFlag(customerBankDetail.getAccountCLSFlag());
                    CASCustomerBankDetail.setBankAccountType(customerBankDetail.getBankAccountType());
                    CASCustomerBankDetail.setBranchCode(customerBankDetail.getBranchCode());
                    CASCustomerBankDetail.setAccountCLSFlag(customerBankDetail.getAccountCLSFlag());
                    CASCustomerBankDetail.setAccSince(customerBankDetail.getAccSince());
                    CASCustomerBankDetail.setSchmCode(customerBankDetail.getSchmCode());
                    CASCustomerBankDetail.setSchmType(customerBankDetail.getSchmType());
                    CASCustomerBankDetail.setAccountCurrencyCode(customerBankDetail.getAccountCurrencyCode());
                    CASCustomerBankDetail.setAccountStatus(customerBankDetail.getAccountStatus());
                    CASCustomerBankDetail.setStatus(customerBankDetail.getStatus());
                    CASCustomerBankDetail.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerBankDetail.setCreatedDate(date);
                    CASCustomer.addCasCustomerBankDetail(CASCustomerBankDetail);
                }
            }
        }

        for (FacilityDTO facilityDTO : facilityPaperDTO.getFacilityDTOList()) {
            if (facilityDTO.getStatus() == AppsConstants.Status.ACT) {
                LOG.info("START : Facility save update : {} : {}", facilityDTO, credentialsDTO.getUserID());
                FacilityBuilder facilityBuilder = new FacilityBuilder(facilityDTO, credentialsDTO);
                facilityBuilder.setCreditFacilityTemplateDao(creditFacilityTemplateDao);
                facilityBuilder.setSystemParameterService(systemParameterService);
                facilityBuilder.setFacilityPaper(facilityPaper);

                Facility facility = facilityBuilder.loadFacility()
                        .buildBaseFacility()
                        .buildFacilityDocument()
                        .buildAdvanceOfPurpose()
                        .buildInterestRate()
                        .buildVitalInfo()
                        .getFacility();

                facilityPaper.addFacility(facility);
            }
        }


        FPStatusHistory fpStatusHistory = new FPStatusHistory();
        fpStatusHistory.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
//        fpStatusHistory.setRemark("Draft Facility Paper");
        fpStatusHistory.setUpdateBy(facilityPaperDTO.getAssignUserDisplayName());
        fpStatusHistory.setUpdatedUser(credentialsDTO.getUserName());
        fpStatusHistory.setUpdateDate(date);
        fpStatusHistory.setAuthorityLevel(facilityPaper.getCurrentAuthorityLevel());
        fpStatusHistory.setAssignUserID(facilityPaperDTO.getCurrentAssignUserID());
        fpStatusHistory.setAssignUser(facilityPaperDTO.getCurrentAssignUser());
        fpStatusHistory.setAssignUserDisplayName(facilityPaperDTO.getAssignUserDisplayName());
        fpStatusHistory.setAssignUserUpmID(facilityPaperDTO.getCurrentAssignUserID());
        fpStatusHistory.setAssignUserUpmGroupCode(facilityPaperDTO.getAssignUserUpmGroupCode());
        fpStatusHistory.setAssignUserDivCode(facilityPaperDTO.getCurrentAssignUserDivCode());
        fpStatusHistory.setAssignDepartmentCode(null);
        fpStatusHistory.setIsPublic(AppsConstants.YesNo.Y);
        fpStatusHistory.setIsUsersOnly(AppsConstants.YesNo.N);
        fpStatusHistory.setIsDivisionOnly(AppsConstants.YesNo.N);
        fpStatusHistory.setWorkflowOrder(facilityPaper.getCurrentCycle() + "");
        fpStatusHistory.setActionMessage("Draft by " + facilityPaperDTO.getAssignUserDisplayName());
        facilityPaper.addFpStatusHistory(fpStatusHistory);

        facilityPaper.setBranchCode(facilityPaperDTO.getBranchCode());
        facilityPaper.setCreatedUserBranchCode(facilityPaperDTO.getCreatedUserBranchCode());
        facilityPaper.setLeadRefNumber(facilityPaperDTO.getLeadRefNumber());
        facilityPaper.setLeadID(facilityPaperDTO.getLeadID());
        facilityPaper.setLeadType(facilityPaperDTO.getLeadType());
        facilityPaper.setIsCooperate(AppsConstants.YesNo.N);
        facilityPaper.setFacilityPaperNumber(facilityPaperDTO.getFacilityPaperNumber());
        facilityPaper.setBankAccountID(facilityPaperDTO.getBankAccountID());

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadFacilities();
        FacilityLoadOptionDTO facilityLoadOptionDTO = new FacilityLoadOptionDTO();
        facilityLoadOptionDTO.loadAllData();
        fpLoadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOptionDTO);

        FacilityPaperDTO updateFacilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityPaperAudit(updateFacilityPaperDTO, previousFacilityPaperDTO, credentialsDTO, date, isNewFacilityPaper, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Facility Paper draft : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return updateFacilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO saveOrUpdateFacilityPaper(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        FacilityPaperDTO previousFacilityPaperDTO = null;
        Date date = new Date();
        boolean isNewFacilityPaper = facilityPaperDTO.getFacilityPaperID() == null;

        if (isNewFacilityPaper) {
            facilityPaper = new FacilityPaper();
            facilityPaper.setCreatedBy(credentialsDTO.getUserName());
            facilityPaper.setCreatedDate(date);
            facilityPaper.setCreatedUserDisplayName(facilityPaperDTO.getCreatedUserDisplayName());
            facilityPaper.setOutstandingDate(date); // Default The Outstanding date is created date
            facilityPaper.setCurrentCycle(0);
            facilityPaper.setFpRefNumber(this.getFPaperRefCode());
            facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
        } else {
            facilityPaper = facilityPaperDao.getOne(facilityPaperDTO.getFacilityPaperID());
            previousFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);
            facilityPaper.setModifiedBy(credentialsDTO.getUserName());
            facilityPaper.setModifiedDate(date);
        }

//        if (facilityPaperDTO.getUpcTemplateID() != null) {
//            UpcTemplate upcTemplate = this.upcTemplateDao.getOne(facilityPaperDTO.getUpcTemplateID());
//            if (upcTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
//                facilityPaper.setUpcTemplate(this.upcTemplateDao.getOne(facilityPaperDTO.getUpcTemplateID()));
//            } else {
//                LOG.error("ERROR: Cannot applied not approved template, {}", facilityPaperDTO);
//                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_APPLY_NOT_APPROVED_TEMPLATE);
//            }
//        }
//
//        if (facilityPaperDTO.getWorkflowTemplateID() != null) {
//            WorkFlowTemplate workFlowTemplate = this.workFlowTemplateDao.getOne(facilityPaperDTO.getWorkflowTemplateID());
//            if (workFlowTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
//                facilityPaper.setWorkFlowTemplate(this.workFlowTemplateDao.getOne(facilityPaperDTO.getWorkflowTemplateID()));
//            } else {
//                LOG.error("ERROR: Cannot applied not approved template, {}", facilityPaperDTO);
//                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_APPLY_NOT_APPROVED_TEMPLATE);
//            }
//        }

        facilityPaper.setIsCooperate(facilityPaperDTO.getIsCooperate());
        facilityPaper.setExistingFacilitiesROA(facilityPaperDTO.getExistingFacilitiesROA());
        facilityPaper.setProposedFacilitiesROA(facilityPaperDTO.getProposedFacilitiesROA());

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FacilityPaperDTO updateFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);
        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityPaperAudit(updateFacilityPaperDTO, previousFacilityPaperDTO, credentialsDTO, date, isNewFacilityPaper, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Facility Paper update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return updateFacilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO updateFacilityPaperExposure(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper exposure update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        FacilityPaperDTO previousFacilityPaperDTO = null;
        Date date = new Date();

        FacilityPaperBuilder facilityPaperBuilder = new FacilityPaperBuilder(facilityPaperDTO, credentialsDTO);
        facilityPaperBuilder.setFacilityPaperDao(facilityPaperDao);
        facilityPaperBuilder.setFacilityPaperService(this);
        facilityPaper = facilityPaperBuilder.init()
                .updateFacilityPaperExposure()
                .getFacilityPaper();
        previousFacilityPaperDTO = facilityPaperBuilder.getPreviousFacilityPaperDTO();

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FacilityPaperDTO updateFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityPaperAudit(updateFacilityPaperDTO, previousFacilityPaperDTO, credentialsDTO, date, false, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Facility Paper exposure update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return updateFacilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO calculateFacilityPaperExposure(CalculateExposureRQ calculateExposureRQ, CredentialsDTO credentialsDTO) {
        facilityPaperUtils = new FacilityPaperUtils(facilityPaperJdbcDao);
        return new FacilityPaperDTO(facilityPaperUtils.calculateFacilityPaperExposure(facilityPaperDao.getOne(calculateExposureRQ.getFacilityPaperID())));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO saveOrUpdateFacility(FacilityDTO facilityDTO, CredentialsDTO credentialsDTO) throws SAXException, AppsException, IOException {
        Date date = new Date();
        FacilityPaper facilityPaper = facilityPaperDao.findById(facilityDTO.getFacilityPaperID()).get();
        FacilityDTO previousFacilityDTO = null;
        boolean isNewFacility = (facilityDTO.getFacilityID() == null);
        if (!isNewFacility) {
            Facility previousFacility = facilityPaper.getFacilityByID(facilityDTO.getFacilityID());
            previousFacilityDTO = new FacilityDTO(previousFacility);
        }

        LOG.info("START : Facility save update : {} : {}", facilityDTO, credentialsDTO.getUserID());
        FacilityBuilder facilityBuilder = new FacilityBuilder(facilityDTO, credentialsDTO);
        facilityBuilder.setCreditFacilityTemplateDao(creditFacilityTemplateDao);
        facilityBuilder.setSystemParameterService(systemParameterService);
        facilityBuilder.setCalculatorService(calculatorService);
        facilityBuilder.setFacilityPaper(facilityPaper);

        Facility facility = facilityBuilder.loadFacility()
                .buildBaseFacility()
                .buildFacilityDocument()
                .buildAdvanceOfPurpose()
                .buildVitalInfo()
                .buildOtherInfo()
                .buildCreditCalculatorInfo()
                .buildInterestRate()
                .buildRentalInfo()
                .buildCustomInfo()
                .getFacility();

        facilityPaper.addFacility(facility);
        facilityPaperUtils = new FacilityPaperUtils(facilityPaperJdbcDao);
        facilityPaper = facilityPaperUtils.calculateFacilityPaperExposure(facilityPaper);
        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);

        //set facility fus trace
        for (Facility fc : facilityPaper.getFacilitySet()) {

            FusTraceDTO fpUPCTemplateComparisonRQ = new FusTraceDTO();

            fpUPCTemplateComparisonRQ.setMainKey(fc.getFacilityID());
            fpUPCTemplateComparisonRQ.setSubKey(0);
            fpUPCTemplateComparisonRQ.setFlag(AppsConstants.FusTraceFlage.ALL.getFlag());
            fpUPCTemplateComparisonRQ.setStatus("ACT");
            List<UPCTemplateCommentHistoryDTO> fusTraceList = facilityPaperJdbcDao.getFusTraceDataRepository(fpUPCTemplateComparisonRQ, credentialsDTO);

            fc.setFusTraceList(fusTraceList);
        }

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityAudit(new FacilityDTO(facility), previousFacilityDTO, credentialsDTO, date, isNewFacility, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Facility save update : {} : {}", facilityDTO, credentialsDTO.getUserID());

        FPLoadOptionDTO loadOption = new FPLoadOptionDTO();
        loadOption.loadFacilities();
        loadOption.loadCustomerRelatedDetails();
        loadOption.loadDocument();
        loadOption.loadDirectorDetail();
        loadOption.loadShareHolderDetail();
        loadOption.loadComment();
        loadOption.loadReviewerComment();
        loadOption.loadCompanyROA();
        loadOption.loadCreditRiskComments();
        loadOption.loadWalletShares();
        loadOption.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOption.getFacilityLoadOptionDTO().loadAllData();

        List<Formula> formulaList = new CalculatorUtil().parseXml(calculatorService.getEnvironment(), AppsConstants.FacilityType.LEASE.getType(), AppsConstants.CalculatorType.NORMAL.getType());

        return new FacilityPaperDTO(facilityPaper, loadOption, formulaList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO updateFacilityDisplayOrderAndStatus(FacilityPaperFacilityDTO facilityPaperFacilityDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Update Facility display order by : {}", credentialsDTO.getUserID());
        List<Facility> facilities = new ArrayList<>();
        List<Number> parentIds = new ArrayList<>();

        int inactiveCount = 0;
        for (FacilityDTO facilityDTO : facilityPaperFacilityDTO.getFacilityDTOS()) {
            LOG.info("START : Update Facility display order : {} : {}", facilityDTO, credentialsDTO.getUserID());
            Date date = new Date();
            if (facilityDTO.getFacilityID() != null) {
                Facility facility = facilityDao.getOne(facilityDTO.getFacilityID());
                facility.setModifiedBy(credentialsDTO.getUserName());
                facility.setModifiedDate(date);

                facility.setDisplayOrder(facilityDTO.getDisplayOrder());
                facility.setStatus(facilityDTO.getStatus());

                if (facilityDTO.getStatus() == AppsConstants.Status.INA) {
                    ++inactiveCount;

//                  The Following for Manage Common Securities when removing the Facilities;
                    for (FacilityFacilitySecurity facilityFacilitySecurity : facility.getFacilityFacilitySecurities()) {
                        facilityFacilitySecurity.setStatus(AppsConstants.Status.INA);
                        if (facilityFacilitySecurity.getFacilitySecurity().getIsCommonSecurity() == AppsConstants.YesNo.Y) {
                            FacilitySecurity facilitySecurity = facilityFacilitySecurity.getFacilitySecurity();
                            if (facilitySecurity.getFacilitiesFacilitySecurities().stream().filter(fFSecurity -> fFSecurity.getStatus() == AppsConstants.Status.ACT).count() == 1) {
                                facilitySecurity.setIsCommonSecurity(AppsConstants.YesNo.N);
                                LOG.info("INFO : Common Security Label Removed Nic Name : {} : {} : {}", facilitySecurity.getSecurityCode(), facilitySecurity.getSecurityAmount(), credentialsDTO.getUserName());
                            }
                        }
                    }

                    parentIds.add(facilityDTO.getFacilityID());

                    //inactive document by facility
                    securityDocumentService.updateDocumentStatusByFacility(facilityDTO.getFacilityID(), credentialsDTO);
                }
                facilities.add(facility);
            }
            LOG.info("END : Update Facility display order : {} : {}", facilityDTO, credentialsDTO.getUserID());
        }

        for (Number x : parentIds) {

            for (int i = 0; i < facilities.size(); i++) {
                if (Objects.equals(facilities.get(i).getParentFacilityID(), x)) {
                    facilities.get(i).setParentFacilityID(null);
                }
            }

        }

//        facilities.stream().filter(facility -> facility.getStatus() == AppsConstants.Status.ACT);
        if (inactiveCount > 0) {
            facilities = facilities.stream()
                    .sorted(Comparator.comparing(Facility::getCreatedDate)
                            .thenComparing(Facility::getModifiedDate))
                    .collect(Collectors.toList());

            int p = 1;
            for (int i = 0; i < facilities.size(); i++) {
                if (facilities.get(i).getStatus() == AppsConstants.Status.ACT) {
                    facilities.get(i).setDisplayOrder(p);
                    p++;
                }
            }
        }


//        filteredFacilities.stream().filter(facility -> facility.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList()).size()


        if (inactiveCount > 0) {
            FacilityPaper facilityPaper = this.facilityPaperDao.getOne(facilityPaperFacilityDTO.getFacilityPaperID());
            facilityPaper.setTotalDirectExposurePrevious(null);
            facilityPaper.setTotalDirectExposureNew(null);
            facilityPaper.setTotalIndirectExposurePrevious(null);
            facilityPaper.setTotalIndirectExposureNew(null);
            facilityPaper.setTotalExposurePrevious(null);
            facilityPaper.setTotalExposureNew(null);
        }

        facilityDao.saveAll(facilities);

        if (inactiveCount > 0) {
            FacilityPaper facilityAfterDelete = this.facilityPaperDao.getOne(facilityPaperFacilityDTO.getFacilityPaperID());

            CalculateExposureRQ calculateExposureRQ = new CalculateExposureRQ();
            calculateExposureRQ.setFacilityPaperID(facilityPaperFacilityDTO.getFacilityPaperID());
            calculateExposureRQ.setIsCooperate(facilityAfterDelete.getIsCommittee());

            FacilityPaperDTO calculatedFacilityPaperDTO = this.calculateFacilityPaperExposure(calculateExposureRQ, credentialsDTO);

            facilityAfterDelete.setTotalDirectExposurePrevious(calculatedFacilityPaperDTO.getTotalDirectExposurePrevious());
            facilityAfterDelete.setTotalDirectExposureNew(calculatedFacilityPaperDTO.getTotalDirectExposureNew());
            facilityAfterDelete.setTotalIndirectExposurePrevious(calculatedFacilityPaperDTO.getTotalIndirectExposurePrevious());
            facilityAfterDelete.setTotalIndirectExposureNew(calculatedFacilityPaperDTO.getTotalIndirectExposureNew());
            facilityAfterDelete.setTotalExposurePrevious(calculatedFacilityPaperDTO.getTotalExposurePrevious());
            facilityAfterDelete.setTotalExposureNew(calculatedFacilityPaperDTO.getTotalExposureNew());
            facilityAfterDelete.setGroupTotalDirectExposurePrevious(calculatedFacilityPaperDTO.getGroupTotalDirectExposurePrevious());
            facilityAfterDelete.setGroupTotalDirectExposureNew(calculatedFacilityPaperDTO.getGroupTotalDirectExposureNew());
            facilityAfterDelete.setGroupTotalIndirectExposurePrevious(calculatedFacilityPaperDTO.getGroupTotalIndirectExposurePrevious());
            facilityAfterDelete.setGroupTotalIndirectExposureNew(calculatedFacilityPaperDTO.getGroupTotalIndirectExposureNew());
            facilityAfterDelete.setGroupTotalExposurePrevious(calculatedFacilityPaperDTO.getGroupTotalExposurePrevious());
            facilityAfterDelete.setGroupTotalExposureNew(calculatedFacilityPaperDTO.getGroupTotalExposureNew());
            facilityAfterDelete.setTotalDirectExposureExisting(calculatedFacilityPaperDTO.getTotalDirectExposureExisting());
            facilityAfterDelete.setTotalIndirectExposureExisting(calculatedFacilityPaperDTO.getTotalIndirectExposureExisting());
            facilityAfterDelete.setTotalExposureExisting(calculatedFacilityPaperDTO.getTotalExposureExisting());
            facilityAfterDelete.setExistingCashMargin(calculatedFacilityPaperDTO.getExistingCashMargin());
            facilityAfterDelete.setGroupExistingCashMargin(calculatedFacilityPaperDTO.getGroupExistingCashMargin());
            facilityAfterDelete.setGroupTotalDirectExposureExisting(calculatedFacilityPaperDTO.getGroupTotalDirectExposureExisting());
            facilityAfterDelete.setGroupTotalIndirectExposureExisting(calculatedFacilityPaperDTO.getGroupTotalIndirectExposureExisting());
            facilityAfterDelete.setGroupTotalExposureExisting(calculatedFacilityPaperDTO.getGroupTotalExposureExisting());
            facilityAfterDelete.setNetTotalExposureNew(calculatedFacilityPaperDTO.getNetTotalExposureNew());
            facilityAfterDelete.setNetTotalExposurePrevious(calculatedFacilityPaperDTO.getNetTotalExposurePrevious());
            facilityAfterDelete.setNetTotalExposureExisting(calculatedFacilityPaperDTO.getNetTotalExposureExisting());
            facilityAfterDelete.setGroupNetTotalExposureNew(calculatedFacilityPaperDTO.getGroupNetTotalExposureNew());
            facilityAfterDelete.setGroupNetTotalExposurePrevious(calculatedFacilityPaperDTO.getGroupNetTotalExposurePrevious());
            facilityAfterDelete.setGroupNetTotalExposureExisting(calculatedFacilityPaperDTO.getGroupNetTotalExposureExisting());
            facilityAfterDelete.setOutstandingCashMargin(calculatedFacilityPaperDTO.getOutstandingCashMargin());
            facilityAfterDelete.setProposedCashMargin(calculatedFacilityPaperDTO.getProposedCashMargin());
            facilityAfterDelete.setGroupOutstandingCashMargin(calculatedFacilityPaperDTO.getGroupOutstandingCashMargin());
            facilityAfterDelete.setGroupProposedCashMargin(calculatedFacilityPaperDTO.getGroupProposedCashMargin());

            facilityPaperDao.saveAndFlush(facilityAfterDelete);
        }

        //Audit
        //TODO WEB Audit

        LOG.info("END : Update Facility display order by : {}", credentialsDTO.getUserID());

        FacilityPaper facilityPaper = facilityPaperDao.getOne(facilityPaperFacilityDTO.getFacilityPaperID());
        FPLoadOptionDTO loadOption = new FPLoadOptionDTO();
        loadOption.loadFacilities();
        loadOption.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOption.getFacilityLoadOptionDTO().loadAllData();

        return new FacilityPaperDTO(facilityPaper, loadOption);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditDirectorDetails(DirectorDetailUpdateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update director details: {} : {}", updateDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(updateDTO.getFacilityPaperID());

        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        for (FPDirectorDetailDTO directorDetailDTO : updateDTO.getFpDirectorDetailDTOList()) {
            boolean isNewDirectorDetail = directorDetailDTO.getFpDirectorDetailID() == null;
            FPDirectorDetailDTO previousDirectorDetailDTO = null;
            FPDirectorDetailDTO updateDirectorDetailDTO = null;
            FPDirectorDetail fpDirectorDetail = null;
            if (isNewDirectorDetail) {
                fpDirectorDetail = new FPDirectorDetail();
                fpDirectorDetail.setCreatedBy(credentialsDTO.getUserName());
                fpDirectorDetail.setCreatedDate(date);
                facilityPaper.addFpDirectorDetail(fpDirectorDetail);
            } else {
                fpDirectorDetail = facilityPaper.getFPDirectorDetailByID(directorDetailDTO.getFpDirectorDetailID());
                previousDirectorDetailDTO = new FPDirectorDetailDTO(fpDirectorDetail);
                fpDirectorDetail.setModifiedBy(credentialsDTO.getUserName());
                fpDirectorDetail.setModifiedDate(date);
            }
            fpDirectorDetail.setAddress(directorDetailDTO.getAddress());
            fpDirectorDetail.setCivilStatus(directorDetailDTO.getCivilStatus());
            fpDirectorDetail.setFullName(directorDetailDTO.getFullName());
            fpDirectorDetail.setDirectorName(directorDetailDTO.getDirectorName());
            fpDirectorDetail.setNic(directorDetailDTO.getNic());
            fpDirectorDetail.setShareHolding(directorDetailDTO.getShareHolding());
            fpDirectorDetail.setStatus(directorDetailDTO.getStatus());
            if (StringUtils.isNotBlank(directorDetailDTO.getDateOfBirthStr())) {
                fpDirectorDetail.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(directorDetailDTO.getDateOfBirthStr()));
            }
            updateDirectorDetailDTO = new FPDirectorDetailDTO(fpDirectorDetail);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructFPDirectorDetailAudit(updateDirectorDetailDTO, previousDirectorDetailDTO, credentialsDTO, date, isNewDirectorDetail, webAuditJDBCDao);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);
        }

        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadCreditRiskComments();
        // loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadShareHolderDetail();
        loadOptionDTO.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOptionDTO.getFacilityLoadOptionDTO().loadAllData();

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : Facility Paper update director details: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditCompanyRao(CompanyRoaUpdateDTO companyRoaUpdateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update company roa: {} : {}", companyRoaUpdateDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(companyRoaUpdateDTO.getFacilityPaperID());
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        for (FPCompanyRoaDTO companyRoaDTO : companyRoaUpdateDTO.getFpCompanyRoaDTOList()) {
            boolean isNewCompanyRoa = companyRoaDTO.getFpCompanyRoaID() == null;
            FPCompanyRoa fpCompanyRoa = null;
            FPCompanyRoaDTO updateFPCompanyRoaDTO = null;
            FPCompanyRoaDTO previousFPCompanyRoaDTO = null;

            if (isNewCompanyRoa) {
                fpCompanyRoa = new FPCompanyRoa();
                fpCompanyRoa.setCreatedBy(credentialsDTO.getUserName());
                fpCompanyRoa.setCreatedDate(date);
                facilityPaper.addFpCompanyRoa(fpCompanyRoa);
            } else {
                fpCompanyRoa = facilityPaper.getFpCompanyRoaByID(companyRoaDTO.getFpCompanyRoaID());
                previousFPCompanyRoaDTO = new FPCompanyRoaDTO(fpCompanyRoa);
                fpCompanyRoa.setModifiedBy(credentialsDTO.getUserName());
                fpCompanyRoa.setModifiedDate(date);
            }
            fpCompanyRoa.setDescription(companyRoaDTO.getDescription());
            fpCompanyRoa.setComment(companyRoaDTO.getComment());
            fpCompanyRoa.setStatus(companyRoaDTO.getStatus());
            updateFPCompanyRoaDTO = new FPCompanyRoaDTO(fpCompanyRoa);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructFPCompanyRoaContentAudit(updateFPCompanyRoaDTO, previousFPCompanyRoaDTO, credentialsDTO, date, isNewCompanyRoa, webAuditJDBCDao);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);
        }

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCompanyROA();
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        LOG.info("END : Facility Paper update company roa: {} : {}", companyRoaUpdateDTO, credentialsDTO.getUserID());

        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditComment(FPCommentDTO fpCommentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper Update Comment: {} : {}", fpCommentDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = facilityPaperDao.getOne(fpCommentDTO.getFacilityPaperID());
        Date date = new Date();
        FPComment fpComment;

        boolean isNewComment = fpCommentDTO.getFpCommentID() == null;
        if (isNewComment) {
            fpComment = new FPComment();
            fpComment.setCreatedDate(date);
            fpComment.setCreatedBy(credentialsDTO.getUserName());
            fpComment.setStatus(AppsConstants.Status.ACT);
        } else {
            fpComment = facilityPaper.getFpCommentByID(fpCommentDTO.getFpCommentID());
            fpComment.setModifiedBy(credentialsDTO.getUserName());
            fpComment.setModifiedDate(date);
            fpComment.setStatus(fpCommentDTO.getStatus());
        }
        fpComment.setComment(fpCommentDTO.getComment());
        fpComment.setCreatedUserID(fpCommentDTO.getCreatedUserID());
        fpComment.setCreatedUser(fpCommentDTO.getCreatedUser());
        fpComment.setCreatedUserDisplayName(fpCommentDTO.getCreatedUserDisplayName());
        fpComment.setCreatedUserDivCode(fpCommentDTO.getCreatedUserDivCode());
        fpComment.setCreatedUserUpmCode(fpCommentDTO.getCreatedUserUpmCode());
        fpComment.setAssignedUserID(fpCommentDTO.getAssignedUserID());
        fpComment.setAssignedUser(fpCommentDTO.getAssignedUser());
        fpComment.setAssignedUserDisplayName(fpCommentDTO.getAssignedUserDisplayName());
        fpComment.setAssignedUserDivCode(fpCommentDTO.getAssignedUserDivCode());
        fpComment.setActionMessage(fpCommentDTO.getActionMessage());
        fpComment.setIsPublic(fpCommentDTO.getIsPublic());
        fpComment.setIsUsersOnly(fpCommentDTO.getIsUsersOnly());
        fpComment.setIsDivisionOnly(fpCommentDTO.getIsDivisionOnly());
        fpComment.setCurrentFacilityPaperStatus(fpCommentDTO.getCurrentFacilityPaperStatus());

        facilityPaper.addFpComment(fpComment);

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadComment();

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        LOG.info("END : Facility Paper Update Comment: {} : {}", fpCommentDTO, credentialsDTO.getUserID());

        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditCreditRiskComment(CreditRiskCommentUpdateDTO riskCommentUpdateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update credit risk comment for Facility Paper ID: {}, by : {}", riskCommentUpdateDTO.getFacilityPaperID(), credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(riskCommentUpdateDTO.getFacilityPaperID());

        FPCreditRiskComment notLockedRiskComment = facilityPaper.getNotLockedRiskComment();
        if (notLockedRiskComment != null) {
            notLockedRiskComment.setIsLocked(AppsConstants.YesNo.Y);
            facilityPaper.addFPCreditRiskComment(notLockedRiskComment);
        }

        FPCreditRiskComment validRiskComment = facilityPaper.getValidRiskComment();

        for (FPCreditRiskCommentDTO riskCommentDTO : riskCommentUpdateDTO.getFpCreditRiskCommentDTOS()) {

            boolean isNewComment = riskCommentDTO.getFpCreditRiskCommentID() == null;
            FPCreditRiskComment creditRiskComment = null;

            if (isNewComment) {
                creditRiskComment = new FPCreditRiskComment();
                creditRiskComment.setCreatedBy(credentialsDTO.getUserName());
                creditRiskComment.setCreatedDate(date);
                creditRiskComment.setCreatedUserName(riskCommentUpdateDTO.getUserName());
                creditRiskComment.setIsLocked(AppsConstants.YesNo.N);
                creditRiskComment.setIsValidComment(AppsConstants.YesNo.Y);

                if (validRiskComment != null) {
                    validRiskComment.setIsValidComment(AppsConstants.YesNo.N); // this will invalidate the previous comment
                    facilityPaper.addFPCreditRiskComment(validRiskComment);
                }

            } else {
                creditRiskComment = facilityPaper.getFPCreditRiskCommentByID(riskCommentDTO.getFpCreditRiskCommentID());
                creditRiskComment.setModifiedBy(credentialsDTO.getUserName());
                creditRiskComment.setModifiedDate(date);
                creditRiskComment.setIsLocked(AppsConstants.YesNo.N);
                creditRiskComment.setIsValidComment(AppsConstants.YesNo.Y);
                creditRiskComment.setModifiedUserName(riskCommentUpdateDTO.getUserName());
            }
            facilityPaper.addFPCreditRiskComment(creditRiskComment);
            creditRiskComment.setUpmID(riskCommentUpdateDTO.getUpmID());
            creditRiskComment.setUPMPrivilegeCode(riskCommentDTO.getUPMPrivilegeCode());
            creditRiskComment.setCreditRiskComment(riskCommentDTO.getCreditRiskComment());
            creditRiskComment.setStatus(riskCommentDTO.getStatus());

            //audit
            //TODO Audit
        }

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCreditRiskComments();

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        CreditRiskCommentFilterDTO creditRiskCommentFilterDTO = facilityPaperDTO.getFpCreditRiskCommentFilterDTO();

        if (creditRiskCommentFilterDTO == null) {
            creditRiskCommentFilterDTO = new CreditRiskCommentFilterDTO();
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if ((!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) && (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode()))) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else if (!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else {
            creditRiskCommentFilterDTO.setLoad(false);
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoadHistory(true);
        } else {
            creditRiskCommentFilterDTO.setLoadHistory(false);
        }
        facilityPaperDTO.setFpCreditRiskCommentFilterDTO(creditRiskCommentFilterDTO);

        LOG.info("END : Facility Paper update credit risk comment for Facility Paper ID: {}, by : {}", riskCommentUpdateDTO.getFacilityPaperID(), credentialsDTO.getUserID());

        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditCreditRiskReply(FPCreditRiskReplyDTO fpCreditRiskReplyDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper Credit Risk Reply Save Or Update: {} : {}", fpCreditRiskReplyDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(fpCreditRiskReplyDTO.getFacilityPaperID());

        FPCreditRiskComment creditRiskComment = facilityPaper.getFPCreditRiskCommentByID(fpCreditRiskReplyDTO.getFpCreditRiskCommentID());

        boolean isNew = fpCreditRiskReplyDTO.getFpCreditRiskReplyID() == null;
        FPCreditRiskReply fpCreditRiskReply = null;

        if (isNew) {
            fpCreditRiskReply = new FPCreditRiskReply();
            fpCreditRiskReply.setCreatedUserName(fpCreditRiskReplyDTO.getCreatedUserName());
            fpCreditRiskReply.setCreatedDivCode(fpCreditRiskReplyDTO.getCreatedDivCode());
            fpCreditRiskReply.setCreatedBy(credentialsDTO.getUserName());
            fpCreditRiskReply.setCreatedDate(date);
            fpCreditRiskReply.setStatus(AppsConstants.Status.ACT);
        } else {
            fpCreditRiskReply = creditRiskComment.getFPCreditRiskReplyByID(fpCreditRiskReplyDTO.getFpCreditRiskReplyID());
            fpCreditRiskReply.setModifiedUserName(fpCreditRiskReplyDTO.getModifiedUserName());
            fpCreditRiskReply.setModifiedBy(credentialsDTO.getUserName());
            fpCreditRiskReply.setModifiedDate(date);
        }
        fpCreditRiskReply.setReplyComment(fpCreditRiskReplyDTO.getReplyComment());
        creditRiskComment.addFPCreditRiskReply(fpCreditRiskReply);

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCreditRiskComments();

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        CreditRiskCommentFilterDTO creditRiskCommentFilterDTO = facilityPaperDTO.getFpCreditRiskCommentFilterDTO();

        if (creditRiskCommentFilterDTO == null) {
            creditRiskCommentFilterDTO = new CreditRiskCommentFilterDTO();
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if ((!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) && (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode()))) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else if (!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else {
            creditRiskCommentFilterDTO.setLoad(false);
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoadHistory(true);
        } else {
            creditRiskCommentFilterDTO.setLoadHistory(false);
        }
        facilityPaperDTO.setFpCreditRiskCommentFilterDTO(creditRiskCommentFilterDTO);

        LOG.info("END : Facility Paper Credit Risk Reply Save Or Update: {} : {}", fpCreditRiskReplyDTO, credentialsDTO.getUserID());

        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO saveOrUpdateSecuritySummery(FPSecuritySummeryDTO fpSecuritySummeryDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update security summery: {} : {}", fpSecuritySummeryDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(fpSecuritySummeryDTO.getFacilityPaperID());
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        boolean isNewSecSummery = fpSecuritySummeryDTO.getFpSecuritySummeryID() == null;
        FPSecuritySummery fpSecuritySummery = null;

        if (isNewSecSummery) {
            fpSecuritySummery = new FPSecuritySummery();
            fpSecuritySummery.setCreatedBy(credentialsDTO.getUserName());
            fpSecuritySummery.setCreatedDate(date);
            fpSecuritySummery.setFacilityPaper(facilityPaper);
            facilityPaper.setFpSecuritySummery(fpSecuritySummery);
        } else {
            fpSecuritySummery = facilityPaper.getFpSecuritySummery();
            fpSecuritySummery.setModifiedBy(credentialsDTO.getUserName());
            fpSecuritySummery.setModifiedDate(date);
        }


        fpSecuritySummery.setCompanySubTotalOne(fpSecuritySummeryDTO.getCompanySubTotalOne());
        fpSecuritySummery.setCompanySubTotalTwo(fpSecuritySummeryDTO.getCompanySubTotalTwo());
        fpSecuritySummery.setCompanyTotal(fpSecuritySummeryDTO.getCompanyTotal());
        fpSecuritySummery.setCompanySubTotalThree(fpSecuritySummeryDTO.getCompanySubTotalThree());
        fpSecuritySummery.setGroupSubTotalThree(fpSecuritySummeryDTO.getGroupSubTotalThree());
        fpSecuritySummery.setCompanySubTotalFour(fpSecuritySummeryDTO.getCompanySubTotalFour());
        fpSecuritySummery.setGroupSubTotalFour(fpSecuritySummeryDTO.getGroupSubTotalFour());

        if (fpSecuritySummeryDTO.getFacilitySecuritySummaryType() == DomainConstants.FacilitySecuritySummaryType.INDIVIDUAL) {
            fpSecuritySummery.setGroupSubTotalOne(DecimalCalculator.getDefaultZero());
            fpSecuritySummery.setGroupSubTotalTwo(DecimalCalculator.getDefaultZero());
            fpSecuritySummery.setGroupTotal(DecimalCalculator.getDefaultZero());
        } else {
            fpSecuritySummery.setGroupSubTotalOne(fpSecuritySummeryDTO.getGroupSubTotalOne());
            fpSecuritySummery.setGroupSubTotalTwo(fpSecuritySummeryDTO.getGroupSubTotalTwo());
            fpSecuritySummery.setGroupTotal(fpSecuritySummeryDTO.getGroupTotal());
        }

        fpSecuritySummery.setFacilitySecuritySummaryType(fpSecuritySummeryDTO.getFacilitySecuritySummaryType());

        fpSecuritySummery.setLimitSummery(fpSecuritySummeryDTO.getLimitSummery());

        for (FPSecuritySummaryTopicDTO fpSecuritySummaryTopicDTO : fpSecuritySummeryDTO.getFpSecuritySummaryTopicDTOS()) {
            boolean isNew = fpSecuritySummaryTopicDTO.getFpSecuritySummaryTopicID() == null;
            FPSecuritySummaryTopic fpSecuritySummaryTopic;
            if (isNew) {
                fpSecuritySummaryTopic = new FPSecuritySummaryTopic();
                fpSecuritySummaryTopic.setCreatedBy(credentialsDTO.getUserName());
                fpSecuritySummaryTopic.setCreatedDate(date);
                fpSecuritySummaryTopic.setStatus(AppsConstants.Status.ACT);
            } else {
                fpSecuritySummaryTopic = fpSecuritySummaryTopicDao.getOne(fpSecuritySummaryTopicDTO.getFpSecuritySummaryTopicID());
                fpSecuritySummaryTopic.setModifiedBy(credentialsDTO.getUserName());
                fpSecuritySummaryTopic.setModifiedDate(date);
                fpSecuritySummaryTopic.setStatus(fpSecuritySummaryTopicDTO.getStatus());
            }
            fpSecuritySummaryTopic.setCompanyPercentage(fpSecuritySummaryTopicDTO.getCompanyPercentage());
            fpSecuritySummaryTopic.setCompanyValue(fpSecuritySummaryTopicDTO.getCompanyValue());
            if (fpSecuritySummeryDTO.getFacilitySecuritySummaryType() == DomainConstants.FacilitySecuritySummaryType.INDIVIDUAL) {
                fpSecuritySummaryTopic.setGroupPercentage(0.0);
                fpSecuritySummaryTopic.setGroupValue(DecimalCalculator.getDefaultZero());
            } else {
                fpSecuritySummaryTopic.setGroupPercentage(fpSecuritySummaryTopicDTO.getGroupPercentage());
                fpSecuritySummaryTopic.setGroupValue(fpSecuritySummaryTopicDTO.getGroupValue());
            }
            fpSecuritySummaryTopic.setDisplayOrder(fpSecuritySummaryTopicDTO.getDisplayOrder());
            fpSecuritySummaryTopic.setSecurityTypeGroup(fpSecuritySummaryTopicDTO.getSecurityTypeGroup());
            fpSecuritySummaryTopic.setSecurityType(fpSecuritySummaryTopicDTO.getSecurityType());
            fpSecuritySummaryTopic.setFacilityPaperID(facilityPaper.getFacilityPaperID());

            fpSecuritySummery.addFpSecuritySummaryTopic(fpSecuritySummaryTopic);
        }

        //audit
        //TODO Audit

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper);
        LOG.info("END : Facility Paper update security summery: {} : {}", fpSecuritySummeryDTO, credentialsDTO.getUserID());

        return facilityPaperDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditCustomerOtherBankDetail(CustomerOtherBankFacilityDetailUpdateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : CASCustomer update other bank detail: {} : {}", updateDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        CASCustomer CASCustomer = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(updateDTO.getFacilityPaperID());

        CASCustomer = facilityPaper.getCasCustomerByID(updateDTO.getCasCustomerID());
        CASCustomer.setModifiedBy(credentialsDTO.getUserName());
        CASCustomer.setModifiedDate(date);

        for (CASCustomerOtherBankFacilityDTO CASCustomerOtherBankFacilityDTO : updateDTO.getCasCustomerOtherBankFacilityDTOList()) {
            boolean isNewOtherBankDetail = CASCustomerOtherBankFacilityDTO.getCasCustomerOtherBankFacilityID() == null;
            CasCustomerOtherBankFacility casCustomerOtherBankFacility = null;
            if (isNewOtherBankDetail) {
                casCustomerOtherBankFacility = new CasCustomerOtherBankFacility();
                casCustomerOtherBankFacility.setCreatedBy(credentialsDTO.getUserName());
                casCustomerOtherBankFacility.setCreatedDate(date);
                CASCustomer.addCasCustomerOtherBankFacility(casCustomerOtherBankFacility);
            } else {
                casCustomerOtherBankFacility = CASCustomer.getCasCustomerOtherBankFacilityByID(CASCustomerOtherBankFacilityDTO.getCasCustomerOtherBankFacilityID());
                casCustomerOtherBankFacility.setModifiedBy(credentialsDTO.getUserName());
                casCustomerOtherBankFacility.setModifiedDate(date);
            }
            casCustomerOtherBankFacility.setBankName(CASCustomerOtherBankFacilityDTO.getBankName());
            casCustomerOtherBankFacility.setBranchName(CASCustomerOtherBankFacilityDTO.getBranchName());
            casCustomerOtherBankFacility.setFacilityAmount(CASCustomerOtherBankFacilityDTO.getFacilityAmount());
            casCustomerOtherBankFacility.setOriginalAmount(CASCustomerOtherBankFacilityDTO.getOriginalAmount());
            casCustomerOtherBankFacility.setExistingAmount(CASCustomerOtherBankFacilityDTO.getExistingAmount());
            casCustomerOtherBankFacility.setOutstandingAmount(CASCustomerOtherBankFacilityDTO.getOutstandingAmount());
            casCustomerOtherBankFacility.setFacilityType(CASCustomerOtherBankFacilityDTO.getFacilityType());
            casCustomerOtherBankFacility.setSecurities(CASCustomerOtherBankFacilityDTO.getSecurities());
            casCustomerOtherBankFacility.setStatus(CASCustomerOtherBankFacilityDTO.getStatus());
            if (StringUtils.isNotBlank(CASCustomerOtherBankFacilityDTO.getDisbursementDateStr())) {
                casCustomerOtherBankFacility.setDisbursementDate(CalendarUtil.getDefaultParsedDateOnly(CASCustomerOtherBankFacilityDTO.getDisbursementDateStr()));
            }
            if (StringUtils.isNotBlank(CASCustomerOtherBankFacilityDTO.getMaturityDateStr())) {
                casCustomerOtherBankFacility.setMaturityDate(CalendarUtil.getDefaultParsedDateOnly(CASCustomerOtherBankFacilityDTO.getMaturityDateStr()));
            }
            //audit

        }

        facilityPaper.addCasCustomer(CASCustomer);
        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadCustomerOtherBankFacility();
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : CASCustomer update other bank detail: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO uploadFacilityPaperDocument(FPDocumentUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload facility paper document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();

        FacilityPaper facilityPaper = this.facilityPaperDao.getOne(uploadRQ.getFacilityPaperID());
        FPDocument fpDocument = null;
        FPDocumentDTO previousFPDoc = null;
        Boolean isNewFpDoc = uploadRQ.getFpDocumentID() == null;
        if (isNewFpDoc) {
            fpDocument = new FPDocument();
            fpDocument.setCreatedBy(credentialsDTO.getUserName());
            fpDocument.setCreatedDate(date);
            fpDocument.setUploadedDivCode(uploadRQ.getUploadedDivCode());
            fpDocument.setUploadedUserDisplayName(uploadRQ.getUploadedUserDisplayName());
            facilityPaper.addFpDocument(fpDocument);
        } else {
            fpDocument = facilityPaper.getFpDocumentByID(uploadRQ.getFpDocumentID());
            previousFPDoc = new FPDocumentDTO(fpDocument);
            fpDocument.setModifiedBy(credentialsDTO.getUserName());
            fpDocument.setModifiedDate(date);
        }
        if (uploadRQ.getDocStorageDTO() != null) {
            uploadRQ.getDocStorageDTO().setDescription("FACILITY PAPER: " + facilityPaper.getFacilityPaperNumber() + "");
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
            fpDocument.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Facility paper Document data null:{}", uploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_DOCUMENT_NULL_SUPPORT_DOC);
        }

        fpDocument.setDescription(uploadRQ.getRemark());
        fpDocument.setSupportingDoc(this.supportingDocDao.getOne(uploadRQ.getSupportingDocID()));
        fpDocument.setStatus(uploadRQ.getStatus());

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FPDocumentDTO updateFPDocDTO = new FPDocumentDTO(fpDocument);

        //audit
//        WebAuditDTO webAuditDTO = WebAuditUtils.constructFPDocumentAudit(updateFPDocDTO, previousFPDoc, credentialsDTO, date, isNewFpDoc, webAuditJDBCDao);
//        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Upload facility paper document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        FPLoadOptionDTO loadOption = new FPLoadOptionDTO();
        loadOption.loadFacilities();
        loadOption.loadCustomerRelatedDetails();
        loadOption.loadDocument();
        loadOption.loadDirectorDetail();
        loadOption.loadShareHolderDetail();
        loadOption.loadComment();
        loadOption.loadReviewerComment();
        loadOption.loadCompanyROA();
        loadOption.loadCreditRiskComments();
        loadOption.loadCustomer();
        loadOption.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOption.getFacilityLoadOptionDTO().loadAllData();
        return new FacilityPaperDTO(facilityPaper, loadOption);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditCasCustomer(CASCustomerUpdateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update customer: {} : {}", updateDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(updateDTO.getFacilityPaperID());

        for (CASCustomerDTO casCustomerDTO : updateDTO.getCasCustomerDTOList()) {
            boolean isNewFpCustomer = casCustomerDTO.getCasCustomerID() == null;
            CASCustomer casCustomer = null;
            CASCustomerDTO updateCASCustomerDTO = null;
            CASCustomerDTO previousCASCustomerDTO = null;
            FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
            loadOptionDTO.loadCustomerCribDetail();

            Customer customer = this.customerDao.getOne(casCustomerDTO.getCustomerID());

            if (isNewFpCustomer) {
                casCustomer = new CASCustomer();
                casCustomer.setCreatedBy(credentialsDTO.getUserName());
                casCustomer.setCreatedDate(date);
                casCustomer.setCustomer(customer);
                casCustomer.setTitle(customer.getTitle());
                casCustomer.setCasCustomerName(customer.getCustomerName());
                casCustomer.setDateOfBirth(customer.getDateOfBirth());
                casCustomer.setEmailAddress(customer.getEmailAddress());
                casCustomer.setSecondaryEmailAddress(customer.getSecondaryEmailAddress());
                casCustomer.setCivilStatus(customer.getCivilStatus());
                casCustomer.setIsPrimary(AppsConstants.YesNo.valueOf(casCustomerDTO.getIsPrimary()));
                casCustomer.setDisplayOrder(casCustomerDTO.getDisplayOrder());
                casCustomer.setType(casCustomerDTO.getType());
                casCustomer.setNameWithInitials(casCustomerDTO.getNameWithInitials());
                casCustomer.setInitialRepresentation(casCustomerDTO.getInitialRepresentation());
                casCustomer.setNameOfBusiness(casCustomerDTO.getNameOfBusiness());
                casCustomer.setRegistrationNo(casCustomerDTO.getRegistrationNo());
                casCustomer.setConstitution(casCustomerDTO.getConstitution());
                if (StringUtils.isNotBlank(casCustomerDTO.getDateOfIncorporateStr())) {
                    casCustomer.setDateOfIncorporate(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfIncorporateStr()));
                }
                if (StringUtils.isNotBlank(casCustomerDTO.getDateOfCommencementStr())) {
                    casCustomer.setDateOfCommencement(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfCommencementStr()));
                }
                if (StringUtils.isNotBlank(casCustomerDTO.getDateOfRegistrationStr())) {
                    casCustomer.setDateOfRegistration(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfRegistrationStr()));
                }
                casCustomer.setNatureOfBusiness(casCustomerDTO.getNatureOfBusiness());
                casCustomer.setNoOfBusinessYears(casCustomerDTO.getNoOfBusinessYears());
                casCustomer.setCitizenship(casCustomerDTO.getCitizenship());
                casCustomer.setPrivateAddress(casCustomerDTO.getPrivateAddress());
                casCustomer.setOfficialAddress(casCustomerDTO.getOfficialAddress());
                casCustomer.setBusinessAddress(casCustomerDTO.getBusinessAddress());
                casCustomer.setTelNumber(casCustomerDTO.getTelNumber());
                if (StringUtils.isNotBlank(casCustomerDTO.getDateOfBirthStr())) {
                    casCustomer.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfBirthStr()));
                }
                casCustomer.setPlaceOfBirth(casCustomerDTO.getPlaceOfBirth());
                casCustomer.setCivilStatus(casCustomerDTO.getCivilStatus());
                casCustomer.setNationality(casCustomerDTO.getNationality());
                casCustomer.setEmployment(casCustomerDTO.getEmployment());
                casCustomer.setEmployer(casCustomerDTO.getEmployer());
                casCustomer.setHighestEduAchievement(casCustomerDTO.getHighestEduAchievement());
                casCustomer.setPosition(casCustomerDTO.getPosition());
                casCustomer.setNoOfYearsEmployment(casCustomerDTO.getNoOfYearsEmployment());
                casCustomer.setCapitalAuthorized(casCustomerDTO.getCapitalAuthorized());
                casCustomer.setCapitalIssued(casCustomerDTO.getCapitalIssued());
                casCustomer.setCapitalPaidUp(casCustomerDTO.getCapitalPaidUp());


                for (CustomerAddress customerAddress : customer.getCustomerAddresses()) {
                    if (customerAddress.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerAddress CASCustomerAddress = new CASCustomerAddress();
                        CASCustomerAddress.setAddress1(customerAddress.getAddress1());
                        CASCustomerAddress.setAddress2(customerAddress.getAddress2());
                        CASCustomerAddress.setAddressType(customerAddress.getAddressType());
                        CASCustomerAddress.setCity(customerAddress.getCity());
                        CASCustomerAddress.setStatus(customerAddress.getStatus());
                        CASCustomerAddress.setCreatedDate(date);
                        CASCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
                        casCustomer.addCasCustomerAddress(CASCustomerAddress);
                    }
                }

                for (CustomerTelephone customerTelephone : customer.getCustomerTelephones()) {
                    if (customerTelephone.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerTelephone CASCustomerTelephone = new CASCustomerTelephone();
                        CASCustomerTelephone.setContactNumber(customerTelephone.getContactNumber());
                        CASCustomerTelephone.setDescription(customerTelephone.getDescription());
                        CASCustomerTelephone.setStatus(customerTelephone.getStatus());
                        CASCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                        CASCustomerTelephone.setCreatedDate(date);
                        casCustomer.addCasCustomerTelephone(CASCustomerTelephone);
                    }
                }

                for (CustomerIdentification customerIdentification : customer.getCustomerIdentifications()) {
                    if (customerIdentification.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerIdentification CASCustomerIdentification = new CASCustomerIdentification();
                        CASCustomerIdentification.setIdentificationNumber(customerIdentification.getIdentificationNumber());
                        CASCustomerIdentification.setIdentificationType(customerIdentification.getIdentificationType());
                        CASCustomerIdentification.setStatus(customerIdentification.getStatus());
                        CASCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                        CASCustomerIdentification.setCreatedDate(date);
                        casCustomer.addCasCustomerIdentification(CASCustomerIdentification);
                    }
                }

                for (CustomerBankDetail customerBankDetail : customer.getCustomerBankDetails()) {
                    if (customerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerBankDetail CASCustomerBankDetail = new CASCustomerBankDetail();
                        CASCustomerBankDetail.setBankAccountNumber(customerBankDetail.getBankAccountNumber());
                        CASCustomerBankDetail.setAccountCLSFlag(customerBankDetail.getAccountCLSFlag());
                        CASCustomerBankDetail.setBankAccountType(customerBankDetail.getBankAccountType());
                        CASCustomerBankDetail.setBranchCode(customerBankDetail.getBranchCode());
                        CASCustomerBankDetail.setAccountCLSFlag(customerBankDetail.getAccountCLSFlag());
                        CASCustomerBankDetail.setAccSince(customerBankDetail.getAccSince());
                        CASCustomerBankDetail.setSchmCode(customerBankDetail.getSchmCode());
                        CASCustomerBankDetail.setSchmType(customerBankDetail.getSchmType());
                        CASCustomerBankDetail.setAccountCurrencyCode(customerBankDetail.getAccountCurrencyCode());
                        CASCustomerBankDetail.setAccountStatus(customerBankDetail.getAccountStatus());
                        CASCustomerBankDetail.setStatus(customerBankDetail.getStatus());
                        CASCustomerBankDetail.setCreatedBy(credentialsDTO.getUserName());
                        CASCustomerBankDetail.setCreatedDate(date);
                        casCustomer.addCasCustomerBankDetail(CASCustomerBankDetail);
                    }
                }

                facilityPaper.addCasCustomer(casCustomer);
            } else {
                casCustomer = facilityPaper.getCasCustomerByID(casCustomerDTO.getCasCustomerID());
                previousCASCustomerDTO = new CASCustomerDTO(casCustomer, loadOptionDTO);
                casCustomer.setModifiedBy(credentialsDTO.getUserName());
                casCustomer.setModifiedDate(date);
            }

            casCustomer.setStatus(casCustomerDTO.getStatus());

            updateCASCustomerDTO = new CASCustomerDTO(casCustomer, loadOptionDTO);
            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructFPCustomerAudit(updateCASCustomerDTO, previousCASCustomerDTO, credentialsDTO, date, isNewFpCustomer, webAuditJDBCDao);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);
        }

        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadCustomerRelatedDetails();
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : Facility Paper update customer: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditNonFinacleCasCustomer(JoinNonFinacleCustomerRQ joinNonFinacleCustomerRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update customer: {} : {}", joinNonFinacleCustomerRQ, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(joinNonFinacleCustomerRQ.getFacilityPaperID());

        boolean isNewFpCustomer = joinNonFinacleCustomerRQ.getCasCustomerID() == null;
        CASCustomerDTO casCustomerDTO = joinNonFinacleCustomerRQ.getCasCustomerDTO();
        CASCustomer casCustomer = null;

        if (isNewFpCustomer) {
            casCustomer = new CASCustomer();
            casCustomer.setCreatedBy(credentialsDTO.getUserName());
            casCustomer.setCreatedDate(date);
            facilityPaper.addCasCustomer(casCustomer);
        } else {
            casCustomer = facilityPaper.getCasCustomerByID(joinNonFinacleCustomerRQ.getCasCustomerID());
            casCustomer.setModifiedBy(credentialsDTO.getUserName());
            casCustomer.setModifiedDate(date);
        }

        casCustomer.setTitle(casCustomerDTO.getTitle());
        casCustomer.setCivilStatus(casCustomerDTO.getCivilStatus());
        casCustomer.setEmailAddress(casCustomerDTO.getEmailAddress());
        casCustomer.setStatus(joinNonFinacleCustomerRQ.getStatus());
        switch (casCustomerDTO.getType()) {
            case PERSONAL:
                casCustomer.setCasCustomerName(casCustomerDTO.getNameWithInitials());
                break;
            case BUSINESS:
            case CORPORATE:
                casCustomer.setCasCustomerName(casCustomerDTO.getNameOfBusiness());
                break;
            default:
                casCustomer.setCasCustomerName(casCustomerDTO.getCasCustomerName());
        }
        casCustomer.setIsPrimary(joinNonFinacleCustomerRQ.getIsPrimary());
        casCustomer.setDisplayOrder(joinNonFinacleCustomerRQ.getDisplayOrder());
        casCustomer.setSecondaryEmailAddress(casCustomerDTO.getSecondaryEmailAddress());
        if (StringUtils.isNotBlank(casCustomerDTO.getDateOfBirthStr())) {
            casCustomer.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfBirthStr()));
        }
        casCustomer.setType(casCustomerDTO.getType());
        casCustomer.setNameWithInitials(casCustomerDTO.getNameWithInitials());
        casCustomer.setInitialRepresentation(casCustomerDTO.getInitialRepresentation());
        casCustomer.setNameOfBusiness(casCustomerDTO.getNameOfBusiness());
        casCustomer.setRegistrationNo(casCustomerDTO.getRegistrationNo());
        casCustomer.setConstitution(casCustomerDTO.getConstitution());
        if (StringUtils.isNotBlank(casCustomerDTO.getDateOfIncorporateStr())) {
            casCustomer.setDateOfIncorporate(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfIncorporateStr()));
        }
        if (StringUtils.isNotBlank(casCustomerDTO.getDateOfCommencementStr())) {
            casCustomer.setDateOfCommencement(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfCommencementStr()));
        }
        if (StringUtils.isNotBlank(casCustomerDTO.getDateOfRegistrationStr())) {
            casCustomer.setDateOfRegistration(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfRegistrationStr()));
        }
        casCustomer.setNatureOfBusiness(casCustomerDTO.getNatureOfBusiness());
        casCustomer.setNoOfBusinessYears(casCustomerDTO.getNoOfBusinessYears());
        casCustomer.setCitizenship(casCustomerDTO.getCitizenship());
        casCustomer.setPrivateAddress(casCustomerDTO.getPrivateAddress());
        casCustomer.setOfficialAddress(casCustomerDTO.getOfficialAddress());
        casCustomer.setBusinessAddress(casCustomerDTO.getBusinessAddress());
        casCustomer.setTelNumber(casCustomerDTO.getTelNumber());
        if (StringUtils.isNotBlank(casCustomerDTO.getDateOfBirthStr())) {
            casCustomer.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfBirthStr()));
        }
        casCustomer.setPlaceOfBirth(casCustomerDTO.getPlaceOfBirth());
        casCustomer.setCivilStatus(casCustomerDTO.getCivilStatus());
        casCustomer.setNationality(casCustomerDTO.getNationality());
        casCustomer.setEmployment(casCustomerDTO.getEmployment());
        casCustomer.setEmployer(casCustomerDTO.getEmployer());
        casCustomer.setHighestEduAchievement(casCustomerDTO.getHighestEduAchievement());
        casCustomer.setPosition(casCustomerDTO.getPosition());
        casCustomer.setNoOfYearsEmployment(casCustomerDTO.getNoOfYearsEmployment());
        casCustomer.setCapitalAuthorized(casCustomerDTO.getCapitalAuthorized());
        casCustomer.setCapitalIssued(casCustomerDTO.getCapitalIssued());
        casCustomer.setCapitalPaidUp(casCustomerDTO.getCapitalPaidUp());

        for (CASCustomerAddressDTO CASCustomerAddressDTO : casCustomerDTO.getCasCustomerAddressDTOList()) {
            if (CASCustomerAddressDTO.getStatus() == AppsConstants.Status.ACT) {
                CASCustomerAddress CASCustomerAddress;
                if (CASCustomerAddressDTO.getCasCustomerAddressID() != null) {
                    CASCustomerAddress = casCustomer.getCasCustomerAddressByID(CASCustomerAddressDTO.getCasCustomerAddressID());
                    CASCustomerAddress.setModifiedBy(credentialsDTO.getUserName());
                    CASCustomerAddress.setModifiedDate(date);
                } else {
                    CASCustomerAddress = new CASCustomerAddress();
                    CASCustomerAddress.setCreatedDate(date);
                    CASCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
                }
                CASCustomerAddress.setAddress1(CASCustomerAddressDTO.getAddress1());
                CASCustomerAddress.setAddress2(CASCustomerAddressDTO.getAddress2());
                CASCustomerAddress.setAddressType(CASCustomerAddressDTO.getAddressType());
                CASCustomerAddress.setCity(CASCustomerAddressDTO.getCity());
                CASCustomerAddress.setStatus(CASCustomerAddressDTO.getStatus());

                casCustomer.addCasCustomerAddress(CASCustomerAddress);
            }
        }

        for (CASCustomerTelephoneDTO CASCustomerTelephoneDTO : casCustomerDTO.getCasCustomerTelephoneDTOList()) {
            if (CASCustomerTelephoneDTO.getStatus() == AppsConstants.Status.ACT) {
                CASCustomerTelephone CASCustomerTelephone;
                if (CASCustomerTelephoneDTO.getFpCustomerTelephoneID() != null) {
                    CASCustomerTelephone = casCustomer.getcasCustomerTelephoneByID(CASCustomerTelephoneDTO.getFpCustomerTelephoneID());
                    CASCustomerTelephone.setModifiedBy(credentialsDTO.getUserName());
                    CASCustomerTelephone.setModifiedDate(date);
                } else {
                    CASCustomerTelephone = new CASCustomerTelephone();
                    CASCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerTelephone.setCreatedDate(date);
                }
                CASCustomerTelephone.setContactNumber(CASCustomerTelephoneDTO.getContactNumber());
                CASCustomerTelephone.setDescription(CASCustomerTelephoneDTO.getDescription());
                CASCustomerTelephone.setStatus(CASCustomerTelephoneDTO.getStatus());

                casCustomer.addCasCustomerTelephone(CASCustomerTelephone);
            }
        }

        for (CASCustomerIdentificationDTO CASCustomerIdentificationDTO : casCustomerDTO.getCasCustomerIdentificationDTOList()) {
            if (CASCustomerIdentificationDTO.getStatus() == AppsConstants.Status.ACT) {
                CASCustomerIdentification CASCustomerIdentification;
                if (CASCustomerIdentificationDTO.getCasCustomerIdentificationID() != null) {
                    CASCustomerIdentification = casCustomer.getCasCustomerIdentificationByID(CASCustomerIdentificationDTO.getCasCustomerIdentificationID());
                    CASCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerIdentification.setCreatedDate(date);
                } else {
                    CASCustomerIdentification = new CASCustomerIdentification();
                    CASCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerIdentification.setCreatedDate(date);
                }
                CASCustomerIdentification.setIdentificationNumber(CASCustomerIdentificationDTO.getIdentificationNumber());
                CASCustomerIdentification.setIdentificationType(CASCustomerIdentificationDTO.getIdentificationType());
                CASCustomerIdentification.setStatus(CASCustomerIdentificationDTO.getStatus());

                casCustomer.addCasCustomerIdentification(CASCustomerIdentification);
            }
        }

        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadCribDetails();
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : Facility Paper update customer: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO removeFPJoningParties(CASCustomerUpdateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Remove Joining Party from the Facility Paper : {} : {}", updateDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();
        facilityPaper = facilityPaperDao.getOne(updateDTO.getFacilityPaperID());

        boolean isDeleteInsurance = false;
        for (CASCustomerDTO CASCustomerDTO : updateDTO.getCasCustomerDTOList()) {
            if (CASCustomerDTO.getStatus() == AppsConstants.Status.INA) {
                CASCustomer CASCustomer = facilityPaper.getCasCustomerByID(CASCustomerDTO.getCasCustomerID());
                CASCustomer.setModifiedBy(credentialsDTO.getUserName());
                CASCustomer.setModifiedDate(date);
                CASCustomer.setStatus(CASCustomerDTO.getStatus());

                try {
                    isDeleteInsurance = finacleService.removeInsuranceValuationsToDB(updateDTO.getFacilityPaperID(), CASCustomerDTO.getCustomerFinancialID(), credentialsDTO);

                } catch (Exception e) {
                    isDeleteInsurance = false;
                }
                LOG.info("END :Status of delete Insurance Details When Remove Joining Party from the Facility Paper : {} ", isDeleteInsurance);
            }
        }

        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadCribDetails();
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : Remove Joining Party from the Facility Paper : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO uploadFPCustomerDocument(FPCustomerDocumentUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload facility paper customer document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();

        FacilityPaper facilityPaper = this.facilityPaperDao.getOne(uploadRQ.getFacilityPaperID());
        CASCustomerDoc CASCustomerDoc = null;
        CASCustomerDocDTO previousCASCustomerDocDTO = null;
        CASCustomer CASCustomer = facilityPaper.getCasCustomerByID(uploadRQ.getCasCustomerID());
        boolean isNewCustomerDoc = uploadRQ.getFpCustomerDocumentID() == null;
        if (isNewCustomerDoc) {
            CASCustomerDoc = new CASCustomerDoc();
            CASCustomerDoc.setCreatedBy(credentialsDTO.getUserName());
            CASCustomerDoc.setCreatedDate(date);
            CASCustomer.addCasCustomerDoc(CASCustomerDoc);
        } else {
            CASCustomerDoc = CASCustomer.getCasCustomerDocByID(uploadRQ.getFpCustomerDocumentID());
            previousCASCustomerDocDTO = new CASCustomerDocDTO(CASCustomerDoc);
            CASCustomerDoc.setModifiedBy(credentialsDTO.getUserName());
            CASCustomerDoc.setModifiedDate(date);
        }
        if (uploadRQ.getDocStorageDTO() != null) {
            uploadRQ.getDocStorageDTO().setDescription("FACILITY PAPER: " + facilityPaper.getFacilityPaperNumber() + ", CUSTOMER: ");
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
            CASCustomerDoc.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Facility paper customer document data null:{}", uploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_CUSTOMER_DOCUMENT_NULL_SUPPORT_DOC);
        }

        CASCustomerDoc.setDescription(uploadRQ.getRemark());
        CASCustomerDoc.setSupportingDoc(this.supportingDocDao.getOne(uploadRQ.getSupportingDocID()));
        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        CASCustomerDocDTO updateCASCustomerDocDTO = new CASCustomerDocDTO(CASCustomerDoc);

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFPCustomerDocAudit(updateCASCustomerDocDTO, previousCASCustomerDocDTO, credentialsDTO, date, isNewCustomerDoc, webAuditJDBCDao, facilityPaper.getFacilityPaperID());
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Upload facility paper customer document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadCustomer();
        loadOptionDTO.loadCustomerDocument();
        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO replicateFacilityPaper(FacilityPaperReplicationRQ replicationRQ, CredentialsDTO credentialsDTO) throws AppsException {
        Date date = new Date();
        LOG.info("START : Replicate facility paper : {} : {}", replicationRQ, credentialsDTO.getUserID());
        FacilityPaperReplicationBuilder replicationBuilder = new FacilityPaperReplicationBuilder(credentialsDTO, replicationRQ);
        replicationBuilder.setFacilityPaperDao(facilityPaperDao);
        replicationBuilder.setDocStorageDao(docStorageDao);
        replicationBuilder.setWorkFlowTemplateDao(workFlowTemplateDao);
        replicationBuilder.setFacilitySecurityDao(facilitySecurityDao);
        replicationBuilder.setApplicationFormDao(applicationFormDao);
        replicationBuilder.setFacilityPaperService(this);
        FacilityPaper replicatedFacilityPaper = replicationBuilder.buildInitialData()
                .buildCalculateFacilityPaperExposure()
                .buildBaseFacilityData()
                .buildCompanyROADetails()
                .buildDirectorDetails()
                .buildShareholderDetails()
                .buildFacilityPaperCustomers()
                .buildFacilityPaperDocuments()
                .buildFacilities()
                .replicateSecuritySummary()
                .buildUPCSectionData()
                //.buildCustomerCovenant()
                //.buildFacilityCovenant()
                .buildInitialHistory()
                .buildCribDetails()
                //.buildRiskCategories()
                .getReplicatedFacilityPaper();

        replicatedFacilityPaper = this.facilityPaperDao.saveAndFlush(replicatedFacilityPaper);


        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructReplicateFacilityPaperAudit(new FacilityPaperDTO(replicatedFacilityPaper), replicationRQ.getOriginalFacilityPaperID(), credentialsDTO, date, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCustomerRatings();
        loadOptionDTO.loadShareHolderDetail();
        loadOptionDTO.loadCustomerCovenant();
        loadOptionDTO.loadFacilityCovenant();

        FacilityLoadOptionDTO facilityLoadOptionDTO = new FacilityLoadOptionDTO();
        facilityLoadOptionDTO.loadAllData();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOptionDTO);

        LOG.info("END : Replicate facility paper : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return new FacilityPaperDTO(replicatedFacilityPaper, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO copyUPCSectionData(UpcTemplateAddRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        FacilityPaper facilityPaper = facilityPaperDao.getOne(updateDTO.getFacilityPaperID());
        FacilityPaper copyFormFacilityPaper = facilityPaperDao.getOne(updateDTO.getCopyFromFacilityPaperID());
        Date date = new Date();
        boolean isNewUPCSection = false;
        if (facilityPaper.getFpUpcSectionDataSet().size() == 0) {
            isNewUPCSection = true;
        } else {
            isNewUPCSection = false;
        }

        for (FPUpcSectionData fpUpcSectionData : copyFormFacilityPaper.getFpUpcSectionDataSet()) {
            if (fpUpcSectionData.getStatus() == AppsConstants.Status.ACT) {
                FPUpcSectionData replicatedUPCSectionData = new FPUpcSectionData();
                if (StringUtils.isNotEmpty(fpUpcSectionData.getData())) {
                    replicatedUPCSectionData.setModifiedUserDisplayName(updateDTO.getUpdatedUserDisplayName());
                    replicatedUPCSectionData.setModifiedDate(date);
                }
                replicatedUPCSectionData.setCreatedBy(credentialsDTO.getUserName());
                replicatedUPCSectionData.setCreatedDate(date);
                replicatedUPCSectionData.setStatus(AppsConstants.Status.ACT);

                replicatedUPCSectionData.setUpcSection(fpUpcSectionData.getUpcSection());
                replicatedUPCSectionData.setParentSectionID(fpUpcSectionData.getParentSectionID());
                replicatedUPCSectionData.setSectionLevel(fpUpcSectionData.getSectionLevel());
                replicatedUPCSectionData.setDisplayOrder(fpUpcSectionData.getDisplayOrder());
                replicatedUPCSectionData.setData(fpUpcSectionData.getData());
                facilityPaper.addFpUpcSectionData(replicatedUPCSectionData);
            }
        }

        if (facilityPaper.getApplicationFormID() != null && isNewUPCSection) {

            Map<Integer, FacilitySecurity> facilitySecurityMap = new HashMap<>();
            Map<Integer, FacilitySecurity> addedFacilitySecuritiesMap = new HashMap<>();

            for (Facility originalFacility : copyFormFacilityPaper.getFacilitySet()) {
                for (FacilityFacilitySecurity originalFacilitySecurityFacilitySecurity : originalFacility.getFacilityFacilitySecurities()) {
                    facilitySecurityMap.putIfAbsent(originalFacilitySecurityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID(), originalFacilitySecurityFacilitySecurity.getFacilitySecurity());
                }
            }

            for (Integer facilitySecurityID : facilitySecurityMap.keySet()) {
                FacilitySecurity facilitySecurity = new FacilitySecurity();
                FacilitySecurity mappedFacilitySecurity = facilitySecurityMap.get(facilitySecurityID);
                facilitySecurity.setSecurityDetail(mappedFacilitySecurity.getSecurityDetail());
                facilitySecurity.setSecurityAmount(mappedFacilitySecurity.getSecurityAmount());
                facilitySecurity.setStatus(mappedFacilitySecurity.getStatus());
                facilitySecurity.setIsCommonSecurity(mappedFacilitySecurity.getIsCommonSecurity());
                facilitySecurity.setCashAmount(mappedFacilitySecurity.getCashAmount());
                facilitySecurity.setIsCashSecurity(mappedFacilitySecurity.getIsCashSecurity());
                facilitySecurity.setSecurityCode(mappedFacilitySecurity.getSecurityCode());
                facilitySecurity.setSecurityCurrency(mappedFacilitySecurity.getSecurityCurrency());
                facilitySecurity.setCreatedDate(date);
                facilitySecurity.setCreatedBy(credentialsDTO.getUserName());
                facilitySecurity = facilitySecurityDao.saveAndFlush(facilitySecurity);
                addedFacilitySecuritiesMap.put(facilitySecurityID, facilitySecurity);
            }

            for (Facility facility : copyFormFacilityPaper.getFacilitySet()) {
                if (facility.getStatus() == AppsConstants.Status.ACT) {

                    Facility replicatedFacility = new Facility();
                    replicatedFacility.setFacilityPaper(facilityPaper);
                    replicatedFacility.setCreatedBy(credentialsDTO.getUserName());
                    replicatedFacility.setCreatedDate(date);
                    replicatedFacility.setCreditFacilityType(facility.getCreditFacilityType());
                    replicatedFacility.setCreditFacilityTemplate(facility.getCreditFacilityTemplate());
                    replicatedFacility.setCondition(facility.getCondition());
                    replicatedFacility.setDisbursementAccNumber(facility.getDisbursementAccNumber());
                    replicatedFacility.setDisplayOrder(facility.getDisplayOrder());
                    replicatedFacility.setFacilityAmount(facility.getFacilityAmount());
                    replicatedFacility.setExistingAmount(facility.getExistingAmount());
                    replicatedFacility.setOriginalAmount(facility.getOriginalAmount());
                    replicatedFacility.setFacilityCurrency(facility.getFacilityCurrency());
                    replicatedFacility.setIsCooperate(facility.getIsCooperate());
                    replicatedFacility.setIsOneOff(facility.getIsOneOff());
                    replicatedFacility.setSeriesOfLoans(facility.getSeriesOfLoans());
                    replicatedFacility.setRevolving(facility.getRevolving());
                    replicatedFacility.setDirectFacility(facility.getDirectFacility());
                    replicatedFacility.setIsNew(facility.getIsNew());
                    replicatedFacility.setReduction(facility.getReduction());
                    replicatedFacility.setEnhancement(facility.getEnhancement());
                    replicatedFacility.setSectorID(facility.getSectorID());
                    replicatedFacility.setSubSectorID(facility.getSubSectorID());
                    replicatedFacility.setCashFlowGenerationSectorID(facility.getCashFlowGenerationSectorID());
                    replicatedFacility.setStatus(facility.getStatus());
                    replicatedFacility.setOutstandingAmount(facility.getOutstandingAmount());
                    replicatedFacility.setRepayment(facility.getRepayment());
                    replicatedFacility.setPurpose(facility.getPurpose());
                    replicatedFacility.setPurposeOfAdvance(facility.getPurposeOfAdvance());
                    replicatedFacility.setFacilityRefCode(facility.getFacilityRefCode());
                    replicatedFacility.setFacilityType(facility.getFacilityType());
                    replicatedFacility.setRemark(facility.getRemark());

                    for (FacilityVitalInfoData facilityVitalInfoData : facility.getFacilityVitalInfoData()) {
                        FacilityVitalInfoData replicatedFacilityVitalInfoData = new FacilityVitalInfoData();
                        replicatedFacilityVitalInfoData.setFacility(replicatedFacility);
                        replicatedFacilityVitalInfoData.setCftVitalInfoID(facilityVitalInfoData.getCftVitalInfoID());
                        replicatedFacilityVitalInfoData.setVitalInfoName(facilityVitalInfoData.getVitalInfoName());
                        replicatedFacilityVitalInfoData.setVitalInfoData(facilityVitalInfoData.getVitalInfoData());
                        replicatedFacilityVitalInfoData.setMandatory(facilityVitalInfoData.getMandatory());
                        replicatedFacilityVitalInfoData.setStatus(facilityVitalInfoData.getStatus());
                        replicatedFacilityVitalInfoData.setCreatedDate(date);
                        replicatedFacilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                        replicatedFacilityVitalInfoData.setVersion(0L);
                        replicatedFacilityVitalInfoData.setDisplayOrder(facilityVitalInfoData.getDisplayOrder());
                        replicatedFacility.addFacilityVitalInfoData(replicatedFacilityVitalInfoData);

                    }

                    for (FacilityFacilitySecurity originalFacilitySecurityFacilitySecurity : facility.getFacilityFacilitySecurities()) {
                        FacilityFacilitySecurity replicatedFacilityFacilitySecurity = new FacilityFacilitySecurity();
                        replicatedFacilityFacilitySecurity.setFacility(replicatedFacility);
                        replicatedFacilityFacilitySecurity.setFacilitySecurity(addedFacilitySecuritiesMap.get(originalFacilitySecurityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID()));
                        replicatedFacilityFacilitySecurity.setIsCashSecurity(originalFacilitySecurityFacilitySecurity.getIsCashSecurity());
                        replicatedFacilityFacilitySecurity.setFacilitySecurityAmount(originalFacilitySecurityFacilitySecurity.getFacilitySecurityAmount());
                        replicatedFacilityFacilitySecurity.setStatus(originalFacilitySecurityFacilitySecurity.getStatus());
                        replicatedFacility.addFacilityFacilitySecurity(replicatedFacilityFacilitySecurity);
                    }

                    for (FacilityInterestRate originalFacilityInterestRate : facility.getFacilityInterestRates()) {
                        FacilityInterestRate replicatedFacilityInterestRate = new FacilityInterestRate();
                        replicatedFacilityInterestRate.setCftInterestRateID(originalFacilityInterestRate.getCftInterestRateID());
                        replicatedFacilityInterestRate.setRateName(originalFacilityInterestRate.getRateName());
                        replicatedFacilityInterestRate.setInterestRatingSubCategory(originalFacilityInterestRate.getInterestRatingSubCategory());
                        replicatedFacilityInterestRate.setIsEditable(originalFacilityInterestRate.getIsEditable());
                        replicatedFacilityInterestRate.setRateCode(originalFacilityInterestRate.getRateCode());
                        replicatedFacilityInterestRate.setUserComment(originalFacilityInterestRate.getUserComment());
                        replicatedFacilityInterestRate.setValue(originalFacilityInterestRate.getValue());
                        replicatedFacilityInterestRate.setIsDefault(originalFacilityInterestRate.getIsDefault());
                        replicatedFacilityInterestRate.setStatus(originalFacilityInterestRate.getStatus());
                        replicatedFacilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                        replicatedFacilityInterestRate.setCreatedDate(date);
                        replicatedFacility.addFacilityInterestRate(replicatedFacilityInterestRate);
                    }

                    for (FacilityOtherFacilityInformation originalFacilityOtherFacilityInformation : facility.getOrderedFacilityOtherFacilityInformations()) {
                        if (originalFacilityOtherFacilityInformation.getStatus().equals(AppsConstants.Status.ACT)) {
                            FacilityOtherFacilityInformation facilityOtherFacilityInformation = new FacilityOtherFacilityInformation();
                            facilityOtherFacilityInformation.setCftOtherFacilityInfoID(originalFacilityOtherFacilityInformation.getCftOtherFacilityInfoID());
                            facilityOtherFacilityInformation.setOtherFacilityInfoName(originalFacilityOtherFacilityInformation.getOtherFacilityInfoName());
                            facilityOtherFacilityInformation.setOtherFacilityInfoCode(originalFacilityOtherFacilityInformation.getOtherFacilityInfoCode());
                            facilityOtherFacilityInformation.setOtherFacilityInfoFieldType(originalFacilityOtherFacilityInformation.getOtherFacilityInfoFieldType());
                            facilityOtherFacilityInformation.setDefaultValue(originalFacilityOtherFacilityInformation.getDefaultValue());
                            facilityOtherFacilityInformation.setDisplayOrder(originalFacilityOtherFacilityInformation.getDisplayOrder());
                            facilityOtherFacilityInformation.setCreatedBy(credentialsDTO.getUserName());
                            facilityOtherFacilityInformation.setCreatedDate(date);
                            facilityOtherFacilityInformation.setStatus(AppsConstants.Status.ACT);
                            replicatedFacility.addOtherFacilityInformation(facilityOtherFacilityInformation);
                        }
                    }
                    facilityPaper.addFacility(replicatedFacility);
                }
            }


            for (FPDocument originalFpDocument : copyFormFacilityPaper.getFpDocumentSet()) {
                FPDocument replicatedFpDocument = new FPDocument();
                replicatedFpDocument.setCreatedBy(credentialsDTO.getUserName());
                replicatedFpDocument.setCreatedDate(date);
                if (originalFpDocument.getDocStorage() != null) {
                    DocStorage docStorage = new DocStorage();
                    docStorage.setFileName(originalFpDocument.getDocStorage().getFileName());
                    docStorage.setDescription(originalFpDocument.getDocStorage().getDescription());
                    docStorage.setDocument(originalFpDocument.getDocStorage().getDocument());
                    docStorage.setLastUpdatedDate(date);
                    replicatedFpDocument.setDocStorage(this.docStorageDao.save(docStorage));
                }

                replicatedFpDocument.setUploadedDivCode(credentialsDTO.getDivCode());
                replicatedFpDocument.setUploadedUserDisplayName(updateDTO.getUpdatedUserDisplayName());
                replicatedFpDocument.setDescription(originalFpDocument.getDescription());
                replicatedFpDocument.setSupportingDoc(originalFpDocument.getSupportingDoc());
                replicatedFpDocument.setStatus(originalFpDocument.getStatus());

                facilityPaper.addFpDocument(replicatedFpDocument);
            }


            for (FPCompanyRoa originalFPCompanyRoa : copyFormFacilityPaper.getFpCompanyRoaSet()) {
                FPCompanyRoa fpCompanyRoa = new FPCompanyRoa();
                fpCompanyRoa.setCreatedBy(credentialsDTO.getUserName());
                fpCompanyRoa.setCreatedDate(date);
                fpCompanyRoa.setDescription(originalFPCompanyRoa.getDescription());
                fpCompanyRoa.setComment(originalFPCompanyRoa.getComment());
                fpCompanyRoa.setStatus(originalFPCompanyRoa.getStatus());
                facilityPaper.addFpCompanyRoa(fpCompanyRoa);
            }


            for (FPDirectorDetail originalFPDirectorDetail : copyFormFacilityPaper.getFpDirectorDetailSet()) {
                FPDirectorDetail replicateFPDirector = new FPDirectorDetail();
                replicateFPDirector.setCreatedBy(credentialsDTO.getUserName());
                replicateFPDirector.setCreatedDate(date);
                replicateFPDirector.setAddress(originalFPDirectorDetail.getAddress());
                replicateFPDirector.setCivilStatus(originalFPDirectorDetail.getCivilStatus());
                replicateFPDirector.setFullName(originalFPDirectorDetail.getFullName());
                replicateFPDirector.setDirectorName(originalFPDirectorDetail.getDirectorName());
                replicateFPDirector.setStatus(originalFPDirectorDetail.getStatus());
                replicateFPDirector.setDateOfBirth(originalFPDirectorDetail.getDateOfBirth());
                replicateFPDirector.setNic(originalFPDirectorDetail.getNic());
                replicateFPDirector.setShareHolding(originalFPDirectorDetail.getShareHolding());

                facilityPaper.addFpDirectorDetail(replicateFPDirector);
            }


            FPSecuritySummery originalFpSecuritySummery = copyFormFacilityPaper.getFpSecuritySummery();
            if (originalFpSecuritySummery != null) {
                FPSecuritySummery replicatedFpSecuritySummery = new FPSecuritySummery();
                replicatedFpSecuritySummery.setCompanyCash(originalFpSecuritySummery.getCompanyCash());
                replicatedFpSecuritySummery.setGroupCash(originalFpSecuritySummery.getGroupCash());
                replicatedFpSecuritySummery.setCompanyProperty(originalFpSecuritySummery.getCompanyProperty());
                replicatedFpSecuritySummery.setGroupProperty(originalFpSecuritySummery.getGroupProperty());
                replicatedFpSecuritySummery.setCompanySubTotalOne(originalFpSecuritySummery.getCompanySubTotalOne());
                replicatedFpSecuritySummery.setGroupSubTotalOne(originalFpSecuritySummery.getGroupSubTotalOne());
                replicatedFpSecuritySummery.setCompanyInvoiceReceivables(originalFpSecuritySummery.getCompanyInvoiceReceivables());
                replicatedFpSecuritySummery.setGroupInvoiceReceivables(originalFpSecuritySummery.getGroupInvoiceReceivables());
                replicatedFpSecuritySummery.setCompanyCorporateGuarantees(originalFpSecuritySummery.getCompanyCorporateGuarantees());
                replicatedFpSecuritySummery.setGroupCorporateGuarantees(originalFpSecuritySummery.getGroupCorporateGuarantees());
                replicatedFpSecuritySummery.setCompanyLeaseIndenture(originalFpSecuritySummery.getCompanyLeaseIndenture());
                replicatedFpSecuritySummery.setGroupLeaseIndenture(originalFpSecuritySummery.getGroupLeaseIndenture());
                replicatedFpSecuritySummery.setCompanySubTotalTwo(originalFpSecuritySummery.getCompanySubTotalTwo());
                replicatedFpSecuritySummery.setGroupSubTotalTwo(originalFpSecuritySummery.getGroupSubTotalTwo());
                replicatedFpSecuritySummery.setCompanyClean(originalFpSecuritySummery.getCompanyClean());
                replicatedFpSecuritySummery.setGroupClean(originalFpSecuritySummery.getGroupClean());
                replicatedFpSecuritySummery.setCompanyTotal(originalFpSecuritySummery.getCompanyTotal());
                replicatedFpSecuritySummery.setGroupTotal(originalFpSecuritySummery.getGroupTotal());
                replicatedFpSecuritySummery.setLimitSummery(originalFpSecuritySummery.getLimitSummery());
                replicatedFpSecuritySummery.setFacilitySecuritySummaryType(originalFpSecuritySummery.getFacilitySecuritySummaryType());
                replicatedFpSecuritySummery.setCreatedBy(credentialsDTO.getUserName());
                replicatedFpSecuritySummery.setCreatedDate(date);
                replicatedFpSecuritySummery.setCompanySubTotalThree(originalFpSecuritySummery.getCompanySubTotalThree());
                replicatedFpSecuritySummery.setGroupSubTotalThree(originalFpSecuritySummery.getGroupSubTotalThree());

                for (FPSecuritySummaryTopic originalFpSecuritySummaryTopic : originalFpSecuritySummery.getFpSecuritySummaryTopics()) {
                    if (originalFpSecuritySummaryTopic.getStatus() == AppsConstants.Status.ACT) {
                        FPSecuritySummaryTopic replicatedSecuritySummaryTopic = new FPSecuritySummaryTopic();
                        replicatedSecuritySummaryTopic.setDisplayOrder(originalFpSecuritySummaryTopic.getDisplayOrder());
                        replicatedSecuritySummaryTopic.setSecurityType(originalFpSecuritySummaryTopic.getSecurityType());
                        replicatedSecuritySummaryTopic.setSecurityTypeGroup(originalFpSecuritySummaryTopic.getSecurityTypeGroup());
                        replicatedSecuritySummaryTopic.setCompanyValue(originalFpSecuritySummaryTopic.getCompanyValue());
                        replicatedSecuritySummaryTopic.setCompanyPercentage(originalFpSecuritySummaryTopic.getCompanyPercentage());
                        replicatedSecuritySummaryTopic.setGroupValue(originalFpSecuritySummaryTopic.getGroupValue());
                        replicatedSecuritySummaryTopic.setGroupPercentage(originalFpSecuritySummaryTopic.getGroupPercentage());
                        replicatedSecuritySummaryTopic.setStatus(originalFpSecuritySummaryTopic.getStatus());
                        replicatedSecuritySummaryTopic.setCreatedBy(credentialsDTO.getUserName());
                        replicatedSecuritySummaryTopic.setCreatedDate(date);
                        replicatedFpSecuritySummery.addFpSecuritySummaryTopic(replicatedSecuritySummaryTopic);
                    }
                }

                replicatedFpSecuritySummery.setFacilityPaper(facilityPaper);
                facilityPaper.setFpSecuritySummery(replicatedFpSecuritySummery);
            }


            facilityPaper.setFpSecuritySummery(copyFormFacilityPaper.getFpSecuritySummery());


        }


        facilityPaper.setUpcTemplate(upcTemplateDao.getOne(updateDTO.getUpcTemplateID()));
        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);

        if (facilityPaper.getApplicationFormID() != null && isNewUPCSection) {

            facilityPaper = this.facilityPaperDao.getOne(updateDTO.getFacilityPaperID());

            CalculateExposureRQ calculateExposureRQ = new CalculateExposureRQ();
            calculateExposureRQ.setFacilityPaperID(updateDTO.getFacilityPaperID());
            calculateExposureRQ.setIsCooperate(facilityPaper.getIsCommittee());

            FacilityPaperDTO calculatedFacilityPaperDTO = this.calculateFacilityPaperExposure(calculateExposureRQ, credentialsDTO);

            facilityPaper.setTotalDirectExposurePrevious(calculatedFacilityPaperDTO.getTotalDirectExposurePrevious());
            facilityPaper.setTotalDirectExposureNew(calculatedFacilityPaperDTO.getTotalDirectExposureNew());
            facilityPaper.setTotalIndirectExposurePrevious(calculatedFacilityPaperDTO.getTotalIndirectExposurePrevious());
            facilityPaper.setTotalIndirectExposureNew(calculatedFacilityPaperDTO.getTotalIndirectExposureNew());
            facilityPaper.setTotalExposurePrevious(calculatedFacilityPaperDTO.getTotalExposurePrevious());
            facilityPaper.setTotalExposureNew(calculatedFacilityPaperDTO.getTotalExposureNew());
            facilityPaper.setGroupTotalDirectExposurePrevious(calculatedFacilityPaperDTO.getGroupTotalDirectExposurePrevious());
            facilityPaper.setGroupTotalDirectExposureNew(calculatedFacilityPaperDTO.getGroupTotalDirectExposureNew());
            facilityPaper.setGroupTotalIndirectExposurePrevious(calculatedFacilityPaperDTO.getGroupTotalIndirectExposurePrevious());
            facilityPaper.setGroupTotalIndirectExposureNew(calculatedFacilityPaperDTO.getGroupTotalIndirectExposureNew());
            facilityPaper.setGroupTotalExposurePrevious(calculatedFacilityPaperDTO.getGroupTotalExposurePrevious());
            facilityPaper.setGroupTotalExposureNew(calculatedFacilityPaperDTO.getGroupTotalExposureNew());
            facilityPaper.setTotalDirectExposureExisting(calculatedFacilityPaperDTO.getTotalDirectExposureExisting());
            facilityPaper.setTotalIndirectExposureExisting(calculatedFacilityPaperDTO.getTotalIndirectExposureExisting());
            facilityPaper.setTotalExposureExisting(calculatedFacilityPaperDTO.getTotalExposureExisting());
            facilityPaper.setExistingCashMargin(calculatedFacilityPaperDTO.getExistingCashMargin());
            facilityPaper.setGroupExistingCashMargin(calculatedFacilityPaperDTO.getGroupExistingCashMargin());
            facilityPaper.setGroupTotalDirectExposureExisting(calculatedFacilityPaperDTO.getGroupTotalDirectExposureExisting());
            facilityPaper.setGroupTotalIndirectExposureExisting(calculatedFacilityPaperDTO.getGroupTotalIndirectExposureExisting());
            facilityPaper.setGroupTotalExposureExisting(calculatedFacilityPaperDTO.getGroupTotalExposureExisting());
            facilityPaper.setNetTotalExposureNew(calculatedFacilityPaperDTO.getNetTotalExposureNew());
            facilityPaper.setNetTotalExposurePrevious(calculatedFacilityPaperDTO.getNetTotalExposurePrevious());
            facilityPaper.setNetTotalExposureExisting(calculatedFacilityPaperDTO.getNetTotalExposureExisting());
            facilityPaper.setGroupNetTotalExposureNew(calculatedFacilityPaperDTO.getGroupNetTotalExposureNew());
            facilityPaper.setGroupNetTotalExposurePrevious(calculatedFacilityPaperDTO.getGroupNetTotalExposurePrevious());
            facilityPaper.setGroupNetTotalExposureExisting(calculatedFacilityPaperDTO.getGroupNetTotalExposureExisting());
            facilityPaper.setOutstandingCashMargin(calculatedFacilityPaperDTO.getOutstandingCashMargin());
            facilityPaper.setProposedCashMargin(calculatedFacilityPaperDTO.getProposedCashMargin());
            facilityPaper.setGroupOutstandingCashMargin(calculatedFacilityPaperDTO.getGroupOutstandingCashMargin());
            facilityPaper.setGroupProposedCashMargin(calculatedFacilityPaperDTO.getGroupProposedCashMargin());

            facilityPaperDao.saveAndFlush(facilityPaper);

        }

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();

        FacilityLoadOptionDTO facilityLoadOptionDTO = new FacilityLoadOptionDTO();
        facilityLoadOptionDTO.loadSecurities();

        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOptionDTO);


//        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
//        LOG.info("END : Facility Paper copy upc section data detail: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : Facility Paper copy upc section data, Facilities, securities and FP documents detail: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditUPCSectionData(UpcTemplateAddRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException, JsonProcessingException {

        LOG.info("START : Facility Paper update upc section data detail: {} : {}", updateDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(updateDTO.getFacilityPaperID());
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        if (updateDTO.getUpcTemplateID() != null && (updateDTO.getAddedUpcSectionDTOs() == null || updateDTO.getAddedUpcSectionDTOs().isEmpty())) {
            UpcTemplate upcTemplate = this.upcTemplateDao.getOne(updateDTO.getUpcTemplateID());
            if (upcTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
                facilityPaper.setUpcTemplate(upcTemplate);
                if (facilityPaper.getApplicationFormID() != null) {
//                    UPC Section with a UPC Template has Only One Topic Mapped :
//                    Here We do : If the Facility paper Generated from Application Form Then Add LPS topics as UPC Sections Content as Default content
                    ApplicationFormTopicRQ applicationFormTopicRQ = new ApplicationFormTopicRQ();
                    applicationFormTopicRQ.setApplicationFormID(updateDTO.getApplicationFormID());
                    applicationFormTopicRQ.getPageList().add(DomainConstants.ApplicationFormTopicPage.EXECUTIVE_SUMMARY.toString());
                    List<AFTopicDataDTO> afTopicDataDTOS = applicationFormJdbcDao.getApplicationFormTopics(applicationFormTopicRQ);
                    List<AFTopicUpcSection> afTopicUpcSections = new ArrayList<>();

                    for (AFTopicDataDTO afTopicDataDTO : afTopicDataDTOS) {
                        List<AFTopicUpcSection> afTopicUpcSectionList = afTopicUpcSectionDao.findAllByUpcTemplateIDAndTopic_TopicIDAndStatus(upcTemplate.getUpcTemplateID(), afTopicDataDTO.getTopicID(), AppsConstants.Status.ACT);
                        if (afTopicUpcSectionList != null) {
                            afTopicUpcSections.addAll(afTopicUpcSectionList);
                        }
                    }

                    for (UpcTemplateData upcTemplateData : facilityPaper.getUpcTemplate().getUpcTemplateDataSet()) {
                        FPUpcSectionData fpUpcSectionData = new FPUpcSectionData();
                        fpUpcSectionData.setCreatedBy(credentialsDTO.getUserName());
                        fpUpcSectionData.setCreatedDate(date);
                        if (!afTopicUpcSections.isEmpty()) {
                            AFTopicUpcSection afTopicUpcSection = afTopicUpcSections.stream().filter(topicUpcSection -> topicUpcSection.getUpcSectionID().equals(upcTemplateData.getUpcSection().getUpcSectionID())).findFirst().orElse(null);
                            if (afTopicUpcSection != null) {
                                AFTopicDataDTO afTopicDataDTO = afTopicDataDTOS.stream().filter(afTopicData -> afTopicData.getTopicID().equals(afTopicUpcSection.getTopic().getTopicID())).findFirst().orElse(null);
                                if (afTopicDataDTO != null) {

                                    fpUpcSectionData.setData(afTopicDataDTO.getTopicData());
                                }
                            }
                        }
                        LOG.info("student test :{}", fpUpcSectionData);
                        fpUpcSectionData.setParentSectionID(upcTemplateData.getParentSectionID());
                        fpUpcSectionData.setDisplayOrder(upcTemplateData.getDisplayOrder());
                        fpUpcSectionData.setSectionLevel(upcTemplateData.getSectionLevel());
                        fpUpcSectionData.setUpcSection(upcTemplateData.getUpcSection());
                        fpUpcSectionData.setStatus(AppsConstants.Status.ACT);

                        facilityPaper.addFpUpcSectionData(fpUpcSectionData);
                    }
                }

            } else {
                LOG.error("ERROR: Cannot applied not approved template, {}", updateDTO);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_APPLY_NOT_APPROVED_TEMPLATE);
            }
        }

        for (FPUpcSectionDataDTO upcSectionDataDTO : updateDTO.getAddedUpcSectionDTOs()) {
            boolean isNewOtherBankDetail = upcSectionDataDTO.getFpUpcSectionDataID() == null;
            FPUpcSectionData fpUpcSectionData = null;
            FPUpcSectionDataDTO updateFPUpcSectionDataDTO = null;
            FPUpcSectionDataDTO previousFPUpcSectionDataDTO = null;
            if (isNewOtherBankDetail) {
                fpUpcSectionData = new FPUpcSectionData();
                fpUpcSectionData.setCreatedBy(credentialsDTO.getUserName());
                fpUpcSectionData.setCreatedDate(date);
                if (upcSectionDataDTO.getData() != null) {
                    fpUpcSectionData.setModifiedBy(credentialsDTO.getUserName());
                    fpUpcSectionData.setModifiedUserDisplayName(upcSectionDataDTO.getModifiedUserDisplayName());
                    fpUpcSectionData.setModifiedDate(date);
                }
                facilityPaper.addFpUpcSectionData(fpUpcSectionData);
            } else {
                fpUpcSectionData = facilityPaper.getFPUpcSectionDataByID(upcSectionDataDTO.getFpUpcSectionDataID());
                previousFPUpcSectionDataDTO = new FPUpcSectionDataDTO(fpUpcSectionData);
                if (upcSectionDataDTO.getData() != null
                        && (StringUtils.isNotEmpty(fpUpcSectionData.getData()) && !upcSectionDataDTO.isSameDataContent(fpUpcSectionData))
                        || (fpUpcSectionData.getData() == null && StringUtils.isNotEmpty(upcSectionDataDTO.getData()))) {
                    fpUpcSectionData.setModifiedBy(credentialsDTO.getUserName());
                    fpUpcSectionData.setModifiedUserDisplayName(upcSectionDataDTO.getModifiedUserDisplayName());
                    fpUpcSectionData.setModifiedDate(date);
                }
            }
            fpUpcSectionData.setData(upcSectionDataDTO.getData());
            fpUpcSectionData.setParentSectionID(upcSectionDataDTO.getParentSectionID());
            fpUpcSectionData.setDisplayOrder(upcSectionDataDTO.getDisplayOrder());
            fpUpcSectionData.setSectionLevel(upcSectionDataDTO.getSectionLevel());
            fpUpcSectionData.setUpcSection(this.upcSectionDao.getOne(upcSectionDataDTO.getUpcSectionID()));
            fpUpcSectionData.setStatus(upcSectionDataDTO.getStatus());
            fpUpcSectionData.setComment(upcSectionDataDTO.getComment());

            updateFPUpcSectionDataDTO = new FPUpcSectionDataDTO(fpUpcSectionData);

            //audit
//            WebAuditDTO webAuditDTO = WebAuditUtils.constructFPUpcSectionDataAudit(updateFPUpcSectionDataDTO, previousFPUpcSectionDataDTO, credentialsDTO, date, isNewOtherBankDetail, webAuditJDBCDao);
//            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);
//            facilityPaperJdbcDao.saveFPUpcSectionDataHistory(updateFPUpcSectionDataDTO,credentialsDTO);
        }

        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : Facility Paper update  upc section data detail: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getFPaperRefCode() throws AppsException {
        String ref = null;
        synchronized (guard) {
            ref = fPaperRefProc.executeFunction();
        }
        return ref;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<FacilityPaperDTO> getPagedFacilityPaperDTO(FacilityPaperSearchRQ facilityPaperSearchRQ) {
        return facilityPaperJdbcDao.getPagedFacilityPaperDTOForSearch(facilityPaperSearchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<FacilityPaperDTO> getSearchedFacilityPaperDTO(SearchFacilityPaperRQ searchFacilityPaperRQ) throws AppsException {
        return facilityPaperJdbcDao.getSearchedFacilityPaperDTOForSearch(searchFacilityPaperRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<FacilityPaperDTO> getPagedMyFacilityPaper(FacilityPaperSearchRQ facilityPaperSearchRQ) {
        if (facilityPaperSearchRQ.isInboxRequest()) {
            return facilityPaperJdbcDao.getPagedInbox(facilityPaperSearchRQ);
        } else if (facilityPaperSearchRQ.isInProgressRequest()) {
            return facilityPaperJdbcDao.getPagedInProgress(facilityPaperSearchRQ);
        } else if (facilityPaperSearchRQ.isReturnRequest()) {
            return facilityPaperJdbcDao.getPagedCancelled(facilityPaperSearchRQ);
        }

        return facilityPaperJdbcDao.getPagedMyFacilityPaperDTO(facilityPaperSearchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<FacilityPaperDTO> getAgentPagedMyFacilityPaper(FacilityPaperSearchRQ facilityPaperSearchRQ) {

        if (facilityPaperSearchRQ.isInboxRequest()) {
            return agentFacilityPaperJdbcDao.getPagedInbox(facilityPaperSearchRQ);
        } else if (facilityPaperSearchRQ.isInProgressRequest()) {
            return agentFacilityPaperJdbcDao.getPagedInProgress(facilityPaperSearchRQ);
        } else if (facilityPaperSearchRQ.isReturnRequest()) {
            return agentFacilityPaperJdbcDao.getPagedCancelled(facilityPaperSearchRQ);
        }

        return facilityPaperJdbcDao.getPagedMyFacilityPaperDTO(facilityPaperSearchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DashboardCountDTO getDashboardCount(DashboardCountSearchRQ searchRQ) {
        DashboardCountDTO dashboardCounts = facilityPaperJdbcDao.getDashboardCount(searchRQ);

        Integer inboxCount = this.facilityPaperJdbcDao.getInboxCount(searchRQ);
        Integer inProgressCount = this.facilityPaperJdbcDao.getInProgressCount(searchRQ);

        dashboardCounts.setDraftFacilityPaper(inboxCount);
        dashboardCounts.setInProgressFacilityPaper(inProgressCount);

        return dashboardCounts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DashboardCountDTO getAgentDashboardCount(DashboardCountSearchRQ searchRQ) {
        DashboardCountDTO dashboardCounts = agentFacilityPaperJdbcDao.getDashboardCount(searchRQ);

        Integer inboxCount = this.agentFacilityPaperJdbcDao.getInboxCount(searchRQ);
        Integer inProgressCount = this.agentFacilityPaperJdbcDao.getInProgressCount(searchRQ);

        dashboardCounts.setDraftFacilityPaper(inboxCount);
        dashboardCounts.setInProgressFacilityPaper(inProgressCount);

        return dashboardCounts;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperRemoveRS removeFacilityPaper(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Remove facility paper: {} by: {}", facilityPaperID, credentialsDTO.getUserName());
        FacilityPaperRemoveRS facilityPaperRemoveRS = new FacilityPaperRemoveRS();
        FacilityPaperDTO previousFacilityPaperDTO = null;
        Date date = new Date();

        FacilityPaper facilityPaper = facilityPaperDao.getOne(facilityPaperID);
        previousFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);

        if (facilityPaper.getFacilityPaperID() == null) {
            facilityPaperRemoveRS.setMessage("ERROR can't find FacilityPaper id: " + facilityPaperID + "");
            LOG.info("ERROR: Remove facility paper fail: {} by: {}", facilityPaperID, credentialsDTO.getUserName());
        } else {
            facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.REMOVED);
            facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);

            //Audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityPaperAudit(new FacilityPaperDTO(facilityPaper), previousFacilityPaperDTO, credentialsDTO, date, false, webAuditJDBCDao);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

            if (facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.REMOVED) {
                facilityPaperRemoveRS.setMessage("Successfully removed facility paper id: " + facilityPaperID + "");
                facilityPaperRemoveRS.setSuccessfullyRemoved(true);
                LOG.info("END: Remove facility paper: {} by: {}", facilityPaperID, credentialsDTO.getUserName());
            } else {
                facilityPaperRemoveRS.setMessage("ERROR removed facility paper id:" + facilityPaperID + "");
                LOG.info("ERROR: Remove facility paper fail: {} by: {}", facilityPaperID, credentialsDTO.getUserName());
            }
        }
        return facilityPaperRemoveRS;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO updateFacilityPaper(FacilityPaperUpdateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException, IOException, SAXException {
        LOG.info("START: Update facility paper status and workflow routing {}, by {}", updateDTO, credentialsDTO.getUserName());

        FacilityPaperDTO previousFacilityPaperDTO = this.getFacilityPaperDTOByID(updateDTO.getFacilityPaperID(), credentialsDTO);

        FacilityPaperModificationContext context = new FacilityPaperModificationContext();
        context.setBranchLoadResponseListDTO(integrationService.getBranchList(credentialsDTO));
        context.setFacilityPaperUpdateDto(updateDTO);
        context.setCredentialsDto(credentialsDTO);
        context.setDate(new Date());
        fpStatusAndWFModifier.execute(context);
        FacilityPaper facilityPaper = context.getFacilityPaper();
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        //loadOptionDTO.loadUpcSectionData();
        loadOptionDTO.loadCustomerRelatedDetails();
        //loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCreditRiskComments();
        loadOptionDTO.loadApprovedFacilityDocuments();
        loadOptionDTO.loadShareHolderDetail();

        FacilityLoadOptionDTO facilityLoadOption = new FacilityLoadOptionDTO();
        facilityLoadOption.loadAllData();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOption);

        FacilityPaperDTO updatedFacilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);

        LOG.info("START: check bcc paper");
        //send bcc email
        if (updateDTO.getCurrentAssignUser() != null && updateDTO.getCurrentAssignUser().equals("BCC")
                && updatedFacilityPaperDTO.getCurrentAssignUser() != null && updatedFacilityPaperDTO.getCurrentAssignUser().equals("BCC")) {

            LOG.info("START: bcc paper");

            String subject = "Facility Paper Document - BCC Paper Ref No : " + facilityPaper.getFpRefNumber() + " (" + facilityPaper.getCurrentFacilityPaperStatus() + ")";

            //get email request body
            FacilityPaperStatusTransitionRQ emailRQ = getEmailRequestBody(updateDTO.getFacilityPaperID(), credentialsDTO);

            //set last comment from BCC
            if (!updateDTO.getBccActionComment().isEmpty() && !updateDTO.getCommentUserDisplayName().isEmpty()) {
                emailRQ.setLastComment(updateDTO.getBccActionComment());
                emailRQ.setLastCommentedUser(updateDTO.getCommentUserDisplayName());
            }

            //send email
            sendCommitteeEmail(updateDTO.getBccAddresses(), updateDTO.getAttachments(), subject, "forward_full_paper", emailRQ);
            LOG.info("END: bcc paper");
        }

        //if status approved, send credit admin notification
        if (updateDTO.getIsReviewPaper().equals(AppsConstants.YesNo.Y)) {
            sendCreditAdminEmail(updateDTO.getFacilityPaperID(), previousFacilityPaperDTO.getUpcTemplateID(), credentialsDTO);
        }


        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityPaperAudit(new FacilityPaperDTO(facilityPaper), previousFacilityPaperDTO, credentialsDTO, new Date(), false, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper);

        if (updateDTO.getFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.IN_PROGRESS) ||
                updateDTO.getFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.APPROVED) ||
                updateDTO.getFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.CANCEL)) {


            facilityPaperJdbcDao.updateStatusUPCSectionDataHistory(facilityPaperDTO.getFacilityPaperID());

        }

        leadService.addLeadDetailsToCrmRequest(facilityPaper.getLeadID());

        //update documentation status DRAFT to SUBMITTED by facility paper
        if (updateDTO.getIsChangeDocumentStatus().equals(AppsConstants.YesNo.Y)) {
            securityDocumentService.updateDocumentStatusByFacilityPaper(updateDTO.getFacilityPaperID(), credentialsDTO);
        }

        if(updateDTO.getFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.APPROVED)){
            LOG.info("START: Facility paper approved, check whether need to trigger turnover calculation for customers linked with the facility paper: {}", updateDTO.getFacilityPaperID());
            List<CASCustomerDTO> casCustomerDTOList = updatedFacilityPaperDTO.getCasCustomerDTOList();

            for(CASCustomerDTO casCustomerDTO : casCustomerDTOList){
                SmeCustomerTurnoverRqDTO request = new SmeCustomerTurnoverRqDTO();
                request.setCustId(casCustomerDTO.getCustomerFinancialID());
                smeService.isFacilityPaperApproved(casCustomerDTO.getCustomerID(), updatedFacilityPaperDTO.getFacilityPaperID(), request, credentialsDTO);
            }
        }

        LOG.info("END: Update facility paper status and workflow routing {}, by {}", updateDTO, credentialsDTO.getUserName());
        return updatedFacilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO deactivateFpFacilitySupportingDoc(FPDocumentDTO fpDocumentDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the Fp supporting document: {} by: {}", fpDocumentDTO, credentialsDTO.getUserName());

        FacilityPaper facilityPaper = facilityPaperDao.getOne(fpDocumentDTO.getFacilityPaperID());
        FPDocumentDTO previousFPDoc = new FPDocumentDTO(fpDocumentDao.getOne(fpDocumentDTO.getFpOtherBankFacilityID()));
        FPDocument fpDocument = null;

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();
        loadOptionDTO.loadCustomer();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCustomerCribDetail();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadCustomerOtherBankFacility();
        loadOptionDTO.loadCustomerDocument();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadShareHolderDetail();
        if (fpDocumentDTO.getSupportingDocID() != null) {
            fpDocument = fpDocumentDao.getOne(fpDocumentDTO.getFpOtherBankFacilityID());
            fpDocument.setStatus(AppsConstants.Status.INA);
            fpDocument = fpDocumentDao.saveAndFlush(fpDocument);

            //audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructFPDocumentAudit(new FPDocumentDTO(fpDocument), previousFPDoc, credentialsDTO, new Date(), false, webAuditJDBCDao);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);
        }

        LOG.info("END: Deactivate the Fp supporting document: {} by: {}", fpDocumentDTO, credentialsDTO.getUserName());
        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO deactivateFpCribSupportingDoc(CASCustomerCribDetailDTO cribDetailDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the crib supporting document: {} by: {}", cribDetailDTO, credentialsDTO.getUserName());
        FacilityPaper facilityPaper = facilityPaperDao.getOne(cribDetailDTO.getFacilityPaperID());

        if (cribDetailDTO.getCasCustomerCribDetailsID() != null) {
            CASCustomer CASCustomer = facilityPaper.getCasCustomerByID(cribDetailDTO.getCasCustomerID());
            CASCustomerCribDetail CasCustomerCribDetail = CASCustomer.getCasCustomerCribDetailByID(cribDetailDTO.getCasCustomerCribDetailsID());
            CasCustomerCribDetail.setStatus(AppsConstants.Status.INA);
            CasCustomerCribDetail.setModifiedBy(credentialsDTO.getUserName());
            CasCustomerCribDetail.setModifiedDate(new Date());
            facilityPaperDao.saveAndFlush(facilityPaper);
            //audit

        }
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadCustomerCribDetail();
        loadOptionDTO.loadCribDetails();

        LOG.info("END: Deactivate the crib supporting document: {} by: {}", cribDetailDTO, credentialsDTO.getUserName());
        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<RemarkDTO> getRemarkDtoList(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {

        List<RemarkDTO> mergedList = new ArrayList<>();

//        List<RemarkDTO> listOne = facilityPaperJdbcDao.getFacilityPaperRemarksFromStatusHistory(facilityPaperID);
        List<RemarkDTO> listTwo = facilityPaperJdbcDao.getFacilityPaperRemarksFromFpComment(facilityPaperID);
//        List<RemarkDTO> listThree = facilityPaperJdbcDao.getFacilityPaperRemarksFromFpComment(facilityPaperID);

//        for (RemarkDTO remarkDTO : listOne) {
//            mergedList.add(remarkDTO);
//        }
        for (RemarkDTO remarkDTO : listTwo) {
            mergedList.add(remarkDTO);
        }
//        for (RemarkDTO remarkDTO : listThree) {
//            mergedList.add(remarkDTO);
//        }
        if (!mergedList.isEmpty()) {
            Collections.sort(mergedList);
//            Collections.sort(mergedList, new Comparator<RemarkDTO>() {
//                @Override
//                public int compare(RemarkDTO o1, RemarkDTO o2) {
//                    return o2.getDate().compareTo(o1.getDate());
//                }
//            });
        }
        return mergedList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UserDaDTO getUserDAByLoggedInUser(String userName, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: get UseDa by logged user: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        UserDaDTO userDaDTO = facilityPaperJdbcDao.getUserDAByLoggedInUser(userName);

        LOG.info("END: get UseDa by logged user: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        return userDaDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<UpcTemplateDTO> getActiveApprovedUpcTemplateDtoList() throws AppsException {
        return facilityPaperJdbcDao.getActiveApprovedUpcTemplateDtoList();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public Long getAssignedFacilityPaperCount(FacilityPaperSearchRQ facilityPaperSearchRQ) throws AppsException {
        return this.facilityPaperJdbcDao.getAssignedFacilityPaperCount(facilityPaperSearchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public FacilityPaperDTO removeUpcSectionData(UpcTemplateAddRQ upcTemplateAddRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: remove upc sectio data: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        FacilityPaper facilityPaper = facilityPaperDao.getOne(upcTemplateAddRQ.getFacilityPaperID());

        if (facilityPaper.getUpcTemplate() != null) {
            facilityPaper.setUpcTemplate(null);
        } else {
            facilityPaper.setUpcTemplate(null);
        }

        for (FPUpcSectionDataDTO fpUpcSectionDataDTO : upcTemplateAddRQ.getAddedUpcSectionDTOs()) {
            FPUpcSectionData fpUpcSectionData = null;
            Date date = new Date();
            boolean oldUpcSection = fpUpcSectionDataDTO.getFpUpcSectionDataID() != null;
            if (oldUpcSection) {
                fpUpcSectionData = facilityPaper.getFPUpcSectionDataByID(fpUpcSectionDataDTO.getFpUpcSectionDataID());
                fpUpcSectionData.setModifiedBy(credentialsDTO.getUserName());
                fpUpcSectionData.setModifiedDate(date);
                fpUpcSectionData.setStatus(AppsConstants.Status.INA);
            }
        }

        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();
        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : remove upc sectio data: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO saveUpdateFacilitySecurity(FacilitySecurityDTO facilitySecurityDTO, CredentialsDTO credentialsDTO) throws SAXException, AppsException, IOException {

        LOG.info("START: save or update Facility security: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        FacilitySecurity facilitySecurity;

        FacilitySecurityBuilder facilitySecurityBuilder = new FacilitySecurityBuilder(facilitySecurityDTO, credentialsDTO);
        facilitySecurityBuilder.setFacilityDao(facilityDao);
        facilitySecurityBuilder.setFacilitySecurityDao(facilitySecurityDao);
        facilitySecurityBuilder.setFacilityFacilitySecurityDao(facilityFacilitySecurityDao);
        facilitySecurityBuilder.setFacilityFacilitySecurityJdbcDao(facilityFacilitySecurityJdbcDao);

        facilitySecurity = facilitySecurityBuilder
                .createFacilitySecurity()
                .getFacilitySecurity();
        facilitySecurityDao.saveAndFlush(facilitySecurity);

        FacilityPaper facilityPaper = facilityPaperDao.getOne(facilitySecurityDTO.getFacilityPaperID());

        FPLoadOptionDTO loadOption = new FPLoadOptionDTO();
        loadOption.loadFacilities();
        loadOption.loadCustomerRelatedDetails();
        loadOption.loadDocument();
        loadOption.loadDirectorDetail();
        loadOption.loadComment();
        loadOption.loadReviewerComment();
        loadOption.loadCompanyROA();
        loadOption.loadCreditRiskComments();
        loadOption.loadWalletShares();
        loadOption.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOption.getFacilityLoadOptionDTO().loadAllData();

        FacilityPaperDTO updatedFP = new FacilityPaperDTO(facilityPaper, loadOption);

        for (FacilityDTO facilityDTO : updatedFP.getFacilityDTOList()) {

            FusTraceDTO fpUPCTemplateComparisonRQ = new FusTraceDTO();

            fpUPCTemplateComparisonRQ.setMainKey(facilityDTO.getFacilityID());
            fpUPCTemplateComparisonRQ.setSubKey(0);
            fpUPCTemplateComparisonRQ.setFlag(AppsConstants.FusTraceFlage.ALL.getFlag());
            fpUPCTemplateComparisonRQ.setStatus("ACT");
            List<UPCTemplateCommentHistoryDTO> fusTraceList = facilityPaperJdbcDao.getFusTraceDataRepository(fpUPCTemplateComparisonRQ, credentialsDTO);
            facilityDTO.setFusTraceList(fusTraceList);
        }

        return updatedFP;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<FPReviewSummaryDTO> getPagedFacilityPaperReviewSummary(FPReviewSummarySearchRQ searchRQ) throws AppsException {
        LOG.info("START : Get Paged Facility Paper update Reviewer Summery : {}", searchRQ);
        Set<String> otherUpmAccessLevels = this.getAllowApprovedUPMGroupsForLoginUser(searchRQ);
        LOG.info("END : Other Access Levels : {}", otherUpmAccessLevels);
        searchRQ.setOtherUpmAccessLevels(new ArrayList<>(otherUpmAccessLevels));
        LOG.info("END : Get Paged Facility Paper update Reviewer Summery : {}", searchRQ);

        return facilityPaperJdbcDao.getPagedFacilityPaperReviewSummary(searchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<FacilityReviewDTO> getPagedFacilitiesForReview(FacilityReviewSearchRQ searchRQ) {
        return facilityPaperJdbcDao.getPagedFacilitiesForReview(searchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Set<String> getAllowApprovedUPMGroupsForLoginUser(FPReviewSummarySearchRQ searchRQ) {
        LOG.info("START : Get UPM Groups (Access Levels) : {}", searchRQ);
        return masterDataJdbcDao.getAllowApprovedUPMGroupsForLoginUser(searchRQ);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO approveOrRejectFacilityPaper(FPReviewerCommentDTO fpReviewerCommentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update Reviewer Comment: {} : {}", fpReviewerCommentDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(fpReviewerCommentDTO.getFacilityPaperID());
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        switch (fpReviewerCommentDTO.getPaperReviewStatus()) {
            case ACTION_REQUIRED:
            case APPROVED:
            case REJECTED:
                facilityPaper.setReviewUserDisplayName(fpReviewerCommentDTO.getCreatedUserDisplayName());
                facilityPaper.setReviewUserID(credentialsDTO.getUserID());
                facilityPaper.setReviewUserName(credentialsDTO.getUserName());
                facilityPaper.setPaperReviewStatus(fpReviewerCommentDTO.getPaperReviewStatus());
                break;
            case REPLIED:
                facilityPaper.setPaperReviewStatus(fpReviewerCommentDTO.getPaperReviewStatus());
            default:
        }

        saveOrUpdateReviewComment(fpReviewerCommentDTO, credentialsDTO);
       /* FPReviewerComment fpReviewerComment = fpReviewerCommentDao.getOne(fpReviewerCommentDTO.getFpReviewerCommentID());
        fpReviewerComment.setComment(fpReviewerCommentDTO.getComment());
        fpReviewerComment.setPaperReviewStatus(fpReviewerCommentDTO.getPaperReviewStatus());
        fpReviewerCommentDao.saveAndFlush(fpReviewerComment);*/

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadReviewerComment();

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        LOG.info("END :  Facility Paper update Reviewer Comment: {} : {}", fpReviewerCommentDTO, credentialsDTO.getUserID());

        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO draftFacilityPaperByLead(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LeadStatusUpdateRQ leadStatusUpdateRQ = new LeadStatusUpdateRQ();
        leadStatusUpdateRQ.setLeadID(facilityPaperDTO.getLeadID());
        leadStatusUpdateRQ.setLeadStatus(DomainConstants.LeadStatus.PAPER_CREATED);
        leadStatusUpdateRQ.setAction("Create Paper");
        leadStatusUpdateRQ.setAssignUserID(credentialsDTO.getUserName());
        leadStatusUpdateRQ.setActionedByDisplayName(facilityPaperDTO.getAssignUserDisplayName());
        leadService.updateLeadDTOStatus(leadStatusUpdateRQ, credentialsDTO);

        return this.draftAgentFacilityPaper(facilityPaperDTO, credentialsDTO);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public BooleanValueDTO isAbleToReturnFacilityPaperToAgent(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws Exception {
        LOG.info("START: Is able to return facility paper to the agent: {} by: {}", facilityPaperDTO, credentialsDTO.getUserName());
        return facilityPaperJdbcDao.isAbleToReturnFacilityPaperToAgent(facilityPaperDTO);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<FPStatusHistoryDTO> getFPDirectReturnUsersList(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Facility Paper Direct Return Users List : {} by: {}", facilityPaperDTO, credentialsDTO.getUserName());

        return facilityPaperJdbcDao.getFPDirectReturnUsersList(facilityPaperDTO).stream().filter(element -> {
            return !element.getAssignUser().equals(credentialsDTO.getUserName());
        }).distinct().collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Set<String> getAllUnderAllowApprovedUPMGroupsForLoginUser(FacilityPaperSearchRQ searchRQ) {
        LOG.info("START : Get All Lower UPM Groups (Access Levels) : {}", searchRQ);
        return masterDataJdbcDao.getAllUnderAllowApprovedUPMGroupsForLoginUser(searchRQ.getLoginUpmAccessLevel());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public FacilityPaperDTO getPagedFacilityPaperForTransfer(FacilityPaperSearchRQ facilityPaperSearchRQ) throws AppsException {

        LOG.info("START : Get Paged Facility Paper for Transfer : {}", facilityPaperSearchRQ);
        Set<String> upmAccessLevels = this.getAllUnderAllowApprovedUPMGroupsForLoginUser(facilityPaperSearchRQ);
        LOG.info("INFO : Get All Lower UPM Groups (Access Levels) : {}", upmAccessLevels);

        if (systemParameterService.isSameLevelTransferEnabled()) {
            upmAccessLevels.add(facilityPaperSearchRQ.getLoginUpmAccessLevel());
            LOG.info("INFO : SAME USER UPM LEVEL TRANSFER_ENABLED : Logged in user Upm Level : {} ", facilityPaperSearchRQ.getLoginUpmAccessLevel());
        }
        LOG.info("INFO : Searching All UPM Groups (Access Levels) : {}", upmAccessLevels);

        FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(facilityPaperSearchRQ.getFpRefNumber());
        if (facilityPaper == null) {
            LOG.warn("WARN : No Facility Paper Found for {}", facilityPaperSearchRQ.getFpRefNumber());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_PAPER_NOT_FOUND);
        }

        if (facilityPaper.getCurrentFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.APPROVED)
                || facilityPaper.getCurrentFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.REJECTED)

        ) {
            LOG.warn("WARN : Invalid Facility Paper status Found {}", facilityPaper.getCurrentFacilityPaperStatus());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_PAPER_TRANSFER_INVALID_STATUS);
        }

        if (facilityPaperSearchRQ.getLoginUpmAccessLevel().equals(String.valueOf(WorkGroupUtil.getSUPER_USER()))) {
            LOG.info("INFO : Facility Paper {} Is transferred by {}", facilityPaperSearchRQ.getFpRefNumber(), facilityPaperSearchRQ.getLoginUpmAccessLevel());
        } else if (facilityPaper.getAssignUserUpmGroupCode() != null && upmAccessLevels.stream().noneMatch(facilityPaper.getAssignUserUpmGroupCode()::contains)) {
            LOG.warn("WARN : Facility Paper {} is not allowed to transfer according to UPM Group {}", facilityPaperSearchRQ.getFpRefNumber(), facilityPaperSearchRQ.getLoginUpmAccessLevel());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_PAPER_TRANSFER_INVALID_UPM_GROUP);
        }

        String branchLevelUPMGroupCodes = systemParameterService.getBranchLevelUPMGroupCodeValues();
        String[] upmGroups = branchLevelUPMGroupCodes.trim().split(",", 5);
        boolean isBranchLevelPaperTransfer = Arrays.stream(upmGroups).anyMatch(facilityPaperSearchRQ.getLoginUpmAccessLevel()::contains);

        if (isBranchLevelPaperTransfer) {
            LOG.info("INFO : Manager level and bellow paper transfer : {}", facilityPaperSearchRQ);

            if (facilityPaper.getCurrentAssignUserDivCode() != null && !facilityPaperSearchRQ.getLoggedInUserBranchCode().equals(facilityPaper.getCurrentAssignUserDivCode())) {
                LOG.warn("WARN : Facility Paper {} is not allowed to transfer according to Div Code {}", facilityPaperSearchRQ.getFpRefNumber(), facilityPaperSearchRQ.getLoggedInUserBranchCode());
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_PAPER_TRANSFER_INVALID_DIV_CODE);
            }
            if (facilityPaper.getAssignDepartmentCode() != null && !facilityPaperSearchRQ.getLoggedInUserBranchCode().equals(facilityPaper.getAssignDepartmentCode())) {
                LOG.warn("WARN : Facility Paper {} is not allowed to transfer according to Div Code {}", facilityPaperSearchRQ.getFpRefNumber(), facilityPaperSearchRQ.getLoggedInUserBranchCode());
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_PAPER_TRANSFER_INVALID_DIV_CODE);
            }
        }

        LOG.info("END : Get Paged Facility Paper for Transfer Search RQ : {}", facilityPaperSearchRQ);
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCustomer();
        fpLoadOptionDTO.loadFacilities();
        return new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO updateFacilityPaperOutstandingDate(FacilityPaperUpdateDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Facility Paper update Outstanding Date: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(facilityPaperDTO.getFacilityPaperID());
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);
        if (facilityPaperDTO.getOutstandingDateStr() != null) {
            facilityPaper.setOutstandingDate(CalendarUtil.getDefaultParsedDateOnly(facilityPaperDTO.getOutstandingDateStr()));
        }

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        LOG.info("END : Facility Paper update Outstanding Date : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());

        FPLoadOptionDTO loadOption = new FPLoadOptionDTO();
        loadOption.loadFacilities();
        loadOption.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOption.getFacilityLoadOptionDTO().loadAllData();
        return new FacilityPaperDTO(facilityPaper, loadOption);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<LeadFacilityPaperStatusDetailDTO> getFacilityPaperHistory(FacilityPaperDTO searchRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Facility Paper Status history :{} by :{}", searchRQ, credentialsDTO.getUserName());
        FacilityPaper facilityPaper = facilityPaperDao.getOne(searchRQ.getFacilityPaperID());
        return facilityPaperJdbcDao.getFacilityPaperHistory(facilityPaper);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<FPPreviousUPCTemplateRS> getPagedFacilityPaperHistoryWithUPCTemplateDetails(FPPreviousUPCTemplateRQ fpPreviousUPCTemplateRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Facility Paper Get Previous UPC Templates :{} by :{}", fpPreviousUPCTemplateRQ, credentialsDTO.getUserName());
        return facilityPaperJdbcDao.getPagedFacilityPaperHistoryWithUPCTemplateDetails(fpPreviousUPCTemplateRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FacilityPaperDTO updateCasCustomerDTO(CASCustomerUpdateRQ casCustomerUpdateRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: update Cas Customer :{} by :{}", casCustomerUpdateRQ, credentialsDTO.getUserName());
        CasCustomerBuilder casCustomerBuilder = new CasCustomerBuilder(casCustomerUpdateRQ, credentialsDTO);
        casCustomerBuilder.setSearchCustomerRQ(casCustomerUpdateRQ);
        casCustomerBuilder.setIntegrationService(integrationService);
        casCustomerBuilder.setCustomerService(customerService);
        casCustomerBuilder.setCasCustomerDao(casCustomerDao);
        casCustomerBuilder.setCustomerDao(customerDao);
        CASCustomer casCustomer = casCustomerBuilder
                .searchCustomer()
                .initialize()
                .updateBuildCustomerBasicDetail()
                .updateAddress()
                .updateTelephone()
                .updateIdentification()
                .updateBankDetail()
                .getCasCustomer();

        FacilityPaper facilityPaper = facilityPaperDao.getOne(casCustomerUpdateRQ.getFacilityPaperID());
        FPLoadOptionDTO loadOption = new FPLoadOptionDTO();
        loadOption.loadCustomerRelatedDetails();
        loadOption.loadCribDetails();
        LOG.info("END: update Cas Customer :{} by :{}", casCustomerUpdateRQ, credentialsDTO.getUserName());
        return new FacilityPaperDTO(facilityPaper, loadOption);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<FPReviewerCommentDTO> getReviewCommentFromFpID(FPReviewerCommentSearchRQ searchRQ) {
        searchRQ.getPaperReviewStatusList().add(DomainConstants.PaperReviewStatus.ACTION_REQUIRED.toString());
        searchRQ.getPaperReviewStatusList().add(DomainConstants.PaperReviewStatus.APPROVED.toString());
        searchRQ.getPaperReviewStatusList().add(DomainConstants.PaperReviewStatus.REJECTED.toString());
        searchRQ.getPaperReviewStatusList().add(DomainConstants.PaperReviewStatus.REPLIED.toString());
        return facilityPaperJdbcDao.getPagedReviewComments(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FPReviewerCommentDTO getReviewCommentFromFPIDAndUpmID(FPReviewerCommentSearchRQ searchRQ) {
        Page<FPReviewerCommentDTO> reviewerComments = facilityPaperJdbcDao.getPagedReviewComments(searchRQ);
        ArrayList<FPReviewerCommentDTO> comments = new ArrayList<>(reviewerComments.getPageData());
        if (!comments.isEmpty() && comments.get(0).getPaperReviewStatus().equals(DomainConstants.PaperReviewStatus.SAVED)) {
            return comments.get(0);
        } else {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FPReviewerCommentDTO saveOrUpdateReviewComment(FPReviewerCommentDTO reviewerCommentDTO, CredentialsDTO credentialsDTO) {
        FPReviewerComment fpReviewerComment = null;
        Date date = new Date();

        if (reviewerCommentDTO.getFpReviewerCommentID() != null) {
            LOG.info("START: Update Reviewer Comment :{} by :{}", reviewerCommentDTO.getFpReviewerCommentID(), credentialsDTO.getUserName());

            fpReviewerComment = fpReviewerCommentDao.getOne(reviewerCommentDTO.getFpReviewerCommentID());
            fpReviewerComment.setComment(reviewerCommentDTO.getComment());
            fpReviewerComment.setPaperReviewStatus(reviewerCommentDTO.getPaperReviewStatus());
            fpReviewerComment.setModifiedUserName(credentialsDTO.getUserName());
            fpReviewerComment.setModifiedBy(credentialsDTO.getUserName());
            fpReviewerComment.setModifiedDate(date);
        } else {
            LOG.info("START:Save Reviewer Comment :{} by :{}", reviewerCommentDTO.getComment(), credentialsDTO.getUserName());

            FacilityPaper facilityPaper = facilityPaperDao.getOne(reviewerCommentDTO.getFacilityPaperID());

            fpReviewerComment = new FPReviewerComment();
            fpReviewerComment.setFacilityPaper(facilityPaper);
            fpReviewerComment.setStatus(reviewerCommentDTO.getStatus());
            fpReviewerComment.setCreatedDate(date);
            fpReviewerComment.setCreatedBy(credentialsDTO.getUserName());
            fpReviewerComment.setCreatedUserDisplayName(reviewerCommentDTO.getCreatedUserDisplayName());
            fpReviewerComment.setUpmID(reviewerCommentDTO.getUpmID());
            fpReviewerComment.setComment(reviewerCommentDTO.getComment());
            fpReviewerComment.setPaperReviewStatus(reviewerCommentDTO.getPaperReviewStatus());
            fpReviewerComment.setCreatedUserDivCode(reviewerCommentDTO.getCreatedUserDivCode());
            fpReviewerComment.setCreatedUserUpmCode(reviewerCommentDTO.getCreatedUserUpmCode());
        }
        fpReviewerComment = fpReviewerCommentDao.saveAndFlush(fpReviewerComment);
        LOG.info("END:Save or Update Reviewer Comment :{} by :{}", fpReviewerComment.getFpReviewerCommentID(), credentialsDTO.getUserName());
        return new FPReviewerCommentDTO(fpReviewerComment);

    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO uploadFPCustomerCribDetail(CASCustomerCribDetailUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload facility paper customer CRIB detail :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();

        FacilityPaper facilityPaper = this.facilityPaperDao.getOne(uploadRQ.getFacilityPaperID());
        CASCustomerCribDetail CASCustomerCribDetail = null;
        CASCustomer CASCustomer = facilityPaper.getCasCustomerByID(uploadRQ.getCasCustomerID());
        boolean isNewCribDetail = uploadRQ.getCasCustomerCribDetailsID() == null;
        if (isNewCribDetail) {
            CASCustomerCribDetail = new CASCustomerCribDetail();
            CASCustomerCribDetail.setCreatedBy(credentialsDTO.getUserName());
            CASCustomerCribDetail.setCreatedDate(date);
            CASCustomerCribDetail.setUploadedDivCode(uploadRQ.getUploadedDivCode());
            CASCustomerCribDetail.setUploadedUserDisplayName(uploadRQ.getUploadedUserDisplayName());
            CASCustomer.addCasCustomerCribDetail(CASCustomerCribDetail);
        } else {
            CASCustomerCribDetail = CASCustomer.getCasCustomerCribDetailByID(uploadRQ.getCasCustomerCribDetailsID());
            CASCustomerCribDetail.setModifiedBy(credentialsDTO.getUserName());
            CASCustomerCribDetail.setModifiedDate(date);
        }
        if (uploadRQ.getDocStorageDTO() != null) {
            uploadRQ.getDocStorageDTO().setDescription("FACILITY PAPER: " + facilityPaper.getFacilityPaperNumber() + ", CUSTOMER: " + CASCustomer.getCasCustomerID());
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
            CASCustomerCribDetail.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Facility paper customer CRIB data null: {}", uploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_CUSTOMER_DOCUMENT_NULL_SUPPORT_DOC);
        }

        CASCustomerCribDetail.setRemark(uploadRQ.getRemarks());
        if (StringUtils.isNotBlank(uploadRQ.getCribIssueDateStr())) {
            CASCustomerCribDetail.setCribIssueDate(CalendarUtil.getDefaultParsedDateOnly(uploadRQ.getCribIssueDateStr()));
        }
        CASCustomerCribDetail.setCribStatus(uploadRQ.getCribStatus());
        CASCustomerCribDetail.setStatus(uploadRQ.getStatus());
        CASCustomerCribDetail.setSupportingDoc(this.supportingDocDao.getOne(uploadRQ.getSupportingDocID()));
        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);

        //audit

        LOG.info("END: Upload facility paper customer CRIB detail :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadCreditRiskComments();
        loadOptionDTO.loadShareHolderDetail();
        loadOptionDTO.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOptionDTO.getFacilityLoadOptionDTO().loadAllData();

        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FacilityPaperDTO saveOrUpdateCribReport(CASCustomerCribReportDTO cribReportDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Save/Update Customer Crib Report :{} by :{}", cribReportDTO, credentialsDTO.getUserName());
        FacilityPaper facilityPaper = facilityPaperDao.getOne(cribReportDTO.getFacilityPaperID());
        CASCustomerCribReport customerCribReport = null;
        CASCustomer casCustomer = facilityPaper.getCasCustomerByID(cribReportDTO.getCasCustomerID());
        Date date = new Date();

        if (cribReportDTO.getCustomerCribReportID() != null) {
            LOG.info("START: Update Customer Crib Report :{} by :{}", cribReportDTO, credentialsDTO.getUserName());
            customerCribReport = casCustomer.getCasCustomerCribReportByID(cribReportDTO.getCustomerCribReportID());
            customerCribReport.setModifiedBy(credentialsDTO.getUserName());
            customerCribReport.setModifiedDate(date);
        } else {
            customerCribReport = new CASCustomerCribReport();
            customerCribReport.setCreatedBy(credentialsDTO.getUserName());
            customerCribReport.setCreatedDate(date);
            casCustomer.addCasCustomerCribReport(customerCribReport);
        }

        if (StringUtils.isNotEmpty(cribReportDTO.getCribDateStr())) {
            customerCribReport.setCribDate(CalendarUtil.getDefaultParsedDateOnly(cribReportDTO.getCribDateStr()));
        }
        customerCribReport.setCribStatus(cribReportDTO.getCribStatus());
        customerCribReport.setStatus(cribReportDTO.getStatus());
        customerCribReport.setIdentificationNo(cribReportDTO.getIdentificationNo());
        customerCribReport.setIdentificationType(cribReportDTO.getIdentificationType());
        customerCribReport.setRemark(cribReportDTO.getRemark());
        customerCribReport.setReportContent(cribReportDTO.getReportContent());
        customerCribReport.setSavedUserDisplayName(cribReportDTO.getSavedUserDisplayName());
        customerCribReport.setSavedUserDivCode(cribReportDTO.getSavedUserDivCode());
        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);

        LOG.info("END: Save/Update Customer Crib Report :{} by :{}", cribReportDTO, credentialsDTO.getUserName());
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCustomerCribDetail();
        fpLoadOptionDTO.loadCribDetails();
        return new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<CreditScheduleESBResponseDTO> getCreditCalculatedFacilitiesESBResponseStatus(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Credit Calculated Facilities ESB Response Status : {} by: {}", facilityPaperDTO, credentialsDTO.getUserName());
        return creditScheduleResponseJdbcDao.getCustomerFacilityPaperSummaryList(facilityPaperDTO.getFacilityPaperID());
    }

    public String getRiskDivCode() {
        String value = integrationService.getRiskDivCode();
        return value;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FPCreditRiskCommentHistoryDTO> getRiskCommentList(Integer facilityPaperId, CredentialsDTO credentialsDTO) throws AppsException {
        List<FPCreditRiskCommentHistoryDTO> commentList = new ArrayList<>();

        List<FPCreditRiskCommentHistoryDTO> listTwo = facilityPaperJdbcDao.getPastRiskOpinion(facilityPaperId);

        for (FPCreditRiskCommentHistoryDTO fpCreditRiskCommentHistoryDTO : listTwo) {
            if (fpCreditRiskCommentHistoryDTO.getCreatedUserName() != null) {
                commentList.add(fpCreditRiskCommentHistoryDTO);
            }
        }

        if (!commentList.isEmpty()) {
            Collections.sort(commentList);
        }

        return commentList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FPReviewCIFDetailsDTO> getCIFDetails(Integer facilityPaperId, CredentialsDTO credentialsDTO) throws AppsException {
        List<FPReviewCIFDetailsDTO> idList = new ArrayList<>();

        List<FPReviewCIFDetailsDTO> listTwo = facilityPaperJdbcDao.getCIDDetails(facilityPaperId);

        for (FPReviewCIFDetailsDTO fpReviewCIFDetailsDTO : listTwo) {
            idList.add(fpReviewCIFDetailsDTO);
        }

        return idList;
    }

    public FPCustomerEvaluation saveAndUpdateCIFId(FPCustomerEvaluation fpCustomerEvaluation, CredentialsDTO credentialsDTO) throws AppsException {

        fpCustomerEvaluation.setStatus(AppsConstants.EvaluationForm.ACTIVE);

        LOG.info("evaluation status {}", AppsConstants.EvaluationForm.valueOf("ACTIVE"));

        LOG.info("evaluation response {}", fpCustomerEvaluation.getId());

        return fpCustomerEvaluationDao.save(fpCustomerEvaluation);


    }


    @Transactional(readOnly = true)
    public CustomerEvaluationIdRequest findEvaluationById(Integer facilityPaperId, CredentialsDTO credentialsDTO) throws AppsException {
        return customerJdbcDao.getCustomerEvaluationInFullPaper(facilityPaperId);
    }

    @Transactional(readOnly = true)
    public List<CustomerEvaluationDTO> findEvaluationById2(Integer facilityPaperId, CredentialsDTO credentialsDTO) throws AppsException {


//        return customerJdbcDao.getCustomerEvaluationForFacilityPaper(facilityPaperId);

        List<CustomerEvaluationDTO> customerEvaluationAnswersDTOList = new ArrayList<>();

        List<CustomerEvaluationDTO> listTwo = customerJdbcDao.getCustomerEvaluationForFacilityPaper(facilityPaperId);

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
            }
        }

        for (CustomerEvaluationDTO customerEvaluationAnswersDTO : listTwo) {
            customerEvaluationAnswersDTOList.add(customerEvaluationAnswersDTO);
        }

        return listFnl;
    }


    public Integer deleteEvaluationForm(CustomerEvaluationDeleteRequestDTO customerEvaluationDeleteRequestDTO) throws AppsException {


        FPCustomerEvaluation fpCustomerEvaluation = fpCustomerEvaluationDao.findById(customerEvaluationDeleteRequestDTO.getId())
                .orElseThrow(() -> new AppsException("Scorecard with ID " + customerEvaluationDeleteRequestDTO.getId() + " does not exist"));

        fpCustomerEvaluation.setStatus(AppsConstants.EvaluationForm.DELETED);

        fpCustomerEvaluationDao.saveAndFlush(fpCustomerEvaluation);

        return fpCustomerEvaluation.getId();
    }


//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public ApplicationLevelCovenant saveOrUpdateApplicationCovenant(ApplicationCovenantReqDTO applicationCovenantReqDTO, CredentialsDTO credentialsDTO) throws AppsException {
//        ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO = applicationCovenantReqDTO.getCovenantDetails().get(0);
//        Date date = new Date();
//
//        ApplicationLevelCovenant applicationLevelCovenant;
//
//        // Check if it's an update or a save operation
//        if (applicationCovenantReqDTO.getApplicationCovenantId() != null) {
//            // You are updating an existing covenant
//            applicationLevelCovenant = applicationCovenantDao.findById(applicationCovenantReqDTO.getApplicationCovenantId()).orElseThrow(
//                    () -> new AppsException("Covenant with ID " + applicationCovenantReqDTO.getApplicationCovenantId() + " not found")
//            );
//            facilityCovenantFacilitiesDao.deleteByApplicationLevelCovenantApplicationCovenantId(applicationCovenantReqDTO.getApplicationCovenantId());
//            //applicationCovenantDao.deleteByApplicationCovenantId(applicationCovenantReqDTO.getApplicationCovenantId());
//            applicationLevelCovenant.setCreatedDate(date);
//            applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
//            applicationLevelCovenant.setCreatedUserDisplayName(applicationCovenantReqDTO.getCreatedUserDisplayName());
//        } else {
//            // You are creating a new covenant
//            applicationLevelCovenant = new ApplicationLevelCovenant();
//            applicationLevelCovenant.setCreatedDate(date);
//            //applicationLevelCovenant.setCreatedDate(applicationLevelCovenant.getCreatedDate());
//            applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
//            applicationLevelCovenant.setCreatedUserDisplayName(applicationCovenantReqDTO.getCreatedUserDisplayName());
//            applicationLevelCovenant.setStatus(AppsConstants.CovenantStatus.Active);
//        }
//
//
//
//        applicationLevelCovenant.setRequestUUID(applicationCovenantReqDTO.getRequestUUID());
//
//        Customer customer = customerDao.findByCustomerFinancialID(applicationCovenantReqDTO.getCustId());
//        if (customer == null) {
//            Customer newCustomer = new Customer();
//            newCustomer.setCustomerFinancialID(applicationCovenantReqDTO.getCustId());
//            customer = customerDao.save(newCustomer);
//        }
////        applicationLevelCovenant.setCustomer(customer);
//        applicationLevelCovenant.setCustomerFinacleID(customer.getCustomerFinancialID());
//
//        FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(applicationCovenantReqDTO.getCasReference());
//        if (facilityPaper == null) {
//            FacilityPaper newFacilityPaper = new FacilityPaper();
//            newFacilityPaper.setFpRefNumber(applicationCovenantReqDTO.getCasReference());
//            //newFacilityPaper.setFacilityPaperID(applicationCovenantReqDTO.getFacilityPaperID());
//            facilityPaper = facilityPaperDao.save(newFacilityPaper);
//        }
//        applicationLevelCovenant.setFacilityPaper(facilityPaper);
//
//        applicationLevelCovenant.setCovenant_Code(applicationCovenantDetailsDTO.getCovenant_Code());
//        applicationLevelCovenant.setCovenant_Description(applicationCovenantDetailsDTO.getCovenant_Description());
//        applicationLevelCovenant.setCovenant_Frequency(applicationCovenantDetailsDTO.getCovenant_Frequency());
//        applicationLevelCovenant.setCovenant_Due_Date(applicationCovenantDetailsDTO.getCovenant_Due_Date());
//        applicationLevelCovenant.setDisbursementType(applicationCovenantDetailsDTO.getDisbursementType());
//       // applicationLevelCovenant.setCreatedDate(applicationLevelCovenant.getCreatedDate());
//
//        // Save or update the ApplicationLevelCovenant
//        applicationLevelCovenant = applicationCovenantDao.save(applicationLevelCovenant);
//
//        // Create and populate FacilityCovenantFacilities records
//        List<FacilityCovenantFacilities> facilityCovenantFacilitiesList = new ArrayList<>();
//        for (ApplicationCovenantFacilityDTO applicationCovenantFacilityDTO : applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS()) {
//            FacilityCovenantFacilities facilityCovenantFacilities = new FacilityCovenantFacilities();
//
//            Facility facility = facilityDao.getOne(applicationCovenantFacilityDTO.getFacilityID());
//
//            facilityCovenantFacilities.setApplicationLevelCovenant(applicationLevelCovenant);
//            facilityCovenantFacilities.setFacility(facility);
//            facilityCovenantFacilities.setCreditFacilityTemplateID(applicationCovenantFacilityDTO.getCreditFacilityTemplateID());
//            facilityCovenantFacilities.setCreditFacilityName(applicationCovenantFacilityDTO.getCreditFacilityName());
//            facilityCovenantFacilities.setFacilityRefCode(applicationCovenantFacilityDTO.getFacilityRefCode());
//            facilityCovenantFacilities.setFacilityCurrency(applicationCovenantFacilityDTO.getFacilityCurrency());
//            facilityCovenantFacilities.setFacilityAmount(applicationCovenantFacilityDTO.getFacilityAmount());
//            facilityCovenantFacilities.setDisplayOrder(applicationCovenantFacilityDTO.getDisplayOrder());
//
//            facilityCovenantFacilitiesList.add(facilityCovenantFacilities);
//        }
//
//        facilityCovenantFacilitiesList = facilityCovenantFacilitiesDao.saveAll(facilityCovenantFacilitiesList);
//
//        applicationLevelCovenant.setFacilityCovenantFacilitiesSet(facilityCovenantFacilitiesList);
//
//        return applicationLevelCovenant;
//    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<ApplicationLevelCovenant> saveOrUpdateApplicationCovenant(ApplicationCovenantReqDTO applicationCovenantReqDTO, CredentialsDTO credentialsDTO) throws AppsException {
        Date date = new Date();
        List<ApplicationLevelCovenant> savedCovenants = new ArrayList<>();

        List<ApplicationLevelCovenant> existingCovenants = applicationCovenantDao.findByFacilityPaperFpRefNumber(applicationCovenantReqDTO.getCasReference());

        existingCovenants.stream()
                .filter(covenant -> covenant.getDisplayOrder() == null)
                .forEach(covenant -> {
                    covenant.setDisplayOrder(0);
                    applicationCovenantDao.save(covenant);
                });

        // Determine the maximum display order
        int maxDisplayOrder = existingCovenants.stream()
                .mapToInt(ApplicationLevelCovenant::getDisplayOrder)
                .max()
                .orElse(0);

        for (ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO : applicationCovenantReqDTO.getCovenantDetails()) {
            ApplicationLevelCovenant applicationLevelCovenant;

            if (applicationCovenantReqDTO.getApplicationCovenantId() != null) {
                applicationLevelCovenant = applicationCovenantDao.findById(applicationCovenantReqDTO.getApplicationCovenantId()).orElseThrow(
                        () -> new AppsException("Covenant with ID " + applicationCovenantReqDTO.getApplicationCovenantId() + " not found")
                );
                facilityCovenantFacilitiesDao.deleteByApplicationLevelCovenantApplicationCovenantId(applicationCovenantReqDTO.getApplicationCovenantId());
                applicationLevelCovenant.setCreatedDate(date);
                applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
                applicationLevelCovenant.setCreatedUserDisplayName(applicationCovenantReqDTO.getCreatedUserDisplayName());
            } else {
                applicationLevelCovenant = new ApplicationLevelCovenant();
                applicationLevelCovenant.setCreatedDate(date);
                applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
                applicationLevelCovenant.setCreatedUserDisplayName(applicationCovenantReqDTO.getCreatedUserDisplayName());
                applicationLevelCovenant.setStatus(AppsConstants.CovenantStatus.Active);
            }

            applicationLevelCovenant.setRequestUUID(applicationCovenantReqDTO.getRequestUUID());

            Customer customer = customerDao.findCustomerByFinancialID(applicationCovenantReqDTO.getCustId());
            if (customer == null) {
                Customer newCustomer = new Customer();
                newCustomer.setCustomerFinancialID(applicationCovenantReqDTO.getCustId());
                customer = customerDao.save(newCustomer);
            }
            applicationLevelCovenant.setCustomerFinacleID(customer.getCustomerFinancialID());

            FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(applicationCovenantReqDTO.getCasReference());
            if (facilityPaper == null) {
                FacilityPaper newFacilityPaper = new FacilityPaper();
                newFacilityPaper.setFpRefNumber(applicationCovenantReqDTO.getCasReference());
                facilityPaper = facilityPaperDao.save(newFacilityPaper);
            }
            applicationLevelCovenant.setFacilityPaper(facilityPaper);

            applicationLevelCovenant.setCovenant_Code(applicationCovenantDetailsDTO.getCovenant_Code());
            applicationLevelCovenant.setCovenant_Description(applicationCovenantDetailsDTO.getCovenant_Description());
            applicationLevelCovenant.setCovenant_Frequency(applicationCovenantDetailsDTO.getCovenant_Frequency());
            applicationLevelCovenant.setCovenant_Due_Date(applicationCovenantDetailsDTO.getCovenant_Due_Date());
            applicationLevelCovenant.setDisbursementType(applicationCovenantDetailsDTO.getDisbursementType());
            applicationLevelCovenant.setNoFrequencyDueDate(applicationCovenantDetailsDTO.getNoFrequencyDueDate());
            applicationLevelCovenant.setIsExists(AppsConstants.YesNo.N);
            applicationLevelCovenant.setDisplayOrder(++maxDisplayOrder);
            applicationLevelCovenant.setApplicableType(applicationCovenantDetailsDTO.getApplicableType());

            applicationLevelCovenant = applicationCovenantDao.save(applicationLevelCovenant);

            List<FacilityCovenantFacilities> facilityCovenantFacilitiesList = new ArrayList<>();
            for (ApplicationCovenantFacilityDTO applicationCovenantFacilityDTO : applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS()) {
                FacilityCovenantFacilities facilityCovenantFacilities = new FacilityCovenantFacilities();

                Facility facility = facilityDao.getOne(applicationCovenantFacilityDTO.getFacilityID());

                facilityCovenantFacilities.setApplicationLevelCovenant(applicationLevelCovenant);
                facilityCovenantFacilities.setFacility(facility);
                facilityCovenantFacilities.setCreditFacilityTemplateID(applicationCovenantFacilityDTO.getCreditFacilityTemplateID());
                facilityCovenantFacilities.setCreditFacilityName(applicationCovenantFacilityDTO.getCreditFacilityName());
                facilityCovenantFacilities.setFacilityRefCode(applicationCovenantFacilityDTO.getFacilityRefCode());
                facilityCovenantFacilities.setFacilityCurrency(applicationCovenantFacilityDTO.getFacilityCurrency());
                facilityCovenantFacilities.setFacilityAmount(applicationCovenantFacilityDTO.getFacilityAmount());
                facilityCovenantFacilities.setDisplayOrder(applicationCovenantFacilityDTO.getDisplayOrder());

                facilityCovenantFacilitiesList.add(facilityCovenantFacilities);
            }

            facilityCovenantFacilitiesList = facilityCovenantFacilitiesDao.saveAll(facilityCovenantFacilitiesList);

            applicationLevelCovenant.setFacilityCovenantFacilitiesSet(facilityCovenantFacilitiesList);

            savedCovenants.add(applicationLevelCovenant);
        }

        return savedCovenants;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO uploadCreditRiskDocument(FPCreditRiskDocumentUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload credit risk document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();

        FacilityPaper facilityPaper = this.facilityPaperDao.getOne(uploadRQ.getFacilityPaperID());
        FPCreditRiskDocument fpCreditRiskDocument = null;
        FPCreditRiskDocumentDTO previousFPCRDoc = null;
        Boolean isNewFpRiskDoc = uploadRQ.getFpCreditRiskDocumentID() == null;
        if (isNewFpRiskDoc) {
            fpCreditRiskDocument = new FPCreditRiskDocument();
            fpCreditRiskDocument.setCreatedBy(credentialsDTO.getUserName());
            fpCreditRiskDocument.setCreatedDate(date);
            fpCreditRiskDocument.setUploadedDivCode(uploadRQ.getUploadedDivCode());
            fpCreditRiskDocument.setUploadedUserDisplayName(uploadRQ.getUploadedUserDisplayName());
            fpCreditRiskDocument.setIsLocked(uploadRQ.getIsLocked());
            facilityPaper.addFPCreditRiskDocument(fpCreditRiskDocument);
        } else {
            fpCreditRiskDocument = facilityPaper.getFpCreditRiskDocumentByID(uploadRQ.getFpCreditRiskDocumentID());
            previousFPCRDoc = new FPCreditRiskDocumentDTO(fpCreditRiskDocument);
            fpCreditRiskDocument.setModifiedBy(credentialsDTO.getUserName());
            fpCreditRiskDocument.setModifiedDate(date);
        }
        if (uploadRQ.getDocStorageDTO() != null) {
            uploadRQ.getDocStorageDTO().setDescription("FACILITY PAPER : " + facilityPaper.getFacilityPaperNumber());
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
            fpCreditRiskDocument.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("credit risk Document data null:{}", uploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_DOCUMENT_NULL_SUPPORT_DOC);
        }

        fpCreditRiskDocument.setDescription(uploadRQ.getRemark());
        //fpCreditRiskDocument.setSupportingDoc(this.globalSupportingDocDao.getOne(uploadRQ.getSupportingDocID()));
        fpCreditRiskDocument.setStatus(uploadRQ.getStatus());

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);

        LOG.info("END: Upload credit risk document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadDocument();
        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<ApplicationCovenantListResponseDTO> getApplicationCovenantList(String fpRefNumber, CredentialsDTO credentialsDTO) throws AppsException {


        List<ApplicationLevelCovenant> applicationLevelCovenants = applicationCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        applicationLevelCovenants = applicationLevelCovenants.stream()
                .sorted(Comparator.comparing(ApplicationLevelCovenant::getDisplayOrder))
                .collect(Collectors.toList());


        List<ApplicationCovenantListResponseDTO> applicationCovenantReqDTOList = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList();
        for (ApplicationLevelCovenant applicationLevelCovenant : applicationLevelCovenants) {

            List<FacilityListForCovenantDTO> facilityList = facilityService.getFacilities(applicationLevelCovenant.getFacilityPaper().getFacilityPaperID(), credentialsDTO);
            if (!facilityList.isEmpty()) {
                Collections.sort(facilityList);
                List<FacilityListForCovenantDTO> newFacilityLst = new ArrayList<FacilityListForCovenantDTO>();
                HashMap<Integer, FacilityListForCovenantDTO> newFacilityMap = new HashMap<Integer, FacilityListForCovenantDTO>();
                for (FacilityListForCovenantDTO f : facilityList) {
                    f.setDisplayOrder(newFacilityLst.size() + 1);
                    newFacilityLst.add(f);
                    newFacilityMap.put(f.getFacilityID(), f);
                }

                ApplicationCovenantListResponseDTO applicationCovenantReqDTO = new ApplicationCovenantListResponseDTO();
                applicationCovenantReqDTO.setApplicationCovenantId(applicationLevelCovenant.getApplicationCovenantId());
                applicationCovenantReqDTO.setRequestUUID(applicationLevelCovenant.getRequestUUID());
//                applicationCovenantReqDTO.setCustId(applicationLevelCovenant.getCustomer().getCustomerFinancialID());
                applicationCovenantReqDTO.setCustId(applicationLevelCovenant.getCustomerFinacleID());
                applicationCovenantReqDTO.setCasReference(applicationLevelCovenant.getFacilityPaper().getFpRefNumber());
                applicationCovenantReqDTO.setCreatedBy(applicationLevelCovenant.getCreatedBy());
                applicationCovenantReqDTO.setCreatedUserDisplayName(applicationLevelCovenant.getCreatedUserDisplayName());
                applicationCovenantReqDTO.setStatus(applicationLevelCovenant.getStatus());
                applicationCovenantReqDTO.setWorkClass(String.valueOf(securityService.getUserUPMDetails(applicationLevelCovenant.getCreatedBy()).getApplicationSecurityClass()));

                LOG.info("created user display name", applicationCovenantReqDTO.getCreatedUserDisplayName());
                applicationCovenantReqDTO.setCreatedDate(applicationLevelCovenant.getCreatedDate());

                List<ApplicationCovenantDetailsDTO> applicationCovenantDetailsDTOS = new ArrayList<>();
                ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO = new ApplicationCovenantDetailsDTO();
                applicationCovenantDetailsDTO.setCovenant_Code(applicationLevelCovenant.getCovenant_Code());
                applicationCovenantDetailsDTO.setCovenant_Description(applicationLevelCovenant.getCovenant_Description());
                applicationCovenantDetailsDTO.setCovenant_Frequency(applicationLevelCovenant.getCovenant_Frequency());
                applicationCovenantDetailsDTO.setCovenant_Due_Date(applicationLevelCovenant.getCovenant_Due_Date());

                applicationCovenantReqDTO.setCovenantDetails(applicationCovenantDetailsDTOS);

                List<FacilityCovenantFacilities> covenantFacilities = facilityCovenantFacilitiesDao.findByApplicationLevelCovenantApplicationCovenantId(applicationLevelCovenant.getApplicationCovenantId());
                HashMap<String, List<ApplicationCovenantFacilityDTO>> covenantFacilityLst = new HashMap<String, List<ApplicationCovenantFacilityDTO>>();

                List<ApplicationCovenantFacilityDTO> facilityDTOS = new ArrayList<>();

                Comparator<ApplicationCovenantFacilityDTO> displayOrderComparator = Comparator.comparingInt(facilityDTO -> {
                    FacilityDTO facility = facilityService.getFacilityByID(facilityDTO.getFacilityID());
                    return facility.getDisplayOrder();
                });

                for (FacilityCovenantFacilities facilityCovenantFacilities : covenantFacilities) {
                    ApplicationCovenantFacilityDTO facilityDTO = new ApplicationCovenantFacilityDTO();

                    FacilityDTO facility = facilityService.getFacilityByID(facilityCovenantFacilities.getFacility().getFacilityID());

                    Facility facility1 = facilityService.getFacilityByIDForCovenants(facilityCovenantFacilities.getFacility().getFacilityID());

                    StringBuffer value = new StringBuffer();

                    if (facility.getStatus().equals(AppsConstants.Status.ACT)) {
                        facilityDTO.setFacilityID(facilityCovenantFacilities.getFacility().getFacilityID());
                        value.append(facilityCovenantFacilities.getFacility().getFacilityID());
                        facilityDTO.setCreditFacilityTemplateID(facilityCovenantFacilities.getCreditFacilityTemplateID());
                        facilityDTO.setCreditFacilityName(facilityCovenantFacilities.getCreditFacilityName());
                        facilityDTO.setFacilityRefCode(facilityCovenantFacilities.getFacilityRefCode());
                        facilityDTO.setFacilityCurrency(facilityCovenantFacilities.getFacilityCurrency());
                        facilityDTO.setFacilityAmount(facility.getFacilityAmount());
                        facilityDTO.setDisplayOrder(facility.getDisplayOrder());
                        facilityDTOS.add(facilityDTO);
                        arrayList.add(String.valueOf(value));
                    }

                }
                facilityDTOS.sort(displayOrderComparator);
                applicationCovenantDetailsDTO.setApplicationCovenantFacilityDTOS(facilityDTOS);
                applicationCovenantDetailsDTOS.add(applicationCovenantDetailsDTO);
                applicationCovenantReqDTO.setCovenantDetails(applicationCovenantDetailsDTOS);
//                applicationCovenantReqDTO.setFacIdList(arrayList);
                applicationCovenantReqDTOList.add(applicationCovenantReqDTO);
            }

        }
        return applicationCovenantReqDTOList;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Integer deleteApplicationCovenant(Integer applicationCovenantId, String createdUserDisplayName, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();

        ApplicationCovenantReqDTO applicationCovenantReqDTO = new ApplicationCovenantReqDTO();

        ApplicationLevelCovenant applicationLevelCovenant = applicationCovenantDao.findById(applicationCovenantId).orElseThrow(() -> new AppsException("Covenant with " + applicationCovenantId + " does not exist."));

        applicationLevelCovenant.setStatus(AppsConstants.CovenantStatus.Inactive);
        applicationLevelCovenant.setCreatedDate(date);
        applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
        applicationLevelCovenant.setCreatedUserDisplayName(createdUserDisplayName);

        return applicationLevelCovenant.getApplicationCovenantId();
    }

    public FacilityPaperDTO deactivateFpCreditRiskDoc(FPCreditRiskDocumentDTO fpCreditRiskDocumentDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the Fp Credit Risk document: {} by: {}", fpCreditRiskDocumentDTO, credentialsDTO.getUserName());
        FacilityPaper facilityPaper = facilityPaperDao.getOne(fpCreditRiskDocumentDTO.getFacilityPaperID());
        FPCreditRiskDocumentDTO previousFPDoc = new FPCreditRiskDocumentDTO(fpCreditRiskDocumentDao.getOne(fpCreditRiskDocumentDTO.getCreditRiskDocumentID()));
        FPCreditRiskDocument fpCreditRiskDocument = null;

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();
        loadOptionDTO.loadCustomer();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCustomerCribDetail();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadCustomerOtherBankFacility();
        loadOptionDTO.loadCustomerDocument();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadShareHolderDetail();
        if (fpCreditRiskDocumentDTO.getSupportingDocID() != null) {
            fpCreditRiskDocument = fpCreditRiskDocumentDao.getOne(fpCreditRiskDocumentDTO.getCreditRiskDocumentID());
            fpCreditRiskDocument.setStatus(AppsConstants.Status.INA);
            fpCreditRiskDocument = fpCreditRiskDocumentDao.saveAndFlush(fpCreditRiskDocument);

        }

        LOG.info("END: Deactivate the Fp Credit Risk document: {} by: {}", fpCreditRiskDocumentDTO, credentialsDTO.getUserName());
        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FPDocumentElementDTO> getFacilityDocumentElements() throws AppsException {
        return facilityPaperJdbcDao.getFacilityDocumentElements();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FPSecurityDocumentDTO saveOrUpdateSecurityDocument(FPSecurityDocumentDTO fpSecurityDocumentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : saveOrUpdateSecurityDocument : {} ", credentialsDTO.getUserID());
        FPSecurityDocument fpSecurityDocument = null;
        FPSecurityDocumentTagData fpSecurityDocumentTagData = null;

        Date date = new Date();
        LOG.info("securityDocumentID : {} ", fpSecurityDocumentDTO.getSecurityDocumentID());
        LOG.info("securityDocumentName : {} ", fpSecurityDocumentDTO.getDocumentName());
        LOG.info("fpSecurityDocumentTagDataDTOList : {} ", fpSecurityDocumentDTO.getFpSecurityDocumentTagDataDTOList().size());
        LOG.info("Document status : {} ", fpSecurityDocumentDTO.getDocumentStatus());

        boolean isNewSecurityDocument = fpSecurityDocumentDTO.getSecurityDocumentID() == null;
        LOG.info("isNewSecurityDocument : {} ", isNewSecurityDocument);

        if (isNewSecurityDocument) {
            fpSecurityDocument = new FPSecurityDocument();
            fpSecurityDocumentTagData = new FPSecurityDocumentTagData();
            fpSecurityDocument.setSavedBy(credentialsDTO.getUserName());
            fpSecurityDocument.setSavedByDisplayName(fpSecurityDocumentDTO.getSavedByDisplayName());
            fpSecurityDocument.setSavedByDiv(credentialsDTO.getDivCode());
            fpSecurityDocument.setDocumentStatus(fpSecurityDocumentDTO.getDocumentStatus());
            fpSecurityDocument.setSavedDate(date);

        } else {
            fpSecurityDocument = this.fpSecurityDocumentDao.getOne(fpSecurityDocumentDTO.getSecurityDocumentID());

            if ((fpSecurityDocumentDTO.getDocumentStatus().equals("SUBMIT")) || (fpSecurityDocumentDTO.getDocumentStatus().equals("DRAFT")) || (fpSecurityDocumentDTO.getDocumentStatus().equals("DELETE"))) {
                fpSecurityDocument.setSavedBy(credentialsDTO.getUserName());
                fpSecurityDocument.setSavedByDisplayName(fpSecurityDocumentDTO.getSavedByDisplayName());
                fpSecurityDocument.setSavedByDiv(credentialsDTO.getDivCode());
                fpSecurityDocument.setDocumentStatus(fpSecurityDocumentDTO.getDocumentStatus());
                fpSecurityDocument.setSavedDate(date);
            }
            if ((fpSecurityDocumentDTO.getDocumentStatus().equals("RETURN")) || (fpSecurityDocumentDTO.getDocumentStatus().equals("APPROVE") || (fpSecurityDocumentDTO.getDocumentStatus().equals("PRINT")))) {
                if (fpSecurityDocumentDTO.getDocumentStatus().equals("PRINT")) {
                    fpSecurityDocument.setPrintedBy(credentialsDTO.getUserName());
                    fpSecurityDocument.setPrintedByDiv(credentialsDTO.getDivCode());
                    fpSecurityDocument.setPrintedByDisplayName(fpSecurityDocumentDTO.getPrintedByDisplayName());
                    fpSecurityDocument.setPrintedDate(date);
                } else {
                    fpSecurityDocument.setDocumentStatus(fpSecurityDocumentDTO.getDocumentStatus());
                    fpSecurityDocument.setAuthByDisplayName(fpSecurityDocumentDTO.getAuthByDisplayName());
                    fpSecurityDocument.setAuthBy(credentialsDTO.getUserName());
                    fpSecurityDocument.setAuthByDiv(credentialsDTO.getDivCode());
                    fpSecurityDocument.setAuthDate(date);
                }
            }
        }

        fpSecurityDocument.setFacilityPaperID(fpSecurityDocumentDTO.getFacilityPaperID());
        fpSecurityDocument.setCreditFacilityTemplateID(fpSecurityDocumentDTO.getCreditFacilityTemplateID());
        fpSecurityDocument.setCreditFacilityName(fpSecurityDocumentDTO.getCreditFacilityName());
        fpSecurityDocument.setDocumentName(fpSecurityDocumentDTO.getDocumentName());
        fpSecurityDocument.setDocumentContent(fpSecurityDocumentDTO.getDocumentContent());
        fpSecurityDocument.setFacilityID(fpSecurityDocumentDTO.getFacilityID());
        FPDocumentElement fpDocumentElement = fpDocumentElementDao.getOne(fpSecurityDocumentDTO.getElementID());
        fpSecurityDocument.setFpDocumentElement(fpDocumentElement);
        fpSecurityDocument.setReturnComment(fpSecurityDocumentDTO.getReturnComment());
        fpSecurityDocument = this.fpSecurityDocumentDao.saveAndFlush(fpSecurityDocument);

        if (fpSecurityDocument.getSecurityDocumentID() != null) {
            if (fpSecurityDocumentDTO.getDocumentStatus().equals("DRAFT")) {
                if (fpSecurityDocumentDTO.getFpSecurityDocumentTagDataDTOList() != null) {
                    for (FPSecurityDocumentTagDataDTO fpSecurityDocumentTagDataDTO : fpSecurityDocumentDTO.getFpSecurityDocumentTagDataDTOList()) {
                        if (fpSecurityDocumentTagDataDTO.getTagID() == null) {
                            fpSecurityDocumentTagData = new FPSecurityDocumentTagData();
                        } else {
                            fpSecurityDocumentTagData = fpSecurityDocument.getFpSecurityDocumentTagDataByID(fpSecurityDocumentTagDataDTO.getTagID());
                        }
                        fpSecurityDocumentTagData.setFpSecurityDocument(fpSecurityDocument);
                        fpSecurityDocumentTagData.setTagOrder(fpSecurityDocumentTagDataDTO.getTagOrder());
                        fpSecurityDocumentTagData.setTag(fpSecurityDocumentTagDataDTO.getTag());
                        fpSecurityDocumentTagData.setTagValue(fpSecurityDocumentTagDataDTO.getTagValue());
                        fpSecurityDocumentTagData.setTagType(fpSecurityDocumentTagDataDTO.getTagType());
                        fpSecurityDocumentTagData.setFieldType(fpSecurityDocumentTagDataDTO.getFieldType());
                        fpSecurityDocumentTagData = this.fpSecurityDocumentTagDataDao.saveAndFlush(fpSecurityDocumentTagData);
                        fpSecurityDocument.addFpSecurityDocumentTagData(fpSecurityDocumentTagData);
                    }
                }
            }
        }

        FPSecurityDocumentDTO updateFpSecurityDocumentDTO = new FPSecurityDocumentDTO(fpSecurityDocument);
        LOG.info("END : saveOrUpdateSecurityDocument : {}", credentialsDTO.getUserID());
        return updateFpSecurityDocumentDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FPSecurityDocumentDTO> getSecurityDocumentHistory(Integer securityDocumentID) throws AppsException {

        List<FPSecurityDocumentDTO> mainHisotryList = new ArrayList<>();
        List<FPSecurityDocumentDTO> historyList = facilityPaperJdbcDao.getSecurityDocumentHistory(securityDocumentID);
        for (FPSecurityDocumentDTO fpSecurityDocumentDTO : historyList) {
            mainHisotryList.add(fpSecurityDocumentDTO);
        }
        return mainHisotryList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FPDocumentElementDTO> getSecurityDocumentElements(Integer facilityPaperID) throws AppsException {
        List<FPDocumentElementDTO> fpDocumentElementDTOList = facilityPaperJdbcDao.getSecurityDocumentElements(facilityPaperID);
        return fpDocumentElementDTOList;
    }

    public ApplicationLevelCovenant updateApplicationLevelCovenant(ApplicationCovenantUpdateDTO applicationCovenantUpdateDTO) {

        ApplicationLevelCovenant applicationLevelCovenantDb = applicationCovenantDao.findById(applicationCovenantUpdateDTO.getApplicationCovenantId()).orElseThrow(
                () -> new RuntimeException("facility covenant with " + applicationCovenantUpdateDTO.getApplicationCovenantId() + " does not exist")
        );

        applicationLevelCovenantDb.setRequestUUID(applicationCovenantUpdateDTO.getRequestUUID());

        Customer customer = customerDao.findCustomerByFinancialID(applicationCovenantUpdateDTO.getCustId());
//        applicationLevelCovenantDb.setCustomer(customer);

        FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(applicationCovenantUpdateDTO.getCasReference());
        applicationLevelCovenantDb.setFacilityPaper(facilityPaper);


        for (ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO : applicationCovenantUpdateDTO.getCovenantDetails()) {
            applicationLevelCovenantDb.setCovenant_Code(applicationCovenantDetailsDTO.getCovenant_Code());
            applicationLevelCovenantDb.setCovenant_Description(applicationCovenantDetailsDTO.getCovenant_Description());
            applicationLevelCovenantDb.setCovenant_Due_Date(applicationCovenantDetailsDTO.getCovenant_Due_Date());
            applicationLevelCovenantDb.setCovenant_Frequency(applicationCovenantDetailsDTO.getCovenant_Frequency());
            applicationLevelCovenantDb.setDisbursementType(applicationCovenantDetailsDTO.getDisbursementType());

            List<FacilityCovenantFacilities> facilityCovenantFacilitiesList = new ArrayList<>();
            for (ApplicationCovenantFacilityDTO facilityDTO : applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS()) {
                FacilityCovenantFacilities facilityCovenantFacilities = new FacilityCovenantFacilities();
                facilityCovenantFacilities.setApplicationLevelCovenant(applicationLevelCovenantDb);
                facilityCovenantFacilities.setCreditFacilityTemplateID(facilityDTO.getCreditFacilityTemplateID());
                facilityCovenantFacilities.setCreditFacilityName(facilityDTO.getCreditFacilityName());
                facilityCovenantFacilities.setFacilityRefCode(facilityDTO.getFacilityRefCode());
                facilityCovenantFacilities.setFacilityCurrency(facilityDTO.getFacilityCurrency());
                facilityCovenantFacilities.setFacilityAmount(facilityDTO.getFacilityAmount());

                facilityCovenantFacilitiesList.add(facilityCovenantFacilities);
            }

            applicationLevelCovenantDb.setFacilityCovenantFacilitiesSet(facilityCovenantFacilitiesList);
        }

        applicationLevelCovenantDb.setStatus(AppsConstants.CovenantStatus.Active);
        return applicationCovenantDao.save(applicationLevelCovenantDb);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationCovenantListResponseDTO findApplicationCovenantById(int applicationCovenantId, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();

        ApplicationLevelCovenant applicationLevelCovenant = applicationCovenantDao.findById(applicationCovenantId).get();

        ApplicationCovenantListResponseDTO applicationCovenantReqDTO = new ApplicationCovenantListResponseDTO();
        applicationCovenantReqDTO.setApplicationCovenantId(applicationLevelCovenant.getApplicationCovenantId());
        applicationCovenantReqDTO.setRequestUUID(applicationLevelCovenant.getRequestUUID());
        applicationCovenantReqDTO.setCustId(applicationLevelCovenant.getCustomerFinacleID());
        applicationCovenantReqDTO.setCasReference(applicationLevelCovenant.getFacilityPaper().getFpRefNumber());
        applicationCovenantReqDTO.setCreatedBy(applicationLevelCovenant.getCreatedBy());
        applicationCovenantReqDTO.setCreatedUserDisplayName(applicationLevelCovenant.getCreatedUserDisplayName());
        applicationCovenantReqDTO.setStatus(applicationLevelCovenant.getStatus());

        LOG.info("created user display name", applicationCovenantReqDTO.getCreatedUserDisplayName());
        applicationCovenantReqDTO.setCreatedDate(applicationLevelCovenant.getCreatedDate());

        List<ApplicationCovenantDetailsDTO> applicationCovenantDetailsDTOS = new ArrayList<>();
        //for(ApplicationLevelCovenant applicationLevelCovenant1: applicationLevelCovenants){
        ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO = new ApplicationCovenantDetailsDTO();
        applicationCovenantDetailsDTO.setCovenant_Code(applicationLevelCovenant.getCovenant_Code());
        applicationCovenantDetailsDTO.setCovenant_Description(applicationLevelCovenant.getCovenant_Description());
        applicationCovenantDetailsDTO.setCovenant_Frequency(applicationLevelCovenant.getCovenant_Frequency());
        applicationCovenantDetailsDTO.setCovenant_Due_Date(applicationLevelCovenant.getCovenant_Due_Date());
        applicationCovenantDetailsDTO.setDisbursementType(applicationLevelCovenant.getDisbursementType());
        applicationCovenantDetailsDTO.setNoFrequencyDueDate(applicationLevelCovenant.getNoFrequencyDueDate());
        applicationCovenantDetailsDTO.setApplicableType(applicationLevelCovenant.getApplicableType());

        applicationCovenantReqDTO.setCovenantDetails(applicationCovenantDetailsDTOS);
        //applicationCovenantReqDTOList.add(applicationCovenantDetailsDTO);


        List<FacilityCovenantFacilities> covenantFacilities = facilityCovenantFacilitiesDao.findByApplicationLevelCovenantApplicationCovenantId(applicationLevelCovenant.getApplicationCovenantId());

        List<ApplicationCovenantFacilityDTO> facilityDTOS = new ArrayList<>();
        for (FacilityCovenantFacilities facilityCovenantFacilities : covenantFacilities) {
            ApplicationCovenantFacilityDTO facilityDTO = new ApplicationCovenantFacilityDTO();
            facilityDTO.setFacilityID(facilityCovenantFacilities.getFacility().getFacilityID());
            facilityDTO.setCreditFacilityTemplateID(facilityCovenantFacilities.getCreditFacilityTemplateID());
            facilityDTO.setCreditFacilityName(facilityCovenantFacilities.getCreditFacilityName());
            facilityDTO.setFacilityRefCode(facilityCovenantFacilities.getFacilityRefCode());
            facilityDTO.setFacilityCurrency(facilityCovenantFacilities.getFacilityCurrency());
            facilityDTO.setFacilityAmount(facilityCovenantFacilities.getFacilityAmount());
            facilityDTOS.add(facilityDTO);
        }

        applicationCovenantDetailsDTO.setApplicationCovenantFacilityDTOS(facilityDTOS);
        applicationCovenantDetailsDTOS.add(applicationCovenantDetailsDTO);
        //}


        applicationCovenantReqDTO.setCovenantDetails(applicationCovenantDetailsDTOS);
        //applicationCovenantReqDTOList.add(applicationCovenantReqDTO);


        return applicationCovenantReqDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addNewCreditRiskComment(CreditRiskCommentAddDTO riskCommentUpdateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper add credit risk comment: {} : {}", riskCommentUpdateDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(riskCommentUpdateDTO.getFacilityPaperID());

        FPCreditRiskComment notLockedRiskComment = facilityPaper.getNotLockedRiskComment();
        if (notLockedRiskComment != null) {
            notLockedRiskComment.setIsLocked(AppsConstants.YesNo.Y);
            facilityPaper.addFPCreditRiskComment(notLockedRiskComment);
        }

        FPCreditRiskComment validRiskComment = facilityPaper.getValidRiskComment();

        for (FPCreditRiskCommentDTO riskCommentDTO : riskCommentUpdateDTO.getFpCreditRiskCommentDTOS()) {

            boolean isNewComment = riskCommentDTO.getFpCreditRiskCommentID() == null;
            FPCreditRiskComment creditRiskComment = null;

            if (isNewComment) {
                creditRiskComment = new FPCreditRiskComment();
                creditRiskComment.setCreatedBy(credentialsDTO.getUserName());
                creditRiskComment.setCreatedDate(date);
                creditRiskComment.setCreatedUserName(riskCommentUpdateDTO.getUserName());
                creditRiskComment.setIsLocked(AppsConstants.YesNo.N);
                creditRiskComment.setIsValidComment(AppsConstants.YesNo.Y);

                if (validRiskComment != null) {
                    validRiskComment.setIsValidComment(AppsConstants.YesNo.N); // this will invalidate the previous comment
                    facilityPaper.addFPCreditRiskComment(validRiskComment);
                }
            }

            CreditRiskCommentTemplateDTO creditRiskCommentTemplateDTO = new CreditRiskCommentTemplateDTO();

            for (CASCustomer casCustomer : facilityPaper.getCASCustomerSet()) {
                creditRiskCommentTemplateDTO.addCASCustomer(casCustomer);

                if (creditRiskCommentTemplateDTO.getCustomerRatings() == null) {
                    for (CustomerRatings customerRatings : casCustomer.getCustomerRatingsSet()) {
                        creditRiskCommentTemplateDTO.setCustomerRatings(customerRatings);
                    }
                }

            }
            creditRiskCommentTemplateDTO.setPaperType(riskCommentUpdateDTO.getPaperType());
            creditRiskCommentTemplateDTO.setBranchCode(facilityPaper.getBranchCode());
            creditRiskCommentTemplateDTO.setTotalExposureNew(NumberUtil.getFormattedAmount(facilityPaper.getTotalExposureNew()));
            creditRiskCommentTemplateDTO.setGroupTotalExposureNew(NumberUtil.getFormattedAmount(facilityPaper.getGroupTotalExposureNew()));

            try {
                BranchLoadResponseDTO branchLoadResponseDTO = integrationService.getBranchList(credentialsDTO).getBranchResponse(facilityPaper.getBranchCode());
                if (branchLoadResponseDTO != null) {
                    creditRiskCommentTemplateDTO.setBranchName(branchLoadResponseDTO.getBranchName());
                }
            } catch (Exception e) {
                LOG.info("ERROR:", e);
            }

            facilityPaper.addFPCreditRiskComment(creditRiskComment);
            creditRiskComment.setUpmID(riskCommentUpdateDTO.getUpmID());
            creditRiskComment.setUPMPrivilegeCode(riskCommentDTO.getUPMPrivilegeCode());
            creditRiskComment.setCreditRiskComment(this.getCreditRiskDocContent(creditRiskCommentTemplateDTO));
            creditRiskComment.setStatus(riskCommentDTO.getStatus());

        }

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCreditRiskComments();

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);

        CreditRiskCommentFilterDTO creditRiskCommentFilterDTO = facilityPaperDTO.getFpCreditRiskCommentFilterDTO();

        if (creditRiskCommentFilterDTO == null) {
            creditRiskCommentFilterDTO = new CreditRiskCommentFilterDTO();
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if ((!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) && (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode()))) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else if (!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else {
            creditRiskCommentFilterDTO.setLoad(false);
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoadHistory(true);
        } else {
            creditRiskCommentFilterDTO.setLoadHistory(false);
        }
        facilityPaperDTO.setFpCreditRiskCommentFilterDTO(creditRiskCommentFilterDTO);

        LOG.info("END : Facility Paper update credit risk comment: {} : {}", riskCommentUpdateDTO, credentialsDTO.getUserID());

        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public String getCreditRiskDocContent(CreditRiskCommentTemplateDTO creditRiskCommentTemplateDTO) throws AppsException {

        String templateName = "creditRiskDocument";

        if (creditRiskCommentTemplateDTO.getPaperType() == DomainConstants.CRPaperType.RF1) {
            templateName = "ReviewFormat1";
        }

        String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                + "cr" + File.separator + "html" + File.separator + templateName + ".html";

        Context context = new Context();
        Map<String, Object> params = new HashMap<>();
        params.put("crDTO", creditRiskCommentTemplateDTO);
        context.setVariables(params);

        return this.templateEngine.process(templatePath, context);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO deactivateFpCreditRiskComment(FPCreditRiskCommentDTO fpCreditRiskCommentDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the Fp Credit Risk Comment: {} by: {}", fpCreditRiskCommentDTO, credentialsDTO.getUserName());

        FPCreditRiskComment fpCreditRiskComment = null;

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();
        loadOptionDTO.loadCustomer();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCustomerCribDetail();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadCustomerOtherBankFacility();
        loadOptionDTO.loadCustomerDocument();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCreditRiskComments();

        if (fpCreditRiskCommentDTO.getFpCreditRiskCommentID() != null) {
            fpCreditRiskComment = fpCreditRiskCommentDao.getOne(fpCreditRiskCommentDTO.getFpCreditRiskCommentID());
            fpCreditRiskComment.setStatus(AppsConstants.Status.INA);
            fpCreditRiskComment = fpCreditRiskCommentDao.saveAndFlush(fpCreditRiskComment);
        }

        FacilityPaper facilityPaper = facilityPaperDao.getOne(fpCreditRiskCommentDTO.getFacilityPaperID());

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);

        CreditRiskCommentFilterDTO creditRiskCommentFilterDTO = facilityPaperDTO.getFpCreditRiskCommentFilterDTO();

        if (creditRiskCommentFilterDTO == null) {
            creditRiskCommentFilterDTO = new CreditRiskCommentFilterDTO();
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if ((!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), credentialsDTO.getDivCode())) && (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode()))) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else if (!Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(true);
        } else if (Objects.equals(facilityPaper.getCurrentAssignUserDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoad(false);
        } else {
            creditRiskCommentFilterDTO.setLoad(false);
        }

        if (Objects.equals(credentialsDTO.getDivCode(), getRiskDivCode())) {
            creditRiskCommentFilterDTO.setLoadHistory(true);
        } else {
            creditRiskCommentFilterDTO.setLoadHistory(false);
        }
        facilityPaperDTO.setFpCreditRiskCommentFilterDTO(creditRiskCommentFilterDTO);

        LOG.info("END: Deactivate the Fp Credit Risk Comment: {} by: {}", fpCreditRiskCommentDTO, credentialsDTO.getUserName());
        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FacilityCovenantDetailsDTO> getFacilityCovenantsByFacilityId(Integer facilityID, CredentialsDTO credentialsDTO) throws AppsException {
        return facilityPaperJdbcDao.getFacilityCovenantsByFacilityId(facilityID);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FinalDTO> getFacilityCovenantList(String fpRefNumber, CredentialsDTO credentialsDTO) throws AppsException {

        Map<String, List<ApplicationCovenantDetailsDTO>> map = new HashMap<String, List<ApplicationCovenantDetailsDTO>>();
        List<ApplicationLevelCovenant> applicationLevelCovenants = applicationCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        for (ApplicationLevelCovenant applicationLevelCovenant : applicationLevelCovenants) {
            List<FacilityListForCovenantDTO> facilityListDTOS = facilityService.getFacilities(applicationLevelCovenant.getFacilityPaper().getFacilityPaperID(), credentialsDTO);

            if (!facilityListDTOS.isEmpty()) {

                ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO = new ApplicationCovenantDetailsDTO();
                applicationCovenantDetailsDTO.setApplicationCovenantId(applicationLevelCovenant.getApplicationCovenantId());
                applicationCovenantDetailsDTO.setCustId(applicationLevelCovenant.getCustomerFinacleID());
                applicationCovenantDetailsDTO.setCasReference(applicationLevelCovenant.getFacilityPaper().getFpRefNumber());
                applicationCovenantDetailsDTO.setCreatedDate(applicationLevelCovenant.getCreatedDate());
                applicationCovenantDetailsDTO.setCreatedBy(applicationLevelCovenant.getCreatedBy());
                applicationCovenantDetailsDTO.setCreatedUserDisplayName(applicationLevelCovenant.getCreatedUserDisplayName());
                applicationCovenantDetailsDTO.setStatus(applicationLevelCovenant.getStatus());
                //applicationCovenantDetailsDTO.setWorkClass(String.valueOf(securityService.getUserUPMDetails(applicationLevelCovenant.getCreatedBy()).getApplicationSecurityClass()));

                applicationCovenantDetailsDTO.setCovenant_Code(applicationLevelCovenant.getCovenant_Code());
                applicationCovenantDetailsDTO.setCovenant_Description(applicationLevelCovenant.getCovenant_Description());
                applicationCovenantDetailsDTO.setCovenant_Frequency(applicationLevelCovenant.getCovenant_Frequency());
                applicationCovenantDetailsDTO.setCovenant_Due_Date(applicationLevelCovenant.getCovenant_Due_Date());
                applicationCovenantDetailsDTO.setDisbursementType(applicationLevelCovenant.getDisbursementType());
                applicationCovenantDetailsDTO.setNoFrequencyDueDate(applicationLevelCovenant.getNoFrequencyDueDate());
                applicationCovenantDetailsDTO.setIsExists(applicationLevelCovenant.getIsExists());
                applicationCovenantDetailsDTO.setComplianceStatus(applicationLevelCovenant.getComplianceStatus());
                applicationCovenantDetailsDTO.setApplicableType(applicationLevelCovenant.getApplicableType());

                List<FacilityCovenantFacilities> facilityCovenantFacilities = facilityCovenantFacilitiesDao.findByApplicationLevelCovenantApplicationCovenantId(applicationLevelCovenant.getApplicationCovenantId());
                List<ApplicationCovenantFacilityDTO> facilityDTOS = new ArrayList<>();

                Comparator<ApplicationCovenantFacilityDTO> displayOrderComparator = Comparator.comparingInt(facilityDTO -> {
                    FacilityDTO facility = facilityService.getFacilityByID(facilityDTO.getFacilityID());
                    return facility.getDisplayOrder();
                });

                for (FacilityCovenantFacilities facilities : facilityCovenantFacilities) {

                    FacilityDTO facility = facilityService.getFacilityByID(facilities.getFacility().getFacilityID());

                    if (facility.getStatus().equals(AppsConstants.Status.ACT)) {
                        ApplicationCovenantFacilityDTO applicationCovenantFacilityDTO = new ApplicationCovenantFacilityDTO();
                        applicationCovenantFacilityDTO.setFacilityID(facilities.getFacility().getFacilityID());
                        applicationCovenantFacilityDTO.setCreditFacilityName(facilities.getCreditFacilityName());
                        applicationCovenantFacilityDTO.setCreditFacilityTemplateID(facilities.getCreditFacilityTemplateID());
                        applicationCovenantFacilityDTO.setFacilityRefCode(facilities.getFacilityRefCode());
                        //BigDecimal facilityAmountInMillions = facility.getFacilityAmount().divide(BigDecimal.valueOf(1_000_000), 3, RoundingMode.HALF_UP);
                        applicationCovenantFacilityDTO.setFacilityAmount(facility.getFacilityAmount());
                        applicationCovenantFacilityDTO.setDisplayOrder(facility.getDisplayOrder());
                        applicationCovenantFacilityDTO.setFacilityCurrency(facilities.getFacilityCurrency());
                        facilityDTOS.add(applicationCovenantFacilityDTO);
                    }

                }

                facilityDTOS.sort(Comparator.comparingInt(ApplicationCovenantFacilityDTO::getDisplayOrder));

                facilityDTOS.sort(displayOrderComparator);
                applicationCovenantDetailsDTO.setApplicationCovenantFacilityDTOS(facilityDTOS);

                applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS().sort(Comparator.comparingInt(ApplicationCovenantFacilityDTO::getDisplayOrder));

                String covenantKey = applicationCovenantDetailsDTO.getCovanentKey();
                if (map.containsKey(covenantKey)) {

                    map.get(covenantKey).add(applicationCovenantDetailsDTO);
                } else {

                    List<ApplicationCovenantDetailsDTO> covenantDetailsList = new ArrayList<>();
                    covenantDetailsList.add(applicationCovenantDetailsDTO);
                    map.put(covenantKey, covenantDetailsList);
                }
            }

        }

        List<FinalDTO> finalDTOS = new ArrayList<>();
        for (Map.Entry<String, List<ApplicationCovenantDetailsDTO>> entry : map.entrySet()) {
            FinalDTO finalDTO = new FinalDTO();
            finalDTO.setCovanentKey(entry.getKey());
            finalDTO.setCovValue(entry.getValue());
            finalDTOS.add(finalDTO);
        }

        return finalDTOS;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO addEditShareHolderDetails(ShareHolderDetailUpdateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Paper update share holder details: {} : {}", updateDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(updateDTO.getFacilityPaperID());

        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        for (FPShareHolderDetailDTO fpShareHolderDetailDTO : updateDTO.getFpShareHolderDetailDTOList()) {
            boolean isNewShareHolderDetail = fpShareHolderDetailDTO.getFpShareHolderDetailID() == null;
            FPShareHolderDetailDTO previousShareHolderDetailDTO = null;
            FPShareHolderDetailDTO updateShareHolderDetailDTO = null;
            FPShareHolderDetail fpShareHolderDetail = null;
            if (isNewShareHolderDetail) {
                fpShareHolderDetail = new FPShareHolderDetail();
                fpShareHolderDetail.setCreatedBy(credentialsDTO.getUserName());
                fpShareHolderDetail.setCreatedDate(date);
                facilityPaper.addFpShareHolderDetail(fpShareHolderDetail);
            } else {
                fpShareHolderDetail = facilityPaper.getFpShareHolderDetailByID(fpShareHolderDetailDTO.getFpShareHolderDetailID());
                previousShareHolderDetailDTO = new FPShareHolderDetailDTO(fpShareHolderDetail);
                fpShareHolderDetail.setModifiedBy(credentialsDTO.getUserName());
                fpShareHolderDetail.setModifiedDate(date);
            }

            fpShareHolderDetail.setShareHolderName(fpShareHolderDetailDTO.getShareHolderName());
            fpShareHolderDetail.setShareHolding(fpShareHolderDetailDTO.getShareHolding());
            fpShareHolderDetail.setStatus(fpShareHolderDetailDTO.getStatus());
            updateShareHolderDetailDTO = new FPShareHolderDetailDTO(fpShareHolderDetail);

        }

        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadCreditRiskComments();
        loadOptionDTO.loadShareHolderDetail();
        loadOptionDTO.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOptionDTO.getFacilityLoadOptionDTO().loadAllData();

        FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);
        LOG.info("END : Facility Paper update shareholder details: {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public String getDocumentContent(FacilityPaperDTO facilityPaperDTO, int secId, String branchName) throws AppsException {

        String content = "";

        try {
            UpcSectionDTO result = this.facilityPaperJdbcDao.getDocumentContent(secId);

            String upcSectionName = !result.getUpcSectionName().isEmpty() ? result.getUpcSectionName() : "";

            //removed special characters
            String replacedName = upcSectionName.replaceAll("[-+.^:,/]", "");

            //removed extra spaces
            String templateName = replacedName.replaceAll("\\s+", " ");

            if (!templateName.isEmpty()) {

                //get template
                String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                        + "cp" + File.separator + "html" + File.separator + templateName + ".html";

                List<FacilityDTO> facilityList = facilityPaperDTO.getFacilityDTOList();
                List<CASCustomerDTO> customers = facilityPaperDTO.getCasCustomerDTOList();

                if (!customers.isEmpty()) {
                    for (CASCustomerDTO customer : customers) {

                        //filter and get permanent address
                        CASCustomerAddressDTO address = customer.getCasCustomerAddressDTOList().stream()
                                .filter(item -> "PERMENENT".equals(item.getAddressType()))
                                .findAny()
                                .orElse(null);

                        String registeredAddress = null;

                        //set register address
                        if (address != null) {
                            registeredAddress = address.getAddress1() + ", " + address.getAddress2() + ", " + address.getCity();
                        }
                        customer.setRegisteredAddress(registeredAddress);
                    }
                }

                List<FacilityDTO> newFacilityList = new ArrayList<>();
                List<FacilityDTO> existingFacilityList = new ArrayList<>();

                for (FacilityDTO facilityDTO : facilityList) {

                    //set sector data
                    SectorDTO sector = this.facilityPaperJdbcDao.getSectorById(facilityDTO.getSectorID());
                    String sectorName = sector.getReferenceCode() + " - " + sector.getReferenceDescription();
                    facilityDTO.setSector(sectorName);

                    //set sub sector data
                    SubSectorDTO subSector = this.facilityPaperJdbcDao.getSubSectorById(facilityDTO.getSubSectorID());
                    String subSectorName = subSector.getReferenceDescription();
                    facilityDTO.setSubSector(subSectorName);

                    //set purpose of advance
                    PurposeOfAdvancedDTO purpose = this.facilityPaperJdbcDao.getPurposeById(facilityDTO.getPurposeOfAdvance());
                    facilityDTO.setPurposeRefDescription(purpose.getReferenceDescription());

                    //filter new data
                    if (facilityDTO.getIsNew() == AppsConstants.YesNo.Y) {
                        newFacilityList.add(facilityDTO);
                    } else {
                        existingFacilityList.add(facilityDTO);
                    }
                }

                facilityPaperDTO.setTurnoverGurantees(getTurnoversAndGurantees(facilityPaperDTO));
                //set branch name
                facilityPaperDTO.setBranchName(branchName);

                facilityPaperDTO.setNewFacilityDTOList(newFacilityList);
                facilityPaperDTO.setExistingFacilityDTOList(existingFacilityList);

                if (templateName.equals("WALLET SHARE")) {
                    List<WalletShareDTO> walletShares = getWalletShare(facilityPaperDTO.getFacilityPaperID());
                    facilityPaperDTO.setWalletShares(walletShares);
                }

                Context context = new Context();
                Map<String, Object> params = new HashMap<>();
                params.put("fpDTO", facilityPaperDTO);
                context.setVariables(params);
                content = this.templateEngine.process(templatePath, context);
            }


        } catch (Exception ex) {
            content = "";
        }

        return content;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CommitteePaperDashboardCountDTO getCommitteePaperDashboardCount(CommitteePaperDashboardRQ searchRQ) {
        LOG.info("START: getCommitteePaperDashboardCount: {} ", searchRQ);
        CommitteePaperDashboardCountDTO dashboardCounts = new CommitteePaperDashboardCountDTO();
        dashboardCounts = facilityPaperJdbcDao.getCommitteePaperDashboardCount(searchRQ);
        LOG.info("dashboardCounts: {} ", dashboardCounts);
        LOG.info("END: getCommitteePaperDashboardCount: {} ", searchRQ);
        return dashboardCounts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<CommitteePaperDashboardDTO> getPagedCommitteePaperDashboard(CommitteePaperDashboardRQ committeePaperDashboardRQ) {

        LOG.info("START: getPagedCommitteePaperDashboard", committeePaperDashboardRQ);
        return facilityPaperJdbcDao.getPagedCommitteePaperDashboardDTO(committeePaperDashboardRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CommitteeStatusHistoryDTO updateCommitteeStatusHistory(CommitteeStatusHistoryDTO committeeStatusHistoryDTO, CredentialsDTO credentialsDTO) throws AppsException, IOException, SAXException {
        LOG.info("START: updateCommitteePaper status and workflow routing {}, by {}", committeeStatusHistoryDTO, credentialsDTO.getUserName());
        LOG.info("START: Committee Paper ID : {}", committeeStatusHistoryDTO.getCommitteePaperID());

        CommitteeStatusHistory committeeStatusHistory = null;
        CommitteeStatusHistory updatedCommitteeStatusHistory = null;
        Integer returnCode = null;
        Date date = new Date();

        if (committeeStatusHistoryDTO.getCommitteePaperID() != null) {
            CommitteePaper committeePaper = committeePaperDao.getOne(committeeStatusHistoryDTO.getCommitteePaperID());
            LOG.info("Committee ID : {}", committeePaper.getCommitteeID());
            LOG.info("Committee Name : {}", committeePaper.getCommitteeName());
            LOG.info("Committee Paper ID : {}", committeePaper.getCommitteePaperID());
            LOG.info("Committee Paper Status : {}", committeeStatusHistoryDTO.getCommitteePaperStatus());
            LOG.info("Committee Path : {}", committeeStatusHistoryDTO.getPathType());

            DomainConstants.FacilityPaperStatus currentFPStatus = committeePaper.getFacilityPaper().getCurrentFacilityPaperStatus();
            String committeeName = committeePaper.getCommitteeName() != null ? committeePaper.getCommitteeName() : "committee";

            if (currentFPStatus == DomainConstants.FacilityPaperStatus.IN_PROGRESS) {
                if (committeeStatusHistoryDTO.getCommitteePaperStatus().equals("RETURNED")) {
                    returnCode = facilityPaperJdbcDao.returnCommitteePaper(committeeStatusHistoryDTO, credentialsDTO);
                }

                if (committeeStatusHistoryDTO.getCommitteePaperStatus().equals("RECOMMENDED")) {
                    returnCode = facilityPaperJdbcDao.recommendCommitteePaper(committeeStatusHistoryDTO, credentialsDTO);
                }

                if (committeeStatusHistoryDTO.getCommitteePaperStatus().equals("COMMENTED")) {
                    returnCode = 3;
                }

                LOG.info("DB Return Code: {}", returnCode);

                if (returnCode == 0) {
                    committeeStatusHistory = new CommitteeStatusHistory();
                    committeeStatusHistory.setCommitteePaperStatus(committeeStatusHistoryDTO.getCommitteePaperStatus());
                    committeeStatusHistory.setFacilityPaperStatus(committeeStatusHistoryDTO.getFacilityPaperStatus());
                    committeeStatusHistory.setCreatedBy(credentialsDTO.getUserName());
                    committeeStatusHistory.setActionMessage(committeeStatusHistoryDTO.getActionMessage());
                    committeeStatusHistory.setCreatedUserDisplayName(committeeStatusHistoryDTO.getCreatedUserDisplayName());
                    committeeStatusHistory.setCreatedDate(date);
                    committeeStatusHistory.setPathType(committeeStatusHistoryDTO.getPathType());
                    committeeStatusHistory.setFacilityPaper(committeePaper.getFacilityPaper());
                    committeePaper.addCommitteeStatusHistory(committeeStatusHistory);
                    committeeStatusHistoryDTO = new CommitteeStatusHistoryDTO(committeeStatusHistory);
                    committeeStatusHistoryDTO.setIsUserAtSameLevel("Y");

                }
                if (returnCode == 2) {
                    committeeStatusHistoryDTO = new CommitteeStatusHistoryDTO();
                    committeeStatusHistoryDTO.setIsUserAtSameLevel("N");
                }

                if (returnCode == 3) {
                    committeeStatusHistory = new CommitteeStatusHistory();
                    committeeStatusHistory.setCommitteePaperStatus(committeeStatusHistoryDTO.getCommitteePaperStatus());
                    committeeStatusHistory.setFacilityPaperStatus(committeeStatusHistoryDTO.getFacilityPaperStatus());
                    committeeStatusHistory.setCreatedBy(credentialsDTO.getUserName());
                    committeeStatusHistory.setActionMessage(committeeStatusHistoryDTO.getActionMessage());
                    committeeStatusHistory.setCreatedUserDisplayName(committeeStatusHistoryDTO.getCreatedUserDisplayName());
                    committeeStatusHistory.setCreatedDate(date);
                    committeeStatusHistory.setPathType(committeeStatusHistoryDTO.getPathType());
                    committeeStatusHistory.setFacilityPaper(committeePaper.getFacilityPaper());
                    committeePaper.addCommitteeStatusHistory(committeeStatusHistory);
                    committeeStatusHistoryDTO = new CommitteeStatusHistoryDTO(committeeStatusHistory);
                }

                if (returnCode == 1) {
                    LOG.info("ERROR: committeeStatusHistoryDTO.getCommitteePaperStatus() {}", committeeStatusHistoryDTO.getCommitteePaperStatus());
                    if (committeeStatusHistoryDTO.getCommitteePaperStatus().equals("RETURNED")) {
                        LOG.info("ERROR: Failed to return this facility paper");
                        throw new AppsException("Failed to return this facility paper.");
                    }
                    if (committeeStatusHistoryDTO.getCommitteePaperStatus().equals("RECOMMENDED")) {
                        LOG.info("ERROR: Failed to recommend this facility paper");
                        throw new AppsException("Failed to recommend this facility paper.");
                    }
                }

            } else {
                LOG.info("ERROR: currentFPStatus {}", currentFPStatus);
                if (currentFPStatus == DomainConstants.FacilityPaperStatus.APPROVED) {
                    LOG.info("ERROR: This facility paper has already been approved {}", committeeName);
                    String errorMsg = "This facility paper has already been approved by " + committeeName + ".";
                    throw new AppsException(errorMsg);
                }

                if (currentFPStatus == DomainConstants.FacilityPaperStatus.CANCEL) {
                    LOG.info("ERROR: This facility paper has already been returned {}", committeeName);
                    String errorMsg = "This facility paper has already been returned by " + committeeName + ".";
                    throw new AppsException(errorMsg);
                }

                if (currentFPStatus == DomainConstants.FacilityPaperStatus.DECLINED) {
                    LOG.info("ERROR: This facility paper has already been declined {}", committeeName);
                    String errorMsg = "This facility paper has already been declined by " + committeeName + ".";
                    throw new AppsException(errorMsg);
                }
            }
        }

        LOG.info("END: updateCommitteeStatusHistory status and workflow routing {}, by {}", committeeStatusHistoryDTO, credentialsDTO.getUserName());
        return committeeStatusHistoryDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BCCPaperDashboardCountDTO getBCCPaperDashboardCount(CommitteePaperDashboardRQ searchRQ) {
        LOG.info("START: getBCCPaperDashboardCount: {} ", searchRQ);
        BCCPaperDashboardCountDTO dashboardCounts = new BCCPaperDashboardCountDTO();
        dashboardCounts = facilityPaperJdbcDao.getBCCPaperDashboardCount(searchRQ);
        LOG.info("dashboardCounts: {} ", dashboardCounts);
        LOG.info("END: getBCCPaperDashboardCount: {} ", searchRQ);
        return dashboardCounts;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<CommitteePaperDashboardDTO> getPagedBCCPaperDashboard(CommitteePaperDashboardRQ committeePaperDashboardRQ) {

        LOG.info("START: getPagedBCCPaperDashboard", committeePaperDashboardRQ);
        return facilityPaperJdbcDao.getPagedBCCPaperDashboardDTO(committeePaperDashboardRQ);
    }

    //submit BCC decision and authorize BCC Paper
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FPBccDTO updateBccDTO(FPBccDTO fpBccDTO, CredentialsDTO credentialsDTO) throws AppsException, IOException, SAXException {
        LOG.info("START: update BBC Paper status and workflow routing {}, by {}", fpBccDTO, credentialsDTO.getUserName());

        Date date = new Date();
        FPBcc fpBcc = null;
        FacilityPaper facilityPaper = null;
        AppsConstants.BccStatus bccStatus = null;
        DomainConstants.MasterDataApproveStatus approveStatus = null;
        List<String> emailAddress = new ArrayList<>();
        String subject = "";
        String templateName = "";

        bccStatus = fpBccDTO.getBccStatus().equals("APPROVED") ? AppsConstants.BccStatus.APPROVED : fpBccDTO.getBccStatus().equals("DECLINED") ?
                AppsConstants.BccStatus.DECLINED : null;

        approveStatus = fpBccDTO.getApproveStatus().equals("APPROVED") ? DomainConstants.MasterDataApproveStatus.APPROVED :
                fpBccDTO.getApproveStatus().equals("REJECTED") ? DomainConstants.MasterDataApproveStatus.REJECTED :
                        fpBccDTO.getApproveStatus().equals("PENDING") ? DomainConstants.MasterDataApproveStatus.PENDING : null;

        boolean isNewBcc = fpBccDTO.getFpBccId() == null;
        LOG.info("START: BCC Paper ID : {}", fpBccDTO.getFpBccId());
        LOG.info("START: BCC Paper Status : {}", bccStatus);
        LOG.info("START: BCC Approval Status : {}", approveStatus);

        if (isNewBcc) {
            fpBcc = new FPBcc();
            fpBcc.setCreatedBy(credentialsDTO.getUserName());
            fpBcc.setCreatedDate(date);
            fpBcc.setCreatedUserDisplayName(fpBccDTO.getCreatedUserDisplayName());
        } else {
            fpBcc = fpBccDao.getOne(fpBccDTO.getFpBccId());
        }

        facilityPaper = facilityPaperDao.findById(fpBccDTO.getFacilityPaperID()).get();
        fpBcc.setApprovedUserDisplayName(fpBccDTO.getApprovedUserDisplayName());
        fpBcc.setApprovedBy(credentialsDTO.getUserName());
        fpBcc.setApprovedDate(date);
        fpBcc.setApproveStatus(approveStatus);
        fpBcc.setActionMessage(fpBccDTO.getActionMessage());
        fpBcc.setBccStatus(bccStatus);
        fpBcc.setFacilityPaper(facilityPaper);

        if (approveStatus.equals(DomainConstants.MasterDataApproveStatus.PENDING)) {
            if (bccStatus.equals(AppsConstants.BccStatus.APPROVED)) {
                subject = "Facility Paper Approved - BCC Paper Ref No : " + facilityPaper.getFpRefNumber() + " (" + facilityPaper.getCurrentFacilityPaperStatus() + ")";
                templateName = "bcc-authorize";

                facilityPaper.setApprovedDate(fpBccDTO.getBccMeetingDate());
                facilityPaper.setRejectedDate(null);
            }

            if (bccStatus.equals(AppsConstants.BccStatus.DECLINED)) {
                subject = "Facility Paper Declined - BCC Paper Ref No : " + facilityPaper.getFpRefNumber() + " (" + facilityPaper.getCurrentFacilityPaperStatus() + ")";
                templateName = "bcc-declined";

                facilityPaper.setRejectedDate(fpBccDTO.getBccMeetingDate());
                facilityPaper.setApprovedDate(null);
            }

            emailAddress = fpBccDTO.getBccAuthRecipients();


            AppsConstants.Status fileStatus = AppsConstants.Status.ACT;
            List<FPBccDocument> fpBccDocument = fpBccDocumentDao.findByFpBccAndStatus(fpBccDTO.getFpBccId(), fileStatus.name());

            for (FPBccDocument fpBccDocument1 : fpBccDocument) {
                fpBccDocument1.setApproveStatus(DomainConstants.MasterDataApproveStatus.PENDING);

                FPBccDocument newFbDoc = fpBccDocumentDao.saveAndFlush(fpBccDocument1);
            }

        }

        if (approveStatus.equals(DomainConstants.MasterDataApproveStatus.APPROVED)) {

            for (String usersId : fpBccDTO.getBccAuthRecipientIds()) {
                UpmDetailResponse userUPMDetails = securityService.getUserUPMDetailsById(usersId);
                if (!userUPMDetails.getEmail().isEmpty()) {
                    emailAddress.add(userUPMDetails.getEmail());
                }
            }

            if (bccStatus.equals(AppsConstants.BccStatus.APPROVED)) {
                //fpBcc.setActionMessage("Approved by Board Credit Committee");
                fpBcc.setActionMessage(fpBccDTO.getActionMessage());
                facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.APPROVED);
                //facilityPaper.setApprovedDate(date);

                subject = "Facility Paper Approved - BCC Paper Ref No : " + facilityPaper.getFpRefNumber() + " (" + facilityPaper.getCurrentFacilityPaperStatus() + ")";
                templateName = "bcc-authorize";
            }

            if (bccStatus.equals(AppsConstants.BccStatus.DECLINED)) {
                //  fpBcc.setActionMessage("Declined by Board Credit Committee");
                fpBcc.setActionMessage(fpBccDTO.getActionMessage());
                facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.REJECTED);
                //facilityPaper.setRejectedDate(date);

                subject = "Facility Paper Declined - BCC Paper Ref No : " + facilityPaper.getFpRefNumber() + " (" + facilityPaper.getCurrentFacilityPaperStatus() + ")";
                templateName = "bcc-declined";
            }

            AppsConstants.Status fileStatus = AppsConstants.Status.ACT;
            List<FPBccDocument> fpBccDocument = fpBccDocumentDao.findByFpBccAndStatus(fpBccDTO.getFpBccId(), fileStatus.name());

            for (FPBccDocument fpBccDocument1 : fpBccDocument) {
                fpBccDocument1.setApproveStatus(DomainConstants.MasterDataApproveStatus.APPROVED);

                FPBccDocument newFbDoc = fpBccDocumentDao.saveAndFlush(fpBccDocument1);
            }
            fpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.APPROVED);

            facilityPaper.setModifiedDate(date);
            facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        }

        if (approveStatus.equals(DomainConstants.MasterDataApproveStatus.REJECTED)) {
            subject = "Facility Paper Rejected - BCC Paper Ref No : " + facilityPaper.getFpRefNumber() + " (" + facilityPaper.getCurrentFacilityPaperStatus() + ")";
            templateName = "bcc-rejected";

            emailAddress = fpBccDTO.getBccAuthRecipients();

            fpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.REJECTED);

            AppsConstants.Status fileStatus = AppsConstants.Status.ACT;
            List<FPBccDocument> fpBccDocument = fpBccDocumentDao.findByFpBccAndStatus(fpBccDTO.getFpBccId(), fileStatus.name());

            for (FPBccDocument fpBccDocument1 : fpBccDocument) {
                fpBccDocument1.setApproveStatus(DomainConstants.MasterDataApproveStatus.REJECTED);

                FPBccDocument newFbDoc = fpBccDocumentDao.saveAndFlush(fpBccDocument1);
            }
        }

        if (approveStatus.equals(DomainConstants.MasterDataApproveStatus.APPROVED) || approveStatus.equals(DomainConstants.MasterDataApproveStatus.REJECTED) || bccStatus.equals(AppsConstants.BccStatus.APPROVED) || bccStatus.equals(AppsConstants.BccStatus.DECLINED)) {

            if (!fpBccDTO.getAttachments().isEmpty()) {

                //get email request body
                FacilityPaperStatusTransitionRQ emailRQ = getEmailRequestBody(fpBccDTO.getFacilityPaperID(), credentialsDTO);

                //set last comment from BCC
                if (!fpBccDTO.getBccActionComment().isEmpty() && !fpBccDTO.getCommentUserDisplayName().isEmpty()) {
                    emailRQ.setLastComment(fpBccDTO.getBccActionComment());
                    emailRQ.setLastCommentedUser(fpBccDTO.getCommentUserDisplayName());
                }

                //send email
                sendCommitteeEmail(emailAddress, fpBccDTO.getAttachments(), subject, templateName, emailRQ);
            }
        }

        if (fpBcc.getApproveStatus() == DomainConstants.MasterDataApproveStatus.PENDING) {
            fpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.PENDING);
        }

        fpBcc.setFacilityPaper(facilityPaper);
        fpBcc = fpBccDao.saveAndFlush(fpBcc);
        fpBccDTO = new FPBccDTO(fpBcc);

        LOG.info("END: update BBC Paper  status and workflow routing {}, by {}", fpBccDTO, credentialsDTO.getUserName());
        return fpBccDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<FPStatusHistoryDTO> getFPUsersInvolved(Integer facilityPaperID) throws AppsException {
        LOG.info("START: get Facility Paper Involved Users List : {} ", facilityPaperID);
        return facilityPaperJdbcDao.getFPUsersInvolvedList(facilityPaperID);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO copyFacilities(FacilityAndSecurityCopyDTO facilityDTO, CredentialsDTO credentialsDTO) throws AppsException {

        FacilityPaperFacilityCopyBuilder facilityCopyBuilder = new FacilityPaperFacilityCopyBuilder(credentialsDTO, facilityDTO);
        facilityCopyBuilder.setFacilityPaperDao(facilityPaperDao);
        facilityCopyBuilder.setDocStorageDao(docStorageDao);
        facilityCopyBuilder.setFacilitySecurityDao(facilitySecurityDao);
        facilityCopyBuilder.setFacilityPaperService(this);

        FacilityPaper facilityPaper = facilityCopyBuilder.buildInitialData()
                .buildFacilities()
                .getCopyFacilities();

        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);

        FacilityPaperFacilityCopyBuilder facilityCopyBuilderAfterCopy = new FacilityPaperFacilityCopyBuilder(credentialsDTO, facilityDTO);
        facilityCopyBuilderAfterCopy.setFacilityPaperDao(facilityPaperDao);
        facilityCopyBuilderAfterCopy.setDocStorageDao(docStorageDao);
        facilityCopyBuilderAfterCopy.setFacilitySecurityDao(facilitySecurityDao);
        facilityCopyBuilderAfterCopy.setFacilityPaperService(this);

        FacilityPaper facilityAfterCopy = facilityCopyBuilderAfterCopy.buildInitialData()
                .buildCalculateFacilityPaperExposure()
                .buildBaseFacilityData()
                .getCopyFacilities();

        facilityPaperDao.saveAndFlush(facilityAfterCopy);

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCustomerRatings();
        loadOptionDTO.loadShareHolderDetail();
        FacilityLoadOptionDTO facilityLoadOptionDTO = new FacilityLoadOptionDTO();
        facilityLoadOptionDTO.loadAllData();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOptionDTO);

        LOG.info("END : copy facilities for : {} : {}", facilityDTO, credentialsDTO.getUserID());
        return new FacilityPaperDTO(facilityAfterCopy, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO updateFacilityPaperType(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws SAXException, AppsException, IOException {

        LOG.info("START : Facility Paper update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        FacilityPaperDTO previousFacilityPaperDTO = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(facilityPaperDTO.getFacilityPaperID());
        previousFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        facilityPaper.setIsCommittee(facilityPaperDTO.getIsCommittee());
        facilityPaper.setBankAccountID(facilityPaperDTO.getBankAccountID());

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);

        if (facilityPaperDTO.getIsCommittee() != previousFacilityPaperDTO.getIsCommittee()) {
            updateFacilityPaperExposure(facilityPaperDTO, credentialsDTO);
        }

        FacilityPaperDTO updateFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);

        LOG.info("END : Facility Paper update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());

        return updateFacilityPaperDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<FPCommitteeSignatureDTO> getFPCommitteeSignatureList(Integer facilityPaperID) throws AppsException {
        LOG.info("START: getFPCommitteeSignatureList : {} ", facilityPaperID);
        LOG.info("get cas application code : {} ", appCode);

        List<FPCommitteeSignatureDTO> fpCommitteeSignatureDTOList =
                facilityPaperJdbcDao.getFPCommitteeSignatureList(facilityPaperID, integrationService.getRiskDivCode());

        LOG.info("fpCommitteeSignatureDTOList size: {} ", fpCommitteeSignatureDTOList.size());
        UpmDetailResponse upmDetailResponse = null;
        UpmDetailLoadRQ upmDetailLoadRQ = new UpmDetailLoadRQ();
        upmDetailLoadRQ.setAppCode(appCode);

        if (fpCommitteeSignatureDTOList != null) {
            for (FPCommitteeSignatureDTO fpCommitteeSignatureDTO : fpCommitteeSignatureDTOList) {
                LOG.info("fpCommitteeSignatureDTO: {} ", fpCommitteeSignatureDTO);
                upmDetailLoadRQ.setUserID(fpCommitteeSignatureDTO.getAssignUserID());
                upmDetailResponse = integrationService.getUpmDetailsByUserIdAndAppCode(upmDetailLoadRQ);

                if (upmDetailResponse.getFunctionCode3() != null) {
                    LOG.info("assign user functional units: {} ", upmDetailResponse.getFunctionCode3());
                    fpCommitteeSignatureDTO.setAssignUserFunctionalUnits(upmDetailResponse.getFunctionCode3());
                }

                if (fpCommitteeSignatureDTO.getAssignUserDivType().equals("DEPT") &&
                        fpCommitteeSignatureDTO.getAssignUserDesignation() == null) {
                    if (upmDetailResponse.getDesignationDescription() != null) {
                        LOG.info("committee signature user id : {} ", fpCommitteeSignatureDTO.getAssignUserID());
                        LOG.info("committee signature user designation : {} ", upmDetailResponse.getDesignationDescription());
                        fpCommitteeSignatureDTO.setAssignUserDesignation(upmDetailResponse.getDesignationDescription());
                    }
                }
            }
        }

        LOG.info("END : getFPCommitteeSignatureList : {}", fpCommitteeSignatureDTOList);

        return fpCommitteeSignatureDTOList;
    }

    public void sendCommitteeEmail(List<String> emailAddress, List<EmailAttachment> attachments, String subject, String templateName, FacilityPaperStatusTransitionRQ emailRQ) throws AppsException {

        EmailSendRequest emailSendRequest = new EmailSendRequest();

        emailSendRequest.setPurpose(DomainConstants.EmailPurpose.OTHER);
        emailSendRequest.setSubject(subject);
        emailSendRequest.setTemplateName(templateName);
        emailSendRequest.setToAddresses(emailAddress);
        emailSendRequest.setAttachments(attachments);
        emailSendRequest.setSendType(DomainConstants.EmailSendType.HTML);

        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperStatusTransitionRQ", emailRQ);
        emailSendRequest.setParams(params);
        casEmailService.sendMail(emailSendRequest);
    }

    public FacilityPaperStatusTransitionRQ getEmailRequestBody(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {
        BranchLoadResponseListDTO branchLoadResponseListDTO = integrationService.getBranchList(credentialsDTO);

        FacilityPaper facilityPaper = facilityPaperDao.getOne(facilityPaperID);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadUpcSectionData();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCreditRiskComments();
        loadOptionDTO.loadApprovedFacilityDocuments();
        loadOptionDTO.loadShareHolderDetail();

        FacilityLoadOptionDTO facilityLoadOption = new FacilityLoadOptionDTO();
        facilityLoadOption.loadAllData();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOption);

        FacilityPaperDTO updatedFacilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);

        FacilityPaperStatusTransitionRQ emailRQ = new FacilityPaperStatusTransitionRQ();
        emailRQ.setSolID(facilityPaper.getBranchCode());
        emailRQ.setCredentialsDTO(credentialsDTO);
        emailRQ.setUserName(updatedFacilityPaperDTO.getAssignUserDisplayName());
        emailRQ.setFacilityPaperID(facilityPaper.getFacilityPaperID());

        if (updatedFacilityPaperDTO.getCasCustomerDTOList() != null && !updatedFacilityPaperDTO.getCasCustomerDTOList().isEmpty()) {
            emailRQ.setCustomerName(updatedFacilityPaperDTO.getCasCustomerDTOList().get(0).getCustomerName());
        }
        if (updatedFacilityPaperDTO.getFpCommentDTOList() != null && !updatedFacilityPaperDTO.getFpCommentDTOList().isEmpty()) {
            List<FPCommentDTO> comments = updatedFacilityPaperDTO.getFpCommentDTOList();
            emailRQ.setLastComment(comments.get(0).getComment());
        }
        emailRQ.setAssignUserDisplayName(updatedFacilityPaperDTO.getAssignUserDisplayName());

        if (facilityPaper.getIsCommittee() != null && facilityPaper.getIsCommittee() == AppsConstants.YesNo.Y) {

            //Set Group Exposure
            if (facilityPaper.getGroupNetTotalExposureNew() != null) {
                if (NumberUtil.getFormattedAmount(facilityPaper.getGroupNetTotalExposureNew()) != null) {
                    emailRQ.setTotalGroupExposure(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(facilityPaper.getGroupNetTotalExposureNew())));
                } else {
                    emailRQ.setTotalGroupExposure("0.00");
                }
            } else {
                emailRQ.setTotalGroupExposure("0.00");
            }

            //Set Company Exposure
            if (facilityPaper.getNetTotalExposureNew() != null) {
                if (NumberUtil.getFormattedAmount(facilityPaper.getNetTotalExposureNew()) != null) {
                    emailRQ.setTotalCompanyExposure(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(facilityPaper.getNetTotalExposureNew())));
                } else {
                    emailRQ.setTotalCompanyExposure("0.00");
                }
            } else {
                emailRQ.setTotalCompanyExposure("0.00");
            }

        } else {
            //Set Group Exposure
            if (facilityPaper.getGroupTotalExposureNew() != null) {
                if (NumberUtil.getFormattedAmount(facilityPaper.getGroupTotalExposureNew()) != null) {
                    emailRQ.setTotalGroupExposure(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(facilityPaper.getGroupTotalExposureNew())));
                } else {
                    emailRQ.setTotalGroupExposure("0.00");
                }
            } else {
                emailRQ.setTotalGroupExposure("0.00");
            }

            //Set Company Exposure
            if (facilityPaper.getTotalExposureNew() != null) {
                if (NumberUtil.getFormattedAmount(facilityPaper.getTotalExposureNew()) != null) {
                    emailRQ.setTotalCompanyExposure(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(facilityPaper.getTotalExposureNew())));
                } else {
                    emailRQ.setTotalCompanyExposure("0.00");
                }
            } else {
                emailRQ.setTotalCompanyExposure("0.00");
            }

        }
        emailRQ.setLastCommentedUser(credentialsDTO.getDisplayName());
        emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCreatedDate()));
        emailRQ.setBranchName(branchLoadResponseListDTO.getBranchResponse(facilityPaper.getBranchCode()).getBranchName());
        emailRQ.setShowActiveNewFacilities(false);
        for (FacilityDTO facility : updatedFacilityPaperDTO.getFacilityDTOList()) {
            if (facility.getIsNew() == AppsConstants.YesNo.Y) {
                emailRQ.getFacilityDTOList().add(facility);
                emailRQ.setShowActiveNewFacilities(true);
            }
        }
        if (facilityPaper.getApprovedDate() != null) {
            emailRQ.setPaperApprovedDate(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getApprovedDate()));
        }

        if (facilityPaper.getRejectedDate() != null) {
            emailRQ.setPaperDeclinedDate(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getRejectedDate()));
        }

        // only for credit admin notification
        if (facilityPaper.getUpcTemplate().getTemplateName().contains("Review")) {
            emailRQ.setFacilityPaperRefNumber(facilityPaper.getFpRefNumber());
            emailRQ.setAccountNumber(facilityPaper.getBankAccountID());

            if (updatedFacilityPaperDTO.getFpUpcSectionDataDTOList().stream().anyMatch(data -> data.getUpcSectionName().equals("Review Charges"))) {
                FPUpcSectionDataDTO upcSectionData = updatedFacilityPaperDTO.getFpUpcSectionDataDTOList().stream()
                        .filter(data -> data.getUpcSectionName().equals("Review Charges")).findFirst().get();

                emailRQ.setReviewChargeUPCData(upcSectionData.getData());
            }
        }

        return emailRQ;
    }

    public List<FPUPCTemplateComparisonDateDTO> getUPCTemplateComparisonByDateService(FusTraceDTO fpUPCTemplateComparisonRQ, CredentialsDTO credentialsDTO) {
        List<FPUPCTemplateComparisonDateDTO> resultSet = new ArrayList<>();
        List<FPUPCTemplateComparisonDateDTO> fpUPCTemplateComparisonDateDTOList = facilityPaperJdbcDao.getUPCTemplateComparisonByDateRepository(fpUPCTemplateComparisonRQ);

        fpUPCTemplateComparisonDateDTOList.forEach((res) -> {
            FPUPCTemplateComparisonDateDTO FPUPCTemplateComparisonDateDTO = new FPUPCTemplateComparisonDateDTO();

            FusTraceDTO FusTraceDTO = new FusTraceDTO();
            FusTraceDTO.setFacilityPaperId(fpUPCTemplateComparisonRQ.getFacilityPaperId());
            FusTraceDTO.setFpUPCSectionDataId(fpUPCTemplateComparisonRQ.getFpUPCSectionDataId());
            FusTraceDTO.setUpcSectionId(fpUPCTemplateComparisonRQ.getUpcSectionId());
            FusTraceDTO.setCreatedDate(res.getCreatedDate());

            List<FPUPCTemplateComparisonContentDTO> FPUPCTemplateComparisonContentDTOList = facilityPaperJdbcDao.getUPCTemplateComparisonDataRepository(FusTraceDTO);
            List<FPUPCTemplateComparisonContentDTO> FinalFPUPCTemplateComparisonContentDTOList = new ArrayList<>();

            FPUPCTemplateComparisonContentDTOList.forEach((res1) -> {
                FPUPCTemplateComparisonContentDTO FPUPCTemplateComparisonContentDTO = new FPUPCTemplateComparisonContentDTO();
                FPUPCTemplateComparisonContentDTO.setFpUPCSectionDataId(res1.getFpUPCSectionDataId());
                FPUPCTemplateComparisonContentDTO.setModifiedUserDisplayName(res1.getModifiedUserDisplayName());
                FPUPCTemplateComparisonContentDTO.setFpUPCSectionDataHistoryID(res1.getFpUPCSectionDataHistoryID());
                FPUPCTemplateComparisonContentDTO.setData(res1.getData());
                FPUPCTemplateComparisonContentDTO.setComment(res1.getComment());
                FPUPCTemplateComparisonContentDTO.setStatus(res1.getStatus());
                FPUPCTemplateComparisonContentDTO.setCreatedBy(res1.getCreatedBy());
                FPUPCTemplateComparisonContentDTO.setModifiedDate(res1.getModifiedDate());

                FinalFPUPCTemplateComparisonContentDTOList.add(FPUPCTemplateComparisonContentDTO);
            });

            FPUPCTemplateComparisonDateDTO.setCreatedDate(res.getCreatedDate());
            FPUPCTemplateComparisonDateDTO.setHistoryList(FinalFPUPCTemplateComparisonContentDTOList);

            resultSet.add(FPUPCTemplateComparisonDateDTO);
        });
        return resultSet;
    }

    public Boolean saveFusTraceService(FusTraceDTO fusTraceDto, CredentialsDTO credentialsDTO) {
        Date date = new Date();
        FUSTraceRQ fusTraceRQ = new FUSTraceRQ();

        fusTraceRQ.setId(fusTraceDto.getId());
        fusTraceRQ.setMainKey(fusTraceDto.getMainKey());
        fusTraceRQ.setSubKey(fusTraceDto.getSubKey());
        fusTraceRQ.setParentRecId(fusTraceDto.getParentRecId());
        fusTraceRQ.setCreatedBy(credentialsDTO.getUserName());
        fusTraceRQ.setCreatedDate(date);
        fusTraceRQ.setComment(fusTraceDto.getComment());
        fusTraceRQ.setFlag(fusTraceDto.getFlag());
        fusTraceRQ.setStatus("ACT");
        fusTraceRQ.setCreatedUserDisplayName(credentialsDTO.getDisplayName());
        fusTraceRQ.setModifiedDate(date);
        fusTraceRQ.setModifiedBy(credentialsDTO.getUserName());
        fusTraceRQ.setCreatedUserWC(fusTraceDto.getCreatedUserWC());
        fusTraceRQ.setCreatedUserDesignation(fusTraceDto.getCreatedUserDesignation());

        return facilityPaperJdbcDao.saveFUSTraceRepository(fusTraceRQ);
    }

    public Boolean saveFusTraceView(FusTraceDTO fusTraceDTO, CredentialsDTO credentialsDTO) {
        return facilityPaperJdbcDao.saveFUSTraceView(fusTraceDTO.getMainKey(), fusTraceDTO.getFlag(), credentialsDTO.getDisplayName());
    }

    public List<UPCTemplateCommentHistoryDTO> getFusTraceDataService(FusTraceDTO fusTraceDTO, CredentialsDTO credentialsDTO) {
        return facilityPaperJdbcDao.getFusTraceDataRepository(fusTraceDTO, credentialsDTO);
    }

    public ArrayList<Integer> getUPCSectionHistoryDataByIdService(FusTraceDTO fusTraceDTO, CredentialsDTO credentialsDTO) {
        return facilityPaperJdbcDao.getUPCSectionHistoryDataByIdRepository(fusTraceDTO, credentialsDTO);
    }

    public List<FPUpcSectionDataDTO> getUPCSectionsDataByFacilityPaperId(Integer fpId) {

        List<FPUpcSectionDataDTO> result = new ArrayList<>();

        try {
            result = facilityPaperJdbcDao.getSectionsDataByFacilityPaperId(fpId);
        } catch (Exception e) {
            result = null;
            LOG.info("Exception : GET UPC SECTIONS DATA BY FP ID :", e);
        }

        return result;
    }

    public FPUpcSectionDataDTO getUPCSectionDataBySectionId(Integer fpId, Integer sectionId) {

        FPUpcSectionDataDTO result = new FPUpcSectionDataDTO();

        try {
            result = facilityPaperJdbcDao.getSectionDataBySectionId(fpId, sectionId);
        } catch (Exception e) {
            result = null;
            LOG.info("Exception : GET UPC SECTION DATA BY SECTION ID :", e);
        }

        return result;
    }

    public List<UPCTemplateCommentHistoryDTO> getFusTracesByFacilityPaper(Integer fpId, String flag, CredentialsDTO credentialsDTO) {

        List<UPCTemplateCommentHistoryDTO> result = new ArrayList<>();

        try {
            result = facilityPaperJdbcDao.getFusTraceDataByFacilityPaper(fpId, flag, credentialsDTO);
        } catch (Exception e) {
            result = null;
            LOG.info("Exception : GET FUS TRACE BU FP :", e);
        }

        return result;
    }

    public Boolean removeFusTrace(FusTraceDTO fusTraceDTO) {
        return facilityPaperJdbcDao.removeFUSTrace(fusTraceDTO.getId());
    }

    public Integer getCommitteeButtonEnableData(Integer facilityPaperID, CredentialsDTO credentialsDTO) {
        return facilityPaperJdbcDao.getCommitteeButtonEnableData(facilityPaperID, credentialsDTO);
    }

    public void sendCAPaperEmail(CAEmailDTO caEmailDTO, CredentialsDTO credentialsDTO) throws AppsException {
        if (caEmailDTO.getIsCommittee() == AppsConstants.YesNo.Y && caEmailDTO.getAssignDepartmentCode() != null && caEmailDTO.getAssignDepartmentCode().equals("CA")
                && caEmailDTO.getCurrentAssignUser() != null && !caEmailDTO.getCurrentAssignUser().equals("BCC")) {

            boolean isFPPresent = facilityPaperDao.findById(caEmailDTO.getFacilityPaperID()).isPresent();
            if (isFPPresent) {
                FacilityPaper facilityPaper = facilityPaperDao.findById(caEmailDTO.getFacilityPaperID()).get();
                CommitteePaper committeePaper = facilityPaper.getCommitteePaperByFacilityPaperID(caEmailDTO.getFacilityPaperID());

                if (facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.IN_PROGRESS || facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.CANCEL) {
                    if (committeePaper != null) {
                        if (caEmailDTO.getIsCommitteePaperReturn()) {
                            sendReturnEmailToUser(committeePaper.getCommitteePaperID(), caEmailDTO.getCommitteePaperReturnUser(), caEmailDTO, credentialsDTO);
                        } else {
                            Integer currentLevel = 0;
                            if (!caEmailDTO.getIsForwardToCA()) {
                                if (Objects.equals(caEmailDTO.getCurrentPath(), committeePaper.getCurrentPath())) {
                                    if (Objects.equals(caEmailDTO.getCurrentPath(), AppsConstants.CAPathType.REG.toString())) {
                                        currentLevel = caEmailDTO.getCurrentRegLevelID();
                                    }
                                    if (Objects.equals(caEmailDTO.getCurrentPath(), AppsConstants.CAPathType.ALT.toString())) {
                                        currentLevel = caEmailDTO.getCurrentAltLevelID();
                                    }
                                }
                            }

                            sendEmailToLevelUsers(committeePaper.getCommitteePaperID(), currentLevel, caEmailDTO, credentialsDTO);
                        }
                    }
                }

                if (facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.APPROVED) {
                    if (committeePaper != null) {
                        sendApproveEmailToUsers(committeePaper.getCommitteePaperID(), caEmailDTO, credentialsDTO);
                    }
                }
            }
        }
    }

    public void sendEmailToLevelUsers(Integer committeePaperId, Integer actionedUserLevel, CAEmailDTO caEmailDTO, CredentialsDTO credentialsDTO) throws AppsException {

        try {
            if (committeePaperId != null && committeePaperId != 0) {
                CommitteePaper committeePaper = committeePaperDao.getOne(committeePaperId);
                AppsConstants.CAPathType currentPath = AppsConstants.CAPathType.valueOf(committeePaper.getCurrentPath());
                Integer currentLevel = 0;

                if (Objects.equals(currentPath, AppsConstants.CAPathType.REG)) {
                    currentLevel = committeePaper.getCurrentRegLevelID();
                }
                if (Objects.equals(currentPath, AppsConstants.CAPathType.ALT)) {
                    currentLevel = committeePaper.getCurrentAltLevelID();
                }

                if (actionedUserLevel == 0 || !Objects.equals(currentLevel, actionedUserLevel)) {
                    CALevel caLevel = caLevelDao.findByCaCommitteeCommitteeIdAndLevelIdAndPathType(committeePaper.getCommitteeID(), currentLevel, currentPath);
                    if (caLevel != null) {
                        List<CAUser> levelUsers = caUserDao.findByCaLevelLevelConfigId(caLevel.getLevelConfigId());
                        List<FPActionDTO> involvedUserActions = this.facilityPaperJdbcDao.getCommitteePaperInvolvedUsers(committeePaper.getCommitteePaperID());

                        List<String> levelUserEmailList = new ArrayList<>();

                        if (levelUsers != null && !levelUsers.isEmpty()) {
                            for (CAUser caUser : levelUsers) {
                                String email = this.integrationService.getEmailByUserAD(caUser.getUserPool().getUserName());
                                if (!email.isEmpty()) {
                                    levelUserEmailList.add(email);
                                }
                            }
                            if (!levelUserEmailList.isEmpty()) {
                                List<EmailAttachment> emailAttachments = new ArrayList<>();
                                String committeeName = committeePaper.getCommitteeName() != null ? committeePaper.getCommitteeName() : "Committee";
                                String subject = "Facility Paper -" + committeeName + " Paper Ref No : " + committeePaper.getFacilityPaper().getFpRefNumber() + " (In Progress)";
                                String template = "committee_paper_update";

                                //get email request body
                                FacilityPaperStatusTransitionRQ emailRQ = getEmailRequestBody(committeePaper.getFacilityPaper().getFacilityPaperID(), credentialsDTO);
                                emailRQ.setCommitteeName(committeeName);
                                emailRQ.setCommitteeAction(" has been forwarded to ");
                                emailRQ.setLastCommentedUser(caEmailDTO.getRecentCommentedBy());
                                emailRQ.setLastComment(caEmailDTO.getRecentComment());

                                if (involvedUserActions.isEmpty()) {
                                    emailRQ.setShowActions(false);
                                } else {
                                    emailRQ.setShowActions(true);
                                    emailRQ.setMembersActions(involvedUserActions);
                                }

                                LOG.info("LOG : levelUserEmailList :{}", levelUserEmailList);
                                LOG.info("LOG : emailRQ :{}", emailRQ);
                                sendCommitteeEmail(levelUserEmailList, emailAttachments, subject, template, emailRQ);
                            }
                        }
                    }
                } else {
                    if (actionedUserLevel != 0 && Objects.equals(currentLevel, actionedUserLevel)) {
                        CALevel caLevel = caLevelDao.findByCaCommitteeCommitteeIdAndLevelIdAndPathType(committeePaper.getCommitteeID(), currentLevel, currentPath);
                        if (caLevel != null) {
                            List<CAUser> levelUsers = caUserDao.findByCaLevelLevelConfigId(caLevel.getLevelConfigId());
                            List<FPActionDTO> involvedUserActions = this.facilityPaperJdbcDao.getCommitteePaperInvolvedUsers(committeePaper.getCommitteePaperID());

                            List<String> levelUserEmailList = new ArrayList<>();

                            if (levelUsers != null && !levelUsers.isEmpty()) {
                                for (CAUser caUser : levelUsers) {

                                    if (involvedUserActions.stream().noneMatch(u -> u.getCreatedBy().equals(caUser.getCreatedBy()))) {
                                        String email = this.integrationService.getEmailByUserAD(caUser.getUserPool().getUserName());
                                        if (!email.isEmpty()) {
                                            levelUserEmailList.add(email);
                                        }
                                    }
                                }
                                if (!levelUserEmailList.isEmpty()) {
                                    List<EmailAttachment> emailAttachments = new ArrayList<>();
                                    String committeeName = committeePaper.getCommitteeName() != null ? committeePaper.getCommitteeName() : "Committee";
                                    String subject = "Facility Paper -" + committeeName + " Paper Ref No : " + committeePaper.getFacilityPaper().getFpRefNumber() + " (In Progress)";
                                    String template = "committee_paper_update";

                                    //get email request body
                                    FacilityPaperStatusTransitionRQ emailRQ = getEmailRequestBody(committeePaper.getFacilityPaper().getFacilityPaperID(), credentialsDTO);
                                    emailRQ.setCommitteeName(committeeName);
                                    emailRQ.setCommitteeAction(" has been attended by a member of ");
                                    emailRQ.setLastCommentedUser(caEmailDTO.getRecentCommentedBy());
                                    emailRQ.setLastComment(caEmailDTO.getRecentComment());

                                    if (!involvedUserActions.isEmpty()) {
                                        emailRQ.setShowActions(true);
                                        emailRQ.setMembersActions(involvedUserActions);
                                    } else {
                                        emailRQ.setShowActions(false);
                                    }

                                    LOG.info("LOG : levelUserEmailList :{}", levelUserEmailList);
                                    LOG.info("LOG : emailRQ :{}", emailRQ);
                                    sendCommitteeEmail(levelUserEmailList, emailAttachments, subject, template, emailRQ);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("Exception : sendEmailToLevelUsers :", e);
        }
    }

    public void sendReturnEmailToUser(Integer committeePaperId, String receivedUser, CAEmailDTO caEmailDTO, CredentialsDTO credentialsDTO) throws AppsException {
        try {
            if (committeePaperId != null && committeePaperId != 0) {
                CommitteePaper committeePaper = committeePaperDao.getOne(committeePaperId);
                List<String> userEmailList = new ArrayList<>();
                List<FPActionDTO> involvedUserActions = this.facilityPaperJdbcDao.getCommitteePaperInvolvedUsers(committeePaper.getCommitteePaperID());

                String email = this.integrationService.getEmailByUserAD(receivedUser);
                if (!email.isEmpty()) {
                    userEmailList.add(email);
                }

                if (!userEmailList.isEmpty()) {
                    List<EmailAttachment> emailAttachments = new ArrayList<>();
                    String committeeName = committeePaper.getCommitteeName() != null ? committeePaper.getCommitteeName() : "Committee";
                    String subject = "Facility Paper -" + committeeName + " Paper Ref No : " + committeePaper.getFacilityPaper().getFpRefNumber() + " (Return)";
                    String template = "committee_paper_update";

                    //get email request body
                    FacilityPaperStatusTransitionRQ emailRQ = getEmailRequestBody(committeePaper.getFacilityPaper().getFacilityPaperID(), credentialsDTO);
                    emailRQ.setCommitteeName(committeeName);
                    emailRQ.setCommitteeAction(" has been returned from ");
                    emailRQ.setLastCommentedUser(caEmailDTO.getRecentCommentedBy());
                    emailRQ.setLastComment(caEmailDTO.getRecentComment());

                    if (involvedUserActions.isEmpty()) {
                        emailRQ.setShowActions(false);
                    } else {
                        emailRQ.setShowActions(true);
                        emailRQ.setMembersActions(involvedUserActions);
                    }
                    LOG.info("LOG : levelUserEmailList :{}", userEmailList);
                    LOG.info("LOG : emailRQ :{}", emailRQ);
                    sendCommitteeEmail(userEmailList, emailAttachments, subject, template, emailRQ);
                }

            }
        } catch (Exception e) {
            LOG.info("Exception : sendEmailToLevelUsers :", e);
        }
    }

    public void sendApproveEmailToUsers(Integer committeePaperId, CAEmailDTO caEmailDTO, CredentialsDTO credentialsDTO) throws AppsException {
        try {
            if (committeePaperId != null && committeePaperId != 0) {
                CommitteePaper committeePaper = committeePaperDao.getOne(committeePaperId);

                List<String> levelUserEmailList = new ArrayList<>();

                List<FPActionDTO> involvedUsers = this.facilityPaperJdbcDao.getCommitteePaperInvolvedUsers(committeePaper.getCommitteePaperID());
                List<FPActionDTO> involveFPdUsers = this.facilityPaperJdbcDao.getFacilityPaperInvolvedUsers(committeePaper.getFacilityPaper().getFacilityPaperID());

                List<FPActionDTO> involvedUserActions = new ArrayList<>(involvedUsers);
                involvedUsers.addAll(involveFPdUsers);

                if (!involvedUsers.isEmpty()) {
                    for (FPActionDTO fpActionDTO : involvedUsers) {
                        String email = this.integrationService.getEmailByUserAD(fpActionDTO.getCreatedBy());
                        if (!email.isEmpty()) {
                            levelUserEmailList.add(email);
                        }
                    }
                    Set<String> set = new HashSet<>(levelUserEmailList.size());
                    levelUserEmailList.removeIf(p -> !set.add(p));
                }

                if (!levelUserEmailList.isEmpty()) {
                    List<EmailAttachment> emailAttachments = new ArrayList<>();
                    String committeeName = committeePaper.getCommitteeName() != null ? committeePaper.getCommitteeName() : "Committee";
                    String subject = "Facility Paper -" + committeeName + " Paper Ref No : " + committeePaper.getFacilityPaper().getFpRefNumber() + " (Approved)";
                    String template = "committee_paper_update";

                    //get email request body
                    FacilityPaperStatusTransitionRQ emailRQ = getEmailRequestBody(committeePaper.getFacilityPaper().getFacilityPaperID(), credentialsDTO);
                    emailRQ.setCommitteeName(committeeName);
                    emailRQ.setCommitteeAction(" has been approved from ");
                    emailRQ.setLastCommentedUser(caEmailDTO.getRecentCommentedBy());
                    emailRQ.setLastComment(caEmailDTO.getRecentComment());

                    if (involvedUserActions.isEmpty()) {
                        emailRQ.setShowActions(false);
                    } else {
                        emailRQ.setShowActions(true);
                        emailRQ.setMembersActions(involvedUserActions);
                    }
                    LOG.info("LOG : levelUserEmailList :{}", levelUserEmailList);
                    LOG.info("LOG : emailRQ :{}", emailRQ);
                    sendCommitteeEmail(levelUserEmailList, emailAttachments, subject, template, emailRQ);
                }


                //if status approved, send credit admin notification
                if (caEmailDTO.getIsReviewPaper() != null && caEmailDTO.getIsReviewPaper().equals(AppsConstants.YesNo.Y)) {
                    sendCreditAdminEmail(committeePaper.getFacilityPaper().getFacilityPaperID(), caEmailDTO.getUpcTemplateId(), credentialsDTO);
                }
            }
        } catch (Exception e) {
            LOG.info("Exception : sendEmailToLevelUsers :", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO updateFacilityPaperYearType(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws SAXException, AppsException, IOException {

        LOG.info("START : Facility Paper Year update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        FacilityPaper facilityPaper = null;
        FacilityPaperDTO previousFacilityPaperDTO = null;
        Date date = new Date();

        facilityPaper = facilityPaperDao.getOne(facilityPaperDTO.getFacilityPaperID());
        previousFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        facilityPaper.setIsFinancialYear(facilityPaperDTO.getIsFinancialYear());
        FacilityPaperDTO updateFacilityPaperDTO = new FacilityPaperDTO();
        try {
            facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
            guaranteeVolumesDao.deleteByFacilityPaperId(facilityPaperDTO.getFacilityPaperID());
            exportTurnOversDao.deleteByFacilityPaperId(facilityPaperDTO.getFacilityPaperID());
            importTurnOversDao.deleteByFacilityPaperId(facilityPaperDTO.getFacilityPaperID());

//        if (facilityPaperDTO.getIsFinancialYear() != previousFacilityPaperDTO.getIsFinancialYear()){
//            updateFacilityPaperExposure(facilityPaperDTO, credentialsDTO);
//        }
            updateFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);
        } catch (Exception e) {
            updateFacilityPaperDTO = null;
        }
        LOG.info("END : Facility Paper Year update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());

        return updateFacilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO uploadFPCustomerCribDetails(List<CribDetailRQ> uploadRQList, MultipartFile[] uploadFiles, CredentialsDTO credentialsDTO) throws AppsException, IOException {
        LOG.info("START: Upload facility paper customer CRIB details :{} by :{}", uploadRQList, credentialsDTO.getUserName());
        Date date = new Date();

        FacilityPaper facilityPaper = this.facilityPaperDao.getOne(uploadRQList.get(0).getFacilityPaperID());

        for (MultipartFile uploadFile : uploadFiles) {
            String fileName = uploadFile.getOriginalFilename();
            CribDetailRQ uploadRQ = uploadRQList.stream().filter(d -> d.getDocumentName().equals(fileName)).findFirst().orElse(null);

            if (uploadRQ != null) {

                uploadRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());
                uploadRQ.getDocStorageDTO().setFileName(uploadFile.getOriginalFilename());
                uploadRQ.setDocumentName(uploadFile.getOriginalFilename());

                CribDetails cribDetail = new CribDetails();
                cribDetail.setIdentificationType(uploadRQ.getIdentificationType());
                cribDetail.setIdentificationNo(uploadRQ.getIdentificationNumber());
                cribDetail.setFullName(uploadRQ.getFullName());
                cribDetail.setGender(uploadRQ.getGender());
                cribDetail.setRemark(uploadRQ.getRemark());
                cribDetail.setInquiryReason(uploadRQ.getInquiryReason());
                cribDetail.setCribStatus(uploadRQ.getCribStatus());
                cribDetail.setCreatedBy(credentialsDTO.getUserName());
                cribDetail.setCreatedDate(date);
                cribDetail.setUploadedDivCode(uploadRQ.getUploadedDivCode());
                cribDetail.setUploadedUserDisplayName(uploadRQ.getUploadedUserDisplayName());
                cribDetail.setStatus(uploadRQ.getStatus());
                cribDetail.setIsSystem(uploadRQ.getIsSystem());

                if (uploadRQ.getDocStorageDTO() != null) {
                    uploadRQ.getDocStorageDTO().setDescription("FACILITY PAPER: " + facilityPaper.getFacilityPaperID() + ", CUSTOMER: " + uploadRQ.getIdentificationNumber());
                    DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
                    cribDetail.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
                } else {
                    LOG.error("Facility paper customer CRIB data null: {}", uploadRQ);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_CUSTOMER_DOCUMENT_NULL_SUPPORT_DOC);
                }

                if (StringUtils.isNotBlank(uploadRQ.getCribIssueDateStr())) {
                    cribDetail.setCribIssueDate(CalendarUtil.getDefaultParsedDateOnly(uploadRQ.getCribIssueDateStr()));
                }
                cribDetail.setSupportingDoc(this.supportingDocDao.getOne(uploadRQ.getSupportingDocID()));
                cribDetail.setFacilityPaper(facilityPaper);
                cribDetail = cribDetailsDao.saveAndFlush(cribDetail);

                //handle history
                saveCribDetailsHistory(cribDetail);
            } else {
                throw new AppsException("Crib report upload has been failed.");
            }
        }

        LOG.info("END: Upload facility paper customer CRIB details :{} by :{}", uploadRQList, credentialsDTO.getUserName());
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadFacilities();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadDocument();
        loadOptionDTO.loadDirectorDetail();
        loadOptionDTO.loadComment();
        loadOptionDTO.loadReviewerComment();
        loadOptionDTO.loadCompanyROA();
        loadOptionDTO.loadCreditRiskComments();
        loadOptionDTO.loadShareHolderDetail();
        loadOptionDTO.loadCribDetails();
        loadOptionDTO.loadCustomerCribDetail();
        loadOptionDTO.setFacilityLoadOptionDTO(new FacilityLoadOptionDTO());
        loadOptionDTO.getFacilityLoadOptionDTO().loadAllData();

        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FacilityPaperDTO updateCribReport(CribDetailRQ cribReportDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Update Customer Crib Report :{} by :{}", cribReportDTO, credentialsDTO.getUserName());
        Date date = new Date();

        FacilityPaper facilityPaper = facilityPaperDao.getOne(cribReportDTO.getFacilityPaperID());
        CribDetails cribDetail = facilityPaper.getCribDetailByID(cribReportDTO.getCribDetailsID());
        if (cribDetail != null) {
            if (cribDetail.getIsSystem() == AppsConstants.YesNo.N) {
                cribDetail.setFullName(cribReportDTO.getFullName());
                cribDetail.setGender(cribReportDTO.getGender());
                cribDetail.setIdentificationNo(cribReportDTO.getIdentificationNumber());
                cribDetail.setIdentificationType(cribReportDTO.getIdentificationType());
            }
            cribDetail.setRemark(cribReportDTO.getRemark());

            if (cribReportDTO.isReportUpdated()) {
                cribDetail.setCribStatus(cribReportDTO.getCribStatus());
                cribDetail.setInquiryReason(cribReportDTO.getInquiryReason());

                if (StringUtils.isNotBlank(cribReportDTO.getCribIssueDateStr())) {
                    cribDetail.setCribIssueDate(CalendarUtil.getDefaultParsedDateOnly(cribReportDTO.getCribIssueDateStr()));
                }

                if (cribReportDTO.getDocStorageDTO() != null) {
                    cribReportDTO.getDocStorageDTO().setDescription("FACILITY PAPER: " + facilityPaper.getFacilityPaperID() + ", CUSTOMER: " + cribReportDTO.getIdentificationNumber());
                    DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(cribReportDTO.getDocStorageDTO(), credentialsDTO);
                    cribDetail.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
                } else {
                    LOG.error("Facility paper customer CRIB data null: {}", cribReportDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_CUSTOMER_DOCUMENT_NULL_SUPPORT_DOC);
                }
            }

            cribDetail.setModifiedBy(credentialsDTO.getUserName());
            cribDetail.setModifiedDate(date);
            cribDetail.setFacilityPaper(facilityPaper);
            cribDetailsDao.saveAndFlush(cribDetail);

            //handle history
            saveCribDetailsHistory(cribDetail);

        } else {
            throw new AppsException("Crib report update has been failed.");
        }

        LOG.info("END: Update Customer Crib Report :{} by :{}", cribReportDTO, credentialsDTO.getUserName());
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCustomerCribDetail();
        fpLoadOptionDTO.loadCribDetails();
        return new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FacilityPaperDTO deleteCribReport(CribDetailRQ cribReportDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Delete Crib Report :{} by :{}", cribReportDTO, credentialsDTO.getUserName());
        FacilityPaper facilityPaper = facilityPaperDao.getOne(cribReportDTO.getFacilityPaperID());
        CribDetails cribDetail = facilityPaper.getCribDetailByID(cribReportDTO.getCribDetailsID());
        if (cribDetail != null) {
            cribDetail.setStatus(AppsConstants.Status.INA);
            cribDetailsDao.saveAndFlush(cribDetail);

            //handle history
            saveCribDetailsHistory(cribDetail);
        } else {
            throw new AppsException("Crib report deletion has been failed.");
        }

        LOG.info("END: Delete Crib Report :{} by :{}", cribReportDTO, credentialsDTO.getUserName());

        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadCustomerCribDetail();
        fpLoadOptionDTO.loadCribDetails();
        return new FacilityPaperDTO(facilityPaper, fpLoadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveCribDetailsHistory(CribDetails cribDetails) throws AppsException {
        CribDetailsHistory cribDetailsHistory = new CribDetailsHistory();

        cribDetailsHistory.setCribDetailsID(cribDetails.getCribDetailsID());
        cribDetailsHistory.setIdentificationType(cribDetails.getIdentificationType());
        cribDetailsHistory.setIdentificationNo(cribDetails.getIdentificationNo());
        cribDetailsHistory.setFullName(cribDetails.getFullName());
        cribDetailsHistory.setGender(cribDetails.getGender());
        cribDetailsHistory.setCribStatus(cribDetails.getCribStatus());
        cribDetailsHistory.setRemark(cribDetails.getRemark());
        cribDetailsHistory.setInquiryReason(cribDetailsHistory.getInquiryReason());
        cribDetailsHistory.setCribIssueDate(cribDetails.getCribIssueDate());
        cribDetailsHistory.setFacilityPaper(cribDetails.getFacilityPaper());
        cribDetailsHistory.setDocStorage(cribDetails.getDocStorage());
        cribDetailsHistory.setSupportingDoc(cribDetails.getSupportingDoc());
        cribDetailsHistory.setUploadedDivCode(cribDetails.getUploadedDivCode());
        cribDetailsHistory.setUploadedUserDisplayName(cribDetails.getUploadedUserDisplayName());
        cribDetailsHistory.setStatus(cribDetails.getStatus());
        cribDetailsHistory.setIsSystem(cribDetails.getIsSystem());
        cribDetailsHistory.setCreatedBy(cribDetails.getCreatedBy());
        cribDetailsHistory.setCreatedDate(cribDetails.getCreatedDate());
        cribDetailsHistory.setModifiedBy(cribDetails.getModifiedBy());
        cribDetailsHistory.setModifiedDate(cribDetails.getModifiedDate());

        cribDetailsHistoryDao.saveAndFlush(cribDetailsHistory);
    }

    public List<CribDetailsResponse> getCribHistoryByCustomer(Integer facilityPaperId, String identificationNo) throws AppsException {
        List<CribDetailsResponse> cribDetailsList = new ArrayList<>();

        cribDetailsList = facilityPaperJdbcDao.getCribHistoryByCustomer(facilityPaperId, identificationNo);

        return cribDetailsList;
    }

    private List<CustomerTurnoverGurantee> getTurnoversAndGurantees(FacilityPaperDTO facilityPaperDTO) {
        List<CustomerTurnoverGurantee> customerTurnoverGurantees = new ArrayList<>();

        try {


            ExportTurnoverGetRQ exportTurnoverGetRQ = new ExportTurnoverGetRQ();
            exportTurnoverGetRQ.setFacilityPaperId(facilityPaperDTO.getFacilityPaperID());

            ImportTurnoverGetRQ importTurnoverGetRQ = new ImportTurnoverGetRQ();
            importTurnoverGetRQ.setFacilityPaperId(facilityPaperDTO.getFacilityPaperID());
            CredentialsDTO credentialsDTO = new CredentialsDTO() {
            };
            credentialsDTO.setDisplayName("For UPC document");
            if (facilityPaperDTO.getCasCustomerDTOList().isEmpty()) {
                customerTurnoverGurantees = null;
            }
            for (CASCustomerDTO customer : facilityPaperDTO.getCasCustomerDTOList()) {
                Set<String> allYearsSet = new HashSet<>();
                List<TurnoverGuranteeDTO> turnoverGuranteeDTOS = new ArrayList<>();
                exportTurnoverGetRQ.setCusId(customer.getCustomerFinancialID());
                importTurnoverGetRQ.setCusId(customer.getCustomerFinancialID());
                List<ExportTurnoverRS> exportTo = finacleService.getExportTurnOversDB(exportTurnoverGetRQ, credentialsDTO);
                List<ImportTurnoverRS> importTO = finacleService.getImportTurnOversDB(importTurnoverGetRQ, credentialsDTO);

                Map<String, Double> totalExportToConvertedAmountByYear = exportTo.stream()
                        .collect(Collectors.groupingBy(
                                ExportTurnoverRS::getYear,
                                Collectors.summingDouble(ExportTurnoverRS::getConvertedAmount)
                        ));

                Map<String, Double> totalImportTOConvertedAmountByYear = importTO.stream()
                        .collect(Collectors.groupingBy(
                                ImportTurnoverRS::getYear,
                                Collectors.summingDouble(ImportTurnoverRS::getConvertedAmount)
                        ));

                allYearsSet.addAll(totalExportToConvertedAmountByYear.keySet());
                allYearsSet.addAll(totalImportTOConvertedAmountByYear.keySet());
                String[] allYearsArray = allYearsSet.toArray(new String[0]);


                for (String year : allYearsArray) {
                    TurnoverGuranteeDTO turnoverGuranteeDTO = new TurnoverGuranteeDTO();

                    turnoverGuranteeDTO.setYear(year);

                    turnoverGuranteeDTO.setExportTo
                            (totalExportToConvertedAmountByYear.containsKey(year) ?
                                    (NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(BigDecimal.valueOf(totalExportToConvertedAmountByYear.get(year))))) : null
                            );
                    turnoverGuranteeDTO.setImportTo
                            (totalImportTOConvertedAmountByYear.containsKey(year) ?
                                    (NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(BigDecimal.valueOf(totalImportTOConvertedAmountByYear.get(year))))) : null
                            );
                    turnoverGuranteeDTOS.add(turnoverGuranteeDTO);
                }
                CustomerTurnoverGurantee customerTurnoverGurantee = new CustomerTurnoverGurantee();
                customerTurnoverGurantee.setTurnoverGuranteeDTOS(turnoverGuranteeDTOS);
                customerTurnoverGurantee.setCustomerName(customer.getCustomerName());

                customerTurnoverGurantees.add(customerTurnoverGurantee);
            }

        } catch (Exception e) {
            customerTurnoverGurantees = null;
        }

        return customerTurnoverGurantees;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO updateGroupExposure(CalculateGrpExposureReq calculateGrpExposureReq, CredentialsDTO credentialsDTO) throws AppsException, IOException, SAXException {
        FacilityPaperDTO facilityPaperDTO;
        try {
            Integer facilityPaperId = calculateGrpExposureReq.getFacilityPaperDTO().getFacilityPaperID();
            List<GroupExposureDetail> exposureDetails = groupExposureDetailDao.findByFacilityIDAndIsSelected(facilityPaperId, 1);

            if (exposureDetails != null && !exposureDetails.isEmpty()) {
                List<GroupExposureDetailDTO> exposures = exposureDetails.stream().map(GroupExposureDetailDTO::new).collect(Collectors.toList());
                calculateGrpExposureReq.setExposures(exposures);
                facilityPaperDTO = calculateGroupExposure(calculateGrpExposureReq, credentialsDTO);
            } else {
                facilityPaperDTO = getFacilityPaperDTOByID(calculateGrpExposureReq.getFacilityPaperDTO().getFacilityPaperID(), credentialsDTO);
            }
        } catch (Exception e) {
            LOG.info("Failed Update Exposure: ", e);
            throw new AppsException("Failed to update group exposure.");
        }
        return facilityPaperDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO calculateGroupExposure(CalculateGrpExposureReq calculateGrpExposureReq, CredentialsDTO credentialsDTO) throws AppsException, IOException, SAXException {
        FacilityPaperDTO facilityPaperDTO;
        try {
            facilityPaperDTO = getFacilityPaperDTOByID(calculateGrpExposureReq.getFacilityPaperDTO().getFacilityPaperID(), credentialsDTO);
            //Clear Group Exposure Before Calculation
            this.clearGroupExposureData(facilityPaperDTO.getFacilityPaperID());

            BigDecimal totalFundedSanctionLimit = DecimalCalculator.getDefaultZero();
            BigDecimal totalFundedOutstanding = DecimalCalculator.getDefaultZero();
            BigDecimal totalNonFundedSanctionLimit = DecimalCalculator.getDefaultZero();
            BigDecimal totalNonFundedOutstanding = DecimalCalculator.getDefaultZero();
            BigDecimal totalCashMargin = DecimalCalculator.getDefaultZero();

            BigDecimal totalSanctionLimit = DecimalCalculator.getDefaultZero();
            BigDecimal totalOutstanding = DecimalCalculator.getDefaultZero();

            int index = 0;

            for (GroupExposureDetailDTO exposure : calculateGrpExposureReq.getExposures()) {
                if (exposure.getIsSelected() == 1) {
                    if (exposure.getIsPrimary() != 1) {
                        totalFundedSanctionLimit = DecimalCalculator.calculateExposureAdd(totalFundedSanctionLimit, exposure.getFundedTotalLimit());
                        totalFundedOutstanding = DecimalCalculator.calculateExposureAdd(totalFundedOutstanding, exposure.getFundedOutstandingAmount());
                        totalNonFundedSanctionLimit = DecimalCalculator.calculateExposureAdd(totalNonFundedSanctionLimit, exposure.getNonFundedTotalLimit());
                        totalNonFundedOutstanding = DecimalCalculator.calculateExposureAdd(totalNonFundedOutstanding, exposure.getNonFundedOutstandingAmount());
                        totalCashMargin = DecimalCalculator.calculateExposureAdd(totalCashMargin, exposure.getLienAmount());

                        totalSanctionLimit = DecimalCalculator.calculateExposureAdd(totalSanctionLimit, exposure.getTotalSanctionLimit());
                        totalOutstanding = DecimalCalculator.calculateExposureAdd(totalOutstanding, exposure.getOutstandingAmount());
                    }
                    groupExposureDetailDao.updateSelectedExposure(exposure.getGroupExposureID(), 1);
                } else {
                    groupExposureDetailDao.updateSelectedExposure(exposure.getGroupExposureID(), 0);
                }

                index = index + 1;
            }

            if (index == calculateGrpExposureReq.getExposures().size()) {

                BigDecimal groupTotalDirectExposureExisting = DecimalCalculator.calculateExposureAdd(facilityPaperDTO.getTotalDirectExposureExisting(), totalFundedSanctionLimit);
                BigDecimal groupTotalDirectExposureNew = DecimalCalculator.calculateExposureAdd(facilityPaperDTO.getTotalDirectExposureNew(), totalFundedSanctionLimit);
                BigDecimal groupTotalDirectExposurePrevious = DecimalCalculator.calculateExposureAdd(facilityPaperDTO.getTotalDirectExposurePrevious(), totalFundedOutstanding);

                BigDecimal groupTotalIndirectExposureExisting = DecimalCalculator.calculateExposureAdd(facilityPaperDTO.getTotalIndirectExposureExisting(), totalNonFundedSanctionLimit);
                BigDecimal groupTotalIndirectExposureNew = DecimalCalculator.calculateExposureAdd(facilityPaperDTO.getTotalIndirectExposureNew(), totalNonFundedSanctionLimit);
                BigDecimal groupTotalIndirectExposurePrevious = DecimalCalculator.calculateExposureAdd(facilityPaperDTO.getTotalIndirectExposurePrevious(), totalNonFundedOutstanding);

                BigDecimal groupTotalExposureExisting = DecimalCalculator.calculateExposureAdd(totalSanctionLimit, facilityPaperDTO.getTotalExposureExisting());
                BigDecimal groupTotalExposureNew = DecimalCalculator.calculateExposureAdd(totalSanctionLimit, facilityPaperDTO.getTotalExposureNew());
                BigDecimal groupTotalExposurePrevious = DecimalCalculator.calculateExposureAdd(totalOutstanding, facilityPaperDTO.getTotalExposurePrevious());

                facilityPaperDTO.setGroupTotalDirectExposurePrevious(groupTotalDirectExposurePrevious);
                facilityPaperDTO.setGroupTotalDirectExposureNew(groupTotalDirectExposureNew);

                facilityPaperDTO.setGroupTotalIndirectExposurePrevious(groupTotalIndirectExposurePrevious);
                facilityPaperDTO.setGroupTotalIndirectExposureNew(groupTotalIndirectExposureNew);

                facilityPaperDTO.setGroupTotalExposurePrevious(groupTotalExposurePrevious);
                facilityPaperDTO.setGroupTotalExposureNew(groupTotalExposureNew);

                if (facilityPaperDTO.getIsCommittee() == AppsConstants.YesNo.Y) {

                    facilityPaperDTO.setGroupTotalDirectExposureExisting(groupTotalDirectExposureExisting);
                    facilityPaperDTO.setGroupTotalIndirectExposureExisting(groupTotalIndirectExposureExisting);
                    facilityPaperDTO.setGroupTotalExposureExisting(groupTotalExposureExisting);

                    facilityPaperDTO.setGroupExistingCashMargin(totalCashMargin);
                    facilityPaperDTO.setGroupProposedCashMargin(totalCashMargin);
                    facilityPaperDTO.setGroupOutstandingCashMargin(totalCashMargin);

                    BigDecimal groupNetTotalExposureExisting = DecimalCalculator.calculateExposureReduction(groupTotalExposureExisting, totalCashMargin);
                    BigDecimal groupNetTotalExposureNew = DecimalCalculator.calculateExposureReduction(groupTotalExposureNew, totalCashMargin);
                    BigDecimal groupNetTotalExposurePrevious = DecimalCalculator.calculateExposureReduction(groupTotalExposurePrevious, totalCashMargin);

                    facilityPaperDTO.setGroupNetTotalExposureExisting(groupNetTotalExposureExisting);
                    facilityPaperDTO.setGroupNetTotalExposureNew(groupNetTotalExposureNew);
                    facilityPaperDTO.setGroupNetTotalExposurePrevious(groupNetTotalExposurePrevious);
                }

                facilityPaperDTO = this.facilityPaperService.updateFacilityPaperExposure(facilityPaperDTO, credentialsDTO);

            }
        } catch (Exception e) {
            LOG.info("Calculate Group Exposure : {} : {}", e, credentialsDTO.getUserID());
            throw new AppsException("Group exposure calculation has been failed.");
        }

        return facilityPaperDTO;
    }

    public List<GroupExposureDetailDTO> getGroupExposureDetailsByCustomerID(String customerId, Integer fpId) throws AppsException {

        LOG.info("START: Fetching group exposure details for customerID: {} and facilityID: {}", customerId, fpId);
        List<GroupExposureDetailDTO> responseList;
        List<GroupExposureDetail> exposureDetails = groupExposureDetailDao.findByFacilityID(fpId);

        if (exposureDetails.isEmpty()) {
            LOG.warn("No records found in GROUP_EXPOSURE_DETAIL for customerID: {}", customerId);
            GroupExposureRequest groupExposureRequest = new GroupExposureRequest();
            groupExposureRequest.setRequestId("smb_1121344");
            groupExposureRequest.setCifiId(customerId);
            groupExposureRequest.setFacilityID(fpId);
            responseList = this.integrationService.getGroupExposure(groupExposureRequest);
        } else {
            responseList = exposureDetails.stream()
                    .map(detail -> GroupExposureDetailDTO.builder()
                            .groupExposureID(detail.getGroupExposureID())
                            .customerID(detail.getCustomerID())
                            .customerName(detail.getCustomerName())
                            .fundedTotalLimit(detail.getFundedLimitTotal())
                            .fundedOutstandingAmount(detail.getFundedOutstanding())
                            .nonFundedTotalLimit(detail.getNonFundedLimitTotal())
                            .nonFundedOutstandingAmount(detail.getNonFundedOutstanding())
                            .parentCustID(detail.getParentCustID())
                            .relationshipCode(detail.getRelationshipCode())
                            .totalSanctionLimit(detail.getTotalSanctionLimit())
                            .outstandingAmount(detail.getOutstandingAmount())
                            .facilityID(detail.getFacilityID())
                            .lienAmount(detail.getLienAmount())
                            .isSelected(detail.getIsSelected())
                            .isPrimary(detail.getIsPrimary())
                            .build())
                    .collect(Collectors.toList());
        }
        LOG.info("INFO: Retrieved {} group exposure details for customerID: {} and facilityID: {}",
                responseList.size(), customerId, fpId);

        return responseList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void clearGroupExposureData(Integer facilityPaperId) throws AppsException {
        try {
            FacilityPaper facilityPaper = facilityPaperDao.getOne(facilityPaperId);
            facilityPaper.setGroupTotalDirectExposurePrevious(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupTotalDirectExposureNew(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupTotalIndirectExposurePrevious(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupTotalIndirectExposureNew(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupTotalExposurePrevious(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupTotalExposureNew(DecimalCalculator.getDefaultZero());

            facilityPaper.setGroupTotalDirectExposureExisting(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupTotalIndirectExposureExisting(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupTotalExposureExisting(DecimalCalculator.getDefaultZero());

            facilityPaper.setGroupExistingCashMargin(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupProposedCashMargin(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupOutstandingCashMargin(DecimalCalculator.getDefaultZero());

            facilityPaper.setGroupNetTotalExposureExisting(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupNetTotalExposureNew(DecimalCalculator.getDefaultZero());
            facilityPaper.setGroupNetTotalExposurePrevious(DecimalCalculator.getDefaultZero());

            facilityPaperDao.saveAndFlush(facilityPaper);
        } catch (Exception e) {
            LOG.info("Clear Group Exposure By FPId", e);
            throw new AppsException("Group exposure calculation has been failed.");
        }
    }

    public byte[] SupportingDocsZipFile(Integer fpId) throws IOException {
        FacilityPaper fp = facilityPaperDao.findById(fpId)
                .orElseThrow(() -> new RuntimeException("FacilityPaper not found"));

        String fpRefNumber = fp.getFpRefNumber();

        String customerName = fp.getCASCustomerSet().stream()
                .filter(c -> c.getIsPrimary() == AppsConstants.YesNo.Y)
                .map(CASCustomer::getCasCustomerName)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Primary customer not found"));


        List<FPDocument> activeDocuments = fp.getFpDocumentSet().stream()
                .filter(doc -> doc.getStatus() == AppsConstants.Status.ACT)
                .filter(doc -> doc.getDocStorage() != null && doc.getDocStorage().getDocument() != null)
                .sorted(Comparator.comparing(FPDocument::getCreatedDate))
                .collect(Collectors.toList());

        if (activeDocuments.isEmpty()) {
            throw new RuntimeException("No active documents found for FacilityPaper ID: " + fpId);
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            int totalFiles = activeDocuments.size();

            for (int i = 0; i < totalFiles; i++) {
                DocStorage docStorage = activeDocuments.get(i).getDocStorage();
                byte[] documentBytes = docStorage.getDocument();

                if (documentBytes == null || documentBytes.length == 0) continue;

                String originalName = docStorage.getFileName();
                String ext = "";
                String nameWithoutExt = originalName;

                int extensionIndex = originalName.lastIndexOf('.');
                if (extensionIndex > 0 && extensionIndex < originalName.length() - 1) {
                    ext = originalName.substring(extensionIndex);
                    nameWithoutExt = originalName.substring(0, extensionIndex);
                }

                // Truncate customerName if it exceeds 30 characters
                if (customerName.length() > 30) {
                    customerName = customerName.substring(0, 30);
                    customerName = customerName + "_";
                }

                if (nameWithoutExt.length() > 20) {
                    nameWithoutExt = nameWithoutExt.substring(0, 20);
                    nameWithoutExt = nameWithoutExt + "_";
                }

                // Final file name format
                String renamed = String.format("%s - %s (ANNEX - %d of %d)(%s)%s",
                        customerName, fpRefNumber, i + 1, totalFiles, nameWithoutExt, ext);

                ZipEntry zipEntry = new ZipEntry(renamed);
                zos.putNextEntry(zipEntry);
                zos.write(documentBytes);
                zos.closeEntry();
            }

            zos.finish();
            return baos.toByteArray();
        }
    }

    public void sendCommitteeInquiryEmail(InquiryEmailDTO inquiryEmailDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Sending Committee Inquiry Email for Facility Paper ID: {} by User: {}", inquiryEmailDTO.getFacilityPaperId(), credentialsDTO.getUserName());
        String email = this.integrationService.getEmailByUserAD(inquiryEmailDTO.getAssignTo());
        if (email != null && !email.isEmpty()) {
            EmailSendRequest emailSendRequest = new EmailSendRequest();
            List<String> toAddresses = new ArrayList<>();
            toAddresses.add(email);

            FacilityPaperStatusTransitionRQ emailRQ = this.getInquiryEmailRequestBody(inquiryEmailDTO.getFacilityPaperId(), inquiryEmailDTO, credentialsDTO);
            String templateName = "committee_inquiry";
            String subject = "Committee Inquiry For - ".concat(emailRQ.getFacilityPaperRefNumber());

            emailSendRequest.setPurpose(DomainConstants.EmailPurpose.OTHER);
            emailSendRequest.setSubject(subject);
            emailSendRequest.setTemplateName(templateName);
            emailSendRequest.setToAddresses(toAddresses);
            emailSendRequest.setSendType(DomainConstants.EmailSendType.HTML);

            Map<String, Object> params = new HashMap<>();
            params.put("facilityPaperStatusTransitionRQ", emailRQ);
            emailSendRequest.setParams(params);
            casEmailService.sendMail(emailSendRequest);
            LOG.info("END: Sending Committee Inquiry Email for Facility Paper ID: {} by User: {}", inquiryEmailDTO.getFacilityPaperId(), credentialsDTO.getUserName());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<EnvironmentalRiskDataDTO> saveFPEnvironmentalRisk(EnvironmentalRiskDataReq environmentalRiskReq, CredentialsDTO credentialsDTO) throws AppsException {

        List<EnvironmentalRiskDataDTO> response = new ArrayList<>();
        Date date = new Date();

        try {
            if (facilityPaperDao.findById(environmentalRiskReq.getFacilityPaperId()).isPresent()) {
                FacilityPaper facilityPaper = facilityPaperDao.findById(environmentalRiskReq.getFacilityPaperId()).get();

                //clear categories under facility paper
                if (!environmentalRiskDataDao.findAllByFacilityPaper(facilityPaper).isEmpty()) {
                    environmentalRiskDataDao.deleteByFacilityPaper(facilityPaper);
                }

                for (EnvironmentalRiskDataDTO category : environmentalRiskReq.getCategories()) {
                    EnvironmentalRiskData environmentalRisk = new EnvironmentalRiskData();
                    environmentalRisk.setFacilityPaper(facilityPaper);
                    environmentalRisk.setRiskCategoryId(category.getRiskCategoryId());
                    environmentalRisk.setCategoryParentId(category.getCategoryParentId());
                    environmentalRisk.setDescription(category.getDescription());
                    environmentalRisk.setScore(category.getScore());
                    environmentalRisk.setType(category.getType());
                    environmentalRisk.setCreatedBy(credentialsDTO.getUserName());
                    environmentalRisk.setCreatedDate(date);

                    environmentalRiskDataDao.save(environmentalRisk);

                    List<EnvironmentalRiskData> environmentalRisks = environmentalRiskDataDao.findAllByFacilityPaper(facilityPaper);
                    if (environmentalRisks != null && !environmentalRisks.isEmpty()) {
                        response = environmentalRisks.stream().map(EnvironmentalRiskDataDTO::new).collect(Collectors.toList());
                    }
                }

                //update esg status in application form
                if (facilityPaper.getIsESGPaper().equals(AppsConstants.YesNo.N)) {
                    facilityPaper.setIsESGPaper(AppsConstants.YesNo.Y);
                    facilityPaper.setModifiedDate(date);
                    facilityPaper.setModifiedBy(credentialsDTO.getUserName());

                    facilityPaperDao.saveAndFlush(facilityPaper);
                }
            } else {
                throw new AppsException("Facility Paper does not exists.");
            }

        } catch (Exception e) {
            LOG.info("ERROR: Failed to save risk category", e);
            throw new AppsException("Failed to save risk category.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<EnvironmentalRiskDataDTO> removeFPEnvironmentalRisk(Integer facilityPaperId, CredentialsDTO credentialsDTO) throws AppsException {
        List<EnvironmentalRiskDataDTO> response = new ArrayList<>();
        Date date = new Date();

        try {
            if (facilityPaperDao.findById(facilityPaperId).isPresent()) {
                FacilityPaper facilityPaper = facilityPaperDao.findById(facilityPaperId).get();

                //clear categories under facility paper
                if (!environmentalRiskDataDao.findAllByFacilityPaper(facilityPaper).isEmpty()) {
                    environmentalRiskDataDao.deleteByFacilityPaper(facilityPaper);
                }

                //update esg status in facility paper
                if (facilityPaper.getIsESGPaper().equals(AppsConstants.YesNo.Y)) {
                    facilityPaper.setIsESGPaper(AppsConstants.YesNo.N);
                    facilityPaper.setIsESGApproved(AppsConstants.YesNo.N);
                    facilityPaper.setModifiedDate(date);
                    facilityPaper.setModifiedBy(credentialsDTO.getUserName());

                    String clear = "";
                    facilityPaper.setApprovedESGScore(clear);

                    facilityPaperDao.saveAndFlush(facilityPaper);
                }
            }
        } catch (Exception e) {
            LOG.info("Failed to remove risk data.");
            throw new AppsException("Failed to remove risk data.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO approvedFPEnvironmentalRisk(ApproveRiskScoreDTO approveRiskScoreDTO, CredentialsDTO credentialsDTO) throws AppsException {
        FacilityPaperDTO response = new FacilityPaperDTO();
        Date date = new Date();

        try {
            if (facilityPaperDao.findById(approveRiskScoreDTO.getFacilityPaperId()).isPresent()) {
                FacilityPaper facilityPaper = facilityPaperDao.findById(approveRiskScoreDTO.getFacilityPaperId()).get();

                if (facilityPaper.getIsESGApproved().equals(AppsConstants.YesNo.N)) {
                    if (approveRiskScoreDTO.getScore() != null && !approveRiskScoreDTO.getScore().isEmpty()) {
                        facilityPaper.setApprovedESGScore(approveRiskScoreDTO.getScore());
                    }
                    facilityPaper.setIsESGApproved(AppsConstants.YesNo.Y);
                } else if (facilityPaper.getIsESGApproved().equals(AppsConstants.YesNo.Y)) {
                    facilityPaper.setIsESGApproved(AppsConstants.YesNo.N);
                    String clear = "";
                    facilityPaper.setApprovedESGScore(clear);
                }

                facilityPaper.setModifiedDate(date);
                facilityPaper.setModifiedBy(credentialsDTO.getUserName());

                facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
                response = new FacilityPaperDTO(facilityPaper);
            }
        } catch (Exception e) {
            LOG.info("Failed to approved risk data.");
            throw new AppsException("Failed to approved risk data.");
        }

        return response;
    }

    public List<RiskOpinionDTO> getFPEnvironmentalRiskOpinion(Integer facilityPaperId) throws AppsException {

        List<RiskOpinionDTO> response = new ArrayList<>();

        try {
            FacilityPaper facilityPaper = facilityPaperDao.findById(facilityPaperId)
                    .orElseThrow(() -> new AppsException("Facility paper not found."));

            List<RiskOpinion> riskOpinions = riskOpinionDao.findAllByFacilityPaper(facilityPaper);
            if (riskOpinions != null && !riskOpinions.isEmpty()) {
                response = riskOpinions.stream().map(RiskOpinionDTO::new).collect(Collectors.toList());
            }

        } catch (Exception e) {
            LOG.info("Failed to fetch risk opinions: ", e);
            throw new AppsException("Failed to fetch risk opinions.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<RiskOpinionDTO> saveFPEnvironmentalRiskOpinion(RiskOpinionDTO riskOpinionDTO, CredentialsDTO credentialsDTO) throws AppsException {

        List<RiskOpinionDTO> response;
        Date date = new Date();
        try {
            RiskOpinion riskOpinion = riskOpinionDao.findById(riskOpinionDTO.getRiskOpinionId()).orElse(new RiskOpinion());

            if (riskOpinion.getRiskOpinionId() != null && riskOpinion.getRiskOpinionId() > 0) {
                riskOpinion.setModifiedBy(credentialsDTO.getUserName());
                riskOpinion.setModifiedDate(date);
            } else {
                FacilityPaper facilityPaper = facilityPaperDao.findById(riskOpinionDTO.getFacilityPaperId())
                        .orElseThrow(() -> new AppsException("Facility paper not found."));
                riskOpinion.setFacilityPaper(facilityPaper);
                riskOpinion.setCreatedBy(credentialsDTO.getUserName());
                riskOpinion.setCreatedDate(date);
            }
            riskOpinion.setOpinion(riskOpinionDTO.getOpinion());
            riskOpinionDao.saveAndFlush(riskOpinion);

            response = getFPEnvironmentalRiskOpinion(riskOpinionDTO.getFacilityPaperId());
        } catch (Exception e) {
            LOG.info("Failed to save risk opinion: ", e);
            throw new AppsException("Failed to save risk opinion.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<RiskOpinionDTO> saveFPEnvironmentalRiskOpinionReply(RiskOpinionReplyDTO riskOpinionReplyDTO, CredentialsDTO credentialsDTO) throws AppsException {

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

            response = getFPEnvironmentalRiskOpinion(riskOpinionReplyDTO.getFacilityPaperId());

        } catch (Exception e) {
            LOG.info("Failed to save risk opinion reply: ", e);
            throw new AppsException("Failed to save risk reply.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<RiskOpinionDTO> removeFPEnvironmentalRiskOpinion(RiskOpinionDTO riskOpinionDTO) throws AppsException {

        List<RiskOpinionDTO> response;
        try {
            RiskOpinion riskOpinion = riskOpinionDao.findById(riskOpinionDTO.getRiskOpinionId())
                    .orElseThrow(() -> new AppsException("Risk opinion not found."));

            if (riskOpinion.getRiskOpinionReply() == null) {
                riskOpinionDao.delete(riskOpinion);
                response = getFPEnvironmentalRiskOpinion(riskOpinionDTO.getFacilityPaperId());
            } else {
                throw new AppsException("There is a reply for this opinion.");
            }
        } catch (Exception e) {
            LOG.info("Failed to remove risk opinion : ", e);
            throw new AppsException("Failed to remove risk.");
        }

        return response;
    }


    public FacilityPaperStatusTransitionRQ getInquiryEmailRequestBody(Integer facilityPaperID, InquiryEmailDTO inquiryEmailDTO, CredentialsDTO credentialsDTO) {
        BranchLoadResponseListDTO branchLoadResponseListDTO = integrationService.getBranchList(credentialsDTO);

        FacilityPaper facilityPaper = facilityPaperDao.getOne(facilityPaperID);
        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadCustomerRelatedDetails();
        loadOptionDTO.loadFacilities();

        FacilityLoadOptionDTO facilityLoadOption = new FacilityLoadOptionDTO();
        facilityLoadOption.loadAllData();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOption);

        FacilityPaperDTO updatedFacilityPaperDTO = new FacilityPaperDTO(facilityPaper, loadOptionDTO);

        FacilityPaperStatusTransitionRQ emailRQ = new FacilityPaperStatusTransitionRQ();
        emailRQ.setSolID(facilityPaper.getBranchCode());
        emailRQ.setCredentialsDTO(credentialsDTO);
        emailRQ.setUserName(updatedFacilityPaperDTO.getAssignUserDisplayName());
        emailRQ.setFacilityPaperID(facilityPaper.getFacilityPaperID());
        emailRQ.setFacilityPaperRefNumber(facilityPaper.getFpRefNumber());

        if (updatedFacilityPaperDTO.getCasCustomerDTOList() != null && !updatedFacilityPaperDTO.getCasCustomerDTOList().isEmpty()) {
            emailRQ.setCustomerName(updatedFacilityPaperDTO.getCasCustomerDTOList().get(0).getCustomerName());
        }

        //Set Group Exposure
        if (facilityPaper.getGroupNetTotalExposureNew() != null) {
            if (NumberUtil.getFormattedAmount(facilityPaper.getGroupNetTotalExposureNew()) != null) {
                emailRQ.setTotalGroupExposure(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(facilityPaper.getGroupNetTotalExposureNew())));
            } else {
                emailRQ.setTotalGroupExposure("0.00");
            }
        } else {
            emailRQ.setTotalGroupExposure("0.00");
        }

        //Set Company Exposure
        if (facilityPaper.getNetTotalExposureNew() != null) {
            if (NumberUtil.getFormattedAmount(facilityPaper.getNetTotalExposureNew()) != null) {
                emailRQ.setTotalCompanyExposure(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(facilityPaper.getNetTotalExposureNew())));
            } else {
                emailRQ.setTotalCompanyExposure("0.00");
            }
        } else {
            emailRQ.setTotalCompanyExposure("0.00");
        }

        emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCreatedDate()));
        emailRQ.setBranchName(branchLoadResponseListDTO.getBranchResponse(facilityPaper.getBranchCode()).getBranchName());
        emailRQ.setShowActiveNewFacilities(false);
        for (FacilityDTO facility : updatedFacilityPaperDTO.getFacilityDTOList()) {
            if (facility.getIsNew() == AppsConstants.YesNo.Y) {
                emailRQ.getFacilityDTOList().add(facility);
                emailRQ.setShowActiveNewFacilities(true);
            }
        }
        if (inquiryEmailDTO.getCommentType().equals(AppsConstants.InquiryType.REQ)) {
            emailRQ.setShowInqComment(true);
            emailRQ.setCommitteeMember(true);
            emailRQ.setInqComment(inquiryEmailDTO.getComment());
            emailRQ.setInqCommentedBy(credentialsDTO.getDisplayName());
        } else {
            emailRQ.setShowInqComment(false);
            emailRQ.setCommitteeMember(false);
        }
        emailRQ.setCommitteeName(inquiryEmailDTO.getCommitteeName());

        return emailRQ;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CusApplicableCovenantDTO> getCusApplicableCovenantList(String fpRefNumber, Integer facilityId, CredentialsDTO credentialsDTO) throws AppsException {

        List<CusApplicableCovenantDTO> response = new ArrayList<>();
        List<CustomerCovenant> customerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        //map facility covenants
        List<FinalDTO> allFacilityCovenants = getFacilityCovenantList(fpRefNumber, credentialsDTO);
        if (allFacilityCovenants != null) {
            for (FinalDTO finalDTO : allFacilityCovenants) {
                for (ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO : finalDTO.getCovValue()) {

                    boolean isFacilityExist = applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS().
                            stream().anyMatch(data -> Objects.equals(data.getFacilityID(), facilityId));

                    if (isFacilityExist && applicationCovenantDetailsDTO.getApplicableType().equals(AppsConstants.CovenantApplicableType.AC)
                            && applicationCovenantDetailsDTO.getStatus().equals(AppsConstants.CovenantStatus.Active)) {

                        CusApplicableCovenantDTO cusApplicableCovenant = new CusApplicableCovenantDTO();
                        cusApplicableCovenant.setCovenant_Code(applicationCovenantDetailsDTO.getCovenant_Code());
                        cusApplicableCovenant.setCovenant_Description(applicationCovenantDetailsDTO.getCovenant_Description());
                        cusApplicableCovenant.setCovenant_Frequency(applicationCovenantDetailsDTO.getCovenant_Frequency());
                        cusApplicableCovenant.setCovenant_Due_Date(applicationCovenantDetailsDTO.getCovenant_Due_Date());
                        cusApplicableCovenant.setApplicableType(applicationCovenantDetailsDTO.getApplicableType());
                        cusApplicableCovenant.setDisbursementType(applicationCovenantDetailsDTO.getDisbursementType());
                        cusApplicableCovenant.setNoFrequencyDueDate(applicationCovenantDetailsDTO.getNoFrequencyDueDate());

                        response.add(cusApplicableCovenant);
                    }
                }
            }
        }

        //map customer covenants
        if (customerCovenants != null) {
            List<CustomerCovenant> filteredCustomerCovList = customerCovenants.stream().filter(data -> data.getApplicableType()
                            .equals(AppsConstants.CovenantApplicableType.AC) && data.getStatus().equals(AppsConstants.CovenantStatus.Active))
                    .collect(Collectors.toList());

            for (CustomerCovenant customerCovenant : filteredCustomerCovList) {
                CusApplicableCovenantDTO cusApplicableCovenant = new CusApplicableCovenantDTO();
                cusApplicableCovenant.setCovenant_Code(customerCovenant.getCovenant_Code());
                cusApplicableCovenant.setCovenant_Description(customerCovenant.getCovenant_Description());
                cusApplicableCovenant.setCovenant_Frequency(customerCovenant.getCovenant_Frequency());
                cusApplicableCovenant.setCovenant_Due_Date(customerCovenant.getCovenant_Due_Date());
                cusApplicableCovenant.setApplicableType(customerCovenant.getApplicableType());
                cusApplicableCovenant.setDisbursementType(customerCovenant.getDisbursementType());
                cusApplicableCovenant.setNoFrequencyDueDate(customerCovenant.getNoFrequencyDueDate());

                response.add(cusApplicableCovenant);
            }
        }
        return response;
    }

    public List<WalletShareDTO> getWalletShare(Integer facilityPaperId) throws AppsException {
        List<WalletShareDTO> response = new ArrayList<>();
        try {
            FacilityPaper facilityPaper = facilityPaperDao.findById(facilityPaperId)
                    .orElseThrow(() -> new AppsException("Facility Paper not found."));

            List<WalletShare> walletShares = walletShareDao.findByFacilityPaper(facilityPaper);
            if (walletShares != null && !walletShares.isEmpty()) {
                response = walletShares.stream().map(WalletShareDTO::new).collect(Collectors.toList());
            }

        } catch (Exception e) {
            LOG.info("Wallet share data not found: ", e);
            throw new AppsException("Wallet share data not found.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<WalletShareDTO> saveWalletShare(WalletShareReq walletShareReq, CredentialsDTO credentialsDTO) throws AppsException {
        List<WalletShareDTO> response = new ArrayList<>();

        try {
            Date date = new Date();
            FacilityPaper facilityPaper = facilityPaperDao.findById(walletShareReq.getFacilityPaperId())
                    .orElseThrow(() -> new AppsException("Facility Paper not found."));

            for (WalletShareDTO walletShareDTO : walletShareReq.getWalletShares()) {

                WalletShare walletShare = new WalletShare();

                walletShareDao.findById(walletShareDTO.getWalletShareId()).ifPresent(data -> {
                    walletShare.setWalletShareId(data.getWalletShareId());
                    walletShare.setModifiedBy(credentialsDTO.getUserName());
                    walletShare.setModifiedDate(date);
                    walletShare.setFacilities(data.getFacilities());
                });

                if (!walletShareDTO.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.DELETE)) {
                    walletShare.setFacilityPaper(facilityPaper);
                    walletShare.setBank(walletShareDTO.getBank());
                    walletShare.setShare(walletShareDTO.getShare());
                    walletShare.setLimitAmountTotal(walletShareDTO.getLimitTotal());
                    walletShare.setOsAmountTotal(walletShareDTO.getOsTotal());
                    walletShare.setIsSystem(walletShareDTO.getIsSystem());

                    if (walletShareDTO.getWalletShareId() == 0) {
                        walletShare.setCreatedBy(credentialsDTO.getUserName());
                        walletShare.setCreatedDate(date);
                    }

                    if (walletShareDTO.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.NEW) ||
                            walletShareDTO.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.UPDATE)) {

                        List<WalletShareFacility> walletShareFacilities = this.saveWalletShareFacility(walletShareDTO.getFacilities(), walletShare, credentialsDTO);
                        walletShare.setFacilities(walletShareFacilities);

                        List<WSFacilitySecurity> commonSecurities = this.saveWalletShareCommonSecurity(walletShareDTO.getCommonSecurities(), walletShare, credentialsDTO);
                        walletShare.setWsFacilitySecurities(commonSecurities);

                        walletShareDao.saveAndFlush(walletShare);
                    }
                } else {
                    wsFacilitySecurityDao.deleteByWalletShare(walletShare);
                    walletShareFacilityDao.deleteByWalletShare(walletShare);
                    walletShareDao.deleteById(walletShareDTO.getWalletShareId());
                }
            }

            List<WalletShare> walletShares = walletShareDao.findByFacilityPaper(facilityPaper);
            if (walletShares != null && !walletShares.isEmpty()) {
                response = walletShares.stream().map(WalletShareDTO::new).collect(Collectors.toList());
            }

        } catch (Exception e) {
            LOG.info("Failed Save wallet share details: ", e);
            throw new AppsException("Failed to save wallet share details.");
        }

        return response;
    }

    public List<WalletShareFacility> saveWalletShareFacility(List<WalletShareFacilityDTO> facilities, WalletShare walletShare, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();
        List<WalletShareFacility> walletShareFacilities = walletShare.getFacilities();

        try {
            for (WalletShareFacilityDTO facility : facilities) {

                WalletShareFacility walletShareFacility = walletShareFacilityDao.findById(facility.getFacilityId()).orElse(new WalletShareFacility());

                walletShareFacility.setFacility(facility.getFacility());
                walletShareFacility.setFacilityCurrency(facility.getFacilityCurrency());
                walletShareFacility.setLimitAmount(facility.getLimitAmount());
                walletShareFacility.setOsAmount(facility.getOsAmount());
                walletShareFacility.setWalletShare(walletShare);

                if (facility.getFacilityId() == 0) {
                    walletShareFacility.setCreatedBy(credentialsDTO.getUserName());
                    walletShareFacility.setCreatedDate(date);
                } else {
                    walletShareFacility.setModifiedBy(credentialsDTO.getUserName());
                    walletShareFacility.setModifiedDate(date);
                }

                if (facility.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.NEW) ||
                        facility.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.UPDATE)) {

                    List<WSFacilitySecurity> securities = this.saveWalletShareFacilitySecurity(facility.getFacilitySecurities(), walletShareFacility, credentialsDTO);
                    walletShareFacility.setWsFacilitySecurities(securities);

                    walletShareFacilities.add(walletShareFacility);
                }

                if (facility.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.DELETE)) {
                    wsFacilitySecurityDao.deleteByWalletShareFacility(walletShareFacility);
                    walletShareFacilityDao.deleteById(walletShareFacility.getFacilityId());
                }
            }
        } catch (Exception e) {
            LOG.info("Failed to add wallet share facilities: ", e);
            throw new AppsException("Failed to add wallet share facilities.");
        }

        return walletShareFacilities;
    }

    public List<WSFacilitySecurity> saveWalletShareCommonSecurity(List<WSFacilitySecurityDTO> securities, WalletShare walletShare, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();
        List<WSFacilitySecurity> wsFacilitySecurities = new ArrayList<>();
        try {
            for (WSFacilitySecurityDTO security : securities) {
                WSFacilitySecurity wsFacilitySecurity = wsFacilitySecurityDao.findById(security.getSecurityId()).orElse(new WSFacilitySecurity());

                wsFacilitySecurity.setWalletShare(walletShare);
                wsFacilitySecurity.setSecurityDetail(security.getSecurityDetail());
                wsFacilitySecurity.setIsCommonSecurity(AppsConstants.YesNo.Y);

                if (security.getSecurityId() == 0) {
                    wsFacilitySecurity.setCreatedBy(credentialsDTO.getUserName());
                    wsFacilitySecurity.setCreatedDate(date);
                } else {
                    wsFacilitySecurity.setModifiedBy(credentialsDTO.getUserName());
                    wsFacilitySecurity.setModifiedDate(date);
                }

                if (security.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.NEW) ||
                        security.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.UPDATE)) {
                    wsFacilitySecurities.add(wsFacilitySecurity);
                }

                if (security.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.DELETE)) {
                    wsFacilitySecurityDao.deleteById(wsFacilitySecurity.getSecurityId());
                }
            }
        } catch (Exception e) {
            LOG.info("Failed to add wallet share common securities: ", e);
            throw new AppsException("Failed to add wallet share common securities.");
        }

        return wsFacilitySecurities;
    }

    public List<WSFacilitySecurity> saveWalletShareFacilitySecurity(List<WSFacilitySecurityDTO> securities, WalletShareFacility walletShareFacility, CredentialsDTO credentialsDTO) throws AppsException {

        Date date = new Date();
        List<WSFacilitySecurity> wsFacilitySecurities = new ArrayList<>();

        try {
            for (WSFacilitySecurityDTO security : securities) {
                WSFacilitySecurity wsFacilitySecurity = wsFacilitySecurityDao.findById(security.getSecurityId()).orElse(new WSFacilitySecurity());

                wsFacilitySecurity.setWalletShare(walletShareFacility.getWalletShare());
                wsFacilitySecurity.setSecurityDetail(security.getSecurityDetail());
                wsFacilitySecurity.setIsCommonSecurity(AppsConstants.YesNo.N);
                wsFacilitySecurity.setWalletShareFacility(walletShareFacility);

                if (security.getSecurityId() == 0) {
                    wsFacilitySecurity.setCreatedBy(credentialsDTO.getUserName());
                    wsFacilitySecurity.setCreatedDate(date);
                } else {
                    wsFacilitySecurity.setModifiedBy(credentialsDTO.getUserName());
                    wsFacilitySecurity.setModifiedDate(date);
                }

                if (security.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.NEW) ||
                        security.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.UPDATE)) {
                    wsFacilitySecurities.add(wsFacilitySecurity);
                }

                if (security.getRecordStatus().equals(DomainConstants.MasterDataActionStatus.DELETE)) {
                    wsFacilitySecurityDao.deleteById(wsFacilitySecurity.getSecurityId());
                }
            }
        } catch (Exception e) {
            LOG.info("Failed to add wallet share facility securities: ", e);
            throw new AppsException("Failed to add wallet share facility securities.");
        }

        return wsFacilitySecurities;
    }

    public void sendCreditAdminEmail(Integer facilityPaperId, Integer upcTemplateID, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Sending Credit Admin Email for Facility Paper ID: {} by User: {}", facilityPaperId, credentialsDTO.getUserName());

        List<FPStatusHistoryDTO> involvedUsers = getFPUsersInvolved(facilityPaperId);
        List<String> toAddresses = new ArrayList<>();

        if (upcTemplateDao.findById(upcTemplateID).isPresent()) {
            UpcTemplate template = upcTemplateDao.findById(upcTemplateID).get();
            toAddresses = Arrays.stream(template.getNotifyEmails().split(",")).collect(Collectors.toList());
        }

        List<String> ccAddresses = new ArrayList<>();
        involvedUsers = involvedUsers.stream().filter(data -> Integer.parseInt(data.getAssignUserUpmGroupCode()) <= 72).collect(Collectors.toList());
        if (involvedUsers.stream().anyMatch(data -> data.getAssignUserUpmGroupCode().equals("71"))) {
            involvedUsers = involvedUsers.stream().filter(data -> !data.getAssignUserUpmGroupCode().equals("72")).collect(Collectors.toList());
        }

        for (FPStatusHistoryDTO history : involvedUsers) {

            String email = this.integrationService.getEmailByUserAD(history.getAssignUser());
            if (email != null && !email.isEmpty()) {
                ccAddresses.add(email);
            }
        }

        if (!toAddresses.isEmpty()) {
            EmailSendRequest emailSendRequest = new EmailSendRequest();

            FacilityPaperStatusTransitionRQ emailRQ = this.getEmailRequestBody(facilityPaperId, credentialsDTO);

            String templateName = "credit_admin_notification";
            String subject = String.format(
                    "Credit Facility Review Paper Approved - [%s] [%s] [%s] [%s]",
                    emailRQ.getCustomerName(),
                    emailRQ.getSolID(),
                    emailRQ.getBranchName(),
                    emailRQ.getFacilityPaperRefNumber()
            );

            emailSendRequest.setPurpose(DomainConstants.EmailPurpose.OTHER);
            emailSendRequest.setSubject(subject);
            emailSendRequest.setTemplateName(templateName);
            emailSendRequest.setToAddresses(toAddresses);
            if (!ccAddresses.isEmpty()) {
                emailSendRequest.setCcAddresses(ccAddresses);
            }
            emailSendRequest.setSendType(DomainConstants.EmailSendType.HTML);

            Map<String, Object> params = new HashMap<>();
            params.put("facilityPaperStatusTransitionRQ", emailRQ);
            emailSendRequest.setParams(params);
            casEmailService.sendMailService(emailSendRequest);
            LOG.info("END: Sending Credit Admin Email for Facility Paper ID: {} by User: {}", facilityPaperId, credentialsDTO.getUserName());
        }
    }

    @Transactional(readOnly = true)
    public List<FPUpcSectionDataDTO> getUpcSectionData(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {
        List<FPUpcSectionDataDTO> response = new ArrayList<>();

        try {
            FacilityPaper facilityPaper = facilityPaperDao.findById(facilityPaperID)
                    .orElseThrow(() -> new AppsException("Facility Paper not found."));

            List<FPUpcSectionData> upcSectionDataList = fpUpcSectionDataDao.findByFacilityPaperFacilityPaperID(facilityPaperID);
            if (upcSectionDataList != null && !upcSectionDataList.isEmpty()) {

                for (FPUpcSectionData upcSectionData : upcSectionDataList) {
                    response.add(new FPUpcSectionDataDTO(upcSectionData));
                }
            }

        } catch (Exception e) {
            LOG.info("Failed to fetch UPC section data: ", e);
            throw new AppsException("Failed to fetch UPC section data.");
        }

        return response;
    }


    @Transactional(readOnly = true)
    public List<DeviationDTO> getDeviations() throws AppsException {
        List<DeviationDTO> response = new ArrayList<>();

        try {
            response = facilityPaperJdbcDao.getDeviations();
        } catch (Exception e) {
            LOG.info("ERROR : getDeviations ", e);
        }
        return response;
    }

    @Transactional(readOnly = true)
    public List<CompDeviationDTO> getCompDeviations(Integer facilityPaperId, CredentialsDTO credentialsDTO) throws AppsException {
        List<CompDeviationDTO> response = new ArrayList<>();

        try {
            LOG.info("START: getCompDeviations for facility paper id : {} by User: {}", facilityPaperId, credentialsDTO.getUserName());
            FacilityPaper facilityPaper = facilityPaperDao.findByFacilityPaperID(facilityPaperId);

            response = facilityPaperJdbcDao.getCompDeviations(facilityPaperId);
            List<DeviationDTO> deviationDTOList = new ArrayList<>();
            if (!facilityPaper.getCurrentFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.APPROVED)) {
                deviationDTOList = getDeviations();
            }

            Set<Integer> idsToRemove = response.stream()
                    .map(CompDeviationDTO::getDeviationId)
                    .collect(Collectors.toSet());

            deviationDTOList.removeIf(a -> idsToRemove.contains(a.getDeviationId()));


            List<CompDeviationDTO> finalResponse = new ArrayList<>();
            deviationDTOList.forEach(deviationDTO -> {
                CompDeviationDTO compDeviationDTO = new CompDeviationDTO();
                compDeviationDTO.setDescription(deviationDTO.getDescription());
                compDeviationDTO.setDeviationType(deviationDTO.getDeviationType());
                compDeviationDTO.setDeviationId(deviationDTO.getDeviationId());
                compDeviationDTO.setFpDeviationId(0);
                compDeviationDTO.setFacilityPaperId(facilityPaperId);
                compDeviationDTO.setChecked(false);

                finalResponse.add(compDeviationDTO);
            });

            response.addAll(finalResponse);

        } catch (Exception e) {
            LOG.info("ERROR : getDeviations ", e);
        }
        return response;
    }

    public List<CompDeviationDTO> saveOrUpdateCompDeviations(List<CompDeviationDTO> compDeviationDTOList, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: saveOrUpdateCompDeviations for CompDeviationDTO list : {} by User: {}", compDeviationDTOList, credentialsDTO.getUserName());
        List<CompDeviationDTO> response = new ArrayList<>();
        try {
            Date date = new Date();
            List<CompDeviation> compDeviationList = new ArrayList<>();
            compDeviationDTOList.forEach(compDeviationDTO -> {

                CompDeviation deviation = compDeviationsDao.findById(compDeviationDTO.getFpDeviationId()).orElse(new CompDeviation());

                deviation.setIsChecked(compDeviationDTO.isChecked() ? AppsConstants.YesNo.Y.toString() : AppsConstants.YesNo.N.toString());
                deviation.setDivComment(compDeviationDTO.getDivComment());
                deviation.setDescription(compDeviationDTO.getDescription());
                deviation.setDeviationType(compDeviationDTO.getDeviationType());

                if (deviation.getFpDeviationId() != null && deviation.getFpDeviationId() != 0) {
                    deviation.setModifiedDate(date);
                    deviation.setModifiedBy(credentialsDTO.getUserName());
                } else {
                    deviation.setDeviationId(compDeviationDTO.getDeviationId());
                    deviation.setFacilityPaperId(compDeviationDTO.getFacilityPaperId());
                    deviation.setStatus(AppsConstants.Status.ACT.toString());
                    deviation.setCreatedDate(date);
                    deviation.setCreatedBy(credentialsDTO.getUserName());
                }

                compDeviationList.add(deviation);

            });
           List<CompDeviation> deviations = compDeviationsDao.saveAll(compDeviationList);
            response = deviations.stream().map(CompDeviationDTO :: new).collect(Collectors.toList());
        } catch (Exception ex) {

            LOG.info("ERROR : Failed Save or Update CompDeviations with Exception : {}", ex);
            throw new AppsException("Failed to Save or Update CompDeviations.");

        }

        return response;
    }

    public DigitalApplicationDTO getDigitalFormApplicationContent(DigitalApplicationReq digitalApplicationReq) throws AppsException {

        DigitalApplicationDTO digitalApplicationDTO = new DigitalApplicationDTO();

        try {
            List<DigitalApplicationDTO> applications = digitalApplicationJdbcDao.getDigitalApplicationByLead(digitalApplicationReq.getLeadID());
            if (applications != null && !applications.isEmpty()) {

                String content = "";
                for (DigitalApplicationDTO application : applications) {
                    String applicationContent = application.getDocumentContent();
                    Document doc = Jsoup.parse(applicationContent);
                    String cssQuery = "[id='".concat(digitalApplicationReq.getSection()).concat("']");
                    Elements elements = doc.select(cssQuery);

                    if (elements != null && !elements.isEmpty()) {
                        for (Element element : elements) {
                            content = content.concat(element.toString()).concat("<br/><br/><br/>");
                        }
                    }
                }

                //get template
                String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                        + "cp" + File.separator + "html" + File.separator + "TemplateStructure.html";

                Context context = new Context();
                Map<String, Object> params = new HashMap<>();
                params.put("tempContent", content);
                context.setVariables(params);
                String preparedContent = this.templateEngine.process(templatePath, context);
                digitalApplicationDTO.setDocumentContent(preparedContent);
            }
        } catch (Exception e) {
            LOG.info("Error: Get Digital Form Application Content", e);
        }

        return digitalApplicationDTO;
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CustomerClassificationDTO> getCustomerClassification(FPCustomerClassificationDTO classificationDTO) throws AppsException {

        List<CustomerClassification> all = customerClassificationDao.findAll();

        List<FPCustomerClassification> saved =
                fpCustomerClassificationDao
                        .findByFacilityPaperFacilityPaperIDAndCustomerCasCustomerID(
                                classificationDTO.getFacilityPaperId(),
                                classificationDTO.getCustomerId());

        Set<Long> savedIds = saved.stream()
                .map(e -> e.getClassification().getId())
                .collect(Collectors.toSet());

        Map<Long, CustomerClassificationDTO> nodeById = new HashMap<>();

        for (CustomerClassification c : all) {
            CustomerClassificationDTO dto = new CustomerClassificationDTO(c.getId(), c.getDescription());

            if (savedIds.contains(c.getId())) {
                dto.setSelected("Y");
                dto.setComment(saved.stream()
                        .filter(e -> e.getClassification().getId().equals(c.getId()))
                        .findFirst()
                        .map(FPCustomerClassification::getComment)
                        .orElse(""));
            } else {
                dto.setSelected("N");
            }

            nodeById.put(c.getId(), dto);
        }

        List<CustomerClassificationDTO> roots = new ArrayList<>();

        for (CustomerClassification c : all) {

            CustomerClassificationDTO node = nodeById.get(c.getId());
            CustomerClassification parent = c.getParent();

            if (parent == null || parent.getId() == null || parent.getId() == 0) {
                roots.add(node);
            } else {
                CustomerClassificationDTO parentNode = nodeById.get(parent.getId());

                if (parentNode != null) {
                    parentNode.getChildren().add(node);
                } else {
                    roots.add(node);
                }
            }
        }

        return roots;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CustomerClassificationDTO> saveCustomerClassification(FPCustomerClassificationDTO dto) throws AppsException {
        LOG.info("START: saveCustomerClassification for FacilityPaperId: {}, CustomerId: {} by User: {}", dto.getFacilityPaperId(), dto.getCustomerId(), dto.getCreatedBy());

        try {

            Date now = new Date();

            List<FPCustomerClassification> existing = fpCustomerClassificationDao.findByFacilityPaperFacilityPaperIDAndCustomerCasCustomerID(dto.getFacilityPaperId(), dto.getCustomerId());
            LOG.info("Existing classifications found: {}", existing.size());

            if (!existing.isEmpty()) {
                fpCustomerClassificationDao.deleteAll(existing);
                fpCustomerClassificationDao.flush();
            }

            List<Long> classificationIds = dto.getClassificationDTOList()
                    .stream()
                    .map(CustomerClassificationDTO::getId)
                    .collect(Collectors.toList());

            List<CustomerClassification> classifications = customerClassificationDao.findAllById(classificationIds);

            if (classifications.size() != classificationIds.size()) {
                LOG.warn("Mismatch in classification count. Expected: {}, Found: {}", classificationIds.size(), classifications.size());
                throw new AppsException("Some classifications not found for the provided IDs.");
            } else {
                LOG.info("Successfully fetched all classifications for IDs: {}", classificationIds);
            }

            List<FPCustomerClassification> newEntities = new ArrayList<>();

            FacilityPaper facilityPaper = facilityPaperDao.findById(dto.getFacilityPaperId())
                    .orElseThrow(() -> new AppsException("Facility Paper not found."));

            CASCustomer customer = casCustomerDao.findById(dto.getCustomerId())
                    .orElseThrow(() -> new AppsException("Customer not found."));

            Map<Long, CustomerClassification> classificationMap = classifications.stream().collect(Collectors.toMap(CustomerClassification::getId, c -> c));

            for (CustomerClassificationDTO classificationDTO : dto.getClassificationDTOList()) {

                CustomerClassification classification = classificationMap.get(classificationDTO.getId());

                if (classification == null) {
                    throw new AppsException("Classification ID not found: " + classificationDTO.getId());
                }

                FPCustomerClassification entity = new FPCustomerClassification();

                entity.setFacilityPaper(facilityPaper);
                entity.setCustomer(customer);
                entity.setClassification(classification);
                entity.setComment(classificationDTO.getComment());   // ✅ SAVE COMMENT
                entity.setCreatedBy(dto.getCreatedBy());
                entity.setCreatedDate(new Date());

                newEntities.add(entity);
            }


            LOG.info("Total new classifications to save: {}", newEntities.size());

            List<FPCustomerClassification> saved = fpCustomerClassificationDao.saveAll(newEntities);

            LOG.info("Total classifications saved: {}", saved.size());

            Set<Long> savedIds = saved.stream()
                    .map(e -> e.getClassification().getId())
                    .collect(Collectors.toSet());

            return buildClassificationTreeWithSelection(dto.getFacilityPaperId(), dto.getCustomerId());

        } catch (Exception e) {
            LOG.error("Failed to save Customer Classification for FacilityPaperId: {}, CustomerId: {}. Error: {}", dto.getFacilityPaperId(), dto.getCustomerId(), e.getMessage(), e);
            throw new AppsException("Failed to save Customer Classification");
        }
    }

    private List<CustomerClassificationDTO> buildClassificationTreeWithSelection(
            Integer facilityPaperId,
            Integer customerId) {

        List<CustomerClassification> all = customerClassificationDao.findAll();

        List<FPCustomerClassification> saved =
                fpCustomerClassificationDao
                        .findByFacilityPaperFacilityPaperIDAndCustomerCasCustomerID(
                                facilityPaperId,
                                customerId);

        // Map saved classificationId → comment
        Map<Long, String> savedMap = saved.stream()
                .collect(Collectors.toMap(
                        e -> e.getClassification().getId(),
                        FPCustomerClassification::getComment
                ));

        Map<Long, CustomerClassificationDTO> nodeById = new HashMap<>();

        for (CustomerClassification c : all) {

            CustomerClassificationDTO dto =
                    new CustomerClassificationDTO(c.getId(), c.getDescription());

            if (savedMap.containsKey(c.getId())) {
                dto.setSelected("Y");
                dto.setComment(savedMap.get(c.getId()));   // ✅ RETURN COMMENT
            } else {
                dto.setSelected("N");
            }

            nodeById.put(c.getId(), dto);
        }

        List<CustomerClassificationDTO> roots = new ArrayList<>();

        for (CustomerClassification c : all) {

            CustomerClassificationDTO node = nodeById.get(c.getId());
            CustomerClassification parent = c.getParent();

            if (parent == null || parent.getId() == null || parent.getId() == 0) {
                roots.add(node);
            } else {
                CustomerClassificationDTO parentNode =
                        nodeById.get(parent.getId());

                if (parentNode != null) {
                    parentNode.getChildren().add(node);
                } else {
                    roots.add(node);
                }
            }
        }

        return roots;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CompDeviationDTO> refreshCompDeviations(Integer facilityPaperId, CredentialsDTO credentialsDTO) throws AppsException {
        List<CompDeviationDTO> response = new ArrayList<>();
        LOG.info("START: refreshCompDeviations by facilityPaperId : {} by User: {}", facilityPaperId, credentialsDTO.getUserName());

        try {
            int numberOfDeletedRows = compDeviationsDao.deleteAllByFacilityPaperId(facilityPaperId);
            if (numberOfDeletedRows <= 0) {
                throw new AppsException("Did not find any deviations to corresponding facilityPaperId");
            }
            response = getCompDeviations(facilityPaperId, credentialsDTO);
        } catch (AppsException ex) {
            LOG.info("ERROR : Failed refresh CompDeviations with Exception : {}", ex);
            throw ex;
        } catch (Exception ex) {
            LOG.info("ERROR : Failed Save or Update CompDeviations with Exception : {}", ex);
            throw new AppsException("Could not refresh the deviations");
        }

        return response;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FPMDReviewCommentDTO> saveMDReviewComment(FPMDReviewCommentDTO fpmdReviewCommentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        List<FPMDReviewCommentDTO> response = new ArrayList<>();
        Date date = new Date();

        try {
            FPMDReviewComment fpmdReviewComment = new FPMDReviewComment();

            FacilityPaper facilityPaper = facilityPaperDao.findById(fpmdReviewCommentDTO.getFacilityPaperId())
                    .orElseThrow(() -> new AppsException("Facility paper not found"));

            fpmdReviewComment.setFacilityPaper(facilityPaper);
            fpmdReviewComment.setComment(fpmdReviewCommentDTO.getComment());
            fpmdReviewComment.setIsView(AppsConstants.YesNo.N);
            fpmdReviewComment.setCreatedBy(credentialsDTO.getUserName());
            fpmdReviewComment.setCreatedUserWC(Integer.parseInt(credentialsDTO.getUpmGroupCode()));
            fpmdReviewComment.setCreatedDate(date);
            fpmdReviewComment.setCreatedUserDisplayName(credentialsDTO.getDisplayName());

            fpmdReviewCommentDao.saveAndFlush(fpmdReviewComment);

            List<FPMDReviewComment> comments = fpmdReviewCommentDao.findAllByFacilityPaper(facilityPaper);
            if (comments != null) {
                response = comments.stream().map(FPMDReviewCommentDTO::new).collect(Collectors.toList());
            }

        } catch (Exception e) {
            LOG.info("Failed to save review comment: ", e);
            throw new AppsException("Failed to save review comment.");
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FPMDReviewCommentDTO> markAsViewMDComments(FPMDReviewCommentDTO fpmdReviewCommentDTO, CredentialsDTO credentialsDTO) throws AppsException {
        List<FPMDReviewCommentDTO> response = new ArrayList<>();
        try {
            FPMDReviewComment fpmdReviewComment = fpmdReviewCommentDao.findById(fpmdReviewCommentDTO.getFpCommentID())
                    .orElseThrow(() -> new AppsException("Comment not found."));

            FacilityPaper facilityPaper = facilityPaperDao.findById(fpmdReviewCommentDTO.getFacilityPaperId())
                    .orElseThrow(() -> new AppsException("Facility paper not found"));

            fpmdReviewComment.setIsView(AppsConstants.YesNo.Y);
            fpmdReviewCommentDao.saveAndFlush(fpmdReviewComment);

            List<FPMDReviewComment> comments = fpmdReviewCommentDao.findAllByFacilityPaper(facilityPaper);
            if (comments != null) {
                response = comments.stream().map(FPMDReviewCommentDTO::new).collect(Collectors.toList());
            }

        } catch (Exception e) {
            LOG.info("Failed to save review comment: ", e);
            throw new AppsException("Failed to save review comment.");
        }
        return response;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CASCustomerBankDetailsDTO> getCustomerBankDetails(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: getCustomerBankDetails for FacilityPaperId: {} by User: {}", facilityPaperID, credentialsDTO.getUserName());
        List<CASCustomerBankDetailsDTO> response = new ArrayList<>();
        try {
            LOG.info("Fetching CAS Customers for FacilityPaperId: {}", facilityPaperID);
            List<CASCustomer> casCustomerList = casCustomerDao.findAllByFacilityPaperFacilityPaperID(facilityPaperID);
            FacilityPaper facilityPaper = facilityPaperDao.findById(facilityPaperID).orElseThrow(() -> new AppsException("Facility Paper not found."));

            String facilityBankAccountId = facilityPaper.getBankAccountID();
            LOG.info("Facility Bank Account ID: {}", facilityBankAccountId);
            // Get the primary customer
            CASCustomer primaryCustomer = casCustomerList.stream()
                    .filter(c -> AppsConstants.YesNo.Y.equals(c.getIsPrimary()))
                    .findFirst()
                    .orElse(null);

            if (primaryCustomer != null) {

                Set<CASCustomerBankDetail> customerBankDetailsList =
                        primaryCustomer.getCASCustomerBankDetailSet();

                if (customerBankDetailsList != null && !customerBankDetailsList.isEmpty()) {

                    response = customerBankDetailsList.stream()
                            .map(CASCustomerBankDetailsDTO::new)
                            .collect(Collectors.toList());

                    boolean exists = response.stream()
                            .anyMatch(acc -> acc.getBankAccountNumber().equals(facilityBankAccountId));

                    // If facility bank account is not in list, add it
                    if (!exists && facilityBankAccountId != null) {

                        CASCustomerBankDetailsDTO dto = new CASCustomerBankDetailsDTO();
                        dto.setBankAccountNumber(facilityBankAccountId);

                        response.add(dto);
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("Failed to fetch customer bank details: ", e);
            throw new AppsException("Failed to fetch customer bank details.");
        }
        LOG.info("END: getCustomerBankDetails for FacilityPaperId: {} by User: {} with response size: {}", facilityPaperID, credentialsDTO.getUserName(), response.size());
        return response;
    }
}