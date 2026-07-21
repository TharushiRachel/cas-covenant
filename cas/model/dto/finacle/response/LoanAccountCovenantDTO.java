package com.itechro.cas.model.dto.finacle.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoanAccountCovenantDTO implements Serializable {

        private String status;

        private String requestId;

        List<LoanAccCovenantDataDTO> covenant;
}
