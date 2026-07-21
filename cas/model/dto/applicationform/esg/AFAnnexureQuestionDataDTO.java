package com.itechro.cas.model.dto.applicationform.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureAnswerData;
import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureQuestionData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AFAnnexureQuestionDataDTO {

    private Integer questionDataId;

    private Integer questionId;

    private Integer annexureId;

    private String question;

    private AppsConstants.AnswerType answerType;

    private AppsConstants.YesNo isMandatory;

    private Integer displayOrder;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private List<AFAnnexureAnswerDataDTO> afAnnexureAnswerDataDTOList;

    public AFAnnexureQuestionDataDTO(AFAnnexureQuestionData afAnnexureQuestion){
        this.questionDataId = afAnnexureQuestion.getQuestionDataId();
        this.questionId = afAnnexureQuestion.getQuestionId();
        this.annexureId = afAnnexureQuestion.getAfAnnexureData().getAnnexureId();
        this.question = afAnnexureQuestion.getQuestion();
        this.answerType = afAnnexureQuestion.getAnswerType();
        this.isMandatory = afAnnexureQuestion.getIsMandatory();
        this.displayOrder = afAnnexureQuestion.getDisplayOrder();
        this.createdDate = afAnnexureQuestion.getCreatedDate();
        this.createdBy = afAnnexureQuestion.getCreatedBy();
        this.modifiedDate = afAnnexureQuestion.getModifiedDate();
        this.modifiedBy = afAnnexureQuestion.getModifiedBy();

        if(afAnnexureQuestion.getAfAnnexureAnswerDataList() != null && !afAnnexureQuestion.getAfAnnexureAnswerDataList().isEmpty()) {
            this.afAnnexureAnswerDataDTOList = new ArrayList<>();
            for (AFAnnexureAnswerData answer : afAnnexureQuestion.getAfAnnexureAnswerDataList()) {
                this.afAnnexureAnswerDataDTOList.add(new AFAnnexureAnswerDataDTO(answer));
            }
            this.afAnnexureAnswerDataDTOList.sort(Comparator.comparingInt(AFAnnexureAnswerDataDTO::getDisplayOrder));
        } else {
            this.afAnnexureAnswerDataDTOList = new ArrayList<>();
        }
    }
}
