package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

@Data
public class ACAEGridPermissionDTO {

    Boolean isForwardEnable;
    Boolean isRejectEnable;
    Boolean isTobeResubmitEnable;
    Boolean isApproveEnable;
}
