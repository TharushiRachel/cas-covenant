package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

import java.io.Serializable;
import java.util.List;

public class RentalAmountDTO implements Serializable {
    private int period;
    private List<Double> rentalAmountList;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public List<Double> getRentalAmountList() {
        return rentalAmountList;
    }

    public void setRentalAmountList(List<Double> rentalAmountList) {
        this.rentalAmountList = rentalAmountList;
    }
}
