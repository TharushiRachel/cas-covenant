package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportOutwardTurnover implements Serializable {


    private String ttRef;
    private String ttCrncy;
    private String ttAmt;
    private String usrRate;
    private String crncyRate;
    private String solDesc;
    private String rm;
    private String solId;
    private String partyName;
    private String cifId;
    private String lkrAmount;
    private String usdEqvulant;
}
