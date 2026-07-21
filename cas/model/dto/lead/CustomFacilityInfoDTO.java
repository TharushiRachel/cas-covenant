package com.itechro.cas.model.dto.lead;


import lombok.Data;

import java.util.Date;

@Data
public class CustomFacilityInfoDTO {
    private Long customFacilityInfoId;
    private Long creditFacilityTemplateId;
    private String customFacilityInfoName;
    private String description;
    private String fieldType;
    private String mandatory;
    private String status;
    private String createdBy;
    private Date createdDate;
    private Date modifiedDate;
    private String modifiedBy;
    private Integer displayOrder;
    private Integer version;
    private String customFacilityInfoCode;

}

