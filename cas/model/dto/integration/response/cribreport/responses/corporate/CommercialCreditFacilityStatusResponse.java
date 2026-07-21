package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialCreditFacilityStatusResponse implements Serializable {

    @JsonProperty("CATALOGUE_CODE")
    private String catalogueCode;

    @JsonProperty("CATALOGUE_VAL_ENGLISH")
    private String catalogueValEnglish;

    @JsonProperty("BUREAU_ACC_STATUS")
    private String bureauAccStatus;

    @JsonProperty("ZERO")
    private String zero;

    @JsonProperty("ONETO30")
    private String oneTo30;

    @JsonProperty("THIRTYONETO60")
    private String thirtyOneToSixty;

    @JsonProperty("SIXTYONETO90")
    private String sixtyOneToNinety;

    @JsonProperty("NINETY")
    private String ninety;

    @JsonProperty("MONTHYEAR")
    private String monthYear;

    @JsonProperty("CATALOGUE_CODE")
    public String getCatalogueCode() {
        return catalogueCode;
    }

    @JsonProperty("CATALOGUE_CODE")
    public void setCatalogueCode(String catalogueCode) {
        this.catalogueCode = catalogueCode;
    }

    @JsonProperty("CATALOGUE_VAL_ENGLISH")
    public String getCatalogueValEnglish() {
        return catalogueValEnglish;
    }

    @JsonProperty("CATALOGUE_VAL_ENGLISH")
    public void setCatalogueValEnglish(String catalogueValEnglish) {
        this.catalogueValEnglish = catalogueValEnglish;
    }

    @JsonProperty("BUREAU_ACC_STATUS")
    public String getBureauAccStatus() {
        return bureauAccStatus;
    }

    @JsonProperty("BUREAU_ACC_STATUS")
    public void setBureauAccStatus(String bureauAccStatus) {
        this.bureauAccStatus = bureauAccStatus;
    }

    @JsonProperty("ZERO")
    public String getZero() {
        return zero;
    }

    @JsonProperty("ZERO")
    public void setZero(String zero) {
        this.zero = zero;
    }

    @JsonProperty("ONETO30")
    public String getOneTo30() {
        return oneTo30;
    }

    @JsonProperty("ONETO30")
    public void setOneTo30(String oneTo30) {
        this.oneTo30 = oneTo30;
    }

    @JsonProperty("MONTHYEAR")
    public String getMonthYear() {
        return monthYear;
    }

    @JsonProperty("MONTHYEAR")
    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    @JsonProperty("THIRTYONETO60")
    public String getThirtyOneToSixty() {
        return thirtyOneToSixty;
    }

    @JsonProperty("THIRTYONETO60")
    public void setThirtyOneToSixty(String thirtyOneToSixty) {
        this.thirtyOneToSixty = thirtyOneToSixty;
    }

    @JsonProperty("SIXTYONETO90")
    public String getSixtyOneToNinety() {
        return sixtyOneToNinety;
    }

    @JsonProperty("SIXTYONETO90")
    public void setSixtyOneToNinety(String sixtyOneToNinety) {
        this.sixtyOneToNinety = sixtyOneToNinety;
    }

    @JsonProperty("NINETY")
    public String getNinety() {
        return ninety;
    }

    @JsonProperty("NINETY")
    public void setNinety(String ninety) {
        this.ninety = ninety;
    }
}
