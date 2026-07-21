package com.itechro.cas.model.dto.esg;

import com.itechro.cas.model.domain.esg.RiskOpinion;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class RiskOpinionDTO implements Serializable {

    private Integer riskOpinionId;

    private Integer applicationFormId;

    private Integer facilityPaperId;

    private String opinion;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private RiskOpinionReplyDTO riskOpinionReply;

    public RiskOpinionDTO(RiskOpinion riskOpinion) {
        this.riskOpinionId = riskOpinion.getRiskOpinionId();
        this.applicationFormId = riskOpinion.getApplicationForm() != null ? riskOpinion.getApplicationForm().getApplicationFormID() : 0;
        this.facilityPaperId = riskOpinion.getFacilityPaper() != null ? riskOpinion.getFacilityPaper().getFacilityPaperID() : 0;
        this.opinion = riskOpinion.getOpinion();
        this.createdDate = riskOpinion.getCreatedDate();
        this.createdBy = riskOpinion.getCreatedBy();
        this.modifiedDate = riskOpinion.getModifiedDate();
        this.modifiedBy = riskOpinion.getModifiedBy();
        if (riskOpinion.getRiskOpinionReply() != null) {
            this.riskOpinionReply = new RiskOpinionReplyDTO(riskOpinion.getRiskOpinionReply());
        }
    }

    public Integer getRiskOpinionId() {
        if (this.riskOpinionId == null){
            this.riskOpinionId = 0;
        }
        return riskOpinionId;
    }
}
