package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportDCTurnover implements Serializable {


    private String chrgRefCode;
    private String eventDate;
    private String solId;
    private String issuPartyCode;
    private String applicantName;
    private String eventAmount;
    private String eventType;
    private String crncyCode;
    private String marginCurrancyC;
    private String marginCurrancyL;
    private String rate;
    private String foracid;
    private String empName;
    private String dcRefNum;
}
