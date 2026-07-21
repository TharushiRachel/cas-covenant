package lk.sampath.cas_covenant.dto;

import lombok.Data;

@Data
public class LoadCovenantDataDTO {
    private String requestId;

    private String custId;

    private String acctId;

    private Integer facilityPaperId;
}
