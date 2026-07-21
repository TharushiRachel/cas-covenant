package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

@Data
public class ACAECurrentUserDTO {
    String userId;
    String userName;
    String userLevel;
    String branch;
}
