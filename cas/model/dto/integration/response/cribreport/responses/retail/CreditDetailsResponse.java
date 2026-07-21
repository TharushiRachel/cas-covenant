package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditDetailsResponse implements Serializable {


    @JsonProperty("ROWNUM")
    private String rownum;

    @JsonProperty("SNO")
    private String sno;

    @JsonProperty("RELATION_ID")
    private String relationID;

    @JsonProperty("ACCOUNT_STATUS")
    private String accountStats;

    @JsonProperty("PRIMARY_CARD_LIMIT")
    private String primaryCardLimit;

    @JsonProperty("DISPUTE")
    private String dispute;

    @JsonProperty("BUREAU_GUARANTEE_COVERAGE")
    private String bureauGuaranteeCoverage;

    @JsonProperty("BUREAU_SECURITY_COVERAGE")
    private String bureauSecurityCoverage;

    @JsonProperty("INTEREST_OUTSTANDING")
    private String interestOutstanding;

    @JsonProperty("INSTALLMENT_AMOUNT")
    private String installmentAmount;

    @JsonProperty("MAX_NUM_DAYS_DUE")
    private String maxNumDaysDue;

    @JsonProperty("LOAN_TYPE")
    private String loanType;

    @JsonProperty("SANCTIONED_AMOUNT")
    private String sanctionedAmount;

    @JsonProperty("OWNERSHIP_INDICATOR")
    private String ownershipIndicator;

    @JsonProperty("REPAYMENT_TYPE")
    private String repaymentType;

    @JsonProperty("CURRENCY")
    private String currency;

    @JsonProperty("PRIORITY")
    private String priority;

    @JsonProperty("PRIORITY2")
    private String priority2;

    @JsonProperty("FIRST_DISBURSE_DATE")
    private String firstDisburseDate;

    @JsonProperty("DATE_LATEST_PAY_RECEIVED")
    private String dateLatestPayReceived;

    @JsonProperty("CURRENT_BALANCE")
    private String currentBalance;

    @JsonProperty("OVERDUE_AMOUNT")
    private String overdueAmount;

    @JsonProperty("BUREAU_CREDIT_FAC_PURPOSE")
    private String bureauCreditFacPurpose;

    @JsonProperty("PRIMARY_ROOT_ID")
    private String primaryRootID;

    @JsonProperty("ACTIVE_ROOT_ID")
    private String activeRootID;

    @JsonProperty("RUID")
    private String ruid;

    @JsonProperty("REPORTED_DATE")
    private String reportedDate;

    @JsonProperty("PROVIDER_SOURCE")
    private String providerSource;

    @JsonProperty("CATEGORY_DESC")
    private String categoryDesc;

    @JsonProperty("INS_CATEGORY")
    private String insCategory;

    @JsonProperty("DISPUTE_ID")
    private String disputeID;

    @JsonProperty("BLOCK_FLAG")
    private String blockFlag;

    @JsonProperty("COVERAGE")
    private String coverage;

    @JsonProperty("REPAY_TYPE")
    private String repayType;

    @JsonProperty("OWNER_SHIP_INDICATOR")
    private String ownerShipIndicator;

    @JsonProperty("CREDIT_FACILITY_STATUS")
    private String creditFacilityStatus;

    @JsonProperty("CREDIT_FACILITY_TYPE")
    private String creditFacilityType;

    @JsonProperty("RANK")
    private String rank;

    @JsonProperty("WRITE_OFF_AMOUNT")
    private String writeOffAmount;

    @JsonProperty("DATE_ACC_CLOSE")
    private String endDate;

    @JsonProperty("ROWNUM")
    public String getRownum() {
        return rownum;
    }

    @JsonProperty("ROWNUM")
    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    @JsonProperty("SNO")
    public String getSno() {
        return sno;
    }

    @JsonProperty("SNO")
    public void setSno(String sno) {
        this.sno = sno;
    }

    @JsonProperty("RELATION_ID")
    public String getRelationID() {
        return relationID;
    }

    @JsonProperty("RELATION_ID")
    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    @JsonProperty("ACCOUNT_STATUS")
    public String getAccountStats() {
        return accountStats;
    }

    @JsonProperty("ACCOUNT_STATUS")
    public void setAccountStats(String accountStats) {
        this.accountStats = accountStats;
    }

    @JsonProperty("PRIMARY_CARD_LIMIT")
    public String getPrimaryCardLimit() {
        return primaryCardLimit;
    }

    @JsonProperty("PRIMARY_CARD_LIMIT")
    public void setPrimaryCardLimit(String primaryCardLimit) {
        this.primaryCardLimit = primaryCardLimit;
    }

    @JsonProperty("DISPUTE")
    public String getDispute() {
        return dispute;
    }

    @JsonProperty("DISPUTE")
    public void setDispute(String dispute) {
        this.dispute = dispute;
    }

    @JsonProperty("BUREAU_GUARANTEE_COVERAGE")
    public String getBureauGuaranteeCoverage() {
        return bureauGuaranteeCoverage;
    }

    @JsonProperty("BUREAU_GUARANTEE_COVERAGE")
    public void setBureauGuaranteeCoverage(String bureauGuaranteeCoverage) {
        this.bureauGuaranteeCoverage = bureauGuaranteeCoverage;
    }

    @JsonProperty("BUREAU_SECURITY_COVERAGE")
    public String getBureauSecurityCoverage() {
        return bureauSecurityCoverage;
    }

    @JsonProperty("BUREAU_SECURITY_COVERAGE")
    public void setBureauSecurityCoverage(String bureauSecurityCoverage) {
        this.bureauSecurityCoverage = bureauSecurityCoverage;
    }

    @JsonProperty("INTEREST_OUTSTANDING")
    public String getInterestOutstanding() {
        return interestOutstanding;
    }

    @JsonProperty("INTEREST_OUTSTANDING")
    public void setInterestOutstanding(String interestOutstanding) {
        this.interestOutstanding = interestOutstanding;
    }

    @JsonProperty("MAX_NUM_DAYS_DUE")
    public String getMaxNumDaysDue() {
        return maxNumDaysDue;
    }

    @JsonProperty("MAX_NUM_DAYS_DUE")
    public void setMaxNumDaysDue(String maxNumDaysDue) {
        this.maxNumDaysDue = maxNumDaysDue;
    }

    @JsonProperty("LOAN_TYPE")
    public String getLoanType() {
        return loanType;
    }

    @JsonProperty("LOAN_TYPE")
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    @JsonProperty("SANCTIONED_AMOUNT")
    public String getSanctionedAmount() {
        return sanctionedAmount;
    }

    @JsonProperty("SANCTIONED_AMOUNT")
    public void setSanctionedAmount(String sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    @JsonProperty("OWNERSHIP_INDICATOR")
    public String getOwnershipIndicator() {
        return ownershipIndicator;
    }

    @JsonProperty("OWNERSHIP_INDICATOR")
    public void setOwnershipIndicator(String ownershipIndicator) {
        this.ownershipIndicator = ownershipIndicator;
    }

    @JsonProperty("REPAYMENT_TYPE")
    public String getRepaymentType() {
        return repaymentType;
    }

    @JsonProperty("REPAYMENT_TYPE")
    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    @JsonProperty("CURRENCY")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("CURRENCY")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("PRIORITY")
    public String getPriority() {
        return priority;
    }

    @JsonProperty("PRIORITY")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("PRIORITY2")
    public String getPriority2() {
        return priority2;
    }

    @JsonProperty("PRIORITY2")
    public void setPriority2(String priority2) {
        this.priority2 = priority2;
    }

    @JsonProperty("FIRST_DISBURSE_DATE")
    public String getFirstDisburseDate() {
        return firstDisburseDate;
    }

    @JsonProperty("FIRST_DISBURSE_DATE")
    public void setFirstDisburseDate(String firstDisburseDate) {
        this.firstDisburseDate = firstDisburseDate;
    }

    @JsonProperty("DATE_LATEST_PAY_RECEIVED")
    public String getDateLatestPayReceived() {
        return dateLatestPayReceived;
    }

    @JsonProperty("DATE_LATEST_PAY_RECEIVED")
    public void setDateLatestPayReceived(String dateLatestPayReceived) {
        this.dateLatestPayReceived = dateLatestPayReceived;
    }

    @JsonProperty("CURRENT_BALANCE")
    public String getCurrentBalance() {
        return currentBalance;
    }

    @JsonProperty("CURRENT_BALANCE")
    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    @JsonProperty("OVERDUE_AMOUNT")
    public String getOverdueAmount() {
        return overdueAmount;
    }

    @JsonProperty("OVERDUE_AMOUNT")
    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    @JsonProperty("BUREAU_CREDIT_FAC_PURPOSE")
    public String getBureauCreditFacPurpose() {
        return bureauCreditFacPurpose;
    }

    @JsonProperty("BUREAU_CREDIT_FAC_PURPOSE")
    public void setBureauCreditFacPurpose(String bureauCreditFacPurpose) {
        this.bureauCreditFacPurpose = bureauCreditFacPurpose;
    }

    @JsonProperty("PRIMARY_ROOT_ID")
    public String getPrimaryRootID() {
        return primaryRootID;
    }

    @JsonProperty("PRIMARY_ROOT_ID")
    public void setPrimaryRootID(String primaryRootID) {
        this.primaryRootID = primaryRootID;
    }

    @JsonProperty("ACTIVE_ROOT_ID")
    public String getActiveRootID() {
        return activeRootID;
    }

    @JsonProperty("ACTIVE_ROOT_ID")
    public void setActiveRootID(String activeRootID) {
        this.activeRootID = activeRootID;
    }

    @JsonProperty("RUID")
    public String getRuid() {
        return ruid;
    }

    @JsonProperty("RUID")
    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    @JsonProperty("REPORTED_DATE")
    public String getReportedDate() {
        return reportedDate;
    }

    @JsonProperty("REPORTED_DATE")
    public void setReportedDate(String reportedDate) {
        this.reportedDate = reportedDate;
    }

    @JsonProperty("PROVIDER_SOURCE")
    public String getProviderSource() {
        return providerSource;
    }

    @JsonProperty("PROVIDER_SOURCE")
    public void setProviderSource(String providerSource) {
        this.providerSource = providerSource;
    }

    @JsonProperty("CATEGORY_DESC")
    public String getCategoryDesc() {
        return categoryDesc;
    }

    @JsonProperty("CATEGORY_DESC")
    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @JsonProperty("INS_CATEGORY")
    public String getInsCategory() {
        return insCategory;
    }

    @JsonProperty("INS_CATEGORY")
    public void setInsCategory(String insCategory) {
        this.insCategory = insCategory;
    }

    @JsonProperty("DISPUTE_ID")
    public String getDisputeID() {
        return disputeID;
    }

    @JsonProperty("DISPUTE_ID")
    public void setDisputeID(String disputeID) {
        this.disputeID = disputeID;
    }

    @JsonProperty("BLOCK_FLAG")
    public String getBlockFlag() {
        return blockFlag;
    }

    @JsonProperty("BLOCK_FLAG")
    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    @JsonProperty("COVERAGE")
    public String getCoverage() {
        return coverage;
    }

    @JsonProperty("COVERAGE")
    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    @JsonProperty("REPAY_TYPE")
    public String getRepayType() {
        return repayType;
    }

    @JsonProperty("REPAY_TYPE")
    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    @JsonProperty("OWNER_SHIP_INDICATOR")
    public String getOwnerShipIndicator() {
        return ownerShipIndicator;
    }

    @JsonProperty("OWNER_SHIP_INDICATOR")
    public void setOwnerShipIndicator(String ownerShipIndicator) {
        this.ownerShipIndicator = ownerShipIndicator;
    }

    @JsonProperty("CREDIT_FACILITY_STATUS")
    public String getCreditFacilityStatus() {
        return creditFacilityStatus;
    }

    @JsonProperty("CREDIT_FACILITY_STATUS")
    public void setCreditFacilityStatus(String creditFacilityStatus) {
        this.creditFacilityStatus = creditFacilityStatus;
    }

    @JsonProperty("CREDIT_FACILITY_TYPE")
    public String getCreditFacilityType() {
        return creditFacilityType;
    }

    @JsonProperty("CREDIT_FACILITY_TYPE")
    public void setCreditFacilityType(String creditFacilityType) {
        this.creditFacilityType = creditFacilityType;
    }

    @JsonProperty("RANK")
    public String getRank() {
        return rank;
    }

    @JsonProperty("RANK")
    public void setRank(String rank) {
        this.rank = rank;
    }

    @JsonProperty("INSTALLMENT_AMOUNT")
    public String getInstallmentAmount() {
        return installmentAmount;
    }

    @JsonProperty("INSTALLMENT_AMOUNT")
    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    @JsonProperty("WRITE_OFF_AMOUNT")
    public String getWriteOffAmount() {
        return writeOffAmount;
    }

    @JsonProperty("WRITE_OFF_AMOUNT")
    public void setWriteOffAmount(String writeOffAmount) {
        this.writeOffAmount = writeOffAmount;
    }

    @JsonProperty("DATE_ACC_CLOSE")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("DATE_ACC_CLOSE")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CreditDetailsResponse{" +
                "sanctionedAmount='" + sanctionedAmount + '\'' +
                ", currentBalance='" + currentBalance + '\'' +
                ", overdueAmount='" + overdueAmount + '\'' +
                ", insCategory='" + insCategory + '\'' +
                ", coverage='" + coverage + '\'' +
                ", ownerShipIndicator='" + ownerShipIndicator + '\'' +
                '}';
    }
}
