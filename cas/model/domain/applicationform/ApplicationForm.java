package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureData;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.lead.LeadAppCode;
import com.itechro.cas.model.domain.esg.EnvironmentalRiskData;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_AF_APPLICATION_FORM")
public class ApplicationForm extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_APPLICATION_FORM")
    @SequenceGenerator(name = "SEQ_T_AF_APPLICATION_FORM", sequenceName = "SEQ_T_AF_APPLICATION_FORM", allocationSize = 1)
    @Column(name = "APPLICATION_FORM_ID")
    private Integer applicationFormID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKFLOW_TEMPLATE_ID")
    private WorkFlowTemplate workFlowTemplate;

    @Column(name = "AF_REF_NUMBER")
    private String afRefNumber;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "CREATED_USER_ID")
    private Integer createdUserID;

    @Enumerated(EnumType.STRING)
    @Column(name = "FORM_TYPE")
    private DomainConstants.BasicInformationType formType;

    @Column(name = "ASSIGN_USER_ID")
    private Integer assignUserID;

    @Column(name = "ASSIGN_USER")
    private String assignUser;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "ASSIGN_USER_UPM_ID")
    private Integer assignUserUpmID;

    @Column(name = "ASSIGN_USER_UPM_GROUP_CODE")
    private String assignUserUpmGroupCode;

    @Column(name = "ASSIGN_USER_DIV_CODE")
    private String assignUserDivCode;

    @Column(name = "LEAD_ID")
    private Integer leadID;

    @Column(name = "FS_TYPE")
    private String fsType;

    @Column(name = "ASSIGN_DEPARTMENT_CODE")
    private String assignDepartmentCode; // This is for cluster forwarding for one Branch or Department (SOL IDs) 900,827.... : if Multiple SOLs need to Deprecate this Field

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENT_APP_FORM_STATUS")
    private DomainConstants.ApplicationFormStatus currentApplicationFormStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ESG_PAPER")
    private AppsConstants.YesNo isESGPaper;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ESG_APPROVED")
    private AppsConstants.YesNo isESGApproved;

    @Column(name = "APPROVED_ESG_SCORE")
    private String approvedESGScore;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<AFComment> afCommentSet; // Not Used fot now

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<BasicInformation> basicInformationSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<AFDocument> afDocumentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<AFFacility> afFacilities;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<AFTopicData> afTopicDataSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<AFStatusHistory> afStatusHistorySet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<AFAssignDepartment> afAssignDepartmentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<AFWorkflowRouting> afWorkflowRoutingSet;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAD_ID", referencedColumnName = "LEAD_ID", insertable = false, updatable = false)
    private LeadAppCode leadAppCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<AFAnnexureData> afAnnexureDataSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "applicationForm")
    private Set<EnvironmentalRiskData> environmentalRiskSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "applicationForm")
    private List<EnvironmentalRiskData> environmentalRisks;


    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
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

    public Set<AFComment> getAfCommentSet() {
        if (afCommentSet == null) {
            afCommentSet = new HashSet<>();
        }
        return afCommentSet;
    }

    public List<AFComment> getOrderedAfComments() {
        return getAfCommentSet().stream().sorted(new Comparator<AFComment>() {
            @Override
            public int compare(AFComment o1, AFComment o2) {
                return o1.getCommentID().compareTo(o2.getCommentID());
            }
        }).collect(Collectors.toList());
    }

    public void setAfCommentSet(Set<AFComment> afCommentSet) {
        this.afCommentSet = afCommentSet;
    }

    public void addAFComment(AFComment afComment) {
        afComment.setApplicationForm(this);
        this.getAfCommentSet().add(afComment);
    }

    public AFComment getAFCommentByID(Integer commentID) {
        return this.getAfCommentSet().stream().filter(afComment ->
                commentID.equals(afComment.getCommentID()))
                .findFirst().orElse(null);
    }

    public Set<BasicInformation> getBasicInformationSet() {
        if (basicInformationSet == null) {
            this.basicInformationSet = new HashSet<>();
        }
        return basicInformationSet;
    }

    public void setBasicInformationSet(Set<BasicInformation> basicInformationSet) {
        this.basicInformationSet = basicInformationSet;
    }

    public List<BasicInformation> getOrderedBasicInformation() {
        return getBasicInformationSet().stream().sorted(new Comparator<BasicInformation>() {
            @Override
            public int compare(BasicInformation o1, BasicInformation o2) {
                return o1.getBasicInformationID().compareTo(o2.getBasicInformationID());
            }
        }).collect(Collectors.toList());
    }

    public BasicInformation getBasicInformationByID(Integer basicInformationID) {
        return this.getBasicInformationSet().stream().filter(basicInformation ->
                basicInformationID.equals(basicInformation.getBasicInformationID()))
                .findFirst().orElse(null);
    }

    public void addBasicInformation(BasicInformation basicInformation) {
        basicInformation.setApplicationForm(this);
        this.getBasicInformationSet().add(basicInformation);
    }

    public Set<AFDocument> getAfDocumentSet() {
        if (afDocumentSet == null) {
            this.afDocumentSet = new HashSet<>();
        }
        return afDocumentSet;
    }

    public List<AFDocument> getOrderedAfDocument() {
        return getAfDocumentSet().stream().sorted(new Comparator<AFDocument>() {
            @Override
            public int compare(AFDocument o1, AFDocument o2) {
                return o1.getAfDocumentID().compareTo(o2.getAfDocumentID());
            }
        }).collect(Collectors.toList());
    }

    public void setAfDocumentSet(Set<AFDocument> afDocumentSet) {
        this.afDocumentSet = afDocumentSet;
    }

    public void addAFDocument(AFDocument afDocument) {
        afDocument.setApplicationForm(this);
        this.getAfDocumentSet().add(afDocument);
    }

    public AFDocument getAFDocumentByID(Integer afDocumentID) {
        return this.getAfDocumentSet().stream().filter(afDocument ->
                afDocumentID.equals(afDocument.getAfDocumentID()))
                .findFirst().orElse(null);
    }

    public Set<AFFacility> getAfFacilities() {
        if (afFacilities == null) {
            this.afFacilities = new HashSet<>();
        }
        return afFacilities;
    }

    public void setAfFacilities(Set<AFFacility> afFacilities) {
        this.afFacilities = afFacilities;
    }

    public List<AFFacility> getOrderedAFFacilities() {
        return getAfFacilities().stream().sorted(new Comparator<AFFacility>() {
            @Override
            public int compare(AFFacility o1, AFFacility o2) {
                return o1.getDisplayOrder().compareTo(o2.getDisplayOrder());
            }
        }).collect(Collectors.toList());
    }

    public List<AFFacility> getParentAFFacilitiesFirst() {
        return getAfFacilities().stream().sorted(new Comparator<AFFacility>() {
            @Override
            public int compare(AFFacility o1, AFFacility o2) {
                return o1.getFacilityID().compareTo(o2.getFacilityID());
            }
        }).collect(Collectors.toList());
    }

    public Set<AFTopicData> getAfTopicDataSet() {
        if (afTopicDataSet == null) {
            this.afTopicDataSet = new HashSet<>();
        }
        return afTopicDataSet;
    }

    public void setAfTopicDataSet(Set<AFTopicData> afTopicDataSet) {
        this.afTopicDataSet = afTopicDataSet;
    }

    public List<AFTopicData> getOrderedAFTopicData() {
        return getAfTopicDataSet().stream().sorted(new Comparator<AFTopicData>() {
            @Override
            public int compare(AFTopicData o1, AFTopicData o2) {
                return o1.getTopicDataID().compareTo(o2.getTopicDataID());
            }
        }).collect(Collectors.toList());
    }

    public AFTopicData getAFTopicsDataByID(Integer afTopicDataID) {
        return this.getAfTopicDataSet().stream().filter(afTopicData ->
                afTopicDataID.equals(afTopicData.getTopicDataID()))
                .findFirst().orElse(null);
    }

    public void addAFTopicData(AFTopicData afTopicData) {
        afTopicData.setApplicationForm(this);
        this.getAfTopicDataSet().add(afTopicData);
    }

    public DomainConstants.BasicInformationType getFormType() {
        return formType;
    }

    public void setFormType(DomainConstants.BasicInformationType formType) {
        this.formType = formType;
    }

    public Set<AFStatusHistory> getAfStatusHistorySet() {
        if (afStatusHistorySet == null) {
            afStatusHistorySet = new HashSet<>();
        }
        return afStatusHistorySet;
    }

    public List<AFStatusHistory> getOrderedAfStatusHistorySet() {
        return getAfStatusHistorySet().stream().sorted(new Comparator<AFStatusHistory>() {
            @Override
            public int compare(AFStatusHistory o1, AFStatusHistory o2) {
                return o1.getStatusHistoryID().compareTo(o2.getStatusHistoryID());
            }
        }).collect(Collectors.toList());
    }

    public void addAFStatusHistory(AFStatusHistory afStatusHistory) {
        afStatusHistory.setApplicationForm(this);
        this.getAfStatusHistorySet().add(afStatusHistory);
    }

    public void setAfStatusHistorySet(Set<AFStatusHistory> afStatusHistorySet) {
        this.afStatusHistorySet = afStatusHistorySet;
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

    public String getAssignUserDivCode() {
        return assignUserDivCode;
    }

    public void setAssignUserDivCode(String assignUserDivCode) {
        this.assignUserDivCode = assignUserDivCode;
    }

    public BasicInformation getPrimaryBasicInformation() {
        return this.getBasicInformationSet().stream().filter(basicInformation ->
                basicInformation.getPrimaryInformation() == AppsConstants.YesNo.Y)
                .findFirst().orElse(null);
    }

    public WorkFlowTemplate getWorkFlowTemplate() {
        return workFlowTemplate;
    }

    public void setWorkFlowTemplate(WorkFlowTemplate workFlowTemplate) {
        this.workFlowTemplate = workFlowTemplate;
    }

    public AFFacility getActiveFacilityByID(Integer facilityID) {
        return this.getAfFacilities().stream().filter(facility ->
                {
                    return facilityID.equals(facility.getFacilityID()) && facility.getStatus() == AppsConstants.Status.ACT;
                }
        ).findFirst().orElse(null);
    }

    public void addAFFacilities(AFFacility afFacility) {
        afFacility.setApplicationForm(this);
        this.getAfFacilities().add(afFacility);
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public Set<AFAssignDepartment> getAfAssignDepartmentSet() {
        if (afAssignDepartmentSet == null) {
            this.afAssignDepartmentSet = new HashSet<>();
        }
        return afAssignDepartmentSet;
    }

    public AFAssignDepartment getActiveAssignDepartmentBySolIDAndUserGroupUPMGroup(String divCode, String userGroupUPMCode) {
        return this.getAfAssignDepartmentSet().stream().filter(afAssignDepartment ->
                {
                    return divCode.equals(afAssignDepartment.getDivCode()) && userGroupUPMCode.equals(afAssignDepartment.getUserGroupUPMCode());
                }
        ).findFirst().orElse(null);
    }

    public void setAfAssignDepartmentSet(Set<AFAssignDepartment> afAssignDepartmentSet) {
        this.afAssignDepartmentSet = afAssignDepartmentSet;
    }

    public void addAFAssignDepartment(AFAssignDepartment afAssignDepartment) {
        afAssignDepartment.setApplicationForm(this);
        this.getAfAssignDepartmentSet().add(afAssignDepartment);
    }

    public Set<AFAssignDepartment> getActiveAssignDepartmentSet() {
        return this.getAfAssignDepartmentSet().stream().filter(assignDepartment ->
                assignDepartment.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toSet());
    }

    public Set<AFWorkflowRouting> getAfWorkflowRoutingSet() {
        if (afAssignDepartmentSet == null) {
            this.afAssignDepartmentSet = new HashSet<>();
        }
        return afWorkflowRoutingSet;
    }

    public void setAfWorkflowRoutingSet(Set<AFWorkflowRouting> afWorkflowRoutingSet) {
        this.afWorkflowRoutingSet = afWorkflowRoutingSet;
    }

    public void addAfWorkflowRouting(AFWorkflowRouting afWorkflowRouting) {
        afWorkflowRouting.setApplicationForm(this);
        this.getAfWorkflowRoutingSet().add(afWorkflowRouting);
    }

    public Set<AFAnnexureData> getAfAnnexureDataSet() {
        if (afAnnexureDataSet == null) {
            this.afAnnexureDataSet = new HashSet<>();
        }
        return afAnnexureDataSet;
    }

    public void setAfAnnexureDataSet(Set<AFAnnexureData> afAnnexureDataSet) {
        this.afAnnexureDataSet = afAnnexureDataSet;
    }

    public List<EnvironmentalRiskData> getEnvironmentalRisks() {
        if (environmentalRisks == null){
            return new ArrayList<>();
        }
        return environmentalRisks;
    }

    public void setEnvironmentalRisks(List<EnvironmentalRiskData> environmentalRisks) {
        this.environmentalRisks = environmentalRisks;
    }

    public AppsConstants.YesNo getIsESGPaper() {
        if (this.isESGPaper == null){
            this.isESGPaper = AppsConstants.YesNo.N;
        }
        return isESGPaper;
    }

    public void setIsESGPaper(AppsConstants.YesNo isESGPaper) {
        this.isESGPaper = isESGPaper;
    }

    public Set<EnvironmentalRiskData> getEnvironmentalRiskSet() {
        if (environmentalRiskSet == null) {
            this.environmentalRiskSet = new HashSet<>();
        }
        return environmentalRiskSet;
    }

    public void setEnvironmentalRiskSet(Set<EnvironmentalRiskData> environmentalRiskSet) {
        this.environmentalRiskSet = environmentalRiskSet;
    }

    public void addEnvironmentalRisk(EnvironmentalRiskData environmentalRiskData) {
        environmentalRiskData.setApplicationForm(this);
        this.getEnvironmentalRiskSet().add(environmentalRiskData);
    }

    public AppsConstants.YesNo getIsESGApproved() {
        if(this.isESGApproved == null){
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

    public LeadAppCode getLeadAppCode() {
        return leadAppCode;
    }

    public void setLeadAppCode(LeadAppCode leadAppCode) {
        this.leadAppCode = leadAppCode;
    }

}
