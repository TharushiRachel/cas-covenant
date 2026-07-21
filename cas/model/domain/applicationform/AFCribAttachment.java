package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.storage.DocStorage;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_AF_CRIB_ATTACHMENT")
public class AFCribAttachment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_CRIB_ATTACHMENT")
    @SequenceGenerator(name = "SEQ_T_AF_CRIB_ATTACHMENT", sequenceName = "SEQ_T_AF_CRIB_ATTACHMENT", allocationSize = 1)
    @Column(name = "CRIB_ATTACHMENT_ID")
    private Integer cribAttachmentID;

    @Column(name = "APPLICATION_FORM_ID")
    private Integer applicationFormID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASIC_INFORMATION_ID")
    private BasicInformation basicInformation;

    @Column(name = "IDENTIFICATION_TYPE")
    private String identificationType;

    @Column(name = "IDENTIFICATION_NO")
    private String identificationNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRIB_DATE")
    private Date cribDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "CRIB_STATUS")
    private DomainConstants.CribStatusType cribStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    private DocStorage docStorage;

    @Column(name = "SUPPORTING_DOC_ID")
    private Integer supportingDocID;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "UPLOADED_USER_DISPLAY_NAME")
    private String uploadedUserDisplayName;

    @Column(name = "UPLOADED_SOL_ID")
    private String uploadedDivCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

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

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public Date getCribDate() {
        return cribDate;
    }

    public void setCribDate(Date cribDate) {
        this.cribDate = cribDate;
    }

    public DomainConstants.CribStatusType getCribStatus() {
        return cribStatus;
    }

    public void setCribStatus(DomainConstants.CribStatusType cribStatus) {
        this.cribStatus = cribStatus;
    }

    public DocStorage getDocStorage() {
        return docStorage;
    }

    public void setDocStorage(DocStorage docStorage) {
        this.docStorage = docStorage;
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
}
