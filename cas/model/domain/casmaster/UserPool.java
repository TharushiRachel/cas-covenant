package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Audited
@Table(name = "CA_POOL_CONFIG")
public class UserPool extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMMITTEE_USER_POOL")
    @SequenceGenerator(name = "SEQ_COMMITTEE_USER_POOL", sequenceName = "SEQ_COMMITTEE_USER_POOL", allocationSize = 1)
//    @Column(name = "USER_POOL_ID")
//    private Integer userPoolId;
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

//    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "userPool")
//    private List<CAUser> caUserList;
}
