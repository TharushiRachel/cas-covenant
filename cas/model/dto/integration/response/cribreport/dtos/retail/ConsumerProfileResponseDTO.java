package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerDetailResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerProfileResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerProfileResponseDTO implements Serializable {

    private List<ConsumerDetailResponseDTO> consumerDetailResponseDTOList;

    public ConsumerProfileResponseDTO() {
    }

    public ConsumerProfileResponseDTO(ConsumerProfileResponse consumerProfileResponse) {
        if (consumerProfileResponse.getConsumerDetailResponseList() != null) {
            for (ConsumerDetailResponse consumerDetailResponse : consumerProfileResponse.getConsumerDetailResponseList()) {
                this.getConsumerDetailResponseDTOList().add(new ConsumerDetailResponseDTO(consumerDetailResponse));
            }
        }
    }

    public List<ConsumerDetailResponseDTO> getConsumerDetailResponseDTOList() {
        if (consumerDetailResponseDTOList == null) {
            this.consumerDetailResponseDTOList = new ArrayList<>();
        }
        return consumerDetailResponseDTOList;
    }

    public void setConsumerDetailResponseDTOList(List<ConsumerDetailResponseDTO> consumerDetailResponseDTOList) {
        this.consumerDetailResponseDTOList = consumerDetailResponseDTOList;
    }

    @Override
    public String toString() {
        return "ConsumerProfileResponseDTO{" +
                "consumerDetailResponseDTOList=" + consumerDetailResponseDTOList +
                '}';
    }
}
