package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class RCTProductGroup  implements Serializable {

    @JsonProperty("RefCode")
    private String RefCode;

    @JsonProperty("RefDesc1")
    private String RefDesc1;

    @JsonProperty("RefDesc2")
    private String RefDesc2;
}
