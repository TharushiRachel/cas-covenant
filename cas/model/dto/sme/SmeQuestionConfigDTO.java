package com.itechro.cas.model.dto.sme;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.sme.SmeQuestionConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmeQuestionConfigDTO {

    private Integer smeQuestionConfigId;

    private Integer smeQuestionId;

    private String condition;

    private Integer workClass;

    private AppsConstants.YesNo isShow;

    private AppsConstants.YesNo isShowComment;

    public SmeQuestionConfigDTO(SmeQuestionConfig smeQuestionConfig) {
        this.smeQuestionConfigId = smeQuestionConfig.getSmeQuestionConfigId();
        this.smeQuestionId = smeQuestionConfig.getSmeQuestion().getSmeQuestionId();
        this.condition = smeQuestionConfig.getCondition();
        this.workClass = smeQuestionConfig.getWorkClass();
        this.isShow = smeQuestionConfig.getIsShow();
    }

}
