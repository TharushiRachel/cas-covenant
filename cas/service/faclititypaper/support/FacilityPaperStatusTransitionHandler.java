package com.itechro.cas.service.faclititypaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.CasBranchDepartmentCode;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.casmaster.jdbc.MasterDataJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.facilitypaper.FPAssignDepartment;
import com.itechro.cas.model.domain.facilitypaper.FPComment;
import com.itechro.cas.model.domain.facilitypaper.FPCreditRiskComment;
import com.itechro.cas.model.domain.facilitypaper.FPStatusHistory;
import com.itechro.cas.model.domain.facilitypaper.facility.CommitteePaper;
import com.itechro.cas.model.domain.facilitypaper.facility.CommitteeStatusHistory;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.facilitypaper.*;
import com.itechro.cas.service.faclititypaper.command.EmailNotificationSendable;
import com.itechro.cas.service.faclititypaper.command.FacilityPaperModificationContext;
import com.itechro.cas.util.WorkGroupUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class FacilityPaperStatusTransitionHandler implements EmailNotificationSendable {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperStatusTransitionHandler.class);

    private String assistantUPMGroup = Integer.toString(WorkGroupUtil.getAssistantUPMGroup());

    protected FacilityPaperModificationContext context;

    private CasProperties casProperties;

    protected FacilityPaperStatusTransitionRQ emailRQ;

    private MasterDataJdbcDao masterDataJdbcDao;

    public FacilityPaperStatusTransitionHandler(FacilityPaperModificationContext context, CasProperties casProperties) {
        this.context = context;
        this.casProperties = casProperties;
    }

    public FacilityPaperStatusTransitionHandler(FacilityPaperModificationContext context) {
        this.context = context;
    }

    public void transfer() throws AppsException {
        FacilityPaper facilityPaper = context.getFacilityPaper();
        FacilityPaperUpdateDTO facilityPaperUpdateDto = context.getFacilityPaperUpdateDto();
        LOG.info("facilityPaperUpdateDto: {}",facilityPaperUpdateDto);


        if (isValidStatusChange(context)) {
            FPStatusHistory fpStatusHistory = new FPStatusHistory();
            fpStatusHistory.setFacilityPaperStatus(facilityPaperUpdateDto.getFacilityPaperStatus());
//            fpStatusHistory.setRemark(facilityPaperUpdateDto.getComment());
            fpStatusHistory.setUpdateBy(facilityPaperUpdateDto.getUpdatedByUserDisplayName());
            fpStatusHistory.setUpdatedUser(context.getCredentialsDto().getUserName());
            fpStatusHistory.setUpdateDate(context.getDate());
            fpStatusHistory.setAuthorityLevel(facilityPaper.getCurrentAuthorityLevel());
            fpStatusHistory.setWorkflowOrder(facilityPaper.getCurrentCycle() + "");
            fpStatusHistory.setActionMessage(facilityPaperUpdateDto.getActionMessage());
            fpStatusHistory.setForwardType(facilityPaperUpdateDto.getForwardType());
            setLogsViewOptions(fpStatusHistory, AppsConstants.YesNo.N, AppsConstants.YesNo.N, AppsConstants.YesNo.Y);//Default status history is public

            LOG.info("getForwardType(): {}",facilityPaperUpdateDto.getForwardType());
            if (facilityPaperUpdateDto.getForwardType() == DomainConstants.ForwardType.DIRECT_USER) {
                //Committee Approval
                LOG.info("getAssignDepartmentCode: {}",facilityPaperUpdateDto.getAssignDepartmentCode());
                if (facilityPaperUpdateDto.getAssignDepartmentCode() != null && facilityPaperUpdateDto.getAssignDepartmentCode().equals("CA")){
                    CommitteePaper committeePaper = null;
                    facilityPaper.setAssignUserUpmID(null);
                    facilityPaper.setCurrentAssignUser(facilityPaperUpdateDto.getCurrentAssignUser());
                    facilityPaper.setCurrentAssignUserID(null);
                    facilityPaper.setCurrentAssignADUserID(null);
                    facilityPaper.setCurrentAssignUserDivCode(null);
                    facilityPaper.setAssignUserUpmGroupCode(null);
                    facilityPaper.setAssignUserDisplayName(facilityPaperUpdateDto.getAssignUserDisplayName());
                    facilityPaper.setAssignDepartmentCode(facilityPaperUpdateDto.getAssignDepartmentCode());

                    for (CommitteePaperDTO committeePaperDTO : facilityPaperUpdateDto.getCommitteePaperDTOList()) {
                        CommitteeStatusHistory committeeStatusHistory  = new CommitteeStatusHistory();

                        if(committeePaperDTO.getCommitteePaperID() != null){
                             committeePaper = facilityPaper.getCommitteePaperByID(committeePaperDTO.getCommitteePaperID());
                        }

                        if (committeePaper == null) {
                            LOG.info("Facility Paper ID: {}",facilityPaperUpdateDto.getFacilityPaperID());
                            CommitteePaper previousCommitteePaper = facilityPaper.getCommitteePaperByFacilityPaperID(facilityPaperUpdateDto.getFacilityPaperID());
                            if (previousCommitteePaper != null){
                                LOG.info("Previous Committee Paper ID: {}",previousCommitteePaper.getCommitteePaperID());
                                previousCommitteePaper.setStatus("INA");
                                facilityPaper.addCommitteePaper(previousCommitteePaper);
                            }
                            committeePaper = new CommitteePaper();
                            facilityPaper.addCommitteePaper(committeePaper);
                            committeePaper.setCreatedBy(context.getCredentialsDto().getUserName());
                            committeePaper.setCreatedDate(context.getDate());
                            committeePaper.setCreatedUserDisplayName(facilityPaperUpdateDto.getUpdatedByUserDisplayName());
                        } else {
                            committeePaper.setModifiedBy(context.getCredentialsDto().getUserName());
                            committeePaper.setModifiedDate(context.getDate());
                        }

                        String currentPath = masterDataJdbcDao.getCommitteeCurrentPath(committeePaperDTO.getCommitteeID());

                        committeePaper.setCommitteeType(committeePaperDTO.getCommitteeType());
                        committeePaper.setCommitteeTypeID(committeePaperDTO.getCommitteeTypeID());
                        committeePaper.setCommitteeID(committeePaperDTO.getCommitteeID());
                        committeePaper.setCommitteeName(committeePaperDTO.getCommitteeName());
//                        committeePaper.setCurrentRegLevelID(committeePaperDTO.getCurrentRegLevelID());
//                        committeePaper.setCurrentAltLevelID(committeePaperDTO.getCurrentAltLevelID());
                        committeePaper.setCurrentRegRecommendedCount(committeePaperDTO.getCurrentRegRecommendedCount());
                        committeePaper.setCurrentAltRecommendedCount(committeePaperDTO.getCurrentAltRecommendedCount());
                      //  committeePaper.setCurrentPath(committeePaperDTO.getCurrentPath());

                        if (!Objects.equals(currentPath, committeePaperDTO.getCurrentPath())){
                            committeePaper.setCurrentPath(currentPath);

                            int maxLevel = masterDataJdbcDao.getCommitteePathMaxLevel(committeePaperDTO.getCommitteeID(),currentPath);

                            if (Objects.equals(currentPath, "REG")){
                                committeePaper.setCurrentRegLevelID(maxLevel);
                                committeePaper.setCurrentAltLevelID(0);
                            }

                            if (Objects.equals(currentPath, "ALT")){
                                committeePaper.setCurrentAltLevelID(maxLevel);
                                committeePaper.setCurrentRegLevelID(0);
                            }

                        }else {
                            committeePaper.setCurrentPath(committeePaperDTO.getCurrentPath());
                            committeePaper.setCurrentRegLevelID(committeePaperDTO.getCurrentRegLevelID());
                            committeePaper.setCurrentAltLevelID(committeePaperDTO.getCurrentAltLevelID());
                        }

                        committeePaper.setCurrentCommitteePaperStatus(facilityPaperUpdateDto.getCurrentCommitteePaperStatus());

                        committeeStatusHistory.setCommitteePaperStatus(facilityPaperUpdateDto.getCurrentCommitteePaperStatus());
                        committeeStatusHistory.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.IN_PROGRESS.toString());
                        committeeStatusHistory.setCreatedBy(context.getCredentialsDto().getUserName());
                        committeeStatusHistory.setCreatedDate(context.getDate());
                        committeeStatusHistory.setPathType(committeePaperDTO.getCurrentPath());
                        committeeStatusHistory.setCreatedUserDisplayName(facilityPaperUpdateDto.getUpdatedByUserDisplayName());
                        committeeStatusHistory.setActionMessage("Forwarded by " + facilityPaperUpdateDto.getUpdatedByUserDisplayName());
                        committeePaper.setCurrentCommitteePaperStatus("FORWARDED");
                        committeePaper.setStatus("ACT");
                        committeeStatusHistory.setFacilityPaper(committeePaper.getFacilityPaper());
                        committeePaper.addCommitteeStatusHistory(committeeStatusHistory);
                    }

                    fpStatusHistory.setAssignUserID(null);
                    fpStatusHistory.setAssignUser(null);
                    fpStatusHistory.setAssignUserDisplayName(null);
                    fpStatusHistory.setAssignUserUpmID(null);
                    fpStatusHistory.setAssignUserUpmGroupCode(null);
                    fpStatusHistory.setAssignUserDivCode(null);

                    fpStatusHistory.setAssignDepartmentCode(facilityPaperUpdateDto.getAssignDepartmentCode());

                }else{
                    if (facilityPaperUpdateDto.getAssignUser() == null || facilityPaperUpdateDto.getAssignUserID() == null) {
                        LOG.error("ERROR: Unable to transfer Facility Paper  : Invalid Request : {}", facilityPaperUpdateDto);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_INVALID_FACILITY_PAPER_UPDATE_REQUEST);
                    }

                    for (FPAssignDepartment fpAssignDepartment : facilityPaper.getActiveAssignDepartmentSet()) {
                        fpAssignDepartment.setStatus(AppsConstants.Status.INA);
                    }

//              Here is to mark the history to keep public or private to the users.
                    if ((facilityPaperUpdateDto.getAssignUserUpmGroupCode() != null && facilityPaperUpdateDto.getAssignUserUpmGroupCode().equals(assistantUPMGroup))
                            || (facilityPaper.getAssignUserUpmGroupCode() != null && facilityPaper.getAssignUserUpmGroupCode().equals(assistantUPMGroup))) {
                        setLogsViewOptions(fpStatusHistory, AppsConstants.YesNo.Y, AppsConstants.YesNo.N, AppsConstants.YesNo.N);
                    } else {
                        setLogsViewOptions(fpStatusHistory, AppsConstants.YesNo.N, AppsConstants.YesNo.N, AppsConstants.YesNo.Y);
                    }

                    facilityPaper.setAssignUserUpmID(facilityPaperUpdateDto.getAssignUserUpmID());
                    facilityPaper.setCurrentAssignUser(facilityPaperUpdateDto.getAssignUser());
                    facilityPaper.setCurrentAssignUserID(facilityPaperUpdateDto.getAssignUserID());
                    facilityPaper.setCurrentAssignADUserID(facilityPaperUpdateDto.getAssignADUserID());
                    facilityPaper.setCurrentAssignUserDivCode(facilityPaperUpdateDto.getAssignUserDivCode());
                    facilityPaper.setAssignUserDisplayName(facilityPaperUpdateDto.getAssignUserDisplayName());
                    facilityPaper.setAssignUserUpmGroupCode(facilityPaperUpdateDto.getAssignUserUpmGroupCode());
                    facilityPaper.setAssignDepartmentCode(null);

                    fpStatusHistory.setAssignUserID(facilityPaperUpdateDto.getAssignUserID());
                    fpStatusHistory.setAssignUser(facilityPaperUpdateDto.getAssignUser());
                    fpStatusHistory.setAssignUserDisplayName(facilityPaperUpdateDto.getAssignUserDisplayName());
                    fpStatusHistory.setAssignUserUpmID(facilityPaperUpdateDto.getAssignUserUpmID());
                    fpStatusHistory.setAssignUserUpmGroupCode(facilityPaperUpdateDto.getAssignUserUpmGroupCode());
                    fpStatusHistory.setAssignUserDivCode(facilityPaperUpdateDto.getAssignUserDivCode());
                    fpStatusHistory.setAssignDepartmentCode(null);
                }

            } else if (facilityPaperUpdateDto.getForwardType() == DomainConstants.ForwardType.OTHER_SOL_USER_GROUP || facilityPaperUpdateDto.getForwardType() == DomainConstants.ForwardType.SAME_SOL_USER_GROUP) {

                if (facilityPaperUpdateDto.getAssignDepartmentCode() == null) {
                    LOG.error("ERROR: Unable to transfer Facility Paper : Invalid Request : {}", facilityPaperUpdateDto);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_INVALID_FACILITY_PAPER_UPDATE_REQUEST);
                }

                facilityPaper.setAssignUserUpmID(null);
                facilityPaper.setCurrentAssignUser(null);
                facilityPaper.setCurrentAssignUserID(null);
                facilityPaper.setCurrentAssignADUserID(null);
                facilityPaper.setAssignUserDisplayName(null);
                facilityPaper.setCurrentAssignUserDivCode(null);
                facilityPaper.setAssignUserUpmGroupCode(null);
                facilityPaper.setAssignDepartmentCode(facilityPaperUpdateDto.getAssignDepartmentCode());

                for (FPAssignDepartment fpAssignDepartment : facilityPaper.getActiveAssignDepartmentSet()) {
                    fpAssignDepartment.setStatus(AppsConstants.Status.INA);
                }

                for (FPAssignDepartmentDTO fpAssignDepartmentDTO : facilityPaperUpdateDto.getFpAssignDepartmentDTOList()) {

                    FPAssignDepartment fpAssignDepartment =
                            facilityPaper.getActiveAssignDepartmentBySolIDAndUserGroupUPMGroup(fpAssignDepartmentDTO.getDivCode(), fpAssignDepartmentDTO.getUserGroupUPMCode());

                    if (fpAssignDepartment == null) {
                        fpAssignDepartment = new FPAssignDepartment();
                        facilityPaper.addFPAssignDepartment(fpAssignDepartment);
                        fpAssignDepartment.setCreatedBy(context.getCredentialsDto().getUserName());
                        fpAssignDepartment.setCreatedDate(context.getDate());
                    } else {
                        fpAssignDepartment.setModifiedBy(context.getCredentialsDto().getUserName());
                        fpAssignDepartment.setModifiedDate(context.getDate());
                    }
                    fpAssignDepartment.setStatus(AppsConstants.Status.ACT);
                    fpAssignDepartment.setDivCode(fpAssignDepartmentDTO.getDivCode());
                    fpAssignDepartment.setDepartmentName(fpAssignDepartmentDTO.getDepartmentName());
                    fpAssignDepartment.setUserGroupUPMCode(fpAssignDepartmentDTO.getUserGroupUPMCode());
                    fpAssignDepartment.setUserGroupName(fpAssignDepartmentDTO.getUserGroupName());
                }

                fpStatusHistory.setAssignUserID(null);
                fpStatusHistory.setAssignUser(null);
                fpStatusHistory.setAssignUserDisplayName(null);
                fpStatusHistory.setAssignUserUpmID(null);
                fpStatusHistory.setAssignUserUpmGroupCode(null);
                fpStatusHistory.setAssignUserDivCode(null);
                if (facilityPaperUpdateDto.getFpAssignDepartmentDTOList().stream().anyMatch(item -> item.getUserGroupUPMCode().equals(assistantUPMGroup))) {
                    setLogsViewOptions(fpStatusHistory, AppsConstants.YesNo.Y, AppsConstants.YesNo.Y, AppsConstants.YesNo.N);
                } else {
                    setLogsViewOptions(fpStatusHistory, AppsConstants.YesNo.N, AppsConstants.YesNo.N, AppsConstants.YesNo.Y);
                }
                fpStatusHistory.setAssignDepartmentCode(facilityPaperUpdateDto.getAssignDepartmentCode());
            }

            facilityPaper.addFpStatusHistory(fpStatusHistory);
            facilityPaper.setCurrentFacilityPaperStatus(facilityPaperUpdateDto.getFacilityPaperStatus());
        }
    }

    private FPStatusHistory setLogsViewOptions(FPStatusHistory fpStatusHistory, AppsConstants.YesNo isUsersOnly, AppsConstants.YesNo isDivisionOnly, AppsConstants.YesNo isPublic) {
        fpStatusHistory.setIsUsersOnly(isUsersOnly);
        fpStatusHistory.setIsDivisionOnly(isDivisionOnly);
        fpStatusHistory.setIsPublic(isPublic);
        return fpStatusHistory;
    }

    public void addComment() throws AppsException {
        if (context.getFacilityPaperUpdateDto().getFpCommentDTO().getComment() != null && !context.getFacilityPaperUpdateDto().getFpCommentDTO().getComment().isEmpty()) {
            FPComment fpComment = new FPComment();
            FPCommentDTO fpCommentDTO = context.getFacilityPaperUpdateDto().getFpCommentDTO();
            fpComment.setComment(fpCommentDTO.getComment());
            fpComment.setCreatedUser(fpCommentDTO.getCreatedUser());
            fpComment.setCreatedUserID(fpCommentDTO.getCreatedUserID());
            fpComment.setCreatedUserDivCode(fpCommentDTO.getCreatedUserDivCode());
            fpComment.setCreatedUserUpmCode(fpCommentDTO.getCreatedUserUpmCode());
            fpComment.setCreatedUserDisplayName(fpCommentDTO.getCreatedUserDisplayName());

            fpComment.setAssignedUser(fpCommentDTO.getAssignedUser());
            fpComment.setAssignedUserID(fpCommentDTO.getAssignedUserID());
            fpComment.setAssignedUserDivCode(fpCommentDTO.getAssignedUserDivCode());
            fpComment.setAssignedUserDisplayName(fpCommentDTO.getAssignedUserDisplayName());

            fpComment.setActionMessage(fpCommentDTO.getActionMessage());
            fpComment.setIsPublic(fpCommentDTO.getIsPublic());
            fpComment.setIsDivisionOnly(fpCommentDTO.getIsDivisionOnly());
            fpComment.setIsUsersOnly(fpCommentDTO.getIsUsersOnly());
            fpComment.setCurrentFacilityPaperStatus(fpCommentDTO.getCurrentFacilityPaperStatus());
            fpComment.setStatus(AppsConstants.Status.ACT);
            fpComment.setCreatedBy(context.getCredentialsDto().getUserName());
            fpComment.setCreatedDate(context.getDate());

            this.context.getFacilityPaper().addFpComment(fpComment);
        }
    }

    @Override
    public void sendEmailNotification(FacilityPaperModificationContext context) throws AppsException {

    }

    protected boolean isValidStatusChange(FacilityPaperModificationContext context) throws AppsException {
        if (context.getFacilityPaperUpdateDto().getFacilityPaperStatus() != null) {
            return true;
        } else {
            LOG.info("NO Status transition found : {}", context.getFacilityPaperUpdateDto());
            return false;
        }
    }

    public CasProperties getCasProperties() {
        return casProperties;
    }

    public void blockCreditRiskComment() {
        String riskDepartmentDivCode = masterDataJdbcDao.getCasActiveBranchDepartmentByBranchDepartmentCasCode(CasBranchDepartmentCode.Code.RISK_MANAGEMENT_AND_COMPLIENCE);
        if (riskDepartmentDivCode != null) {
            if (StringUtils.isNotEmpty(context.getFacilityPaperUpdateDto().getAssignUserDivCode()) &&
                    !riskDepartmentDivCode.equals(context.getFacilityPaperUpdateDto().getAssignUserDivCode()) ||
                    StringUtils.isNotEmpty(context.getFacilityPaperUpdateDto().getAssignDepartmentCode()) &&
                            !riskDepartmentDivCode.equals(context.getFacilityPaperUpdateDto().getAssignDepartmentCode())
            ) {
                FPCreditRiskComment fpCreditRiskComment = context.getFacilityPaper().getNotLockedRiskComment();
                if (fpCreditRiskComment != null) {
                    fpCreditRiskComment.setIsLocked(AppsConstants.YesNo.Y);
                    LOG.info("Credit Risk Comment is Blocked : FpCreditRiskCommentID {} ", fpCreditRiskComment.getFpCreditRiskCommentID());
                }
            }
        }
    }

    public void blockCreditRiskDocument() {
        String riskDepartmentDivCode = masterDataJdbcDao.getCasActiveBranchDepartmentByBranchDepartmentCasCode(CasBranchDepartmentCode.Code.RISK_MANAGEMENT_AND_COMPLIENCE);
        if (riskDepartmentDivCode != null) {
            if (StringUtils.isNotEmpty(context.getFacilityPaperUpdateDto().getAssignUserDivCode()) &&
                    !riskDepartmentDivCode.equals(context.getFacilityPaperUpdateDto().getAssignUserDivCode()) ||
                    StringUtils.isNotEmpty(context.getFacilityPaperUpdateDto().getAssignDepartmentCode()) &&
                            !riskDepartmentDivCode.equals(context.getFacilityPaperUpdateDto().getAssignDepartmentCode())
            ) {
//                List<FPCreditRiskDocument> fpCreditRiskDocuments = context.getFacilityPaper().getNotLockedRiskDocuments();
//                if (fpCreditRiskDocuments != null) {
//                    for (FPCreditRiskDocument fpCreditRiskDocument : fpCreditRiskDocuments) {
//                        fpCreditRiskDocument.setIsLocked(AppsConstants.YesNo.Y);
//                        LOG.info("Credit Risk Document is Blocked : FpCreditRiskDocumentID {} ", fpCreditRiskDocument.getFpCreditRiskDocumentID());
//                    }
//
//                }
            }
        }
    }

    public void setMasterDataJdbcDao(MasterDataJdbcDao masterDataJdbcDao) {
        this.masterDataJdbcDao = masterDataJdbcDao;
    }


}
