package com.itechro.cas.model.dto.sme;

import com.itechro.cas.model.domain.sme.SmeAnswerDescription;
import com.itechro.cas.model.domain.sme.SmeQuestionAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmeAnswerDescriptionDTO {
    private Integer descriptionId;
    private Integer smeAnswerId;
    private String description;

    public SmeAnswerDescriptionDTO(SmeAnswerDescription smeAnswerDescription) {
        this.descriptionId = smeAnswerDescription.getDescriptionId();
        this.smeAnswerId = smeAnswerDescription.getSmeQuestionAnswer().getSmeAnswerId();
        this.description = smeAnswerDescription.getDescription();
    }
}
