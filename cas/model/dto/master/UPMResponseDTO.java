package com.itechro.cas.model.dto.master;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "DA_AMOUNT_BOT",
        "APPLICATION_SECURITY_CLASS",
        "DA_LEVEL_BOT",
        "SOL_ID",
        "BOT_STATUS",
        "ACTIVE_STATUS",
        "SUP_ID",
        "OAT_STATUS",
        "USER_ID",
        "SIGN_ON_STATUS",
        "WORK_CLASS_BOT",
        "LAST_NAME",
        "DESIGNATION_CODE",
        "SOL_DIV_ID",
        "SECURITY_CLASS",
        "FIRST_NAME",
        "APPLICATION_CODE",
        "DA_AMOUNT_OAT",
        "AREA_CODE",
        "SIGNATURE_ID",
        "DA_LEVEL_OAT",
        "ZONE",
        "WORK_CLASS_OAT",
        "MOBILE_PHONE",
        "AD_USER_ID",
        "DESIGNATION_DESC",
        "SOL_NAME",
        "EXPIRY_DATE",
        "OFFICER_STATUS",
        "DIV_CODE",
        "EMAIL",
        "FUNCTION_CODE1",
        "FUNCTION_CODE2",
        "FUNCTION_CODE3",
        "FINACLE_WORK_CLASS",
})
public class UPMResponseDTO implements Serializable {

    @JsonProperty("DA_AMOUNT_BOT")
    private String dAAMOUNTBOT;
    @JsonProperty("APPLICATION_SECURITY_CLASS")
    private String aPPLICATIONSECURITYCLASS;
    @JsonProperty("DA_LEVEL_BOT")
    private Object dALEVELBOT;
    @JsonProperty("SOL_ID")
    private String sOLID;
    @JsonProperty("BOT_STATUS")
    private String bOTSTATUS;
    @JsonProperty("ACTIVE_STATUS")
    private String aCTIVESTATUS;
    @JsonProperty("SUP_ID")
    private String sUPID;
    @JsonProperty("OAT_STATUS")
    private String oATSTATUS;
    @JsonProperty("USER_ID")
    private String uSERID;
    @JsonProperty("SIGN_ON_STATUS")
    private String sIGNONSTATUS;
    @JsonProperty("WORK_CLASS_BOT")
    private String wORKCLASSBOT;
    @JsonProperty("LAST_NAME")
    private String lASTNAME;
    @JsonProperty("DESIGNATION_CODE")
    private String dESIGNATIONCODE;
    @JsonProperty("SOL_DIV_ID")
    private String sOLDIVID;
    @JsonProperty("SECURITY_CLASS")
    private String sECURITYCLASS;
    @JsonProperty("FIRST_NAME")
    private String fIRSTNAME;
    @JsonProperty("APPLICATION_CODE")
    private String aPPLICATIONCODE;
    @JsonProperty("DA_AMOUNT_OAT")
    private String dAAMOUNTOAT;
    @JsonProperty("AREA_CODE")
    private String aREACODE;
    @JsonProperty("SIGNATURE_ID")
    private String sIGNATUREID;
    @JsonProperty("DA_LEVEL_OAT")
    private String dALEVELOAT;
    @JsonProperty("ZONE")
    private Object zONE;
    @JsonProperty("WORK_CLASS_OAT")
    private String wORKCLASSOAT;
    @JsonProperty("MOBILE_PHONE")
    private Object mOBILEPHONE;
    @JsonProperty("AD_USER_ID")
    private String aDUSERID;
    @JsonProperty("DESIGNATION_DESC")
    private String dESIGNATIONDESC;
    @JsonProperty("SOL_NAME")
    private String sOLNAME;
    @JsonProperty("EXPIRY_DATE")
    private String eXPIRYDATE;
    @JsonProperty("OFFICER_STATUS")
    private String oFFICERSTATUS;
    @JsonProperty("DIV_CODE")
    private String dIVCODE;
    @JsonProperty("EMAIL")
    private String eMAIL;
    @JsonProperty("FUNCTION_CODE1")
    private String fUNCTIONCODE1;
    @JsonProperty("FUNCTION_CODE2")
    private Object fUNCTIONCODE2;
    @JsonProperty("FUNCTION_CODE3")
    private Object fUNCTIONCODE3;
    @JsonProperty("FINACLE_WORK_CLASS")
    private String fINACLEWORKCLASS;

    @JsonProperty("DA_AMOUNT_BOT")
    public String getDAAMOUNTBOT() {
        return dAAMOUNTBOT;
    }

