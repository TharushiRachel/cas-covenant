package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
//import com.itechro.cas.model.domain.casmaster.GlobalSupportingDoc;
import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.domain.storage.DocStorage;

import javax.persistence.*;

@Entity
@Table(name = "T_FP_CREDIT_RISK_DOCUMENT")
public class FPCreditRiskDocument extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_CREDIT_RISK_DOCUMENT")
    @SequenceGenerator(name = "SEQ_T_FP_CREDIT_RISK_DOCUMENT", sequenceName = "SEQ_T_FP_CREDIT_RISK_DOCUMENT", allocationSize = 1)
    @Column(name = "FP_CREDIT_RISK_DOCUMENT_ID")
    private Integer fpCreditRiskDocumentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "SUPPORT_DOC_ID")
//    private GlobalSupportingDoc supportingDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    private DocStorage docStorage;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "UPLOADED_USER_DISPLAY_NAME")
    private String uploadedUserDisplayName;

    @Column(name = "UPLOADED_SOL_ID")
    private String uploadedDivCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_LOCKED")
    private AppsConstants.YesNo isLocked;

    public Integer getFpCreditRiskDocumentID() {
        return fpCreditRiskDocumentID;
    }

    public void setFpCreditRiskDocumentID(Integer fpCreditRiskDocumentID) {
        this.fpCreditRiskDocumentID = fpCreditRiskDocumentID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

//    public GlobalSupportingDoc getSupportingDoc() {
//        return supportingDoc;
//    }
//
//    public void setSupportingDoc(GlobalSupportingDoc supportingDoc) {
//        this.supportingDoc = supportingDoc;
//    }

    public DocStorage getDocStorage() {
        return docStorage;
    }

    public void setDocStorage(DocStorage docStorage) {
        this.docStorage = docStorage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public AppsConstants.YesNo getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(AppsConstants.YesNo isLocked) {
        this.isLocked = isLocked;
    }
}
