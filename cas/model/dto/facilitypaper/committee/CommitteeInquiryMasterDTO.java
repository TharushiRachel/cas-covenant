package com.itechro.cas.model.dto.facilitypaper.committee;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInquiryMaster;
import com.itechro.cas.model.dto.email.InquiryEmailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitteeInquiryMasterDTO {
    private Integer committeeInquiryId;
    private Integer facilityPaperID;
    private AppsConstants.InquiryStatus inquiryStatus;
    private String assignUser;
    private String assignUserDisplayName;
    private String assignUserWorkClass;
    private Integer referenceInquiryId;
    private Date lastModifiedDate;
    private AppsConstants.Status status;
    private InquiryEmailDTO inquiryEmail;
    private List<CommitteeInqReqResDTO> committeeInqReqResDTOList;
    private List<CommitteeInqReqResAuditDTO> committeeInqReqResAuditDTOList;

    public CommitteeInquiryMasterDTO(CommitteeInquiryMaster committeeInquiryMaster) {
        this.committeeInquiryId = committeeInquiryMaster.getCommitteeInquiryId();
        this.facilityPaperID = committeeInquiryMaster.getFacilityPaper().getFacilityPaperID();
        this.inquiryStatus = committeeInquiryMaster.getInquiryStatus();
        this.assignUser = committeeInquiryMaster.getAssignUser();
        this.assignUserDisplayName = committeeInquiryMaster.getAssignUserDisplayName();
        this.assignUserWorkClass = committeeInquiryMaster.getAssignUserWorkClass();
        this.referenceInquiryId = committeeInquiryMaster.getReferenceInquiryId();
        this.lastModifiedDate = committeeInquiryMaster.getLastModifiedDate();
        this.status = committeeInquiryMaster.getStatus();

        if (committeeInquiryMaster.getCommitteeInqReqResList() != null) {
            this.committeeInqReqResDTOList = committeeInquiryMaster.getCommitteeInqReqResList().stream()
                    .map(CommitteeInqReqResDTO::new)
                    .collect(Collectors.toList());
        }

        if (committeeInquiryMaster.getCommitteeInqReqResAudits() != null) {
            this.committeeInqReqResAuditDTOList = committeeInquiryMaster.getCommitteeInqReqResAudits().stream()
                    .map(CommitteeInqReqResAuditDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public void setResponses(List<CommitteeInquiryMasterDTO> responseDTOs) {
    }
}
