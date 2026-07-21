package com.itechro.cas.model.dto.finacle.request;

import com.itechro.cas.model.dto.finacle.CompReportingData;
import lombok.Data;

import java.util.List;

@Data
public class CompReportingListReq {

    private Integer boardCreditCommitteePaperID;

    private List<CompReportingData> compReportingData;
}
