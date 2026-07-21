package com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CasStat implements Serializable {

    @JsonProperty("lowBalance")
    private String lowBalance;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("averageBalanace")
    private String averageBalance;

    @JsonProperty("accNo")
    private String accNo;

    @JsonProperty("totalCredit")
    private String totalCredit;

    @JsonProperty("totalDebt")
    private String totalDebt;

    @JsonProperty("highBalanace")
    private String highBalance;

    @JsonProperty("floatAmmount")
    private String floatAmount;

    @JsonProperty("chequeReturnRd")
    private String chequeReturnRd;

    @JsonProperty("custid")
    private String customerId;

    @JsonProperty("lowBalance")
    public String getLowBalance() {
        return lowBalance;
    }

    @JsonProperty("lowBalance")
    public void setLowBalance(String lowBalance) {
        this.lowBalance = lowBalance;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("averageBalanace")
    public String getAverageBalance() {
        return averageBalance;
    }

    @JsonProperty("averageBalanace")
    public void setAverageBalance(String averageBalance) {
        this.averageBalance = averageBalance;
    }

    @JsonProperty("accNo")
    public String getAccNo() {
        return accNo;
    }

    @JsonProperty("accNo")
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    @JsonProperty("totalCredit")
    public String getTotalCredit() {
        return totalCredit;
    }

    @JsonProperty("totalCredit")
    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    @JsonProperty("totalDebt")
    public String getTotalDebt() {
        return totalDebt;
    }

    @JsonProperty("totalDebt")
    public void setTotalDebt(String totalDebt) {
        this.totalDebt = totalDebt;
    }

    @JsonProperty("highBalanace")
    public String getHighBalance() {
        return highBalance;
    }

    @JsonProperty("highBalanace")
    public void setHighBalance(String highBalance) {
        this.highBalance = highBalance;
    }

    @JsonProperty("floatAmmount")
    public String getFloatAmount() {
        return floatAmount;
    }

    @JsonProperty("floatAmmount")
    public void setFloatAmount(String floatAmount) {
        this.floatAmount = floatAmount;
    }

    @JsonProperty("chequeReturnRd")
    public String getChequeReturnRd() {
        return chequeReturnRd;
    }

    @JsonProperty("chequeReturnRd")
    public void setChequeReturnRd(String chequeReturnRd) {
        this.chequeReturnRd = chequeReturnRd;
    }

    @JsonProperty("custid")
    public String getCustomerId() {
        return customerId;
    }

    @JsonProperty("custid")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
