package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.CustomerEvaluationResults;
import com.itechro.cas.model.domain.customer.EvaluationAnswers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */

public class EvaluationElementDTO implements Serializable {

    private Integer EvaluationElementId;


    private Integer parentId;


    private String caption;


    private AppsConstants.EvaluationElement evaluationType;


    private AppsConstants.CustomerEvaluation status;


    private String savedBy;


    private Date savedOn;


    private String authorizedBy;


    private String authorizedOn;


    private Integer orderBy;


    private Integer groupBy;


    private AppsConstants.YesNo isRequired;


    private List<EvaluationAnswers> answersSet;


    private List<CustomerEvaluationResults> customerEvaluationResultsSet;

}
