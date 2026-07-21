package com.itechro.cas.dao.agent.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.agent.AgentDTO;
import com.itechro.cas.model.dto.agent.AgentSearchRQ;
import com.itechro.cas.model.dto.master.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AgentJDBCDao extends BaseJDBCDao {

    public Page<AgentDTO> getPagedAgents(AgentSearchRQ agentSearchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();


        SQL.append(" SELECT                                                               \n");
        SQL.append("   ta.AGENT_ID,                                                       \n");
        SQL.append("   ta.MOBILE_NUMBER,                                                  \n");
        SQL.append("   ta.SUPERVISOR_AD_USER_ID,                                              \n");
        SQL.append("   ta.NIC,                                                            \n");
        SQL.append("   ta.STATUS,                                                         \n");
        SQL.append("   ta.REMARK,                                                         \n");
        SQL.append("   tu.USER_ID,                                                        \n");
        SQL.append("   tu.USER_NAME,                                                      \n");
        SQL.append("   tu.TITLE,                                                          \n");
        SQL.append("   tu.FIRST_NAME,                                                     \n");
        SQL.append("   tu.LAST_NAME,                                                      \n");
        SQL.append("   tu.EMAIL,                                                          \n");
        SQL.append("   tu.STATUS as user_status                                           \n");
        SQL.append(" FROM T_AGENT ta INNER JOIN T_USER tu ON ta.AGENT_ID = tu.USER_REF_ID \n");
        SQL.append(" WHERE ta.AGENT_ID IS NOT NULL                                        \n");

        if (StringUtils.isNotEmpty(agentSearchRQ.getMobileNumber())) {
            SQL.append(" AND ta.MOBILE_NUMBER LIKE '%" + agentSearchRQ.getMobileNumber() + "%' \n");
        }

        if (StringUtils.isNotEmpty(agentSearchRQ.getUserName())) {
            SQL.append(" AND UPPER(tu.USER_NAME) LIKE '%" + agentSearchRQ.getUserName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(agentSearchRQ.getFirstName())) {
            SQL.append(" AND UPPER(tu.FIRST_NAME) LIKE '%" + agentSearchRQ.getFirstName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(agentSearchRQ.getLastName())) {
            SQL.append(" AND UPPER(tu.LAST_NAME) LIKE '%" + agentSearchRQ.getLastName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(agentSearchRQ.getEmail())) {
            SQL.append(" AND UPPER(tu.EMAIL) LIKE '%" + agentSearchRQ.getEmail().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(agentSearchRQ.getNic())) {
            SQL.append(" AND UPPER(ta.NIC) LIKE '%" + agentSearchRQ.getNic().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(agentSearchRQ.getSupervisorADUserID())) {
            SQL.append(" AND UPPER(ta.SUPERVISOR_AD_USER_ID) LIKE '%" + agentSearchRQ.getSupervisorADUserID().toUpperCase() + "%' \n");
        }

        if (agentSearchRQ.getStatus() != null) {
            SQL.append(" AND tu.STATUS =:status                                               \n");
            SQL.append(" AND ta.STATUS = :status                                              \n");
            params.put("status", agentSearchRQ.getStatus().toString());
        }

        SQL.append(" ORDER BY tu.FIRST_NAME, tu.LAST_NAME, tu.USER_NAME                   \n");

        return getPagedData(SQL.toString(), params, new RowMapper<AgentDTO>() {
            @Override
            public AgentDTO mapRow(ResultSet rs, int i) throws SQLException {

                AgentDTO agentDTO = new AgentDTO();

                agentDTO.setAgentID(rs.getInt("AGENT_ID"));
                agentDTO.setMobileNumber(rs.getString("MOBILE_NUMBER"));
                agentDTO.setSupervisorADUserID(rs.getString("SUPERVISOR_AD_USER_ID"));
                agentDTO.setNic(rs.getString("NIC"));
                agentDTO.setRemark(rs.getString("REMARK"));
                agentDTO.setStatus(AppsConstants.Status.valueOf(rs.getString("STATUS")));

                UserDTO userDTO = new UserDTO();
                userDTO.setUserID(rs.getInt("USER_ID"));
                userDTO.setUserName(rs.getString("USER_NAME"));
                userDTO.setFirstName(rs.getString("FIRST_NAME"));
                userDTO.setLastName(rs.getString("LAST_NAME"));
                userDTO.setTitle(DomainConstants.Title.valueOf(rs.getString("TITLE")));
                userDTO.setEmail(rs.getString("EMAIL"));
                userDTO.setStatus(AppsConstants.Status.valueOf(rs.getString("user_status")));

                agentDTO.setUserDTO(userDTO);

                return agentDTO;
            }
        }, agentSearchRQ);
    }

}
