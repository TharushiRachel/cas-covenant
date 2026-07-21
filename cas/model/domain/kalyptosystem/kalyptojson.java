package com.itechro.cas.model.domain.kalyptosystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class kalyptojson {

    @JsonProperty("realKey")
    private String parameterId;

    @JsonProperty("possibleKeys")
    private List<String> valueNumeric;



}
