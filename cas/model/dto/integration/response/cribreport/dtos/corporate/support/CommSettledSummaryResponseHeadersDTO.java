package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate.support;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialSettledSummaryResponse;

import java.io.Serializable;

public class CommSettledSummaryResponseHeadersDTO implements Serializable {

    private String RP;
    private String STYR1;
    private String STYR2;
    private String STYR3;
    private String STYR4;
    private String STYR5;

    public CommSettledSummaryResponseHeadersDTO() {
    }


    public CommSettledSummaryResponseHeadersDTO(CommercialSettledSummaryResponse commercialSettledSummaryResponse) {
        this.RP = commercialSettledSummaryResponse.getRp();
        this.STYR1 = commercialSettledSummaryResponse.getStyr1();
        this.STYR2 = commercialSettledSummaryResponse.getStyr2();
        this.STYR3 = commercialSettledSummaryResponse.getStyr3();
        this.STYR4 = commercialSettledSummaryResponse.getStyr4();
        this.STYR5 = commercialSettledSummaryResponse.getStyr5();
    }

    public String getRP() {
        return RP;
    }

    public void setRP(String RP) {
        this.RP = RP;
    }

    public String getSTYR1() {
        return STYR1;
    }

    public void setSTYR1(String STYR1) {
        this.STYR1 = STYR1;
    }

    public String getSTYR2() {
        return STYR2;
    }

    public void setSTYR2(String STYR2) {
        this.STYR2 = STYR2;
    }

    public String getSTYR3() {
        return STYR3;
    }

    public void setSTYR3(String STYR3) {
        this.STYR3 = STYR3;
    }

    public String getSTYR4() {
        return STYR4;
    }

    public void setSTYR4(String STYR4) {
        this.STYR4 = STYR4;
    }

    public String getSTYR5() {
        return STYR5;
    }

    public void setSTYR5(String STYR5) {
        this.STYR5 = STYR5;
    }

}
