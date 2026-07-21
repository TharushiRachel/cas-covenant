package com.itechro.cas.model.dto.facilitypaper.request;

import lombok.Data;

import java.util.List;

@Data
public class FinalDTO {
    private String covanentKey;
    private List<ApplicationCovenantDetailsDTO> covValue;

}
