package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.util.CalendarUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author tharushi
 */

@Data
@Getter
@Setter
public class CustomerEvaluationListDTO implements Serializable, Comparable<CustomerEvaluationListDTO>{

    private Integer customerEvaluationId;

    private String customerId;

    private Integer evaluationRecordId;

    private Integer score;

    private String filledDate;

    private AppsConstants.CustomerEvaluation status;

    private String userId;

//    private String facilityPaper;

    @Override
    public int compareTo(@NotNull CustomerEvaluationListDTO customerEvaluationDTO) {
        try {
            Date dateOne = CalendarUtil.getDefaultParsedDateTime(this.filledDate);
            Date date = CalendarUtil.getDefaultParsedDateTime(customerEvaluationDTO.filledDate);
            //int x = dateOne.compareTo(date);
            return dateOne.compareTo(date);
        } catch (Exception e) {
            return 0;
        }
    }

}
