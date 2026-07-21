package com.itechro.cas.dao.lead.jdbc;

import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.lead.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class DigitalApplicationJdbcDao {

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final NamedParameterJdbcTemplate jdbc;
    private static final DateTimeFormatter DOB_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DigitalApplicationJdbcDao(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Fetch all parties (applicants) for given COMP_LEAD_ID
     * from T_COMP_PARTIES and map to ApplicantDetailsDTO.
     * Java 8 compatible.
     */
    public SDFacilityPaperPreviewDTO fetchPreviewByCompLeadId(Long compLeadId, List<Long> compPartyIds) {
        String sql = buildSql(compPartyIds != null && !compPartyIds.isEmpty());

        Map<String, Object> params = new HashMap<>();
        params.put("compLeadId", compLeadId);

        if (compPartyIds != null && !compPartyIds.isEmpty()) {
            params.put("ids", compPartyIds);
        }

        return jdbc.query(sql, params, buildExtractor());
    }

    private String buildSql(boolean filterByIds) {
        String base =  "WITH addr AS (\n" +
                "  SELECT a.COMP_PARTY_ID,\n" +
                "         a.ADDRESS1,\n" +
                "         a.ADDRESS2,\n" +
                "         a.CITY,\n" +
                "         ROW_NUMBER() OVER (\n" +
                "            PARTITION BY a.COMP_PARTY_ID\n" +
                "            ORDER BY CASE WHEN NVL(a.STATUS,'ACT') = 'ACT' THEN 0 ELSE 1 END,\n" +
                "                     a.ADDRESSES_ID DESC\n" +
                "         ) AS rn\n" +
                "  FROM T_COMP_PARTY_ADDRESSES a\n" +
                "), idn AS (\n" +
                "  SELECT i.COMP_PARTY_ID,\n" +
                "         i.IDENTIFICATION_NUMBER,\n" +
                "         i.IDENTIFICATION_TYPE,\n" +
                "         ROW_NUMBER() OVER (\n" +
                "            PARTITION BY i.COMP_PARTY_ID\n" +
                "            ORDER BY CASE WHEN UPPER(i.IDENTIFICATION_TYPE) IN ('NIC','NIC_NO','NATIONAL_ID') THEN 0 ELSE 1 END,\n" +
                "                     CASE WHEN NVL(i.STATUS,'ACT') = 'ACT' THEN 0 ELSE 1 END,\n" +
                "                     i.IDENTIFICATION_ID DESC\n" +
                "         ) AS rn\n" +
                "  FROM T_COMP_PARTY_IDENTIFICATIONS i\n" +
                ")\n" +
                "SELECT l.PREFERRED_BRANCH AS BRANCH_NAME,\n" +
                "       p.ACCOUNT_NUMBER   AS ACCOUNT_NO,\n" +
                "       p.COMP_PARTY_ID,\n" +
                "       p.PARTY_TYPE,\n" +
                "       p.PERSONAL_NAME,\n" +
                "       p.EMAIL,\n" +
                "       p.MOBILE_NUMBER,\n" +
                "       p.DATE_OF_BIRTH,\n" +
                "       p.CIVIL_STATUS,\n" +
                "       p.CONTACT_PERSON,\n" +
                "       a.ADDRESS1,\n" +
                "       a.ADDRESS2,\n" +
                "       a.CITY,\n" +
                "       i.IDENTIFICATION_NUMBER\n" +
                "FROM T_COMP_LEAD l\n" +
                "JOIN T_COMP_PARTIES p ON p.COMP_LEAD_ID = l.COMP_LEAD_ID\n" +
                "LEFT JOIN addr a ON a.COMP_PARTY_ID = p.COMP_PARTY_ID AND a.rn = 1\n" +
                "LEFT JOIN idn i ON i.COMP_PARTY_ID = p.COMP_PARTY_ID AND i.rn = 1\n" +
                "WHERE l.COMP_LEAD_ID = :compLeadId\n" +
                "  AND NVL(p.STATUS, 'ACTIVE') <> 'DELETE'\n";

        if (filterByIds) {
            base += " AND p.COMP_PARTY_ID IN (:ids) \n";
        }
        base += " ORDER BY p.COMP_PARTY_ID";
        return base;

    }

    private ResultSetExtractor<SDFacilityPaperPreviewDTO> buildExtractor() {
        return rs -> {
            SDFacilityPaperPreviewDTO preview = new SDFacilityPaperPreviewDTO();
            List<ApplicantDetailsDTO> applicants = new ArrayList<>();

            while (rs.next()) {
                setPreviewFields(preview, rs);
                applicants.add(mapApplicant(rs));
            }

            preview.setApplicantsDetails(applicants);
            return preview;
        };
    }

    private void setPreviewFields(SDFacilityPaperPreviewDTO preview, ResultSet rs) throws SQLException {
        if (preview.getBranchName() == null) {
            preview.setBranchName(rs.getString("BRANCH_NAME"));
        }
        if (preview.getAccountNo() == null || preview.getAccountNo().trim().isEmpty()) {
            String acc = rs.getString("ACCOUNT_NO");
            if (acc != null && !acc.trim().isEmpty()) {
                preview.setAccountNo(acc);
            }
        }
    }

    private ApplicantDetailsDTO mapApplicant(ResultSet rs) throws SQLException {
        ApplicantDetailsDTO dto = new ApplicantDetailsDTO();
        dto.setFullName(rs.getString("PERSONAL_NAME"));

        java.sql.Date dob = rs.getDate("DATE_OF_BIRTH");
        dto.setDob(dob != null ? dob.toLocalDate().format(DOB_FMT) : "");

        dto.setNic(nvlToEmpty(rs.getString("IDENTIFICATION_NUMBER")));

        String fullAddr = joinNonBlank(rs.getString("ADDRESS1"),
                joinNonBlank(rs.getString("ADDRESS2"), rs.getString("CITY")));
        dto.setPermanentAddress1(fullAddr);
        dto.setPermanentAddress2("");

        dto.setCurrentAddressType("");
        dto.setYearsAtAddress(null);
        dto.setCurrentAddressOther("");
        dto.setCommunicationAddress("");
        dto.setResidencePhone("");
        dto.setOfficePhone("");

        dto.setEmail(rs.getString("EMAIL"));
        dto.setMobilePhone(rs.getString("MOBILE_NUMBER"));

        dto.setContactPersonName(rs.getString("CONTACT_PERSON"));
        dto.setContactPersonMobile("");
        dto.setContactPersonRelationship("");

        dto.setIsPep("");
        dto.setPepDescription("");
        dto.setHighestAcademicQualification("");
        dto.setProfessionalQualification("");
        dto.setNoOfChildren("");

        dto.setCivilStatus(rs.getString("CIVIL_STATUS"));
        dto.setCivilStatusDescription("");

        dto.setNationality(Collections.emptyList());

        return dto;
    }


    private static String nvlToEmpty(String s) {
        return s == null ? "" : s;
    }

    private static String joinNonBlank(String p1, String p2) {
        String t1 = p1 == null ? "" : p1.trim();
        String t2 = p2 == null ? "" : p2.trim();
        if (t1.isEmpty() && t2.isEmpty()) return "";
        if (t1.isEmpty()) return t2;
        if (t2.isEmpty()) return t1;
        return t1 + ", " + t2;
    }

    public List<DigitalApplicationDTO> getDigitalApplicationByLead(Integer leadId) throws AppsException {

        List<DigitalApplicationDTO> resultSet = new ArrayList<>();

        try {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("leadId", leadId);
            StringBuilder SQL = new StringBuilder();

            SQL.append("SELECT * FROM T_DIGITAL_APPLICATION_FORM \n");
            SQL.append(" WHERE LEAD_ID = :leadId  \n");
            SQL.append("   AND NVL(DOCUMENT_STATUS, 'ACTIVE') <> 'DELETE' \n");
            return namedParameterJdbcTemplate.query(SQL.toString(), paramsMap, new ResultSetExtractor<List<DigitalApplicationDTO>>() {
                @Override
                public List<DigitalApplicationDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    while (rs.next()) {
                        DigitalApplicationDTO dataRow = new DigitalApplicationDTO();

                        dataRow.setDigitalApplicationID(rs.getInt("DIGITAL_APPLICATION_FORM_ID"));
                        dataRow.setLeadID(rs.getInt("LEAD_ID"));
                        dataRow.setDocumentContent(rs.getString("DOCUMENT_CONTENT"));
                        resultSet.add(dataRow);

                    }
                    return resultSet;
                }
            });
        } catch (Exception e) {
            return resultSet;
        }
    }

    public List<FacilityDTO> fetchFacilitiesByCompLeadId(Long compLeadId) {
        String sql =
                "SELECT " +
                        "  f.FACILITY_TEMPLATE_ID, " +
                        "  t.CREDIT_FACILITY_NAME AS FACILITY_TEMPLATE_NAME, " +
                        "  f.FACILITY_DESCRIPTION, " +
                        "  f.REQUESTED_TENURE, " +
                        "  f.LEASE_AMOUNT, " +
                        "  f.REPAYMENT_MODE " +
                        "  FROM T_COMP_FACILITIES f " +
                        "  LEFT JOIN T_CREDIT_FACILITY_TEMPLATE t " +
                        "  ON t.CREDIT_FACILITY_TEMPLATE_ID = f.FACILITY_TEMPLATE_ID " +
                        // " AND NVL(t.STATUS, 'ACT') <> 'DEL' " +
                        "  WHERE f.COMP_LEAD_ID = :compLeadId " +
                        "  AND NVL(f.STATUS, 'ACTIVE') <> 'DELETED' " +
                        "  ORDER BY f.COMP_FACILITY_ID";

        Map<String, Object> params = new HashMap<>();
        params.put("compLeadId", compLeadId);

        return namedParameterJdbcTemplate.query(sql, params, rs -> {
            List<FacilityDTO> list = new ArrayList<>();
            while (rs.next()) {
                FacilityDTO dto = new FacilityDTO();
                dto.setFacilityTemplateId(
                        rs.getObject("FACILITY_TEMPLATE_ID") == null ? "" : rs.getString("FACILITY_TEMPLATE_ID")
                );
                dto.setFacilityTemplateName(rs.getString("FACILITY_TEMPLATE_NAME"));

                dto.setFacilityDescription(rs.getString("FACILITY_DESCRIPTION"));
                dto.setRequestedTenure(
                        rs.getObject("REQUESTED_TENURE") == null ? "" : rs.getString("REQUESTED_TENURE")
                );
                dto.setLeaseAmount(
                        rs.getBigDecimal("LEASE_AMOUNT") == null ? "0" : rs.getBigDecimal("LEASE_AMOUNT").toPlainString()
                );
                dto.setRepaymentMode(rs.getString("REPAYMENT_MODE"));
                list.add(dto);
            }
            return list;
        });

    }

    public void markPartiesAsDigitalAppCreated(Long compLeadId, List<Long> compPartyIds) {
        if (compPartyIds == null || compPartyIds.isEmpty()) return;

        String sql =
                "UPDATE T_COMP_PARTIES " +
                        "   SET IS_DIGITAL_APPLICATION_CREATED = 'Y', MODIFIED_DATE = SYSDATE " + // (optional audit)
                        " WHERE COMP_LEAD_ID = :compLeadId " +
                        "   AND COMP_PARTY_ID IN (:ids) " +
                        "   AND NVL(STATUS,'ACTIVE') <> 'DELETED'";

        Map<String, Object> params = new HashMap<>();
        params.put("leadId", compLeadId.longValue());
        params.put("ids", compPartyIds);

        namedParameterJdbcTemplate.update(sql, params);
    }


}


