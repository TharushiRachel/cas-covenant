package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "CA_COMMITTEE_CONFIG_TEMP")
public class CACommitteeTemp extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_COMMITTEE_CONFIG_TEMP ")
    @SequenceGenerator(name = "SEQ_CA_COMMITTEE_CONFIG_TEMP ", sequenceName = "SEQ_CA_COMMITTEE_CONFIG_TEMP ", allocationSize = 1)
    @Column(name = "COMMITTEE_ID")
    private Integer committeeId;

    @Column(name = "COMMITTEE_NAME")
    private String committeeName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CommitteeType.class)
    @JoinColumns({
            @JoinColumn(name = "COMMITTEE_TYPE_ID", referencedColumnName = "COMMITTEE_TYPE_ID"),
            @JoinColumn(name = "COMMITTEE_TYPE", referencedColumnName = "COMMITTEE_TYPE")
    })
    private CommitteeType committeeType;

    @Column(name = "DELEGATED_AUTHORITY_IN_BILLION")
    private String delegatedAuthority;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "REVIEWER")
    private String reviewer;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENT_PATH")
    private AppsConstants.CAPathType currentPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "COMMITTEE_STATUS")
    private AppsConstants.CACommitteeStatus committeeStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caCommitteeTemp")
    private List<CALevelTemp> caLevelTempList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caCommitteeTemp")
    private List<CAUserTemp> caUserTempList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caCommitteeTemp")
    private List<CACommitteeCommentTemp> caCommitteeCommentTemps;
}
