package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditFacilityStatusResponse;

import java.io.Serializable;

public class CommercialCreditFacilityStatusResponseDTO implements Serializable {

    private String catalogueCode;

    private String catalogueValEnglish;

    private String bureauAccStatus;

    private String zero;

    private String oneTo30;

    private String thirtyOneToSixty;

    private String sixtyOneToNinety;

    private String ninety;

    private String monthYear;

    public CommercialCreditFacilityStatusResponseDTO() {
    }

    public CommercialCreditFacilityStatusResponseDTO(CommercialCreditFacilityStatusResponse commercialCreditFacilityStatusResponse) {
        if (commercialCreditFacilityStatusResponse != null) {
            this.catalogueCode = commercialCreditFacilityStatusResponse.getCatalogueCode();
            this.catalogueValEnglish = commercialCreditFacilityStatusResponse.getCatalogueValEnglish();
            this.bureauAccStatus = commercialCreditFacilityStatusResponse.getBureauAccStatus();
            this.zero = commercialCreditFacilityStatusResponse.getZero();
            this.oneTo30 = commercialCreditFacilityStatusResponse.getOneTo30();
            this.thirtyOneToSixty = commercialCreditFacilityStatusResponse.getThirtyOneToSixty();
            this.sixtyOneToNinety = commercialCreditFacilityStatusResponse.getSixtyOneToNinety();
            this.ninety = commercialCreditFacilityStatusResponse.getNinety();
            this.monthYear = commercialCreditFacilityStatusResponse.getMonthYear();
        }
    }

    public String getCatalogueCode() {
        return catalogueCode;
    }

    public void setCatalogueCode(String catalogueCode) {
        this.catalogueCode = catalogueCode;
    }

    public String getCatalogueValEnglish() {
        return catalogueValEnglish;
    }

    public void setCatalogueValEnglish(String catalogueValEnglish) {
        this.catalogueValEnglish = catalogueValEnglish;
    }

    public String getBureauAccStatus() {
        return bureauAccStatus;
    }

    public void setBureauAccStatus(String bureauAccStatus) {
        this.bureauAccStatus = bureauAccStatus;
    }

    public String getZero() {
        return zero;
    }

    public void setZero(String zero) {
        this.zero = zero;
    }

    public String getOneTo30() {
        return oneTo30;
    }

    public void setOneTo30(String oneTo30) {
        this.oneTo30 = oneTo30;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getThirtyOneToSixty() {
        return thirtyOneToSixty;
    }

    public void setThirtyOneToSixty(String thirtyOneToSixty) {
        this.thirtyOneToSixty = thirtyOneToSixty;
    }

    public String getSixtyOneToNinety() {
        return sixtyOneToNinety;
    }

    public void setSixtyOneToNinety(String sixtyOneToNinety) {
        this.sixtyOneToNinety = sixtyOneToNinety;
    }

    public String getNinety() {
        return ninety;
    }

    public void setNinety(String ninety) {
        this.ninety = ninety;
    }
}
