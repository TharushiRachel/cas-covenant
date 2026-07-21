package com.itechro.cas.model.dto.customer;

import com.itechro.cas.model.domain.customer.CustomerEvaluation;
import com.itechro.cas.model.domain.customer.EvaluationAnswers;
import com.itechro.cas.model.domain.customer.EvaluationElement;

import java.io.Serializable;

/**
 *
 *
 * @author tharushi
 */
public class CustomerEvaluationResultsDTO implements Serializable {
    private Integer customerEvaluationResultId;

    private CustomerEvaluation customerEvaluation;

    private EvaluationElement evaluationElement;

    private Integer score;

    private EvaluationAnswers evaluationAnswers;
}
