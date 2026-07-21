package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.dto.facilitypaper.GroupExposureCustomerID;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor

public class GroupExposureData implements Serializable {

    @JsonProperty("customerId")
    private GroupExposureCustomerID  customerId;

}
