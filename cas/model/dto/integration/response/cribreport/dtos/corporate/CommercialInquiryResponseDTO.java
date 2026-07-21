package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialInquiryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.InquiryDetailsResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialInquiryResponseDTO implements Serializable {

    private List<InquiryDetailsResponseDTO> inquiryDetailsResponsesDTOList;

    public CommercialInquiryResponseDTO(CommercialInquiryResponse commercialInquiryResponse) {
        if (commercialInquiryResponse != null) {
            if (commercialInquiryResponse.getInquiryDetailsResponses() != null) {
                for (InquiryDetailsResponse inquiryDetailsResponse : commercialInquiryResponse.getInquiryDetailsResponses()) {
                    getInquiryDetailsResponsesDTOList().add(new InquiryDetailsResponseDTO(inquiryDetailsResponse));
                }
            }
        }
    }

    public CommercialInquiryResponseDTO() {
    }

    public List<InquiryDetailsResponseDTO> getInquiryDetailsResponsesDTOList() {
        if (inquiryDetailsResponsesDTOList == null) {
            this.inquiryDetailsResponsesDTOList = new ArrayList<>();
        }
        return inquiryDetailsResponsesDTOList;
    }

    public void setInquiryDetailsResponsesDTOList(List<InquiryDetailsResponseDTO> inquiryDetailsResponsesDTOList) {
        this.inquiryDetailsResponsesDTOList = inquiryDetailsResponsesDTOList;
    }
}
