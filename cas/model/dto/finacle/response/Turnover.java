package com.itechro.cas.model.dto.finacle.response;

public interface Turnover {
     String getTurnOverType();
     String getBillCurrencyCode();
     double getBillAmount();
     double getConvertedAmount();
}
