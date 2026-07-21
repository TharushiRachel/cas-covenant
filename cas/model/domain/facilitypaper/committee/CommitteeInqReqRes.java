package com.itechro.cas.model.domain.facilitypaper.committee;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "T_FP_INQ_REQ_RES")
public class CommitteeInqReqRes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_INQ_REQ_RES")
    @SequenceGenerator(name = "SEQ_T_FP_INQ_REQ_RES", sequenceName = "SEQ_T_FP_INQ_REQ_RES", allocationSize = 1)
    @Column(name = "INQREQ_RES_ID")
    private Integer inqReqResId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COM_INQ_ID")
    private CommitteeInquiryMaster committeeInquiryMaster;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "committeeInqReqRes")
    private List<CommitteeInqReqResAudit> committeeInqReqResAudits;
}
