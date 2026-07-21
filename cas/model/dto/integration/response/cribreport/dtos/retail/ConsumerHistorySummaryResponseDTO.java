package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerHistorySummaryResponse;

import java.io.Serializable;

public class ConsumerHistorySummaryResponseDTO implements Serializable {

    private String activeRootID;
    private String toMonthYear;
    private String fromMonthYear;
    private String month;
    private String currentBalance;
    private String amountOverdue;
    private String assetClassification;
    private String maximumNumberOfDaysOverdue;

    public ConsumerHistorySummaryResponseDTO() {
    }

    public ConsumerHistorySummaryResponseDTO(ConsumerHistorySummaryResponse consumerHistorySummaryResponse) {
        this.activeRootID = consumerHistorySummaryResponse.getActiveRootID();
        this.toMonthYear = consumerHistorySummaryResponse.getToMonthYear();
        this.fromMonthYear = consumerHistorySummaryResponse.getFromMonthYear();
        this.month = consumerHistorySummaryResponse.getMonth();
//        this.currentBalance = consumerHistorySummaryResponse.getActiveRootID();
//        this.amountOverdue = consumerHistorySummaryResponse.getActiveRootID();
//        this.assetClassification = consumerHistorySummaryResponse.getActiveRootID();
        if (consumerHistorySummaryResponse.getMaximumNumberOfDaysOverdueResponse() instanceof String) {
            this.maximumNumberOfDaysOverdue = (String) consumerHistorySummaryResponse.getMaximumNumberOfDaysOverdueResponse();
        } else if (consumerHistorySummaryResponse.getMaximumNumberOfDaysOverdueResponse() instanceof Integer) {
            this.maximumNumberOfDaysOverdue = consumerHistorySummaryResponse.getMaximumNumberOfDaysOverdueResponse().toString();
        }

    }

    public String getActiveRootID() {
        return activeRootID;
    }

    public void setActiveRootID(String activeRootID) {
        this.activeRootID = activeRootID;
    }

    public String getToMonthYear() {
        return toMonthYear;
    }

    public void setToMonthYear(String toMonthYear) {
        this.toMonthYear = toMonthYear;
    }

    public String getFromMonthYear() {
        return fromMonthYear;
    }

    public void setFromMonthYear(String fromMonthYear) {
        this.fromMonthYear = fromMonthYear;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getAmountOverdue() {
        return amountOverdue;
    }

    public void setAmountOverdue(String amountOverdue) {
        this.amountOverdue = amountOverdue;
    }

    public String getAssetClassification() {
        return assetClassification;
    }

    public void setAssetClassification(String assetClassification) {
        this.assetClassification = assetClassification;
    }

    public String getMaximumNumberOfDaysOverdue() {
        if (maximumNumberOfDaysOverdue == null) {
            this.maximumNumberOfDaysOverdue = "--";
        } else {
            if (this.maximumNumberOfDaysOverdue.equals("Cls")) {
                this.maximumNumberOfDaysOverdue = "";
            }
        }
        return maximumNumberOfDaysOverdue;
    }

    public void setMaximumNumberOfDaysOverdue(String maximumNumberOfDaysOverdue) {
        this.maximumNumberOfDaysOverdue = maximumNumberOfDaysOverdue;
    }

    public String getCSSClass() {
        if (this.getMaximumNumberOfDaysOverdue() != null && !this.getMaximumNumberOfDaysOverdue().isEmpty()) {
            if (this.getMaximumNumberOfDaysOverdue().equals("OK")) {
                return "td-color-green";
            } else if (this.getMaximumNumberOfDaysOverdue().equals("ND")) {
                return "";
            } else if (this.getMaximumNumberOfDaysOverdue().equals("--")) {
                return "";
            } else {
                try {
                    if (Integer.parseInt(this.getMaximumNumberOfDaysOverdue()) > 90) {
                        return "td-color-red";
                    } else if (Integer.parseInt(this.getMaximumNumberOfDaysOverdue()) <= 90) {
                        return "td-color-yellow";
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    return "";
                }
            }
        }
        return "";
    }
}
