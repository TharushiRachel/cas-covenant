package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ACAEInquiryByResubmittedDTO implements Serializable {
    String solId;
    String acaeDate;
    String accountNo;
    String refNo;
    Integer currentStatus;
    String currentUser;
    String maxOdForWeek;
}
