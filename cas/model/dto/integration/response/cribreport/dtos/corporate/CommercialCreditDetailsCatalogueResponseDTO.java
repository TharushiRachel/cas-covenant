package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditDetailsCatalogueResponse;

import java.io.Serializable;

public class CommercialCreditDetailsCatalogueResponseDTO implements Serializable {

    private String label;

    private String values;

    private String serialNumber;

    public CommercialCreditDetailsCatalogueResponseDTO() {
    }

    public CommercialCreditDetailsCatalogueResponseDTO(CommercialCreditDetailsCatalogueResponse commercialCreditDetailsCatalogueResponse) {
        if (commercialCreditDetailsCatalogueResponse != null) {
            this.label = commercialCreditDetailsCatalogueResponse.getLabel();
            this.values = commercialCreditDetailsCatalogueResponse.getValues();
            this.serialNumber = commercialCreditDetailsCatalogueResponse.getSerialNumber();
        }
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
