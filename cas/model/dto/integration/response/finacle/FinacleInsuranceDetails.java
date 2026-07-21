package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

@Data
public class FinacleInsuranceDetails {
    private String insu_ref_num;
    private String insu_type;
    private String policy_amt;
    private String company_name;
    private String items_insurd;
    private String risk_cover_start_date;
    private String risk_cover_end_date;
    private String premium_amt;
}
