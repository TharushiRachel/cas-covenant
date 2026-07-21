package com.itechro.cas.model.dto.customer;

import com.hazelcast.osgi.impl.HazelcastInternalOSGiService;
import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 * @author tharushi
 */
@Data
@Setter
@Getter
public class CustomerEvaluationAnswersDTO implements Serializable , Comparable<CustomerEvaluationAnswersDTO> {

    private Integer evaluationElementId;

    private Integer parentId;

    private String elementCaption;

    private AppsConstants.EvaluationElement evaluationType;

    private Integer orderBy;

    private Integer groupBy;

    private AppsConstants.YesNo isRequired;

    private Integer  answerId;

    private Integer score;

    private String answerCaption;

    private Integer ansOrderBy;

    private List<CustomerEvaluationAnswersDTO> LstEva;

    @Override
    public int compareTo(@NotNull CustomerEvaluationAnswersDTO o) {
        return 0;
    }

    public void setLstEva(ArrayList<CustomerEvaluationAnswersDTO> customerEvaluationAnswersDTOS) {
    }
}
