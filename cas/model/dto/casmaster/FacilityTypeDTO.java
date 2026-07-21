package com.itechro.cas.model.dto.casmaster;

import lombok.Data;

import java.util.List;

@Data
public class FacilityTypeDTO {

    private Integer facilityTypeID;

    private String facilityTypeName;

    private String description;

    private List<FacilityTemplateDTO> facilityTemplates;
}
