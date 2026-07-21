package com.itechro.cas.model.dto.lead;

import com.itechro.cas.model.domain.lead.DigitalApplicationFormDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DigitalApplicationDTO implements Serializable {
    private Integer digitalApplicationID;
    private Integer leadID;
    private String documentContent;
    private String documentStatus;
    private List<DigitalApplicationTagDTO> documentTags;
    private List<Long> compPartyIds;
    private Integer compLeadId;

    public DigitalApplicationDTO(){

    }
    public DigitalApplicationDTO(DigitalApplicationFormDetail digitalApplicationFormDetail){
        this.digitalApplicationID = digitalApplicationFormDetail.getDigitalApplicationID();
        this.leadID = digitalApplicationFormDetail.getLeadID();
        this.documentContent = digitalApplicationFormDetail.getDocumentContent();
    }
}
