package com.itechro.cas.model.dto.integration.response.CustomerStatistics;

import java.io.Serializable;

public class ExecuteFinacleScriptCustomDataDTO implements Serializable {

    private String accNum;

    private String accCrncy;

    private String yearMonth;

    private String drCash;

    private String drInstrument;

    private String drCard;

    private String drFc;

    private String drExport;

    private String drLoan;

    private String drError;

    private String drSweeps;

    private String drChgAndInt;

    private String drOther;

    private String crCash;

    private String crInstrument;

    private String crCard;

    private String crFc;

    private String crExport;

    private String crLoan;

    private String crError;

    private String crSweeps;

    private String crChgAndInt;

    private String crOther;

    private String crExpMsg;

    private String crTotal;

    private String drTotal;

    public ExecuteFinacleScriptCustomDataDTO( AccTrainData executeFinacleScriptCustomData) {
        this.accNum = executeFinacleScriptCustomData.getAccNum();
        this.accCrncy = executeFinacleScriptCustomData.getAccCrncy();
        this.yearMonth = executeFinacleScriptCustomData.getYearMonth();
        this.drCash = executeFinacleScriptCustomData.getDrCash();
        this.drInstrument = executeFinacleScriptCustomData.getDrInstrument();
        this.drCard = executeFinacleScriptCustomData.getDrCard();
        this.drFc = executeFinacleScriptCustomData.getDrFc();
        this.drExport = executeFinacleScriptCustomData.getDrExport();
        this.drLoan = executeFinacleScriptCustomData.getDrLoan();
        this.drError = executeFinacleScriptCustomData.getDrError();
        this.drSweeps = executeFinacleScriptCustomData.getDrSweeps();
        this.drChgAndInt = executeFinacleScriptCustomData.getDrChgAndInt();
        this.drOther = executeFinacleScriptCustomData.getDrOther();
        this.crCash = executeFinacleScriptCustomData.getCrCash();
        this.crInstrument = executeFinacleScriptCustomData.getCrInstrument();
        this.crCard = executeFinacleScriptCustomData.getCrCard();
        this.crFc = executeFinacleScriptCustomData.getCrFc();
        this.crExport = executeFinacleScriptCustomData.getCrExport();
        this.crLoan = executeFinacleScriptCustomData.getCrLoan();
        this.crError = executeFinacleScriptCustomData.getCrError();
        this.crSweeps = executeFinacleScriptCustomData.getCrSweeps();
        this.crChgAndInt = executeFinacleScriptCustomData.getCrChgAndInt();
        this.crOther = executeFinacleScriptCustomData.getCrOther();
        this.crExpMsg = executeFinacleScriptCustomData.getCrExpMsg();
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getAccCrncy() {
        return accCrncy;
    }

    public void setAccCrncy(String accCrncy) {
        this.accCrncy = accCrncy;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getDrCash() {
        return drCash;
    }

    public void setDrCash(String drCash) {
        this.drCash = drCash;
    }

    public String getDrInstrument() {
        return drInstrument;
    }

    public void setDrInstrument(String drInstrument) {
        this.drInstrument = drInstrument;
    }

    public String getDrCard() {
        return drCard;
    }

    public void setDrCard(String drCard) {
        this.drCard = drCard;
    }

    public String getDrFc() {
        return drFc;
    }

    public void setDrFc(String drFc) {
        this.drFc = drFc;
    }

    public String getDrExport() {
        return drExport;
    }

    public void setDrExport(String drExport) {
        this.drExport = drExport;
    }

    public String getDrLoan() {
        return drLoan;
    }

    public void setDrLoan(String drLoan) {
        this.drLoan = drLoan;
    }

    public String getDrError() {
        return drError;
    }

    public void setDrError(String drError) {
        this.drError = drError;
    }

    public String getDrSweeps() {
        return drSweeps;
    }

    public void setDrSweeps(String drSweeps) {
        this.drSweeps = drSweeps;
    }

    public String getDrChgAndInt() {
        return drChgAndInt;
    }

    public void setDrChgAndInt(String drChgAndInt) {
        this.drChgAndInt = drChgAndInt;
    }

    public String getDrOther() {
        return drOther;
    }

    public void setDrOther(String drOther) {
        this.drOther = drOther;
    }

    public String getCrCash() {
        return crCash;
    }

    public void setCrCash(String crCash) {
        this.crCash = crCash;
    }

    public String getCrInstrument() {
        return crInstrument;
    }

    public void setCrInstrument(String crInstrument) {
        this.crInstrument = crInstrument;
    }

    public String getCrCard() {
        return crCard;
    }

    public void setCrCard(String crCard) {
        this.crCard = crCard;
    }

    public String getCrFc() {
        return crFc;
    }

    public void setCrFc(String crFc) {
        this.crFc = crFc;
    }

    public String getCrExport() {
        return crExport;
    }

    public void setCrExport(String crExport) {
        this.crExport = crExport;
    }

    public String getCrLoan() {
        return crLoan;
    }

    public void setCrLoan(String crLoan) {
        this.crLoan = crLoan;
    }

    public String getCrError() {
        return crError;
    }

    public void setCrError(String crError) {
        this.crError = crError;
    }

    public String getCrSweeps() {
        return crSweeps;
    }

    public void setCrSweeps(String crSweeps) {
        this.crSweeps = crSweeps;
    }

    public String getCrChgAndInt() {
        return crChgAndInt;
    }

    public void setCrChgAndInt(String crChgAndInt) {
        this.crChgAndInt = crChgAndInt;
    }

    public String getCrOther() {
        return crOther;
    }

    public void setCrOther(String crOther) {
        this.crOther = crOther;
    }

    public String getCrExpMsg() {
        return crExpMsg;
    }

    public void setCrExpMsg(String crExpMsg) {
        this.crExpMsg = crExpMsg;
    }

    public String getCrTotal() {
        return crTotal;
    }

    public void setCrTotal(String crTotal) {
        this.crTotal = crTotal;
    }

    public String getDrTotal() {
        return drTotal;
    }

    public void setDrTotal(String drTotal) {
        this.drTotal = drTotal;
    }
}
