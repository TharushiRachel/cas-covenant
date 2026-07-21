package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

@Data
public class VolumeSummary {
    private String stDate;
    private String enDate;
    private Object cnyCode;
    private String volume;
    private String createdDate;
}
