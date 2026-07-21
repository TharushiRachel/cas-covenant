package com.itechro.cas.model.dto.covenants;

import lombok.Data;

/**
 *
 *
 * @author tharushi
 */
@Data
public class LoadCovenantDataDTO {

    private String requestId;

    private String custId;

    private String acctId;

    private Integer facilityPaperId;
}
