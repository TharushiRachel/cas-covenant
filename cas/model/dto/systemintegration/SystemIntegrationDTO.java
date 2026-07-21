package com.itechro.cas.model.dto.systemintegration;

import lombok.Data;

/**
 *
 *
 * @author tharushi
 */

@Data
public class SystemIntegrationDTO {
    private String applicationCode;

    private String applicationName;

    private String applicationShortName;

    private String activeStatus;

    private String applicationDescription;

    private String url;

    private String applicationCodeWithStatus;
}
