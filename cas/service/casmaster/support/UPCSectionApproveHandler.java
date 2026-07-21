package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.UpcTemplateDao;
import com.itechro.cas.dao.casmaster.UpcTemplateDataDao;
import com.itechro.cas.dao.master.UpcSectionDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.UpcSection;
import com.itechro.cas.model.domain.casmaster.UpcTemplate;
import com.itechro.cas.model.domain.casmaster.UpcTemplateData;
import com.itechro.cas.model.dto.casmaster.UpcTemplateDTO;
import com.itechro.cas.model.dto.casmaster.UpcTemplateDataDTO;
import com.itechro.cas.model.dto.casmaster.UpdateLowerLevelRQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UPCSectionApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UPCSectionApproveHandler.class);

    private UpcSectionDao upcSectionDao;

    private UpcTemplateDataDao upcTemplateDataDao;

    private UpcTemplateDao upcTemplateDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();
        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("Support Document ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : Support doc approve status change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        UpcSection upcSection = null;
        Date date = new Date();

        upcSection = upcSectionDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(upcSection);
        upcSection.setModifiedBy(credentialsDTO.getUserName());
        upcSection.setModifiedDate(date);
        upcSection.setApproveStatus(approveRejectRQ.getApproveStatus());
        upcSection.setApprovedBy(credentialsDTO.getUserName());
        upcSection.setApprovedDate(date);
        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (upcSection.getParentRecordID() != null) {
                UpcSection parentUpcSection = upcSectionDao.getOne(upcSection.getParentRecordID());
                parentUpcSection.setStatus(AppsConstants.Status.INA);
                parentUpcSection.setModifiedBy(credentialsDTO.getUserName());
                parentUpcSection.setModifiedDate(date);
                this.upcSectionDao.saveAndFlush(parentUpcSection);

                updateLowerLevelRQ = new UpdateLowerLevelRQ(approveRejectRQ.getApproveRejectDataID(), upcSection.getParentRecordID(),
                        credentialsDTO, date);
                this.needToUpdateLowerLevel = true;
            }
        } else {
            upcSection.setStatus(AppsConstants.Status.INA);
        }
        response = upcSectionDao.saveAndFlush(upcSection);
    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {
        if (needToUpdateLowerLevel) {
            LOG.info("START: Update UPC template Data based on changes {}", updateLowerLevelRQ);
            List<UpcTemplateData> upcTemplateDataPendingList = this.upcTemplateDataDao.findAllByUpcSection_UpcSectionID(
                    updateLowerLevelRQ.getPreviousEntityID());
            for (UpcTemplateData pendingUpcTemplateData : upcTemplateDataPendingList) {
                if (pendingUpcTemplateData.getUpcTemplate().getApproveStatus() == DomainConstants.MasterDataApproveStatus.PENDING) {
                    pendingUpcTemplateData.setUpcSection(this.upcSectionDao.getOne(updateLowerLevelRQ.getNewEntityID()));
                }
            }

            List<UpcTemplateData> upcTemplateDataApprovedList = this.upcTemplateDataDao.findAllByUpcSection_UpcSectionID(
                    updateLowerLevelRQ.getPreviousEntityID());

            List<Integer> approvedUPCTemplateIDs = new ArrayList<>();
            for (UpcTemplateData approvedUpcTemplateData : upcTemplateDataApprovedList) {
                if (approvedUpcTemplateData.getUpcTemplate().getStatus() == AppsConstants.Status.ACT &&
                        !approvedUPCTemplateIDs.contains(approvedUpcTemplateData.getUpcTemplate().getUpcTemplateID())) {
                    approvedUPCTemplateIDs.add(approvedUpcTemplateData.getUpcTemplate().getUpcTemplateID());
                }
            }

            for (Integer approvedTemplateID : approvedUPCTemplateIDs) {
                UpcTemplate upcTemplate = this.upcTemplateDao.getOne(approvedTemplateID);
                if (upcTemplate.getStatus() == AppsConstants.Status.ACT &&
                        upcTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {

                    UpcTemplateDTO upcTemplateDTO = new UpcTemplateDTO(upcTemplate);

                    upcTemplate = new UpcTemplate();
                    upcTemplate.setCreatedBy(credentialsDTO.getUserName());
                    upcTemplate.setCreatedDate(date);
                    upcTemplate.setApproveStatus(approveRejectRQ.getApproveStatus());
                    upcTemplate.setApprovedBy(credentialsDTO.getUserName());
                    upcTemplate.setApprovedDate(date);

                    for (UpcTemplateDataDTO dataDTO : upcTemplateDTO.getUpcTemplateDataDTOList()) {
                        UpcTemplateData templateData = null;

                        templateData = new UpcTemplateData();
                        templateData.setDisplayOrder(dataDTO.getDisplayOrder());
                        if (Objects.equals(dataDTO.getUpcSectionID(), updateLowerLevelRQ.getPreviousEntityID())) {
                            templateData.setUpcSection(this.upcSectionDao.getOne(updateLowerLevelRQ.getNewEntityID()));
                        }
                        templateData.setSectionLevel(dataDTO.getSectionLevel());
                        templateData.setParentSectionID(dataDTO.getParentSectionID());
                        upcTemplate.addUpcTemplateData(templateData);
                    }

                    upcTemplate.setTemplateName(upcTemplateDTO.getTemplateName());
                    upcTemplate.setDescription(upcTemplateDTO.getDescription());
                    upcTemplate.setStatus(upcTemplateDTO.getStatus());
                    upcTemplateDao.saveAndFlush(upcTemplate);

                }
            }

            LOG.info("END: Update UPC template Data based on changes {} : {}", approveRejectRQ, updateLowerLevelRQ);
        }
    }

    public void setUpcSectionDao(UpcSectionDao upcSectionDao) {
        this.upcSectionDao = upcSectionDao;
    }

    public void setUpcTemplateDataDao(UpcTemplateDataDao upcTemplateDataDao) {
        this.upcTemplateDataDao = upcTemplateDataDao;
    }

    public void setUpcTemplateDao(UpcTemplateDao upcTemplateDao) {
        this.upcTemplateDao = upcTemplateDao;
    }
}
