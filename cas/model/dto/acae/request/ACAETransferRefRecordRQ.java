package com.itechro.cas.model.dto.acae.request;

import lombok.Data;

import java.util.Date;

@Data
public class ACAETransferRefRecordRQ {

    String solId;
    Date fromDate;
    Date toDate;
}
