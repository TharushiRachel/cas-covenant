package com.itechro.cas.model.dto.acae.response;
import lombok.Data;

@Data
public class ACAEStatusInquiryByDateRangeDTO {
    String currentUser;
    String solId;
    String ACAEDate;
    String receivedDate;
    String status;
    String paperCount;
}
