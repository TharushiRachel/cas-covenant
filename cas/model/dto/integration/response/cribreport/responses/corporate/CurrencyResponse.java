package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyResponse implements Serializable {

    @JsonProperty("BUREAU_CURRENCY")
    private String bureauCurrency;

    @JsonProperty("PRIORITY")
    private String priority;

    @JsonProperty("MONTHYEAR")
    private String monthYear;

    @JsonProperty("SUMMARY_VER4")
    private List<SummaryResponse> summary;

    @JsonProperty("BUREAU_CURRENCY")
    public String getBureauCurrency() {
        return bureauCurrency;
    }

    @JsonProperty("BUREAU_CURRENCY")
    public void setBureauCurrency(String bureauCurrency) {
        this.bureauCurrency = bureauCurrency;
    }

    @JsonProperty("PRIORITY")
    public String getPriority() {
        return priority;
    }

    @JsonProperty("PRIORITY")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("MONTHYEAR")
    public String getMonthYear() {
        return monthYear;
    }

    @JsonProperty("MONTHYEAR")
    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    @JsonProperty("SUMMARY_VER4")
    public List<SummaryResponse> getSummary() {
        return summary;
    }

    @JsonProperty("SUMMARY_VER4")
    public void setSummary(List<SummaryResponse> summary) {
        this.summary = summary;
    }
}
