package com.itechro.cas.model.dto.master;

import java.io.Serializable;

public class BooleanValueDTO implements Serializable {

    private boolean booleanValue;

    public BooleanValueDTO() {
    }

    public BooleanValueDTO(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Override
    public String toString() {
        return "BooleanValueDTO{" +
                "booleanValue=" + booleanValue +
                '}';
    }
}
