package com.itechro.cas.model.dto.finacle.request;

import lombok.Data;

import java.util.List;

@Data
public class ImportTurnoverSaveRQ {

    private List<ImportTurnoverDataRQ> importTurnOverData;
    private Integer facilityPaperId;
    private String cusId;

}
