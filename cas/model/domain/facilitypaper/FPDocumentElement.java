package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_FACILITY_DOCUMENT_ELEMENT")
public class FPDocumentElement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_DOCUMENT_ELEMENT")
    @SequenceGenerator(name = "SEQ_T_FACILITY_DOCUMENT_ELEMENT", sequenceName = "SEQ_T_FACILITY_DOCUMENT_ELEMENT", allocationSize = 1)
    @Column(name = "ELEMENT_ID")
    private Integer elementID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @Column(name = "CREDIT_FACILITY_NAME")
    private String creditFacilityName;

    @Column(name = "PARENT_ID")
    private Integer parentID;

    @Column(name = "ELEMENT_NAME")
    private String elementName;

    @Column(name = "ELEMENT_TYPE")
    private String elementType;

    @Column(name = "FACILITY_TYPE_ID")
    private Integer facilityTypeID;

    @Column(name = "IS_NEW")
    private String isNew;

    @Column(name = "DOCUMENT_CONTENT")
    private String documentContent;


    @Column(name = "KEY")
    private String key;

    @Column(name = "STATUS")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "fpDocumentElement")
    @NotAudited
    private Set<FPSecurityDocument> fpSecurityDocument;

    public Integer getElementID() {
        return elementID;
    }

    public void setElementID(Integer elementID) {
        this.elementID = elementID;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public Integer getFacilityTypeID() {
        return facilityTypeID;
    }

    public void setFacilityTypeID(Integer facilityTypeID) {
        this.facilityTypeID = facilityTypeID;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public CreditFacilityTemplate getCreditFacilityTemplate() {
        return creditFacilityTemplate;
    }

    public void setCreditFacilityTemplate(CreditFacilityTemplate creditFacilityTemplate) {
        this.creditFacilityTemplate = creditFacilityTemplate;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<FPSecurityDocument> getFpSecurityDocument() {
        if (fpSecurityDocument == null) {
            fpSecurityDocument = new HashSet<>();
        }
        return fpSecurityDocument;
    }

    public void setFpSecurityDocument(Set<FPSecurityDocument> fpSecurityDocument) {
        this.fpSecurityDocument = fpSecurityDocument;
    }

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
    }
}

