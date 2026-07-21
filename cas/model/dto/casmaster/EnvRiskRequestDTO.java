package com.itechro.cas.model.dto.casmaster;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EnvRiskRequestDTO implements Serializable {

    private Integer riskCategoryId;

    private Integer parentCategoryId;

    private Integer parentId;

    private String description;

    private String score;

    private String type;

    private String status;

    private String approvedStatus;
}
