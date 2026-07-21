package com.itechro.cas.model.domain.sme;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "T_SME_QUESTION")
@Data
public class SmeQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SME_QUESTION")
    @SequenceGenerator(name = "SEQ_SME_QUESTION", sequenceName = "SEQ_SME_QUESTION", allocationSize = 1)
    @Column(name = "SME_QUESTION_ID")
    private Integer smeQuestionId;

    @Column(name = "QUESTION")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "QUESTION_TYPE")
    private AppsConstants.AnswerType questionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "smeQuestion")
    private List<SmeQuestionConfig> smeQuestionConfigList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "smeQuestion")
    private List<SmeQuestionAnswer> smeQuestionAnswerList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "smeQuestion")
    private List<FpSmeAnswer> fpSmeAnswerList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmeQuestion)) return false;
        SmeQuestion that = (SmeQuestion) o;
        return Objects.equals(smeQuestionId, that.smeQuestionId);
    }

    @Override
    public int hashCode() {
        return smeQuestionId != null ? smeQuestionId.hashCode() : 0;
    }
}
