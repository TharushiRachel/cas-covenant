package com.itechro.cas.model.dto.facilitypaper;

import lombok.Data;

import java.util.List;

@Data
public class CustomerTurnoverGurantee {

    String customerName;

    List<TurnoverGuranteeDTO> turnoverGuranteeDTOS;
}
