package com.itechro.cas.model.dto.coveringApproval;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import lombok.Data;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CoveringApprovalStatusChangeRQ {

    private Integer covAppId;

    private String covAppRefNumber;

    private String currentAssignUserId;

    private String currentAssignUserAD;

    private String currentAssignUser;

    private String assignUserDisplayName;

    private String assignUserUpmId;

    private String currentAssignUserDivCode;

    private String updatedByUserDisplayName;

    private String actionMessage;

    private DomainConstants.CoveringApprovalStatus currentStatus;

    private DomainConstants.ForwardType forwardType;

    private String assignUserUpmGroupCode;

    private Boolean isAuthorized;

    private String updatedUserId;

    private CovAppCommentDTO covAppCommentDTO;
}
