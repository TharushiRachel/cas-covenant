package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.storage.DocStorage;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_DOCUMENT")
public class AFDocument extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_DOCUMENT")
    @SequenceGenerator(name = "SEQ_T_AF_DOCUMENT", sequenceName = "SEQ_T_AF_DOCUMENT", allocationSize = 1)
    @Column(name = "AF_DOCUMENT_ID")
    private Integer afDocumentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPORT_DOC_ID")
    private SupportingDoc supportingDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    private DocStorage docStorage;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "UPLOADED_USER_DISPLAY_NAME")
    private String uploadedUserDisplayName;

    @Column(name = "UPLOADED_SOL_ID")
    private String uploadedDivCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getAfDocumentID() {
        return afDocumentID;
    }

    public void setAfDocumentID(Integer afDocumentID) {
        this.afDocumentID = afDocumentID;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }

    public SupportingDoc getSupportingDoc() {
        return supportingDoc;
    }

    public void setSupportingDoc(SupportingDoc supportingDoc) {
        this.supportingDoc = supportingDoc;
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
