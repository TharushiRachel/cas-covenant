package com.itechro.cas.model.dto.casv1;

import lombok.Data;
import java.util.Date;

@Data
public class CommentTableDTO {

    private String refNo;
    private String type;
    private String remark1;
    private String remark2;
    private String remarkDate;
    private String userID;
    private Integer state;
    private String facilityDate;
    private String codeDesc;
}
