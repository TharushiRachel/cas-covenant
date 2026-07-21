package com.itechro.cas.model.dto.acae.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class ACAEListDoneRQ implements Serializable {

    String userId;
    String solId;
    String referenceNumber;
}
