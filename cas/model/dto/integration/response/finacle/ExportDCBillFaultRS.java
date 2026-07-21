package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportDCBillFaultRS implements Serializable {


    private String code;
    private String description;

}
