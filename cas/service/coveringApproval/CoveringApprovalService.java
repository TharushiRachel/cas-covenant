package com.itechro.cas.service.coveringApproval;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.coveringApproval.CovAppBasinInfoDao;
import com.itechro.cas.dao.coveringApproval.CovAppCommentDao;
import com.itechro.cas.dao.coveringApproval.CoveringApprovalDao;
import com.itechro.cas.dao.coveringApproval.jdbc.CovAppRefProc;
import com.itechro.cas.dao.coveringApproval.jdbc.CoveringApprovalJdbcDao;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.coveringApproval.CovAppBasicInfo;
import com.itechro.cas.model.domain.coveringApproval.CovAppComment;
import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import com.itechro.cas.model.dto.coveringApproval.*;
import com.itechro.cas.model.dto.integration.request.LoadBankAccountDetailsRQ;
import com.itechro.cas.model.dto.integration.request.LoadTranDataRQ;
import com.itechro.cas.model.dto.integration.response.coveringApproval.CovAppCustomerDTO;
import com.itechro.cas.model.dto.integration.response.coveringApproval.CoveringApprovalTranDetailsDTO;
import com.itechro.cas.model.dto.integration.response.coveringApproval.TranDetailsResponseDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.coveringApproval.command.CoveringApprovalModificationContext;
import com.itechro.cas.service.coveringApproval.command.CoveringApprovalStatusModifier;
import com.itechro.cas.service.coveringApproval.support.CovAppDraftBuilder;
import com.itechro.cas.service.coveringApproval.support.CovAppStatusHandler;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CoveringApprovalService {

    private static final Logger LOG = LoggerFactory.getLogger(CoveringApprovalService.class);

    private final ApplicationContext appContext;

    @Autowired
    private CoveringApprovalJdbcDao coveringApprovalJdbcDao;

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private CoveringApprovalDao coveringApprovalDao;

    private final Object guard = new Object();

    @Autowired
    private CovAppRefProc covAppRefProc;

    @Autowired
    private CustomerDao customerDao;

    private static final AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private CovAppCommentDao covAppCommentDao;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CovAppBasinInfoDao covAppBasinInfoDao;

    @Autowired
    private CoveringApprovalStatusModifier coveringApprovalStatusModifier;

    @Autowired
    public CoveringApprovalService(ApplicationContext appContext) {
        this.appContext = appContext;
    }


    //done
    @Transactional(propagation = Propagation.REQUIRED)
    public CoveringApprovalDashboardCountDTO getCoveringApprovalDashboardCount(CoveringApprovalDashboardRQ coveringApprovalDashboardRQ) {
        Integer dashboardTimePeriodDays = 0;
        LOG.info("START: getCoveringApprovalDashboardCount: {} ", coveringApprovalDashboardRQ);

        dashboardTimePeriodDays = casProperties.getDashboardTimePeriodDays();
        LOG.info("getDashboardTimePeriodDays: {} ", dashboardTimePeriodDays);

        CoveringApprovalDashboardCountDTO dashboardCountDTO;

        dashboardCountDTO = coveringApprovalJdbcDao.getCoveringApprovalDashboardCount(coveringApprovalDashboardRQ, dashboardTimePeriodDays);
        dashboardCountDTO.setDashboardTimePeriodDays(dashboardTimePeriodDays);

        LOG.info("dashboardCountDTO: {} ", dashboardCountDTO);

        LOG.info("END: getCoveringApprovalDashboardCount: {} ", coveringApprovalDashboardRQ);

        return dashboardCountDTO;
    }

    //done
    @Transactional(propagation = Propagation.REQUIRED)
    public Page<CoveringApprovalDashboardDTO> getPagedCoveringApprovalDashboard(CoveringApprovalDashboardRQ coveringApprovalDashboardRQ) {

        Integer dashboardTimePeriodDays = 0;
        LOG.info("START: getPagedLeadDashboard: {} ", coveringApprovalDashboardRQ);

        dashboardTimePeriodDays = casProperties.getDashboardTimePeriodDays();
        LOG.info("getDashboardTimePeriodDays: {} ", dashboardTimePeriodDays);

        return coveringApprovalJdbcDao.getPagedCoveringApprovalDashboardDTO(coveringApprovalDashboardRQ, dashboardTimePeriodDays);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getCovAppRefCode() throws AppsException {
        String ref = null;
        synchronized (guard) {
            ref = covAppRefProc.executeFunction();
        }
        return ref;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String generateRefCode() throws AppsException {
        Date now = new Date();
        // Define the format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // Format the date and time to a string
        String formattedDate = sdf.format(now);
        int nextVal = counter.incrementAndGet();
        return "CA" + formattedDate + nextVal;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CoveringApproval updateCoveringApproval(CoveringApproval coveringApproval, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Covering Approval update : {} : {}", coveringApproval, credentialsDTO.getUserID());
        Date date = new Date();

        CoveringApproval coveringApprovalDb = coveringApprovalDao.findById(coveringApproval.getCovAppId())
                .orElseThrow(() -> new AppsException("Covering Approval with " + coveringApproval.getCovAppId() + " does not exists"));

        coveringApprovalDb.setCurrentStatus(coveringApproval.getCurrentStatus());
        coveringApprovalDb.setModifiedBy(credentialsDTO.getUserName());
        coveringApprovalDb.setModifiedDate(date);
        coveringApprovalDb.setAssignUserDisplayName(coveringApproval.getAssignUserDisplayName());
        coveringApprovalDb.setAssignUserUpmGroupCode(coveringApproval.getAssignUserUpmGroupCode());
        coveringApprovalDb.setAssignUserUpmId(coveringApproval.getAssignUserUpmId());
        coveringApprovalDb.setAssignUserDisplayName(coveringApproval.getCreatedUserDisplayName());
        coveringApprovalDb.setIsAuthorized(coveringApproval.getIsAuthorized());

        return coveringApprovalDao.saveAndFlush(coveringApprovalDb);
    }

//done
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CoveringApprovalDTO draftCoveringApproval(CoveringApprovalDTO coveringApprovalDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Draft Application Form : {} : {}", coveringApprovalDTO, credentialsDTO.getUserID());

        CovAppDraftBuilder covAppDraftBuilder = new CovAppDraftBuilder(coveringApprovalDTO, credentialsDTO);
        covAppDraftBuilder.setCoveringApprovalService(this);
        covAppDraftBuilder.setCoveringApprovalDao(coveringApprovalDao);
        covAppDraftBuilder.setIntegrationService(integrationService);
        covAppDraftBuilder.setCustomerService(customerService);
        covAppDraftBuilder.setCustomerDao(customerDao);
        CoveringApproval coveringApproval = covAppDraftBuilder.buildBaseCoveringApproval()
                .buildComment()
                .buildBasicInfo()
                .buildStatusHistory()
                .getCoveringApproval();

        LOG.info("coveringApproval {}", coveringApproval);
        coveringApproval = coveringApprovalDao.saveAndFlush(coveringApproval);
        CovAppLoadOptionDTO covAppLoadOptionDTO = new CovAppLoadOptionDTO();
        covAppLoadOptionDTO.loadAllData();

        LOG.info("END : Draft Covering Approval : {} : {}", coveringApproval, credentialsDTO.getUserID());

        return new CoveringApprovalDTO(coveringApproval, covAppLoadOptionDTO);
    }

//done
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CoveringApprovalDTO updateCoveringApprovalStatus(CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Update Status Covering Approval Form : {} : {}", coveringApprovalStatusChangeRQ, credentialsDTO.getUserID());

        CoveringApprovalModificationContext context = new CoveringApprovalModificationContext();
        context.setBranchLoadResponseListDTO(integrationService.getBranchList(credentialsDTO));
        context.setCoveringApprovalStatusChangeRQ(coveringApprovalStatusChangeRQ);
        context.setCredentialsDto(credentialsDTO);
        context.setDate(new Date());

        CoveringApproval coveringApproval = coveringApprovalDao.getOne(coveringApprovalStatusChangeRQ.getCovAppId());

        context.setCoveringApproval(coveringApproval);

        CovAppStatusHandler covAppStatusHandler = null;

        switch (coveringApprovalStatusChangeRQ.getCurrentStatus()) {

            case DRAFT:
                covAppStatusHandler = new CovAppStatusHandler(coveringApproval, coveringApprovalStatusChangeRQ, credentialsDTO, casProperties);
                covAppStatusHandler.updateCoveringApproval();
                covAppStatusHandler.recordStatusHistory();
                covAppStatusHandler.transitStatus();
                break;

            case IN_PROGRESS:
                covAppStatusHandler = new CovAppStatusHandler(coveringApproval, coveringApprovalStatusChangeRQ, credentialsDTO, casProperties);
                covAppStatusHandler.updateCoveringApproval();
                covAppStatusHandler.transitStatus();
                covAppStatusHandler.addComment();
                covAppStatusHandler.recordStatusHistory();
                covAppStatusHandler.sendEmailNotificationInProgress(context);
                break;

            case CANCEL:
                covAppStatusHandler = new CovAppStatusHandler(coveringApproval, coveringApprovalStatusChangeRQ, credentialsDTO, casProperties);
                covAppStatusHandler.updateCoveringApproval();
                covAppStatusHandler.transitStatus();
                covAppStatusHandler.addComment();
                covAppStatusHandler.recordStatusHistory();
                covAppStatusHandler.sendEmailNotificationDeclined(context);
                break;

            case REJECTED:
                covAppStatusHandler = new CovAppStatusHandler(coveringApproval, coveringApprovalStatusChangeRQ, credentialsDTO, casProperties);
                covAppStatusHandler.updateCoveringApproval();
                covAppStatusHandler.transitStatus();
                covAppStatusHandler.addComment();
                covAppStatusHandler.recordStatusHistory();
                covAppStatusHandler.setCoveringApprovalJdbcDao(coveringApprovalJdbcDao);
                covAppStatusHandler.sendEmailNotificationReturned(context);
                break;

            case APPROVED:
                covAppStatusHandler = new CovAppStatusHandler(coveringApproval, coveringApprovalStatusChangeRQ, credentialsDTO, casProperties);
                covAppStatusHandler.updateCoveringApproval();
                covAppStatusHandler.transitStatus();
                covAppStatusHandler.addComment();
                covAppStatusHandler.recordStatusHistory();
                covAppStatusHandler.setCoveringApprovalJdbcDao(coveringApprovalJdbcDao);
                covAppStatusHandler.sendEmailNotificationApproved(context);
                break;

            case DELETE:
                covAppStatusHandler = new CovAppStatusHandler(coveringApproval, coveringApprovalStatusChangeRQ, credentialsDTO, casProperties);
                covAppStatusHandler.updateCoveringApproval();
                covAppStatusHandler.addComment();
                covAppStatusHandler.recordStatusHistory();
                covAppStatusHandler.transitStatus();
                break;

            default:
                break;
        }

        coveringApprovalStatusModifier.execute(context);

        coveringApproval = this.coveringApprovalDao.saveAndFlush(coveringApproval);
        LOG.info("END: Update Status Covering Approval Form :{} by :{}", coveringApproval, credentialsDTO.getUserName());

        CovAppLoadOptionDTO covAppLoadOptionDTO = new CovAppLoadOptionDTO();
        covAppLoadOptionDTO.loadAllData();

        return new CoveringApprovalDTO(coveringApproval, covAppLoadOptionDTO);
    }


    //done
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CoveringApprovalDTO getCoveringApprovalByID(Integer covAppId, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Load Covering Approval details by ID : {} ", covAppId);

        CoveringApproval coveringApproval = this.coveringApprovalDao.getOne(covAppId);

        CovAppLoadOptionDTO covAppLoadOptionDTO = new CovAppLoadOptionDTO();
        covAppLoadOptionDTO.loadAllData();

        CoveringApprovalDTO coveringApprovalDTO = new CoveringApprovalDTO(coveringApproval, covAppLoadOptionDTO);

        LOG.info("END : Load Covering Approval details by ID : {} ", covAppId);

        return coveringApprovalDTO;
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public List<CoveringApprovalDTO> getPendingCoveringApprovals(CovAppBasicInfoRQ covAppBasicInfoRQ, CredentialsDTO credentialsDTO) throws AppsException {
//
//        LOG.info("START : Load Covering Approval pending records by Account Number : {} ", covAppBasicInfoRQ.getAccountNumber());
//
//        List<CoveringApproval> coveringApprovalList = coveringApprovalDao.findByAccountNumber(covAppBasicInfoRQ.getAccountNumber());
//
//        LoadBankAccountDetailsRQ loadBankAccountDetailsRQ = new LoadBankAccountDetailsRQ();
//        loadBankAccountDetailsRQ.setRefId("20220224133817459039");
//        loadBankAccountDetailsRQ.setAccountNo(covAppBasicInfoRQ.getAccountNumber());
//
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Define date format
//
//        LOG.info("END : Load Covering Approval pending records by Account Number : {} ", coveringApprovalList.toString());
//
//        List<CoveringApprovalDTO> coveringApprovalDTOList = new ArrayList<>();
//
//        // Initialize variables to track the previous transaction date and balance
//        Date previousTranDate = null;
//        String previousAsAtDateAcctBalance = null;
//        String floatBalance = null;
//        String fundsInClearing = null;
//        String sanctionLimit = null;
//
//        for (CoveringApproval coveringApproval : coveringApprovalList) {
//            if ((!coveringApproval.getCurrentStatus().equals(DomainConstants.CoveringApprovalStatus.APPROVED)) && (!coveringApproval.getCurrentStatus().equals(DomainConstants.CoveringApprovalStatus.REJECTED) && (!coveringApproval.getCurrentStatus().equals(DomainConstants.CoveringApprovalStatus.DELETE)))) {
//
//                CoveringApprovalDTO coveringApprovalDTO = new CoveringApprovalDTO();
//                BeanUtils.copyProperties(coveringApproval, coveringApprovalDTO);
//
//                List<CovAppBasicInfoDTO> covAppBasicInfoDTOS = new ArrayList<>();
//
//                for (CovAppBasicInfo covAppBasicInfo : coveringApproval.getCovAppBasicInfoList()) {
//                    CovAppBasicInfoDTO basicInfoDTO = new CovAppBasicInfoDTO();
//                    basicInfoDTO.setCovAppId(covAppBasicInfo.getCoveringApproval().getCovAppId());
//                    basicInfoDTO.setTranStatus(covAppBasicInfo.getTranStatus());
//                    BeanUtils.copyProperties(covAppBasicInfo, basicInfoDTO);
//
//                    loadBankAccountDetailsRQ.setBalanceDate(dateFormat.format(covAppBasicInfo.getTranDate()));
//
//                    if (previousTranDate != null && previousTranDate.equals(covAppBasicInfo.getTranDate())) {
//                        // If the transaction date is the same as the previous record, use the previous balance
//                        basicInfoDTO.setAsAtDateAcctBalance(previousAsAtDateAcctBalance);
//                        basicInfoDTO.setFloatBalance(floatBalance);
//                        basicInfoDTO.setFundsinClearing(fundsInClearing);
//                        basicInfoDTO.setSanctionLimit(sanctionLimit);
//                    } else {
//                        // If the transaction date is different, get the new balance
//                        CovAppCustomerDTO covAppCustomerDTO = integrationService.getAccountBalanceDetailsByRefNoAndAccountNo(loadBankAccountDetailsRQ, credentialsDTO);
//
//                        if (coveringApproval.getCreatedDate().before(date)) {
//                            previousAsAtDateAcctBalance = covAppCustomerDTO.getAsAtDateAcctBalance();
//                            basicInfoDTO.setAsAtDateAcctBalance(previousAsAtDateAcctBalance);
//
//                            floatBalance = covAppCustomerDTO.getFloatBalance();
//                            basicInfoDTO.setFloatBalance(floatBalance);
//
//                            fundsInClearing = covAppCustomerDTO.getFundsinClearing();
//                            basicInfoDTO.setFundsinClearing(fundsInClearing);
//
//                            sanctionLimit = covAppCustomerDTO.getSanctionLimit();
//                            basicInfoDTO.setSanctionLimit(sanctionLimit);
//                        } else {
//                            previousAsAtDateAcctBalance = "Refer current Balance";
//                            basicInfoDTO.setAsAtDateAcctBalance(previousAsAtDateAcctBalance);
//
//                            floatBalance = covAppCustomerDTO.getFloatBalance();
//                            basicInfoDTO.setFloatBalance(floatBalance);
//
//                            fundsInClearing = covAppCustomerDTO.getFundsinClearing();
//                            basicInfoDTO.setFundsinClearing(fundsInClearing);
//
//                            sanctionLimit = covAppCustomerDTO.getSanctionLimit();
//                            basicInfoDTO.setSanctionLimit(sanctionLimit);
//                        }
//
//                        // Update the previous transaction date
//                        previousTranDate = covAppBasicInfo.getTranDate();
//                    }
//
//                    covAppBasicInfoDTOS.add(basicInfoDTO);
//                }
//
//                List<CovAppCommentDTO> covAppCommentDTOList = new ArrayList<>();
//                for(CovAppComment covAppComment : coveringApproval.getCovAppComments()){
//                    CovAppCommentDTO covAppCommentDTO = new CovAppCommentDTO();
//                    covAppCommentDTO.setCovAppId(coveringApproval.getCovAppId());
//                    BeanUtils.copyProperties(covAppComment, covAppCommentDTO);
//
//                    covAppCommentDTOList.add(covAppCommentDTO);
//                }
//
//                coveringApprovalDTO.setCovAppCommentDTOList(covAppCommentDTOList);
//                coveringApprovalDTO.setCovAppBasicInfoDTOList(covAppBasicInfoDTOS);
//                coveringApprovalDTOList.add(coveringApprovalDTO);
//            }
//        }
//
//        return coveringApprovalDTOList;
//    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CoveringApprovalDTO> getPendingCoveringApprovals(CovAppBasicInfoRQ covAppBasicInfoRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Load Covering Approval pending records by Account Number : {} ", covAppBasicInfoRQ.getAccountNumber());

        List<CoveringApproval> coveringApprovalList = coveringApprovalDao.findByAccountNumber(covAppBasicInfoRQ.getAccountNumber());
        List<CoveringApprovalDTO> coveringApprovalDTOList = new ArrayList<>();

        for (CoveringApproval coveringApproval : coveringApprovalList) {
            if (isPendingApproval(coveringApproval)) {
                CoveringApprovalDTO coveringApprovalDTO = mapToDTO(coveringApproval, credentialsDTO);
                coveringApprovalDTOList.add(coveringApprovalDTO);
            }
        }

        LOG.info("END : Load Covering Approval pending records by Account Number : {} ", coveringApprovalDTOList);
        return coveringApprovalDTOList;
    }

    private boolean isPendingApproval(CoveringApproval coveringApproval) {
        return !Arrays.asList(
                DomainConstants.CoveringApprovalStatus.APPROVED,
                DomainConstants.CoveringApprovalStatus.REJECTED,
                DomainConstants.CoveringApprovalStatus.DELETE
        ).contains(coveringApproval.getCurrentStatus());
    }

    private CoveringApprovalDTO mapToDTO(CoveringApproval coveringApproval, CredentialsDTO credentialsDTO) throws AppsException {
        CoveringApprovalDTO coveringApprovalDTO = new CoveringApprovalDTO();
        BeanUtils.copyProperties(coveringApproval, coveringApprovalDTO);

        List<CovAppBasicInfoDTO> basicInfoDTOList = new ArrayList<>();
        Date previousTranDate = null;
        String previousAsAtDateAcctBalance = null;
        String floatBalance = null;
        String fundsInClearing = null;
        String sanctionLimit = null;

        for (CovAppBasicInfo covAppBasicInfo : coveringApproval.getCovAppBasicInfoList()) {
            CovAppBasicInfoDTO basicInfoDTO = new CovAppBasicInfoDTO();
            BeanUtils.copyProperties(covAppBasicInfo, basicInfoDTO);
            basicInfoDTO.setCovAppId(coveringApproval.getCovAppId());

            if (previousTranDate != null && previousTranDate.equals(covAppBasicInfo.getTranDate())) {
                setBalanceDetails(basicInfoDTO, previousAsAtDateAcctBalance, floatBalance, fundsInClearing, sanctionLimit);
            } else {
                CovAppCustomerDTO customerDTO = fetchAccountBalance(covAppBasicInfo.getTranDate(), coveringApproval.getCreatedDate(), coveringApproval.getAccountNumber(), credentialsDTO);

                previousAsAtDateAcctBalance = customerDTO.getAsAtDateAcctBalance();
                floatBalance = customerDTO.getFloatBalance();
                fundsInClearing = customerDTO.getFundsinClearing();
                sanctionLimit = customerDTO.getSanctionLimit();

                setBalanceDetails(basicInfoDTO, previousAsAtDateAcctBalance, floatBalance, fundsInClearing, sanctionLimit);
                previousTranDate = covAppBasicInfo.getTranDate();
            }
            basicInfoDTOList.add(basicInfoDTO);
        }

        coveringApprovalDTO.setCovAppBasicInfoDTOList(basicInfoDTOList);
        coveringApprovalDTO.setCovAppCommentDTOList(mapCommentsToDTO(coveringApproval.getCovAppComments()));
        return coveringApprovalDTO;
    }

    private void setBalanceDetails(CovAppBasicInfoDTO dto, String balance, String floatBalance, String fundsInClearing, String sanctionLimit) {
        dto.setAsAtDateAcctBalance(balance);
        dto.setFloatBalance(floatBalance);
        dto.setFundsinClearing(fundsInClearing);
        dto.setSanctionLimit(sanctionLimit);
    }

    private CovAppCustomerDTO fetchAccountBalance(Date tranDate, Date createdDate, String accountNumber, CredentialsDTO credentialsDTO) throws AppsException {
        LoadBankAccountDetailsRQ request = new LoadBankAccountDetailsRQ();
        request.setRefId("20220224133817459039");
        request.setAccountNo(accountNumber);
        request.setBalanceDate(new SimpleDateFormat("dd-MM-yyyy").format(tranDate));

        return integrationService.getAccountBalanceDetailsByRefNoAndAccountNo(request, credentialsDTO);
    }

    private List<CovAppCommentDTO> mapCommentsToDTO(List<CovAppComment> comments) {
        List<CovAppCommentDTO> commentDTOList = new ArrayList<>();
        for (CovAppComment comment : comments) {
            CovAppCommentDTO dto = new CovAppCommentDTO();
            BeanUtils.copyProperties(comment, dto);
            commentDTOList.add(dto);
        }
        return commentDTOList;
    }


    //done
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CovAppComment> getComments(Integer covAppId, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Load Covering Approval comments by id : {}, user {} ", covAppId, credentialsDTO.getUserName());

        return covAppCommentDao.findByCoveringApprovalCovAppId(covAppId);

    }

    //done
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<CovAppStatusHistoryDTO> getDirectReturnUsersList(Integer covAppId, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Facility Paper Direct Return Users List : {} by: {}", covAppId, credentialsDTO.getUserName());

        return coveringApprovalJdbcDao.getCovAppDirectReturnUserList(covAppId)
                .stream().filter(element -> {
            return !element.getAssignUser().equals(credentialsDTO.getUserName());
        }).distinct().collect(Collectors.toList());
    }

    //done
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<TranDetailsResponseDTO> getCoveringApprovalTranDetails(LoadTranDataRQ loadTranDataRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START : Get covering approval tran details by user {}", credentialsDTO.getUserID());

        List<TranDetailsResponseDTO> tranDetailsResponseDTOList = integrationService.getCoveringApprovalTranDetails(loadTranDataRQ, credentialsDTO);
        Set<String> partTranSRLSet = extractPartTranSRL(tranDetailsResponseDTOList);
        updateIsExistsField(tranDetailsResponseDTOList, partTranSRLSet);

        LOG.info("END : Get covering approval tran details : {}, by user {}", tranDetailsResponseDTOList, credentialsDTO.getUserID());
        return tranDetailsResponseDTOList;
    }

    private Set<String> extractPartTranSRL(List<TranDetailsResponseDTO> tranDetailsResponseDTOList) {
        Set<String> partTranSRLSet = new HashSet<>();

        for (TranDetailsResponseDTO tranDetailsResponseDTO : tranDetailsResponseDTOList) {
            for (CoveringApprovalTranDetailsDTO tranDetailsDTO : tranDetailsResponseDTO.getTraninquiry()) {
                List<CovAppBasicInfo> covAppBasicInfoList = covAppBasinInfoDao.findByPartTranSRLAndChequeNumber(
                        tranDetailsDTO.getPartTrnSRL(), tranDetailsDTO.getInstNum()
                );

                for (CovAppBasicInfo covAppBasicInfo : covAppBasicInfoList) {
                    if (covAppBasicInfo != null) {
                        partTranSRLSet.add(covAppBasicInfo.getPartTranSRL());
                        tranDetailsDTO.setCreatedBy(covAppBasicInfo.getCreatedBy());
                        tranDetailsDTO.setCreatedDate(covAppBasicInfo.getCreatedDate());

                        CoveringApproval coveringApproval = coveringApprovalDao.getOne(covAppBasicInfo.getCoveringApproval().getCovAppId());
                        tranDetailsDTO.setCurrentStatus(coveringApproval.getCurrentStatus());
                    }
                }
            }
        }
        return partTranSRLSet;
    }

    private void updateIsExistsField(List<TranDetailsResponseDTO> tranDetailsResponseDTOList, Set<String> partTranSRLSet) {
        for (TranDetailsResponseDTO tranDetailsResponseDTO : tranDetailsResponseDTOList) {
            for (CoveringApprovalTranDetailsDTO tranDetailsDTO : tranDetailsResponseDTO.getTraninquiry()) {
                tranDetailsDTO.setIsExists(partTranSRLSet.contains(tranDetailsDTO.getPartTrnSRL()) ? "already exists" : "no");
            }
        }
    }

}
