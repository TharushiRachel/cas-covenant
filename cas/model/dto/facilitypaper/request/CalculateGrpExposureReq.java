package com.itechro.cas.model.dto.facilitypaper.request;

import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.GroupExposureDetailDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CalculateGrpExposureReq implements Serializable {

    private List<GroupExposureDetailDTO> exposures;

    private FacilityPaperDTO facilityPaperDTO;
}
