package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.storage.DocStorage;

import javax.persistence.*;

@Entity
@Table(name = "T_FACILITY_DOCUMENT")
public class FacilityDocument extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_DOCUMENT")
    @SequenceGenerator(name = "SEQ_T_FACILITY_DOCUMENT", sequenceName = "SEQ_T_FACILITY_DOCUMENT", allocationSize = 1)
    @Column(name = "FACILITY_DOCUMENT_ID")
    private Integer facilityDocumentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Column(name = "CFT_SUPPORTING_DOC_ID")
    private Integer cftSupportDocID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPORT_DOCUMENT_ID")
    private SupportingDoc supportingDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    private DocStorage docStorage;

    @Enumerated(EnumType.STRING)
    @Column(name = "MANDATORY")
    private AppsConstants.YesNo mandatory;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "REMARK")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFacilityDocumentID() {
        return facilityDocumentID;
    }

    public void setFacilityDocumentID(Integer facilityDocumentID) {
        this.facilityDocumentID = facilityDocumentID;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Integer getCftSupportDocID() {
        return cftSupportDocID;
    }

    public void setCftSupportDocID(Integer cftSupportDocID) {
        this.cftSupportDocID = cftSupportDocID;
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

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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
}
