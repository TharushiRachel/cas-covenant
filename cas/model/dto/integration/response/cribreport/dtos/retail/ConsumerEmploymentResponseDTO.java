package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerEmploymentResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.EmploymentDetailResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerEmploymentResponseDTO implements Serializable {

    private List<EmploymentDetailResponseDTO> employmentDetailResponsesDtos;

    public ConsumerEmploymentResponseDTO(ConsumerEmploymentResponse consumerEmploymentResponse) {
        if (consumerEmploymentResponse.getEmploymentDetailResponses() != null) {
            for (EmploymentDetailResponse employmentDetailResponse : consumerEmploymentResponse.getEmploymentDetailResponses()) {
                EmploymentDetailResponseDTO employmentDetailResponseDTO = new EmploymentDetailResponseDTO(employmentDetailResponse);
                getEmploymentDetailResponsesDtos().add(employmentDetailResponseDTO);
            }
        }

    }

    public List<EmploymentDetailResponseDTO> getEmploymentDetailResponsesDtos() {
        if (employmentDetailResponsesDtos == null) {
            this.employmentDetailResponsesDtos = new ArrayList<>();
        }
        return employmentDetailResponsesDtos;
    }

    public void setEmploymentDetailResponsesDtos(List<EmploymentDetailResponseDTO> employmentDetailResponsesDtos) {
        this.employmentDetailResponsesDtos = employmentDetailResponsesDtos;
    }
}
