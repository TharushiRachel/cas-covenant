package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.casstat;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat.CasStat;

import java.io.Serializable;

public class CasStatDTO implements Serializable {

    private String lowBalance;

    private String startDate;

    private String averageBalance;

    private String accNo;

    private String totalCredit;

    private String totalDebt;

    private String highBalance;

    private String floatAmount;

    private String chequeReturnRd;

    private String customerID;

    public CasStatDTO() {
    }

    public CasStatDTO(CasStat casStat) {
        this.lowBalance = casStat.getLowBalance();
        this.startDate = casStat.getStartDate();
        this.averageBalance = casStat.getAverageBalance();
        this.accNo = casStat.getAccNo();
        this.totalCredit = casStat.getTotalCredit();
        this.totalDebt = casStat.getTotalDebt();
        this.highBalance = casStat.getHighBalance();
        this.floatAmount = casStat.getFloatAmount();
        this.chequeReturnRd = casStat.getChequeReturnRd();
        this.customerID = casStat.getCustomerId();
    }

    public String getLowBalance() {
        return lowBalance;
    }

    public void setLowBalance(String lowBalance) {
        this.lowBalance = lowBalance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getAverageBalance() {
        return averageBalance;
    }

    public void setAverageBalance(String averageBalance) {
        this.averageBalance = averageBalance;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(String totalDebt) {
        this.totalDebt = totalDebt;
    }

    public String getHighBalance() {
        return highBalance;
    }

    public void setHighBalance(String highBalance) {
        this.highBalance = highBalance;
    }

    public String getFloatAmount() {
        return floatAmount;
    }

    public void setFloatAmount(String floatAmount) {
        this.floatAmount = floatAmount;
    }

    public String getChequeReturnRd() {
        return chequeReturnRd;
    }

    public void setChequeReturnRd(String chequeReturnRd) {
        this.chequeReturnRd = chequeReturnRd;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "CasStatDTO{" +
                "lowBalance='" + lowBalance + '\'' +
                ", startDate='" + startDate + '\'' +
                ", averageBalance='" + averageBalance + '\'' +
                ", accNo='" + accNo + '\'' +
                ", totalCredit='" + totalCredit + '\'' +
                ", totalDebt='" + totalDebt + '\'' +
                ", highBalance='" + highBalance + '\'' +
                ", floatAmount='" + floatAmount + '\'' +
                ", chequeReturnRd='" + chequeReturnRd + '\'' +
                ", customerID='" + customerID + '\'' +
                '}';
    }
}
