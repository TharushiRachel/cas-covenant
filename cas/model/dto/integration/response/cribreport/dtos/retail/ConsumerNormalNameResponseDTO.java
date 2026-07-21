package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerNormalNameResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.NameResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerNormalNameResponseDTO implements Serializable {

    private List<NameResponseDTO> nameResponseDTOS;

    public ConsumerNormalNameResponseDTO() {
    }

    public ConsumerNormalNameResponseDTO(ConsumerNormalNameResponse consumerNormalNameResponse) {
        if(consumerNormalNameResponse.getNameResponses()!=null){
            for (NameResponse nameResponse : consumerNormalNameResponse.getNameResponses()) {
                NameResponseDTO nameResponseDTO = new NameResponseDTO(nameResponse);
                getNameResponseDTOS().add(nameResponseDTO);
            }
        }
    }

    public List<NameResponseDTO> getNameResponseDTOS() {
        if (nameResponseDTOS == null) {
            nameResponseDTOS = new ArrayList<>();
        }
        return nameResponseDTOS;
    }

    public void setNameResponseDTOS(List<NameResponseDTO> nameResponseDTOS) {
        this.nameResponseDTOS = nameResponseDTOS;
    }
}
