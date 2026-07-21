package com.itechro.cas.dao.audit;

import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.audit.WebAuditSearchRQ;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.QueryInBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class WebAuditJDBCDao extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(WebAuditJDBCDao.class);

    public Page<WebAuditDTO> getPagedWebAudit(WebAuditSearchRQ searchRQ) throws AppsException {


        Map<String, Object> params = new HashMap<>();
        params.put("userID", searchRQ.getUserID());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append("  wa.WEB_AUDIT_ID, \n");
        SQL.append("  wa.USER_ID, \n");
        SQL.append("  wa.AUDIT_MAIN_CATEGORY, \n");
        SQL.append("  wa.AUDIT_SUB_CATEGORY, \n");
        SQL.append("  wa.AUDIT_TYPE, \n");
        SQL.append("  wa.AUDIT_TYPE_ID, \n");
        SQL.append("  wa.AUDITED_DATE_TIME, \n");
        SQL.append("  wa.PREVIOUS_CONTENT, \n");
        SQL.append("  wa.UPDATED_CONTENT, \n");
        SQL.append("  u.USER_NAME, \n");
        SQL.append("  u.FIRST_NAME, \n");
        SQL.append("  u.LAST_NAME \n");
        SQL.append(" FROM \n");
        SQL.append(" t_web_audit wa \n");
        SQL.append(" INNER JOIN t_user u \n");
        SQL.append(" ON \n");
        SQL.append(" wa.USER_ID = u.USER_ID \n");

        if (!searchRQ.getAuditMainCategoryList().isEmpty()) {
            SQL.append("AND wa.AUDIT_MAIN_CATEGORY IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getAuditMainCategoryList()) + ") \n");
        }

        if (!searchRQ.getAuditSubCategoryList().isEmpty()) {
            SQL.append("AND wa.AUDIT_SUB_CATEGORY IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getAuditSubCategoryList()) + ") \n");
        }

        if (!searchRQ.getAuditTypeList().isEmpty()) {
            SQL.append("AND wa.AUDIT_TYPE IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getAuditTypeList()) + ") \n");
        }

        if (searchRQ.getAuditTypeID() != null) {
            SQL.append("AND wa.AUDIT_TYPE_ID =:auditTypeID \n");
            params.put("auditTypeID", searchRQ.getAuditTypeID());
        }

        if (StringUtils.isNotEmpty(searchRQ.getAuditFromDateStr())) {
            SQL.append("AND wa.AUDITED_DATE_TIME >= :auditFromDateTime \n");
            params.put("auditFromDateTime", CalendarUtil.getParsedStartDateTime(searchRQ.getAuditFromDateStr()));
        }

        if (StringUtils.isNotEmpty(searchRQ.getAuditToDateStr())) {
            SQL.append("AND wa.AUDITED_DATE_TIME <= :auditToDateTime \n");
            params.put("auditToDateTime", CalendarUtil.getParsedEndDateTime(searchRQ.getAuditToDateStr()));
        }

        if (StringUtils.isNotEmpty(searchRQ.getContent())) {
            SQL.append("AND upper(wa.CONTENT) LIKE '%" + searchRQ.getContent().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getUserFirstName())) {
            SQL.append("AND upper(u.FIRST_NAME) LIKE '%" + searchRQ.getUserFirstName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getUserLastName())) {
            SQL.append("AND upper(u.LAST_NAME) LIKE '%" + searchRQ.getUserLastName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getUserName())) {
            SQL.append("AND upper(u.USER_NAME) LIKE '%" + searchRQ.getUserName().toUpperCase() + "%' \n");
        }

        SQL.append("ORDER BY wa.AUDITED_DATE_TIME DESC");


        return getPagedData(SQL.toString(), params, new RowMapper<WebAuditDTO>() {

            @Override
            public WebAuditDTO mapRow(ResultSet rs, int i) throws SQLException {
                WebAuditDTO webAuditDTO = new WebAuditDTO();

                webAuditDTO.setWebAuditID(rs.getInt("WEB_AUDIT_ID"));
                webAuditDTO.setAuditMainCategory(rs.getString("AUDIT_MAIN_CATEGORY"));
                webAuditDTO.setAuditSubCategory(rs.getString("AUDIT_SUB_CATEGORY"));
                webAuditDTO.setAuditTypeStr(rs.getString("AUDIT_TYPE"));
                webAuditDTO.setAuditTypeID(rs.getInt("AUDIT_TYPE_ID"));
                webAuditDTO.setAuditedDateTimeStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("AUDITED_DATE_TIME")));
                webAuditDTO.setUpdateContent(rs.getString("UPDATED_CONTENT"));
                webAuditDTO.setPreviousContent(rs.getString("PREVIOUS_CONTENT"));

                webAuditDTO.setUserID(rs.getInt("USER_ID"));
                webAuditDTO.setUserName(rs.getString("USER_NAME"));
                webAuditDTO.setUserFirstName(rs.getString("FIRST_NAME"));
                webAuditDTO.setUserLastName(rs.getString("LAST_NAME"));


                return webAuditDTO;
            }
        }, searchRQ);


    }

    public String getUserNameByID(Integer userID) {

        Map<String, Object> params = new HashMap<>();
        params.put("userID", userID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" us.USER_NAME \n");
        SQL.append(" FROM \n");
        SQL.append(" t_user us \n");
        SQL.append(" WHERE \n");
        SQL.append(" us.USER_ID =:userID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getCustomerNameByID(Integer customerID) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("customerID", customerID.toString());

            StringBuilder SQL = new StringBuilder();

            SQL.append("SELECT \n");
            SQL.append(" c.CUSTOMER_NAME \n");
            SQL.append(" FROM \n");
            SQL.append(" t_customer c \n");
            SQL.append(" WHERE \n");
            SQL.append(" c.CUSTOMER_ID =:customerID \n");

            return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
        } catch (Exception e) {
            LOG.warn("WARN : No customer found for customerID:{}", customerID, e);
            return null;
        }
    }

    public String getCustomerNameByBankDetailID(Integer bankDetailID) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("bankDetailID", bankDetailID.toString());

            StringBuilder SQL = new StringBuilder();

            SQL.append("SELECT \n");
            SQL.append(" c.CUSTOMER_NAME \n");
            SQL.append(" FROM \n");
            SQL.append(" t_customer c \n");
            SQL.append(" INNER JOIN T_CUSTOMER_BANK_DETAILS cbd ON \n");
            SQL.append(" c.CUSTOMER_ID = cbd.CUSTOMER_ID \n");
            SQL.append(" WHERE \n");
            SQL.append(" cbd.CUSTOMER_BANK_DETAILS_ID =:bankDetailID \n");

            return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
        } catch (Exception e) {
            LOG.warn("WARN : No customer found for bankDetailID:{}", bankDetailID, e);
            return null;
        }
    }

    public Integer getCustomerIDByBankDetailID(Integer bankDetailID) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("bankDetailID", bankDetailID.toString());

            StringBuilder SQL = new StringBuilder();

            SQL.append("SELECT \n");
            SQL.append(" c.CUSTOMER_ID \n");
            SQL.append(" FROM \n");
            SQL.append(" t_customer c \n");
            SQL.append(" INNER JOIN T_CUSTOMER_BANK_DETAILS cbd ON \n");
            SQL.append(" c.CUSTOMER_ID = cbd.CUSTOMER_ID \n");
            SQL.append(" WHERE \n");
            SQL.append(" cbd.CUSTOMER_BANK_DETAILS_ID =:bankDetailID \n");

            return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, Integer.class);
        } catch (Exception e) {
            LOG.warn("WARN : No customer found for bankDetailID:{}", bankDetailID, e);
            return null;
        }
    }

    public String getUpcTemplateNameByID(Integer upcTemplateID) {

        Map<String, Object> params = new HashMap<>();
        params.put("upcTemplateID", upcTemplateID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" ut.TEMPLATE_NAME \n");
        SQL.append(" FROM \n");
        SQL.append(" t_upc_template ut \n");
        SQL.append(" WHERE \n");
        SQL.append(" ut.UPC_TEMPLATE_ID =:upcTemplateID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getSupportDocNameByID(Integer supportDocID) {

        Map<String, Object> params = new HashMap<>();
        params.put("supportingDocID", supportDocID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" sd.DOCUMENT_NAME \n");
        SQL.append(" FROM \n");
        SQL.append(" t_supporting_doc sd \n");
        SQL.append(" WHERE \n");
        SQL.append(" sd.SUPPORTING_DOC_ID =:supportingDocID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getCustomerNameByCASCustomerID(Integer casCustomerID) {

        Map<String, Object> params = new HashMap<>();
        params.put("casCustomerID", casCustomerID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" c.CUSTOMER_NAME \n");
        SQL.append(" FROM \n");
        SQL.append(" t_customer c \n");
        SQL.append(" INNER JOIN T_CAS_CUSTOMER cc \n");
        SQL.append(" ON c.CUSTOMER_ID = cc.CUSTOMER_ID \n");
        SQL.append(" WHERE \n");
        SQL.append(" cc.CAS_CUSTOMER_ID =:casCustomerID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public Integer getCustomerIDByCASCustomerID(Integer casCustomerID) {

        Map<String, Object> params = new HashMap<>();
        params.put("casCustomerID", casCustomerID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" cc.CUSTOMER_ID \n");
        SQL.append(" FROM T_CAS_CUSTOMER cc \n");
        SQL.append(" WHERE \n");
        SQL.append(" cc.CAS_CUSTOMER_ID =:casCustomerID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, Integer.class);
    }

    public String getUpcSectionNameBySectionID(Integer upcSectionID) {

        Map<String, Object> params = new HashMap<>();
        params.put("upcSectionID", upcSectionID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" us.UPC_SECTION_NAME \n");
        SQL.append(" FROM t_upc_section us \n");
        SQL.append(" WHERE \n");
        SQL.append(" us.UPC_SECTION_ID =:upcSectionID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getLeadRefNumberByLeadID(Integer leadID) {

        Map<String, Object> params = new HashMap<>();
        params.put("leadID", leadID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" l.LEAD_REF_NUMBER \n");
        SQL.append(" FROM t_lead l \n");
        SQL.append(" WHERE \n");
        SQL.append(" l.LEAD_ID =:leadID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getFacilityPaperRefNumberByFpID(Integer facilityPaperID) {

        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" fp.FP_REF_NUMBER \n");
        SQL.append(" FROM t_facility_paper fp \n");
        SQL.append(" WHERE \n");
        SQL.append(" fp.FACILITY_PAPER_ID =:facilityPaperID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getFacilityRefNumberByFacilityID(Integer facilityID) {

        Map<String, Object> params = new HashMap<>();
        params.put("facilityID", facilityID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" f.FACILITY_REF_CODE \n");
        SQL.append(" FROM T_FACILITY f \n");
        SQL.append(" WHERE \n");
        SQL.append(" f.FACILITY_ID =:facilityID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getDocumentNameByDocumentID(Integer docStorageID) {

        Map<String, Object> params = new HashMap<>();
        params.put("docStorageID", docStorageID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" ds.FILE_NAME \n");
        SQL.append(" FROM T_DOCUMENT_STORAGE ds \n");
        SQL.append(" WHERE \n");
        SQL.append(" ds.DOCUMENT_STORAGE_ID =:docStorageID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getCftInterestRateNameByID(Integer CftInterestRateID) {

        Map<String, Object> params = new HashMap<>();
        params.put("CftInterestRateID", CftInterestRateID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" cftir.RATE_NAME \n");
        SQL.append(" FROM T_CFT_INTEREST_RATE cftir \n");
        SQL.append(" WHERE \n");
        SQL.append(" cftir.CFT_INTEREST_RATE_ID =:CftInterestRateID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getCreditFacilityNameByID(Integer creditFacilityTemplateID) {

        Map<String, Object> params = new HashMap<>();
        params.put("creditFacilityTemplateID", creditFacilityTemplateID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" cft.CREDIT_FACILITY_NAME \n");
        SQL.append(" FROM T_CREDIT_FACILITY_TEMPLATE cft \n");
        SQL.append(" WHERE \n");
        SQL.append(" cft.CREDIT_FACILITY_TEMPLATE_ID =:creditFacilityTemplateID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

    public String getWorkFlowTemplateNameByID(Integer WorkFlowTemplateID) {

        Map<String, Object> params = new HashMap<>();
        params.put("WorkFlowTemplateID", WorkFlowTemplateID.toString());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT \n");
        SQL.append(" wft.WORK_FLOW_TEMPLATE_NAME \n");
        SQL.append(" FROM T_WORK_FLOW_TEMPLATE wft \n");
        SQL.append(" WHERE \n");
        SQL.append(" wft.WORK_FLOW_TEMPLATE_ID =:WorkFlowTemplateID \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), params, String.class);
    }

}
