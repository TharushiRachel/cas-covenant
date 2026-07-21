package com.itechro.cas.model.dto.applicationform.esg;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EnvironmentalRiskDTO {

    private Integer riskCategoryId;

    private Integer parentId;

    private String description;

    private String score;

    private String type;

    private List<EnvironmentalRiskDTO> children = new ArrayList<>();

}
