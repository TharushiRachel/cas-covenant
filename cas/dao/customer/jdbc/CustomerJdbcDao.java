package com.itechro.cas.dao.customer.jdbc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.applicationform.AFCribReportDTO;
import com.itechro.cas.model.dto.customer.*;
import com.itechro.cas.model.dto.customer.request.CustomerEvaluationIdRequest;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.QueryBuilder;
import com.itechro.cas.util.QueryInBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class CustomerJdbcDao extends BaseJDBCDao {

    public Page<CustomerDTO> getPagedCustomerDTO(CustomerSearchRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append(" SELECT DISTINCT\n");
        SQL.append("   c.CUSTOMER_ID, \n");
        SQL.append("   c.CUSTOMER_FINANCIAL_ID, \n");
        SQL.append("   c.CUSTOMER_NAME, \n");
        SQL.append("   c.CIVIL_STATUS, \n");
        SQL.append("   c.STATUS, \n");
//        SQL.append("   ct.CONTACT_NUMBER,");
//        SQL.append("   cb.BANK_ACCOUNT_NUMBER,");
//        SQL.append("   ci.IDENTIFICATION_TYPE,");
        SQL.append(" NVL(c.MODIFIED_DATE,c.CREATED_DATE) AS LAST_UPDATE_DATE \n");
        SQL.append(" FROM T_CUSTOMER c \n");
        SQL.append(" LEFT JOIN t_Customer_telephone ct on \n");
        SQL.append(" c.CUSTOMER_ID = ct.CUSTOMER_ID \n");
        SQL.append(" LEFT JOIN t_Customer_bank_details cb on \n");
        SQL.append(" c.CUSTOMER_ID = cb.CUSTOMER_ID \n");
        SQL.append(" LEFT JOIN t_Customer_identification ci on \n");
        SQL.append(" c.CUSTOMER_ID = ci.CUSTOMER_ID     \n");
        SQL.append(" WHERE c.CUSTOMER_ID IS NOT NULL    \n");

        if (searchRQ.getCustomerID() != null) {
            SQL.append("AND c.CUSTOMER_ID =:customerID \n");
            params.put("customerID", searchRQ.getCustomerID());
        }

        if (searchRQ.getCustomerFinancialID() != null) {
            SQL.append("AND c.CUSTOMER_FINANCIAL_ID =:customerFinancialID \n");
            params.put("customerFinancialID", searchRQ.getCustomerFinancialID());
        }

        if (StringUtils.isNotBlank(searchRQ.getCustomerName())) {
            SQL.append("       AND UPPER(c.CUSTOMER_NAME) LIKE '%" + searchRQ.getCustomerName().trim().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotBlank(searchRQ.getContactNumber())) {
            SQL.append("AND c.CUSTOMER_ID IN ( \n");
            SQL.append("SELECT CUSTOMER_ID FROM T_CUSTOMER_TELEPHONE WHERE UPPER(CONTACT_NUMBER) LIKE '%" + searchRQ.getContactNumber().trim().toUpperCase() + "%' \n");
            SQL.append(") \n");
//            SQL.append("       AND UPPER(ct.CONTACT_NUMBER) LIKE '%" + searchRQ.getContactNumber().trim().toUpperCase() + "%' \n");
        }

        if (StringUtils.isNotBlank(searchRQ.getBankAccountNumber())) {
            SQL.append("AND c.CUSTOMER_ID IN ( \n");
            SQL.append("SELECT CUSTOMER_ID FROM T_CUSTOMER_BANK_DETAILS WHERE UPPER(BANK_ACCOUNT_NUMBER) LIKE '%" + searchRQ.getBankAccountNumber().trim().toUpperCase() + "%' \n");
            SQL.append(") \n");
//            SQL.append("       AND UPPER(cb.BANK_ACCOUNT_NUMBER) LIKE '%" + searchRQ.getBankAccountNumber().trim().toUpperCase() + "%' \n");
        }

        if (searchRQ.getIdentificationType() != null && StringUtils.isNotEmpty(searchRQ.getIdentificationNumber())) {
            SQL.append("AND c.CUSTOMER_ID IN ( \n");
            SQL.append("SELECT CUSTOMER_ID FROM T_CUSTOMER_IDENTIFICATION WHERE IDENTIFICATION_TYPE =:identificationType AND UPPER(IDENTIFICATION_NUMBER) LIKE '%" + searchRQ.getIdentificationNumber().trim().toUpperCase() + "%' \n");
            SQL.append(") \n");

            params.put("identificationType", searchRQ.getIdentificationType().toString());
//            SQL.append("       AND ci.IDENTIFICATION_TYPE = :identificationType  \n");
//            params.put("identificationType", searchRQ.getIdentificationType().name());
        }

        if (StringUtils.isNotBlank(searchRQ.getCivilStatus())) {
            SQL.append("       AND UPPER(c.CIVIL_STATUS) LIKE '%" + searchRQ.getCivilStatus().trim().toUpperCase() + "%' \n");
        }

        if (searchRQ.getStatus() != null) {
            SQL.append("AND c.STATUS =:status \n");
            params.put("status", searchRQ.getStatus().toString());
        }

        SQL.append(" ORDER BY c.CUSTOMER_ID  \n");

        return getPagedData(SQL.toString(), params, new RowMapper<CustomerDTO>() {

            @Nullable
            @Override
            public CustomerDTO mapRow(ResultSet rs, int i) throws SQLException {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setCustomerID(rs.getInt("CUSTOMER_ID"));
                customerDTO.setCustomerFinancialID(rs.getString("CUSTOMER_FINANCIAL_ID"));
                customerDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                customerDTO.setCivilStatus(rs.getString("CIVIL_STATUS"));
                customerDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
//                customerDTO.setTelephoneNumber(rs.getString("CONTACT_NUMBER"));
//                customerDTO.setBankAccNumber(rs.getString("BANK_ACCOUNT_NUMBER"));
//                customerDTO.setIdentificationType(DomainConstants.CustomerIdentificationType.resolveStatus(rs.getString("IDENTIFICATION_TYPE")));
                customerDTO.setLastUpdateDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_UPDATE_DATE")));

                return customerDTO;
            }

        }, searchRQ);
    }

    public CustomerDTO getCustomerDTOBy(SearchCustomerRQ searchRQ) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT DISTINCT \n");
        SQL.append("   c.CUSTOMER_ID, \n");
        SQL.append("   c.CUSTOMER_FINANCIAL_ID, \n");
        SQL.append("   c.CUSTOMER_NAME, \n");
        SQL.append("   c.CIVIL_STATUS, \n");
        SQL.append("   c.STATUS, \n");
        SQL.append("   NVL(c.MODIFIED_DATE, c.CREATED_DATE) AS LAST_UPDATE_DATE \n");
        SQL.append(" FROM T_CUSTOMER c \n");
        SQL.append("   LEFT JOIN t_Customer_telephone ct ON \n");
        SQL.append("                                        c.CUSTOMER_ID = ct.CUSTOMER_ID AND ct.STATUS = 'ACT' \n");
        SQL.append("   LEFT JOIN t_Customer_bank_details cb ON \n");
        SQL.append("                                           c.CUSTOMER_ID = cb.CUSTOMER_ID AND cb.STATUS = 'ACT' \n");
        SQL.append("   LEFT JOIN t_Customer_identification ci ON \n");
        SQL.append("                                            c.CUSTOMER_ID = ci.CUSTOMER_ID AND ci.STATUS = 'ACT'\n");
        SQL.append(" WHERE c.CUSTOMER_ID IS NOT NULL \n");
        if (StringUtils.isNotBlank(searchRQ.getCustomerFinancialID())) {
            SQL.append("       AND c.CUSTOMER_FINANCIAL_ID = :customerFinancialID \n");
            params.put("customerFinancialID", searchRQ.getCustomerFinancialID().trim());
        }
        if (StringUtils.isNotBlank(searchRQ.getBankAccountNumber())) {
            SQL.append("       AND UPPER(cb.BANK_ACCOUNT_NUMBER) = :bankAccountNumber \n");
            params.put("bankAccountNumber", searchRQ.getBankAccountNumber().trim());
        }
        if (StringUtils.isNotBlank(searchRQ.getIdentificationType()) &&
                StringUtils.isNotBlank(searchRQ.getIdentificationNumber())) {
            SQL.append("       AND UPPER(ci.IDENTIFICATION_TYPE) = :identificationType \n");
            SQL.append("       AND UPPER(ci.IDENTIFICATION_NUMBER) = :identificationNumber \n");

            params.put("identificationType", searchRQ.getIdentificationType().toUpperCase().trim());
            params.put("identificationNumber", searchRQ.getIdentificationNumber().toUpperCase().trim());
        }
        SQL.append(" ORDER BY c.CUSTOMER_NAME \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<CustomerDTO>() {

            @Override
            public CustomerDTO extractData(ResultSet rs) throws SQLException, DataAccessException {

                CustomerDTO customerDTO = null;
                if (rs.next()) {
                    customerDTO = new CustomerDTO();
                    customerDTO.setCustomerID(rs.getInt("CUSTOMER_ID"));
                    customerDTO.setCustomerFinancialID(rs.getString("CUSTOMER_FINANCIAL_ID"));
                    customerDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                    customerDTO.setCivilStatus(rs.getString("CIVIL_STATUS"));
                    customerDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                    customerDTO.setLastUpdateDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_UPDATE_DATE")));


                }
                return customerDTO;
            }
        });
    }

    public List<CustomerDTO> getCustomerDTOList(List<Integer> customerIDs) {
        final List<CustomerDTO> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT \n");
        SQL.append("   c.CUSTOMER_ID,           \n");
        SQL.append("   c.CUSTOMER_FINANCIAL_ID, \n");
        SQL.append("   c.CUSTOMER_NAME,         \n");
        SQL.append("   c.EMAIL_ADDRESS,         \n");
        SQL.append("   c.SECONDARY_EMAIL_ADDRESS,       \n");
        SQL.append("   c.DATE_OF_BIRTH,                 \n");
        SQL.append("   c.CIVIL_STATUS,          \n");
        SQL.append("   c.STATUS,                \n");
        SQL.append(" NVL(c.MODIFIED_DATE,c.CREATED_DATE) AS LAST_UPDATE_DATE \n");
        SQL.append(" FROM T_CUSTOMER c          \n");
        SQL.append(" WHERE c.CUSTOMER_ID IS NOT NULL        \n");

        if (customerIDs != null && !customerIDs.isEmpty()) {
            SQL.append("  AND c.CUSTOMER_ID IN (" + QueryInBuilder.buildSQLINQuery(customerIDs) + ") \n");
        }

        SQL.append(" ORDER BY c.CUSTOMER_NAME \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CustomerDTO>>() {
            @Override
            public List<CustomerDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    CustomerDTO customerDTO = new CustomerDTO();
                    customerDTO.setCustomerID(rs.getInt("CUSTOMER_ID"));
                    customerDTO.setCustomerFinancialID(rs.getString("CUSTOMER_FINANCIAL_ID"));
                    customerDTO.setCustomerName(rs.getString("CUSTOMER_NAME"));
                    customerDTO.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
                    customerDTO.setEmailAddress(rs.getString("SECONDARY_EMAIL_ADDRESS"));
                    customerDTO.setDateOfBirth(rs.getString("DATE_OF_BIRTH"));
                    customerDTO.setCivilStatus(rs.getString("CIVIL_STATUS"));
                    customerDTO.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                    customerDTO.setLastUpdateDateStr(CalendarUtil.getDefaultFormattedDateOnly(rs.getTimestamp("LAST_UPDATE_DATE")));

                    result.add(customerDTO);
                }
                return result;
            }
        });
    }

    public List<CustomerCribLiabilityDTO> getCustomerCribLiabilitiesList(AFCribReportDTO afCribReportDTO) {

        List<CustomerCribLiabilityDTO> resultList = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("identificationNumber", afCribReportDTO.getIdentificationNo().toUpperCase());
        params.put("identificationType", afCribReportDTO.getIdentificationType().name());
        String cribDate = afCribReportDTO.getCribDateStr().concat(" 23:59:59");
        StringBuilder SQL = new StringBuilder();

        SQL.append("                                                                       \n");
        SQL.append("SELECT                                                                 \n");
        SQL.append("  tccl.CUSTOMER_CRIB_LIABILITY_ID,                                     \n");
        SQL.append("  tccl.CUSTOMER_CRIB_RESPONSE_ID,                                      \n");
        SQL.append("  tccl.IDENTIFICATION_TYPE,                                            \n");
        SQL.append("  tccl.IDENTIFICATION_NUMBER,                                          \n");
        SQL.append("  tccl.FINANCIAL_INSTITUTION,                                          \n");
        SQL.append("  tccl.ORIGINAL_AMOUNT,                                                \n");
        SQL.append("  tccl.PRESENT_OUTSTANDING,                                            \n");
        SQL.append("  tccl.ARREARS,                                                        \n");
        SQL.append("  tccl.SIGNED_AS,                                                      \n");
        SQL.append("  tccl.SECURITIES,                                                     \n");
        SQL.append("  tccl.FINANCIAL_INSTITUTION,                                          \n");
        SQL.append("  tccl.CREATED_DATE,                                                   \n");
        SQL.append("  tccl.STATUS                                                          \n");
        SQL.append("FROM T_CUSTOMER_CRIB_LIABILITY tccl                                    \n");
        SQL.append("WHERE tccl.CUSTOMER_CRIB_LIABILITY_ID IS NOT NULL                      \n");
        SQL.append("      AND UPPER(tccl.IDENTIFICATION_NUMBER) =:identificationNumber     \n");
        SQL.append("      AND tccl.IDENTIFICATION_TYPE =:identificationType                \n");
        SQL.append("   AND tccl.CREATED_DATE =                                             \n");
        SQL.append("       (                                                               \n");
        SQL.append("    SELECT max(t.CREATED_DATE)                                         \n");
        SQL.append("    FROM T_CUSTOMER_CRIB_LIABILITY t                                   \n");
        SQL.append("    WHERE t.CUSTOMER_CRIB_LIABILITY_ID IS NOT NULL                     \n");
        SQL.append("          AND upper(t.IDENTIFICATION_NUMBER) =:identificationNumber    \n");
        SQL.append("          AND t.IDENTIFICATION_TYPE =:identificationType               \n");
        SQL.append("          AND (t.CREATED_DATE <= TO_DATE('" + cribDate + "','dd/mm/yyyy hh24:mi:ss')) \n");
        SQL.append("       )                                                               \n");
        SQL.append("      AND STATUS = 'ACT'                                               \n");

        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CustomerCribLiabilityDTO>>() {

            @Override
            public List<CustomerCribLiabilityDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    CustomerCribLiabilityDTO customerCribLiability = new CustomerCribLiabilityDTO();
                    customerCribLiability.setCustomerCribLiabilityID(rs.getInt("CUSTOMER_CRIB_LIABILITY_ID"));
                    customerCribLiability.setCustomerCribResponseID(rs.getInt("CUSTOMER_CRIB_RESPONSE_ID"));
                    customerCribLiability.setIdentificationType(DomainConstants.CustomerIdentificationType.resolveStatus(rs.getString("IDENTIFICATION_TYPE")));
                    customerCribLiability.setIdentificationNumber(rs.getString("IDENTIFICATION_NUMBER"));
                    customerCribLiability.setFinancialInstitution(rs.getString("FINANCIAL_INSTITUTION"));
                    customerCribLiability.setOriginalAmount(rs.getString("ORIGINAL_AMOUNT"));
                    customerCribLiability.setPresentOutstanding(rs.getString("PRESENT_OUTSTANDING"));
                    customerCribLiability.setArrears(rs.getString("ARREARS"));
                    customerCribLiability.setSignedAs(rs.getString("SIGNED_AS"));
                    customerCribLiability.setSecurities(rs.getString("SECURITIES"));
                    customerCribLiability.setStatus(AppsConstants.Status.resolveStatus(rs.getString("STATUS")));
                    resultList.add(customerCribLiability);
                }
                return resultList;
            }
        });
    }

    public int saveCustomerCribLiabilitiesJDBC(CustomerCribLiabilityDTO customerCribLiabilityDTO, CredentialsDTO credentialsDTO, Date date) {

        StringBuilder INSERT_QUERY = new StringBuilder();
        INSERT_QUERY.append("INSERT INTO T_CUSTOMER_CRIB_LIABILITY (CUSTOMER_CRIB_LIABILITY_ID, CUSTOMER_CRIB_RESPONSE_ID, IDENTIFICATION_NUMBER, IDENTIFICATION_TYPE, ORIGINAL_AMOUNT, PRESENT_OUTSTANDING, ARREARS, SIGNED_AS, SECURITIES, FINANCIAL_INSTITUTION, STATUS, CREATED_DATE, CREATED_BY, VERSION) VALUES");
        INSERT_QUERY.append("(");
        INSERT_QUERY.append("SEQ_T_CUS_CRIB_LIABILITY.nextval,");
        INSERT_QUERY.append(":customerCribResponseID,");
        INSERT_QUERY.append(":identificationNumber,");
        INSERT_QUERY.append(":identificationType,");
        INSERT_QUERY.append(":originalAmount,");
        INSERT_QUERY.append(":presentOutstanding,");
        INSERT_QUERY.append(":arrears,");
        INSERT_QUERY.append(":signedAs,");
        INSERT_QUERY.append(":securities,");
        INSERT_QUERY.append(":financialInstitution,");
        INSERT_QUERY.append(":status,");
        INSERT_QUERY.append(":createdDate,");
        INSERT_QUERY.append(":createdBy,");
        INSERT_QUERY.append(":version");
        INSERT_QUERY.append(")");

        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("customerCribResponseID", null);
        paramMap.put("identificationNumber", customerCribLiabilityDTO.getIdentificationNumber());
        paramMap.put("identificationType", customerCribLiabilityDTO.getIdentificationType().toString());
        paramMap.put("originalAmount", customerCribLiabilityDTO.getOriginalAmount());
        paramMap.put("presentOutstanding", customerCribLiabilityDTO.getPresentOutstanding());
        paramMap.put("arrears", customerCribLiabilityDTO.getArrears());
        paramMap.put("signedAs", customerCribLiabilityDTO.getSignedAs());
        paramMap.put("securities", customerCribLiabilityDTO.getSecurities());
        paramMap.put("financialInstitution", customerCribLiabilityDTO.getFinancialInstitution());
        paramMap.put("status", AppsConstants.Status.ACT.toString());
        paramMap.put("createdDate", date);
        paramMap.put("createdBy", credentialsDTO.getUserName());
        paramMap.put("version", 0);

        return namedParameterJdbcTemplate.update(INSERT_QUERY.toString(), paramMap);
    }

    //get customer evaluation by customer id(finacle id) method
    public List<CustomerEvaluationListDTO> getCustomerEvaluationListById(String customerId) throws AppsException {

        List<CustomerEvaluationListDTO> customerEvaluationDTOList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                 \n");
        SQL.append("  ce.C_E_ID,           \n");
        SQL.append("  ce.CUST_ID,          \n");
        SQL.append("  ce.SCORE,            \n");
        SQL.append("  ce.E_R_ID,            \n");
        SQL.append("  ce.USER_ID,           \n");
        SQL.append("  ce.FILLED_DATE           \n");
        SQL.append("FROM CEF_CUSTOMER_EVALUATION ce     \n");
        SQL.append("WHERE ce.CUST_ID = :customerId  \n");
        SQL.append("ORDER BY FILLED_DATE DESC  \n");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new RowMapper<CustomerEvaluationListDTO>() {
            @Override
            public CustomerEvaluationListDTO mapRow(ResultSet resultSet, int rownumber) throws SQLException {
                CustomerEvaluationListDTO customerEvaluationListDTO = new CustomerEvaluationListDTO();
                customerEvaluationListDTO.setCustomerEvaluationId(resultSet.getInt("C_E_ID"));
                customerEvaluationListDTO.setCustomerId(resultSet.getString("CUST_ID"));
                customerEvaluationListDTO.setScore(resultSet.getInt("SCORE"));
                customerEvaluationListDTO.setEvaluationRecordId(resultSet.getInt("E_R_ID"));
                customerEvaluationListDTO.setUserId(resultSet.getString("USER_ID"));
                customerEvaluationListDTO.setFilledDate(CalendarUtil.getDefaultFormattedDateOnly(resultSet.getTimestamp("FILLED_DATE")));
                return customerEvaluationListDTO;
            }
        });
    }

    public List<CustomerEvaluationDTO> getCustomerEvaluationById(String customerId, Integer customerEvaluationId) throws AppsException{

        List<CustomerEvaluationDTO> customerEvaluationDTOList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("customerEvaluationId", customerEvaluationId);
        QueryBuilder SQL = new QueryBuilder();

        SQL.append(" SELECT * \n");
        SQL.appendNotNullMandatory(" FROM TABLE(GET_CUST_QSTNS_AND_ANS(:customerId, \n", customerId);
        SQL.appendNotNullMandatory(" :customerEvaluationId))\n", customerEvaluationId);


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CustomerEvaluationDTO>>() {
            @Override
            public List<CustomerEvaluationDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

                Map<Integer, CustomerEvaluationDTO> mapEva = new HashMap<>();

                while (resultSet.next()){
                    CustomerEvaluationDTO customerEvaluationDTO = new CustomerEvaluationDTO();
                    customerEvaluationDTO.setEvaluationElementId(resultSet.getInt("EE_ID"));
                    customerEvaluationDTO.setParentId(resultSet.getInt("PARENT_ID"));
                    customerEvaluationDTO.setElementCaption(resultSet.getString("element_CAPTION"));
                    customerEvaluationDTO.setAnswerId(resultSet.getInt("A_ID"));
                    customerEvaluationDTO.setAnswerCaption(resultSet.getString("ans_CAPTION"));
                    customerEvaluationDTO.setEleType(resultSet.getString("ELE_TYPE"));

                    System.out.println("resultSet" +resultSet);

                    customerEvaluationDTOList.add(customerEvaluationDTO);
                }

                return customerEvaluationDTOList;
            }
        });
    }



    //new
