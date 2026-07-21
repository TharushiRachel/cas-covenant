package lk.sampath.cas_covenant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CovenantListDTO {

    @JsonProperty("Status")
    private String Status;

    @JsonProperty("covenants")
    private CovenantLevelDTO covenants;
}
