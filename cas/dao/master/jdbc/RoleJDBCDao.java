package com.itechro.cas.dao.master.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.master.PrivilegeDTO;
import com.itechro.cas.model.dto.master.RoleDTO;
import com.itechro.cas.model.dto.master.RoleSearchRQ;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleJDBCDao extends BaseJDBCDao {
    public Page<RoleDTO> getPagedRoles(RoleSearchRQ searchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT r.role_id, \n");
        SQL.append("  r.role_name, \n");
        SQL.append("  r.UPM_PRIVILAGE_CODE, \n");
        SQL.append("  r.status, \n");
        SQL.append("r.APPROVE_STATUS, \n");
        SQL.append("r.APPROVED_BY, \n");
        SQL.append("r.APPROVED_DATE \n");
        SQL.append("FROM t_role r \n");
        SQL.append("WHERE r.role_id IS NOT NULL \n");
        if (StringUtils.isNotEmpty(searchRQ.getRoleName())) {
            SQL.append("AND upper(r.role_name) LIKE '%" + searchRQ.getRoleName().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getUpmPrivilegeCode())) {
            SQL.append("AND upper(r.UPM_PRIVILAGE_CODE) LIKE '%" + searchRQ.getUpmPrivilegeCode().toUpperCase() + "%' \n");
        }
        if (searchRQ.getStatus() != null) {
            SQL.append("AND r.status = :status \n");
            params.put("status", searchRQ.getStatus().toString());
        }
        SQL.append("ORDER BY r.role_name");

        return getPagedData(SQL.toString(), params, new RowMapper<RoleDTO>() {
            @Nullable
            @Override
            public RoleDTO mapRow(ResultSet rs, int i) throws SQLException {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setRoleID(rs.getInt("role_id"));
                roleDTO.setRoleName(rs.getString("role_name"));
                roleDTO.setUpmPrivilegeCode(rs.getString("UPM_PRIVILAGE_CODE"));
                roleDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                roleDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    roleDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                roleDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("status")));

                return roleDTO;
            }
        }, searchRQ);
    }

    public List<RoleDTO> getRoles(AppsConstants.Status status) {
        final List<RoleDTO> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT r.role_id, \n");
        SQL.append("  r.role_name, \n");
        SQL.append("  r.UPM_PRIVILAGE_CODE, \n");
        SQL.append("  r.status \n");
        SQL.append("FROM t_role r \n");
        SQL.append("WHERE r.role_id IS NOT NULL \n");
        if (status != null) {
            SQL.append("AND r.status = :status \n");
            params.put("status", status.toString());
        }
        SQL.append("ORDER BY r.role_name");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<RoleDTO>>() {

            @Nullable
            @Override
            public List<RoleDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setRoleID(rs.getInt("role_id"));
                    roleDTO.setRoleName(rs.getString("role_name"));
                    roleDTO.setUpmPrivilegeCode(rs.getString("UPM_PRIVILAGE_CODE"));
                    roleDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("status")));

                    result.add(roleDTO);
                }
                return result;
            }
        });
    }

    public List<PrivilegeDTO> getPrivileges(AppsConstants.Status status, DomainConstants.PrivilegeModule privilegeModule) {
        final List<PrivilegeDTO> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT p.privilege_id, \n");
        SQL.append("  p.privilege_name, \n");
        SQL.append("  p.privilege_code, \n");
        SQL.append("  p.behaviour_description, \n");
        SQL.append("  pc.category, \n");
        SQL.append("  p.status \n");
        SQL.append("FROM t_privilege p \n");
        SQL.append("INNER JOIN t_privilege_category pc ON \n");
        SQL.append("p.privilege_category_id = pc.privilege_category_id \n");
        SQL.append("WHERE p.privilege_id IS NOT NULL \n");
        if (status != null) {
            SQL.append("AND p.status          = :status \n");
            params.put("status", status.toString());
        }

        if(privilegeModule != null) {
            SQL.append("AND pc.module          = :privilegeModule \n");
            params.put("privilegeModule", privilegeModule.getDescription());
        }
        SQL.append("ORDER BY p.privilege_code");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<PrivilegeDTO>>() {

            @Nullable
            @Override
            public List<PrivilegeDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    PrivilegeDTO privilegeDTO = new PrivilegeDTO();
                    privilegeDTO.setCode(rs.getString("privilege_code"));
                    privilegeDTO.setDescription(rs.getString("behaviour_description"));
                    privilegeDTO.setPrivilegeID(rs.getInt("privilege_id"));
                    privilegeDTO.setPrivilegeName(rs.getString("privilege_name"));
                    privilegeDTO.setCategory(rs.getString("category"));
                    privilegeDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("status")));
                    result.add(privilegeDTO);
                }
                return result;
            }
        });
    }

    public Long getPendingRoleCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(tr.role_id) pendingRoleCount \n");
        SQL.append(" FROM t_role tr                           \n");
        SQL.append(" WHERE tr.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND tr.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND tr.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

}