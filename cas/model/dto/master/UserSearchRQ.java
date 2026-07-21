package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserSearchRQ extends PagedParamDTO implements Serializable {

    private static final long serialVersionUID = 4037385156027883973L;
    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> uneditableUsers;

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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public List<String> getUneditableUsers() {
        if (uneditableUsers == null) {
            this.uneditableUsers = new ArrayList<>();
        }
        return uneditableUsers;
    }

    public void setUneditableUsers(List<String> uneditableUsers) {
        this.uneditableUsers = uneditableUsers;
    }

    @Override
    public String toString() {
        return "UserSearchRQ{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}
