package com.itechro.cas.model.dto.casmaster;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class EnvRiskResponseDTO implements Serializable {

    private List<EnvironmentalRiskDTO> tempRiskList;

    private List<EnvironmentalRiskDTO> masterRiskList;
}
