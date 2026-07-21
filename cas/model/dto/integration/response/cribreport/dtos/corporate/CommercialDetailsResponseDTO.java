package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialDetailsResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.IdentificationDetailsResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialDetailsResponseDTO implements Serializable {

    private String name;

    private String ruid;

    private String legalConstitution;

    private String blockFlag;

    private String cra;

    private String craRuid;

    private String vatRuid;

    private List<IdentificationDetailsResponseDTO> identificationDetailsResponseDTOList;

    public CommercialDetailsResponseDTO() {
    }

    public CommercialDetailsResponseDTO(CommercialDetailsResponse commercialDetailsResponse) {
        if (commercialDetailsResponse != null) {
            this.name = commercialDetailsResponse.getName();
            this.ruid = commercialDetailsResponse.getRuid();
            this.legalConstitution = commercialDetailsResponse.getLegalConstitution();
            this.blockFlag = commercialDetailsResponse.getBlockFlag();
            this.cra = commercialDetailsResponse.getCra();
            this.craRuid = commercialDetailsResponse.getCraRuid();
            this.vatRuid = commercialDetailsResponse.getVatRuid();

            if (!commercialDetailsResponse.getIdentificationDetailsResponse().isEmpty()) {
                for (IdentificationDetailsResponse identificationDetailsResponse : commercialDetailsResponse.getIdentificationDetailsResponse()) {
                    this.getIdentificationDetailsResponseDTOList().add(new IdentificationDetailsResponseDTO(identificationDetailsResponse));
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    public String getLegalConstitution() {
        return legalConstitution;
    }

    public void setLegalConstitution(String legalConstitution) {
        this.legalConstitution = legalConstitution;
    }

    public String getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    public String getCra() {
        return cra;
    }

    public void setCra(String cra) {
        this.cra = cra;
    }

    public String getCraRuid() {
        return craRuid;
    }

    public void setCraRuid(String craRuid) {
        this.craRuid = craRuid;
    }

    public String getVatRuid() {
        return vatRuid;
    }

    public void setVatRuid(String vatRuid) {
        this.vatRuid = vatRuid;
    }

    public List<IdentificationDetailsResponseDTO> getIdentificationDetailsResponseDTOList() {
        if (identificationDetailsResponseDTOList == null) {
            this.identificationDetailsResponseDTOList = new ArrayList<>();
        }
        return identificationDetailsResponseDTOList;
    }

    public void setIdentificationDetailsResponseDTOList(List<IdentificationDetailsResponseDTO> identificationDetailsResponseDTOList) {
        this.identificationDetailsResponseDTOList = identificationDetailsResponseDTOList;
    }
}
