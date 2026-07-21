package com.itechro.cas.model.dto.sme;

import com.itechro.cas.model.domain.sme.FpSmeAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FpSmeAnswerDTO {

    private Integer answerId;

    private Integer smeQuestionId;

    private String question;

    private Integer smeAnswerId;

    private Integer facilityPaperID;

    private String answer;

    private String answerDescription;

    private String createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private Integer createdUserWorkClass;

    private String createdUserDisplayName;


    public FpSmeAnswerDTO(FpSmeAnswer fpSmeAnswer) {
        this.answerId = fpSmeAnswer.getAnswerId();
        if(fpSmeAnswer.getSmeQuestion() != null) {
            this.smeQuestionId = fpSmeAnswer.getSmeQuestion().getSmeQuestionId();
            this.question = fpSmeAnswer.getSmeQuestion().getQuestion();
        }
        if(fpSmeAnswer.getSmeQuestionAnswer() != null) {
            this.smeAnswerId = fpSmeAnswer.getSmeQuestionAnswer().getSmeAnswerId();
        }
        if(fpSmeAnswer.getFacilityPaper() != null) {
            this.facilityPaperID = fpSmeAnswer.getFacilityPaper().getFacilityPaperID();
        }
        this.answer = fpSmeAnswer.getAnswer();
        this.answerDescription = fpSmeAnswer.getAnswerDescription();
        //this.createdDate = fpSmeAnswer.getCreatedDate();
        if (fpSmeAnswer.getCreatedDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this.createdDate = sdf.format(fpSmeAnswer.getCreatedDate());
        }
        this.createdBy = fpSmeAnswer.getCreatedBy();
        this.modifiedDate = fpSmeAnswer.getModifiedDate();
        this.modifiedBy = fpSmeAnswer.getModifiedBy();
        this.createdUserWorkClass = fpSmeAnswer.getCreatedUserWorkClass();
        this.createdUserDisplayName = fpSmeAnswer.getCreatedUserDisplayName();
    }
}
