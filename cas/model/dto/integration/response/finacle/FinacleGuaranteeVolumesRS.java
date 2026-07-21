package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FinacleGuaranteeVolumesRS implements Serializable {

    private String Status;
    private String refId;
    private List<VolumeSummary> bgmVolumeSummary;


}
