package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

import com.itechro.cas.model.dto.facility.FacilityOtherFacilityInformationDTO;

import java.io.Serializable;

public class CalculationDTO implements Serializable {
    private String name;
    private String code;
    private String value;
    private String outputCode;
    private int outputOrder;
    private boolean currency;
    private boolean percentage;
    private boolean output;

    public CalculationDTO() {

    }

    public CalculationDTO(FacilityOtherFacilityInformationDTO facilityOtherFacilityInformationDTO) {
        this.name = facilityOtherFacilityInformationDTO.getOtherFacilityInfoName();
        this.code = facilityOtherFacilityInformationDTO.getOtherFacilityInfoCode();
        this.value = facilityOtherFacilityInformationDTO.getOtherInfoData();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOutputCode() {
        return outputCode;
    }

    public void setOutputCode(String outputCode) {
        this.outputCode = outputCode;
    }

    public int getOutputOrder() {
        return outputOrder;
    }

    public void setOutputOrder(int outputOrder) {
        this.outputOrder = outputOrder;
    }

    public boolean isCurrency() {
        return currency;
    }

    public void setCurrency(boolean currency) {
        this.currency = currency;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "CalculationDTO{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                ", outputCode='" + outputCode + '\'' +
                ", outputOrder=" + outputOrder +
                ", currency=" + currency +
                ", percentage=" + percentage +
                ", output=" + output +
                '}';
    }
}
