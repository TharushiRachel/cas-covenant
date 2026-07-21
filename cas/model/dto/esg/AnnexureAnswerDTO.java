package com.itechro.cas.model.dto.esg;

import com.itechro.cas.model.domain.esg.AnnexureAnswer;
import com.itechro.cas.model.domain.esg.AnnexureAnswerTemp;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class AnnexureAnswerDTO implements Serializable {

    private Integer answerId;

    private Integer parentId;

    private Integer questionId;

    private String answer;

    private Integer displayOrder;

    private String status;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private String actionStatus;

    private String userAnswer;

    private Boolean isSelected;

    public AnnexureAnswerDTO(AnnexureAnswer annexureAnswer){
        this.answerId = annexureAnswer.getAnswerId();
        this.parentId = 0;
        this.questionId = annexureAnswer.getAnnexureQuestion().getQuestionId();
        this.answer = annexureAnswer.getAnswer();
        this.displayOrder = annexureAnswer.getDisplayOrder();
        this.createdDate = annexureAnswer.getCreatedDate();
        this.createdBy = annexureAnswer.getCreatedBy();
        this.modifiedDate = annexureAnswer.getModifiedDate();
        this.modifiedBy = annexureAnswer.getModifiedBy();
        this.status = annexureAnswer.getStatus();
    }

    public AnnexureAnswerDTO(AnnexureAnswerTemp annexureAnswerTemp){
        this.answerId = annexureAnswerTemp.getAnswerId();
        this.parentId = annexureAnswerTemp.getParentId();
        this.questionId = annexureAnswerTemp.getAnnexureQuestionTemp().getQuestionId();
        this.answer = annexureAnswerTemp.getAnswer();
        this.displayOrder = annexureAnswerTemp.getDisplayOrder();
        this.createdDate = annexureAnswerTemp.getCreatedDate();
        this.createdBy = annexureAnswerTemp.getCreatedBy();
        this.modifiedDate = annexureAnswerTemp.getModifiedDate();
        this.modifiedBy = annexureAnswerTemp.getModifiedBy();
        this.actionStatus = annexureAnswerTemp.getActionStatus();
        this.status = annexureAnswerTemp.getStatus();
    }

    public AnnexureAnswerDTO(Integer answerId, String answer, Integer displayOrder) {
        this.answerId = answerId;
        this.answer = answer;
        this.displayOrder = displayOrder;
    }
}
