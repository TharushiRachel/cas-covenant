package com.itechro.cas.model.dto.acae.request;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class ACAEDateRangeInquiryRQ implements Serializable {

    private String solId;
    private Date fromDate;
    private Date toDate;

}
