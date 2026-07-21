package com.itechro.cas.model.dto.acae.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class ACAEPaperApproveRQ implements Serializable {

    private String referenceNumber;
    private String accountNumber;
    private String sanctionLimit;
    private String currentUsername;
}
