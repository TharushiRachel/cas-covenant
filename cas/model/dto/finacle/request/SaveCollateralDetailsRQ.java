package com.itechro.cas.model.dto.finacle.request;

import com.itechro.cas.model.dto.finacle.response.InsurenceDetailsFromFinacleRS;
import lombok.Data;

import java.util.List;

@Data
public class SaveCollateralDetailsRQ {

    private Integer facilityPaperId;
    private List<InsurenceDetailsFromFinacleRS> saveInsuranceValuationRQ;
}
