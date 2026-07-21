package com.itechro.cas.service.acae;

import com.itechro.cas.dao.acae.jdbc.ACAEBaseJdbcDAO;
import com.itechro.cas.dao.acae.jdbc.ACAEPaperJdbcDAO;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.acae.request.*;
import com.itechro.cas.model.dto.acae.response.*;
import com.itechro.cas.model.dto.integration.request.UpmDetailLoadRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ACAEBaseService {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEBaseService.class);
    private final ACAEBaseJdbcDAO acaeJdbcDao;
    private final ACAEPaperJdbcDAO acaePaperJdbcDAO;
    private final IntegrationService integrationService;

    @Autowired
    public ACAEBaseService(ACAEBaseJdbcDAO acaeJdbcDao,
                           ACAEPaperJdbcDAO acaePaperJdbcDAO, IntegrationService integrationService) {
        this.acaeJdbcDao = acaeJdbcDao;
        this.acaePaperJdbcDAO = acaePaperJdbcDAO;
        this.integrationService = integrationService;
    }

    public Page<ACAESearchByStatusDTO> getACAEPagedRecordsByStatusService(ACAESearchByStatusRQ acaeSearchByStatusRQ, CredentialsDTO credentialsDTO) throws  AppsException{
        LOG.info("START : ACAE Paged Records By Status Service: {} , user {}", acaeSearchByStatusRQ, credentialsDTO);

        int selectedStatus = 0;
        selectedStatus = acaeSearchByStatusRQ.getAcaeStatus();
        Page<ACAESearchByStatusDTO> pagedData;

        if (selectedStatus >= 0 && selectedStatus <= 14) {
            pagedData = acaeJdbcDao.getPagedACAERecordsByStatusRepository(acaeSearchByStatusRQ, credentialsDTO);
        } else {
            throw new AppsException("Status is Invalid");
        }
        LOG.info("END : All ACAE Records By Status Service: {}", pagedData);
        return pagedData;
    }

    public List<ACAESearchByStatusDTO> getAllACAERecordsByStatusService(ACAESearchByStatusRQ acaeSearchByStatusRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : All ACAE Records By Status Service: {} , user {}", acaeSearchByStatusRQ, credentialsDTO);

        List<ACAESearchByStatusDTO> acaeSearchByStatusDTOList = null;
        int selectedStatus = 0;
        selectedStatus = acaeSearchByStatusRQ.getAcaeStatus();
        if (selectedStatus >= 0 && selectedStatus <= 14) {
            acaeSearchByStatusDTOList = acaeJdbcDao.getACAERecordsByStatusRepository(acaeSearchByStatusRQ, credentialsDTO);
        } else {
            throw new AppsException("Status is Invalid");
        }

        LOG.info("END : All ACAE Records By Status Service: {} , user {}", acaeSearchByStatusDTOList, credentialsDTO);
        return acaeSearchByStatusDTOList;
    }


    public ACAEStatusCountDTO getACAEStatusCountService(ACAEStatusCountRQ acaeStatusCountRQ, CredentialsDTO credentialsDTO) {
        return acaeJdbcDao.getACAEStatusCountRepository(acaeStatusCountRQ, credentialsDTO);
    }

    @Transactional
    public String getEligibilityForwardACAEBatchService(ACAEListDoneRQ acaeListDoneRQ, CredentialsDTO credentialsDTO) {
        String result = acaeJdbcDao.getEligibilityForwardACAEBatchRepository(acaeListDoneRQ, credentialsDTO);
        if(result.equals("Y")){
            acaeJdbcDao.updateACAECompleteRepository(acaeListDoneRQ);
        }
        return result;
    }

    public List<ACAEUserDTO> getACAEPaperUserWiseSummaryService(ACAESearchByStatusRQ acaeSearchByStatusRQ,
                                                                CredentialsDTO credentialsDTO) {
        LOG.info("START : ACAE Paper User Wise Summary Service: {} , user {}", acaeSearchByStatusRQ, credentialsDTO);
        List<ACAEUserDTO> finalAcaeUserDTOList = new ArrayList<>();
        try {
            List<ACAEUserDTO> acaeUserDTOList = this.acaeJdbcDao.getACAEUsersRepository(acaeSearchByStatusRQ, credentialsDTO);
            if (!acaeUserDTOList.isEmpty()) {

                acaeUserDTOList.forEach(rec -> {
                    ACAEUserDTO acaeUserDTO = new ACAEUserDTO();
                    UpmDetailLoadRQ upmDetailLoadRQ = new UpmDetailLoadRQ();
                    UpmDetailResponse upmDetailResponse = new UpmDetailResponse();
                    List<ACAESearchByStatusDTO> acaeSearchByStatusDTOList = new ArrayList<>();
                    upmDetailLoadRQ.setUserID(rec.getUserId());
                    upmDetailLoadRQ.setAppCode("CAS");

                    upmDetailResponse = integrationService.getUpmDetailsByUserIdAndAppCode(upmDetailLoadRQ);
                    acaeSearchByStatusDTOList = acaeJdbcDao.getACAERecordsByStatusRepository(acaeSearchByStatusRQ, credentialsDTO);

                    if (!acaeSearchByStatusDTOList.isEmpty()) {
                        acaeUserDTO.setUserId(rec.getUserId());
                        acaeUserDTO.setFirstName(upmDetailResponse.getFirstName());
                        acaeUserDTO.setLastName(upmDetailResponse.getLastName());
                        acaeUserDTO.setAcaeSearchByStatusDTOList(acaeSearchByStatusDTOList);
                    }

                    finalAcaeUserDTOList.add(acaeUserDTO);
                });
            }
            LOG.info("END : ACAE Paper User Wise Summary Service: {}", finalAcaeUserDTOList);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return finalAcaeUserDTOList;
    }

    @Transactional
    public String saveBulkComments(ACAEBulkCommentRQ acaeBulkCommentRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : save ACAE Comment Repository : {} : {}", acaeBulkCommentRQ, credentialsDTO.getUserID());
        String message = "";
        try {
            if(!acaeBulkCommentRQ.getRecordSet().isEmpty()){
                for(ACAEBaseRQ rec:acaeBulkCommentRQ.getRecordSet()){
                    ACAECommentRQ acaeCommentRQ = new ACAECommentRQ();
                    acaeCommentRQ.setActiveComment(acaeBulkCommentRQ.getComment());
                    acaeCommentRQ.setAccountNumber(rec.getAccNumber());
                    acaeCommentRQ.setReferenceNumber(rec.getRefNumber());
                    acaeCommentRQ.setNegDate(acaeBulkCommentRQ.getNegDate());
                    acaeCommentRQ.setPreviousNegDate(acaeBulkCommentRQ.getPreviousNegDate());
                    acaePaperJdbcDAO.saveACAECommentRepository(acaeCommentRQ,credentialsDTO);
                }
            }else{
                throw new AppsException("Recordset can't be empty");
            }
            message = "ok";
        }catch (Exception ex){
            LOG.error("ERROR : ", ex);
            message = "error";
        }
        return  message;
    }
}
