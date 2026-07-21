package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportOutwardTurnOverRQ implements Serializable {

    private String reqId;
    private String forAcId;
    private String startDate;
    private String endDate;
}
