package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.util.List;

@Data
public class InsuranceSuccessResponse {

    private List<InsuranceCollateral> collateral;

}
