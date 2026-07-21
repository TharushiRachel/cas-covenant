package com.itechro.cas.model.dto.acae.request;

import lombok.Data;

@Data
public class ACAEInitializeReviewRQ {
    String accountNumber;
    String referenceNumber;
    String currentUser;
}
