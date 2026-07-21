package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialDetailsResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialProfileResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialProfileResponseDTO implements Serializable {

    private List<CommercialDetailsResponseDTO> commercialDetailsResponsesDTOList;

    public CommercialProfileResponseDTO() {
    }

    public CommercialProfileResponseDTO(CommercialProfileResponse commercialProfileResponse) {
        if (commercialProfileResponse != null) {
            if (commercialProfileResponse.getCommercialDetailsResponses() != null) {
                for (CommercialDetailsResponse commercialDetailsResponse : commercialProfileResponse.getCommercialDetailsResponses()) {
                    this.getCommercialDetailsResponsesDTOList().add(new CommercialDetailsResponseDTO(commercialDetailsResponse));
                }
            }
        }
    }

    public List<CommercialDetailsResponseDTO> getCommercialDetailsResponsesDTOList() {
        if (commercialDetailsResponsesDTOList == null) {
            this.commercialDetailsResponsesDTOList = new ArrayList<>();
        }
        return commercialDetailsResponsesDTOList;
    }

    public void setCommercialDetailsResponsesDTOList(List<CommercialDetailsResponseDTO> commercialDetailsResponsesDTOList) {
        this.commercialDetailsResponsesDTOList = commercialDetailsResponsesDTOList;
    }
}
