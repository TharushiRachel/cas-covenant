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
public class ImportOutwardTurnoverRS implements Serializable {

    private String status;
    private String reqId;
    private List<ImportOutwardTurnover> ttTrnOverRecs = new ArrayList<>();
    private List<ImportOutwardFaultRS> fault = new ArrayList<>();
}
