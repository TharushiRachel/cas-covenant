package com.itechro.cas.model.dto.das;

import com.itechro.cas.model.domain.das.DADesignationMasterData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DADesignationAndDALimitsDTO {

    private FullDATableWithTypeDTO fullDATableRS;

    private DADesignationsWithType daDesignationMasterData;

}



