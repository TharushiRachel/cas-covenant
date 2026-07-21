package com.itechro.cas.model.dto.covenants;

import lombok.Data;

import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CovenantDataDTO {
    private String casReference;

    List<CovenantInquiryDTO> covenantInq;
}
