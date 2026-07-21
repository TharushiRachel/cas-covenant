package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class FinacleExOutLimitsRS implements Serializable {

    private String status;
    private String requestUUID;
    private NodeDetails nodeDetails;
    private NotLinkedAC not_Linked_AC;
}