//    public Page<CustomerEvaluationAnswersDTO> getCustomerEvaluationDashboardDTO(SearchCustomerEvaluationRQ searchCustomerEvaluationRQ) {
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("customerId", searchCustomerEvaluationRQ.getCustomerId());
//        params.put("customerEvaluationId", searchCustomerEvaluationRQ.getCustomerEvaluationId());
//        StringBuilder SQL = new StringBuilder();
//
//        SQL.append("SELECT * From TABLE(CAST(GET_CUST_QSTNS_AND_ANS(:customerId, :customerEvaluationId) AS EVALUATION_ELEMENTS))  \n");
//
//
//        return getPagedData(SQL.toString(), params, new RowMapper<CustomerEvaluationAnswersDTO>() {
//
//            @Nullable
//            @Override
//            public CustomerEvaluationAnswersDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
//                CustomerEvaluationAnswersDTO docDTO = new CustomerEvaluationAnswersDTO();
//                docDTO.setEvaluationElementId(rs.getInt("EE_ID"));
//                docDTO.setScore(rs.getInt("PARENT_ID"));
//                docDTO.setScore(rs.getInt("ELEMENT_CAPTION"));
//                docDTO.setScore(rs.getInt("ELE_TYPE"));
//                docDTO.setScore(rs.getInt("E_ORDER_BY"));
//                docDTO.setScore(rs.getInt("GROUP_BY"));
//                docDTO.setScore(rs.getInt("REQUIRED"));
//                docDTO.setScore(rs.getInt("A_ID"));
//                docDTO.setScore(rs.getInt("SCORE"));
//                docDTO.setScore(rs.getInt("ANS_CAPTION"));
//                docDTO.setScore(rs.getInt("A_ORDER_BY"));
//                docDTO.setScore(rs.getInt("CHECKED"));
//                return docDTO;
//            }
//        }, searchCustomerEvaluationRQ);
//    }

    public Page<CustomerEvaluationAnswersDTO> getCustomerEvaluationDashboardDTO(SearchCustomerEvaluationRQ searchCustomerEvaluationRQ) {
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", searchCustomerEvaluationRQ.getCustomerId());
        params.put("customerEvaluationId", searchCustomerEvaluationRQ.getCustomerEvaluationId());

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT * FROM TABLE(CAST(GET_CUST_QSTNS_AND_ANS(:customerId, :customerEvaluationId) AS EVALUATION_ELEMENTS))");

        return getPagedData(SQL.toString(), params, new RowMapper<CustomerEvaluationAnswersDTO>() {
            @Nullable
            @Override
            public CustomerEvaluationAnswersDTO mapRow(ResultSet rs, int i) throws SQLException, DataAccessException {
                CustomerEvaluationAnswersDTO docDTO = new CustomerEvaluationAnswersDTO();
                docDTO.setEvaluationElementId(rs.getInt("EE_ID"));
                docDTO.setParentId(rs.getInt("PARENT_ID"));
                docDTO.setElementCaption(rs.getString("ELEMENT_CAPTION"));
                docDTO.setEvaluationType(AppsConstants.EvaluationElement.valueOf(rs.getString("ELE_TYPE")));
                docDTO.setOrderBy(rs.getInt("E_ORDER_BY"));
                docDTO.setGroupBy(rs.getInt("GROUP_BY"));
                //docDTO.setIsRequired(AppsConstants.YesNo.valueOf(rs.getString("REQUIRED")));
                docDTO.setAnswerId(rs.getInt("A_ID"));
                docDTO.setScore(rs.getInt("SCORE"));
                docDTO.setAnswerCaption(rs.getString("ANS_CAPTION"));
                docDTO.setAnsOrderBy(rs.getInt("A_ORDER_BY"));

                return docDTO;
            }
        }, searchCustomerEvaluationRQ);
    }




    public List<CustomerEvaluationScoreDTO> getCustomerEvaluationDetails(String customerId, Integer customerEvaluationId) throws AppsException{

        List<CustomerEvaluationScoreDTO> customerEvaluationScoreDTOList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("customerEvaluationId", customerEvaluationId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                 \n");
        SQL.append("  ce.C_E_ID,           \n");
        SQL.append("  ce.CUST_ID,          \n");
        SQL.append("  ce.SCORE,            \n");
        SQL.append("  ce.E_R_ID,            \n");
        SQL.append("  ce.USER_ID           \n");
        SQL.append("FROM CEF_CUSTOMER_EVALUATION ce     \n");
        SQL.append("WHERE ce.CUST_ID = :customerId AND ce.C_E_ID = :customerEvaluationId \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CustomerEvaluationScoreDTO>>() {
            @Override
            public List<CustomerEvaluationScoreDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()){
                    CustomerEvaluationScoreDTO customerEvaluationDTO = new CustomerEvaluationScoreDTO();
                    customerEvaluationDTO.setScore(resultSet.getInt("SCORE"));
                    customerEvaluationScoreDTOList.add(customerEvaluationDTO);
                }

                return customerEvaluationScoreDTOList;
            }
        });
    }


    public CustomerEvaluationIdRequest getCustomerEvaluationInFullPaper(Integer facilityPaperID) throws AppsException {

        CustomerEvaluationIdRequest customerEvaluationIdRequests = new CustomerEvaluationIdRequest();
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperID", facilityPaperID);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT    t1.*                                                                   \n");
        SQL.append("FROM T_CUSTOMER_EVALUATION t1                                                    \n");
        SQL.append(" JOIN(                                                                           \n");
        SQL.append(" SELECT FACILITY_PAPER_ID, MAX(ID) AS MAXIMUM_ID                                 \n");
        SQL.append(" FROM T_CUSTOMER_EVALUATION                                                      \n");
        SQL.append(" WHERE FACILITY_PAPER_ID=:facilityPaperID                                        \n");
        SQL.append(" GROUP BY FACILITY_PAPER_ID)                                                     \n");
        SQL.append(" t2 ON t1.FACILITY_PAPER_ID = t2.FACILITY_PAPER_ID AND t1.ID = t2.MAXIMUM_ID     \n");
        SQL.append(" where t1.STATUS='ACTIVE'                                                        \n");



        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<CustomerEvaluationIdRequest>() {


            @Override
            public CustomerEvaluationIdRequest extractData(ResultSet rs) throws SQLException, DataAccessException {

                if(rs.next()) {
                    customerEvaluationIdRequests.setCustomerId(rs.getString("CUST_ID"));
                    customerEvaluationIdRequests.setCustomerEvaluationId(rs.getInt("C_E_ID"));
                    customerEvaluationIdRequests.setId(rs.getInt("ID"));
                }
                return customerEvaluationIdRequests;

            }

        });
    }

    public List<CustomerEvaluationAnswersDTO> getCustomerEvaluationData(Integer facilityPaperId) throws AppsException {
        // Step 1: Call method 1 to get customerId and customerEvaluationId
        CustomerEvaluationIdRequest customerEvaluationIdRequest = getCustomerEvaluationInFullPaper(facilityPaperId);

        // Step 2: Check if method 1 returned valid data
        if (customerEvaluationIdRequest != null && customerEvaluationIdRequest.getCustomerId() != null && customerEvaluationIdRequest.getCustomerEvaluationId() != null) {
            // Step 3: Call method 2 with the customerId and customerEvaluationId as input parameters
            SearchCustomerEvaluationRQ searchCustomerEvaluationRQ = new SearchCustomerEvaluationRQ();
            searchCustomerEvaluationRQ.setCustomerId(customerEvaluationIdRequest.getCustomerId());
            searchCustomerEvaluationRQ.setCustomerEvaluationId(customerEvaluationIdRequest.getCustomerEvaluationId());
            Page<CustomerEvaluationAnswersDTO> customerEvaluationAnswersDTOPage = getCustomerEvaluationDashboardDTO(searchCustomerEvaluationRQ);

            // Step 4: Extract and return the list of CustomerEvaluationAnswersDTO from the Page object
            return customerEvaluationAnswersDTOPage.toList();
        } else {
            // Handle the case when method 1 does not return valid data (e.g., throw an exception or return an empty list)
            return Collections.emptyList();
        }
    }

    public List<CustomerEvaluationDTO> getCustomerEvaluationForFacilityPaper(Integer facilityPaperId) throws AppsException {
        CustomerEvaluationIdRequest customerEvaluationIdRequest = getCustomerEvaluationInFullPaper(facilityPaperId);
        String customerId = customerEvaluationIdRequest.getCustomerId();
        Integer customerEvaluationId = customerEvaluationIdRequest.getCustomerEvaluationId();

        if (customerEvaluationId == null) {
            return Collections.emptyList(); // or return a new ArrayList<>() if preferred
        }

        return getCustomerEvaluationById(customerId, customerEvaluationId);
    }

    public String getCustomerNameWithInitials(String casCustomerFullName) throws AppsException{

        Map<String, Object> params = new HashMap<>();
        params.put("casCustomerFullName", casCustomerFullName);
        QueryBuilder SQL = new QueryBuilder();

        SQL.append(" SELECT GET_NAME_WITH_INITIALS(:casCustomerFullName) NAME_WITH_INITIAL FROM DUAL \n");
        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String responseStatus = null;
                if (rs.next()) {
                    responseStatus = rs.getString("NAME_WITH_INITIAL");
                }
                return responseStatus;
            }
        });
    }

}
