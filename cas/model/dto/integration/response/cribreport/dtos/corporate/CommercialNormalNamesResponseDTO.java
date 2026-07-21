package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialNormalNamesResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.NamesResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialNormalNamesResponseDTO implements Serializable {

    private List<NamesResponseDTO> namesResponseDTOList;

    public CommercialNormalNamesResponseDTO() {
    }

    public CommercialNormalNamesResponseDTO(CommercialNormalNamesResponse commercialNormalNamesResponse) {
        if (commercialNormalNamesResponse != null) {
            if (commercialNormalNamesResponse.getNamesResponses() != null) {
                for (NamesResponse namesResponse : commercialNormalNamesResponse.getNamesResponses()) {
                    this.getNamesResponseDTOList().add(new NamesResponseDTO(namesResponse));
                }
            }
        }
    }

    public List<NamesResponseDTO> getNamesResponseDTOList() {
        if (namesResponseDTOList == null) {
            this.namesResponseDTOList = new ArrayList<>();
        }
        return namesResponseDTOList;
    }

    public void setNamesResponseDTOList(List<NamesResponseDTO> namesResponseDTOList) {
        this.namesResponseDTOList = namesResponseDTOList;
    }
}
