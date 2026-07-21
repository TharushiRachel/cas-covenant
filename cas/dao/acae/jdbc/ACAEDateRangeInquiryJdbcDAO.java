package com.itechro.cas.dao.acae.jdbc;

import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.dto.acae.response.ACAESearchByStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ACAEDateRangeInquiryJdbcDAO extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEDateRangeInquiryJdbcDAO.class);
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public ACAEDateRangeInquiryJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ACAESearchByStatusDTO> getACAEDateRangeInquiryRepository(String solId, Date fromDate, Date toDate) {

        List<ACAESearchByStatusDTO> acaeSearchByStatusDTOList = new ArrayList<>();

        try {
            LOG.info("START : ACAE Date Range Inquiry Repository" + solId +
                    " ," + new java.sql.Date(fromDate.getTime()) + " ," + new java.sql.Date(toDate.getTime()));

            StringBuilder sql = new StringBuilder();

            final Integer[] recordIndex = {0};

            Map<String, Object> params = new HashMap<>();
            params.put("solId", solId);
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);

            sql.append("  SELECT REF_NUM, ACCT_NO, ACCT_NAME, ATTENDED, RECEIVED_DATE, SOL_USER_ID, SOL_USER_NAME ");
            sql.append(" FROM TABLE(CAST(ACAE_OPERATIONS.GET_ACAE_LIST_BY_DATE_RANGE(:solId, :fromDate,:toDate) AS ACAE_REFERENCES)) ");

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
                    acaeSearchByStatusDTO.setSolUserId(rs.getString("SOL_USER_ID"));
                    acaeSearchByStatusDTO.setRecordIndex(recordIndex[0]);
                    recordIndex[0]++;
                    return acaeSearchByStatusDTO;
                }
            });

            LOG.info("END : ACAE Date Range Inquiry Repository : {} ", acaeSearchByStatusDTOList);

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return acaeSearchByStatusDTOList;
    }
}
