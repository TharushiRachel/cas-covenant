package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.util.List;

@Data
public class NodeDetails {
    private List<LimitValues> limit_Values;
}
