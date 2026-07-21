package com.itechro.cas.model.dto.facilitypaper.committee;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInqReqResAudit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommitteeInqReqResAuditDTO {
    private Integer inqReqResAuditId;
    private Integer auditSetId;
    private Integer committeeInquiryId;
    private Integer inqReqResId;
    private String inquiryRequestResponseText1;
    private String inquiryRequestResponseText2;
    private String inquiryRequestResponseText3;
    private String inquiryRequestResponseText4;
    private String inquiryRequestResponseText5;
    private AppsConstants.InquiryType inquiryRequestResponseType;
    private String createdBy;
    private Date createdDate;
    private String createdUserDisplayName;

    public CommitteeInqReqResAuditDTO(CommitteeInqReqResAudit committeeInqReqResAudit){
        this.inqReqResAuditId = committeeInqReqResAudit.getInqReqResAuditId();
        this.auditSetId = committeeInqReqResAudit.getAuditSetId();
        this.committeeInquiryId = committeeInqReqResAudit.getCommitteeInquiryMaster().getCommitteeInquiryId();
        this.inqReqResId = committeeInqReqResAudit.getCommitteeInqReqRes().getInqReqResId();
        this.inquiryRequestResponseText1 = committeeInqReqResAudit.getInquiryRequestResponseText1();
        this.inquiryRequestResponseText2 = committeeInqReqResAudit.getInquiryRequestResponseText2();
        this.inquiryRequestResponseText3 = committeeInqReqResAudit.getInquiryRequestResponseText3();
        this.inquiryRequestResponseText4 = committeeInqReqResAudit.getInquiryRequestResponseText4();
        this.inquiryRequestResponseText5 = committeeInqReqResAudit.getInquiryRequestResponseText5();
        this.inquiryRequestResponseType = committeeInqReqResAudit.getInquiryRequestResponseType();
        this.createdBy = committeeInqReqResAudit.getCreatedBy();
        this.createdDate = committeeInqReqResAudit.getCreatedDate();
        this.createdUserDisplayName = committeeInqReqResAudit.getCreatedUserDisplayName();
    }
}
