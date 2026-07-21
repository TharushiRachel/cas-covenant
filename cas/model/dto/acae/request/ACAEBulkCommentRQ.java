package com.itechro.cas.model.dto.acae.request;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ACAEBulkCommentRQ {
    private List<ACAEBaseRQ> recordSet;
    private String comment;
    private Date negDate;
    private Date previousNegDate;

}
