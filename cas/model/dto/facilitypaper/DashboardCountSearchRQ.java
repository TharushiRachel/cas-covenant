package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DashboardCountSearchRQ implements Serializable {

    private static final long serialVersionUID = 8829060961289405906L;

    private String currentAssignUser;

    private String intiatedUserName;

    private String loggedInUserBranchCode; //DIV Code

    private String loggedInUserUPMGroupCode;

    private List<String> assignUsers;

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }

    public String getIntiatedUserName() {
        return intiatedUserName;
    }

    public void setIntiatedUserName(String intiatedUserName) {
        this.intiatedUserName = intiatedUserName;
    }

    public String getLoggedInUserBranchCode() {
        return loggedInUserBranchCode;
    }

    public void setLoggedInUserBranchCode(String loggedInUserBranchCode) {
        this.loggedInUserBranchCode = loggedInUserBranchCode;
    }

    public String getLoggedInUserUPMGroupCode() {
        return loggedInUserUPMGroupCode;
    }

    public void setLoggedInUserUPMGroupCode(String loggedInUserUPMGroupCode) {
        this.loggedInUserUPMGroupCode = loggedInUserUPMGroupCode;
    }

    public List<String> getAssignUsers() {
        if (assignUsers == null) {
            this.assignUsers = new ArrayList<>();
        }
        return assignUsers;
    }

    public void setAssignUsers(List<String> assignUsers) {
        this.assignUsers = assignUsers;
    }

    @Override
    public String toString() {
        return "DashboardCountSearchRQ{" +
                "currentAssignUser='" + currentAssignUser + '\'' +
                ", intiatedUserName='" + intiatedUserName + '\'' +
                ", loggedInUserBranchCode='" + loggedInUserBranchCode + '\'' +
                ", loggedInUserUPMGroupCode='" + loggedInUserUPMGroupCode + '\'' +
                ", assignUsers=" + assignUsers +
                '}';
    }
}
