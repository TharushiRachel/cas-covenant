package com.itechro.cas.model.dto.integration.response.finacle;

import com.itechro.cas.model.dto.finacle.response.InsuranceDetails;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExportDcBillsTurnoverRS implements Serializable {

    private String status;
    private String reqId;
    private List<ExportDcBillsTurnover> responseData;
    private List<ExportDCBillFaultRS> fault;
}
