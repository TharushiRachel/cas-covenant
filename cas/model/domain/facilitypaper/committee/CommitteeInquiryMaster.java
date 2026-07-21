package com.itechro.cas.model.domain.facilitypaper.committee;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "T_FP_INQ_MASTER")
public class CommitteeInquiryMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_INQ_MASTER")
    @SequenceGenerator(name = "SEQ_T_FP_INQ_MASTER", sequenceName = "SEQ_T_FP_INQ_MASTER", allocationSize = 1)
    @Column(name = "COM_INQ_ID")
    private Integer committeeInquiryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Enumerated(EnumType.STRING)
    @Column(name = "COM_INQ_STATUS")
    private AppsConstants.InquiryStatus inquiryStatus;

    @Column(name = "ASSIGN_USER")
    private String assignUser;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "ASSIGN_USER_WORK_CLASS")
    private String assignUserWorkClass;

    @Column(name = "REFERANCE_INQ_ID")
    private Integer referenceInquiryId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE", updatable = false)
    private Date lastModifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "committeeInquiryMaster")
    private List<CommitteeInqReqRes> committeeInqReqResList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "committeeInquiryMaster")
    private List<CommitteeInqReqResAudit> committeeInqReqResAudits;
}
