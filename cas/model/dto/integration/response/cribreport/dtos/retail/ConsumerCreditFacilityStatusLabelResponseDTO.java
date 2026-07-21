package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditFacilityStatusLabelResponse;

import java.io.Serializable;

public class ConsumerCreditFacilityStatusLabelResponseDTO implements Serializable {

    private String monthYear;

    public ConsumerCreditFacilityStatusLabelResponseDTO() {
    }

    public ConsumerCreditFacilityStatusLabelResponseDTO(ConsumerCreditFacilityStatusLabelResponse consumerCreditFacilityStatusLabelResponse) {
        this.monthYear = consumerCreditFacilityStatusLabelResponse.getMonthYear();
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
}