    @JsonProperty("DA_AMOUNT_BOT")
    public void setDAAMOUNTBOT(String dAAMOUNTBOT) {
        this.dAAMOUNTBOT = dAAMOUNTBOT;
    }

    @JsonProperty("APPLICATION_SECURITY_CLASS")
    public String getAPPLICATIONSECURITYCLASS() {
        return aPPLICATIONSECURITYCLASS;
    }

    @JsonProperty("APPLICATION_SECURITY_CLASS")
    public void setAPPLICATIONSECURITYCLASS(String aPPLICATIONSECURITYCLASS) {
        this.aPPLICATIONSECURITYCLASS = aPPLICATIONSECURITYCLASS;
    }

    @JsonProperty("DA_LEVEL_BOT")
    public Object getDALEVELBOT() {
        return dALEVELBOT;
    }

    @JsonProperty("DA_LEVEL_BOT")
    public void setDALEVELBOT(Object dALEVELBOT) {
        this.dALEVELBOT = dALEVELBOT;
    }

    @JsonProperty("SOL_ID")
    public String getSOLID() {
        return sOLID;
    }

    @JsonProperty("SOL_ID")
    public void setSOLID(String sOLID) {
        this.sOLID = sOLID;
    }

    @JsonProperty("BOT_STATUS")
    public String getBOTSTATUS() {
        return bOTSTATUS;
    }

    @JsonProperty("BOT_STATUS")
    public void setBOTSTATUS(String bOTSTATUS) {
        this.bOTSTATUS = bOTSTATUS;
    }

    @JsonProperty("ACTIVE_STATUS")
    public String getACTIVESTATUS() {
        return aCTIVESTATUS;
    }

    @JsonProperty("ACTIVE_STATUS")
    public void setACTIVESTATUS(String aCTIVESTATUS) {
        this.aCTIVESTATUS = aCTIVESTATUS;
    }

    @JsonProperty("SUP_ID")
    public String getSUPID() {
        return sUPID;
    }

    @JsonProperty("SUP_ID")
    public void setSUPID(String sUPID) {
        this.sUPID = sUPID;
    }

    @JsonProperty("OAT_STATUS")
    public String getOATSTATUS() {
        return oATSTATUS;
    }

    @JsonProperty("OAT_STATUS")
    public void setOATSTATUS(String oATSTATUS) {
        this.oATSTATUS = oATSTATUS;
    }

    @JsonProperty("USER_ID")
    public String getUSERID() {
        return uSERID;
    }

    @JsonProperty("USER_ID")
    public void setUSERID(String uSERID) {
        this.uSERID = uSERID;
    }

    @JsonProperty("SIGN_ON_STATUS")
    public String getSIGNONSTATUS() {
        return sIGNONSTATUS;
    }

    @JsonProperty("SIGN_ON_STATUS")
    public void setSIGNONSTATUS(String sIGNONSTATUS) {
        this.sIGNONSTATUS = sIGNONSTATUS;
    }

    @JsonProperty("WORK_CLASS_BOT")
    public String getWORKCLASSBOT() {
        return wORKCLASSBOT;
    }

    @JsonProperty("WORK_CLASS_BOT")
    public void setWORKCLASSBOT(String wORKCLASSBOT) {
        this.wORKCLASSBOT = wORKCLASSBOT;
    }

    @JsonProperty("LAST_NAME")
    public String getLASTNAME() {
        return lASTNAME;
    }

    @JsonProperty("LAST_NAME")
    public void setLASTNAME(String lASTNAME) {
        this.lASTNAME = lASTNAME;
    }

    @JsonProperty("DESIGNATION_CODE")
    public String getDESIGNATIONCODE() {
        return dESIGNATIONCODE;
    }

    @JsonProperty("DESIGNATION_CODE")
    public void setDESIGNATIONCODE(String dESIGNATIONCODE) {
        this.dESIGNATIONCODE = dESIGNATIONCODE;
    }

    @JsonProperty("SOL_DIV_ID")
    public String getSOLDIVID() {
        return sOLDIVID;
    }

    @JsonProperty("SOL_DIV_ID")
    public void setSOLDIVID(String sOLDIVID) {
        this.sOLDIVID = sOLDIVID;
    }

    @JsonProperty("SECURITY_CLASS")
    public String getSECURITYCLASS() {
        return sECURITYCLASS;
    }

