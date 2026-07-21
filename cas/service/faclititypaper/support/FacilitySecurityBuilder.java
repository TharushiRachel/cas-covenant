package com.itechro.cas.service.faclititypaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.facility.FacilityDao;
import com.itechro.cas.dao.facility.FacilityFacilitySecurityDao;
import com.itechro.cas.dao.facility.FacilitySecurityDao;
import com.itechro.cas.dao.facility.jdbc.FacilityFacilitySecurityJdbcDao;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityFacilitySecurity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilitySecurity;
import com.itechro.cas.model.dto.facility.FacilityFacilitySecurityDTO;
import com.itechro.cas.model.dto.facility.FacilitySecurityDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.DecimalCalculator;

import java.util.Date;

public class FacilitySecurityBuilder {

    private FacilitySecurityDTO facilitySecurityDTO;

    private FacilitySecurity facilitySecurity;

    private CredentialsDTO credentialsDTO;

    private Date date = new Date();

    private FacilitySecurityDao facilitySecurityDao;

    private FacilityFacilitySecurityDao facilityFacilitySecurityDao;

    private FacilityFacilitySecurityJdbcDao facilityFacilitySecurityJdbcDao;

    private FacilityDao facilityDao;

    public FacilitySecurityBuilder(
            FacilitySecurityDTO facilitySecurityDTO,
            CredentialsDTO credentialsDTO
    ) {
        this.facilitySecurityDTO = facilitySecurityDTO;
        this.credentialsDTO = credentialsDTO;
    }


    public FacilitySecurityBuilder createFacilitySecurity() {
        boolean isNewSecurity = facilitySecurityDTO.getFacilitySecurityID() == null;

        if (!isNewSecurity) {
            facilitySecurity = facilitySecurityDao.getOne(facilitySecurityDTO.getFacilitySecurityID());
            facilitySecurity.setModifiedDate(date);
            facilitySecurity.setModifiedBy(credentialsDTO.getUserName());
        } else {
            facilitySecurity = new FacilitySecurity();
            facilitySecurity.setCreatedDate(date);
            facilitySecurity.setCreatedBy(credentialsDTO.getUserName());
        }
        facilitySecurity.setSecurityCode(facilitySecurityDTO.getSecurityCode());
        facilitySecurity.setSecurityDetail(facilitySecurityDTO.getSecurityDetail());
        facilitySecurity.setSecurityAmount(facilitySecurityDTO.getSecurityAmount());
        facilitySecurity.setCashAmount(facilitySecurityDTO.getCashAmount());
        facilitySecurity.setSecurityCurrency(facilitySecurityDTO.getSecurityCurrency());
        facilitySecurity.setStatus(facilitySecurityDTO.getStatus());
        if (facilitySecurityDTO.getIsCommonSecurity() != null) {
            facilitySecurity.setIsCommonSecurity(facilitySecurityDTO.getIsCommonSecurity());
        }
        if (facilitySecurityDTO.getIsCashSecurity() != null) {
            facilitySecurity.setIsCashSecurity(facilitySecurityDTO.getIsCashSecurity());
        }

        if (facilitySecurityDTO.getFacilityFacilitySecurityDTOS() != null && !facilitySecurityDTO.getFacilityFacilitySecurityDTOS().isEmpty()) {
            for (FacilityFacilitySecurityDTO facilityFacilitySecurityDTO : facilitySecurityDTO.getFacilityFacilitySecurityDTOS()) {

                FacilityFacilitySecurityDTO existingFacilityFacilitySecurityDTO = facilityFacilitySecurityJdbcDao.getFacilityFacilitySecurityDTO(facilityFacilitySecurityDTO);
                boolean isNew = existingFacilityFacilitySecurityDTO == null;
                FacilityFacilitySecurity facilityFacilitySecurity = null;
                if (isNew) {
                    facilityFacilitySecurity = new FacilityFacilitySecurity();
                } else {
                    facilityFacilitySecurity = facilityFacilitySecurityDao.getOne(existingFacilityFacilitySecurityDTO.getFacilitySecuritySecurityID());
                }
                facilityFacilitySecurity.setFacilitySecurity(facilitySecurity);
                facilityFacilitySecurity.setFacility(facilityDao.getOne(facilityFacilitySecurityDTO.getFacilityID()));

                if (facilityFacilitySecurityDTO.getFacilitySecurityAmount() != null) {
                    facilityFacilitySecurity.setFacilitySecurityAmount(facilityFacilitySecurityDTO.getFacilitySecurityAmount());
                } else {
                    facilityFacilitySecurity.setFacilitySecurityAmount(DecimalCalculator.getDefaultZero());
                }

                if (facilityFacilitySecurityDTO.getIsAddedFacility() == AppsConstants.YesNo.N) {
                    facilityFacilitySecurity.setFacilitySecurityAmount(DecimalCalculator.getDefaultZero());
                }
                facilityFacilitySecurity.setIsCashSecurity(facilityFacilitySecurityDTO.getIsCashSecurity());
                facilityFacilitySecurity.setStatus(facilityFacilitySecurityDTO.getStatus());
                this.facilitySecurity.addFacilityFacilitySecurity(facilityFacilitySecurity);
            }
        }
        return this;
    }


    public FacilitySecurity getFacilitySecurity() {
        return facilitySecurity;
    }


    public void setFacilitySecurityDao(FacilitySecurityDao facilitySecurityDao) {
        this.facilitySecurityDao = facilitySecurityDao;
    }

    public void setFacilityFacilitySecurityDao(FacilityFacilitySecurityDao facilityFacilitySecurityDao) {
        this.facilityFacilitySecurityDao = facilityFacilitySecurityDao;
    }

    public void setFacilityDao(FacilityDao facilityDao) {
        this.facilityDao = facilityDao;
    }

    public void setFacilityFacilitySecurityJdbcDao(FacilityFacilitySecurityJdbcDao facilityFacilitySecurityJdbcDao) {
        this.facilityFacilitySecurityJdbcDao = facilityFacilitySecurityJdbcDao;
    }
}
