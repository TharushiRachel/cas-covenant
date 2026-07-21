package com.itechro.cas.model.dto.integration.response;

import java.io.Serializable;

public class UpmDetailResponse implements Serializable {

    private String userID;

    private String supID;

    private String firstName;

    private String lastName;

    private String designationCode;

    private String designationDescription;

    private String securityClass;

    private String workClassBot;

    private String adUserID;

    private String signatureID;

    private String officerStatus;

    private String daLevelBot;

    private String daAmountBot;

    private String mobilePhone;

    private String email;

    private String solID;

    private String divCode;

    private String botStatus;

    private String applicationCode;

    private String applicationSecurityClass;

    private String workClassOat;

    private String expiryDate;

    private String daLevelOat;

    private String daAmountOat;

    private String signOnStatus;

    private String activeStatus;

    private String functionCode1;

    private String functionCode2;

    private String functionCode3;

    private String oatStatus;

    private String solName;

    private String areaCode;

    private String zone;

    private String solDivID;

    private boolean success;

    public UpmDetailResponse() {
    }

    public UpmDetailResponse(UpmDetailLoadRS upmDetailLoadRS) {

        this.userID = upmDetailLoadRS.getUserID();
        this.supID = upmDetailLoadRS.getSupID();
        this.firstName = upmDetailLoadRS.getFirstName();
        this.lastName = upmDetailLoadRS.getLastName();
        this.designationCode = upmDetailLoadRS.getDesignationCode();
        this.designationDescription = upmDetailLoadRS.getDesignationDescription();
        this.securityClass = upmDetailLoadRS.getSecurityClass();
        this.workClassBot = upmDetailLoadRS.getWorkClassBot();
        this.adUserID = upmDetailLoadRS.getAdUserID();
        this.signatureID = upmDetailLoadRS.getSignatureID();
        this.officerStatus = upmDetailLoadRS.getOfficerStatus();
        this.daLevelBot = upmDetailLoadRS.getDaLevelBot();
        this.daAmountBot = upmDetailLoadRS.getDaAmountBot();
        this.mobilePhone = upmDetailLoadRS.getMobilePhone();
        this.email = upmDetailLoadRS.getEmail();
        this.solID = upmDetailLoadRS.getSolID();
        this.divCode = upmDetailLoadRS.getDivCode();
        this.botStatus = upmDetailLoadRS.getBotStatus();
        this.applicationCode = upmDetailLoadRS.getApplicationCode();
        this.applicationSecurityClass = upmDetailLoadRS.getApplicationSecurityClass();
        this.workClassOat = upmDetailLoadRS.getWorkClassOat();
        this.expiryDate = upmDetailLoadRS.getExpiryDate();
        this.daLevelOat = upmDetailLoadRS.getDaLevelOat();
        this.daAmountOat = upmDetailLoadRS.getDaAmountOat();
        this.signOnStatus = upmDetailLoadRS.getSignOnStatus();
        this.activeStatus = upmDetailLoadRS.getActiveStatus();
        this.functionCode1 = upmDetailLoadRS.getFunctionCode1();
        this.functionCode2 = upmDetailLoadRS.getFunctionCode2();
        this.functionCode3 = upmDetailLoadRS.getFunctionCode3();
        this.oatStatus = upmDetailLoadRS.getOatStatus();
        this.solName = upmDetailLoadRS.getSolName();
        this.areaCode = upmDetailLoadRS.getAreaCode();
        this.zone = upmDetailLoadRS.getZone();
        this.solDivID = upmDetailLoadRS.getSolDivID();

    }

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
        if (firstName == null) {
            firstName = "";
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (lastName == null) {
            lastName = "";
        }
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "UpmDetailResponse{" +
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
                ", success=" + success +
                '}';
    }
}
