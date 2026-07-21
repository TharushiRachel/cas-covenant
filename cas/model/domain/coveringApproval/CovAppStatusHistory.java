package com.itechro.cas.model.domain.coveringApproval;

import com.itechro.cas.commons.constants.DomainConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 *
 *
 * @author tharushi
 */
@Getter
@Setter
@Entity
@Table(name = "T_COVAPP_STATUS_HISTORY")
@ToString
public class CovAppStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_COVAPP_STATUS_HISTORY")
    @SequenceGenerator(name = "SEQ_T_COVAPP_STATUS_HISTORY", sequenceName = "SEQ_T_COVAPP_STATUS_HISTORY", allocationSize = 1)
    @Column(name = "COVAPP_STATUS_HISTORY_ID")
    private Integer statusHistoryID;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COVAPP_ID", referencedColumnName = "COVAPP_ID")
    private CoveringApproval coveringApproval;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENT_COVAPP_STATUS")
    private DomainConstants.CoveringApprovalStatus currentStatus;

    @Column(name = "ASSIGN_USER")
    private String assignUser;

    @Column(name = "REMARK") // Deprecated ,cause the remark will add as a comment
    private String remark;

    @Column(name = "UPDATE_BY")
    private String updateBy;

    @Column(name = "UPDATED_USER_DISPLAY_NAME", nullable = false)
    private String updatedUserDisplayName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updateDate;

    @Column(name = "ACTION_MESSAGE")
    private String actionMessage;

    @Column(name = "ASSIGN_USER_ID")
    private String assignUserID;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "ASSIGN_USER_UPM_ID")
    private String assignUserUpmID;

    @Column(name = "ASSIGN_USER_UPM_GROUP_CODE")
    private String assignUserUpmGroupCode;

    @Column(name = "ASSIGN_USER_DIV_CODE")
    private String assignUserDivCode;

    @Column(name = "ASSIGN_DEPARTMENT_CODE")
    private String assignDepartmentCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "FORWARDING_TYPE")
    private DomainConstants.ForwardType forwardType;

    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

}
