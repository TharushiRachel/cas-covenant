package com.itechro.cas.model.dto.integration.request.cribreport;

import lombok.Data;

import java.io.Serializable;

@Data
public class IDNumberDTO implements Serializable {

    private String idNumberType;

    private String idNumber;
}