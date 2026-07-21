package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerHistorySummaryResponse implements Serializable {

    @JsonProperty("ACTIVE_ROOT_ID")
    private String activeRootID;

    @JsonProperty("to_month_year")
    private String toMonthYear;

    @JsonProperty("from_month_year")
    private String fromMonthYear;

    @JsonProperty("MONTH")
    private String month;

    @JsonProperty("CURRENT_BALANCE")
    private CurrentBalanceResponse currentBalanceResponse;

    @JsonProperty("AMOUNT_OVERDUE")
    private AmountOverdueResponse amountOverdueResponse;

    @JsonProperty("ASSET_CLASSIFICATION")
    private AssetClassificationResponse assetClassificationResponse;

    @JsonProperty("MAXIMUM_NUMBER_OF_DAYS_OVERDUE")
    private Object maximumNumberOfDaysOverdueResponse;

    @JsonProperty("ACTIVE_ROOT_ID")
    public String getActiveRootID() {
        return activeRootID;
    }

    @JsonProperty("ACTIVE_ROOT_ID")
    public void setActiveRootID(String activeRootID) {
        this.activeRootID = activeRootID;
    }

    @JsonProperty("to_month_year")
    public String getToMonthYear() {
        return toMonthYear;
    }

    @JsonProperty("to_month_year")
    public void setToMonthYear(String toMonthYear) {
        this.toMonthYear = toMonthYear;
    }

    @JsonProperty("from_month_year")
    public String getFromMonthYear() {
        return fromMonthYear;
    }

    @JsonProperty("from_month_year")
    public void setFromMonthYear(String fromMonthYear) {
        this.fromMonthYear = fromMonthYear;
    }

    @JsonProperty("MONTH")
    public String getMonth() {
        return month;
    }

    @JsonProperty("MONTH")
    public void setMonth(String month) {
        this.month = month;
    }

    @JsonProperty("CURRENT_BALANCE")
    public CurrentBalanceResponse getCurrentBalanceResponse() {
        return currentBalanceResponse;
    }

    @JsonProperty("CURRENT_BALANCE")
    public void setCurrentBalanceResponse(CurrentBalanceResponse currentBalanceResponse) {
        this.currentBalanceResponse = currentBalanceResponse;
    }

    @JsonProperty("AMOUNT_OVERDUE")
    public AmountOverdueResponse getAmountOverdueResponse() {
        return amountOverdueResponse;
    }

    @JsonProperty("AMOUNT_OVERDUE")
    public void setAmountOverdueResponse(AmountOverdueResponse amountOverdueResponse) {
        this.amountOverdueResponse = amountOverdueResponse;
    }

    @JsonProperty("ASSET_CLASSIFICATION")
    public AssetClassificationResponse getAssetClassificationResponse() {
        return assetClassificationResponse;
    }

    @JsonProperty("ASSET_CLASSIFICATION")
    public void setAssetClassificationResponse(AssetClassificationResponse assetClassificationResponse) {
        this.assetClassificationResponse = assetClassificationResponse;
    }

    @JsonProperty("MAXIMUM_NUMBER_OF_DAYS_OVERDUE")
    public Object getMaximumNumberOfDaysOverdueResponse() {
        return maximumNumberOfDaysOverdueResponse;
    }

    @JsonProperty("MAXIMUM_NUMBER_OF_DAYS_OVERDUE")
    public void setMaximumNumberOfDaysOverdueResponse(Object maximumNumberOfDaysOverdueResponse) {
        this.maximumNumberOfDaysOverdueResponse = maximumNumberOfDaysOverdueResponse;
    }

}
