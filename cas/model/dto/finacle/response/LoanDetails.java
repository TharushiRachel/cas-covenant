package com.itechro.cas.model.dto.finacle.response;

import lombok.Data;

@Data
public class LoanDetails {
    private String id;
    private String grantedAmount;
    private String outstandingAmount;
    private String purpose;
    private String interestRate;
    private String repayment;
    private String security;
    private String currencyType;
    private String loanType;

}
