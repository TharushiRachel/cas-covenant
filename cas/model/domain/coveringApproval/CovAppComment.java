package com.itechro.cas.model.domain.coveringApproval;


import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.customer.CustomerAddress;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString
@Table(name = "T_COVAPP_COMMENT")
public class CovAppComment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_COVAPP_COMMENT")
    @SequenceGenerator(name = "SEQ_T_COVAPP_COMMENT", sequenceName = "SEQ_T_COVAPP_COMMENT", allocationSize = 1)
    @Column(name = "COVAPP_COMMENT_ID")
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COVAPP_ID", referencedColumnName = "COVAPP_ID")
    private CoveringApproval coveringApproval;

    @Column(name = "USER_COMMENT")
    private String userComment;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "CURRENT_COVAPP_STATUS")
    private DomainConstants.CoveringApprovalStatus currentStatus;

    @Column(name = "CREATED_USER_ID")
    private String createdUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "CREATED_USER")
    private String createdUser;

    @Column(name = "CREATED_USER_DIV_CODE")
    private String createdUserDivCode;

    @Column(name = "ASSIGNED_USER_ID")
    private String assignUserId;

    @Column(name = "ASSIGNED_USER")
    private String assignUser;

    @Column(name = "ASSIGNED_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "ASSIGNED_USER_DIV_CODE")
    private String assignUserDivCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_PUBLIC")
    private AppsConstants.YesNo isPublic;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_DIVISION_ONLY")
    private AppsConstants.YesNo isDivisionOnly;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_USERS_ONLY")
    private AppsConstants.YesNo isUsersOnly;

    @Column(name = "ACTION_MESSAGE")
    private String actionMessage;

    @Column(name = "ASSIGN_DEPARTMENT_CODE")
    private String assignDepartmentCode;

    @Column(name = "CREATED_USER_UPM_CODE")
    private String createdUserUpmCode;
}
