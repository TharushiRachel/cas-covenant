package com.itechro.cas.dao.casmaster.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.casmaster.CACommittee;
import com.itechro.cas.model.domain.casmaster.CommitteeType;
import com.itechro.cas.model.dto.casmaster.*;
import com.itechro.cas.model.dto.facility.PurposeOfAdvancedDTO;
import com.itechro.cas.model.dto.facility.SectorDTO;
import com.itechro.cas.model.dto.facility.SubSectorDTO;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummarySearchRQ;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPUpmGroupForSearch;
import com.itechro.cas.model.dto.master.UpcSectionSearchRQ;
import com.itechro.cas.model.dto.master.UserDaSearchRQ;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.QueryInBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class MasterDataJdbcDao extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDataJdbcDao.class);

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public Page<SupportingDocDTO> getPagedSupportingDocDTO(SupportingDocSearchRQ searchRQ) {


        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("sd.SUPPORTING_DOC_ID, \n");
        SQL.append("sd.DOCUMENT_NAME, \n");
        SQL.append("sd.DESCRIPTION, \n");
        SQL.append("sd.SUPPORT_DOCUMENT_TYPE, \n");
        SQL.append("sd.STATUS, \n");
        SQL.append("sd.APPROVE_STATUS, \n");
        SQL.append("sd.APPROVED_BY, \n");
        SQL.append("sd.APPROVED_DATE, \n");
        SQL.append("sd.CREATED_DATE,       \n");
        SQL.append("sd.CREATED_BY,          \n");
        SQL.append("sd.MODIFIED_DATE,       \n");
        SQL.append("sd.MODIFIED_BY          \n");
        SQL.append("FROM t_supporting_doc sd \n");
        SQL.append("WHERE sd.SUPPORTING_DOC_ID IS NOT NULL \n");

        if (searchRQ.getSupportingDocID() != null) {
            SQL.append("AND sd.SUPPORTING_DOC_ID =:supportingDocID \n");
            params.put("supportingDocID", searchRQ.getSupportingDocID());
        }

        if (StringUtils.isNotEmpty(searchRQ.getDocumentName())) {
            SQL.append("AND upper(sd.DOCUMENT_NAME) LIKE '%" + searchRQ.getDocumentName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getDescription())) {
            SQL.append("AND upper(sd.DESCRIPTION) LIKE '%" + searchRQ.getDescription().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getSupportDocumentType())) {
            SQL.append("AND upper(sd.SUPPORT_DOCUMENT_TYPE) LIKE '%" + searchRQ.getSupportDocumentType().toUpperCase() + "%' \n");
        }

        if (searchRQ.getApproveStatus() != null) {
            SQL.append("AND sd.APPROVE_STATUS =:approveStatus \n");
            params.put("approveStatus", searchRQ.getApproveStatus().toString());
        }

        if (searchRQ.getStatus() != null) {
            SQL.append("AND sd.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }
        if (StringUtils.isNotEmpty(searchRQ.getApprovedDateStr())) {
            SQL.append("AND upper(sd.APPROVED_DATE) LIKE '%" + searchRQ.getApprovedDateStr().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getApprovedBy())) {
            SQL.append("AND upper(ftuba.APPROVED_BY) LIKE '%" + searchRQ.getApprovedBy().toUpperCase() + "%' \n");
        }

        SQL.append("ORDER BY sd.CREATED_DATE DESC \n");

        return getPagedData(SQL.toString(), params, new RowMapper<SupportingDocDTO>() {

            @Nullable
            @Override
            public SupportingDocDTO mapRow(ResultSet rs, int i) throws SQLException {
                SupportingDocDTO supportingDocDTO = new SupportingDocDTO();
                supportingDocDTO.setSupportingDocID(rs.getInt("SUPPORTING_DOC_ID"));
                supportingDocDTO.setDocumentName(rs.getString("DOCUMENT_NAME"));
                supportingDocDTO.setDescription(rs.getString("DESCRIPTION"));
                supportingDocDTO.setSupportDocumentType(rs.getString("SUPPORT_DOCUMENT_TYPE"));
                supportingDocDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                supportingDocDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                supportingDocDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    supportingDocDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    supportingDocDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }
                supportingDocDTO.setCreatedBy(rs.getString("CREATED_BY"));
                if (rs.getTimestamp("MODIFIED_DATE") != null) {
                    supportingDocDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("MODIFIED_DATE")));
                }
                supportingDocDTO.setModifiedBy(rs.getString("MODIFIED_BY"));

                return supportingDocDTO;
            }
        }, searchRQ);
    }

    public Page<CreditFacilityTypeDTO> getPagedCreditFacilityTypeDTO(CreditFacilityTypeSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("ft.CREDIT_FACILITY_TYPE_ID, \n");
        SQL.append("ft.FACILITY_TYPE_NAME, \n");
        SQL.append("ft.DESCRIPTION, \n");
        SQL.append("ft.STATUS, \n");
        SQL.append("ft.APPROVE_STATUS, \n");
        SQL.append("ft.APPROVED_DATE,       \n");
        SQL.append("ft.APPROVED_BY,          \n");
        SQL.append("ft.CREATED_DATE,       \n");
        SQL.append("ft.CREATED_BY,          \n");
        SQL.append("ft.MODIFIED_DATE,       \n");
        SQL.append("ft.MODIFIED_BY          \n");
        SQL.append("FROM t_credit_facility_type ft \n");
        SQL.append("WHERE ft.CREDIT_FACILITY_TYPE_ID IS NOT NULL \n");

        if (searchRQ.getCreditFacilityTypeID() != null) {
            SQL.append("AND sd.SUPPORTING_DOC_ID =:creditFacilityTypeID \n");
            params.put("creditFacilityTypeID", searchRQ.getCreditFacilityTypeID());
        }

        if (StringUtils.isNotEmpty(searchRQ.getFacilityTypeName())) {
            SQL.append("AND upper(ft.FACILITY_TYPE_NAME) LIKE '%" + searchRQ.getFacilityTypeName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getDescription())) {
            SQL.append("AND upper(ft.DESCRIPTION) LIKE '%" + searchRQ.getDescription().toUpperCase() + "%' \n");
        }

        if (searchRQ.getApproveStatus() != null) {
            SQL.append("AND ft.APPROVE_STATUS =:approveStatus \n");
            params.put("approveStatus", searchRQ.getApproveStatus().toString());
        }
        if (searchRQ.getApproveStatusList() != null && !searchRQ.getApproveStatusList().isEmpty()) {
            SQL.append("AND ft.APPROVE_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getApproveStatusList()) + ")\n");
        }

        if (searchRQ.getStatus() != null) {
            SQL.append("AND ft.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }

        if (StringUtils.isNotEmpty(searchRQ.getApprovedDateStr())) {
            SQL.append("AND upper(ft.APPROVED_DATE) LIKE '%" + searchRQ.getApprovedDateStr().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getApprovedBy())) {
            SQL.append("AND upper(ftuba.APPROVED_BY) LIKE '%" + searchRQ.getApprovedBy().toUpperCase() + "%' \n");
        }

        SQL.append("ORDER BY ft.FACILITY_TYPE_NAME \n");

        return getPagedData(SQL.toString(), params, new RowMapper<CreditFacilityTypeDTO>() {

            @Nullable
            @Override
            public CreditFacilityTypeDTO mapRow(ResultSet rs, int i) throws SQLException {
                CreditFacilityTypeDTO creditFacilityTypeDTO = new CreditFacilityTypeDTO();
                creditFacilityTypeDTO.setCreditFacilityTypeID(rs.getInt("CREDIT_FACILITY_TYPE_ID"));
                creditFacilityTypeDTO.setFacilityTypeName(rs.getString("FACILITY_TYPE_NAME"));
                creditFacilityTypeDTO.setDescription(rs.getString("DESCRIPTION"));
                creditFacilityTypeDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                creditFacilityTypeDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                creditFacilityTypeDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    creditFacilityTypeDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    creditFacilityTypeDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }
                creditFacilityTypeDTO.setCreatedBy(rs.getString("CREATED_BY"));
                if (rs.getTimestamp("MODIFIED_DATE") != null) {
                    creditFacilityTypeDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("MODIFIED_DATE")));
                }
                creditFacilityTypeDTO.setModifiedBy(rs.getString("MODIFIED_BY"));

                return creditFacilityTypeDTO;
            }
        }, searchRQ);
    }

    public Page<CftInterestRateDTO> getPagedCftInterestRateDTO(CftInterestRateSearchRQ searchRQ) {


        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("ir.CFT_INTEREST_RATE_ID, \n");
        SQL.append("ir.CREDIT_FACILITY_TEMPLATE_ID, \n");
        SQL.append("ir.RATE_NAME, \n");
        SQL.append("ir.RATE_CODE, \n");
        SQL.append("ir.VALUE, \n");
        SQL.append("ir.STATUS, \n");
        SQL.append("ir.IS_DEFAULT, \n");
        SQL.append("ir.APPROVE_STATUS \n");
        SQL.append("FROM T_CFT_INTEREST_RATE ir \n");
        SQL.append("WHERE ir.CFT_INTEREST_RATE_ID IS NOT NULL \n");

        if (searchRQ.getCftInterestRateID() != null) {
            SQL.append("AND ir.CFT_INTEREST_RATE_ID =:cftInterestRateID \n");
            params.put("cftInterestRateID", searchRQ.getCftInterestRateID());
        }

        if (searchRQ.getCreditFacilityTemplateID() != null) {
            SQL.append("AND ir.CREDIT_FACILITY_TEMPLATE_ID =:creditFacilityTemplateID \n");
            params.put("creditFacilityTemplateID", searchRQ.getCreditFacilityTemplateID());
        }

        if (StringUtils.isNotEmpty(searchRQ.getRateName())) {
            SQL.append("AND upper(ir.RATE_NAME) LIKE '%" + searchRQ.getRateName().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getRateCode())) {
            SQL.append("AND upper(ir.RATE_CODE) LIKE '%" + searchRQ.getRateCode().toUpperCase() + "%' \n");
        }

        if (searchRQ.getApproveStatus() != null) {
            SQL.append("AND ir.APPROVE_STATUS =:approveStatus \n");
            params.put("approveStatus", searchRQ.getApproveStatus().toString());
        }

        if (searchRQ.getStatus() != null) {
            SQL.append("AND ir.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }

        SQL.append("ORDER BY sd.RATE_NAME DESC \n");

        return getPagedData(SQL.toString(), params, new RowMapper<CftInterestRateDTO>() {

            @Nullable
            @Override
            public CftInterestRateDTO mapRow(ResultSet rs, int i) throws SQLException {
                CftInterestRateDTO cftInterestRateDTO = new CftInterestRateDTO();
                cftInterestRateDTO.setCftInterestRateID(rs.getInt("SUPPORTING_DOC_ID"));
                cftInterestRateDTO.setCreditFacilityTemplateID(rs.getInt("CREDIT_FACILITY_TEMPLATE_ID"));
                cftInterestRateDTO.setRateName(rs.getString("RATE_NAME"));
                cftInterestRateDTO.setRateCode(rs.getString("RATE_CODE"));
                cftInterestRateDTO.setValue(rs.getDouble("VALUE"));
                cftInterestRateDTO.setIsDefault(AppsConstants.YesNo.resolver(rs.getString("IS_DEFAULT")));
                cftInterestRateDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                cftInterestRateDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));

                return cftInterestRateDTO;
            }
        }, searchRQ);
    }

    public Page<WorkFlowTemplateDTO> getPagedWorkFlowTemplateDTO(WorkFlowTemplateSearchRQ searchRQ) {


        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("wft.WORK_FLOW_TEMPLATE_ID, \n");
        SQL.append("wft.WORK_FLOW_TEMPLATE_NAME, \n");
        SQL.append("wft.CODE, \n");
        SQL.append("wft.DESCRIPTION, \n");
        SQL.append("wft.STATUS, \n");
        SQL.append("wft.APPROVE_STATUS, \n");
        SQL.append("wft.APPROVED_BY, \n");
        SQL.append("wft.APPROVED_DATE, \n");
        SQL.append("wft.CREATED_DATE,       \n");
        SQL.append("wft.CREATED_BY,          \n");
        SQL.append("wft.MODIFIED_DATE,       \n");
        SQL.append("wft.MODIFIED_BY          \n");
        SQL.append("FROM T_WORK_FLOW_TEMPLATE wft \n");
        SQL.append("WHERE wft.WORK_FLOW_TEMPLATE_ID IS NOT NULL \n");

        if (searchRQ.getWorkFlowTemplateID() != null) {
            SQL.append("AND wft.WORK_FLOW_TEMPLATE_ID =:workFlowTemplateID \n");
            params.put("workFlowTemplateID", searchRQ.getWorkFlowTemplateID());
        }
        if (StringUtils.isNotEmpty(searchRQ.getWorkFlowTemplateName())) {
            SQL.append("AND upper(wft.WORK_FLOW_TEMPLATE_NAME) LIKE '%" + searchRQ.getWorkFlowTemplateName().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getCode())) {
            SQL.append("AND upper(wft.CODE) LIKE '%" + searchRQ.getCode().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getDescription())) {
            SQL.append("AND upper(wft.DESCRIPTION) LIKE '%" + searchRQ.getDescription().toUpperCase() + "%' \n");
        }
        if (searchRQ.getApproveStatusList() != null && !searchRQ.getApproveStatusList().isEmpty()) {
            SQL.append("AND wft.APPROVE_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getApproveStatusList()) + ") \n");
        }
        if (searchRQ.getStatus() != null) {
            SQL.append("AND wft.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }

        SQL.append("ORDER BY wft.WORK_FLOW_TEMPLATE_NAME DESC \n");

        return getPagedData(SQL.toString(), params, new RowMapper<WorkFlowTemplateDTO>() {

            @Nullable
            @Override
            public WorkFlowTemplateDTO mapRow(ResultSet rs, int i) throws SQLException {
                WorkFlowTemplateDTO workFlowTemplateDTO = new WorkFlowTemplateDTO();
                workFlowTemplateDTO.setWorkFlowTemplateID(rs.getInt("WORK_FLOW_TEMPLATE_ID"));
                workFlowTemplateDTO.setWorkFlowTemplateName(rs.getString("WORK_FLOW_TEMPLATE_NAME"));
                workFlowTemplateDTO.setCode(rs.getString("CODE"));
                workFlowTemplateDTO.setDescription(rs.getString("DESCRIPTION"));
                workFlowTemplateDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                workFlowTemplateDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                workFlowTemplateDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    workFlowTemplateDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                workFlowTemplateDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    workFlowTemplateDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }
                workFlowTemplateDTO.setCreatedBy(rs.getString("CREATED_BY"));
                if (rs.getTimestamp("MODIFIED_DATE") != null) {
                    workFlowTemplateDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("MODIFIED_DATE")));
                }
                workFlowTemplateDTO.setModifiedBy(rs.getString("MODIFIED_BY"));

                return workFlowTemplateDTO;
            }
        }, searchRQ);
    }

    public Page<UpmGroupDTO> getPagedUpmGroupDTO(UpmGroupSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("ug.UPM_GROUP_ID, \n");
        SQL.append("ug.GROUP_CODE, \n");
        SQL.append("ug.REFERENCE_NAME, \n");
        SQL.append("ug.WORK_FLOW_LEVEL, \n");
        SQL.append("ug.STATUS, \n");
        SQL.append("ug.APPROVE_STATUS, \n");
        SQL.append("ug.APPROVED_BY, \n");
        SQL.append("ug.APPROVED_DATE, \n");
        SQL.append("ug.CREATED_DATE,       \n");
        SQL.append("ug.CREATED_BY,          \n");
        SQL.append("ug.MODIFIED_DATE,       \n");
        SQL.append("ug.MODIFIED_BY          \n");
        SQL.append("FROM T_UPM_GROUP ug \n");
        SQL.append("WHERE ug.UPM_GROUP_ID IS NOT NULL \n");

        if (searchRQ.getUpmGroupID() != null) {
            SQL.append("AND ug.UPM_GROUP_ID =:upmGroupID \n");
            params.put("upmGroupID", searchRQ.getUpmGroupID());
        }

        if (StringUtils.isNotEmpty(searchRQ.getGroupCode())) {
            SQL.append("AND upper(ug.GROUP_CODE) LIKE '%" + searchRQ.getGroupCode().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getReferenceName())) {
            SQL.append("AND upper(ug.REFERENCE_NAME) LIKE '%" + searchRQ.getReferenceName().toUpperCase() + "%' \n");
        }

        if (searchRQ.getWorkFlowLevel() != null) {
            SQL.append("AND ug.WORK_FLOW_LEVEL =:workFlowLevel \n");
            params.put("workFlowLevel", searchRQ.getWorkFlowLevel());
        }

        if (searchRQ.getApproveStatusList() != null && !searchRQ.getApproveStatusList().isEmpty()) {
            SQL.append("AND ug.APPROVE_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getApproveStatusList()) + ") \n");
        }

        if (searchRQ.getStatus() != null) {
            SQL.append("AND ug.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().name());
        }

        SQL.append("ORDER BY ug.GROUP_CODE, ug.REFERENCE_NAME \n");

        return getPagedData(SQL.toString(), params, new RowMapper<UpmGroupDTO>() {

            @Nullable
            @Override
            public UpmGroupDTO mapRow(ResultSet rs, int i) throws SQLException {
                UpmGroupDTO upmGroupDTO = new UpmGroupDTO();
                upmGroupDTO.setUpmGroupID(rs.getInt("UPM_GROUP_ID"));
                upmGroupDTO.setGroupCode(rs.getString("GROUP_CODE"));
                upmGroupDTO.setReferenceName(rs.getString("REFERENCE_NAME"));
                upmGroupDTO.setWorkFlowLevel(rs.getInt("WORK_FLOW_LEVEL"));
                upmGroupDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                upmGroupDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                upmGroupDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    upmGroupDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                upmGroupDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    upmGroupDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }
                upmGroupDTO.setCreatedBy(rs.getString("CREATED_BY"));
                if (rs.getTimestamp("MODIFIED_DATE") != null) {
                    upmGroupDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("MODIFIED_DATE")));
                }
                upmGroupDTO.setModifiedBy(rs.getString("MODIFIED_BY"));

                return upmGroupDTO;
            }
        }, searchRQ);
    }

    public List<PurposeOfAdvancedDTO> getAllPurposeOfAdvanced() {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                        \n");
        SQL.append("   poa.PURPOSE_OF_ADVANCE_ID,  \n");
        SQL.append("   poa.REF_CODE,               \n");
        SQL.append("   poa.REF_DESC                \n");
        SQL.append(" FROM T_PURPOSE_OF_ADVANCE poa \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<PurposeOfAdvancedDTO>() {

            @Nullable
            @Override
            public PurposeOfAdvancedDTO mapRow(ResultSet rs, int i) throws SQLException {
                PurposeOfAdvancedDTO purposeOfAdvancedDTO = new PurposeOfAdvancedDTO();
                purposeOfAdvancedDTO.setPurposeOfAdvanceID(rs.getInt("PURPOSE_OF_ADVANCE_ID"));
                purposeOfAdvancedDTO.setReferenceCode(rs.getString("REF_CODE"));
                purposeOfAdvancedDTO.setReferenceDescription(rs.getString("REF_DESC"));

                return purposeOfAdvancedDTO;
            }
        });
    }

    public List<SectorDTO> getAllSectorData() {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("  s.SECTOR_ID, \n");
        SQL.append("  s.REF_CODE, \n");
        SQL.append("  s.REF_DESC \n");
        SQL.append("FROM T_SECTOR s \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<SectorDTO>() {

            @Nullable
            @Override
            public SectorDTO mapRow(ResultSet rs, int i) throws SQLException {
                SectorDTO sectorDTO = new SectorDTO();
                sectorDTO.setSectorID(rs.getInt("SECTOR_ID"));
                sectorDTO.setReferenceCode(rs.getString("REF_CODE"));
                sectorDTO.setReferenceDescription(rs.getString("REF_DESC"));
                return sectorDTO;
            }
        });
    }


    public List<UpcSectionDTO> getAllUpcSectionData() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                              \n");
        SQL.append("   us.UPC_SECTION_ID,                \n");
        SQL.append("   us.UPC_SECTION_NAME,              \n");
        SQL.append("   us.STATUS,                        \n");
        SQL.append("   us.MODIFIED_BY,                   \n");
        SQL.append("   us.MODIFIED_DATE,                 \n");
        SQL.append("   us.UPC_SECTION_DESCRIPTION        \n");
        SQL.append(" FROM T_UPC_SECTION us               \n");
        SQL.append(" WHERE us.APPROVE_STATUS = 'APPROVED'\n");
        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<UpcSectionDTO>() {

            @Nullable
            @Override
            public UpcSectionDTO mapRow(ResultSet rs, int i) throws SQLException {
                UpcSectionDTO upcSectionDTO = new UpcSectionDTO();
                upcSectionDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                upcSectionDTO.setUpcSectionID(rs.getInt("UPC_SECTION_ID"));
                upcSectionDTO.setUpcSectionName(rs.getString("UPC_SECTION_NAME"));
                upcSectionDTO.setUpcSectionDescription(rs.getString("UPC_SECTION_DESCRIPTION"));
                upcSectionDTO.setModifiedBy(rs.getString("MODIFIED_BY"));
                upcSectionDTO.setModifiedDateStr(rs.getString("MODIFIED_DATE"));
                return upcSectionDTO;
            }
        });
    }

    public List<WorkFlowTemplateDTO> getAllWorkFlowTemplates() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                            \n");
        SQL.append("   wt.WORK_FLOW_TEMPLATE_ID,       \n");
        SQL.append("   wt.WORK_FLOW_TEMPLATE_NAME,     \n");
        SQL.append("   wt.CODE,                        \n");
        SQL.append("   wt.STATUS                       \n");
        SQL.append(" FROM T_WORK_FLOW_TEMPLATE wt      \n");
        SQL.append(" WHERE APPROVE_STATUS = 'APPROVED' \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<WorkFlowTemplateDTO>() {

            @Nullable
            @Override
            public WorkFlowTemplateDTO mapRow(ResultSet rs, int i) throws SQLException {
                WorkFlowTemplateDTO workFlowTemplateDTO = new WorkFlowTemplateDTO();
                workFlowTemplateDTO.setWorkFlowTemplateID(rs.getInt("WORK_FLOW_TEMPLATE_ID"));
                workFlowTemplateDTO.setWorkFlowTemplateName(rs.getString("WORK_FLOW_TEMPLATE_NAME"));
                workFlowTemplateDTO.setCode(rs.getString("CODE"));
                workFlowTemplateDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                return workFlowTemplateDTO;
            }
        });
    }

    public SubSectorDataRS getAllSubSectorData() {
        SubSectorDataRS result = new SubSectorDataRS();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                 \n");
        SQL.append("   sub.SUB_SECTOR_ID,   \n");
        SQL.append("   sub.SECTRO_ID,       \n");
        SQL.append("   sub.REF_DESC,        \n");
        SQL.append("   sub.LONG_REF_CODE    \n");
        SQL.append(" FROM T_SUB_SECTOR sub  \n");

        this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<SubSectorDTO>>() {
            @Override
            public List<SubSectorDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    Integer sectorID = rs.getInt("SECTRO_ID");
                    SubSectorDTO subSectorDTO = new SubSectorDTO();
                    subSectorDTO.setSubSectorID(rs.getInt("SUB_SECTOR_ID"));
                    subSectorDTO.setSectorID(sectorID);
                    subSectorDTO.setLongRefCode(rs.getString("LONG_REF_CODE"));
                    subSectorDTO.setReferenceDescription(rs.getString("REF_DESC"));

                    result.getSubSectorDTOList().add(subSectorDTO);
                    result.getSectorWiseSubSectorMap().putIfAbsent(sectorID, new ArrayList<>());
                    result.getSectorWiseSubSectorMap().get(sectorID).add(subSectorDTO);
                }
                return result.getSubSectorDTOList();
            }
        });
        return result;
    }

    public Long getPendingSupportDocCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(sd.SUPPORTING_DOC_ID) pendingDocCount \n");
        SQL.append(" FROM T_SUPPORTING_DOC sd                           \n");
        SQL.append(" WHERE sd.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND sd.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND sd.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getPendingFacilityTypeCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(ft.CREDIT_FACILITY_TYPE_ID) pendingDocCount \n");
        SQL.append(" FROM t_credit_facility_type ft                           \n");
        SQL.append(" WHERE ft.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND ft.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND ft.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getPendingFacilityTemplateCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(ft.CREDIT_FACILITY_TEMPLATE_ID) pendingDocCount \n");
        SQL.append(" FROM t_credit_facility_template ft                           \n");
        SQL.append(" WHERE ft.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND ft.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND ft.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getPendingUpcSectionCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(us.UPC_SECTION_ID) pendingUpcSectionCount \n");
        SQL.append(" FROM T_UPC_SECTION us                           \n");
        SQL.append(" WHERE us.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND us.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND us.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getPendingUserDaCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(ud.USER_DA_ID) pendingUserDaCount \n");
        SQL.append(" FROM T_USER_DA ud                           \n");
        SQL.append(" WHERE ud.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND ud.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND ud.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getPendingUpmGroupCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(ug.UPM_GROUP_ID) pendingDocCount \n");
        SQL.append(" FROM T_UPM_GROUP ug                           \n");
        SQL.append(" WHERE ug.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND ug.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND ug.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getPendingWorkFlowTemplateCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(wft.WORK_FLOW_TEMPLATE_ID) pendingDocCount \n");
        SQL.append(" FROM T_WORK_FLOW_TEMPLATE wft                      \n");
        SQL.append(" WHERE wft.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND wft.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND wft.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Page<CreditFacilityTemplateDTO> getCreditFacilityTemplateDTO(CreditFacilityTemplateSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("tem.CREDIT_FACILITY_TEMPLATE_ID, \n");
        SQL.append("tem.CREDIT_FACILITY_TYPE_ID, \n");
        SQL.append("tem.CREDIT_FACILITY_NAME, \n");
        SQL.append("typ.FACILITY_TYPE_NAME, \n");
        SQL.append("tem.DESCRIPTION, \n");
        SQL.append("tem.MAXIMUM_FACILITY_AMOUNT, \n");
        SQL.append("tem.MINIMUM_FACILITY_AMOUNT, \n");
        SQL.append("tem.STATUS, \n");
        SQL.append("tem.APPROVE_STATUS, \n");
        SQL.append("tem.APPROVED_DATE,       \n");
        SQL.append("tem.APPROVED_BY,          \n");
        SQL.append("tem.CREATED_DATE,       \n");
        SQL.append("tem.CREATED_BY,          \n");
        SQL.append("tem.MODIFIED_DATE,       \n");
        SQL.append("tem.MODIFIED_BY          \n");
        SQL.append("FROM t_credit_facility_template tem \n");
        SQL.append("INNER JOIN t_credit_facility_type typ ON \n");
        SQL.append("tem.CREDIT_FACILITY_TYPE_ID = typ.CREDIT_FACILITY_TYPE_ID \n");
        SQL.append("WHERE tem.CREDIT_FACILITY_TEMPLATE_ID IS NOT NULL \n");

        if (searchRQ.getCreditFacilityTemplateID() != null) {
            SQL.append("AND tem.CREDIT_FACILITY_TEMPLATE_ID =:creditFacilityTemplateID \n");
            params.put("creditFacilityTemplateID", searchRQ.getCreditFacilityTemplateID());
        }
        if (searchRQ.getCreditFacilityTypeID() != null) {
            SQL.append("AND tem.CREDIT_FACILITY_TYPE_ID =:creditFacilityTypeID \n");
            params.put("creditFacilityTypeID", searchRQ.getCreditFacilityTypeID());
        }
        if (StringUtils.isNotEmpty(searchRQ.getCreditFacilityName())) {
            SQL.append("AND upper(tem.CREDIT_FACILITY_NAME) LIKE '%" + searchRQ.getCreditFacilityName().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getFacilityTypeName())) {
            SQL.append("AND upper(typ.FACILITY_TYPE_NAME) LIKE '%" + searchRQ.getFacilityTypeName().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getDescription())) {
            SQL.append("AND upper(tem.DESCRIPTION) LIKE '%" + searchRQ.getDescription().toUpperCase() + "%' \n");
        }
        if (searchRQ.getMaxFacilityAmount() != null) {
            SQL.append("AND tem.MAXIMUM_FACILITY_AMOUNT =:maxFacilityAmount \n");
            params.put("maxFacilityAmount", searchRQ.getMaxFacilityAmount());
        }
        if (searchRQ.getMinFacilityAmount() != null) {
            SQL.append("AND tem.MINIMUM_FACILITY_AMOUNT =:minFacilityAmount \n");
            params.put("minFacilityAmount", searchRQ.getMinFacilityAmount());
        }
        if (searchRQ.getStatus() != null) {
            SQL.append("AND tem.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }
        if (searchRQ.getApproveStatus() != null) {
            SQL.append("AND tem.APPROVE_STATUS =:approveStatus \n");
            params.put("approveStatus", searchRQ.getApproveStatus().toString());
        }
        if (searchRQ.getApproveStatusList() != null && !searchRQ.getApproveStatusList().isEmpty()) {
            SQL.append("AND tem.APPROVE_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getApproveStatusList()) + ") \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getApprovedDateStr())) {
            SQL.append("AND upper(tem.APPROVED_DATE) LIKE '%" + searchRQ.getApprovedDateStr().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(searchRQ.getApprovedBy())) {
            SQL.append("AND upper(tem.APPROVED_BY) LIKE '%" + searchRQ.getApprovedBy().toUpperCase() + "%' \n");
        }


        return getPagedData(SQL.toString(), params, new RowMapper<CreditFacilityTemplateDTO>() {

            @Nullable
            @Override
            public CreditFacilityTemplateDTO mapRow(ResultSet rs, int i) throws SQLException {
                CreditFacilityTemplateDTO creditFacilityTemplateDTO = new CreditFacilityTemplateDTO();
                creditFacilityTemplateDTO.setCreditFacilityTemplateID(rs.getInt("CREDIT_FACILITY_TEMPLATE_ID"));
                creditFacilityTemplateDTO.setCreditFacilityTypeID(rs.getInt("CREDIT_FACILITY_TYPE_ID"));
                creditFacilityTemplateDTO.setCreditFacilityName(rs.getString("CREDIT_FACILITY_NAME"));
                creditFacilityTemplateDTO.setFacilityTypeName(rs.getString("FACILITY_TYPE_NAME"));
                creditFacilityTemplateDTO.setDescription(rs.getString("DESCRIPTION"));
                creditFacilityTemplateDTO.setMaxFacilityAmount(rs.getBigDecimal("MAXIMUM_FACILITY_AMOUNT"));
                creditFacilityTemplateDTO.setMinFacilityAmount(rs.getBigDecimal("MINIMUM_FACILITY_AMOUNT"));
                creditFacilityTemplateDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                creditFacilityTemplateDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                creditFacilityTemplateDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    creditFacilityTemplateDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    creditFacilityTemplateDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }
                creditFacilityTemplateDTO.setCreatedBy(rs.getString("CREATED_BY"));
                if (rs.getTimestamp("MODIFIED_DATE") != null) {
                    creditFacilityTemplateDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("MODIFIED_DATE")));
                }
                creditFacilityTemplateDTO.setModifiedBy(rs.getString("MODIFIED_BY"));


                return creditFacilityTemplateDTO;
            }
        }, searchRQ);
    }

    public Page<UserDaDTO> getPagedUserDaDTO(UserDaSearchRQ userDaSearchRQ) {

        StringBuilder SQL = new StringBuilder();
        HashMap<String, Object> params = new HashMap<>();

        SQL.append(" SELECT ud.USER_DA_ID,          \n");
        SQL.append("        ud.USER_NAME,           \n");
        SQL.append("        ud.MAX_AMOUNT,          \n");
        SQL.append("        ud.CLEAN_AMOUNT,        \n");
        SQL.append("        ud.DESCRIPTION,         \n");
        SQL.append("        ud.STATUS,              \n");
        SQL.append("        ud.APPROVE_STATUS,      \n");
        SQL.append("        ud.APPROVED_DATE,       \n");
        SQL.append("        ud.APPROVED_BY,          \n");
        SQL.append("        ud.CREATED_DATE,       \n");
        SQL.append("        ud.CREATED_BY,          \n");
        SQL.append("        ud.MODIFIED_DATE,       \n");
        SQL.append("        ud.MODIFIED_BY          \n");
        SQL.append(" FROM T_USER_DA ud              \n");
        SQL.append(" WHERE ud.USER_DA_ID IS NOT NULL\n");

        if (userDaSearchRQ.getUserDaID() != null) {
            SQL.append(" AND ud.USER_DA_ID = :userDaID       \n");
            params.put("userDaID", userDaSearchRQ.getUserDaID());
        }
        if (StringUtils.isNotEmpty(userDaSearchRQ.getUserName())) {
            SQL.append("AND upper(ud.USER_NAME) LIKE '%" + userDaSearchRQ.getUserName().toUpperCase() + "%' \n");
        }
        if (userDaSearchRQ.getMaxAmount() != null) {
            SQL.append(" AND ud.MAX_AMOUNT = :maxAmount       \n");
            params.put("maxAmount", userDaSearchRQ.getMaxAmount());
        }
        if (StringUtils.isNotEmpty(userDaSearchRQ.getDescription())) {
            SQL.append("AND upper(ud.DESCRIPTION) LIKE '%" + userDaSearchRQ.getDescription().toUpperCase() + "%' \n");
        }
        if (userDaSearchRQ.getStatus() != null) {
            SQL.append(" AND ud.STATUS = :status              \n");
            params.put("status", userDaSearchRQ.getStatus().name());
        }
        if (userDaSearchRQ.getApproveStatus() != null) {
            SQL.append(" AND ud.APPROVE_STATUS = :approveStatus              \n");
            params.put("approveStatus", userDaSearchRQ.getApproveStatus().name());
        }
        if (userDaSearchRQ.getApproveStatusList() != null && !userDaSearchRQ.getApproveStatusList().isEmpty()) {
            SQL.append(" AND ud.APPROVE_STATUS IN(" + QueryInBuilder.buildSQLINQuery(userDaSearchRQ.getApproveStatusList()) + ") \n");
        }
        if (StringUtils.isNotEmpty(userDaSearchRQ.getApprovedDateStr())) {
            SQL.append("AND upper(ud.APPROVED_DATE) LIKE '%" + userDaSearchRQ.getApprovedDateStr().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(userDaSearchRQ.getApprovedBy())) {
            SQL.append("AND upper(ud.APPROVED_BY) LIKE '%" + userDaSearchRQ.getApprovedBy().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(userDaSearchRQ.getCreatedDateStr())) {
            SQL.append("AND upper(ud.CREATED_DATE) LIKE '%" + userDaSearchRQ.getCreatedDateStr().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(userDaSearchRQ.getCreatedBy())) {
            SQL.append("AND upper(ud.CREATED_BY) LIKE '%" + userDaSearchRQ.getCreatedBy().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(userDaSearchRQ.getModifiedDateStr())) {
            SQL.append("AND upper(ud.MODIFIED_DATE) LIKE '%" + userDaSearchRQ.getModifiedDateStr().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(userDaSearchRQ.getModifiedBy())) {
            SQL.append("AND upper(ud.MODIFIED_BY) LIKE '%" + userDaSearchRQ.getModifiedBy().toUpperCase() + "%' \n");
        }

        return getPagedData(SQL.toString(), params, new RowMapper<UserDaDTO>() {
            @Nullable
            @Override
            public UserDaDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                UserDaDTO userDaDTO = new UserDaDTO();
                userDaDTO.setUserDaID(resultSet.getInt("USER_DA_ID"));
                userDaDTO.setUserName(resultSet.getString("USER_NAME"));
                userDaDTO.setMaxAmount(resultSet.getBigDecimal("MAX_AMOUNT"));
                userDaDTO.setCleanAmount(resultSet.getBigDecimal("CLEAN_AMOUNT"));
                userDaDTO.setDescription(resultSet.getString("DESCRIPTION"));
                userDaDTO.setStatus(AppsConstants.Status.resolveStatus(resultSet.getString("STATUS")));
                userDaDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(resultSet.getString("APPROVE_STATUS")));
                if (resultSet.getTimestamp("APPROVED_DATE") != null) {
                    userDaDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(resultSet.getTimestamp("APPROVED_DATE")));
                }
                userDaDTO.setApprovedBy(resultSet.getString("APPROVED_BY"));
                if (resultSet.getTimestamp("CREATED_DATE") != null) {
                    userDaDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(resultSet.getTimestamp("CREATED_DATE")));
                }
                userDaDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
                if (resultSet.getTimestamp("MODIFIED_DATE") != null) {
                    userDaDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(resultSet.getTimestamp("MODIFIED_DATE")));
                }
                userDaDTO.setModifiedBy(resultSet.getString("MODIFIED_BY"));

                return userDaDTO;
            }
        }, userDaSearchRQ);
    }

    public Page<UpcSectionDTO> getPagedUpcSectionDTO(UpcSectionSearchRQ upcSectionSearchRQ) {

        StringBuilder SQL = new StringBuilder();
        HashMap<String, Object> params = new HashMap<>();

        SQL.append("SELECT us.UPC_SECTION_ID,             \n");
        SQL.append("       us.UPC_SECTION_NAME,          \n");
        SQL.append("       us.UPC_SECTION_DESCRIPTION,   \n");
        SQL.append("       us.STATUS,                    \n");
        SQL.append("       us.APPROVE_STATUS,            \n");
        SQL.append("       us.APPROVED_DATE,             \n");
        SQL.append("       us.APPROVED_BY,                \n");
        SQL.append("        us.CREATED_DATE,       \n");
        SQL.append("        us.CREATED_BY,          \n");
        SQL.append("        us.MODIFIED_DATE,       \n");
        SQL.append("        us.MODIFIED_BY          \n");
        SQL.append("FROM T_UPC_SECTION us                 \n");
        SQL.append("WHERE UPC_SECTION_ID IS NOT NULL      \n");

        if (upcSectionSearchRQ.getUpcSectionID() != null) {
            SQL.append(" AND us.UPC_SECTION_ID = :upcSectionID      \n");
            params.put("userDaID", upcSectionSearchRQ.getUpcSectionID());
        }
        if (StringUtils.isNotEmpty(upcSectionSearchRQ.getUpcSectionName())) {
            SQL.append("AND upper(us.UPC_SECTION_NAME) LIKE '%" + upcSectionSearchRQ.getUpcSectionName().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(upcSectionSearchRQ.getUpcSectionDescription())) {
            SQL.append("AND upper(us.UPC_SECTION_DESCRIPTION) LIKE '%" + upcSectionSearchRQ.getUpcSectionDescription().toUpperCase() + "%' \n");
        }
        if (upcSectionSearchRQ.getStatus() != null) {
            SQL.append(" AND us.STATUS = :status      \n");
            params.put("status", upcSectionSearchRQ.getStatus().name());
        }

        if (upcSectionSearchRQ.getApproveStatusList() != null && !upcSectionSearchRQ.getApproveStatusList().isEmpty()) {
            SQL.append(" AND us.APPROVE_STATUS IN (" + QueryInBuilder.buildSQLINQuery(upcSectionSearchRQ.getApproveStatusList()) + ")\n");
        }

        if (StringUtils.isNotEmpty(upcSectionSearchRQ.getApprovedDateStr())) {
            SQL.append("AND upper(us.APPROVED_DATE) LIKE '%" + upcSectionSearchRQ.getApprovedDateStr().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotEmpty(upcSectionSearchRQ.getApprovedBy())) {
            SQL.append("AND upper(us.APPROVED_BY ) LIKE '%" + upcSectionSearchRQ.getApprovedBy().toUpperCase() + "%' \n");
        }

        return getPagedData(SQL.toString(), params, new RowMapper<UpcSectionDTO>() {
            @Nullable
            @Override
            public UpcSectionDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                UpcSectionDTO upcSectionDTO = new UpcSectionDTO();
                upcSectionDTO.setUpcSectionID(resultSet.getInt("UPC_SECTION_ID"));
                upcSectionDTO.setUpcSectionName(resultSet.getString("UPC_SECTION_NAME"));
                upcSectionDTO.setUpcSectionDescription(resultSet.getString("UPC_SECTION_DESCRIPTION"));
                upcSectionDTO.setStatus(AppsConstants.Status.resolveStatus(resultSet.getString("STATUS")));
                upcSectionDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(resultSet.getString("APPROVE_STATUS")));
                if (resultSet.getTimestamp("APPROVED_DATE") != null) {
                    upcSectionDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(resultSet.getTimestamp("APPROVED_DATE")));
                }
                upcSectionDTO.setApprovedBy(resultSet.getString("APPROVED_BY"));
                if (resultSet.getTimestamp("CREATED_DATE") != null) {
                    upcSectionDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(resultSet.getTimestamp("CREATED_DATE")));
                }
                upcSectionDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
                if (resultSet.getTimestamp("MODIFIED_DATE") != null) {
                    upcSectionDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(resultSet.getTimestamp("MODIFIED_DATE")));
                }
                upcSectionDTO.setModifiedBy(resultSet.getString("MODIFIED_BY"));

                return upcSectionDTO;
            }
        }, upcSectionSearchRQ);
    }

    public Page<UpcTemplateDTO> getPagedUpcTemplateDTO(UpcTemplateSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT \n");
        SQL.append("ut.UPC_TEMPLATE_ID, \n");
        SQL.append("ut.TEMPLATE_NAME, \n");
        SQL.append("ut.DESCRIPTION, \n");
        SQL.append("ut.STATUS, \n");
        SQL.append("       ut.APPROVE_STATUS,            \n");
        SQL.append("       ut.APPROVED_DATE,             \n");
        SQL.append("       ut.APPROVED_BY,                \n");
        SQL.append("        ut.CREATED_DATE,       \n");
        SQL.append("        ut.CREATED_BY,          \n");
        SQL.append("        ut.MODIFIED_DATE,       \n");
        SQL.append("        ut.MODIFIED_BY          \n");
        SQL.append("FROM  T_UPC_TEMPLATE ut \n");
        SQL.append("WHERE ut.UPC_TEMPLATE_ID IS NOT NULL \n");

        if (searchRQ.getUpcTemplateID() != null) {
            SQL.append("AND ut.UPC_TEMPLATE_ID =:upcTemplateID \n");
            params.put("upcTemplateID", searchRQ.getUpcTemplateID());
        }
        if (StringUtils.isNotBlank(searchRQ.getTemplateName())) {
            SQL.append("AND ut.TEMPLATE_NAME LIKE '%" + searchRQ.getTemplateName() + "%' \n");
        }
        if (searchRQ.getApproveStatusList() != null && !searchRQ.getApproveStatusList().isEmpty()) {
            SQL.append("AND ut.APPROVE_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getApproveStatusList()) + ") \n");
        }
        if (searchRQ.getStatus() != null) {
            SQL.append("AND ut.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }
        SQL.append("ORDER BY ut.TEMPLATE_NAME DESC \n");

        return getPagedData(SQL.toString(), params, new RowMapper<UpcTemplateDTO>() {

            @Nullable
            @Override
            public UpcTemplateDTO mapRow(ResultSet rs, int i) throws SQLException {
                UpcTemplateDTO upcTemplateDTO = new UpcTemplateDTO();
                upcTemplateDTO.setUpcTemplateID(rs.getInt("UPC_TEMPLATE_ID"));
                upcTemplateDTO.setTemplateName(rs.getString("TEMPLATE_NAME"));
                upcTemplateDTO.setDescription(rs.getString("DESCRIPTION"));
                upcTemplateDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                upcTemplateDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                upcTemplateDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                if (rs.getTimestamp("APPROVED_DATE") != null) {
                    upcTemplateDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                }
                if (rs.getTimestamp("CREATED_DATE") != null) {
                    upcTemplateDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                }
                upcTemplateDTO.setCreatedBy(rs.getString("CREATED_BY"));
                if (rs.getTimestamp("MODIFIED_DATE") != null) {
                    upcTemplateDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("MODIFIED_DATE")));
                }
                upcTemplateDTO.setModifiedBy(rs.getString("MODIFIED_BY"));
                return upcTemplateDTO;
            }
        }, searchRQ);
    }

    public Long getPendingUpcTemplateCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(ut.UPC_TEMPLATE_ID) pendingUtCount \n");
        SQL.append(" FROM T_UPC_TEMPLATE ut                           \n");
        SQL.append(" WHERE ut.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND ut.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND ut.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateSupportingDocCountByName(String documentName) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("documentName", documentName);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(sd.SUPPORTING_DOC_ID) docCount        \n");
        SQL.append(" FROM T_SUPPORTING_DOC sd                           \n");
        SQL.append(" WHERE sd.STATUS = 'ACT'                            \n");
        SQL.append("       AND sd.DOCUMENT_NAME = :documentName         \n");


        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateCreditFacilityTypeCountByName(String documentName) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("documentName", documentName);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(cft.CREDIT_FACILITY_TYPE_ID) docCount \n");
        SQL.append(" FROM t_credit_facility_type cft                    \n");
        SQL.append(" WHERE cft.STATUS = 'ACT'                           \n");
        SQL.append("       AND cft.FACILITY_TYPE_NAME = :documentName   \n");


        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateUpmGroupCountByCode(String groupCode) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("groupCode", groupCode);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(ug.UPM_GROUP_ID) groupCount       \n");
        SQL.append(" FROM T_UPM_GROUP ug                            \n");
        SQL.append(" WHERE ug.STATUS = 'ACT'                        \n");
        SQL.append("       AND ug.GROUP_CODE = :groupCode           \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateWorkFlowTemplateCountByName(String templateName) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("templateName", templateName);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(wft.WORK_FLOW_TEMPLATE_ID) wftCount       \n");
        SQL.append(" FROM T_WORK_FLOW_TEMPLATE wft                          \n");
        SQL.append(" WHERE wft.STATUS = 'ACT'                               \n");
        SQL.append("       AND wft.WORK_FLOW_TEMPLATE_NAME = :templateName  \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateUpcSectionCountByName(String sectionName) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("sectionName", sectionName);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(us.UPC_SECTION_ID) upcSectionCount    \n");
        SQL.append(" FROM T_UPC_SECTION us                              \n");
        SQL.append(" WHERE us.STATUS = 'ACT'                            \n");
        SQL.append("       AND us.UPC_SECTION_NAME = :sectionName       \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateUpcTemplateCountByName(String templateName) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("templateName", templateName);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(ut.UPC_TEMPLATE_ID) UtCount         \n");
        SQL.append(" FROM T_UPC_TEMPLATE ut                           \n");
        SQL.append(" WHERE ut.STATUS = 'ACT'                          \n");
        SQL.append("       AND ut.TEMPLATE_NAME = :templateName       \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateCftCountByNameAndFacilityType(String creditFacilityName, Integer facilityTypeID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("creditFacilityName", creditFacilityName);
        paramsMap.put("facilityTypeID", facilityTypeID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(cft.CREDIT_FACILITY_TEMPLATE_ID) cftCount         \n");
        SQL.append(" FROM T_CREDIT_FACILITY_TEMPLATE cft                            \n");
        SQL.append(" WHERE cft.STATUS = 'ACT'                                       \n");
        SQL.append("       AND cft.CREDIT_FACILITY_NAME = :creditFacilityName       \n");
        SQL.append("       AND cft.CREDIT_FACILITY_TYPE_ID = :facilityTypeID        \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateCftInterestRateByNameAndCftID(String rateName, Integer creditFacilityTemplateID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("rateName", rateName);
        paramsMap.put("creditFacilityTemplateID", creditFacilityTemplateID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(inr.CFT_INTEREST_RATE_ID) rateCount                       \n");
        SQL.append(" FROM T_CFT_INTEREST_RATE inr                                           \n");
        SQL.append(" WHERE inr.STATUS = 'ACT'                                               \n");
        SQL.append("       AND inr.RATE_NAME = :rateName                                    \n");
        SQL.append("       AND inr.CREDIT_FACILITY_TEMPLATE_ID = :creditFacilityTemplateID  \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getDuplicateOtherFacilityInfoByNameAndCftID(String otherFacilityName, Integer creditFacilityTemplateID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("otherFacilityName", otherFacilityName);
        paramsMap.put("creditFacilityTemplateID", creditFacilityTemplateID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT count(ofi.OTHER_FACILITY_INFO_ID) infoCount                        \n");
        SQL.append("FROM T_CFT_OTHER_FACILITY_INFO ofi                                            \n");
        SQL.append("WHERE ofi.STATUS = 'ACT'                                                  \n");
        SQL.append("      AND ofi.OTHER_FACILITY_INFO_NAME = :otherFacilityName               \n");
        SQL.append("      AND ofi.CREDIT_FACILITY_TEMPLATE_ID = :creditFacilityTemplateID      \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }


    public Long getDuplicateUserDaCountByName(String stringCheckUsername) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userName", stringCheckUsername);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(ud.USER_DA_ID) userCount        \n");
        SQL.append(" FROM T_USER_DA ud                           \n");
        SQL.append(" WHERE ud.STATUS = 'ACT'                            \n");
        SQL.append("       AND LOWER(ud.USER_NAME) = LOWER(:userName)         \n");


        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public List<UpmGroupDTO> getUpmGroupByWorkFlowTemplateID(Integer workFlowTemplteID) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("workFlowTemplteID", workFlowTemplteID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT ug.UPM_GROUP_ID,                                                                         \n");
        SQL.append("      ug.GROUP_CODE,                                                                            \n");
        SQL.append("     ug.REFERENCE_NAME                                                                          \n");
        SQL.append("FROM T_WORK_FLOW_TEMPLATE wt                                                                    \n");
        SQL.append("INNER JOIN T_WORK_FLOW_TEMPLATE_DATA wtd ON wt.WORK_FLOW_TEMPLATE_ID = wtd.WORK_FLOW_TEMPLATE_ID\n");
        SQL.append("INNER JOIN T_UPM_GROUP ug ON wtd.UPM_GROUP_ID  = ug.UPM_GROUP_ID                                \n");
        SQL.append("WHERE wt.WORK_FLOW_TEMPLATE_ID IS NOT NULL                                                      \n");
        SQL.append("AND wt.WORK_FLOW_TEMPLATE_ID = :workFlowTemplteID                                                                         \n");
        SQL.append("ORDER BY DISPLAY_ORDER                                                                          \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), paramsMap, new RowMapper<UpmGroupDTO>() {

            @Nullable
            @Override
            public UpmGroupDTO mapRow(ResultSet rs, int i) throws SQLException {
                UpmGroupDTO upmGroupDTO = new UpmGroupDTO();
                upmGroupDTO.setUpmGroupID(rs.getInt("UPM_GROUP_ID"));
                upmGroupDTO.setGroupCode(rs.getString("GROUP_CODE"));
                upmGroupDTO.setReferenceName(rs.getString("REFERENCE_NAME"));
                return upmGroupDTO;
            }
        });
    }

    public List<UpmGroupDTO> getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(LoggedInUserWorkFlowRQ loggedInUserWorkFlowRQ) {
        Map<String, Object> paramsMap = new HashMap<>();
        Integer workFlowTemplateId;

        if (loggedInUserWorkFlowRQ.getWorkFlowTemplateID() == null){
            workFlowTemplateId = getWorkFlowTemplateIdByUpmGroup(loggedInUserWorkFlowRQ.getLoggedInUserUpmGroupCode());
        }else{
            workFlowTemplateId = loggedInUserWorkFlowRQ.getWorkFlowTemplateID();
        }

       // paramsMap.put("workFlowTemplateID", loggedInUserWorkFlowRQ.getWorkFlowTemplateID());
        paramsMap.put("workFlowTemplateID", workFlowTemplateId);
        paramsMap.put("loggedInUserSolID", loggedInUserWorkFlowRQ.getLoggedInUserSolID());
        paramsMap.put("loggedInUserUpmGroupCode", loggedInUserWorkFlowRQ.getLoggedInUserUpmGroupCode());
//        int[] sampathBankDefinedWorkflowsInt = WorkGroupUtil.getNextForwardGroupLevels(DecimalCalculator.parseInt(loggedInUserWorkFlowRQ.getLoggedInUserUpmGroupCode()));
//        List<Integer> sampathBankDefinedWorkflows = IntStream.of(sampathBankDefinedWorkflowsInt).boxed().collect(Collectors.toCollection(ArrayList::new));
//        LOG.info("START : Sampath Bank Defined Workflow UPM Codes for : {} ==> : {} ", loggedInUserWorkFlowRQ.getLoggedInUserUpmGroupCode(), sampathBankDefinedWorkflows);

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT ug.UPM_GROUP_ID,                                                                         \n");
        SQL.append("      ug.GROUP_CODE,                                                                            \n");
        SQL.append("     ug.REFERENCE_NAME                                                                          \n");
        SQL.append("FROM T_WORK_FLOW_TEMPLATE wt                                                                    \n");
        SQL.append("INNER JOIN T_WORK_FLOW_TEMPLATE_DATA wtd ON wt.WORK_FLOW_TEMPLATE_ID = wtd.WORK_FLOW_TEMPLATE_ID\n");
        SQL.append("INNER JOIN T_UPM_GROUP ug ON wtd.UPM_GROUP_ID  = ug.UPM_GROUP_ID                                \n");
        SQL.append("WHERE wt.WORK_FLOW_TEMPLATE_ID IS NOT NULL                                                      \n");
        SQL.append("AND wt.WORK_FLOW_TEMPLATE_ID = :workFlowTemplateID                                              \n");
//        if (!sampathBankDefinedWorkflows.isEmpty()) {
//            SQL.append("AND ug.GROUP_CODE IN (" + QueryInBuilder.buildSQLINQuery(sampathBankDefinedWorkflows) + ")  \n");
//        }
        SQL.append("ORDER BY DISPLAY_ORDER                                                                          \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), paramsMap, new RowMapper<UpmGroupDTO>() {

            @Nullable
            @Override
            public UpmGroupDTO mapRow(ResultSet rs, int i) throws SQLException {
                UpmGroupDTO upmGroupDTO = new UpmGroupDTO();
//                upmGroupDTO.setUpmGroupID(rs.getInt("UPM_GROUP_ID"));
                upmGroupDTO.setGroupCode(rs.getString("GROUP_CODE"));
                upmGroupDTO.setReferenceName(rs.getString("REFERENCE_NAME"));
                return upmGroupDTO;
            }
        });
    }
    /**
     *
     *  This function will return the  lower and higher  user groups levels from logged UPM Access  level
     */
    public List<UpmGroupDTO> getHighOrLowerUpmGroupByWorkFlowStatusAndLoggedInUserUpmGroupCodeFrom(LoggedInUserWorkFlowByStatusRQ loggedInUserWorkFlowByStatusRQ) {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("loggedInUserUpmGroupCode", loggedInUserWorkFlowByStatusRQ.getLoggedInUserUpmGroupCode());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT DISTINCT ug.GROUP_CODE,                                                                         \n");
        SQL.append("       ug.UPM_GROUP_ID,                                                                            \n");
        SQL.append("     ug.REFERENCE_NAME                                                                          \n");
        SQL.append("FROM T_WORK_FLOW_TEMPLATE wt                                                                    \n");
        SQL.append("INNER JOIN T_WORK_FLOW_TEMPLATE_DATA wtd ON wt.WORK_FLOW_TEMPLATE_ID = wtd.WORK_FLOW_TEMPLATE_ID\n");
        SQL.append("INNER JOIN T_UPM_GROUP ug ON wtd.UPM_GROUP_ID  = ug.UPM_GROUP_ID                                \n");
        SQL.append("WHERE wt.WORK_FLOW_TEMPLATE_ID IS NOT NULL                                                      \n");
        SQL.append("AND wt.STATUS    = 'ACT'                                                                        \n");

        //get forward user level
        if (loggedInUserWorkFlowByStatusRQ.getLevel().equals(LoggedInUserWorkFlowByStatusRQ.Level.HIGH)){
            SQL.append("AND ug.GROUP_CODE >= :loggedInUserUpmGroupCode                                              \n");
        }else{ //get reject user level
            SQL.append("AND ug.GROUP_CODE < :loggedInUserUpmGroupCode                                               \n");
        }

        SQL.append("ORDER BY  ug.GROUP_CODE                                                                          \n");
        return this.namedParameterJdbcTemplate.query(SQL.toString(), paramsMap, new RowMapper<UpmGroupDTO>() {
            @Nullable
            @Override
            public UpmGroupDTO mapRow(ResultSet rs, int i) throws SQLException {
                UpmGroupDTO upmGroupDTO = new UpmGroupDTO();
                upmGroupDTO.setGroupCode(rs.getString("GROUP_CODE"));
                upmGroupDTO.setReferenceName(rs.getString("REFERENCE_NAME"));
                return upmGroupDTO;
            }
        });
    }

    //This function return all the immediate lower upm levels of approved work flow
    public Set<String> getAllowApprovedUPMGroupsForLoginUser(FPReviewSummarySearchRQ searchRQ) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        Map<Integer, List<FPUpmGroupForSearch>> integerListMap = new HashMap<>();

        Set<String> strings = new HashSet<>();

        SQL.append(" SELECT                                                     \n");
        SQL.append("   twftd.WORK_FLOW_TEMPLATE_ID,                             \n");
        SQL.append("   tug.GROUP_CODE,                                          \n");
        SQL.append("   tug.REFERENCE_NAME,                                      \n");
        SQL.append("   twftd.DISPLAY_ORDER                                      \n");
        SQL.append(" FROM T_WORK_FLOW_TEMPLATE_DATA twftd                       \n");
        SQL.append("   INNER JOIN T_UPM_GROUP tug ON twftd.UPM_GROUP_ID = tug.UPM_GROUP_ID          \n");
        SQL.append(" WHERE twftd.WORK_FLOW_TEMPLATE_ID IN (                                         \n");
        SQL.append("   SELECT twft.WORK_FLOW_TEMPLATE_ID                                            \n");
        SQL.append("   FROM T_WORK_FLOW_TEMPLATE twft                                               \n");
        SQL.append("     INNER JOIN T_WORK_FLOW_TEMPLATE_DATA twftd ON                              \n");
        SQL.append("     twft.WORK_FLOW_TEMPLATE_ID = twftd.WORK_FLOW_TEMPLATE_ID                   \n");
        SQL.append("     INNER JOIN T_UPM_GROUP tug ON twftd.UPM_GROUP_ID = tug.UPM_GROUP_ID        \n");
        SQL.append("   WHERE twft.APPROVE_STATUS = 'APPROVED'                                       \n");
        if (StringUtils.isNotBlank(searchRQ.getLoginUpmAccessLevel())) {
            SQL.append("AND tug.GROUP_CODE =:upmCode                                                \n");
            params.put("upmCode", searchRQ.getLoginUpmAccessLevel());
        }
        SQL.append(" )           \n");
        SQL.append(" ORDER BY twftd.WORK_FLOW_TEMPLATE_ID, twftd.DISPLAY_ORDER \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Set<String>>() {
            @Override
            public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {

                    if (StringUtils.isNotEmpty(rs.getString("WORK_FLOW_TEMPLATE_ID"))) {

                        integerListMap.putIfAbsent(rs.getInt("WORK_FLOW_TEMPLATE_ID"), new ArrayList<>());
                        List<FPUpmGroupForSearch> fpUpmGroupForSearches1 = integerListMap.get(rs.getInt("WORK_FLOW_TEMPLATE_ID"));

                        fpUpmGroupForSearches1.add(new FPUpmGroupForSearch(
                                rs.getInt("WORK_FLOW_TEMPLATE_ID"),
                                rs.getString("GROUP_CODE"),
                                rs.getInt("DISPLAY_ORDER"),
                                rs.getString("REFERENCE_NAME")
                        ));
                    }
                }

                for (Integer getWorkFlowTemplateID : integerListMap.keySet()) {
                    for (int i = 0; i < integerListMap.get(getWorkFlowTemplateID).size(); i++) {
                        if (integerListMap.get(getWorkFlowTemplateID).get(i).getUpmCode().equals(searchRQ.getLoginUpmAccessLevel())) {
                            if (i > 0) {
                                strings.add(integerListMap.get(getWorkFlowTemplateID).get(i - 1).getUpmCode());
                            }
                        }
                    }
                }
                return strings;
            }
        });
    }

    //This function return all the lower upm levels of approved work flow
    public Set<String> getAllUnderAllowApprovedUPMGroupsForLoginUser(String loginUpmAccessLevel) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        Map<Integer, List<FPUpmGroupForSearch>> integerListMap = new HashMap<>();

        Set<String> strings = new HashSet<>();

        SQL.append(" SELECT                                                     \n");
        SQL.append("   twftd.WORK_FLOW_TEMPLATE_ID,                             \n");
        SQL.append("   tug.GROUP_CODE,                                          \n");
        SQL.append("   tug.REFERENCE_NAME,                                      \n");
        SQL.append("   twftd.DISPLAY_ORDER                                      \n");
        SQL.append(" FROM T_WORK_FLOW_TEMPLATE_DATA twftd                       \n");
        SQL.append("   INNER JOIN T_UPM_GROUP tug ON twftd.UPM_GROUP_ID = tug.UPM_GROUP_ID          \n");
        SQL.append(" WHERE twftd.WORK_FLOW_TEMPLATE_ID IN (                                         \n");
        SQL.append("   SELECT twft.WORK_FLOW_TEMPLATE_ID                                            \n");
        SQL.append("   FROM T_WORK_FLOW_TEMPLATE twft                                               \n");
        SQL.append("     INNER JOIN T_WORK_FLOW_TEMPLATE_DATA twftd ON                              \n");
        SQL.append("     twft.WORK_FLOW_TEMPLATE_ID = twftd.WORK_FLOW_TEMPLATE_ID                   \n");
        SQL.append("     INNER JOIN T_UPM_GROUP tug ON twftd.UPM_GROUP_ID = tug.UPM_GROUP_ID        \n");
        SQL.append("   WHERE twft.APPROVE_STATUS = 'APPROVED'                                       \n");
        if (StringUtils.isNotBlank(loginUpmAccessLevel)) {
            SQL.append("AND tug.GROUP_CODE =:upmCode                                                \n");
            params.put("upmCode", loginUpmAccessLevel);
        }
        SQL.append(" )           \n");
        SQL.append(" ORDER BY twftd.WORK_FLOW_TEMPLATE_ID, twftd.DISPLAY_ORDER DESC \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Set<String>>() {
            @Override
            public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {

                    if (StringUtils.isNotEmpty(rs.getString("WORK_FLOW_TEMPLATE_ID"))) {

                        integerListMap.putIfAbsent(rs.getInt("WORK_FLOW_TEMPLATE_ID"), new ArrayList<>());
                        List<FPUpmGroupForSearch> fpUpmGroupForSearches1 = integerListMap.get(rs.getInt("WORK_FLOW_TEMPLATE_ID"));

                        fpUpmGroupForSearches1.add(new FPUpmGroupForSearch(
                                rs.getInt("WORK_FLOW_TEMPLATE_ID"),
                                rs.getString("GROUP_CODE"),
                                rs.getInt("DISPLAY_ORDER"),
                                rs.getString("REFERENCE_NAME")
                        ));
                    }
                }

                for (Integer getWorkFlowTemplateID : integerListMap.keySet()) {
                    Integer userAccessLevelIndex = null;
                    for (int i = 0; i < integerListMap.get(getWorkFlowTemplateID).size(); i++) {
                        if (integerListMap.get(getWorkFlowTemplateID).get(i).getUpmCode().equals(loginUpmAccessLevel)) {
                            userAccessLevelIndex = i;
                        }
                        if (userAccessLevelIndex != null && i > userAccessLevelIndex) {
                            strings.add(integerListMap.get(getWorkFlowTemplateID).get(i).getUpmCode());
                        }
                    }
                }
                return strings;
            }
        });
    }

    public Page<AFTopicDTO> getPagedAFTopics(AFTopicSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                      \n");
        SQL.append("aft.AF_TOPIC_ID,            \n");
        SQL.append("aft.PAGE,                   \n");
        SQL.append("aft.TOPIC,                  \n");
        SQL.append("aft.TOPIC_CODE,             \n");
        SQL.append("aft.DESCRIPTION,            \n");
        SQL.append("aft.STATUS,                 \n");
        SQL.append("aft.APPROVE_STATUS,         \n");
        SQL.append("aft.APPROVED_DATE,          \n");
        SQL.append("aft.APPROVED_BY,            \n");
        SQL.append("aft.CREATED_DATE,           \n");
        SQL.append("aft.CREATED_BY,             \n");
        SQL.append("aft.MODIFIED_DATE,          \n");
        SQL.append("aft.MODIFIED_BY             \n");
        SQL.append("FROM T_AF_TOPIC aft         \n");
        SQL.append("WHERE aft.AF_TOPIC_ID IS NOT NULL   \n");

        if (searchRQ.getAfTopicID() != null) {
            SQL.append("AND aft.AF_TOPIC_ID =:topicID \n");
            params.put("topicID", searchRQ.getAfTopicID());
        }

        if (StringUtils.isNotEmpty(searchRQ.getApplicationFormPage())) {
            SQL.append("AND upper(aft.PAGE) LIKE '%" + searchRQ.getApplicationFormPage().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getTopic())) {
            SQL.append("AND upper(aft.TOPIC) LIKE '%" + searchRQ.getTopic().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getTopicCode())) {
            SQL.append("AND upper(aft.TOPIC_CODE) LIKE '%" + searchRQ.getTopicCode().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getDescription())) {
            SQL.append("AND upper(aft.DESCRIPTION) LIKE '%" + searchRQ.getDescription().toUpperCase() + "%' \n");
        }

        if (searchRQ.getApproveStatus() != null) {
            SQL.append("AND aft.APPROVE_STATUS =:approveStatus \n");
            params.put("approveStatus", searchRQ.getApproveStatus().toString());
        }
        if (searchRQ.getApproveStatusList() != null && !searchRQ.getApproveStatusList().isEmpty()) {
            SQL.append("AND aft.APPROVE_STATUS IN (" + QueryInBuilder.buildSQLINQuery(searchRQ.getApproveStatusList()) + ")\n");
        }

        if (searchRQ.getStatus() != null) {
            SQL.append("AND aft.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }

        if (StringUtils.isNotEmpty(searchRQ.getApprovedDateStr())) {
            SQL.append("AND upper(aft.APPROVED_DATE) LIKE '%" + searchRQ.getApprovedDateStr().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotEmpty(searchRQ.getApprovedBy())) {
            SQL.append("AND upper(aft.APPROVED_BY) LIKE '%" + searchRQ.getApprovedBy().toUpperCase() + "%' \n");
        }

        SQL.append("ORDER BY aft.CREATED_DATE ASC \n");

        return getPagedData(SQL.toString(), params, new RowMapper<AFTopicDTO>() {

            @Nullable
            @Override
            public AFTopicDTO mapRow(ResultSet rs, int i) throws SQLException {
                AFTopicDTO afTopicDTO = null;
                if (StringUtils.isNotEmpty(rs.getString("AF_TOPIC_ID"))) {
                    afTopicDTO = new AFTopicDTO();
                    afTopicDTO.setTopicID(rs.getInt("AF_TOPIC_ID"));
                    afTopicDTO.setPage(DomainConstants.ApplicationFormTopicPage.resolve(rs.getString("PAGE")));
                    afTopicDTO.setTopic(rs.getString("TOPIC"));
                    afTopicDTO.setTopicCode(rs.getString("TOPIC_CODE"));
                    afTopicDTO.setDescription(rs.getString("DESCRIPTION"));
                    afTopicDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                    afTopicDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                    afTopicDTO.setApprovedBy(rs.getString("APPROVED_BY"));
                    if (rs.getTimestamp("APPROVED_DATE") != null) {
                        afTopicDTO.setApprovedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("APPROVED_DATE")));
                    }
                    if (rs.getTimestamp("CREATED_DATE") != null) {
                        afTopicDTO.setCreatedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("CREATED_DATE")));
                    }
                    afTopicDTO.setCreatedBy(rs.getString("CREATED_BY"));
                    if (rs.getTimestamp("MODIFIED_DATE") != null) {
                        afTopicDTO.setModifiedDateStr(CalendarUtil.getDefaultFormattedDateTime(rs.getTimestamp("MODIFIED_DATE")));
                    }
                    afTopicDTO.setModifiedBy(rs.getString("MODIFIED_BY"));
                }
                return afTopicDTO;
            }
        }, searchRQ);
    }

    public Long getDuplicateAFTopicsCountByTopicORCode(String topic, String topicCode) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("topic", topic);
        paramsMap.put("topicCode", topicCode);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(AF_TOPIC_ID) topicCount               \n");
        SQL.append(" FROM T_AF_TOPIC                                    \n");
        SQL.append(" WHERE STATUS = 'ACT'                               \n");
        SQL.append("       AND (TOPIC = :topic                          \n");
        SQL.append("       OR TOPIC_CODE = :topicCode)                  \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Long getPendingAFTopicCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(AF_TOPIC_ID) pendingAFTopicCount   \n");
        SQL.append(" FROM T_AF_TOPIC                                 \n");
        SQL.append(" WHERE APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND PARENT_REC_ID = :parentID             \n");
        SQL.append("       AND STATUS = 'ACT'                        \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public List<UpcSectionDTO> getActiveApprovedUpcSectionListByTemplateID(UpcTemplateDTO upcTemplateDTO) throws AppsException {

        List<UpcSectionDTO> responseList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("upcTemplateID", upcTemplateDTO.getUpcTemplateID());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT tus.UPC_SECTION_ID,                                                                      \n");
        SQL.append(" tus.UPC_SECTION_NAME,                                                                          \n");
        SQL.append(" tut.UPC_TEMPLATE_ID                                                                            \n");
        SQL.append("FROM T_UPC_SECTION tus                                                                          \n");
        SQL.append("INNER JOIN T_UPC_TEMPLATE_DATA tutd ON tus.UPC_SECTION_ID = tutd.UPC_SECTION_ID                 \n");
        SQL.append("INNER JOIN T_UPC_TEMPLATE tut ON tutd.UPC_TEMPLATE_ID = tut.UPC_TEMPLATE_ID                     \n");
        SQL.append("WHERE tut.APPROVE_STATUS ='APPROVED'                                                            \n");
        SQL.append("AND tut.STATUS ='ACT'                                                                           \n");
        SQL.append("AND tut.UPC_TEMPLATE_ID = :upcTemplateID                                                        \n");
        SQL.append("AND tus.APPROVE_STATUS ='APPROVED'                                                              \n");
        SQL.append("AND tus.STATUS ='ACT'                                                                           \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<UpcSectionDTO>>() {
            @Override
            public List<UpcSectionDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()) {
                    UpcSectionDTO upcSectionDTO = new UpcSectionDTO();
                    upcSectionDTO.setUpcSectionID(resultSet.getInt("UPC_SECTION_ID"));
                    upcSectionDTO.setUpcSectionName(resultSet.getString("UPC_SECTION_NAME"));
                    responseList.add(upcSectionDTO);
                }
                return responseList;
            }
        });
    }

    public List<CreditFacilityTypeDTO> getApprovedCreditFacilityType() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                                     \n");
        SQL.append("   tcft.CREDIT_FACILITY_TYPE_ID,            \n");
        SQL.append("   tcft.FACILITY_TYPE_NAME,                 \n");
        SQL.append("   tcft.DESCRIPTION,                        \n");
        SQL.append("   tcft.STATUS,                             \n");
        SQL.append("   tcft.APPROVE_STATUS                      \n");
        SQL.append(" FROM T_CREDIT_FACILITY_TYPE tcft           \n");
        SQL.append(" WHERE tcft.STATUS = 'ACT' AND tcft.APPROVE_STATUS = 'APPROVED'\n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CreditFacilityTypeDTO>() {

            @Nullable
            @Override
            public CreditFacilityTypeDTO mapRow(ResultSet rs, int i) throws SQLException {
                CreditFacilityTypeDTO creditFacilityTypeDTO = new CreditFacilityTypeDTO();
                creditFacilityTypeDTO.setCreditFacilityTypeID(rs.getInt("CREDIT_FACILITY_TYPE_ID"));
                creditFacilityTypeDTO.setFacilityTypeName(rs.getString("FACILITY_TYPE_NAME"));
                creditFacilityTypeDTO.setDescription(rs.getString("DESCRIPTION"));
                creditFacilityTypeDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                creditFacilityTypeDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                return creditFacilityTypeDTO;
            }
        });
    }


    public List<SupportingDocDTO> getApprovedSupportingDoc() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                                       \n");
        SQL.append("  tsd.SUPPORTING_DOC_ID,                                     \n");
        SQL.append("  tsd.DOCUMENT_NAME,                                         \n");
        SQL.append("  tsd.DESCRIPTION,                                           \n");
        SQL.append("  tsd.SUPPORT_DOCUMENT_TYPE,                                 \n");
        SQL.append("  tsd.STATUS,                                                \n");
        SQL.append("  tsd.APPROVE_STATUS                                         \n");
        SQL.append("FROM T_SUPPORTING_DOC tsd                                    \n");
        SQL.append("WHERE tsd.SUPPORTING_DOC_ID IS NOT NULL                      \n");
        SQL.append("AND tsd.STATUS ='ACT' AND tsd.APPROVE_STATUS ='APPROVED'     \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<SupportingDocDTO>() {

            @Nullable
            @Override
            public SupportingDocDTO mapRow(ResultSet rs, int i) throws SQLException {
                SupportingDocDTO supportingDocDTO = new SupportingDocDTO();
                supportingDocDTO.setSupportingDocID(rs.getInt("SUPPORTING_DOC_ID"));
                supportingDocDTO.setDocumentName(rs.getString("DOCUMENT_NAME"));
                supportingDocDTO.setSupportDocumentType(rs.getString("SUPPORT_DOCUMENT_TYPE"));
                supportingDocDTO.setDescription(rs.getString("DESCRIPTION"));
                supportingDocDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                supportingDocDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                return supportingDocDTO;
            }
        });
    }

    public List<GlobalSupportingDocDTO> getApprovedGlobalSupportingDoc() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                                       \n");
        SQL.append("  tsd.SUPPORTING_DOC_ID,                                     \n");
        SQL.append("  tsd.DOCUMENT_NAME,                                         \n");
        SQL.append("  tsd.DESCRIPTION,                                           \n");
        SQL.append("  tsd.SUPPORT_DOCUMENT_TYPE,                                 \n");
        SQL.append("  tsd.STATUS,                                                \n");
        SQL.append("  tsd.APPROVE_STATUS,                                         \n");
        SQL.append("  tsd.CATEGORY_ID,                                            \n");
        SQL.append("  tsd.CATEGORY_NAME                                           \n");
        SQL.append("FROM T_GLOBAL_SUPPORTING_DOC tsd                              \n");
        SQL.append("WHERE tsd.SUPPORTING_DOC_ID IS NOT NULL                      \n");
        SQL.append("AND tsd.STATUS ='ACT' AND tsd.APPROVE_STATUS ='APPROVED'     \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<GlobalSupportingDocDTO>() {

            @Nullable
            @Override
            public GlobalSupportingDocDTO mapRow(ResultSet rs, int i) throws SQLException {
                GlobalSupportingDocDTO supportingDocDTO = new GlobalSupportingDocDTO();
                supportingDocDTO.setSupportingDocID(rs.getInt("SUPPORTING_DOC_ID"));
                supportingDocDTO.setDocumentName(rs.getString("DOCUMENT_NAME"));
                supportingDocDTO.setSupportDocumentType(rs.getString("SUPPORT_DOCUMENT_TYPE"));
                supportingDocDTO.setDescription(rs.getString("DESCRIPTION"));
                supportingDocDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                supportingDocDTO.setApproveStatus(DomainConstants.MasterDataApproveStatus.resolve(rs.getString("APPROVE_STATUS")));
                supportingDocDTO.setCategoryID(rs.getInt("CATEGORY_ID"));
                supportingDocDTO.setCategoryName(rs.getString("CATEGORY_NAME"));
                return supportingDocDTO;
            }
        });
    }


    public List<SecuritySummaryTopicDTO> getActiveSecuritySummaryTopics() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT tsst.SECURITY_SUMMARY_TOPIC_ID,                                        \n");
        SQL.append("  tsst.SECURITY_TYPE,                                                         \n");
        SQL.append("  tsst.SECURITY_TYPE_GROUP,                                                   \n");
        SQL.append("  tsst.STATUS                                                                 \n");
        SQL.append("FROM T_SECURITY_SUMMARY_TOPIC tsst                                            \n");
        SQL.append("WHERE tsst.SECURITY_SUMMARY_TOPIC_ID IS NOT NULL                              \n");
        SQL.append("AND tsst.STATUS = 'ACT'                                                       \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<SecuritySummaryTopicDTO>() {

            @Nullable
            @Override
            public SecuritySummaryTopicDTO mapRow(ResultSet rs, int i) throws SQLException {
                SecuritySummaryTopicDTO securitySummaryTopicDTO = new SecuritySummaryTopicDTO();
                securitySummaryTopicDTO.setSecuritySummaryTopicID(rs.getInt("SECURITY_SUMMARY_TOPIC_ID"));
                securitySummaryTopicDTO.setSecurityType(rs.getString("SECURITY_TYPE"));
                securitySummaryTopicDTO.setSecurityTypeGroup(rs.getString("SECURITY_TYPE_GROUP"));
                securitySummaryTopicDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                return securitySummaryTopicDTO;
            }
        });
    }

    public List<CASBranchDepartmentDTO> getCasActiveBranchDepartmentList() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                        \n");
        SQL.append("  BRANCH_DEPARTMENT_CAS_CODE,                 \n");
        SQL.append("  BRANCH_DEPARTMENT_NAME,                     \n");
        SQL.append("  BRANCH_DEPARTMENT_DIV_CODE,                 \n");
        SQL.append("  STATUS                                      \n");
        SQL.append("FROM T_BRANCH_DEPARTMENT                      \n");
        SQL.append("WHERE STATUS = 'ACT'                          \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CASBranchDepartmentDTO>() {

            @Nullable
            @Override
            public CASBranchDepartmentDTO mapRow(ResultSet rs, int i) throws SQLException {
                CASBranchDepartmentDTO casBranchDepartmentDTO = new CASBranchDepartmentDTO();
                casBranchDepartmentDTO.setBranchDepartmentCasCode(rs.getString("BRANCH_DEPARTMENT_CAS_CODE"));
                casBranchDepartmentDTO.setBranchDepartmentName(rs.getString("BRANCH_DEPARTMENT_NAME"));
                casBranchDepartmentDTO.setBranchDepartmentDivCode(rs.getString("BRANCH_DEPARTMENT_DIV_CODE"));
                casBranchDepartmentDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                return casBranchDepartmentDTO;
            }
        });
    }

    public String getCasActiveBranchDepartmentByBranchDepartmentCasCode(String branchDepartmentCasCode) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("branchDepartmentCasCode", branchDepartmentCasCode.toUpperCase());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT  UNIQUE                                                  \n");
        SQL.append("  BRANCH_DEPARTMENT_DIV_CODE                                      \n");
        SQL.append("FROM T_BRANCH_DEPARTMENT                                        \n");
        SQL.append(" WHERE upper(BRANCH_DEPARTMENT_CAS_CODE) =:branchDepartmentCasCode     \n");
        SQL.append(" AND STATUS = 'ACT'                                             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, String.class);
    }

    public Integer getWorkFlowTemplateIdByUpmGroup(String upmGroup) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("groupCode", upmGroup);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT \n");
        SQL.append(" T.WORK_FLOW_TEMPLATE_ID \n");
        SQL.append(" FROM T_WORK_FLOW_TEMPLATE_DATA T, T_UPM_GROUP G \n");
        SQL.append(" WHERE T.UPM_GROUP_ID = G.UPM_GROUP_ID \n");
        SQL.append("  AND G.GROUP_CODE= :groupCode \n");
        SQL.append(" AND G.STATUS = 'ACT' ORDER BY T.WORK_FLOW_TEMPLATE_DATA_ID DESC \n");
        SQL.append(" FETCH FIRST 1 ROW ONLY \n");
        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Integer.class);
    }

    public List<CommitteeTypeDTO> getCommitteeTypes() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT                                        \n");
        SQL.append("  COMMITTEE_TYPE_ID,                          \n");
        SQL.append("  COMMITTEE_TYPE,                             \n");
        SQL.append("  COMMITTEE_TYPE_NAME,                        \n");
        SQL.append("  STATUS                                      \n");
        SQL.append(" FROM CA_COMMITTEE_TYPE_CONFIG                 \n");
        SQL.append(" WHERE STATUS = 'ACT'                     \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CommitteeTypeDTO>() {

            @Nullable
            @Override
            public CommitteeTypeDTO mapRow(ResultSet rs, int i) throws SQLException {
                CommitteeTypeDTO caCommitteeTypeConfigDTO = new CommitteeTypeDTO();
                caCommitteeTypeConfigDTO.setCommitteeTypeId(rs.getInt("COMMITTEE_TYPE_ID"));
                caCommitteeTypeConfigDTO.setCommitteeTypeName(rs.getString("COMMITTEE_TYPE"));
                caCommitteeTypeConfigDTO.setCommitteeTypeName(rs.getString("COMMITTEE_TYPE_NAME"));
                caCommitteeTypeConfigDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                return caCommitteeTypeConfigDTO;
            }
        });
    }

    public List<CommitteeType> getDuplicateCommitteeType(String typeName) {

        Map<String, Object> params = new HashMap<>();
        params.put("typeName", typeName);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT COMMITTEE_TYPE_ID,                \n");
        SQL.append(" COMMITTEE_TYPE                \n");
        SQL.append(" FROM CA_COMMITTEE_TYPE_CONFIG                          \n");
        SQL.append(" WHERE STATUS = 'ACT'                                  \n");
        SQL.append(" AND COMMITTEE_TYPE = :typeName      \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CommitteeType>() {
            @Nullable
            @Override
            public CommitteeType mapRow(ResultSet rs, int i) throws SQLException {
                CommitteeType committee = new CommitteeType();
                committee.setCommitteeTypeId(rs.getInt("COMMITTEE_TYPE_ID"));
                committee.setCommitteeTypeName(rs.getString("COMMITTEE_TYPE"));
                return committee;
            }
        });
    }

    public List<CACommitteeDTO> getCommittees() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                        \n");
        SQL.append("  COMMITTEE_ID,                               \n");
        SQL.append("  COMMITTEE_TYPE_ID,                          \n");
        SQL.append("  COMMITTEE_TYPE,                             \n");
        SQL.append("  COMMITTEE_NAME,                             \n");
        SQL.append("  CURRENT_PATH,                             \n");
        SQL.append("  STATUS                                      \n");
        SQL.append(" FROM CA_COMMITTEE_CONFIG                      \n");
        SQL.append(" WHERE APPROVE_STATUS = 'APPROVED' AND STATUS = 'ACT'   \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CACommitteeDTO>() {

            @Nullable
            @Override
            public CACommitteeDTO mapRow(ResultSet rs, int i) throws SQLException {
                CACommitteeDTO caCommitteeConfigDTO = new CACommitteeDTO();
                caCommitteeConfigDTO.setCommitteeId(rs.getInt("COMMITTEE_ID"));
                caCommitteeConfigDTO.setCommitteeTypeId(rs.getInt("COMMITTEE_TYPE_ID"));
            //    caCommitteeConfigDTO.setCommitteeType(rs.getString("COMMITTEE_TYPE"));
                caCommitteeConfigDTO.setCommitteeName(rs.getString("COMMITTEE_NAME"));
                caCommitteeConfigDTO.setCurrentPath(AppsConstants.CAPathType.valueOf(rs.getString("CURRENT_PATH")));
                caCommitteeConfigDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                return caCommitteeConfigDTO;
            }
        });
    }
    
    public Long getDuplicateUserPoolCountByName(String userName) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userName", userName);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(up.USER_ID) userCount        \n");
        SQL.append(" FROM CA_POOL_CONFIG up                           \n");
        SQL.append(" WHERE up.USER_STATUS = 'ACT'                            \n");
        SQL.append("       AND up.USER_NAME = :userName         \n");


        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }


    public Long getPendingUserPoolCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(up.USER_ID) pendingUserPoolCount \n");
        SQL.append(" FROM CA_POOL_CONFIG up                           \n");
        SQL.append(" WHERE up.APPROVE_STATUS = 'PENDING'                \n");
        SQL.append("       AND up.PARENT_REC_ID IS NOT NULL             \n");
        SQL.append("       AND up.PARENT_REC_ID = :parentID             \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }


    public Long getDuplicateSbCountByNameAndCommitteeType(String subCommitteeName, Integer committeeId) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("subCommitteeName", subCommitteeName);
        paramsMap.put("committeeId", committeeId);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(sc.COMMITTEE_ID) scCount                \n");
        SQL.append(" FROM CA_COMMITTEE_CONFIG sc                          \n");
        SQL.append(" WHERE sc.STATUS = 'ACT'                                  \n");
        SQL.append("       AND sc.COMMITTEE_NAME = :subCommitteeName      \n");
        SQL.append("       AND sc.COMMITTEE_TYPE_ID = :committeeId                 \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }


    public Long getPendingSubCommitteeCount(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT count(sc.COMMITTEE_ID) pendingSubCommitteeCount         \n");
        SQL.append(" FROM CA_COMMITTEE_CONFIG sc                           \n");
        SQL.append(" WHERE sc.APPROVE_STATUS = 'PENDING'                       \n");
        SQL.append("       AND sc.PARENT_REC_ID IS NOT NULL                    \n");
        SQL.append("       AND sc.PARENT_REC_ID = :parentID                    \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }

    public Integer isTempCommitteeExist(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT COMMITTEE_ID         \n");
        SQL.append(" FROM CA_COMMITTEE_CONFIG_TEMP                           \n");
        SQL.append(" WHERE PARENT_REC_ID = :parentID                    \n");

        int result = 0;

        try {
            result = namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Integer.class);
        } catch (Exception e){
        }

        return result;
    }

    public List<CACommittee> getCommitteesByUserName(String userName) {

        Map<String, Object> params = new HashMap<>();
        params.put("userName",userName);

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                        \n");
        SQL.append(" CAC.COMMITTEE_ID,                 \n");
        SQL.append(" CAC.COMMITTEE_NAME                 \n");
        SQL.append(" FROM CA_USER_CONFIG CAU                     \n");
        SQL.append(" LEFT JOIN CA_COMMITTEE_CONFIG CAC ON CAU.COMMITTEE_ID=CAC.COMMITTEE_ID                      \n");
        SQL.append(" WHERE CAU.USER_NAME = :userName                          \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CACommittee>() {
            @Nullable
            @Override
            public CACommittee mapRow(ResultSet rs, int i) throws SQLException {
                CACommittee committee = new CACommittee();
                committee.setCommitteeId(rs.getInt("COMMITTEE_ID"));
                committee.setCommitteeName(rs.getString("COMMITTEE_NAME"));
                return committee;
            }
        });
    }

    public List<CALevelDTO> getCommitteeLevels() {
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                             \n");
        SQL.append("  LEVEL_CONFIG_ID,                 \n");
        SQL.append("  COMMITTEE_ID,                    \n");
        SQL.append("  LEVEL_ID,                        \n");
        SQL.append("  PATH_TYPE,                       \n");
        SQL.append("  COMBINATION,                     \n");
        SQL.append("  USER_COUNT,                     \n");
        SQL.append("  STATUS                           \n");
        SQL.append("  FROM  CA_LEVEL_CONFIG            \n");
        SQL.append(" WHERE STATUS = 'ACT'         \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CALevelDTO>() {

            @Nullable
            @Override
            public CALevelDTO mapRow(ResultSet rs, int i) throws SQLException {
                CALevelDTO caCommitteeLevelConfigDTO = new CALevelDTO();
                caCommitteeLevelConfigDTO.setLevelConfigId(rs.getInt("LEVEL_CONFIG_ID"));
                caCommitteeLevelConfigDTO.setCommitteeId(rs.getInt("COMMITTEE_ID"));
                caCommitteeLevelConfigDTO.setLevelId(rs.getInt("LEVEL_ID"));
                caCommitteeLevelConfigDTO.setUserCount(rs.getInt("USER_COUNT"));
                caCommitteeLevelConfigDTO.setPathType(AppsConstants.CAPathType.valueOf(rs.getString("PATH_TYPE")));
                caCommitteeLevelConfigDTO.setCombination(rs.getString("COMBINATION"));
                caCommitteeLevelConfigDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                return caCommitteeLevelConfigDTO;
            }
        });
    }
    
    public Integer isTempPoolUserExist(Integer parentID) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("parentID", parentID);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT USER_ID         \n");
        SQL.append(" FROM CA_POOL_CONFIG_TEMP                           \n");
        SQL.append(" WHERE PARENT_REC_ID = :parentID                    \n");

        int result = 0;

        try {
           result = namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Integer.class);
        } catch (Exception e){
        }

        return result;
    }

    public List<CACommittee> getCommitteesByType(Integer typeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("typeId",typeId);

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                        \n");
        SQL.append(" COMMITTEE_ID                 \n");
        SQL.append(" FROM CA_COMMITTEE_CONFIG                     \n");
        SQL.append(" WHERE COMMITTEE_TYPE_ID = :typeId                          \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CACommittee>() {
            @Nullable
            @Override
            public CACommittee mapRow(ResultSet rs, int i) throws SQLException {
                CACommittee committee = new CACommittee();
                committee.setCommitteeId(rs.getInt("COMMITTEE_ID"));
                return committee;
            }
        });
    }

    public List<CACommittee> getTempCommitteesByType(Integer typeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("typeId",typeId);

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                        \n");
        SQL.append(" COMMITTEE_ID                 \n");
        SQL.append(" FROM CA_COMMITTEE_CONFIG_TEMP                     \n");
        SQL.append(" WHERE COMMITTEE_TYPE_ID = :typeId                          \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CACommittee>() {
            @Nullable
            @Override
            public CACommittee mapRow(ResultSet rs, int i) throws SQLException {
                CACommittee committee = new CACommittee();
                committee.setCommitteeId(rs.getInt("COMMITTEE_ID"));
                return committee;
            }
        });
    }

    public List<CAUserDTO> getCommitteeUsers() {

        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT                                              \n");
        SQL.append("  UC.USER_CONFIG_ID,                                \n");
        SQL.append("  UC.COMMITTEE_ID,                                  \n");
        SQL.append("  LC.LEVEL_ID,                                      \n");
        SQL.append("  UC.PATH_TYPE,                                     \n");
        SQL.append("  UC.USER_ID,                                       \n");
        SQL.append("  UC.STATUS                                         \n");
        SQL.append("  FROM CA_USER_CONFIG UC, CA_LEVEL_CONFIG LC        \n");
        SQL.append(" WHERE  UC.LEVEL_CONFIG_ID = LC.LEVEL_CONFIG_ID      \n");
        SQL.append(" AND UC.STATUS = 'APPROVED' AND UC.USER_STATUS='ACT'    \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CAUserDTO>() {

            @Nullable
            @Override
            public CAUserDTO mapRow(ResultSet rs, int i) throws SQLException {
                CAUserDTO caCommitteeUserConfigDTO = new CAUserDTO();
                caCommitteeUserConfigDTO.setUserConfigId(rs.getInt("USER_CONFIG_ID"));
                caCommitteeUserConfigDTO.setCommitteeId(rs.getInt("COMMITTEE_ID"));
              //  caCommitteeUserConfigDTO.setLevelId(rs.getInt("LEVEL_ID"));
              //  caCommitteeUserConfigDTO.setUserId(rs.getString("USER_ID"));
                caCommitteeUserConfigDTO.setPathType(AppsConstants.CAPathType.valueOf(rs.getString("PATH_TYPE")));
                caCommitteeUserConfigDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                return caCommitteeUserConfigDTO;
            }
        });
    }

    public Integer changeCommitteePaperCurrentLevel(Integer committeeID, AppsConstants.CAPathType path) {

        Map<String, Object> params = new HashMap<>();
        System.out.println("path:" + path.toString());
        params.put("currentPath", path.toString());
        params.put("committeeID", committeeID);


        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT CA_WORKFLOW.CHANGE_COMMITTEE_PAPER_CURRENT_LEVEL(:committeeID, :currentPath) RETURN_CODE FROM DUAL");

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

    public Integer removeCommitteeLevel(Integer committeeId) {

        try {
            StringBuilder SQL = new StringBuilder();

            Map<String, Object> params = new HashMap<>();
            params.put("committeeId", committeeId);
            SQL.append("SELECT CA_COMMITTEE_LEVEL_REMOVE(:committeeId) STATUS FROM DUAL");

         return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<Integer>() {
                @Override
                public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                    int status = 0;
                    while (rs.next()) {
                        status = rs.getInt("STATUS");
                    }

                  return status;
                }
            });


        } catch (Exception e) {
            LOG.error("ERROR : ", e);
            return 0;
        }
    }

    public Integer isCommitteeUsed(Integer committeeId) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("committeeId", committeeId);
        paramsMap.put("status", "APPROVED");
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT COUNT(COMMITTEE_PAPER_ID) AS ACTIVE_COUNT         \n");
        SQL.append(" FROM CA_COMMITTEE_PAPER                           \n");
        SQL.append(" WHERE COMMITTEE_ID = :committeeId AND CURRENT_COMMITTEE_PAPER_STATUS != :status    \n");

        int result = 0;

        try {
            result = namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Integer.class);
        } catch (Exception e){
            LOG.error("Committee Used ERROR : ", e);
        }

        return result;
    }

    public String getCommitteeCurrentPath(Integer committeeId) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("committeeId", committeeId);
        paramsMap.put("status", "APPROVED");
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT CURRENT_PATH        \n");
        SQL.append(" FROM CA_COMMITTEE_CONFIG                           \n");
        SQL.append(" WHERE COMMITTEE_ID = :committeeId AND APPROVE_STATUS = :status    \n");

        String result = "REG";

        try {
            result = namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, String.class);
        } catch (Exception e){
            LOG.error("Get Committee Current Path ERROR : ", e);
        }

        return result;
    }


    public Integer getCommitteePathMaxLevel(Integer committeeId, String pathType) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("committeeId", committeeId);
        paramsMap.put("pathType", pathType);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT MAX(LEVEL_ID)        \n");
        SQL.append(" FROM CA_LEVEL_CONFIG                           \n");
        SQL.append(" WHERE COMMITTEE_ID = :committeeId AND PATH_TYPE = :pathType    \n");

        int result = 0;

        try {
            result = namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Integer.class);
        } catch (Exception e){
            LOG.error("Get Committee Path Max Level ERROR : ", e);
        }

        return result;
    }
}
