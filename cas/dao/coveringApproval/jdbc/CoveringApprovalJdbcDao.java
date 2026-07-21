package com.itechro.cas.dao.coveringApproval.jdbc;

import com.itechro.cas.controller.customer.CustomerController;
import com.itechro.cas.dao.BaseJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.coveringApproval.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

@Repository
public class CoveringApprovalJdbcDao extends BaseJDBCDao {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

//    public CoveringApprovalDashboardCountDTO getCoveringApprovalDashboardCount(CoveringApprovalDashboardRQ coveringApprovalDashboardRQ, Integer dashboardTimePeriodDays) {
//        CoveringApprovalDashboardCountDTO countDTO = new CoveringApprovalDashboardCountDTO();
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("userId", coveringApprovalDashboardRQ.getLoggedInUserId());
//        params.put("branchCode", coveringApprovalDashboardRQ.getLoggedInUserBranchCode());
//
//        StringBuilder SQL = new StringBuilder();
//
//        SQL.append("SELECT 'DRAFT' COVAPP_STATUS, COVAPP_DASHBOARD.GET_DRAFT_COUNT(:userId, :branchCode) AS COUNT FROM DUAL  \n");
//        SQL.append(" UNION ALL \n");
//        SQL.append("SELECT 'IN_PROGRESS' COVAPP_STATUS, COVAPP_DASHBOARD.GET_IN_PROGRESS_COUNT(:userId, :branchCode) AS COUNT FROM DUAL  \n");
//        SQL.append(" UNION ALL \n");
//        SQL.append("SELECT 'APPROVED' COVAPP_STATUS, COVAPP_DASHBOARD.GET_APPROVED_COUNT(:userId, :branchCode) AS COUNT FROM DUAL  \n");
//        SQL.append(" UNION ALL \n");
//        SQL.append("SELECT 'REJECTED' COVAPP_STATUS, COVAPP_DASHBOARD.GET_REJECTED_COUNT(:userId, :branchCode) AS COUNT FROM DUAL  \n");
//        SQL.append(" UNION ALL \n");
//        SQL.append("SELECT 'CANCEL' COVAPP_STATUS, COVAPP_DASHBOARD.GET_RETURN_COUNT(:userId, :branchCode) AS COUNT FROM DUAL  \n");
//
//        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<CoveringApprovalDashboardCountDTO>() {
//            @Override
//            public CoveringApprovalDashboardCountDTO extractData(ResultSet resultSet) throws SQLException, DataAccessException {
//                while (resultSet.next()){
//                    switch (resultSet.getString("COVAPP_STATUS")){
//                        case "DRAFT" : {
//                            countDTO.setInboxCovApp(resultSet.getInt("COUNT"));
//                            break;
//                        }
//                        case "IN_PROGRESS" : {
//                            countDTO.setInProgressCovApp(resultSet.getInt("COUNT"));
//                            break;
//                        }
//                        case "APPROVED" : {
//                            countDTO.setApprovedCovApp(resultSet.getInt("COUNT"));
//                            break;
//                        }
//                        case "REJECTED" : {
//                            countDTO.setRejectedCovApp(resultSet.getInt("COUNT"));
//                            break;
//                        }
//                        case "CANCEL" : {
//                            countDTO.setReturnedCovApp(resultSet.getInt("COUNT"));
//                            break;
//                        }
//                    }
//                }
//                return countDTO;
//            }
//        });
//
//    }


