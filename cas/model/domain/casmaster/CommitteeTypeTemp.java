package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "CA_COMMITTEE_TYPE_CONFIG_TEMP")
public class CommitteeTypeTemp extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_COMMITTEE_TYPE_TEMP ")
    @SequenceGenerator(name = "SEQ_CA_COMMITTEE_TYPE_TEMP ", sequenceName = "SEQ_CA_COMMITTEE_TYPE_TEMP ", allocationSize = 1)
    @Column(name = "COMMITTEE_TYPE_ID")
    private Integer committeeTypeId;

    @Column(name = "COMMITTEE_TYPE")
    private String committeeTypeName;

    @Column(name = "COMMITTEE_TYPE_NAME")
    private String committeeDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

}
