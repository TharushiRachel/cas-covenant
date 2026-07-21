package com.itechro.cas.model.dto.applicationform.esg;

import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureAnswerData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AFAnnexureAnswerDataDTO {

    private Integer answerDataId;

    private Integer answerId;

    private Integer questionId;

    private String answer;

    private Integer displayOrder;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private List<AFEsgAnswerDTO> afEsgAnswerDTOList;

    public AFAnnexureAnswerDataDTO(AFAnnexureAnswerData afAnnexureAnswer){
        this.answerDataId = afAnnexureAnswer.getAnswerDataId();
        this.answerId = afAnnexureAnswer.getAnswerId();
        this.questionId = afAnnexureAnswer.getAfAnnexureQuestionData().getQuestionId();
        this.answer = afAnnexureAnswer.getAnswer();
        this.displayOrder = afAnnexureAnswer.getDisplayOrder();
        this.createdDate = afAnnexureAnswer.getCreatedDate();
        this.createdBy = afAnnexureAnswer.getCreatedBy();
        this.modifiedDate = afAnnexureAnswer.getModifiedDate();
        this.modifiedBy = afAnnexureAnswer.getModifiedBy();

//        if(afAnnexureAnswer.getAfEsgAnswerList() != null && !afAnnexureAnswer.getAfEsgAnswerList().isEmpty()) {
//            this.afEsgAnswerDTOList = new ArrayList<>();
//            for(AFEsgAnswer esgAnswer : afAnnexureAnswer.getAfEsgAnswerList()) {
//                this.afEsgAnswerDTOList.add(new AFEsgAnswerDTO(esgAnswer));
//            }
//        } else {
//            this.afEsgAnswerDTOList = new ArrayList<>();
//        }
    }
}
