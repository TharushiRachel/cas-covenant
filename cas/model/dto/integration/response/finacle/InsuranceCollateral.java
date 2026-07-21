package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.util.List;

@Data
public class InsuranceCollateral {

    private String secu_srl_num;
    private String secu_type_ind;
    private String secu_code;
    private String secu_desc;
    private String valuation_expiryDate;
    private List<FinacleInsuranceDetails> insuranceDetails;
}
