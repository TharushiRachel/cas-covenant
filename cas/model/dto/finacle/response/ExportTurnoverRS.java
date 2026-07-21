package com.itechro.cas.model.dto.finacle.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExportTurnoverRS implements Turnover {
    private String year;
    private String billCurrencyCode;
    private Double billAmount =0.0;
    private Double convertedAmount=0.0;
    private String turnOverType;
    private String createdDate;
    private String foracid;


    @Override
    public String getTurnOverType() {
        return turnOverType;
    }

    @Override
    public String getBillCurrencyCode() {
        return billCurrencyCode;
    }

    @Override
    public double getBillAmount() {
        return billAmount;
    }

    @Override
    public double getConvertedAmount() {
        return convertedAmount;
    }
}
