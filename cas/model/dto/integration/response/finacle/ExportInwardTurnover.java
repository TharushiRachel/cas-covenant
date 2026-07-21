package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportInwardTurnover implements Serializable {


    private String partyName;
    private String cifId;
    private String collectionCrncy;
    private String collectionAmt;
    private String turnoverLkr;
    private String collectionId;
    private String cgmSol;
    private String gamSol;
    private String startDate;
    private String endDate;
    private String empName;
}
