package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 *
 *
 * @author tharushi
 */

@Data
@Setter
@Getter
public class CustomerEvaluationScoreDTO implements Serializable, Comparable<CustomerEvaluationScoreDTO>{

    private Integer customerEvaluationId;

    private String customerId;

    private Integer evaluationRecordId;

    private Integer score;

    private String filledDate;

    private AppsConstants.CustomerEvaluation status;

    private String userId;

    @Override
    public int compareTo(@NotNull CustomerEvaluationScoreDTO o) {
        return 0;
    }
}
