package com.itechro.cas.model.dto.lead;

import com.itechro.cas.model.common.PagedParamDTO;

public class LeadDashboardRQ extends PagedParamDTO {

    private static final long serialVersionUID = -1062192877923337640L;

    private String loggedInUserId;
    private String loginUpmAccessLevel;
    private String loggedInUserBranchCode; //divCode
    private String leadDashboardStatus;

    private String leadDashboardSubStatus;

    public String getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public String getLoginUpmAccessLevel() {
        return loginUpmAccessLevel;
    }

    public void setLoginUpmAccessLevel(String loginUpmAccessLevel) {
        this.loginUpmAccessLevel = loginUpmAccessLevel;
    }

    public String getLoggedInUserBranchCode() {
        return loggedInUserBranchCode;
    }

    public void setLoggedInUserBranchCode(String loggedInUserBranchCode) {
        this.loggedInUserBranchCode = loggedInUserBranchCode;
    }

    public String getLeadDashboardStatus() {
        return leadDashboardStatus;
    }

    public void setLeadDashboardStatus(String leadDashboardStatus) {
        this.leadDashboardStatus = leadDashboardStatus;
    }

    public String getLeadDashboardSubStatus() {
        return leadDashboardSubStatus;
    }

    public void setLeadDashboardSubStatus(String leadDashboardSubStatus) {
        this.leadDashboardSubStatus = leadDashboardSubStatus;
    }

    @Override
    public String toString() {
        return "LeadDashboardRQ{" +
                "loggedInUserId='" + loggedInUserId + '\'' +
                ", loginUpmAccessLevel='" + loginUpmAccessLevel + '\'' +
                ", loggedInUserBranchCode='" + loggedInUserBranchCode + '\'' +
                ", leadDashboardStatus='" + leadDashboardStatus + '\'' +
                ", leadDashboardSubStatus='" + leadDashboardSubStatus + '\'' +
                '}';
    }
}
