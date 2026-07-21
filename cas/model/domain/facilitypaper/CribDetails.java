package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.domain.storage.DocStorage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_CRIB_DETAILS")
public class CribDetails extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CRIB_DETAIL")
    @SequenceGenerator(name = "SEQ_T_CRIB_DETAIL", sequenceName = "SEQ_T_CRIB_DETAIL", allocationSize = 1)
    @Column(name = "CRIB_DETAIL_ID")
    private Integer cribDetailsID;

    @Column(name = "IDENTIFICATION_TYPE")
    private String identificationType;

    @Column(name = "IDENTIFICATION_NO")
    private String identificationNo;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "GENDER")
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "CRIB_STATUS")
    private DomainConstants.CribStatusType cribStatus;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "INQUIRY_REASON")
    private String inquiryReason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRIB_ISSUE_DATE")
    private Date cribIssueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    private DocStorage docStorage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPORTING_DOC_ID")
    private SupportingDoc supportingDoc;

    @Column(name = "UPLOADED_SOL_ID")
    private String uploadedDivCode;

    @Column(name = "UPLOADED_USER_DISPLAY_NAME")
    private String uploadedUserDisplayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_SYSTEM")
    private AppsConstants.YesNo isSystem;
}