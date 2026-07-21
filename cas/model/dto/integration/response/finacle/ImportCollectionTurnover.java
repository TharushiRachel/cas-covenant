package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportCollectionTurnover implements Serializable {


    private String partyName;
    private String partyCode;
    private String billCRNCYCode;
    private String billAmt;
    private String convertedAmt;
    private String billId;
    private String solId;
    private String accMgrSolId;
    private String startDate;
    private String endDate;
    private String empName;
    private String foracid;
}
