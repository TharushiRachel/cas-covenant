package com.itechro.cas.model.dto.acae.request;

import lombok.Data;

@Data
public class ACAEPreviousUserRQ {

    String searchReference;
    String accountNumber;
    String thisUser;
    Integer numOfDays;
}
