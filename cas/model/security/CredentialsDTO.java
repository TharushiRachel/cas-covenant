package com.itechro.cas.model.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class CredentialsDTO implements Serializable {

    private static final long serialVersionUID = -1450496936621785269L;

    private Integer userID;

    private String userName;

    private String divCode;

    private String upmGroupCode;

    private String displayName;

    private String requestIpAddress;

    private Collection<? extends GrantedAuthority> authorities;

    private boolean agent;

    private boolean isAssistant;

    private boolean isSupervisor;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDivCode() {
        return divCode;
    }

    public void setDivCode(String divCode) {
        this.divCode = divCode;
    }

    public String getUpmGroupCode() {
        return upmGroupCode;
    }

    public void setUpmGroupCode(String upmGroupCode) {
        this.upmGroupCode = upmGroupCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRequestIpAddress() {
        return requestIpAddress;
    }

    public void setRequestIpAddress(String requestIpAddress) {
        this.requestIpAddress = requestIpAddress;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean isAgent() {
        return agent;
    }

    public void setAgent(boolean agent) {
        this.agent = agent;
    }

    public boolean getIsAssistant() {
        return isAssistant;
    }

    public void setIsAssistant(boolean assistant) {
        isAssistant = assistant;
    }

    public boolean getIsSupervisor() {
        return isSupervisor;
    }

    public void setIsSupervisor(boolean supervisor) {
        isSupervisor = supervisor;
    }

    @Override
    public String toString() {
        return "CredentialsDTO{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", requestIpAddress='" + requestIpAddress + '\'' +
                ", isAssistant=" + isAssistant +
                '}';
    }
}
