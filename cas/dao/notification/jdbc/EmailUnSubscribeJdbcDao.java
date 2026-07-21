package com.itechro.cas.dao.notification.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.dto.notification.EmailUnSubscribeDTO;
import com.itechro.cas.model.dto.notification.EmailUnSubscribeRQ;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmailUnSubscribeJdbcDao extends BaseJDBCDao {

    public EmailUnSubscribeDTO getEmailUnSubscribeDTOByUser(String userName, String emailAddress) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT \n");
        SQL.append("   eu.EMAIL_UN_SUBSCRIBE_ID, \n");
        SQL.append("   eu.USER_NAME, \n");
        SQL.append("   eu.USER_EMAIL, \n");
        SQL.append("   eu.CREATED_DATE, \n");
        SQL.append("   eu.CREATED_BY, \n");
        SQL.append("   eu.MODIFIED_DATE, \n");
        SQL.append("   eu.MODIFIED_BY, \n");
        SQL.append("   eu.STATUS \n");
        SQL.append(" FROM T_EMAIL_UN_SUBSCRIBE eu \n");
        SQL.append(" WHERE eu.EMAIL_UN_SUBSCRIBE_ID IS NOT NULL \n");

        if (userName != null) {
            SQL.append("  AND UPPER(eu.USER_NAME) LIKE '%" + userName.trim().toUpperCase() + "%' \n");
        }
        if (emailAddress != null) {
            SQL.append("  AND UPPER(eu.USER_EMAIL) LIKE '%" + emailAddress.trim().toUpperCase() + "%' \n");
        }

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<EmailUnSubscribeDTO>() {

            @Override
            public EmailUnSubscribeDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

                EmailUnSubscribeDTO emailUnSubscribeDTO = null;
                if (rs.next()) {
                    emailUnSubscribeDTO = new EmailUnSubscribeDTO();
                    emailUnSubscribeDTO.setEmailUnSubscribeID(rs.getInt("EMAIL_UN_SUBSCRIBE_ID"));
                    emailUnSubscribeDTO.setUserName(rs.getString("USER_NAME"));
                    emailUnSubscribeDTO.setUserEmail(rs.getString("USER_EMAIL"));
                    emailUnSubscribeDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                    if (rs.getTimestamp("CREATED_DATE") != null) {
                        emailUnSubscribeDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                    }
                    if (rs.getTimestamp("MODIFIED_DATE") != null) {
                        emailUnSubscribeDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("MODIFIED_DATE")));
                    }
                    emailUnSubscribeDTO.setCreatedBy(rs.getString("CREATED_BY"));
                    emailUnSubscribeDTO.setModifiedBy(rs.getString("MODIFIED_BY"));
                }
                return emailUnSubscribeDTO;
            }
        });
    }
}
