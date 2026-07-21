package com.itechro.cas.service.faclititypaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.faclititypaper.FacilityPaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class FacilityPaperBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperBuilder.class);

    private FacilityPaperDao facilityPaperDao;

    private FacilityPaperService facilityPaperService;

    private Date date;

    private FacilityPaper facilityPaper;

    private FacilityPaperDTO facilityPaperDTO;

    private FacilityPaperDTO previousFacilityPaperDTO;

    private CredentialsDTO credentialsDTO;

    public FacilityPaperBuilder(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) {
        this.facilityPaperDTO = facilityPaperDTO;
        this.credentialsDTO = credentialsDTO;
        this.date = new Date();
    }

    public void setFacilityPaperDao(FacilityPaperDao facilityPaperDao) {
        this.facilityPaperDao = facilityPaperDao;
    }

    public void setFacilityPaperService(FacilityPaperService facilityPaperService) {
        this.facilityPaperService = facilityPaperService;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public FacilityPaperDTO getPreviousFacilityPaperDTO() {
        return previousFacilityPaperDTO;
    }

    public FacilityPaperBuilder init() throws AppsException {
        boolean isNewFacilityPaper = facilityPaperDTO.getFacilityPaperID() == null;

        if (isNewFacilityPaper) {
            facilityPaper = new FacilityPaper();
            facilityPaper.setCreatedUserDisplayName(facilityPaperDTO.getCreatedUserDisplayName());
            facilityPaper.setCreatedBy(credentialsDTO.getUserName());
            facilityPaper.setCreatedDate(date);
            facilityPaper.setOutstandingDate(date); // Default The Outstanding date is created date
            facilityPaper.setCurrentCycle(0);
            facilityPaper.setFpRefNumber(this.facilityPaperService.getFPaperRefCode());
            facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
        } else {
            facilityPaper = facilityPaperDao.getOne(facilityPaperDTO.getFacilityPaperID());
            previousFacilityPaperDTO = new FacilityPaperDTO(facilityPaper);
            facilityPaper.setModifiedBy(credentialsDTO.getUserName());
            facilityPaper.setModifiedDate(date);
        }
        return this;
    }

    public FacilityPaperBuilder updateFacilityPaperExposure() throws AppsException {

        LOG.info("START : Facility Paper exposure update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());

        Date date = new Date();
        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(date);

        facilityPaper.setTotalDirectExposurePrevious(facilityPaperDTO.getTotalDirectExposurePrevious());
        facilityPaper.setTotalDirectExposureNew(facilityPaperDTO.getTotalDirectExposureNew());
        facilityPaper.setTotalIndirectExposurePrevious(facilityPaperDTO.getTotalIndirectExposurePrevious());
        facilityPaper.setTotalIndirectExposureNew(facilityPaperDTO.getTotalIndirectExposureNew());
        facilityPaper.setTotalExposurePrevious(facilityPaperDTO.getTotalExposurePrevious());
        facilityPaper.setTotalExposureNew(facilityPaperDTO.getTotalExposureNew());
        facilityPaper.setAddTotalExposureToGroup(facilityPaperDTO.getAddTotalExposureToGroup());
        facilityPaper.setGroupTotalDirectExposurePrevious(facilityPaperDTO.getGroupTotalDirectExposurePrevious());
        facilityPaper.setGroupTotalDirectExposureNew(facilityPaperDTO.getGroupTotalDirectExposureNew());
        facilityPaper.setGroupTotalIndirectExposurePrevious(facilityPaperDTO.getGroupTotalIndirectExposurePrevious());
        facilityPaper.setGroupTotalIndirectExposureNew(facilityPaperDTO.getGroupTotalIndirectExposureNew());
        facilityPaper.setGroupTotalExposurePrevious(facilityPaperDTO.getGroupTotalExposurePrevious());
        facilityPaper.setGroupTotalExposureNew(facilityPaperDTO.getGroupTotalExposureNew());

        if (facilityPaperDTO.getIsCommittee() == AppsConstants.YesNo.Y) {
            facilityPaper.setTotalDirectExposureExisting(facilityPaperDTO.getTotalDirectExposureExisting());
            facilityPaper.setTotalIndirectExposureExisting(facilityPaperDTO.getTotalIndirectExposureExisting());
            facilityPaper.setTotalExposureExisting(facilityPaperDTO.getTotalExposureExisting());
            facilityPaper.setExistingCashMargin(facilityPaperDTO.getExistingCashMargin());
            facilityPaper.setOutstandingCashMargin(facilityPaperDTO.getOutstandingCashMargin());
            facilityPaper.setProposedCashMargin(facilityPaperDTO.getProposedCashMargin());

            facilityPaper.setNetTotalExposureExisting(facilityPaperDTO.getNetTotalExposureExisting());
            facilityPaper.setNetTotalExposureNew(facilityPaperDTO.getNetTotalExposureNew());
            facilityPaper.setNetTotalExposurePrevious(facilityPaperDTO.getNetTotalExposurePrevious());

            facilityPaper.setGroupTotalDirectExposureExisting(facilityPaperDTO.getGroupTotalDirectExposureExisting());
            facilityPaper.setGroupTotalIndirectExposureExisting(facilityPaperDTO.getGroupTotalIndirectExposureExisting());
            facilityPaper.setGroupTotalExposureExisting(facilityPaperDTO.getGroupTotalExposureExisting());
            facilityPaper.setGroupNetTotalExposureExisting(facilityPaperDTO.getGroupNetTotalExposureExisting());
            facilityPaper.setGroupNetTotalExposureNew(facilityPaperDTO.getGroupNetTotalExposureNew());
            facilityPaper.setGroupNetTotalExposurePrevious(facilityPaperDTO.getGroupNetTotalExposurePrevious());
            facilityPaper.setGroupExistingCashMargin(facilityPaperDTO.getGroupExistingCashMargin());
            facilityPaper.setGroupProposedCashMargin(facilityPaperDTO.getGroupProposedCashMargin());
            facilityPaper.setGroupOutstandingCashMargin(facilityPaperDTO.getGroupOutstandingCashMargin());
        }

        facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);

        //TODO audit Update with Attributes
        LOG.info("END : Facility Paper exposure update : {} : {}", facilityPaperDTO, credentialsDTO.getUserID());
        return this;
    }
}
