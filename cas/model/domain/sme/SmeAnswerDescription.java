package com.itechro.cas.model.domain.sme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "T_SME_ANSWER_DESCRIPTION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmeAnswerDescription {

    @Id
    @Column(name = "DESCRIPTION_ID")
    private Integer descriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SME_ANSWER_ID", referencedColumnName = "SME_ANSWER_ID")
    private SmeQuestionAnswer smeQuestionAnswer;

    @Column(name = "DESCRIPTION")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmeAnswerDescription)) return false;
        SmeAnswerDescription that = (SmeAnswerDescription) o;
        return Objects.equals(descriptionId, that.descriptionId);
    }

    @Override
    public int hashCode() {
        return descriptionId != null ? descriptionId.hashCode() : 0;
    }
}
