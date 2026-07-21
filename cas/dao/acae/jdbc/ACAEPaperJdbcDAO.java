package com.itechro.cas.dao.acae.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.acae.request.*;
import com.itechro.cas.model.dto.acae.response.*;
import com.itechro.cas.model.dto.casmaster.UserDaDTO;
import com.itechro.cas.model.dto.integration.response.*;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@Repository
public class ACAEPaperJdbcDAO extends BaseJDBCDao {

    protected NamedParameterJdbcTemplate namedJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    @Value("${apps.integration.load.acae.status.url}")
    private String acaeStatusDetailsURL;

    @Value("${apps.integration.load.acae.status.enable}")
    private boolean acaeStatusDetailsEnable;

    @Value("${apps.integration.load.branch.list.url}")
    private String loadBranchListUrl;

    @Value("${apps.integration.upm.detail.by.user.id.and.app.code.url}")
    private String umpDetailByUserIdAndAppCodeUrl;

    @Value("${apps.cas.application.code}")
    private String appCode;

    @Autowired
    public ACAEPaperJdbcDAO(NamedParameterJdbcTemplate namedJdbcTemplate, JdbcTemplate jdbcTemplate, EntityManager entityManager) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    private static final Logger LOG = LoggerFactory.getLogger(ACAEPaperJdbcDAO.class);

