package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.IdentificationDetailsResponse;

import java.io.Serializable;

public class IdentificationDetailsResponseDTO implements Serializable {

    private String sourceID;

    private String idValue;

    private String idDisplayName;

    private String ruid;

    public IdentificationDetailsResponseDTO() {
    }

    public IdentificationDetailsResponseDTO(IdentificationDetailsResponse identificationDetailsResponse) {
        if (identificationDetailsResponse != null) {
            this.sourceID = identificationDetailsResponse.getSourceID();
            this.idValue = identificationDetailsResponse.getIdValue();
            this.idDisplayName = identificationDetailsResponse.getIdDisplayName();
            this.ruid = identificationDetailsResponse.getRuid();
        }
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public String getIdDisplayName() {
        return idDisplayName;
    }

    public void setIdDisplayName(String idDisplayName) {
        this.idDisplayName = idDisplayName;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }
}
