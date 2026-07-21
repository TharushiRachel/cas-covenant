package com.itechro.cas.dao.bccpaper.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperSearchRQ;
import com.itechro.cas.model.dto.facilitypaper.SearchFacilityPaperRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BCCPaperJdbcDao extends BaseJDBCDao {

    public Page<FacilityPaperDTO> getPagedFacilityPaperDTOForBCC(SearchFacilityPaperRQ facilityPaperSearchRQ) throws AppsException {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT                                 \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                 \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,             \n");
        SQL.append("  fp.BRANCH_CODE,                       \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,     \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,               \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,          \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                   \n");
        SQL.append("  fp.APPROVED_DATE,                     \n");
        SQL.append("  fp.CREATED_DATE,                      \n");
        SQL.append("  fp.CREATED_BY,                        \n");
        SQL.append("  fp.IS_BCC_CREATED,                    \n");
        SQL.append("  fp.FP_REF_NUMBER,                     \n");
        SQL.append("  fp.LEAD_REF_NUMBER,                   \n");
        SQL.append("  cus.CUSTOMER_NAME,                    \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME                  \n");
        SQL.append(" FROM T_FACILITY_PAPER fp               \n");
        SQL.append(" INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID   \n");
        SQL.append(" LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                  \n");
        SQL.append(" LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER             \n");
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

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCustomerName())) {
            SQL.append("AND upper(cus.CUSTOMER_NAME) LIKE '%" + facilityPaperSearchRQ.getCustomerName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getLeadRefNumber())) {
            SQL.append("AND upper(lead.LEAD_REF_NUMBER) LIKE '%" + facilityPaperSearchRQ.getLeadRefNumber().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getBankAccountID())) {
            SQL.append("AND upper(fp.BANK_ACCOUNT_ID) LIKE '%" + facilityPaperSearchRQ.getBankAccountID().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotBlank(facilityPaperSearchRQ.getBranchCode())) {
            SQL.append("       AND fp.BRANCH_CODE = :branchCode \n");
            params.put("branchCode", facilityPaperSearchRQ.getBranchCode());
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCreatedFromDateStr())) {
            SQL.append("        AND fp.APPROVED_DATE >= :approvedFromDateStr \n");
            params.put("approvedFromDateStr", CalendarUtil.getParsedStartDateTime(facilityPaperSearchRQ.getCreatedFromDateStr()));
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCreatedToDateStr())) {
            SQL.append("        AND fp.APPROVED_DATE <= :approvedToDateStr \n");
            params.put("approvedToDateStr", CalendarUtil.getParsedEndDateTime(facilityPaperSearchRQ.getCreatedToDateStr()));
        }

        SQL.append(" ORDER BY fp.APPROVED_DATE DESC\n");

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                if (StringUtils.isNotEmpty(rs.getString("CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                } else if (StringUtils.isNotEmpty(rs.getString("CAS_CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CAS_CUSTOMER_NAME"));
                }
                facilityPaperDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                facilityPaperDTO.setBankAccountID(rs.getString("BANK_ACCOUNT_ID"));
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                facilityPaperDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    facilityPaperDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                if (StringUtils.isNotEmpty(rs.getString("ASSIGN_USER_DISPLAY_NAME"))) {
                    facilityPaperDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                }
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    facilityPaperDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }

                if (StringUtils.isNotEmpty(rs.getString("IS_BCC_CREATED"))) {
                    facilityPaperDTO.setIsBccCreated(AppsConstants.YesNo.valueOf(rs.getString("IS_BCC_CREATED")));
                }
                facilityPaperDTO.setCreatedBy(rs.getString("CREATED_BY"));
                facilityPaperDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                facilityPaperDTO.setLeadRefNumber(rs.getString("LEAD_REF_NUMBER"));
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }

    public Page<FacilityPaperDTO> getPagedFacilityPaperDTOForBCCForUserName(SearchFacilityPaperRQ facilityPaperSearchRQ, CredentialsDTO credentialsDTO) throws AppsException {

        Map<String, Object> params = new HashMap<>();
        params.put("userName", credentialsDTO.getUserName());
        params.put("fpStatus", facilityPaperSearchRQ.getFacilityPaperStatus().toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("  SELECT                                 \n");
        SQL.append("  fp.FACILITY_PAPER_ID,                 \n");
        SQL.append("  fp.FACILITY_PAPER_NUMBER,             \n");
        SQL.append("  bcc.PAPER_TYPE,                       \n");
        SQL.append("  bcc.STATUS,                           \n");
        SQL.append("  fp.BRANCH_CODE,                       \n");
        SQL.append("  fp.CURRENT_FACILITY_PAPER_STATUS,     \n");
        SQL.append("  fp.CURRENT_ASSIGN_USER,               \n");
        SQL.append("  fp.ASSIGN_USER_DISPLAY_NAME,          \n");
        SQL.append("  fp.BANK_ACCOUNT_ID,                   \n");
        SQL.append("  fp.APPROVED_DATE,                     \n");
        SQL.append("  fp.CREATED_DATE,                      \n");
        SQL.append("  fp.CREATED_BY,                        \n");
        SQL.append("  fp.IS_BCC_CREATED,                    \n");
        SQL.append("  fp.FP_REF_NUMBER,                     \n");
        SQL.append("  fp.LEAD_REF_NUMBER,                   \n");
        SQL.append("  cus.CUSTOMER_NAME,                    \n");
        SQL.append("  cc.CAS_CUSTOMER_NAME                  \n");
        SQL.append(" FROM T_FACILITY_PAPER fp               \n");
        SQL.append(" INNER JOIN T_CAS_CUSTOMER cc ON fp.FACILITY_PAPER_ID = cc.FACILITY_PAPER_ID                    \n");
        SQL.append(" LEFT JOIN T_CUSTOMER cus ON cc.CUSTOMER_ID = cus.CUSTOMER_ID                                   \n");
        SQL.append(" LEFT JOIN T_LEAD lead ON fp.LEAD_REF_NUMBER = lead.LEAD_REF_NUMBER                             \n");
        SQL.append(" LEFT JOIN T_BOARD_CREDIT_COMMITTEE_PAPER bcc ON fp.FACILITY_PAPER_ID = bcc.FACILITY_PAPER_ID   \n");
        SQL.append(" WHERE fp.FACILITY_PAPER_ID IS NOT NULL \n");
        SQL.append(" AND cc.IS_PRIMARY = 'Y'\n");
        SQL.append(" AND fp.CURRENT_FACILITY_PAPER_STATUS = :fpStatus\n");
        SQL.append(" AND (bcc.STATUS = 'ACT')\n");
        SQL.append(" AND bcc.CURRENT_ASSIGN_USER = :userName \n");

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getFpRefNumber())) {
            SQL.append("AND upper(fp.FP_REF_NUMBER) LIKE '%" + facilityPaperSearchRQ.getFpRefNumber().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getBankAccountID())) {
            SQL.append("AND upper(fp.BANK_ACCOUNT_ID) LIKE '%" + facilityPaperSearchRQ.getBankAccountID().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCreatedFromDateStr())) {
            SQL.append("        AND fp.APPROVED_DATE >= :approvedFromDateStr \n");
            params.put("approvedFromDateStr", CalendarUtil.getParsedStartDateTime(facilityPaperSearchRQ.getCreatedFromDateStr()));
        }

        if (StringUtils.isNotEmpty(facilityPaperSearchRQ.getCreatedToDateStr())) {
            SQL.append("        AND fp.APPROVED_DATE <= :approvedToDateStr \n");
            params.put("approvedToDateStr", CalendarUtil.getParsedEndDateTime(facilityPaperSearchRQ.getCreatedToDateStr()));
        }

        SQL.append(" ORDER BY fp.APPROVED_DATE DESC\n");

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityPaperDTO>() {

            @Nullable
            @Override
            public FacilityPaperDTO mapRow(ResultSet rs, int i) throws SQLException {

                FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO();
                facilityPaperDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityPaperDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                if (StringUtils.isNotEmpty(rs.getString("CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                } else if (StringUtils.isNotEmpty(rs.getString("CAS_CUSTOMER_NAME"))) {
                    facilityPaperDTO.setCustomerName(rs.getString("CAS_CUSTOMER_NAME"));
                }
                facilityPaperDTO.setFacilityPaperNumber(rs.getString("FACILITY_PAPER_NUMBER"));
                facilityPaperDTO.setBankAccountID(rs.getString("BANK_ACCOUNT_ID"));
                facilityPaperDTO.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.resolveStatus(rs.getString("CURRENT_FACILITY_PAPER_STATUS")));
                facilityPaperDTO.setCurrentAssignUser(rs.getString("CURRENT_ASSIGN_USER"));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    facilityPaperDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                if (StringUtils.isNotEmpty(rs.getString("ASSIGN_USER_DISPLAY_NAME"))) {
                    facilityPaperDTO.setAssignUserDisplayName(rs.getString("ASSIGN_USER_DISPLAY_NAME"));
                }
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    facilityPaperDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }

                if (StringUtils.isNotEmpty(rs.getString("IS_BCC_CREATED"))) {
                    facilityPaperDTO.setIsBccCreated(AppsConstants.YesNo.valueOf(rs.getString("IS_BCC_CREATED")));
                }
                facilityPaperDTO.setCreatedBy(rs.getString("CREATED_BY"));
                facilityPaperDTO.setFpRefNumber(rs.getString("FP_REF_NUMBER"));
                facilityPaperDTO.setLeadRefNumber(rs.getString("LEAD_REF_NUMBER"));
                return facilityPaperDTO;
            }

        }, facilityPaperSearchRQ);
    }
}
