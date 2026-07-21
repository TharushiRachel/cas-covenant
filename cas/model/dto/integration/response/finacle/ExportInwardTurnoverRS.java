package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExportInwardTurnoverRS implements Serializable {

    private String Status;
    private List<ExportInwardTurnover> ttTrnOverRecs = new ArrayList<>();
    private List<ExportFaultRS> Fault = new ArrayList<>();
}
