package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ImportDCTurnoverTempRS implements Serializable {

    private String status;
    private String reqId;
    private Object impDCTORecs ;
    private List<ImportDCFaultRS> fault = new ArrayList<>();
}
