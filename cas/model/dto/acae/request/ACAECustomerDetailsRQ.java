package com.itechro.cas.model.dto.acae.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ACAECustomerDetailsRQ {

    @JsonProperty("refId")
    String refId;
    @JsonProperty("AccountNo")
    String accountNo;

}
