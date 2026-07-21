package com.itechro.cas.model.domain.sme;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "T_SME_QUESTION_ANSWER")
@Data
public class SmeQuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SME_QUESTION_ANSWER")
    @SequenceGenerator(name = "SEQ_SME_QUESTION_ANSWER", sequenceName = "SEQ_SME_QUESTION_ANSWER", allocationSize = 1)
    @Column(name = "SME_ANSWER_ID")
    private Integer smeAnswerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SME_QUESTION_ID", referencedColumnName = "SME_QUESTION_ID")
    private SmeQuestion smeQuestion;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "COMMENT_DESCRIPTION")
    private String commentDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "smeQuestionAnswer")
    private List<FpSmeAnswer> fpSmeAnswerList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "smeQuestionAnswer")
    private List<SmeAnswerDescription> smeAnswerDescriptionList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmeQuestionAnswer)) return false;
        SmeQuestionAnswer that = (SmeQuestionAnswer) o;
        return Objects.equals(smeAnswerId, that.smeAnswerId);
    }

    @Override
    public int hashCode() {
        return smeAnswerId != null ? smeAnswerId.hashCode() : 0;
    }

}
