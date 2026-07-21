package com.itechro.cas.model.dto.covenants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CovenantListDTO {

    @JsonProperty("Status")
    private String Status;

    @JsonProperty("covenants")
    private CovenantLevelDTO covenants;

}
