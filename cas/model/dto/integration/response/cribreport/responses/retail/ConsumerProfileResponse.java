package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerProfileResponse implements Serializable {

    @JsonProperty("CONSUMER_DETAILS_VER4")
    private List<ConsumerDetailResponse> consumerDetailResponseList;

    @JsonProperty("CONSUMER_DETAILS_VER4")
    public List<ConsumerDetailResponse> getConsumerDetailResponseList() {
        return consumerDetailResponseList;
    }

    @JsonProperty("CONSUMER_DETAILS_VER4")
    public void setConsumerDetailResponseList(List<ConsumerDetailResponse> consumerDetailResponseList) {
        this.consumerDetailResponseList = consumerDetailResponseList;
    }
}
