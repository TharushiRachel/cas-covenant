package com.itechro.cas.model.domain.bcc;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "T_FP_BCC_COMMENT_TEMP")
public class FPBccCommentTemp extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_BCC_COMMENT_TEMP")
    @SequenceGenerator(name = "SEQ_T_FP_BCC_COMMENT_TEMP", sequenceName = "SEQ_T_FP_BCC_COMMENT_TEMP", allocationSize = 1)
    @Column(name = "FP_BCC_DOC_ID")
    private Integer FPBccCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FP_BCC_ID", referencedColumnName = "FP_BCC_ID")
    private FPBccTemp fpBccTemp;

    @Column(name = "BCC_COMMENT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "COMMENT_STATUS")
    private DomainConstants.MasterDataApproveStatus commentStatus;

}