    public CoveringApprovalDashboardCountDTO getCoveringApprovalDashboardCount(CoveringApprovalDashboardRQ coveringApprovalDashboardRQ, Integer dashboardTimePeriodDays) {
        CoveringApprovalDashboardCountDTO countDTO = new CoveringApprovalDashboardCountDTO();
        Map<String, Object> params = new HashMap<>();

        params.put("userId", coveringApprovalDashboardRQ.getLoggedInUserId());
        params.put("branchCode", coveringApprovalDashboardRQ.getLoggedInUserBranchCode());

        StringBuilder SQL = new StringBuilder();
        SQL.append("SELECT STATUS, COUNT FROM TABLE(COVAPP_DASHBOARD.GET_ALL_COUNTS(:userId, :branchCode))");

        return namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<CoveringApprovalDashboardCountDTO>() {
            @Override
            public CoveringApprovalDashboardCountDTO extractData(ResultSet resultSet) throws SQLException, DataAccessException {

                Map<String, Consumer<Integer>> statusSetterMap = new HashMap<>();
                statusSetterMap.put("DRAFT", count -> countDTO.setInboxCovApp(count));
                statusSetterMap.put("IN_PROGRESS", count -> countDTO.setInProgressCovApp(count));
                statusSetterMap.put("APPROVED", count -> countDTO.setApprovedCovApp(count));
                statusSetterMap.put("REJECTED", count -> countDTO.setRejectedCovApp(count));
                statusSetterMap.put("CANCEL", count -> countDTO.setReturnedCovApp(count));

                while (resultSet.next()) {
                    String status = resultSet.getString("STATUS");
                    int count = resultSet.getInt("COUNT");
                    Consumer<Integer> setter = statusSetterMap.get(status);
                    if (setter != null) {
                        setter.accept(count);
                    }
                }
                return countDTO;
            }
        });

    }

    public Page<CoveringApprovalDashboardDTO> getPagedCoveringApprovalDashboardDTO(CoveringApprovalDashboardRQ coveringApprovalDashboardRQ, Integer dashboardTimePeriodDays){
        Map<String, Object> params = new HashMap<>();
        params.put("userId", coveringApprovalDashboardRQ.getLoggedInUserId());
        params.put("branchCode", coveringApprovalDashboardRQ.getLoggedInUserBranchCode());

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT DISTINCT  * \n");

        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("DRAFT")){
            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_DRAFT_LIST(:userId, :branchCode)) \n");
        }
        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("FORWARDED")){
            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_IN_PROGRESS_LIST(:userId, :branchCode)) \n");
        }
        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("RECEIVED_TO_ME")){
            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_RECEIVED_TO_ME_LIST(:userId, :branchCode)) \n");
        }
        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("RETURNED_TO_ME")){
            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_RETURNED_TO_ME_LIST(:userId, :branchCode)) \n");
        }
        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("PAPER_APPROVED")){
            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_APPROVED_LIST(:userId, :branchCode)) \n");
        }
        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("NOT_APPROVED")){
            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_REJECTED_LIST(:userId, :branchCode)) \n");
        }
