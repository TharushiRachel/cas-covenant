package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.CommonCode;
import lombok.Data;

@Data
public class CommonCodeDTO {

    private Integer codeID;

    private Integer codeType;

    private String codeDescription;

    private Integer parentID;

    private Integer orderID;

    public CommonCodeDTO(){

    }

    public CommonCodeDTO(CommonCode commonCodeDTO){

        this.codeID = commonCodeDTO.getCodeID();
        this.codeType = commonCodeDTO.getCodeType();
        this.codeDescription = commonCodeDTO.getCodeDescription();
        this.parentID = commonCodeDTO.getParentID();
        this.orderID = commonCodeDTO.getOrderID();
    }
}
