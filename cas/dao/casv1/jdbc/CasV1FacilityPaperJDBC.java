package com.itechro.cas.dao.casv1.jdbc;

import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.dto.casv1.*;
import com.itechro.cas.util.CalendarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CasV1FacilityPaperJDBC extends BaseJDBCDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(CasV1FacilityPaperJDBC.class);

    public List<DirectorDTO> getCustomerDirectors(String refNo){

        List<DirectorDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", refNo);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT REF_NUM, DIRECTOR_NAME, AGE                      \n");
        SQL.append("FROM CASV1_DIRECTORS_TABLE                                           \n");
        SQL.append(" WHERE REF_NUM =:refNo       \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<DirectorDTO>>() {
            @Override
            public List<DirectorDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    DirectorDTO directorDTO = new DirectorDTO();
                    try {
                        directorDTO.setRefNo(rs.getString("REF_NUM"));
                        directorDTO.setDirectorName(rs.getString("DIRECTOR_NAME"));
                        directorDTO.setAge(rs.getInt("AGE"));
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(directorDTO);
                }
                return resultSet;
            }
        });
    }

    public List<CompanyDetailDTO> getCustomerCompanies(String refNo){

        List<CompanyDetailDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", refNo);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT REF_NUM, CONSTITUTION_OF_BUSSINESS                      \n");
        SQL.append("FROM CASV1_COMPANY_DETAILS_TABLE                                           \n");
        SQL.append(" WHERE REF_NUM =:refNo     \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CompanyDetailDTO>>() {
            @Override
            public List<CompanyDetailDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    CompanyDetailDTO companyDetailDTO = new CompanyDetailDTO();
                    try {
                        companyDetailDTO.setRefNo(rs.getString("REF_NUM"));
                        companyDetailDTO.setConstitutionOfBusiness(rs.getString("CONSTITUTION_OF_BUSSINESS"));
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(companyDetailDTO);
                }
                return resultSet;
            }
        });
    }

    public List<OtherFacilityDTO> getCustomerOtherFacilities(String refNo){

        List<OtherFacilityDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", refNo);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT OFT.*, OBT.BANK_NAME, LCT.CURRENCY_CODE AS LIMIT_CURRENCY_CODE, OCT.CURRENCY_CODE AS OS_CURRENCY_CODE                     \n");
        SQL.append(" FROM CASV1_OTHER_FACILITIES OFT LEFT JOIN CASV1_OTHER_BANKS_TABLE OBT ON OFT.BANK_ID=OBT.BANK_NO                                         \n");
        SQL.append(" LEFT JOIN CASV1_CURRENCY_TABLE LCT ON OFT.LIMIT_CURRENCY=LCT.CURRENCY_NO           \n");
        SQL.append(" LEFT JOIN CASV1_CURRENCY_TABLE OCT ON OFT.O_S_CURRENCY=OCT.CURRENCY_NO           \n");
        SQL.append(" WHERE REF_NUM =:refNo     \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<OtherFacilityDTO>>() {
            @Override
            public List<OtherFacilityDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    OtherFacilityDTO otherFacilityDTO = new OtherFacilityDTO();
                    try {
                        otherFacilityDTO.setRefNo(rs.getString("REF_NUM"));
                        otherFacilityDTO.setBankID(rs.getString("BANK_ID"));
                        otherFacilityDTO.setBankName(rs.getString("BANK_NAME"));
                        otherFacilityDTO.setFacility(rs.getString("FACILITY"));
                        otherFacilityDTO.setLimit(rs.getBigDecimal("LIMIT"));
                        otherFacilityDTO.setLimitCurrency(rs.getString("LIMIT_CURRENCY_CODE"));
                        otherFacilityDTO.setLimitType(rs.getInt("LIMIT_TYPE"));
                        otherFacilityDTO.setOs(rs.getBigDecimal("O_S"));
                        otherFacilityDTO.setOsCurrency(rs.getString("OS_CURRENCY_CODE"));
                        otherFacilityDTO.setOsType(rs.getInt("O_S_TYPE"));
                        otherFacilityDTO.setSecurity(rs.getString("SECURITY"));
                        otherFacilityDTO.setFacOrder(rs.getInt("FAC_ORDER"));
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(otherFacilityDTO);
                }
                return resultSet;
            }
        });
    }

    public List<FacilityStatusDTO> getCustomerFacilityPapers(String refNo){

        List<FacilityStatusDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", refNo);
        params.put("status", 4);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT FST.REF_NUM, FST.FACILITY_DATE, FST.DIRECT_TOTAL, FST.INDIRECT_TOTAL, FST.TOTAL_EXPOSURE, FST.PRE_DIRECT_TOTAL, FST.PRE_INDIRECT_TOTAL,                     \n");
        SQL.append(" FST.PRE_TOTAL_EXPOSURE, FST.GRP_PRE_DIRECT_TOTAL, FST.GRP_PRE_INDIRECT_TOTAL, FST.GRP_DIRECT_TOTAL,                         \n");
        SQL.append(" FST.GRP_INDIRECT_TOTAL, FST.GRP_PRE_TOTAL_EXPOSURE, FST.GRP_TOTAL_EXPOSURE, FST.UPC_FORMAT_NUM,                         \n");
        SQL.append(" CCT.CODE_ID, CCT.CODE_DESC                         \n");
        SQL.append("FROM CASV1_FACILITY_STATUS_TABLE FST                                           \n");
        SQL.append(" LEFT JOIN CASV1_COMMON_CODES_TABLE CCT ON CCT.CODE_ID = FST.CURRENT_USER_STATUS                                      \n");
        SQL.append(" WHERE REF_NUM =:refNo AND FST.CURRENT_USER_STATUS =:status       \n");
        SQL.append(" AND cct.CODE_TYPE = '50' \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FacilityStatusDTO>>() {
            @Override
            public List<FacilityStatusDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    FacilityStatusDTO facilityStatusDTO = new FacilityStatusDTO();
                    try {
                        facilityStatusDTO.setRefNo(rs.getString("REF_NUM"));
                        facilityStatusDTO.setFacilityDate(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("FACILITY_DATE")));
                        facilityStatusDTO.setDirectTotal(rs.getBigDecimal("DIRECT_TOTAL"));
                        facilityStatusDTO.setIndirectTotal(rs.getBigDecimal("INDIRECT_TOTAL"));
                        facilityStatusDTO.setTotalExposure(rs.getBigDecimal("TOTAL_EXPOSURE"));
                        facilityStatusDTO.setPreDirectTotal(rs.getBigDecimal("PRE_DIRECT_TOTAL"));
                        facilityStatusDTO.setPreIndirectTotal(rs.getBigDecimal("PRE_INDIRECT_TOTAL"));
                        facilityStatusDTO.setPreTotalExposure(rs.getBigDecimal("PRE_TOTAL_EXPOSURE"));
                        facilityStatusDTO.setGrpPreDirectTotal(rs.getBigDecimal("GRP_PRE_DIRECT_TOTAL"));
                        facilityStatusDTO.setGrpPreIndirectTotal(rs.getBigDecimal("GRP_PRE_INDIRECT_TOTAL"));
                        facilityStatusDTO.setGrpDirectTotal(rs.getBigDecimal("GRP_DIRECT_TOTAL"));
                        facilityStatusDTO.setGrpIndirectTotal(rs.getBigDecimal("GRP_INDIRECT_TOTAL"));
                        facilityStatusDTO.setGrpPreTotalExposure(rs.getBigDecimal("GRP_PRE_TOTAL_EXPOSURE"));
                        facilityStatusDTO.setGrpTotalExposure(rs.getBigDecimal("GRP_TOTAL_EXPOSURE"));
                        facilityStatusDTO.setUpcFormatNum(rs.getString("UPC_FORMAT_NUM"));
                        facilityStatusDTO.setStatus(rs.getInt("CODE_ID"));
                        facilityStatusDTO.setStatusText(rs.getString("CODE_DESC"));
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(facilityStatusDTO);
                }
                return resultSet;
            }
        });
    }

    public String getCommonCodeDescription(Integer codeType, Integer codeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("codeType", codeType);
        params.put("codeId",codeId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT CODE_DESC                      \n");
        SQL.append("FROM CASV1_COMMON_CODES_TABLE                                           \n");
        SQL.append(" WHERE CODE_TYPE =:codeType AND CODE_ID =:codeId       \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String returnCount = "";
                while (rs.next()) {
                    returnCount = rs.getString("CODE_DESC");
                }
                return returnCount;
            }
        });
    }

    public List<FacilityTotalDTO> getFPFacilities(FPRequestDTO fpRequestDTO){

        List<FacilityTotalDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", fpRequestDTO.getRefNo());
        params.put("facilityDate", fpRequestDTO.getFacilityDate());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT DISTINCT CFT.FACILITY_ID, CFT.VALUE, CFT.PRE_VALUE, CFT.SUBLIMIT, CFT.FACILITY_TYPE, CFT.CURRENCY, CFT.ONEOFF,       \n");
        SQL.append(" CFT.SIGHT_USANCE, CFT.SECTOR_REF, CFT.SUB_SECTOR_REF, CFT.PRPS_ADVNC_REF, CF.NAME AS FACILITY_NAME, CCT.CURRENCY_CODE AS CURRENCY_CODE                   \n");
        SQL.append(" FROM CASV1_FACILITY_TOTALS CFT      \n");
        SQL.append(" INNER JOIN CASV1_FACILITY_FIELDS CFF ON CFF.FACILITY_NO = CFT.FACILITY_ID                          \n");
        SQL.append(" AND CFF.CUSTOMER_REF =:refNo AND CFF.FACILITY_DATE = TO_DATE(:facilityDate, 'DD-MM-YYYY')            \n");
        SQL.append(" INNER JOIN CASV1_FACILITY_TABLE CF ON CF.NO = CFF.FACILITY_ID                                      \n");
        SQL.append(" INNER JOIN CASV1_CURRENCY_TABLE CCT ON CCT.CURRENCY_NO = CFT.CURRENCY                                      \n");
        SQL.append(" WHERE CFT.CUSTOMER_REF =:refNo AND CFT.FACILITY_DATE =TO_DATE(:facilityDate , 'DD-MM-YYYY')        \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<FacilityTotalDTO>>() {
            @Override
            public List<FacilityTotalDTO> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    FacilityTotalDTO facilityTotalDTO = new FacilityTotalDTO();
                    try {
                        facilityTotalDTO.setFacilityID(rs.getInt("FACILITY_ID"));
                        facilityTotalDTO.setValue(rs.getString("VALUE"));
                        facilityTotalDTO.setPreValue(rs.getString("PRE_VALUE"));
                        facilityTotalDTO.setSublimit(rs.getInt("SUBLIMIT"));
                        facilityTotalDTO.setFacilityType(rs.getString("FACILITY_TYPE"));
                        facilityTotalDTO.setCurrency(rs.getString("CURRENCY_CODE"));
                        facilityTotalDTO.setOneOff(rs.getInt("ONEOFF"));
                        facilityTotalDTO.setSightUsance(rs.getString("SIGHT_USANCE"));
                        facilityTotalDTO.setSectorRef(rs.getString("SECTOR_REF"));
                        facilityTotalDTO.setSubSectorRef(rs.getString("SUB_SECTOR_REF"));
                        facilityTotalDTO.setPrpsAdvncRef(rs.getString("PRPS_ADVNC_REF"));
                        facilityTotalDTO.setFacilityName(rs.getString("FACILITY_NAME"));
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(facilityTotalDTO);
   }
                return resultSet;
            }
        });
    }

    public List<AttachmentDetailDTO> getAttachmentDetails(String refNo){

        List<AttachmentDetailDTO> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", refNo);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT REF_NUM, FACILITY_DATE, FILE_NAME, TYPE, FILE_CONTENT,                     \n");
        SQL.append(" SECTION_ID, LAST_MODIFIED_DATE, PAGE_ORIENTATION,                         \n");
        SQL.append(" PAGE_BREAK_AFTER, MODIFIED_USER                          \n");
        SQL.append("FROM CASV1_ATTACHMENTS_TABLE                                           \n");
        SQL.append(" WHERE REF_NUM =:refNo       \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<AttachmentDetailDTO>>() {
            @Override
            public List<AttachmentDetailDTO> extractData(ResultSet rs) throws SQLException {
                while(rs.next()){
                    AttachmentDetailDTO attachmentDetailDTO = new AttachmentDetailDTO();
                    try {
                        Timestamp facilityDate = rs.getTimestamp("FACILITY_DATE");
                        attachmentDetailDTO.setFileName(rs.getString("FILE_NAME"));
                        attachmentDetailDTO.setSectionID(rs.getInt("SECTION_ID"));
                        Timestamp lastModifiedDate = rs.getTimestamp("LAST_MODIFIED_DATE");
                        attachmentDetailDTO.setLastModifiedDate(lastModifiedDate != null ? CalendarUtil.getDefaultFormattedDateOnly(lastModifiedDate) : null);
                    } catch (Exception ex){
                        LOG.error("ERROR : ", ex);
                    }
                    resultSet.add(attachmentDetailDTO);

                }
                return resultSet;
            }
        });
    }

    public List<String> getFPFacilityTexts(FPRequestDTO fpRequestDTO){

        List<String> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", fpRequestDTO.getRefNo());
        params.put("facilityDate", fpRequestDTO.getFacilityDate());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT COLUMN_VALUE FROM TABLE(CAST(GET_CASV1_FACILITY_BODY(:refNo,:facilityDate)AS STR_LST))                     \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<String>>() {
            @Override
            public List<String> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    String result = "";
                    try {
                        result = rs.getString("COLUMN_VALUE");
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(result);
                }
                return resultSet;
   }
                
        });
    }

    public List<AttachmentDetailDTO> getAttachments(String refNo, String paperDate, String upcFormat) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cct.CODE_ID AS SECTION_ID, cct.CODE_DESC, \n");
        sql.append("       MAX(catt.LAST_MODIFIED_DATE) AS LAST_MODIFIED_DATE, \n");
        sql.append("       MAX(catt.FILE_NAME) AS FILE_NAME \n");
        sql.append("FROM CASV1_COMMON_CODES_TABLE cct \n");
        sql.append("LEFT JOIN CASV1_ATTACHMENTS_TABLE catt \n");
        sql.append("ON cct.CODE_ID = catt.SECTION_ID \n");
        sql.append("AND catt.REF_NUM = :refNo \n");
        sql.append("AND catt.FACILITY_DATE = TO_DATE(:paperDate, 'dd/MM/yy') \n");
        sql.append("WHERE cct.CODE_TYPE = :upcFormat \n");
        sql.append("GROUP BY cct.CODE_ID, cct.CODE_DESC, cct.ORDER_ID \n");
        sql.append("ORDER BY cct.ORDER_ID");

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", refNo);
        params.put("paperDate", paperDate);
        params.put("upcFormat", upcFormat);

        List<AttachmentDetailDTO> attachments = namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<AttachmentDetailDTO>>() {
            @Override
            public List<AttachmentDetailDTO> extractData(ResultSet rs) throws SQLException {
                List<AttachmentDetailDTO> results = new ArrayList<>();
                while (rs.next()) {
                    AttachmentDetailDTO attachment = new AttachmentDetailDTO();
                    attachment.setSectionID(rs.getInt("SECTION_ID"));
                    attachment.setCodeDesc(rs.getString("CODE_DESC"));
                    attachment.setFileName(rs.getString("FILE_NAME") != null ? rs.getString("FILE_NAME") : "");
                    Timestamp lastModifiedDate = rs.getTimestamp("LAST_MODIFIED_DATE");
                    attachment.setLastModifiedDate(lastModifiedDate != null ? lastModifiedDate.toString() : "No Record");
                    results.add(attachment);
                }
                return results;
            }
        });

        //  Fetch FILE_CONTENT separately
        for (AttachmentDetailDTO attachment : attachments) {
            byte[] fileContent = fetchFileContent(refNo, paperDate, attachment.getSectionID());
            attachment.setFileContent(fileContent);
        }

        return attachments;
    }

    public List<String> getFPSecurityTexts(FPRequestDTO fpRequestDTO){

        List<String> resultSet = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", fpRequestDTO.getRefNo());
        params.put("facilityDate", fpRequestDTO.getFacilityDate());
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT COLUMN_VALUE FROM TABLE(CAST(GET_CASV1_SECURITY_BODY(:refNo,:facilityDate)AS STR_LST))                     \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<String>>() {
            @Override
            public List<String> extractData(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    String result = "";
                    try {
                        result = rs.getString("COLUMN_VALUE");
                    } catch (Exception e) {
                        LOG.error("ERROR : ", e);
                    }

                    resultSet.add(result);
                }
                return resultSet;
            }
        });
    }

    private byte[] fetchFileContent(String refNo, String paperDate, int sectionId) {
        String sql = "SELECT FILE_CONTENT FROM CASV1_ATTACHMENTS_TABLE " +
                "WHERE REF_NUM = :refNo " +
                "AND FACILITY_DATE = TO_DATE(:paperDate, 'dd/MM/yy') " +
                "AND SECTION_ID = :sectionId";

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", refNo);
        params.put("paperDate", paperDate);
        params.put("sectionId", sectionId);

        return namedParameterJdbcTemplate.query(sql, params, rs -> {
            if (rs.next()) {
                return rs.getBytes("FILE_CONTENT");
            }
            return null;
        });
    }

    public List<SectionDetailDTO> getDropdownSections(String upcFormatNum) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODE_ID, CODE_DESC \n");
        sql.append("FROM CASV1_COMMON_CODES_TABLE \n");
        sql.append("WHERE CODE_TYPE = :upcFormatNum \n");
        sql.append("ORDER BY ORDER_ID");

        Map<String, Object> params = new HashMap<>();
        params.put("upcFormatNum", upcFormatNum);

        return namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<SectionDetailDTO>>() {
            @Override
            public List<SectionDetailDTO> extractData(ResultSet rs) throws SQLException {
                List<SectionDetailDTO> sections = new ArrayList<>();
                while (rs.next()) {
                    SectionDetailDTO section = new SectionDetailDTO();
                    section.setCodeId(rs.getInt("CODE_ID"));
                    section.setCodeDesc(rs.getString("CODE_DESC"));
                    sections.add(section);
                }
                return sections;
            }
        });
    }

    public List<CommentTableDTO> getComments(String refNo, String paperDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cct.CODE_ID AS STATE, cct.CODE_DESC, \n");
        sql.append("       catt.REMARK_DATE, \n");
        sql.append("       catt.REMARK_1 AS COMMENT_TEXT, \n");
        sql.append("       catt.USER_ID, \n");
        sql.append("       catt.STATE AS COMMENT_TYPE, \n");
        sql.append("       catt.FACILITY_DATE, \n");
        sql.append("       catt.REF_NUM, \n");
        sql.append("       catt.TYPE \n");
        sql.append("FROM CASV1_COMMON_CODES_TABLE cct \n");
        sql.append("LEFT JOIN CASV1_COMMENTS_TABLE catt \n");
        sql.append("ON TO_NUMBER(cct.CODE_ID) = TO_NUMBER(catt.STATE) \n");
        sql.append("AND catt.REF_NUM = :refNo \n");
        sql.append("WHERE cct.CODE_TYPE = '50' \n");
        sql.append("AND catt.FACILITY_DATE = TO_DATE(:paperDate, 'DD-MM-YY') \n");
        sql.append("ORDER BY cct.CODE_ID");

        Map<String, Object> params = new HashMap<>();
        params.put("refNo", refNo);
        params.put("paperDate", paperDate);

        return namedParameterJdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<CommentTableDTO>>() {
            @Override
            public List<CommentTableDTO> extractData(ResultSet rs) throws SQLException {
                List<CommentTableDTO> results = new ArrayList<>();
                while (rs.next()) {
                    CommentTableDTO comment = new CommentTableDTO();
                    comment.setUserID(rs.getString("USER_ID"));
                    comment.setRefNo(rs.getString("REF_NUM"));
                    comment.setType(rs.getString("TYPE"));
                    comment.setRemark1(rs.getString("COMMENT_TEXT"));
                    comment.setState(rs.getInt("STATE"));
                    comment.setCodeDesc(rs.getString("CODE_DESC"));

                    Timestamp remarkDate = rs.getTimestamp("REMARK_DATE");
                    comment.setRemarkDate(remarkDate != null ? remarkDate.toString() : "No Record");

                    Timestamp facilityDate = rs.getTimestamp("FACILITY_DATE");
                    comment.setFacilityDate(facilityDate != null ? facilityDate.toString() : "No Record");

                    results.add(comment);
                }
                return results;
            }
        });
    }


}
