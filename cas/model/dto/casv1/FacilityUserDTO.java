package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.FacilityUser;
import lombok.Data;

import java.util.Date;

@Data
public class FacilityUserDTO {

    private String refNo;

    private Date facilityDate;

    private String fromUserID;

    private String toUserID;

    private Date date;

    public FacilityUserDTO(){
    }

    public FacilityUserDTO (FacilityUser facilityUserDTO){

        this.refNo = facilityUserDTO.getRefNo();
        this.facilityDate = facilityUserDTO.getFacilityDate();
        this.fromUserID = facilityUserDTO.getFromUserID();
        this.toUserID = facilityUserDTO.getToUserID();
        this.date = facilityUserDTO.getDate();
    }
}
