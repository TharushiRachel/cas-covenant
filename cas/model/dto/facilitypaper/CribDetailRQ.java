package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CribDetailRQ implements Serializable {

    private Integer cribDetailsID;

    private Integer facilityPaperID;

    private Integer supportingDocID;

    private String documentName;

    private DocStorageDTO docStorageDTO;

    private DomainConstants.CribStatusType cribStatus;

    private Date cribIssueDate;

    private String cribIssueDateStr;

    private String remark;

    private String inquiryReason;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private AppsConstants.Status status;

    private String fullName;

    private String gender;

    private String identificationType;

    private String identificationNumber;

    private boolean isReportUpdated;

    private AppsConstants.YesNo isSystem;
}