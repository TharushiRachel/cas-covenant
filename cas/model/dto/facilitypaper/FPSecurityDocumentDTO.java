package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPSecurityDocument;
import com.itechro.cas.model.domain.facilitypaper.FPSecurityDocumentTagData;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FPSecurityDocumentDTO implements Serializable {

    private Integer securityDocumentID;

    private Integer facilityPaperID;

    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private Integer elementID;

    private Integer facilityID;

    private String documentName;

    private String documentContent;

    private String documentStatus;

    private String savedBy;

    private String authBy;

    private String printedBy;

    private String savedDateStr;

    private String printedDateStr;

    private String authDateStr;

    private String savedByDiv;

    private String authByDiv;

    private String printedByDiv;

    private String savedByDisplayName;

    private String authByDisplayName;

    private String printedByDisplayName;

    private String returnComment;

    private List<FPSecurityDocumentTagDataDTO> fpSecurityDocumentTagDataDTOList;


    public FPSecurityDocumentDTO() {
    }

    public FPSecurityDocumentDTO(FPSecurityDocument fpSecurityDocument) {
        this.securityDocumentID = fpSecurityDocument.getSecurityDocumentID();
        this.facilityPaperID = fpSecurityDocument.getFacilityPaperID();
        this.facilityID = fpSecurityDocument.getFacilityID();
        this.savedByDiv = fpSecurityDocument.getSavedByDiv();
        this.authByDiv = fpSecurityDocument.getAuthByDiv();
        this.returnComment = fpSecurityDocument.getReturnComment();
        // this.tagID = fpSecurityDocument.getFpSecurityDocumentTagData().getTagID();
        this.elementID = fpSecurityDocument.getFpDocumentElement().getElementID();
        this.creditFacilityTemplateID = fpSecurityDocument.getCreditFacilityTemplateID();
        this.creditFacilityName = fpSecurityDocument.getCreditFacilityName();
        this.documentName = fpSecurityDocument.getDocumentName();
        this.documentContent = fpSecurityDocument.getDocumentContent();
        this.documentStatus = fpSecurityDocument.getDocumentStatus();
        this.savedBy = fpSecurityDocument.getSavedBy();
        this.authBy = fpSecurityDocument.getAuthBy();
        this.printedBy = fpSecurityDocument.getPrintedBy();
        this.printedByDiv = fpSecurityDocument.getPrintedByDiv();
        this.savedByDisplayName = fpSecurityDocument.getSavedByDisplayName();
        this.authByDisplayName = fpSecurityDocument.getAuthByDisplayName();
        this.printedByDisplayName = fpSecurityDocument.getPrintedByDisplayName();


        if (fpSecurityDocument.getSavedDate() != null) {
            this.savedDateStr = CalendarUtil.getDefaultFormattedDateTime(fpSecurityDocument.getSavedDate());
        }
        if (fpSecurityDocument.getAuthDate() != null) {
            this.authDateStr = CalendarUtil.getDefaultFormattedDateTime(fpSecurityDocument.getAuthDate());
        }
        if (fpSecurityDocument.getPrintedDate() != null) {
            this.printedDateStr = CalendarUtil.getDefaultFormattedDateTime(fpSecurityDocument.getPrintedDate());
        }


       for (FPSecurityDocumentTagData fpSecurityDocumentTagData : fpSecurityDocument.getFpSecurityDocumentTagDataSet()) {
            this.getFpSecurityDocumentTagDataDTOList().add(new FPSecurityDocumentTagDataDTO(fpSecurityDocumentTagData));
        }
    }

    public List<FPSecurityDocumentTagDataDTO> getFpSecurityDocumentTagDataDTOList() {
        if (fpSecurityDocumentTagDataDTOList == null) {
            fpSecurityDocumentTagDataDTOList = new ArrayList<>();
        }
        return fpSecurityDocumentTagDataDTOList;
    }

    public void setFpSecurityDocumentTagDataDTOList(List<FPSecurityDocumentTagDataDTO> fpSecurityDocumentTagDataDTOList) {
        this.fpSecurityDocumentTagDataDTOList = fpSecurityDocumentTagDataDTOList;
    }

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


    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(String savedBy) {
        this.savedBy = savedBy;
    }

    public String getSavedDateStr() {
        return savedDateStr;
    }

    public void setSavedDateStr(String savedDateStr) {
        this.savedDateStr = savedDateStr;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getAuthDateStr() {
        return authDateStr;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public void setAuthDateStr(String authDateStr) {
        this.authDateStr = authDateStr;
    }

    public Integer getElementID() {
        return elementID;
    }

    public void setElementID(Integer elementID) {
        this.elementID = elementID;
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

    public String getPrintedDateStr() {
        return printedDateStr;
    }

    public void setPrintedDateStr(String printedDateStr) {
        this.printedDateStr = printedDateStr;
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

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
    }

    @Override
    public String toString() {
        return "FPSecurityDocumentDTO{" +
                "securityDocumentID=" + securityDocumentID +
                ", facilityPaperID=" + facilityPaperID +
                ", creditFacilityTemplateID=" + creditFacilityTemplateID +
                ", creditFacilityName='" + creditFacilityName + '\'' +
                ", elementID=" + elementID +
                ", facilityID=" + facilityID +
                ", documentName='" + documentName + '\'' +
                ", documentContent='" + documentContent + '\'' +
                ", documentStatus='" + documentStatus + '\'' +
                ", savedBy='" + savedBy + '\'' +
                ", authBy='" + authBy + '\'' +
                ", printedBy='" + printedBy + '\'' +
                ", savedDateStr='" + savedDateStr + '\'' +
                ", printedDateStr='" + printedDateStr + '\'' +
                ", authDateStr='" + authDateStr + '\'' +
                ", savedByDiv='" + savedByDiv + '\'' +
                ", authByDiv='" + authByDiv + '\'' +
                ", printedByDiv='" + printedByDiv + '\'' +
                ", savedByDisplayName='" + savedByDisplayName + '\'' +
                ", authByDisplayName='" + authByDisplayName + '\'' +
                ", printedByDisplayName='" + printedByDisplayName + '\'' +
                ", returnComment='" + returnComment + '\'' +
                ", fpSecurityDocumentTagDataDTOList=" + fpSecurityDocumentTagDataDTOList +
                '}';
    }
}
