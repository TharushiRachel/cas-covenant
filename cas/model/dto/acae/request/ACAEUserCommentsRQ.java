package com.itechro.cas.model.dto.acae.request;

import lombok.Data;
import java.util.Date;

@Data
public class ACAEUserCommentsRQ {
    String accountNumber;
    String referenceNumber;
    String toDate;
    String fromDate;
}
