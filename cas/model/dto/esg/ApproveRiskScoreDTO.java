package com.itechro.cas.model.dto.esg;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApproveRiskScoreDTO implements Serializable {

    private Integer applicationFormId;

    private Integer facilityPaperId;

    private String score;
}
