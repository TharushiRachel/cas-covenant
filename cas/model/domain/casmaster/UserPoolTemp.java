package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
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
@Table(name = "CA_POOL_CONFIG_TEMP")
public class UserPoolTemp extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMMITTEE_USER_POOL_TEMP")
    @SequenceGenerator(name = "SEQ_COMMITTEE_USER_POOL_TEMP", sequenceName = "SEQ_COMMITTEE_USER_POOL_TEMP", allocationSize = 1)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "POOL_ID")
    private Integer poolId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_DISPLAY_NAME")
    private String userDisplayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS")
    private AppsConstants.Status userStatus;

    @Column(name = "GROUP_CODE")
    private String groupCode;

    @Column(name = "REFERENCE_NAME")
    private String referenceName;
}
