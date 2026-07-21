package com.itechro.cas.model.dto.integration.response;

import java.io.Serializable;

public class BranchAuthorityLevelResponseDTO implements Serializable {

    private String userID;

    private String firstName;

    private String lastName;

    private String solID;

    private String divCode;

    private String adUserID;

    private String applicationCode;

    private String applicationSecurityClass;

    private String daLevel;

    private String daAmount;

    private String functionCode1;

    private String functionCode2;

    private String functionCode3;

    private String email;

    public BranchAuthorityLevelResponseDTO(BranchAuthorityLevelResponse branchAuthorityLevelResponse){

        this.userID = branchAuthorityLevelResponse.getUserID();
        this.firstName = branchAuthorityLevelResponse.getFirstName();
        this.lastName = branchAuthorityLevelResponse.getLastName();
        this.solID = branchAuthorityLevelResponse.getSolID();
        this.divCode = branchAuthorityLevelResponse.getDivCode();
        this.adUserID = branchAuthorityLevelResponse.getAdUserID();
        this.applicationCode = branchAuthorityLevelResponse.getApplicationCode();
        this.applicationSecurityClass = branchAuthorityLevelResponse.getApplicationSecurityClass();
        this.daLevel = branchAuthorityLevelResponse.getDaLevel();
        this.daAmount = branchAuthorityLevelResponse.getDaAmount();
        this.functionCode1 = branchAuthorityLevelResponse.getFunctionCode1();
        this.functionCode2 = branchAuthorityLevelResponse.getFunctionCode2();
        this.functionCode3 = branchAuthorityLevelResponse.getFunctionCode3();
        this.email = branchAuthorityLevelResponse.getEmail();

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getSolID() {
        return solID;
    }

    public void setSolID(String solID) {
        this.solID = solID;
    }

    public String getAdUserID() {
        return adUserID;
    }

    public void setAdUserID(String adUserID) {
        this.adUserID = adUserID;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getApplicationSecurityClass() {
        return applicationSecurityClass;
    }

    public void setApplicationSecurityClass(String applicationSecurityClass) {
        this.applicationSecurityClass = applicationSecurityClass;
    }

    public String getDaLevel() {
        return daLevel;
    }

    public void setDaLevel(String daLevel) {
        this.daLevel = daLevel;
    }

    public String getDaAmount() {
        return daAmount;
    }

    public void setDaAmount(String daAmount) {
        this.daAmount = daAmount;
    }

    public String getFunctionCode1() {
        return functionCode1;
    }

    public void setFunctionCode1(String functionCode1) {
        this.functionCode1 = functionCode1;
    }

    public String getFunctionCode2() {
        return functionCode2;
    }

    public void setFunctionCode2(String functionCode2) {
        this.functionCode2 = functionCode2;
    }

    public String getFunctionCode3() {
        return functionCode3;
    }

    public void setFunctionCode3(String functionCode3) {
        this.functionCode3 = functionCode3;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDivCode() {
        return divCode;
    }

    public void setDivCode(String divCode) {
        this.divCode = divCode;
    }

    @Override
    public String toString() {
        return "BranchAuthorityLevelResponseDTO{" +
                "userID='" + userID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", solID='" + solID + '\'' +
                ", divCode='" + divCode + '\'' +
                ", adUserID='" + adUserID + '\'' +
                ", applicationCode='" + applicationCode + '\'' +
                ", applicationSecurityClass='" + applicationSecurityClass + '\'' +
                ", daLevel='" + daLevel + '\'' +
                ", daAmount='" + daAmount + '\'' +
                ", functionCode1='" + functionCode1 + '\'' +
                ", functionCode2='" + functionCode2 + '\'' +
                ", functionCode3='" + functionCode3 + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
