package com.itechro.cas.model.dto.casv1;

import lombok.Data;

import java.io.Serializable;

@Data
public class FPRequestDTO implements Serializable {

    private String refNo;

    private String facilityDate;
}
