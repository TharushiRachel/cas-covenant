package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportFaultRS implements Serializable {


    private String Code;
    private String Description;

}
