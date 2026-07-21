package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialDetailsResponse implements Serializable {

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("RUID")
    private String ruid;

    @JsonProperty("LEGAL_CONSTITUTION")
    private String legalConstitution;

    @JsonProperty("BLOCK_FLAG")
    private String blockFlag;

    @JsonProperty("CRA")
    private String cra;

    @JsonProperty("CRA_RUID")
    private String craRuid;

    @JsonProperty("VAT_RUID")
    private String vatRuid;

    @JsonProperty("IDENTIFICATION_DETAILS_VER4")
    private List<IdentificationDetailsResponse> identificationDetailsResponse;


    @JsonProperty("NAME")
    public String getName() {
        return name;
    }

    @JsonProperty("NAME")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("RUID")
    public String getRuid() {
        return ruid;
    }

    @JsonProperty("RUID")
    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    @JsonProperty("LEGAL_CONSTITUTION")
    public String getLegalConstitution() {
        return legalConstitution;
    }

    @JsonProperty("LEGAL_CONSTITUTION")
    public void setLegalConstitution(String legalConstitution) {
        this.legalConstitution = legalConstitution;
    }

    @JsonProperty("BLOCK_FLAG")
    public String getBlockFlag() {
        return blockFlag;
    }

    @JsonProperty("BLOCK_FLAG")
    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    @JsonProperty("CRA")
    public String getCra() {
        return cra;
    }

    @JsonProperty("CRA")
    public void setCra(String cra) {
        this.cra = cra;
    }

    @JsonProperty("CRA_RUID")
    public String getCraRuid() {
        return craRuid;
    }

    @JsonProperty("CRA_RUID")
    public void setCraRuid(String craRuid) {
        this.craRuid = craRuid;
    }

    @JsonProperty("VAT_RUID")
    public String getVatRuid() {
        return vatRuid;
    }

    @JsonProperty("VAT_RUID")
    public void setVatRuid(String vatRuid) {
        this.vatRuid = vatRuid;
    }

    @JsonProperty("IDENTIFICATION_DETAILS_VER4")
    public List<IdentificationDetailsResponse> getIdentificationDetailsResponse() {
        return identificationDetailsResponse;
    }

    @JsonProperty("IDENTIFICATION_DETAILS_VER4")
    public void setIdentificationDetailsResponse(List<IdentificationDetailsResponse> identificationDetailsResponse) {
        this.identificationDetailsResponse = identificationDetailsResponse;
    }

}
