package com.itechro.cas.model.dto.das;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class DALimitsAuthorizeRQ {

    private Integer designationId;

    private DomainConstants.MasterDataApproveStatus approveStatus;

}
