package lk.sampath.cas_covenant.dto;

import lombok.Data;

import java.util.List;

@Data
public class CovenantDetailsFinacleDTO {

    private String status;

    private String requestId;

    List<CovenantDataDTO> covenant;

    NoneComplianceCovenantDTO specialComment;
}
