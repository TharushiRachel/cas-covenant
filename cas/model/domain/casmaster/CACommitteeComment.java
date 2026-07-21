package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;
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
@Table(name = "CA_COMMITTEE_COMMENT")
public class CACommitteeComment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_COMMITTEE_COMMENT ")
    @SequenceGenerator(name = "SEQ_CA_COMMITTEE_COMMENT ", sequenceName = "SEQ_CA_COMMITTEE_COMMENT ", allocationSize = 1)
    @Column(name = "COMMENT_ID")
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "COMMITTEE_ID")
    private CACommittee caCommittee;

    @Column(name = "CA_COMMENT")
    private String comment;

    @Column(name = "COMMENT_STATUS")
    private String commentStatus;

    @Column(name = "USER_DISPLAY_NAME")
    private String userDisplayName;
}
