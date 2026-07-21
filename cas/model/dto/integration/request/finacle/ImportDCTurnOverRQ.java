package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportDCTurnOverRQ implements Serializable {

    private String reqId;
    private String foracId;
    private String startDate;
    private String endDate;
}
