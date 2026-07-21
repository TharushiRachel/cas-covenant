package com.itechro.cas.model.dto.integration.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author tharushi
 */
@Data
public class LoadBankAccountDetailsRQ {

    @JsonProperty("refId")
    String refId;
    @JsonProperty("AccountNo")
    String accountNo;

    @JsonProperty("BalanceDate")
    String balanceDate;

    @JsonProperty("partTranSRL")
    String partTranSRL;
}
