package com.itechro.cas.model.dto.acae.request;

import com.itechro.cas.model.common.PagedParamDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class ACAEInquiryByResubmittedRQ  extends PagedParamDTO {
    String reportType;
    String fromDate;
    String toDate;
    String solIdList;
    ArrayList solList;
}
