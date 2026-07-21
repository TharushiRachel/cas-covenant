package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.CustomerEvaluationResults;
import com.itechro.cas.model.domain.customer.EvaluationElement;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */

@Data
public class EvaluationAnswersDTO implements Serializable {


    private Integer  answerId;

    private Integer score;

    private String caption;

    private AppsConstants.Answers status;

    private String savedBy;

    private Date savedOn;

    private String authorizedBy;

    private String authorizedOn;

    private Integer orderBy;

    private EvaluationElement evaluationElement;


    private List<CustomerEvaluationResults> customerEvaluationResultsSet;
}
