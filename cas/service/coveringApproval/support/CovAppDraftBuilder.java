package com.itechro.cas.service.coveringApproval.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.coveringApproval.CoveringApprovalDao;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.coveringApproval.CovAppBasicInfo;
import com.itechro.cas.model.domain.coveringApproval.CovAppComment;
import com.itechro.cas.model.domain.coveringApproval.CovAppStatusHistory;
import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.dto.acae.request.ACAECustomerDetailsRQ;
import com.itechro.cas.model.dto.applicationform.ApplicationFormDTO;
import com.itechro.cas.model.dto.coveringApproval.CovAppBasicInfoDTO;
import com.itechro.cas.model.dto.coveringApproval.CovAppCommentDTO;
import com.itechro.cas.model.dto.coveringApproval.CoveringApprovalDTO;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.customer.SearchCustomerRQ;
import com.itechro.cas.model.dto.integration.request.LoadBankAccountDetailsRQ;
import com.itechro.cas.model.dto.integration.response.coveringApproval.CovAppCustomerDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.coveringApproval.CoveringApprovalService;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.integration.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 *
 * @author tharushi
 */
public class CovAppDraftBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(CovAppDraftBuilder.class);

    private CoveringApprovalDTO coveringApprovalDTO;

    private CredentialsDTO credentialsDTO;

    private Date date;

    private CoveringApproval coveringApproval;

    private CoveringApprovalService coveringApprovalService;

    private CoveringApprovalDao coveringApprovalDao;

    private IntegrationService integrationService;

    private CustomerService customerService;

    private CustomerDao customerDao;

    public CovAppDraftBuilder(CoveringApprovalDTO coveringApprovalDTO, CredentialsDTO credentialsDTO) {
        this.coveringApprovalDTO = coveringApprovalDTO;
        this.credentialsDTO = credentialsDTO;
        this.date = new Date();
    }

    public CovAppDraftBuilder buildBaseCoveringApproval() throws AppsException {

        boolean isNew = coveringApprovalDTO.getCovAppId() == null;
        if(isNew) {
            coveringApproval = new CoveringApproval();
            coveringApproval.setCovAppRefNumber(this.coveringApprovalService.generateRefCode());
            coveringApproval.setCreatedDate(date);
            coveringApproval.setCreatedBy(credentialsDTO.getUserName());
            coveringApproval.setCreatedUserDisplayName(coveringApprovalDTO.getCreatedUserDisplayName());
            coveringApproval.setInProgressDate(date);
        } else {
            coveringApproval = coveringApprovalDao.getOne(coveringApprovalDTO.getCovAppId());
            coveringApproval.setModifiedDate(date);
            coveringApproval.setModifiedBy(credentialsDTO.getUserName());
        }

        coveringApproval.setAssignUserDisplayName(coveringApprovalDTO.getAssignUserDisplayName());
        coveringApproval.setAssignUserUpmGroupCode(coveringApprovalDTO.getAssignUserUpmGroupCode());
        coveringApproval.setAssignUserUpmId(coveringApprovalDTO.getAssignUserUpmId());
        coveringApproval.setAssignUserDisplayName(coveringApprovalDTO.getCreatedUserDisplayName());
        coveringApproval.setCreatedUserBranchCode(coveringApprovalDTO.getCreatedUserBranchCode());
        coveringApproval.setCreatedUserDisplayName(coveringApprovalDTO.getCreatedUserDisplayName());
        coveringApproval.setCurrentAssignUser(coveringApprovalDTO.getCurrentAssignUser());
        coveringApproval.setCurrentAssignUserDivCode(coveringApprovalDTO.getCurrentAssignUserDivCode());
        coveringApproval.setCurrentAssignUserId(coveringApprovalDTO.getCurrentAssignUserId());
        coveringApproval.setCurrentStatus(coveringApprovalDTO.getCurrentStatus());
        coveringApproval.setBranchCode(coveringApprovalDTO.getBranchCode());
        coveringApproval.setAccountNumber(coveringApprovalDTO.getAccountNumber());
        coveringApproval.setCurrentAssignUserId(coveringApproval.getCurrentAssignUserId());
        coveringApproval.setIsAuthorized(coveringApprovalDTO.getIsAuthorized());
        coveringApproval.setModifiedUserID(credentialsDTO.getUserID().toString());

        LOG.info("START : Build Base Covering Approval : {} : {}", coveringApprovalDTO, credentialsDTO.getUserID());
        return this;
    }

    public CovAppDraftBuilder buildComment() throws AppsException{
        for(CovAppCommentDTO commentDTO : coveringApprovalDTO.getCovAppCommentDTOList()){
            boolean isNewComment = commentDTO.getCommentId() == null;
            if(isNewComment){
                CovAppComment comment = new CovAppComment();
                comment.setActionMessage("Draft Covering Approval Form by " +coveringApprovalDTO.getAssignUserDisplayName());
                comment.setUserComment(commentDTO.getUserComment());
                comment.setCurrentStatus(commentDTO.getCurrentStatus());

                comment.setCreatedUserId(commentDTO.getCreatedUserId());
                comment.setCreatedUser(commentDTO.getCreatedUser());
                comment.setCreatedUserDisplayName(commentDTO.getCreatedUserDisplayName());
                comment.setCreatedUserDivCode(commentDTO.getCreatedUserDivCode());
                comment.setCreatedUserUpmCode(commentDTO.getCreatedUserUpmCode());

                comment.setIsPublic(commentDTO.getIsPublic());
                comment.setIsUsersOnly(commentDTO.getIsUsersOnly());
                comment.setIsDivisionOnly(commentDTO.getIsDivisionOnly());

                comment.setStatus(AppsConstants.Status.ACT);
                comment.setCreatedBy(credentialsDTO.getUserName());
                comment.setCreatedDate(date);

                coveringApproval.addComment(comment);
            }
        }
        LOG.info("START : Build Comments Covering Approval : {} : {}", coveringApprovalDTO, credentialsDTO.getUserID());
        return this;
    }

    public CovAppDraftBuilder buildBasicInfo() throws AppsException{

        LOG.info("START :Covering Approval Basic Info : {} : {}", coveringApprovalDTO, credentialsDTO.getUserID());

        for(CovAppBasicInfoDTO basicInfoDTO : coveringApprovalDTO.getCovAppBasicInfoDTOList()){
            boolean isNewBasicInfo = basicInfoDTO.getBasicInformationID() == null;
            if(isNewBasicInfo){
                CovAppBasicInfo covAppBasicInfo = new CovAppBasicInfo();
                covAppBasicInfo.setIdentificationNumber(basicInfoDTO.getIdentificationNumber());
                covAppBasicInfo.setIdentificationType(basicInfoDTO.getIdentificationType());
                covAppBasicInfo.setAccountNumber(basicInfoDTO.getAccountNumber());
                covAppBasicInfo.setChequeNumber(basicInfoDTO.getChequeNumber());
                covAppBasicInfo.setTranId(basicInfoDTO.getTranId());
                covAppBasicInfo.setTranDate(basicInfoDTO.getTranDate());
                covAppBasicInfo.setTranAmount(basicInfoDTO.getTranAmount());
                covAppBasicInfo.setAccounManager(basicInfoDTO.getAccounManager());
                covAppBasicInfo.setCifId(basicInfoDTO.getCustomerFinancialID());
                covAppBasicInfo.setCustomerName(basicInfoDTO.getCustomerName());
                covAppBasicInfo.setAddress(basicInfoDTO.getAddress());
                covAppBasicInfo.setBranchCode(basicInfoDTO.getBranchCode());
                covAppBasicInfo.setBranchName(basicInfoDTO.getBranchName());
                covAppBasicInfo.setClearBalance(basicInfoDTO.getClearBalance());
                covAppBasicInfo.setTranCurrency(basicInfoDTO.getTranCurrency());
                covAppBasicInfo.setPartTranSRL(basicInfoDTO.getPartTranSRL());
                covAppBasicInfo.setTranStatus(basicInfoDTO.getTranStatus());
                covAppBasicInfo.setCoveringApproval(coveringApproval);

                covAppBasicInfo.setCreatedBy(credentialsDTO.getUserName());
                covAppBasicInfo.setCreatedDate(date);

                coveringApproval.addBasicInfo(covAppBasicInfo);
            }
        }
        return this;
    }


    public CovAppDraftBuilder buildStatusHistory() throws AppsException {

        CovAppStatusHistory covAppStatusHistory = new CovAppStatusHistory();
        covAppStatusHistory.setActionMessage("Drafting Covering Approval");
        covAppStatusHistory.setCurrentStatus(coveringApprovalDTO.getCurrentStatus());
        covAppStatusHistory.setAssignUserID(coveringApprovalDTO.getCurrentAssignUserId());
        covAppStatusHistory.setAssignUser(coveringApprovalDTO.getCurrentAssignUser());
        covAppStatusHistory.setAssignUserDisplayName(coveringApprovalDTO.getAssignUserDisplayName());
        covAppStatusHistory.setAssignUserUpmID(coveringApprovalDTO.getAssignUserUpmId());
        covAppStatusHistory.setAssignUserUpmGroupCode(coveringApprovalDTO.getAssignUserUpmGroupCode());
        covAppStatusHistory.setAssignUserDivCode(coveringApprovalDTO.getCurrentAssignUserDivCode());
        covAppStatusHistory.setUpdatedUserDisplayName(coveringApprovalDTO.getAssignUserDisplayName());
        covAppStatusHistory.setUpdateBy(credentialsDTO.getUserName());
        covAppStatusHistory.setUpdateDate(date);
        covAppStatusHistory.setUpdateUserId(coveringApprovalDTO.getUpdatedUserId());
        //covAppStatusHistory.setCurrentStatus(coveringApprovalDTO.getCurrentStatus());
        LOG.info("covAppStatusHistory: {}", covAppStatusHistory);
        coveringApproval.addStatusHistory(covAppStatusHistory);

        LOG.info("START : Build Status History Covering Approval : {} : {}", coveringApprovalDTO, credentialsDTO.getUserID());
        return this;
    }

    public void setCoveringApprovalDTO(CoveringApprovalDTO coveringApprovalDTO) {
        this.coveringApprovalDTO = coveringApprovalDTO;
    }

    public void setCoveringApproval(CoveringApproval coveringApproval) {
        this.coveringApproval = coveringApproval;
    }

    public void setCoveringApprovalService(CoveringApprovalService coveringApprovalService) {
        this.coveringApprovalService = coveringApprovalService;
    }

    public void setCoveringApprovalDao(CoveringApprovalDao coveringApprovalDao) {
        this.coveringApprovalDao = coveringApprovalDao;
    }

    public void setIntegrationService(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    public CoveringApproval getCoveringApproval() {
        return coveringApproval;
    }
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }
}
