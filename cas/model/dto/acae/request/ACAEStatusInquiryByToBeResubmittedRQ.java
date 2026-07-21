package com.itechro.cas.model.dto.acae.request;

import lombok.Data;

@Data
public class ACAEStatusInquiryByToBeResubmittedRQ {

    String reportType;
    String fromDate;
    String toDate;
    String solId;
}
