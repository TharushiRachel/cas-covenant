package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.FacilityTable;
import lombok.Data;

@Data
public class FacilityTableDTO {

    private Integer no;

    private String name;

    private String description;

    public FacilityTableDTO(){
    }

    public FacilityTableDTO (FacilityTable facilityTableDTO){

        this.no = facilityTableDTO.getNo();
        this.name = facilityTableDTO.getName();
        this.description = facilityTableDTO.getDescription();
    }
}
