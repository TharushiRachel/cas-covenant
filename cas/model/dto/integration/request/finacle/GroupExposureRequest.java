package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupExposureRequest implements Serializable {

    private String requestId;

    private String cifiId;

    private Integer facilityID;
}
