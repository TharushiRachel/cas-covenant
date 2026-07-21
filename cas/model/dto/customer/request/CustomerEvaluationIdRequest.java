package com.itechro.cas.model.dto.customer.request;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerEvaluationIdRequest{

    private Integer id;

    private Integer facilityPaperID;

    private String customerId;

    private Integer customerEvaluationId;

    private AppsConstants.EvaluationForm Status;


}
