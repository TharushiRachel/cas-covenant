package com.itechro.cas.model.dto.integration.response.coveringApproval;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Data
public class TranDetailsResponseDTO implements Serializable {

    private List<CoveringApprovalTranDetailsDTO> traninquiry;

    private String Status;

}
