package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerDCSummaryResponse;

import java.io.Serializable;

public class ConsumerDCSummaryResponseDTO implements Serializable {

    private DishonouredChequeSummResponseDTO dishonouredChequeSummResponseDTO;

    public ConsumerDCSummaryResponseDTO() {
    }

    public ConsumerDCSummaryResponseDTO(ConsumerDCSummaryResponse consumerDCSummaryResponse) {
        if(consumerDCSummaryResponse.getDishonouredChequeSummResponse()!=null){
            this.dishonouredChequeSummResponseDTO = new DishonouredChequeSummResponseDTO(consumerDCSummaryResponse.getDishonouredChequeSummResponse());
        }
    }

    public DishonouredChequeSummResponseDTO getDishonouredChequeSummResponseDTO() {
        return dishonouredChequeSummResponseDTO;
    }

    public void setDishonouredChequeSummResponseDTO(DishonouredChequeSummResponseDTO dishonouredChequeSummResponseDTO) {
        this.dishonouredChequeSummResponseDTO = dishonouredChequeSummResponseDTO;
    }
}
