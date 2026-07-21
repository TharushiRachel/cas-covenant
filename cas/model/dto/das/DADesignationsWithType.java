package com.itechro.cas.model.dto.das;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DADesignationsWithType {
    private List<DADesignationMasterDataDTO> committeeDesignations;
    private List<DADesignationMasterDataDTO> individualDesignations;
}
