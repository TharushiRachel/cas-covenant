package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_FP_MD_REVIEW_COMMENTS")
public class FPMDReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_MD_REVIEW_COMMENTS")
    @SequenceGenerator(name = "SEQ_T_FP_MD_REVIEW_COMMENTS", sequenceName = "SEQ_T_FP_MD_REVIEW_COMMENTS", allocationSize = 1)
    @Column(name = "FP_COMMENT_ID")
    private Integer fpCommentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "REVIEW_COMMENT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_VIEW")
    private AppsConstants.YesNo isView;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Column(name = "CREATED_USER_WC")
    private Integer createdUserWC;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;
}
