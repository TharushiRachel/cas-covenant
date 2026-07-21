package com.itechro.cas.dao.applicationform.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.applicationform.AFStatusHistoryDTO;
import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import com.itechro.cas.model.dto.applicationform.*;
import com.itechro.cas.model.dto.facility.FacilityOtherFacilityInformationDTO;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.QueryInBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import com.itechro.cas.model.dto.applicationform.applicationFormCustomer.SearchApplicationFormRQ;
import com.itechro.cas.model.dto.lead.LeadDashboardCountDTO;
import com.itechro.cas.model.dto.lead.LeadDashboardDTO;
import com.itechro.cas.model.dto.lead.LeadDashboardRQ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

@Repository
public class ApplicationFormJdbcDao extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationFormJdbcDao.class);

    public Page<ApplicationFormDTO> getPagedApplicationFormDTO(ApplicationFormSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                  \n");
        SQL.append("  taf.APPLICATION_FORM_ID,                              \n");
        SQL.append("  taf.AF_REF_NUMBER,                                    \n");
        SQL.append("  taf.BRANCH_CODE,                                      \n");
        SQL.append("  taf.CREATED_BY,                                       \n");
        SQL.append("  taf.CREATED_DATE,                                     \n");
        SQL.append("  taf.CREATED_USER_DISPLAY_NAME,                        \n");
        SQL.append("  taf.ASSIGN_USER_DISPLAY_NAME,                         \n");
        SQL.append("  taf.ASSIGN_DEPARTMENT_CODE,                           \n");
        SQL.append("  taf.CURRENT_APP_FORM_STATUS                           \n");
        SQL.append("FROM T_AF_APPLICATION_FORM taf                          \n");
        SQL.append("WHERE APPLICATION_FORM_ID IS NOT NULL                   \n");

        if (StringUtils.isNotEmpty(searchRQ.getAfRefNumber())) {
            SQL.append(" AND taf.AF_REF_NUMBER LIKE '%" + searchRQ.getAfRefNumber() + "%'           \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getBranchCode())) {
            SQL.append(" AND taf.BRANCH_CODE =:branchCode                                           \n");
            params.put("branchCode", searchRQ.getBranchCode());
        }

        if (StringUtils.isNotEmpty(searchRQ.getAssignUserDisplayName())) {
            SQL.append("AND upper(taf.ASSIGN_USER_DISPLAY_NAME) LIKE '%" + searchRQ.getAssignUserDisplayName().toUpperCase() + "%'   \n");
        }

        if (searchRQ.getCurrentApplicationFormStatus() != null) {
            SQL.append(" AND taf.CURRENT_APP_FORM_STATUS =:currentApplicationFormStatus                                             \n");
            params.put("currentApplicationFormStatus", searchRQ.getCurrentApplicationFormStatus().toString());
        }

        if (StringUtils.isNotEmpty(searchRQ.getAssignUser())) {
            SQL.append(" AND taf.ASSIGN_USER =:assignedUser                                         \n");
            params.put("assignedUser", searchRQ.getAssignUser());
        }

        SQL.append(" ORDER BY taf.CREATED_DATE  DESC                                                                                \n");

        return getPagedData(SQL.toString(), params, new RowMapper<ApplicationFormDTO>() {

            @Nullable
            @Override
            public ApplicationFormDTO mapRow(ResultSet rs, int i) throws SQLException {
                ApplicationFormDTO applicationFormDTO = new ApplicationFormDTO();
                applicationFormDTO.setApplicationFormID(rs.getInt("APPLICATION_FORM_ID"));
                applicationFormDTO.setAfRefNumber(rs.getString("AF_REF_NUMBER"));
                applicationFormDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                applicationFormDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                applicationFormDTO.setCreatedBy(rs.getString("CREATED_BY"));
                applicationFormDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                applicationFormDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    applicationFormDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                applicationFormDTO.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.resolve(rs.getString("CURRENT_APP_FORM_STATUS")));
                return applicationFormDTO;
            }

        }, searchRQ);
    }

    public Page<ApplicationFormPageRSDTO> getInboxPagedApplicationForms(ApplicationFormSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();
        params.put("assignedUser", searchRQ.getAssignUser());
        params.put("assignGroupUPMGroupCode", searchRQ.getAssignGroupUPMGroupCode());
        params.put("assignDepartmentCode", searchRQ.getAssignDepartmentCode());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                                                                \n");
        SQL.append("  DISTINCT taf.APPLICATION_FORM_ID,                                                                                                   \n");
        SQL.append("  tc.CUSTOMER_ID,                                                                                                                     \n");
        SQL.append("  tc.CUSTOMER_NAME,                                                                                                                   \n");
        SQL.append("  taf.AF_REF_NUMBER,                                                                                                                  \n");
        SQL.append("  taf.WORKFLOW_TEMPLATE_ID,                                                                                                           \n");
        SQL.append("  taf.BRANCH_CODE,                                                                                                                    \n");
        SQL.append("  taf.CREATED_BY,                                                                                                                     \n");
        SQL.append("  taf.CREATED_DATE,                                                                                                                   \n");
        SQL.append("  taf.CREATED_USER_DISPLAY_NAME,                                                                                                      \n");
        SQL.append("  taf.ASSIGN_USER_DISPLAY_NAME,                                                                                                       \n");
        SQL.append("  taf.CURRENT_APP_FORM_STATUS,                                                                                                        \n");
        SQL.append("  taf.ASSIGN_DEPARTMENT_CODE,                                                                                                         \n");
        SQL.append("  tabi.NAME_WITH_INITIAL,                                                                                                             \n");
        SQL.append("  tabi.NAME_OF_BUSINESS,                                                                                                              \n");
        SQL.append("  tabi.REGISTRATION_NO,                                                                                                               \n");
        SQL.append("  tabi.IDENTIFICATION_NO,                                                                                                             \n");
        SQL.append("  tabi.IDENTIFICATION_TYPE                                                                                                            \n");
        SQL.append("FROM T_AF_APPLICATION_FORM taf                                                                                                        \n");
        SQL.append("  INNER JOIN T_AF_BASIC_INFORMATION TABI                                                                                              \n");
        SQL.append("    ON TAF.APPLICATION_FORM_ID = TABI.APPLICATION_FORM_ID AND tabi.STATUS = 'ACT' AND TABI.PRIMARY_INFORMATION = 'Y'                  \n");
        SQL.append("     LEFT JOIN  T_CUSTOMER tc                                                                                                         \n");
        SQL.append("        ON TABI.CUSTOMER_ID = tc.CUSTOMER_ID AND tc.STATUS='ACT'                                                                      \n");
        SQL.append("   LEFT JOIN T_AF_ASSIGN_DEPARTMENT tad                                                                                               \n");
        SQL.append("   ON taf.APPLICATION_FORM_ID = tad.APPLICATION_FORM_ID AND tc.STATUS='ACT'                                                           \n");
        SQL.append("WHERE taf.APPLICATION_FORM_ID IS NOT NULL                                                                                             \n");
        SQL.append("      AND (taf.ASSIGN_USER =:assignedUser                                                                                             \n");
        SQL.append("           OR (taf.ASSIGN_DEPARTMENT_CODE =:assignDepartmentCode AND tad.USER_GROUP_UPM_CODE =:assignGroupUPMGroupCode AND tad.STATUS='ACT')) \n");

        if (StringUtils.isNotEmpty(searchRQ.getAfRefNumber())) {
            SQL.append(" AND taf.AF_REF_NUMBER LIKE '%" + searchRQ.getAfRefNumber() + "%'                                               \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getBranchCode())) {
            SQL.append(" AND taf.BRANCH_CODE =:branchCode                                                                               \n");
            params.put("branchCode", searchRQ.getBranchCode());
        }

        if (StringUtils.isNotEmpty(searchRQ.getAssignUserDisplayName())) {
            SQL.append("AND upper(taf.ASSIGN_USER_DISPLAY_NAME) LIKE '%" + searchRQ.getAssignUserDisplayName().toUpperCase() + "%'      \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getIdentificationNumber())) {
            SQL.append("AND upper(tabi.IDENTIFICATION_NO) LIKE '%" + searchRQ.getIdentificationNumber().toUpperCase() + "%'              \n");
        }

        if (searchRQ.getCurrentApplicationFormStatus() != null) {
            SQL.append(" AND taf.CURRENT_APP_FORM_STATUS =:currentApplicationFormStatus                                                 \n");
            params.put("currentApplicationFormStatus", searchRQ.getCurrentApplicationFormStatus().toString());
        }

        SQL.append(" ORDER BY taf.CREATED_DATE  DESC                                                                                    \n");

        return getPagedData(SQL.toString(), params, new RowMapper<ApplicationFormPageRSDTO>() {

            @Nullable
            @Override
            public ApplicationFormPageRSDTO mapRow(ResultSet rs, int i) throws SQLException {
                ApplicationFormPageRSDTO applicationFormDTO = new ApplicationFormPageRSDTO();
                applicationFormDTO.setApplicationFormID(rs.getInt("APPLICATION_FORM_ID"));
                applicationFormDTO.setAfRefNumber(rs.getString("AF_REF_NUMBER"));
                applicationFormDTO.setWorkflowTemplateID(rs.getInt("WORKFLOW_TEMPLATE_ID"));
                applicationFormDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                applicationFormDTO.setNameWithInitials(rs.getString("NAME_WITH_INITIAL"));
                applicationFormDTO.setNameOfBusiness(rs.getString("NAME_OF_BUSINESS"));
                applicationFormDTO.setRegistrationNo(rs.getString("REGISTRATION_NO"));
                applicationFormDTO.setIdentificationNumber(rs.getString("IDENTIFICATION_NO"));
                applicationFormDTO.setIdentificationType(DomainConstants.CustomerIdentificationType.resolveStatus(rs.getString("IDENTIFICATION_TYPE")));
                applicationFormDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                applicationFormDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                applicationFormDTO.setCreatedBy(rs.getString("CREATED_BY"));
                applicationFormDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                applicationFormDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));

                if (rs.getTimestamp("CREATED_DATE") != null) {
                    applicationFormDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                applicationFormDTO.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.resolve(rs.getString("CURRENT_APP_FORM_STATUS")));
                return applicationFormDTO;
            }

        }, searchRQ);
    }

    public Page<ApplicationFormPageRSDTO> getPagedBranchApplicationForm(ApplicationFormSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();
        params.put("assignedUser", searchRQ.getAssignUser());
        params.put("assignGroupUPMGroupCode", searchRQ.getAssignGroupUPMGroupCode());
        params.put("assignDepartmentCode", searchRQ.getAssignDepartmentCode());
        params.put("branchCode", searchRQ.getBranchCode());

        StringBuilder SQL = new StringBuilder();

        SQL.append("                                                                                                                                      \n");
        SQL.append("SELECT                                                                                                                                \n");
        SQL.append("  DISTINCT taf.APPLICATION_FORM_ID,                                                                                                   \n");
        SQL.append("  tc.CUSTOMER_ID,                                                                                                                     \n");
        SQL.append("  tc.CUSTOMER_NAME,                                                                                                                   \n");
        SQL.append("  taf.AF_REF_NUMBER,                                                                                                                  \n");
        SQL.append("  taf.WORKFLOW_TEMPLATE_ID,                                                                                                           \n");
        SQL.append("  taf.BRANCH_CODE,                                                                                                                    \n");
        SQL.append("  taf.CREATED_BY,                                                                                                                     \n");
        SQL.append("  taf.CREATED_DATE,                                                                                                                   \n");
        SQL.append("  taf.CREATED_USER_DISPLAY_NAME,                                                                                                      \n");
        SQL.append("  taf.ASSIGN_USER_DISPLAY_NAME,                                                                                                       \n");
        SQL.append("  taf.CURRENT_APP_FORM_STATUS,                                                                                                        \n");
        SQL.append("  taf.ASSIGN_DEPARTMENT_CODE,                                                                                                         \n");
        SQL.append("  tabi.NAME_WITH_INITIAL,                                                                                                             \n");
        SQL.append("  tabi.NAME_OF_BUSINESS,                                                                                                              \n");
        SQL.append("  tabi.REGISTRATION_NO,                                                                                                               \n");
        SQL.append("  tabi.IDENTIFICATION_NO,                                                                                                             \n");
        SQL.append("  tabi.IDENTIFICATION_TYPE                                                                                                            \n");
        SQL.append("FROM T_AF_APPLICATION_FORM taf                                                                                                        \n");
        SQL.append("  INNER JOIN T_AF_BASIC_INFORMATION TABI                                                                                              \n");
        SQL.append("    ON TAF.APPLICATION_FORM_ID = TABI.APPLICATION_FORM_ID AND tabi.STATUS = 'ACT' AND TABI.PRIMARY_INFORMATION = 'Y'                  \n");
        SQL.append("     LEFT JOIN  T_CUSTOMER tc                                                                                                         \n");
        SQL.append("        ON TABI.CUSTOMER_ID = tc.CUSTOMER_ID AND tc.STATUS='ACT'                                                                      \n");
        SQL.append("   LEFT JOIN T_AF_ASSIGN_DEPARTMENT tad                                                                                               \n");
        SQL.append("   ON taf.APPLICATION_FORM_ID = tad.APPLICATION_FORM_ID AND tc.STATUS='ACT'                                                           \n");
        SQL.append("WHERE taf.APPLICATION_FORM_ID IS NOT NULL                                                                                             \n");
        SQL.append("      AND taf.BRANCH_CODE =:branchCode                                                                                                \n");

        if (StringUtils.isNotEmpty(searchRQ.getAfRefNumber())) {
            SQL.append(" AND taf.AF_REF_NUMBER LIKE '%" + searchRQ.getAfRefNumber() + "%'                                               \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getAssignUserDisplayName())) {
            SQL.append("AND upper(taf.ASSIGN_USER_DISPLAY_NAME) LIKE '%" + searchRQ.getAssignUserDisplayName().toUpperCase() + "%'      \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getIdentificationNumber())) {
            SQL.append("AND upper(tabi.IDENTIFICATION_NO) LIKE '%" + searchRQ.getIdentificationNumber().toUpperCase() + "%'              \n");
        }

        if (searchRQ.getCurrentApplicationFormStatus() != null) {
            SQL.append(" AND taf.CURRENT_APP_FORM_STATUS =:currentApplicationFormStatus                                                 \n");
            params.put("currentApplicationFormStatus", searchRQ.getCurrentApplicationFormStatus().toString());
        }

        SQL.append(" ORDER BY taf.CREATED_DATE  DESC                                                                                    \n");

        return getPagedData(SQL.toString(), params, new RowMapper<ApplicationFormPageRSDTO>() {

            @Nullable
            @Override
            public ApplicationFormPageRSDTO mapRow(ResultSet rs, int i) throws SQLException {
                ApplicationFormPageRSDTO applicationFormDTO = new ApplicationFormPageRSDTO();
                applicationFormDTO.setApplicationFormID(rs.getInt("APPLICATION_FORM_ID"));
                applicationFormDTO.setAfRefNumber(rs.getString("AF_REF_NUMBER"));
                applicationFormDTO.setWorkflowTemplateID(rs.getInt("WORKFLOW_TEMPLATE_ID"));
                applicationFormDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                applicationFormDTO.setNameWithInitials(rs.getString("NAME_WITH_INITIAL"));
                applicationFormDTO.setNameOfBusiness(rs.getString("NAME_OF_BUSINESS"));
                applicationFormDTO.setRegistrationNo(rs.getString("REGISTRATION_NO"));
                applicationFormDTO.setIdentificationNumber(rs.getString("IDENTIFICATION_NO"));
                applicationFormDTO.setIdentificationType(DomainConstants.CustomerIdentificationType.resolveStatus(rs.getString("IDENTIFICATION_TYPE")));
                applicationFormDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                applicationFormDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                applicationFormDTO.setCreatedBy(rs.getString("CREATED_BY"));
                applicationFormDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                applicationFormDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));

                if (rs.getTimestamp("CREATED_DATE") != null) {
                    applicationFormDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                applicationFormDTO.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.resolve(rs.getString("CURRENT_APP_FORM_STATUS")));
                return applicationFormDTO;
            }

        }, searchRQ);
    }

    public List<AFTopicDataDTO> getApplicationFormTopics(ApplicationFormTopicRQ applicationFormTopicRQ) {

        List<AFTopicDataDTO> resultList = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("applicationFormID", applicationFormTopicRQ.getApplicationFormID());
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT                                                                                                      \n");
        SQL.append("   TATD.APPLICATION_FORM_ID,                                                                                 \n");
        SQL.append("   TAT.AF_TOPIC_ID,                                                                                          \n");
        SQL.append("   TAT.TOPIC,                                                                                                \n");
        SQL.append("   TAT.TOPIC_CODE,                                                                                           \n");
        SQL.append("   TATD.TOPIC_DATA                                                                                           \n");
        SQL.append(" FROM T_AF_TOPIC_DATA TATD INNER JOIN T_AF_TOPIC TAT ON TATD.TOPIC_ID = TAT.AF_TOPIC_ID                      \n");
        SQL.append(" INNER JOIN T_AF_APPLICATION_FORM ATAF ON TATD.APPLICATION_FORM_ID = ATAF.APPLICATION_FORM_ID                \n");
        SQL.append(" WHERE TATD.APPLICATION_FORM_ID =:applicationFormID                                                          \n");
        SQL.append(" AND TATD.PAGE IN (" + QueryInBuilder.buildSQLINQuery(applicationFormTopicRQ.getPageList()) + ")             \n");
        SQL.append("       AND TATD.STATUS = 'ACT'                                                                               \n");
        SQL.append("       AND TAT.APPROVE_STATUS = 'APPROVED'                                                                   \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<AFTopicDataDTO>>() {

            @Override
            public List<AFTopicDataDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    AFTopicDataDTO topicDataDTO = new AFTopicDataDTO();
                    topicDataDTO.setApplicationFormID(rs.getInt("APPLICATION_FORM_ID"));
                    topicDataDTO.setTopicID(rs.getInt("AF_TOPIC_ID"));
                    topicDataDTO.setTopic(rs.getString("TOPIC"));
                    topicDataDTO.setTopicCode(rs.getString("TOPIC_CODE"));
                    topicDataDTO.setTopicData(rs.getString("TOPIC_DATA"));
                    resultList.add(topicDataDTO);
                }
                return resultList;
            }

        });
    }

    public List<AFStatusHistoryDTO> getAFReturnUsersList(ApplicationFormDTO applicationFormDTO) throws AppsException {

        List<AFStatusHistoryDTO> responseList = new ArrayList<>();
        //This addedUsersIDList is used to restrict duplicates and Return Users and Order by Updated Date
        List<Integer> addedUsersIDList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("applicationFormID", applicationFormDTO.getApplicationFormID());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                                               \n");
        SQL.append("  ASSIGN_USER,                                                                                                       \n");
        SQL.append("  ASSIGN_USER_ID,                                                                                                    \n");
        SQL.append("  ASSIGN_USER_DISPLAY_NAME,                                                                                          \n");
        SQL.append("  ASSIGN_USER_UPM_ID,                                                                                                \n");
        SQL.append("  ASSIGN_USER_DIV_CODE,                                                                                                \n");
        SQL.append("  ASSIGN_USER_UPM_GROUP_CODE                                                                                         \n");
        SQL.append("FROM T_AF_STATUS_HISTORY tafht                                                                                       \n");
        SQL.append("  WHERE tafht.APPLICATION_FORM_ID IS NOT NULL AND ASSIGN_USER_ID IS NOT NULL AND ASSIGN_USER_DIV_CODE IS NOT NULL      \n");
        SQL.append("AND tafht.APPLICATION_FORM_ID = :applicationFormID                                                                   \n");
        SQL.append("ORDER BY tafht.UPDATED_DATE  DESC                                                                                    \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<AFStatusHistoryDTO>>() {
            @Override
            public List<AFStatusHistoryDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()) {
                    //This If Condition is to restrict adding Duplicates cause quarry returns duplicates : order by and group by does not work with proper order by updated date
                    if (StringUtils.isNotEmpty(resultSet.getString("ASSIGN_USER_ID")) && !addedUsersIDList.contains(resultSet.getInt("ASSIGN_USER_ID"))) {
                        AFStatusHistoryDTO afStatusHistoryDTO = new AFStatusHistoryDTO();
                        afStatusHistoryDTO.setAssignUserID(resultSet.getInt("ASSIGN_USER_ID"));
                        afStatusHistoryDTO.setAssignUser(resultSet.getString("ASSIGN_USER"));
                        afStatusHistoryDTO.setAssignUserDisplayName(resultSet.getString("ASSIGN_USER_DISPLAY_NAME"));
                        afStatusHistoryDTO.setAssignUserUpmID(resultSet.getInt("ASSIGN_USER_UPM_ID"));
                        afStatusHistoryDTO.setAssignUserUpmGroupCode(resultSet.getString("ASSIGN_USER_UPM_GROUP_CODE"));
                        afStatusHistoryDTO.setAssignUserDivCode(resultSet.getString("ASSIGN_USER_DIV_CODE"));
                        responseList.add(afStatusHistoryDTO);
                        addedUsersIDList.add(resultSet.getInt("ASSIGN_USER_ID"));
                    }
                }
                return responseList;
            }
        });
    }

    public Page<ApplicationFormCopyRSDTO> getCopyPagedApplicationForms(ApplicationFormSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                            \n");
        SQL.append("  taf.APPLICATION_FORM_ID,                                                                        \n");
        SQL.append("  taf.AF_REF_NUMBER,                                                                              \n");
        SQL.append("  taf.BRANCH_CODE,                                                                                \n");
        SQL.append("  taf.CREATED_BY,                                                                                 \n");
        SQL.append("  taf.CREATED_DATE,                                                                               \n");
        SQL.append("  taf.CREATED_USER_DISPLAY_NAME,                                                                  \n");
        SQL.append("  taf.ASSIGN_USER_DISPLAY_NAME,                                                                   \n");
        SQL.append("  taf.CURRENT_APP_FORM_STATUS,                                                                    \n");
        SQL.append("  tabi.REGISTRATION_NO,                                                                           \n");
        SQL.append("  tabi.PRIMARY_INFORMATION,                                                                       \n");
        SQL.append("  tcid.IDENTIFICATION_NUMBER,                                                                     \n");
        SQL.append("  tcid.IDENTIFICATION_TYPE,                                                                       \n");
        SQL.append("  tc.CUSTOMER_ID,                                                                                 \n");
        SQL.append("  tc.CUSTOMER_NAME,                                                                               \n");
        SQL.append("  tc.CUSTOMER_FINANCIAL_ID,                                                                       \n");
        SQL.append("  tabi.AF_CUSTOMER_ID                                                                             \n");
        SQL.append("FROM T_AF_APPLICATION_FORM taf                                                                    \n");
        SQL.append("INNER JOIN T_AF_BASIC_INFORMATION tabi ON taf.APPLICATION_FORM_ID = tabi.APPLICATION_FORM_ID      \n");
        SQL.append(" INNER JOIN  T_AF_CUSTOMER tac ON tabi.AF_CUSTOMER_ID = tac.AF_CUSTOMER_ID                        \n");
        SQL.append(" INNER JOIN  T_CUSTOMER tc ON tac.CUSTOMER_ID = tc.CUSTOMER_ID                                    \n");
        SQL.append(" INNER JOIN T_CUSTOMER_IDENTIFICATION tcid on tc.CUSTOMER_ID = tcid.CUSTOMER_ID                   \n");
        SQL.append("WHERE taf.APPLICATION_FORM_ID IS NOT NULL                                                         \n");

        if (StringUtils.isNotEmpty(searchRQ.getAfRefNumber())) {
            SQL.append(" AND taf.AF_REF_NUMBER LIKE '%" + searchRQ.getAfRefNumber() + "%'                                           \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getIdentificationNumber())) {
            SQL.append(" AND tcid.IDENTIFICATION_NUMBER LIKE '%" + searchRQ.getIdentificationNumber() + "%'                         \n");

            if (searchRQ.getIdentificationType() != null && StringUtils.isNotEmpty(searchRQ.getIdentificationType().name())) {
                SQL.append(" AND tcid.IDENTIFICATION_TYPE =:identificationType                                                      \n");
                params.put("identificationType", searchRQ.getIdentificationType().name());
            }
        }
        SQL.append(" ORDER BY taf.CREATED_DATE  DESC                                                                                \n");

        return getPagedData(SQL.toString(), params, new RowMapper<ApplicationFormCopyRSDTO>() {

            @Nullable
            @Override
            public ApplicationFormCopyRSDTO mapRow(ResultSet rs, int i) throws SQLException {
                ApplicationFormCopyRSDTO applicationFormCopyRSDTO = new ApplicationFormCopyRSDTO();
                applicationFormCopyRSDTO.setApplicationFormID(rs.getInt("APPLICATION_FORM_ID"));
                applicationFormCopyRSDTO.setAfRefNumber(rs.getString("AF_REF_NUMBER"));
                applicationFormCopyRSDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                applicationFormCopyRSDTO.setCreatedBy(rs.getString("CREATED_BY"));
                applicationFormCopyRSDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                applicationFormCopyRSDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                applicationFormCopyRSDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                applicationFormCopyRSDTO.setPrimaryInformation(AppsConstants.YesNo.valueOf(rs.getString("PRIMARY_INFORMATION")));

                if (rs.getTimestamp("CREATED_DATE") != null) {
                    applicationFormCopyRSDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                applicationFormCopyRSDTO.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.resolve(rs.getString("CURRENT_APP_FORM_STATUS")));
                return applicationFormCopyRSDTO;
            }

        }, searchRQ);
    }

    public Set<ApplicationFormPageRSDTO> getApplicationFormsForTransfer(ApplicationFormTransferRQ applicationFormTransferRQ, boolean isBranchLevelPaperTransfer,  String[] upmGroups) {

        Set<ApplicationFormPageRSDTO> responseList = new HashSet<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        LOG.info("applicationFormTransferRQ : {}", applicationFormTransferRQ);

        SQL.append("SELECT                                                                                                                \n");
        SQL.append("  DISTINCT                                                                                                            \n");
        SQL.append("  taf.APPLICATION_FORM_ID,                                                                                            \n");
        SQL.append("  tc.CUSTOMER_ID,                                                                                                     \n");
        SQL.append("  tc.CUSTOMER_NAME,                                                                                                   \n");
        SQL.append("  taf.AF_REF_NUMBER,                                                                                                  \n");
        SQL.append("  taf.WORKFLOW_TEMPLATE_ID,                                                                                           \n");
        SQL.append("  taf.BRANCH_CODE,                                                                                                    \n");
        SQL.append("  taf.CREATED_BY,                                                                                                     \n");
        SQL.append("  taf.CREATED_DATE,                                                                                                   \n");
        SQL.append("  taf.CREATED_USER_DISPLAY_NAME,                                                                                      \n");
        SQL.append("  taf.ASSIGN_USER_DISPLAY_NAME,                                                                                       \n");
        SQL.append("  taf.CURRENT_APP_FORM_STATUS,                                                                                        \n");
        SQL.append("  taf.ASSIGN_DEPARTMENT_CODE,                                                                                         \n");
        SQL.append("  tabi.NAME_WITH_INITIAL,                                                                                             \n");
        SQL.append("  tabi.NAME_OF_BUSINESS,                                                                                              \n");
        SQL.append("  tabi.REGISTRATION_NO,                                                                                               \n");
        SQL.append("  tabi.IDENTIFICATION_NO,                                                                                             \n");
        SQL.append("  tabi.IDENTIFICATION_TYPE                                                                                            \n");
        SQL.append("FROM T_AF_APPLICATION_FORM taf                                                                                        \n");
        SQL.append("  INNER JOIN T_AF_BASIC_INFORMATION TABI                                                                              \n");
        SQL.append("    ON TAF.APPLICATION_FORM_ID = TABI.APPLICATION_FORM_ID AND tabi.STATUS = 'ACT' AND TABI.PRIMARY_INFORMATION = 'Y'  \n");
        SQL.append("  LEFT JOIN T_CUSTOMER tc                                                                                             \n");
        SQL.append("    ON TABI.CUSTOMER_ID = tc.CUSTOMER_ID AND tc.STATUS = 'ACT'                                                        \n");
        SQL.append("  LEFT JOIN T_AF_ASSIGN_DEPARTMENT tad                                                                                \n");
        SQL.append("    ON taf.APPLICATION_FORM_ID = tad.APPLICATION_FORM_ID AND tc.STATUS = 'ACT'                                        \n");
        SQL.append("WHERE taf.APPLICATION_FORM_ID IS NOT NULL                                                                             \n");
      //  SQL.append("AND taf.ASSIGN_USER_UPM_GROUP_CODE IN (" + QueryInBuilder.buildSQLINQuery(applicationFormTransferRQ.getOtherUpmAccessLevels()) + ")     \n");
        SQL.append("AND taf.ASSIGN_USER_UPM_GROUP_CODE IN (" + QueryInBuilder.buildSQLINQuery(upmGroups) + ")     \n");
        SQL.append("AND upper(taf.AF_REF_NUMBER) LIKE '%" + applicationFormTransferRQ.getAfRefNumber().toUpperCase() + "%'                                  \n");
        SQL.append(" AND taf.CURRENT_APP_FORM_STATUS IN ('IN_PROGRESS','RETURNED')                                                            \n");
      // SQL.append(" AND taf.CURRENT_APP_FORM_STATUS NOT IN ('DECLINED','DRAFT')                                                                            \n");

        if (isBranchLevelPaperTransfer) {
            SQL.append("AND taf.ASSIGN_USER_DIV_CODE =:branchCode    \n");
            params.put("branchCode", applicationFormTransferRQ.getLoggedInUserBranchCode());
        }

        LOG.info("Paper Transfer SQL : {}", SQL.toString());

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Set<ApplicationFormPageRSDTO>>() {
            @Override
            public Set<ApplicationFormPageRSDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ApplicationFormPageRSDTO applicationFormPageRSDTO = new ApplicationFormPageRSDTO();
                    applicationFormPageRSDTO.setApplicationFormID(rs.getInt("APPLICATION_FORM_ID"));
                    applicationFormPageRSDTO.setAfRefNumber(rs.getString("AF_REF_NUMBER"));
                    applicationFormPageRSDTO.setWorkflowTemplateID(rs.getInt("WORKFLOW_TEMPLATE_ID"));
                    applicationFormPageRSDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                    applicationFormPageRSDTO.setNameWithInitials(rs.getString("NAME_WITH_INITIAL"));
                    applicationFormPageRSDTO.setNameOfBusiness(rs.getString("NAME_OF_BUSINESS"));
                    applicationFormPageRSDTO.setRegistrationNo(rs.getString("REGISTRATION_NO"));
                    applicationFormPageRSDTO.setIdentificationNumber(rs.getString("IDENTIFICATION_NO"));
                    applicationFormPageRSDTO.setIdentificationType(DomainConstants.CustomerIdentificationType.resolveStatus(rs.getString("IDENTIFICATION_TYPE")));
                    applicationFormPageRSDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                    applicationFormPageRSDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                    applicationFormPageRSDTO.setCreatedBy(rs.getString("CREATED_BY"));
                    applicationFormPageRSDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                    applicationFormPageRSDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));

                    if (rs.getTimestamp("CREATED_DATE") != null) {
                        applicationFormPageRSDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                    }
                    applicationFormPageRSDTO.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.resolve(rs.getString("CURRENT_APP_FORM_STATUS")));
                    responseList.add(applicationFormPageRSDTO);
                }
                return responseList;
            }
        });
    }
    public ApplicationFormDashboardCountDTO getApplicationFormDashboardCount(ApplicationFormDashboardRQ applicationFormDashboardRQ, Integer dashboardTimePeriodDays) {
        ApplicationFormDashboardCountDTO countDTO = new ApplicationFormDashboardCountDTO();
        Map<String, Object> params = new HashMap<>();

        params.put("userId", applicationFormDashboardRQ.getLoggedInUserId());
        params.put("branchCode", applicationFormDashboardRQ.getLoggedInUserBranchCode());
        params.put("workClass", applicationFormDashboardRQ.getLoginUpmAccessLevel());
        params.put("timePeriod", dashboardTimePeriodDays);
        params.put("dashboardType", "APPLICATION_FORM");

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT STATUS, COUNT FROM TABLE( CASV2_DASHBOARD.GET_ALL_DASHBOARD_COUNTS ");
        SQL.append(" (:userId, :branchCode, :dashboardType, :workClass, :timePeriod)) ");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<ApplicationFormDashboardCountDTO>() {
            @Override
            public ApplicationFormDashboardCountDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<String, Consumer<Integer>> statusSetterMap = new HashMap<>();
                statusSetterMap.put("INBOX", count -> countDTO.setInboxApplicationForm(count));
                statusSetterMap.put("IN_PROGRESS", count -> countDTO.setInProgressApplicationForm(count));
                statusSetterMap.put("DECLINED", count -> countDTO.setDeclinedApplicationForm(count));
                statusSetterMap.put("RETURNED", count -> countDTO.setReturnedApplicationForm(count));
                statusSetterMap.put("ACCEPTED", count -> countDTO.setAcceptedApplicationForm(count));
                statusSetterMap.put("PAPER_APPROVED", count -> countDTO.setPaperApprovedApplicationForm(count));

                while (rs.next()) {
                    String status = rs.getString("STATUS");
                    int count = rs.getInt("COUNT");
                    Consumer<Integer> setter = statusSetterMap.get(status);
                    if (setter != null) {
                        setter.accept(count);
                    }
                }
                return countDTO;
            }
        });
    }

    public Page<ApplicationFormDashboardDTO> getPagedApplicationFormDashboardDTO(ApplicationFormDashboardRQ applicationFormDashboardRQ, Integer dashboardTimePeriodDays) {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", applicationFormDashboardRQ.getLoggedInUserId());
        params.put("branchCode", applicationFormDashboardRQ.getLoggedInUserBranchCode());
        params.put("workClass", applicationFormDashboardRQ.getLoginUpmAccessLevel());
        params.put("timePeriod", dashboardTimePeriodDays);
        params.put("dashboardType", "APPLICATION_FORM");
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT DASHBOARD_TYPE, DASHBOARD_STATUS, DASHBOARD_SUB_STATUS, ID, REFERENCE_NAME, CUSTOMER_NAME, \n");
        SQL.append(" AF_REFERENCE_NUMBER, FP_REFERENCE_NUMBER, LEAD_REFERENCE_NUMBER, BRANCH_CODE, BRANCH_NAME, ACCOUNT_NUMBER, \n");
        SQL.append(" IDENTIFICATION_NUMBER, CREATED_DATE, CREATED_BY, ASSIGNED_USER, STATUS, FROM_DATE, ASSIGNED_DIV_CODE \n");

        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("DRAFT")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_DRAFT_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("RETURNED_TO_ME")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_RETURNED_TO_ME_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("RECEIVED_TO_ME")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_RECEIVED_TO_ME_LIST(:userId, :branchCode, :dashboardType, :workClass)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("IN_PROGRESS")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_IN_PROGRESS_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("DECLINED_BY_ME")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_DECLINED_BY_ME_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("DECLINED_BY_OTHERS")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_DECLINED_BY_OTHERS_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("RETURNED_BY_ME")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_RETURNED_BY_ME_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("RETURNED_BY_OTHERS")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_RETURNED_BY_OTHERS_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("PAPER_CREATED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_PAPER_CREATED_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("PAPER_RETURNED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_PAPER_RETURNED_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("PAPER_DECLINED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_PAPER_DECLINED_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }
        if (applicationFormDashboardRQ.getApplicationFormDashboardSubStatus().equals("PAPER_APPROVED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_PAPER_APPROVED_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }

        System.out.println(SQL.toString());

        return getPagedData(SQL.toString(), params, new RowMapper<ApplicationFormDashboardDTO>() {

            @Nullable
            @Override
            public ApplicationFormDashboardDTO mapRow(ResultSet rs, int i) throws SQLException , DataAccessException{
                ApplicationFormDashboardDTO docDTO = new ApplicationFormDashboardDTO();
                docDTO.setDashboardType(rs.getString("DASHBOARD_TYPE"));
                docDTO.setDashboardStatus(rs.getString("DASHBOARD_STATUS"));
                docDTO.setDashboardSubStatus(rs.getString("DASHBOARD_SUB_STATUS"));
                docDTO.setId(rs.getInt("ID"));
                docDTO.setReferenceName(rs.getString("REFERENCE_NAME"));
                docDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                docDTO.setLeadReferenceNumber(rs.getString("LEAD_REFERENCE_NUMBER"));
                docDTO.setAfReferenceNumber(rs.getString("AF_REFERENCE_NUMBER"));
                docDTO.setFpReferenceNumber(rs.getString("FP_REFERENCE_NUMBER"));
                docDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                docDTO.setBranchName(rs.getString("BRANCH_NAME"));
                docDTO.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
                docDTO.setIdentificationNumber(rs.getString("IDENTIFICATION_NUMBER"));
                docDTO.setCreatedDate(rs.getString("CREATED_DATE"));
                docDTO.setCreatedBy(rs.getString("CREATED_BY"));
                docDTO.setAssignedUser(rs.getString("ASSIGNED_USER"));
                docDTO.setStatus(rs.getString("STATUS"));
                docDTO.setFromDate(rs.getString("FROM_DATE"));
                docDTO.setAssignedDivCode(rs.getString("ASSIGNED_DIV_CODE"));

                return docDTO;
            }
        }, applicationFormDashboardRQ);
    }
    public Page<ApplicationFormPageRSDTO> getPagedSearchApplicationForm(SearchApplicationFormRQ searchRQ) throws AppsException{
        Map<String, Object> params = new HashMap<>();
        params.put("assignedUser", searchRQ.getAssignUser());
        params.put("assignGroupUPMGroupCode", searchRQ.getAssignGroupUPMGroupCode());
        params.put("assignDepartmentCode", searchRQ.getAssignDepartmentCode());
        params.put("branchCode", searchRQ.getBranchCode());

        StringBuilder SQL = new StringBuilder();

        SQL.append("                                                                                                                                      \n");
        SQL.append("SELECT                                                                                                                                \n");
        SQL.append("  DISTINCT taf.APPLICATION_FORM_ID,                                                                                                   \n");
        SQL.append("  tc.CUSTOMER_ID,                                                                                                                     \n");
        SQL.append("  tc.CUSTOMER_NAME,                                                                                                                   \n");
        SQL.append("  taf.AF_REF_NUMBER,                                                                                                                  \n");
        SQL.append("  taf.WORKFLOW_TEMPLATE_ID,                                                                                                           \n");
        SQL.append("  taf.BRANCH_CODE,                                                                                                                    \n");
        SQL.append("  taf.CREATED_BY,                                                                                                                     \n");
        SQL.append("  taf.CREATED_DATE,                                                                                                                   \n");
        SQL.append("  taf.CREATED_USER_DISPLAY_NAME,                                                                                                      \n");
        SQL.append("  taf.ASSIGN_USER_DISPLAY_NAME,                                                                                                       \n");
        SQL.append("  taf.CURRENT_APP_FORM_STATUS,                                                                                                        \n");
        SQL.append("  taf.ASSIGN_DEPARTMENT_CODE,                                                                                                         \n");
        SQL.append("  tabi.NAME_WITH_INITIAL,                                                                                                             \n");
        SQL.append("  tabi.NAME_OF_BUSINESS,                                                                                                              \n");
        SQL.append("  tabi.REGISTRATION_NO,                                                                                                               \n");
        SQL.append("  tabi.IDENTIFICATION_NO,                                                                                                             \n");
        SQL.append("  tabi.IDENTIFICATION_TYPE                                                                                                            \n");
        SQL.append("FROM T_AF_APPLICATION_FORM taf                                                                                                        \n");
        SQL.append("  INNER JOIN T_AF_BASIC_INFORMATION TABI                                                                                              \n");
        SQL.append("    ON TAF.APPLICATION_FORM_ID = TABI.APPLICATION_FORM_ID AND tabi.STATUS = 'ACT' AND TABI.PRIMARY_INFORMATION = 'Y'                  \n");
        SQL.append("     LEFT JOIN  T_CUSTOMER tc                                                                                                         \n");
        SQL.append("        ON TABI.CUSTOMER_ID = tc.CUSTOMER_ID AND tc.STATUS='ACT'                                                                      \n");
        SQL.append("   LEFT JOIN T_AF_ASSIGN_DEPARTMENT tad                                                                                               \n");
        SQL.append("   ON taf.APPLICATION_FORM_ID = tad.APPLICATION_FORM_ID AND tc.STATUS='ACT'                                                           \n");
        SQL.append("WHERE taf.APPLICATION_FORM_ID IS NOT NULL                                                                                             \n");
        //       SQL.append("      AND taf.BRANCH_CODE =:branchCode                                                                                                \n");

        if (StringUtils.isNotEmpty(searchRQ.getAfRefNumber())) {
            SQL.append(" AND taf.AF_REF_NUMBER LIKE '%" + searchRQ.getAfRefNumber() + "%'                                               \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getAssignUserDisplayName())) {
            SQL.append("AND upper(taf.ASSIGN_USER_DISPLAY_NAME) LIKE '%" + searchRQ.getAssignUserDisplayName().toUpperCase() + "%'      \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getIdentificationNumber())) {
            SQL.append("AND upper(tabi.IDENTIFICATION_NO) LIKE '%" + searchRQ.getIdentificationNumber().toUpperCase() + "%'              \n");
        }

        if (searchRQ.getCurrentApplicationFormStatus() != null) {
            SQL.append(" AND taf.CURRENT_APP_FORM_STATUS =:currentApplicationFormStatus                                                 \n");
            params.put("currentApplicationFormStatus", searchRQ.getCurrentApplicationFormStatus().toString());
        }

        if (StringUtils.isNotEmpty(searchRQ.getCreatedFromDateStr())) {
            SQL.append("        AND taf.CREATED_DATE >= :createdFromDateStr \n");
            params.put("createdFromDateStr", CalendarUtil.getParsedStartDateTime(searchRQ.getCreatedFromDateStr()));

        }
        if (StringUtils.isNotEmpty(searchRQ.getCreatedToDateStr())) {
            SQL.append("        AND taf.CREATED_DATE <= :createdToDateStr \n");
            params.put("createdToDateStr", CalendarUtil.getParsedEndDateTime(searchRQ.getCreatedToDateStr()));
        }

        SQL.append(" ORDER BY taf.CREATED_DATE  DESC                                                                                    \n");

        return getPagedData(SQL.toString(), params, new RowMapper<ApplicationFormPageRSDTO>() {

            @Nullable
            @Override
            public ApplicationFormPageRSDTO mapRow(ResultSet rs, int i) throws SQLException {
                ApplicationFormPageRSDTO applicationFormDTO = new ApplicationFormPageRSDTO();
                applicationFormDTO.setApplicationFormID(rs.getInt("APPLICATION_FORM_ID"));
                applicationFormDTO.setAfRefNumber(rs.getString("AF_REF_NUMBER"));
                applicationFormDTO.setWorkflowTemplateID(rs.getInt("WORKFLOW_TEMPLATE_ID"));
                applicationFormDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                applicationFormDTO.setNameWithInitials(rs.getString("NAME_WITH_INITIAL"));
                applicationFormDTO.setNameOfBusiness(rs.getString("NAME_OF_BUSINESS"));
                applicationFormDTO.setRegistrationNo(rs.getString("REGISTRATION_NO"));
                applicationFormDTO.setIdentificationNumber(rs.getString("IDENTIFICATION_NO"));
                applicationFormDTO.setIdentificationType(DomainConstants.CustomerIdentificationType.resolveStatus(rs.getString("IDENTIFICATION_TYPE")));
                applicationFormDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                applicationFormDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                applicationFormDTO.setCreatedBy(rs.getString("CREATED_BY"));
                applicationFormDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                applicationFormDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));

                if (rs.getTimestamp("CREATED_DATE") != null) {
                    applicationFormDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                applicationFormDTO.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.resolve(rs.getString("CURRENT_APP_FORM_STATUS")));
                return applicationFormDTO;
            }

        }, searchRQ);
    }

    public List<CompPartyDataDTO> findPartyByLeadId(Integer leadId) throws AppsException {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("leadId", leadId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT CL.LEAD_PURPOSE, CP.COMP_PARTY_ID, CP.PARTY_TYPE FROM T_COMP_LEAD CL INNER JOIN T_COMP_PARTIES CP ON CL.COMP_LEAD_ID=CP.COMP_LEAD_ID \n");
        SQL.append(" WHERE CL.LEAD_ID = :leadId ORDER BY CP.COMP_PARTY_ID ASC \n");

        return this.namedParameterJdbcTemplate.query(
                SQL.toString(),
                paramsMap,
                (ResultSet rs) -> {
                    List<CompPartyDataDTO> list = new ArrayList<>();
                    while (rs.next()) {
                        CompPartyDataDTO dataRow = new CompPartyDataDTO();

                        dataRow.setLeadPurpose(rs.getString("LEAD_PURPOSE"));
                        dataRow.setCompPartyId(rs.getInt("COMP_PARTY_ID"));
                        dataRow.setPartyType(rs.getString("PARTY_TYPE"));

                        list.add(dataRow);
                    }
                    return list;
                }
        );
    }
}
