package com.itechro.cas.model.domain.facilitypaper.committee;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "T_FP_INQ_REQ_RES_AUDIT")
public class CommitteeInqReqResAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_INQ_REQ_RES_AUDIT")
    @SequenceGenerator(name = "SEQ_T_FP_INQ_REQ_RES_AUDIT", sequenceName = "SEQ_T_FP_INQ_REQ_RES_AUDIT", allocationSize = 1)
    @Column(name = "INQ_REQ_RES_AUDIT_ID")
    private Integer inqReqResAuditId;

    @Column(name = "AUDIT_SET_ID")
    private Integer auditSetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COM_INQ_ID")
    private CommitteeInquiryMaster committeeInquiryMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INQREQ_RES_ID")
    private CommitteeInqReqRes committeeInqReqRes;

    @Column(name = "INQ_REQ_RES_TXT1")
    private String inquiryRequestResponseText1;

    @Column(name = "INQ_REQ_RES_TXT2")
    private String inquiryRequestResponseText2;

    @Column(name = "INQ_REQ_RES_TXT3")
    private String inquiryRequestResponseText3;

    @Column(name = "INQ_REQ_RES_TXT4")
    private String inquiryRequestResponseText4;

    @Column(name = "INQ_REQ_RES_TXT5")
    private String inquiryRequestResponseText5;

    @Enumerated(EnumType.STRING)
    @Column(name = "INQ_REQ_RES_TYPE")
    private AppsConstants.InquiryType inquiryRequestResponseType;

    @Column(name = "ACT_BY")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACT_ON", updatable = false)
    private Date createdDate;

    @Column(name = "ACT_USER_DISPLAY_NAME")
    private String createdUserDisplayName;
}
