package com.itechro.cas.service.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.dao.bccpaper.*;
import com.itechro.cas.dao.bccpaper.jdbc.BCCPaperJdbcDao;
import com.itechro.cas.dao.bccpaper.jdbc.BCCPaperRefProc;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.bccpaper.*;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.bccpaper.*;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.SearchFacilityPaperRQ;
import com.itechro.cas.model.dto.finacle.CompReportCusLoan;
import com.itechro.cas.model.dto.finacle.CompReportingData;
import com.itechro.cas.model.dto.finacle.request.CompReportingListReq;
import com.itechro.cas.model.dto.finacle.request.LoanAccCovenantReqDTO;
import com.itechro.cas.model.dto.finacle.response.LoanAccountCovenantDTO;
import com.itechro.cas.model.dto.integration.request.finacle.AccCollateralRequest;
import com.itechro.cas.model.dto.integration.request.finacle.CompReportingRequest;
import com.itechro.cas.model.dto.integration.request.finacle.LimitNodeRequest;
import com.itechro.cas.model.dto.integration.request.finacle.ReportingDateRange;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseDTO;
import com.itechro.cas.model.dto.integration.response.CommissionReportingResponse;
import com.itechro.cas.model.dto.integration.response.finacle.AccCollateralResponse;
import com.itechro.cas.model.dto.integration.response.finacle.CompReportingResponse;
import com.itechro.cas.model.dto.integration.response.finacle.LimitNodeResponse;
import com.itechro.cas.model.dto.integration.response.finacle.ReportingDataResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.bccpaper.pdf.BCCReportCreator;
import com.itechro.cas.service.bccpaper.support.BCCCreateBuilder;
import com.itechro.cas.service.bccpaper.support.BCCUpdateBuilder;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class BCCPaperService implements EnvironmentAware {

    private Environment environment;

    private final Object guard = new Object();

    @Autowired
    BCCPaperDao bccPaperDao;

    @Autowired
    FacilityPaperDao facilityPaperDao;

    @Autowired
    ApplicationFormDao applicationFormDao;

    @Autowired
    BCCFacilityDao bccFacilityDao;

    @Autowired
    BCCCompanyDirectorDao bccCompanyDirectorDao;

    @Autowired
    BCCRiskRatingYearDao bccRiskRatingYearDao;

    @Autowired
    BccCustomerCribDetailDao customerCribDetailDao;

    @Autowired
    BCCPaperJdbcDao bccPaperJdbcDao;

    @Autowired
    BccCustomerCribDetailDao bccCustomerCribDetailDao;

    @Autowired
    BCCPaperRefProc bccPaperRefProc;

    @Autowired
    IntegrationService integrationService;

    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine templateEngine;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private static final Logger LOG = LoggerFactory.getLogger(BCCPaperService.class);

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getBCCPaperRefCode() throws AppsException {
        String ref = null;
        synchronized (guard) {
            ref = bccPaperRefProc.executeFunction();
        }
        return ref;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO createBCCPaper(BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : BCC Paper Create : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaper boardCreditCommitteePaper;
        FacilityPaper facilityPaper;
        BCCCreateBuilder bccCreateBuilder = new BCCCreateBuilder(boardCreditCommitteePaperDTO, credentialsDTO);
        bccCreateBuilder.setBccPaperRefNumber(this.getBCCPaperRefCode());
        bccCreateBuilder.setApplicationFormDao(applicationFormDao);
        bccCreateBuilder.setFacilityPaperDao(facilityPaperDao);
        bccCreateBuilder.setTemplateEngine(templateEngine);
        bccCreateBuilder.setEnvironment(environment);
        bccCreateBuilder.setBccPaperDao(bccPaperDao);
        try{
            BranchLoadResponseDTO branchLoadResponseDTO = integrationService.getBranchList(credentialsDTO).getBranchResponse(boardCreditCommitteePaperDTO.getBranchCode());
            if (branchLoadResponseDTO != null) {
                bccCreateBuilder.setBranchName(branchLoadResponseDTO.getBranchName());
            }
        } catch (Exception e) {
            LOG.info("ERROR:", e);
        }
        bccCreateBuilder.init()
                .buildBCCValues()
                .mapApplicationFormDetails()
                .setCustomer()
                .mapFacilitiesData()
                .facilityTotalCalculations()
                .mapCommonSecurities()
                .mapDirectorDetails()
                .mapShareholderDetails()
                .mapRiskRatingYear()
                .setFPExposureCompany()
                .setFPExposureGroup()
                .updateFacilityPaper()
                .generateAndSavePDFReport();

        facilityPaper = bccCreateBuilder.getFacilityPaper();
        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        LOG.info("END : BCC Paper Create : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return new FacilityPaperDTO(facilityPaper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO updateBCCPaper(BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : BCC Paper update : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaper boardCreditCommitteePaper;

        BCCUpdateBuilder bccUpdateBuilder = new BCCUpdateBuilder(boardCreditCommitteePaperDTO, credentialsDTO);
        bccUpdateBuilder.setBccPaperDao(this.bccPaperDao);
        bccUpdateBuilder.setBccFacilityDao(this.bccFacilityDao);
        bccUpdateBuilder.setBccRiskRatingYearDao(this.bccRiskRatingYearDao);
        bccUpdateBuilder.setBccCompanyDirectorDao(this.bccCompanyDirectorDao);
        bccUpdateBuilder.setBccCustomerCribDetailDao(this.bccCustomerCribDetailDao);
        boardCreditCommitteePaper = bccUpdateBuilder.init()
                .updateBasicInfo()
                .updateRiskRatingYear()
                .updateCompanyDirectors()
                .updateStrength()
                .updateCostOfFund()
                .updateCustomerCribDetails()
                .updateBCCFacilities()
                .updateCommonSecurityText()
                .updateBCCExposureCompany()
                .updateBCCExposureGroup()
                .updateException()
                .updateOfficers()
                .getBoardCreditCommitteePaper();

        boardCreditCommitteePaper = bccPaperDao.saveAndFlush(boardCreditCommitteePaper);

        //TODO WEB AUDIT
        LOG.info("END : BCC Paper save or update : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return new BoardCreditCommitteePaperDTO(boardCreditCommitteePaper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO updateBCCPDFReport(BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : BCC Paper PDF Report : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        BoardCreditCommitteePaper boardCreditCommitteePaper = bccPaperDao.getOne(boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID());
        boardCreditCommitteePaper.setPdfReport(boardCreditCommitteePaperDTO.getPdfReport());
        boardCreditCommitteePaper.setModifiedBy(credentialsDTO.getUserName());
        boardCreditCommitteePaper.setModifiedDate(new Date());
        boardCreditCommitteePaper = bccPaperDao.saveAndFlush(boardCreditCommitteePaper);
        LOG.info("END : BCC Paper PDF Report : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return new BoardCreditCommitteePaperDTO(boardCreditCommitteePaper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO getBCCPaperByFacilityPaperByID(BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO, CredentialsDTO credentialsDTO) throws AppsException {
        return new BoardCreditCommitteePaperDTO(bccPaperDao.findByFacilityPaper_FacilityPaperIDAndStatus(boardCreditCommitteePaperDTO.getFacilityPaperID(), AppsConstants.Status.ACT));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO saveOrUpdateCompanyDirectorDetails(BccCompanyDirectorDTO bccCompanyDirectorDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : BCC Paper Company Director Save or update : {} : {}", bccCompanyDirectorDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaper boardCreditCommitteePaper = bccPaperDao.getOne(bccCompanyDirectorDTO.getBoardCreditCommitteePaperID());

        Date date = new Date();
        boolean isNew = bccCompanyDirectorDTO.getBccCompanyDirectorID() == null;
        BccCompanyDirector bccCompanyDirector;
        if (isNew) {
            bccCompanyDirector = new BccCompanyDirector();
            bccCompanyDirector.setCreatedBy(credentialsDTO.getUserName());
            bccCompanyDirector.setCreatedDate(date);

        } else {
            bccCompanyDirector = bccCompanyDirectorDao.getOne(bccCompanyDirectorDTO.getBccCompanyDirectorID());
            bccCompanyDirector.setModifiedBy(credentialsDTO.getUserName());
            bccCompanyDirector.setModifiedDate(date);
        }
        bccCompanyDirector.setCompanyDirectorName(bccCompanyDirectorDTO.getCompanyDirectorName());
        bccCompanyDirector.setAge(bccCompanyDirectorDTO.getAge());
        bccCompanyDirector.setNicOrBRN(bccCompanyDirectorDTO.getNicOrBRN());
        bccCompanyDirector.setAddress(bccCompanyDirectorDTO.getAddress());
        bccCompanyDirector.setShareHolding(bccCompanyDirectorDTO.getShareHolding());
        bccCompanyDirector.setStatus(bccCompanyDirectorDTO.getStatus());

        boardCreditCommitteePaper.addBccCompanyDirector(bccCompanyDirector);

        LOG.info("END : BCC Paper Company Director Save or update : {} : {}", bccCompanyDirectorDTO, credentialsDTO.getUserID());
        return new BoardCreditCommitteePaperDTO(boardCreditCommitteePaper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO saveOrUpdateRiskRatingYear(BccRiskRatingYearDTO bccRiskRatingYearDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : BCC Paper Risk Rating Save or update : {} : {}", bccRiskRatingYearDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaper boardCreditCommitteePaper = bccPaperDao.getOne(bccRiskRatingYearDTO.getBoardCreditCommitteePaperID());

        Date date = new Date();
        boolean isNew = bccRiskRatingYearDTO.getBccRiskRatingYearID() == null;
        BccRiskRatingYear bccRiskRatingYear;
        if (isNew) {
            bccRiskRatingYear = new BccRiskRatingYear();
            bccRiskRatingYear.setCreatedBy(credentialsDTO.getUserName());
            bccRiskRatingYear.setCreatedDate(date);
        } else {
            bccRiskRatingYear = bccRiskRatingYearDao.getOne(bccRiskRatingYearDTO.getBccRiskRatingYearID());
            bccRiskRatingYear.setModifiedBy(credentialsDTO.getUserName());
            bccRiskRatingYear.setModifiedDate(date);
        }
        bccRiskRatingYear.setRiskScore(bccRiskRatingYearDTO.getRiskScore());
        bccRiskRatingYear.setRiskGrading(bccRiskRatingYearDTO.getRiskGrading());
        bccRiskRatingYear.setRiskYear(bccRiskRatingYearDTO.getRiskYear());
        bccRiskRatingYear.setStatus(bccRiskRatingYearDTO.getStatus());

        boardCreditCommitteePaper.addBccRiskRatingYear(bccRiskRatingYear);

        LOG.info("END : BCC Paper Risk Rating Save or update : {} : {}", bccRiskRatingYearDTO, credentialsDTO.getUserID());
        return new BoardCreditCommitteePaperDTO(boardCreditCommitteePaper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO saveOrUpdateBCCCustomerCribDetails(BccCustomerCribDetailDTO bccCustomerCribDetailDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : BCC Paper Customer CribDetails update : {} : {}", bccCustomerCribDetailDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaper boardCreditCommitteePaper = bccPaperDao.getOne(bccCustomerCribDetailDTO.getBoardCreditCommitteePaperID());

        Date date = new Date();
        boolean isNew = bccCustomerCribDetailDTO.getBccCustomerCribDetailsID() == null;
        BccCustomerCribDetail bccCustomerCribDetail;
        if (isNew) {
            bccCustomerCribDetail = new BccCustomerCribDetail();
            bccCustomerCribDetail.setCreatedBy(credentialsDTO.getUserName());
            bccCustomerCribDetail.setCreatedDate(date);
        } else {
            bccCustomerCribDetail = bccCustomerCribDetailDao.getOne(bccCustomerCribDetailDTO.getBccCustomerCribDetailsID());
            bccCustomerCribDetail.setModifiedBy(credentialsDTO.getUserName());
            bccCustomerCribDetail.setModifiedDate(date);
        }
        bccCustomerCribDetail.setBorrower(bccCustomerCribDetailDTO.getBorrower());
        bccCustomerCribDetail.setCribStatus(bccCustomerCribDetailDTO.getCribStatus());
        bccCustomerCribDetail.setRemark(bccCustomerCribDetailDTO.getRemark());
        if (StringUtils.isNotBlank(bccCustomerCribDetailDTO.getReportDateStr())) {
            bccCustomerCribDetail.setReportDate(CalendarUtil.getDefaultParsedDateOnly(bccCustomerCribDetailDTO.getReportDateStr()));
        }
        bccCustomerCribDetail.setStatus(bccCustomerCribDetailDTO.getStatus());

        boardCreditCommitteePaper.addCustomerCribDetails(bccCustomerCribDetail);

        boardCreditCommitteePaper = bccPaperDao.saveAndFlush(boardCreditCommitteePaper);

        LOG.info("END :  BCC Paper Customer CribDetails update : {} : {}", bccCustomerCribDetailDTO, credentialsDTO.getUserID());
        return new BoardCreditCommitteePaperDTO(boardCreditCommitteePaper);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO saveOrUpdateBccFacilities(BccFacilityDTO bccFacilityDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : BCC Paper Facilities Save or update : {} : {}", bccFacilityDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaper boardCreditCommitteePaper = bccPaperDao.getOne(bccFacilityDTO.getBoardCreditCommitteePaperID());

        Date date = new Date();
        boolean isNew = bccFacilityDTO.getBccFacilityID() == null;
        BccFacility bccFacility;
        if (isNew) {
            bccFacility = new BccFacility();
            bccFacility.setCreatedBy(credentialsDTO.getUserName());
            bccFacility.setCreatedDate(date);
        } else {
            bccFacility = bccFacilityDao.getOne(bccFacilityDTO.getBccFacilityID());
            bccFacility.setModifiedBy(credentialsDTO.getUserName());
            bccFacility.setModifiedDate(date);
        }
        bccFacility.setType(bccFacilityDTO.getType());
        bccFacility.setDateGranted(bccFacilityDTO.getDateGranted());
        bccFacility.setAmount(bccFacilityDTO.getAmount());
        bccFacility.setInterestRate(bccFacilityDTO.getInterestRate());
        bccFacility.setPurpose(bccFacilityDTO.getPurpose());
        bccFacility.setOutstandingAsAt(bccFacilityDTO.getOutstandingAsAt());
        bccFacility.setSettlementDate(bccFacilityDTO.getSettlementDate());
        bccFacility.setSettlementPlan(bccFacilityDTO.getSettlementPlan());
        bccFacility.setSecurity(bccFacilityDTO.getSecurity());
        bccFacility.setStatus(bccFacilityDTO.getStatus());
        boardCreditCommitteePaper.addBccFacility(bccFacility);

        boardCreditCommitteePaper = bccPaperDao.saveAndFlush(boardCreditCommitteePaper);
        LOG.info("END : BCC Paper Facilities Save or update : {} : {}", bccFacilityDTO, credentialsDTO.getUserID());
        return new BoardCreditCommitteePaperDTO(boardCreditCommitteePaper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<FacilityPaperDTO> getPagedFacilityPaperDTOForBCC(SearchFacilityPaperRQ facilityPaperSearchRQ) throws AppsException{
        return bccPaperJdbcDao.getPagedFacilityPaperDTOForBCC(facilityPaperSearchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String getBCCReport(BoardCreditCommitteePaperDTO searchRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get BCC pdf report {} : {}", searchRQ, credentialsDTO);

        BoardCreditCommitteePaper boardCreditCommitteePaper = this.bccPaperDao.getOne(searchRQ.getBoardCreditCommitteePaperID());

        if (boardCreditCommitteePaper.getStatus() == AppsConstants.Status.ACT) {
            BCCReportCreator contentCreator = new BCCReportCreator(boardCreditCommitteePaper);
            contentCreator.setEnvironment(this.environment);
            contentCreator.setTemplateEngine(this.templateEngine);

            String pdfContent = contentCreator.getPdfContent();

            LOG.info("END: Get BCC pdf report {} : {}", searchRQ, credentialsDTO);
            return pdfContent;
        } else {
            LOG.error("ERROR: BCC pdf report {}", searchRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_BCC_PAPER_NOT_FOUND);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO deactivateBccPaper(BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Deactivate the BCC Paper: {} by: {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserName());
        FacilityPaper facilityPaper = facilityPaperDao.getOne(boardCreditCommitteePaperDTO.getFacilityPaperID());

        facilityPaper.setIsBccCreated(AppsConstants.YesNo.N);
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(new Date());
        facilityPaperDao.saveAndFlush(facilityPaper);

        BoardCreditCommitteePaper boardCreditCommitteePaper = bccPaperDao.findByFacilityPaper_FacilityPaperIDAndStatus(boardCreditCommitteePaperDTO.getFacilityPaperID(), AppsConstants.Status.ACT);
        boardCreditCommitteePaper.setStatus(AppsConstants.Status.INA);
        boardCreditCommitteePaper.setModifiedBy(credentialsDTO.getUserName());
        boardCreditCommitteePaper.setModifiedDate(new Date());
        bccPaperDao.saveAndFlush(boardCreditCommitteePaper);

//        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
//        loadOptionDTO.loadCustomerCribDetail();

        LOG.info("END: Deactivate the BCC Paper: {} by: {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserName());
        return new FacilityPaperDTO(facilityPaper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page<FacilityPaperDTO> getPagedFacilityPaperDTOForBCCForUserName(SearchFacilityPaperRQ facilityPaperSearchRQ, CredentialsDTO credentialsDTO) throws AppsException{
        return bccPaperJdbcDao.getPagedFacilityPaperDTOForBCCForUserName(facilityPaperSearchRQ, credentialsDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO changeAssignUserBCCPaper(BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Change Assign User : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaper boardCreditCommitteePaper = bccPaperDao.findByFacilityPaper_FacilityPaperIDAndStatus(boardCreditCommitteePaperDTO.getFacilityPaperID(), AppsConstants.Status.ACT);
        boardCreditCommitteePaper.setModifiedBy(credentialsDTO.getUserName());
        boardCreditCommitteePaper.setModifiedDate(new Date());
        boardCreditCommitteePaper.setCurrentAssignUser(credentialsDTO.getUserName());
        boardCreditCommitteePaper.setAssignUserDisplayName(boardCreditCommitteePaperDTO.getAssignUserDisplayName());
        boardCreditCommitteePaper.setCurrentAssignUserDivCode(credentialsDTO.getDivCode());
        bccPaperDao.saveAndFlush(boardCreditCommitteePaper);

        LOG.info("END : Change Assign User : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return new BoardCreditCommitteePaperDTO(boardCreditCommitteePaper);
    }

    public String getComparableContent(CompReportingListReq compReportingListReq) throws AppsException {

        String content = "";

        try {

            String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                    + "bcc" + File.separator + "html" + File.separator + "ComparableStatement" + ".html";

            BCCPrintPreviewDTO bccPrintPreviewDTO = new BCCPrintPreviewDTO();

            //prepare data with covenants
            CompletableFuture<List<CompReportingData>> dataWithCovenantsFuture = prepareLoanCovenantAsync(compReportingListReq.getCompReportingData());
            List<CompReportingData> dataWithCovenants = dataWithCovenantsFuture.get();

            //prepare data with collateral
            CompletableFuture<List<CompReportingData>> dataWithCollateralsFuture = prepareLoanCollateralAsync(dataWithCovenants);
            List<CompReportingData> dataWithCollaterals = dataWithCollateralsFuture.get();

            List<CompReportingData> compReportingData = prepareLoanByType(dataWithCollaterals);
            bccPrintPreviewDTO.setCompReportingData(compReportingData);

            Context context = new Context();
            Map<String, Object> params = new HashMap<>();
            params.put("bccDTO", bccPrintPreviewDTO);
            context.setVariables(params);
            content = this.templateEngine.process(templatePath, context);

        }catch (Exception ex){
            throw new AppsException("An error occurred.");
        }

        return content;
    }

    public CompletableFuture<List<CompReportingData>> prepareLoanCovenantAsync(List<CompReportingData> compReportingData) {
        return CompletableFuture.supplyAsync(() -> {
            List<CompReportingData> processedData = new ArrayList<>();
            try {
                processedData = prepareLoanCovenant(compReportingData);
            } catch (AppsException e) {
                LOG.info("Exception : Loan Account Covenant", e);
            }
            return processedData;
        });
    }

    public CompletableFuture<List<CompReportingData>> prepareLoanCollateralAsync(List<CompReportingData> compReportingData){
        return CompletableFuture.supplyAsync(() -> {
            List<CompReportingData> processedData = new ArrayList<>();
            try {
                processedData = prepareLoanCollateral(compReportingData);
            } catch (AppsException e) {
                LOG.info("Exception : Loan Account Collateral", e);
            }
            return processedData;
        });
    }

    public List<CompReportingData> prepareLoanByType(List<CompReportingData> compReportingData) throws AppsException {
        return compReportingData.stream()
                .peek(data ->
                {
                    List<CompReportCusLoan> termLoans = data.getLoans().stream().filter(loan -> loan.getFacilityType().equals("TL")).collect(Collectors.toList());
                    List<CompReportCusLoan> workingCapitalLoans = data.getLoans().stream().filter(loan -> loan.getFacilityType().equals("WC")).collect(Collectors.toList());
                    data.setTermLoans(termLoans);
                    data.setWorkingCapitalLoans(workingCapitalLoans);
                    data.getLoans().clear();
                }).collect(Collectors.toList());
    }

    public List<CompReportingData> prepareLoanCovenant(List<CompReportingData> compReportingData) throws AppsException {
        for (CompReportingData data : compReportingData) {
            for (CompReportCusLoan loan : data.getLoans()) {
                LoanAccCovenantReqDTO loanAccCovenantReqDTO = new LoanAccCovenantReqDTO();
                loanAccCovenantReqDTO.setRequestId("1224");
                loanAccCovenantReqDTO.setCustId("");
                loanAccCovenantReqDTO.setAcctId(loan.getForacid());

                try {
                    LoanAccountCovenantDTO loanAccountCovenants = this.integrationService.getLoanAccountCovenants(loanAccCovenantReqDTO);

                    if (loanAccountCovenants != null && !loanAccountCovenants.getCovenant().isEmpty()) {
                        List<String> covenants = loanAccountCovenants.getCovenant().stream()
                                .filter(c -> c.getCovenantInq().get(0).getCovTyp().equals("A"))
                                .map(c -> c.getCovenantInq().get(0).getCovRem())
                                .collect(Collectors.toList());
                        loan.setCovenants(covenants);
                    }
                } catch (Exception e) {
                    LOG.info("Exception : Loan Account Covenant", e);
                }
            }
        }

        return compReportingData;
    }

    public List<CompReportingData> prepareLoanCollateral(List<CompReportingData> compReportingData) throws AppsException {
        for (CompReportingData data : compReportingData) {
            for (CompReportCusLoan loan : data.getLoans()) {
                try {
                    if (loan.getIimitB2kId() == null || loan.getIimitB2kId().isEmpty()){
                        AccCollateralRequest accCollateralRequest = new AccCollateralRequest();
                        accCollateralRequest.setType("A");
                        accCollateralRequest.setAcctId(loan.getForacid());
                        accCollateralRequest.setNodeId("");
                        AccCollateralResponse loanCollaterals = this.integrationService.getLoanAccountCollateral(accCollateralRequest);

                        if (loanCollaterals != null && !loanCollaterals.getCollateral().isEmpty()){
                            List<String> collaterals = loanCollaterals.getCollateral().stream()
                                    .filter(c -> !c.getSecu_desc().toString().equals("{}"))
                                    .map(c -> c.getSecu_desc().toString())
                                    .collect(Collectors.toList());
                            loan.setCollateral(collaterals);
                        }
                    }else {
                        List<String> collaterals = new ArrayList<>();
                        String parentLimitB2kId = "";
                        do {
                            LimitNodeRequest limitNodeRequest = new LimitNodeRequest();
                            limitNodeRequest.setReqId("Req_SBF-123456");
                            if (parentLimitB2kId.isEmpty()) {
                                limitNodeRequest.setLimitB2kId(loan.getIimitB2kId());
                            } else {
                                limitNodeRequest.setLimitB2kId(parentLimitB2kId);
                            }

                            LimitNodeResponse limitNodeData = this.integrationService.getLimitNodeData(limitNodeRequest);
                            if (limitNodeData != null && limitNodeData.getNodeDetails() != null) {
                                String nodeId = limitNodeData.getNodeDetails().getLimitPrefix().concat("/"+limitNodeData.getNodeDetails().getLimitSuffix());

                                AccCollateralRequest accCollateralRequest = new AccCollateralRequest();
                                accCollateralRequest.setType("N");
                                accCollateralRequest.setNodeId(nodeId);
                                accCollateralRequest.setAcctId("");
                                AccCollateralResponse loanCollaterals = this.integrationService.getLoanAccountCollateral(accCollateralRequest);

                                if (loanCollaterals != null && !loanCollaterals.getCollateral().isEmpty()) {
                                    collaterals.addAll(loanCollaterals.getCollateral().stream()
                                            .filter(c -> !c.getSecu_desc().toString().equals("{}"))
                                            .map(c -> c.getSecu_desc().toString())
                                            .collect(Collectors.toList()));
                                }
                                parentLimitB2kId = limitNodeData.getNodeDetails().getParentLimitB2kId();
                            }
                        }
                        while (!parentLimitB2kId.isEmpty() && !parentLimitB2kId.equals("ROOT"));

                        loan.setCollateral(collaterals);
                    }
                } catch (Exception e) {
                    List<String> collaterals = new ArrayList<>();
                    loan.setCollateral(collaterals);
                    LOG.info("Exception : Loan Account Collateral", e);
                }
            }
        }

        return compReportingData;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public BoardCreditCommitteePaperDTO bCCPaperSubmission(Integer facilityPaperId, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : BCC Paper Submission : {} : {}", facilityPaperId, credentialsDTO.getUserID());

        BoardCreditCommitteePaper boardCreditCommitteePaper = bccPaperDao.findByFacilityPaper_FacilityPaperIDAndStatus(facilityPaperId, AppsConstants.Status.ACT);
        boardCreditCommitteePaper.setModifiedBy(credentialsDTO.getUserName());
        boardCreditCommitteePaper.setModifiedDate(new Date());
        boardCreditCommitteePaper.setIsSubmitted(AppsConstants.YesNo.Y);

        bccPaperDao.saveAndFlush(boardCreditCommitteePaper);

        LOG.info("END : BCC Paper Submission : {} : {}", facilityPaperId, credentialsDTO.getUserID());
        return new BoardCreditCommitteePaperDTO(boardCreditCommitteePaper);
    }

    public CompReportingResponse getReportingData(CompReportingRequest compReportingRequest) throws AppsException{
        CompReportingResponse responseDTO = new CompReportingResponse();
        List<ReportingDataResponse> responseDataList = new ArrayList<>();

        try {
            for (ReportingDateRange dateRange : compReportingRequest.getDateRanges()) {

                CompletableFuture<CompReportingResponse> asyncResponse = prepareReportingDataAsync(dateRange);
                CompReportingResponse compReportingResponse = asyncResponse.get();

                if (compReportingResponse.getResponseData() != null && !compReportingResponse.getResponseData().isEmpty()) {
                    responseDataList.addAll(compReportingResponse.getResponseData());
                }
            }

            if (!responseDataList.isEmpty()) {
                responseDTO.setResponseData(getSortedReportingData(compReportingRequest, responseDataList));
            }
        } catch (Exception e){
            throw new AppsException("An error occurred.");
        }
        return responseDTO;
    }

    public CompletableFuture<CompReportingResponse> prepareReportingDataAsync(ReportingDateRange dateRange) {
        return CompletableFuture.supplyAsync(() -> {
            CompReportingResponse processedData = new CompReportingResponse();
            try {
                processedData = integrationService.getReportingData(dateRange);
            } catch (AppsException e) {
                LOG.info("Exception : Reporting Data", e);
            }
            return processedData;
        });
    }

    public List<ReportingDataResponse> getSortedReportingData(CompReportingRequest compReportingRequest,List<ReportingDataResponse> dataList){
        List<ReportingDataResponse> result = dataList;
        
        if (Boolean.FALSE.equals(compReportingRequest.getIsAllSector())){
            result = result.stream().filter(data -> data.getSectorCode().equals(compReportingRequest.getSector()))
                    .collect(Collectors.toList());
        }

        if (Boolean.FALSE.equals(compReportingRequest.getIsAllRiskRating())){
            result = result.stream().filter(data -> data.getRiskRating().equals(compReportingRequest.getRiskRating()))
                    .collect(Collectors.toList());
        }

        if (Boolean.FALSE.equals(compReportingRequest.getIsAllProductGroup())){
            result = result.stream().filter(data -> data.getProductGroup().equals(compReportingRequest.getProductGroup()))
                    .collect(Collectors.toList());
        }

        if (Boolean.TRUE.equals(compReportingRequest.getIsInterestRate())){
            result = result.stream().filter(data -> Float.parseFloat(data.getInterestRate()) >= compReportingRequest.getRateFrom() &&
                            Float.parseFloat(data.getInterestRate()) <= compReportingRequest.getRateTo())
                    .collect(Collectors.toList());
        }else {
            List<String> rateCodeTypes = compReportingRequest.getRateCodeTypes();

            result = result.stream().filter(data -> rateCodeTypes.contains(data.getIntTbleCode()) &&
                            Float.parseFloat(data.getPrefRate()) >= compReportingRequest.getRateFrom() &&
                            Float.parseFloat(data.getPrefRate()) <= compReportingRequest.getRateTo())
                    .collect(Collectors.toList());
        }

        return result;
    }

    public CommissionReportingResponse getCommissionReportingData(CompReportingRequest compReportingRequest) throws AppsException{
        CommissionReportingResponse responseDTO = new CommissionReportingResponse();
        List<ReportingDataResponse> responseDataList = new ArrayList<>();

        try {
            for (ReportingDateRange dateRange : compReportingRequest.getDateRanges()) {

                CompletableFuture<CommissionReportingResponse> asyncResponse = prepareCommissionReportingDataAsync(dateRange);
                CommissionReportingResponse compReportingResponse = asyncResponse.get();

                if (compReportingResponse.getReportDtls() != null && !compReportingResponse.getReportDtls().isEmpty()) {
                    responseDataList.addAll(compReportingResponse.getReportDtls());
                }
            }

            if (!responseDataList.isEmpty()) {
                responseDTO.setReportDtls(getSortedCommissionReportingData(compReportingRequest, responseDataList));
            }
        } catch (Exception e){
            throw new AppsException("An error occurred.");
        }
        return responseDTO;
    }

    public CompletableFuture<CommissionReportingResponse> prepareCommissionReportingDataAsync(ReportingDateRange reportingDateRange) {
        return CompletableFuture.supplyAsync(() -> {
            CommissionReportingResponse processedData = new CommissionReportingResponse();
            try {
                processedData = integrationService.getCommissionLoanAccounts(reportingDateRange);
            } catch (AppsException e) {
                LOG.info("Exception : Reporting Data", e);
            }
            return processedData;
        });
    }

    public List<ReportingDataResponse> getSortedCommissionReportingData(CompReportingRequest compReportingRequest,List<ReportingDataResponse> dataList){
        List<ReportingDataResponse> result = dataList;

        if (Boolean.FALSE.equals(compReportingRequest.getIsAllSector())){
            result = dataList.stream().filter(data -> data.getSectorCode().equals(compReportingRequest.getSector()))
                    .collect(Collectors.toList());
        }

        if (Boolean.FALSE.equals(compReportingRequest.getIsAllRiskRating())){
            result = result.stream().filter(data -> data.getRiskRating().equals(compReportingRequest.getRiskRating()))
                    .collect(Collectors.toList());
        }

        return result;
    }
}

