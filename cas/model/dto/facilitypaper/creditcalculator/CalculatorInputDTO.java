package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

import com.itechro.cas.model.dto.facility.FacilityRentalInformationDTO;

import java.util.List;

public class CalculatorInputDTO {
    private String facilityType;
    private List<CalculationDTO> data;
    private String calculatorType;
    private List<FacilityRentalInformationDTO> rentalData;

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public List<CalculationDTO> getData() {
        return data;
    }

    public void setData(List<CalculationDTO> data) {
        this.data = data;
    }

    public String getCalculatorType() {
        return calculatorType;
    }

    public void setCalculatorType(String calculatorType) {
        this.calculatorType = calculatorType;
    }

    public List<FacilityRentalInformationDTO> getRentalData() {
        return rentalData;
    }

    public void setRentalData(List<FacilityRentalInformationDTO> rentalData) {
        this.rentalData = rentalData;
    }
}
