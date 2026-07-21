package com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CasChqReturn implements Serializable {

    @JsonProperty("accno")
    private String accNo;

    @JsonProperty("noOfCheqReturn")
    private String noOfCheqReturn;

    @JsonProperty("chqreturncode")
    private String chqReturnCode;

    @JsonProperty("accno")
    public String getAccNo() {
        return accNo;
    }

    @JsonProperty("accno")
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    @JsonProperty("noOfCheqReturn")
    public String getNoOfCheqReturn() {
        return noOfCheqReturn;
    }

    @JsonProperty("noOfCheqReturn")
    public void setNoOfCheqReturn(String noOfCheqReturn) {
        this.noOfCheqReturn = noOfCheqReturn;
    }

    @JsonProperty("chqreturncode")
    public String getChqReturnCode() {
        return chqReturnCode;
    }

    @JsonProperty("chqreturncode")
    public void setChqReturnCode(String chqReturnCode) {
        this.chqReturnCode = chqReturnCode;
    }

    @Override
    public String toString() {
        return "CasChqReturn{" +
                "accNo='" + accNo + '\'' +
                ", noOfCheqReturn='" + noOfCheqReturn + '\'' +
                ", chqReturnCode='" + chqReturnCode + '\'' +
                '}';
    }
}
