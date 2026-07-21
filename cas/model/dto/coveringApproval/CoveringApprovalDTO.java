package com.itechro.cas.model.dto.coveringApproval;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.coveringApproval.CovAppBasicInfo;
import com.itechro.cas.model.domain.coveringApproval.CovAppComment;
import com.itechro.cas.model.domain.coveringApproval.CovAppStatusHistory;
import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.dto.applicationform.AFCommentDTO;
import com.itechro.cas.service.lead.LeadService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CoveringApprovalDTO implements Serializable {

    private Integer covAppId;

    private DomainConstants.CoveringApprovalStatus currentStatus;

    private String currentAssignUserId;

    private String accountNumber;

    private String covAppRefNumber;

    private String branchCode;

    private String createdUserDisplayName;

    private String assignUserDisplayName;

    private String createdUserBranchCode;

    private String currentAssignUserAD;

    private String currentAssignUserDivCode;

    private String assignUserUpmId;

    private String assignUserUpmGroupCode;

    private Date inProgressDate;

    private Date approveDate;

    private String currentAuthorityLevel;

    private String currentAssignUser;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private String updatedUserId;

    private Boolean isAuthorized;

    private List<CovAppCommentDTO> covAppCommentDTOList;

    private List<CovAppBasicInfoDTO> covAppBasicInfoDTOList;

    private List<CovAppStatusHistoryDTO> covAppStatusHistoryDTOList;

    public CoveringApprovalDTO() {
    }

    public CoveringApprovalDTO(CoveringApproval coveringApproval, CovAppLoadOptionDTO covAppLoadOptionDTO){
        this.covAppId = coveringApproval.getCovAppId();
        this.covAppRefNumber = coveringApproval.getCovAppRefNumber();
        this.currentStatus  = coveringApproval.getCurrentStatus();
        this.createdUserDisplayName = coveringApproval.getCreatedUserDisplayName();
        this.assignUserDisplayName = coveringApproval.getAssignUserDisplayName();
        this.createdUserBranchCode = coveringApproval.getCreatedUserBranchCode();
        this.currentAssignUserAD = coveringApproval.getCurrentAssignUserAD();
        this.currentAssignUserDivCode = coveringApproval.getCurrentAssignUserDivCode();
        this.assignUserUpmId = coveringApproval.getAssignUserUpmId();
        this.assignUserUpmGroupCode = coveringApproval.getAssignUserUpmGroupCode();
        this.inProgressDate = coveringApproval.getInProgressDate();
        this.approveDate = coveringApproval.getApproveDate();
        this.currentAuthorityLevel = coveringApproval.getCurrentAuthorityLevel();
        this.currentAssignUser = coveringApproval.getCurrentAssignUser();
        this.createdDate = coveringApproval.getCreatedDate();
        this.createdBy = coveringApproval.getCreatedBy();
        this.modifiedDate = coveringApproval.getModifiedDate();
        this.modifiedBy = coveringApproval.getModifiedBy();
        this.branchCode = coveringApproval.getBranchCode();
        this.accountNumber = coveringApproval.getAccountNumber();
        this.currentAssignUserId = coveringApproval.getCurrentAssignUserId();
        this.isAuthorized = coveringApproval.getIsAuthorized();

       if(covAppLoadOptionDTO != null){

           if(covAppLoadOptionDTO.isLoadComments()){
               if(!coveringApproval.getCovAppComments().isEmpty()){
                   for(CovAppComment comment : coveringApproval.getCovAppComments()){
                       if(comment.getStatus() == AppsConstants.Status.ACT){
                           this.getCovAppCommentDTOList().add(new CovAppCommentDTO(comment));
                       }
                   }
               }
           }

            if(covAppLoadOptionDTO.isLoadBasicInformation()){
                if(!coveringApproval.getCovAppBasicInfoList().isEmpty()){
                    for(CovAppBasicInfo covAppBasicInfo : coveringApproval.getCovAppBasicInfoList()){
                        this.getCovAppBasicInfoDTOList().add(new CovAppBasicInfoDTO(covAppBasicInfo));
                    }
                }
            }

            if(covAppLoadOptionDTO.isLoadStatusHistory()){
                for (CovAppStatusHistory covAppStatusHistory : coveringApproval.getCovAppStatusHistoryList()){
                    this.getCovAppStatusHistoryDTOList().add(new CovAppStatusHistoryDTO(covAppStatusHistory));
                }
            }
       }
    }

    public CoveringApprovalDTO(CoveringApproval coveringApproval1) {
    }

    public List<CovAppCommentDTO> getCovAppCommentDTOList() {
        if(covAppCommentDTOList == null){
            this.covAppCommentDTOList = new ArrayList<>();
        }
        return covAppCommentDTOList;
    }

    public List<CovAppBasicInfoDTO> getCovAppBasicInfoDTOList() {
        if(covAppBasicInfoDTOList == null){
            this.covAppBasicInfoDTOList = new ArrayList<>();
        }
        return covAppBasicInfoDTOList;
    }

    public List<CovAppStatusHistoryDTO> getCovAppStatusHistoryDTOList() {
        if(covAppStatusHistoryDTOList == null){
            this.covAppStatusHistoryDTOList = new ArrayList<>();
        }
        return covAppStatusHistoryDTOList;
    }
}
