package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

@Data
public class LimitValues {
    private String limitB2K;
    private String ccyCode;
    private String lPrefix;
    private String lSuffix;
    private String lDesc;
    private String sancLimit;
    private String liab;
    private String contingLiab;
}
