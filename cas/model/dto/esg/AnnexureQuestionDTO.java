package com.itechro.cas.model.dto.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.esg.AnnexureAnswer;
import com.itechro.cas.model.domain.esg.AnnexureAnswerTemp;
import com.itechro.cas.model.domain.esg.AnnexureQuestion;
import com.itechro.cas.model.domain.esg.AnnexureQuestionTemp;
import com.itechro.cas.model.dto.applicationform.esg.AFAnnexureAnswerDataDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AnnexureQuestionDTO implements Serializable {

    private Integer questionId;

    private Integer parentId;

    private Integer annexureId;

    private String question;

    private AppsConstants.AnswerType answerType;

    private AppsConstants.YesNo isMandatory;

    private Integer displayOrder;

    private String status;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private String actionStatus;

    private String answeredBy;

    private Date answeredDate;

    private List<AnnexureAnswerDTO> answers;

    private List<AFAnnexureAnswerDataDTO> userAnswer;


    public AnnexureQuestionDTO(AnnexureQuestion annexureQuestion){
        this.questionId = annexureQuestion.getQuestionId();
        this.parentId = 0;
        this.annexureId = annexureQuestion.getAnnexure().getAnnexureId();
        this.question = annexureQuestion.getQuestion();
        this.answerType = annexureQuestion.getAnswerType();
        this.isMandatory = annexureQuestion.getIsMandatory();
        this.displayOrder = annexureQuestion.getDisplayOrder();
        this.status = annexureQuestion.getStatus();
        this.createdDate = annexureQuestion.getCreatedDate();
        this.createdBy = annexureQuestion.getCreatedBy();
        this.modifiedDate = annexureQuestion.getModifiedDate();
        this.modifiedBy = annexureQuestion.getModifiedBy();

        if(annexureQuestion.getAnnexureAnswerList() != null && !annexureQuestion.getAnnexureAnswerList().isEmpty()) {
            this.answers = new ArrayList<>();
            List<AnnexureAnswer> activeAnswers = annexureQuestion.getAnnexureAnswerList().stream()
                    .filter(answer -> answer.getStatus().equals(AppsConstants.Status.ACT.toString()))
                    .collect(Collectors.toList());
            for (AnnexureAnswer answer : activeAnswers) {
                this.answers.add(new AnnexureAnswerDTO(answer));
            }
            this.answers.sort(Comparator.comparingInt(AnnexureAnswerDTO::getDisplayOrder));
        } else {
            this.answers = new ArrayList<>();
        }
    }

    public AnnexureQuestionDTO(AnnexureQuestionTemp annexureQuestionTemp){
        this.questionId = annexureQuestionTemp.getQuestionId();
        this.parentId = annexureQuestionTemp.getParentId();
        this.annexureId = annexureQuestionTemp.getAnnexureTemp().getAnnexureId();
        this.question = annexureQuestionTemp.getQuestion();
        this.answerType = annexureQuestionTemp.getAnswerType();
        this.isMandatory = annexureQuestionTemp.getIsMandatory();
        this.displayOrder = annexureQuestionTemp.getDisplayOrder();
        this.createdDate = annexureQuestionTemp.getCreatedDate();
        this.createdBy = annexureQuestionTemp.getCreatedBy();
        this.modifiedDate = annexureQuestionTemp.getModifiedDate();
        this.modifiedBy = annexureQuestionTemp.getModifiedBy();
        this.actionStatus = annexureQuestionTemp.getActionStatus();
        this.status = annexureQuestionTemp.getStatus();

        if(annexureQuestionTemp.getAnnexureAnswerList() != null && !annexureQuestionTemp.getAnnexureAnswerList().isEmpty()) {
            this.answers = new ArrayList<>();
            for (AnnexureAnswerTemp answer : annexureQuestionTemp.getAnnexureAnswerList()) {
                this.answers.add(new AnnexureAnswerDTO(answer));
            }
            this.answers.sort(Comparator.comparingInt(AnnexureAnswerDTO::getDisplayOrder));
        } else {
            this.answers = new ArrayList<>();
        }
    }
}
