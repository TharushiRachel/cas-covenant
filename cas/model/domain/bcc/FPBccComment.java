package com.itechro.cas.model.domain.bcc;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "T_FP_BCC_COMMENT")
@Audited
@AuditOverride(forClass = UserTrackableEntity.class)
public class FPBccComment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_BCC_COMMENT")
    @SequenceGenerator(name = "SEQ_T_FP_BCC_COMMENT", sequenceName = "SEQ_T_FP_BCC_COMMENT", allocationSize = 1)
    @Column(name = "FP_BCC_DOC_ID")
    private Integer FPBccCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FP_BCC_ID", referencedColumnName = "FP_BCC_ID")
    private FPBcc fpBcc;

    @Column(name = "BCC_COMMENT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "COMMENT_STATUS")
    private DomainConstants.MasterDataApproveStatus commentStatus;

}
