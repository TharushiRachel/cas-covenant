package com.itechro.cas.model.dto.covenants;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CovenantDetailsFinacleDTO implements Serializable {

    private String status;

    private String requestId;

    List<CovenantDataDTO> covenant;

    NoneComplianceCovenantDTO specialComment;

}
