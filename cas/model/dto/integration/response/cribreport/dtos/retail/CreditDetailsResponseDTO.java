package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CreditDetailsResponse;

import java.io.Serializable;

public class CreditDetailsResponseDTO implements Serializable {

    private String rownum;
    private String sno;
    private String relationID;
    private String accountStats;
    private String primaryCardLimit;
    private String dispute;
    private String bureauGuaranteeCoverage;
    private String bureauSecurityCoverage;
    private String interestOutstanding;
    private String installmentAmount;
    private String maxNumDaysDue;
    private String loanType;
    private String sanctionedAmount;
    private String ownershipIndicator;
    private String repaymentType;
    private String currency;
    private String priority;
    private String priority2;
    private String firstDisburseDate;
    private String dateLatestPayReceived;
    private String currentBalance;
    private String overdueAmount;
    private String bureauCreditFacPurpose;
    private String primaryRootID;
    private String activeRootID;
    private String ruid;
    private String reportedDate;
    private String providerSource;
    private String categoryDesc;
    private String insCategory;
    private String disputeID;
    private String blockflag;
    private String coverage;
    private String repayType;
    private String ownerShipIndicator;
    private String creditFacilityStatus;
    private String creditFacilityType;
    private String writeOffAmount;
    private String endDate;

    public CreditDetailsResponseDTO() {
    }


