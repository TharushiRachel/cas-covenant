package com.itechro.cas.model.domain.facilitypaper;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_FACILITY_SECURITY_DOCUMENT")
@Audited
public class FPSecurityDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_SECURITY_DOCUMENT")
    @SequenceGenerator(name = "SEQ_T_FACILITY_SECURITY_DOCUMENT", sequenceName = "SEQ_T_FACILITY_SECURITY_DOCUMENT", allocationSize = 1)
    @Column(name = "SECURITY_DOCUMENT_ID")
    private Integer securityDocumentID;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ELEMENT_ID")
    @NotAudited
    private FPDocumentElement fpDocumentElement;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperID;

    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private Integer creditFacilityTemplateID;

    @Column(name = "CREDIT_FACILITY_NAME")
    private String creditFacilityName;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    @Column(name = "DOCUMENT_CONTENT")
    private String documentContent;

    @Column(name = "SAVED_BY")
    private String savedBy;

    @Column(name = "PRINTED_BY")
    private String printedBy;

    @Column(name = "SAVED_BY_DIV")
    private String savedByDiv;

    @Column(name = "AUTH_BY_DIV")
    private String authByDiv;

    @Column(name = "SAVED_DATE")
    private Date savedDate;

    @Column(name = "PRINTED_DATE")
    private Date printedDate;

    @Column(name = "RETURN_COMMENT")
    private String returnComment;

    @Column(name = "AUTH_BY")
    private String authBy;

    @Column(name = "AUTH_DATE")
    private Date authDate;

    @Column(name = "PRINTED_BY_DIV")
    private String printedByDiv;

    @Column(name = "SAVED_BY_DISPLAY_NAME")
    private String savedByDisplayName;

    @Column(name = "AUTH_BY_DISPLAY_NAME")
    private String authByDisplayName;

    @Column(name = "PRINTED_BY_DISPLAY_NAME")
    private String printedByDisplayName;

    @Column(name = "DOCUMENT_STATUS")
    private String documentStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "fpSecurityDocument")
    @Audited
    private Set<FPSecurityDocumentTagData> fpSecurityDocumentTagDataSet;

    public Integer getSecurityDocumentID() {
        return securityDocumentID;
    }

    public void setSecurityDocumentID(Integer securityDocumentID) {
        this.securityDocumentID = securityDocumentID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    public String getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(String savedBy) {
        this.savedBy = savedBy;
    }

    public Date getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(Date savedDate) {
        this.savedDate = savedDate;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public FPDocumentElement getFpDocumentElement() {
        return fpDocumentElement;
    }

    public void setFpDocumentElement(FPDocumentElement fpDocumentElement) {
        this.fpDocumentElement = fpDocumentElement;
    }

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
    }

    public Set<FPSecurityDocumentTagData> getFpSecurityDocumentTagDataSet() {
        if (fpSecurityDocumentTagDataSet == null) {
            fpSecurityDocumentTagDataSet = new HashSet<>();
        }
        return fpSecurityDocumentTagDataSet;
    }

    public void setFpSecurityDocumentTagDataSet(Set<FPSecurityDocumentTagData> fpSecurityDocumentTagDataSet) {
        this.fpSecurityDocumentTagDataSet = fpSecurityDocumentTagDataSet;
    }

    public void addFpSecurityDocumentTagData(FPSecurityDocumentTagData fpSecurityDocumentTagData) {
        fpSecurityDocumentTagData.setFpSecurityDocument(this);
        this.getFpSecurityDocumentTagDataSet().add(fpSecurityDocumentTagData);
    }

    public FPSecurityDocumentTagData getFpSecurityDocumentTagDataByID(Integer tagID) {
        return this.getFpSecurityDocumentTagDataSet().stream().filter(fpSecurityDocumentTagData ->
                        tagID.equals(fpSecurityDocumentTagData.getTagID()))
                .findFirst().orElse(null);
    }

    public String getReturnComment() {
        return returnComment;
    }

    public void setReturnComment(String returnComment) {
        this.returnComment = returnComment;
    }

    public String getSavedByDiv() {
        return savedByDiv;
    }

    public void setSavedByDiv(String savedByDiv) {
        this.savedByDiv = savedByDiv;
    }

    public String getAuthByDiv() {
        return authByDiv;
    }

    public void setAuthByDiv(String authByDiv) {
        this.authByDiv = authByDiv;
    }

    public String getPrintedBy() {
        return printedBy;
    }

    public void setPrintedBy(String printedBy) {
        this.printedBy = printedBy;
    }

    public Date getPrintedDate() {
        return printedDate;
    }

    public void setPrintedDate(Date printedDate) {
        this.printedDate = printedDate;
    }

    public String getPrintedByDiv() {
        return printedByDiv;
    }

    public void setPrintedByDiv(String printedByDiv) {
        this.printedByDiv = printedByDiv;
    }

    public String getSavedByDisplayName() {
        return savedByDisplayName;
    }

    public void setSavedByDisplayName(String savedByDisplayName) {
        this.savedByDisplayName = savedByDisplayName;
    }

    public String getAuthByDisplayName() {
        return authByDisplayName;
    }

    public void setAuthByDisplayName(String authByDisplayName) {
        this.authByDisplayName = authByDisplayName;
    }

    public String getPrintedByDisplayName() {
        return printedByDisplayName;
    }

    public void setPrintedByDisplayName(String printedByDisplayName) {
        this.printedByDisplayName = printedByDisplayName;
    }
}
