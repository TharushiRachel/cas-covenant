package com.itechro.cas.model.dto.esg;

import com.itechro.cas.model.domain.esg.RiskOpinionReply;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class RiskOpinionReplyDTO implements Serializable {

    private Integer riskReplyId;

    private Integer riskOpinionId;

    private Integer facilityPaperId;

    private Integer applicationFormId;

    private String reply;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    public RiskOpinionReplyDTO(RiskOpinionReply riskOpinionReply) {
        this.riskReplyId = riskOpinionReply.getRiskReplyId();
        this.riskOpinionId = riskOpinionReply.getRiskOpinion().getRiskOpinionId();
        this.reply = riskOpinionReply.getReply();
        this.createdDate = riskOpinionReply.getCreatedDate();
        this.createdBy = riskOpinionReply.getCreatedBy();
        this.modifiedDate = riskOpinionReply.getModifiedDate();
        this.modifiedBy = riskOpinionReply.getModifiedBy();
    }
}
