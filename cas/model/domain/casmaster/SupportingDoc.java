package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_SUPPORTING_DOC")
public class SupportingDoc extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SUPPORTING_DOC")
    @SequenceGenerator(name = "SEQ_T_SUPPORTING_DOC", sequenceName = "SEQ_T_SUPPORTING_DOC", allocationSize = 1)
    @Column(name = "SUPPORTING_DOC_ID")
    private Integer supportingDocID;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SUPPORT_DOCUMENT_TYPE")
    private String supportDocumentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportDocumentType() {
        return supportDocumentType;
    }

    public void setSupportDocumentType(String supportDocumentType) {
        this.supportDocumentType = supportDocumentType;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }


}
