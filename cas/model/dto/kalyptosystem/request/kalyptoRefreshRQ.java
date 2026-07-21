package com.itechro.cas.model.dto.kalyptosystem.request;

import com.itechro.cas.model.dto.integration.request.LoadKalyptoDataRQ;
import lombok.Data;

@Data
public class kalyptoRefreshRQ {

    private Integer kalyptoId;
    private LoadKalyptoDataRQ loadKalyptoDataRQ;
}
