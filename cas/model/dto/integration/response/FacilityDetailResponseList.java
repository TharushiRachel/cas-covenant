package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Exsisting_Facility_List"
})
public class FacilityDetailResponseList {


    @JsonProperty("Exsisting_Facility_List")
    private ArrayList<FacilityDetailResponse> facilityDetailResponseArrayList;

    public ArrayList<FacilityDetailResponse> getFacilityDetailResponseArrayList() {
        return facilityDetailResponseArrayList;
    }

    public void setFacilityDetailResponseArrayList(ArrayList<FacilityDetailResponse> facilityDetailResponseArrayList) {

        this.facilityDetailResponseArrayList = facilityDetailResponseArrayList;
    }

    @Override
    public String toString() {
        return "FacilityDetailResponseList{" +
                "facilityDetailResponseArrayList=" + facilityDetailResponseArrayList +
                '}';
    }
}


