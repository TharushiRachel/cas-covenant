package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportDCBillsTurnOverRQ implements Serializable {

    private String reqId;
    private String accId;
    private String stDate;
    private String enDate;
}
