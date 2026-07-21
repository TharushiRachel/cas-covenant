package com.itechro.cas.model.domain.facilitypaper;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "T_SECURITY_DOCUMENT_TAG_DATA")
@Audited
public class FPSecurityDocumentTagData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SECURITY_DOCUMENT_TAG_DATA")
    @SequenceGenerator(name = "SEQ_T_SECURITY_DOCUMENT_TAG_DATA", sequenceName = "SEQ_T_SECURITY_DOCUMENT_TAG_DATA", allocationSize = 1)
    @Column(name = "TAG_ID")
    private Integer tagID;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECURITY_DOCUMENT_ID")
    private FPSecurityDocument fpSecurityDocument;

   /* @Column(name = "SECURITY_DOCUMENT_ID")
    private Integer securityDocumentID;*/

    @Column(name = "TAG_ORDER")
    private Integer tagOrder;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "TAG_VALUE")
    private String tagValue;

    @Column(name = "TAG_TYPE")
    private String tagType;

    @Column(name = "FIELD_TYPE")
    private String fieldType;



    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }


    public Integer getTagID() {
        return tagID;
    }

    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /*  public Integer getSecurityDocumentID() {
        return securityDocumentID;
    }

    public void setSecurityDocumentID(Integer securityDocumentID) {
        this.securityDocumentID = securityDocumentID;
    }*/

    public FPSecurityDocument getFpSecurityDocument() {
        return fpSecurityDocument;
    }

    public void setFpSecurityDocument(FPSecurityDocument fpSecurityDocument) {
        this.fpSecurityDocument = fpSecurityDocument;
    }

    public Integer getTagOrder() {
        return tagOrder;
    }

    public void setTagOrder(Integer tagOrder) {
        this.tagOrder = tagOrder;
    }
}

