package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditDetailsCatalogueResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditFacilityCatalogueResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerCreditFacilityCatalogueResponseDTO implements Serializable {

    private List<ConsumerCreditDetailsCatalogueResponseDTO> consumerCreditDetailsCatalogueResponseDTOS;

    public ConsumerCreditFacilityCatalogueResponseDTO() {
    }


    public ConsumerCreditFacilityCatalogueResponseDTO(ConsumerCreditFacilityCatalogueResponse consumerCreditFacilityCatalogueResponse) {
        if (consumerCreditFacilityCatalogueResponse.getConsumerCreditDetailsCatalogueResponses() != null) {
            for (ConsumerCreditDetailsCatalogueResponse consumerCreditDetailsCatalogueResponse : consumerCreditFacilityCatalogueResponse.getConsumerCreditDetailsCatalogueResponses()) {
                ConsumerCreditDetailsCatalogueResponseDTO consumerCreditDetailsCatalogueResponseDTO = new ConsumerCreditDetailsCatalogueResponseDTO(consumerCreditDetailsCatalogueResponse);
                this.getConsumerCreditDetailsCatalogueResponseDTOS().add(consumerCreditDetailsCatalogueResponseDTO);
            }
        }
    }

    public List<ConsumerCreditDetailsCatalogueResponseDTO> getConsumerCreditDetailsCatalogueResponseDTOS() {
        if (consumerCreditDetailsCatalogueResponseDTOS == null) {
            consumerCreditDetailsCatalogueResponseDTOS = new ArrayList<>();
        }
        return consumerCreditDetailsCatalogueResponseDTOS;
    }

    public void setConsumerCreditDetailsCatalogueResponseDTOS(List<ConsumerCreditDetailsCatalogueResponseDTO> consumerCreditDetailsCatalogueResponseDTOS) {
        this.consumerCreditDetailsCatalogueResponseDTOS = consumerCreditDetailsCatalogueResponseDTOS;
    }
}
