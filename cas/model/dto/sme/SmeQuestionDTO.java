package com.itechro.cas.model.dto.sme;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.sme.SmeQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmeQuestionDTO {

    private Integer smeQuestionId;

    private String question;

    private AppsConstants.AnswerType questionType;

    private AppsConstants.Status status;

    private List<SmeQuestionAnswerDTO> smeQuestionAnswerDTOList;

    private List<SmeQuestionConfigDTO> smeQuestionConfigDTOList;

    private List<FpSmeAnswerDTO> fpSmeAnswerDTOList;

    public SmeQuestionDTO(SmeQuestion smeQuestion, Boolean isSave) {
        this.smeQuestionId = smeQuestion.getSmeQuestionId();
        this.question = smeQuestion.getQuestion();
        this.questionType = smeQuestion.getQuestionType();
        this.status = smeQuestion.getStatus();

        if(smeQuestion.getSmeQuestionAnswerList() != null && !smeQuestion.getSmeQuestionAnswerList().isEmpty()) {
            this.smeQuestionAnswerDTOList = smeQuestion.getSmeQuestionAnswerList().stream()
                    .map(SmeQuestionAnswerDTO::new)
                    .collect(Collectors.toList());
        }

        if(smeQuestion.getSmeQuestionConfigList() != null && !smeQuestion.getSmeQuestionConfigList().isEmpty()) {
            this.smeQuestionConfigDTOList = smeQuestion.getSmeQuestionConfigList().stream()
                    .map(SmeQuestionConfigDTO::new)
                    .collect(Collectors.toList());
        }

        if(isSave == true){
            if(smeQuestion.getFpSmeAnswerList() != null && !smeQuestion.getFpSmeAnswerList().isEmpty()) {
                this.fpSmeAnswerDTOList = smeQuestion.getFpSmeAnswerList().stream()
                        .map(FpSmeAnswerDTO::new)
                        .collect(Collectors.toList());
            }
        }

    }

}
