package com.itechro.cas.model.domain.coveringApproval;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.AFComment;
import com.itechro.cas.model.domain.applicationform.AFStatusHistory;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;
import com.itechro.cas.model.dto.coveringApproval.CovAppBasicInfoDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "T_COVAPP")
public class CoveringApproval extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_COVAPP")
    @SequenceGenerator(name = "SEQ_T_COVAPP", sequenceName = "SEQ_T_COVAPP", allocationSize = 1)
    @Column(name = "COVAPP_ID")
    private Integer covAppId;

    @Enumerated(EnumType.STRING)
    @Column(name = "COVAPP_STATUS")
    private DomainConstants.CoveringApprovalStatus currentStatus;

    @Column(name = "ASSIGN_USER_ID")
    private String currentAssignUserId;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "COVAPP_REF_NUMBER", unique=true)
    private String covAppRefNumber;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;
    @Column(name = "CREATED_USER_BRANCH_CODE")
    private String createdUserBranchCode;

    @Column(name = "CURRENT_ASSIGN_AD_USER_ID")
    private String currentAssignUserAD;

    @Column(name = "CURRENT_ASSIGN_USER_DIV_CODE")
    private String currentAssignUserDivCode;

    @Column(name = "ASSIGN_USER_UPM_ID")
    private String assignUserUpmId;

    @Column(name = "ASSIGN_USER_UPM_GROUP_CODE")
    private String assignUserUpmGroupCode;

    @Column(name = "IN_PROGRESS_DATE")
    private Date inProgressDate;

    @Column(name = "APPROVED_DATE")
    private Date approveDate;

    @Column(name = "CURRENT_AUTHORITY_LEVEL")
    private String currentAuthorityLevel;

    @Column(name = "CURRENT_ASSIGN_USER")
    private String currentAssignUser;

    @Column(name = "IS_AUTHORIZED")
    private Boolean isAuthorized;

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserID;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "coveringApproval")
    private List<CovAppComment> covAppComments;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "coveringApproval")
    private List<CovAppBasicInfo> covAppBasicInfoList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "coveringApproval")
    private List<CovAppStatusHistory> covAppStatusHistoryList;

    public void addComment(CovAppComment comment) {
        comment.setCoveringApproval(this);
        this.getCovAppComments().add(comment);
    }

    public List<CovAppComment> getCovAppComments() {
        if(covAppComments == null){
            covAppComments = new ArrayList<>();
        }
        return covAppComments;
    }

    public void addBasicInfo(CovAppBasicInfo basicInfo){
        basicInfo.setCoveringApproval(this);
        this.getCovAppBasicInfoList().add(basicInfo);
    }

    public List<CovAppBasicInfo> getCovAppBasicInfoList() {
        if(covAppBasicInfoList == null){
            covAppBasicInfoList = new ArrayList<>();
        }
        return covAppBasicInfoList;
    }

    public void addStatusHistory(CovAppStatusHistory covAppStatusHistory) {
        covAppStatusHistory.setCoveringApproval(this);
        this.getCovAppStatusHistoryList().add(covAppStatusHistory);
    }

    public List<CovAppStatusHistory> getCovAppStatusHistoryList() {
        if(covAppStatusHistoryList == null){
            covAppStatusHistoryList = new ArrayList<>();
        }
        return covAppStatusHistoryList;
    }
}
