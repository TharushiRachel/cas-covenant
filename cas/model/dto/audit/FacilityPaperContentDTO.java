package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class FacilityPaperContentDTO extends BaseContentDTO {
    
    @SerializedName("FACILITY PAPER ID")
    private Integer facilityPaperID;

    @SerializedName("UPC TEMPLATE ID")
    private Integer upcTemplateID;

    @SerializedName("UPC TEMPLATE NAME")
    private String upcTemplateName;

    @SerializedName("WORK FLOW TEMPLATE ID")
    private Integer workFlowTemplateID;

    @SerializedName("WORK FLOW TEMPLATE NAME")
    private String workFlowTemplateName;

    @SerializedName("BRANCH CODE")
    private String branchCode;

    @SerializedName("FP REF NUMBER")
    private String fpRefNumber;

    @SerializedName("LEAD REF NUMBER")
    private String leadRefNumber;

    @SerializedName("IS COOPERATE")
    private String isCooperate;

    @SerializedName("FACILITY PAPER NUMBER")
    private String facilityPaperNumber;

    @SerializedName("BANK ACCOUNT ID")
    private String bankAccountID;

    @SerializedName("FP APPROVING AUTHORITY LEVEL")
    private String fpApprovingAuthorityLevel;

    @SerializedName("CURRENT FACILITY PAPER STATUS")
    private String currentFacilityPaperStatus;

    @SerializedName("CURRENT AUTHORITY LEVEL")
    private String currentAuthorityLevel;

    @SerializedName("CURRENT ASSIGN USER")
    private String currentAssignUser;

    @SerializedName("ROA PERCENTAGE EXISTING")
    private Double existingFacilitiesROA;

    @SerializedName("ROA PERCENTAGE PROPOSED")
    private Double proposedFacilitiesROA;

    @SerializedName("TOTAL DIRECT EXPOSURE PRE")
    private BigDecimal totalDirectExposurePrevious;

    @SerializedName("TOTAL DIRECT EXPOSURE NEW")
    private BigDecimal totalDirectExposureNew;

    @SerializedName("TOTAL INDIRECT EXPOSURE PRE")
    private BigDecimal totalIndirectExposurePrevious;

    @SerializedName("TOTAL INDIRECT EXPOSURE NEW")
    private BigDecimal totalIndirectExposureNew;

    @SerializedName("TOTAL EXPOSURE PREVIOUS")
    private BigDecimal totalExposurePrevious;

    @SerializedName("TOTAL EXPOSURE NEW")
    private BigDecimal totalExposureNew;

    @SerializedName("ADD TOTAL EXP TO GROUP EXP")
    private String addTotalExposureToGroup;

    @SerializedName("GRP TOTAL DIRECT EXPOSURE PRE")
    private BigDecimal groupTotalDirectExposurePrevious;

    @SerializedName("GRP TOTAL DIRECT EXPOSURE NEW")
    private BigDecimal groupTotalDirectExposureNew;

    @SerializedName("GRP TOTAL INDIR EXPOSURE PRE")
    private BigDecimal groupTotalIndirectExposurePrevious;

    @SerializedName("GRP TOTAL INDIR EXPOSURE NEW")
    private BigDecimal groupTotalIndirectExposureNew;

    @SerializedName("GRP TOTAL EXPOSURE PRE")
    private BigDecimal groupTotalExposurePrevious;

    @SerializedName("GRP TOTAL EXPOSURE NEW")
    private BigDecimal groupTotalExposureNew;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
    }

    public String getUpcTemplateName() {
        return upcTemplateName;
    }

    public void setUpcTemplateName(String upcTemplateName) {
        this.upcTemplateName = upcTemplateName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public String getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(String isCooperate) {
        this.isCooperate = isCooperate;
    }

    public String getFacilityPaperNumber() {
        return facilityPaperNumber;
    }

    public void setFacilityPaperNumber(String facilityPaperNumber) {
        this.facilityPaperNumber = facilityPaperNumber;
    }

    public String getBankAccountID() {
        return bankAccountID;
    }

    public void setBankAccountID(String bankAccountID) {
        this.bankAccountID = bankAccountID;
    }

    public String getFpApprovingAuthorityLevel() {
        return fpApprovingAuthorityLevel;
    }

    public void setFpApprovingAuthorityLevel(String fpApprovingAuthorityLevel) {
        this.fpApprovingAuthorityLevel = fpApprovingAuthorityLevel;
    }

    public String getCurrentFacilityPaperStatus() {
        return currentFacilityPaperStatus;
    }

    public void setCurrentFacilityPaperStatus(String currentFacilityPaperStatus) {
        this.currentFacilityPaperStatus = currentFacilityPaperStatus;
    }

    public String getCurrentAuthorityLevel() {
        return currentAuthorityLevel;
    }

    public void setCurrentAuthorityLevel(String currentAuthorityLevel) {
        this.currentAuthorityLevel = currentAuthorityLevel;
    }

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }

    public Double getExistingFacilitiesROA() {
        return existingFacilitiesROA;
    }

    public void setExistingFacilitiesROA(Double existingFacilitiesROA) {
        this.existingFacilitiesROA = existingFacilitiesROA;
    }

    public Double getProposedFacilitiesROA() {
        return proposedFacilitiesROA;
    }

    public void setProposedFacilitiesROA(Double proposedFacilitiesROA) {
        this.proposedFacilitiesROA = proposedFacilitiesROA;
    }

    public Integer getWorkFlowTemplateID() {
        return workFlowTemplateID;
    }

    public void setWorkFlowTemplateID(Integer workFlowTemplateID) {
        this.workFlowTemplateID = workFlowTemplateID;
    }

    public String getWorkFlowTemplateName() {
        return workFlowTemplateName;
    }

    public void setWorkFlowTemplateName(String workFlowTemplateName) {
        this.workFlowTemplateName = workFlowTemplateName;
    }

    public String getLeadRefNumber() {
        return leadRefNumber;
    }

    public void setLeadRefNumber(String leadRefNumber) {
        this.leadRefNumber = leadRefNumber;
    }

    public BigDecimal getTotalDirectExposurePrevious() {
        return totalDirectExposurePrevious;
    }

    public void setTotalDirectExposurePrevious(BigDecimal totalDirectExposurePrevious) {
        this.totalDirectExposurePrevious = totalDirectExposurePrevious;
    }

    public BigDecimal getTotalDirectExposureNew() {
        return totalDirectExposureNew;
    }

    public void setTotalDirectExposureNew(BigDecimal totalDirectExposureNew) {
        this.totalDirectExposureNew = totalDirectExposureNew;
    }

    public BigDecimal getTotalIndirectExposurePrevious() {
        return totalIndirectExposurePrevious;
    }

    public void setTotalIndirectExposurePrevious(BigDecimal totalIndirectExposurePrevious) {
        this.totalIndirectExposurePrevious = totalIndirectExposurePrevious;
    }

    public BigDecimal getTotalIndirectExposureNew() {
        return totalIndirectExposureNew;
    }

    public void setTotalIndirectExposureNew(BigDecimal totalIndirectExposureNew) {
        this.totalIndirectExposureNew = totalIndirectExposureNew;
    }

    public BigDecimal getTotalExposurePrevious() {
        return totalExposurePrevious;
    }

    public void setTotalExposurePrevious(BigDecimal totalExposurePrevious) {
        this.totalExposurePrevious = totalExposurePrevious;
    }

    public BigDecimal getTotalExposureNew() {
        return totalExposureNew;
    }

    public void setTotalExposureNew(BigDecimal totalExposureNew) {
        this.totalExposureNew = totalExposureNew;
    }

    public String getAddTotalExposureToGroup() {
        return addTotalExposureToGroup;
    }

    public void setAddTotalExposureToGroup(String addTotalExposureToGroup) {
        this.addTotalExposureToGroup = addTotalExposureToGroup;
    }

    public BigDecimal getGroupTotalDirectExposurePrevious() {
        return groupTotalDirectExposurePrevious;
    }

    public void setGroupTotalDirectExposurePrevious(BigDecimal groupTotalDirectExposurePrevious) {
        this.groupTotalDirectExposurePrevious = groupTotalDirectExposurePrevious;
    }

    public BigDecimal getGroupTotalDirectExposureNew() {
        return groupTotalDirectExposureNew;
    }

    public void setGroupTotalDirectExposureNew(BigDecimal groupTotalDirectExposureNew) {
        this.groupTotalDirectExposureNew = groupTotalDirectExposureNew;
    }

    public BigDecimal getGroupTotalIndirectExposurePrevious() {
        return groupTotalIndirectExposurePrevious;
    }

    public void setGroupTotalIndirectExposurePrevious(BigDecimal groupTotalIndirectExposurePrevious) {
        this.groupTotalIndirectExposurePrevious = groupTotalIndirectExposurePrevious;
    }

    public BigDecimal getGroupTotalIndirectExposureNew() {
        return groupTotalIndirectExposureNew;
    }

    public void setGroupTotalIndirectExposureNew(BigDecimal groupTotalIndirectExposureNew) {
        this.groupTotalIndirectExposureNew = groupTotalIndirectExposureNew;
    }

    public BigDecimal getGroupTotalExposurePrevious() {
        return groupTotalExposurePrevious;
    }

    public void setGroupTotalExposurePrevious(BigDecimal groupTotalExposurePrevious) {
        this.groupTotalExposurePrevious = groupTotalExposurePrevious;
    }

    public BigDecimal getGroupTotalExposureNew() {
        return groupTotalExposureNew;
    }

    public void setGroupTotalExposureNew(BigDecimal groupTotalExposureNew) {
        this.groupTotalExposureNew = groupTotalExposureNew;
    }
}
