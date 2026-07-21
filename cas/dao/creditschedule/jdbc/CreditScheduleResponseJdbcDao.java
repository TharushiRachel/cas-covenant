package com.itechro.cas.dao.creditschedule.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CreditScheduleESBResponseDTO;
import com.itechro.cas.model.dto.integration.request.creditschedule.CreditScheduleESBResponseSaveRQ;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CreditScheduleResponseJdbcDao extends BaseJDBCDao {

    public int saveCreditScheduleJDBC(CreditScheduleESBResponseSaveRQ creditScheduleESBResponseSaveRQ) {

        StringBuilder INSERT_QUERY = new StringBuilder();
        INSERT_QUERY.append("INSERT INTO T_CREDIT_SCHEDULE_ESB_RESPONSE (CREDIT_SCHEDULE_RESPONSE_ID,FACILITY_PAPER_ID,FACILITY_ID,RESPONSE_STATUS,RESPONSE,CREATED_DATE,CREATED_BY,VERSION,STATUS) VALUES(");
        INSERT_QUERY.append("SEQ_T_CREDIT_SCHEDULE_RESPONSE.nextval,");
        INSERT_QUERY.append(":facilityPaperId,");
        INSERT_QUERY.append(":facilityId,");
        INSERT_QUERY.append(":responseStatus,");
        INSERT_QUERY.append(":response,");
        INSERT_QUERY.append(":createdDate,");
        INSERT_QUERY.append(":createdBy,");
        INSERT_QUERY.append(":version,");
        INSERT_QUERY.append(":status");
        INSERT_QUERY.append(")");


        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("facilityPaperId", creditScheduleESBResponseSaveRQ.getFacilityPaperId());
        paramMap.put("facilityId", creditScheduleESBResponseSaveRQ.getFacilityId());
        paramMap.put("responseStatus", creditScheduleESBResponseSaveRQ.getResponseStatus());
        paramMap.put("response", creditScheduleESBResponseSaveRQ.getResponse());
        paramMap.put("createdDate", creditScheduleESBResponseSaveRQ.getCreatedDate());
        paramMap.put("createdBy", creditScheduleESBResponseSaveRQ.getCreatedBy());
        paramMap.put("version", 0);
        paramMap.put("status", AppsConstants.Status.ACT.toString());

        return namedParameterJdbcTemplate.update(INSERT_QUERY.toString(), paramMap);
    }

    public String getResponseStatus(Integer facilityID) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                                 \n");
        SQL.append(" RESPONSE_STATUS                        \n");
        SQL.append(" FROM T_CREDIT_SCHEDULE_ESB_RESPONSE    \n");
        SQL.append(" WHERE FACILITY_ID =:facilityID         \n");
        params.put("facilityID", facilityID);

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<String>() {

            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String responseStatus = null;
                if (rs.next()) {
                    responseStatus = rs.getString("RESPONSE_STATUS");
                }
                return responseStatus;
            }
        });
    }

    public List<CreditScheduleESBResponseDTO> getCustomerFacilityPaperSummaryList(Integer facilityPaperID) {

        List<CreditScheduleESBResponseDTO> resultList = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT                                             \n");
        SQL.append(" CREDIT_SCHEDULE_RESPONSE_ID,                       \n");
        SQL.append(" FACILITY_PAPER_ID,                                 \n");
        SQL.append(" FACILITY_ID,                                       \n");
        SQL.append(" RESPONSE_STATUS                                    \n");
        SQL.append(" FROM T_CREDIT_SCHEDULE_ESB_RESPONSE                \n");
        SQL.append(" WHERE FACILITY_PAPER_ID =:facilityPaperID          \n");
        params.put("facilityPaperID", facilityPaperID);

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CreditScheduleESBResponseDTO>>() {

            @Override
            public List<CreditScheduleESBResponseDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    CreditScheduleESBResponseDTO summaryDTO = new CreditScheduleESBResponseDTO();
                    summaryDTO.setCreditScheduleESBResponseId(rs.getInt("CREDIT_SCHEDULE_RESPONSE_ID"));
                    summaryDTO.setFacilityPaperId(rs.getInt("FACILITY_PAPER_ID"));
                    summaryDTO.setFacilityId(rs.getInt("FACILITY_ID"));
                    summaryDTO.setResponseStatus(rs.getString("RESPONSE_STATUS"));
                    resultList.add(summaryDTO);
                }
                return resultList;
            }
        });
    }

}
