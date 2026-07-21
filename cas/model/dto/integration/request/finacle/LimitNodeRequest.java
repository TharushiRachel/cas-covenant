package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class LimitNodeRequest implements Serializable {


    private String reqId;

    private String limitB2kId;
}
