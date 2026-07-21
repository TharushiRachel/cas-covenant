package com.itechro.cas.dao.facility.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facility.FacilityDocumentDTO;
import com.itechro.cas.model.dto.facility.FacilityDocumentSearchRQ;
import com.itechro.cas.model.dto.facility.FacilitySearchRQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FacilityJdbcDao extends BaseJDBCDao {

    public Page<FacilityDTO> getPagedFacilityDTO(FacilitySearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT \n");
        SQL.append("   f.FACILITY_ID, \n");
        SQL.append("   f.FACILITY_REF_CODE, \n");
        SQL.append("   f.CREDIT_FACILITY_TEMPLATE_ID, \n");
        SQL.append("   f.FACILITY_PAPER_ID, \n");
        SQL.append("   f.FACILITY_CURRENCY, \n");
        SQL.append("   f.DISBURSEMENT_ACC_NUM, \n");
        SQL.append("   f.FACILITY_AMOUNT, \n");
        SQL.append("   f.IS_COOPERATE,");
        SQL.append("   f.OUTSTANDING_AMOUNT,");
        SQL.append("   f.PARENT_REC_ID, \n");
        SQL.append("   f.SECTOR_ID,");
        SQL.append("   f.SUB_SECTOR_ID, \n");
        SQL.append("   f.PURPOSE_OF_ADVANCE, \n");
        SQL.append("   f.PURPOSE, \n");
        SQL.append("   f.FACILITY_TYPE,");
        SQL.append("   f.IS_ONE_OFF,");
        SQL.append("   f.REPAYMENT,");
        SQL.append("   f.CONDITION, \n");
        SQL.append("   f.IS_NEW, \n");
        SQL.append("   f.DISPLAY_ORDER,");
        SQL.append("   f.REMARK,");
        SQL.append("   f.STATUS ");
        SQL.append(" FROM T_FACILITY f \n");
        SQL.append(" WHERE f.FACILITY_ID IS NOT NULL \n");

        if (searchRQ.getFacilityID() != null) {
            SQL.append("AND f.FACILITY_ID =:facilityID \n");
            params.put("facilityID", searchRQ.getFacilityID());
        }
        if (StringUtils.isNotBlank(searchRQ.getFacilityRefCode())) {
            SQL.append("AND UPPER(f.FACILITY_REF_CODE) LIKE '%" + searchRQ.getFacilityRefCode().trim().toUpperCase() + "%' \n");
        }
        if (searchRQ.getCreditFacilityTemplateID() != null) {
            SQL.append("AND f.CREDIT_FACILITY_TEMPLATE_ID =:creditFacilityTemplateID \n");
            params.put("creditFacilityTemplateID", searchRQ.getCreditFacilityTemplateID());
        }
        if (searchRQ.getFacilityPaperID() != null) {
            SQL.append("AND f.FACILITY_PAPER_ID =:facilityPaperID \n");
            params.put("facilityPaperID", searchRQ.getFacilityPaperID());
        }
        if (StringUtils.isNotBlank(searchRQ.getFacilityCurrency())) {
            SQL.append("AND UPPER(f.FACILITY_CURRENCY) LIKE '%" + searchRQ.getFacilityCurrency().trim().toUpperCase() + "%' \n");
        }
        if (searchRQ.getFacilityAmount() != null) {
            SQL.append("AND f.FACILITY_AMOUNT =:facilityAmount \n");
            params.put("facilityAmount", searchRQ.getFacilityAmount());
        }
        if (searchRQ.getIsCooperate() != null) {
            SQL.append("AND f.IS_COOPERATE =:isCooperate \n");
            params.put("isCooperate", searchRQ.getIsCooperate().toString());
        }
        if (searchRQ.getOutstandingAmount() != null) {
            SQL.append("AND f.OUTSTANDING_AMOUNT =:outstandingAmount \n");
            params.put("outstandingAmount", searchRQ.getOutstandingAmount());
        }
        if (searchRQ.getSectorID() != null) {
            SQL.append("AND f.SECTOR_ID =:sectorID \n");
            params.put("sectorID", searchRQ.getSectorID());
        }
        if (searchRQ.getSubSectorID() != null) {
            SQL.append("AND f.SUB_SECTOR_ID =:subSectorID \n");
            params.put("subSectorID", searchRQ.getSubSectorID());
        }
        if (StringUtils.isNotBlank(searchRQ.getPurposeOfAdvance())) {
            SQL.append("AND UPPER(f.PURPOSE_OF_ADVANCE) LIKE '%" + searchRQ.getPurposeOfAdvance().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getPurpose())) {
            SQL.append("AND UPPER(f.PURPOSE) LIKE '%" + searchRQ.getPurpose().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getFacilityType())) {
            SQL.append("AND UPPER(f.FACILITY_TYPE) LIKE '%" + searchRQ.getFacilityType().trim().toUpperCase() + "%' \n");
        }
        if (searchRQ.getIsOneOff() != null) {
            SQL.append("AND f.IS_ONE_OFF =:isOneOff \n");
            params.put("isOneOff", searchRQ.getIsOneOff().toString());
        }
        if (StringUtils.isNotBlank(searchRQ.getRepayment())) {
            SQL.append("AND UPPER(f.REPAYMENT) LIKE '%" + searchRQ.getRepayment().trim().toUpperCase() + "%' \n");
        }
        if (StringUtils.isNotBlank(searchRQ.getCondition())) {
            SQL.append("AND UPPER(f.CONDITION) LIKE '%" + searchRQ.getCondition().trim().toUpperCase() + "%' \n");
        }
        if (searchRQ.getIsNew() != null) {
            SQL.append("AND f.IS_NEW =:isNew \n");
            params.put("isNew", searchRQ.getIsNew().toString());
        }
        if (searchRQ.getDisplayOrder() != null) {
            SQL.append("AND f.DISPLAY_ORDER =:displayOrder \n");
            params.put("displayOrder", searchRQ.getDisplayOrder());
        }
        if (StringUtils.isNotBlank(searchRQ.getRemark())) {
            SQL.append("AND UPPER(f.REMARK) LIKE '%" + searchRQ.getRemark().trim().toUpperCase() + "%' \n");
        }
        if (searchRQ.getStatus() != null) {
            SQL.append("AND f.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }
        SQL.append(" ORDER BY f.DISPLAY_ORDER \n");

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityDTO>() {

            @Nullable
            @Override
            public FacilityDTO mapRow(ResultSet rs, int i) throws SQLException {
                FacilityDTO facilityDTO = new FacilityDTO();
                facilityDTO.setFacilityID(rs.getInt("FACILITY_ID"));
                facilityDTO.setFacilityRefCode(rs.getString("FACILITY_REF_CODE"));
                facilityDTO.setParentFacilityID(rs.getInt("PARENT_REC_ID"));
                facilityDTO.setFacilityPaperID(rs.getInt("FACILITY_PAPER_ID"));
                facilityDTO.setCreditFacilityTemplateID(rs.getInt("CREDIT_FACILITY_TEMPLATE_ID"));
                facilityDTO.setFacilityCurrency(rs.getString("FACILITY_CURRENCY"));
                facilityDTO.setDisbursementAccNumber(rs.getString("DISBURSEMENT_ACC_NUM"));
                facilityDTO.setFacilityAmount(rs.getBigDecimal("FACILITY_AMOUNT"));
                facilityDTO.setExistingAmount(rs.getBigDecimal("EXISTING_AMOUNT"));
                facilityDTO.setOriginalAmount(rs.getBigDecimal("ORIGINAL_AMOUNT"));
                facilityDTO.setOutstandingAmount(rs.getBigDecimal("OUTSTANDING_AMOUNT"));
                facilityDTO.setIsCooperate(AppsConstants.YesNo.resolver(rs.getString("IS_COOPERATE")));
                facilityDTO.setIsOneOff(AppsConstants.YesNo.resolver(rs.getString("IS_ONE_OFF")));
                facilityDTO.setIsNew(AppsConstants.YesNo.resolver(rs.getString("IS_NEW")));
                facilityDTO.setReduction(AppsConstants.YesNo.resolver(rs.getString("IS_REDUCTION")));
                facilityDTO.setEnhancement(AppsConstants.YesNo.resolver(rs.getString("IS_ENHANCEMENT")));
                facilityDTO.setSectorID(rs.getInt("SECTOR_ID"));
                facilityDTO.setSubSectorID(rs.getInt("SUB_SECTOR_ID"));
                facilityDTO.setPurposeOfAdvance(rs.getString("PURPOSE_OF_ADVANCE"));
                facilityDTO.setPurpose(rs.getString("PURPOSE"));
                facilityDTO.setFacilityType(rs.getString("FACILITY_TYPE"));
                facilityDTO.setRepayment(rs.getString("REPAYMENT"));
                facilityDTO.setCondition(rs.getString("CONDITION"));
                facilityDTO.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
                facilityDTO.setRemark(rs.getString("REMARK"));
                facilityDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));

                return facilityDTO;
            }

        }, searchRQ);
    }

    public Page<FacilityDocumentDTO> getPagedFacilityDocDTO(FacilityDocumentSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT \n");
        SQL.append("   fd.FACILITY_DOCUMENT_ID, \n");
        SQL.append("   fd.FACILITY_ID, \n");
        SQL.append("   fd.SUPPORT_DOCUMENT_ID, \n");
        SQL.append("   fd.DOCUMENT_STORAGE_ID, \n");
        SQL.append("   fd.MANDATORY, \n");
        SQL.append("   fd.DISPLAY_ORDER, \n");
        SQL.append("   fd.REMARK, \n");
        SQL.append("   fd.STATUS \n");
        SQL.append(" FROM T_FACILITY_DOCUMENT fd \n");
        SQL.append(" WHERE fd.FACILITY_DOCUMENT_ID IS NOT NULL \n");

        if (searchRQ.getFacilityDocumentID() != null) {
            SQL.append("AND fd.FACILITY_DOCUMENT_ID =:creditFacilityDocID \n");
            params.put("creditFacilityDocID", searchRQ.getFacilityDocumentID());
        }
        if (searchRQ.getFacilityID() != null) {
            SQL.append("AND fd.FACILITY_ID =:facilityID \n");
            params.put("facilityID", searchRQ.getFacilityID());
        }
        if (searchRQ.getSupportingDocID() != null) {
            SQL.append("AND fd.SUPPORT_DOCUMENT_ID =:supportingDocID \n");
            params.put("supportingDocID", searchRQ.getSupportingDocID());
        }
        if (searchRQ.getDocumentStorageID() != null) {
            SQL.append("AND fd.DOCUMENT_STORAGE_ID =:storageID \n");
            params.put("storageID", searchRQ.getDocumentStorageID());
        }
        if (searchRQ.getMandatory() != null) {
            SQL.append("AND fd.MANDATORY =:mandatory \n");
            params.put("mandatory", searchRQ.getMandatory());
        }
        if (searchRQ.getDisplayOrder() != null) {
            SQL.append("AND fd.DISPLAY_ORDER =:displayOrder \n");
            params.put("displayOrder", searchRQ.getDisplayOrder());
        }
        if (StringUtils.isNotBlank(searchRQ.getRemark())) {
            SQL.append("AND UPPER(fd.REMARK) LIKE '%" + searchRQ.getRemark().trim().toUpperCase() + "%' \n");
        }
        if (searchRQ.getStatus() != null) {
            SQL.append("AND fd.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }
        SQL.append(" ORDER BY fd.DISPLAY_ORDER \n");

        return getPagedData(SQL.toString(), params, new RowMapper<FacilityDocumentDTO>() {

            @Nullable 
            @Override
            public FacilityDocumentDTO mapRow(ResultSet rs, int i) throws SQLException {
                FacilityDocumentDTO facilityDocumentDTO = new FacilityDocumentDTO();
                facilityDocumentDTO.setFacilityID(rs.getInt("FACILITY_ID"));
                facilityDocumentDTO.setFacilityDocumentID(rs.getInt("FACILITY_DOCUMENT_ID"));
                facilityDocumentDTO.setSupportingDocID(rs.getInt("SUPPORT_DOCUMENT_ID"));
                //facilityDocumentDTO.setDocStorageDTO(rs.getInt("DOCUMENT_STORAGE_ID"));
                facilityDocumentDTO.setMandatory(AppsConstants.YesNo.resolver(rs.getString("MANDATORY")));
                facilityDocumentDTO.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
                facilityDocumentDTO.setRemark(rs.getString("REMARK"));
                facilityDocumentDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));

                return facilityDocumentDTO;
            }

        }, searchRQ);
    }

    public Integer getMaxFacilityId(Integer facilityPaperId) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("facilityPaperId", facilityPaperId);
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT MAX(FACILITY_ID) AS FACILITY_ID         \n");
        SQL.append(" FROM T_FACILITY                           \n");
        SQL.append(" WHERE FACILITY_PAPER_ID = :facilityPaperId                    \n");

        int result = 0;

        try {
            result = namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Integer.class);
        } catch (Exception e){
        }

        return result;
    }
}
