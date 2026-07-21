package com.itechro.cas.model.dto.lead;

import lombok.Data;

import java.util.Date;

@Data
public class FPSecurityDocumentTagDTO {

    private Integer tagId;

    private Integer facilityPaperId;

    private String tag;

    private String tagValue;

    private String fieldType;

    private String savedBy;

    private Date savedDate;

    public FPSecurityDocumentTagDTO() {
    }

    public FPSecurityDocumentTagDTO(FPSecurityDocumentTagDTO fpSecurityDocumentTag) {
        this.tagId = fpSecurityDocumentTag.getTagId();
        this.facilityPaperId = fpSecurityDocumentTag.getFacilityPaperId();
        this.tag = fpSecurityDocumentTag.getTag();
        this.tagValue = fpSecurityDocumentTag.getTagValue();
        this.fieldType = fpSecurityDocumentTag.getFieldType();
        this.savedBy = fpSecurityDocumentTag.getSavedBy();
        this.savedDate = fpSecurityDocumentTag.getSavedDate();
    }
}
