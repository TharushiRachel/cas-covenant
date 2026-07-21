package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
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
@Table(name = "CA_USER_CONFIG_TEMP")
public class CAUserTemp extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_USER_CONFIG_TEMP ")
    @SequenceGenerator(name = "SEQ_CA_USER_CONFIG_TEMP ", sequenceName = "SEQ_CA_USER_CONFIG_TEMP ", allocationSize = 1)
    @Column(name = "USER_CONFIG_ID")
    private Integer userConfigId;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS")
    private AppsConstants.Status userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "COMMITTEE_ID")
    private CACommitteeTemp caCommitteeTemp;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CALevelTemp.class)
    @JoinColumns({
            @JoinColumn(name = "LEVEL_CONFIG_ID", referencedColumnName = "LEVEL_CONFIG_ID"),
            @JoinColumn(name = "PATH_TYPE", referencedColumnName = "PATH_TYPE")
    })
    private CALevelTemp caLevelTemp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NAME", referencedColumnName = "USER_NAME")
    private UserPool userPool;
}
