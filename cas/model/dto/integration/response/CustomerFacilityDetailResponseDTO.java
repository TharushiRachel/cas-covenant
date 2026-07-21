package com.itechro.cas.model.dto.integration.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerFacilityDetailResponseDTO implements Serializable {

    private boolean success;

    private List<FacilityDetailResponseDTO> facilityDetailResponseDTOList;

    public CustomerFacilityDetailResponseDTO(FacilityDetailResponseList facilityDetailResponseList) {

        if (facilityDetailResponseList != null) {
            if (facilityDetailResponseList.getFacilityDetailResponseArrayList() != null) {
                for (FacilityDetailResponse facilityDetailResponse : facilityDetailResponseList.getFacilityDetailResponseArrayList()) {
                    this.getFacilityDetailResponseDTOList().add(new FacilityDetailResponseDTO(facilityDetailResponse));
                }
            }
        }
    }

    public List<FacilityDetailResponseDTO> getFacilityDetailResponseDTOList() {
        if (facilityDetailResponseDTOList == null) {
            facilityDetailResponseDTOList = new ArrayList<>();
        }
        return facilityDetailResponseDTOList;
    }

    public void setFacilityDetailResponseDTOList(List<FacilityDetailResponseDTO> facilityDetailResponseDTOList) {
        this.facilityDetailResponseDTOList = facilityDetailResponseDTOList;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CustomerFacilityDetailResponseDTO{" +
                "facilityDetailResponseDTOList=" + facilityDetailResponseDTOList +
                '}';
    }
}
