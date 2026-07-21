package com.itechro.cas.model.dto.finacle.request;

import lombok.Data;

@Data
public class ImportTurnOverRQ {
    private String accountId;
    private String startDate;
    private String endDate;
}
