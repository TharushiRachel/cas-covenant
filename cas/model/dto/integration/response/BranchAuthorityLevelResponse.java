package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "USER_ID",
        "FIRST_NAME",
        "LAST_NAME",
        "SOL_ID",
        "DIV_CODE",
        "AD_USER_ID",
        "APPLICATION_CODE",
        "APPLICATION_SECURITY_CLASS",
        "DA_LEVEL",
        "DA_AMOUNT",
        "FUNCTION_CODE1",
        "FUNCTION_CODE2",
        "FUNCTION_CODE3",
        "EMAIL"
})
public class BranchAuthorityLevelResponse implements Serializable {

    @JsonProperty("USER_ID")
    private String userID;

    @JsonProperty("FIRST_NAME")
    private String firstName;

    @JsonProperty("LAST_NAME")
    private String lastName;

    @JsonProperty("SOL_ID")
    private String solID;

    @JsonProperty("DIV_CODE")
    private String divCode;

    @JsonProperty("AD_USER_ID")
    private String adUserID;

    @JsonProperty("APPLICATION_CODE")
    private String applicationCode;

    @JsonProperty("APPLICATION_SECURITY_CLASS")
    private String applicationSecurityClass;

    @JsonProperty("DA_LEVEL")
    private String daLevel;

    @JsonProperty("DA_AMOUNT")
    private String daAmount;

    @JsonProperty("FUNCTION_CODE1")
    private String functionCode1;

    @JsonProperty("FUNCTION_CODE2")
    private String functionCode2;

    @JsonProperty("FUNCTION_CODE3")
    private String functionCode3;

    @JsonProperty("EMAIL")
    private String email;

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

    public String getDivCode() {
        return divCode;
    }

    public void setDivCode(String divCode) {
        this.divCode = divCode;
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

    @Override
    public String toString() {
        return "BranchAuthorityLevelResponse{" +
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