    public CreditDetailsResponseDTO(CreditDetailsResponse creditDetailsResponse) {
        this.rownum = creditDetailsResponse.getRownum();
        this.sno = creditDetailsResponse.getSno();
        this.relationID = creditDetailsResponse.getRelationID();
        this.accountStats = creditDetailsResponse.getAccountStats();
        this.primaryCardLimit = creditDetailsResponse.getPrimaryCardLimit();
        this.dispute = creditDetailsResponse.getDispute();
        this.bureauGuaranteeCoverage = creditDetailsResponse.getBureauGuaranteeCoverage();
        this.bureauSecurityCoverage = creditDetailsResponse.getBureauSecurityCoverage();
        this.interestOutstanding = creditDetailsResponse.getInterestOutstanding();
        this.installmentAmount = creditDetailsResponse.getInstallmentAmount();
        this.maxNumDaysDue = creditDetailsResponse.getMaxNumDaysDue();
        this.loanType = creditDetailsResponse.getLoanType();
        this.sanctionedAmount = creditDetailsResponse.getSanctionedAmount();
        this.ownershipIndicator = creditDetailsResponse.getOwnershipIndicator();
        this.repaymentType = creditDetailsResponse.getRepaymentType();
        this.currency = creditDetailsResponse.getCurrency();
        this.priority = creditDetailsResponse.getPriority();
        this.priority2 = creditDetailsResponse.getPriority2();
        this.firstDisburseDate = creditDetailsResponse.getFirstDisburseDate();
        this.dateLatestPayReceived = creditDetailsResponse.getDateLatestPayReceived();
        this.currentBalance = creditDetailsResponse.getCurrentBalance();
        this.overdueAmount = creditDetailsResponse.getOverdueAmount();
        this.bureauCreditFacPurpose = creditDetailsResponse.getBureauCreditFacPurpose();
        this.primaryRootID = creditDetailsResponse.getPrimaryRootID();
        this.activeRootID = creditDetailsResponse.getActiveRootID();
        this.ruid = creditDetailsResponse.getRuid();
        this.reportedDate = creditDetailsResponse.getReportedDate();
        this.providerSource = creditDetailsResponse.getProviderSource();
        this.categoryDesc = creditDetailsResponse.getCategoryDesc();
        this.insCategory = creditDetailsResponse.getInsCategory();
        this.disputeID = creditDetailsResponse.getDisputeID();
        this.blockflag = creditDetailsResponse.getBlockFlag();
        this.coverage = creditDetailsResponse.getCoverage();
        this.repayType = creditDetailsResponse.getRepayType();
        this.ownerShipIndicator = creditDetailsResponse.getOwnerShipIndicator();
        this.creditFacilityStatus = creditDetailsResponse.getCreditFacilityStatus();
        this.creditFacilityType = creditDetailsResponse.getCreditFacilityType();
        this.writeOffAmount = creditDetailsResponse.getWriteOffAmount();
        this.endDate = creditDetailsResponse.getEndDate();
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getRelationID() {
        return relationID;
    }

    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    public String getAccountStats() {
        return accountStats;
    }

    public void setAccountStats(String accountStats) {
        this.accountStats = accountStats;
    }

    public String getPrimaryCardLimit() {
        return primaryCardLimit;
    }

    public void setPrimaryCardLimit(String primaryCardLimit) {
        this.primaryCardLimit = primaryCardLimit;
    }

    public String getDispute() {
        return dispute;
    }

    public void setDispute(String dispute) {
        this.dispute = dispute;
    }

    public String getBureauGuaranteeCoverage() {
        return bureauGuaranteeCoverage;
    }

    public void setBureauGuaranteeCoverage(String bureauGuaranteeCoverage) {
        this.bureauGuaranteeCoverage = bureauGuaranteeCoverage;
    }

    public String getBureauSecurityCoverage() {
        return bureauSecurityCoverage;
    }

    public void setBureauSecurityCoverage(String bureauSecurityCoverage) {
        this.bureauSecurityCoverage = bureauSecurityCoverage;
    }

    public String getInterestOutstanding() {
        return interestOutstanding;
    }

    public void setInterestOutstanding(String interestOutstanding) {
        this.interestOutstanding = interestOutstanding;
    }

    public String getMaxNumDaysDue() {
        return maxNumDaysDue;
    }

    public void setMaxNumDaysDue(String maxNumDaysDue) {
        this.maxNumDaysDue = maxNumDaysDue;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getSanctionedAmount() {
        return sanctionedAmount;
    }

    public void setSanctionedAmount(String sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public String getOwnershipIndicator() {
        return ownershipIndicator;
    }

    public void setOwnershipIndicator(String ownershipIndicator) {
        this.ownershipIndicator = ownershipIndicator;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority2() {
        return priority2;
    }

    public void setPriority2(String priority2) {
        this.priority2 = priority2;
    }

    public String getFirstDisburseDate() {
        return firstDisburseDate;
    }

    public void setFirstDisburseDate(String firstDisburseDate) {
        this.firstDisburseDate = firstDisburseDate;
    }

    public String getDateLatestPayReceived() {
        return dateLatestPayReceived;
    }

    public void setDateLatestPayReceived(String dateLatestPayReceived) {
        this.dateLatestPayReceived = dateLatestPayReceived;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getBureauCreditFacPurpose() {
        return bureauCreditFacPurpose;
    }

    public void setBureauCreditFacPurpose(String bureauCreditFacPurpose) {
        this.bureauCreditFacPurpose = bureauCreditFacPurpose;
    }

    public String getPrimaryRootID() {
        return primaryRootID;
    }

    public void setPrimaryRootID(String primaryRootID) {
        this.primaryRootID = primaryRootID;
    }

    public String getActiveRootID() {
        return activeRootID;
    }

    public void setActiveRootID(String activeRootID) {
        this.activeRootID = activeRootID;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    public String getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }

    public String getProviderSource() {
        return providerSource;
    }

    public void setProviderSource(String providerSource) {
        this.providerSource = providerSource;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getInsCategory() {
        return insCategory;
    }

    public void setInsCategory(String insCategory) {
        this.insCategory = insCategory;
    }

    public String getDisputeID() {
        return disputeID;
    }

    public void setDisputeID(String disputeID) {
        this.disputeID = disputeID;
    }

    public String getBlockflag() {
        return blockflag;
    }

    public void setBlockflag(String blockflag) {
        this.blockflag = blockflag;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getOwnerShipIndicator() {
        return ownerShipIndicator;
    }

    public void setOwnerShipIndicator(String ownerShipIndicator) {
        this.ownerShipIndicator = ownerShipIndicator;
    }

    public String getCreditFacilityStatus() {
        return creditFacilityStatus;
    }

    public void setCreditFacilityStatus(String creditFacilityStatus) {
        this.creditFacilityStatus = creditFacilityStatus;
    }

    public String getCreditFacilityType() {
        return creditFacilityType;
    }

    public void setCreditFacilityType(String creditFacilityType) {
        this.creditFacilityType = creditFacilityType;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getWriteOffAmount() {
        return writeOffAmount;
    }

    public void setWriteOffAmount(String writeOffAmount) {
        this.writeOffAmount = writeOffAmount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
