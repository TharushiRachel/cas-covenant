package com.itechro.cas.model.dto.finacle.request;

import com.itechro.cas.model.dto.integration.response.finacle.VolumeSummary;
import lombok.Data;

import java.util.List;
@Data
public class GuaranteeVolumeSaveRQ {

    private List<VolumesSummaryRQ> bgmVolumeSummary;
    private Integer facilityPaperId;
    private String cusId;

}
