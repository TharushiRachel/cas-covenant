package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditFacilityResponse;

import java.io.Serializable;

public class ConsumerCreditFacilityResponseDTO implements Serializable {

    private MasterCreditDetailsResponseDTO masterCreditDetailsResponseDTO;

    public ConsumerCreditFacilityResponseDTO() {
    }

    public ConsumerCreditFacilityResponseDTO(ConsumerCreditFacilityResponse consumerCreditFacilityResponse) {
        this.masterCreditDetailsResponseDTO = new MasterCreditDetailsResponseDTO(consumerCreditFacilityResponse.getMasterCreditDetailsResponse());
    }

    public MasterCreditDetailsResponseDTO getMasterCreditDetailsResponseDTO() {
        return masterCreditDetailsResponseDTO;
    }

    public void setMasterCreditDetailsResponseDTO(MasterCreditDetailsResponseDTO masterCreditDetailsResponseDTO) {
        this.masterCreditDetailsResponseDTO = masterCreditDetailsResponseDTO;
    }
}
