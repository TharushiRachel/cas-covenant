package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerCreditFacilityStatusResponse implements Serializable {

    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_VER4")
    private List<ConsumerCreditFacilityStatus> consumerCreditFacilityStatusList;

    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_LABEL")
    private List<ConsumerCreditFacilityStatusLabelResponse> consumerCreditFacilityStatusLabelResponseList;


    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_LABEL")
    public List<ConsumerCreditFacilityStatusLabelResponse> getConsumerCreditFacilityStatusLabelResponseList() {
        return consumerCreditFacilityStatusLabelResponseList;
    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_LABEL")
    public void setConsumerCreditFacilityStatusLabelResponseList(List<ConsumerCreditFacilityStatusLabelResponse> consumerCreditFacilityStatusLabelResponseList) {
        this.consumerCreditFacilityStatusLabelResponseList = consumerCreditFacilityStatusLabelResponseList;
    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_VER4")
    public List<ConsumerCreditFacilityStatus> getConsumerCreditFacilityStatusList() {
        return consumerCreditFacilityStatusList;
    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_VER4")
    public void setConsumerCreditFacilityStatusList(List<ConsumerCreditFacilityStatus> consumerCreditFacilityStatusList) {
        this.consumerCreditFacilityStatusList = consumerCreditFacilityStatusList;
    }
}
