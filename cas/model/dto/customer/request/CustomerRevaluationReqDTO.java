package com.itechro.cas.model.dto.customer.request;

import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.customer.CustomerEvaluationAnswersDTO;
import lombok.Data;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CustomerRevaluationReqDTO {

//    private CustomerEvaluationIdRequest customerEvaluationIdRequest;
//    private Page<CustomerEvaluationAnswersDTO> evaluationAnswersPage;

    private String customerId;

    private Integer customerEvaluationId;


}
