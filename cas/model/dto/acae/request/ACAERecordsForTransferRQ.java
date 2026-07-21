package com.itechro.cas.model.dto.acae.request;

import lombok.Data;

import java.util.Date;
@Data
public class ACAERecordsForTransferRQ {

    private String solId;
    private String fromDate;
    private String toDate;

}
