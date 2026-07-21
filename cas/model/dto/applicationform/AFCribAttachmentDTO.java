package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.AFCribAttachment;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class AFCribAttachmentDTO implements Serializable {

    private Integer cribAttachmentID;

    private Integer applicationFormID;

    private Integer supportingDocID;

    private Integer basicInformationID;

    private String identificationType;

    private String identificationNo;

    private String cribDateStr;

    private DomainConstants.CribStatusType cribStatus;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private AppsConstants.Status status;

    private String createdBy;

    private String createdDateStr;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    public AFCribAttachmentDTO() {
    }

    public AFCribAttachmentDTO(AFCribAttachment afCribAttachment) {
        this(afCribAttachment, true);
    }

    public AFCribAttachmentDTO(AFCribAttachment afCribAttachment, boolean loadDoc) {
        this.applicationFormID = afCribAttachment.getApplicationFormID();
        this.cribAttachmentID = afCribAttachment.getCribAttachmentID();
        this.basicInformationID = afCribAttachment.getBasicInformation().getBasicInformationID();
        this.supportingDocID = afCribAttachment.getSupportingDocID();
        this.identificationType = afCribAttachment.getIdentificationType();
        this.identificationNo = afCribAttachment.getIdentificationNo();
        if (afCribAttachment.getCribDate() != null) {
            this.cribDateStr = CalendarUtil.getDefaultFormattedDateOnly(afCribAttachment.getCribDate());
        }
        if (afCribAttachment.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(afCribAttachment.getCreatedDate());
        }
        if (afCribAttachment.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(afCribAttachment.getDocStorage(), loadDoc);
        }
        this.cribStatus = afCribAttachment.getCribStatus();
        this.remark = afCribAttachment.getRemark();
        this.status = afCribAttachment.getStatus();
        this.createdBy = afCribAttachment.getCreatedBy();
        this.uploadedDivCode = afCribAttachment.getUploadedDivCode();
        this.uploadedUserDisplayName = afCribAttachment.getUploadedUserDisplayName();
    }

    public Integer getCribAttachmentID() {
        return cribAttachmentID;
    }

    public void setCribAttachmentID(Integer cribAttachmentID) {
        this.cribAttachmentID = cribAttachmentID;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public Integer getBasicInformationID() {
        return basicInformationID;
    }

    public void setBasicInformationID(Integer basicInformationID) {
        this.basicInformationID = basicInformationID;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public String getCribDateStr() {
        return cribDateStr;
    }

    public void setCribDateStr(String cribDateStr) {
        this.cribDateStr = cribDateStr;
    }

    public DomainConstants.CribStatusType getCribStatus() {
        return cribStatus;
    }

    public void setCribStatus(DomainConstants.CribStatusType cribStatus) {
        this.cribStatus = cribStatus;
    }

    public DocStorageDTO getDocStorageDTO() {
        return docStorageDTO;
    }

    public void setDocStorageDTO(DocStorageDTO docStorageDTO) {
        this.docStorageDTO = docStorageDTO;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getUploadedUserDisplayName() {
        return uploadedUserDisplayName;
    }

    public void setUploadedUserDisplayName(String uploadedUserDisplayName) {
        this.uploadedUserDisplayName = uploadedUserDisplayName;
    }

    public String getUploadedDivCode() {
        return uploadedDivCode;
    }

    public void setUploadedDivCode(String uploadedDivCode) {
        this.uploadedDivCode = uploadedDivCode;
    }

    @Override
    public String toString() {
        return "AFCribAttachmentDTO{" +
                "cribAttachmentID=" + cribAttachmentID +
                ", applicationFormID=" + applicationFormID +
                ", supportingDocID=" + supportingDocID +
                ", basicInformationID=" + basicInformationID +
                ", identificationType='" + identificationType + '\'' +
                ", identificationNo='" + identificationNo + '\'' +
                ", cribDateStr='" + cribDateStr + '\'' +
                ", cribStatus=" + cribStatus +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", uploadedUserDisplayName='" + uploadedUserDisplayName + '\'' +
                ", uploadedDivCode='" + uploadedDivCode + '\'' +
                '}';
    }
}
