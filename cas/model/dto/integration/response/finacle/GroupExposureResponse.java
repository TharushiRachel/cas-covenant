package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class GroupExposureResponse implements Serializable {
    @JsonProperty("status")
    private String status;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("exposureDtls")
    private List<GroupExposureData> exposureDtls;

    public GroupExposureResponse(String status, String requestId, List<GroupExposureData> exposureDtls){
        this.status = status;
        this.requestId = requestId;
        this.exposureDtls = exposureDtls;
    }
}
