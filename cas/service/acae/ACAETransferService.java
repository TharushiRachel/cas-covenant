package com.itechro.cas.service.acae;

import com.google.maps.errors.ApiException;
import com.itechro.cas.dao.acae.jdbc.ACAEBaseJdbcDAO;
import com.itechro.cas.dao.acae.jdbc.ACAEPaperJdbcDAO;
import com.itechro.cas.dao.acae.jdbc.ACAETransferJdbcDAO;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.acae.request.*;
import com.itechro.cas.model.dto.acae.response.*;
import com.itechro.cas.model.dto.casmaster.LoggedInUserWorkFlowByStatusRQ;
import com.itechro.cas.model.dto.casmaster.UpmGroupDTO;
import com.itechro.cas.model.dto.integration.request.UmpDetailLoadByAdIdRQ;
import com.itechro.cas.model.dto.integration.request.UpmDetailLoadRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ACAETransferService {

    @Autowired
    ACAETransferJdbcDAO acaeTransferJdbcDAO;
    @Autowired
    ACAEPaperJdbcDAO acaePaperJdbcDAO;
    @Autowired
    ACAECommonService acaeCommonService;

    @Autowired
    ACAEBaseJdbcDAO acaeBaseJdbcDAO;

    @Autowired
    private IntegrationService integrationService;

    private static final Logger LOG = LoggerFactory.getLogger(ACAETransferService.class);

    @Value("${apps.integration.load.branch.details.url}")
    private String umpLoadBranchDetailsUrl;

    public List<ACAERecordsForTransferDTO> getACAERecordsForTransferService(ACAERecordsForTransferRQ acaeRecordsForTransferRQ,
                                                                            CredentialsDTO credentialsDTO) throws Exception {

        LOG.info("START : GET ACAE Records For Transfer Service: {} , user {}", acaeRecordsForTransferRQ, credentialsDTO);

        String responseStr = "";

        String url = this.umpLoadBranchDetailsUrl;
        RestTemplate restTemplate = new RestTemplate();
        responseStr = restTemplate.getForObject(url, String.class);

        Gson gson = new Gson();

        ACAEBranchListDTO branchList = gson.fromJson(responseStr, ACAEBranchListDTO.class);

        List<ACAEBranchDTO> branches = branchList.getBranchList();

        for (ACAEBranchDTO branch : branches) {
            System.out.println("BranchCode e: " + branch.getBranchCode());
            System.out.println("BranchName ee: " + branch.getBranchName());
            System.out.println();
        }

        List<ACAERecordsForTransferDTO> acaeRecordsForTransferDTOList = new ArrayList<>();

        //Get the current user details
        List<ACAETransferUserRecordDTO> acaeTransferUserRecordDTOList =
                acaeTransferJdbcDAO.getACAETransferOptionUsersRepository(acaeRecordsForTransferRQ);

        final Integer[] tranACAENo = {0};
        final Integer[] userNo = {0};

        if (!acaeTransferUserRecordDTOList.isEmpty()) {
            acaeTransferUserRecordDTOList.forEach((userRec) -> {

                ACAERecordsForTransferDTO acaeRecordsForTransferDTO = new ACAERecordsForTransferDTO();

                //get the incomplete users(Null users)
                if (userRec.getUserId() == null) {
                    acaeRecordsForTransferDTO.setFirstName("Null");
                    acaeRecordsForTransferDTO.setLastName("User");
                    acaeRecordsForTransferDTO.setUserId(null);
                    //transfer enable true because of incomplete user
                    acaeRecordsForTransferDTO.setIsSelectEnable(true);

                } else {
                    //if draft set there
                    if ((userRec.getUserId().equals("SYSTEM")) || (userRec.getUserId().equals("SYS"))) {
                        /** in this assumption  we assume that solid number size is 3 then it will be branch if it is more than 3 it will be
                         * account manager or higher rank (RM, AGM)
                         * */
                        if (Integer.parseInt(acaeRecordsForTransferRQ.getSolId()) < 1000) {
                            //branch
                            List<ACAEBranchDTO> acaeBranchDTOList = branches.stream().filter(
                                    (rec) -> (rec.getBranchCode().equals(acaeRecordsForTransferRQ.getSolId()))).collect(Collectors.toList());

                            acaeRecordsForTransferDTO.setFirstName(acaeBranchDTOList.get(0).getBranchName());
                            acaeRecordsForTransferDTO.setLastName("");
                            acaeRecordsForTransferDTO.setUserId(userRec.getUserId());
                            //the transfer options will only available logged user => current users
                            acaeRecordsForTransferDTO.setIsSelectEnable(true);
                        } else {
                            //acount mgr
                            UpmDetailLoadRQ upmDetailLoadRQ = new UpmDetailLoadRQ();
                            UpmDetailResponse upmDetailResponse = new UpmDetailResponse();

                            // if current user system get searched sol id
                            upmDetailLoadRQ.setUserID(acaeRecordsForTransferRQ.getSolId());
                            upmDetailLoadRQ.setAppCode("CAS");
                            try {
                                upmDetailResponse = integrationService.getUpmDetailsByUserIdAndAppCode(upmDetailLoadRQ);
                            } catch (Exception e) {
                                LOG.error("ERROR : ", e);
                            }
                            if (!upmDetailResponse.getFirstName().isEmpty() || !upmDetailResponse.getLastName().isEmpty()) {
                                acaeRecordsForTransferDTO.setFirstName(upmDetailResponse.getFirstName());
                                acaeRecordsForTransferDTO.setLastName(upmDetailResponse.getLastName());
                                acaeRecordsForTransferDTO.setUserId(userRec.getUserId());
                                //the transfer options will only available logged user => current users
                                acaeRecordsForTransferDTO.setIsSelectEnable(checkEnableAllACAERecordTransfer(upmDetailResponse, credentialsDTO));
                            } else {
                                acaeRecordsForTransferDTO.setFirstName("No User in UPM ,");
                                acaeRecordsForTransferDTO.setLastName(userRec.getUserId());
                                acaeRecordsForTransferDTO.setUserId(userRec.getUserId());
                                //the transfer options will only available logged user => current users
                                acaeRecordsForTransferDTO.setIsSelectEnable(true);
                            }
                        }
                    } else {
                        //except draft
                        UpmDetailLoadRQ upmDetailLoadRQ = new UpmDetailLoadRQ();
                        UpmDetailResponse upmDetailResponse = new UpmDetailResponse();

                        upmDetailLoadRQ.setUserID(userRec.getUserId());
                        upmDetailLoadRQ.setAppCode("CAS");

                        try {
                            upmDetailResponse = integrationService.getUpmDetailsByUserIdAndAppCode(upmDetailLoadRQ);
                        } catch (Exception e) {
                            LOG.error("ERROR : ", e);
                        }
                        if (!upmDetailResponse.getFirstName().isEmpty() || !upmDetailResponse.getLastName().isEmpty()) {
                            acaeRecordsForTransferDTO.setFirstName(upmDetailResponse.getFirstName());
                            acaeRecordsForTransferDTO.setLastName(upmDetailResponse.getLastName());
                            acaeRecordsForTransferDTO.setUserId(userRec.getUserId());
                            //the transfer options will only available logged user => current users
                            acaeRecordsForTransferDTO.setIsSelectEnable(checkEnableAllACAERecordTransfer(upmDetailResponse, credentialsDTO));
                        } else {
                            acaeRecordsForTransferDTO.setFirstName("No User in UPM ,");
                            acaeRecordsForTransferDTO.setLastName(userRec.getUserId());
                            acaeRecordsForTransferDTO.setUserId(userRec.getUserId());
                            //the transfer options will only available logged user => current users
                            acaeRecordsForTransferDTO.setIsSelectEnable(true);
                        }
                    }
                }

                acaeRecordsForTransferDTO.setUserNo(String.valueOf(userNo[0]));
                userNo[0] = userNo[0] + 1;

                List<ACAETransferRefRecordDTO> acaeTransferRefRecordDTOList = new ArrayList<>();

                //Get the reference records
                if (userRec.getUserId() == null) {
                    acaeTransferRefRecordDTOList =
                            acaeTransferJdbcDAO.getACAETransferOptionRefRepository(acaeRecordsForTransferRQ, null);
                } else {
                    acaeTransferRefRecordDTOList =
                            acaeTransferJdbcDAO.getACAETransferOptionRefRepository(acaeRecordsForTransferRQ, userRec.getUserId());
                }

                List<ACAETransferRefRecordDTO> refRecordDTOList = new ArrayList<>();
                final Integer[] refNo = {0};

                if (!acaeTransferRefRecordDTOList.isEmpty()) {
                    acaeTransferRefRecordDTOList.forEach((refRec) -> {

                        ACAETransferRefRecordDTO acaeTransferRefRecordDTO = new ACAETransferRefRecordDTO();
                        acaeTransferRefRecordDTO.setRefNumber(refRec.getRefNumber());
                        acaeTransferRefRecordDTO.setCreatedOn(refRec.getCreatedOn());
                        acaeTransferRefRecordDTO.setCreatedBy(refRec.getCreatedBy());
                        acaeTransferRefRecordDTO.setRefNo(String.valueOf(refNo[0]));
                        acaeTransferRefRecordDTO.setIsSelectAll(false);
                        acaeTransferRefRecordDTO.setIsExpanded(false);
                        refNo[0] = refNo[0] + 1;
                        List<ACAETransferACAERecordDTO> acaeTransferACAERecordDTOList = new ArrayList<>();

                        //Get the ACAE records
                        if (userRec.getUserId() == null) {
                            acaeTransferACAERecordDTOList =
                                    acaeTransferJdbcDAO.getACAETransferACAERecordsRepository(refRec.getRefNumber(), null);
                        } else {
                            acaeTransferACAERecordDTOList =
                                    acaeTransferJdbcDAO.getACAETransferACAERecordsRepository(refRec.getRefNumber(), userRec.getUserId());
                        }

                        List<ACAETransferACAERecordDTO> acaeRecordDTOList = new ArrayList<>();
                        final Integer[] recordNo = {0};

                        if (!acaeTransferACAERecordDTOList.isEmpty()) {
                            acaeTransferACAERecordDTOList.forEach((acaeRec) -> {
                                ACAETransferACAERecordDTO acaeTransferACAERecordDTO = new ACAETransferACAERecordDTO();
                                acaeTransferACAERecordDTO.setAccName(acaeRec.getAccName());
                                acaeTransferACAERecordDTO.setAccNo(acaeRec.getAccNo());
                                acaeTransferACAERecordDTO.setRefNumber(acaeRec.getRefNumber());
                                acaeTransferACAERecordDTO.setAcaeStatus(acaeRec.getAcaeStatus());
                                acaeTransferACAERecordDTO.setReceivedDate(acaeRec.getReceivedDate());
                                acaeTransferACAERecordDTO.setCurrentUser(acaeRec.getCurrentUser());
                                acaeTransferACAERecordDTO.setIsActive(acaeRec.getIsActive());
                                acaeTransferACAERecordDTO.setIsSelected(false);
                                acaeTransferACAERecordDTO.setIsSelectEnable(checkEnableACAERecordTransfer(acaeRec, acaeRecordsForTransferDTO.getIsSelectEnable()));
                                acaeTransferACAERecordDTO.setIsAttended(acaeRec.getIsAttended());
                                acaeTransferACAERecordDTO.setTranCountNo(tranACAENo[0]);
                                acaeTransferACAERecordDTO.setRecordNo(String.valueOf(recordNo[0]));
                                tranACAENo[0] = tranACAENo[0] + 1;
                                recordNo[0] = recordNo[0] + 1;

                                acaeRecordDTOList.add(acaeTransferACAERecordDTO);
                            });
                        }
                        acaeTransferRefRecordDTO.setAcaeCount(String.valueOf(acaeTransferACAERecordDTOList.size()));
                        acaeTransferRefRecordDTO.setAcaeRecords(acaeRecordDTOList);
                        refRecordDTOList.add(acaeTransferRefRecordDTO);
                    });
                }
                acaeRecordsForTransferDTO.setRefRecords(refRecordDTOList);
                acaeRecordsForTransferDTOList.add(acaeRecordsForTransferDTO);
            });
        }
        LOG.info("END : ACAE Records For Transfer Service: {}", acaeRecordsForTransferDTOList);

        return acaeRecordsForTransferDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean forwardTransferOptionService(ACAETransferOptionRQ transferOptionRQ,
                                                CredentialsDTO credentialsDTO) throws AppsException {
        boolean isSuccess = false;

        LOG.info("START : Forward Transfer Option Service: {} , user {}", transferOptionRQ, credentialsDTO);

        List<ACAETransferACAERecordDTO> arrayList = transferOptionRQ.getAcaeRecordList();

        try {
            if (!arrayList.isEmpty()) {
                arrayList.forEach((rec) -> {
                    ACAETransferOptionRQ acaeTransferOptionRQ = new ACAETransferOptionRQ();
                    acaeTransferOptionRQ.setRefNum(rec.getRefNumber());
                    acaeTransferOptionRQ.setAccNo(rec.getAccNo());
                    acaeTransferOptionRQ.setComment(transferOptionRQ.getComment());
                    acaeTransferOptionRQ.setNextUser(transferOptionRQ.getNextUser());
                    acaeTransferOptionRQ.setThisUser(transferOptionRQ.getThisUser());
                    acaeTransferOptionRQ.setAcaeStatus(transferOptionRQ.getAcaeStatus());
                    acaeTransferOptionRQ.setNegDate(transferOptionRQ.getNegDate());
                    acaeTransferOptionRQ.setPreNegDate(transferOptionRQ.getPreNegDate());
                    acaeTransferOptionRQ.setSolId(transferOptionRQ.getSolId());
                    acaeTransferOptionRQ.setAnticipatedDate(transferOptionRQ.getAnticipatedDate());
                    acaeTransferOptionRQ.setNextUserName(transferOptionRQ.getNextUserName());
                    if (rec.getAcaeStatus() == null) {
                        LOG.error("Transfer error");
                        throw new RuntimeException("Transfer error");
                    }

                    if (rec.getAcaeStatus() == 14) {
                        try {
                            acaeTransferJdbcDAO.transferDraftPaperRepository(acaeTransferOptionRQ);
                        } catch (Exception e) {
                            LOG.error("Transfer Draft Paper Repository : {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            acaeTransferJdbcDAO.transferACAEPaperRepository(acaeTransferOptionRQ);
                        } catch (Exception e) {
                            LOG.error("Error in transfer Paper Repository : {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            isSuccess = true;

            LOG.info("END : Forward Transfer Option Service: {} , user {}", transferOptionRQ, credentialsDTO);

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSuccess;
    }

    public Boolean checkEnableAllACAERecordTransfer(UpmDetailResponse upmDetailResponse, CredentialsDTO
            credentialsDTO) {

        Boolean isEnable = false;
        try {
            LOG.info("START : Check Enable All ACAE Record Transfer Service: {} , user {}", upmDetailResponse, credentialsDTO);

            Integer currentUser = 0;
            if (upmDetailResponse.getApplicationSecurityClass() == null) {
                currentUser = 50;
            } else {
                currentUser = Integer.parseInt(upmDetailResponse.getApplicationSecurityClass());
            }
            Integer loggedUser = Integer.parseInt(credentialsDTO.getUpmGroupCode());
            if (loggedUser >= currentUser)
                isEnable = true;
            else
                isEnable = false;

            LOG.info("END : Check Enable All ACAE Record Transfer Service: {}", isEnable);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isEnable;
    }

    public Boolean checkEnableACAERecordTransfer(ACAETransferACAERecordDTO acaeTransferACAERecordDTO, Boolean
            isSelectAll) {
        boolean isEnable = false;
        try {
            LOG.info("START : Check Enable ACAE Record Transfer: {}", acaeTransferACAERecordDTO);

            if (acaeTransferACAERecordDTO.getIsAttended().equals("N") && isSelectAll)
                isEnable = true;
            else {
                isEnable = false;
            }
            LOG.info("END : Check Enable ACAE Record Transfer: {}", isEnable);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isEnable;
    }

    public List<UpmGroupDTO> getTransferUserListService(LoggedInUserWorkFlowByStatusRQ
                                                                loggedInUserWorkFlowByStatusRQ) {
        return acaeTransferJdbcDAO.getTransferUserGroupList(loggedInUserWorkFlowByStatusRQ);
    }
}
