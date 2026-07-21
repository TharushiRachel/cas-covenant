package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditFacilityStatus;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditFacilityStatusLabelResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerCreditFacilityStatusResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerCreditFacilityStatusResponseDTO implements Serializable {

    private List<ConsumerCreditFacilityStatusDTO> consumerCreditFacilityStatusDTOList;

    private List<ConsumerCreditFacilityStatusLabelResponseDTO> consumerCreditFacilityStatusLabelResponseDTOList;

    public ConsumerCreditFacilityStatusResponseDTO() {
    }

    public ConsumerCreditFacilityStatusResponseDTO(ConsumerCreditFacilityStatusResponse consumerCreditFacilityStatusResponse) {
        if (consumerCreditFacilityStatusResponse.getConsumerCreditFacilityStatusList() != null) {
            for (ConsumerCreditFacilityStatus consumerCreditFacilityStatus : consumerCreditFacilityStatusResponse.getConsumerCreditFacilityStatusList()) {
                getConsumerCreditFacilityStatusDTOList().add(new ConsumerCreditFacilityStatusDTO(consumerCreditFacilityStatus));
            }
        }

        if (consumerCreditFacilityStatusResponse.getConsumerCreditFacilityStatusLabelResponseList() != null) {
            for (ConsumerCreditFacilityStatusLabelResponse consumerCreditFacilityStatusLabelResponse : consumerCreditFacilityStatusResponse.getConsumerCreditFacilityStatusLabelResponseList()) {
                getConsumerCreditFacilityStatusLabelResponseDTOList().add(new ConsumerCreditFacilityStatusLabelResponseDTO(consumerCreditFacilityStatusLabelResponse));
            }
        }
    }

    public List<ConsumerCreditFacilityStatusDTO> getConsumerCreditFacilityStatusDTOList() {
        if (consumerCreditFacilityStatusDTOList == null) {
            this.consumerCreditFacilityStatusDTOList = new ArrayList<>();
        }
        return consumerCreditFacilityStatusDTOList;
    }

    public void setConsumerCreditFacilityStatusDTOList(List<ConsumerCreditFacilityStatusDTO> consumerCreditFacilityStatusDTOList) {
        this.consumerCreditFacilityStatusDTOList = consumerCreditFacilityStatusDTOList;
    }

    public List<ConsumerCreditFacilityStatusLabelResponseDTO> getConsumerCreditFacilityStatusLabelResponseDTOList() {
        if (consumerCreditFacilityStatusLabelResponseDTOList == null) {
            this.consumerCreditFacilityStatusLabelResponseDTOList = new ArrayList<>();
        }
        return consumerCreditFacilityStatusLabelResponseDTOList;
    }

    public void setConsumerCreditFacilityStatusLabelResponseDTOList(List<ConsumerCreditFacilityStatusLabelResponseDTO> consumerCreditFacilityStatusLabelResponseDTOList) {
        this.consumerCreditFacilityStatusLabelResponseDTOList = consumerCreditFacilityStatusLabelResponseDTOList;
    }
}
