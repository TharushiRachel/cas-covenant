package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class IsWatchlistedRQ implements Serializable {

    private String refId;
    private String custId;

}
