package com.itechro.cas.model.dto.lead.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.lead.LeadComment;
import com.itechro.cas.model.dto.lead.LeadCommentCrmDTO;
import lombok.Data;

import java.util.List;

@Data
public class LeadCrmObjectDTO {

    @JsonProperty("Lea_ex2_73")
    private String Lea_ex2_73;

    @JsonProperty("Lea_ex2_74")
    private String Lea_ex2_74;

    @JsonProperty("Lea_ex2_88")
    private String Lea_ex2_88;

    @JsonProperty("Lea_ex2_91")
    private String Lea_ex2_91;
    @JsonProperty("Lea_ex2_116")
    private String Lea_ex2_116;
}
