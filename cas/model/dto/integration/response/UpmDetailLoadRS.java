package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "USER_ID",
        "SUP_ID",
        "FIRST_NAME",
        "LAST_NAME",
        "DESIGNATION_CODE",
        "DESIGNATION_DESC",
        "SECURITY_CLASS",
        "WORK_CLASS_BOT",
        "AD_USER_ID",
        "SIGNATURE_ID",
        "OFFICER_STATUS",
        "DA_LEVEL_BOT",
        "DA_AMOUNT_BOT",
        "MOBILE_PHONE",
        "EMAIL",
        "SOL_ID",
        "DIV_CODE",
        "BOT_STATUS",
        "APPLICATION_CODE",
        "APPLICATION_SECURITY_CLASS",
        "WORK_CLASS_OAT",
        "EXPIRY_DATE",
        "DA_LEVEL_OAT",
        "DA_AMOUNT_OAT",
        "SIGN_ON_STATUS",
        "ACTIVE_STATUS",
        "FUNCTION_CODE1",
        "FUNCTION_CODE2",
        "FUNCTION_CODE3",
        "OAT_STATUS",
        "SOL_NAME",
        "AREA_CODE",
        "ZONE",
        "SOL_DIV_ID",
        "FINACLE_WORK_CLASS"
})
public class UpmDetailLoadRS implements Serializable {

    @JsonProperty("USER_ID")
    private String userID;

    @JsonProperty("SUP_ID")
    private String supID;

    @JsonProperty("FIRST_NAME")
    private String firstName;

    @JsonProperty("LAST_NAME")
    private String lastName;

    @JsonProperty("DESIGNATION_CODE")
    private String designationCode;

    @JsonProperty("DESIGNATION_DESC")
    private String designationDescription;

    @JsonProperty("SECURITY_CLASS")
    private String securityClass;

    @JsonProperty("WORK_CLASS_BOT")
    private String workClassBot;

    @JsonProperty("AD_USER_ID")
    private String adUserID;

    @JsonProperty("SIGNATURE_ID")
    private String signatureID;

    @JsonProperty("OFFICER_STATUS")
    private String officerStatus;

    @JsonProperty("DA_LEVEL_BOT")
    private String daLevelBot;

    @JsonProperty("DA_AMOUNT_BOT")
    private String daAmountBot;

    @JsonProperty("MOBILE_PHONE")
    private String mobilePhone;

    @JsonProperty("EMAIL")
    private String email;

    @JsonProperty("SOL_ID")
    private String solID;

    @JsonProperty("DIV_CODE")
    private String divCode;

    @JsonProperty("BOT_STATUS")
    private String botStatus;

    @JsonProperty("APPLICATION_CODE")
    private String applicationCode;

    @JsonProperty("APPLICATION_SECURITY_CLASS")
    private String applicationSecurityClass;

    @JsonProperty("WORK_CLASS_OAT")
    private String workClassOat;

    @JsonProperty("EXPIRY_DATE")
    private String expiryDate;

    @JsonProperty("DA_LEVEL_OAT")
    private String daLevelOat;

    @JsonProperty("DA_AMOUNT_OAT")
    private String daAmountOat;

    @JsonProperty("SIGN_ON_STATUS")
    private String signOnStatus;

    @JsonProperty("ACTIVE_STATUS")
    private String activeStatus;

    @JsonProperty("FUNCTION_CODE1")
    private String functionCode1;

    @JsonProperty("FUNCTION_CODE2")
    private String functionCode2;

    @JsonProperty("FUNCTION_CODE3")
    private String functionCode3;

    @JsonProperty("OAT_STATUS")
    private String oatStatus;

    @JsonProperty("SOL_NAME")
    private String solName;

    @JsonProperty("AREA_CODE")
    private String areaCode;

    @JsonProperty("ZONE")
    private String zone;

    @JsonProperty("SOL_DIV_ID")
    private String solDivID;

    @JsonProperty("FINACLE_WORK_CLASS")
    private String finacleWorkClass;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSupID() {
        return supID;
    }

