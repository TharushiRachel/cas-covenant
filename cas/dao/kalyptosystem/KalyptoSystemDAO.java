package com.itechro.cas.dao.kalyptosystem;

import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoRatingEvalDTO;
import com.itechro.cas.model.dto.kalyptosystem.KalyptoGeneralDTO;
import com.itechro.cas.model.dto.kalyptosystem.KalyptoValuesDTO;
import com.itechro.cas.model.dto.kalyptosystem.kalyptoPeriodValues;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoDataSaveRQ;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoRQ;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoValuesRQ;
import com.itechro.cas.model.dto.kalyptosystem.response.KalyptoDTO;
import com.itechro.cas.model.dto.kalyptosystem.response.KalyptoDataDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class KalyptoSystemDAO {
    private static final Logger LOG = LoggerFactory.getLogger(KalyptoSystemDAO.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public KalyptoSystemDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean isExistKalyptoDataDAO(KalyptoRQ kalyptoRQ) {
        boolean isExist = false;
        Integer recordCount = 0;

        StringBuilder sql = new StringBuilder();
        try {
            String customerId = kalyptoRQ.getCustomerId();
            String facilityId = kalyptoRQ.getFacilityId();

            sql.append(" SELECT COUNT(*) FROM T_KALYPTO_COMMON WHERE CUSTOMER_ID = :customerId AND FACILITY_ID = :facilityId");

            recordCount = jdbcTemplate.queryForObject(sql.toString(), Integer.class, customerId, facilityId);

            if (recordCount > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            LOG.error("ERROR : {}", e);
        }
        return isExist;
    }

    public KalyptoDTO getKalyptoColomnsDAO(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) {
        KalyptoDTO result = new KalyptoDTO();
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        try {
            LOG.info("START : Kalypto Details By customerId Repository: {} , user {} ", kalyptoRQ, credentialsDTO);

            sql.append(" SELECT ID,PERIOD_ID,HEADER_TEXT,CUSTOMER_ID,FACILITY_ID,IS_NEW_ADDED FROM T_KALYPTO_PERIOD " +
                    " WHERE CUSTOMER_ID = :customerId AND FACILITY_ID = :facilityId ");

            String customerId = kalyptoRQ.getCustomerId();
            String facilityId = kalyptoRQ.getFacilityId();

            result = (KalyptoDTO) jdbcTemplate.queryForObject(sql.toString(), new Object[]{customerId, facilityId}, new BeanPropertyRowMapper(KalyptoDTO.class));
            LOG.info("END : Kalypto Details By Customer ID Repository: {} , user {} ", result, credentialsDTO);

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return result;
    }

    public Boolean isAddedNewKalyptoValuesDAO(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START : is Added New Kalypto Values Repository: {} , kalyptoRQ {}, user {}", kalyptoRQ, credentialsDTO);
        StringBuilder SQL = new StringBuilder();
        Boolean isExist = false;
        Integer recordCount = 0;
        try {
            SQL.append(" SELECT COUNT(*) " +
                    " FROM T_KALYPTO_PERIOD WHERE CUSTOMER_ID = :customerId  AND FACILITY_ID =:facilityId AND IS_NEW_ADDED = 1");

            recordCount = jdbcTemplate.queryForObject(SQL.toString(), Integer.class, kalyptoRQ.getCustomerId(), kalyptoRQ.getFacilityId());

            if (recordCount > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isExist;
    }

    public List<KalyptoDataDTO> getKalyptoRowByKalyptoIdDAO(Integer kalyptoId, CredentialsDTO credentialsDTO) {
        List<KalyptoDataDTO> resultArray = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        try {
            LOG.info("START : Kalypto Rows By Customer ID Repository: {} , user {} ", kalyptoId, credentialsDTO);

            params.put("kalyptoId", kalyptoId);

            SQL.append(" SELECT ID,PARAMETER_ID,KALYPTO_ID,VALUE_NUMERIC,VALUE_PERCENTAGE,VALUE_TEXT,PERIOD_ID " +
                    " FROM T_KALYPTO_DATA WHERE KALYPTO_ID = :kalyptoId ");
            resultArray = namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<KalyptoDataDTO>() {
                public KalyptoDataDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                    KalyptoDataDTO kalyptoDataDTO = new KalyptoDataDTO();
                    kalyptoDataDTO.setKalyptoDataId(rs.getInt("ID"));
                    kalyptoDataDTO.setParameterId(rs.getInt("PARAMETER_ID"));
                    kalyptoDataDTO.setKalyptoId(rs.getInt("KALYPTO_ID"));
                    kalyptoDataDTO.setValueNumeric(rs.getInt("VALUE_NUMERIC"));
                    kalyptoDataDTO.setPercentage(rs.getInt("VALUE_PERCENTAGE"));
                    kalyptoDataDTO.setValueText(rs.getString("VALUE_TEXT"));
                    kalyptoDataDTO.setPeriodId(rs.getInt("PERIOD_ID"));

                    return kalyptoDataDTO;
                }
            });

            LOG.info("END : Kalypto Rows By Customer ID Repository: {} , user {} ", resultArray, credentialsDTO);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return resultArray;
    }

    public Integer generatePeriodId() {
        Integer periodId = 0;
        StringBuilder SQL = new StringBuilder();
        try {
            SQL.append(" SELECT SEQ_ID_KALYPTO_PERIOD_ID.nextval FROM DUAL ");

            Integer result = jdbcTemplate.queryForObject(SQL.toString(), Integer.class);

            periodId = Integer.parseInt("-".concat(String.valueOf(result)));

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return periodId;
    }

    public Integer generateParameterId() {
        Integer parameterId = 0;
        StringBuilder SQL = new StringBuilder();
        try {
            SQL.append(" SELECT SEQ_ID_KALYPTO_PARAMETER_ID.nextval FROM DUAL ");

            Integer result = jdbcTemplate.queryForObject(SQL.toString(), Integer.class);

            parameterId = Integer.parseInt("-".concat(String.valueOf(result)));

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return parameterId;
    }

    public Integer generateKalyptoId() {
        Integer kalyptoId = 0;
        StringBuilder SQL = new StringBuilder();
        try {
            SQL.append(" SELECT SEQ_T_KALYPTO.nextval FROM DUAL ");

            kalyptoId = jdbcTemplate.queryForObject(SQL.toString(), Integer.class);

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return kalyptoId;
    }

    public Boolean saveNewKALYPTOSystemDAO(KalyptoValuesRQ kalyptoSaveRQ, CredentialsDTO credentialsDTO) {
        StringBuilder SQL = new StringBuilder();

        Map<String, Object> paramMap = new HashMap<>();
        Boolean isSucess = false;
        int result = 0;
        try {
            SQL.append(" INSERT INTO T_KALYPTO_PERIOD " +
                    " (ID, PERIOD_ID ,HEADER_TEXT ,CUSTOMER_ID ,FACILITY_ID ,IS_NEW_ADDED) " +
                    " VALUES(SEQ_T_KALYPTO.nextval, :periodId, :headerText, :customerId, :facilityId, 1) ");

            paramMap.put("kalyptoId", kalyptoSaveRQ.getKalyptoId());
            paramMap.put("periodId", kalyptoSaveRQ.getPeriodId());
            paramMap.put("headerText", kalyptoSaveRQ.getColumnName());
            paramMap.put("customerId", kalyptoSaveRQ.getCustomerId());
            paramMap.put("facilityId", kalyptoSaveRQ.getFacilityId());

            LOG.info("START : Kalypto Rows By Customer ID Repository: {} , user {} ", 23, credentialsDTO);

            result = namedParameterJdbcTemplate.update(SQL.toString(), paramMap);

            if (result > 0) {
                isSucess = true;
            } else {
                isSucess = false;
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSucess;
    }

    public Boolean saveKalyptoDataDAO(KalyptoDataSaveRQ kalyptoDataSaveRQ, CredentialsDTO credentialsDTO) {
        StringBuilder SQL = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<>();
        Boolean isSucess = false;
        int result = 0;

        try {
            SQL.append(" INSERT INTO T_KALYPTO_PARAMS " +
                    " (ID,PARAMETER_ID,IS_NEW_ADDED,VALUE_NUMERIC,VALUE_PERCENTAGE,VALUE_TEXT,PERIOD_ID,PARAMETER_NAME,CUSTOMER_ID,FACILITY_ID) " +
                    " VALUES(SEQ_T_KALYPTO_DATA.nextval, :parameterId , :isNewAdded , :valueNumberic, :valuePercentage , :valueText , :periodId ,:parameterName,:customerId,:facilityId)");

            paramMap.put("isNewAdded", kalyptoDataSaveRQ.getIsAddedNew());
            paramMap.put("parameterId", kalyptoDataSaveRQ.getParameterId());
            paramMap.put("valueNumberic", kalyptoDataSaveRQ.getValueNumberic());
            paramMap.put("valuePercentage", kalyptoDataSaveRQ.getValuePercentage());
            paramMap.put("valueText", kalyptoDataSaveRQ.getValueText());
            paramMap.put("periodId", kalyptoDataSaveRQ.getPeriodId());
            paramMap.put("parameterName", kalyptoDataSaveRQ.getParameterName());
            paramMap.put("customerId", kalyptoDataSaveRQ.getCustomerId());
            paramMap.put("facilityId", kalyptoDataSaveRQ.getFacilityId());

            result = namedParameterJdbcTemplate.update(SQL.toString(), paramMap);

            if (result > 0) {
                isSucess = true;
            } else {
                isSucess = false;
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSucess;
    }

    public boolean saveKalyptoGeneralDataDAO(KalyptoRatingEvalDTO kalyptoRatingEvalDTO, String customerId, String facilityId) {
        StringBuilder SQL = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<>();
        boolean isSucess = false;
        int result = 0;

        try {
            SQL.append(" INSERT INTO T_KALYPTO_COMMON " +
                    " (ID,COUNTER_PARTY_NAME,MODEL_NAME,RATING_SCORE,UNIQUE_IDENTIFIER,CUSTOMER_ID,FACILITY_ID) " +
                    " VALUES(SEQ_T_KALYPTO_GENERAL.nextval, :counterpartyName , :model , :ratingScore, :uniqueIdentifier ,:customerId,:facilityId)");

            paramMap.put("counterpartyName", kalyptoRatingEvalDTO.getCounterpartyName());
            paramMap.put("model", kalyptoRatingEvalDTO.getModel());
            paramMap.put("ratingScore", kalyptoRatingEvalDTO.getRatingscore());
            paramMap.put("uniqueIdentifier", kalyptoRatingEvalDTO.getUniqueIdentifier());
            paramMap.put("customerId", customerId);
            paramMap.put("facilityId", facilityId);

            result = namedParameterJdbcTemplate.update(SQL.toString(), paramMap);

            if (result > 0) {
                isSucess = true;
            } else {
                isSucess = false;
            }
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSucess;
    }

    public KalyptoGeneralDTO getKalyptoGeneralDataDAO(KalyptoRQ kalyptoRQ) {
        KalyptoGeneralDTO result = new KalyptoGeneralDTO();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        try {
            LOG.info("START : Kalypto Details By customerId Repository: {} , user {} ", kalyptoRQ);

            SQL.append(" SELECT ID,COUNTER_PARTY_NAME,MODEL_NAME,RATING_SCORE,UNIQUE_IDENTIFIER,CUSTOMER_ID,FACILITY_ID FROM T_KALYPTO_COMMON " +
                    " WHERE CUSTOMER_ID = :customerId AND FACILITY_ID = :facilityId ");

            String customerId = kalyptoRQ.getCustomerId();
            String facilityId = kalyptoRQ.getFacilityId();

            params.put("customerId", customerId);
            params.put("facilityId", facilityId);


            LOG.info("END : Kalypto Details By Customer ID Repository: {} ", result);

            result = namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<KalyptoGeneralDTO>() {
                @Override
                public KalyptoGeneralDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

                    KalyptoGeneralDTO kalyptoGeneralDTO = new KalyptoGeneralDTO();
                    if (rs.next()) {
                        kalyptoGeneralDTO.setId(rs.getInt("ID"));
                        kalyptoGeneralDTO.setCustomerId(rs.getString("CUSTOMER_ID"));
                        kalyptoGeneralDTO.setFacilityId(rs.getString("FACILITY_ID"));
                        kalyptoGeneralDTO.setCounterpartyName(rs.getString("COUNTER_PARTY_NAME"));
                        kalyptoGeneralDTO.setModel(rs.getString("MODEL_NAME"));
                        kalyptoGeneralDTO.setRatingscore(rs.getString("RATING_SCORE"));
                        kalyptoGeneralDTO.setUniqueIdentifier(rs.getString("UNIQUE_IDENTIFIER"));
                    }
                    return kalyptoGeneralDTO;
                }
            });

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return result;
    }


    public Boolean saveKalyptoPeriods(List<kalyptoPeriodValues> kalyptoPeriodValues, boolean isEdited, CredentialsDTO credentialsDTO) {
        StringBuilder sql = new StringBuilder();
        boolean isSucess = false;
        int[] results;
        try {
            if (isEdited) {
                sql.append(" MERGE INTO T_KALYPTO_PERIOD target USING\n" +
                        " (SELECT :periodId AS PERIOD_ID,:headerText  AS HEADER_TEXT,:customerId AS CUSTOMER_ID, :facilityId AS FACILITY_ID,:id AS ID,:isNewAdded AS IS_NEW_ADDED FROM DUAL) \n" +
                        " source ON (target.PERIOD_ID = source.PERIOD_ID AND target.CUSTOMER_ID = source.CUSTOMER_ID AND target.FACILITY_ID = source.FACILITY_ID AND target.ID = source.ID)\n" +
                        " WHEN MATCHED THEN\n" +
                        " UPDATE SET target.HEADER_TEXT = source.HEADER_TEXT \n" +
                        " WHEN NOT MATCHED THEN\n" +
                        " INSERT( ID, PERIOD_ID, HEADER_TEXT, CUSTOMER_ID, FACILITY_ID, IS_NEW_ADDED )\n" +
                        " VALUES( SEQ_T_KALYPTO.nextval,source.PERIOD_ID,source.HEADER_TEXT, source.CUSTOMER_ID, source.FACILITY_ID, source.IS_NEW_ADDED)");
            }else{
                sql.append(" MERGE INTO T_KALYPTO_PERIOD target USING\n" +
                        " (SELECT :periodId AS PERIOD_ID,:headerText  AS HEADER_TEXT,:customerId AS CUSTOMER_ID, :facilityId AS FACILITY_ID,:isNewAdded AS IS_NEW_ADDED FROM DUAL) \n" +
                        " source ON (target.PERIOD_ID = source.PERIOD_ID AND target.CUSTOMER_ID = source.CUSTOMER_ID AND target.FACILITY_ID = source.FACILITY_ID)\n" +
                        " WHEN MATCHED THEN\n" +
                        " UPDATE SET target.HEADER_TEXT = source.HEADER_TEXT \n" +
                        " WHEN NOT MATCHED THEN\n" +
                        " INSERT( ID, PERIOD_ID, HEADER_TEXT, CUSTOMER_ID, FACILITY_ID, IS_NEW_ADDED )\n" +
                        " VALUES( SEQ_T_KALYPTO.nextval,source.PERIOD_ID,source.HEADER_TEXT, source.CUSTOMER_ID, source.FACILITY_ID, source.IS_NEW_ADDED)");
            }

            MapSqlParameterSource[] batchParams = new MapSqlParameterSource[kalyptoPeriodValues.size()];
            for (int i = 0; i < kalyptoPeriodValues.size(); i++) {
                kalyptoPeriodValues periodToInsert = kalyptoPeriodValues.get(i);
                MapSqlParameterSource paramMaps = new MapSqlParameterSource();
                paramMaps.addValue("id", periodToInsert.getId());
                paramMaps.addValue("periodId", periodToInsert.getPeriodId());
                paramMaps.addValue("headerText", periodToInsert.getPeriodName());
                paramMaps.addValue("customerId", periodToInsert.getCustomerId());
                paramMaps.addValue("facilityId", periodToInsert.getFacilityId());
                paramMaps.addValue("isNewAdded", periodToInsert.getIsAddedNew());
                batchParams[i] = paramMaps;
            }

            results = namedParameterJdbcTemplate.batchUpdate(sql.toString(), batchParams);

            for (int result : results) {
                if (result > 0) {
                    isSucess = true;
                    break;
                }
            }
            LOG.info("START : Kalypto Rows By Customer ID Repository: {} , user {} ", 23, credentialsDTO);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSucess;
    }

    public Boolean saveKalyptoValues(List<KalyptoValuesDTO> kalyptoDataValues, Boolean isEdited, CredentialsDTO credentialsDTO) {
        StringBuilder sql = new StringBuilder();
        boolean isSucess = false;
        int[] results;
        try {
            if (isEdited) {
                sql.append("MERGE INTO T_KALYPTO_PARAMS target ")
                        .append("USING (SELECT :periodId AS PERIOD_ID, :parameterId AS PARAMETER_ID, :customerId AS CUSTOMER_ID, :valueNumeric AS VALUE_NUMERIC, :valuePercentage AS VALUE_PERCENTAGE, :ValueText AS VALUE_TEXT , :facilityId AS FACILITY_ID, :isAddedNew AS IS_NEW_ADDED ,:paramterName AS PARAMETER_NAME ,:id AS ID FROM DUAL) source ")
                        .append("ON (target.PARAMETER_ID = source.PARAMETER_ID AND target.CUSTOMER_ID = source.CUSTOMER_ID AND target.PERIOD_ID = source.PERIOD_ID AND target.FACILITY_ID = source.FACILITY_ID AND target.ID = source.ID AND target.IS_NEW_ADDED = source.IS_NEW_ADDED) ")
                        .append("WHEN MATCHED THEN ")
                        .append("UPDATE SET target.VALUE_NUMERIC = source.VALUE_NUMERIC , target.VALUE_PERCENTAGE = source.VALUE_PERCENTAGE, target.VALUE_TEXT = source.VALUE_TEXT, target.PARAMETER_NAME = source.PARAMETER_NAME  ")
                        .append("WHEN NOT MATCHED THEN ")
                        .append("INSERT (ID, PERIOD_ID, PARAMETER_ID, CUSTOMER_ID, VALUE_NUMERIC,VALUE_PERCENTAGE,VALUE_TEXT,PARAMETER_NAME,FACILITY_ID,IS_NEW_ADDED) ")
                        .append("VALUES (SEQ_T_KALYPTO_DATA.nextval, source.PERIOD_ID, source.PARAMETER_ID, source.CUSTOMER_ID, source.VALUE_NUMERIC, source.VALUE_PERCENTAGE, source.VALUE_TEXT, source.PARAMETER_NAME,source.FACILITY_ID,source.IS_NEW_ADDED)");
            } else {
                sql.append("MERGE INTO T_KALYPTO_PARAMS target ")
                        .append("USING (SELECT :periodId AS PERIOD_ID, :parameterId AS PARAMETER_ID, :customerId AS CUSTOMER_ID, :valueNumeric AS VALUE_NUMERIC, :valuePercentage AS VALUE_PERCENTAGE, :ValueText AS VALUE_TEXT , :facilityId AS FACILITY_ID, :isAddedNew AS IS_NEW_ADDED ,:paramterName AS PARAMETER_NAME FROM DUAL) source ")
                        .append("ON (target.PARAMETER_ID = source.PARAMETER_ID AND target.CUSTOMER_ID = source.CUSTOMER_ID AND target.PERIOD_ID = source.PERIOD_ID AND target.FACILITY_ID = source.FACILITY_ID AND target.IS_NEW_ADDED = source.IS_NEW_ADDED) ")
                        .append("WHEN MATCHED THEN ")
                        .append("UPDATE SET target.VALUE_NUMERIC = source.VALUE_NUMERIC , target.VALUE_PERCENTAGE = source.VALUE_PERCENTAGE, target.VALUE_TEXT = source.VALUE_TEXT, target.PARAMETER_NAME = source.PARAMETER_NAME  ")
                        .append("WHEN NOT MATCHED THEN ")
                        .append("INSERT (ID, PERIOD_ID, PARAMETER_ID, CUSTOMER_ID, VALUE_NUMERIC,VALUE_PERCENTAGE,VALUE_TEXT,PARAMETER_NAME,FACILITY_ID,IS_NEW_ADDED) ")
                        .append("VALUES (SEQ_T_KALYPTO_DATA.nextval, source.PERIOD_ID, source.PARAMETER_ID, source.CUSTOMER_ID, source.VALUE_NUMERIC, source.VALUE_PERCENTAGE, source.VALUE_TEXT, source.PARAMETER_NAME,source.FACILITY_ID,source.IS_NEW_ADDED)");
            }

            MapSqlParameterSource[] batchParams = new MapSqlParameterSource[kalyptoDataValues.size()];
            for (int i = 0; i < kalyptoDataValues.size(); i++) {
                KalyptoValuesDTO periodToInsert = kalyptoDataValues.get(i);
                MapSqlParameterSource paramMaps = new MapSqlParameterSource();
                paramMaps.addValue("id", periodToInsert.getId());
                paramMaps.addValue("periodId", periodToInsert.getPeriodId());
                paramMaps.addValue("parameterId", periodToInsert.getParameterId());
                paramMaps.addValue("valueNumeric", periodToInsert.getValueNumberic());
                paramMaps.addValue("valuePercentage", periodToInsert.getValuePercentage());
                paramMaps.addValue("ValueText", periodToInsert.getValueText());
                paramMaps.addValue("paramterName", periodToInsert.getParameterName());
                paramMaps.addValue("isAddedNew", periodToInsert.getIsAddedNew());
                paramMaps.addValue("customerId", periodToInsert.getCustomerId());
                paramMaps.addValue("facilityId", periodToInsert.getFacilityId());

                batchParams[i] = paramMaps;
            }

            results = namedParameterJdbcTemplate.batchUpdate(sql.toString(), batchParams);

            for (int result : results) {
                if (result > 0) {
                    isSucess = true;
                    break;
                }
            }

            LOG.info("START : Kalypto Rows By Customer ID Repository: {} , user {} ", 23, credentialsDTO);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return isSucess;
    }
}
