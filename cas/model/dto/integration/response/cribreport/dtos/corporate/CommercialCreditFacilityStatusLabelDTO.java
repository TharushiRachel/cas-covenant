package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditFacilityStatusLabel;

import java.io.Serializable;

public class CommercialCreditFacilityStatusLabelDTO implements Serializable {

    private String monthYear;

    public CommercialCreditFacilityStatusLabelDTO() {
    }

    public CommercialCreditFacilityStatusLabelDTO(CommercialCreditFacilityStatusLabel commercialCreditFacilityStatusLabel) {

        if (commercialCreditFacilityStatusLabel != null) {
            this.monthYear = commercialCreditFacilityStatusLabel.getMonthYear();
        }
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
}
