package com.itechro.cas.model.dto.acae.request;

import com.itechro.cas.model.common.PagedParamDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ACAEInquiryByDateRangeRQ extends PagedParamDTO {

    String fromDate;
    String toDate;

}
