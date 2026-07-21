package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthYearResponse implements Serializable {

    @JsonProperty("SNO")
    private String sno;

    @JsonProperty("ROWNUM")
    private String rowNum;

    @JsonProperty("RELATION_ID")
    private String relationID;

    @JsonProperty("FROM_MONTH_YEAR")
    private String fromMonthYear;

    @JsonProperty("TO_MONTH_YEAR")
    private String toMonthYear;

    @JsonProperty("ACTIVE_ROOT_ID")
    private String activeRootID;

    @JsonProperty("BUREAU_CURRENCY")
    private String bureauCurrency;

    @JsonProperty("BUREAU_ACC_STATUS")
    private String bureauAccStatus;

    @JsonProperty("RANK")
    private String rank;

    @JsonProperty("CONSUMER_HISTORY_SUMMARY_VER4")
    private List<ConsumerHistorySummaryResponse> consumerHistorySummaryResponses;

    @JsonProperty("SNO")
    public String getSno() {
        return sno;
    }

    @JsonProperty("SNO")
    public void setSno(String sno) {
        this.sno = sno;
    }

    @JsonProperty("ROWNUM")
    public String getRowNum() {
        return rowNum;
    }

    @JsonProperty("ROWNUM")
    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    @JsonProperty("RELATION_ID")
    public String getRelationID() {
        return relationID;
    }

    @JsonProperty("RELATION_ID")
    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    @JsonProperty("FROM_MONTH_YEAR")
    public String getFromMonthYear() {
        return fromMonthYear;
    }

    @JsonProperty("FROM_MONTH_YEAR")
    public void setFromMonthYear(String fromMonthYear) {
        this.fromMonthYear = fromMonthYear;
    }

    @JsonProperty("TO_MONTH_YEAR")
    public String getToMonthYear() {
        return toMonthYear;
    }

    @JsonProperty("TO_MONTH_YEAR")
    public void setToMonthYear(String toMonthYear) {
        this.toMonthYear = toMonthYear;
    }

    @JsonProperty("ACTIVE_ROOT_ID")
    public String getActiveRootID() {
        return activeRootID;
    }

    @JsonProperty("ACTIVE_ROOT_ID")
    public void setActiveRootID(String activeRootID) {
        this.activeRootID = activeRootID;
    }

    @JsonProperty("BUREAU_CURRENCY")
    public String getBureauCurrency() {
        return bureauCurrency;
    }

    @JsonProperty("BUREAU_CURRENCY")
    public void setBureauCurrency(String bureauCurrency) {
        this.bureauCurrency = bureauCurrency;
    }

    @JsonProperty("BUREAU_ACC_STATUS")
    public String getBureauAccStatus() {
        return bureauAccStatus;
    }

    @JsonProperty("BUREAU_ACC_STATUS")
    public void setBureauAccStatus(String bureauAccStatus) {
        this.bureauAccStatus = bureauAccStatus;
    }

    @JsonProperty("RANK")
    public String getRank() {
        return rank;
    }

    @JsonProperty("RANK")
    public void setRank(String rank) {
        this.rank = rank;
    }

    @JsonProperty("CONSUMER_HISTORY_SUMMARY_VER4")
    public List<ConsumerHistorySummaryResponse> getConsumerHistorySummaryResponses() {
        return consumerHistorySummaryResponses;
    }

    @JsonProperty("CONSUMER_HISTORY_SUMMARY_VER4")
    public void setConsumerHistorySummaryResponses(List<ConsumerHistorySummaryResponse> consumerHistorySummaryResponses) {
        this.consumerHistorySummaryResponses = consumerHistorySummaryResponses;
    }
}
