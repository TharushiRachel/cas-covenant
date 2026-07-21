package com.itechro.cas.model.dto.acae.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;

@Data
public class ACAERangeInquiryRQ {

    @JsonProperty("ReqId")
    private String ReqId;

    @JsonProperty("accno")
    private String accno;

    @JsonProperty("fromDate")
    private String fromDate;

    @JsonProperty("toDate")
    private String toDate;

    @JsonProperty("lowerAmt")
    private Double lowerAmt;

    @JsonProperty("higherAmt")
    private Double higherAmt;

    @JsonProperty("beginChq")
    private Double beginChq;

    @JsonProperty("endChq")
    private Double endChq;

    @JsonProperty("numRec")
    private Double numRec;

    @JsonProperty("partTranType")
    private String partTranType;

    @JsonProperty("ccyCode")
    private String ccyCode;

    @JsonProperty("solId")
    private String solId;

    @JsonProperty("sortOrder")
    private String sortOrder;
}
