package com.itechro.cas.model.dto.das;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FullDATableRS {

//    private FullDATable approvedDALimits;
//
//    private FullDATable pendingDALimits;

    private List<DATableDTO> approvedDALimits;

    private List<DATableDTO> pendingDALimits;
}
