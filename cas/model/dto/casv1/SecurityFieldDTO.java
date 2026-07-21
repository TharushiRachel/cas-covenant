package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.SecurityField;
import lombok.Data;

import java.util.Date;


@Data
public class SecurityFieldDTO {
    private Date facilityDate;

    private Date securityTextDate;

    private Integer securityTextNo;

    private Integer facilityID;

    private Integer facilityNo;

    private String customerRef;

    private String fieldNo;

    private String fieldValue;

    public SecurityFieldDTO(){
    }

    public SecurityFieldDTO(SecurityField securityFieldDTO){

        this.facilityDate = securityFieldDTO.getFacilityDate();
        this.securityTextDate = securityFieldDTO.getSecurityTextDate();
        this.securityTextNo = securityFieldDTO.getSecurityTextNo();
        this.facilityID = securityFieldDTO.getFacilityID();
        this.facilityNo = securityFieldDTO.getFacilityNo();
        this.customerRef = securityFieldDTO.getCustomerRef();
        this.fieldNo = securityFieldDTO.getFieldNo();
        this.fieldValue = securityFieldDTO.getFieldValue();
    }

}
