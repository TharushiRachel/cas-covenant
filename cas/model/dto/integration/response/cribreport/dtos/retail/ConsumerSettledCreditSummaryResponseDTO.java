package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsSettledSummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerSettledCreditSummaryResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerSettledCreditSummaryResponseDTO implements Serializable {

    private List<ConsSettledSummaryResponseDTO> consSettledSummaryResponseDTOS;

    public ConsumerSettledCreditSummaryResponseDTO() {
    }

    public ConsumerSettledCreditSummaryResponseDTO(ConsumerSettledCreditSummaryResponse consumerSettledCreditSummaryResponse) {
        if (consumerSettledCreditSummaryResponse.getConsSettledSummaryResponses() != null) {
            for (ConsSettledSummaryResponse consSettledSummaryResponse : consumerSettledCreditSummaryResponse.getConsSettledSummaryResponses()) {
                ConsSettledSummaryResponseDTO consSettledSummaryResponseDTO = new ConsSettledSummaryResponseDTO(consSettledSummaryResponse);
                this.getConsSettledSummaryResponseDTOS().add(consSettledSummaryResponseDTO);
            }
        }

    }


    public List<ConsSettledSummaryResponseDTO> getConsSettledSummaryResponseDTOS() {
        if (consSettledSummaryResponseDTOS == null) {
            consSettledSummaryResponseDTOS = new ArrayList<>();
        }
        return consSettledSummaryResponseDTOS;
    }

    public void setConsSettledSummaryResponseDTOS(List<ConsSettledSummaryResponseDTO> consSettledSummaryResponseDTOS) {
        this.consSettledSummaryResponseDTOS = consSettledSummaryResponseDTOS;
    }
}
