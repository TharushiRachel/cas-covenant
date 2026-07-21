package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ACAEUserDTO implements Serializable {
    String userId;
    String firstName;
    String lastName;
    List<ACAESearchByStatusDTO> acaeSearchByStatusDTOList;
}
