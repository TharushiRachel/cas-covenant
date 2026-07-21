package com.itechro.cas.model.dto.acae.response;
import lombok.Data;

@Data
public class ACAEStatusInquiryBySOLIdDTO {
    String solId;
    String forwardedByApprovalDate;
    String forwardedByApprovalCount;
    String forwardedByBranchLastFetchedDate;
    String forwardedByBranchCount;
}
