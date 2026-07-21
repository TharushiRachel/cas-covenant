package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

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
@Table(name = "CA_LEVEL_CONFIG_TEMP")
public class CALevelTemp extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_LEVEL_CONFIG_TEMP")
    @SequenceGenerator(name = "SEQ_CA_LEVEL_CONFIG_TEMP", sequenceName = "SEQ_CA_LEVEL_CONFIG_TEMP ", allocationSize = 1)
    @Column(name = "LEVEL_CONFIG_ID")
    private Integer levelConfigId;

    @Column(name = "LEVEL_ID")
    private Integer levelId;

    @Column(name = "COMBINATION")
    private String combination;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "PATH_TYPE")
    private AppsConstants.CAPathType pathType;

    @Column(name = "USER_COUNT")
    private Integer userCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "COMMITTEE_ID")
    private CACommitteeTemp caCommitteeTemp;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEVEL_CONFIG_ID", referencedColumnName = "LEVEL_CONFIG_ID")
    private List<CAUserTemp> caUserTempList;
}