//        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("RETURNED")){
//            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_CANCEL_LIST(:userId, :branchCode)) \n");
//        }
        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("RETURNED_BY_ME")){
            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_RETURNED_BY_ME_LIST(:userId, :branchCode)) \n");
        }
        if(coveringApprovalDashboardRQ.getCoveringApprovalDashboardSubStatus().equals("RETURNED_BY_OTHERS")){
            SQL.append(" FROM TABLE(COVAPP_DASHBOARD.GET_RETURNED_BY_OTHERS_LIST(:userId, :branchCode)) \n");
        }

        return getPagedData(SQL.toString(), params, new RowMapper<CoveringApprovalDashboardDTO>() {
            @Nullable
            @Override
            public CoveringApprovalDashboardDTO mapRow(ResultSet resultSet, int i) throws SQLException, DataAccessException {
                CoveringApprovalDashboardDTO dashboardDTO = new CoveringApprovalDashboardDTO();
                dashboardDTO.setCustomerName(resultSet.getString("CUSTOMER_NAME"));
                dashboardDTO.setCovAppRefNumber(resultSet.getString("COVAPP_REFERENCE_NUMBER"));
                dashboardDTO.setBranchCode(resultSet.getString("BRANCH_CODE"));
                dashboardDTO.setBranchName(resultSet.getString("BRANCH_NAME"));
                dashboardDTO.setAccountNumber(resultSet.getString("ACCOUNT_NUMBER"));
                dashboardDTO.setIdentificationNumber(resultSet.getString("IDENTIFICATION_NUMBER"));
                dashboardDTO.setCreatedDate(resultSet.getString("CREATED_DATE"));
                dashboardDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
                dashboardDTO.setAssignedUser(resultSet.getString("ASSIGNED_USER"));
                dashboardDTO.setStatus(resultSet.getString("STATUS"));
                dashboardDTO.setChequeNumber(resultSet.getString("CHEQUE_NUMBER"));
                dashboardDTO.setAmount(resultSet.getString("TRAN_AMOUNT"));
                dashboardDTO.setCovAppId(resultSet.getInt("ID"));
                dashboardDTO.setCustomerName(resultSet.getString("CUSTOMER_NAME"));
                dashboardDTO.setIsAuthorized(resultSet.getBoolean("IS_AUTHORIZED"));
                dashboardDTO.setTranCurrency(resultSet.getString("TRAN_CURRENCY"));
                return dashboardDTO;
            }
        }, coveringApprovalDashboardRQ);
    }

    public List<CovAppStatusHistoryDTO> getCovAppDirectReturnUserList(Integer covAppId) throws AppsException{

        List<CovAppStatusHistoryDTO> statusHistoryDTOS = new ArrayList<>();
        List<String> addedUsersIDList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("covAppId", covAppId);
        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT                                                                                                              \n");
        SQL.append("  tfpsh.ASSIGN_USER,                                                                                                \n");
        SQL.append("  tfpsh.ASSIGN_USER_ID,                                                                                             \n");
        SQL.append("  tfpsh.ASSIGN_USER_DISPLAY_NAME,                                                                                   \n");
        SQL.append("  tfpsh.ASSIGN_USER_UPM_ID,                                                                                         \n");
        SQL.append("  tfpsh.ASSIGN_USER_DIV_CODE,                                                                                       \n");
        SQL.append("  tfpsh.ASSIGN_USER_UPM_GROUP_CODE,                                                                                 \n");
        SQL.append("  u.REFERENCE_NAME                                                                                                  \n");
        SQL.append("FROM T_COVAPP_STATUS_HISTORY tfpsh                                                                                  \n");
        SQL.append("JOIN T_UPM_GROUP u ON tfpsh.ASSIGN_USER_UPM_GROUP_CODE = u.GROUP_CODE                                               \n");
        SQL.append("WHERE tfpsh.COVAPP_ID IS NOT NULL                                                                                   \n");
        SQL.append("  AND tfpsh.ASSIGN_USER_DIV_CODE IS NOT NULL                                                                        \n");
        SQL.append("  AND tfpsh.COVAPP_ID = :covAppId                                                                                   \n");
        SQL.append("ORDER BY tfpsh.UPDATED_DATE DESC                                                                                    \n");


        return this.namedParameterJdbcTemplate.query(SQL.toString(), params, new ResultSetExtractor<List<CovAppStatusHistoryDTO>>() {
            @Override
            public List<CovAppStatusHistoryDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()){
                    if(StringUtils.isNotEmpty(resultSet.getString("ASSIGN_USER_ID")) && !addedUsersIDList.contains(resultSet.getString("ASSIGN_USER_ID"))){
                        CovAppStatusHistoryDTO covAppStatusHistoryDTO = new CovAppStatusHistoryDTO();
                        covAppStatusHistoryDTO.setAssignUserID(resultSet.getString("ASSIGN_USER_ID"));
                        covAppStatusHistoryDTO.setAssignUser(resultSet.getString("ASSIGN_USER"));
                        covAppStatusHistoryDTO.setAssignUserDisplayName(resultSet.getString("ASSIGN_USER_DISPLAY_NAME"));
                        covAppStatusHistoryDTO.setAssignUserUpmID(resultSet.getString("ASSIGN_USER_UPM_ID"));
                        covAppStatusHistoryDTO.setAssignUserDivCode(resultSet.getString("ASSIGN_USER_DIV_CODE"));
                        covAppStatusHistoryDTO.setAssignUserUpmGroupCode(resultSet.getString("ASSIGN_USER_UPM_GROUP_CODE"));
                        covAppStatusHistoryDTO.setReferenceName(resultSet.getString("REFERENCE_NAME"));
                        statusHistoryDTOS.add(covAppStatusHistoryDTO);
                        addedUsersIDList.add(resultSet.getString("ASSIGN_USER_ID"));
                    }
                }
                return statusHistoryDTOS;
            }
        });
    }
}
