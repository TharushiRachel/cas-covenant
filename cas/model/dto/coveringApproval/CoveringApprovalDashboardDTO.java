package com.itechro.cas.model.dto.coveringApproval;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CoveringApprovalDashboardDTO implements Serializable {

 public int covAppId;

 public String customerName;
 public String covAppRefNumber;

 public String branchCode;

 public String branchName;

 private String accountNumber;

 private String identificationNumber;

 private String createdDate;

 private String createdBy;

 private String assignedUser;

 private String status;

 private String chequeNumber;

 private String amount;

 private Boolean isAuthorized;

 private String tranCurrency;
}
