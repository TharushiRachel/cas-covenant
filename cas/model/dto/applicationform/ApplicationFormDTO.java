package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import com.itechro.cas.model.dto.esg.AnswerDataDTO;
import com.itechro.cas.model.dto.esg.EnvironmentalRiskDataDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationFormDTO implements Serializable {

    private Integer applicationFormID;

    private Integer workflowTemplateID;

    private String afRefNumber;

    private String branchCode;

    private String createdUserDisplayName;

    private Integer createdUserID;

    private DomainConstants.BasicInformationType formType;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private Integer assignUserID;

    private String assignUser;

    private String assignUserDisplayName;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private String assignUserDivCode;

    private String assignDepartmentCode;

    private DomainConstants.ApplicationFormStatus currentApplicationFormStatus;

    private List<AFCommentDTO> afCommentDTOList;

    private List<BasicInformationDTO> basicInformationDTOList;

    private List<AFDocumentDTO> afDocumentDTOList;

    private List<AFFacilityDTO> afFacilityDTOList;

    private List<AFTopicDataDTO> afTopicDataDTOList;

    private List<AFAssignDepartmentDTO> afAssignDepartmentDTOList;

    private List<AFStatusHistoryDTO> afStatusHistoryDTOList;

    private Integer leadID;

    private String fsType;

    private String externalAppDescription;

    private String externalAppRefNumber;

    private AppsConstants.YesNo isESGPaper;

    private AppsConstants.YesNo isESGApproved;

    private String approvedESGScore;

    private List<EnvironmentalRiskDataDTO> riskCategories;

    private List<AnswerDataDTO> answerDataDTO;

    private AnalyticsDecisionDTO analyticsDecision;

    public ApplicationFormDTO() {
    }

    public ApplicationFormDTO(ApplicationForm applicationForm) {
        this(applicationForm, null);
    }

    public ApplicationFormDTO(ApplicationForm applicationForm, AFLoadOptionsDTO afLoadOptionsDTO) {
        this.applicationFormID = applicationForm.getApplicationFormID();
        this.workflowTemplateID = applicationForm.getWorkFlowTemplate().getWorkFlowTemplateID();
        this.afRefNumber = applicationForm.getAfRefNumber();
        this.branchCode = applicationForm.getBranchCode();
        this.createdUserDisplayName = applicationForm.getCreatedUserDisplayName();
        this.createdUserID = applicationForm.getCreatedUserID();
        this.formType = applicationForm.getFormType();
        this.currentApplicationFormStatus = applicationForm.getCurrentApplicationFormStatus();
        this.assignUserID = applicationForm.getAssignUserID();
        this.assignUser = applicationForm.getAssignUser();
        this.assignUserDisplayName = applicationForm.getAssignUserDisplayName();
        this.assignUserUpmID = applicationForm.getAssignUserUpmID();
        this.assignUserUpmGroupCode = applicationForm.getAssignUserUpmGroupCode();
        this.assignUserDivCode = applicationForm.getAssignUserDivCode();
        this.createdBy = applicationForm.getCreatedBy();
        this.modifiedBy = applicationForm.getModifiedBy();
        this.assignDepartmentCode = applicationForm.getAssignDepartmentCode();
        this.leadID = applicationForm.getLeadID();
        this.fsType = applicationForm.getFsType();
        this.isESGPaper = applicationForm.getIsESGPaper();
        this.isESGApproved = applicationForm.getIsESGApproved();
        this.approvedESGScore = applicationForm.getApprovedESGScore();

        if (applicationForm.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(applicationForm.getCreatedDate());
        }
        if (applicationForm.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(applicationForm.getModifiedDate());
        }

        if (!applicationForm.getAfAssignDepartmentSet().isEmpty()) {
            for (AFAssignDepartment afAssignDepartment : applicationForm.getAfAssignDepartmentSet()) {
                if (afAssignDepartment.getStatus() == AppsConstants.Status.ACT) {
                    this.getAfAssignDepartmentDTOList().add(new AFAssignDepartmentDTO(afAssignDepartment));
                }
            }
        }

        if (afLoadOptionsDTO != null) {
            if (afLoadOptionsDTO.isLoadComments()) {
                if (!applicationForm.getAfCommentSet().isEmpty()) {
                    for (AFComment afComment : applicationForm.getOrderedAfComments()) {
                        if (afComment.getStatus() == AppsConstants.Status.ACT) {
                            this.getAfCommentDTOList().add(new AFCommentDTO(afComment));
                        }
                    }
                }
            }

            if (afLoadOptionsDTO.isLoadBasicInformation()) {
                if (!applicationForm.getBasicInformationSet().isEmpty()) {
                    List<BasicInformationDTO> secondaryBasicInformation = new ArrayList<>(); // This is for the joining parties
                    for (BasicInformation basicInformation : applicationForm.getOrderedBasicInformation()) {
                        if (basicInformation.getStatus() == AppsConstants.Status.ACT) {
                            if (basicInformation.getPrimaryInformation() == AppsConstants.YesNo.Y) {
                                this.getBasicInformationDTOList().add(new BasicInformationDTO(basicInformation, afLoadOptionsDTO.getAfBasicInformationLoadOptionDTO())); //Here add the primary basic information first
                            } else {
                                secondaryBasicInformation.add(new BasicInformationDTO(basicInformation, afLoadOptionsDTO.getAfBasicInformationLoadOptionDTO()));
                            }
                        }
                    }
                    this.getBasicInformationDTOList().addAll(secondaryBasicInformation);
                }
            }

            if (afLoadOptionsDTO.isLoadDocument()) {
                for (AFDocument afDocument : applicationForm.getOrderedAfDocument()) {
                    if (afDocument.getStatus() == AppsConstants.Status.ACT) {
                        this.getAfDocumentDTOList().add(new AFDocumentDTO(afDocument, false));
                    }
                }
            }

            if (afLoadOptionsDTO.isLoadFacilities()) {
                for (AFFacility afFacility : applicationForm.getOrderedAFFacilities()) {
                    if (afFacility.getStatus() == AppsConstants.Status.ACT) {
                        this.getAfFacilityDTOList().add(new AFFacilityDTO(afFacility, afLoadOptionsDTO.getAfFacilityLoadOptionDTO()));
                    }
                }
            }

            if (afLoadOptionsDTO.isLoadAFTopics()) {
                for (AFTopicData afTopicData : applicationForm.getOrderedAFTopicData()) {
                    if (afTopicData.getStatus() == AppsConstants.Status.ACT) {
                        this.getAfTopicDataDTOList().add(new AFTopicDataDTO(afTopicData));
                    }
                }
            }

            if (afLoadOptionsDTO.isLoadAFStatusHistory()) {
                for (AFStatusHistory afStatusHistory : applicationForm.getOrderedAfStatusHistorySet()) {
                    this.getAfStatusHistoryDTOList().add(new AFStatusHistoryDTO(afStatusHistory));
                }
            }

            if (!applicationForm.getEnvironmentalRisks().isEmpty()){
                this.riskCategories = applicationForm.getEnvironmentalRisks().stream().map(EnvironmentalRiskDataDTO::new).collect(Collectors.toList());
            }

        }

        if(applicationForm.getLeadID() !=null){
          if(applicationForm.getLeadAppCode() !=null){
              this.externalAppRefNumber = applicationForm.getLeadAppCode().getAppRef();
              if(applicationForm.getLeadAppCode().getAppDescription() !=null){
                  this.externalAppDescription = applicationForm.getLeadAppCode().getAppDescription().getAppDescription();
              }
          }
        }
    }

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadId) {
        this.leadID = leadID;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getAfRefNumber() {
        return afRefNumber;
    }

    public void setAfRefNumber(String afRefNumber) {
        this.afRefNumber = afRefNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public Integer getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(Integer createdUserID) {
        this.createdUserID = createdUserID;
    }

    public DomainConstants.ApplicationFormStatus getCurrentApplicationFormStatus() {
        return currentApplicationFormStatus;
    }

    public void setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus currentApplicationFormStatus) {
        this.currentApplicationFormStatus = currentApplicationFormStatus;
    }

    public List<AFCommentDTO> getAfCommentDTOList() {
        if (afCommentDTOList == null) {
            this.afCommentDTOList = new ArrayList<>();
        }
        return afCommentDTOList;
    }

    public void setAfCommentDTOList(List<AFCommentDTO> afCommentDTOList) {
        this.afCommentDTOList = afCommentDTOList;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<BasicInformationDTO> getBasicInformationDTOList() {
        if (basicInformationDTOList == null) {
            this.basicInformationDTOList = new ArrayList<>();
        }
        return basicInformationDTOList;
    }

    public void setBasicInformationDTOList(List<BasicInformationDTO> basicInformationDTOList) {
        this.basicInformationDTOList = basicInformationDTOList;
    }

    public List<AFDocumentDTO> getAfDocumentDTOList() {
        if (afDocumentDTOList == null) {
            this.afDocumentDTOList = new ArrayList<>();
        }
        return afDocumentDTOList;
    }

    public void setAfDocumentDTOList(List<AFDocumentDTO> afDocumentDTOList) {
        this.afDocumentDTOList = afDocumentDTOList;
    }

    public List<AFFacilityDTO> getAfFacilityDTOList() {
        if (afFacilityDTOList == null) {
            afFacilityDTOList = new ArrayList<>();
        }
        return afFacilityDTOList;
    }

    public void setAfFacilityDTOList(List<AFFacilityDTO> afFacilityDTOList) {
        this.afFacilityDTOList = afFacilityDTOList;
    }

    public List<AFTopicDataDTO> getAfTopicDataDTOList() {
        if (afTopicDataDTOList == null) {
            this.afTopicDataDTOList = new ArrayList<>();
        }
        return afTopicDataDTOList;
    }

    public void setAfTopicDataDTOList(List<AFTopicDataDTO> afTopicDataDTOList) {
        this.afTopicDataDTOList = afTopicDataDTOList;
    }

    public DomainConstants.BasicInformationType getFormType() {
        return formType;
    }

    public void setFormType(DomainConstants.BasicInformationType formType) {
        this.formType = formType;
    }

    public Integer getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(Integer assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public Integer getAssignUserUpmID() {
        return assignUserUpmID;
    }

    public void setAssignUserUpmID(Integer assignUserUpmID) {
        this.assignUserUpmID = assignUserUpmID;
    }

    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
    }

    public Integer getWorkflowTemplateID() {
        return workflowTemplateID;
    }

    public void setWorkflowTemplateID(Integer workflowTemplateID) {
        this.workflowTemplateID = workflowTemplateID;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public List<AFAssignDepartmentDTO> getAfAssignDepartmentDTOList() {
        if (afAssignDepartmentDTOList == null) {
            this.afAssignDepartmentDTOList = new ArrayList<>();
        }
        return afAssignDepartmentDTOList;
    }

    public void setAfAssignDepartmentDTOList(List<AFAssignDepartmentDTO> afAssignDepartmentDTOList) {
        this.afAssignDepartmentDTOList = afAssignDepartmentDTOList;
    }

    public List<AFStatusHistoryDTO> getAfStatusHistoryDTOList() {
        if (afStatusHistoryDTOList == null) {
            this.afStatusHistoryDTOList = new ArrayList<>();
        }
        return afStatusHistoryDTOList;
    }

    public void setAfStatusHistoryDTOList(List<AFStatusHistoryDTO> afStatusHistoryDTOList) {
        this.afStatusHistoryDTOList = afStatusHistoryDTOList;
    }

    public String getAssignUserDivCode() {
        return assignUserDivCode;
    }

    public void setAssignUserDivCode(String assignUserDivCode) {
        this.assignUserDivCode = assignUserDivCode;
    }

    public List<AnswerDataDTO> getAnswerDataDTO() {
        return answerDataDTO;
    }

    public void setAnswerDataDTO(List<AnswerDataDTO> answerDataDTO) {
        this.answerDataDTO = answerDataDTO;
    }

    @Override
    public String toString() {
        return "ApplicationFormDTO{" +
                "applicationFormID=" + applicationFormID +
                ", workflowTemplateID=" + workflowTemplateID +
                ", afRefNumber='" + afRefNumber + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", createdUserID=" + createdUserID +
                ", formType=" + formType +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", assignUserID=" + assignUserID +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", assignUserDivCode='" + assignUserDivCode + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", currentApplicationFormStatus=" + currentApplicationFormStatus +
                ", afCommentDTOList=" + afCommentDTOList +
                ", basicInformationDTOList=" + basicInformationDTOList +
                ", afDocumentDTOList=" + afDocumentDTOList +
                ", afFacilityDTOList=" + afFacilityDTOList +
                ", afTopicDataDTOList=" + afTopicDataDTOList +
                ", afAssignDepartmentDTOList=" + afAssignDepartmentDTOList +
                ", afStatusHistoryDTOList=" + afStatusHistoryDTOList +
                ", leadID=" + leadID +
                ", fsType='" + fsType + '\'' +
                '}';
    }

    public List<EnvironmentalRiskDataDTO> getRiskCategories() {
        return riskCategories;
    }

    public void setRiskCategories(List<EnvironmentalRiskDataDTO> riskCategories) {
        this.riskCategories = riskCategories;
    }

    public AppsConstants.YesNo getIsESGPaper() {
        return isESGPaper;
    }

    public void setIsESGPaper(AppsConstants.YesNo isESGPaper) {
        this.isESGPaper = isESGPaper;
    }

    public AppsConstants.YesNo getIsESGApproved() {
        if (this.isESGApproved == null){
            this.isESGApproved = AppsConstants.YesNo.N;
        }
        return isESGApproved;
    }

    public void setIsESGApproved(AppsConstants.YesNo isESGApproved) {
        this.isESGApproved = isESGApproved;
    }

    public String getApprovedESGScore() {
        return approvedESGScore;
    }

    public void setApprovedESGScore(String approvedESGScore) {
        this.approvedESGScore = approvedESGScore;
    }
    
    public String getExternalAppDescription() {
        return externalAppDescription;
    }

    public void setExternalAppDescription(String externalAppDescription) {
        this.externalAppDescription = externalAppDescription;
    }

    public String getExternalAppRefNumber() {
        return externalAppRefNumber;
    }

    public void setExternalAppRefNumber(String externalAppRefNumber) {
        this.externalAppRefNumber = externalAppRefNumber;
    }

    public AnalyticsDecisionDTO getAnalyticsDecision() {
        return analyticsDecision;
    }

    public void setAnalyticsDecision(AnalyticsDecisionDTO analyticsDecision) {
        this.analyticsDecision = analyticsDecision;
    }
}
