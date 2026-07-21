package com.itechro.cas.model.dto.acae.response;

import lombok.Data;
import java.util.Date;

@Data
public class ACAECustomerDetailsDTO {

    String customerName;
    String accountNumber;
    Date ReviewingDate;
    String riskGrade;
    String ClassificationId;
    String WatchListed;
    String referenceNumber;
    String balance;
    String sanctionLimit;
    String todBal;
    String ualBal;
    String postDatedChqs;
    String currentFloatBalance;
    String debitBalance;
    Date negDate;
    Date classificationDate;
    Integer classificationDays;
    String classificationStatus;

}
