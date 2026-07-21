package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsSettledSummaryDetailsResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerSettledCreditSummaryDetailsResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerSettledCreditSummaryDetailsResponseDTO implements Serializable {

    private List<ConsSettledSummaryDetailsResponseDTO> consSettledSummaryDetailsResponseDTOS;

    public ConsumerSettledCreditSummaryDetailsResponseDTO() {
    }


    public ConsumerSettledCreditSummaryDetailsResponseDTO(ConsumerSettledCreditSummaryDetailsResponse consumerSettledCreditSummaryDetailsResponse) {
        if (consumerSettledCreditSummaryDetailsResponse.getConsSettledSummaryDetailsResponseList() != null) {
            for (ConsSettledSummaryDetailsResponse consSettledSummaryDetailsResponse : consumerSettledCreditSummaryDetailsResponse.getConsSettledSummaryDetailsResponseList()) {
                ConsSettledSummaryDetailsResponseDTO consSettledSummaryDetailsResponseDTO = new ConsSettledSummaryDetailsResponseDTO(consSettledSummaryDetailsResponse);
                this.getConsSettledSummaryDetailsResponseDTOS().add(consSettledSummaryDetailsResponseDTO);
            }
        }

    }

    public List<ConsSettledSummaryDetailsResponseDTO> getConsSettledSummaryDetailsResponseDTOS() {
        if (consSettledSummaryDetailsResponseDTOS == null) {
            consSettledSummaryDetailsResponseDTOS = new ArrayList<>();
        }
        return consSettledSummaryDetailsResponseDTOS;
    }

    public void setConsSettledSummaryDetailsResponseDTOS(List<ConsSettledSummaryDetailsResponseDTO> consSettledSummaryDetailsResponseDTOS) {
        this.consSettledSummaryDetailsResponseDTOS = consSettledSummaryDetailsResponseDTOS;
    }
}
