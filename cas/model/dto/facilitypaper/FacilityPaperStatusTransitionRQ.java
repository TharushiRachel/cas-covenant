package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.NumberUtil;

import javax.swing.*;
import javax.swing.text.html.HTML;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FacilityPaperStatusTransitionRQ implements Serializable {

    private Integer facilityPaperID;

    private String facilityPaperRefNumber;

    private String customerName;

    private String branchName;

    private String solID;

    private String totalExposure;

    private String paperCreatedDate;

    private String lastComment;

    private String lastCommentedUser; //Senders Display name

    private String assignUserDisplayName; // Receivers Display Name

    private String action;

    private List<FacilityDTO> facilityDTOList;

    private DomainConstants.FacilityPaperStatus newStatus;

    private DomainConstants.FacilityPaperRoutingStatus routingStatus;

    private String userName;

    private List<String> toAddresses;

    private List<String> ccAddress;

    private CredentialsDTO credentialsDTO;

    private Boolean showActiveNewFacilities;

    private String paperApprovedDate;

    private String paperDeclinedDate;

    private String committeeName;

    private String committeeAction;

    private String totalCompanyExposure;

    private String totalGroupExposure;

    private Boolean isShowActions;

    private List<FPActionDTO> membersActions;

    private AppsConstants.YesNo isESGPaper;

    private String inqComment;

    private String inqCommentedBy;

    private Boolean showInqComment;

    private Boolean isCommitteeMember;

    private String accountNumber;

    private String reviewChargeUPCData;

    public FacilityPaperStatusTransitionRQ() {
    }

    public String getCommitteeAction() {
        return committeeAction;
    }

    public void setCommitteeAction(String committeeAction) {
        this.committeeAction = committeeAction;
    }


    public String getPaperApprovedDate() {
        return paperApprovedDate;
    }

    public void setPaperApprovedDate(String paperApprovedDate) {
        this.paperApprovedDate = paperApprovedDate;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFacilityPaperRefNumber() {
        return facilityPaperRefNumber;
    }

    public void setFacilityPaperRefNumber(String facilityPaperRefNumber) {
        this.facilityPaperRefNumber = facilityPaperRefNumber;
    }

    public DomainConstants.FacilityPaperStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(DomainConstants.FacilityPaperStatus newStatus) {
        this.newStatus = newStatus;
    }

    public DomainConstants.FacilityPaperRoutingStatus getRoutingStatus() {
        return routingStatus;
    }

    public void setRoutingStatus(DomainConstants.FacilityPaperRoutingStatus routingStatus) {
        this.routingStatus = routingStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getToAddresses() {
        if (toAddresses == null) {
            toAddresses = new ArrayList<>();
        }
        return toAddresses;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTotalExposure() {
        return totalExposure;
    }

    public void setTotalExposure(String totalExposure) {
        this.totalExposure = totalExposure;
    }

    public String getPaperCreatedDate() {
        return paperCreatedDate;
    }

    public void setPaperCreatedDate(String paperCreatedDate) {
        this.paperCreatedDate = paperCreatedDate;
    }

    public String getLastComment() {
        return lastComment;
    }

    public void setLastComment(String lastComment) {
        this.lastComment = lastComment;
    }

    public String getSolID() {
        return solID;
    }

    public void setSolID(String solID) {
        this.solID = solID;
    }

    public List<FacilityDTO> getFacilityDTOList() {
        if (facilityDTOList == null) {
            this.facilityDTOList = new ArrayList<>();
        }
        return facilityDTOList;
    }

    public List<FacilityDTO> getFacilityOrderedDTOList() {
        return getFacilityDTOList().stream().sorted(new Comparator<FacilityDTO>() {
            @Override
            public int compare(FacilityDTO o1, FacilityDTO o2) {
                return o1.getDisplayOrder().compareTo(o2.getDisplayOrder());
            }
        }).collect(Collectors.toList());
    }

    public List<FacilityDTO> getFacilityOrderedNewDTOList() {
        return getFacilityDTOList().stream().filter(facility -> facility.getIsNew() == AppsConstants.YesNo.Y).sorted(new Comparator<FacilityDTO>() {
            @Override
            public int compare(FacilityDTO o1, FacilityDTO o2) {
                return o1.getDisplayOrder().compareTo(o2.getDisplayOrder());
            }
        }).collect(Collectors.toList());
    }

    public void setFacilityDTOList(List<FacilityDTO> facilityDTOList) {
        this.facilityDTOList = facilityDTOList;
    }

    public String getLastCommentedUser() {
        return lastCommentedUser;
    }

    public void setLastCommentedUser(String lastCommentedUser) {
        this.lastCommentedUser = lastCommentedUser;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public String getAction() {
        switch (this.getNewStatus()) {
            case IN_PROGRESS:
                this.action = ", has been forwarded to you";
                break;
            case APPROVED:
                this.action = ", has been approved";
                break;
            case CANCEL:
                this.action = ", has been returned to you";
                break;
            case REJECTED:
                this.action = ", has been declined";
                break;
            default:
                this.action = "";
        }
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean showActiveNewFacilities() {
        return this.getFacilityOrderedNewDTOList().size() > 0;
    }

    public List<String> getCcAddress() {
        if(ccAddress == null){
            ccAddress = new ArrayList<>();
        }
        return ccAddress;
    }

    public void setCcAddress(List<String> ccAddress) {
        this.ccAddress = ccAddress;
    }

    @Override
    public String toString() {
        return "FacilityPaperStatusTransitionRQ{" +
                "facilityPaperID=" + facilityPaperID +
                ", facilityPaperRefNumber='" + facilityPaperRefNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", solID='" + solID + '\'' +
                ", totalExposure='" + totalExposure + '\'' +
                ", paperCreatedDate='" + paperCreatedDate + '\'' +
                ", lastComment='" + lastComment + '\'' +
                ", lastCommentedUser='" + lastCommentedUser + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", facilityDTOList=" + facilityDTOList +
                ", newStatus=" + newStatus +
                ", routingStatus=" + routingStatus +
                ", userName='" + userName + '\'' +
                ", toAddresses=" + toAddresses +
                ", credentialsDTO=" + credentialsDTO +
                ", ccAddress=" + ccAddress +
                ", paperApprovedDate='" + paperApprovedDate +
                '}';
    }

    public Boolean getShowActiveNewFacilities() {
        return showActiveNewFacilities;
    }

    public void setShowActiveNewFacilities(Boolean showActiveNewFacilities) {
        this.showActiveNewFacilities = showActiveNewFacilities;
    }

    public String getPaperDeclinedDate() {
        return paperDeclinedDate;
    }

    public void setPaperDeclinedDate(String paperDeclinedDate) {
        this.paperDeclinedDate = paperDeclinedDate;
    }

    public String getFormattedAmount(BigDecimal amount) {
        return NumberUtil.getFormattedAmount(amount);
    }

    public BigDecimal getMillionValue(BigDecimal amount) {
        return NumberUtil.getMillionValue(amount);
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getTotalCompanyExposure() {
        return totalCompanyExposure;
    }

    public void setTotalCompanyExposure(String totalCompanyExposure) {
        this.totalCompanyExposure = totalCompanyExposure;
    }

    public String getTotalGroupExposure() {
        return totalGroupExposure;
    }

    public void setTotalGroupExposure(String totalGroupExposure) {
        this.totalGroupExposure = totalGroupExposure;
    }

    public List<FPActionDTO> getMembersActions() {
        return membersActions;
    }

    public void setMembersActions(List<FPActionDTO> membersActions) {
        this.membersActions = membersActions;
    }

    public Boolean getShowActions() {
        return isShowActions;
    }

    public void setShowActions(Boolean showActions) {
        isShowActions = showActions;
    }


    public AppsConstants.YesNo getIsESGPaper() {
        return isESGPaper;
    }

    public void setIsESGPaper(AppsConstants.YesNo isESGPaper) {
        this.isESGPaper = isESGPaper;
    }
    public String getInqComment() {
        return inqComment;
    }

    public void setInqComment(String inqComment) {
        this.inqComment = inqComment;
    }

    public String getInqCommentedBy() {
        return inqCommentedBy;
    }

    public void setInqCommentedBy(String inqCommentedBy) {
        this.inqCommentedBy = inqCommentedBy;
    }

    public Boolean getShowInqComment() {
        return showInqComment;
    }

    public void setShowInqComment(Boolean showInqComment) {
        this.showInqComment = showInqComment;
    }

    public Boolean getCommitteeMember() {
        return isCommitteeMember;
    }

    public void setCommitteeMember(Boolean committeeMember) {
        isCommitteeMember = committeeMember;
    }

    public String getAccountNumber() {
        if (this.accountNumber == null){
            this.accountNumber = "-";
        }
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getReviewChargeUPCData() {

        if (this.reviewChargeUPCData != null){
            String pattern = "<[^>]*id=['\"]" + "display-none-txt" + "['\"][^>]*>.*?</[^>]*>";
            this.reviewChargeUPCData = this.reviewChargeUPCData.replaceAll(pattern, "");
        }else {
            this.reviewChargeUPCData = "";
        }

        return reviewChargeUPCData;
    }

    public void setReviewChargeUPCData(String reviewChargeUPCData) {
        this.reviewChargeUPCData = reviewChargeUPCData;
    }
}
