package com.itechro.cas.model.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CustomerCovenantRQ {

    @JsonProperty("RequestUUID")
    private String RequestUUID;

    private String type;
}
