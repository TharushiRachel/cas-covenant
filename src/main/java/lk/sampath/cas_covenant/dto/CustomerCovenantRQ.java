package lk.sampath.cas_covenant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerCovenantRQ {

    @JsonProperty("RequestUUID")
    private String RequestUUID;

    private String type;
}
