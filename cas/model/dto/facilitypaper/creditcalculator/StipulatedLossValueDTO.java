package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

public class StipulatedLossValueDTO {
    private int term;
    private double stipulatedLossValue;
    private double interest;
    private double capital;
    private boolean colored;

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getStipulatedLossValue() {
        return stipulatedLossValue;
    }

    public void setStipulatedLossValue(double stipulatedLossValue) {
        this.stipulatedLossValue = stipulatedLossValue;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }
}
