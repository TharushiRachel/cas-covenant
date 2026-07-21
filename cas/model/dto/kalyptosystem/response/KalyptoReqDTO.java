package com.itechro.cas.model.dto.kalyptosystem.response;

import lombok.Data;

import java.util.List;

@Data
public class KalyptoReqDTO {

    private String realKey;

    private List<String> possibleKeys;
}
