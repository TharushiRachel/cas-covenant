package com.itechro.cas.model.dto.customer;

import com.itechro.cas.model.common.PagedParamDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 *
 * @author tharushi
 */
@Getter
@Setter
public class SearchCustomerEvaluationRQ  extends PagedParamDTO {

    private String customerId;

    private Integer customerEvaluationId;
}
