package com.itechro.cas.model.dto.facilitypaper.committee;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInqReqRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CommitteeInqReqResDTO {

    private Integer inqReqResId;
    private Integer committeeInquiryId;
    private String inquiryRequestResponseText1;
//    private String inquiryRequestResponseText2;
//    private String inquiryRequestResponseText3;
//    private String inquiryRequestResponseText4;
//    private String inquiryRequestResponseText5;
    private AppsConstants.InquiryType inquiryRequestResponseType;
    private String createdBy;
    private Date createdDate;

    private AppsConstants.InquiryStatus inquiryStatus;

    private AppsConstants.Status status;

    private String assignUser;
    private String assignUserDisplayName;

    private String createdUserDisplayName;
    private List<CommitteeInqReqResAuditDTO> committeeInqReqResAuditDTOList;

    public CommitteeInqReqResDTO(CommitteeInqReqRes committeeInqReqRes) {
        this.inqReqResId = committeeInqReqRes.getInqReqResId();
        this.committeeInquiryId = committeeInqReqRes.getCommitteeInquiryMaster().getCommitteeInquiryId();
        //this.inquiryRequestResponseText1 = committeeInqReqRes.getInquiryRequestResponseText1();
//        this.inquiryRequestResponseText2 = committeeInqReqRes.getInquiryRequestResponseText2();
//        this.inquiryRequestResponseText3 = committeeInqReqRes.getInquiryRequestResponseText3();
//        this.inquiryRequestResponseText4 = committeeInqReqRes.getInquiryRequestResponseText4();
//        this.inquiryRequestResponseText5 = committeeInqReqRes.getInquiryRequestResponseText5();
        this.inquiryStatus = committeeInqReqRes.getCommitteeInquiryMaster().getInquiryStatus();
        this.status = committeeInqReqRes.getCommitteeInquiryMaster().getStatus();
        this.assignUser = committeeInqReqRes.getCommitteeInquiryMaster().getAssignUser();
        this.assignUserDisplayName = committeeInqReqRes.getCommitteeInquiryMaster().getAssignUserDisplayName();
        this.createdUserDisplayName = committeeInqReqRes.getCreatedUserDisplayName();

        StringBuilder combinedText = new StringBuilder();
        if (committeeInqReqRes.getInquiryRequestResponseText1() != null) {
            combinedText.append(committeeInqReqRes.getInquiryRequestResponseText1());
        }
        if (committeeInqReqRes.getInquiryRequestResponseText2() != null) {
            combinedText.append(" ").append(committeeInqReqRes.getInquiryRequestResponseText2());
        }
        if (committeeInqReqRes.getInquiryRequestResponseText3() != null) {
            combinedText.append(" ").append(committeeInqReqRes.getInquiryRequestResponseText3());
        }
        if (committeeInqReqRes.getInquiryRequestResponseText4() != null) {
            combinedText.append(" ").append(committeeInqReqRes.getInquiryRequestResponseText4());
        }
        if (committeeInqReqRes.getInquiryRequestResponseText5() != null) {
            combinedText.append(" ").append(committeeInqReqRes.getInquiryRequestResponseText5());
        }

        this.inquiryRequestResponseText1 = combinedText.toString().trim();

        this.inquiryRequestResponseType = committeeInqReqRes.getInquiryRequestResponseType();
        this.createdBy = committeeInqReqRes.getCreatedBy();
        this.createdDate = committeeInqReqRes.getCreatedDate();

        if (committeeInqReqRes.getCommitteeInqReqResAudits() != null) {
            this.committeeInqReqResAuditDTOList = committeeInqReqRes.getCommitteeInqReqResAudits().stream()
                    .map(CommitteeInqReqResAuditDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
