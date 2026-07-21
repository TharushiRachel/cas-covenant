package com.itechro.cas.model.domain.facilitypaper.facility;

import lombok.Data;

import java.util.Date;

@Data
public class FPUPCTemplateComparisonContentDTO {
    private Integer fpUPCSectionDataId;

    private Integer fpUPCSectionDataHistoryID;

    private String data;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedUserDisplayName;

    private String comment;

    private String status;
}
