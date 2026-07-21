package com.itechro.cas.dao.facilitypaper.jdbc;

import com.itechro.cas.model.dto.finacle.request.*;
import com.itechro.cas.model.dto.finacle.response.GuaranteeVolumesRS;
import com.itechro.cas.model.dto.finacle.response.VolumeSummaryResult;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class FinacleInformationDao {
    private static final Logger LOG = LoggerFactory.getLogger(FinacleInformationDao.class);

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

//    public List<TTTurnoverDetailsRS> getTTTurnoverDetailsDB(TTTurnoverDetailsRQ facilityPaperId, CredentialsDTO credentialsDTO) {
//        List<TTTurnoverDetailsRS> resultArray = new ArrayList<>();
//        Map<String, Object> params = new HashMap<>();
//        StringBuilder SQL = new StringBuilder();
//
//        try {
//            LOG.info("START : Get TT turnover from DB Repository: {} , user {} ", facilityPaperId, credentialsDTO);
//
//            params.put("facilitypaperId", facilityPaperId.getFacilityPaperId());
//
//
//            SQL.append(" SELECT * FROM TT_TURNOVER_DETAILS WHERE FACILITY_PAPER_ID = :facilitypaperId  ");
//
//            resultArray = namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<TTTurnoverDetailsRS>() {
//                public TTTurnoverDetailsRS mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
//                    TTTurnoverDetailsRS turnoverDetailsRS = new TTTurnoverDetailsRS();
//                    turnoverDetailsRS.setTtRef(rs.getString( "TT_REF"));
//                    turnoverDetailsRS.setTtCrncy(rs.getString("TT_CURRENCY"));
//                    turnoverDetailsRS.setTtAmt(rs.getString("TT_AMOUNT"));
//                    turnoverDetailsRS.setUsrRate(rs.getString("USR_RATE"));
//                    turnoverDetailsRS.setCrncyRate(rs.getString("CURRENCY_RATE"));
//                    turnoverDetailsRS.setSolDesc(rs.getString("SOL_DESC"));
//                    turnoverDetailsRS.setRm(rs.getString("RM"));
//                    turnoverDetailsRS.setSolId(rs.getString("SOL_ID"));
//                    turnoverDetailsRS.setUsdEqvulant(rs.getString("USD_EQUIVALENT"));
//                    turnoverDetailsRS.setPartyName(rs.getString("PARTY_NAME"));
//                    turnoverDetailsRS.setCifId(rs.getString("CIF_ID"));
//                    turnoverDetailsRS.setLkrAmount(rs.getString("LKR_AMOUNT"));
//                    turnoverDetailsRS.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
//                    turnoverDetailsRS.setStartDate(rs.getDate("START_DATE").toString());
//                    turnoverDetailsRS.setEndDate(rs.getDate("END_DATE").toString());
//
//
//                    return turnoverDetailsRS;
//                }
//            });
//
//            LOG.info("END : Get TT turnover from DB Repository Repository: {} , user {} ", resultArray, credentialsDTO);
//        } catch (Exception e) {
//            LOG.error("ERROR :Get TT turnover from DB Repository ", e);
//        }
//        LOG.info("END : Get TT turnover from DB Repository Repository: {} , user {} ", resultArray, credentialsDTO);
//        return resultArray;
//    }

    public List<String> getTTTurnoverAccountsFromDB(Integer facilityPaperId, CredentialsDTO credentialsDTO) {
        List<String> resultArray = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        try {
            LOG.info("START :Get TT turnover accounts in Repository: {} , user {} ", facilityPaperId, credentialsDTO);

            params.put("facilitypaperId", facilityPaperId);

            SQL.append(" SELECT ACCOUNT_NUMBER FROM TT_TURNOVER_DETAILS WHERE FACILITY_PAPER_ID = :facilitypaperId");

            resultArray = namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<String>() {
                public String mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                    String account = new String();

                    account=(rs.getString("ACCOUNT_NUMBER"));



                    return account;
                }
            });
            LOG.info("END : Get TT turnover accounts in Repository Repository: {} , user {} ", resultArray, credentialsDTO);
        } catch (Exception e) {
            LOG.error("ERROR : Get TT turnover accounts in  ", e);
        }
        LOG.info("END : Get TT turnover accounts in Repository Repository: {} , user {} ", resultArray, credentialsDTO);
        return resultArray;
    }


