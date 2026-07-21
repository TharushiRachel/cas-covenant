package com.itechro.cas.model.dto.customer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerCovenantDetailsReqDTO {
    @JsonProperty("covenant_Code")
    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private String covenant_Due_Date;

//    private String covenant_Due_DateStr;
}
