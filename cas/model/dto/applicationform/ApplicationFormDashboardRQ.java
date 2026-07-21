package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.model.common.PagedParamDTO;

public class ApplicationFormDashboardRQ extends PagedParamDTO {

    private static final long serialVersionUID = -1062192877923337640L;

    private String loggedInUserId;
    private String loginUpmAccessLevel;
    private String loggedInUserBranchCode; //divCode
    private String applicationFormDashboardStatus;

    private String applicationFormDashboardSubStatus;

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

    public String getApplicationFormDashboardStatus() {
        return applicationFormDashboardStatus;
    }

    public void setApplicationFormdDashboardStatus(String leadDashboardStatus) {
        this.applicationFormDashboardStatus = applicationFormDashboardStatus;
    }

    public void setApplicationFormDashboardStatus(String applicationFormDashboardStatus) {
        this.applicationFormDashboardStatus = applicationFormDashboardStatus;
    }

    public String getApplicationFormDashboardSubStatus() {
        return applicationFormDashboardSubStatus;
    }

    public void setApplicationFormDashboardSubStatus(String applicationFormDashboardSubStatus) {
        this.applicationFormDashboardSubStatus = applicationFormDashboardSubStatus;
    }

    @Override
    public String toString() {
        return "ApplicationFormDashboardRQ{" +
                "loggedInUserId='" + loggedInUserId + '\'' +
                ", loginUpmAccessLevel='" + loginUpmAccessLevel + '\'' +
                ", loggedInUserBranchCode='" + loggedInUserBranchCode + '\'' +
                ", applicationFormDashboardStatus='" + applicationFormDashboardStatus + '\'' +
                ", applicationFormDashboardSubStatus='" + applicationFormDashboardSubStatus + '\'' +
                '}';
    }
}
