package com.itechro.cas.dao.facilitypaper.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.facilitypaper.facility.*;
import com.itechro.cas.model.dto.casmaster.*;
import com.itechro.cas.model.dto.facility.PurposeOfAdvancedDTO;
import com.itechro.cas.model.dto.facility.SectorDTO;
import com.itechro.cas.model.dto.facility.SubSectorDTO;
import com.itechro.cas.model.dto.facility.facilityreview.FacilityReviewDTO;
import com.itechro.cas.model.dto.facility.facilityreview.FacilityReviewSearchRQ;
import com.itechro.cas.model.dto.facilitypaper.*;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummaryDTO;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummarySearchRQ;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewerCommentDTO;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewerCommentSearchRQ;
import com.itechro.cas.model.dto.facilitypaper.request.FacilityCovenantDetailsDTO;
import com.itechro.cas.model.dto.facilitypaper.response.CribDetailsResponse;
import com.itechro.cas.model.dto.facilitypaper.securityDocument.SecurityDocumentDTO;
import com.itechro.cas.model.dto.lead.LeadFacilityPaperStatusDetailDTO;
import com.itechro.cas.model.dto.master.BooleanValueDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.util.QueryBuilder;
import com.itechro.cas.util.QueryInBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class FacilityPaperJdbcDao extends BaseJDBCDao {
    private EntityManager entityManager;
    @Autowired
    SystemParameterService systemParameterService;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperJdbcDao.class);

    public List<FacilityPaperSummaryDTO> getCustomerFacilityPaperSummaryList(Integer customerID) {

        List<FacilityPaperSummaryDTO> resultList = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("customerID", customerID);
        StringBuilder SQL = new StringBuilder();


        SQL.append(" SELECT \n");
        SQL.append("   fp.FACILITY_PAPER_ID, \n");
        SQL.append("   fp.FACILITY_PAPER_NUMBER, \n");
        SQL.append("   fp.FP_REF_NUMBER, \n");
        SQL.append("   fp.BRANCH_CODE, \n");
        SQL.append("   fp.BANK_ACCOUNT_ID, \n");
        SQL.append("   fp.CURRENT_ASSIGN_USER, \n");
        SQL.append("   fp.CURRENT_FACILITY_PAPER_STATUS, \n");
        SQL.append("   SUM(f.OUTSTANDING_AMOUNT) facility_outstanding_amount \n");
        SQL.append(" FROM T_FACILITY_PAPER fp \n");
        SQL.append("   INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID  \n");
        SQL.append("   LEFT JOIN T_FACILITY f ON f.FACILITY_PAPER_ID = fp.FACILITY_PAPER_ID \n");
        SQL.append(" WHERE cc.CUSTOMER_ID = :customerID  \n");
        SQL.append("       AND cc.IS_PRIMARY = 'Y'  \n");
        SQL.append("    AND NOT fp.CURRENT_FACILITY_PAPER_STATUS = 'REMOVED' \n");
        SQL.append(" GROUP BY fp.FP_REF_NUMBER, \n");
        SQL.append("   fp.FACILITY_PAPER_ID, \n");
        SQL.append("   fp.FACILITY_PAPER_NUMBER, \n");
        SQL.append("   fp.BRANCH_CODE, \n");
        SQL.append("   fp.BANK_ACCOUNT_ID, \n");
        SQL.append("   fp.CURRENT_ASSIGN_USER, \n");
        SQL.append("   fp.CURRENT_FACILITY_PAPER_STATUS \n");
        //    SQL.append(" ORDER BY fp.CREATED_DATE DESC  \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FacilityPaperSummaryDTO>>() {

            @Override
            public List<FacilityPaperSummaryDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    FacilityPaperSummaryDTO summaryDTO = new FacilityPaperSummaryDTO();
                    summaryDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                    summaryDTO.setBankAccountNumber(rs.getString("BANK_ACCOUNT_ID"));
                    summaryDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                    summaryDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                    summaryDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                    summaryDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                    summaryDTO.setTotalOutstandingAmount(rs.getBigDecimal("facility_outstanding_amount"));
                    summaryDTO.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                    resultList.add(summaryDTO);
                }
                return resultList;
            }


        });
    }

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
        SQL.append("  cc.CAS_CUSTOMER_NAME,  \n");
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
                if (StringUtils.isNotEmpty(rs.getString("CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                } else if (StringUtils.isNotEmpty(rs.getString("CAS_CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CAS_CUSTOMER_NAME"));
                }
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

        boolean isDivCodeIgnored = Arrays.stream(systemParameterService.getDIVCodeIgnoredUPMGroups().trim().split(",", 5)).anyMatch(facilityPaperSearchRQ.getLoginUpmAccessLevel()::contains); // If more UPM groups added to the system parameter increase the limit (5) of spitting

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  NVL(fp.IS_COMMITTEE,'N') IS_COMMITTEE,                                                  \n");
        SQL.append("  NVL(fp.IS_ESG_PAPER,'N') IS_ESG_PAPER,                                                  \n");
        SQL.append("  DECODE(fp.ASSIGN_DEPARTMENT_CODE,'CA',fp.CURRENT_ASSIGN_USER,'REG') COMMITTEE_TYPE,     \n");
        // SQL.append("  DECODE(fp.CURRENT_ASSIGN_USER,'BCC','BCC','REG') COMMITTEE_TYPE,                        \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR,                                                               \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
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
        SQL.append(" LEFT JOIN T_UPC_TEMPLATE upc ON  fp.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID \n");
        SQL.append("   LEFT JOIN T_FP_ASSIGN_DEPARTMENT tfpad                                                 \n");
        SQL.append("   ON fp.FACILITY_PAPER_ID = tfpad.FACILITY_PAPER_ID AND tfpad.STATUS='ACT'               \n");
        SQL.append("WHERE cc.IS_PRIMARY = 'Y'                                                                \n");
        SQL.append("AND fp.CURRENT_FACILITY_PAPER_STATUS IN ('DRAFT', 'IN_PROGRESS', 'PENDING', 'CANCEL')                 \n");
        SQL.append("AND (upper(fp.CURRENT_ASSIGN_USER) =:user                \n");
        if (facilityPaperSearchRQ.getAssignUsers() != null && !facilityPaperSearchRQ.getAssignUsers().isEmpty()) {
            // this line is added to get supervisor's papers into the assistant's inbox
            SQL.append("         OR upper(fp.CURRENT_ASSIGN_USER) IN (");
            SQL.append(QueryInBuilder.buildSQLINQuery(facilityPaperSearchRQ.getAssignUsers().stream().map(String::toUpperCase).collect(Collectors.toList())));
            SQL.append(" ) \n");
        }
        if (isDivCodeIgnored) {
            SQL.append("           OR (tfpad.USER_GROUP_UPM_CODE =:assignGroupUPMGroupCode AND tfpad.STATUS='ACT')) \n");
        } else {
            SQL.append("           OR (fp.ASSIGN_DEPARTMENT_CODE =:assignDepartmentCode AND tfpad.USER_GROUP_UPM_CODE =:assignGroupUPMGroupCode AND tfpad.STATUS='ACT')) \n");
        }

        SQL.append("GROUP BY fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("  fp.IS_COMMITTEE,                                                                        \n");
        SQL.append("  fp.IS_ESG_PAPER,                                                                        \n");
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
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR                                                                \n");

        //GET COMMITTEE INBOX DATA
        SQL.append("  UNION ALL                                                \n");
        SQL.append("  SELECT                                                   \n");
        SQL.append("  ASSIGNED_USER CURRENT_ASSIGN_USER,                       \n");
        SQL.append("  CUSTOMER_NAME,                                           \n");
        SQL.append("  FACILITY_PAPER_ID,                                       \n");
        SQL.append("  FP_REFERENCE_NUMBER FP_REF_NUMBER,                       \n");
        SQL.append("  BRANCH_CODE,                                             \n");
        SQL.append("  ACCOUNT_NUMBER BANK_ACCOUNT_ID,                          \n");
        SQL.append("  FACILITY_PAPER_STATUS CURRENT_FACILITY_PAPER_STATUS,     \n");
        SQL.append("  ASSIGNED_USER ASSIGN_USER_DISPLAY_NAME,                  \n");
        SQL.append("  'Y' IS_COMMITTEE, 'N' IS_ESG_PAPER,                                       \n");
        SQL.append("  COMMITTEE_TYPE, null, null, null, null, null, null, null, null, upc.TEMPLATE_NAME, upc.UPC_LABEL, upc.UPC_LABEL_BACKGROUND_COLOR, upc.UPC_LABEL_FONT_COLOR, null, null,  \n");
        SQL.append("  TO_DATE(LAST_ACTION_DATE_STR,'DD-MM-YYYY') LAST_ACTION_DATE                                          \n");
        SQL.append("  FROM                                                                                                 \n");
        SQL.append("  TABLE(CASV2_DASHBOARD.GET_CA_FORWARDED_LIST(:user)) TCA                                                  \n");

        SQL.append("  LEFT JOIN T_FACILITY_PAPER TFP ON TFP.FACILITY_PAPER_ID = TCA.FACILITY_PAPER_ID \n");
        SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON  TFP.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID \n");

        //  SQL.append("  ORDER BY LAST_ACTION_DATE DESC                                                                       \n");
        //SQL.append("  ORDER BY IS_COMMITTEE, COMMITTEE_TYPE, LAST_ACTION_DATE DESC                                         \n");

        //GET COMMITTEE INQUIRY DATA
        SQL.append("  UNION ALL                                                \n");
        SQL.append("  SELECT                                                   \n");
        SQL.append("  ASSIGNED_USER CURRENT_ASSIGN_USER,                       \n");
        SQL.append("  CUSTOMER_NAME,                                           \n");
        SQL.append("  FACILITY_PAPER_ID,                                       \n");
        SQL.append("  FP_REFERENCE_NUMBER FP_REF_NUMBER,                       \n");
        SQL.append("  BRANCH_CODE,                                             \n");
        SQL.append("  ACCOUNT_NUMBER BANK_ACCOUNT_ID,                          \n");
        SQL.append("  FACILITY_PAPER_STATUS CURRENT_FACILITY_PAPER_STATUS,     \n");
        SQL.append("  ASSIGNED_USER ASSIGN_USER_DISPLAY_NAME,                  \n");
        SQL.append("  'Y' IS_COMMITTEE, 'N' IS_ESG_PAPER,                                        \n");
        SQL.append("  'COM_INQ', null, null, null, null, null, null, null, null, upc.TEMPLATE_NAME, upc.UPC_LABEL, upc.UPC_LABEL_BACKGROUND_COLOR, upc.UPC_LABEL_FONT_COLOR, null, null,  \n");
        SQL.append("  TO_DATE(LAST_ACTION_DATE_STR,'DD-MM-YYYY') LAST_ACTION_DATE                                          \n");
        SQL.append("  FROM                                                                                                 \n");
        SQL.append("  TABLE(CASV2_DASHBOARD.GET_CA_INQ_FORWARDED_LIST(:user)) TCI                                                \n");

        SQL.append("  LEFT JOIN T_FACILITY_PAPER TFP ON TFP.FACILITY_PAPER_ID = TCI.FACILITY_PAPER_ID \n");
        SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON  TFP.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID \n");
        SQL.append("  ORDER BY LAST_ACTION_DATE DESC                                                                       \n");

        params.put("user", facilityPaperSearchRQ.getIntiatedUserName().toUpperCase());
        params.put("assignDepartmentCode", facilityPaperSearchRQ.getLoggedInUserBranchCode());
        params.put("assignGroupUPMGroupCode", facilityPaperSearchRQ.getLoginUpmAccessLevel());

        System.out.println(SQL.toString());
        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setIsCommittee(AppsConstants.YesNo.resolver(rs.getString("IS_COMMITTEE")));
                facilityPaperDTO.setIsESGPaper(AppsConstants.YesNo.resolver(rs.getString("IS_ESG_PAPER")));
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
                if (rs.getTimestamp("LAST_ACTION_DATE") != null) {
                    facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                }
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                facilityPaperDTO.setUpcTemplateName(rs.getString("TEMPLATE_NAME"));
                facilityPaperDTO.setUpcLabel(rs.getString("UPC_LABEL"));
                facilityPaperDTO.setUpcLabelBackgroundColor(rs.getString("UPC_LABEL_BACKGROUND_COLOR"));
                facilityPaperDTO.setUpcLabelFontColor(rs.getString("UPC_LABEL_FONT_COLOR"));
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
                facilityPaperDTO.setCommitteeType(rs.getString("COMMITTEE_TYPE"));

                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public Page<FacilityPaperDTO> getPagedCancelled(FacilityPaperSearchRQ facilityPaperSearchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  NVL(fp.IS_COMMITTEE,'N') IS_COMMITTEE,                                                  \n");
        SQL.append("  NVL(fp.IS_ESG_PAPER,'N') IS_ESG_PAPER,                                                  \n");
        SQL.append("  DECODE(fp.ASSIGN_DEPARTMENT_CODE,'CA',fp.CURRENT_ASSIGN_USER,'REG') COMMITTEE_TYPE,     \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        // SQL.append("  DECODE(fp.CURRENT_ASSIGN_USER,'BCC','BCC','REG') COMMITTEE_TYPE,                        \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR,                                                               \n");
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
        SQL.append(" FROM T_FACILITY_PAPER fp                                                                    \n");
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID               \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                              \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                        \n");
        SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON  fp.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID                 \n");
        SQL.append(" WHERE cc.IS_PRIMARY = 'Y'                                                                  \n");
        //   SQL.append(" AND FP.IS_COMMITTEE = 'N'                                                                  \n");
        //  SQL.append(" AND (FP.IS_COMMITTEE = 'N' OR (FP.IS_COMMITTEE = 'Y' AND upper(fp.MODIFIED_BY) = :user  ))  \n");
        // SQL.append(" AND FP.IS_COMMITTEE = 'N'                                                                \n");
        SQL.append(" AND fp.CURRENT_FACILITY_PAPER_STATUS IN ('CANCEL')                                          \n");
        SQL.append(" AND upper(fp.CURRENT_ASSIGN_USER) =:user                                                    \n");

        SQL.append(" GROUP BY fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("  fp.IS_COMMITTEE,                                                                        \n");
        SQL.append("  fp.IS_ESG_PAPER,                                                                        \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
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
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                        \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                       \n");
        SQL.append("  upc.UPC_LABEL,                                                                           \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                           \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR                                                               \n");

        //GET COMMITTEE RETURN DATA
      /*  SQL.append("  UNION ALL                                                 \n");
        SQL.append("  SELECT                                                    \n");
        SQL.append("  ASSIGNED_USER CURRENT_ASSIGN_USER,                        \n");
        SQL.append("  CUSTOMER_NAME,                                            \n");
        SQL.append("  FACILITY_PAPER_ID,                                        \n");
        SQL.append("  FP_REFERENCE_NUMBER FP_REF_NUMBER,                        \n");
        SQL.append("  BRANCH_CODE,                                              \n");
        SQL.append("  ACCOUNT_NUMBER BANK_ACCOUNT_ID,                           \n");
        SQL.append("  FACILITY_PAPER_STATUS CURRENT_FACILITY_PAPER_STATUS,      \n");
        SQL.append("  ASSIGNED_USER ASSIGN_USER_DISPLAY_NAME,                   \n");
        SQL.append("  'Y' IS_COMMITTEE,                                         \n");
        SQL.append("  COMMITTEE_TYPE, null, null, null, null, null, null, null, null, null, 'Board Paper', 'BP', '#e4643a', '#ffffff',  null,  \n");
        SQL.append("  TO_DATE(LAST_ACTION_DATE_STR,'DD-MM-YYYY') LAST_ACTION_DATE                                           \n");
        SQL.append("  FROM                                                                                                  \n");
        SQL.append("  TABLE(CASV2_DASHBOARD.GET_CA_RETURNED_LIST(:user))                                                    \n");
        SQL.append("  ORDER BY LAST_ACTION_DATE DESC                                                                        \n");*/
        //SQL.append("  ORDER BY IS_COMMITTEE, COMMITTEE_TYPE, LAST_ACTION_DATE DESC                                          \n");
        // System.out.println(SQL.toString());
        params.put("user", facilityPaperSearchRQ.getIntiatedUserName().toUpperCase());
        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setIsCommittee(AppsConstants.YesNo.resolver(rs.getString("IS_COMMITTEE")));
                facilityPaperDTO.setIsESGPaper(AppsConstants.YesNo.resolver(rs.getString("IS_ESG_PAPER")));
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
                facilityPaperDTO.setUpcTemplateName(rs.getString("TEMPLATE_NAME"));
                facilityPaperDTO.setUpcLabel(rs.getString("UPC_LABEL"));
                facilityPaperDTO.setUpcLabelBackgroundColor(rs.getString("UPC_LABEL_BACKGROUND_COLOR"));
                facilityPaperDTO.setUpcLabelFontColor(rs.getString("UPC_LABEL_FONT_COLOR"));
                // facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                if (rs.getTimestamp("LAST_ACTION_DATE") != null) {
                    facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
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
                facilityPaperDTO.setCommitteeType(rs.getString("COMMITTEE_TYPE"));
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public Page<FacilityPaperDTO> getPagedInProgress(FacilityPaperSearchRQ facilityPaperSearchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT * from (                                                                            \n");
        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  NVL(fp.IS_COMMITTEE,'N') IS_COMMITTEE,                                                  \n");
        SQL.append("  NVL(fp.IS_ESG_PAPER,'N') IS_ESG_PAPER,                                                  \n");
        SQL.append("  DECODE(fp.ASSIGN_DEPARTMENT_CODE,'CA',fp.CURRENT_ASSIGN_USER,'REG') COMMITTEE_TYPE,     \n");
        //SQL.append("  DECODE(fp.CURRENT_ASSIGN_USER,'BCC','BCC','REG') COMMITTEE_TYPE,                        \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR,                                                               \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
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
        SQL.append(" FROM T_FACILITY_PAPER fp                                                                        \n");
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID                   \n");
        SQL.append("  INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr   ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID    \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                                  \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                            \n");
        SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON  fp.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID                     \n");
        SQL.append(" WHERE cc.IS_PRIMARY = 'Y'                                                                       \n");
        //   SQL.append(" AND FP.IS_COMMITTEE = 'N'                                                                       \n");
        // SQL.append(" AND (FP.IS_COMMITTEE = 'N' OR (FP.IS_COMMITTEE = 'Y' AND upper(fp.MODIFIED_BY) = :user  ))  \n");
        SQL.append(" AND fp.CURRENT_FACILITY_PAPER_STATUS NOT IN ('DRAFT', 'REJECTED', 'APPROVED')                   \n");
//        SQL.append("AND upper(fp.CURRENT_ASSIGN_USER) !=:user                \n");
        SQL.append(" AND (upper(fp.CURRENT_ASSIGN_USER) !=:user)                                                     \n");
        SQL.append(" AND (upper(fpwfr.ASSIGN_USER) =:user OR upper(fp.CREATED_BY) =:user)                            \n");


        SQL.append(" GROUP BY fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("  fp.IS_COMMITTEE,                                                                        \n");
        SQL.append("  fp.IS_ESG_PAPER,                                                                        \n");
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
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR                                                                \n");

        //GET COMMITTEE IN_PROGRESS DATA
        SQL.append("  UNION ALL                                                 \n");
        SQL.append("  SELECT                                                    \n");
        SQL.append("  ASSIGNED_USER CURRENT_ASSIGN_USER,                        \n");
        SQL.append("  CUSTOMER_NAME,                                            \n");
        SQL.append("  FACILITY_PAPER_ID,                                        \n");
        SQL.append("  FP_REFERENCE_NUMBER FP_REF_NUMBER,                        \n");
        SQL.append("  BRANCH_CODE,                                              \n");
        SQL.append("  ACCOUNT_NUMBER BANK_ACCOUNT_ID,                           \n");
        SQL.append("  FACILITY_PAPER_STATUS CURRENT_FACILITY_PAPER_STATUS,      \n");
        SQL.append("  ASSIGNED_USER ASSIGN_USER_DISPLAY_NAME,                   \n");
        SQL.append("  'Y' IS_COMMITTEE,                                         \n");
        SQL.append("  'N' IS_ESG_PAPER,                                         \n");
        SQL.append("  COMMITTEE_TYPE, null, null, null, null, null, null, null, null, upc.TEMPLATE_NAME, upc.UPC_LABEL, upc.UPC_LABEL_BACKGROUND_COLOR, upc.UPC_LABEL_FONT_COLOR, null, null,  \n");
        SQL.append("  TO_DATE(LAST_ACTION_DATE_STR,'DD-MM-YYYY') LAST_ACTION_DATE           \n");
        SQL.append("  FROM                                                                  \n");
        SQL.append("  TABLE(CASV2_DASHBOARD.GET_CA_IN_PROGRESS_LIST(:user,:userRole)) TCA       \n");

        SQL.append("  LEFT JOIN T_FACILITY_PAPER TFP ON TFP.FACILITY_PAPER_ID = TCA.FACILITY_PAPER_ID \n");
        SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON TFP.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID \n");

        SQL.append("  ORDER BY LAST_ACTION_DATE DESC                                        \n");
        SQL.append("  ) IN_PROGRESS_DATA                                                    \n");
        //SQL.append("  ORDER BY IS_COMMITTEE, COMMITTEE_TYPE, LAST_ACTION_DATE DESC          \n");

        SQL.append("WHERE NOT EXISTS (                                                                      \n");
        SQL.append("  SELECT CA_INQ.FACILITY_PAPER_ID                                                       \n");
        SQL.append("  FROM TABLE(CASV2_DASHBOARD.GET_CA_INQ_FORWARDED_LIST(:user)) CA_INQ                   \n");
        SQL.append("  WHERE CA_INQ.FACILITY_PAPER_ID = IN_PROGRESS_DATA.FACILITY_PAPER_ID                   \n");
        SQL.append(")                                                                                       \n");

        params.put("user", facilityPaperSearchRQ.getIntiatedUserName().toUpperCase());
        params.put("loggedInUserBranchCode", facilityPaperSearchRQ.getLoggedInUserBranchCode().toUpperCase());
        params.put("userRole", "");

        System.out.println(SQL.toString());

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setIsCommittee(AppsConstants.YesNo.resolver(rs.getString("IS_COMMITTEE")));
                facilityPaperDTO.setIsESGPaper(AppsConstants.YesNo.resolver(rs.getString("IS_ESG_PAPER")));
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
                facilityPaperDTO.setUpcTemplateName(rs.getString("TEMPLATE_NAME"));
                facilityPaperDTO.setUpcLabel(rs.getString("UPC_LABEL"));
                facilityPaperDTO.setUpcLabelBackgroundColor(rs.getString("UPC_LABEL_BACKGROUND_COLOR"));
                facilityPaperDTO.setUpcLabelFontColor(rs.getString("UPC_LABEL_FONT_COLOR"));
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
                facilityPaperDTO.setCommitteeType(rs.getString("COMMITTEE_TYPE"));
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public Page<FacilityPaperDTO> getPagedMyFacilityPaperDTO(FacilityPaperSearchRQ facilityPaperSearchRQ) {

        Map<String, Object> params = new HashMap<>();
        params.put("initiatedUserName", facilityPaperSearchRQ.getIntiatedUserName().toUpperCase());
        params.put("currentAssignUser", facilityPaperSearchRQ.getCurrentAssignUser().toUpperCase());
        params.put("user", facilityPaperSearchRQ.getIntiatedUserName().toUpperCase());
        params.put("userRole", "");

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  NVL(fp.IS_COMMITTEE,'N') IS_COMMITTEE,                                                  \n");
        SQL.append("  NVL(fp.IS_ESG_PAPER,'N') IS_ESG_PAPER,                                                  \n");
        SQL.append("  DECODE(fp.ASSIGN_DEPARTMENT_CODE,'CA',fp.CURRENT_ASSIGN_USER,'REG') COMMITTEE_TYPE,     \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR,                                                               \n");
        SQL.append("  fp.PAPER_REVIEW_STATUS,                                                                 \n");
        SQL.append("  fp.REVIEW_USER_DISPLAY_NAME,                                                            \n");
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
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID             \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                            \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                      \n");
        SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON  fp.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID               \n");
        SQL.append(" WHERE cc.IS_PRIMARY = 'Y'                                                                \n");
        //   SQL.append(" AND FP.IS_COMMITTEE = 'N'                                                                \n");
        //SQL.append(" AND (FP.IS_COMMITTEE = 'N' OR (FP.IS_COMMITTEE = 'Y' AND upper(fp.MODIFIED_BY) = :user  ))  \n");

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
                SQL.append("AND upper(fp.CURRENT_ASSIGN_USER) =:initiatedUserName \n");
            } else {
                SQL.append("AND upper(fp.CREATED_BY) =:initiatedUserName\n");
            }
        }

        SQL.append("UNION                                                                                     \n");
        SQL.append("SELECT                                                                                    \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,                                                                 \n");
        SQL.append("  cus.CUSTOMER_NAME,                                                                      \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                                                                   \n");
        SQL.append("  fp.FP_REF_NUMBER,                                                                       \n");
        SQL.append("  fp.BRANCH_CODE,                                                                         \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                                                                     \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,                                                            \n");
        SQL.append("  NVL(fp.IS_COMMITTEE,'N') IS_COMMITTEE,                                                  \n");
        SQL.append("  NVL(fp.IS_ESG_PAPER,'N') IS_ESG_PAPER,                                                  \n");
        SQL.append("  DECODE(fp.ASSIGN_DEPARTMENT_CODE,'CA',fp.CURRENT_ASSIGN_USER,'REG') COMMITTEE_TYPE,     \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,                                                               \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,                                                                   \n");
        SQL.append("  fp.CREATED_DATE,                                                                        \n");
        SQL.append("  fp.IN_PROGRESS_DATE,                                                                    \n");
        SQL.append("  fp.APPROVED_DATE,                                                                       \n");
        SQL.append("  fp.CANCELED_DATE,                                                                       \n");
        SQL.append("  fp.REJECTED_DATE,                                                                       \n");
        SQL.append("  fp.CREATED_BY,                                                                          \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR,                                                               \n");
        SQL.append("  fp.PAPER_REVIEW_STATUS,                                                                 \n");
        SQL.append("  fp.REVIEW_USER_DISPLAY_NAME,                                                            \n");
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
        SQL.append("  INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID             \n");
        SQL.append("  LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                            \n");
        SQL.append("  LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                      \n");
        SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON  fp.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID               \n");
        SQL.append(" WHERE cc.IS_PRIMARY = 'Y'                                                                 \n");
        //  SQL.append(" AND FP.IS_COMMITTEE = 'N'                                                                \n");
        //   SQL.append(" AND (FP.IS_COMMITTEE = 'N' OR (FP.IS_COMMITTEE = 'Y' AND upper(fp.MODIFIED_BY) = :user  ))  \n");
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
            SQL.append("AND upper(fpwfr.ASSIGN_USER) =:currentAssignUser \n");
        }
        SQL.append("GROUP BY fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("  fp.IS_COMMITTEE,                                                                        \n");
        SQL.append("  fp.IS_ESG_PAPER,                                                                        \n");
        SQL.append("  fp.ASSIGN_DEPARTMENT_CODE,                                                              \n");
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
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,                                                       \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR                                                                \n");

        if (facilityPaperSearchRQ.getFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.APPROVED)) {
            //GET COMMITTEE APPROVED DATA
            SQL.append("  UNION ALL                                                 \n");
            SQL.append("  SELECT                                                    \n");
            SQL.append("  ASSIGNED_USER CURRENT_ASSIGN_USER,                        \n");
            SQL.append("  CUSTOMER_NAME,                                            \n");
            SQL.append("  FACILITY_PAPER_ID,                                        \n");
            SQL.append("  FP_REFERENCE_NUMBER FP_REF_NUMBER,                        \n");
            SQL.append("  BRANCH_CODE,                                              \n");
            SQL.append("  ACCOUNT_NUMBER BANK_ACCOUNT_ID,                           \n");
            SQL.append("  FACILITY_PAPER_STATUS CURRENT_FACILITY_PAPER_STATUS,      \n");
            SQL.append("  ASSIGNED_USER ASSIGN_USER_DISPLAY_NAME,                   \n");
            SQL.append("  'Y' IS_COMMITTEE,                                         \n");
            SQL.append("  'N' IS_ESG_PAPER,                                         \n");
            SQL.append("  COMMITTEE_TYPE, null, null, null, null, null, null, null, null, null, upc.TEMPLATE_NAME, upc.UPC_LABEL, upc.UPC_LABEL_BACKGROUND_COLOR, upc.UPC_LABEL_FONT_COLOR, null, null, null,  \n");
            SQL.append("  TO_DATE(LAST_ACTION_DATE_STR,'DD-MM-YYYY') LAST_ACTION_DATE                   \n");
            SQL.append("  FROM                                                                          \n");
            SQL.append(" TABLE(CASV2_DASHBOARD.GET_CA_COMMITTEE_APPROVED_LIST(:user, :userRole)) TCA       \n");

            SQL.append("  LEFT JOIN T_FACILITY_PAPER TFP ON TFP.FACILITY_PAPER_ID = TCA.FACILITY_PAPER_ID \n");
            SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON TFP.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID \n");
        }
        if (facilityPaperSearchRQ.getFacilityPaperStatus().equals(DomainConstants.FacilityPaperStatus.REJECTED)) {
            //GET COMMITTEE DECLINED DATA
            SQL.append("  UNION ALL                                                 \n");
            SQL.append("  SELECT                                                    \n");
            SQL.append("  ASSIGNED_USER CURRENT_ASSIGN_USER,                        \n");
            SQL.append("  CUSTOMER_NAME,                                            \n");
            SQL.append("  FACILITY_PAPER_ID,                                        \n");
            SQL.append("  FP_REFERENCE_NUMBER FP_REF_NUMBER,                        \n");
            SQL.append("  BRANCH_CODE,                                              \n");
            SQL.append("  ACCOUNT_NUMBER BANK_ACCOUNT_ID,                           \n");
            SQL.append("  FACILITY_PAPER_STATUS CURRENT_FACILITY_PAPER_STATUS,      \n");
            SQL.append("  ASSIGNED_USER ASSIGN_USER_DISPLAY_NAME,                   \n");
            SQL.append("  'Y' IS_COMMITTEE,                                         \n");
            SQL.append("  'N' IS_ESG_PAPER,                                         \n");
            SQL.append("  COMMITTEE_TYPE, null, null, null, null, null, null, null, null, null, upc.TEMPLATE_NAME, upc.UPC_LABEL, upc.UPC_LABEL_BACKGROUND_COLOR, upc.UPC_LABEL_FONT_COLOR, null, null, null,  \n");
            SQL.append("  TO_DATE(LAST_ACTION_DATE_STR,'DD-MM-YYYY') LAST_ACTION_DATE                   \n");
            SQL.append("  FROM                                                                          \n");
            SQL.append(" TABLE(CASV2_DASHBOARD.GET_CA_DECLINED_LIST(:user, :userRole)) TCA                 \n");

            SQL.append("  LEFT JOIN T_FACILITY_PAPER TFP ON TFP.FACILITY_PAPER_ID = TCA.FACILITY_PAPER_ID \n");
            SQL.append("  LEFT JOIN T_UPC_TEMPLATE upc ON TFP.UPC_TEMPLATE_ID = UPC.UPC_TEMPLATE_ID \n");
        }
        SQL.append("  ORDER BY LAST_ACTION_DATE DESC                                                    \n");
        // SQL.append("  ORDER BY IS_COMMITTEE, COMMITTEE_TYPE, LAST_ACTION_DATE DESC                  \n");
        // System.out.println(SQL.toString());

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setIsCommittee(AppsConstants.YesNo.resolver(rs.getString("IS_COMMITTEE")));
                facilityPaperDTO.setIsESGPaper(AppsConstants.YesNo.resolver(rs.getString("IS_ESG_PAPER")));
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
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                facilityPaperDTO.setDaysDiff(rs.getLong("DAYS_DIFF"));
                facilityPaperDTO.setUpcTemplateName(rs.getString("TEMPLATE_NAME"));
                facilityPaperDTO.setUpcLabel(rs.getString("UPC_LABEL"));
                facilityPaperDTO.setUpcLabelBackgroundColor(rs.getString("UPC_LABEL_BACKGROUND_COLOR"));
                facilityPaperDTO.setUpcLabelFontColor(rs.getString("UPC_LABEL_FONT_COLOR"));
                //facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                if (rs.getTimestamp("LAST_ACTION_DATE") != null) {
                    facilityPaperDTO.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
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
                facilityPaperDTO.setCommitteeType(rs.getString("COMMITTEE_TYPE"));
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public List<RemarkDTO> getFacilityPaperRemarksFromWorkflowRouting(Integer facilityPaperID) throws AppsException {

        List<RemarkDTO> remarkDTOList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                     \n");
        SQL.append("  fpwr.FP_WORKFLOW_ROUTING_ID,             \n");
        SQL.append("  fpwr.FACILITY_PAPER_ID,                  \n");
        SQL.append("  fpwr.ROUTING_REMARKS,                    \n");
        SQL.append("  fpwr.CREATED_DATE,                       \n");
        SQL.append("  fpwr.CREATED_BY,                          \n");
        SQL.append("  fpwr.ASSIGN_USER,                          \n");
        SQL.append("  fpwr.ROUTING_STATUS                        \n");
        SQL.append("  FROM T_FP_WORKFLOW_ROUTING fpwr          \n");
        SQL.append("  WHERE fpwr.FACILITY_PAPER_ID IS NOT NULL \n");
        SQL.append("  AND fpwr.FACILITY_PAPER_ID = :facilityPaperID \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<RemarkDTO>>() {

            @Override
            public List<RemarkDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    RemarkDTO remarkDTO = new RemarkDTO();
                    remarkDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                    remarkDTO.setFpWorkflowRoutingID(rs.getInt("FP_WORKFLOW_ROUTING_ID"));
                    remarkDTO.setAssignUser(rs.getString("CREATED_BY"));
                    remarkDTO.setComment(rs.getString("ROUTING_REMARKS"));
                    if (rs.getTimestamp("CREATED_DATE") != null) {
                        remarkDTO.setDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                    }
                    remarkDTO.setActionBy(rs.getString("ASSIGN_USER"));
                    remarkDTO.setAction(rs.getString("ROUTING_STATUS"));

                    remarkDTOList.add(remarkDTO);
                }
                return remarkDTOList;
            }
        });

    }

    public List<RemarkDTO> getFacilityPaperRemarksFromStatusHistory(Integer facilityPaperID) throws AppsException {

        List<RemarkDTO> remarkDTOListFromStatusHistory = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                              \n");
        SQL.append("fpsh.FP_STATUS_HISTORY_ID,                          \n");
        SQL.append("fpsh.FACILITY_PAPER_ID,                             \n");
        SQL.append("fpsh.REMARK,                                        \n");
        SQL.append("fpsh.UPDATE_BY,                                     \n");
        SQL.append("fpsh.UPDATED_DATE,                                  \n");
        SQL.append("fpsh.FACILITY_PAPER_STATUS,                         \n");
        SQL.append("fpsh.ASSIGN_USER,                                   \n");
        SQL.append("fpsh.ACTION_MESSAGE                                 \n");
        SQL.append("FROM T_FP_STATUS_HISTORY fpsh                       \n");
        SQL.append("WHERE fpsh.FACILITY_PAPER_ID IS NOT NULL            \n");
        SQL.append("AND fpsh.FACILITY_PAPER_STATUS NOT IN('DRAFT')      \n");
        SQL.append("  AND fpsh.FACILITY_PAPER_ID = :facilityPaperID     \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<RemarkDTO>>() {

            @Override
            public List<RemarkDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    RemarkDTO remarkDTO = new RemarkDTO();
                    remarkDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                    remarkDTO.setFpWorkflowRoutingID(rs.getInt("FP_STATUS_HISTORY_ID"));
                    remarkDTO.setAssignUser(rs.getString("UPDATE_BY"));
                    remarkDTO.setComment(rs.getString("REMARK"));
                    if (rs.getTimestamp("UPDATED_DATE") != null) {
                        remarkDTO.setDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("UPDATED_DATE")));
                    }
                    remarkDTO.setAction(rs.getString("FACILITY_PAPER_STATUS"));
                    remarkDTO.setActionBy(rs.getString("ASSIGN_USER"));
                    remarkDTO.setActionMessage(rs.getString("ACTION_MESSAGE"));

                    remarkDTOListFromStatusHistory.add(remarkDTO);
                }
                return remarkDTOListFromStatusHistory;
            }
        });

    }

    public List<RemarkDTO> getFacilityPaperRemarksFromFpComment(Integer facilityPaperID) throws AppsException {

        List<RemarkDTO> remarkDTOListFromFpComment = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                  \n");
        SQL.append("cc.FP_COMMENT_ID,                                      \n");
        SQL.append("cc.FACILITY_PAPER_ID,                                  \n");
        SQL.append("cc.FP_COMMENT,                                         \n");
        SQL.append("cc.ACTION_MESSAGE,                                     \n");
        SQL.append("cc.CREATED_BY,                                         \n");
        SQL.append("cc.CREATED_DATE,                                       \n");
        SQL.append("cc.CREATED_USER_DISPLAY_NAME,                          \n");
        SQL.append("cc.ASSIGNED_USER_DISPLAY_NAME                          \n");
        SQL.append("FROM T_FP_COMMENT cc                                   \n");
        SQL.append("WHERE cc.FACILITY_PAPER_ID IS NOT NULL                 \n");
        SQL.append("  AND cc.FACILITY_PAPER_ID = :facilityPaperID          \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<RemarkDTO>>() {

            @Override
            public List<RemarkDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    RemarkDTO remarkDTO = new RemarkDTO();
                    remarkDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                    remarkDTO.setFpWorkflowRoutingID(rs.getInt("FP_COMMENT_ID"));
                    if (StringUtils.isNotEmpty(rs.getString("ASSIGNED_USER_DISPLAY_NAME"))) {
                        remarkDTO.setAssignUser(rs.getString("ASSIGNED_USER_DISPLAY_NAME"));
                    } else if (StringUtils.isNotEmpty(rs.getString("CREATED_USER_DISPLAY_NAME"))) {
                        remarkDTO.setAssignUser(rs.getString("CREATED_USER_DISPLAY_NAME"));
                    } else {
                        remarkDTO.setAssignUser(rs.getString("CREATED_BY"));
                    }
                    remarkDTO.setComment(rs.getString("FP_COMMENT"));
                    remarkDTO.setActionMessage(rs.getString("ACTION_MESSAGE"));
                    if (rs.getTimestamp("CREATED_DATE") != null) {
                        remarkDTO.setDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                    }

                    remarkDTOListFromFpComment.add(remarkDTO);
                }
                return remarkDTOListFromFpComment;
            }
        });

    }

    public UserDaDTO getUserDAByLoggedInUser(String userName) throws AppsException {

        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName.toUpperCase());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                     \n");
        SQL.append("uda.USER_DA_ID,                 \n");
        SQL.append("uda.MAX_AMOUNT                   \n");
        SQL.append("FROM T_USER_DA uda                  \n");
        SQL.append("WHERE upper(uda.USER_NAME) = :userName            \n");
        SQL.append("AND uda.STATUS = 'ACT'              \n");
        SQL.append("AND uda.APPROVE_STATUS = 'APPROVED' \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<UserDaDTO>() {

            @Override
            public UserDaDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

                UserDaDTO userDaDTO = null;
                if (rs.next()) {
                    userDaDTO = new UserDaDTO();
                    userDaDTO.setUserDaID(rs.getInt("USER_DA_ID"));
                    userDaDTO.setMaxAmount(rs.getBigDecimal("MAX_AMOUNT"));

                }
                return userDaDTO;
            }
        });
    }

    public List<UpcTemplateDTO> getActiveApprovedUpcTemplateDtoList() throws AppsException {

        List<UpcTemplateDTO> responseList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                \n");
        SQL.append("upt.UPC_TEMPLATE_ID,                  \n");
        SQL.append("upt.TEMPLATE_NAME                     \n");
        SQL.append("FROM T_UPC_TEMPLATE upt               \n");
        SQL.append("WHERE upt.UPC_TEMPLATE_ID IS NOT NULL \n");
        SQL.append("AND upt.STATUS = 'ACT'                \n");
        SQL.append("AND upt.APPROVE_STATUS = 'APPROVED'   \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), new ResultSetExtractor<List<UpcTemplateDTO>>() {
            @Override
            public List<UpcTemplateDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()) {
                    UpcTemplateDTO upcTemplateDTO = new UpcTemplateDTO();
                    upcTemplateDTO.setUpcTemplateID(resultSet.getInt("UPC_TEMPLATE_ID"));
                    upcTemplateDTO.setTemplateName(resultSet.getString("TEMPLATE_NAME"));
                    responseList.add(upcTemplateDTO);
                }
                return responseList;
            }
        });
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
        SQL.append("  SELECT UNIQUE fp.FACILITY_PAPER_ID                                                      \n");
        SQL.append("  FROM T_FACILITY_PAPER fp                                                                \n");
        SQL.append("    INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID          \n");
        SQL.append("   LEFT JOIN T_FP_ASSIGN_DEPARTMENT tfpad                                                 \n");
        SQL.append("   ON fp.FACILITY_PAPER_ID = tfpad.FACILITY_PAPER_ID AND tfpad.STATUS='ACT'               \n");
        SQL.append("  WHERE                                                                                   \n");
        SQL.append("    cc.IS_PRIMARY = 'Y'                                                                  \n");
        SQL.append("    AND fp.CURRENT_FACILITY_PAPER_STATUS IN ('DRAFT', 'IN_PROGRESS', 'PENDING', 'CANCEL') \n");
        SQL.append("    AND (upper(fp.CURRENT_ASSIGN_USER) = :user                                             \n");

        if (searchRQ.getAssignUsers() != null && !searchRQ.getAssignUsers().isEmpty()) {
            // this line is added to get supervisor's papers into the assistant's inbox
            SQL.append("         OR upper(fp.CURRENT_ASSIGN_USER) IN (");
            SQL.append(QueryInBuilder.buildSQLINQuery(searchRQ.getAssignUsers().stream().map(String::toUpperCase).collect(Collectors.toList())));
            SQL.append(" ) ) \n");
        } else {
            SQL.append(" )  \n");
        }

        SQL.append("    OR (upper(fp.ASSIGN_DEPARTMENT_CODE) =:loggedInUserBranchCode  \n");
        SQL.append("        AND  upper(tfpad.USER_GROUP_UPM_CODE) =:loggedInUserUPMGroupCode \n");
        SQL.append("        AND  tfpad.STATUS='ACT') \n");
        SQL.append(")                                                                                         \n");

        params.put("user", searchRQ.getIntiatedUserName().toUpperCase());
        params.put("loggedInUserBranchCode", searchRQ.getLoggedInUserBranchCode().toUpperCase());
        params.put("loggedInUserUPMGroupCode", searchRQ.getLoggedInUserUPMGroupCode().toUpperCase());

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
        SQL.append("    AND (upper(fp.CURRENT_ASSIGN_USER) !=:user)   \n");
        SQL.append("    AND (upper(fpwfr.ASSIGN_USER) =:user OR upper(fp.CREATED_BY) =:user)          \n");
        SQL.append("  GROUP BY fp.FACILITY_PAPER_ID                                                   \n");
        SQL.append(")                                                                                 \n");

        params.put("user", searchRQ.getIntiatedUserName().toUpperCase());
        params.put("loggedInUserBranchCode", searchRQ.getLoggedInUserBranchCode().toUpperCase());

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

    public BigDecimal getTotalCashAmount(Integer facilityPaperID) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        params.put("facilityPaperID", facilityPaperID);

        SQL.append("SELECT SUM(tfs.CASH_AMOUNT)    as CASH_AMOUNT                                                            \n");
        SQL.append("FROM T_FACILITY_SECURITY tfs                                                                             \n");
        SQL.append("WHERE TFS.FACILITY_SECURITY_ID IN (                                                                      \n");
        SQL.append("  SELECT DISTINCT tfs.FACILITY_SECURITY_ID                                                               \n");
        SQL.append("  FROM T_FACILITY_PAPER tfp                                                                              \n");
        SQL.append("    INNER JOIN T_FACILITY tf ON tfp.FACILITY_PAPER_ID = tf.FACILITY_PAPER_ID                             \n");
        SQL.append("    INNER JOIN T_FACILITY_F_SECURITY tffs ON tf.FACILITY_ID = tffs.FACILITY_ID AND tffs.STATUS = 'ACT'   \n");
        SQL.append("    INNER JOIN T_FACILITY_SECURITY tfs ON tffs.FACILITY_SECURITY_ID = tfs.FACILITY_SECURITY_ID           \n");
        SQL.append("  WHERE tfp.FACILITY_PAPER_ID = :facilityPaperID                                                         \n");
        SQL.append("   AND tfs.IS_CASH_SECURITY='Y'                                                                          \n");
        SQL.append("   AND tfs.CASH_AMOUNT IS NOT NULL                                                                       \n");
        SQL.append(")                                                                                                        \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<BigDecimal>() {
            @Override
            public BigDecimal extractData(ResultSet rs) throws SQLException, DataAccessException {
                BigDecimal count = DecimalCalculator.getDefaultZero();
                while (rs.next()) {
                    count = rs.getBigDecimal("CASH_AMOUNT");
                }
                if (count != null) {
                    return count;
                } else {
                    return DecimalCalculator.getDefaultZero();
                }

            }
        });
    }


    public DashboardCountDTO getDashboardCount(DashboardCountSearchRQ searchRQ) {
        DashboardCountDTO countDTO = new DashboardCountDTO();
        Map<String, Object> params = new HashMap<>();

        params.put("initiatedUserName", searchRQ.getIntiatedUserName().toUpperCase());
        params.put("currentAssignUser", searchRQ.getCurrentAssignUser().toUpperCase());

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT *                                                                                       \n");
        SQL.append("FROM (SELECT count(*) AS DRAFT_FACILITY                                                        \n");
        SQL.append("      FROM (SELECT                                                                             \n");
        SQL.append("              fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("              fp.FP_REF_NUMBER                                                                 \n");
        SQL.append("            FROM T_FACILITY_PAPER fp                                                           \n");
        SQL.append("              INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID      \n");
        SQL.append("            WHERE cc.IS_PRIMARY = 'Y'                                                          \n");
        SQL.append("                  AND fp.CURRENT_FACILITY_PAPER_STATUS != 'REMOVED'                            \n");
        SQL.append("                  AND upper(fp.CURRENT_ASSIGN_USER) =:initiatedUserName                        \n");
        SQL.append("            UNION                                                                              \n");
        SQL.append("            SELECT                                                                             \n");
        SQL.append("              fp.FACILITY_PAPER_ID,                                                            \n");
        SQL.append("              fp.FP_REF_NUMBER                                                                 \n");
        SQL.append("            FROM T_FACILITY_PAPER fp                                                           \n");
        SQL.append("              INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                           \n");
        SQL.append("                ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                              \n");
        SQL.append("              INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID      \n");
        SQL.append("            WHERE cc.IS_PRIMARY = 'Y'                                                          \n");
        SQL.append("                  AND fp.CURRENT_FACILITY_PAPER_STATUS = 'REMOVED'                             \n");
        SQL.append("                  AND upper(fpwfr.ASSIGN_USER) =:currentAssignUser                             \n");
        SQL.append("            GROUP BY fp.FACILITY_PAPER_ID,                                                     \n");
        SQL.append("              fp.FP_REF_NUMBER)) df,                                                           \n");
        SQL.append("  (SELECT count(*) AS IN_PROGRESS_FACILITY                                                     \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'IN_PROGRESS'                            \n");
        SQL.append("               AND upper(fp.CREATED_BY) =:initiatedUserName                                    \n");
        SQL.append("         UNION                                                                                 \n");
        SQL.append("         SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                              \n");
        SQL.append("             ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                 \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'IN_PROGRESS'                            \n");
        SQL.append("               AND upper(fpwfr.ASSIGN_USER) =:currentAssignUser                                \n");
        SQL.append("         GROUP BY fp.FACILITY_PAPER_ID,                                                        \n");
        SQL.append("           fp.FP_REF_NUMBER)) ipf,                                                             \n");
        SQL.append("  (SELECT count(*) AS REJECTED_FACILITY                                                        \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'REJECTED'                               \n");
        SQL.append("               AND upper(fp.CREATED_BY) =:initiatedUserName                                    \n");
        SQL.append("         UNION                                                                                 \n");
        SQL.append("         SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                              \n");
        SQL.append("             ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                 \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'REJECTED'                               \n");
        SQL.append("               AND upper(fpwfr.ASSIGN_USER) =:currentAssignUser                                \n");
        SQL.append("         GROUP BY fp.FACILITY_PAPER_ID,                                                        \n");
        SQL.append("           fp.FP_REF_NUMBER)) rejf,                                                            \n");
        SQL.append("  (SELECT count(*) AS APPROVED_FACILITY                                                        \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");
        SQL.append("               AND upper(fp.CREATED_BY) =:initiatedUserName                                    \n");
        SQL.append("         UNION                                                                                 \n");
        SQL.append("         SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                              \n");
        SQL.append("             ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                 \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");
        SQL.append("               AND upper(fpwfr.ASSIGN_USER) =:currentAssignUser                                \n");
        SQL.append("         GROUP BY fp.FACILITY_PAPER_ID,                                                        \n");
        SQL.append("           fp.FP_REF_NUMBER)) af,                                                              \n");

        SQL.append("  (SELECT count(*) AS REVIEW_REJECTED_FACILITY                                                 \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");
        SQL.append("               AND fp.PAPER_REVIEW_STATUS = 'REJECTED'                                         \n");
        SQL.append("               AND upper(fp.CREATED_BY) =:initiatedUserName                                    \n");
        SQL.append("         UNION                                                                                 \n");
        SQL.append("         SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_FP_WORKFLOW_ROUTING fpwfr                                              \n");
        SQL.append("             ON fp.FACILITY_PAPER_ID = fpwfr.FACILITY_PAPER_ID                                 \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");
        SQL.append("               AND fp.PAPER_REVIEW_STATUS = 'REJECTED'                                         \n");
        SQL.append("               AND upper(fpwfr.ASSIGN_USER) =:currentAssignUser                                \n");
        SQL.append("         GROUP BY fp.FACILITY_PAPER_ID,                                                        \n");
        SQL.append("           fp.FP_REF_NUMBER)) rf,                                                              \n");

        SQL.append("  (SELECT count(*) AS CANCEl_FACILITY                                                          \n");
        SQL.append("   FROM (SELECT                                                                                \n");
        SQL.append("           fp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("           fp.FP_REF_NUMBER                                                                    \n");
        SQL.append("         FROM T_FACILITY_PAPER fp                                                              \n");
        SQL.append("           INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID         \n");
        SQL.append("         WHERE cc.IS_PRIMARY = 'Y'                                                             \n");
        SQL.append("               AND fp.CURRENT_FACILITY_PAPER_STATUS = 'CANCEL'                                 \n");
        SQL.append("               AND upper(fp.CURRENT_ASSIGN_USER) =:initiatedUserName                           \n");
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

    public Page<FPReviewSummaryDTO> getPagedFacilityPaperReviewSummary(FPReviewSummarySearchRQ searchRQ) throws AppsException {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT                                                                                 \n");
        SQL.append("   tfp.CURRENT_ASSIGN_USER,                                                             \n");
        SQL.append("   tfp.ASSIGN_USER_DISPLAY_NAME,                                                        \n");
        SQL.append("   tfp.CURRENT_ASSIGN_USER_ID,                                                          \n");
        SQL.append("   tfp.ASSIGN_USER_UPM_ID,                                                              \n");
        SQL.append("   sum(tfp.TOTAL_DIRECT_EXPOSURE_NEW) AS DIRECT_EXPOSURE,                               \n");
        SQL.append("   sum(tfp.TOTAL_INDIRECT_EXPOSURE_NEW) AS INDIRECT_EXPOSURE,                           \n");
        SQL.append("   sum(tfp.TOTAL_EXPOSURE_NEW) AS TOTAL_FACILITY_AMOUNT,                                \n");
        SQL.append("   count(DISTINCT tfp.FP_REF_NUMBER)   AS NO_OF_FACILITIES,                             \n");
        SQL.append("   avg(tfp.TOTAL_EXPOSURE_NEW) AS AVERAGE,                                              \n");
        SQL.append("   sum(ROUND(tfp.APPROVED_DATE -tfp.IN_PROGRESS_DATE,0)) as NO_DAYS                     \n");
        SQL.append(" FROM T_FACILITY_PAPER tfp                                                              \n");
        SQL.append("   INNER JOIN T_CAS_CUSTOMER tcc ON tfp.FACILITY_PAPER_ID = tcc.FACILITY_PAPER_ID       \n");
        SQL.append("   INNER JOIN T_CUSTOMER tc ON tcc.CUSTOMER_ID = tc.CUSTOMER_ID                         \n");
        SQL.append(" WHERE tfp.CURRENT_ASSIGN_USER IS NOT NULL                                              \n");
        SQL.append("       AND tc.CUSTOMER_ID IS NOT NULL                                                   \n");
        SQL.append("       AND tfp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                               \n");

        if (searchRQ.getUserName() != null && StringUtils.isNotEmpty(searchRQ.getUserName())) {
            SQL.append("AND upper(tfp.CURRENT_ASSIGN_USER) LIKE '%" +
                    searchRQ.getUserName().toUpperCase() + "%'                            \n");
        }

        if (searchRQ.getApprovedUser() != null && StringUtils.isNotEmpty(searchRQ.getApprovedUser())) {
            SQL.append("AND upper(tfp.ASSIGN_USER_DISPLAY_NAME) LIKE '%" +
                    searchRQ.getApprovedUser().toUpperCase() + "%'                            \n");
        }

        if (searchRQ.getPaperReviewStatusList() != null && !searchRQ.getPaperReviewStatusList().isEmpty()) {
            SQL.append("AND tfp.PAPER_REVIEW_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getPaperReviewStatusList()) + ") \n");
        }

        if (searchRQ.getDateRange() == DomainConstants.DateRange.CUSTOM && searchRQ.getFromDate() != null && searchRQ.getToDate() != null) {
            SQL.append("AND (tfp.APPROVED_DATE BETWEEN  " +
                    "TO_DATE('" + searchRQ.getFromDate() + "','yyyy/mm/dd') AND TO_DATE('" + searchRQ.getToDate() + "','yyyy/mm/dd') ) \n");
        }

        if (searchRQ.getDateRange() == DomainConstants.DateRange.LAST_30_DAYS) {
            String before30Days = LocalDate.now().minusDays(30).format(DateTimeFormatter.ofPattern(CalendarUtil.DEFAULT_DATE_FORMAT));
            SQL.append("AND tfp.APPROVED_DATE >=  TO_DATE('" + before30Days + "','dd/mm/yyyy') \n");
        }

        if (searchRQ.getDateRange() == DomainConstants.DateRange.THIS_MONTH) {
            String thisMonthStartingDate = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ofPattern(CalendarUtil.DEFAULT_DATE_FORMAT));
            SQL.append("AND tfp.APPROVED_DATE >=  TO_DATE('" + thisMonthStartingDate + "','dd/mm/yyyy') \n");
        }

        if (searchRQ.getAssignUserUpmGroupCode() != null && StringUtils.isNotEmpty(searchRQ.getAssignUserUpmGroupCode())) {
            SQL.append("AND tfp.ASSIGN_USER_UPM_GROUP_CODE =:upmCode                                   \n");
            params.put("upmCode", searchRQ.getAssignUserUpmGroupCode());
        } else if (searchRQ.getOtherUpmAccessLevels() != null && !searchRQ.getOtherUpmAccessLevels().isEmpty()) {
            SQL.append("AND tfp.ASSIGN_USER_UPM_GROUP_CODE IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getOtherUpmAccessLevels()) + ") \n");
        }

        SQL.append(" GROUP BY (tfp.CURRENT_ASSIGN_USER_ID, tfp.CURRENT_ASSIGN_USER,tfp.ASSIGN_USER_DISPLAY_NAME,tfp.ASSIGN_USER_UPM_ID)     \n");

        return getPagedData(SQL.toString(), params, new RowMapper<FPReviewSummaryDTO>() {
            @Nullable
            @Override
            public FPReviewSummaryDTO mapRow(ResultSet rs, int i) throws SQLException {

                FPReviewSummaryDTO reviewSummaryDTO = new FPReviewSummaryDTO();
                reviewSummaryDTO.setAssignedUserID(rs.getInt("CURRENT_ASSIGN_USER_ID"));
                reviewSummaryDTO.setUpmID(rs.getInt("ASSIGN_USER_UPM_ID"));
                reviewSummaryDTO.setAverage(rs.getDouble("AVERAGE"));
                reviewSummaryDTO.setNumberOfFacilities(rs.getInt("NO_OF_FACILITIES"));
                reviewSummaryDTO.setTotalFacilityAmount(rs.getDouble("TOTAL_FACILITY_AMOUNT"));
                reviewSummaryDTO.setTotalDirectExposureAmount(rs.getDouble("DIRECT_EXPOSURE"));
                reviewSummaryDTO.setTotalIndirectExposureAmount(rs.getDouble("INDIRECT_EXPOSURE"));
                reviewSummaryDTO.setUserName(rs.getString("CURRENT_ASSIGN_USER"));
                reviewSummaryDTO.setDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                reviewSummaryDTO.setNoOfDays(rs.getDouble("NO_DAYS"));
                return reviewSummaryDTO;
            }
        }, searchRQ);
    }


    public Page<FacilityReviewDTO> getPagedFacilitiesForReview(FacilityReviewSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT                                                                                 \n");
        SQL.append("   tc.CUSTOMER_ID,                                                                      \n");
        SQL.append("   tfp.FACILITY_PAPER_ID,                                                               \n");
        SQL.append("   tc.CUSTOMER_NAME AS CUSTOMER,                                                        \n");
        SQL.append("   tfp.FP_REF_NUMBER AS FACILITY,                                                       \n");
        SQL.append("   tfp.APPROVED_DATE,                                                                   \n");
        SQL.append("   tfp.TOTAL_EXPOSURE_NEW,                                                              \n");
        SQL.append("   tfp.TOTAL_EXPOSURE_PREVIOUS,                                                         \n");
        SQL.append("   tfp.PAPER_REVIEW_STATUS,                                                             \n");
        SQL.append("   tfp.REVIEW_USER_DISPLAY_NAME,                                                        \n");
        SQL.append("   ROUND(tfp.APPROVED_DATE -tfp.IN_PROGRESS_DATE,0) AS NO_OF_DAYS                       \n");
        SQL.append(" FROM T_FACILITY_PAPER tfp                                                              \n");
        SQL.append("   INNER JOIN T_CAS_CUSTOMER tfpc ON tfp.FACILITY_PAPER_ID = tfpc.FACILITY_PAPER_ID     \n");
        SQL.append("   INNER JOIN T_CUSTOMER tc ON tfpc.CUSTOMER_ID = tc.CUSTOMER_ID                        \n");
        SQL.append(" WHERE tfp.FACILITY_PAPER_ID IS NOT NULL                                                \n");
        SQL.append(" AND tfp.CURRENT_FACILITY_PAPER_STATUS = 'APPROVED'                                     \n");
        SQL.append(" AND tfpc.IS_PRIMARY = 'Y'                                                              \n");

        if (searchRQ.getCurrentAssignedUserID() != null) {
            SQL.append("AND tfp.CURRENT_ASSIGN_USER_ID =:userID                                                \n");
            params.put("userID", searchRQ.getCurrentAssignedUserID());
        }

        if (searchRQ.getCustomerName() != null && StringUtils.isNotEmpty(searchRQ.getCustomerName())) {
            SQL.append("AND upper(tc.CUSTOMER_NAME) LIKE '%" +
                    searchRQ.getCustomerName().toUpperCase() + "%'                                            \n");
        }

        if (searchRQ.getFpRefNumber() != null && StringUtils.isNotEmpty(searchRQ.getFpRefNumber())) {
            SQL.append("AND upper(tfp.FP_REF_NUMBER) LIKE '%" +
                    searchRQ.getFpRefNumber().toUpperCase() + "%'                                            \n");
        }

        if (searchRQ.getDateRange() == DomainConstants.DateRange.CUSTOM && searchRQ.getFromDate() != null && searchRQ.getToDate() != null) {
            SQL.append("AND (tfp.APPROVED_DATE BETWEEN  " +
                    "TO_DATE('" + searchRQ.getFromDate() + "','yyyy/mm/dd') AND TO_DATE('" + searchRQ.getToDate() + "','yyyy/mm/dd') ) \n");
        }

        if (searchRQ.getDateRange() == DomainConstants.DateRange.LAST_30_DAYS) {
            String before30Days = LocalDate.now().minusDays(30).format(DateTimeFormatter.ofPattern(CalendarUtil.DEFAULT_DATE_FORMAT));
            SQL.append("AND tfp.APPROVED_DATE >=  TO_DATE('" + before30Days + "','dd/mm/yyyy') \n");
        }

        if (searchRQ.getDateRange() == DomainConstants.DateRange.THIS_MONTH) {
            String thisMonthStartingDate = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ofPattern(CalendarUtil.DEFAULT_DATE_FORMAT));
            SQL.append("AND tfp.APPROVED_DATE >=  TO_DATE('" + thisMonthStartingDate + "','dd/mm/yyyy') \n");
        }

        if (searchRQ.getPaperReviewStatusList() != null && !searchRQ.getPaperReviewStatusList().isEmpty()) {
            SQL.append("       AND tfp.PAPER_REVIEW_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getPaperReviewStatusList()) + ") \n");
        }

        SQL.append(" ORDER BY tfp.FP_REF_NUMBER DESC,  tfp.CREATED_DATE DESC                                       \n");

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityReviewDTO>() {

            @Nullable
            @Override
            public FacilityReviewDTO mapRow(ResultSet rs, int i) throws SQLException {
                FacilityReviewDTO facilityReviewDTO = new FacilityReviewDTO();
                facilityReviewDTO.setCustomerName(rs.getString("CUSTOMER"));
                facilityReviewDTO.setReviewUserDisplayName(rs.getString("REVIEW_USER_DISPLAY_NAME"));
                facilityReviewDTO.setCustomerID(rs.getInt("CUSTOMER_ID"));
                facilityReviewDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityReviewDTO.setTotalExposureNew(rs.getBigDecimal("TOTAL_EXPOSURE_NEW"));
                facilityReviewDTO.setTotalExposurePrevious(rs.getBigDecimal("TOTAL_EXPOSURE_PREVIOUS"));
                facilityReviewDTO.setFpRefNumber(rs.getString("FACILITY"));
                facilityReviewDTO.setNoOfDays(rs.getDouble("NO_OF_DAYS"));
                facilityReviewDTO.setPaperReviewStatus(DomainConstants.PaperReviewStatus.resolve(rs.getString("PAPER_REVIEW_STATUS")));
                facilityReviewDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("APPROVED_DATE")));
                return facilityReviewDTO;
            }
        }, searchRQ);
    }


    public BooleanValueDTO isAbleToReturnFacilityPaperToAgent(FacilityPaperDTO facilityPaperDTO) throws SQLException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("facilityPaperID", facilityPaperDTO.getFacilityPaperID());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT COUNT(fpsh.FP_STATUS_HISTORY_ID) AS COUNT                            \n");
        SQL.append("FROM T_FP_STATUS_HISTORY fpsh                                               \n");
        SQL.append("WHERE fpsh.FACILITY_PAPER_ID = :facilityPaperID                             \n");
        SQL.append("AND fpsh.ASSIGN_USER NOT IN ('" + facilityPaperDTO.getCreatedBy() + "','" + facilityPaperDTO.getCurrentAssignUser() + "')");

        Long count = namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
        if (count != null) {
            return new BooleanValueDTO(count.intValue() < 1);
        } else {
            return new BooleanValueDTO(false);
        }
    }


    public List<FPStatusHistoryDTO> getFPDirectReturnUsersList(FacilityPaperDTO facilityPaperDTO) throws AppsException {

        List<FPStatusHistoryDTO> responseList = new ArrayList<>();
        //This addedUsersIDList is used to restrict duplicates and Return Users and Order by Updated Date
        List<Integer> addedUsersIDList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperDTO.getFacilityPaperID());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                                             \n");
        SQL.append("  ASSIGN_USER,                                                                                                     \n");
        SQL.append("  ASSIGN_USER_ID,                                                                                                  \n");
        SQL.append("  ASSIGN_USER_DISPLAY_NAME,                                                                                        \n");
        SQL.append("  ASSIGN_USER_UPM_ID,                                                                                              \n");
        SQL.append("  ASSIGN_USER_DIV_CODE,                                                                                              \n");
        SQL.append("  ASSIGN_USER_UPM_GROUP_CODE                                                                                       \n");
        SQL.append("FROM T_FP_STATUS_HISTORY tfpsh                                                                                     \n");
        SQL.append("  WHERE tfpsh.FACILITY_PAPER_ID IS NOT NULL AND tfpsh.ASSIGN_USER_DIV_CODE IS NOT NULL                               \n");
        SQL.append("AND tfpsh.FACILITY_PAPER_ID = :facilityPaperID                                                                     \n");
        SQL.append("ORDER BY tfpsh.UPDATED_DATE  DESC                                                                                  \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPStatusHistoryDTO>>() {
            @Override
            public List<FPStatusHistoryDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()) {
                    //This If Condition is to restrict adding Duplicates cause quarry returns duplicates : order by and group by does not work with proper order by updated date
                    if (StringUtils.isNotEmpty(resultSet.getString("ASSIGN_USER_ID")) && !addedUsersIDList.contains(resultSet.getInt("ASSIGN_USER_ID"))) {
                        FPStatusHistoryDTO fpStatusHistoryDTO = new FPStatusHistoryDTO();
                        fpStatusHistoryDTO.setAssignUserID(resultSet.getInt("ASSIGN_USER_ID"));
                        fpStatusHistoryDTO.setAssignUser(resultSet.getString("ASSIGN_USER"));
                        fpStatusHistoryDTO.setAssignUserDisplayName(resultSet.getString("ASSIGN_USER_DISPLAY_NAME"));
                        fpStatusHistoryDTO.setAssignUserUpmID(resultSet.getInt("ASSIGN_USER_UPM_ID"));
                        fpStatusHistoryDTO.setAssignUserDivCode(resultSet.getString("ASSIGN_USER_DIV_CODE"));
                        fpStatusHistoryDTO.setAssignUserUpmGroupCode(resultSet.getString("ASSIGN_USER_UPM_GROUP_CODE"));
                        responseList.add(fpStatusHistoryDTO);
                        addedUsersIDList.add(resultSet.getInt("ASSIGN_USER_ID"));
                    }
                }
                return responseList;
            }
        });
    }

    public List<LeadFacilityPaperStatusDetailDTO> getFacilityPaperHistory(FacilityPaper facilityPaper) throws AppsException {

        List<LeadFacilityPaperStatusDetailDTO> facilityPaperStatusDetailRS = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaper.getFacilityPaperID());
        StringBuilder SQL = new StringBuilder();

      /*  SQL.append("SELECT                              \n");
        SQL.append("tfp.FACILITY_PAPER_ID,              \n");
        SQL.append("tfp.FP_REF_NUMBER,                  \n");
        SQL.append("tfp.CREATED_DATE,                   \n");
        SQL.append("tfsh.UPDATE_BY,                     \n");
        SQL.append("tfp.CURRENT_FACILITY_PAPER_STATUS,  \n");
        SQL.append("tfp.ASSIGN_USER_DISPLAY_NAME,       \n");
        SQL.append("tfsh.FACILITY_PAPER_STATUS ,        \n");
        SQL.append("tfsh.ACTION_MESSAGE,                \n");
        SQL.append("tfsh.UPDATED_USER,                  \n");
        SQL.append("tfsh.ASSIGN_USER_ID,                \n");
        SQL.append("tfsh.ASSIGN_DEPARTMENT_CODE,        \n");
        SQL.append("tfsh.IS_PUBLIC,                     \n");
        SQL.append("tfsh.IS_USERS_ONLY,                 \n");
        SQL.append("tfsh.IS_DIVISION_ONLY,              \n");
        SQL.append("tfsh.UPDATED_DATE                   \n");
        SQL.append("FROM T_FP_STATUS_HISTORY tfsh                                                                                   \n");
        SQL.append("INNER JOIN T_FACILITY_PAPER tfp ON TFSH .FACILITY_PAPER_ID = tfp.FACILITY_PAPER_ID                              \n");
        SQL.append("WHERE TFSH.FP_STATUS_HISTORY_ID IS NOT NULL                                                                     \n");
        SQL.append("AND tfp.FACILITY_PAPER_ID =:facilityPaperID                                                                     \n");
        SQL.append("ORDER BY tfsh.UPDATED_DATE ASC                                                                                  \n");*/

        SQL.append(" SELECT * FROM (  SELECT    \n");
        SQL.append(" TO_DATE(TFSH.UPDATED_DATE,'dd/mm/yyyy hh24:mi:ss') UPDATED_DATE_STR, \n");
        SQL.append(" TFP.FACILITY_PAPER_ID,    \n");
        SQL.append(" TFP.FP_REF_NUMBER,    \n");
        SQL.append(" TFP.CREATED_DATE,   \n");
        SQL.append(" TFSH.UPDATE_BY,     \n");
        SQL.append(" TFP.CURRENT_FACILITY_PAPER_STATUS,   \n");
        SQL.append(" TFP.ASSIGN_USER_DISPLAY_NAME, \n");
        SQL.append(" TFSH.FACILITY_PAPER_STATUS ,     \n");
        SQL.append(" TFSH.ACTION_MESSAGE ,   \n");
        SQL.append(" TFSH.UPDATED_USER,      \n");
        SQL.append(" TFSH.ASSIGN_USER_ID,    \n");
        SQL.append(" TFSH.ASSIGN_DEPARTMENT_CODE,    \n");
        SQL.append(" TFSH.IS_PUBLIC,       \n");
        SQL.append(" TFSH.IS_USERS_ONLY,      \n");
        SQL.append(" TFSH.IS_DIVISION_ONLY,      \n");
        SQL.append(" TFSH.UPDATED_DATE    \n");
        SQL.append(" FROM T_FP_STATUS_HISTORY TFSH    \n");
        SQL.append(" INNER JOIN T_FACILITY_PAPER TFP ON TFSH .FACILITY_PAPER_ID = TFP.FACILITY_PAPER_ID    \n");
        SQL.append(" WHERE TFSH.FP_STATUS_HISTORY_ID IS NOT NULL             \n");
        SQL.append(" AND TFP.FACILITY_PAPER_ID = :facilityPaperID           \n");
        SQL.append(" UNION  ALL  \n");
        SQL.append(" SELECT   \n");
        SQL.append(" TO_DATE(TFSH.CREATED_DATE ,'dd/mm/yyyy hh24:mi:ss') UPDATED_DATE_STR, \n");
        SQL.append(" TFP.FACILITY_PAPER_ID,  \n");
        SQL.append(" TFP.FP_REF_NUMBER,    \n");
        SQL.append(" TFP.CREATED_DATE,     \n");
        SQL.append(" TFSH.CREATED_USER_DISPLAY_NAME,      \n");
        SQL.append(" TFP.CURRENT_FACILITY_PAPER_STATUS,   \n");
        SQL.append(" TFP.ASSIGN_USER_DISPLAY_NAME,   \n");
        SQL.append(" DECODE(TFSH.COMMITTEE_PAPER_STATUS,'RECOMMENDED','APPROVED',TFSH.COMMITTEE_PAPER_STATUS),    \n");
        SQL.append(" TFSH.ACTION_MESSAGE,    \n");
        SQL.append(" TFSH.CREATED_BY,  \n");
        SQL.append(" NULL,TFP.ASSIGN_DEPARTMENT_CODE,'Y','N','N',  \n");
        SQL.append(" TFSH.CREATED_DATE UPDATED_DATE  \n");
        SQL.append(" FROM CA_COMMITTEE_STATUS_HISTORY TFSH, CA_COMMITTEE_PAPER CCP,  T_FACILITY_PAPER TFP \n");
        SQL.append(" WHERE TFSH.FACILITY_PAPER_ID = TFP.FACILITY_PAPER_ID \n");
        SQL.append(" AND TFSH.COMMITTEE_PAPER_ID = CCP.COMMITTEE_PAPER_ID \n");
        // SQL.append(" AND TFSH.PATH_TYPE = CCP.CURRENT_PATH \n");
        // SQL.append(" AND CCP.STATUS = 'ACT'  \n");
        SQL.append(" AND TFSH.COMMITTEE_STATUS_HISTORY_ID IS NOT NULL       \n");
        SQL.append(" AND TFP.FACILITY_PAPER_ID = :facilityPaperID       \n");
        SQL.append(" AND TFSH.COMMITTEE_PAPER_STATUS IN ('RECOMMENDED', 'COMMENTED')   \n");
        SQL.append(" UNION  ALL  \n");
        SQL.append(" SELECT \n");
        SQL.append(" DISTINCT TO_DATE(BCCH.APPROVED_DATE,'dd/mm/yyyy hh24:mi:ss') UPDATED_DATE_STR, \n");
        SQL.append(" TFP.FACILITY_PAPER_ID, \n");
        SQL.append(" TFP.FP_REF_NUMBER, \n");
        SQL.append(" TFP.CREATED_DATE, \n");
        SQL.append(" BCCH.APPROVED_USER_DISPLAY_NAME CREATED_USER_DISPLAY_NAME, \n");
        SQL.append(" TFP.CURRENT_FACILITY_PAPER_STATUS, \n");
        SQL.append(" TFP.ASSIGN_USER_DISPLAY_NAME, \n");
        // SQL.append(" DECODE(BCCH.APPROVE_STATUS,'PENDING','SUBMITTED',BCCH.APPROVE_STATUS), \n");
        // SQL.append(" DECODE(BCCH.APPROVE_STATUS,'PENDING','SUBMITTED','APPROVED',BCCH.BCC_STATUS,BCCH.APPROVE_STATUS), \n");
        SQL.append(" DECODE(BCCH.APPROVE_STATUS,'PENDING','SUBMITTED','APPROVED','APPROVED',BCCH.APPROVE_STATUS), \n");
        SQL.append(" BCCH.ACTION_MESSAGE, \n");
        SQL.append(" BCCH.APPROVED_BY UPDATE_BY, \n");
        SQL.append(" NULL,TFP.ASSIGN_DEPARTMENT_CODE,'N','N','Y', \n");
        SQL.append(" BCCH.APPROVED_DATE UPDATED_DATE \n");
        SQL.append(" FROM  T_FP_BCC_AUD BCCH,  T_FACILITY_PAPER TFP \n");
        SQL.append(" WHERE BCCH.FACILITY_PAPER_ID = TFP.FACILITY_PAPER_ID \n");
        SQL.append(" AND TFP.FACILITY_PAPER_ID =  :facilityPaperID \n");
        SQL.append(" AND BCCH.APPROVE_STATUS NOT IN ('DRAFT') \n");
        /*SQL.append(" UNION  ALL         \n");
        SQL.append(" SELECT             \n");
        SQL.append(" TO_DATE(CREATED_DATE,'dd/mm/yyyy hh24:mi:ss') UPDATED_DATE_STR, \n");
        SQL.append(" :facilityPaperID FACILITY_PAPER_ID,    \n");
        SQL.append(" NULL,                                  \n");
        SQL.append(" CREATED_DATE,                          \n");
        SQL.append(" CREATED_USER_DISPLAY_NAME,             \n");
        SQL.append(" NULL,                                  \n");
        SQL.append(" NULL,                                  \n");
        SQL.append(" COMMITTEE_PAPER_STATUS,                \n");
        SQL.append(" ACTION_MESSAGE,                        \n");
        SQL.append(" CREATED_BY,                            \n");
        SQL.append(" NULL,NULL,'Y','N','N',                 \n");
        SQL.append(" CREATED_DATE UPDATED_DATE              \n");
        SQL.append(" FROM CA_COMMITTEE_STATUS_HISTORY                            \n");
        SQL.append(" WHERE FACILITY_PAPER_ID = :facilityPaperID                  \n");
        SQL.append(" AND COMMITTEE_PAPER_STATUS NOT IN ( 'FORWARDED','APPROVED') \n");*/

        // System.out.println(facilityPaper.getAssignDepartmentCode());
        //System.out.println(facilityPaper.getCurrentFacilityPaperStatus());

        if (facilityPaper.getAssignDepartmentCode() != null && facilityPaper.getAssignDepartmentCode().equals("CA")) {
            if (facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.APPROVED ||
                    facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.REJECTED) {

                String assignUserDisplayName = facilityPaper.getAssignUserDisplayName().toUpperCase();

                if (facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.APPROVED) {
                    params.put("actionMessage", "Approved by " + assignUserDisplayName);
                    Date approvedDate = facilityPaper.getApprovedDate();
                    approvedDate = new Date(approvedDate.getTime() + 1000);
                    params.put("updatedDate", approvedDate);
                    params.put("currentFacilityPaperStatus", DomainConstants.FacilityPaperStatus.APPROVED.toString());
                }
                if (facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.REJECTED) {
                    params.put("actionMessage", "Declined by " + assignUserDisplayName);

                    Date declinedDate = facilityPaper.getRejectedDate();
                    declinedDate = new Date(declinedDate.getTime() + 1000);
                    params.put("updatedDate", declinedDate);
                    params.put("currentFacilityPaperStatus", DomainConstants.FacilityPaperStatus.REJECTED.toString());
                }

                params.put("assignUserDisplayName", assignUserDisplayName);

                SQL.append(" UNION  ALL  \n");
                SQL.append(" SELECT \n");
                SQL.append(" NULL UPDATED_DATE_STR, \n");
                SQL.append(" :facilityPaperID FACILITY_PAPER_ID, \n");
                SQL.append(" '' FP_REF_NUMBER, \n");
                SQL.append(" NULL CREATED_DATE, \n");
                SQL.append(" :assignUserDisplayName , \n");
                SQL.append(" :currentFacilityPaperStatus CURRENT_FACILITY_PAPER_STATUS, \n");
                SQL.append(" :assignUserDisplayName ASSIGN_USER_DISPLAY_NAME, \n");
                SQL.append(" :currentFacilityPaperStatus CURRENT_FACILITY_PAPER_STATUS, \n");
                SQL.append(" :actionMessage ACTION_MESSAGE, \n");
                SQL.append(" :assignUserDisplayName , \n");
                SQL.append("  NULL,'CA' ASSIGN_DEPARTMENT_CODE,'Y','N','N', \n");
                SQL.append(" :updatedDate UPDATED_DATE \n");
                SQL.append(" FROM  DUAL \n");
            }
        }

        SQL.append(" )ORDER BY UPDATED_DATE ASC \n");

