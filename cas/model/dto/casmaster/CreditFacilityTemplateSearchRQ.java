package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CreditFacilityTemplateSearchRQ extends PagedParamDTO implements Serializable {

    private Integer creditFacilityTemplateID;

    private Integer creditFacilityTypeID;

    private String creditFacilityName;

    private String facilityTypeName;

    private String description;

    private BigDecimal maxFacilityAmount;

    private BigDecimal minFacilityAmount;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private List<DomainConstants.MasterDataApproveStatus> approveStatusList;

    private String approvedDateStr;

    private String approvedBy;



    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
    }

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
    }

    public String getFacilityTypeName() {
        return facilityTypeName;
    }

    public void setFacilityTypeName(String facilityTypeName) {
        this.facilityTypeName = facilityTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMaxFacilityAmount() {
        return maxFacilityAmount;
    }

    public void setMaxFacilityAmount(BigDecimal maxFacilityAmount) {
        this.maxFacilityAmount = maxFacilityAmount;
    }

    public BigDecimal getMinFacilityAmount() {
        return minFacilityAmount;
    }

    public void setMinFacilityAmount(BigDecimal minFacilityAmount) {
        this.minFacilityAmount = minFacilityAmount;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public List<DomainConstants.MasterDataApproveStatus> getApproveStatusList() {
        return approveStatusList;
    }

    public void setApproveStatusList(List<DomainConstants.MasterDataApproveStatus> approveStatusList) {
        this.approveStatusList = approveStatusList;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }



    @Override
    public String toString() {
        return "CreditFacilityTemplateDTO{" +
                "creditFacilityTemplateID=" + creditFacilityTemplateID +
                "creditFacilityTypeID=" + creditFacilityTypeID +
                "creditFacilityName=" + creditFacilityName +
                "facilityTypeName=" + facilityTypeName +
                "description=" + description +
                "status=" + status +
                "maxFacilityAmount=" + maxFacilityAmount +
                "minFacilityAmount=" + minFacilityAmount +
                "approveStatus=" + approveStatus +
                "approvedDateStr=" + approvedDateStr +
                '}';
    }
}
