package com.itechro.cas.dao.master.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.CachingConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.master.SystemParamDTO;
import com.itechro.cas.model.dto.master.SystemParamSearchRQ;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SystemParameterJDBCDao extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(SystemParameterJDBCDao.class);

//    @Cacheable(CachingConstants.SYSTEM_PARAMS_KEY)
    public Map<String, String> getParameters() {
        final Map<String, String> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT sp.PARAM_KEY, \n");
        SQL.append("  sp.PARAM_VALUE \n");
        SQL.append("FROM t_system_parameter sp \n");

        namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Map<String, String>>() {
            @Override
            public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    String paramKey = rs.getString("PARAM_KEY");
                    String paramValue = rs.getString("PARAM_VALUE");

                    result.put(paramKey, paramValue);
                }

                return result;
            }
        });

        LOG.info("System parameters retrieved : {}", new Date());

        return result;
    }

    public Map<String, SystemParamDTO> getSystemParameters() {
        final Map<String, SystemParamDTO> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT sp.system_parameter_id, \n");
        SQL.append("  sp.param_key, \n");
        SQL.append("  sp.param_name, \n");
        SQL.append("  sp.description, \n");
        SQL.append("  sp.param_value, \n");
        SQL.append("  sp.param_type, \n");
        SQL.append("  sp.status \n");
        SQL.append("FROM t_system_parameter sp \n");

        namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Map<String, SystemParamDTO>>() {
            @Override
            public Map<String, SystemParamDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    SystemParamDTO paramDTO = new SystemParamDTO();

                    String paramKey = rs.getString("param_key");
                    paramDTO.setSystemParameterID(rs.getInt("system_parameter_id"));
                    paramDTO.setParamKey(paramKey);
                    paramDTO.setParamName(rs.getString("param_name"));
                    paramDTO.setDescription(rs.getString("description"));
                    paramDTO.setParamValue(rs.getString("param_value"));
                    paramDTO.setParamType(rs.getString("param_type"));
                    paramDTO.setStatus(AppsConstants.Status.valueOf(rs.getString("status")));

                    result.put(paramKey, paramDTO);
                }

                return result;
            }
        });

        LOG.info("Load all system parameters : {}", new Date());

        return result;
    }

    public Page<SystemParamDTO> getPagedSystemParameters(SystemParamSearchRQ searchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT sp.system_parameter_id, \n");
        SQL.append("  sp.param_key, \n");
        SQL.append("  sp.param_name, \n");
        SQL.append("  sp.description, \n");
        SQL.append("  sp.param_value, \n");
        SQL.append("  sp.param_type, \n");
        SQL.append("  sp.status \n");
        SQL.append("FROM t_system_parameter sp \n");
       // SQL.append("WHERE sp.editable ='Y' \n");
        SQL.append("WHERE sp.status ='ACT' \n");

        if (StringUtils.isNotEmpty(searchRQ.getParamKey())) {
            SQL.append("AND upper(sp.param_key) LIKE '%" + searchRQ.getParamKey().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getParamName())) {
            SQL.append("AND upper(sp.param_name) LIKE '%" + searchRQ.getParamName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getDescription())) {
            SQL.append("AND upper(sp.description) LIKE '%" + searchRQ.getDescription().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getParamType())) {
            SQL.append("AND upper(sp.param_type) LIKE '%" + searchRQ.getParamType().toUpperCase() + "%' \n");
        }

       /* if (searchRQ.getStatus() != null) {
            SQL.append("AND sp.status = :status \n");
            params.put("status", searchRQ.getStatus().toString());
        }*/

        SQL.append("ORDER BY sp.param_key");

        return getPagedData(SQL.toString(), params, new RowMapper<SystemParamDTO>() {
            @Nullable
            @Override
            public SystemParamDTO mapRow(ResultSet rs, int i) throws SQLException {
                SystemParamDTO paramDTO = new SystemParamDTO();
                paramDTO.setSystemParameterID(rs.getInt("system_parameter_id"));
                paramDTO.setParamKey(rs.getString("param_key"));
                paramDTO.setParamName(rs.getString("param_name"));
                paramDTO.setDescription(rs.getString("description"));
                paramDTO.setParamValue(rs.getString("param_value"));
                paramDTO.setParamType(rs.getString("param_type"));
                paramDTO.setStatus(AppsConstants.Status.valueOf(rs.getString("status")));

                return paramDTO;
            }
        }, searchRQ);
    }
}
