package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditDetailsCatalogueResponse;

import java.io.Serializable;

public class ConsumerCreditDetailsCatalogueResponseDTO implements Serializable {

    private String label;

    private String values;

    private String serialNumber;

    public ConsumerCreditDetailsCatalogueResponseDTO() {
    }

    public ConsumerCreditDetailsCatalogueResponseDTO(ConsumerCreditDetailsCatalogueResponse consumerCreditDetailsCatalogueResponse) {
        this.label = consumerCreditDetailsCatalogueResponse.getLabel();
        this.values = consumerCreditDetailsCatalogueResponse.getValues();
        this.serialNumber = consumerCreditDetailsCatalogueResponse.getSerialNumber();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isHeader() {
        return this.values.equals("Header");
    }
}
