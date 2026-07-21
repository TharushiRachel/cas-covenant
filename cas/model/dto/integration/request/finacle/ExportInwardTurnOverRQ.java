package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportInwardTurnOverRQ implements Serializable {

    private String requestId;
    private String foracId;
    private String stDate;
    private String enDate;
}
