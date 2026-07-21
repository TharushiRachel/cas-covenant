package com.itechro.cas.dao.facility.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.dto.facility.FacilityFacilitySecurityDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FacilityFacilitySecurityJdbcDao extends BaseJDBCDao {

    public FacilityFacilitySecurityDTO getFacilityFacilitySecurityDTO(FacilityFacilitySecurityDTO searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                                  \n");
        SQL.append("   FACILITY_FACILITY_SECURITY_ID,        \n");
        SQL.append("   FACILITY_ID,                          \n");
        SQL.append("   FACILITY_SECURITY_ID,                 \n");
        SQL.append("   STATUS,                               \n");
        SQL.append("   FACILITY_SECURITY_AMOUNT              \n");
        SQL.append(" FROM T_FACILITY_F_SECURITY              \n");
        SQL.append(" WHERE FACILITY_FACILITY_SECURITY_ID IS NOT NULL           \n");

        SQL.append("AND FACILITY_ID =:facilityID \n");
        params.put("facilityID", searchRQ.getFacilityID());

        SQL.append("AND FACILITY_SECURITY_ID =:facilitySecurityID \n");
        params.put("facilitySecurityID", searchRQ.getFacilitySecurityID());

//        if (searchRQ.getStatus() != null) {
//            SQL.append("AND STATUS =:status \n");
//            params.put("status", searchRQ.getStatus().toString());
//        }

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<FacilityFacilitySecurityDTO>() {

            @Override
            public FacilityFacilitySecurityDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

                FacilityFacilitySecurityDTO facilityFacilitySecurityDTO = null;
                if (rs.next()) {
                    facilityFacilitySecurityDTO = new FacilityFacilitySecurityDTO();
                    facilityFacilitySecurityDTO.setFacilitySecuritySecurityID(rs.getInt("FACILITY_FACILITY_SECURITY_ID"));
                    facilityFacilitySecurityDTO.setFacilityID(rs.getInt("FACILITY_ID"));
                    facilityFacilitySecurityDTO.setFacilitySecurityID(rs.getInt("FACILITY_SECURITY_ID"));
                    facilityFacilitySecurityDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                    facilityFacilitySecurityDTO.setFacilitySecurityAmount(rs.getBigDecimal("FACILITY_SECURITY_AMOUNT"));
                }
                return facilityFacilitySecurityDTO;
            }
        });
    }

}
