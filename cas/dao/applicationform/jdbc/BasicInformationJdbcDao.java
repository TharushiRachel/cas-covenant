package com.itechro.cas.dao.applicationform.jdbc;

import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.model.dto.applicationform.AFCribReportUpdateRQ;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BasicInformationJdbcDao extends BaseJDBCDao {

    public Long getApplicationFormActiveBasicInformationRecordCount(AFCribReportUpdateRQ afCribReportUpdateRQ) {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("applicationFormID", afCribReportUpdateRQ.getApplicationFormID());
        paramsMap.put("identificationNo", afCribReportUpdateRQ.getIdentificationNo().trim().toUpperCase());
        paramsMap.put("identificationType", afCribReportUpdateRQ.getIdentificationType().name());
        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT BASIC_INFORMATION_ID                                    \n");
        SQL.append(" FROM T_AF_BASIC_INFORMATION                                    \n");
        SQL.append(" WHERE APPLICATION_FORM_ID = :applicationFormID                 \n");
        SQL.append("       AND upper(IDENTIFICATION_NO) = :identificationNo         \n");
        SQL.append("       AND upper(IDENTIFICATION_TYPE) = :identificationType     \n");
        SQL.append("       AND STATUS = 'ACT'                                       \n");

        return namedParameterJdbcTemplate.queryForObject(SQL.toString(), paramsMap, Long.class);
    }
}
