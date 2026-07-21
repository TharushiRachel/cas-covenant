package com.itechro.cas.dao.lead.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import com.itechro.cas.model.dto.facility.FacilityOtherFacilityInformationDTO;
import com.itechro.cas.model.dto.facility.FacilityVitalInfoDataDTO;
import com.itechro.cas.model.dto.facilitypaper.FPUpcSectionDataDTO;
import com.itechro.cas.model.dto.lead.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.QueryInBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Repository
public class LeadJdbcDao extends BaseJDBCDao {

    public Page<LeadDTO> getPagedLeadDTO(LeadSearchRQ searchRQ, CredentialsDTO credentialsDTO, boolean loadCustomerSpecifyLead) throws AppsException {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        if (loadCustomerSpecifyLead) {
            SQL = this.getLeadPageDataForCustomerQuery(SQL, searchRQ, params);
        } else {
            SQL = this.getLeadPageDataSQL(SQL, searchRQ, credentialsDTO, params);
        }

        return getPagedData(SQL.toString(), params, new RowMapper<LeadDTO>() {

            @Nullable
            @Override
            public LeadDTO mapRow(ResultSet rs, int i) throws SQLException {
                LeadDTO docDTO = new LeadDTO();
                docDTO.setLeadID(rs.getInt("LEAD_ID"));
                docDTO.setLeadRefNumber(rs.getString("LEAD_REF_NUMBER"));
                docDTO.setLeadName(rs.getString("LEAD_NAME"));
                docDTO.setName(rs.getString("NAME"));
                docDTO.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
                docDTO.setAddress(rs.getString("ADDRESS"));
                docDTO.setAssignUserID(rs.getString("ASSIGN_USER_ID"));
                docDTO.setCivilStatus(DomainConstants.CivilStatus.resolveStatus(rs.getString("CIVIL_STATUS")));
                docDTO.setIsCompLead(AppsConstants.YesNo.resolver(rs.getString("IS_COMP_LEAD")));
                docDTO.setLeadStatus(DomainConstants.LeadStatus.resolveStatus(rs.getString("LEAD_STATUS")));
                docDTO.setIdentificationNumber(rs.getString("IDENTIFICATION_NUMBER"));
                docDTO.setIdentificationType(rs.getString("IDENTIFICATION_TYPE"));
                docDTO.setMobileNo(rs.getString("MOBILE_NO"));
                docDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                docDTO.setBranchName(rs.getString("BRANCH_NAME"));
                if (StringUtils.isNotBlank(rs.getString("LEAD_CREATION_TYPE"))) {
                    docDTO.setLeadCreationType(DomainConstants.LeadCreationType.resolveStatus(rs.getString("LEAD_CREATION_TYPE")));
                }
                docDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                if (rs.getTimestamp("DATE_OF_BIRTH") != null) {
                    docDTO.setDateOfBirthStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("DATE_OF_BIRTH")));
                }

                return docDTO;
            }

        }, searchRQ);
    }

    private StringBuilder getLeadPageDataSQL(StringBuilder SQL, LeadSearchRQ searchRQ, CredentialsDTO credentialsDTO, Map<String, Object> params) throws AppsException {

        SQL.append(" SELECT \n");
        SQL.append("   l.LEAD_ID, \n");
        SQL.append("   l.LEAD_NAME, \n");
        SQL.append("   l.LEAD_REF_NUMBER, \n");
        SQL.append("   l.LEAD_STATUS, \n");
        SQL.append("   l.ADDRESS, \n");
        SQL.append("   l.ASSIGN_USER_ID, \n");
        SQL.append("   l.CIVIL_STATUS, \n");
        SQL.append("   l.DATE_OF_BIRTH, \n");
        SQL.append("   l.BRANCH_CODE, \n");
        SQL.append("   l.BRANCH_NAME, \n");
        SQL.append("   l.NAME, \n");
        SQL.append("   l.IDENTIFICATION_NUMBER, \n");
        SQL.append("   l.IDENTIFICATION_TYPE, \n");
        SQL.append("   l.MOBILE_NO, \n");
        SQL.append("   l.IS_COMP_LEAD, \n");
        SQL.append("   l.ACCOUNT_NUMBER, \n");
        SQL.append("   l.NATIONALITY, \n");
        SQL.append("   l.LEAD_CREATION_TYPE, \n");
        SQL.append("   l.CREATED_DATE \n");
        SQL.append(" FROM T_LEAD l \n");
        SQL.append(" WHERE l.LEAD_ID IS NOT NULL \n");

        if (StringUtils.isNotBlank(searchRQ.getLeadName())) {
            SQL.append("       AND UPPER(l.LEAD_NAME) LIKE '%" + searchRQ.getLeadName().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getLeadRefNumber())) {
            SQL.append("       AND l.LEAD_REF_NUMBER LIKE '%" + searchRQ.getLeadRefNumber().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getName())) {
            SQL.append("       AND l.NAME LIKE '%" + searchRQ.getName().trim().toUpperCase() + "%'  \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getAddress())) {
            SQL.append("       AND l.ADDRESS LIKE '%" + searchRQ.getAddress().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getMobileNo())) {
            SQL.append("       AND l.MOBILE_NO LIKE '%" + searchRQ.getMobileNo().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getAccountNumber())) {
            SQL.append("       AND l.ACCOUNT_NUMBER LIKE '%" + searchRQ.getAccountNumber().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getNationality())) {
            SQL.append("       AND l.NATIONALITY LIKE '%" + searchRQ.getNationality().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getIdentificationType())) {
            SQL.append("       AND l.IDENTIFICATION_TYPE LIKE '%" + searchRQ.getIdentificationType().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getIdentificationNumber())) {
            SQL.append("       AND l.IDENTIFICATION_NUMBER LIKE '%" + searchRQ.getIdentificationNumber().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getBranchCode())) {
            SQL.append("       AND l.BRANCH_CODE = :branchCode \n");
            params.put("branchCode", searchRQ.getBranchCode());
        }
        if (searchRQ.getCustomerID() != null) {
            SQL.append("       AND l.CUSTOMER_ID = :customerID \n");
            params.put("customerID", searchRQ.getCustomerID());
        }
        if (StringUtils.isNotBlank(searchRQ.getBranchName())) {
            SQL.append("       AND l.BRANCH_NAME = :branchName \n");
            params.put("branchName", searchRQ.getBranchName());
        }

        if (StringUtils.isNotBlank(searchRQ.getAssignedUserID())) {
            SQL.append("       AND l.ASSIGN_USER_ID = :assignedUserName \n");
            params.put("assignedUserName", searchRQ.getAssignedUserID());
        }

        if (searchRQ.getLeadCreationTypeList() != null && !searchRQ.getLeadCreationTypeList().isEmpty()) {
            SQL.append("       AND l.LEAD_CREATION_TYPE IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getLeadCreationTypeList()) + ") \n");
        }

        if (searchRQ.getLeadStatusList() != null && !searchRQ.getLeadStatusList().isEmpty()) {
            SQL.append("       AND l.LEAD_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getLeadStatusList()) + ") \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getCreatedFromDateStr())) {
            SQL.append("        AND l.CREATED_DATE >= :createdFromDateStr \n");
            params.put("createdFromDateStr", CalendarUtil.getParsedStartDateTime(searchRQ.getCreatedFromDateStr()));

        }
        if (StringUtils.isNotEmpty(searchRQ.getCreatedToDateStr())) {
            SQL.append("        AND l.CREATED_DATE <= :createdToDateStr \n");
            params.put("createdToDateStr", CalendarUtil.getParsedEndDateTime(searchRQ.getCreatedToDateStr()));
        }

        if (credentialsDTO.isAgent()) {
            SQL.append("       AND l.CREATED_BY = :createdBy \n");
            params.put("createdBy", credentialsDTO.getUserName());
        } else {
            if (searchRQ.getIsInMyBranchLeadPage() == null || !searchRQ.getIsInMyBranchLeadPage().getBoolVal()) {
                SQL.append("       AND (l.ASSIGN_USER_ID = :assignedUserID OR l.CREATED_BY = :createdBy) \n");
                params.put("assignedUserID", credentialsDTO.getUserName());
                params.put("createdBy", credentialsDTO.getUserName());
            }
        }

        SQL.append(" ORDER BY l.CREATED_DATE DESC\n");
        return SQL;
    }


    private StringBuilder getLeadPageDataForCustomerQuery(StringBuilder SQL, LeadSearchRQ searchRQ, Map<String, Object> params) throws AppsException {

        SQL.append(" SELECT \n");
        SQL.append("   l.LEAD_ID, \n");
        SQL.append("   l.LEAD_NAME, \n");
        SQL.append("   l.LEAD_REF_NUMBER, \n");
        SQL.append("   l.LEAD_STATUS, \n");
        SQL.append("   l.ADDRESS, \n");
        SQL.append("   l.ASSIGN_USER_ID, \n");
        SQL.append("   l.CIVIL_STATUS, \n");
        SQL.append("   l.DATE_OF_BIRTH, \n");
        SQL.append("   l.BRANCH_CODE, \n");
        SQL.append("   l.BRANCH_NAME, \n");
        SQL.append("   l.NAME, \n");
        SQL.append("   l.IDENTIFICATION_NUMBER, \n");
        SQL.append("   l.IDENTIFICATION_TYPE, \n");
        SQL.append("   l.MOBILE_NO, \n");
        SQL.append("   l.ACCOUNT_NUMBER, \n");
        SQL.append("   l.NATIONALITY, \n");
        SQL.append("   l.LEAD_CREATION_TYPE, \n");
        SQL.append("   l.IS_COMP_LEAD, \n");
        SQL.append("   l.CREATED_DATE \n");
        SQL.append(" FROM T_LEAD l \n");
        SQL.append(" WHERE l.LEAD_ID IS NOT NULL \n");
        SQL.append("       AND l.CUSTOMER_ID = :customerID \n");
        params.put("customerID", searchRQ.getCustomerID());
        SQL.append(" ORDER BY l.CREATED_DATE DESC\n");

        return SQL;
    }

    public Long getBranchPendingLeadCount(LeadSearchRQ searchRQ) throws AppsException {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("branchCode", searchRQ.getBranchCode());
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT  \n");
        SQL.append("   COUNT(l.LEAD_ID) LEAD_COUNT  \n");
        SQL.append(" FROM T_LEAD l  \n");
        SQL.append(" WHERE l.LEAD_ID IS NOT NULL  \n");
        SQL.append("       AND l.BRANCH_CODE = :branchCode  \n");
        SQL.append("       AND l.LEAD_STATUS ='PENDING'  \n");
        SQL.append(" ORDER BY CREATED_DATE DESC  \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public LeadDTO getLeadDTOByLastWithinThreeMonth(LeadDTO searchRQ) throws AppsException {
        Map<String, Object> params = new HashMap<>();
        params.put("identificationNumber", searchRQ.getIdentificationNumber());
        params.put("identificationType", searchRQ.getIdentificationType());
        StringBuilder SQL = new StringBuilder();

        SQL.append("     SELECT                                                                                              \n");
        SQL.append("     tl.LEAD_ID,                                                                                         \n");
        SQL.append("     tl.LEAD_NAME,                                                                                       \n");
        SQL.append("     tl.LEAD_REF_NUMBER,                                                                                 \n");
        SQL.append("     tl.LEAD_STATUS,                                                                                     \n");
        SQL.append("     tl.ADDRESS,                                                                                         \n");
        SQL.append("     tl.ASSIGN_USER_ID,                                                                                  \n");
        SQL.append("     tl.CIVIL_STATUS,                                                                                    \n");
        SQL.append("     tl.DATE_OF_BIRTH,                                                                                   \n");
        SQL.append("     tl.BRANCH_CODE,                                                                                     \n");
        SQL.append("     tl.BRANCH_NAME,                                                                                     \n");
        SQL.append("     tl.NAME,                                                                                            \n");
        SQL.append("     tl.IDENTIFICATION_NUMBER,                                                                           \n");
        SQL.append("     tl.IDENTIFICATION_TYPE,                                                                             \n");
        SQL.append("     tl.MOBILE_NO,                                                                                       \n");
        SQL.append("     tl.ACCOUNT_NUMBER,                                                                                  \n");
        SQL.append("     tl.NATIONALITY,                                                                                     \n");
        SQL.append("     tl.LEAD_CREATION_TYPE,                                                                              \n");
        SQL.append("     tl.CREATED_BY,                                                                                      \n");
        SQL.append("     tl.CREATED_DATE                                                                                     \n");
        SQL.append("     FROM                                                                                                \n");
        SQL.append("     T_LEAD tl                                                                                           \n");
        SQL.append("     WHERE                                                                                               \n");
        SQL.append("     upper(IDENTIFICATION_NUMBER) = upper(:identificationNumber)                                                     \n");
        SQL.append("     AND upper(IDENTIFICATION_TYPE) = upper(:identificationType)                                                       \n");
        SQL.append("     AND CREATED_DATE > = ADD_MONTHS(sysdate ,-3)                                                        \n");
        SQL.append("     AND (CREATED_DATE) = (                                                                              \n");
        SQL.append("     SELECT                                                                                              \n");
        SQL.append("     max(CREATED_DATE)                                                                                   \n");
        SQL.append("     FROM                                                                                                \n");
        SQL.append("     T_LEAD tl                                                                                           \n");
        SQL.append("     WHERE                                                                                               \n");
        SQL.append("     upper(IDENTIFICATION_NUMBER) = upper(:identificationNumber)                                                     \n");
        SQL.append("     AND upper(IDENTIFICATION_TYPE) = upper(:identificationType)                                                       \n");
        SQL.append("     GROUP BY                                                                                            \n");
        SQL.append("     upper(IDENTIFICATION_NUMBER),                                                                       \n");
        SQL.append("     upper(IDENTIFICATION_TYPE))                                                                         \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<LeadDTO>() {

            @Override
            public LeadDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

                LeadDTO leadDTO = null;
                if (rs.next()) {
                    leadDTO = new LeadDTO();
                    if (StringUtils.isNotBlank(rs.getString("LEAD_ID"))) {
                        leadDTO.setLeadID(rs.getInt("LEAD_ID"));
                        leadDTO.setLeadRefNumber(rs.getString("LEAD_REF_NUMBER"));
                        leadDTO.setLeadName(rs.getString("LEAD_NAME"));
                        leadDTO.setName(rs.getString("NAME"));
                        leadDTO.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
                        leadDTO.setAddress(rs.getString("ADDRESS"));
                        leadDTO.setAssignUserID(rs.getString("ASSIGN_USER_ID"));
                        leadDTO.setIsLast3MonthsLeadFound(AppsConstants.YesNo.Y);
                        leadDTO.setIdentificationNumber(rs.getString("IDENTIFICATION_NUMBER"));
                        leadDTO.setIdentificationType(rs.getString("IDENTIFICATION_TYPE"));
                        leadDTO.setMobileNo(rs.getString("MOBILE_NO"));
                        leadDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                        leadDTO.setBranchName(rs.getString("BRANCH_NAME"));
                        leadDTO.setCreatedBy(rs.getString("CREATED_BY"));
                        if (StringUtils.isNotEmpty(rs.getString("CREATED_DATE"))) {
                            leadDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                        }
                    }
                }
                return leadDTO;
            }
        });
    }

    public LeadFacilityPaperStatusDetailRS getFacilityPaperHistoryForLead(Integer leadID) throws AppsException {

        LeadFacilityPaperStatusDetailRS leadFacilityPaperStatusDetailRS = new LeadFacilityPaperStatusDetailRS();
        Map<String, Object> params = new HashMap<>();
        params.put("leadID", leadID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                              \n");
        SQL.append("tfp.FACILITY_PAPER_ID,              \n");
        SQL.append("tfp.FP_REF_NUMBER,                  \n");
        SQL.append("tfp.CREATED_DATE,                   \n");
        SQL.append("tfsh.UPDATE_BY,                     \n");
        SQL.append("tfp.CURRENT_FACILITY_PAPER_STATUS,  \n");
        SQL.append("tfp.ASSIGN_USER_DISPLAY_NAME,       \n");
        SQL.append("tfsh.FACILITY_PAPER_STATUS ,        \n");
        SQL.append("tfsh.ACTION_MESSAGE,                \n");
        SQL.append("tfsh.ASSIGN_USER_ID,                \n");
        SQL.append("tfsh.ASSIGN_DEPARTMENT_CODE,        \n");
        SQL.append("tfsh.UPDATED_USER,                  \n");
        SQL.append("tfsh.IS_PUBLIC,                     \n");
        SQL.append("tfsh.IS_USERS_ONLY,                 \n");
        SQL.append("tfsh.IS_DIVISION_ONLY,              \n");
        SQL.append("tfsh.UPDATED_DATE,                  \n");
        SQL.append("tfp.LEAD_ID                         \n");
        SQL.append("FROM T_FP_STATUS_HISTORY tfsh                                                                                   \n");
        SQL.append("INNER JOIN T_FACILITY_PAPER tfp ON TFSH .FACILITY_PAPER_ID = tfp.FACILITY_PAPER_ID AND tfp.LEAD_ID IS NOT NULL  \n");
        SQL.append("INNER JOIN T_LEAD tl ON tfp.LEAD_ID = tl.LEAD_ID AND tl.LEAD_STATUS = 'PAPER_CREATED'                           \n");
        SQL.append("WHERE TFSH.FP_STATUS_HISTORY_ID IS NOT NULL                                                                     \n");
        SQL.append("AND tl.LEAD_ID =:leadID                                                                                         \n");
        SQL.append("ORDER BY tfsh.UPDATED_DATE ASC                                                                                  \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<LeadFacilityPaperStatusDetailRS>() {

            @Override
            public LeadFacilityPaperStatusDetailRS extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    LeadFacilityPaperStatusDetailDTO remarkDTO = new LeadFacilityPaperStatusDetailDTO();

                    if (leadFacilityPaperStatusDetailRS.getFacilityPaperID() == null) {
                        leadFacilityPaperStatusDetailRS.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                        leadFacilityPaperStatusDetailRS.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                        leadFacilityPaperStatusDetailRS.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                        leadFacilityPaperStatusDetailRS.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                        if (rs.getTimestamp("CREATED_DATE") != null) {
                            leadFacilityPaperStatusDetailRS.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                        }
                    }

                    remarkDTO.setUpdatedUser(rs.getString("UPDATED_USER"));
                    remarkDTO.setAssignUserID(rs.getInt("ASSIGN_USER_ID"));
                    remarkDTO.setIsPublic(AppsConstants.YesNo.resolver(rs.getString("IS_PUBLIC")));
                    remarkDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                    remarkDTO.setIsUsersOnly(AppsConstants.YesNo.resolver(rs.getString("IS_USERS_ONLY")));
                    remarkDTO.setIsDivisionOnly(AppsConstants.YesNo.resolver(rs.getString("IS_DIVISION_ONLY")));
                    remarkDTO.setUpdateBy(rs.getString("UPDATE_BY"));
                    remarkDTO.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("FACILITY_PAPER_STATUS")));
                    remarkDTO.setActionMessage(rs.getString("ACTION_MESSAGE"));
                    if (rs.getTimestamp("UPDATED_DATE") != null) {
                        remarkDTO.setUpdateDate(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("UPDATED_DATE")));
                    }
                    leadFacilityPaperStatusDetailRS.getLeadFacilityPaperStatusDetailDTOS().add(remarkDTO);
                }
                return leadFacilityPaperStatusDetailRS;
            }
        });
    }
    public LeadDashboardCountDTO getLeadDashboardCount(LeadDashboardRQ leadDashboardRQ, Integer dashboardTimePeriodDays ) {
        LeadDashboardCountDTO countDTO = new LeadDashboardCountDTO();
        Map<String, Object> params = new HashMap<>();

        params.put("userId", leadDashboardRQ.getLoggedInUserId());
        params.put("branchCode", leadDashboardRQ.getLoggedInUserBranchCode());
        params.put("workClass", leadDashboardRQ.getLoginUpmAccessLevel());
        params.put("timePeriod", dashboardTimePeriodDays);
        params.put("dashboardType", "LEAD");

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT STATUS, COUNT FROM TABLE( CASV2_DASHBOARD.GET_ALL_DASHBOARD_COUNTS ");
        SQL.append(" (:userId, :branchCode, :dashboardType, :workClass, :timePeriod)) ");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<LeadDashboardCountDTO>() {
            @Override
            public LeadDashboardCountDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<String, Consumer<Integer>> statusSetterMap = new HashMap<>();
                statusSetterMap.put("INBOX", count -> countDTO.setInboxLead(count));
                statusSetterMap.put("IN_PROGRESS", count -> countDTO.setInProgressLead(count));
                statusSetterMap.put("DECLINED", count -> countDTO.setDeclinedLead(count));
                statusSetterMap.put("RETURNED", count -> countDTO.setReturnedLead(count));
                statusSetterMap.put("ACCEPTED", count -> countDTO.setAcceptedLead(count));
                statusSetterMap.put("PAPER_APPROVED", count -> countDTO.setPaperApprovedLead(count));

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

    public Page<LeadDashboardDTO> getPagedLeadDashboardDTO(LeadDashboardRQ leadDashboardRQ, Integer dashboardTimePeriodDays) {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", leadDashboardRQ.getLoggedInUserId());
        params.put("branchCode", leadDashboardRQ.getLoggedInUserBranchCode());
        params.put("workClass", leadDashboardRQ.getLoginUpmAccessLevel());
        params.put("timePeriod", dashboardTimePeriodDays);
        params.put("dashboardType", "LEAD");
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT DASHBOARD_TYPE, DASHBOARD_STATUS, DASHBOARD_SUB_STATUS, ID, REFERENCE_NAME, CUSTOMER_NAME, \n");
        SQL.append(" AF_REFERENCE_NUMBER, FP_REFERENCE_NUMBER, LEAD_REFERENCE_NUMBER, BRANCH_CODE, BRANCH_NAME, ACCOUNT_NUMBER, \n");
        SQL.append(" IDENTIFICATION_NUMBER, CREATED_DATE, CREATED_BY, ASSIGNED_USER, STATUS, FROM_DATE, IS_COMP_LEAD \n");

        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("DRAFT")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_DRAFT_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("RETURNED_TO_ME")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_RETURNED_TO_ME_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("RECEIVED_TO_ME")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_RECEIVED_TO_ME_LIST(:userId, :branchCode, :dashboardType, :workClass)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("IN_PROGRESS")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_IN_PROGRESS_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("DECLINED_BY_ME")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_DECLINED_BY_ME_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("DECLINED_BY_OTHERS")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_DECLINED_BY_OTHERS_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("RETURNED_BY_ME")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_RETURNED_BY_ME_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("RETURNED_BY_OTHERS")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_RETURNED_BY_OTHERS_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("APPLICATION_CREATED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_APPLICATION_CREATED_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("APPLICATION_RETURNED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_APPLICATION_RETURNED_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("APPLICATION_DECLINED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_APPLICATION_DECLINED_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("PAPER_CREATED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_PAPER_CREATED_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("PAPER_RETURNED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_PAPER_RETURNED_LIST(:userId, :branchCode, :dashboardType)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("PAPER_DECLINED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_PAPER_DECLINED_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }
        if (leadDashboardRQ.getLeadDashboardSubStatus().equals("PAPER_APPROVED")){
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_PAPER_APPROVED_LIST(:userId, :branchCode, :dashboardType, :timePeriod)) \n");
        }

        return getPagedData(SQL.toString(), params, new RowMapper<LeadDashboardDTO>() {

            @Nullable
            @Override
            public LeadDashboardDTO mapRow(ResultSet rs, int i) throws SQLException , DataAccessException{
                LeadDashboardDTO docDTO = new LeadDashboardDTO();
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
                docDTO.setIsCompLead(rs.getString("IS_COMP_LEAD"));
                return docDTO;
            }
        }, leadDashboardRQ);
    }

    public AnalyticsDecisionDTO getAnalyticsDecisionByLead(Integer leadId) throws AppsException {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("leadId", leadId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT TAD.* FROM T_ANALYTICS_DECISION TAD INNER JOIN T_LEAD TL ON TAD.LEAD_ID = TL.LEAD_ID \n");
        SQL.append(" WHERE TAD.LEAD_ID = :leadId AND TL.IS_COMP_LEAD = 'Y' \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), paramsMap, new ResultSetExtractor<AnalyticsDecisionDTO>() {

            @Override
            public AnalyticsDecisionDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                AnalyticsDecisionDTO dataRow = new AnalyticsDecisionDTO();
                if (rs.next()) {
                    dataRow.setDecisionId(rs.getInt("DECISION_ID"));
                    dataRow.setLeadId(rs.getInt("LEAD_ID"));
                    dataRow.setDecision(rs.getString("DECISION"));
                    dataRow.setFacilityData(rs.getString("FACILITY_DATA"));
                    dataRow.setDecisionStatus(rs.getString("DECISION_STATUS"));
                    dataRow.setChannel(rs.getString("CHANNEL"));
                    dataRow.setCreatedBy(rs.getString("CREATED_BY"));
                    dataRow.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                return dataRow;
            }
        });
    }

    public List<CompLeadFacilityDTO> getFacilitiesByLeadId(Integer leadID) {
        Map<String, Object> params = new HashMap<>();
        params.put("leadID", leadID);

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                                                 \n");
        SQL.append("  f.COMP_FACILITY_ID,                                                 \n");
        SQL.append("  f.COMP_LEAD_ID,                                                     \n");
        SQL.append("  f.LEAD_ID,                                                          \n");
        SQL.append("  f.FACILITY_DESCRIPTION,                                             \n");
        SQL.append("  f.REQUESTED_TENURE,                                                 \n");
        SQL.append("  f.LEASE_RENTAL,                                                     \n");
        SQL.append("  f.PROCESSING_FEE,                                                   \n");
        SQL.append("  f.LEASE_AMOUNT,                                                     \n");
        SQL.append("  f.REPAYMENT_MODE,                                                   \n");
        SQL.append("  f.UPFRONT,                                                          \n");
        SQL.append("  f.INSURANCE,                                                        \n");
        SQL.append("  f.CREATED_DATE,                                                     \n");
        SQL.append("  f.CREATED_BY,                                                       \n");
        SQL.append("  f.MODIFIED_DATE,                                                    \n");
        SQL.append("  f.MODIFIED_BY,                                                      \n");
        SQL.append("  f.STATUS,                                                           \n");
        SQL.append("  f.EFFECTIVE_RATE,                                                   \n");
        SQL.append("  f.MODEL,                                                            \n");
        SQL.append("  f.MAKE,                                                             \n");
        SQL.append("  f.VALIDITY_OF_OFFER,                                                 \n");
        SQL.append("  f.FACILITY_TEMPLATE_ID,                                                 \n");
        SQL.append("  f.FACILITY_GROUP_ID                                                 \n");
        SQL.append("FROM T_COMP_FACILITIES f                                     \n");
        SQL.append("INNER JOIN T_COMP_LEAD l                                     \n");
        SQL.append("   ON f.COMP_LEAD_ID = l.COMP_LEAD_ID                                 \n");
        SQL.append("WHERE l.LEAD_ID = :leadID                                             \n");
        SQL.append("AND f.STATUS = 'ACT'                                            \n");
        SQL.append("ORDER BY f.COMP_FACILITY_ID ASC                                       \n");

        return this.namedParameterJdbcTemplate.query(
                SQL.toString(),
                params,
                (rs) -> {
                    List<CompLeadFacilityDTO> list = new ArrayList<>();
                    while (rs.next()) {
                        CompLeadFacilityDTO dto = new CompLeadFacilityDTO();

                        // NUMBER → Long/Integer via BigDecimal (safe for Oracle)
                        BigDecimal compFacilityId = rs.getBigDecimal("COMP_FACILITY_ID");
                        dto.setCompFacilityId(compFacilityId != null ? compFacilityId.longValue() : null);

                        BigDecimal compLeadId = rs.getBigDecimal("COMP_LEAD_ID");
                        dto.setCompLeadId(compLeadId != null ? compLeadId.longValue() : null);

                        BigDecimal leadIdFromRow = rs.getBigDecimal("LEAD_ID");
                        dto.setLeadId(leadIdFromRow != null ? leadIdFromRow.longValue() : null);

                        dto.setFacilityDescription(rs.getString("FACILITY_DESCRIPTION"));

                        BigDecimal requestedTenure = rs.getBigDecimal("REQUESTED_TENURE");
                        dto.setRequestedTenure(requestedTenure != null ? requestedTenure.intValue() : null);

                        dto.setLeaseRental(rs.getBigDecimal("LEASE_RENTAL"));
                        dto.setProcessingFee(rs.getBigDecimal("PROCESSING_FEE"));
                        dto.setLeaseAmount(rs.getBigDecimal("LEASE_AMOUNT"));
                        dto.setRepaymentMode(rs.getString("REPAYMENT_MODE"));
                        dto.setUpfront(rs.getBigDecimal("UPFRONT"));
                        dto.setInsurance(rs.getBigDecimal("INSURANCE"));

                        // Your DTO uses java.util.Date → read as Timestamp and assign (it’s a subclass)
                        dto.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                        dto.setCreatedBy(rs.getString("CREATED_BY"));
                        dto.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                        dto.setModifiedBy(rs.getString("MODIFIED_BY"));

                        dto.setStatus(rs.getString("STATUS"));
                        dto.setEffectiveRate(rs.getString("EFFECTIVE_RATE"));
                        dto.setModel(rs.getString("MODEL"));
                        dto.setMake(rs.getString("MAKE"));
                        dto.setValidityOfOffer(rs.getString("VALIDITY_OF_OFFER"));
                        dto.setCreditFacilityTemplateId(rs.getInt("FACILITY_TEMPLATE_ID"));
                        dto.setCreditFacilityTemplateGroupId(rs.getInt("FACILITY_GROUP_ID"));

                        list.add(dto);
                    }
                    return list;
                }
        );
    }

    public List<CustomFacilityInfoDTO> getCustomFacilityInfoByTemplateAndCode(
            Integer templateId) {

        Map<String, Object> params = new HashMap<>();
        params.put("templateId", templateId);

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("  CUSTOM_FACILITY_INFO_ID, \n");
        SQL.append("  CREDIT_FACILITY_TEMPLATE_ID, \n");
        SQL.append("  CUSTOM_FACILITY_INFO_NAME, \n");
        SQL.append("  DESCRIPTION, \n");
        SQL.append("  FIELD_TYPE, \n");
        SQL.append("  MANDATORY, \n");
        SQL.append("  STATUS, \n");
        SQL.append("  CREATED_BY, \n");
        SQL.append("  CREATED_DATE, \n");
        SQL.append("  MODIFIED_DATE, \n");
        SQL.append("  MODIFIED_BY, \n");
        SQL.append("  DISPLAY_ORDER, \n");
        SQL.append("  VERSION, \n");
        SQL.append("  CUSTOM_FACILITY_INFO_CODE \n");
        SQL.append("FROM T_CFT_CUSTOM_FACILITY_INFO \n");
        SQL.append("WHERE CREDIT_FACILITY_TEMPLATE_ID = :templateId \n");
        SQL.append("AND STATUS = 'ACT' \n");
        SQL.append("ORDER BY DISPLAY_ORDER ASC, CUSTOM_FACILITY_INFO_ID ASC \n");

        return this.namedParameterJdbcTemplate.query(
                SQL.toString(),
                params,
                (ResultSet rs) -> {
                    List<CustomFacilityInfoDTO> list = new ArrayList<>();
                    while (rs.next()) {
                        CustomFacilityInfoDTO dto = new CustomFacilityInfoDTO();

                        // NUMBER(10,0) → BigDecimal → Long (safe)
                        BigDecimal id = rs.getBigDecimal("CUSTOM_FACILITY_INFO_ID");
                        dto.setCustomFacilityInfoId(id != null ? id.longValue() : null);

                        BigDecimal tmpl = rs.getBigDecimal("CREDIT_FACILITY_TEMPLATE_ID");
                        dto.setCreditFacilityTemplateId(tmpl != null ? tmpl.longValue() : null);

                        dto.setCustomFacilityInfoName(rs.getString("CUSTOM_FACILITY_INFO_NAME"));
                        dto.setDescription(rs.getString("DESCRIPTION"));
                        dto.setFieldType(rs.getString("FIELD_TYPE"));
                        dto.setMandatory(rs.getString("MANDATORY"));
                        dto.setStatus(rs.getString("STATUS"));
                        dto.setCreatedBy(rs.getString("CREATED_BY"));

                        // Oracle DATE → java.sql.Timestamp (assignable to java.util.Date in your DTO)
                        dto.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                        dto.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));

                        dto.setModifiedBy(rs.getString("MODIFIED_BY"));

                        BigDecimal dispOrder = rs.getBigDecimal("DISPLAY_ORDER");
                        dto.setDisplayOrder(dispOrder != null ? dispOrder.intValue() : null);

                        BigDecimal version = rs.getBigDecimal("VERSION");
                        dto.setVersion(version != null ? version.intValue() : null);

                        dto.setCustomFacilityInfoCode(rs.getString("CUSTOM_FACILITY_INFO_CODE"));

                        list.add(dto);
                    }
                    return list;
                }
        );
    }

    public List<FacilityVitalInfoDataDTO> getVitalFacilityInfoByTemplateAndCode(
            Integer templateId) {

        Map<String, Object> params = new HashMap<>();
        params.put("templateId", templateId);

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("  CFT_VITAL_INFO_ID, \n");
        SQL.append("  CREDIT_FACILITY_TEMPLATE_ID, \n");
        SQL.append("  VITAL_INFO_NAME, \n");
        SQL.append("  MANDATORY, \n");
        SQL.append("  STATUS, \n");
        SQL.append("  DISPLAY_ORDER \n");
        SQL.append("FROM T_CFT_VITAL_INFO \n");
        SQL.append("WHERE CREDIT_FACILITY_TEMPLATE_ID = :templateId \n");
        SQL.append("AND STATUS = 'ACT' \n");
        SQL.append("ORDER BY DISPLAY_ORDER ASC \n");

        return this.namedParameterJdbcTemplate.query(
                SQL.toString(),
                params,
                (ResultSet rs) -> {
                    List<FacilityVitalInfoDataDTO> list = new ArrayList<>();
                    while (rs.next()) {
                        FacilityVitalInfoDataDTO dto = new FacilityVitalInfoDataDTO();

                        dto.setCftVitalInfoID(rs.getInt("CFT_VITAL_INFO_ID"));
                        dto.setFacilityVitalInfoDataID(rs.getInt("CREDIT_FACILITY_TEMPLATE_ID"));
                        dto.setVitalInfoName(rs.getString("VITAL_INFO_NAME"));
                        dto.setMandatory(AppsConstants.YesNo.resolver(rs.getString("MANDATORY")));
                        dto.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                        dto.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));

                        list.add(dto);
                    }
                    return list;
                }
        );
    }

    public List<FacilityOtherFacilityInformationDTO> getOtherFacilityInfoByTemplateAndCode(
            Integer templateId) {

        Map<String, Object> params = new HashMap<>();
        params.put("templateId", templateId);

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("  OTHER_FACILITY_INFO_ID, \n");
        SQL.append("  CREDIT_FACILITY_TEMPLATE_ID, \n");
        SQL.append("  OTHER_FACILITY_INFO_NAME, \n");
        SQL.append("  DESCRIPTION, \n");
        SQL.append("  OTHER_FACILITY_INFO_CODE, \n");
        SQL.append("  FIELD_TYPE, \n");
        SQL.append("  DEFAULT_VALUE, \n");
        SQL.append("  MANDATORY, \n");
        SQL.append("  STATUS, \n");
        SQL.append("  DISPLAY_ORDER \n");
        SQL.append("FROM T_CFT_OTHER_FACILITY_INFO \n");
        SQL.append("WHERE CREDIT_FACILITY_TEMPLATE_ID = :templateId \n");
        SQL.append("AND STATUS = 'ACT' \n");
        SQL.append("ORDER BY DISPLAY_ORDER ASC \n");

        return this.namedParameterJdbcTemplate.query(
                SQL.toString(),
                params,
                (ResultSet rs) -> {
                    List<FacilityOtherFacilityInformationDTO> list = new ArrayList<>();
                    while (rs.next()) {
                        FacilityOtherFacilityInformationDTO dto = new FacilityOtherFacilityInformationDTO();

                        dto.setCftOtherFacilityInfoID(rs.getInt("OTHER_FACILITY_INFO_ID"));
                        dto.setOtherFacilityInfoName(rs.getString("OTHER_FACILITY_INFO_NAME"));
                        dto.setOtherFacilityInfoCode(rs.getString("OTHER_FACILITY_INFO_CODE"));
                        dto.setOtherFacilityInfoFieldType(DomainConstants.InputFieldValueType.resolve(rs.getString("FIELD_TYPE")));
                        dto.setDefaultValue(rs.getString("DEFAULT_VALUE"));
                        dto.setMandatory(AppsConstants.YesNo.resolver(rs.getString("MANDATORY")));
                        dto.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                        dto.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));

                        list.add(dto);
                    }
                    return list;
                }
        );
    }
}