//        System.out.println(SQL.toString());


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<LeadFacilityPaperStatusDetailDTO>>() {

            @Override
            public List<LeadFacilityPaperStatusDetailDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    LeadFacilityPaperStatusDetailDTO remarkDTO = new LeadFacilityPaperStatusDetailDTO();
                    remarkDTO.setUpdateBy(rs.getString("UPDATE_BY"));
                    remarkDTO.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("FACILITY_PAPER_STATUS")));
                    remarkDTO.setActionMessage(rs.getString("ACTION_MESSAGE"));
                    remarkDTO.setAssignUserID(rs.getInt("ASSIGN_USER_ID"));
                    remarkDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                    remarkDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                    remarkDTO.setUpdatedUser(rs.getString("UPDATED_USER"));
                    remarkDTO.setIsPublic(AppsConstants.YesNo.resolver(rs.getString("IS_PUBLIC")));
                    remarkDTO.setIsUsersOnly(AppsConstants.YesNo.resolver(rs.getString("IS_USERS_ONLY")));
                    remarkDTO.setIsDivisionOnly(AppsConstants.YesNo.resolver(rs.getString("IS_DIVISION_ONLY")));
                    if (rs.getTimestamp("UPDATED_DATE") != null) {
                        remarkDTO.setUpdateDate(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("UPDATED_DATE")));
                    }
                    facilityPaperStatusDetailRS.add(remarkDTO);
                }
                return facilityPaperStatusDetailRS;
            }
        });
    }

    public Page<FPPreviousUPCTemplateRS> getPagedFacilityPaperHistoryWithUPCTemplateDetails(FPPreviousUPCTemplateRQ fpPreviousUPCTemplateRQ) {

        Map<String, Object> params = new HashMap<>();
        params.put("customerID", fpPreviousUPCTemplateRQ.getCustomerID());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                      \n");
        SQL.append("  tfp.FACILITY_PAPER_ID,                                                    \n");
        SQL.append("  tfp.CURRENT_FACILITY_PAPER_STATUS,                                        \n");
        SQL.append("  (CASE tfp.CURRENT_FACILITY_PAPER_STATUS                                   \n");
        SQL.append("   WHEN 'DRAFT'                                                             \n");
        SQL.append("     THEN tfp.CREATED_DATE                                                  \n");
        SQL.append("   WHEN 'IN_PROGRESS'                                                       \n");
        SQL.append("     THEN tfp.IN_PROGRESS_DATE                                              \n");
        SQL.append("   WHEN 'CANCEL'                                                            \n");
        SQL.append("     THEN tfp.CANCELED_DATE                                                 \n");
        SQL.append("   WHEN 'APPROVED'                                                          \n");
        SQL.append("     THEN tfp.APPROVED_DATE                                                 \n");
        SQL.append("   WHEN 'REJECTED'                                                          \n");
        SQL.append("     THEN tfp.REJECTED_DATE                                                 \n");
        SQL.append("   ELSE tfp.CREATED_DATE                                                    \n");
        SQL.append("   END)                                                                     \n");
        SQL.append("    AS LAST_ACTION_DATE,                                                    \n");
        SQL.append("    tfp.FP_REF_NUMBER,                                                                      \n");
        SQL.append("    tfp.ASSIGN_USER_DISPLAY_NAME,                                                           \n");
        SQL.append("    tfp.BRANCH_CODE,                                                                        \n");
        SQL.append("    tfp.ASSIGN_DEPARTMENT_CODE,                                                             \n");
        SQL.append("    tut.TEMPLATE_NAME,                                                                      \n");
        SQL.append("    tfp.UPC_TEMPLATE_ID,                                                                    \n");
        SQL.append("    tfp.BANK_ACCOUNT_ID                                                                     \n");
        SQL.append(" FROM T_FACILITY_PAPER tfp                                                                  \n");
        SQL.append("   INNER JOIN T_CAS_CUSTOMER tcc ON tfp.FACILITY_PAPER_ID = tcc.FACILITY_PAPER_ID  AND tcc.STATUS = 'ACT'    \n");
        SQL.append("   INNER JOIN T_CUSTOMER tc ON tcc.CUSTOMER_ID = tc.CUSTOMER_ID   AND tc.STATUS = 'ACT'                      \n");
        SQL.append("   INNER JOIN T_UPC_TEMPLATE tut ON tut.UPC_TEMPLATE_ID = tfp.UPC_TEMPLATE_ID   AND tut.STATUS = 'ACT'       \n");
        SQL.append(" WHERE tfp.FACILITY_PAPER_ID IS NOT NULL                                                                     \n");
        SQL.append("       AND tc.CUSTOMER_ID =:customerID                                                      \n");

        if (StringUtils.isNotEmpty(fpPreviousUPCTemplateRQ.getFpRefNumber())) {
            SQL.append("AND upper(tfp.FP_REF_NUMBER) LIKE '%" + fpPreviousUPCTemplateRQ.getFpRefNumber().toUpperCase() + "%'                \n");
        }

        if (StringUtils.isNotEmpty(fpPreviousUPCTemplateRQ.getTemplateName())) {
            SQL.append("AND upper(tut.TEMPLATE_NAME) LIKE '%" + fpPreviousUPCTemplateRQ.getTemplateName().toUpperCase() + "%'                \n");
        }

        if (StringUtils.isNotEmpty(fpPreviousUPCTemplateRQ.getAssignUserDisplayName())) {
            SQL.append("AND upper(tfp.ASSIGN_USER_DISPLAY_NAME) LIKE '%" + fpPreviousUPCTemplateRQ.getAssignUserDisplayName().toUpperCase() + "%'        \n");
        }

        if (StringUtils.isNotEmpty(fpPreviousUPCTemplateRQ.getCreatedBranchCode())) {
            SQL.append("AND upper(tfp.BRANCH_CODE) LIKE '%" + fpPreviousUPCTemplateRQ.getCreatedBranchCode().toUpperCase() + "%'                   \n");
        }

        if (fpPreviousUPCTemplateRQ.getFacilityPaperStatus() != null) {
            SQL.append("AND tfp.CURRENT_FACILITY_PAPER_STATUS =:fpStatus                                        \n");
            params.put("fpStatus", fpPreviousUPCTemplateRQ.getFacilityPaperStatus().toString());
        }

        SQL.append("   ORDER BY LAST_ACTION_DATE DESC                                                           \n");

        return getPagedData(SQL.toString(), params, new RowMapper<FPPreviousUPCTemplateRS>() {

            @Nullable
            @Override
            public FPPreviousUPCTemplateRS mapRow(ResultSet rs, int i) throws SQLException {

                FPPreviousUPCTemplateRS fpPreviousUPCTemplateRS = new FPPreviousUPCTemplateRS();
                fpPreviousUPCTemplateRS.setCopyFromFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                fpPreviousUPCTemplateRS.setBankAccountNumber(rs.getString("BANK_ACCOUNT_ID"));
                fpPreviousUPCTemplateRS.setCreatedBranchCode(rs.getString("BRANCH_CODE"));
                fpPreviousUPCTemplateRS.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                fpPreviousUPCTemplateRS.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                fpPreviousUPCTemplateRS.setTemplateName(rs.getString("TEMPLATE_NAME"));
                fpPreviousUPCTemplateRS.setUpcTemplateID(rs.getInt("UPC_TEMPLATE_ID"));
                fpPreviousUPCTemplateRS.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                fpPreviousUPCTemplateRS.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                fpPreviousUPCTemplateRS.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_ACTION_DATE")));
                return fpPreviousUPCTemplateRS;
            }
        }, fpPreviousUPCTemplateRQ);
    }


    public Page<FPReviewerCommentDTO> getPagedReviewComments(FPReviewerCommentSearchRQ searchRQ) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                            \n");
        SQL.append("  fp.T_FP_REVIEWER_COMMENT_ID,    \n");
        SQL.append("  fp.FACILITY_PAPER_ID,            \n");
        SQL.append("  fp.STATUS,                      \n");
        SQL.append("  fp.CREATED_USER_DISPLAY_NAME,   \n");
        SQL.append("  fp.CREATED_DATE,                \n");
        SQL.append("  fp.PAPER_REVIEW_STATUS,         \n");
        SQL.append("  fp.FP_REVIEWER_COMMENT,         \n");
        SQL.append("  fp.UPM_ID,                      \n");
        SQL.append("  fp.CREATED_USER_DIV_CODE,       \n");
        SQL.append("  fp.CREATED_USER_UPM_CODE       \n");
        SQL.append("FROM T_FP_REVIEWER_COMMENT fp     \n");
        SQL.append("WHERE fp.T_FP_REVIEWER_COMMENT_ID IS NOT NULL  \n");

        if (searchRQ.getFacilityPaperID() != null) {
            params.put("fpID", searchRQ.getFacilityPaperID());
            SQL.append("AND fp.FACILITY_PAPER_ID = :fpID        \n");
        }

        if (!searchRQ.getPaperReviewStatusList().isEmpty()) {
            params.put("paperReviewStatusList", searchRQ.getPaperReviewStatusList());
            SQL.append("AND fp.PAPER_REVIEW_STATUS IN (:paperReviewStatusList)      \n");
        }

        if (searchRQ.getUpmID() != null) {
            params.put("upmID", searchRQ.getUpmID());
            SQL.append("AND FP.UPM_ID = :upmID \n");
        }

        SQL.append(" ORDER BY fp.CREATED_DATE DESC  \n");

        return getPagedData(SQL.toString(), params, new RowMapper<FPReviewerCommentDTO>() {

            @Nullable
            @Override
            public FPReviewerCommentDTO mapRow(ResultSet rs, int i) throws SQLException {

                FPReviewerCommentDTO fpReviewerCommentDTO = new FPReviewerCommentDTO();
                fpReviewerCommentDTO.setFpReviewerCommentID(rs.getInt("T_FP_REVIEWER_COMMENT_ID"));
                fpReviewerCommentDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                fpReviewerCommentDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                fpReviewerCommentDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                fpReviewerCommentDTO.setCommentedTimeStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                fpReviewerCommentDTO.setPaperReviewStatus(DomainConstants.PaperReviewStatus.resolve(rs.getString("PAPER_REVIEW_STATUS").replaceAll("\\s", "")));
                fpReviewerCommentDTO.setComment(rs.getString("FP_REVIEWER_COMMENT"));
                fpReviewerCommentDTO.setUpmID(rs.getInt("UPM_ID"));
                fpReviewerCommentDTO.setCreatedUserDivCode(rs.getString("CREATED_USER_DIV_CODE"));
                fpReviewerCommentDTO.setCreatedUserUpmCode(rs.getString("CREATED_USER_UPM_CODE"));

                return fpReviewerCommentDTO;
            }
        }, searchRQ);

    }

