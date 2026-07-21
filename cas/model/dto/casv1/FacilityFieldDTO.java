package com.itechro.cas.model.dto.casv1;


import com.itechro.cas.model.domain.casv1.FacilityField;
import lombok.Data;

import java.util.Date;

@Data
public class FacilityFieldDTO {

    private Date facilityDate;

    private Date facilityTextDate;

    private Integer facilityTextNo;

    private Integer facilityID;

    private Integer facilityNo;

    private String customerRef;

    private String fieldNo;

    private String fieldValue;

    public FacilityFieldDTO(){
    }

    public FacilityFieldDTO (FacilityField facilityFieldDTO){

        this.facilityDate = facilityFieldDTO.getFacilityDate();
        this.facilityTextDate = facilityFieldDTO.getFacilityTextDate();
        this.facilityTextNo = facilityFieldDTO.getFacilityTextNo();
        this.facilityID = facilityFieldDTO.getFacilityID();
        this.facilityNo = facilityFieldDTO.getFacilityNo();
        this.customerRef = facilityFieldDTO.getCustomerRef();
        this.fieldNo = facilityFieldDTO.getFieldNo();
        this.fieldValue = facilityFieldDTO.getFieldValue();
    }
}
