package com.itechro.cas.model.dto.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.esg.Annexure;
import com.itechro.cas.model.domain.esg.AnnexureQuestion;
import com.itechro.cas.model.domain.esg.AnnexureQuestionTemp;
import com.itechro.cas.model.domain.esg.AnnexureTemp;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AnnexureDTO implements Serializable {

    private Integer annexureId;

    private Integer applicationFormID;

    private Integer facilityPaperID;

    private Integer parentId;

    private String name;

    private String description;

    private AppsConstants.YesNo isMandatory;

    private AppsConstants.YesNo allowRiskEdit;

    private String status;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private String approvedStatus;

    private Date approvedDate;

    private String approvedBy;

    private String actionStatus;

    private String annexureAddedBy;

    private Date annexureAddedDate;

    private String createdUserDivCode;

    private List<AnnexureQuestionDTO> questions;

    public AnnexureDTO(Annexure annexure) {
        this.annexureId = annexure.getAnnexureId();
        this.parentId = 0;
        this.name = annexure.getName();
        this.description = annexure.getDescription();
        this.isMandatory = annexure.getIsMandatory();
        this.allowRiskEdit = annexure.getAllowRiskEdit();
        this.status = annexure.getStatus();
        this.createdDate = annexure.getCreatedDate();
        this.createdBy = annexure.getCreatedBy();
        this.modifiedDate = annexure.getModifiedDate();
        this.modifiedBy = annexure.getModifiedBy();
        this.approvedStatus = annexure.getApprovedStatus();
        this.approvedDate = annexure.getApprovedDate();
        this.approvedBy = annexure.getApprovedBy();

        if(annexure.getAnnexureQuestionList() != null && !annexure.getAnnexureQuestionList().isEmpty()) {
            this.questions = new ArrayList<>();
            List<AnnexureQuestion> activeQuestions = annexure.getAnnexureQuestionList().stream()
                    .filter(question -> question.getStatus().equals(AppsConstants.Status.ACT.toString()))
                    .collect(Collectors.toList());

            for (AnnexureQuestion question : activeQuestions ) {
                this.questions.add(new AnnexureQuestionDTO(question));
            }
        } else {
            this.questions = new ArrayList<>();
        }

    }

    public AnnexureDTO(AnnexureTemp annexureTemp) {
        this.annexureId = annexureTemp.getAnnexureId();
        this.parentId = annexureTemp.getParentId();
        this.name = annexureTemp.getName();
        this.description = annexureTemp.getDescription();
        this.isMandatory = annexureTemp.getIsMandatory();
        this.allowRiskEdit = annexureTemp.getAllowRiskEdit();
        this.status = annexureTemp.getStatus();
        this.createdDate = annexureTemp.getCreatedDate();
        this.createdBy = annexureTemp.getCreatedBy();
        this.modifiedDate = annexureTemp.getModifiedDate();
        this.modifiedBy = annexureTemp.getModifiedBy();
        this.actionStatus = annexureTemp.getActionStatus();

        if(annexureTemp.getAnnexureQuestionList() != null && !annexureTemp.getAnnexureQuestionList().isEmpty()) {
            this.questions = new ArrayList<>();
            for (AnnexureQuestionTemp question : annexureTemp.getAnnexureQuestionList()) {
                this.questions.add(new AnnexureQuestionDTO(question));
            }
        } else {
            this.questions = new ArrayList<>();
        }
    }

    public AppsConstants.YesNo getIsMandatory() {
        if (this.isMandatory == null){
            this.isMandatory = AppsConstants.YesNo.N;
        }
        return isMandatory;
    }

    public AppsConstants.YesNo getAllowRiskEdit() {
        if (this.allowRiskEdit == null) {
            this.allowRiskEdit = AppsConstants.YesNo.N;
        }
        return allowRiskEdit;
    }
}
