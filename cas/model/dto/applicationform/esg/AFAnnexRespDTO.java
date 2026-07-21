package com.itechro.cas.model.dto.applicationform.esg;

import com.itechro.cas.model.dto.esg.AnnexureDTO;
import lombok.Data;

import java.util.List;

@Data
public class AFAnnexRespDTO {

    private List<AnnexureDTO> tempAnnexes;

    private List<AnnexureDTO> masterAnnexes;
}
