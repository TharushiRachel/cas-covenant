package com.itechro.cas.model.dto.finacle.request;

import com.itechro.cas.model.dto.finacle.response.InsuranceDetails;
import lombok.Data;

import java.util.List;

@Data
public class SaveInsuranceValuationRQ {

    private String collateralType;
    private List<SaveInsuranceDetailsRQ> insuranceDetails;
    private String insuranceValuationExpireDates;

}
