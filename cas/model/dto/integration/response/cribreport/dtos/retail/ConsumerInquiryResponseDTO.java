package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerInquiryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.InquiryDetailsResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerInquiryResponseDTO implements Serializable {

    private List<InquiryDetailsResponseDTO> inquiryDetailsResponseDTOS;

    public ConsumerInquiryResponseDTO() {
    }

    public ConsumerInquiryResponseDTO(ConsumerInquiryResponse consumerInquiryResponse) {
        if (consumerInquiryResponse.getInquiryDetailsResponses() != null) {
            for (InquiryDetailsResponse inquiryDetailsResponse : consumerInquiryResponse.getInquiryDetailsResponses()) {
                InquiryDetailsResponseDTO inquiryDetailsResponseDTO = new InquiryDetailsResponseDTO(inquiryDetailsResponse);
                getInquiryDetailsResponseDTOS().add(inquiryDetailsResponseDTO);
            }
        }
    }

    public List<InquiryDetailsResponseDTO> getInquiryDetailsResponseDTOS() {
        if (inquiryDetailsResponseDTOS == null) {
            this.inquiryDetailsResponseDTOS = new ArrayList<>();
        }
        return inquiryDetailsResponseDTOS;
    }

    public void setInquiryDetailsResponseDTOS(List<InquiryDetailsResponseDTO> inquiryDetailsResponseDTOS) {
        this.inquiryDetailsResponseDTOS = inquiryDetailsResponseDTOS;
    }
}
