package com.itechro.cas.model.dto.finacle.response;

import lombok.Data;

import java.util.List;
@Data
public class FinacleExOutLimitsRSDTO {

     private List<LimitDetails> limits;
     private List<LoanDetails> loans;
}
