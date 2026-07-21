package com.itechro.cas.model.dto.finacle.request;

import lombok.Data;

import java.util.List;

@Data
public class ExportTurnoverSaveRQ {

    private List<ExportTurnoverDataRQ> exportTurnOverData;
    private Integer facilityPaperId;
    private String cusId;

}
