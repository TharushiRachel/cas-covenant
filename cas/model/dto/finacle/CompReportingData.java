package com.itechro.cas.model.dto.finacle;


import java.util.List;

public class CompReportingData {

    private String acctName;

    private String custId;

    private String sector;

    private String riskRating;

    private List<CompReportCusLoan> loans;

    private List<CompReportCusLoan> termLoans;

    private List<CompReportCusLoan> workingCapitalLoans;

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public List<CompReportCusLoan> getTermLoans() {
        return termLoans;
    }

    public void setTermLoans(List<CompReportCusLoan> termLoans) {
        this.termLoans = termLoans;
    }

    public List<CompReportCusLoan> getWorkingCapitalLoans() {
        return workingCapitalLoans;
    }

    public void setWorkingCapitalLoans(List<CompReportCusLoan> workingCapitalLoans) {
        this.workingCapitalLoans = workingCapitalLoans;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getRiskRating() {
        return riskRating;
    }

    public void setRiskRating(String riskRating) {
        this.riskRating = riskRating;
    }

    public List<CompReportCusLoan> getLoans() {
        return loans;
    }

    public void setLoans(List<CompReportCusLoan> loans) {
        this.loans = loans;
    }
}
