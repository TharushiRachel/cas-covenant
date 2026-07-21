package com.itechro.cas.dao.crib.jdbc;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.dto.customer.CustomerCribResponseDTO;
import com.itechro.cas.model.dto.customer.CustomerCribResponseRQ;
import com.itechro.cas.model.dto.integration.request.CustomerCASCribRQ;
import com.itechro.cas.model.dto.integration.request.cribreport.CribResponseSaveRQ;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
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
public class CirbResponsesJdbcDao extends BaseJDBCDao {

    public String getCustomerCribCASResponse(CustomerCribResponseRQ customerCribResponseRQ) {

        Map<String, Object> params = new HashMap<>();
        if (customerCribResponseRQ.getBrn() != null) {
            params.put("brn", customerCribResponseRQ.getBrn().trim());
        }

        if (customerCribResponseRQ.getNic() != null) {
            params.put("nic", customerCribResponseRQ.getNic().trim());
        }

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT RESPONSE FROM (\n");
        SQL.append("SELECT tccr.RESPONSE, TCCR.BRN, TCCR.NIC, TCCR.CREATED_DATE \n");
        SQL.append("FROM T_CUSTOMER_CRIB_RESPONSE tccr \n");
        SQL.append("ORDER BY CREATED_DATE DESC)\n");

        if (StringUtils.isNotEmpty(customerCribResponseRQ.getBrn())) {
            SQL.append("WHERE BRN =:brn \n");
        }
        if (StringUtils.isNotEmpty(customerCribResponseRQ.getNic())) {
            SQL.append("WHERE NIC =:nic \n");
        }

        SQL.append("AND CREATED_DATE > = ADD_MONTHS(sysdate ,-3)\n");
        SQL.append("AND ROWNUM <=1\n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String response = "";
                if (rs.next()) {
                    response = rs.getString("RESPONSE");
                }
                return response;
            }
        });
    }

    public List<CustomerCribResponseDTO> getRetailCribReportFromCasDB(CustomerCASCribRQ customerCASCribRQ) {
        final List<CustomerCribResponseDTO> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        params.put("identificationNumber", customerCASCribRQ.getIdentificationNumber().trim().toUpperCase());


        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                \n");
        SQL.append("FROM (                                                  \n");
        SQL.append("SELECT                                                  \n");
        SQL.append("  TCCR.CUSTOMER_CRIB_RESPONSE_ID,                       \n");
        SQL.append("  TCCR.NIC,                                             \n");
        SQL.append("  TCCR.BRN,                                             \n");
        SQL.append("  TCCR.CREATED_DATE,                                    \n");
        SQL.append("  TCCR.CUSTOMER_NAME,                                   \n");
        SQL.append("  TCCR.CUSTOMER_GENDER,                                 \n");
        SQL.append("  TCCR.CREATED_BY,                                      \n");
        SQL.append("  TCCR.RESPONSE                                         \n");
        SQL.append("FROM T_CUSTOMER_CRIB_RESPONSE TCCR                      \n");
        SQL.append("WHERE                                                   \n");

        if (customerCASCribRQ.getIdentificationType() == DomainConstants.CustomerIdentificationType.NIC) {
            SQL.append("upper(TCCR.NIC) =:identificationNumber              \n");
        }

        if (customerCASCribRQ.getIdentificationType() == DomainConstants.CustomerIdentificationType.BRC) {
            SQL.append("upper(TCCR.BRN) =:identificationNumber              \n");
        }
        SQL.append("AND TCCR.RESPONSE IS NOT NULL                           \n");
        SQL.append("ORDER BY TCCR.CREATED_DATE DESC                         \n");
        SQL.append(") RESULTSET                                             \n");
        SQL.append("WHERE ROWNUM <= 2                                       \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CustomerCribResponseDTO>>() {
            @Override
            public List<CustomerCribResponseDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    CustomerCribResponseDTO customerCribResponseDTO = new CustomerCribResponseDTO();
                    customerCribResponseDTO.setCustomerCribReportID(rs.getInt("CUSTOMER_CRIB_RESPONSE_ID"));
                    customerCribResponseDTO.setBrn(rs.getString("BRN"));
                    customerCribResponseDTO.setNic(rs.getString("NIC"));
                    customerCribResponseDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                    customerCribResponseDTO.setCustomerGender(rs.getString("CUSTOMER_GENDER"));
                    customerCribResponseDTO.setResponse(rs.getString("RESPONSE"));
                    customerCribResponseDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                    customerCribResponseDTO.setCreatedBy(rs.getString("CREATED_BY"));
                    result.add(customerCribResponseDTO);
                }
                return result;
            }
        });
    }

    public int saveCribResponseJDBC(CribResponseSaveRQ cribResponseSaveRQ) {

        StringBuilder INSERT_QUERY = new StringBuilder();
        INSERT_QUERY.append("INSERT INTO T_CUSTOMER_CRIB_RESPONSE (CUSTOMER_CRIB_RESPONSE_ID,NIC,BRN,CREATED_DATE,CREATED_BY,CUSTOMER_NAME,CUSTOMER_GENDER,RESPONSE) VALUES");
        INSERT_QUERY.append("(");
        INSERT_QUERY.append("SEQ_T_CUSTOMER_CRIB_RESPONSE.nextval,");
        INSERT_QUERY.append(":nic,");
        INSERT_QUERY.append(":brn,");
        INSERT_QUERY.append(":createdDate,");
        INSERT_QUERY.append(":createdBy,");
        INSERT_QUERY.append(":customerName,");
        INSERT_QUERY.append(":gender,");
        INSERT_QUERY.append(":response");
        INSERT_QUERY.append(")");


        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (cribResponseSaveRQ.getIdentificationType() == DomainConstants.CustomerIdentificationType.NIC) {
            paramMap.put("nic", cribResponseSaveRQ.getIdentificationNumber());
            paramMap.put("brn", null);
        } else if (cribResponseSaveRQ.getIdentificationType() == DomainConstants.CustomerIdentificationType.BRC) {
            paramMap.put("brn", cribResponseSaveRQ.getIdentificationNumber());
            paramMap.put("nic", null);
        }
        paramMap.put("createdDate", cribResponseSaveRQ.getCreatedDate());
        paramMap.put("createdBy", cribResponseSaveRQ.getCredentialsDTO().getUserName());
        paramMap.put("customerName", cribResponseSaveRQ.getCustomerName());
        paramMap.put("gender", cribResponseSaveRQ.getCustomerGender());
        paramMap.put("response", cribResponseSaveRQ.getResponseString());

        return namedParameterJdbcTemplate.update(INSERT_QUERY.toString(), paramMap);

    }


}
