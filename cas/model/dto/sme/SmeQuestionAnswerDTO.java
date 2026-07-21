package com.itechro.cas.model.dto.sme;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.sme.SmeQuestionAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmeQuestionAnswerDTO {

    private Integer smeAnswerId;

    private Integer smeQuestionId;

    private String answer;

    private String commentDescription;

    private AppsConstants.Status status;

    private List<FpSmeAnswerDTO> fpSmeAnswerDTOList;

    private List<SmeAnswerDescriptionDTO> smeAnswerDescriptionDTOList;

    public SmeQuestionAnswerDTO(SmeQuestionAnswer smeQuestionAnswer) {
        this.smeAnswerId = smeQuestionAnswer.getSmeAnswerId();
        this.smeQuestionId = smeQuestionAnswer.getSmeQuestion().getSmeQuestionId();
        this.answer = smeQuestionAnswer.getAnswer();
        this.commentDescription = smeQuestionAnswer.getCommentDescription();
        this.status = smeQuestionAnswer.getStatus();

//        if(smeQuestionAnswer.getFpSmeAnswerList() != null && !smeQuestionAnswer.getFpSmeAnswerList().isEmpty()) {
//            this.fpSmeAnswerDTOList = smeQuestionAnswer.getFpSmeAnswerList().stream()
//                    .map(FpSmeAnswerDTO::new)
//                    .collect(Collectors.toList());
//        }

        if(smeQuestionAnswer.getSmeAnswerDescriptionList() != null && !smeQuestionAnswer.getSmeAnswerDescriptionList().isEmpty()) {
            this.smeAnswerDescriptionDTOList = smeQuestionAnswer.getSmeAnswerDescriptionList().stream()
                    .map(SmeAnswerDescriptionDTO::new)
                    .collect(Collectors.toList());
        }
    }


}
