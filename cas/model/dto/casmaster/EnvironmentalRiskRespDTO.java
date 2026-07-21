package com.itechro.cas.model.dto.casmaster;

import lombok.Data;

@Data
public class EnvironmentalRiskRespDTO {

    private Integer riskCategoryId;

    private Integer parentId;

    private String description;

    private String score;

    private String type;
}
