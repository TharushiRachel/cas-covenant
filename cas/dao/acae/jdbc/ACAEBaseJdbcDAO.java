package com.itechro.cas.dao.acae.jdbc;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.acae.request.ACAEListDoneRQ;
import com.itechro.cas.model.dto.acae.request.ACAESearchByStatusRQ;
import com.itechro.cas.model.dto.acae.request.ACAEStatusCountRQ;
import com.itechro.cas.model.dto.acae.response.ACAESearchByStatusDTO;
import com.itechro.cas.model.dto.acae.response.ACAEStatusCountDTO;
import com.itechro.cas.model.dto.acae.response.ACAEUserDTO;
import com.itechro.cas.model.dto.integration.request.UmpDetailLoadByAdIdRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ACAEBaseJdbcDAO extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEBaseJdbcDAO.class);

    @Value("${apps.cas.application.code}")
    private String applicationCode;

    protected JdbcTemplate jdbcTemplate;
    private final CasProperties casProperties;
    private final EntityManager entityManager;
    private final IntegrationService integrationService;

    @Autowired
    public ACAEBaseJdbcDAO(JdbcTemplate jdbcTemplate, CasProperties casProperties, EntityManager entityManager, IntegrationService integrationService) {
        this.jdbcTemplate = jdbcTemplate;
        this.casProperties = casProperties;
        this.entityManager = entityManager;
        this.integrationService = integrationService;
    }

    public Page<ACAESearchByStatusDTO> getPagedACAERecordsByStatusRepository(ACAESearchByStatusRQ acaeSearchByStatusRQ,
                                                                             CredentialsDTO credentialsDTO) {
        Page<ACAESearchByStatusDTO> pageData = new Page<>();
        try {
            LOG.info("START : ACAE Paged Records By Status Repository: {} , user {} ", acaeSearchByStatusRQ, credentialsDTO);

            String userId = String.valueOf(acaeSearchByStatusRQ.getUserId());
            String listStatus = String.valueOf(acaeSearchByStatusRQ.getAcaeStatus());
            String solId = String.valueOf(acaeSearchByStatusRQ.getSolId());
            String workClass = String.valueOf(credentialsDTO.getUpmGroupCode());
            String solUserName = "";

            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("listStatus", listStatus);
            params.put("branchCode", solId);
            params.put("workClass", workClass);

            StringBuilder sql = new StringBuilder();
            final Integer[] recordIndex = {0};

            UpmDetailResponse upmDetailResponse = new UpmDetailResponse();

            try {
                UmpDetailLoadByAdIdRQ rq = new UmpDetailLoadByAdIdRQ();
                rq.setAdUserID(credentialsDTO.getUserName());
                rq.setAppCode(this.casProperties.getApplicationCode());
                upmDetailResponse = integrationService.getUpmDetailsByAdUserAndAppCode(rq);
            } catch (Exception e) {
                LOG.error("ERROR : ", e);
            }

            if (listStatus.equals("14") && isAccountManagerRepository(solId)) {
                solUserName = acaeSearchByStatusRQ.getUserName();
            } else if (listStatus.equals("14") && !isAccountManagerRepository(solId)) {
                upmDetailResponse.setSolName(null);
                solUserName = (upmDetailResponse.getSolName() == null) || (upmDetailResponse.getSolName().isEmpty()) ?
                        acaeSearchByStatusRQ.getSolName() : upmDetailResponse.getSolName();
            }

            sql.append(" SELECT REF_NUM, ACCT_NO, ACCT_NAME, ATTENDED, RECEIVED_DATE, SOL_USER_ID, SOL_USER_NAME, MIN_BAL,CURRENT_USER_ID,CURRENT_USER_NAME ");
            sql.append(" FROM TABLE(CAST(ACAE_OPERATIONS.GET_ACAE_LIST_BY_STATUS(:userId,:listStatus,:branchCode,:workClass) AS ACAE_REFERENCES)) ");

            String finalSolUserName = solUserName;
            pageData = getPagedData(sql.toString(), params, new RowMapper<ACAESearchByStatusDTO>() {

                @Nullable
                @Override
                public ACAESearchByStatusDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                    ACAESearchByStatusDTO acaeSearchByStatusDTO = new ACAESearchByStatusDTO();
                    acaeSearchByStatusDTO.setRefNumber(rs.getString("REF_NUM"));
                    acaeSearchByStatusDTO.setAccountNumber(rs.getString("ACCT_NO"));
                    acaeSearchByStatusDTO.setCustomerName(rs.getString("ACCT_NAME"));
                    acaeSearchByStatusDTO.setSolUserName(
                            !finalSolUserName.equals("") ?
                                    finalSolUserName : rs.getString("SOL_USER_NAME")
                    );
                    acaeSearchByStatusDTO.setAttended(rs.getString("ATTENDED"));
                    acaeSearchByStatusDTO.setRecievedDate(rs.getDate("RECEIVED_DATE"));
                    acaeSearchByStatusDTO.setSolUserId(rs.getString("SOL_USER_ID"));
                    acaeSearchByStatusDTO.setMinBal(rs.getDouble("MIN_BAL"));
                    acaeSearchByStatusDTO.setCurrentUserName(rs.getString("CURRENT_USER_NAME"));

                    if ((listStatus.equals("14") || listStatus.equals("2") || listStatus.equals("9")
                            || listStatus.equals("5") || listStatus.equals("6") || listStatus.equals("4"))) {
                        acaeSearchByStatusDTO.setCurrentUserName(credentialsDTO.getDisplayName());
                    } else {
                        if (rs.getString("CURRENT_USER_NAME") != null) {
                            acaeSearchByStatusDTO.setCurrentUserName(rs.getString("CURRENT_USER_NAME"));
                            acaeSearchByStatusDTO.setLoadUserName(true);
                        } else {
                            acaeSearchByStatusDTO.setCurrentUserName(rs.getString("CURRENT_USER_ID"));
                            acaeSearchByStatusDTO.setLoadUserName(false);
                        }

                    }

//                    if (!(listStatus.equals("14") || listStatus.equals("2") || listStatus.equals("9")
//                            || listStatus.equals("5") || listStatus.equals("6") || listStatus.equals("4"))) {
//                        UpmDetailResponse upmDetailResponse = new UpmDetailResponse();
//                        UmpDetailLoadByAdIdRQ rq = new UmpDetailLoadByAdIdRQ();
//                        rq.setAdUserID(rs.getString("CURRENT_USER_ID"));
//                        rq.setAppCode(casProperties.getApplicationCode());
//                        try {
//                            upmDetailResponse = integrationService.getUpmDetailsByAdUserAndAppCode(rq);
//                            if(upmDetailResponse.getFirstName().isEmpty() && upmDetailResponse.getFirstName().isEmpty()){
//                                acaeSearchByStatusDTO.setCurrentUserName("user not found!");
//                            }else{
//                                acaeSearchByStatusDTO.setCurrentUserName(upmDetailResponse.getFirstName() + " " + upmDetailResponse.getLastName());
//                            }
//                        } catch (AppsException e) {
//                            acaeSearchByStatusDTO.setCurrentUserName("user not found!");
//                            LOG.error("ERROR : ", e);
//                        }
//                    } else {
//                        acaeSearchByStatusDTO.setCurrentUserName("This User");
//                    }

                    acaeSearchByStatusDTO.setStatus(acaeSearchByStatusRQ.getAcaeStatus().toString());
                    acaeSearchByStatusDTO.setRecordIndex(recordIndex[0]);
                    acaeSearchByStatusDTO.setSelected(false);
                    recordIndex[0]++;
                    return acaeSearchByStatusDTO;
                }
            }, acaeSearchByStatusRQ);

            LOG.info("END : ACAE Paged ACAE Records By Status Repository: {} , acaeSearchByStatusRQ {} ", pageData, acaeSearchByStatusRQ);

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return pageData;
    }

    public String updateACAECompleteRepository(ACAEListDoneRQ acaeListDoneRQ) {

        String message = "";
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.UPDATE_ACAE_COMPLETE");

            query.registerStoredProcedureParameter("IN_SEARCH_REF_NUM", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("IN_SEARCH_REF_NUM", acaeListDoneRQ.getReferenceNumber());
            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("UPDATE_ACAE_COMPLETE PROC Failed");
            }
            message = "SUCESS";
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return message;
    }


    public List<ACAESearchByStatusDTO> getACAERecordsByStatusRepository(ACAESearchByStatusRQ acaeSearchByStatusRQ,
                                                                        CredentialsDTO credentialsDTO) {
        List<ACAESearchByStatusDTO> acaeSearchByStatusDTOList = new ArrayList<>();
        try {
            LOG.info("START : ACAE Records By Status Repository: {} , user {} ", acaeSearchByStatusRQ, credentialsDTO);

            String userId = String.valueOf(acaeSearchByStatusRQ.getUserId());
            String listStatus = String.valueOf(acaeSearchByStatusRQ.getAcaeStatus());
            String solId = String.valueOf(acaeSearchByStatusRQ.getSolId());
            String workClass = String.valueOf(credentialsDTO.getUpmGroupCode());

            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("listStatus", listStatus);
            params.put("branchCode", solId);
            params.put("workClass", workClass);

            StringBuilder sql = new StringBuilder();
            final Integer[] recordIndex = {0};

            sql.append(" SELECT REF_NUM , ACCT_NO ,ACCT_NAME ,ATTENDED,RECEIVED_DATE,SOL_USER_ID ,MIN_BAL ");
            sql.append(" FROM TABLE(CAST(ACAE_OPERATIONS.GET_ACAE_LIST_BY_STATUS(:userId,:listStatus,:branchCode,:workClass) AS ACAE_REFERENCES)) ");

            acaeSearchByStatusDTOList = namedParameterJdbcTemplate.query(sql.toString(), params, new RowMapper<ACAESearchByStatusDTO>() {
                @Nullable
                @Override
                public ACAESearchByStatusDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                    ACAESearchByStatusDTO acaeSearchByStatusDTO = new ACAESearchByStatusDTO();
                    acaeSearchByStatusDTO.setRefNumber(rs.getString("REF_NUM"));
                    acaeSearchByStatusDTO.setAccountNumber(rs.getString("ACCT_NO"));
                    acaeSearchByStatusDTO.setCustomerName(rs.getString("ACCT_NAME"));
                    acaeSearchByStatusDTO.setAttended(rs.getString("ATTENDED"));
                    acaeSearchByStatusDTO.setRecievedDate(rs.getDate("RECEIVED_DATE"));
                    acaeSearchByStatusDTO.setMinBal(rs.getDouble("MIN_BAL"));
                    acaeSearchByStatusDTO.setSolUserId(rs.getString("SOL_USER_ID"));
                    acaeSearchByStatusDTO.setStatus(acaeSearchByStatusRQ.getAcaeStatus().toString());
                    acaeSearchByStatusDTO.setRecordIndex(recordIndex[0]);
                    recordIndex[0]++;
                    return acaeSearchByStatusDTO;
                }
            });
            LOG.info("END : ACAE Records By Status Repository: {}", acaeSearchByStatusDTOList);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return acaeSearchByStatusDTOList;
    }

    public ACAEStatusCountDTO getACAEStatusCountRepository(ACAEStatusCountRQ acaeStatusCountRQ, CredentialsDTO credentialsDTO) {
        ACAEStatusCountDTO acaeStatusCountDTO = new ACAEStatusCountDTO();
        try {
            Map<String, Object> params = new HashMap<>();

            LOG.info("START : ACAE Status Count Repository: {} , User {} ", acaeStatusCountRQ, credentialsDTO);

            params.put("userId", acaeStatusCountRQ.getUserId());
            params.put("solId", acaeStatusCountRQ.getSolId());
            params.put("workClass", credentialsDTO.getUpmGroupCode());

            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT * from  TABLE(CAST(ACAE_OPERATIONS.GET_ACAE_STATUS_COUNT(:userId,:solId,:workClass) AS ACAE_COUNT_TABLE))");

            acaeStatusCountDTO = namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<ACAEStatusCountDTO>() {
                @Override
                public ACAEStatusCountDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                    ACAEStatusCountDTO acaeStatusCountDTO = new ACAEStatusCountDTO();
                    while (rs.next()) {

                        acaeStatusCountDTO.setDraftCount(rs.getInt("DRAFT_C"));
                        acaeStatusCountDTO.setForwardToMeCount(rs.getInt("FORWARD_TO_ME_C"));
                        acaeStatusCountDTO.setReturnToMeCount(rs.getInt("RETURN_TO_ME_C"));
                        acaeStatusCountDTO.setResubmitToMeCount(rs.getInt("RESUBMIT_TO_ME_50_C"));
                        acaeStatusCountDTO.setForwardCount(rs.getInt("FORWARD_BY_ME_C"));
                        acaeStatusCountDTO.setReturnCount(rs.getInt("RETURN_BY_ME_C"));
                        acaeStatusCountDTO.setTransferToMeCount(rs.getInt("TRANSFER_TO_ME_C"));
                        acaeStatusCountDTO.setApprovedCount(rs.getInt("APPROVE_BY_ME_C"));
                        acaeStatusCountDTO.setToBeResubmitCount(rs.getInt("RESUBMIT_BY_ME_C"));
                    }
                    return acaeStatusCountDTO;
                }
            });
            if (Integer.parseInt(credentialsDTO.getUpmGroupCode()) > 50) {
                acaeStatusCountDTO.setNewACAECount(
                        acaeStatusCountDTO.getForwardToMeCount() + acaeStatusCountDTO.getResubmitToMeCount()
                                + acaeStatusCountDTO.getTransferToMeCount() +
                                acaeStatusCountDTO.getReturnToMeCount());
            } else {
                acaeStatusCountDTO.setNewACAECount(
                        acaeStatusCountDTO.getDraftCount() + acaeStatusCountDTO.getForwardToMeCount() +
                                acaeStatusCountDTO.getTransferToMeCount() +
                                acaeStatusCountDTO.getResubmitToMeCount() +
                                acaeStatusCountDTO.getReturnToMeCount());
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return acaeStatusCountDTO;
    }


    public String getEligibilityForwardACAEBatchRepository(ACAEListDoneRQ acaeListDoneRQ, CredentialsDTO credentialsDTO) {

        String execute = "";
        try {
            LOG.info("START : ACAE Eligibility Forward ACAE Batch Repository: {} , User {} ", acaeListDoneRQ, credentialsDTO);

            String solId = acaeListDoneRQ.getSolId();
            String userId = String.valueOf(credentialsDTO.getUserID());
            String referenceNumber = acaeListDoneRQ.getReferenceNumber();

            String sql = "SELECT ACAE_OPERATIONS.IS_ACAE_LIST_DONE(?,?,?) AS COUNT FROM DUAL";

            execute = jdbcTemplate.queryForObject(sql, new Object[]{solId, userId, referenceNumber}, String.class);

            LOG.info("END : ACAE Eligibility Forward ACAE Batch Repository: {} ", execute);

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return execute;
    }

    public boolean isAccountManagerRepository(String solUserId) {
        boolean isAccMgr = false;
        Integer count = 0;
        try {
            LOG.info("START : Is Account Manager Repository : {}  ", solUserId);
            Map<String, Object> params = new HashMap<>();

            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT COUNT(*) ");
            sql.append(" FROM SP_SOL_TABLE ");
            sql.append(" WHERE SOL_ID=:solUserId ");

            params.put("solUserId", solUserId);

            count = namedParameterJdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
            count = count != null ? count : 0;

            if (count > 0) {
                isAccMgr = true;
            }

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isAccMgr;
    }

    public List<ACAEUserDTO> getACAEUsersRepository(ACAESearchByStatusRQ acaeSearchByStatusRQ,
                                                    CredentialsDTO credentialsDTO) {
        List<ACAEUserDTO> acaeUserDTOList = new ArrayList<>();
        List<ACAEUserDTO> resultArray = new ArrayList<>();
        try {
            LOG.info("START : ACAE Users Repository : {} , User {} ", acaeSearchByStatusRQ, credentialsDTO);

            Map<String, Object> params = new HashMap<>();

            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT DISTINCT S.CURRENT_USER ");
            sql.append(" FROM CASV2_ACAE_STATUS_TABLE S ");
            sql.append(" INNER JOIN CASV2_ACAE_COMMENTS CM ");
            sql.append(" ON S.REF_NUM  = CM.REF_NUM ");
            sql.append(" AND S.ACCT_NO = CM.ACCT_NO ");
            sql.append(" INNER JOIN CASV2_ACAE_CUSTOMER_DETAILS C ");
            sql.append(" ON S.REF_NUM      = C.REF_NUM ");
            sql.append(" AND S.ACCT_NO     = C.ACCT_NO ");
            sql.append(" INNER JOIN CASV2_ACAE_SEARCH SI ");
            sql.append(" ON S.REF_NUM     =SI.REF_NUM ");
            sql.append(" WHERE  CM.IS_ACTIVE  = 1");
            sql.append(" AND S.ACAE_STATUS = :status ");

            params.put("status", acaeSearchByStatusRQ.getAcaeStatus());

            if (Integer.parseInt(credentialsDTO.getUpmGroupCode()) <= 50) {
                sql.append(" AND SI.SOL_USER_ID=:solId");
                params.put("solId", acaeSearchByStatusRQ.getSolId());
            } else {
                sql.append(" AND S.PRE_USER  =:userId");
                params.put("userId", acaeSearchByStatusRQ.getSolId());
            }

            acaeUserDTOList = namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<ACAEUserDTO>>() {
                @Nullable
                @Override
                public List<ACAEUserDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    while (rs.next()) {
                        ACAEUserDTO acaeUserDTO = new ACAEUserDTO();
                        acaeUserDTO.setUserId(rs.getString("CURRENT_USER"));
                        acaeUserDTO.setFirstName(" ");
                        acaeUserDTO.setLastName(" ");
                        resultArray.add(acaeUserDTO);
                    }
                    return resultArray;
                }
            });
            LOG.info("END : ACAE Users Repository : {}  ", acaeUserDTOList);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }

        return acaeUserDTOList;
    }

}