    public void setSupID(String supID) {
        this.supID = supID;
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

    public String getDesignationCode() {
        return designationCode;
    }

    public void setDesignationCode(String designationCode) {
        this.designationCode = designationCode;
    }

    public String getDesignationDescription() {
        return designationDescription;
    }

    public void setDesignationDescription(String designationDescription) {
        this.designationDescription = designationDescription;
    }

    public String getSecurityClass() {
        return securityClass;
    }

    public void setSecurityClass(String securityClass) {
        this.securityClass = securityClass;
    }

    public String getWorkClassBot() {
        return workClassBot;
    }

    public void setWorkClassBot(String workClassBot) {
        this.workClassBot = workClassBot;
    }

    public String getAdUserID() {
        return adUserID;
    }

    public void setAdUserID(String adUserID) {
        this.adUserID = adUserID;
    }

    public String getSignatureID() {
        return signatureID;
    }

    public void setSignatureID(String signatureID) {
        this.signatureID = signatureID;
    }

    public String getOfficerStatus() {
        return officerStatus;
    }

    public void setOfficerStatus(String officerStatus) {
        this.officerStatus = officerStatus;
    }

    public String getDaLevelBot() {
        return daLevelBot;
    }

    public void setDaLevelBot(String daLevelBot) {
        this.daLevelBot = daLevelBot;
    }

    public String getDaAmountBot() {
        return daAmountBot;
    }

    public void setDaAmountBot(String daAmountBot) {
        this.daAmountBot = daAmountBot;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBotStatus() {
        return botStatus;
    }

    public void setBotStatus(String botStatus) {
        this.botStatus = botStatus;
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

    public String getWorkClassOat() {
        return workClassOat;
    }

    public void setWorkClassOat(String workClassOat) {
        this.workClassOat = workClassOat;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDaLevelOat() {
        return daLevelOat;
    }

    public void setDaLevelOat(String daLevelOat) {
        this.daLevelOat = daLevelOat;
    }

    public String getDaAmountOat() {
        return daAmountOat;
    }

    public void setDaAmountOat(String daAmountOat) {
        this.daAmountOat = daAmountOat;
    }

    public String getSignOnStatus() {
        return signOnStatus;
    }

    public void setSignOnStatus(String signOnStatus) {
        this.signOnStatus = signOnStatus;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
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

    public String getOatStatus() {
        return oatStatus;
    }

    public void setOatStatus(String oatStatus) {
        this.oatStatus = oatStatus;
    }

    public String getSolName() {
        return solName;
    }

    public void setSolName(String solName) {
        this.solName = solName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSolDivID() {
        return solDivID;
    }

    public void setSolDivID(String solDivID) {
        this.solDivID = solDivID;
    }

    public String getFinacleWorkClass() {
        return finacleWorkClass;
    }

    public void setFinacleWorkClass(String finacleWorkClass) {
        this.finacleWorkClass = finacleWorkClass;
    }

    @Override
    public String toString() {
        return "UpmDetailLoadRS{" +
                "userID='" + userID + '\'' +
                ", supID='" + supID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", designationCode='" + designationCode + '\'' +
                ", designationDescription='" + designationDescription + '\'' +
                ", securityClass='" + securityClass + '\'' +
                ", workClassBot='" + workClassBot + '\'' +
                ", adUserID='" + adUserID + '\'' +
                ", signatureID='" + signatureID + '\'' +
                ", officerStatus='" + officerStatus + '\'' +
                ", daLevelBot='" + daLevelBot + '\'' +
                ", daAmountBot='" + daAmountBot + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", solID='" + solID + '\'' +
                ", divCode='" + divCode + '\'' +
                ", botStatus='" + botStatus + '\'' +
                ", applicationCode='" + applicationCode + '\'' +
                ", applicationSecurityClass='" + applicationSecurityClass + '\'' +
                ", workClassOat='" + workClassOat + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", daLevelOat='" + daLevelOat + '\'' +
                ", daAmountOat='" + daAmountOat + '\'' +
                ", signOnStatus='" + signOnStatus + '\'' +
                ", activeStatus='" + activeStatus + '\'' +
                ", functionCode1='" + functionCode1 + '\'' +
                ", functionCode2='" + functionCode2 + '\'' +
                ", functionCode3='" + functionCode3 + '\'' +
                ", oatStatus='" + oatStatus + '\'' +
                ", solName='" + solName + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", zone='" + zone + '\'' +
                ", solDivID='" + solDivID + '\'' +
                ", finacleWorkClass='" + finacleWorkClass + '\'' +
                '}';
    }
}