//    //    This is only for validating the clearing of duplicated finacle customers
//    public List<FacilityPaperDTO> getFacilityPapersForInactiveCustomer(Set<Integer> inactiveCustomerIds) throws AppsException {
//
//        List<FacilityPaperDTO> facilityPaperDTOS = new ArrayList<>();
//        Map<String, Object> params = new HashMap<>();
//        StringBuilder SQL = new StringBuilder();
//
//        SQL.append(" SELECT                                                                                \n");
//        SQL.append("   tc.CUSTOMER_FINANCIAL_ID,                                                           \n");
//        SQL.append("   tc.CUSTOMER_ID,                                                                     \n");
//        SQL.append("   tfpc.CAS_CUSTOMER_ID,                                                                \n");
//        SQL.append("   tfp.FP_REF_NUMBER,                                                                  \n");
//        SQL.append("   tfp.CURRENT_FACILITY_PAPER_STATUS,                                                  \n");
//        SQL.append("   tfp.CREATED_DATE                                                                    \n");
//        SQL.append(" FROM T_FACILITY_PAPER tfp                                                             \n");
//        SQL.append("   INNER JOIN T_CAS_CUSTOMER tfpc ON tfp.FACILITY_PAPER_ID = tfpc.FACILITY_PAPER_ID     \n");
//        SQL.append("   INNER JOIN T_CUSTOMER tc ON tc.CUSTOMER_ID = tfpc.CUSTOMER_ID                       \n");
//
//        if (inactiveCustomerIds != null && !inactiveCustomerIds.isEmpty()) {
//            SQL.append("WHERE tfpc.CUSTOMER_ID IN (" + QueryInBuilder.buildSQLINQuery(inactiveCustomerIds) + ")  \n");
//        }
//
//        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FacilityPaperDTO>>() {
//
//            @Override
//            public List<FacilityPaperDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
//
//                while (rs.next()) {
//                    FacilityPaperDTO facilityPaper = new FacilityPaperDTO();
//                    LOG.warn("WARNING : Facility Paper : {} remaining with customer ID : {} with Finacle ID : {} ==> FP Customer Id : {} need to restore", rs.getString("FP_REF_NUMBER"), rs.getInt("CUSTOMER_ID"), rs.getString("CUSTOMER_FINANCIAL_ID"), rs.getInt("CAS_CUSTOMER_ID"));
//                    facilityPaper.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
//                    facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("FACILITY_PAPER_STATUS")));
//                    if (rs.getTimestamp("CREATED_DATE") != null) {
//                        facilityPaper.setLastActionDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
//                    }
//                    facilityPaperDTOS.add(facilityPaper);
//                }
//                return facilityPaperDTOS;
//            }
//        });
//    }

    public Page<FacilityPaperDTO> getSearchedFacilityPaperDTOForSearch(SearchFacilityPaperRQ searchFacilityPaperSearchRQ) throws AppsException {

        Map<String, Object> params = new HashMap<>();

        params.put("branchCode", searchFacilityPaperSearchRQ.getBranchCode());

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
        SQL.append("  fp.IS_COMMITTEE, \n");
        SQL.append("  fp.ROA_PERCENTAGE_EXISTING, \n");
        SQL.append("  fp.ROA_PERCENTAGE_PROPOSED, \n");
        SQL.append("  fp.LEAD_REF_NUMBER, \n");
        SQL.append("  fp.FP_REF_NUMBER, \n");
        SQL.append("  cus.CUSTOMER_NAME,  \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME,  \n");
        SQL.append("  upc.TEMPLATE_NAME,                                                                      \n");
        SQL.append("  upc.UPC_LABEL,                                                                          \n");
        SQL.append("  upc.UPC_LABEL_BACKGROUND_COLOR,                                                         \n");
        SQL.append("  upc.UPC_LABEL_FONT_COLOR,                                                                \n");
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
        SQL.append(" LEFT JOIN T_UPC_TEMPLATE upc ON  fp.UPC_TEMPLATE_ID = upc.UPC_TEMPLATE_ID               \n");
        SQL.append(" LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER \n");
        SQL.append(" WHERE fp.FACILITY_PAPER_ID IS NOT NULL \n");
        SQL.append(" AND cc.IS_PRIMARY = 'Y'\n");

        if (searchFacilityPaperSearchRQ.getFacilityPaperStatus() != null) {
            SQL.append("AND fp.CURRENT_FACILITY_PAPER_STATUS =:fpStatus \n");
            params.put("fpStatus", searchFacilityPaperSearchRQ.getFacilityPaperStatus().toString());
        } else {
            SQL.append(" AND NOT fp.CURRENT_FACILITY_PAPER_STATUS = 'REMOVED' \n");
        }

        if (searchFacilityPaperSearchRQ.getIsCommittee() != null) {
            SQL.append(" AND fp.IS_COMMITTEE =:isCommittee\n");
            params.put("isCommittee", searchFacilityPaperSearchRQ.getIsCommittee().toString());
        }

        if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getFpRefNumber())) {
            SQL.append("AND upper(fp.FP_REF_NUMBER) LIKE '%" + searchFacilityPaperSearchRQ.getFpRefNumber().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getIntiatedUserName())) {
            SQL.append("AND upper(fp.CREATED_BY) LIKE '%" + searchFacilityPaperSearchRQ.getIntiatedUserName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getCurrentAssignUser())) {
            SQL.append("AND upper(fp.CURRENT_ASSIGN_USER) LIKE '%" + searchFacilityPaperSearchRQ.getCurrentAssignUser().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getAssignUserDisplayName())) {
            SQL.append("AND upper(fp.ASSIGN_USER_DISPLAY_NAME) LIKE '%" + searchFacilityPaperSearchRQ.getAssignUserDisplayName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getBankAccountID())) {
            SQL.append("AND upper(fp.BANK_ACCOUNT_ID) LIKE '%" + searchFacilityPaperSearchRQ.getBankAccountID().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getCustomerName())) {
            SQL.append("AND upper(cus.CUSTOMER_NAME) LIKE '%" + searchFacilityPaperSearchRQ.getCustomerName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getLeadRefNumber())) {
            SQL.append("AND upper(lead.LEAD_REF_NUMBER) LIKE '%" + searchFacilityPaperSearchRQ.getLeadRefNumber().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotBlank(searchFacilityPaperSearchRQ.getBranchCode())) {
            SQL.append("       AND fp.BRANCH_CODE = :branchCode \n");
            params.put("branchCode", searchFacilityPaperSearchRQ.getBranchCode());
        }

        if (searchFacilityPaperSearchRQ.getIsCommittee() != null
                && searchFacilityPaperSearchRQ.getIsCommittee().equals(AppsConstants.YesNo.Y)
                && StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getCommitteeType())) {
            SQL.append("AND UPPER(fp.CURRENT_ASSIGN_USER) = :currentAssignUser \n");
            params.put("currentAssignUser", searchFacilityPaperSearchRQ.getCommitteeType());
        }

        if (searchFacilityPaperSearchRQ.getFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.APPROVED) {

            if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getCreatedFromDateStr())) {
                SQL.append("        AND fp.APPROVED_DATE >= :approvedFromDateStr \n");
                params.put("approvedFromDateStr", CalendarUtil.getParsedStartDateTime(searchFacilityPaperSearchRQ.getCreatedFromDateStr()));
            }

            if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getCreatedToDateStr())) {
                SQL.append("        AND fp.APPROVED_DATE <= :approvedToDateStr \n");
                params.put("approvedToDateStr", CalendarUtil.getParsedEndDateTime(searchFacilityPaperSearchRQ.getCreatedToDateStr()));
            }
            SQL.append(" ORDER BY fp.APPROVED_DATE DESC\n");
        } else {

            if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getCreatedFromDateStr())) {
                SQL.append("        AND fp.CREATED_DATE >= :createdFromDateStr \n");
                params.put("createdFromDateStr", CalendarUtil.getParsedStartDateTime(searchFacilityPaperSearchRQ.getCreatedFromDateStr()));

            }
            if (StringUtils.isNotEmpty(searchFacilityPaperSearchRQ.getCreatedToDateStr())) {
                SQL.append("        AND fp.CREATED_DATE <= :createdToDateStr \n");
                params.put("createdToDateStr", CalendarUtil.getParsedEndDateTime(searchFacilityPaperSearchRQ.getCreatedToDateStr()));
            }

            SQL.append(" ORDER BY fp.CREATED_DATE DESC\n");
        }

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setUpcTemplateID(rs.getInt("UPC_TEMPLATE_ID"));
                facilityPaperDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                if (StringUtils.isNotEmpty(rs.getString("CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                } else if (StringUtils.isNotEmpty(rs.getString("CAS_CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CAS_CUSTOMER_NAME"));
                }

                facilityPaperDTO.setUpcTemplateName(rs.getString("TEMPLATE_NAME"));
                facilityPaperDTO.setUpcLabel(rs.getString("UPC_LABEL"));
                facilityPaperDTO.setUpcLabelBackgroundColor(rs.getString("UPC_LABEL_BACKGROUND_COLOR"));
                facilityPaperDTO.setUpcLabelFontColor(rs.getString("UPC_LABEL_FONT_COLOR"));

                facilityPaperDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                facilityPaperDTO.setBankAccountID(rs.getString("BANK_ACCOUNT_ID"));
                facilityPaperDTO.setFpApprovingAuthorityLevel(rs.getString("FP_APPROVING_AUTHORITY_LEVEL"));
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                facilityPaperDTO.setCurrentAuthorityLevel(rs.getString("CURRENT_AUTHORITY_LEVEL"));
                facilityPaperDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                facilityPaperDTO.setAssignDepartmentCode(rs.getString("ASSIGN_DEPARTMENT_CODE"));
                facilityPaperDTO.setModifiedBy(rs.getString("MODIFIED_BY"));
                facilityPaperDTO.setIsCooperate(AppsConstants.YesNo.valueOf(rs.getString("IS_COOPERATE")));
                if (rs.getString("IS_COMMITTEE") != null){
                    facilityPaperDTO.setIsCommittee(AppsConstants.YesNo.valueOf(rs.getString("IS_COMMITTEE")));
                }else {
                    facilityPaperDTO.setIsCommittee(AppsConstants.YesNo.N);
                }

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

        }, searchFacilityPaperSearchRQ);
    }




    /*public List<FPCreditRiskCommentHistoryDTO> getPastRiskOpinion(Integer facilityPaperId) throws AppsException{

        List<FPCreditRiskCommentHistoryDTO> fpCreditRiskCommentHistoryDTOList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperId", facilityPaperId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                            \n");
        SQL.append("  fp.FP_CREDIT_RISK_COMMENT_ID,    \n");
        SQL.append("  fp.FACILITY_PAPER_ID,            \n");
        SQL.append("  fp.STATUS,                      \n");
        SQL.append("  fp.CREATED_USER_NAME,   \n");
        SQL.append("  fp.MODIFIED_USER_NAME,   \n");
        SQL.append("  fp.CREATED_DATE,                \n");
        SQL.append("  fp.MODIFIED_DATE,                \n");
//        SQL.append("  fp.FACILITY_PAPER_FORM_STATUS,         \n");
        SQL.append("  fp.FP_CR_COMMENT,               \n");
        SQL.append("  fp.UPM_ID,                      \n");
        SQL.append("  fp.UPM_PRIVILEGE_CODE       \n");
        SQL.append("FROM T_FP_CREDIT_RISK_COMMENT_AUD fp     \n");
        SQL.append("WHERE fp.FACILITY_PAPER_ID = :facilityPaperId  \n");



        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPCreditRiskCommentHistoryDTO>>()  {
            @Override
            public List<FPCreditRiskCommentHistoryDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()){
                    LOG.info("rs.getTimestamp(\"MODIFIED_DATE\") ++++++++++ : {}", rs.getTimestamp("MODIFIED_DATE"));
                    FPCreditRiskCommentHistoryDTO fpCreditRiskCommentHistoryDTO = new FPCreditRiskCommentHistoryDTO();
                    fpCreditRiskCommentHistoryDTO.setFpCreditRiskCommentID(rs.getInt("FP_CREDIT_RISK_COMMENT_ID"));
                    fpCreditRiskCommentHistoryDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                    fpCreditRiskCommentHistoryDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                    fpCreditRiskCommentHistoryDTO.setCreatedUserName(rs.getString("CREATED_USER_NAME"));
                    fpCreditRiskCommentHistoryDTO.setModifiedUserName(rs.getString("MODIFIED_USER_NAME"));
                    fpCreditRiskCommentHistoryDTO.setCommentedTimeStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                    fpCreditRiskCommentHistoryDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("MODIFIED_DATE")));
//                    fpCreditRiskCommentHistoryDTO.setFacilityPaperID(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("FACILITY_PAPER_FORM_STATUS").replaceAll("\\s", "")).ordinal());
                    fpCreditRiskCommentHistoryDTO.setCreditRiskComment(rs.getString("FP_CR_COMMENT"));
                    fpCreditRiskCommentHistoryDTO.setUpmID(rs.getInt("UPM_ID"));
                    fpCreditRiskCommentHistoryDTO.setUPMPrivilegeCode(rs.getString("UPM_PRIVILEGE_CODE"));

                    fpCreditRiskCommentHistoryDTOList.add(fpCreditRiskCommentHistoryDTO);
                }

                return fpCreditRiskCommentHistoryDTOList;
            }
        }) ;
    }*/


    public List<FPCreditRiskCommentHistoryDTO> getPastRiskOpinion(Integer facilityPaperId) throws AppsException {

        List<FPCreditRiskCommentHistoryDTO> fpCreditRiskCommentHistoryDTOList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperId", facilityPaperId);
        QueryBuilder SQL = new QueryBuilder();

        SQL.append(" SELECT * \n");
        SQL.appendNotNullMandatory(" FROM TABLE(GET_RISK_COMMENT_FUNC(:facilityPaperId))\n", facilityPaperId);

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPCreditRiskCommentHistoryDTO>>() {
            @Override
            public List<FPCreditRiskCommentHistoryDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    FPCreditRiskCommentHistoryDTO fpCreditRiskCommentHistoryDTO = new FPCreditRiskCommentHistoryDTO();
                    fpCreditRiskCommentHistoryDTO.setRev(rs.getInt("REV"));
                    fpCreditRiskCommentHistoryDTO.setCreatedUserName(rs.getString("CREATED_USER_NAME"));
                    fpCreditRiskCommentHistoryDTO.setModifiedUserName(rs.getString("MODIFIED_USER_NAME"));
                    if (rs.getTimestamp("MOD_ON") != null) {
                        fpCreditRiskCommentHistoryDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("MOD_ON")));
                    }
                    fpCreditRiskCommentHistoryDTO.setCreditRiskComment(rs.getString("CMT"));
                    fpCreditRiskCommentHistoryDTO.setModifiedBy(rs.getString("MOD_BY"));
                    fpCreditRiskCommentHistoryDTO.setUpmID(rs.getInt("UMP_ID"));

                    fpCreditRiskCommentHistoryDTOList.add(fpCreditRiskCommentHistoryDTO);
                }

                return fpCreditRiskCommentHistoryDTOList;
            }
        });
    }

    public List<FPReviewCIFDetailsDTO> getCIDDetails(Integer facilityPaperId) throws AppsException {

        List<FPReviewCIFDetailsDTO> fpReviewCIFDetailsDTOList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperId", facilityPaperId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                              \n");
        SQL.append("c.CUSTOMER_FINANCIAL_ID,              \n");
        SQL.append("c.CUSTOMER_ID                   \n");
        SQL.append("FROM T_CAS_CUSTOMER t                                                                                   \n");
        SQL.append("INNER JOIN T_CUSTOMER c ON t.CUSTOMER_ID = c.CUSTOMER_ID                              \n");
        SQL.append(" WHERE t.FACILITY_PAPER_ID IS NOT NULL                                                                     \n");
        SQL.append(" AND t.IS_PRIMARY='Y'                                                                     \n");
        SQL.append(" AND t.FACILITY_PAPER_ID =:facilityPaperId                                                      \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<FPReviewCIFDetailsDTO>() {
            @Override
            public FPReviewCIFDetailsDTO mapRow(ResultSet rs, int rownumber) throws SQLException {
                FPReviewCIFDetailsDTO fpReviewCIFDetailsDTO = new FPReviewCIFDetailsDTO();
                fpReviewCIFDetailsDTO.setCustomerId(rs.getInt("CUSTOMER_ID"));
                fpReviewCIFDetailsDTO.setCustomerFinancialId(rs.getInt("CUSTOMER_FINANCIAL_ID"));
                return fpReviewCIFDetailsDTO;
            }
        });
    }

    public List<FacilityCovenantDetailsDTO> getFacilityCovenantsByFacilityId(Integer facilityID) throws AppsException {
        List<FacilityCovenantDetailsDTO> applicationCovenantDetailsDTOS = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityID", facilityID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append("a.COVENANT_CODE, \n");
        SQL.append("a.COVENANT_DESCRIPTION, \n");
        SQL.append("a.COVENANT_FREQUENCY, \n");
        SQL.append("a.COVENANT_DUE_DATE, \n");
        SQL.append("f.FACILITY_ID \n");
        SQL.append("FROM APPLICATION_LEVEL_COVENANT a, \n");
        SQL.append("T_FACILITY_COVENANT_FACILITIES f \n");
        SQL.append("where f.FACILITY_ID = :facilityID \n");
        SQL.append("and a.APPLICATION_COVENANT_ID = f.APPLICATION_COVENANT_ID \n");
        SQL.append("and a.STATUS='Active' \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<FacilityCovenantDetailsDTO>() {
            @Override
            public FacilityCovenantDetailsDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                FacilityCovenantDetailsDTO applicationCovenantDetailsDTO = new FacilityCovenantDetailsDTO();
                applicationCovenantDetailsDTO.setCovenant_Code(resultSet.getString("COVENANT_CODE"));
                applicationCovenantDetailsDTO.setCovenant_Description(resultSet.getString("COVENANT_DESCRIPTION"));
                applicationCovenantDetailsDTO.setCovenant_Frequency(resultSet.getString("COVENANT_FREQUENCY"));
                applicationCovenantDetailsDTO.setCovenant_Due_Date(resultSet.getDate("COVENANT_DUE_DATE"));
                return applicationCovenantDetailsDTO;

            }
        });
    }

    public CommitteePaperDashboardCountDTO getCommitteePaperDashboardCount(CommitteePaperDashboardRQ committeePaperDashboardRQ) {
        CommitteePaperDashboardCountDTO countDTO = new CommitteePaperDashboardCountDTO();
        Map<String, Object> params = new HashMap<>();

        params.put("userId", committeePaperDashboardRQ.getLoggedInUserId());
        params.put("userRole", committeePaperDashboardRQ.getLoggedInUserRole());
        // params.put("committeeTypeID", committeePaperDashboardRQ.getCommitteeTypeID());


        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT 'INBOX' STATUS, CASV2_DASHBOARD.GET_CA_INBOX_COUNT(:userId) AS COUNT FROM DUAL \n");
        SQL.append(" UNION ALL \n");
        SQL.append(" SELECT 'IN_PROGRESS' STATUS, CASV2_DASHBOARD.GET_CA_IN_PROGRESS_COUNT(:userId, :userRole) AS COUNT FROM DUAL \n");
        SQL.append(" UNION ALL \n");
        SQL.append(" SELECT 'RETURNED' STATUS, CASV2_DASHBOARD.GET_CA_RETURNED_COUNT(:userId) AS COUNT FROM DUAL \n");
        SQL.append(" UNION ALL \n");
        SQL.append(" SELECT 'COMMITTEE_APPROVED' STATUS, CASV2_DASHBOARD.GET_CA_COMMITTEE_APPROVED_COUNT(:userId, :userRole) AS COUNT FROM DUAL \n");
        SQL.append(" UNION ALL \n");
        SQL.append(" SELECT 'DECLINED' STATUS, CASV2_DASHBOARD.GET_CA_DECLINED_COUNT(:userId, :userRole) AS COUNT FROM DUAL \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<CommitteePaperDashboardCountDTO>() {
            @Override
            public CommitteePaperDashboardCountDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    switch (rs.getString("STATUS")) {
                        case "INBOX": {
                            countDTO.setInboxCommitteePaper(rs.getInt("COUNT"));
                            break;
                        }
                        case "IN_PROGRESS": {
                            countDTO.setInProgressCommitteePaper(rs.getInt("COUNT"));
                            break;
                        }
                        case "RETURNED": {
                            countDTO.setReturnedCommitteePaper(rs.getInt("COUNT"));
                            break;
                        }
                        case "COMMITTEE_APPROVED": {
                            countDTO.setApprovedCommitteePaper(rs.getInt("COUNT"));
                            break;
                        }
                        case "DECLINED": {
                            countDTO.setDeclinedCommitteePaper(rs.getInt("COUNT"));
                            break;
                        }
                    }
                }
                return countDTO;
            }
        });
    }

    public List<FPDocumentElementDTO> getFacilityDocumentElements() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                                     \n");
        SQL.append("   ELEMENT_ID,                              \n");
        SQL.append("   PARENT_ID,                               \n");
        SQL.append("   ELEMENT_NAME,                            \n");
        SQL.append("   ELEMENT_TYPE,                             \n");
        SQL.append("   CREDIT_FACILITY_TEMPLATE_ID,              \n");
        SQL.append("   CREDIT_FACILITY_NAME,                     \n");
        SQL.append("   FACILITY_TYPE_ID,                         \n");
        SQL.append("   IS_NEW,                                   \n");
        SQL.append("   DOCUMENT_CONTENT,                         \n");
        SQL.append("   KEY                                      \n");
        SQL.append(" FROM T_FACILITY_DOCUMENT_ELEMENT           \n");
        SQL.append(" WHERE STATUS = 'ACT' \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<FPDocumentElementDTO>() {

            @Nullable
            @Override
            public FPDocumentElementDTO mapRow(ResultSet rs, int i) throws SQLException {
                FPDocumentElementDTO fpDocumentElementDTO = new FPDocumentElementDTO();
                fpDocumentElementDTO.setElementID(rs.getInt("ELEMENT_ID"));
                fpDocumentElementDTO.setParentID(rs.getInt("PARENT_ID"));
                fpDocumentElementDTO.setElementName(rs.getString("ELEMENT_NAME"));
                fpDocumentElementDTO.setElementType(rs.getString("ELEMENT_TYPE"));
                fpDocumentElementDTO.setCreditFacilityTemplateID(rs.getInt("CREDIT_FACILITY_TEMPLATE_ID"));
                fpDocumentElementDTO.setCreditFacilityName(rs.getString("CREDIT_FACILITY_NAME"));
                fpDocumentElementDTO.setFacilityTypeID(rs.getInt("FACILITY_TYPE_ID"));
                fpDocumentElementDTO.setIsNew(rs.getString("IS_NEW"));
                fpDocumentElementDTO.setKey(rs.getString("KEY"));
                Clob documentContent = rs.getClob("DOCUMENT_CONTENT");
                fpDocumentElementDTO.setDocumentContent(documentContent != null ? documentContent.getSubString(1, (int) documentContent.length()) : "");
                return fpDocumentElementDTO;
            }
        });
    }

    public UpcSectionDTO getDocumentContent(Integer secId) throws AppsException {
        Map<String, Object> params = new HashMap<>();
        params.put("secId", secId);
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                              \n");
        SQL.append("UPC_SECTION_NAME              \n");
        SQL.append("FROM T_UPC_SECTION                                                                                   \n");
        SQL.append(" WHERE UPC_SECTION_ID =:secId                                                                   \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<UpcSectionDTO>() {

            @Override
            public UpcSectionDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                UpcSectionDTO dataRow = new UpcSectionDTO();
                if (rs.next()) {
                    dataRow.setUpcSectionName(rs.getString("UPC_SECTION_NAME"));
                }
                return dataRow;
            }
        });
    }

    public Page<CommitteePaperDashboardDTO> getPagedCommitteePaperDashboardDTO(CommitteePaperDashboardRQ committeePaperDashboardRQ) {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", committeePaperDashboardRQ.getLoggedInUserId());
        params.put("userRole", committeePaperDashboardRQ.getLoggedInUserRole());

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT DASHBOARD_STATUS, DASHBOARD_SUB_STATUS, CUSTOMER_NAME, FACILITY_PAPER_ID,  \n");
        SQL.append(" FP_REFERENCE_NUMBER, BRANCH_CODE, ACCOUNT_NUMBER, \n");
        SQL.append(" LAST_ACTION_DATE_STR, ASSIGNED_USER, FACILITY_PAPER_STATUS, ASSIGNED_DIV_CODE,  \n");
        SQL.append(" ASSIGNED_USER_LEVEL, COMMITTEE_ID, COMMITTEE_NAME, COMMITTEE_TYPE,  \n");
        SQL.append(" COMMITTEE_PAPER_STATUS, COMMITTEE_PAPER_ID, COMMITTEE_TYPE_ID, APPROVE_STATUS, DOCS_APPROVE_STATUS \n");

        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("FORWARDED")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_CA_FORWARDED_LIST(:userId)) \n");
        }
        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("RECOMMENDED")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_CA_IN_PROGRESS_LIST(:userId, :userRole)) \n");
        }
        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("RETURNED")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_CA_RETURNED_LIST(:userId)) \n");
        }
        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("COMMITTEE_APPROVED")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_CA_COMMITTEE_APPROVED_LIST(:userId, :userRole)) \n");
        }
        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("DECLINED")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_CA_DECLINED_LIST(:userId, :userRole)) \n");
        }


        return getPagedData(SQL.toString(), params, new RowMapper<CommitteePaperDashboardDTO>() {

            @Nullable
            @Override
            public CommitteePaperDashboardDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                CommitteePaperDashboardDTO docDTO = new CommitteePaperDashboardDTO();
                docDTO.setDashboardStatus(rs.getString("DASHBOARD_STATUS"));
                docDTO.setDashboardSubStatus(rs.getString("DASHBOARD_SUB_STATUS"));
                docDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                docDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                docDTO.setFpRefNumber(rs.getString("FP_REFERENCE_NUMBER"));
                docDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                docDTO.setBankAccountID(rs.getString("ACCOUNT_NUMBER"));
                docDTO.setLastActionDateStr(rs.getString("LAST_ACTION_DATE_STR"));
                docDTO.setAssignUserDisplayName(rs.getString("ASSIGNED_USER"));
                docDTO.setFacilityPaperStatus(rs.getString("FACILITY_PAPER_STATUS"));
                docDTO.setAssignDepartmentCode(rs.getString("ASSIGNED_DIV_CODE"));
                docDTO.setCommitteePaperStatus(rs.getString("COMMITTEE_PAPER_STATUS"));
                docDTO.setAssignUserLevel(rs.getInt("ASSIGNED_USER_LEVEL"));
                docDTO.setCommitteeID(rs.getInt("COMMITTEE_ID"));
                docDTO.setCommitteePaperID(rs.getInt("COMMITTEE_PAPER_ID"));
                docDTO.setCommitteeType(rs.getString("COMMITTEE_TYPE"));
                docDTO.setCommitteeName(rs.getString("COMMITTEE_NAME"));
                docDTO.setCommitteeTypeID(rs.getInt("COMMITTEE_TYPE_ID"));
                docDTO.setBccApproveStatus(rs.getString("APPROVE_STATUS"));
                docDTO.setBccDocsApproveStatus(rs.getString("DOCS_APPROVE_STATUS"));
                return docDTO;
            }
        }, committeePaperDashboardRQ);
    }

    public List<FPSecurityDocumentDTO> getSecurityDocumentHistory(Integer securityDocumentID) throws AppsException {

        List<FPSecurityDocumentDTO> FPSecurityDocumentDTOHistoryList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("securityDocumentID", securityDocumentID);
        QueryBuilder SQL = new QueryBuilder();

        SQL.append(" SELECT DOCUMENT_NAME, DOCUMENT_STATUS, SAVED_BY, SAVED_BY_DIV, SAVED_DATE, AUTH_BY, AUTH_BY_DIV, AUTH_DATE, PRINTED_BY, PRINTED_BY_DIV, \n");
        SQL.append(" PRINTED_DATE, RETURN_COMMENT, SAVED_BY_DISPLAY_NAME, AUTH_BY_DISPLAY_NAME, PRINTED_BY_DISPLAY_NAME \n");
        SQL.append(" FROM T_FACILITY_SECURITY_DOCUMENT_AUD \n");
        SQL.append(" WHERE SECURITY_DOCUMENT_ID = :securityDocumentID ORDER BY REV\n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPSecurityDocumentDTO>>() {
            @Override
            public List<FPSecurityDocumentDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    FPSecurityDocumentDTO fpSecurityDocumentHistoryDTO = new FPSecurityDocumentDTO();
                    fpSecurityDocumentHistoryDTO.setDocumentName(rs.getString("DOCUMENT_NAME"));
                    fpSecurityDocumentHistoryDTO.setDocumentStatus(rs.getString("DOCUMENT_STATUS"));
                    fpSecurityDocumentHistoryDTO.setSavedBy(rs.getString("SAVED_BY"));
                    fpSecurityDocumentHistoryDTO.setSavedByDiv(rs.getString("SAVED_BY_DIV"));
                    fpSecurityDocumentHistoryDTO.setAuthBy(rs.getString("AUTH_BY"));
                    fpSecurityDocumentHistoryDTO.setAuthByDiv(rs.getString("AUTH_BY_DIV"));
                    fpSecurityDocumentHistoryDTO.setPrintedBy(rs.getString("PRINTED_BY"));
                    fpSecurityDocumentHistoryDTO.setPrintedByDiv(rs.getString("PRINTED_BY_DIV"));
                    fpSecurityDocumentHistoryDTO.setReturnComment(rs.getString("RETURN_COMMENT"));
                    fpSecurityDocumentHistoryDTO.setSavedByDisplayName(rs.getString("SAVED_BY_DISPLAY_NAME"));
                    fpSecurityDocumentHistoryDTO.setAuthByDisplayName(rs.getString("AUTH_BY_DISPLAY_NAME"));
                    fpSecurityDocumentHistoryDTO.setPrintedByDisplayName(rs.getString("PRINTED_BY_DISPLAY_NAME"));
                    if (rs.getTimestamp("SAVED_DATE") != null) {
                        fpSecurityDocumentHistoryDTO.setSavedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("SAVED_DATE")));
                    }
                    if (rs.getTimestamp("AUTH_DATE") != null) {
                        fpSecurityDocumentHistoryDTO.setAuthDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("AUTH_DATE")));
                    }
                    if (rs.getTimestamp("PRINTED_DATE") != null) {
                        fpSecurityDocumentHistoryDTO.setPrintedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("PRINTED_DATE")));
                    }

                    FPSecurityDocumentDTOHistoryList.add(fpSecurityDocumentHistoryDTO);
                }

                return FPSecurityDocumentDTOHistoryList;
            }
        });
    }

    public List<FPDocumentElementDTO> getSecurityDocumentElements(Integer facilityPaperID) {

        Map<String, Object> params = new HashMap<>();
        //params.put("creditFacilityTemplateID", creditFacilityTemplateID);
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT  \n");
        SQL.append(" DE.ELEMENT_ID,  \n");
        SQL.append(" DE.PARENT_ID,  \n");
        SQL.append(" DE.ELEMENT_NAME,  \n");
        SQL.append(" DE.ELEMENT_TYPE,  \n");
        SQL.append(" DE.CREDIT_FACILITY_NAME,  \n");
        SQL.append(" DE.FACILITY_TYPE_ID,  \n");
        SQL.append("  DE.IS_NEW,  \n");
        SQL.append(" DE.DOCUMENT_CONTENT,  \n");
        SQL.append(" DE.KEY,  \n");
        SQL.append(" FT.CREDIT_FACILITY_TEMPLATE_ID  \n");
        SQL.append(" FROM T_FACILITY_DOCUMENT_ELEMENT DE, T_CREDIT_FACILITY_TEMPLATE FT  \n");
        SQL.append(" WHERE DE.CREDIT_FACILITY_NAME = FT.CREDIT_FACILITY_NAME  \n");
        SQL.append(" AND FT.STATUS = 'ACT'  \n");
        SQL.append(" AND DE.STATUS = 'ACT'  \n");
        SQL.append(" AND FT.APPROVE_STATUS = 'APPROVED'  \n");
        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<FPDocumentElementDTO>() {

            @Nullable
            @Override
            public FPDocumentElementDTO mapRow(ResultSet rs, int i) throws SQLException {
                FPDocumentElementDTO fpDocumentElementDTO = new FPDocumentElementDTO();
                //fpDocumentElementDTO.setCreditFacilityTemplateID(creditFacilityTemplateID);
                String elementType = rs.getString("ELEMENT_TYPE");
                fpDocumentElementDTO.setCreditFacilityTemplateID(rs.getInt("CREDIT_FACILITY_TEMPLATE_ID"));
                fpDocumentElementDTO.setElementID(rs.getInt("ELEMENT_ID"));
                fpDocumentElementDTO.setParentID(rs.getInt("PARENT_ID"));
                fpDocumentElementDTO.setElementName(rs.getString("ELEMENT_NAME"));
                fpDocumentElementDTO.setElementType(elementType);
                fpDocumentElementDTO.setCreditFacilityName(rs.getString("CREDIT_FACILITY_NAME"));
                fpDocumentElementDTO.setFacilityTypeID(rs.getInt("FACILITY_TYPE_ID"));
                fpDocumentElementDTO.setIsNew(rs.getString("IS_NEW"));
                fpDocumentElementDTO.setKey(rs.getString("KEY"));
                Clob documentContent = rs.getClob("DOCUMENT_CONTENT");
                fpDocumentElementDTO.setDocumentContent(documentContent != null ? documentContent.getSubString(1, (int) documentContent.length()) : "");
                List<FPSecurityDocumentDTO> fpSecurityDocumentDTOList = null;
                if (elementType.equals("D")) {
                    fpSecurityDocumentDTOList = getSecurityDocuments(rs.getInt("ELEMENT_ID"), facilityPaperID);
                }
                fpDocumentElementDTO.setFpSecurityDocumentDTOList(fpSecurityDocumentDTOList);
                return fpDocumentElementDTO;
            }
        });
    }

    public SectorDTO getSectorById(Integer sectorId) throws AppsException {
        Map<String, Object> params = new HashMap<>();
        params.put("sectorId", sectorId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                              \n");
        SQL.append("REF_CODE, REF_DESC              \n");
        SQL.append("FROM T_SECTOR                                                                                   \n");
        SQL.append(" WHERE SECTOR_ID =:sectorId                                                                   \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<SectorDTO>() {

            @Override
            public SectorDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                SectorDTO dataRow = new SectorDTO();
                if (rs.next()) {
                    dataRow.setReferenceCode(rs.getString("REF_CODE"));
                    dataRow.setReferenceDescription(rs.getString("REF_DESC"));
                }
                return dataRow;
            }
        });
    }

    public List<FPSecurityDocumentDTO> getSecurityDocuments(Integer elementID, Integer facilityPaperID) {

        Map<String, Object> params = new HashMap<>();
        params.put("elementID", elementID);
        params.put("facilityPaperID", facilityPaperID);

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT \n");
        SQL.append(" SD.AUTH_BY , \n");
        SQL.append(" SD.AUTH_BY_DISPLAY_NAME , \n");
        SQL.append(" SD.AUTH_BY_DIV , \n");
        SQL.append(" SD.AUTH_DATE , \n");
        SQL.append(" SD.CREDIT_FACILITY_NAME , \n");
        SQL.append(" SD.CREDIT_FACILITY_TEMPLATE_ID , \n");
        SQL.append(" SD.DOCUMENT_CONTENT , \n");
        SQL.append(" SD.DOCUMENT_NAME , \n");
        SQL.append(" SD.DOCUMENT_STATUS , \n");
        SQL.append(" SD.ELEMENT_ID , \n");
        SQL.append(" SD.FACILITY_ID , \n");
        SQL.append(" SD.FACILITY_PAPER_ID , \n");
        SQL.append(" SD.PRINTED_BY , \n");
        SQL.append(" SD.PRINTED_BY_DISPLAY_NAME , \n");
        SQL.append(" SD.PRINTED_BY_DIV , \n");
        SQL.append(" SD.PRINTED_DATE , \n");
        SQL.append(" SD.RETURN_COMMENT , \n");
        SQL.append(" SD.SAVED_BY , \n");
        SQL.append(" SD.SAVED_BY_DISPLAY_NAME , \n");
        SQL.append(" SD.SAVED_BY_DIV , \n");
        SQL.append(" SD.SAVED_DATE , \n");
        SQL.append(" SD.SECURITY_DOCUMENT_ID \n");
        SQL.append(" FROM  T_FACILITY_SECURITY_DOCUMENT SD \n");
        SQL.append(" WHERE SD.FACILITY_PAPER_ID = :facilityPaperID AND SD.ELEMENT_ID = :elementID \n");
        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<FPSecurityDocumentDTO>() {

            @Nullable
            @Override
            public FPSecurityDocumentDTO mapRow(ResultSet rs, int i) throws SQLException {
                FPSecurityDocumentDTO fpSecurityDocumentDTO = new FPSecurityDocumentDTO();
                fpSecurityDocumentDTO.setAuthBy(rs.getString("AUTH_BY"));
                fpSecurityDocumentDTO.setAuthByDisplayName(rs.getString("AUTH_BY_DISPLAY_NAME"));
                fpSecurityDocumentDTO.setAuthByDiv(rs.getString("AUTH_BY_DIV"));
                if (rs.getTimestamp("AUTH_DATE") != null) {
                    fpSecurityDocumentDTO.setAuthDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("AUTH_DATE")));
                }
                fpSecurityDocumentDTO.setCreditFacilityName(rs.getString("CREDIT_FACILITY_NAME"));
                fpSecurityDocumentDTO.setCreditFacilityTemplateID(rs.getInt("CREDIT_FACILITY_TEMPLATE_ID"));
                Clob documentContent = rs.getClob("DOCUMENT_CONTENT");
                fpSecurityDocumentDTO.setDocumentContent(documentContent != null ? documentContent.getSubString(1, (int) documentContent.length()) : "");
                fpSecurityDocumentDTO.setDocumentName(rs.getString("DOCUMENT_NAME"));
                fpSecurityDocumentDTO.setDocumentStatus(rs.getString("DOCUMENT_STATUS"));
                fpSecurityDocumentDTO.setElementID(rs.getInt("ELEMENT_ID"));
                fpSecurityDocumentDTO.setFacilityID(rs.getInt("FACILITY_ID"));
                fpSecurityDocumentDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                fpSecurityDocumentDTO.setPrintedBy(rs.getString("PRINTED_BY"));
                fpSecurityDocumentDTO.setPrintedByDisplayName(rs.getString("PRINTED_BY_DISPLAY_NAME"));
                fpSecurityDocumentDTO.setPrintedByDiv(rs.getString("PRINTED_BY_DIV"));
                if (rs.getTimestamp("PRINTED_DATE") != null) {
                    fpSecurityDocumentDTO.setPrintedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("PRINTED_DATE")));
                }
                fpSecurityDocumentDTO.setReturnComment(rs.getString("RETURN_COMMENT"));
                fpSecurityDocumentDTO.setSavedBy(rs.getString("SAVED_BY"));
                fpSecurityDocumentDTO.setSavedByDisplayName(rs.getString("SAVED_BY_DISPLAY_NAME"));
                fpSecurityDocumentDTO.setSavedByDiv(rs.getString("SAVED_BY_DIV"));
                if (rs.getTimestamp("SAVED_DATE") != null) {
                    fpSecurityDocumentDTO.setSavedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("SAVED_DATE")));
                }
                fpSecurityDocumentDTO.setSecurityDocumentID(rs.getInt("SECURITY_DOCUMENT_ID"));
                List<FPSecurityDocumentTagDataDTO> fpSecurityDocumentTagDataDTOList = getSecurityDocumentTagData(rs.getInt("SECURITY_DOCUMENT_ID"));
                fpSecurityDocumentDTO.setFpSecurityDocumentTagDataDTOList(fpSecurityDocumentTagDataDTOList);
                return fpSecurityDocumentDTO;
            }
        });
    }

    public SubSectorDTO getSubSectorById(Integer sectorId) throws AppsException {
        Map<String, Object> params = new HashMap<>();
        params.put("sectorId", sectorId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                              \n");
        SQL.append("REF_DESC              \n");
        SQL.append("FROM T_SUB_SECTOR                                                                                   \n");
        SQL.append(" WHERE SUB_SECTOR_ID =:sectorId                                                                   \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<SubSectorDTO>() {

            @Override
            public SubSectorDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                SubSectorDTO dataRow = new SubSectorDTO();
                if (rs.next()) {
                    dataRow.setReferenceDescription(rs.getString("REF_DESC"));
                }
                return dataRow;
            }
        });
    }


    public List<FPSecurityDocumentTagDataDTO> getSecurityDocumentTagData(Integer securityDocumentID) {

        Map<String, Object> params = new HashMap<>();
        params.put("securityDocumentID", securityDocumentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT \n");
        SQL.append(" DT.FIELD_TYPE, \n");
        SQL.append(" DT.SECURITY_DOCUMENT_ID, \n");
        SQL.append(" DT.TAG, \n");
        SQL.append(" DT.TAG_ID, \n");
        SQL.append(" DT.TAG_ORDER, \n");
        SQL.append(" DT.TAG_TYPE, \n");
        SQL.append(" DT.TAG_VALUE \n");
        SQL.append(" FROM T_SECURITY_DOCUMENT_TAG_DATA DT \n");
        SQL.append(" WHERE SECURITY_DOCUMENT_ID = :securityDocumentID \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<FPSecurityDocumentTagDataDTO>() {

            @Nullable
            @Override
            public FPSecurityDocumentTagDataDTO mapRow(ResultSet rs, int i) throws SQLException {
                FPSecurityDocumentTagDataDTO fpSecurityDocumentTagDataDTO = new FPSecurityDocumentTagDataDTO();
                fpSecurityDocumentTagDataDTO.setFieldType(rs.getString("FIELD_TYPE"));
                fpSecurityDocumentTagDataDTO.setSecurityDocumentID(rs.getInt("SECURITY_DOCUMENT_ID"));
                fpSecurityDocumentTagDataDTO.setTag(rs.getString("TAG"));
                fpSecurityDocumentTagDataDTO.setTagID(rs.getInt("TAG_ID"));
                fpSecurityDocumentTagDataDTO.setTagOrder(rs.getInt("TAG_ORDER"));
                fpSecurityDocumentTagDataDTO.setTagType(rs.getString("TAG_TYPE"));
                fpSecurityDocumentTagDataDTO.setTagValue(rs.getString("TAG_VALUE"));
                return fpSecurityDocumentTagDataDTO;
            }
        });
    }

    public CreditFacilityTemplateDTO getCreditFacilityTemplateById(Integer creditFacilityTemplateID) {
        Map<String, Object> params = new HashMap<>();
        params.put("creditFacilityTemplateID", creditFacilityTemplateID); // Corrected parameter name
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT * \n");
        SQL.append(" FROM  T_CREDIT_FACILITY_TEMPLATE \n");
        SQL.append(" WHERE  CREDIT_FACILITY_TEMPLATE_ID = :creditFacilityTemplateID \n"); // Corrected parameter reference

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<CreditFacilityTemplateDTO>() {
            @Override
            public CreditFacilityTemplateDTO extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                CreditFacilityTemplateDTO creditFacilityTemplateDTO = null;

                if (resultSet.next()) {
                    creditFacilityTemplateDTO = new CreditFacilityTemplateDTO();
                    creditFacilityTemplateDTO.setCreditFacilityName(resultSet.getString("CREDIT_FACILITY_NAME")); // Assuming column name
                    creditFacilityTemplateDTO.setDescription(resultSet.getString("DESCRIPTION")); // Assuming column name
                    creditFacilityTemplateDTO.setCreditFacilityTemplateID(resultSet.getInt("CREDIT_FACILITY_TEMPLATE_ID"));
                    creditFacilityTemplateDTO.setNewFacilityEmail(resultSet.getString("NEW_FACILITY_EMAIL"));
                    creditFacilityTemplateDTO.setExistingFacilityEmail(resultSet.getString("EXISTING_FACILITY_EMAIL"));
                }
                return creditFacilityTemplateDTO;
            }
        });
    }

    public PurposeOfAdvancedDTO getPurposeById(String refCode) throws AppsException {
        Map<String, Object> params = new HashMap<>();
        params.put("refCode", refCode);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                              \n");
        SQL.append("REF_DESC              \n");
        SQL.append("FROM T_PURPOSE_OF_ADVANCE                                                                                   \n");
        SQL.append(" WHERE REF_CODE =:refCode                                                                   \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<PurposeOfAdvancedDTO>() {

            @Override
            public PurposeOfAdvancedDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                PurposeOfAdvancedDTO dataRow = new PurposeOfAdvancedDTO();
                if (rs.next()) {
                    dataRow.setReferenceDescription(rs.getString("REF_DESC"));
                }
                return dataRow;
            }
        });
    }


    public Integer returnCommitteePaper(CommitteeStatusHistoryDTO committeeStatusHistoryDTO, CredentialsDTO credentialsDTO) {

        Map<String, Object> params = new HashMap<>();
        params.put("committeePaperID", committeeStatusHistoryDTO.getCommitteePaperID());
        params.put("committeePaperStatus", committeeStatusHistoryDTO.getCommitteePaperStatus());
        params.put("modifiedBy", credentialsDTO.getUserName());

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT CA_WORKFLOW.RETURN_COMMITTEE_PAPER(:committeePaperID, :committeePaperStatus, :modifiedBy) RETURN_CODE FROM DUAL");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                Integer response = null;
                if (rs.next()) {
                    response = rs.getInt("RETURN_CODE");
                }
                return response;
            }
        });
    }

    public Integer recommendCommitteePaper(CommitteeStatusHistoryDTO committeeStatusHistoryDTO, CredentialsDTO credentialsDTO) {

        Map<String, Object> params = new HashMap<>();
        params.put("committeePaperID", committeeStatusHistoryDTO.getCommitteePaperID());
        params.put("committeePaperStatus", committeeStatusHistoryDTO.getCommitteePaperStatus());
        params.put("modifiedBy", credentialsDTO.getUserName());

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT CA_WORKFLOW.RECOMMEND_COMMITTEE_PAPER(:committeePaperID, :committeePaperStatus, :modifiedBy) RETURN_CODE FROM DUAL");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                Integer response = null;
                if (rs.next()) {
                    response = rs.getInt("RETURN_CODE");
                }
                return response;
            }
        });
    }

    public BCCPaperDashboardCountDTO getBCCPaperDashboardCount(CommitteePaperDashboardRQ committeePaperDashboardRQ) {   ////
        BCCPaperDashboardCountDTO countDTO = new BCCPaperDashboardCountDTO();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", committeePaperDashboardRQ.getLoggedInUserId());
        params.put("userRole", committeePaperDashboardRQ.getLoggedInUserRole());

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT 'INBOX' STATUS, CASV2_DASHBOARD.GET_BCC_INBOX_COUNT(:userId, :userRole) AS COUNT FROM DUAL \n");
        SQL.append(" UNION ALL \n");
        SQL.append(" SELECT 'IN_PROGRESS' STATUS, CASV2_DASHBOARD.GET_BCC_IN_PROGRESS_COUNT(:userId, :userRole) AS COUNT FROM DUAL \n");
        SQL.append(" UNION ALL \n");
        SQL.append(" SELECT 'APPROVED' STATUS, CASV2_DASHBOARD.GET_BCC_APPROVED_COUNT(:userId, :userRole) AS COUNT FROM DUAL \n");
        SQL.append(" UNION ALL \n");
        SQL.append(" SELECT 'DECLINED' STATUS, CASV2_DASHBOARD.GET_BCC_DECLINED_COUNT(:userId, :userRole) AS COUNT FROM DUAL \n");


        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<BCCPaperDashboardCountDTO>() {
            @Override
            public BCCPaperDashboardCountDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    switch (rs.getString("STATUS")) {
                        case "INBOX": {
                            countDTO.setInboxBCCPaper(rs.getInt("COUNT"));
                            break;
                        }
                        case "IN_PROGRESS": {
                            countDTO.setInProgressBCCPaper(rs.getInt("COUNT"));
                            break;
                        }
                        case "APPROVED": {
                            countDTO.setApprovedBCCPaper(rs.getInt("COUNT"));
                            break;
                        }
                        case "DECLINED": {
                            countDTO.setDeclinedBCCPaper(rs.getInt("COUNT"));
                            break;
                        }
                    }
                }
                return countDTO;
            }
        });
    }

    public Page<CommitteePaperDashboardDTO> getPagedBCCPaperDashboardDTO(CommitteePaperDashboardRQ committeePaperDashboardRQ) { ///

        Map<String, Object> params = new HashMap<>();

        params.put("userId", committeePaperDashboardRQ.getLoggedInUserId());
        params.put("userRole", committeePaperDashboardRQ.getLoggedInUserRole());

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT DASHBOARD_STATUS, DASHBOARD_SUB_STATUS, CUSTOMER_NAME, FACILITY_PAPER_ID,  \n");
        SQL.append(" FP_REFERENCE_NUMBER, BRANCH_CODE, ACCOUNT_NUMBER, \n");
        SQL.append(" LAST_ACTION_DATE_STR, ASSIGNED_USER, FACILITY_PAPER_STATUS, ASSIGNED_DIV_CODE,  \n");
        SQL.append(" ASSIGNED_USER_LEVEL, COMMITTEE_ID, COMMITTEE_NAME, COMMITTEE_TYPE,  \n");
        SQL.append(" COMMITTEE_PAPER_STATUS, COMMITTEE_PAPER_ID, COMMITTEE_TYPE_ID, BCC_APPROVE_STATUS, BCC_DOCS_APPROVE_STATUS \n");


        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("AUTH_PENDING_DOCS")) {
            SQL.append("  FROM TABLE(CASV2_DASHBOARD.GET_BCC_AUTH_PENDING_DOCS_LIST(:userId, :userRole)) \n");
        }
        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("DRAFT")) {
            SQL.append("  FROM TABLE(CASV2_DASHBOARD.GET_BCC_DRAFT_LIST(:userId, :userRole)) \n");
        }

        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("FORWARDED")) {
            SQL.append("  FROM TABLE(CASV2_DASHBOARD.GET_BCC_FORWARDED_LIST(:userId, :userRole)) \n");
        }

        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("PENDING")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_BCC_PENDING_LIST(:userId, :userRole)) \n");
        }

        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("REJECTED")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_BCC_REJECTED_LIST(:userId, :userRole)) \n");
        }

        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("APPROVED")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_BCC_APPROVED_LIST(:userId, :userRole)) \n");
        }

        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("DECLINED")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_BCC_DECLINED_LIST(:userId, :userRole)) \n");
        }

        if (committeePaperDashboardRQ.getCommitteePaperDashboardSubStatus().equals("REJECTED_DOCS")) {
            SQL.append(" FROM TABLE(CASV2_DASHBOARD.GET_BCC_DOCS_REJECTED_LIST(:userId, :userRole)) \n");
        }


        return getPagedData(SQL.toString(), params, new RowMapper<CommitteePaperDashboardDTO>() {

            @Nullable
            @Override
            public CommitteePaperDashboardDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                CommitteePaperDashboardDTO docDTO = new CommitteePaperDashboardDTO();
                docDTO.setDashboardStatus(rs.getString("DASHBOARD_STATUS"));
                docDTO.setDashboardSubStatus(rs.getString("DASHBOARD_SUB_STATUS"));
                docDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                docDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                docDTO.setFpRefNumber(rs.getString("FP_REFERENCE_NUMBER"));
                docDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                docDTO.setBankAccountID(rs.getString("ACCOUNT_NUMBER"));
                docDTO.setLastActionDateStr(rs.getString("LAST_ACTION_DATE_STR"));
                docDTO.setAssignUserDisplayName(rs.getString("ASSIGNED_USER"));
                docDTO.setFacilityPaperStatus(rs.getString("FACILITY_PAPER_STATUS"));
                docDTO.setAssignDepartmentCode(rs.getString("ASSIGNED_DIV_CODE"));
                docDTO.setCommitteePaperStatus(rs.getString("COMMITTEE_PAPER_STATUS"));
                docDTO.setAssignUserLevel(rs.getInt("ASSIGNED_USER_LEVEL"));
                docDTO.setCommitteeID(rs.getInt("COMMITTEE_ID"));
                docDTO.setCommitteePaperID(rs.getInt("COMMITTEE_PAPER_ID"));
                docDTO.setCommitteeType(rs.getString("COMMITTEE_TYPE"));
                docDTO.setCommitteeName(rs.getString("COMMITTEE_NAME"));
                docDTO.setCommitteeTypeID(rs.getInt("COMMITTEE_TYPE_ID"));
                docDTO.setBccApproveStatus(rs.getString("BCC_APPROVE_STATUS"));
                docDTO.setBccDocsApproveStatus(rs.getString("BCC_DOCS_APPROVE_STATUS"));
                return docDTO;
            }
        }, committeePaperDashboardRQ);
    }

    public List<FPStatusHistoryDTO> getFPUsersInvolvedList(Integer facilityPaperID) throws AppsException {

        List<FPStatusHistoryDTO> responseList = new ArrayList<>();
        //This addedUsersIDList is used to restrict duplicates and Return Users and Order by Updated Date
        List<Integer> addedUsersIDList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                                             \n");
        SQL.append("  ASSIGN_USER,                                                                                                     \n");
        SQL.append("  ASSIGN_USER_ID,                                                                                                  \n");
        SQL.append("  ASSIGN_USER_DISPLAY_NAME,                                                                                        \n");
        SQL.append("  ASSIGN_USER_UPM_ID,                                                                                              \n");
        SQL.append("  ASSIGN_USER_DIV_CODE,                                                                                              \n");
        SQL.append("  ASSIGN_USER_UPM_GROUP_CODE                                                                                       \n");
        SQL.append(" FROM T_FP_STATUS_HISTORY tfpsh                                                                                     \n");
        SQL.append("  WHERE tfpsh.FACILITY_PAPER_ID = :facilityPaperID                                                              \n");
        SQL.append(" AND tfpsh.ASSIGN_USER_ID IS NOT NULL                                                                     \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPStatusHistoryDTO>>() {
            @Override
            public List<FPStatusHistoryDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()) {
                    //This If Condition is to restrict adding Duplicates cause quarry returns duplicates : order by and group by does not work with proper order by updated date
                    if (StringUtils.isNotEmpty(resultSet.getString("ASSIGN_USER_ID")) && !addedUsersIDList.contains(resultSet.getInt("ASSIGN_USER_ID"))) {
                        FPStatusHistoryDTO fpStatusHistoryDTO = new FPStatusHistoryDTO();
                        fpStatusHistoryDTO.setAssignUserID(resultSet.getInt("ASSIGN_USER_ID"));
                        fpStatusHistoryDTO.setAssignUser(resultSet.getString("ASSIGN_USER"));
                        fpStatusHistoryDTO.setAssignUserDisplayName(resultSet.getString("ASSIGN_USER_DISPLAY_NAME"));
                        fpStatusHistoryDTO.setAssignUserUpmID(resultSet.getInt("ASSIGN_USER_UPM_ID"));
                        fpStatusHistoryDTO.setAssignUserDivCode(resultSet.getString("ASSIGN_USER_DIV_CODE"));
                        fpStatusHistoryDTO.setAssignUserUpmGroupCode(resultSet.getString("ASSIGN_USER_UPM_GROUP_CODE"));
                        responseList.add(fpStatusHistoryDTO);
                        addedUsersIDList.add(resultSet.getInt("ASSIGN_USER_ID"));
                    }
                }
                return responseList;
            }
        });
    }

    public List<FPCommitteeSignatureDTO> getFPCommitteeSignatureList(Integer facilityPaperID, String riskDivCode) throws AppsException {

        List<FPCommitteeSignatureDTO> responseList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperID);
        params.put("riskDivCode", riskDivCode);
        StringBuilder SQL = new StringBuilder();


        SQL.append(" SELECT FACILITY_PAPER_ID, FP_STATUS_HISTORY_ID, ASSIGN_USER, ASSIGN_USER_ID,  \n");
        SQL.append(" ASSIGN_USER_DIV_CODE, FACILITY_PAPER_STATUS, ASSIGN_USER_DISPLAY_NAME, \n");
        SQL.append(" ASSIGN_USER_UPM_GROUP_CODE, ASSIGN_USER_DIV_TYPE, ASSIGN_USER_DESIGNATION, ASSIGN_USER_FUNCTIONAL_UNITS  \n");
        SQL.append(" FROM TABLE(CA_WORKFLOW.GET_COMMITTEE_SIGNATURE_LIST(:facilityPaperID, :riskDivCode)) \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<FPCommitteeSignatureDTO>() {
            @Nullable
            @Override
            public FPCommitteeSignatureDTO mapRow(ResultSet rs, int i) throws SQLException {
                FPCommitteeSignatureDTO fpCommitteeSignatureDTO = new FPCommitteeSignatureDTO();
                fpCommitteeSignatureDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                fpCommitteeSignatureDTO.setFpStatusHistoryID(rs.getInt("FP_STATUS_HISTORY_ID"));
                fpCommitteeSignatureDTO.setAssignUserID(rs.getString("ASSIGN_USER_ID"));
                fpCommitteeSignatureDTO.setAssignUser(rs.getString("ASSIGN_USER"));
                fpCommitteeSignatureDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                fpCommitteeSignatureDTO.setAssignUserDivCode(rs.getString("ASSIGN_USER_DIV_CODE"));
                fpCommitteeSignatureDTO.setAssignUserUpmGroupCode(rs.getString("ASSIGN_USER_UPM_GROUP_CODE"));
                fpCommitteeSignatureDTO.setAssignUserDivType(rs.getString("ASSIGN_USER_DIV_TYPE"));
                fpCommitteeSignatureDTO.setAssignUserDesignation(rs.getString("ASSIGN_USER_DESIGNATION"));
                fpCommitteeSignatureDTO.setAssignUserFunctionalUnits(rs.getString("ASSIGN_USER_FUNCTIONAL_UNITS"));
                return fpCommitteeSignatureDTO;
            }
        });

    }


    public List<FPUpcSectionDataDTO> getUpcSectionDataByFacilityPaperIdRepository(Integer facilityPaperId) {

        LOG.info("START: Get UpcSection Data By Facility PaperId Repository: {}", facilityPaperId);

        List<FPUpcSectionDataDTO> fPUpcSectionDataDTOList = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT FP_UPC_SECTION_DATA_ID,FACILITY_PAPER_ID,UPC_SECTION_ID,PARENT_SECTION_ID,SECTION_LEVEL,DISPLAY_ORDER,\n" +
                " STATUS,CREATED_DATE,CREATED_BY,MODIFIED_DATE,MODIFIED_BY,VERSION,DATA1,DATA,MODIFIED_USER_DISPLAY_NAME,SECTION_COMMENT\n" +
                " FROM T_FP_UPC_SECTION_DATA\n" +
                " WHERE FACILITY_PAPER_ID=:facilityPaperId ");

        params.put("facilityPaperId", facilityPaperId);

        LOG.info("END: Get UpcSection Data By Facility PaperId Repository: {}", facilityPaperId);
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPUpcSectionDataDTO>>() {
            @Override
            public List<FPUpcSectionDataDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    FPUpcSectionDataDTO fpReviewCIFDetailsDTO = new FPUpcSectionDataDTO();
                    fpReviewCIFDetailsDTO.setFpUpcSectionDataID(rs.getInt("FP_UPC_SECTION_DATA_ID"));
                    fpReviewCIFDetailsDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                    fpReviewCIFDetailsDTO.setUpcSectionID(rs.getInt("UPC_SECTION_ID"));
                    fpReviewCIFDetailsDTO.setParentSectionID(rs.getInt("PARENT_SECTION_ID"));
                    fpReviewCIFDetailsDTO.setSectionLevel(rs.getInt("SECTION_LEVEL"));
                    fpReviewCIFDetailsDTO.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));

                    fpReviewCIFDetailsDTO.setStatus(AppsConstants.Status.valueOf(rs.getString("STATUS")));
                    fpReviewCIFDetailsDTO.setModifiedBy(rs.getString("MODIFIED_BY"));
                    fpReviewCIFDetailsDTO.setModifiedDateStr(rs.getString("MODIFIED_DATE"));
                    fpReviewCIFDetailsDTO.setData(rs.getString("DATA"));
                    fpReviewCIFDetailsDTO.setModifiedUserDisplayName(rs.getString("MODIFIED_USER_DISPLAY_NAME"));
                    fpReviewCIFDetailsDTO.setComment(rs.getString("SECTION_COMMENT"));

                    fPUpcSectionDataDTOList.add(fpReviewCIFDetailsDTO);
                }
                return fPUpcSectionDataDTOList;
            }
        });
    }

    public Integer getVersionOfUPCSectionRepository(FusTraceDTO fpUPCTemplateComparisonRQ) {

        LOG.info("START: Get Version Of UPCSection Repository: {}", fpUPCTemplateComparisonRQ);

        Integer version = 0;
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        params.put("fpUPCSectionDataId", fpUPCTemplateComparisonRQ.getFpUPCSectionDataId());
        params.put("facilityPaperId", fpUPCTemplateComparisonRQ.getFacilityPaperId());
        params.put("upcSectionId", fpUPCTemplateComparisonRQ.getUpcSectionId());

        SQL.append("  SELECT VERSION FROM T_FP_UPC_SECTION_DATA_HISTORY\n" +
                " WHERE FACILITY_PAPER_ID=:facilityPaperId AND \n" +
                " UPC_SECTION_ID=:upcSectionId AND FP_UPC_SECTION_DATA_ID=:fpUPCSectionDataId AND STATUS='ACT'");

        version = namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException {
                return rs.next() ? rs.getInt("VERSION") : 0;
            }
        });

        LOG.info("END: Get Version Of UPCSection Repository: {}", version);

        return version;
    }

    public Integer updateStatusUPCSectionDataHistoryRepository(FusTraceDTO fpUPCTemplateComparisonRQ) {

        LOG.info("START: Update Status UPC Section Data History Repository: {}", fpUPCTemplateComparisonRQ);

        Integer isExecuted = 0;
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("   UPDATE T_FP_UPC_SECTION_DATA_HISTORY \n" +
                "  SET STATUS=:status \n" +
                "  WHERE FP_UPC_SECTION_DATA_ID=:fpUPCSectionDataId AND FACILITY_PAPER_ID=:facilityPaperId AND UPC_SECTION_ID=:upcSectionId");

        params.put("status", fpUPCTemplateComparisonRQ.getStatus());
        params.put("fpUPCSectionDataId", fpUPCTemplateComparisonRQ.getFpUPCSectionDataId());
        params.put("facilityPaperId", fpUPCTemplateComparisonRQ.getFacilityPaperId());
        params.put("upcSectionId", fpUPCTemplateComparisonRQ.getUpcSectionId());

        isExecuted = namedParameterJdbcTemplate.update(SQL.toString(), params);

        LOG.info("END: Update Status UPC Section Data History Repository: {}", isExecuted);

        return isExecuted;
    }


    public Integer saveFPUpcSectionDataHistory(FPUpcSectionDataDTO fpUpcSectionDataDTO, Integer version, CredentialsDTO credentialsDTO) throws AppsException, JsonProcessingException {

        LOG.info("START: SAVE FP Upc SectionData History: {} by: {}", fpUpcSectionDataDTO, credentialsDTO);

        Integer isExecuted = 0;

        Map<String, Object> params = new HashMap<>();
        Date date = new Date();
        StringBuilder SQL = new StringBuilder();

        StringBuilder INSERT_QUERY = new StringBuilder();
        INSERT_QUERY.append(" INSERT INTO T_FP_UPC_SECTION_DATA_HISTORY(FP_UPC_SECTION_DATA_HISTORY_ID, FP_UPC_SECTION_DATA_ID,FACILITY_PAPER_ID, UPC_SECTION_ID, PARENT_SECTION_ID, SECTION_LEVEL,\n" +
                " DISPLAY_ORDER, STATUS,CREATED_DATE,CREATED_BY,MODIFIED_DATE,MODIFIED_BY,VERSION, DATA,MODIFIED_USER_DISPLAY_NAME, SECTION_COMMENT,IS_FORWARD,FORWARD_STATUS)\n" +
                " VALUES\n" +
                " (SEQ_UPC_SECTION_DATA_HISTORY.nextval,:facilitySectionId,:facilityPaperId,:upcSectionId,:parentSectionId,:sectionLevel,:displayOrder,:status,\n" +
                "  :createdDate,:createdBy,:modifiedDate,:modifiedBy,:version,:data,:modifiedUserDisplayName,:sectionComment,:isForward,:forwardStatus)");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("facilitySectionId", fpUpcSectionDataDTO.getFpUpcSectionDataID());
        paramMap.put("facilityPaperId", fpUpcSectionDataDTO.getFacilityPaperID());
        paramMap.put("upcSectionId", fpUpcSectionDataDTO.getUpcSectionID());
        paramMap.put("parentSectionId", fpUpcSectionDataDTO.getParentSectionID());
        paramMap.put("sectionLevel", fpUpcSectionDataDTO.getSectionLevel());
        paramMap.put("displayOrder", fpUpcSectionDataDTO.getDisplayOrder());
        paramMap.put("status", "ACT");
        paramMap.put("createdDate", date);
        paramMap.put("createdBy", credentialsDTO.getUserName());
        paramMap.put("modifiedDate", date);
        paramMap.put("modifiedBy", credentialsDTO.getUserName());
        paramMap.put("version", version);
        paramMap.put("data", fpUpcSectionDataDTO.getData());
        paramMap.put("modifiedUserDisplayName", fpUpcSectionDataDTO.getModifiedUserDisplayName());
        paramMap.put("sectionComment", fpUpcSectionDataDTO.getComment());
        paramMap.put("isForward", 1);
        paramMap.put("forwardStatus", "FORWARDED");

        isExecuted = namedParameterJdbcTemplate.update(INSERT_QUERY.toString(), paramMap);

        LOG.info("END: SAVE FP Upc SectionData History: {} by: {}", isExecuted, credentialsDTO);

        return isExecuted;
    }


    public List<FPUPCTemplateComparisonDateDTO> getUPCTemplateComparisonByDateRepository(FusTraceDTO fpUPCTemplateComparisonRQ) {
        LOG.info("START: Get UPCTemplate Comparison Active Version Repository: {}", fpUPCTemplateComparisonRQ);

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        params.put("fpUPCSectionDataId", fpUPCTemplateComparisonRQ.getFpUPCSectionDataId());
        params.put("facilityPaperId", fpUPCTemplateComparisonRQ.getFacilityPaperId());
        params.put("upcSectionId", fpUPCTemplateComparisonRQ.getUpcSectionId());

        List<FPUPCTemplateComparisonDateDTO> resultSet = new ArrayList<>();

        SQL.append("   SELECT CREATED_DATE FROM " +
                "TABLE(CAST(UPCTEMPLATE_COMPARISON_DATE(:facilityPaperId,:upcSectionId,:fpUPCSectionDataId) " +
                "AS UPC_TEMPLATE_COMPARISONS))");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPUPCTemplateComparisonDateDTO>>() {
            @Override
            public List<FPUPCTemplateComparisonDateDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    FPUPCTemplateComparisonDateDTO fpUPCTemplateComparisonDateDTO = new FPUPCTemplateComparisonDateDTO();
                    try {
                        final String FORMAT = "MM-dd-yyyy";
                        String dateString = rs.getString("CREATED_DATE");
                        Date date = new SimpleDateFormat(FORMAT).parse(dateString);
                        fpUPCTemplateComparisonDateDTO.setCreatedDate(new java.sql.Date(date.getTime()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    resultSet.add(fpUPCTemplateComparisonDateDTO);
                }
                return resultSet;
            }
        });
    }

    public List<FPUPCTemplateComparisonContentDTO> getUPCTemplateComparisonDataRepository(FusTraceDTO fusTraceDTO) {
        LOG.info("START: Get UPCTemplate Comparison Active Version Repository: {}", fusTraceDTO);

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        List<FPUPCTemplateComparisonContentDTO> resultSet = new ArrayList<>();

        SQL.append("   SELECT FP_UPC_SECTION_DATA_HISTORY_ID,DATA,CREATED_BY,SECTION_COMMENT,CREATED_DATE,MODIFIED_DATE,STATUS,MODIFIED_USER_DISPLAY_NAME\n" +
                "  FROM T_FP_UPC_SECTION_DATA_HISTORY\n" +
                "  WHERE FACILITY_PAPER_ID=:facilityPaperId \n" +
                "  AND UPC_SECTION_ID=:upcSectionId \n" +
                "  AND FP_UPC_SECTION_DATA_ID=:fpUPCSectionDataId\n" +
                "  AND CREATED_DATE like :CREATED_DATE\n" +
                "  ORDER BY FP_UPC_SECTION_DATA_HISTORY_ID  DESC");

        params.put("facilityPaperId", fusTraceDTO.getFacilityPaperId());
        params.put("upcSectionId", fusTraceDTO.getUpcSectionId());
        params.put("fpUPCSectionDataId", fusTraceDTO.getFpUPCSectionDataId());
        params.put("CREATED_DATE", new java.sql.Date(fusTraceDTO.getCreatedDate().getTime()));

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPUPCTemplateComparisonContentDTO>>() {
            @Override
            public List<FPUPCTemplateComparisonContentDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    FPUPCTemplateComparisonContentDTO fpUPCTemplateComparisonContentDTO = new FPUPCTemplateComparisonContentDTO();
                    fpUPCTemplateComparisonContentDTO.setFpUPCSectionDataHistoryID(rs.getInt("FP_UPC_SECTION_DATA_HISTORY_ID"));
                    fpUPCTemplateComparisonContentDTO.setModifiedUserDisplayName(rs.getString("MODIFIED_USER_DISPLAY_NAME"));
                    fpUPCTemplateComparisonContentDTO.setFpUPCSectionDataId(fusTraceDTO.getFpUPCSectionDataId());
                    fpUPCTemplateComparisonContentDTO.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                    fpUPCTemplateComparisonContentDTO.setData(rs.getString("DATA"));
                    fpUPCTemplateComparisonContentDTO.setCreatedBy(rs.getString("CREATED_BY"));
                    fpUPCTemplateComparisonContentDTO.setComment(rs.getString("SECTION_COMMENT"));
                    fpUPCTemplateComparisonContentDTO.setStatus(rs.getString("STATUS"));
                    resultSet.add(fpUPCTemplateComparisonContentDTO);
                }
                return resultSet;
            }
        });
    }

    public Boolean saveFUSTraceRepository(FUSTraceRQ fusTraceRQ) {
        Boolean isSuccess = false;
        try {
            LOG.info("START: Save FUSTrace Repository: {} by: {}", fusTraceRQ);

            String sql = "{ call FUS_TRACE_RECORD_INSERTION(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

            List<SqlParameter> paramList = new ArrayList<>();
            paramList.add(new SqlParameter(Types.INTEGER));
            paramList.add(new SqlParameter(Types.INTEGER));
            paramList.add(new SqlParameter(Types.INTEGER));
            paramList.add(new SqlParameter(Types.INTEGER));
            paramList.add(new SqlParameter(Types.VARCHAR));
            paramList.add(new SqlParameter(Types.DATE));
            paramList.add(new SqlParameter(Types.VARCHAR));
            paramList.add(new SqlParameter(Types.VARCHAR));
            paramList.add(new SqlParameter(Types.VARCHAR));
            paramList.add(new SqlParameter(Types.VARCHAR));
            paramList.add(new SqlParameter(Types.DATE));
            paramList.add(new SqlParameter(Types.VARCHAR));
            paramList.add(new SqlParameter(Types.INTEGER));
            paramList.add(new SqlParameter(Types.VARCHAR));

            jdbcTemplate.call(connection -> {
                CallableStatement callableStatement = connection.prepareCall(sql);
                callableStatement.setInt(1, fusTraceRQ.getId());
                callableStatement.setInt(2, fusTraceRQ.getMainKey());
                callableStatement.setInt(3, fusTraceRQ.getSubKey());
                callableStatement.setInt(4, fusTraceRQ.getParentRecId());
                callableStatement.setString(5, fusTraceRQ.getCreatedBy());
                callableStatement.setDate(6, new java.sql.Date(fusTraceRQ.getCreatedDate().getTime()));
                callableStatement.setString(7, fusTraceRQ.getComment());
                callableStatement.setString(8, fusTraceRQ.getFlag());
                callableStatement.setString(9, fusTraceRQ.getStatus());
                callableStatement.setString(10, fusTraceRQ.getCreatedUserDisplayName());
                callableStatement.setDate(11, new java.sql.Date(fusTraceRQ.getModifiedDate().getTime()));
                callableStatement.setString(12, fusTraceRQ.getModifiedBy());
                callableStatement.setInt(13, fusTraceRQ.getCreatedUserWC());
                callableStatement.setString(14, fusTraceRQ.getCreatedUserDesignation());
                return callableStatement;

            }, paramList);
            isSuccess = true;
            LOG.info("END: Save FUSTrace Repository: {} by: {}", fusTraceRQ);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSuccess;
    }

    public Boolean saveFUSTraceView(Integer id, String flag, String actionBy) {
        Boolean isSuccess = false;
        try {

            String sql = "{ call SAVE_VIEW_FUS_TRACE_RECORD(?,?,?)}";

            List<SqlParameter> paramList = new ArrayList<>();
            paramList.add(new SqlParameter(Types.INTEGER));
            paramList.add(new SqlParameter(Types.VARCHAR));
            paramList.add(new SqlParameter(Types.VARCHAR));

            jdbcTemplate.call(connection -> {
                CallableStatement callableStatement = connection.prepareCall(sql);
                callableStatement.setInt(1, id);
                callableStatement.setString(2, flag);
                callableStatement.setString(3, actionBy);

                return callableStatement;

            }, paramList);
            isSuccess = true;
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSuccess;
    }

    public List<UPCTemplateCommentHistoryDTO> getFusTraceDataRepository(FusTraceDTO fusTraceDTO, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get FUS Trace Records: {}", fusTraceDTO);

        List<UPCTemplateCommentHistoryDTO> resultSet = new ArrayList<>();

        try {
            Map<String, Object> params = new HashMap<>();
            StringBuilder SQL = new StringBuilder();

            params.put("mainKey", fusTraceDTO.getMainKey());
            params.put("subKey", fusTraceDTO.getSubKey());
            params.put("flag", fusTraceDTO.getFlag());
            params.put("status", fusTraceDTO.getStatus());

            if (Objects.equals(fusTraceDTO.getFlag(), AppsConstants.FusTraceFlage.ALL.getFlag())) {

                SQL.append("SELECT ID, MAIN_KEY, CREATED_BY, COMMENTS,CREATED_DATE,MODIFIED_DATE, FLAG, STATUS, CREATED_USER_DISPLAY_NAME, MODIFIED_BY, PARENT_REC_ID, IS_VIEW, SUB_KEY, CREATED_USER_WC, CREATED_USER_DESIGNATION FROM " +
                        " TABLE(CAST(GET_ALL_FUS_TRACE_RECORDS_FUNC(:mainKey)" +
                        " AS FUS_TRACE_RECORDS))");
            } else if ((Objects.equals(fusTraceDTO.getCondition(), AppsConstants.FusTraceFlage.UPCTALL.getFlag()))) {
                SQL.append("SELECT ID, MAIN_KEY, CREATED_BY, COMMENTS,CREATED_DATE,MODIFIED_DATE, FLAG, STATUS, CREATED_USER_DISPLAY_NAME, MODIFIED_BY, PARENT_REC_ID, IS_VIEW, SUB_KEY, CREATED_USER_WC, CREATED_USER_DESIGNATION FROM " +
                        " TABLE(CAST(GET_FUS_TRACE_FUNC(:flag, :status)" +
                        " AS FUS_TRACE_RECORDS)) WHERE MAIN_KEY=:mainKey");
            } else {

                SQL.append("SELECT ID, MAIN_KEY, CREATED_BY, COMMENTS,CREATED_DATE,MODIFIED_DATE, FLAG, STATUS, CREATED_USER_DISPLAY_NAME, MODIFIED_BY, PARENT_REC_ID, IS_VIEW, SUB_KEY, CREATED_USER_WC, CREATED_USER_DESIGNATION FROM " +
                        " TABLE(CAST(GET_FUS_TRACE_RECORDS_FUNC(:mainKey,:subKey, :flag, :status)" +
                        " AS FUS_TRACE_RECORDS))");
            }

            return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<UPCTemplateCommentHistoryDTO>>() {
                @Override
                public List<UPCTemplateCommentHistoryDTO> extractData(ResultSet rs) throws SQLException {
                    while (rs.next()) {
                        UPCTemplateCommentHistoryDTO upcTemplateCommentHistoryDTO = new UPCTemplateCommentHistoryDTO();
                        try {

                            upcTemplateCommentHistoryDTO.setId(rs.getInt("ID"));
                            upcTemplateCommentHistoryDTO.setMainKey(rs.getInt("MAIN_KEY"));
                            upcTemplateCommentHistoryDTO.setCreatedBy(rs.getString("CREATED_BY"));
                            upcTemplateCommentHistoryDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                            upcTemplateCommentHistoryDTO.setComment(rs.getString("COMMENTS"));
                            upcTemplateCommentHistoryDTO.setFlag(rs.getString("FLAG"));
                            upcTemplateCommentHistoryDTO.setStatus(rs.getString("STATUS"));
                            upcTemplateCommentHistoryDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                            upcTemplateCommentHistoryDTO.setParentRecordId(rs.getInt("PARENT_REC_ID"));
                            upcTemplateCommentHistoryDTO.setIsView(rs.getInt("IS_VIEW"));
                            upcTemplateCommentHistoryDTO.setSubKey(rs.getInt("SUB_KEY"));
                            upcTemplateCommentHistoryDTO.setCreatedUserWC(rs.getInt("CREATED_USER_WC"));
                            upcTemplateCommentHistoryDTO.setCreatedUserDesignation(rs.getString("CREATED_USER_DESIGNATION"));
                        } catch (Exception e) {
                            LOG.error("ERROR : ", e);
                        }

                        resultSet.add(upcTemplateCommentHistoryDTO);
                    }
                    return resultSet;
                }
            });
        } catch (Exception e) {
            return resultSet;
        }
    }

    public ArrayList<Integer> getUPCSectionHistoryDataByIdRepository(FusTraceDTO fusTraceDTO, CredentialsDTO credentialsDTO) {

        LOG.info("START: Get UPC Section History Data By Id Repository: {}", fusTraceDTO);

        ArrayList<Integer> resultSet = new ArrayList<>();
        StringBuilder SQL = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        SQL.append(" SELECT FP_UPC_SECTION_DATA_HISTORY_ID \n" +
                "  FROM  T_FP_UPC_SECTION_DATA_HISTORY \n" +
                "  WHERE FP_UPC_SECTION_DATA_ID =:fpSectionDataId \n" +
                "  ORDER BY FP_UPC_SECTION_DATA_HISTORY_ID DESC");

        params.put("fpSectionDataId", fusTraceDTO.getFpUPCSectionDataId());

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<ArrayList<Integer>>() {
            @Override
            public ArrayList<Integer> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    resultSet.add(rs.getInt("FP_UPC_SECTION_DATA_HISTORY_ID"));
                }
                return resultSet;
            }
        });
    }

    public ArrayList<FPUpcSectionDataDTO> getSectionsDataByFacilityPaperId(Integer fpId) {

        ArrayList<FPUpcSectionDataDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("fpId", fpId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT TFPUPC.*, TUPCS.UPC_SECTION_NAME                           \n");
        SQL.append("FROM T_FP_UPC_SECTION_DATA TFPUPC LEFT JOIN T_UPC_SECTION TUPCS ON TFPUPC.UPC_SECTION_ID = TUPCS.UPC_SECTION_ID                  \n");
        SQL.append(" WHERE TFPUPC.FACILITY_PAPER_ID =:fpId                                                      \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<ArrayList<FPUpcSectionDataDTO>>() {
            @Override
            public ArrayList<FPUpcSectionDataDTO> extractData(ResultSet rs) throws SQLException {

                while (rs.next()) {
                    FPUpcSectionDataDTO dataRow = new FPUpcSectionDataDTO();

                    dataRow.setFpUpcSectionDataID(rs.getInt("FP_UPC_SECTION_DATA_ID"));
                    dataRow.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                    dataRow.setUpcSectionID(rs.getInt("UPC_SECTION_ID"));
                    dataRow.setUpcSectionName(rs.getString("UPC_SECTION_NAME"));
                    dataRow.setParentSectionID(rs.getInt("PARENT_SECTION_ID"));
                    dataRow.setSectionLevel(rs.getInt("SECTION_LEVEL"));
                    dataRow.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
                    dataRow.setModifiedBy(rs.getString("MODIFIED_BY"));
                    dataRow.setModifiedDateStr(rs.getString("MODIFIED_DATE"));
                    dataRow.setModifiedUserDisplayName(rs.getString("MODIFIED_USER_DISPLAY_NAME"));
                    dataRow.setData(rs.getString("DATA"));
                    dataRow.setComment(rs.getString("SECTION_COMMENT"));
                    dataRow.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));

                    resultSet.add(dataRow);
                }
                return resultSet;
            }
        });
    }

    public FPUpcSectionDataDTO getSectionDataBySectionId(Integer fpId, Integer sectionId) {
        Map<String, Object> params = new HashMap<>();
        params.put("fpId", fpId);
        params.put("sectionId", sectionId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT *                      \n");
        SQL.append("FROM T_FP_UPC_SECTION_DATA                                           \n");
        SQL.append(" WHERE FACILITY_PAPER_ID =:fpId AND UPC_SECTION_ID =:sectionId       \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<FPUpcSectionDataDTO>() {

            @Override
            public FPUpcSectionDataDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                FPUpcSectionDataDTO dataRow = new FPUpcSectionDataDTO();
                if (rs.next()) {
                    dataRow.setFpUpcSectionDataID(rs.getInt("FP_UPC_SECTION_DATA_ID"));
                    dataRow.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                    dataRow.setUpcSectionID(rs.getInt("UPC_SECTION_ID"));
                    dataRow.setParentSectionID(rs.getInt("PARENT_SECTION_ID"));
                    dataRow.setSectionLevel(rs.getInt("SECTION_LEVEL"));
                    dataRow.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
                    dataRow.setModifiedBy(rs.getString("MODIFIED_BY"));
                    dataRow.setModifiedDateStr(rs.getString("MODIFIED_DATE"));
                    dataRow.setModifiedUserDisplayName(rs.getString("MODIFIED_USER_DISPLAY_NAME"));
                    dataRow.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("MODIFIED_DATE")));
                    dataRow.setData(rs.getString("DATA"));
                    dataRow.setComment(rs.getString("SECTION_COMMENT"));
                    dataRow.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));

                }
                return dataRow;
            }
        });
    }

    public List<UPCTemplateCommentHistoryDTO> getFusTraceDataByFacilityPaper(Integer facilityPaperId, String flag, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get FUS Trace Records By Sections: {}", facilityPaperId);

        List<UPCTemplateCommentHistoryDTO> resultSet = new ArrayList<>();

        try {
            Map<String, Object> params = new HashMap<>();
            StringBuilder SQL = new StringBuilder();

            params.put("facilityPaperId", facilityPaperId);
            params.put("createdBy", credentialsDTO.getUserName());

            if (flag.equals("UPCT")) {
                SQL.append("SELECT ID, MAIN_KEY, CREATED_BY, COMMENTS,CREATED_DATE,MODIFIED_DATE, FLAG, STATUS, CREATED_USER_DISPLAY_NAME, MODIFIED_BY, PARENT_REC_ID, IS_VIEW, SUB_KEY, CREATED_USER_WC, CREATED_USER_DESIGNATION FROM " +
                        " TABLE(CAST(GET_FUS_TRACE_RECORDS_BY_FP(:facilityPaperId,:createdBy)" +
                        " AS FUS_TRACE_RECORDS))");
            } else {
                SQL.append("SELECT ID, MAIN_KEY, CREATED_BY, COMMENTS,CREATED_DATE,MODIFIED_DATE, FLAG, STATUS, CREATED_USER_DISPLAY_NAME, MODIFIED_BY, PARENT_REC_ID, IS_VIEW, SUB_KEY, CREATED_USER_WC, CREATED_USER_DESIGNATION FROM " +
                        " TABLE(CAST(GET_FAC_FUS_TRACES_BY_FP(:facilityPaperId,:createdBy)" +
                        " AS FUS_TRACE_RECORDS))");
            }

            return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<UPCTemplateCommentHistoryDTO>>() {
                @Override
                public List<UPCTemplateCommentHistoryDTO> extractData(ResultSet rs) throws SQLException {
                    while (rs.next()) {
                        UPCTemplateCommentHistoryDTO upcTemplateCommentHistoryDTO = new UPCTemplateCommentHistoryDTO();
                        try {

                            upcTemplateCommentHistoryDTO.setId(rs.getInt("ID"));
                            upcTemplateCommentHistoryDTO.setMainKey(rs.getInt("MAIN_KEY"));
                            upcTemplateCommentHistoryDTO.setCreatedBy(rs.getString("CREATED_BY"));
                            upcTemplateCommentHistoryDTO.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                            upcTemplateCommentHistoryDTO.setComment(rs.getString("COMMENTS"));
                            upcTemplateCommentHistoryDTO.setFlag(rs.getString("FLAG"));
                            upcTemplateCommentHistoryDTO.setStatus(rs.getString("STATUS"));
                            upcTemplateCommentHistoryDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                            upcTemplateCommentHistoryDTO.setParentRecordId(rs.getInt("PARENT_REC_ID"));
                            upcTemplateCommentHistoryDTO.setIsView(rs.getInt("IS_VIEW"));
                            upcTemplateCommentHistoryDTO.setSubKey(rs.getInt("SUB_KEY"));
                            upcTemplateCommentHistoryDTO.setCreatedUserWC(rs.getInt("CREATED_USER_WC"));
                            upcTemplateCommentHistoryDTO.setCreatedUserDesignation(rs.getString("CREATED_USER_DESIGNATION"));
                        } catch (Exception e) {
                            LOG.error("ERROR : ", e);
                        }

                        resultSet.add(upcTemplateCommentHistoryDTO);
                    }
                    return resultSet;
                }
            });
        } catch (Exception e) {
            return resultSet;
        }
    }

    public Boolean removeFUSTrace(Integer id) {
        Boolean isSuccess = false;
        try {

            String sql = "{ call REMOVE_FUS_TRACE_RECORD(?)}";

            List<SqlParameter> paramList = new ArrayList<>();
            paramList.add(new SqlParameter(Types.INTEGER));

            jdbcTemplate.call(connection -> {
                CallableStatement callableStatement = connection.prepareCall(sql);
                callableStatement.setInt(1, id);

                return callableStatement;

            }, paramList);
            isSuccess = true;
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSuccess;
    }

    public Integer getCommitteeButtonEnableData(Integer facilityPaperId, CredentialsDTO credentialsDTO) {
        LOG.info("START: getCommitteeButtonEnableData By facilityPaperId: {}", facilityPaperId);

        Map<String, Object> params = new HashMap<>();
        params.put("userName", credentialsDTO.getUserName());
        params.put("facilityPaperId", facilityPaperId);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT CA_WORKFLOW.SHOW_COMMITTEE_BUTTONS(:facilityPaperId, :userName) AS COUNT FROM DUAL \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                int returnCount = 0;
                while (rs.next()) {
                    returnCount = rs.getInt("COUNT");
                }
                return returnCount;
            }
        });
    }

    public List<FPActionDTO> getCommitteePaperInvolvedUsers(Integer committeePaperId) {

        List<FPActionDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("committeePaperId", committeePaperId);
        params.put("cpStatusF", "FORWARDED");
        params.put("cpStatusA", "APPROVED");
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT DISTINCT COMMITTEE_STATUS_HISTORY_ID, CREATED_BY, ACTION_MESSAGE, CREATED_USER_DISPLAY_NAME                      \n");
        SQL.append("FROM CA_COMMITTEE_STATUS_HISTORY                                           \n");
        SQL.append(" WHERE COMMITTEE_PAPER_ID =:committeePaperId AND COMMITTEE_PAPER_STATUS !=:cpStatusF AND COMMITTEE_PAPER_STATUS !=:cpStatusA ORDER BY COMMITTEE_STATUS_HISTORY_ID      \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPActionDTO>>() {
            @Override
            public List<FPActionDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    FPActionDTO fpActionDTO = new FPActionDTO();
                    try {
                        fpActionDTO.setCreatedBy(rs.getString("CREATED_BY"));
                        fpActionDTO.setActionMessage(rs.getString("ACTION_MESSAGE"));
                        fpActionDTO.setCreatedUserDisplayName(rs.getString("CREATED_USER_DISPLAY_NAME"));
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(fpActionDTO);
                }
                return resultSet;
            }
        });
    }

    public List<FPActionDTO> getFacilityPaperInvolvedUsers(Integer facilityPaperId) {

        List<FPActionDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperId", facilityPaperId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT DISTINCT UPDATED_USER                      \n");
        SQL.append("FROM T_FP_STATUS_HISTORY                                           \n");
        SQL.append(" WHERE FACILITY_PAPER_ID =:facilityPaperId       \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FPActionDTO>>() {
            @Override
            public List<FPActionDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    FPActionDTO fpActionDTO = new FPActionDTO();
                    try {
                        fpActionDTO.setCreatedBy(rs.getString("UPDATED_USER"));
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(fpActionDTO);
                }
                return resultSet;
            }
        });
    }

    public List<CribDetailsResponse> getCribHistoryByCustomer(Integer facilityPaperId, String identificationNo) {
        LOG.info("START: Get Crib History: {}", identificationNo);

        List<CribDetailsResponse> resultSet = new ArrayList<>();

        try {
            Map<String, Object> params = new HashMap<>();
            StringBuilder SQL = new StringBuilder();

            params.put("identificationNo", identificationNo);
            params.put("facilityPaperId", facilityPaperId);

            SQL.append("SELECT CRIB_DETAIL_ID, IDENTIFICATION_TYPE, IDENTIFICATION_NO, FULL_NAME, GENDER, CRIB_STATUS, REMARK, CRIB_ISSUE_DATE, FACILITY_PAPER_ID, CREATED_DATE, MODIFIED_DATE, DOCUMENT_STORAGE_ID, DOCUMENT_NAME FROM " +
                    " TABLE(CAST(GET_CRIB_DETAILS_BY_CUSTOMER(:identificationNo,:facilityPaperId)" +
                    " AS CRIB_DETAIL_RECORDS))");

            return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CribDetailsResponse>>() {
                @Override
                public List<CribDetailsResponse> extractData(ResultSet rs) throws SQLException {
                    while (rs.next()) {
                        CribDetailsResponse cribDetailsResponse = new CribDetailsResponse();
                        try {
                            cribDetailsResponse.setCribDetailsID(rs.getInt("CRIB_DETAIL_ID"));
                            cribDetailsResponse.setIdentificationType(rs.getString("IDENTIFICATION_TYPE"));
                            cribDetailsResponse.setIdentificationNumber(rs.getString("IDENTIFICATION_NO"));
                            cribDetailsResponse.setFullName(rs.getString("FULL_NAME"));
                            cribDetailsResponse.setGender(rs.getString("GENDER"));
                            cribDetailsResponse.setCribStatus(DomainConstants.CribStatusType.valueOf(rs.getString("CRIB_STATUS")));
                            cribDetailsResponse.setRemark(rs.getString("REMARK"));
                            cribDetailsResponse.setCribIssueDate(rs.getTimestamp("CRIB_ISSUE_DATE"));
                            cribDetailsResponse.setCribIssueDateStr(rs.getTimestamp("CRIB_ISSUE_DATE").toString());
                            cribDetailsResponse.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                            cribDetailsResponse.setDocStorageID(rs.getInt("DOCUMENT_STORAGE_ID"));
                            cribDetailsResponse.setDocumentName(rs.getString("DOCUMENT_NAME"));
                            cribDetailsResponse.setCreatedDateStr(rs.getString("CREATED_DATE"));
                            cribDetailsResponse.setModifiedDateStr(rs.getString("MODIFIED_DATE"));
                        } catch (Exception e) {
                            LOG.error("ERROR : ", e);
                        }

                        resultSet.add(cribDetailsResponse);
                    }
                    return resultSet;
                }
            });
        } catch (Exception e) {
            return resultSet;
        }
    }

    public List<CAUserDTO> getCommitteeUsersByFacilityPaper(Integer facilityPaperId) {
        LOG.info("START: Get Committee Users for FacilityPaperId: {}", facilityPaperId);

        List<CAUserDTO> resultSet = new ArrayList<>();

        try {
            Map<String, Object> params = new HashMap<>();
            StringBuilder SQL = new StringBuilder();

            params.put("facilityPaperId", facilityPaperId);

            SQL.append("SELECT ")
                    .append(" u.USER_CONFIG_ID, ")
                    .append(" u.USER_STATUS, ")
                    .append(" u.STATUS, ")
                    .append(" cp.COMMITTEE_ID, ")
                    .append(" lc.LEVEL_CONFIG_ID, ")
                    .append(" lc.PATH_TYPE, ")
                    .append(" u.USER_NAME, ")
                    .append(" u.CREATED_DATE, ")
                    .append(" u.CREATED_BY, ")
                    .append(" u.CREATED_USER_DISPLAY_NAME, ")
                    .append(" u.MODIFIED_DATE, ")
                    .append(" u.MODIFIED_BY, ")
                    .append(" u.APPROVED_BY, ")
                    .append(" u.APPROVED_DATE, ")
                    .append(" u.AUTHORIZER_DISPLAY_NAME ")
                    .append(" FROM CA_COMMITTEE_PAPER cp ")
                    .append(" JOIN ca_level_config lc ")
                    .append("   ON lc.COMMITTEE_ID = cp.committee_id ")
                    .append("  AND lc.PATH_TYPE = cp.current_path ")
                    .append("  AND lc.LEVEL_ID = CASE ")
                    .append("                     WHEN cp.current_path = 'REG' THEN cp.current_reg_level_id ")
                    .append("                     WHEN cp.current_path = 'ALT' THEN cp.current_alt_level_id ")
                    .append("                   END ")
                    .append(" JOIN ca_user_config u ")
                    .append("   ON u.LEVEL_CONFIG_ID = lc.LEVEL_CONFIG_ID ")
                    .append(" WHERE cp.facility_paper_id = :facilityPaperId ");

            return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CAUserDTO>>() {
                @Override
                public List<CAUserDTO> extractData(ResultSet rs) throws SQLException {
                    while (rs.next()) {
                        CAUserDTO dto = new CAUserDTO();
                        try {
                            dto.setUserConfigId(rs.getInt("USER_CONFIG_ID"));

                            String userStatus = rs.getString("USER_STATUS");
                            if (userStatus != null) {
                                dto.setUserStatus(AppsConstants.Status.valueOf(userStatus));
                            }

                            String status = rs.getString("STATUS");
                            if (status != null) {
                                dto.setStatus(AppsConstants.Status.valueOf(status));
                            }

                            dto.setCommitteeId(rs.getInt("COMMITTEE_ID"));
                            dto.setLevelConfigId(rs.getInt("LEVEL_CONFIG_ID"));

                            String pathType = rs.getString("PATH_TYPE");
                            if (pathType != null) {
                                dto.setPathType(AppsConstants.CAPathType.valueOf(pathType));
                            }

                            dto.setUserName(rs.getString("USER_NAME"));
                            dto.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                            dto.setCreatedBy(rs.getString("CREATED_BY"));
                            dto.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
                            dto.setModifiedBy(rs.getString("MODIFIED_BY"));

                        } catch (Exception e) {
                            LOG.error("ERROR mapping CAUserDTO: ", e);
                        }

                        resultSet.add(dto);
                    }
                    return resultSet;
                }
            });
        } catch (Exception e) {
            LOG.error("ERROR in getCommitteeUsersByFacilityPaper: ", e);
            return resultSet;
        }
    }

    public List<SecurityDocumentDTO> getSecurityDocumentHistoryData(Integer securityDocumentID) throws AppsException{

        List<SecurityDocumentDTO> FPSecurityDocumentDTOHistoryList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("securityDocumentID", securityDocumentID);
        QueryBuilder SQL = new QueryBuilder();

        SQL.append(" SELECT DOCUMENT_NAME, DOCUMENT_STATUS, SAVED_BY, SAVED_BY_DIV, SAVED_DATE, AUTH_BY, AUTH_BY_DIV, AUTH_DATE, PRINTED_BY, PRINTED_BY_DIV, \n");
        SQL.append(" PRINTED_DATE, ACTION_COMMENT, SAVED_BY_DISPLAY_NAME, AUTH_BY_DISPLAY_NAME, PRINTED_BY_DISPLAY_NAME, \n");
        SQL.append(" RECOMMENDED_RETURN_BY, RECOMMENDED_RETURN_DATE, RECOMMENDED_RETURN_DISPLAY_NAME \n");
        SQL.append(" FROM T_FP_SECURITY_DOCUMENT_AUD \n");
        SQL.append(" WHERE SECURITY_DOCUMENT_ID = :securityDocumentID ORDER BY REV\n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<SecurityDocumentDTO>>()  {
            @Override
            public List<SecurityDocumentDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()){
                    SecurityDocumentDTO fpSecurityDocumentHistoryDTO = new SecurityDocumentDTO();
                    fpSecurityDocumentHistoryDTO.setDocumentName(rs.getString("DOCUMENT_NAME"));
                    fpSecurityDocumentHistoryDTO.setDocumentStatus(rs.getString("DOCUMENT_STATUS"));
                    fpSecurityDocumentHistoryDTO.setSavedBy(rs.getString("SAVED_BY"));
                    fpSecurityDocumentHistoryDTO.setSavedByDiv(rs.getString("SAVED_BY_DIV"));
                    fpSecurityDocumentHistoryDTO.setAuthBy(rs.getString("AUTH_BY"));
                    fpSecurityDocumentHistoryDTO.setAuthByDiv(rs.getString("AUTH_BY_DIV"));
                    fpSecurityDocumentHistoryDTO.setPrintedBy(rs.getString("PRINTED_BY"));
                    fpSecurityDocumentHistoryDTO.setPrintedByDiv(rs.getString("PRINTED_BY_DIV"));
                    fpSecurityDocumentHistoryDTO.setActionComment(rs.getString("ACTION_COMMENT"));
                    fpSecurityDocumentHistoryDTO.setSavedByDisplayName(rs.getString("SAVED_BY_DISPLAY_NAME"));
                    fpSecurityDocumentHistoryDTO.setAuthByDisplayName(rs.getString("AUTH_BY_DISPLAY_NAME"));
                    fpSecurityDocumentHistoryDTO.setPrintedByDisplayName(rs.getString("PRINTED_BY_DISPLAY_NAME"));

                    String recommandReturnBy = rs.getString("RECOMMENDED_RETURN_BY");

                    fpSecurityDocumentHistoryDTO.setRecommendedReturnBy(recommandReturnBy);
                    if (recommandReturnBy != null && !recommandReturnBy.isEmpty()) {
                        fpSecurityDocumentHistoryDTO.setIsRecommendedReturn(AppsConstants.YesNo.Y);
                    }else {
                        fpSecurityDocumentHistoryDTO.setIsRecommendedReturn(AppsConstants.YesNo.N);
                    }
                    fpSecurityDocumentHistoryDTO.setRecommendedReturnDisplayName(rs.getString("RECOMMENDED_RETURN_DISPLAY_NAME"));
                    if (rs.getTimestamp("SAVED_DATE") != null) {
                        fpSecurityDocumentHistoryDTO.setSavedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("SAVED_DATE")));
                    }
                    if (rs.getTimestamp("AUTH_DATE") != null) {
                        fpSecurityDocumentHistoryDTO.setAuthDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("AUTH_DATE")));
                    }
                    if (rs.getTimestamp("PRINTED_DATE") != null) {
                        fpSecurityDocumentHistoryDTO.setPrintedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("PRINTED_DATE")));
                    }
                    if (rs.getTimestamp("RECOMMENDED_RETURN_DATE") != null) {
                        fpSecurityDocumentHistoryDTO.setRecommendedReturnDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("RECOMMENDED_RETURN_DATE")));
                    }

                    FPSecurityDocumentDTOHistoryList.add(fpSecurityDocumentHistoryDTO);
                }

                return FPSecurityDocumentDTOHistoryList;
            }
        }) ;
}
    public Integer updateStatusUPCSectionDataHistory(Integer fpId) {

        LOG.info("START: Update Status UPC Section Data History Repository: {}", fpId);

        Integer isExecuted = 0;
        try {
            Map<String, Object> params = new HashMap<>();
            StringBuilder SQL = new StringBuilder();
            SQL.append("INSERT INTO T_FP_UPC_SECTION_DATA_HISTORY (")
                    .append(" FP_UPC_SECTION_DATA_HISTORY_ID, FP_UPC_SECTION_DATA_ID, FACILITY_PAPER_ID, UPC_SECTION_ID, ")
                    .append(" PARENT_SECTION_ID, SECTION_LEVEL, DISPLAY_ORDER, STATUS, ")
                    .append(" CREATED_DATE, CREATED_BY, MODIFIED_DATE, MODIFIED_BY, ")
                    .append(" VERSION, DATA, MODIFIED_USER_DISPLAY_NAME, SECTION_COMMENT, ")
                    .append(" IS_FORWARD, FORWARD_STATUS")
                    .append(") ")
                    .append("SELECT ")
                    .append(" SEQ_UPC_SECTION_DATA_HISTORY.NEXTVAL, t.FP_UPC_SECTION_DATA_ID, t.FACILITY_PAPER_ID, t.UPC_SECTION_ID, ")
                    .append(" t.PARENT_SECTION_ID, t.SECTION_LEVEL, t.DISPLAY_ORDER, t.STATUS, ")
                    .append(" t.CREATED_DATE, t.CREATED_BY, t.MODIFIED_DATE, t.MODIFIED_BY, ")
                    .append(" NVL(( ")
                    .append(" SELECT MAX(h.VERSION) KEEP (DENSE_RANK LAST ORDER BY h.MODIFIED_DATE) ")
                    .append(" FROM T_FP_UPC_SECTION_DATA_HISTORY h ")
                    .append(" WHERE h.FACILITY_PAPER_ID = t.FACILITY_PAPER_ID ")
                    .append(" AND h.UPC_SECTION_ID = t.UPC_SECTION_ID ")
                    .append(" AND h.FP_UPC_SECTION_DATA_ID = t.FP_UPC_SECTION_DATA_ID ")
                    .append(" AND h.STATUS = 'ACT' ")
                    .append(" ), 0) + 1 AS VERSION,")
                    .append(" t.DATA, t.MODIFIED_USER_DISPLAY_NAME, t.SECTION_COMMENT, ")
                    .append(" :isForward, :forwardStatus ")
                    .append("FROM T_FP_UPC_SECTION_DATA t ")
                    .append("WHERE t.FACILITY_PAPER_ID = :facilityPaperId AND t.DATA IS NOT NULL");

            params.put("facilityPaperId", fpId);
            params.put("isForward", 1);
            params.put("forwardStatus", "FORWARDED");

            isExecuted = namedParameterJdbcTemplate.update(SQL.toString(), params);

            LOG.info("END: Update Status UPC Section Data History Repository: {}", isExecuted);
        } catch (Exception e) {
            LOG.info("ERROR: Update Status UPC Section Data History: ", e);
        }
        return isExecuted;
    }

    public List<DeviationDTO> getDeviations() {

        List<DeviationDTO> resultSet = new ArrayList<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT * FROM T_DEVIATIONS WHERE STATUS = 'ACT'");

        return namedParameterJdbcTemplate.query(SQL.toString(), new ResultSetExtractor<List<DeviationDTO>>() {
            @Override
            public List<DeviationDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    DeviationDTO deviationDTO = new DeviationDTO();
                    try {
                        deviationDTO.setDeviationId(rs.getInt("DEVIATION_ID"));
                        deviationDTO.setDeviationType(rs.getString("DEVIATION_TYPE"));
                        deviationDTO.setDescription(rs.getString("DESCRIPTION"));
                        deviationDTO.setStatus(rs.getString("STATUS"));
                    } catch (Exception e) {
                        LOG.error("ERROR : getDeviations ", e);
                    }

                    resultSet.add(deviationDTO);
                }
                return resultSet;
            }
        });
    }

    public List<CompDeviationDTO> getCompDeviations(Integer facilityPaperId) {

        List<CompDeviationDTO> resultSet = new ArrayList<>();

        StringBuilder SQL = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperId", facilityPaperId);

        SQL.append("SELECT * FROM T_COMP_DEVIATIONS WHERE FACILITY_PAPER_ID=:facilityPaperId AND STATUS = 'ACT'");

        return namedParameterJdbcTemplate.query(SQL.toString(), params ,new ResultSetExtractor<List<CompDeviationDTO>>() {
            @Override
            public List<CompDeviationDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    CompDeviationDTO deviationDTO = new CompDeviationDTO();
                    try {
                        deviationDTO.setFpDeviationId(rs.getInt("FP_DEVIATION_ID"));
                        deviationDTO.setDeviationId(rs.getInt("DEVIATION_ID"));
                        deviationDTO.setFacilityPaperId(rs.getInt("FACILITY_PAPER_ID"));
                        deviationDTO.setDescription(rs.getString("DESCRIPTION"));
                        deviationDTO.setDeviationType(rs.getString("DEVIATION_TYPE"));
                        String isChecked = rs.getString("IS_CHECKED");
                        if (isChecked != null){
                            deviationDTO.setChecked(isChecked.equals(AppsConstants.YesNo.Y.toString()));
                        }else {
                            deviationDTO.setChecked(false);
                        }
                        deviationDTO.setDivComment(rs.getString("DIV_COMMENT"));
                        deviationDTO.setStatus(rs.getString("STATUS"));
                        deviationDTO.setCreatedBy(rs.getString("CREATED_BY"));
                        deviationDTO.setModifiedBy(rs.getString("MODIFIED_BY"));


                        if (rs.getTimestamp("CREATED_DATE") != null) {
                            deviationDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("CREATED_DATE")));
                        }
                        if (rs.getTimestamp("MODIFIED_DATE") != null) {
                            deviationDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("MODIFIED_DATE")));
                        }
                    } catch (Exception e) {
                        LOG.error("ERROR : getCompDeviations ", e);
                    }

                    resultSet.add(deviationDTO);
                }
                return resultSet;
            }
        });
    }
}
