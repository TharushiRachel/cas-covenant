package com.itechro.cas.model.dto.agent;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;

public class AgentSearchRQ extends PagedParamDTO implements Serializable {

    private static final long serialVersionUID = -7474146999491802248L;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String nic;

    private String supervisorADUserID;

    private AppsConstants.Status status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getSupervisorADUserID() {
        return supervisorADUserID;
    }

    public void setSupervisorADUserID(String supervisorADUserID) {
        this.supervisorADUserID = supervisorADUserID;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AgentSearchRQ{" +
                "userName='" + userName + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", nic='" + nic + '\'' +
                ", supervisorADUserID='" + supervisorADUserID + '\'' +
                ", status=" + status +
                '}';
    }
}
