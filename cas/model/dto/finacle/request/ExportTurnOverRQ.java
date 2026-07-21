package com.itechro.cas.model.dto.finacle.request;

import lombok.Data;

@Data
public class ExportTurnOverRQ {
    private String accountId;
    private String startDate;
    private String endDate;
}
