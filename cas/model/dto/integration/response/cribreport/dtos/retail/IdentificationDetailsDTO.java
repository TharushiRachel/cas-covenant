package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.IdentificationDetailsResponse;

import java.io.Serializable;

public class IdentificationDetailsDTO implements Serializable {

    private String sourceID;

    private String idValue;

    private String idDisplayName;

    private String ruid;

    public IdentificationDetailsDTO() {
    }

    public IdentificationDetailsDTO(IdentificationDetailsResponse identificationDetailsResponse) {

        this.sourceID = identificationDetailsResponse.getSourceID();
        this.idValue = identificationDetailsResponse.getIdValue();
        this.idDisplayName = identificationDetailsResponse.getIdDisplayName();
        this.ruid = identificationDetailsResponse.getRuid();
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

    @Override
    public String toString() {
        return "IdentificationDetailsDTO{" +
                "sourceID='" + sourceID + '\'' +
                ", idValue='" + idValue + '\'' +
                ", idDisplayName='" + idDisplayName + '\'' +
                ", ruid='" + ruid + '\'' +
                '}';
    }
}
