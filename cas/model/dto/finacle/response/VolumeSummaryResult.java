package com.itechro.cas.model.dto.finacle.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class VolumeSummaryResult {
    private Object cnyCode;
    private String totalVolume;
    private String year;
    private String createdDate;
}