//    public Boolean saveTTTurnoverDetailsDB(saveTTTurnoverDetailsListRQ saveTTTurnoverDetailsListRQ, CredentialsDTO credentialsDTO) {
//        List<TTTurnoverDetailsRS> resultArray = new ArrayList<>();
//        Map<String, Object> params = new HashMap<>();
//        Map<String, Object> deleteParams = new HashMap<>();
//        StringBuilder SQL = new StringBuilder();
//        Boolean result = false;
//        StringBuilder deleteSQL = new StringBuilder();
//
//        try {
//            LOG.info("START : save TTT turnovers to db: {} , user {} ", saveTTTurnoverDetailsListRQ.getFacilityPaperId(), credentialsDTO);
//
//            deleteParams.put("facilitypaperId", saveTTTurnoverDetailsListRQ.getFacilityPaperId());
//            deleteParams.put("accountNumber", saveTTTurnoverDetailsListRQ.getAccountNo());
//            deleteSQL.append(" DELETE FROM TT_TURNOVER_DETAILS WHERE FACILITY_PAPER_ID = :facilitypaperId AND ACCOUNT_NUMBER = :accountNumber ");
//
//            int rowsAffected  = namedParameterJdbcTemplate.update(deleteSQL.toString(), deleteParams) ;
//
//            SQL.append("INSERT INTO TT_TURNOVER_DETAILS (");
//            SQL.append("ID,FACILITY_PAPER_ID, ACCOUNT_NUMBER, TT_REF, TT_CURRENCY, TT_AMOUNT, USR_RATE, CURRENCY_RATE, ");
//            SQL.append("SOL_DESC, RM, SOL_ID, USD_EQUIVALENT, PARTY_NAME, CIF_ID, LKR_AMOUNT, START_DATE, END_DATE) ");
//            SQL.append("VALUES (SEQ_F_TTTURNOVER_DETAILS.nextval, ");
//            SQL.append(":facilitypaperId, :accountNumber, :ttRef, :ttCurrency, :ttAmount, ");
//            SQL.append(":usrRate, :currencyRate, :solDesc, :rm, :solId, ");
//            SQL.append(":usdEquivalent, :partyName, :cifId, :lkrAmount, :startDate, :endDate)");
//
//            for (saveTTTurnoverDetailsRQ saveTTTurnoverDetailsRQ:saveTTTurnoverDetailsListRQ.getTtTurnoverList()) {
//
//
//                params.put("facilitypaperId", saveTTTurnoverDetailsRQ.getFacilityPaperId());
//                params.put("accountNumber", saveTTTurnoverDetailsRQ.getAccountNumber());
//                params.put("ttRef", saveTTTurnoverDetailsRQ.getTtRef());
//                params.put("ttCurrency", saveTTTurnoverDetailsRQ.getTtCrncy());
//                params.put("ttAmount", saveTTTurnoverDetailsRQ.getTtAmt());
//                params.put("usrRate", saveTTTurnoverDetailsRQ.getUsrRate());
//                params.put("currencyRate", saveTTTurnoverDetailsRQ.getCrncyRate());
//                params.put("solDesc", saveTTTurnoverDetailsRQ.getSolDesc());
//                params.put("rm", saveTTTurnoverDetailsRQ.getRm());
//                params.put("solId", saveTTTurnoverDetailsRQ.getSolId());
//                params.put("usdEquivalent", saveTTTurnoverDetailsRQ.getUsdEqvulant());
//                params.put("partyName", saveTTTurnoverDetailsRQ.getPartyName());
//                params.put("cifId", saveTTTurnoverDetailsRQ.getCifId());
//                params.put("lkrAmount", saveTTTurnoverDetailsRQ.getLkrAmount());
//                params.put("startDate", saveTTTurnoverDetailsRQ.getStartDate());
//                params.put("endDate", saveTTTurnoverDetailsRQ.getEndDate());
//
//
//
//
//                int rowsAffectedInSave = namedParameterJdbcTemplate.update(SQL.toString(), params);
//                if (rowsAffectedInSave > 0) {
//                    result = true;
//                } else {
//                    result = false;
//                }
//            }
//            LOG.info("END : save TTT turnovers to db: {} , user {} ", resultArray, credentialsDTO);
//        } catch (Exception e) {
//            LOG.error("ERROR : ", e);
//        }
//        return result;
//    }


    public Boolean saveGuaranteeVolumesDB(GuaranteeVolumeSaveRQ guaranteeVolumeSaveRQ, CredentialsDTO credentialsDTO) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> deleteParams = new HashMap<>();
        StringBuilder SQL = new StringBuilder();
        Boolean result = false;
        StringBuilder deleteSQL = new StringBuilder();

        try {
            LOG.info("START : Save guarantee volumes Repository: {} , user {} ", guaranteeVolumeSaveRQ.getFacilityPaperId(), credentialsDTO);

            deleteParams.put("facilitypaperId", guaranteeVolumeSaveRQ.getFacilityPaperId());
            deleteParams.put("cusId", guaranteeVolumeSaveRQ.getCusId());
            deleteSQL.append(" DELETE FROM GUARANTEE_VOLUMES WHERE FACILITY_PAPER_ID = :facilitypaperId AND CUSTOMER_FINACLE_ID = :cusId");

            int rowsAffected  = namedParameterJdbcTemplate.update(deleteSQL.toString(), deleteParams) ;

            SQL.append("INSERT INTO GUARANTEE_VOLUMES (");
            SQL.append("ID, FACILITY_PAPER_ID,YEAR,CURRENCY, VOLUME, CUSTOMER_FINACLE_ID, CREATED_DATE ) ");
            SQL.append("VALUES (SEQ_F_GUARANTEE_VOLUMES.nextval, :facilitypaperId, ");
            SQL.append(":year, :currency, :volume, :cusId, :createdDate) ");


            for (VolumesSummaryRQ volumeSummary:guaranteeVolumeSaveRQ.getBgmVolumeSummary()) {


                params.put("facilitypaperId", guaranteeVolumeSaveRQ.getFacilityPaperId());
                params.put("currency", volumeSummary.getCnyCode());
                params.put("volume", volumeSummary.getTotalVolume());
                params.put("year", volumeSummary.getYear());
                params.put("cusId", guaranteeVolumeSaveRQ.getCusId());
                params.put("createdDate", volumeSummary.getCreatedDate());



                int rowsAffectedInSave = namedParameterJdbcTemplate.update(SQL.toString(), params);
                if (rowsAffectedInSave > 0) {
                    result = true;
                } else {
                    result = false;
                }
            }
            LOG.info("END : Save guarantee volumes Repository: {} , user {} ", result, credentialsDTO);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return result;
    }

    public GuaranteeVolumesRS getGuaranteeVolumesDB(Integer facilityPaperId, String cusId, CredentialsDTO credentialsDTO) {

        List<VolumeSummaryResult> resultArray = new ArrayList<>();
        GuaranteeVolumesRS response = new GuaranteeVolumesRS();
        Map<String, Object> params = new HashMap<>();
        StringBuilder SQL = new StringBuilder();

        try {
            LOG.info("START : Get guarantee volumes Repository: {} , user {} ", facilityPaperId, credentialsDTO);

            params.put("facilitypaperId", facilityPaperId);
            params.put("cusId", cusId);

            SQL.append(" SELECT * FROM GUARANTEE_VOLUMES WHERE FACILITY_PAPER_ID = :facilitypaperId AND CUSTOMER_FINACLE_ID = :cusId ");

            resultArray = namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<VolumeSummaryResult>() {
                public VolumeSummaryResult mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                    VolumeSummaryResult volume = new VolumeSummaryResult();

                    volume.setCnyCode(rs.getString("CURRENCY"));
                    volume.setTotalVolume(rs.getString("VOLUME"));
                    volume.setYear(rs.getString("YEAR"));
                    volume.setCreatedDate(rs.getString("CREATED_DATE"));



                    return volume;
                }
            });

            response.setBgmVolumeSummary(resultArray);
            LOG.info("END : Get guarantee volumes Repository: {} , user {} ", resultArray, credentialsDTO);
        } catch (Exception e) {
            response.setBgmVolumeSummary(new ArrayList<>());
            LOG.error("ERROR : ", e);
        }
        return response;
    }



}
