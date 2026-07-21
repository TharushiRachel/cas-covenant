package com.itechro.cas.service.faclititypaper.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.UserDaDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.UserDa;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.casmaster.UserDaDTO;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperUpdateDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateFacilityPaperInitializer extends CommandExecutor<FacilityPaperModificationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateFacilityPaperInitializer.class);

    @Autowired
    private FacilityPaperDao facilityPaperDao;

    @Autowired
    private UserDaDao userDaDao;

    @Override
    public FacilityPaperModificationContext execute(FacilityPaperModificationContext context) throws AppsException {

        CredentialsDTO credentialsDTO = context.getCredentialsDto();
        FacilityPaperUpdateDTO paperUpdateDTO = context.getFacilityPaperUpdateDto();

        if (paperUpdateDTO.getFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.APPROVED && paperUpdateDTO.getIsAutoApproval().equals(AppsConstants.YesNo.N)) {
            UserDa userDa = userDaDao.findByApproveStatusAndStatusAndUserName(DomainConstants.MasterDataApproveStatus.APPROVED,
                    AppsConstants.Status.ACT, credentialsDTO.getUserName());
            if (userDa == null) {
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_NO_AUTHORITY_TO_APPROVE);
            }
            context.setUserDaDTO(new UserDaDTO(userDa));
        }
        FacilityPaper facilityPaper = facilityPaperDao.getOne(paperUpdateDTO.getFacilityPaperID());


        facilityPaper.setModifiedBy(credentialsDTO.getUserName());
        facilityPaper.setModifiedDate(context.getDate());

        context.setFacilityPaper(facilityPaper);
        if (facilityPaper.getPrimaryCustomer().getCustomer() != null) {
            context.setCustomerDto(new CustomerDTO(facilityPaper.getPrimaryCustomer().getCustomer()));
        } else {
            context.setCasCustomerDto(new CASCustomerDTO(facilityPaper.getPrimaryCustomer(), null));
        }

        LOG.info("Facility paper update initialized {} ", paperUpdateDTO);
        return context;
    }
}
