package com.itechro.cas.model.dto.kalyptosystem.request;

import lombok.Data;

import java.util.List;

@Data
public class KalyptoSaveRQ {

    private String columnName;

    private Integer customerId;

    private Integer facilityId;

    private Integer kalyptoId;

    private Integer periodId;

    private List<KalyptoDataSaveRQ> dataList;
}
