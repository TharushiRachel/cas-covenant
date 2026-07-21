package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPSecurityDocumentTagData;

import java.io.Serializable;

public class FPSecurityDocumentTagDataDTO implements Serializable {

   private Integer securityDocumentID;

    private Integer tagID;

    private Integer tagOrder;

    private String tag;


    private String tagValue;


    private String tagType;

    private String fieldType;



    public FPSecurityDocumentTagDataDTO() {
    }


    public FPSecurityDocumentTagDataDTO(FPSecurityDocumentTagData fpSecurityDocumentTagData) {
        this.tagID = fpSecurityDocumentTagData.getTagID();
        this.securityDocumentID = fpSecurityDocumentTagData.getFpSecurityDocument().getSecurityDocumentID();
        this.tag = fpSecurityDocumentTagData.getTag();
        this.tagOrder = fpSecurityDocumentTagData.getTagOrder();
        this.fieldType = fpSecurityDocumentTagData.getFieldType();
       // this.securityDocumentID = fpSecurityDocumentTagData.getSecurityDocumentID();
        this.tagValue = fpSecurityDocumentTagData.getTagValue();
        this.tagType = fpSecurityDocumentTagData.getTagType();
    }

    public Integer getSecurityDocumentID() {
        return securityDocumentID;
    }

    public void setSecurityDocumentID(Integer securityDocumentID) {
        this.securityDocumentID = securityDocumentID;
    }

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

    public Integer getTagOrder() {
        return tagOrder;
    }

    public void setTagOrder(Integer tagOrder) {
        this.tagOrder = tagOrder;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return "FPSecurityDocumentTagDataDTO{" +
                "securityDocumentID=" + securityDocumentID +
                ", tagID=" + tagID +
                ", tagOrder=" + tagOrder +
                ", tag='" + tag + '\'' +
                ", tagValue='" + tagValue + '\'' +
                ", tagType='" + tagType + '\'' +
                ", fieldType='" + fieldType + '\'' +
                '}';
    }
}
