package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class AccCollateralData implements Serializable {

    @JsonProperty("secu_srl_num")
    private Object secu_srl_num;

    @JsonProperty("secu_code")
    private Object secu_code;

    @JsonProperty("secu_desc")
    private Object secu_desc;

    @JsonProperty("secu_type_ind")
    private Object secu_type_ind;

    @JsonProperty("lien_acid")
    private Object lien_acid;

    @JsonProperty("secu_value")
    private Object secu_value;

    @JsonProperty("last_valuation_date")
    private Object last_valuation_date;

    @JsonProperty("property_doc_num")
    private Object property_doc_num;

    @JsonProperty("forced_sale_value")
    private Object forced_sale_value;

    @JsonProperty("invoice_value")
    private Object invoice_value;

    @JsonProperty("market_value")
    private Object market_value;

    @JsonProperty("approved_fsv")
    private Object approved_fsv;

    @JsonProperty("apportioned_value")
    private Object apportioned_value;

    @JsonProperty("cust_id")
    private Object cust_id;

    @JsonProperty("full_benefit_flg")
    private Object full_benefit_flg;

    @JsonProperty("chrg_code")
    private Object chrg_code;
}
