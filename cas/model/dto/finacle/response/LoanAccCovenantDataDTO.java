package com.itechro.cas.model.dto.finacle.response;

import lombok.Data;

import java.util.List;

@Data
public class LoanAccCovenantDataDTO {

    private String casReference;

    List<LoanAccCovenantInquiryDTO> covenantInq;
}
