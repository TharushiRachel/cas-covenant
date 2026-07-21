package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportDCTurnoverRS implements Serializable {

    private String status;
    private String reqId;
    private List<ImportDCTurnover> impDCTORecs = new ArrayList<>();
    private List<ImportDCFaultRS> fault = new ArrayList<>();
}
