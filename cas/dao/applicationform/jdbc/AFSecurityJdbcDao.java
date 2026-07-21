package com.itechro.cas.dao.applicationform.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.dto.applicationform.AFFacilitySecurityDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AFSecurityJdbcDao extends BaseJDBCDao {

    public AFFacilitySecurityDTO getFacilityFacilitySecurityDTO(AFFacilitySecurityDTO searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                                             \n");
        SQL.append("   FACILITY_SECURITY_ID,                            \n");
        SQL.append("   FACILITY_ID,                                     \n");
        SQL.append("   SECURITY_ID,                                     \n");
        SQL.append("   STATUS,                                          \n");
        SQL.append("   FACILITY_SECURITY_AMOUNT                         \n");
        SQL.append(" FROM T_AF_FACILITY_SECURITY                        \n");
        SQL.append(" WHERE FACILITY_SECURITY_ID IS NOT NULL             \n");

        SQL.append("AND FACILITY_ID =:facilityID \n");
        params.put("facilityID", searchRQ.getFacilityID());

        SQL.append("AND SECURITY_ID =:securityID \n");
        params.put("securityID", searchRQ.getSecurityID());

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<AFFacilitySecurityDTO>() {

            @Override
            public AFFacilitySecurityDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

                AFFacilitySecurityDTO afFacilitySecurityDTO = null;
                if (rs.next()) {
                    afFacilitySecurityDTO = new AFFacilitySecurityDTO();
                    afFacilitySecurityDTO.setFacilitySecurityID(rs.getInt("FACILITY_SECURITY_ID"));
                    afFacilitySecurityDTO.setFacilityID(rs.getInt("FACILITY_ID"));
                    afFacilitySecurityDTO.setSecurityID(rs.getInt("SECURITY_ID"));
                    afFacilitySecurityDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                    afFacilitySecurityDTO.setFacilitySecurityAmount(rs.getBigDecimal("FACILITY_SECURITY_AMOUNT"));
                }
                return afFacilitySecurityDTO;
            }
        });
    }
}
