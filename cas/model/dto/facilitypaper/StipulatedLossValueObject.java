package com.itechro.cas.model.dto.facilitypaper;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class StipulatedLossValueObject {

    Map<String, String> stipulatedLossValueDetails = new HashMap<>();
}