    @JsonProperty("SECURITY_CLASS")
    public void setSECURITYCLASS(String sECURITYCLASS) {
        this.sECURITYCLASS = sECURITYCLASS;
    }

    @JsonProperty("FIRST_NAME")
    public String getFIRSTNAME() {
        return fIRSTNAME;
    }

    @JsonProperty("FIRST_NAME")
    public void setFIRSTNAME(String fIRSTNAME) {
        this.fIRSTNAME = fIRSTNAME;
    }

    @JsonProperty("APPLICATION_CODE")
    public String getAPPLICATIONCODE() {
        return aPPLICATIONCODE;
    }

    @JsonProperty("APPLICATION_CODE")
    public void setAPPLICATIONCODE(String aPPLICATIONCODE) {
        this.aPPLICATIONCODE = aPPLICATIONCODE;
    }

    @JsonProperty("DA_AMOUNT_OAT")
    public String getDAAMOUNTOAT() {
        return dAAMOUNTOAT;
    }

    @JsonProperty("DA_AMOUNT_OAT")
    public void setDAAMOUNTOAT(String dAAMOUNTOAT) {
        this.dAAMOUNTOAT = dAAMOUNTOAT;
    }

    @JsonProperty("AREA_CODE")
    public String getAREACODE() {
        return aREACODE;
    }

    @JsonProperty("AREA_CODE")
    public void setAREACODE(String aREACODE) {
        this.aREACODE = aREACODE;
    }

    @JsonProperty("SIGNATURE_ID")
    public String getSIGNATUREID() {
        return sIGNATUREID;
    }

    @JsonProperty("SIGNATURE_ID")
    public void setSIGNATUREID(String sIGNATUREID) {
        this.sIGNATUREID = sIGNATUREID;
    }

    @JsonProperty("DA_LEVEL_OAT")
    public String getDALEVELOAT() {
        return dALEVELOAT;
    }

    @JsonProperty("DA_LEVEL_OAT")
    public void setDALEVELOAT(String dALEVELOAT) {
        this.dALEVELOAT = dALEVELOAT;
    }

    @JsonProperty("ZONE")
    public Object getZONE() {
        return zONE;
    }

    @JsonProperty("ZONE")
    public void setZONE(Object zONE) {
        this.zONE = zONE;
    }

    @JsonProperty("WORK_CLASS_OAT")
    public String getWORKCLASSOAT() {
        return wORKCLASSOAT;
    }

    @JsonProperty("WORK_CLASS_OAT")
    public void setWORKCLASSOAT(String wORKCLASSOAT) {
        this.wORKCLASSOAT = wORKCLASSOAT;
    }

    @JsonProperty("MOBILE_PHONE")
    public Object getMOBILEPHONE() {
        return mOBILEPHONE;
    }

    @JsonProperty("MOBILE_PHONE")
    public void setMOBILEPHONE(Object mOBILEPHONE) {
        this.mOBILEPHONE = mOBILEPHONE;
    }

    @JsonProperty("AD_USER_ID")
    public String getADUSERID() {
        return aDUSERID;
    }

    @JsonProperty("AD_USER_ID")
    public void setADUSERID(String aDUSERID) {
        this.aDUSERID = aDUSERID;
    }

    @JsonProperty("DESIGNATION_DESC")
    public String getDESIGNATIONDESC() {
        return dESIGNATIONDESC;
    }

    @JsonProperty("DESIGNATION_DESC")
    public void setDESIGNATIONDESC(String dESIGNATIONDESC) {
        this.dESIGNATIONDESC = dESIGNATIONDESC;
    }

    @JsonProperty("SOL_NAME")
    public String getSOLNAME() {
        return sOLNAME;
    }

    @JsonProperty("SOL_NAME")
    public void setSOLNAME(String sOLNAME) {
        this.sOLNAME = sOLNAME;
    }

    @JsonProperty("EXPIRY_DATE")
    public String getEXPIRYDATE() {
        return eXPIRYDATE;
    }

    @JsonProperty("EXPIRY_DATE")
    public void setEXPIRYDATE(String eXPIRYDATE) {
        this.eXPIRYDATE = eXPIRYDATE;
    }

    @JsonProperty("OFFICER_STATUS")
    public String getOFFICERSTATUS() {
        return oFFICERSTATUS;
    }

    @JsonProperty("OFFICER_STATUS")
    public void setOFFICERSTATUS(String oFFICERSTATUS) {
        this.oFFICERSTATUS = oFFICERSTATUS;
    }

    @JsonProperty("DIV_CODE")
    public String getDIVCODE() {
        return dIVCODE;
    }

