package com.itechro.cas.model.dto.acae.response;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ACAEInquiryBySolIdDTO implements Serializable {

    String solId;
    Date forwardByApprovalDate;
    String forwardByApprovalCount;
    Date lastFetchedDate;
    String lastFetchedCount;
}
