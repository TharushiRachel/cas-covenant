package com.itechro.cas.model.dto.finacle.response;

import com.itechro.cas.model.dto.integration.response.finacle.VolumeSummary;
import lombok.Data;

import java.util.List;

@Data
public class GuaranteeVolumesRS {
    private List<VolumeSummaryResult> bgmVolumeSummary;
}
