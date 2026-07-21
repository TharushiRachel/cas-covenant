package com.itechro.cas.model.dto.facilitypaper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.dto.integration.response.finacle.GroupExposureCashMargin;
import com.itechro.cas.model.dto.integration.response.finacle.GroupExposureFundedLimit;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class GroupExposureCustomerID implements Serializable {

    @JsonProperty("CustId")
    private String custId;

    @JsonProperty("CustName")
    private String custName;

    @JsonProperty("ParentCustId")
    private String parentCustId;

    @JsonProperty("RelationshipCode")
    private Object relationshipCode;

    @JsonProperty("TotalSanctionLimit")
    private String totalSanctionLimit;

    @JsonProperty("OutstandingAmt")
    private String outstandingAmt;

    @JsonProperty("FundedLimit")
    private GroupExposureFundedLimit fundedLimit;

    @JsonProperty("NonFundedLimit")
    private GroupExposureFundedLimit nonFundedLimit;

    @JsonProperty("CashMargin")
    private GroupExposureCashMargin cashMargin;

    public String getRelationshipCodeAsString() {
        if (relationshipCode instanceof String) {
            return (String) relationshipCode;
        } else if (relationshipCode instanceof Map) {
            // Handle empty JSON objects like "RelationshipCode": {}
            if (((Map<?, ?>) relationshipCode).isEmpty()) {
                return null;
            }
        }
        return null;
    }
}
