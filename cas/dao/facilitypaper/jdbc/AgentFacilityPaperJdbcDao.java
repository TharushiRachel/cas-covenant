package com.itechro.cas.dao.facilitypaper.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.facilitypaper.*;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AgentFacilityPaperJdbcDao extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(AgentFacilityPaperJdbcDao.class);

    public Page<FacilityPaperSummaryDTO> getPagedFacilityPaperDTO(FacilityPaperSummarySearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT \n");
        SQL.append("   fp.FACILITY_PAPER_ID, \n");
        SQL.append("   fp.FACILITY_PAPER_NUMBER, \n");
        SQL.append("   fp.FP_REF_NUMBER, \n");
        SQL.append("   fp.BRANCH_CODE, \n");
        SQL.append("   fp.BANK_ACCOUNT_ID, \n");
        SQL.append("   fp.CURRENT_ASSIGN_USER, \n");
        SQL.append("   fp.CREATED_DATE, \n");
        SQL.append("   fp.CURRENT_FACILITY_PAPER_STATUS, \n");
        SQL.append("   SUM(f.OUTSTANDING_AMOUNT) facility_outstanding_amount \n");
        SQL.append(" FROM T_FACILITY_PAPER fp \n");
        SQL.append("   INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID  \n");
        SQL.append("   LEFT JOIN T_FACILITY f ON f.FACILITY_PAPER_ID = fp.FACILITY_PAPER_ID \n");
        SQL.append(" WHERE cc.CUSTOMER_ID = :customerID  \n");
        SQL.append("    AND NOT fp.CURRENT_FACILITY_PAPER_STATUS = 'REMOVED' \n");
        SQL.append(" AND cc.IS_PRIMARY = 'Y'  \n");
        SQL.append(" GROUP BY fp.FP_REF_NUMBER, \n");
        SQL.append("   fp.FACILITY_PAPER_ID, \n");
        SQL.append("   fp.FACILITY_PAPER_NUMBER, \n");
        SQL.append("   fp.BRANCH_CODE, \n");
        SQL.append("   fp.BANK_ACCOUNT_ID, \n");
        SQL.append("   fp.CURRENT_ASSIGN_USER, \n");
        SQL.append("   fp.CREATED_DATE, \n");
        SQL.append("   fp.CURRENT_FACILITY_PAPER_STATUS \n");
        SQL.append("  ORDER BY fp.CREATED_DATE DESC \n");

        params.put("customerID", searchRQ.getCustomerID());

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperSummaryDTO>() {

            @Nullable
            @Override
            public FacilityPaperSummaryDTO mapRow(ResultSet rs, int i) throws SQLException {
                FacilityPaperSummaryDTO summaryDTO = new FacilityPaperSummaryDTO();
                summaryDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                summaryDTO.setBankAccountNumber(rs.getString("BANK_ACCOUNT_ID"));
                summaryDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                summaryDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                summaryDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    summaryDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }
                summaryDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                summaryDTO.setTotalOutstandingAmount(rs.getBigDecimal("facility_outstanding_amount"));
                summaryDTO.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                return summaryDTO;
            }

        }, searchRQ);
    }

    public Page<FacilityPaperDTO> getPagedFacilityPaperDTOForSearch(FacilityPaperSearchRQ facilityPaperSearchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT \n");
        SQL.append("  fp.FACILITY_PAPER_ID, \n");
        SQL.append("  fp.UPC_TEMPLATE_ID, \n");
        SQL.append("  fp.BRANCH_CODE, \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER, \n");
        SQL.append("  fp.BANK_ACCOUNT_ID, \n");
        SQL.append("  fp.FP_APPROVING_AUTHORITY_LEVEL, \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS, \n");
        SQL.append("  fp.PAPER_REVIEW_STATUS,       \n");
        SQL.append("  fp.REVIEW_USER_DISPLAY_NAME,  \n");
        SQL.append("  fp.CURRENT_AUTHORITY_LEVEL, \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER, \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,\n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,\n");
        SQL.append("  fp.CREATED_DATE, \n");
        SQL.append("  fp.IN_PROGRESS_DATE, \n");
        SQL.append("  fp.APPROVED_DATE, \n");
        SQL.append("  fp.CANCELED_DATE, \n");
        SQL.append("  fp.REJECTED_DATE, \n");
        SQL.append("  fp.CREATED_BY, \n");
        SQL.append("  fp.MODIFIED_BY, \n");
        SQL.append("  fp.MODIFIED_DATE, \n");
        SQL.append("  fp.IS_COOPERATE, \n");
//        SQL.append("  fp.VERSION, \n");
        SQL.append("  fp.ROA_PERCENTAGE_EXISTING, \n");
        SQL.append("  fp.ROA_PERCENTAGE_PROPOSED, \n");
        SQL.append("  fp.LEAD_REF_NUMBER, \n");
        SQL.append("  fp.FP_REF_NUMBER, \n");
        SQL.append("  cus.CUSTOMER_NAME,  \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS \n");
        SQL.append("        WHEN 'DRAFT' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')                        \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')                  \n");
        SQL.append("        WHEN 'CANCEL' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')                      \n");
        SQL.append("        WHEN 'APPROVED' THEN  TO_DATE(fp.APPROVED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        WHEN 'REJECTED' THEN  TO_DATE(fp.REJECTED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        ELSE null       \n");
        SQL.append("  END)                  \n");
        SQL.append("  AS DAYS_DIFF,         \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS                       \n");
        SQL.append("        WHEN 'DRAFT' THEN  fp.CREATED_DATE                  \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  fp.IN_PROGRESS_DATE        \n");
        SQL.append("        WHEN 'CANCEL' THEN fp.CANCELED_DATE                 \n");
        SQL.append("        WHEN 'APPROVED' THEN  fp.APPROVED_DATE              \n");
        SQL.append("        WHEN 'REJECTED' THEN  fp.REJECTED_DATE              \n");
        SQL.append("        ELSE fp.CREATED_DATE                                \n");
        SQL.append("  END)                      \n");
        SQL.append("  AS LAST_ACTION_DATE       \n");
        SQL.append(" FROM T_FACILITY_PAPER fp   \n");
        SQL.append(" INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID \n");
        SQL.append(" LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID \n");
        SQL.append(" LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER \n");
        SQL.append(" WHERE fp.FACILITY_PAPER_ID IS NOT NULL \n");
        SQL.append(" AND cc.IS_PRIMARY = 'Y'\n");

        if (facilityPaperSearchRQ.getFacilityPaperStatus() != null) {
            SQL.append("AND fp.CURRENT_FACILITY_PAPER_STATUS =:fpStatus \n");
            params.put("fpStatus", facilityPaperSearchRQ.getFacilityPaperStatus().toString());
        } else {
            SQL.append(" AND NOT fp.CURRENT_FACILITY_PAPER_STATUS = 'REMOVED' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getFpRefNumber())) {
            SQL.append("AND upper(fp.FP_REF_NUMBER) LIKE '%" + facilityPaperSearchRQ.getFpRefNumber().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getIntiatedUserName())) {
            SQL.append("AND upper(fp.CREATED_BY) LIKE '%" + facilityPaperSearchRQ.getIntiatedUserName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCurrentAssignUser())) {
            SQL.append("AND upper(fp.CURRENT_ASSIGN_USER) LIKE '%" + facilityPaperSearchRQ.getCurrentAssignUser().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getAssignUserDisplayName())) {
            SQL.append("AND upper(fp.ASSIGN_USER_DISPLAY_NAME) LIKE '%" + facilityPaperSearchRQ.getAssignUserDisplayName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getBankAccountID())) {
            SQL.append("AND upper(fp.BANK_ACCOUNT_ID) LIKE '%" + facilityPaperSearchRQ.getBankAccountID().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCustomerName())) {
            SQL.append("AND upper(cus.CUSTOMER_NAME) LIKE '%" + facilityPaperSearchRQ.getCustomerName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getLeadRefNumber())) {
            SQL.append("AND upper(lead.LEAD_REF_NUMBER) LIKE '%" + facilityPaperSearchRQ.getLeadRefNumber().toUpperCase() + "%' \n");
        }
        SQL.append(" ORDER BY fp.CREATED_DATE DESC\n");

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setUpcTemplateID(rs.getInt("UPC_TEMPLATE_ID"));
                facilityPaperDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                facilityPaperDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                facilityPaperDTO.setBankAccountID(rs.getString("BANK_ACCOUNT_ID"));
                facilityPaperDTO.setFpApprovingAuthorityLevel(rs.getString("FP_APPROVING_AUTHORITY_LEVEL"));
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                facilityPaperDTO.setCurrentAuthorityLevel(rs.getString("CURRENT_AUTHORITY_LEVEL"));
                facilityPaperDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                facilityPaperDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                facilityPaperDTO.setModifiedBy(rs.getString("MODIFIED_BY"));
                facilityPaperDTO.setIsCooperate(AppsConstants.YesNo.valueOf(rs.getString("IS_COOPERATE")));
                facilityPaperDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                facilityPaperDTO.setLeadRefNumber(rs.getString("LEAD_REF_NUMBER"));
                facilityPaperDTO.setExistingFacilitiesROA(rs.getDouble("ROA_PERCENTAGE_EXISTING"));
                facilityPaperDTO.setProposedFacilitiesROA(rs.getDouble("ROA_PERCENTAGE_PROPOSED"));
                facilityPaperDTO.setDaysDiff(rs.getLong("DAYS_DIFF"));
                facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                if (StringUtils.isNotEmpty(rs.getString("ASSIGN_USER_DISPLAY_NAME"))) {
                    facilityPaperDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                }
                if (StringUtils.isNotEmpty(rs.getString("PAPER_REVIEW_STATUS"))) {
                    facilityPaperDTO.setPaperReviewStatus(DomainConstants.PaperReviewStatus.resolve(rs.getString("PAPER_REVIEW_STATUS")));
                    facilityPaperDTO.setReviewUserDisplayName(rs.getString("REVIEW_USER_DISPLAY_NAME"));
                }
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    facilityPaperDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                if (rs.getTimestamp("IN_PROGRESS_DATE") != null) {
                    facilityPaperDTO.setInProgressDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("IN_PROGRESS_DATE")));
                }
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    facilityPaperDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CANCELED_DATE") != null) {
                    facilityPaperDTO.setCanceledDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CANCELED_DATE")));
                }
                if (rs.getTimestamp("REJECTED_DATE") != null) {
                    facilityPaperDTO.setRejectedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("REJECTED_DATE")));
                }
                facilityPaperDTO.setCreatedBy(rs.getString("CREATED_BY"));
                if (rs.getTimestamp("MODIFIED_DATE") != null) {
                    facilityPaperDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("MODIFIED_DATE")));
                }
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public Page<FacilityPaperDTO> getPagedInbox(FacilityPaperSearchRQ facilityPaperSearchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS \n");
        SQL.append("        WHEN 'DRAFT' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')                        \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.IN_PROGRESS_DATE,'DD/MM/YYYY')              \n");
        SQL.append("        WHEN 'CANCEL' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CANCELED_DATE,'DD/MM/YYYY')                      \n");
        SQL.append("        WHEN 'APPROVED' THEN  TO_DATE(fp.APPROVED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        WHEN 'REJECTED' THEN  TO_DATE(fp.REJECTED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        ELSE null       \n");
        SQL.append("  END)                  \n");
        SQL.append("  AS DAYS_DIFF,         \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS                       \n");
        SQL.append("        WHEN 'DRAFT' THEN  fp.CREATED_DATE                  \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  fp.IN_PROGRESS_DATE        \n");
        SQL.append("        WHEN 'CANCEL' THEN fp.CANCELED_DATE                 \n");
        SQL.append("        WHEN 'APPROVED' THEN  fp.APPROVED_DATE              \n");
        SQL.append("        WHEN 'REJECTED' THEN  fp.REJECTED_DATE              \n");
        SQL.append("        ELSE fp.CREATED_DATE                                \n");
        SQL.append("  END)                      \n");
        SQL.append("  AS LAST_ACTION_DATE       \n");
        SQL.append("FROM T_FACILITY_PAPER fp                                                                  \n");
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID            \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                           \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                      \n");
        SQL.append("WHERE cc.IS_PRIMARY = 'Y'                                                                \n");
        SQL.append("AND fp.CURRENT_FACILITY_PAPER_STATUS IN ('DRAFT', 'IN_PROGRESS', 'PENDING', 'CANCEL')     \n");
        SQL.append("AND upper(fp.CURRENT_ASSIGN_USER) =:user                \n");

        SQL.append("GROUP BY fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS                                                        \n");
        SQL.append("ORDER BY CREATED_DATE DESC                                                                \n");

        params.put("user", facilityPaperSearchRQ.getIntiatedUserName().toUpperCase());

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                if (StringUtils.isNotEmpty(rs.getString("CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                } else if (StringUtils.isNotEmpty(rs.getString("CAS_CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CAS_CUSTOMER_NAME"));
                }
                facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                facilityPaperDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                facilityPaperDTO.setBankAccountID(rs.getString("BANK_ACCOUNT_ID"));
                facilityPaperDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                facilityPaperDTO.setCreatedBy(rs.getString("CREATED_BY"));
                facilityPaperDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                facilityPaperDTO.setDaysDiff(rs.getLong("DAYS_DIFF"));
                facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    facilityPaperDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                if (rs.getTimestamp("IN_PROGRESS_DATE") != null) {
                    facilityPaperDTO.setInProgressDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("IN_PROGRESS_DATE")));
                }
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    facilityPaperDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CANCELED_DATE") != null) {
                    facilityPaperDTO.setCanceledDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CANCELED_DATE")));
                }
                if (rs.getTimestamp("REJECTED_DATE") != null) {
                    facilityPaperDTO.setRejectedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("REJECTED_DATE")));
                }
                if (StringUtils.isNotEmpty(rs.getString("ASSIGN_USER_DISPLAY_NAME"))) {
                    facilityPaperDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                }
                if (StringUtils.isNotEmpty(rs.getString("ASSIGN_DEPARTMENT_CODE"))) {
                    facilityPaperDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                }

                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public Page<FacilityPaperDTO> getPagedCancelled(FacilityPaperSearchRQ facilityPaperSearchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS \n");
        SQL.append("        WHEN 'DRAFT' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')                        \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.IN_PROGRESS_DATE,'DD/MM/YYYY')              \n");
        SQL.append("        WHEN 'CANCEL' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CANCELED_DATE,'DD/MM/YYYY')                      \n");
        SQL.append("        WHEN 'APPROVED' THEN  TO_DATE(fp.APPROVED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        WHEN 'REJECTED' THEN  TO_DATE(fp.REJECTED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        ELSE null       \n");
        SQL.append("  END)                  \n");
        SQL.append("  AS DAYS_DIFF,         \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS                       \n");
        SQL.append("        WHEN 'DRAFT' THEN  fp.CREATED_DATE                  \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  fp.IN_PROGRESS_DATE        \n");
        SQL.append("        WHEN 'CANCEL' THEN fp.CANCELED_DATE                 \n");
        SQL.append("        WHEN 'APPROVED' THEN  fp.APPROVED_DATE              \n");
        SQL.append("        WHEN 'REJECTED' THEN  fp.REJECTED_DATE              \n");
        SQL.append("        ELSE fp.CREATED_DATE                                \n");
        SQL.append("  END)                      \n");
        SQL.append("  AS LAST_ACTION_DATE       \n");
        SQL.append("FROM T_FACILITY_PAPER fp                                                                  \n");
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID            \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                           \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                      \n");
        SQL.append("WHERE cc.IS_PRIMARY = 'Y'                                                                \n");
        SQL.append("AND fp.CURRENT_FACILITY_PAPER_STATUS IN ('CANCEL')                 \n");
        SQL.append("AND upper(fp.CURRENT_ASSIGN_USER) =:user                \n");

        SQL.append("GROUP BY fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS                                                        \n");
        SQL.append("ORDER BY CREATED_DATE DESC                                                                \n");

        params.put("user", facilityPaperSearchRQ.getIntiatedUserName().toUpperCase());

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                if (StringUtils.isNotEmpty(rs.getString("CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                } else if (StringUtils.isNotEmpty(rs.getString("CAS_CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CAS_CUSTOMER_NAME"));
                }
                facilityPaperDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                facilityPaperDTO.setBankAccountID(rs.getString("BANK_ACCOUNT_ID"));
                facilityPaperDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                facilityPaperDTO.setCreatedBy(rs.getString("CREATED_BY"));
                facilityPaperDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                facilityPaperDTO.setDaysDiff(rs.getLong("DAYS_DIFF"));
                facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    facilityPaperDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                if (rs.getTimestamp("IN_PROGRESS_DATE") != null) {
                    facilityPaperDTO.setInProgressDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("IN_PROGRESS_DATE")));
                }
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    facilityPaperDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CANCELED_DATE") != null) {
                    facilityPaperDTO.setCanceledDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CANCELED_DATE")));
                }
                if (rs.getTimestamp("REJECTED_DATE") != null) {
                    facilityPaperDTO.setRejectedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("REJECTED_DATE")));
                }
                if (StringUtils.isNotEmpty(rs.getString("ASSIGN_USER_DISPLAY_NAME"))) {
                    facilityPaperDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                }
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public Page<FacilityPaperDTO> getPagedInProgress(FacilityPaperSearchRQ facilityPaperSearchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS \n");
        SQL.append("        WHEN 'DRAFT' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')                        \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.IN_PROGRESS_DATE,'DD/MM/YYYY')              \n");
        SQL.append("        WHEN 'CANCEL' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CANCELED_DATE,'DD/MM/YYYY')                      \n");
        SQL.append("        WHEN 'APPROVED' THEN  TO_DATE(fp.APPROVED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        WHEN 'REJECTED' THEN  TO_DATE(fp.REJECTED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        ELSE null       \n");
        SQL.append("  END)                  \n");
        SQL.append("  AS DAYS_DIFF,         \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS                       \n");
        SQL.append("        WHEN 'DRAFT' THEN  fp.CREATED_DATE                  \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  fp.IN_PROGRESS_DATE        \n");
        SQL.append("        WHEN 'CANCEL' THEN fp.CANCELED_DATE                 \n");
        SQL.append("        WHEN 'APPROVED' THEN  fp.APPROVED_DATE              \n");
        SQL.append("        WHEN 'REJECTED' THEN  fp.REJECTED_DATE              \n");
        SQL.append("        ELSE fp.CREATED_DATE                                \n");
        SQL.append("  END)                      \n");
        SQL.append("  AS LAST_ACTION_DATE       \n");
        SQL.append("FROM T_FACILITY_PAPER fp                                                                        \n");
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID                  \n");
        SQL.append("  INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr   ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID    \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                                 \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                            \n");
        SQL.append("WHERE cc.IS_PRIMARY = 'Y'                                                                      \n");
        SQL.append("AND fp.CURRENT_FACILITY_PAPER_STATUS NOT IN ('DRAFT', 'REJECTED', 'APPROVED')                   \n");
        SQL.append("AND upper(fp.CURRENT_ASSIGN_USER) !=:user                                                       \n");
        SQL.append("AND (upper(fpwfr.ASSIGN_USER) =:user OR upper(fp.CREATED_BY) =:user)                            \n");

        SQL.append("GROUP BY fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS                                                        \n");
        SQL.append("ORDER BY CREATED_DATE DESC                                                                \n");

        params.put("user", facilityPaperSearchRQ.getIntiatedUserName().toUpperCase());

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                if (StringUtils.isNotEmpty(rs.getString("CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                } else if (StringUtils.isNotEmpty(rs.getString("CAS_CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CAS_CUSTOMER_NAME"));
                }
                facilityPaperDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                facilityPaperDTO.setBankAccountID(rs.getString("BANK_ACCOUNT_ID"));
                facilityPaperDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                facilityPaperDTO.setCreatedBy(rs.getString("CREATED_BY"));
                facilityPaperDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                facilityPaperDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                facilityPaperDTO.setDaysDiff(rs.getLong("DAYS_DIFF"));
                if (rs.getTimestamp("LAST_ACTION_DATE") != null) {
                    facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                } else {
                    LOG.warn("No Action Date For REF : {}  : Paper Status : {}", rs.getString("FP_REF_NUMBER"), rs.getString("CURRENT_FACILITY_PAPER_STATUS"));
                }
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    facilityPaperDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                if (rs.getTimestamp("IN_PROGRESS_DATE") != null) {
                    facilityPaperDTO.setInProgressDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("IN_PROGRESS_DATE")));
                }
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    facilityPaperDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CANCELED_DATE") != null) {
                    facilityPaperDTO.setCanceledDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CANCELED_DATE")));
                }
                if (rs.getTimestamp("REJECTED_DATE") != null) {
                    facilityPaperDTO.setRejectedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("REJECTED_DATE")));
                }
                if (StringUtils.isNotEmpty(rs.getString("ASSIGN_USER_DISPLAY_NAME"))) {
                    facilityPaperDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                }
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public Page<FacilityPaperDTO> getPagedMyFacilityPaperDTO(FacilityPaperSearchRQ facilityPaperSearchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.PAPER_REVIEW_STATUS,                                                                 \n");
        SQL.append("  fp.REVIEW_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS \n");
        SQL.append("        WHEN 'DRAFT' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')                        \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.IN_PROGRESS_DATE,'DD/MM/YYYY')              \n");
        SQL.append("        WHEN 'CANCEL' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CANCELED_DATE,'DD/MM/YYYY')                      \n");
        SQL.append("        WHEN 'APPROVED' THEN  TO_DATE(fp.APPROVED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        WHEN 'REJECTED' THEN  TO_DATE(fp.REJECTED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        ELSE null       \n");
        SQL.append("  END)                  \n");
        SQL.append("  AS DAYS_DIFF,         \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS                       \n");
        SQL.append("        WHEN 'DRAFT' THEN  fp.CREATED_DATE                  \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  fp.IN_PROGRESS_DATE        \n");
        SQL.append("        WHEN 'CANCEL' THEN fp.CANCELED_DATE                 \n");
        SQL.append("        WHEN 'APPROVED' THEN  fp.APPROVED_DATE              \n");
        SQL.append("        WHEN 'REJECTED' THEN  fp.REJECTED_DATE              \n");
        SQL.append("        ELSE fp.CREATED_DATE                                \n");
        SQL.append("  END)                      \n");
        SQL.append("  AS LAST_ACTION_DATE       \n");
        SQL.append("FROM T_FACILITY_PAPER fp                                                                  \n");
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID            \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                           \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                      \n");
        SQL.append("WHERE cc.IS_PRIMARY = 'Y'                                                                \n");

        if (facilityPaperSearchRQ.getFacilityPaperStatus() != null) {
            SQL.append("AND fp.CURRENT_FACILITY_PAPER_STATUS =:fpStatus                                   \n");
            params.put("fpStatus", facilityPaperSearchRQ.getFacilityPaperStatus().toString());
        } else {
            SQL.append(" AND NOT fp.CURRENT_FACILITY_PAPER_STATUS = 'REMOVED'                             \n");
        }
        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getFpRefNumber())) {
            SQL.append("AND upper(fp.FP_REF_NUMBER) LIKE '%" + facilityPaperSearchRQ.getFpRefNumber().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCustomerName())) {
            SQL.append("AND upper(cus.CUSTOMER_NAME) LIKE '%" + facilityPaperSearchRQ.getCustomerName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getLeadRefNumber())) {
            SQL.append("AND upper(lead.LEAD_REF_NUMBER) LIKE '%" + facilityPaperSearchRQ.getLeadRefNumber().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getIntiatedUserName())) {
            if (facilityPaperSearchRQ.isInboxRequest() || facilityPaperSearchRQ.isReturnRequest()) {
                SQL.append("AND upper(fp.CURRENT_ASSIGN_USER) LIKE '%" + facilityPaperSearchRQ.getIntiatedUserName().toUpperCase() + "%' \n");
            } else {
                SQL.append("AND upper(fp.CREATED_BY) LIKE '%" + facilityPaperSearchRQ.getIntiatedUserName().toUpperCase() + "%' \n");
            }
        }

        SQL.append("UNION                                                                                     \n");

        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.PAPER_REVIEW_STATUS,                                                                 \n");
        SQL.append("  fp.REVIEW_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS \n");
        SQL.append("        WHEN 'DRAFT' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')                        \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.IN_PROGRESS_DATE,'DD/MM/YYYY')              \n");
        SQL.append("        WHEN 'CANCEL' THEN  TO_DATE(sysdate,'DD/MM/YYYY') - TO_DATE(fp.CANCELED_DATE,'DD/MM/YYYY')                      \n");
        SQL.append("        WHEN 'APPROVED' THEN  TO_DATE(fp.APPROVED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        WHEN 'REJECTED' THEN  TO_DATE(fp.REJECTED_DATE ,'DD/MM/YYYY') - TO_DATE(fp.CREATED_DATE,'DD/MM/YYYY')           \n");
        SQL.append("        ELSE null       \n");
        SQL.append("  END)                  \n");
        SQL.append("  AS DAYS_DIFF,         \n");
        SQL.append("  (CASE CURRENT_FACILITY_PAPER_STATUS                       \n");
        SQL.append("        WHEN 'DRAFT' THEN  fp.CREATED_DATE                  \n");
        SQL.append("        WHEN 'IN_PROGRESS' THEN  fp.IN_PROGRESS_DATE        \n");
        SQL.append("        WHEN 'CANCEL' THEN fp.CANCELED_DATE                 \n");
        SQL.append("        WHEN 'APPROVED' THEN  fp.APPROVED_DATE              \n");
        SQL.append("        WHEN 'REJECTED' THEN  fp.REJECTED_DATE              \n");
        SQL.append("        ELSE fp.CREATED_DATE                                \n");
        SQL.append("  END)                      \n");
        SQL.append("  AS LAST_ACTION_DATE       \n");
        SQL.append("FROM T_FACILITY_PAPER fp                                                                  \n");
        SQL.append("  INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                                  \n");
        SQL.append("    ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                     \n");
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID            \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                           \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                      \n");
        SQL.append("WHERE cc.IS_PRIMARY = 'Y'                                                                \n");

        if (facilityPaperSearchRQ.getFacilityPaperStatus() != null) {
            SQL.append("AND fp.CURRENT_FACILITY_PAPER_STATUS =:fpStatus                                   \n");
            params.put("fpStatus", facilityPaperSearchRQ.getFacilityPaperStatus().toString());
        } else {
            SQL.append(" AND NOT fp.CURRENT_FACILITY_PAPER_STATUS = 'REMOVED'                             \n");
        }
        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getFpRefNumber())) {
            SQL.append("AND upper(fp.FP_REF_NUMBER) LIKE '%" + facilityPaperSearchRQ.getFpRefNumber().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCustomerName())) {
            SQL.append("AND upper(cus.CUSTOMER_NAME) LIKE '%" + facilityPaperSearchRQ.getCustomerName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getLeadRefNumber())) {
            SQL.append("AND upper(lead.LEAD_REF_NUMBER) LIKE '%" + facilityPaperSearchRQ.getLeadRefNumber().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCurrentAssignUser())) {
            SQL.append("AND upper(fpwfr.ASSIGN_USER) LIKE '%" + facilityPaperSearchRQ.getCurrentAssignUser().toUpperCase() + "%' \n");
        }
        SQL.append("GROUP BY fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.PAPER_REVIEW_STATUS,                                                                 \n");
        SQL.append("  fp.REVIEW_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS                                                        \n");
        SQL.append("ORDER BY CREATED_DATE DESC                                                                \n");

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                if (StringUtils.isNotEmpty(rs.getString("CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                } else if (StringUtils.isNotEmpty(rs.getString("CAS_CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CAS_CUSTOMER_NAME"));
                }
                facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                facilityPaperDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                facilityPaperDTO.setBankAccountID(rs.getString("BANK_ACCOUNT_ID"));
                facilityPaperDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                facilityPaperDTO.setCreatedBy(rs.getString("CREATED_BY"));
                facilityPaperDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                facilityPaperDTO.setDaysDiff(rs.getLong("DAYS_DIFF"));
                facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    facilityPaperDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                }
                if (rs.getTimestamp("IN_PROGRESS_DATE") != null) {
                    facilityPaperDTO.setInProgressDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("IN_PROGRESS_DATE")));
                }
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    facilityPaperDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CANCELED_DATE") != null) {
                    facilityPaperDTO.setCanceledDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CANCELED_DATE")));
                }
                if (rs.getTimestamp("REJECTED_DATE") != null) {
                    facilityPaperDTO.setRejectedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("REJECTED_DATE")));
                }
                if (StringUtils.isNotEmpty(rs.getString("ASSIGN_USER_DISPLAY_NAME"))) {
                    facilityPaperDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                }
                if (StringUtils.isNotEmpty(rs.getString("PAPER_REVIEW_STATUS"))) {
                    facilityPaperDTO.setPaperReviewStatus(DomainConstants.PaperReviewStatus.resolve(rs.getString("PAPER_REVIEW_STATUS")));
                    facilityPaperDTO.setReviewUserDisplayName(rs.getString("REVIEW_USER_DISPLAY_NAME"));
                }
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    facilityPaperDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("APPROVED_DATE")));
                }
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }


    public Long getAssignedFacilityPaperCount(FacilityPaperSearchRQ searchRQ) throws AppsException {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("currentAssignUser", searchRQ.getCurrentAssignUser());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT COUNT(fp.FACILITY_PAPER_ID) FACILITY_PAPER_COUNT            \n");
        SQL.append("FROM T_FACILITY_PAPER fp                                           \n");
        SQL.append("WHERE fp.FACILITY_PAPER_ID IS NOT NULL                             \n");
        SQL.append("      AND fp.CURRENT_FACILITY_PAPER_STATUS = 'IN_PROGRESS'         \n");
        SQL.append("      AND fp.CURRENT_ASSIGN_USER = :currentAssignUser              \n");
        SQL.append("ORDER BY fp.CREATED_DATE DESC                                      \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Integer getInboxCount(DashboardCountSearchRQ searchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT count(*) AS INBOX_COUNT                                                            \n");
        SQL.append("FROM (                                                                                    \n");
        SQL.append("  SELECT fp.FACILITY_PAPER_ID                                                             \n");
        SQL.append("  FROM T_FACILITY_PAPER fp                                                                \n");
        SQL.append("    INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID          \n");
        SQL.append("  WHERE                                                                                   \n");
        SQL.append("    cc.IS_PRIMARY = 'Y'                                                                  \n");
        SQL.append("    AND fp.CURRENT_FACILITY_PAPER_STATUS IN ('DRAFT', 'IN_PROGRESS', 'PENDING', 'CANCEL') \n");
        SQL.append("    AND upper(fp.CURRENT_ASSIGN_USER) = :user                                             \n");
        SQL.append(")                                                                                         \n");

        params.put("user", searchRQ.getIntiatedUserName().toUpperCase());

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                int inboxCount = 0;
                while (rs.next()) {
                    inboxCount = rs.getInt("INBOX_COUNT");
                }
                return inboxCount;
            }
        });
    }

    public Integer getInProgressCount(DashboardCountSearchRQ searchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT count(*) AS INPROGRESS_COUNT                                               \n");
        SQL.append("FROM (                                                                            \n");
        SQL.append("  SELECT fp.FACILITY_PAPER_ID                                                     \n");
        SQL.append("  FROM T_FACILITY_PAPER fp                                                        \n");
        SQL.append("    INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID  \n");
        SQL.append("    INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                        \n");
        SQL.append("      ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                           \n");
        SQL.append("  WHERE                                                                           \n");
        SQL.append("    cc.IS_PRIMARY = 'Y'                                                          \n");
        SQL.append("    AND fp.CURRENT_FACILITY_PAPER_STATUS NOT IN ('DRAFT', 'REJECTED', 'APPROVED') \n");
        SQL.append("    AND upper(fp.CURRENT_ASSIGN_USER) != :user                                    \n");
        SQL.append("    AND (upper(fpwfr.ASSIGN_USER) = :user OR upper(fp.CREATED_BY) = :user)        \n");
        SQL.append("  GROUP BY fp.FACILITY_PAPER_ID                                                   \n");
        SQL.append(")                                                                                 \n");

        params.put("user", searchRQ.getIntiatedUserName().toUpperCase());

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                int count = 0;
                while (rs.next()) {
                    count = rs.getInt("INPROGRESS_COUNT");
                }
                return count;
            }
        });
    }


    public DashboardCountDTO getDashboardCount(DashboardCountSearchRQ searchRQ) {
        DashboardCountDTO countDTO = new DashboardCountDTO();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                                                       \n");
        SQL.append("FROM (SELECT count(*) AS DRAFT_FACILITY                                                        \n");
        SQL.append("      FROM (SELECT                                                                             \n");
        SQL.append("              fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("              fp.FP_REF_NUMBER                                                                 \n");
        SQL.append("            FROM T_FACILITY_PAPER fp                                                           \n");
        SQL.append("              INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID     \n");
        SQL.append("            WHERE cc.IS_PRIMARY = 'Y'                                                         \n");
        SQL.append("                  AND fp.CURRENT_FACILITY_PAPER_STATUS != 'REMOVED'                               \n");
        SQL.append("                  AND upper(fp.CURRENT_ASSIGN_USER) LIKE '%" + searchRQ.getIntiatedUserName().toUpperCase() + "%'     \n");
        SQL.append("            UNION                                                                              \n");
        SQL.append("            SELECT                                                                             \n");
        SQL.append("              fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("              fp.FP_REF_NUMBER                                                                 \n");
        SQL.append("            FROM T_FACILITY_PAPER fp                                                           \n");
        SQL.append("              INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                           \n");
        SQL.append("                ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                              \n");
        SQL.append("              INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID     \n");
        SQL.append("            WHERE cc.IS_PRIMARY = 'Y'                                                         \n");
        SQL.append("                  AND fp.CURRENT_FACILITY_PAPER_STATUS = 'REMOVED'                               \n");
        SQL.append("                  AND upper(fpwfr.ASSIGN_USER) LIKE '%" + searchRQ.getCurrentAssignUser().toUpperCase() + "%'     \n");
        SQL.append("            GROUP BY fp.FACILITY_PAPER_ID,                                                     \n");
        SQL.append("              fp.FP_REF_NUMBER)) df,                                                           \n");
        SQL.append("  (SELECT count(*) AS IN_PROGRESS_FACILITY                                                     \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'IN_PROGRESS'                            \n");
        SQL.append("               AND upper(fp.CREATED_BY) LIKE '%" + searchRQ.getIntiatedUserName().toUpperCase() + "%'     \n");
        SQL.append("         UNION                                                                                 \n");
        SQL.append("         SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                              \n");
        SQL.append("             ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                 \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'IN_PROGRESS'                            \n");
        SQL.append("               AND upper(fpwfr.ASSIGN_USER) LIKE '%" + searchRQ.getCurrentAssignUser().toUpperCase() + "%'     \n");
        SQL.append("         GROUP BY fp.FACILITY_PAPER_ID,                                                        \n");
        SQL.append("           fp.FP_REF_NUMBER)) ipf,                                                             \n");
        SQL.append("  (SELECT count(*) AS REJECTED_FACILITY                                                        \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'REJECTED'                               \n");
        SQL.append("               AND upper(fp.CREATED_BY) LIKE '%" + searchRQ.getIntiatedUserName().toUpperCase() + "%'     \n");
        SQL.append("         UNION                                                                                 \n");
        SQL.append("         SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                              \n");
        SQL.append("             ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                 \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'REJECTED'                               \n");
        SQL.append("               AND upper(fpwfr.ASSIGN_USER) LIKE '%" + searchRQ.getCurrentAssignUser().toUpperCase() + "%'     \n");
        SQL.append("         GROUP BY fp.FACILITY_PAPER_ID,                                                        \n");
        SQL.append("           fp.FP_REF_NUMBER)) rejf,                                                            \n");
        SQL.append("  (SELECT count(*) AS APPROVED_FACILITY                                                        \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");
        SQL.append("               AND upper(fp.CREATED_BY) LIKE '%" + searchRQ.getIntiatedUserName().toUpperCase() + "%'     \n");
        SQL.append("         UNION                                                                                 \n");
        SQL.append("         SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                              \n");
        SQL.append("             ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                 \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");
        SQL.append("               AND upper(fpwfr.ASSIGN_USER) LIKE '%" + searchRQ.getCurrentAssignUser().toUpperCase() + "%'     \n");
        SQL.append("         GROUP BY fp.FACILITY_PAPER_ID,                                                        \n");
        SQL.append("           fp.FP_REF_NUMBER)) af,                                                              \n");

        SQL.append("  (SELECT count(*) AS REVIEW_REJECTED_FACILITY                                                 \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");
        SQL.append("               AND fp.PAPER_REVIEW_STATUS = 'REJECTED'                                          \n");
        SQL.append("               AND upper(fp.CREATED_BY) LIKE '%" + searchRQ.getIntiatedUserName().toUpperCase() + "%'     \n");
        SQL.append("         UNION                                                                                 \n");
        SQL.append("         SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                              \n");
        SQL.append("             ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                 \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");
        SQL.append("               AND fp.PAPER_REVIEW_STATUS = 'REJECTED'                                         \n");
        SQL.append("               AND upper(fpwfr.ASSIGN_USER) LIKE '%" + searchRQ.getCurrentAssignUser().toUpperCase() + "%'     \n");
        SQL.append("         GROUP BY fp.FACILITY_PAPER_ID,                                                        \n");
        SQL.append("           fp.FP_REF_NUMBER)) rf,                                                              \n");

        SQL.append("  (SELECT count(*) AS CANCEl_FACILITY                                                          \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID        \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                            \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'CANCEL'                                 \n");
        SQL.append("               AND upper(fp.CURRENT_ASSIGN_USER) LIKE '%" + searchRQ.getIntiatedUserName().toUpperCase() + "%'     \n");
        SQL.append("           )) cf                                                               \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<DashboardCountDTO>() {
            @Override
            public DashboardCountDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    countDTO.setDraftFacilityPaper(rs.getInt("DRAFT_FACILITY"));
                    countDTO.setInProgressFacilityPaper(rs.getInt("IN_PROGRESS_FACILITY"));
                    countDTO.setApprovedFacilityPaper(rs.getInt("APPROVED_FACILITY"));
                    countDTO.setRejectedFacilityPaper(rs.getInt("REJECTED_FACILITY"));
                    countDTO.setCancelFacilityPaper(rs.getInt("CANCEL_FACILITY"));
                    countDTO.setReviewRejectedPaper(rs.getInt("REVIEW_REJECTED_FACILITY"));
                }
                return countDTO;
            }
        });
    }


}
