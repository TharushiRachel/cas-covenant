package com.itechro.cas.service.acae;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.acae.jdbc.ACAEPaperJdbcDAO;
import com.itechro.cas.dao.casmaster.jdbc.MasterDataJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.acae.request.*;
import com.itechro.cas.model.dto.acae.response.*;
import com.itechro.cas.model.dto.casmaster.LoggedInUserWorkFlowByStatusRQ;
import com.itechro.cas.model.dto.casmaster.UpmGroupDTO;
import com.itechro.cas.model.dto.integration.request.ACAEAcctClassificationDataRQ;
import com.itechro.cas.model.dto.integration.request.UmpDetailLoadByAdIdRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerAccountStatRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerCasServiceAccountStatRQ;
import com.itechro.cas.model.dto.integration.response.ACAEAcctClassificationDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.accountstatistic.CustomerAccountStatisticResponseDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.casstat.CasStatResponseDTO;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class ACAEPaperService {

    @Autowired
    private ACAEPaperJdbcDAO acaePaperJdbcDAO;

    @Autowired
    private MasterDataJdbcDao masterDataJdbcDao;

    @Autowired
    private IntegrationService integrationService;

    @Value("${apps.integration.load.acae.range.inquiry.url}")
    private String acaeRangeInquiryURL;

    @Value("${apps.integration.load.acae.range.inquiry.enable}")
    private boolean acaeRangeInquiryEnable;

    @Value("${apps.cas.application.code}")
    private String applicationCode;


    private static final Logger LOG = LoggerFactory.getLogger(ACAEPaperJdbcDAO.class);

    public ACAECustomerDetailsDTO getCustomerDetailsService(ACAECustomerDetailsRQ acaeCustomerDetailsRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.getCustomerDetailsRepository(acaeCustomerDetailsRQ, credentialsDTO);
    }

    public Object getAccountBalanceDetailsService(ACAECustomerDetailsRQ acaeCustomerDetailsRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.getAccountBalanceDetailsByRefNoAndAccountNoRepository(acaeCustomerDetailsRQ, credentialsDTO);
    }

    public CasStatResponseDTO getACAEOutstandingService(CustomerCasServiceAccountStatRQ customerCasServiceAccountStatRQ, CredentialsDTO credentialsDTO) {
        CasStatResponseDTO casStatResponseDTO = null;
        String finacleId = null;
        finacleId = acaePaperJdbcDAO.getFinacleIdFromAccountNumberRepository(customerCasServiceAccountStatRQ.getAccno());

        if (finacleId != null) {
            customerCasServiceAccountStatRQ.setCumm(finacleId);
            customerCasServiceAccountStatRQ.setValType("A");
        } else {
            customerCasServiceAccountStatRQ.setCumm(customerCasServiceAccountStatRQ.getAccno());
            customerCasServiceAccountStatRQ.setValType("C");
        }
        casStatResponseDTO = integrationService.getCasStatResponse(customerCasServiceAccountStatRQ, credentialsDTO);
        return casStatResponseDTO;
    }

    public CustomerAccountStatisticResponseDTO getACAERelatedAccountService(CustomerAccountStatRQ customerAccountStatRQ, CredentialsDTO credentialsDTO) {
        CustomerAccountStatisticResponseDTO customerAccountStatisticResponseDTO = new CustomerAccountStatisticResponseDTO();
        String finacleId = null;
        finacleId = acaePaperJdbcDAO.getFinacleIdFromAccountNumberRepository(customerAccountStatRQ.getAccno());
        if (finacleId != null) {
            customerAccountStatRQ.setCumm(finacleId);
            customerAccountStatRQ.setValType("A");
        } else {
            customerAccountStatRQ.setCumm(customerAccountStatRQ.getAccno());
            customerAccountStatRQ.setValType("C");
        }
        customerAccountStatisticResponseDTO = integrationService.getCustomerAccountStatisticResponse(customerAccountStatRQ, credentialsDTO);
        return customerAccountStatisticResponseDTO;
    }

    public ACAEAcctClassificationDTO getACAELoanAccountService(ACAEAcctClassificationDataRQ acaeAcctClassificationDataRQ, CredentialsDTO credentialsDTO) {
        return integrationService.getACAEAcctClassificationDataService(acaeAcctClassificationDataRQ, credentialsDTO);
    }

    public List<ACAEBalanceAfterPaymentDTO> getACAEBalanceAfterPaymentService(ACAEBalanceAfterPaymentRQ acaeBalanceAfterPaymentRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.getACAEBalanceAfterPaymentRepository(acaeBalanceAfterPaymentRQ, credentialsDTO);
    }

    public List<ACAEUserCommentsDTO> getACAEUserCommentService(ACAEUserCommentsRQ acaeUserCommentsRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.getACAEUserCommentRepository(acaeUserCommentsRQ, credentialsDTO);
    }

    public List<UpmGroupDTO> getACAELowerOrHigherUserGroupLOVService(LoggedInUserWorkFlowByStatusRQ loggedInUserWorkFlowByStatusRQ, CredentialsDTO credentialsDTO) throws Exception {
        return masterDataJdbcDao.getHighOrLowerUpmGroupByWorkFlowStatusAndLoggedInUserUpmGroupCodeFrom(loggedInUserWorkFlowByStatusRQ);
    }

    public Boolean approveACAEPaperService(ACAEPaperApproveRQ acaePaperApproveRQ, CredentialsDTO credentialsDTO) throws AppsException {
        return acaePaperJdbcDAO.approveACAEPaperRepository(acaePaperApproveRQ, credentialsDTO);
    }

    @Transactional
    public Boolean toBeResubmittedACAEPaperService(ACAEPaperTransferRQ acaePaperTransferRQ, CredentialsDTO credentialsDTO) {
        Boolean allSuccess = false;
        Boolean toBeResubmittedSuccess = acaePaperJdbcDAO.transferACAEPaperRepository(acaePaperTransferRQ);
        if (toBeResubmittedSuccess) {
            allSuccess = acaePaperJdbcDAO.updateEscalationDaysRepository(acaePaperTransferRQ);
        } else {
            allSuccess = false;
        }
        return allSuccess;
    }

    public Boolean forwardACAEPaperService(ACAEPaperTransferRQ acaePaperTransferRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.transferACAEPaperRepository(acaePaperTransferRQ);
    }

    public Boolean forwardACAEBatchService(ACAEBatchForwardRQ acaeBatchForwardRQ, CredentialsDTO credentialsDTO) throws Exception {
        return acaePaperJdbcDAO.forwardACAEBatchRepository(acaeBatchForwardRQ, credentialsDTO);
    }

    public Boolean rejectACAEPaperService(ACAEPaperTransferRQ acaePaperTransferRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.transferACAEPaperRepository(acaePaperTransferRQ);
    }

    public String saveACAECommentService(ACAECommentRQ acaeCommentRQ, CredentialsDTO credentialsDTO) {
        String message = "";
        if (Integer.parseInt(credentialsDTO.getUpmGroupCode()) > 50) {
            message = acaePaperJdbcDAO.saveACAECommentOnlyRepository(acaeCommentRQ, credentialsDTO);
        } else {
            message = acaePaperJdbcDAO.saveACAECommentRepository(acaeCommentRQ, credentialsDTO);
        }
        return message;
    }


    public String getActiveCommentService(ACAEUserCommentsRQ acaeUserCommentsRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.getActiveCommentRepository(acaeUserCommentsRQ, credentialsDTO);
    }


    public ACAEFinacleIdDTO getFinacleIdFromAccountNumberService(CustomerAccountStatRQ customerAccountStatRQ) {
        ACAEFinacleIdDTO acaeFinacleIdDTO = new ACAEFinacleIdDTO();
        String finacleId = acaePaperJdbcDAO.getFinacleIdFromAccountNumberRepository(customerAccountStatRQ.getAccno());
        acaeFinacleIdDTO.setFinacleId(finacleId);
        return acaeFinacleIdDTO;
    }


    public List<ACAEAasRecordResultDTO> getOutstandingService(ACAEAasRecordRQ acaeAasRecordRQ, CredentialsDTO credentialsDTO) {
        List<ACAEAasRecordDataDTO> remakeAasRecords = new ArrayList<>();
        List<ACAEAasRecordResultDTO> result = new ArrayList<>();

        try {
            ACAEAasRecordsDTO acaeAasRecordsDTO = integrationService.getACAEAasRecordService(acaeAasRecordRQ, credentialsDTO);

            if (acaeAasRecordsDTO.getCasAaasData().size() > 0) {
                for (ACAEAasRecordDataDTO acaeAasRecordDataDTO : acaeAasRecordsDTO.getCasAaasData()) {
                    if (acaeAasRecordDataDTO.getSchmType().equals(AppsConstants.SchmType.ODA.getSchmType()) && acaeAasRecordDataDTO.getAcctNumber().equals(acaeAasRecordRQ.getAcctNumber())) {
                        remakeAasRecords.add(acaeAasRecordDataDTO);
                    }
                }
                for (ACAEAasRecordDataDTO acaeAasRecordDataDTO : acaeAasRecordsDTO.getCasAaasData()) {
                    if (acaeAasRecordDataDTO.getSchmType().equals(AppsConstants.SchmType.ODA.getSchmType()) && !acaeAasRecordDataDTO.getAcctNumber().equals(acaeAasRecordRQ.getAcctNumber())) {
                        remakeAasRecords.add(acaeAasRecordDataDTO);
                    }
                }

                if (remakeAasRecords.size() > 0) {
                    for (ACAEAasRecordDataDTO acaeAasRecordDataDTO : remakeAasRecords) {
                        ACAEStatByAcctRQ acaeStatByAcctRQ = new ACAEStatByAcctRQ();
                        acaeStatByAcctRQ.setForacid(acaeAasRecordDataDTO.getAcctNumber());
                        acaeStatByAcctRQ.setRequestId(acaeAasRecordRQ.getReqId());
                        ACAEStatByAcctDTO acaeStatByAcctDTO = integrationService.getACAEStatByAcctService(acaeStatByAcctRQ, credentialsDTO);

                        ACAEAasRecordResultDTO ACAEAasRecordResultDTO = new ACAEAasRecordResultDTO();
                        ACAEAasRecordResultDTO.setAcctNumber(acaeAasRecordDataDTO.getAcctNumber());
                        List<ACAEStatByAcctDataDTO> ACAEStatByAcctDataDTOList = new ArrayList<>();
                        for (ACAEStatByAcctDataDTO ACAEStatByAcctDataDTO : acaeStatByAcctDTO.getSuccessResponse()) {
                            ACAEStatByAcctDataDTOList.add(ACAEStatByAcctDataDTO);
                        }
                        ACAEAasRecordResultDTO.setStatByAcctData(ACAEStatByAcctDataDTOList);
                        result.add(ACAEAasRecordResultDTO);
                    }
                }

            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }

        return result;
    }

    public List<ACAEAcctBalByAcctDataDTO> getACAERelatedAccountsService(ACAEAccountTypeRQ acaeAccountTypeRQ, CredentialsDTO credentialsDTO) {
        List<ACAEAcctBalByAcctDataDTO> result = new ArrayList<>();
        try {
            ACAEAcctBalByAcctRQ acaeAcctBalByAcctRQ = new ACAEAcctBalByAcctRQ();
            acaeAcctBalByAcctRQ.setAcctNum(acaeAccountTypeRQ.getAccountNumber());
            acaeAcctBalByAcctRQ.setRequestId(acaeAccountTypeRQ.getRefNumber());

            ACAEAcctBalByAcctDTO acaeAcctBalByAcctDTO = integrationService.getACAEAcctBalByAcctService(acaeAcctBalByAcctRQ, credentialsDTO);

            if (acaeAcctBalByAcctDTO.getStatus().equals(AppsConstants.Status.SUCCESS.getDescription()) && acaeAcctBalByAcctDTO.getStatRecords().size() > 0) {
                for (ACAEAcctBalByAcctDataDTO rec : acaeAcctBalByAcctDTO.getStatRecords()) {
                    if (rec.getAcctNum().equals(acaeAccountTypeRQ.getAccountNumber())) {
                        result.add(rec);
                    }
                }
                for (ACAEAcctBalByAcctDataDTO rec : acaeAcctBalByAcctDTO.getStatRecords()) {
                    if (!rec.getAcctNum().equals(acaeAccountTypeRQ.getAccountNumber())) {
                        result.add(rec);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return result;
    }

    public List<ACAEOutstandingDTO> getAccountTypeService(ACAEAccountTypeRQ acaeAccountTypeRQ, CredentialsDTO credentialsDTO) {
        List<ACAEOutstandingDTO> result = new ArrayList<>();

        try {
            ACAEAcctBalByAcctRQ acaeAcctBalByAcctRQ = new ACAEAcctBalByAcctRQ();
            acaeAcctBalByAcctRQ.setAcctNum(acaeAccountTypeRQ.getAccountNumber());
            acaeAcctBalByAcctRQ.setRequestId(acaeAccountTypeRQ.getRefNumber());

            ACAEAcctBalByAcctDTO acaeAcctBalByAcctDTO = integrationService.getACAEAcctBalByAcctService(acaeAcctBalByAcctRQ, credentialsDTO);

            if (acaeAcctBalByAcctDTO.getStatus().equals(AppsConstants.Status.SUCCESS.getDescription()) && acaeAcctBalByAcctDTO.getStatRecords().size() > 0) {

                for (ACAEAcctBalByAcctDataDTO rec : acaeAcctBalByAcctDTO.getStatRecords()) {
                    ACAEOutstandingDTO acaeOutstandingDTO = new ACAEOutstandingDTO();

                    if (rec.getAcctType().equals(acaeAccountTypeRQ.getAccountType()) && rec.getAcctNum().equals(acaeAccountTypeRQ.getAccountNumber())) {

                        acaeOutstandingDTO.setAcctType(rec.getAcctType());
                        acaeOutstandingDTO.setAcctNum(rec.getAcctNum());
                        acaeOutstandingDTO.setLienAmount(rec.getLienAmount());
                        acaeOutstandingDTO.setReservedAmount(rec.getReservedAmount());
                        acaeOutstandingDTO.setBalance(rec.getBalance());
                        acaeOutstandingDTO.setCurrency(rec.getCurrency());

                        result.add(acaeOutstandingDTO);
                    }
                }

                for (ACAEAcctBalByAcctDataDTO rec : acaeAcctBalByAcctDTO.getStatRecords()) {
                    ACAEOutstandingDTO acaeAccountTypeDTO = new ACAEOutstandingDTO();

                    if (rec.getAcctType().equals(acaeAccountTypeRQ.getAccountType()) && !rec.getAcctNum().equals(acaeAccountTypeRQ.getAccountNumber())) {

                        acaeAccountTypeDTO.setAcctType(rec.getAcctType());
                        acaeAccountTypeDTO.setAcctNum(rec.getAcctNum());
                        acaeAccountTypeDTO.setLienAmount(rec.getLienAmount());
                        acaeAccountTypeDTO.setReservedAmount(rec.getReservedAmount());
                        acaeAccountTypeDTO.setBalance(rec.getBalance());
                        acaeAccountTypeDTO.setCurrency(rec.getCurrency());

                        result.add(acaeAccountTypeDTO);
                    }
                }
            }

            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    ACAEStatByAcctRQ acaeStatByAcctRQ = new ACAEStatByAcctRQ();
                    acaeStatByAcctRQ.setRequestId(acaeAccountTypeRQ.getRefNumber());
                    acaeStatByAcctRQ.setForacid(result.get(i).getAcctNum());

                    ACAEStatByAcctDTO acaeStatByAcctDTO = integrationService.getACAEStatByAcctService(acaeStatByAcctRQ, credentialsDTO);

                    if (acaeStatByAcctDTO.getStatus().equals(AppsConstants.Status.Success.getDescription()) && acaeStatByAcctDTO.getSuccessResponse().size() > 0) {
                        result.get(i).setStatByAcctData(acaeStatByAcctDTO.getSuccessResponse());
                    }
                }

            }

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return result;
    }

    public ACAEAcctBalByAcctDTO getAcctBalByAcctsService(ACAEAcctBalByAcctRQ acaeAcctBalByAcctRQ, CredentialsDTO credentialsDTO) {
        return integrationService.getACAEAcctBalByAcctService(acaeAcctBalByAcctRQ, credentialsDTO);
    }

    public Boolean isACAEAttendedService(ACAEAttendedRQ attendedRQ) {
        return acaePaperJdbcDAO.isACAEAttendedRepository(attendedRQ);
    }

    public Boolean initializeReviewService(ACAEInitializeReviewRQ acaeInitializeReviewRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.initializeReviewRepository(acaeInitializeReviewRQ, credentialsDTO);
    }

    public Object getClassificationDataService(ACAECustomerDetailsRQ acaeCustomerDetailsRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.getClassificationDataRepository(acaeCustomerDetailsRQ, credentialsDTO);
    }

    public List<ACAEPreviousUserDTO> getPreviousUsersService(ACAEPreviousUserRQ acaePreviousUserRQ, CredentialsDTO credentialsDTO) {
        return acaePaperJdbcDAO.getPreviousUsersRepository(acaePreviousUserRQ, credentialsDTO);
    }

    public ACAERangeInquiryResponse getACAERangeInquiryService(ACAERangeInquiryRQ acaeRangeInquiryRQ, CredentialsDTO credentialsDTO) {

        LOG.info("START : ACAE Range Inquiry Service: {} , user {}", acaeRangeInquiryRQ, credentialsDTO);

        ACAERangeInquiryResponse result = null;

        if (acaeRangeInquiryEnable) {

            String url = this.acaeRangeInquiryURL;
            RestTemplate restTemplate = new RestTemplate();
            try {
                acaeRangeInquiryRQ.setToDate(acaeRangeInquiryRQ.getToDate());
                acaeRangeInquiryRQ.setFromDate(acaeRangeInquiryRQ.getFromDate());

                result = restTemplate.postForObject(url, acaeRangeInquiryRQ, ACAERangeInquiryResponse.class);
                LOG.info("END: Get Account detail by customer account number: {} by {}", result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: Error occur while loading Account detail url :{}: {}: {} by {}", url, acaeRangeInquiryRQ, result, credentialsDTO, e);
            }
        } else {
            LOG.warn("WARN: Account detail loading by account number service is disabled {} by {}", acaeRangeInquiryRQ, credentialsDTO);
        }
        LOG.info("END : ACAE Range Inquiry Service: {} , user {}", acaeRangeInquiryRQ, credentialsDTO);
        return result;
    }

    public List<ACAEOutstandingDTO> getBasicACAEOutstandingService(ACAEAccountTypeRQ acaeAccountTypeRQ, CredentialsDTO credentialsDTO) {

        List<ACAEOutstandingDTO> result = new ArrayList<>();
        try {
            ACAEAcctBalByAcctRQ acaeAcctBalByAcctRQ = new ACAEAcctBalByAcctRQ();
            acaeAcctBalByAcctRQ.setAcctNum(acaeAccountTypeRQ.getAccountNumber());
            acaeAcctBalByAcctRQ.setRequestId(acaeAccountTypeRQ.getRefNumber());

            ACAEAcctBalByAcctDTO acaeAcctBalByAcctDTO = integrationService.getACAEAcctBalByAcctService(acaeAcctBalByAcctRQ, credentialsDTO);

            if (acaeAcctBalByAcctDTO.getStatus().equals(AppsConstants.Status.SUCCESS.getDescription()) && acaeAcctBalByAcctDTO.getStatRecords().size() > 0) {

                for (ACAEAcctBalByAcctDataDTO rec : acaeAcctBalByAcctDTO.getStatRecords()) {

                    if (rec.getAcctNum().equals(acaeAccountTypeRQ.getAccountNumber()) &&
                            rec.getAcctType().equals(AppsConstants.AcctType.CURRENT_ACCOUNT.getAcctTType())) {
                        ACAEOutstandingDTO outstandingDTO = new ACAEOutstandingDTO();
                        outstandingDTO.setAcctType(rec.getAcctType());
                        outstandingDTO.setAcctNum(rec.getAcctNum());
                        outstandingDTO.setLienAmount(rec.getLienAmount());
                        outstandingDTO.setReservedAmount(rec.getReservedAmount());
                        outstandingDTO.setBalance(rec.getBalance());
                        outstandingDTO.setCurrency(rec.getCurrency());
                        outstandingDTO.setIndex(-1);
                        outstandingDTO.setIsSameAcct(true);
                        result.add(outstandingDTO);
                    }
                }
                Integer index = 0;
                for (ACAEAcctBalByAcctDataDTO rec : acaeAcctBalByAcctDTO.getStatRecords()) {

                    if (!rec.getAcctNum().equals(acaeAccountTypeRQ.getAccountNumber()) &&
                            rec.getAcctType().equals(AppsConstants.AcctType.CURRENT_ACCOUNT.getAcctTType())) {
                        ACAEOutstandingDTO outstandingDTO = new ACAEOutstandingDTO();
                        outstandingDTO.setAcctType(rec.getAcctType());
                        outstandingDTO.setAcctNum(rec.getAcctNum());
                        outstandingDTO.setLienAmount(rec.getLienAmount());
                        outstandingDTO.setReservedAmount(rec.getReservedAmount());
                        outstandingDTO.setBalance(rec.getBalance());
                        outstandingDTO.setCurrency(rec.getCurrency());
                        outstandingDTO.setIndex(index);
                        outstandingDTO.setIsSameAcct(false);
                        result.add(outstandingDTO);
                        index++;
                    }
                }
            }

            if (result.size() > 0) {
                ACAEStatByAcctRQ acaeStatByAcctRQ = new ACAEStatByAcctRQ();
                acaeStatByAcctRQ.setRequestId(acaeAccountTypeRQ.getRefNumber());
                acaeStatByAcctRQ.setForacid(result.get(0).getAcctNum());

                ACAEStatByAcctDTO acaeStatByAcctDTO = integrationService.getACAEStatByAcctService(acaeStatByAcctRQ, credentialsDTO);

                if (acaeStatByAcctDTO.getStatus().equals(AppsConstants.Status.Success.getDescription()) && acaeStatByAcctDTO.getSuccessResponse().size() > 0) {
                    result.get(0).setStatByAcctData(acaeStatByAcctDTO.getSuccessResponse());
                }
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return result;
    }

    public List<ACAEStatByAcctDataDTO> getAdvanceACAEOutstandingService(ACAEAccountTypeRQ acaeAccountTypeRQ, CredentialsDTO credentialsDTO) {
        List<ACAEStatByAcctDataDTO> result = new ArrayList<>();
        try {
            ACAEStatByAcctRQ acaeStatByAcctRQ = new ACAEStatByAcctRQ();
            acaeStatByAcctRQ.setRequestId(acaeAccountTypeRQ.getRefNumber());
            acaeStatByAcctRQ.setForacid(acaeAccountTypeRQ.getAccountNumber());

            ACAEStatByAcctDTO acaeStatByAcctDTO = integrationService.getACAEStatByAcctService(acaeStatByAcctRQ, credentialsDTO);
            result = acaeStatByAcctDTO.getSuccessResponse();
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return result;
    }

    public String getDAClearBalanceService(CredentialsDTO credentialsDTO) throws AppsException {
        String cleaBal = "";
        if (acaePaperJdbcDAO.isAvailableCLeanBalance(credentialsDTO.getUserName()) > 0) {
            cleaBal = acaePaperJdbcDAO.getDAClearBalanceRepository(credentialsDTO.getUserName());
        } else {
            throw new AppsException("This User Need to assign Clean Amount for approve!");
        }
        return cleaBal;
    }

    public ACAECurrentUserDTO getCurrentUserService(ACAEAccountTypeRQ acaeAccountTypeRQ) {
        ACAECurrentUserDTO acaeCurrentUserDTO = new ACAECurrentUserDTO();
        UpmDetailResponse upmDetailResponse = new UpmDetailResponse();
        LOG.info("START : GET ACAE Paper Current User Service :{}", acaeAccountTypeRQ);
        try {
            String currentId = acaePaperJdbcDAO.getCurrentUserIdRepository(acaeAccountTypeRQ);
            LOG.info("currentId :{}", currentId);
            UmpDetailLoadByAdIdRQ rq = new UmpDetailLoadByAdIdRQ();
            rq.setAdUserID(currentId);
            rq.setAppCode(applicationCode);
            upmDetailResponse = integrationService.getUpmDetailsByAdUserAndAppCode(rq);
            acaeCurrentUserDTO.setUserName(upmDetailResponse.getFirstName() + " " + upmDetailResponse.getLastName());
            acaeCurrentUserDTO.setUserLevel(upmDetailResponse.getApplicationSecurityClass());
            acaeCurrentUserDTO.setBranch(upmDetailResponse.getSolName());
            acaeCurrentUserDTO.setUserId(upmDetailResponse.getUserID());
            LOG.info("END : GET ACAE Paper Current User Service :{}", acaeCurrentUserDTO);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return acaeCurrentUserDTO;
    }
}