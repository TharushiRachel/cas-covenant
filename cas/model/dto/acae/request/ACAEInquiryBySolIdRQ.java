package com.itechro.cas.model.dto.acae.request;

import com.itechro.cas.model.common.PagedParamDTO;
import lombok.Data;

@Data
public class ACAEInquiryBySolIdRQ extends PagedParamDTO {

    String solIdList;
}
