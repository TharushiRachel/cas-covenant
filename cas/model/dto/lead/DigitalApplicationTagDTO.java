package com.itechro.cas.model.dto.lead;

import com.itechro.cas.model.domain.lead.DigitalApplicationTag;
import lombok.Data;

import java.util.Date;

@Data
public class DigitalApplicationTagDTO {
    private Integer tagId;

    private Integer leadID;

    private String tag;

    private String tagValue;

    private String fieldType;

    private String savedBy;

    private Date savedDate;

    public DigitalApplicationTagDTO() {

    }
    public DigitalApplicationTagDTO(DigitalApplicationTag digitalApplicationTag){
        this.tagId = digitalApplicationTag.getTagId();
        this.leadID = digitalApplicationTag.getLeadId();
        this.tag = digitalApplicationTag.getTag();
        this.tagValue = digitalApplicationTag.getTagValue();
        this.fieldType = digitalApplicationTag.getFieldType();
        this.savedBy = digitalApplicationTag.getSavedBy();
        this.savedDate = digitalApplicationTag.getSavedDate();
    }
}
