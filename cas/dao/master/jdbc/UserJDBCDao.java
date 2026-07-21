package com.itechro.cas.dao.master.jdbc;


import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsCommonErrorCode;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.dto.master.UserSearchRQ;
import com.itechro.cas.util.QueryBuilder;
import com.itechro.cas.util.QueryInBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserJDBCDao extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserJDBCDao.class);

    public UserDTO getUserDTOByName(String userName) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT DISTINCT p.privilege_id, \n");
        SQL.append("  u.user_id, \n");
        SQL.append("  u.user_name, \n");
        SQL.append("  p.privilege_code, \n");
        SQL.append("  p.privilege_name \n");
        SQL.append("FROM t_user u \n");
        SQL.append("INNER JOIN t_user_role ur \n");
        SQL.append("ON (u.user_id = ur.user_id) \n");
        SQL.append("INNER JOIN t_role r \n");
        SQL.append("ON (ur.role_id = r.role_id) \n");
        SQL.append("INNER JOIN t_role_privilege rp \n");
        SQL.append("ON (r.role_id = rp.role_id) \n");
        SQL.append("INNER JOIN t_privilege p \n");
        SQL.append("ON (rp.privilege_id = p.privilege_id) \n");
        SQL.append("WHERE user_name = BINARY :userName \n");
        SQL.append(" AND r.status =:status \n");
        SQL.append(" AND p.status =:status ");

        params.put("status", AppsConstants.Status.ACT.toString());
        params.put("userName", userName);

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<UserDTO>() {
            @Nullable
            @Override
            public UserDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                UserDTO userDTO = new UserDTO();
                while (rs.next()) {
                    if (userDTO.getUserID() == null) {
                        userDTO.setUserID(rs.getInt("user_id"));
                        userDTO.setUserName(rs.getString("user_name"));
                    }
                    userDTO.getPrivileges().add(rs.getString("privilege_code"));
                }

                return userDTO;
            }
        });
    }

    public UserDTO getUserDetails(String userName, boolean isAgentLoad) throws AppsException {
        //LOG.info("START: User retrieval for user {} ", userName);

        final UserDTO user = new UserDTO();
        QueryBuilder userSQL = new QueryBuilder();
        userSQL.append("SELECT \n");
        userSQL.append("  USR.USER_NAME, \n");
        userSQL.append("  CONCAT(CONCAT(USR.FIRST_NAME, ' '), USR.LAST_NAME) AS DISPLAY_NAME, \n");
        userSQL.append("  USR.PASSWORD , \n");
        userSQL.append("  USR.USER_TYPE , \n");
        userSQL.append("  USR.USER_REF_ID , \n");
        userSQL.append("  PR.PRIVILEGE_CODE , \n");
        userSQL.append("  PR.ALLOWED_SOL_IDS, \n");
        userSQL.append("  PR.RESTRICTED_SOL_IDS, \n");
        userSQL.append("  USR.USER_ID AS USER_ID \n");
        userSQL.append("FROM \n");
        userSQL.append("  T_USER USR \n");

        if (isAgentLoad) {
            userSQL.append("INNER JOIN T_ROLE TR \n");
            userSQL.append("ON \n");
            userSQL.append("  (TR.ROLE_NAME = 'AGENT' AND TR.STATUS = 'ACT' AND APPROVE_STATUS = 'APPROVED') \n");
        } else {
            userSQL.append("INNER JOIN T_USER_ROLE UR \n");
            userSQL.append("ON \n");
            userSQL.append("  USR.USER_ID = UR.USER_ID \n");
        }

        userSQL.append("LEFT JOIN T_ROLE_PRIVILEGE RP \n");
        userSQL.append("ON \n");

        if (isAgentLoad) {
            userSQL.append("  RP.ROLE_ID = TR.ROLE_ID \n");
        } else {
            userSQL.append("  RP.ROLE_ID = UR.ROLE_ID \n");
        }

        userSQL.append("LEFT JOIN T_PRIVILEGE PR \n");
        userSQL.append("ON \n");
        userSQL.append("  PR.PRIVILEGE_ID = RP.PRIVILEGE_ID \n");
        userSQL.append("WHERE \n");
        userSQL.appendNotNullMandatory("USR.STATUS = :status ", AppsConstants.Status.ACT.toString());
        userSQL.appendNotNullMandatory("AND USR.USER_NAME =:username ", userName);

        try {
            namedParameterJdbcTemplate.query(userSQL.toString(), userSQL.getParameterMap(), rs -> {
                if (user.getUserID() == null) {
                    user.setUserID(rs.getInt("USER_ID"));
                    user.setUserName(rs.getString("USER_NAME"));
                    user.setDisplayName(rs.getString("DISPLAY_NAME"));
                    user.setPassword(rs.getString("PASSWORD"));
                    user.setUserType(DomainConstants.UserType.valueOf(rs.getString("USER_TYPE")));
                    user.setUserRefID(rs.getInt("USER_REF_ID"));
                }

                List<String> allowedSolsIDs;
                List<String> restrictedSolsIDs;
                Optional<String> privilege = Optional.ofNullable(rs.getString("PRIVILEGE_CODE"));
                Optional<String> allowedSols = Optional.ofNullable(rs.getString("ALLOWED_SOL_IDS"));
                Optional<String> restrictedSols = Optional.ofNullable(rs.getString("RESTRICTED_SOL_IDS"));
                if (privilege.isPresent() && allowedSols.isPresent()) {
                    String allowedSolsString = rs.getString("ALLOWED_SOL_IDS");
                    allowedSolsIDs = Arrays.asList(allowedSolsString.split(","));
                    if (allowedSolsIDs.contains("004")) {
                        user.addPrivilege(privilege.get());
                        LOG.info("INFO: {} Privilege Added", privilege.get());
                    }
                } else if (privilege.isPresent()) {
                    if (restrictedSols.isPresent()) {
                        String restrictedSolsString = rs.getString("RESTRICTED_SOL_IDS");
                        restrictedSolsIDs = Arrays.asList(restrictedSolsString.split(","));
                        if (restrictedSolsIDs.contains("004")) {
                            LOG.warn("INFO: {} Privilege Restricted", privilege.get());
                        } else {
                            user.addPrivilege(privilege.get());
                        }
                    } else {
                        user.addPrivilege(privilege.get());
                    }
                }

            });


        } catch (EmptyResultDataAccessException e) {
            LOG.warn("User with user details `{}` does not exist", userName);
            throw new AppsException(AppsCommonErrorCode.APPS_EMPTY_RESULT, AppsConstants.ResponseStatus.SUCCESS);
        }

        //LOG.info("END: User retrieval for {} completed as {}", userName, user);
        return user;
    }

    public SortedSet<String> getUserPrivilegesByUMPCode(String upmRoleCode, String divCode) throws AppsException {
        LOG.info("START: User retrieval for user {} ", upmRoleCode);
        SortedSet<String> privileges = new TreeSet<>();


        final UserDTO user = new UserDTO();
        QueryBuilder SQL = new QueryBuilder();
        SQL.append(" SELECT  \n");
        SQL.append(" PR.PRIVILEGE_CODE,     \n");
        SQL.append(" PR.RESTRICTED_SOL_IDS, \n");
        SQL.append(" PR.ALLOWED_SOL_IDS     \n");
        SQL.append(" FROM \n");
        SQL.append("   T_ROLE UR \n");
        SQL.append("   LEFT JOIN T_ROLE_PRIVILEGE RP ON RP.ROLE_ID = UR.ROLE_ID \n");
        SQL.append("   LEFT JOIN T_PRIVILEGE PR ON PR.PRIVILEGE_ID = RP.PRIVILEGE_ID \n");
        SQL.append(" WHERE  \n");
        SQL.appendNotNullMandatory(" UR.STATUS = :status \n", AppsConstants.Status.ACT.toString());
        SQL.appendNotNullMandatory("       AND UR.UPM_PRIVILAGE_CODE = :upmRoleCode \n", upmRoleCode);


        try {
            namedParameterJdbcTemplate.query(SQL.toString(), SQL.getParameterMap(), rs -> {
                List<String> allowedDivCodes;
                List<String> restrictedDivCodes;
                Optional<String> privilege = Optional.ofNullable(rs.getString("PRIVILEGE_CODE"));
                Optional<String> allowedSols = Optional.ofNullable(rs.getString("ALLOWED_SOL_IDS"));
                Optional<String> restrictedSols = Optional.ofNullable(rs.getString("RESTRICTED_SOL_IDS"));
                if (privilege.isPresent() && allowedSols.isPresent()) {
                    String allowedDivCodesString = rs.getString("ALLOWED_SOL_IDS");
                    allowedDivCodes = Arrays.asList(allowedDivCodesString.split(","));
                    if (allowedDivCodes.contains(divCode)) {
                        privileges.add(privilege.get());
                        LOG.info("INFO: {} Privilege Added for UPM : {} and DIV CODE : {}", privilege.get(), upmRoleCode, divCode);
                    }
                } else if (privilege.isPresent()) {
                    if (restrictedSols.isPresent()) {
                        String restrictedDivCodesString = rs.getString("RESTRICTED_SOL_IDS");
                        restrictedDivCodes = Arrays.asList(restrictedDivCodesString.split(","));
                        if (restrictedDivCodes.contains(divCode)) {
                            LOG.warn("INFO: {} Privilege Restricted for UPM : {} and DIV CODE : {}", privilege.get(), upmRoleCode, divCode);
                        } else {
                            privileges.add(privilege.get());
                        }
                    } else {
                        privileges.add(privilege.get());
                    }
                }
            });

        } catch (EmptyResultDataAccessException e) {
            LOG.warn("Privileges details `{}` does not exist", upmRoleCode);
            throw new AppsException(AppsCommonErrorCode.APPS_EMPTY_RESULT, AppsConstants.ResponseStatus.SUCCESS);
        }

        LOG.info("END: Privileges retrieval for {} completed as {}", upmRoleCode, privileges);
        return privileges;
    }

    public Page<UserDTO> getPagedUsers(UserSearchRQ searchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT DISTINCT u.user_id, \n");
        SQL.append("  u.user_name, \n");
        SQL.append("  u.title, \n");
        SQL.append("  u.first_name, \n");
        SQL.append("  u.last_name, \n");
        SQL.append("  u.email, \n");
        SQL.append("  u.status \n");
        SQL.append("FROM \n");
        SQL.append("  t_user u \n");
        SQL.append("WHERE u.user_id IS NOT NULL \n");
        if (StringUtils.isNotEmpty(searchRQ.getUserName())) {
            SQL.append("AND upper(u.user_name) LIKE '%" + searchRQ.getUserName().toUpperCase() + "%' \n");
        }
        if (!searchRQ.getUneditableUsers().isEmpty()) {
            SQL.append("AND u.user_name NOT IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getUneditableUsers()) + ") \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getFirstName())) {
            SQL.append("AND upper(u.first_name) LIKE '%" + searchRQ.getFirstName().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getLastName())) {
            SQL.append("AND upper(u.last_name) LIKE '%" + searchRQ.getLastName().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getEmail())) {
            SQL.append("AND upper(u.email) LIKE '%" + searchRQ.getEmail().toUpperCase() + "%' \n");
        }
        if (searchRQ.getStatus() != null) {
            SQL.append("AND u.status = :status \n");
            params.put("status", searchRQ.getStatus().toString());
        }
        SQL.append("ORDER BY u.last_name");

        return getPagedData(SQL.toString(), params, new RowMapper<UserDTO>() {
            @Nullable
            @Override
            public UserDTO mapRow(ResultSet rs, int i) throws SQLException {
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(rs.getString("email"));
                userDTO.setFirstName(rs.getString("first_name"));
                userDTO.setLastName(rs.getString("last_name"));
                userDTO.setTitle(DomainConstants.Title.resolveTitle(rs.getString("title")));
                userDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("status")));
                userDTO.setUserID(rs.getInt("user_id"));
                userDTO.setUserName(rs.getString("user_name"));

                return userDTO;
            }
        }, searchRQ);
    }
}
