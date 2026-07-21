package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.common.PagedParamDTO;

public class CommitteePaperDashboardRQ extends PagedParamDTO {

    private static final long serialVersionUID = -1062192877923337640L;

    private String loggedInUserId;
    private String loginUpmAccessLevel;
    private String loggedInUserBranchCode; //divCode
    private String committeePaperDashboardStatus;
    private String committeePaperDashboardSubStatus;
    private String loggedInUserRole;


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

    public String getCommitteePaperDashboardStatus() {
        return committeePaperDashboardStatus;
    }

    public void setCommitteePaperDashboardStatus(String committeePaperDashboardStatus) {
        this.committeePaperDashboardStatus = committeePaperDashboardStatus;
    }

    public String getCommitteePaperDashboardSubStatus() {
        return committeePaperDashboardSubStatus;
    }

    public void setCommitteePaperDashboardSubStatus(String committeePaperDashboardSubStatus) {
        this.committeePaperDashboardSubStatus = committeePaperDashboardSubStatus;
    }

    public String getLoggedInUserRole() {
        return loggedInUserRole;
    }

    public void setLoggedInUserRole(String loggedInUserRole) {
        this.loggedInUserRole = loggedInUserRole;
    }

    @Override
    public String toString() {
        return "CommitteePaperDashboardRQ{" +
                "loggedInUserId='" + loggedInUserId + '\'' +
                ", loginUpmAccessLevel='" + loginUpmAccessLevel + '\'' +
                ", loggedInUserBranchCode='" + loggedInUserBranchCode + '\'' +
                ", committeePaperDashboardStatus='" + committeePaperDashboardStatus + '\'' +
                ", committeePaperDashboardSubStatus='" + committeePaperDashboardSubStatus + '\'' +
                ", loggedInUserRole='" + loggedInUserRole + '\'' +
                '}';
    }
}
