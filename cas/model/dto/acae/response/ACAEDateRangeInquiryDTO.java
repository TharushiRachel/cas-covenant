package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ACAEDateRangeInquiryDTO implements Serializable {
    String currentUser;
    String solId;
    String acaeDate;
    String receivedDate;
    String status;
    String paperCount;
}
