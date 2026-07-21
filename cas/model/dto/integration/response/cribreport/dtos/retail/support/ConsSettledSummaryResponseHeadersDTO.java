package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail.support;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsSettledSummaryResponse;

import java.io.Serializable;

public class ConsSettledSummaryResponseHeadersDTO implements Serializable {

    private String RP;
    private String STYR1;
    private String STYR2;
    private String STYR3;
    private String STYR4;
    private String STYR5;

    public ConsSettledSummaryResponseHeadersDTO() {
    }


    public ConsSettledSummaryResponseHeadersDTO(ConsSettledSummaryResponse consSettledSummaryResponse) {
        this.RP = consSettledSummaryResponse.getRp();
        this.STYR1 = consSettledSummaryResponse.getStyr1();
        this.STYR2 = consSettledSummaryResponse.getStyr2();
        this.STYR3 = consSettledSummaryResponse.getStyr3();
        this.STYR4 = consSettledSummaryResponse.getStyr4();
        this.STYR5 = consSettledSummaryResponse.getStyr5();
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

