package com.itechro.cas.dao.acae.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.acae.request.ACAERecordsForTransferRQ;
import com.itechro.cas.model.dto.acae.request.ACAETransferOptionRQ;
import com.itechro.cas.model.dto.acae.response.*;
import com.itechro.cas.model.dto.casmaster.LoggedInUserWorkFlowByStatusRQ;
import com.itechro.cas.model.dto.casmaster.UpmGroupDTO;
import com.itechro.cas.model.dto.integration.request.UmpDetailLoadByAdIdRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class ACAETransferJdbcDAO extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(ACAETransferJdbcDAO.class);

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;
    protected ACAEBaseJdbcDAO acaeBaseJdbcDAO;
    private final CasProperties casProperties;
    private final IntegrationService integrationService;
    private final EntityManager entityManager;

    @Autowired
    public ACAETransferJdbcDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, EntityManager entityManager,
                               ACAEBaseJdbcDAO acaeBaseJdbcDAO, CasProperties casProperties, IntegrationService integrationService) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
        this.acaeBaseJdbcDAO = acaeBaseJdbcDAO;
        this.casProperties = casProperties;
        this.integrationService = integrationService;
    }

    public List<ACAETransferRefRecordDTO> getACAETransferRefRecordsRepository(ACAERecordsForTransferRQ acaeRecordsForTransferRQ) {

        List<ACAETransferRefRecordDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();

        params.put("solId", acaeRecordsForTransferRQ.getSolId());
        params.put("fromDate", acaeRecordsForTransferRQ.getFromDate());
        params.put("toDate", acaeRecordsForTransferRQ.getToDate());

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT REF_NUM,CREATED_ON,CREATED_BY FROM TABLE(CAST(ACAE_OPERATIONS.GET_TRANSFER_OPTION_REF_RECORDS(:solId, TO_DATE(:fromDate, 'YYYY-MM-DD'), TO_DATE(:toDate, 'YYYY-MM-DD')) AS ACAE_REF_RECORDS))");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ACAETransferRefRecordDTO>>() {
            @Override
            public List<ACAETransferRefRecordDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ACAETransferRefRecordDTO acaeTransferRefRecordDTO = new ACAETransferRefRecordDTO();
                    acaeTransferRefRecordDTO.setRefNumber(rs.getString("REF_NUM"));
                    acaeTransferRefRecordDTO.setCreatedOn(rs.getDate("CREATED_ON"));
                    acaeTransferRefRecordDTO.setCreatedBy(rs.getString("CREATED_BY"));
                    resultSet.add(acaeTransferRefRecordDTO);
                }
                return resultSet;
            }
        });
    }

    public List<UpmGroupDTO> getTransferUserGroupList(LoggedInUserWorkFlowByStatusRQ loggedInUserWorkFlowByStatusRQ) {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("loggedInUserUpmGroupCode", loggedInUserWorkFlowByStatusRQ.getLoggedInUserUpmGroupCode());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT DISTINCT ug.GROUP_CODE,                                                                  \n");
        SQL.append("       ug.UPM_GROUP_ID,                                                                         \n");
        SQL.append("     ug.REFERENCE_NAME                                                                          \n");
        SQL.append("FROM T_WORK_FLOW_TEMPLATE wt                                                                    \n");
        SQL.append("INNER JOIN T_WORK_FLOW_TEMPLATE_DATA wtd ON wt.WORK_FLOW_TEMPLATE_ID = wtd.WORK_FLOW_TEMPLATE_ID\n");
        SQL.append("INNER JOIN T_UPM_GROUP ug ON wtd.UPM_GROUP_ID  = ug.UPM_GROUP_ID                                \n");
        SQL.append("WHERE wt.WORK_FLOW_TEMPLATE_ID IS NOT NULL                                                      \n");
        SQL.append("AND wt.STATUS    = 'ACT'                                                                        \n");
        SQL.append("AND ug.GROUP_CODE <= :loggedInUserUpmGroupCode                                                  \n");
        SQL.append("ORDER BY  ug.GROUP_CODE                                                                         \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), paramsMap, new RowMapper<UpmGroupDTO>() {
            @Nullable
            @Override
            public UpmGroupDTO mapRow(ResultSet rs, int i) throws SQLException {
                UpmGroupDTO upmGroupDTO = new UpmGroupDTO();
                if (!rs.getString("GROUP_CODE").equals("64")) {
                    upmGroupDTO.setGroupCode(rs.getString("GROUP_CODE"));
                    upmGroupDTO.setReferenceName(rs.getString("REFERENCE_NAME"));
                }
                return upmGroupDTO;
            }
        });
    }

    public List<ACAETransferUserRecordDTO> getACAETransferOptionUsersRepository(ACAERecordsForTransferRQ acaeRecordsForTransferRQ) {

        List<ACAETransferUserRecordDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();

        params.put("solId", acaeRecordsForTransferRQ.getSolId());
        params.put("fromDate", acaeRecordsForTransferRQ.getFromDate());
        params.put("toDate", acaeRecordsForTransferRQ.getToDate());

        StringBuilder SQL = new StringBuilder();

        SQL.append("   SELECT DISTINCT ST.CURRENT_USER\n" +
                "FROM CASV2_ACAE_SEARCH S\n" +
                "INNER JOIN CASV2_ACAE_STATUS_TABLE ST\n" +
                "ON S.REF_NUM        = ST.REF_NUM\n" +
                "WHERE S.SOL_USER_ID = :solId\n" +
                "AND S.ACAE_DATE BETWEEN TO_DATE(:fromDate, 'YYYY-MM-DD') AND TO_DATE(:toDate, 'YYYY-MM-DD')\n" +
                "ORDER BY ST.CURRENT_USER ASC");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<ACAETransferUserRecordDTO>>() {
            @Override
            public List<ACAETransferUserRecordDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ACAETransferUserRecordDTO acaeTransferUserRecordDTO = new ACAETransferUserRecordDTO();
                    acaeTransferUserRecordDTO.setUserId(rs.getString("CURRENT_USER"));
                    acaeTransferUserRecordDTO.setFirstName("");
                    acaeTransferUserRecordDTO.setLastName("");
                    acaeTransferUserRecordDTO.setUserLevel("");
                    resultSet.add(acaeTransferUserRecordDTO);
                }
                return resultSet;
            }
        });
    }

    public List<ACAETransferRefRecordDTO> getACAETransferOptionRefRepository(ACAERecordsForTransferRQ acaeRecordsForTransferRQ, String currentUser) {
        List<ACAETransferRefRecordDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();

        params.put("solId", acaeRecordsForTransferRQ.getSolId());
        params.put("fromDate", acaeRecordsForTransferRQ.getFromDate());
        params.put("toDate", acaeRecordsForTransferRQ.getToDate());
        params.put("currentUser", currentUser);

        StringBuilder sql = new StringBuilder();
        if (currentUser == null) {
            sql.append("  SELECT DISTINCT S.REF_NUM, S.ACAE_DATE,S.ACAE_COUNT,S.USER_ID\n" +
                    "    FROM CASV2_ACAE_SEARCH S\n" +
                    "    INNER JOIN CASV2_ACAE_STATUS_TABLE ST ON S.REF_NUM=ST.REF_NUM\n" +
                    "    WHERE S.SOL_USER_ID=:solId  AND S.ACAE_DATE BETWEEN TO_DATE(:fromDate, 'YYYY-MM-DD') AND TO_DATE(:toDate, 'YYYY-MM-DD') AND ST.CURRENT_USER IS NULL \n" +
                    "    ORDER BY S.ACAE_DATE ASC");
        } else {
            sql.append("  SELECT DISTINCT S.REF_NUM, S.ACAE_DATE,S.ACAE_COUNT,S.USER_ID\n" +
                    "    FROM CASV2_ACAE_SEARCH S\n" +
                    "    INNER JOIN CASV2_ACAE_STATUS_TABLE ST ON S.REF_NUM=ST.REF_NUM\n" +
                    "    WHERE S.SOL_USER_ID=:solId  AND S.ACAE_DATE BETWEEN TO_DATE(:fromDate, 'YYYY-MM-DD') AND TO_DATE(:toDate, 'YYYY-MM-DD') AND ST.CURRENT_USER =:currentUser\n" +
                    "    ORDER BY S.ACAE_DATE ASC");
        }
        return namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<ACAETransferRefRecordDTO>>() {
            @Override
            public List<ACAETransferRefRecordDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ACAETransferRefRecordDTO acaeTransferRefRecordDTO = new ACAETransferRefRecordDTO();
                    acaeTransferRefRecordDTO.setRefNumber(rs.getString("REF_NUM"));
                    acaeTransferRefRecordDTO.setCreatedOn(rs.getDate("ACAE_DATE"));
                    acaeTransferRefRecordDTO.setCreatedBy(rs.getString("USER_ID"));
                    acaeTransferRefRecordDTO.setAcaeCount(rs.getString("ACAE_COUNT"));
                    resultSet.add(acaeTransferRefRecordDTO);
                }
                return resultSet;
            }
        });
    }

    public List<ACAETransferACAERecordDTO> getACAETransferACAERecordsRepository(String refNum, String currentUser) {
        List<ACAETransferACAERecordDTO> resultSet = new ArrayList<>();
        try {
            Map<String, Object> params = new HashMap<>();

            params.put("refNum", refNum);
            params.put("currentUser", currentUser);

            StringBuilder sql = new StringBuilder();

            final Integer[] tranCountNo = {0};

            if (currentUser == null) {
                sql.append("     SELECT S.REF_NUM, S.ACCT_NO,   C.ACCT_NAME,  S.RECEIVED_DATE,  CM.REMARK_FLAG AS ATTENDED, CM.IS_ACTIVE,S.ACAE_STATUS \n" +
                        "    FROM CASV2_ACAE_STATUS_TABLE S\n" +
                        "    INNER JOIN CASV2_ACAE_COMMENTS CM ON S.REF_NUM  = CM.REF_NUM  AND S.ACCT_NO = CM.ACCT_NO  \n" +
                        "    INNER JOIN CASV2_ACAE_CUSTOMER_DETAILS C ON S.REF_NUM  = C.REF_NUM  AND S.ACCT_NO = C.ACCT_NO  \n" +
                        "    INNER JOIN CASV2_ACAE_SEARCH SE ON SE.REF_NUM = C.REF_NUM \n" +
                        "    AND CM.IS_ACTIVE     = 1 \n" +
                        "    AND S.REF_NUM = :refNum\n" +
                        "    AND S.CURRENT_USER IS NULL \n" +
                        "    AND S.ACAE_STATUS NOT IN 4\n" +
                        "    ORDER BY SE.ACAE_DATE ASC");
            } else {
                sql.append("     SELECT S.REF_NUM, S.ACCT_NO,   C.ACCT_NAME,  S.RECEIVED_DATE,  CM.REMARK_FLAG AS ATTENDED, CM.IS_ACTIVE,S.ACAE_STATUS \n" +
                        "    FROM CASV2_ACAE_STATUS_TABLE S\n" +
                        "    INNER JOIN CASV2_ACAE_COMMENTS CM ON S.REF_NUM  = CM.REF_NUM  AND S.ACCT_NO = CM.ACCT_NO  \n" +
                        "    INNER JOIN CASV2_ACAE_CUSTOMER_DETAILS C ON S.REF_NUM  = C.REF_NUM  AND S.ACCT_NO = C.ACCT_NO  \n" +
                        "    INNER JOIN CASV2_ACAE_SEARCH SE ON SE.REF_NUM = C.REF_NUM \n" +
                        "    AND CM.IS_ACTIVE     = 1 \n" +
                        "    AND S.REF_NUM = :refNum\n" +
                        "    AND S.CURRENT_USER = :currentUser \n" +
                        "    AND S.ACAE_STATUS NOT IN 4\n" +
                        "    ORDER BY SE.ACAE_DATE ASC");
            }

            namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<ACAETransferACAERecordDTO>>() {
                @Override
                public List<ACAETransferACAERecordDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    while (rs.next()) {
                        ACAETransferACAERecordDTO acaeTransferACAERecordDTO = new ACAETransferACAERecordDTO();
                        acaeTransferACAERecordDTO.setRefNumber(rs.getString("REF_NUM"));
                        acaeTransferACAERecordDTO.setAccNo(rs.getString("ACCT_NO"));
                        acaeTransferACAERecordDTO.setAccName(rs.getString("ACCT_NAME"));
                        acaeTransferACAERecordDTO.setReceivedDate(rs.getDate("RECEIVED_DATE"));
                        acaeTransferACAERecordDTO.setIsAttended(rs.getString("ATTENDED"));
                        acaeTransferACAERecordDTO.setIsActive(rs.getString("IS_ACTIVE"));
                        acaeTransferACAERecordDTO.setAcaeStatus(rs.getInt("ACAE_STATUS"));
                        acaeTransferACAERecordDTO.setTranCountNo(tranCountNo[0]);
                        acaeTransferACAERecordDTO.setIsSelected(false);
                        tranCountNo[0] = tranCountNo[0] + 1;
                        resultSet.add(acaeTransferACAERecordDTO);
                    }
                    return resultSet;
                }
            });
            LOG.info("Get ACAE Transfer ACAE Records Repository Result {} ", resultSet);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return resultSet;
    }

    private String transferProcCall(ACAETransferOptionRQ acaeTransferOptionRQ) throws AppsException {
        String message = "";
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.TRANSFER_PAPER");

            query.registerStoredProcedureParameter("STRING_REFERENCE_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_ACCT_NO", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("NUMBER_TRAN_STATUS", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_REMARK", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_THIS_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_NEXT_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("DATE_NEG_DATE", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("DATE_PRE_NEG_DATE", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IN_CURRENT_USER_NAME", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("STRING_REFERENCE_ID", acaeTransferOptionRQ.getRefNum());
            query.setParameter("STRING_ACCT_NO", acaeTransferOptionRQ.getAccNo());
            query.setParameter("NUMBER_TRAN_STATUS", acaeTransferOptionRQ.getAcaeStatus());
            query.setParameter("STRING_REMARK", acaeTransferOptionRQ.getComment());
            query.setParameter("STRING_THIS_USER_ID", acaeTransferOptionRQ.getThisUser());
            query.setParameter("STRING_NEXT_USER_ID", acaeTransferOptionRQ.getNextUser());
            query.setParameter("DATE_NEG_DATE", acaeTransferOptionRQ.getNegDate());
            query.setParameter("DATE_PRE_NEG_DATE", acaeTransferOptionRQ.getNegDate());
            query.setParameter("IN_CURRENT_USER_NAME", acaeTransferOptionRQ.getNextUserName());
            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("TRANSFER_PAPER PROC Failed", AppsConstants.ResponseStatus.FAILED, outStatus);
            }
            message = "SUCCESS";
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return message;
    }

    private String transferDraftProcCall(ACAETransferOptionRQ acaeTransferOptionRQ) throws AppsException {
        String message = "";
        try {
            UpmDetailResponse upmDetailResponse = new UpmDetailResponse();
            String solUserName = "";
            try {
                UmpDetailLoadByAdIdRQ rq = new UmpDetailLoadByAdIdRQ();
                rq.setAdUserID(acaeTransferOptionRQ.getThisUser());
                rq.setAppCode(this.casProperties.getApplicationCode());
                upmDetailResponse = integrationService.getUpmDetailsByAdUserAndAppCode(rq);
            } catch (Exception e) {
                LOG.error("ERROR : ", e);
            }

            if (!acaeBaseJdbcDAO.isAccountManagerRepository(acaeTransferOptionRQ.getSolId())) {
                solUserName = upmDetailResponse.getFirstName() + " " + upmDetailResponse.getLastName();
            } else {
                solUserName = upmDetailResponse.getSolName();
            }

            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.TRANSFER_DRAFT_PAPER");

            query.registerStoredProcedureParameter("STRING_REFERENCE_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_ACCT_NO", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("NUMBER_TRAN_STATUS", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_REMARK", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_THIS_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_NEXT_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("DATE_NEG_DATE", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("DATE_PRE_NEG_DATE", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IN_CURRENT_USER_NAME", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IN_SOL_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_SOL_USER_NAME", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("STRING_REFERENCE_ID", acaeTransferOptionRQ.getRefNum());
            query.setParameter("STRING_ACCT_NO", acaeTransferOptionRQ.getAccNo());
            query.setParameter("NUMBER_TRAN_STATUS", acaeTransferOptionRQ.getAcaeStatus());
            query.setParameter("STRING_REMARK", acaeTransferOptionRQ.getComment());
            query.setParameter("STRING_THIS_USER_ID", acaeTransferOptionRQ.getThisUser());
            query.setParameter("STRING_NEXT_USER_ID", acaeTransferOptionRQ.getNextUser());
            query.setParameter("DATE_NEG_DATE", acaeTransferOptionRQ.getNegDate());
            query.setParameter("DATE_PRE_NEG_DATE", acaeTransferOptionRQ.getNegDate());
            query.setParameter("IN_CURRENT_USER_NAME", acaeTransferOptionRQ.getNextUserName());
            query.setParameter("IN_SOL_ID", acaeTransferOptionRQ.getSolId());
            query.setParameter("STRING_SOL_USER_NAME", solUserName);
            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("TRANSFER_DRAFT_PAPER PROC Failed", AppsConstants.ResponseStatus.FAILED, outStatus);
            }
            message = "SUCCESS";
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return message;
    }

    @Transactional
    public boolean transferDraftPaperRepository(ACAETransferOptionRQ acaeTransferOptionRQ) throws AppsException {
        boolean isSuccess = false;
        try {
            String message = this.transferDraftProcCall(acaeTransferOptionRQ);
            if (!message.equals("SUCCESS")) {
                isSuccess = false;
                LOG.info("TRANSFER_DRAFT_PAPER PROC Failed: {}", message);
                return isSuccess;
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
            throw e;
        }
        return isSuccess;
    }

    @Transactional
    public boolean transferACAEPaperRepository(ACAETransferOptionRQ acaeTransferOptionRQ) throws AppsException {
        boolean isSuccess = false;

        try {
            String message = this.transferProcCall(acaeTransferOptionRQ);

            if (!message.equals("SUCCESS")) {
                LOG.info("TRANSFER_PAPER PROC Failed: {}", message);
                return isSuccess;
            }

            UpmDetailResponse upmDetailResponse = new UpmDetailResponse();
            String solUserName = "";
            try {
                UmpDetailLoadByAdIdRQ rq = new UmpDetailLoadByAdIdRQ();
                rq.setAdUserID(acaeTransferOptionRQ.getThisUser());
                rq.setAppCode(this.casProperties.getApplicationCode());
                upmDetailResponse = integrationService.getUpmDetailsByAdUserAndAppCode(rq);
            } catch (Exception e) {
                LOG.error("ERROR : ", e);
            }

            if (!acaeBaseJdbcDAO.isAccountManagerRepository(acaeTransferOptionRQ.getSolId())) {
                solUserName = upmDetailResponse.getFirstName() + " " + upmDetailResponse.getLastName();
            } else {
                solUserName = upmDetailResponse.getSolName();
            }
            String finalSolUserName = solUserName;

            Map<String, Object> params = new HashMap<>();
            params.put("solUserName", finalSolUserName);
            params.put("refNum", acaeTransferOptionRQ.getRefNum());
            params.put("accNo", acaeTransferOptionRQ.getAccNo());
            params.put("anticipatedDate", acaeTransferOptionRQ.getAnticipatedDate());
            StringBuilder sql1 = new StringBuilder();
            StringBuilder sql2 = new StringBuilder();

            sql1.append(" UPDATE CASV2_ACAE_SEARCH \n" +
                    "     SET SOL_USER_NAME=:solUserName\n" +
                    "    WHERE REF_NUM=:refNum ");

            sql2.append(" UPDATE CASV2_ACAE_STATUS_TABLE\n" +
                    "  SET NEG_DATE=:anticipatedDate \n" +
                    "  WHERE REF_NUM=:refNum\n" +
                    "  AND ACCT_NO=:accNo ");

            int executed = namedParameterJdbcTemplate.update(sql1.toString(), params);
//            int executed = namedParameterJdbcTemplate.update(SQL2.toString(),params);

            if (executed == 1) {
                isSuccess = true;
            } else {
                isSuccess = false;
            }

            LOG.info("END : Transfer ACAE Paper Repository: {}", acaeTransferOptionRQ);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
            throw e;
        }
        return isSuccess;
    }

    public List<ACAETransferACAERecordDTO> getACAETransferACAERecordsRepository(String refNum) {
        List<ACAETransferACAERecordDTO> resultSet = new ArrayList<>();
        try {
            Map<String, Object> params = new HashMap<>();

            params.put("refNum", refNum);

            StringBuilder sql = new StringBuilder();

            final Integer[] tranCountNo = {0};

            sql.append(" SELECT ACCT_NO,ACCT_NAME,CURRENT_USER FROM TABLE(CAST(ACAE_OPERATIONS.GET_TRANSFER_OPTION_ACAE_RECORDS(:refNum) AS ACAE_TRANSFER_RECORDS)) ");
            namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<ACAETransferACAERecordDTO>>() {
                @Override
                public List<ACAETransferACAERecordDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    while (rs.next()) {
                        ACAETransferACAERecordDTO acaeTransferACAERecordDTO = new ACAETransferACAERecordDTO();
                        acaeTransferACAERecordDTO.setRefNumber(refNum);
                        acaeTransferACAERecordDTO.setAccNo(rs.getString("ACCT_NO"));
                        acaeTransferACAERecordDTO.setAccName(rs.getString("ACCT_NAME"));
                        acaeTransferACAERecordDTO.setCurrentUser(rs.getString("CURRENT_USER"));
                        acaeTransferACAERecordDTO.setTranCountNo(tranCountNo[0]);
                        acaeTransferACAERecordDTO.setIsSelected(false);
                        tranCountNo[0] = tranCountNo[0] + 1;
                        resultSet.add(acaeTransferACAERecordDTO);
                    }
                    return resultSet;
                }
            });
            LOG.info("Get ACAE Transfer ACAE Records Repository Result {} ", resultSet);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return resultSet;
    }

}
