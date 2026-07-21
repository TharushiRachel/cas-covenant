package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExportDcBillsTurnover implements Serializable {


    private String partyName;
    private String purFlg;
    private String crncyTag;
    private String partyCode;
    private String billCrncyCode;
    private String billAmt;
    private String convertedAmt;
    private String billId;
    private String solId;
    private String acctMgrSolId;
    private String startDate;
    private String endDate;
    private String empName;
    private String underLcFlg;
    private String foracid;
}
