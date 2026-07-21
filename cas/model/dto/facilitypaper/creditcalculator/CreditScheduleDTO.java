package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

public class CreditScheduleDTO {
    public int index;
    public String capitalOutstanding;
    public String vat;
    public String interest;
    public String capital;
    public String sanctionOutstanding;
    public String grossRentalOutstanding;
    public String rentalWithoutVat;
    public String rentalWithVat;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCapitalOutstanding() {
        return capitalOutstanding;
    }

    public void setCapitalOutstanding(String capitalOutstanding) {
        this.capitalOutstanding = capitalOutstanding;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getSanctionOutstanding() {
        return sanctionOutstanding;
    }

    public void setSanctionOutstanding(String sanctionOutstanding) {
        this.sanctionOutstanding = sanctionOutstanding;
    }

    public String getGrossRentalOutstanding() {
        return grossRentalOutstanding;
    }

    public void setGrossRentalOutstanding(String grossRentalOutstanding) {
        this.grossRentalOutstanding = grossRentalOutstanding;
    }

    public String getRentalWithoutVat() {
        return rentalWithoutVat;
    }

    public void setRentalWithoutVat(String rentalWithoutVat) {
        this.rentalWithoutVat = rentalWithoutVat;
    }

    public String getRentalWithVat() {
        return rentalWithVat;
    }

    public void setRentalWithVat(String rentalWithVat) {
        this.rentalWithVat = rentalWithVat;
    }
}
