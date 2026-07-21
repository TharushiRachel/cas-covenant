package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ACAEAccountBalanceDetailDTO implements Serializable {
    private double accountBalance;
    private double sanctionLimit;
    private double todAmount;
    private double ualAmount;
    private double postDatedChequeAmount;
    private double debitBalance;
    private double floatAmount;
}
