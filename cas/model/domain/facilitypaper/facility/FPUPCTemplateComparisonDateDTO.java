package com.itechro.cas.model.domain.facilitypaper.facility;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FPUPCTemplateComparisonDateDTO {
    Date createdDate;

    List<FPUPCTemplateComparisonContentDTO> historyList;

}
