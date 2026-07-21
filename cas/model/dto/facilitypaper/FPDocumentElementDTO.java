package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPDocumentElement;
import com.itechro.cas.model.domain.facilitypaper.FPSecurityDocument;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FPDocumentElementDTO implements Serializable {

    private Integer elementID;

    private Integer parentID;

    private String elementName;

    private String elementType;

    private Integer creditFacilityTemplateID;

    private String  creditFacilityName;

    private Integer facilityTypeID;

    private String isNew;

    private String documentContent;

    private String key;

    private List<FPSecurityDocumentDTO> fpSecurityDocumentDTOList;


    public FPDocumentElementDTO() {
    }


    public FPDocumentElementDTO(FPDocumentElement fpDocumentElement) {
        this.elementID = fpDocumentElement.getElementID();
        this.parentID = fpDocumentElement.getParentID();
        this.elementName = fpDocumentElement.getElementName();
        this.elementType = fpDocumentElement.getElementType();
        this.creditFacilityTemplateID = fpDocumentElement.getCreditFacilityTemplate().getCreditFacilityTemplateID();
        this.creditFacilityName = fpDocumentElement.getCreditFacilityTemplate().getCreditFacilityName();
        this.facilityTypeID = fpDocumentElement.getFacilityTypeID();
        this.isNew = fpDocumentElement.getIsNew();
        this.documentContent = fpDocumentElement.getDocumentContent();
        this.key = fpDocumentElement.getKey();

        for (FPSecurityDocument fpSecurityDocument : fpDocumentElement.getFpSecurityDocument()) {
            //    if (fpSecurityDocument.getDocumentStatus() != "DELETE") {
                    this.getFpSecurityDocumentDTOList().add(new FPSecurityDocumentDTO(fpSecurityDocument));
             //   }
        }

    }

    public List<FPSecurityDocumentDTO> getFpSecurityDocumentDTOList() {
        if (fpSecurityDocumentDTOList == null) {
            fpSecurityDocumentDTOList = new ArrayList<>();
        }
        return fpSecurityDocumentDTOList;
    }

    public void setFpSecurityDocumentDTOList(List<FPSecurityDocumentDTO> fpSecurityDocumentDTOList) {
        this.fpSecurityDocumentDTOList = fpSecurityDocumentDTOList;
    }

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

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
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

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "FPDocumentElementDTO{" +
                "elementID=" + elementID +
                ", parentID=" + parentID +
                ", elementName='" + elementName + '\'' +
                ", elementType='" + elementType + '\'' +
                ", creditFacilityTemplateID=" + creditFacilityTemplateID +
                ", creditFacilityName=" + creditFacilityName +
                ", facilityTypeID=" + facilityTypeID +
                ", isNew='" + isNew + '\'' +
                ", documentContent='" + documentContent + '\'' +
                ", key='" + key + '\'' +
                ", fpSecurityDocumentDTOList=" + fpSecurityDocumentDTOList +
                '}';
    }
}