    public ACAECustomerDetailsDTO getCustomerDetailsRepository(ACAECustomerDetailsRQ acaeCustomerDetailsRQ, CredentialsDTO credentialsDTO) {
        ACAECustomerDetailsDTO acaeCustomerDetailsDTO = new ACAECustomerDetailsDTO();
        try {
            String accountNumber = acaeCustomerDetailsRQ.getAccountNo();
            String referenceNumber = acaeCustomerDetailsRQ.getRefId();

            Map<String, Object> params = new HashMap<>();

            params.put("accountNumber", accountNumber);
            params.put("referenceNumber", referenceNumber);
            //-------------- get rest customer details ----------------------------

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT CAS.ACAE_DATE,\n" +
                    "    CAST.NEG_DATE,\n" +
                    "    CACD.RISK_GRADE,\n" +
                    "    CACD.CLASSIFICATION_STATUS,\n" +
                    "    CACD.CLASSIFICATION_DATE,\n" +
                    "    CACD.CLASSIFICATION_DAYS,\n" +
                    "    CACD.WATCH_LISTED\n" +
                    "FROM \n" +
                    "    CASV2_ACAE_SEARCH CAS\n" +
                    "INNER JOIN \n" +
                    "    CASV2_ACAE_STATUS_TABLE CAST\n" +
                    "    ON CAS.REF_NUM = CAST.REF_NUM\n" +
                    "INNER JOIN\n" +
                    "    CASV2_ACAE_CUSTOMER_DETAILS CACD\n" +
                    "    ON CAST.REF_NUM = CACD.REF_NUM\n" +
                    "WHERE \n" +
                    "    CAS.REF_NUM = :referenceNumber\n" +
                    "    AND CAST.ACCT_NO = :accountNumber\n" +
                    "    AND CACD.ACCT_NO = :accountNumber");

            acaeCustomerDetailsDTO = namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<ACAECustomerDetailsDTO>() {
                @Override
                public ACAECustomerDetailsDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

                    ACAECustomerDetailsDTO acaeCustomerDetailsDTO = null;
                    if (rs.next()) {
                        acaeCustomerDetailsDTO = new ACAECustomerDetailsDTO();
                        acaeCustomerDetailsDTO.setReviewingDate(rs.getDate("ACAE_DATE"));
                        acaeCustomerDetailsDTO.setNegDate(rs.getDate("NEG_DATE"));
                        acaeCustomerDetailsDTO.setRiskGrade(rs.getString("RISK_GRADE"));
                        acaeCustomerDetailsDTO.setClassificationStatus(rs.getString("CLASSIFICATION_STATUS"));
                        acaeCustomerDetailsDTO.setClassificationDate(new java.sql.Date(rs.getDate("CLASSIFICATION_DATE").getTime()));
                        acaeCustomerDetailsDTO.setClassificationDays(rs.getInt("CLASSIFICATION_DAYS"));
                        acaeCustomerDetailsDTO.setWatchListed(rs.getString("WATCH_LISTED"));
                    }
                    return acaeCustomerDetailsDTO;
                }
            });
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
            e.printStackTrace();
        }
        return acaeCustomerDetailsDTO;
    }

    public String getFinacleIdFromAccountNumberRepository(String accountNumber) {
        String finacleId = null;
        try {
            Map<String, Object> params = new HashMap<>();
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT DISTINCT cus.CUSTOMER_FINANCIAL_ID ");
            sql.append(" FROM T_CUSTOMER cus, T_CUSTOMER_BANK_DETAILS cusb");
            sql.append(" WHERE cus.CUSTOMER_ID = cusb.CUSTOMER_ID ");
            sql.append(" AND cusb.BANK_ACCOUNT_NUMBER =:accountNumber");

            params.put("accountNumber", accountNumber);
            finacleId = namedParameterJdbcTemplate.queryForObject(sql.toString(), params, String.class);

        } catch (EmptyResultDataAccessException e) {
            LOG.info("No data found for account number: " + accountNumber);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return finacleId;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Object getAccountBalanceDetailsByRefNoAndAccountNoRepository(ACAECustomerDetailsRQ acaeCustomerDetailsRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Account detail by customer account number: {} by {}", acaeCustomerDetailsRQ, credentialsDTO);
        boolean acaeStatusDetailsEnable = this.acaeStatusDetailsEnable;
        Object result = null;

        if (acaeStatusDetailsEnable) {
            String url = this.acaeStatusDetailsURL;
            RestTemplate restTemplate = new RestTemplate();
            try {
                result = restTemplate.postForObject(url, acaeCustomerDetailsRQ, Object.class);
                LOG.info("INFO : result ==> :{}", result);
                LOG.info("END: Get Account detail by customer account number: {} by {}", result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: Error occur while loading Account detail url :{}: {}: {} by {}", url, acaeCustomerDetailsRQ, result, credentialsDTO, e);
            }
        } else {
            LOG.warn("WARN: Account detail loading by account number service is disabled {} by {}", acaeCustomerDetailsRQ, credentialsDTO);
        }
        return result;
    }

    public List<ACAEUserCommentsDTO> getACAEUserCommentRepository(ACAEUserCommentsRQ acaeUserCommentsRQ, CredentialsDTO credentialsDTO) {
        List<ACAEUserCommentsDTO> resultSet = new ArrayList<>();
        List<ACAEUserCommentsDTO> acaeUserCommentList = new ArrayList<>();
        try {
            String accountNumber = acaeUserCommentsRQ.getAccountNumber();
            String referenceNumber = acaeUserCommentsRQ.getReferenceNumber();
            String fromDate = acaeUserCommentsRQ.getFromDate();
            String toDate = acaeUserCommentsRQ.getToDate();

            Map<String, Object> params = new HashMap<>();
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT cd.ACCT_NAME,\n" +
                    "  TO_CHAR(c.REMARD_DATE, 'yyyy-mm-dd hh24:mi:ss') AS REMARD_DATE_1,\n" +
                    "  c.REMARK_1,\n" +
                    "  c.REG_DATE,\n" +
                    "  c.REM_STATUS,\n" +
                    "  c.ACCT_NO,\n" +
                    "  c.REF_NUM,\n" +
                    "  c.IS_ACTIVE,\n" +
                    "  c.USER_ID\n" +
                    " FROM CASV2_ACAE_COMMENTS c,\n" +
                    "  CASV2_ACAE_CUSTOMER_DETAILS cd\n" +
                    "WHERE cd.ACCT_NO = c.ACCT_NO\n" +
                    "AND c.REF_NUM    = cd.REF_NUM\n" +
                    "AND c.IS_ACTIVE  ='0'\n" +
                    "AND c.ACCT_NO    =:accountNumber\n" +
                    "AND c.REF_NUM    =:referenceNumber \n" +
                    "AND TO_CHAR(c.REMARD_DATE, 'yyyy-mm-dd hh24:mi:ss') IS NOT NULL\n" +
                    "ORDER BY REMARD_DATE_1 DESC ");

            params.put("referenceNumber", referenceNumber);
            params.put("accountNumber", accountNumber);
            params.put("toDate", toDate);
            params.put("fromDate", fromDate);

            List<ACAEUserCommentsDTO> resultMap = namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<ACAEUserCommentsDTO>>() {
                @Nullable
                @Override
                public List<ACAEUserCommentsDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    while (rs.next()) {
                        ACAEUserCommentsDTO acaeUserCommentsDTO = new ACAEUserCommentsDTO();
                        acaeUserCommentsDTO.setAccName(rs.getString("ACCT_NAME"));
                        acaeUserCommentsDTO.setRemardDate(rs.getString("REMARD_DATE_1"));
                        acaeUserCommentsDTO.setRemark(rs.getString("REMARK_1"));
                        acaeUserCommentsDTO.setRegDate(rs.getDate("REG_DATE"));
                        acaeUserCommentsDTO.setUserId(rs.getString("USER_ID"));
                        if (rs.getString("REM_STATUS").equals("1")) {
                            acaeUserCommentsDTO.setRemStatus("Forwarded");
                        } else if (rs.getString("REM_STATUS").equals("3")) {
                            acaeUserCommentsDTO.setRemStatus("Returned");
                        } else if (rs.getString("REM_STATUS").equals("4")) {
                            acaeUserCommentsDTO.setRemStatus("Approved");
                        } else if (rs.getString("REM_STATUS").equals("6")) {
                            acaeUserCommentsDTO.setRemStatus("Transferred");
                        } else if (rs.getString("REM_STATUS").equals("7")) {
                            acaeUserCommentsDTO.setRemStatus("To Be Resubmit");
                        }
                        acaeUserCommentsDTO.setAccNo(rs.getString("ACCT_NO"));
                        acaeUserCommentsDTO.setRefNo(rs.getString("REF_NUM"));
                        resultSet.add(acaeUserCommentsDTO);
                    }
                    return resultSet;
                }
            });

            if (resultMap.size() > 0) {
                resultMap.forEach((rec) -> {
                    ACAEUserCommentsDTO acaeUserCommentsDTO = new ACAEUserCommentsDTO();
                    String url = this.umpDetailByUserIdAndAppCodeUrl.replace("{userId}", rec.getUserId())
                            .replace("{appCode}", appCode);
                    LOG.info("url:  {}", url);
                    RestTemplate restTemplate = new RestTemplate();
                    ObjectMapper objectMapper = new ObjectMapper();
                    UpmDetailLoadRS result = null;
                    UpmDetailResponse detailResponse = null;
                    String responseStr = null;
                    try {
                        responseStr = restTemplate.getForObject(url, String.class);
                        result = objectMapper.readValue(responseStr, UpmDetailLoadRS.class);
                        detailResponse = new UpmDetailResponse(result);
                        detailResponse.setSuccess(true);
                        LOG.info("END : Get Upm detail by userID and appCode url : {} response : {}", url, result);
                    } catch (Exception e) {
                        LOG.error("ERROR: Response String  {}", responseStr);
                        LOG.error("ERROR: Error occur while loading Upm detail by userID and appCode url :{}: {}: {}", url, rec, result, e);
                    }
                    acaeUserCommentsDTO.setUserId(rec.getUserId());
                    acaeUserCommentsDTO.setFirstName(detailResponse.getFirstName());
                    acaeUserCommentsDTO.setLastName(detailResponse.getLastName());
                    acaeUserCommentsDTO.setRemardDate(rec.getRemardDate());
                    acaeUserCommentsDTO.setRemark(rec.getRemark());
                    acaeUserCommentsDTO.setRegDate(rec.getRegDate());
                    acaeUserCommentsDTO.setUserId(rec.getUserId());
                    acaeUserCommentsDTO.setAccNo(rec.getAccNo());
                    acaeUserCommentsDTO.setRefNo(rec.getRefNo());
                    acaeUserCommentsDTO.setRemStatus(rec.getRemStatus());
                    acaeUserCommentList.add(acaeUserCommentsDTO);
                });
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return acaeUserCommentList;
    }

    public List<ACAEBalanceAfterPaymentDTO> getACAEBalanceAfterPaymentRepository(ACAEBalanceAfterPaymentRQ acaeBalanceAfterPaymentRQ, CredentialsDTO credentialsDTO) {
        List<ACAEBalanceAfterPaymentDTO> resultSet = new ArrayList<>();
        List<ACAEBalanceAfterPaymentDTO> acaeBalanceAfterPaymentDTOList = new ArrayList<>();
        try {
            Map<String, Object> params = new HashMap<>();
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT VDATE_1,\n" +
                    "  VBAL_1,\n" +
                    "  VDATE_2, \n" +
                    "  VBAL_2, \n" +
                    "  VDATE_3, \n" +
                    "  VBAL_3, \n" +
                    "  VDATE_4, \n" +
                    "  VBAL_4, \n" +
                    "  VDATE_5, \n" +
                    "  VBAL_5, \n" +
                    "  VDATE_6, \n" +
                    "  VBAL_6, \n" +
                    "  REF_NUM, \n" +
                    "  ACCT_NO \n" +
                    " FROM CASV2_ACAE_DAY_END_BAL \n" +
                    " WHERE REF_NUM=:referenceNumber \n" +
                    " AND ACCT_NO=:accountNumber ");

            params.put("referenceNumber", acaeBalanceAfterPaymentRQ.getReferenceNumber());
            params.put("accountNumber", acaeBalanceAfterPaymentRQ.getAccountNumber());

            acaeBalanceAfterPaymentDTOList = namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<ACAEBalanceAfterPaymentDTO>>() {
                @Nullable
                @Override
                public List<ACAEBalanceAfterPaymentDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    while (rs.next()) {
                        ACAEBalanceAfterPaymentDTO acaeBalanceAfterPaymentDTO = new ACAEBalanceAfterPaymentDTO();
                        acaeBalanceAfterPaymentDTO.setVDATE_1(rs.getString("VDATE_1"));
                        acaeBalanceAfterPaymentDTO.setVBAL_1(rs.getDouble("VBAL_1"));
                        acaeBalanceAfterPaymentDTO.setVDATE_2(rs.getString("VDATE_2"));
                        acaeBalanceAfterPaymentDTO.setVBAL_2(rs.getDouble("VBAL_2"));
                        acaeBalanceAfterPaymentDTO.setVDATE_3(rs.getString("VDATE_3"));
                        acaeBalanceAfterPaymentDTO.setVBAL_3(rs.getDouble("VBAL_3"));
                        acaeBalanceAfterPaymentDTO.setVDATE_4(rs.getString("VDATE_4"));
                        acaeBalanceAfterPaymentDTO.setVBAL_4(rs.getDouble("VBAL_4"));
                        acaeBalanceAfterPaymentDTO.setVDATE_5(rs.getString("VDATE_5"));
                        acaeBalanceAfterPaymentDTO.setVBAL_5(rs.getDouble("VBAL_5"));
                        acaeBalanceAfterPaymentDTO.setVDATE_6(rs.getString("VDATE_6"));
                        acaeBalanceAfterPaymentDTO.setVBAL_6(rs.getDouble("VBAL_6"));
                        resultSet.add(acaeBalanceAfterPaymentDTO);
                    }
                    return resultSet;
                }
            });
            LOG.info("END : Get ACAE Balance After Payment Repository: {} , user {}", acaeBalanceAfterPaymentDTOList, credentialsDTO.getUserID());
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return acaeBalanceAfterPaymentDTOList;
    }

    /*
     * Method to save comment  manager level.
     * */
    @Transactional
    public String saveACAECommentRepository(ACAECommentRQ acaeCommentRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START : save ACAE Comment Repository : {} : {}", acaeCommentRQ, credentialsDTO.getUserID());
        String message = "";
        try {

            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.SAVE_REMARK");

            query.registerStoredProcedureParameter("STRING_REFERENCE_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_ACCT_NO", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_REMARK", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("DATE_NEG_DATE", java.util.Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("DATE_PRE_NEG_DATE", java.util.Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("STRING_REFERENCE_ID", acaeCommentRQ.getReferenceNumber());
            query.setParameter("STRING_ACCT_NO", acaeCommentRQ.getAccountNumber());
            query.setParameter("STRING_REMARK", acaeCommentRQ.getActiveComment());
            query.setParameter("DATE_NEG_DATE", acaeCommentRQ.getNegDate());
            query.setParameter("DATE_PRE_NEG_DATE", acaeCommentRQ.getPreviousNegDate() != null ?
                    acaeCommentRQ.getPreviousNegDate() : new java.util.Date());
            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("SAVE_REMARK PROC Failed");
            }

            StringBuilder sql = new StringBuilder();
            Map<String, Object> params = new HashMap<>();

            sql.append("UPDATE CASV2_ACAE_CUSTOMER_DETAILS");
            sql.append(" SET ATTENDED = :attended");
            sql.append(" WHERE REF_NUM = :referenceNumber AND ACCT_NO = :accountNumber");

            if (acaeCommentRQ.getActiveComment() != null && !acaeCommentRQ.getActiveComment().isEmpty()) {
                params.put("attended", "1");
            } else {
                params.put("attended", "0");
            }
            params.put("referenceNumber", acaeCommentRQ.getReferenceNumber());
            params.put("accountNumber", acaeCommentRQ.getAccountNumber());

            message = String.valueOf(namedParameterJdbcTemplate.update(String.valueOf(sql), params));

            LOG.info("***** End of  saveACAECommentRepository" + message);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return message;
    }

    /*
     * Method to save comment above manager level.
     * */
    @Transactional
    public String saveACAECommentOnlyRepository(ACAECommentRQ acaeCommentRQ, CredentialsDTO credentialsDTO) {
        String message = "";
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.SAVE_REMARK_ABV_MGR");

            query.registerStoredProcedureParameter("STRING_REFERENCE_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_ACCT_NO", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_REMARK", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("STRING_REFERENCE_ID", acaeCommentRQ.getReferenceNumber());
            query.setParameter("STRING_ACCT_NO", acaeCommentRQ.getAccountNumber());
            query.setParameter("STRING_REMARK", acaeCommentRQ.getActiveComment());
            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("SAVE_REMARK_ABV_MGR PROC Failed", AppsConstants.ResponseStatus.FAILED, outStatus);
            }
            message = "SUCCESS";
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return message;
    }

    public String getActiveCommentRepository(ACAEUserCommentsRQ acaeUserCommentsRQ, CredentialsDTO credentialsDTO) {
        String finalComment = "";
        try {
            String comment = "";
            StringBuilder SQL = new StringBuilder();
            Map<String, Object> params = new HashMap<>();
            String accountNumber = acaeUserCommentsRQ.getAccountNumber();
            String referenceNumber = acaeUserCommentsRQ.getReferenceNumber();
            SQL.append("SELECT REMARK_1 \n" +
                    " FROM CASV2_ACAE_COMMENTS \n" +
                    " WHERE REF_NUM=:referenceNumber \n" +
                    "  AND ACCT_NO=:accountNumber \n" +
                    " AND IS_ACTIVE='1' ");
            params.put("referenceNumber", referenceNumber);
            params.put("accountNumber", accountNumber);

            comment = jdbcTemplate.queryForObject(SQL.toString(), String.class, referenceNumber, accountNumber);
            finalComment = comment == null ? "-" : comment;
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
            finalComment = "-";
        }
        return finalComment;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Boolean approveACAEPaperRepository(ACAEPaperApproveRQ acaePaperApproveRQ, CredentialsDTO credentialsDTO) throws AppsException {
        boolean isSuccess = false;
        try {
            Double sanctionLimit = Double.parseDouble(acaePaperApproveRQ.getSanctionLimit());

            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.APPROVE_PAPER");

            query.registerStoredProcedureParameter("STRING_REFERENCE_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_ACCT_NO", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_THIS_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IN_USER_NAME", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IN_SANCTION_LIMIT", Double.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IN_CURRENT_USER_NAME", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("STRING_REFERENCE_ID", acaePaperApproveRQ.getReferenceNumber());
            query.setParameter("STRING_ACCT_NO", acaePaperApproveRQ.getAccountNumber());
            query.setParameter("STRING_THIS_USER_ID", credentialsDTO.getUserID().toString());
            query.setParameter("IN_USER_NAME", credentialsDTO.getUserName());
            query.setParameter("IN_SANCTION_LIMIT", sanctionLimit);
            query.setParameter("IN_CURRENT_USER_NAME", acaePaperApproveRQ.getCurrentUsername());
            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("APPROVE_PAPER PROC Failed", AppsConstants.ResponseStatus.FAILED, outStatus);
            }
            isSuccess = true;
        } catch (AppsException ex) {
            LOG.error("ERROR : ", ex);
            throw ex;
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSuccess;
    }

    @Transactional
    public Boolean transferACAEPaperRepository(ACAEPaperTransferRQ acaePaperTransferRQ) {
        boolean isSuccess = false;
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.FORWARD_PAPER");

            query.registerStoredProcedureParameter("STRING_REFERENCE_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_ACCT_NO", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_NEXT_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_THIS_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("NUMBER_TRAN_STATUS", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IN_CURRENT_USER_NAME", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("STRING_REFERENCE_ID", acaePaperTransferRQ.getSearchReference());
            query.setParameter("STRING_ACCT_NO", acaePaperTransferRQ.getAccountNumber());
            query.setParameter("STRING_NEXT_USER_ID", acaePaperTransferRQ.getNextUser());
            query.setParameter("STRING_THIS_USER_ID", acaePaperTransferRQ.getThisUser());
            query.setParameter("NUMBER_TRAN_STATUS", acaePaperTransferRQ.getStatus());
            query.setParameter("IN_CURRENT_USER_NAME", acaePaperTransferRQ.getCurrentUsername());

            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("FORWARD_PAPER PROC Failed", AppsConstants.ResponseStatus.FAILED, outStatus);
            }
            isSuccess = true;
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSuccess;
    }

    @Transactional
    public Boolean forwardACAEBatchRepository(ACAEBatchForwardRQ acaeBatchForwardRQ, CredentialsDTO credentialsDTO) {
        boolean isSuccess = false;
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.FORWARD_BATCH");

            query.registerStoredProcedureParameter("STRING_REFERENCE_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_NEXT_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_THIS_USER_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("NUMBER_TRAN_STATUS", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_SOL_USER_NAME", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("IN_CURRENT_USER_NAME", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("STRING_REFERENCE_ID", acaeBatchForwardRQ.getReferenceId());
            query.setParameter("STRING_NEXT_USER_ID", acaeBatchForwardRQ.getNextUser());
            query.setParameter("STRING_THIS_USER_ID", acaeBatchForwardRQ.getThisUser());
            query.setParameter("NUMBER_TRAN_STATUS", acaeBatchForwardRQ.getStatus());
            query.setParameter("STRING_SOL_USER_NAME", acaeBatchForwardRQ.getSolUserName());
            query.setParameter("IN_CURRENT_USER_NAME", acaeBatchForwardRQ.getNextUserName());

            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("FORWARD_BATCH PROC Failed", AppsConstants.ResponseStatus.FAILED, outStatus);
            }
            isSuccess = true;

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSuccess;
    }

    public Boolean isACAEAttendedRepository(ACAEAttendedRQ attendedRQ) {
        boolean hasAttended = false;
        try {
            LOG.info("*****  is ACAE Attended Repository" + attendedRQ);

            String result = "";
            StringBuilder SQL = new StringBuilder();
            String accountNumber = attendedRQ.getAccountNumber();
            String referenceNumber = attendedRQ.getReferenceNumber();

            SQL.append("SELECT ATTENDED " +
                    " FROM CASV2_ACAE_CUSTOMER_DETAILS " +
                    " WHERE REF_NUM=:referenceNumber " +
                    " AND ACCT_NO=:accountNumber ");

            result = jdbcTemplate.queryForObject(SQL.toString(), String.class, referenceNumber, accountNumber);
            LOG.info("*****  result" + result);
            if (result.equals("0")) {
                hasAttended = false;
            } else {
                hasAttended = true;
            }
            LOG.info("*****  hasAttended" + hasAttended);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return hasAttended;
    }

    public Boolean initializeReviewRepository(ACAEInitializeReviewRQ acaeInitializeReviewRQ, CredentialsDTO credentialsDTO) {
        boolean success = false;
        int updateCount = 0;
        try {
            StringBuilder SQL1 = new StringBuilder();
            String accountNumber = acaeInitializeReviewRQ.getAccountNumber();
            String referenceNumber = acaeInitializeReviewRQ.getReferenceNumber();
            String currentUser = acaeInitializeReviewRQ.getCurrentUser();

            SQL1.append(" INSERT INTO CASV2_ACAE_USERS_TABLE " +
                    " (REF_NUM,ACCT_NO,CURRENT_USER,ACAE_STATUS) " +
                    " VALUES(:referenceNumber,:accountNumber,:currentUser,'14')");
            updateCount += jdbcTemplate.queryForObject(SQL1.toString(), Integer.class, referenceNumber, accountNumber, currentUser);

            StringBuilder SQL2 = new StringBuilder();

            SQL2.append(" UPDATE CASV2_ACAE_COMMENTS " +
                    " SET USER_ID=:currentUser WHERE REF_NUM=:referenceNumber AND ACCT_NO=:accountNumber");

            updateCount += jdbcTemplate.queryForObject(SQL2.toString(), Integer.class, referenceNumber, accountNumber, currentUser);

            StringBuilder SQL3 = new StringBuilder();
            //need to get day end bal details from finacle
            SQL3.append("INSERT INTO CASV2_ACAE_DAY_END_BAL " +
                    " (REF_NUM,ACCT_NO,VDATE_1,VBAL_1,VDATE_2,VBAL_2,VDATE_3,VBAL_3," +
                    " VDATE_4,VBAL_4,VDATE_5,VBAL_5,VDATE_6,VBAL_6,VDATE_0,VBAL_0) " +
                    " VALUES (:referenceNumber,:accountNumber,:date1Str,:date1Bal,:date2Str,:date2Bal,:date3Str," +
                    " :date3Bal,:date4Str,:date4Bal,:date5Str,date5Bal,date6Str,date6Bal,date0Str,date0Bal)");

            updateCount += jdbcTemplate.queryForObject(SQL3.toString(), Integer.class, referenceNumber, accountNumber, currentUser);

            StringBuilder SQL4 = new StringBuilder();

            SQL4.append("UPDATE CASV2_ACAE_CUSTOMER_DETAILS " +
                    " SET RISK_GRADE =:riskGrade,CLASS_ID =:classificationId" +
                    " WHERE REF_NUM =:referenceNumber AND ACCT_NO =:accountNumber");

            updateCount += jdbcTemplate.queryForObject(SQL4.toString(), Integer.class, referenceNumber, accountNumber);

            StringBuilder SQL5 = new StringBuilder();

            SQL5.append("UPDATE CASV2_ACAE_AC_BAL " +
                    " SET CLEAR_BALANCE=?,SANCTION_LIMIT=?,TOD_BAL=?,UAL_BAL=?," +
                    " POST_DATED_CHQS=?,DEBIT_BALANCE=? WHERE REF_NUM=:referenceNumber AND ACCT_NO=:accountNumber");

            updateCount += jdbcTemplate.queryForObject(SQL5.toString(), Integer.class, referenceNumber, accountNumber);

            if (updateCount > 4) {
                success = true;
            } else {
                success = false;
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return success;
    }

    public Object getClassificationDataRepository(ACAECustomerDetailsRQ acaeCustomerDetailsRQ, CredentialsDTO credentialsDTO) {
        return new Object();
    }

    public List<ACAEPreviousUserDTO> getPreviousUsersRepository(ACAEPreviousUserRQ acaePreviousUserRQ, CredentialsDTO credentialsDTO) {
        List<ACAEPreviousUserDTO> acaePreviousUserDTOList = new ArrayList<>();
        List resultArray = new ArrayList<>();
        try {
            Map<String, Object> params = new HashMap<>();
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT DISTINCT PRE_USER " +
                    " FROM CASV2_ACAE_USERS_TABLE " +
                    " WHERE REF_NUM=:referenceNumber " +
                    " AND ACCT_NO=:accountNumber " +
                    " AND PRE_USER!=:userId ");

            params.put("referenceNumber", acaePreviousUserRQ.getSearchReference());
            params.put("accountNumber", acaePreviousUserRQ.getAccountNumber());
            params.put("userId", acaePreviousUserRQ.getThisUser());

            List<ACAEPreviousUserDTO> resultMap = namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<ACAEPreviousUserDTO>>() {
                @Nullable
                @Override
                public List<ACAEPreviousUserDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    while (rs.next()) {
                        ACAEPreviousUserDTO acaePreviousUserDTO = new ACAEPreviousUserDTO();
                        acaePreviousUserDTO.setId(rs.getString("PRE_USER"));
                        acaePreviousUserDTO.setFirstName("");
                        acaePreviousUserDTO.setLastName("");
                        resultArray.add(acaePreviousUserDTO);
                    }
                    return resultArray;
                }
            });

            if (resultMap != null) {
                resultMap.forEach((res) -> {
                    ACAEPreviousUserDTO acaePreviousUserDTO = new ACAEPreviousUserDTO();
                    String url = this.umpDetailByUserIdAndAppCodeUrl.replace("{userId}", res.getId())
                            .replace("{appCode}", appCode);
                    RestTemplate restTemplate = new RestTemplate();
                    ObjectMapper objectMapper = new ObjectMapper();
                    UpmDetailLoadRS result = null;
                    UpmDetailResponse detailResponse = null;
                    String responseStr = null;
                    try {
                        responseStr = restTemplate.getForObject(url, String.class);
                        LOG.info("INFO : Response String  {}", responseStr);
                        result = objectMapper.readValue(responseStr, UpmDetailLoadRS.class);
                        detailResponse = new UpmDetailResponse(result);
                        detailResponse.setSuccess(true);
                        LOG.info("END : Get Upm detail by userID and appCode url : {} response : {}", url, result);
                    } catch (Exception e) {
                        LOG.error("ERROR: Response String  {}", responseStr);
                        LOG.error("ERROR: Error occur while loading Upm detail by userID and appCode url :{}: {}: {}", url, res, result, e);
                    }
                    acaePreviousUserDTO.setId(res.getId());
                    acaePreviousUserDTO.setFirstName(detailResponse.getFirstName());
                    acaePreviousUserDTO.setLastName(detailResponse.getLastName());
                    acaePreviousUserDTOList.add(acaePreviousUserDTO);
                });
            }


        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return acaePreviousUserDTOList;
    }

    public Integer isAvailableCLeanBalance(String userName) throws AppsException {
        LOG.info("START : Is Available CLean Balance {}", userName);
        Integer count = 0;
        try {
            StringBuilder SQL = new StringBuilder();

            SQL.append(" SELECT count(uda.CLEAN_AMOUNT)\n" +
                    "FROM T_USER_DA uda\n" +
                    "WHERE uda.USER_NAME    = :userName\n" +
                    "AND uda.STATUS         = 'ACT'\n" +
                    "AND uda.APPROVE_STATUS = 'APPROVED'");

            count = jdbcTemplate.queryForObject(SQL.toString(), Integer.class, userName);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
            throw new AppsException("This User Need to assign Clean Amount for approve!");
        }
        return count;
    }

    public String getDAClearBalanceRepository(String userName) throws AppsException {
        LOG.info("START : Get DA Clear Balance Repository user {}", userName);
        String result = null;
        Double clearBalance = 0.0;
        try {
            StringBuilder SQL = new StringBuilder();

            SQL.append(" SELECT uda.CLEAN_AMOUNT\n" +
                    "FROM T_USER_DA uda\n" +
                    "WHERE uda.USER_NAME    = :userName\n" +
                    "AND uda.STATUS         = 'ACT'\n" +
                    "AND uda.APPROVE_STATUS = 'APPROVED'\n" +
                    "AND uda.APPROVED_DATE  =\n" +
                    "  (SELECT MAX(udb.APPROVED_DATE)\n" +
                    "  FROM T_USER_DA udb\n" +
                    "  WHERE udb.USER_NAME    = :userName\n" +
                    "  AND udb.STATUS         = 'ACT'\n" +
                    "  AND udb.APPROVE_STATUS = 'APPROVED')");

            clearBalance = jdbcTemplate.queryForObject(SQL.toString(), Double.class, userName, userName);
            BigDecimal bdValue = BigDecimal.valueOf(clearBalance);
            result = bdValue.toPlainString();

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
            throw new AppsException("Fetching Clean Amount for approve issue!");
        }
        return result;
    }

    public Boolean updateEscalationDaysRepository(ACAEPaperTransferRQ acaePaperTransferRQ) {
        boolean isUpdate = false;
        try {
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("ACAE_OPERATIONS.UPDATE_ESCALATION_DAYS");

            query.registerStoredProcedureParameter("NUMBER_NUM_DAY", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_REFERENCE_ID", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("STRING_ACCT_NO", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);

            query.setParameter("NUMBER_NUM_DAY", acaePaperTransferRQ.getNumOfDays());
            query.setParameter("STRING_REFERENCE_ID", acaePaperTransferRQ.getSearchReference());
            query.setParameter("STRING_ACCT_NO", acaePaperTransferRQ.getAccountNumber());
            query.execute();

            String outStatus = (String) query.getOutputParameterValue("OUT_STATUS");

            if (!outStatus.equals("SUCCESS")) {
                throw new AppsException("UPDATE_ESCALATION_DAYS PROC Failed", AppsConstants.ResponseStatus.FAILED, outStatus);
            }
            isUpdate = true;

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isUpdate;
    }

    public String getCurrentUserIdRepository(ACAEAccountTypeRQ acaeAccountTypeRQ) {
        String result = "";
        try {
            StringBuilder SQL = new StringBuilder();

            SQL.append("SELECT CURRENT_USER FROM CASV2_ACAE_STATUS_TABLE WHERE REF_NUM=:referenceNumber AND ACCT_NO=:accountNumber");
            String accountNumber = acaeAccountTypeRQ.getAccountNumber();
            String referenceNumber = acaeAccountTypeRQ.getRefNumber();

            result = jdbcTemplate.queryForObject(SQL.toString(), String.class, referenceNumber, accountNumber);
            LOG.info("result :{}", result);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
            throw new RuntimeException("Current user details not found!");
        }
        return result;
    }
}
