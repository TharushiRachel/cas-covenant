package lk.sampath.cas_covenant.dto;

import lombok.Data;

import java.util.List;

@Data
public class FinalDTO {

    private String covanentKey;
    private List<ApplicationCovenantDetailsDTO> covValue;
}
