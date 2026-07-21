package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "CA_COMMITTEE_COMMENT_TEMP")
public class CACommitteeCommentTemp extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_COMMITTEE_COMMENT_TEMP ")
    @SequenceGenerator(name = "SEQ_CA_COMMITTEE_COMMENT_TEMP ", sequenceName = "SEQ_CA_COMMITTEE_COMMENT_TEMP ", allocationSize = 1)
    @Column(name = "COMMENT_ID")
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "COMMITTEE_ID")
    private CACommitteeTemp caCommitteeTemp;

    @Column(name = "CA_COMMENT")
    private String comment;

    @Column(name = "COMMENT_STATUS")
    private String commentStatus;

    @Column(name = "USER_DISPLAY_NAME")
    private String userDisplayName;
}
