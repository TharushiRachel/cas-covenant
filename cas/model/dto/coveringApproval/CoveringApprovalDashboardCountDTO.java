package com.itechro.cas.model.dto.coveringApproval;

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CoveringApprovalDashboardCountDTO implements Serializable {

    private Integer inboxCovApp;

    private Integer inProgressCovApp;

    private Integer returnedCovApp;

    private Integer approvedCovApp;

    private Integer rejectedCovApp;

    private Integer dashboardTimePeriodDays;
}
