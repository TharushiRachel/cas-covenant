package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditFacilityStatus;

import java.io.Serializable;

public class ConsumerCreditFacilityStatusDTO implements Serializable {

    private String catalogueCode;

    private String catalogueValEnglish;

    private String bureauAccStatus;

    private String zero;

    private String oneToThirty;

    private String thirtyOneToSixty;

    private String sixtyOneToNinety;

    private String ninety;

    private String monthYear;

    public ConsumerCreditFacilityStatusDTO() {
    }

    public ConsumerCreditFacilityStatusDTO(ConsumerCreditFacilityStatus consumerCreditFacilityStatus) {
        this.catalogueCode = consumerCreditFacilityStatus.getCatalogueCode();
        this.catalogueValEnglish = consumerCreditFacilityStatus.getCatalogueValEnglish();
        this.bureauAccStatus = consumerCreditFacilityStatus.getBureauAccStatus();
        this.zero = consumerCreditFacilityStatus.getZero();
        this.oneToThirty = consumerCreditFacilityStatus.getOneToThirty();
        this.thirtyOneToSixty = consumerCreditFacilityStatus.getThirtyOneToSixty();
        this.sixtyOneToNinety = consumerCreditFacilityStatus.getSixtyOneToNinety();
        this.ninety = consumerCreditFacilityStatus.getNinety();
        this.monthYear = consumerCreditFacilityStatus.getMonthYear();
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

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getOneToThirty() {
        return oneToThirty;
    }

    public void setOneToThirty(String oneToThirty) {
        this.oneToThirty = oneToThirty;
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
