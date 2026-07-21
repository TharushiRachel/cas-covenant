package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CompReportingRequest implements Serializable {

    private List<ReportingDateRange> dateRanges;

    private Boolean isInterestRate;

    private ArrayList<String> rateCodeTypes;

    private Float rateFrom;

    private Float rateTo;

    private String sector;

    private String riskRating;

    private String productGroup;

    private String customerId;

    private Boolean isAllSector;

    private Boolean isAllRiskRating;

    private Boolean isAllProductGroup;
}
