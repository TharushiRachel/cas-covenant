package lk.sampath.cas_covenant.dto;

import lombok.Data;

import java.util.List;

@Data
public class CovenantDataDTO {
    private String casReference;

    List<CovenantInquiryDTO> covenantInq;
}
