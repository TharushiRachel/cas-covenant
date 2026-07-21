package com.itechro.cas.model.dto.facilitypaper.response;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.CribDetails;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.util.CalendarUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CribDetailsResponse implements Serializable {

    private Integer cribDetailsID;

    private Integer facilityPaperID;

    private Integer supportingDocID;

    private String documentName;

    private DocStorageDTO docStorageDTO;

    private DomainConstants.CribStatusType cribStatus;

    private Date cribIssueDate;

    private String cribIssueDateStr;

    private String remark;

    private String inquiryReason;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private AppsConstants.Status status;

    private String fullName;

    private String gender;

    private String identificationType;

    private String identificationNumber;

    private String createdDateStr;

    private String createdBy;

    private AppsConstants.YesNo isSystem;

    private Integer docStorageID;

    private String modifiedDateStr;

    public CribDetailsResponse() {
    }
    public CribDetailsResponse(CribDetails cribDetail){
        this.cribDetailsID = cribDetail.getCribDetailsID();
        this.facilityPaperID = cribDetail.getFacilityPaper().getFacilityPaperID();
        this.supportingDocID = cribDetail.getSupportingDoc().getSupportingDocID();
        this.documentName = cribDetail.getDocStorage().getFileName();
        this.cribStatus = cribDetail.getCribStatus();
        this.remark = cribDetail.getRemark();
        this.inquiryReason = cribDetail.getInquiryReason();
        this.uploadedUserDisplayName = cribDetail.getUploadedUserDisplayName();
        this.uploadedDivCode = cribDetail.getUploadedDivCode();
        this.status = cribDetail.getStatus();
        this.fullName = cribDetail.getFullName();
        this.gender = cribDetail.getGender();
        this.identificationType = cribDetail.getIdentificationType();
        this.identificationNumber = cribDetail.getIdentificationNo();
        this.createdBy = cribDetail.getCreatedBy();
        this.isSystem = cribDetail.getIsSystem();

        if (cribDetail.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(cribDetail.getDocStorage(),false);
        }
        if (cribDetail.getCribIssueDate() != null) {
            this.cribIssueDateStr = CalendarUtil.getDefaultFormattedDateTimeOnly(cribDetail.getCribIssueDate());
        }
        if (cribDetail.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTimeOnly(cribDetail.getCreatedDate());
        }
    }
}