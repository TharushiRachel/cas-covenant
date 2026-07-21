package com.itechro.cas.model.dto.customer.request;

import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

@Data
public class CustomerEvaluationDeleteRequestDTO {

    private String customerId;

    private Integer customerEvaluationId;

    private Integer id;
}
