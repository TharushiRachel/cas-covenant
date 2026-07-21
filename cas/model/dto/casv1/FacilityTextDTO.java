package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.FacilityText;
import lombok.Data;

import java.util.Date;

@Data
public class FacilityTextDTO {

    private Date formatDate;

    private Integer facilityNo;

    private Integer formatNo;

    private Integer lineNo;

    private Integer startWith;

    private Integer endWith;

    private Integer textNo;

    private String text;

    public FacilityTextDTO() {
    }

    public FacilityTextDTO (FacilityText facilityTextDTO){
         this.formatDate = facilityTextDTO.getFormatDate();
         this.facilityNo = facilityTextDTO.getFacilityNo();
         this.formatNo = facilityTextDTO.getFormatNo();
         this.lineNo = facilityTextDTO.getLineNo();
         this.startWith = facilityTextDTO.getStartWith();
         this.endWith = facilityTextDTO.getEndWith();
         this.textNo = facilityTextDTO.getTextNo();
         this.text = facilityTextDTO.getText();
    }


}
