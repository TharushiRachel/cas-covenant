package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialSettledCreditSummaryDetailsResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialSettledSummaryDetailsResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialSettledCreditSummaryDetailsResponseDTO implements Serializable {

    private List<CommercialSettledSummaryDetailsResponseDTO> commercialSettledSummaryDetailsResponsesDTOList;

    public CommercialSettledCreditSummaryDetailsResponseDTO() {
    }

    public CommercialSettledCreditSummaryDetailsResponseDTO(CommercialSettledCreditSummaryDetailsResponse commercialSettledCreditSummaryDetailsResponse) {
        if (commercialSettledCreditSummaryDetailsResponse != null) {
            if (commercialSettledCreditSummaryDetailsResponse.getCommercialSettledSummaryDetailsResponses() != null) {
                for (CommercialSettledSummaryDetailsResponse commercialSettledSummaryDetailsResponse : commercialSettledCreditSummaryDetailsResponse.getCommercialSettledSummaryDetailsResponses()) {
                    this.getCommercialSettledSummaryDetailsResponsesDTOList().add(new CommercialSettledSummaryDetailsResponseDTO(commercialSettledSummaryDetailsResponse));
                }
            }
        }
    }

    public List<CommercialSettledSummaryDetailsResponseDTO> getCommercialSettledSummaryDetailsResponsesDTOList() {
        if (commercialSettledSummaryDetailsResponsesDTOList == null) {
            this.commercialSettledSummaryDetailsResponsesDTOList = new ArrayList<>();
        }
        return commercialSettledSummaryDetailsResponsesDTOList;
    }

    public void setCommercialSettledSummaryDetailsResponsesDTOList(List<CommercialSettledSummaryDetailsResponseDTO> commercialSettledSummaryDetailsResponsesDTOList) {
        this.commercialSettledSummaryDetailsResponsesDTOList = commercialSettledSummaryDetailsResponsesDTOList;
    }
}
