package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "BranchList"
})
public class BranchLoadResponseList implements Serializable {

    @JsonProperty("BranchList")
    private ArrayList<BranchLoadResponse> branchLoadResponseArrayList;

    public ArrayList<BranchLoadResponse> getBranchLoadResponseArrayList() {
        if (branchLoadResponseArrayList == null) {
            this.branchLoadResponseArrayList = new ArrayList<>();
        }
        return branchLoadResponseArrayList;
    }

    public void setBranchLoadResponseArrayList(ArrayList<BranchLoadResponse> branchLoadResponseArrayList) {
        this.branchLoadResponseArrayList = branchLoadResponseArrayList;
    }

    @Override
    public String toString() {
        return "BranchLoadResponseList{" +
                "branchLoadResponseArrayList=" + branchLoadResponseArrayList +
                '}';
    }
}
