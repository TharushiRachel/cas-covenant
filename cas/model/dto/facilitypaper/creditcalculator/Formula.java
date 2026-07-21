package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

public class Formula {
    private String facilityType;
    private String calculatorType;
    private String name;
    private String code;
    private String type;
    private String value;
    private boolean output;
    private String outputCode;
    private String outputName;
    private int outputOrder;
    private boolean currency;
    private boolean percentage;

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getCalculatorType() {
        return calculatorType;
    }

    public void setCalculatorType(String calculatorType) {
        this.calculatorType = calculatorType;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public String getOutputCode() {
        return outputCode;
    }

    public void setOutputCode(String outputCode) {
        this.outputCode = outputCode;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
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

    @Override
    public String toString() {
        return "Formula{" +
                "facilityType='" + facilityType + '\'' +
                ", calculatorType='" + calculatorType + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", output=" + output +
                ", outputCode='" + outputCode + '\'' +
                ", outputName='" + outputName + '\'' +
                ", outputOrder=" + outputOrder +
                ", currency=" + currency +
                ", percentage=" + percentage +
                ", value='" + value + '\'' +
                '}';
    }
}