    @JsonProperty("DIV_CODE")
    public void setDIVCODE(String dIVCODE) {
        this.dIVCODE = dIVCODE;
    }

    @JsonProperty("EMAIL")
    public String getEMAIL() {
        return eMAIL;
    }

    @JsonProperty("EMAIL")
    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    @JsonProperty("FUNCTION_CODE1")
    public String getFUNCTIONCODE1() {
        return fUNCTIONCODE1;
    }

    @JsonProperty("FUNCTION_CODE1")
    public void setFUNCTIONCODE1(String fUNCTIONCODE1) {
        this.fUNCTIONCODE1 = fUNCTIONCODE1;
    }

    @JsonProperty("FUNCTION_CODE2")
    public Object getFUNCTIONCODE2() {
        return fUNCTIONCODE2;
    }

    @JsonProperty("FUNCTION_CODE2")
    public void setFUNCTIONCODE2(Object fUNCTIONCODE2) {
        this.fUNCTIONCODE2 = fUNCTIONCODE2;
    }

    @JsonProperty("FUNCTION_CODE3")
    public Object getFUNCTIONCODE3() {
        return fUNCTIONCODE3;
    }

    @JsonProperty("FUNCTION_CODE3")
    public void setFUNCTIONCODE3(Object fUNCTIONCODE3) {
        this.fUNCTIONCODE3 = fUNCTIONCODE3;
    }

    @JsonProperty("FINACLE_WORK_CLASS")
    public String getfINACLEWORKCLASS() {
        return fINACLEWORKCLASS;
    }

    @JsonProperty("FINACLE_WORK_CLASS")
    public void setfINACLEWORKCLASS(String fINACLEWORKCLASS) {
        this.fINACLEWORKCLASS = fINACLEWORKCLASS;
    }

    @Override
    public String toString() {
        return "UPMResponseDTO{" +
                "dAAMOUNTBOT='" + dAAMOUNTBOT + '\'' +
                ", aPPLICATIONSECURITYCLASS='" + aPPLICATIONSECURITYCLASS + '\'' +
                ", dALEVELBOT=" + dALEVELBOT +
                ", sOLID='" + sOLID + '\'' +
                ", bOTSTATUS='" + bOTSTATUS + '\'' +
                ", aCTIVESTATUS='" + aCTIVESTATUS + '\'' +
                ", sUPID='" + sUPID + '\'' +
                ", oATSTATUS='" + oATSTATUS + '\'' +
                ", uSERID='" + uSERID + '\'' +
                ", sIGNONSTATUS='" + sIGNONSTATUS + '\'' +
                ", wORKCLASSBOT='" + wORKCLASSBOT + '\'' +
                ", lASTNAME='" + lASTNAME + '\'' +
                ", dESIGNATIONCODE='" + dESIGNATIONCODE + '\'' +
                ", sOLDIVID='" + sOLDIVID + '\'' +
                ", sECURITYCLASS='" + sECURITYCLASS + '\'' +
                ", fIRSTNAME='" + fIRSTNAME + '\'' +
                ", aPPLICATIONCODE='" + aPPLICATIONCODE + '\'' +
                ", dAAMOUNTOAT='" + dAAMOUNTOAT + '\'' +
                ", aREACODE='" + aREACODE + '\'' +
                ", sIGNATUREID='" + sIGNATUREID + '\'' +
                ", dALEVELOAT='" + dALEVELOAT + '\'' +
                ", zONE=" + zONE +
                ", wORKCLASSOAT='" + wORKCLASSOAT + '\'' +
                ", mOBILEPHONE=" + mOBILEPHONE +
                ", aDUSERID='" + aDUSERID + '\'' +
                ", dESIGNATIONDESC='" + dESIGNATIONDESC + '\'' +
                ", sOLNAME='" + sOLNAME + '\'' +
                ", eXPIRYDATE='" + eXPIRYDATE + '\'' +
                ", oFFICERSTATUS='" + oFFICERSTATUS + '\'' +
                ", dIVCODE='" + dIVCODE + '\'' +
                ", eMAIL='" + eMAIL + '\'' +
                ", fUNCTIONCODE1='" + fUNCTIONCODE1 + '\'' +
                ", fUNCTIONCODE2=" + fUNCTIONCODE2 +
                ", fUNCTIONCODE3=" + fUNCTIONCODE3 +
                ", fINACLEWORKCLASS='" + fINACLEWORKCLASS + '\'' +
                '}';
    }
}
