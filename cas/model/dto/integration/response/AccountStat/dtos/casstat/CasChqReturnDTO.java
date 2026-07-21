package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.casstat;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat.CasChqReturn;

import java.io.Serializable;

public class CasChqReturnDTO implements Serializable {

    private String accNo;

    private String noOfCheqReturn;

    private String chqReturnCode;

    public CasChqReturnDTO() {
    }

    public CasChqReturnDTO(CasChqReturn casChqReturn) {
        this.accNo = casChqReturn.getAccNo();
        this.noOfCheqReturn = casChqReturn.getNoOfCheqReturn();
        this.chqReturnCode = casChqReturn.getChqReturnCode();
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getNoOfCheqReturn() {
        return noOfCheqReturn;
    }

    public void setNoOfCheqReturn(String noOfCheqReturn) {
        this.noOfCheqReturn = noOfCheqReturn;
    }

    public String getChqReturnCode() {
        return chqReturnCode;
    }

    public void setChqReturnCode(String chqReturnCode) {
        this.chqReturnCode = chqReturnCode;
    }

    @Override
    public String toString() {
        return "CasChqReturnDTO{" +
                "accNo='" + accNo + '\'' +
                ", noOfCheqReturn='" + noOfCheqReturn + '\'' +
                ", chqReturnCode='" + chqReturnCode + '\'' +
                '}';
    }
}
