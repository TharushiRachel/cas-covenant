package com.itechro.cas.model.dto.applicationform.esg;

import com.itechro.cas.model.domain.applicationform.esg.AFEsgAnswer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AFEsgAnswerDTO {

    private Integer esgAnswerId;

    private Integer answerId;

    private String answer;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    public AFEsgAnswerDTO(AFEsgAnswer afEsgAnswer){
        this.esgAnswerId = afEsgAnswer.getEsgAnswerId();
        //this.answerId = afEsgAnswer.getAfAnnexureAnswerData().getAnswerId();
        this.answer = afEsgAnswer.getAnswer();
        this.createdDate = afEsgAnswer.getCreatedDate();
        this.createdBy = afEsgAnswer.getCreatedBy();
        this.modifiedDate = afEsgAnswer.getModifiedDate();
        this.modifiedBy = afEsgAnswer.getModifiedBy();
    }
}
