package com.itechro.cas.model.dto.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureData;
import com.itechro.cas.model.domain.esg.AnswerData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDataDTO {

    private Integer answerDataId;

    private Integer annexureId;

    private Integer questionId;

    private Integer answerId;

    private String answerData;

    private Integer displayOrder;

    private Integer applicationFormID;

    private Integer facilityPaperID;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private AppsConstants.Status status;

    private String annexureName;

    private String annexureDescription;

    private AppsConstants.YesNo isMandatory;

    private AppsConstants.YesNo allowRiskEdit;

    private String createdUserDivCode;

    public AnswerDataDTO(AnswerData answerData){
        this.answerDataId = answerData.getAnswerDataId();
        this.annexureId = answerData.getAnnexure() != null ? answerData.getAnnexure().getAnnexureId() : null;
        this.questionId = answerData.getQuestion() != null ? answerData.getQuestion().getQuestionId() : null;
        this.answerId = answerData.getAnswer() != null ? answerData.getAnswer().getAnswerId() : null;
        this.answerData = answerData.getAnswerData();
        this.displayOrder = answerData.getDisplayOrder();
        this.applicationFormID = answerData.getApplicationForm() != null ? answerData.getApplicationForm().getApplicationFormID() : null;
        this.facilityPaperID = answerData.getFacilityPaper() != null ? answerData.getFacilityPaper().getFacilityPaperID() : null;
        this.createdDate = answerData.getCreatedDate();
        this.createdBy = answerData.getCreatedBy();
        this.modifiedDate = answerData.getModifiedDate();
        this.modifiedBy = answerData.getModifiedBy();
        this.status = answerData.getStatus();
        this.annexureName = answerData.getAnnexureName();
        this.annexureDescription = answerData.getAnnexureDescription();
        this.isMandatory = answerData.getIsMandatory();
        this.allowRiskEdit = answerData.getAllowRiskEdit();
        this.createdUserDivCode = answerData.getCreatedUserDivCode();
    }

    public AnswerDataDTO(AFAnnexureData afAnnexureData) {
    }
}
