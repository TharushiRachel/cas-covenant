package com.itechro.cas.model.domain.sme;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "T_SME_QUESTION_CONFIG")
@Data
public class SmeQuestionConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SME_QUESTION_CONFIG")
    @SequenceGenerator(name = "SEQ_SME_QUESTION_CONFIG", sequenceName = "SEQ_SME_QUESTION_CONFIG", allocationSize = 1)
    @Column(name = "SME_QUESTION_CONFIG_ID")
    private Integer smeQuestionConfigId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SME_QUESTION_ID", referencedColumnName = "SME_QUESTION_ID")
    private SmeQuestion smeQuestion;

    @Column(name = "CONDITION")
    private String condition;

    @Column(name = "WORK_CLASS")
    private Integer workClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_SHOW")
    private AppsConstants.YesNo isShow;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_SHOW_COMMENT")
    private AppsConstants.YesNo isShowComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmeQuestionConfig)) return false;
        SmeQuestionConfig that = (SmeQuestionConfig) o;
        return Objects.equals(smeQuestionConfigId, that.smeQuestionConfigId);
    }

    @Override
    public int hashCode() {
        return smeQuestionConfigId != null ? smeQuestionConfigId.hashCode() : 0;
    }
}
