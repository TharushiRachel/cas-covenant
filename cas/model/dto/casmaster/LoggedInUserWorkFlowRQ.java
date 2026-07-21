package com.itechro.cas.model.dto.casmaster;

import java.io.Serializable;

public class LoggedInUserWorkFlowRQ implements Serializable {

    private String loggedInUserUpmGroupCode;// Upm AccessLevel level ==> 10 , 20 , 50 , 71 ...

    private String loggedInUserSolID;

    private Integer workFlowTemplateID;

    public String getLoggedInUserUpmGroupCode() {
        return loggedInUserUpmGroupCode;
    }

    public void setLoggedInUserUpmGroupCode(String loggedInUserUpmGroupCode) {
        this.loggedInUserUpmGroupCode = loggedInUserUpmGroupCode;
    }

    public String getLoggedInUserSolID() {
        return loggedInUserSolID;
    }

    public void setLoggedInUserSolID(String loggedInUserSolID) {
        this.loggedInUserSolID = loggedInUserSolID;
    }

    public Integer getWorkFlowTemplateID() {
        return workFlowTemplateID;
    }

    public void setWorkFlowTemplateID(Integer workFlowTemplateID) {
        this.workFlowTemplateID = workFlowTemplateID;
    }

    @Override
    public String toString() {
        return "LoggedInUserWorkFlowRQ{" +
                "loggedInUserUpmGroupCode='" + loggedInUserUpmGroupCode + '\'' +
                ", loggedInUserSolID='" + loggedInUserSolID + '\'' +
                ", workFlowTemplateID=" + workFlowTemplateID +
                '}';
    }
}
