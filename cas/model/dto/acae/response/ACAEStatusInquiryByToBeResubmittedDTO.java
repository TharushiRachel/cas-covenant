package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

@Data
public class ACAEStatusInquiryByToBeResubmittedDTO {
    String solId;
    String acaeDate;
    String accountNo;
    String refNo;
    String currentStatus;
    String currentUser;
    String maxODForWeek;
}
