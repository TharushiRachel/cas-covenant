package com.itechro.cas.model.dto.acae.request;

import lombok.Data;

@Data
public class ACAEOutstandingRQ {

    private String accno;
    private String cumm;
    private String nic;
    private String valType;
    private String aduser;
    private String userId;
    private String refId;
    private String fromdate;
    private String todate;
}
