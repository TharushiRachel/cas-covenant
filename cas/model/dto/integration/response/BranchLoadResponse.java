package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "BranchCode",
        "BranchName"

})
public class BranchLoadResponse implements Serializable {

    @JsonProperty("BranchCode")
    private String BranchCode;

    @JsonProperty("BranchName")
    private String BranchName;

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    @Override
    public String toString() {
        return "BranchLoadResponse{" +
                "BranchCode='" + BranchCode + '\'' +
                ", BranchName='" + BranchName + '\'' +
                '}';
    }
}
