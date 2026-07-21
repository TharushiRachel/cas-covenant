package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.SecurityText;
import lombok.Data;

import java.util.Date;


@Data
public class SecurityTextDTO {

    private Date formatDate;

    private Integer facilityNo;

    private Integer formatNo;

    private Integer lineNo;

    private Integer startWith;

    private Integer endWith;

    private Integer textNo;

    private String text;

    public SecurityTextDTO(){
    }

    public SecurityTextDTO (SecurityText securityTextDTO){

        this.formatDate = securityTextDTO.getFormatDate();
        this.facilityNo = securityTextDTO.getFacilityNo();
        this.formatNo = securityTextDTO.getFormatNo();
        this.lineNo = securityTextDTO.getLineNo();
        this.startWith = securityTextDTO.getStartWith();
        this.endWith = securityTextDTO.getEndWith();
        this.textNo = securityTextDTO.getTextNo();
        this.text = securityTextDTO.getText();

    }
}
