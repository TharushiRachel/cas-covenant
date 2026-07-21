package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportingDateRange implements Serializable {

    private String requestId;

    private String startDate;

    private String endDate;

    private String customerId;
}
