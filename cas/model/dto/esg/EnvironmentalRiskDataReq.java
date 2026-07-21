package com.itechro.cas.model.dto.esg;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EnvironmentalRiskDataReq implements Serializable {

    private Integer applicationFormId;

    private Integer facilityPaperId;

    private List<EnvironmentalRiskDataDTO> categories;
}
