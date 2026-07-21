package com.itechro.cas.dao.acae.jdbc;

import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.acae.request.ACAEInquiryByDateRangeRQ;
import com.itechro.cas.model.dto.acae.request.ACAEInquiryByResubmittedRQ;
import com.itechro.cas.model.dto.acae.response.ACAEDateRangeInquiryDTO;
import com.itechro.cas.model.dto.acae.response.ACAEInquiryByResubmittedDTO;
import com.itechro.cas.model.dto.acae.response.ACAEInquiryBySolIdDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ACAEStatusInquiryJdbcDAO extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEStatusInquiryJdbcDAO.class);

    protected JdbcTemplate jdbcTemplate;
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ACAEStatusInquiryJdbcDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Page<ACAEDateRangeInquiryDTO> getInquiryByDateRangeRepository(ACAEInquiryByDateRangeRQ acaeInquiryByDateRangeRQ, String reqWorkClass) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT \n" +
                "  COUNT(ST.ACCT_NO) CNT,\n" +
                "  ST.CURRENT_USER,\n" +
                "  ST.ACAE_STATUS,\n" +
                "  SE.SOL_USER_ID,\n" +
                "  SE.ACAE_DATE,\n" +
                "  ST.RECEIVED_DATE\n" +
                "FROM CASV2_ACAE_STATUS_TABLE ST\n" +
                "INNER JOIN CASV2_ACAE_SEARCH SE\n" +
                "ON ST.REF_NUM                     = SE.REF_NUM\n" +
                "WHERE ST.ACAE_STATUS             IN ('1', '3', '14')\n" +
                "AND SE.ACAE_DATE BETWEEN TO_DATE(:fromDate, 'YYYY-MM-DD') AND TO_DATE(:toDate, 'YYYY-MM-DD')\n" +
                "GROUP BY \n" +
                "  ST.ACAE_STATUS,\n" +
                "  ST.CURRENT_USER,\n" +
                "  SE.SOL_USER_ID,\n" +
                "  SE.ACAE_DATE,\n" +
                "  ST.RECEIVED_DATE ");

        params.put("fromDate", acaeInquiryByDateRangeRQ.getFromDate());
        params.put("toDate", acaeInquiryByDateRangeRQ.getToDate());

        return getPagedData(sql.toString(), params, new RowMapper<ACAEDateRangeInquiryDTO>() {
            @Nullable
            @Override
            public ACAEDateRangeInquiryDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                ACAEDateRangeInquiryDTO acaeDateRangeInquiryDTO = new ACAEDateRangeInquiryDTO();
                acaeDateRangeInquiryDTO.setPaperCount(rs.getString("CNT"));
                acaeDateRangeInquiryDTO.setCurrentUser(rs.getString("CURRENT_USER"));
                acaeDateRangeInquiryDTO.setStatus(rs.getString("ACAE_STATUS"));
                acaeDateRangeInquiryDTO.setSolId(rs.getString("SOL_USER_ID"));
                acaeDateRangeInquiryDTO.setAcaeDate(rs.getString("ACAE_DATE"));
                acaeDateRangeInquiryDTO.setReceivedDate(rs.getString("RECEIVED_DATE"));
                return acaeDateRangeInquiryDTO;
            }
        }, acaeInquiryByDateRangeRQ);
    }

    public List<ACAEInquiryBySolIdDTO> getInquiryBySolIdsRepository(ArrayList solList) {
        List<ACAEInquiryBySolIdDTO> resultSet = new ArrayList<>();
        try {
            LOG.info("*** Number of SOL IDs : " + solList.size());
            if (!solList.isEmpty()) {
                solList.forEach((sol) -> {
                    Map<String, Object> params = new HashMap<>();

                    StringBuilder sql = new StringBuilder();

                    sql.append(" SELECT 'INCOMPLETED' STATUS, SOL_USER_ID, ACAE_DATE, ACAE_COUNT " +
                            "FROM CASV2_ACAE_SEARCH " +
                            "WHERE SOL_USER_ID = :SOL_USER_ID AND ACAE_CMPLETED ='N' " +
                            "UNION ALL " +
                            "SELECT 'COMPLETED' STATUS, SOL_USER_ID, ACAE_DATE, ACAE_COUNT " +
                            "FROM CASV2_ACAE_SEARCH " +
                            "WHERE SOL_USER_ID = :SOL_USER_ID " +
                            "AND ACAE_DATE     =( " +
                            "SELECT MAX(ACAE_DATE) " +
                            "  FROM CASV2_ACAE_SEARCH " +
                            "  WHERE SOL_USER_ID = :SOL_USER_ID AND ACAE_CMPLETED ='Y')");

                    String solId = sol.toString();
                    params.put("SOL_USER_ID", solId);

                    namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<ACAEInquiryBySolIdDTO>>() {
                        @Nullable
                        @Override
                        public List<ACAEInquiryBySolIdDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                            while (rs.next()) {
                                ACAEInquiryBySolIdDTO acaeInquiryBySolIdDTO = new ACAEInquiryBySolIdDTO();
                                acaeInquiryBySolIdDTO.setSolId(rs.getString("SOL_USER_ID"));
                                if (rs.getString("STATUS").equals("COMPLETED")) {
                                    acaeInquiryBySolIdDTO.setForwardByApprovalDate(
                                            rs.getDate("ACAE_DATE"));
                                    acaeInquiryBySolIdDTO.setForwardByApprovalCount(
                                            rs.getString("ACAE_COUNT"));
                                }
                                if (rs.getString("STATUS").equals("INCOMPLETED")) {
                                    acaeInquiryBySolIdDTO.setLastFetchedCount(
                                            rs.getString("ACAE_COUNT"));
                                    acaeInquiryBySolIdDTO.setLastFetchedDate(rs.getDate("ACAE_DATE"));
                                }
                                resultSet.add(acaeInquiryBySolIdDTO);
                            }
                            return resultSet;
                        }
                    });
                });
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return resultSet;
    }

    public Page<ACAEInquiryByResubmittedDTO> getInquiryByResubmittedRepository(ACAEInquiryByResubmittedRQ acaeInquiryByResubmittedRQ) {
        Page<ACAEInquiryByResubmittedDTO> resultSet = new Page<>();
        try {
            LOG.info("*** Get Inquiry By Resubmitted Repository : " + acaeInquiryByResubmittedRQ.getSolList().size());

            Map<String, Object> params = new HashMap<>();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT S.SOL_USER_ID,S.ACAE_DATE,ST.ACAE_STATUS,ST.ACCT_NO,ST.REF_NUM,ST.CURRENT_USER,B.VBAL_1,B.VBAL_2,B.VBAL_3,B.VBAL_4,B.VBAL_5,B.VBAL_6,B.VBAL_0,");
            sql.append("  (SELECT GET_MIN_VAL_FOR_A_WEEK(B.VBAL_0, B.VBAL_1, B.VBAL_2, B.VBAL_3, B.VBAL_4, B.VBAL_5, B.VBAL_6,");
            sql.append("  (SELECT NVL((CASV2_ACAE_AC_BAL.SANCTION_LIMIT + CASV2_ACAE_AC_BAL.TOD_BAL), 0) LMS");
            sql.append("  FROM CASV2_ACAE_AC_BAL");
            sql.append("  WHERE CASV2_ACAE_AC_BAL.REF_NUM = ST.REF_NUM");
            sql.append("  AND CASV2_ACAE_AC_BAL.ACCT_NO = ST.ACCT_NO)) AS bal");
            sql.append("  FROM dual) AS MIN_BAL");
            sql.append(" FROM CASV2_ACAE_SEARCH S");
            sql.append(" INNER JOIN CASV2_ACAE_STATUS_TABLE ST");
            sql.append(" ON S.REF_NUM = ST.REF_NUM");
            sql.append(" INNER JOIN CASV2_ACAE_DAY_END_BAL B");
            sql.append(" ON B.REF_NUM   = ST.REF_NUM");
            sql.append(" AND ST.ACCT_NO = B.ACCT_NO");
            sql.append(" INNER JOIN CASV2_ACAE_USERS_TABLE");
            sql.append(" ON CASV2_ACAE_USERS_TABLE.REF_NUM  = ST.REF_NUM");
            sql.append(" AND CASV2_ACAE_USERS_TABLE.ACCT_NO = ST.ACCT_NO");
            sql.append(" WHERE S.SOL_USER_ID IN ('");

            for (int i = 0; i < acaeInquiryByResubmittedRQ.getSolList().size(); i++) {
                if (i + 1 == acaeInquiryByResubmittedRQ.getSolList().size()) {
                    sql.append((String) acaeInquiryByResubmittedRQ.getSolList().get(i));
                } else {
                    sql.append((String) acaeInquiryByResubmittedRQ.getSolList().get(i)).append("','");
                }

            }
            sql.append("')");
            sql.append(" AND S.ACAE_DATE BETWEEN TO_DATE(:fromDate, 'YYYY-MM-DD') AND TO_DATE(:toDate, 'YYYY-MM-DD')");

            if (acaeInquiryByResubmittedRQ.getReportType().equals("current")) {
                sql.append("  AND ST.ACAE_STATUS LIKE '7'");
            } else {
                sql.append("  AND ST.ACAE_STATUS LIKE '%'");
            }
            sql.append(" AND CASV2_ACAE_USERS_TABLE.ACAE_STATUS = '7'");

            LOG.info("SQL: {} ", sql);

            params.put("toDate", acaeInquiryByResubmittedRQ.getToDate());
            params.put("fromDate", acaeInquiryByResubmittedRQ.getFromDate());

            return getPagedData(sql.toString(), params, new RowMapper<ACAEInquiryByResubmittedDTO>() {
                @Nullable
                @Override
                public ACAEInquiryByResubmittedDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                    ACAEInquiryByResubmittedDTO acaeInquiryByResubmittedDTO = new ACAEInquiryByResubmittedDTO();
                    acaeInquiryByResubmittedDTO.setSolId(rs.getString("SOL_USER_ID"));
                    acaeInquiryByResubmittedDTO.setAccountNo(rs.getString("ACCT_NO"));
                    acaeInquiryByResubmittedDTO.setRefNo(rs.getString("REF_NUM"));
                    acaeInquiryByResubmittedDTO.setCurrentStatus(Integer.parseInt(rs.getString("ACAE_STATUS")));
                    acaeInquiryByResubmittedDTO.setCurrentUser(rs.getString("CURRENT_USER"));
                    acaeInquiryByResubmittedDTO.setMaxOdForWeek(rs.getString("MIN_BAL"));
                    acaeInquiryByResubmittedDTO.setAcaeDate(rs.getString("ACAE_DATE"));
                    return acaeInquiryByResubmittedDTO;

                }
            }, acaeInquiryByResubmittedRQ);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return resultSet;
    }
}
