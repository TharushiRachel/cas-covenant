package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditFacilitiesStatusResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditFacilityStatusLabel;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditFacilityStatusResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialCreditFacilitiesStatusResponseDTO implements Serializable {

    private List<CommercialCreditFacilityStatusResponseDTO> commercialCreditFacilityStatusResponseDTOList;

    private List<CommercialCreditFacilityStatusLabelDTO> commercialCreditFacilityStatusLabelDTOList;

    public CommercialCreditFacilitiesStatusResponseDTO() {
    }

    public CommercialCreditFacilitiesStatusResponseDTO(CommercialCreditFacilitiesStatusResponse commercialCreditFacilitiesStatusResponse) {
        if (commercialCreditFacilitiesStatusResponse != null) {
            if (commercialCreditFacilitiesStatusResponse.getCommercialCreditFacilityStatusResponses() != null) {
                for (CommercialCreditFacilityStatusResponse commercialCreditFacilityStatusResponse : commercialCreditFacilitiesStatusResponse.getCommercialCreditFacilityStatusResponses()) {
                    CommercialCreditFacilityStatusResponseDTO commercialCreditFacilityStatusResponseDTO = new CommercialCreditFacilityStatusResponseDTO(commercialCreditFacilityStatusResponse);
                    getCommercialCreditFacilityStatusResponseDTOList().add(commercialCreditFacilityStatusResponseDTO);
                }
            }

            if (commercialCreditFacilitiesStatusResponse.getCommercialCreditFacilityStatusLabels() != null) {
                for (CommercialCreditFacilityStatusLabel commercialCreditFacilityStatusLabel : commercialCreditFacilitiesStatusResponse.getCommercialCreditFacilityStatusLabels()) {
                    CommercialCreditFacilityStatusLabelDTO commercialCreditFacilityStatusLabelDTO = new CommercialCreditFacilityStatusLabelDTO(commercialCreditFacilityStatusLabel);
                    getCommercialCreditFacilityStatusLabelDTOList().add(commercialCreditFacilityStatusLabelDTO);
                }
            }
        }
    }

    public List<CommercialCreditFacilityStatusResponseDTO> getCommercialCreditFacilityStatusResponseDTOList() {
        if (commercialCreditFacilityStatusResponseDTOList == null) {
            this.commercialCreditFacilityStatusResponseDTOList = new ArrayList<>();
        }
        return commercialCreditFacilityStatusResponseDTOList;
    }

    public void setCommercialCreditFacilityStatusResponseDTOList(List<CommercialCreditFacilityStatusResponseDTO> commercialCreditFacilityStatusResponseDTOList) {
        this.commercialCreditFacilityStatusResponseDTOList = commercialCreditFacilityStatusResponseDTOList;
    }

    public List<CommercialCreditFacilityStatusLabelDTO> getCommercialCreditFacilityStatusLabelDTOList() {
        if (commercialCreditFacilityStatusLabelDTOList == null) {
            this.commercialCreditFacilityStatusLabelDTOList = new ArrayList<>();
        }
        return commercialCreditFacilityStatusLabelDTOList;
    }

    public void setCommercialCreditFacilityStatusLabelDTOList(List<CommercialCreditFacilityStatusLabelDTO> commercialCreditFacilityStatusLabelDTOList) {
        this.commercialCreditFacilityStatusLabelDTOList = commercialCreditFacilityStatusLabelDTOList;
    }
}
