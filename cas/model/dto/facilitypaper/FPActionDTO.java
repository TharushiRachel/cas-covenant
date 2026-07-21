package com.itechro.cas.model.dto.facilitypaper;

import lombok.Data;

import java.io.Serializable;

@Data
public class FPActionDTO implements Serializable {

    private Integer committeePaperID;

    private Integer facilityPaperID;

    private String actionMessage;

    private String createdUserDisplayName;

    private String createdBy;
}
