package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditDetailsCatalogueResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditFacilityCatalogueResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialCreditFacilityCatalogueResponseDTO implements Serializable {

    private List<CommercialCreditDetailsCatalogueResponseDTO> commercialCreditDetailsCatalogueResponseDTOList;

    public CommercialCreditFacilityCatalogueResponseDTO() {
    }


    public CommercialCreditFacilityCatalogueResponseDTO(CommercialCreditFacilityCatalogueResponse commercialCreditFacilityCatalogueResponse) {
        if (commercialCreditFacilityCatalogueResponse != null) {
            if (commercialCreditFacilityCatalogueResponse.getCommercialCreditDetailsCatalogueResponses() != null) {
                for (CommercialCreditDetailsCatalogueResponse commercialCreditDetailsCatalogueResponse : commercialCreditFacilityCatalogueResponse.getCommercialCreditDetailsCatalogueResponses()) {
                    CommercialCreditDetailsCatalogueResponseDTO commercialCreditDetailsCatalogueResponseDTO = new CommercialCreditDetailsCatalogueResponseDTO(commercialCreditDetailsCatalogueResponse);
                    getCommercialCreditDetailsCatalogueResponseDTOList().add(commercialCreditDetailsCatalogueResponseDTO);
                }
            }
        }
    }

    public List<CommercialCreditDetailsCatalogueResponseDTO> getCommercialCreditDetailsCatalogueResponseDTOList() {
        if (commercialCreditDetailsCatalogueResponseDTOList == null) {
            this.commercialCreditDetailsCatalogueResponseDTOList = new ArrayList<>();
        }
        return commercialCreditDetailsCatalogueResponseDTOList;
    }

    public void setCommercialCreditDetailsCatalogueResponseDTOList(List<CommercialCreditDetailsCatalogueResponseDTO> commercialCreditDetailsCatalogueResponseDTOList) {
        this.commercialCreditDetailsCatalogueResponseDTOList = commercialCreditDetailsCatalogueResponseDTOList;
    }
}
