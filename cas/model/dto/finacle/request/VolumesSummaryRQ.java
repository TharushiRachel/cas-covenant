package com.itechro.cas.model.dto.finacle.request;

import lombok.Data;

import java.util.Date;

@Data
public class VolumesSummaryRQ {

    private String year;
    private String cnyCode;
    private String totalVolume;
    private String createdDate;
}

