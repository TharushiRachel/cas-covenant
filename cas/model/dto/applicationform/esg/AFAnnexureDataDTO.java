package com.itechro.cas.model.dto.applicationform.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureData;
import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureQuestionData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AFAnnexureDataDTO {

    private Integer annexureDataId;

    private Integer annexureId;

    private Integer applicationFormID;

    private String name;

    private String description;

    private AppsConstants.YesNo isMandatory;

    private String status;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private DomainConstants.ApplicationFormStatus currentAnnexureStatus;

    private List<AFAnnexureQuestionDataDTO> afAnnexureQuestionDataDTOList;

    public AFAnnexureDataDTO(AFAnnexureData afAnnexure) {
        this.applicationFormID = afAnnexure.getApplicationForm().getApplicationFormID();
        this.annexureDataId = afAnnexure.getAnnexureDataId();
        this.annexureId = afAnnexure.getAnnexureId();
        this.name = afAnnexure.getName();
        this.description = afAnnexure.getDescription();
        this.isMandatory = afAnnexure.getIsMandatory();
        this.status = afAnnexure.getStatus();
        this.createdDate = afAnnexure.getCreatedDate();
        this.createdBy = afAnnexure.getCreatedBy();
        this.modifiedDate = afAnnexure.getModifiedDate();
        this.modifiedBy = afAnnexure.getModifiedBy();
        this.currentAnnexureStatus = afAnnexure.getCurrentAnnexureStatus();

        if(afAnnexure.getAfAnnexureQuestionDataList() != null && !afAnnexure.getAfAnnexureQuestionDataList().isEmpty()) {
            this.afAnnexureQuestionDataDTOList = new ArrayList<>();
            for (AFAnnexureQuestionData question : afAnnexure.getAfAnnexureQuestionDataList()) {
                this.afAnnexureQuestionDataDTOList.add(new AFAnnexureQuestionDataDTO(question));
            }
        } else {
            this.afAnnexureQuestionDataDTOList = new ArrayList<>();
        }
    }
}
