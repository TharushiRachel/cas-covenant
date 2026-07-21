package com.itechro.cas.model.dto.coveringApproval;

import com.itechro.cas.model.common.PagedParamDTO;
import lombok.Data;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CoveringApprovalDashboardRQ extends PagedParamDTO {

    private String loggedInUserId;
    private String loggedInUserBranchCode; //divCode
    private String coveringApprovalDashboardStatus;
    private String coveringApprovalDashboardSubStatus;
}
