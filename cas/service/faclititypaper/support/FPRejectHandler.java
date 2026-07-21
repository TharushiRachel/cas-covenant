package com.itechro.cas.service.faclititypaper.support;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facilitypaper.FPStatusHistoryDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.service.faclititypaper.command.FacilityPaperModificationContext;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FPRejectHandler extends FacilityPaperStatusTransitionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FPRejectHandler.class);

    private FacilityPaperJdbcDao facilityPaperJdbcDao;

    public FPRejectHandler(FacilityPaperModificationContext context, CasProperties casProperties) {
        super(context, casProperties);
    }

    public void sendEmailNotification(FacilityPaperModificationContext context) throws AppsException {
        if (super.getCasProperties().isAllowSendingEmail()) {
            FacilityPaper facilityPaper = context.getFacilityPaper();
            FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper);

            this.facilityPaperJdbcDao.getFPDirectReturnUsersList(facilityPaperDTO);

            List<FPStatusHistoryDTO> fpStatusHistoryDTOS = facilityPaperJdbcDao.getFPDirectReturnUsersList(facilityPaperDTO).stream().filter(element -> {
                return !element.getAssignUser().equals(context.getCredentialsDto().getUserName());
            }).distinct().collect(Collectors.toList());

            for (FPStatusHistoryDTO fpStatusHistoryDTO : fpStatusHistoryDTOS) {
                emailRQ = new FacilityPaperStatusTransitionRQ();
                emailRQ.setSolID(facilityPaper.getBranchCode());
                emailRQ.setCredentialsDTO(context.getCredentialsDto());
                emailRQ.setUserName(fpStatusHistoryDTO.getAssignUser());
                emailRQ.setFacilityPaperID(facilityPaper.getFacilityPaperID());
                emailRQ.setFacilityPaperRefNumber(facilityPaper.getFpRefNumber());
                if (context.getCustomerDto() != null) {
                    emailRQ.setCustomerName(context.getCustomerDto().getCustomerName());
                } else if (context.getCasCustomerDto() != null) {
                    emailRQ.setCustomerName(context.getCasCustomerDto().getCasCustomerName());
                }
                emailRQ.setLastComment(context.getFacilityPaperUpdateDto().getFpCommentDTO().getComment());
                emailRQ.setAssignUserDisplayName(fpStatusHistoryDTO.getAssignUserDisplayName());
                emailRQ.setNewStatus(context.getFacilityPaperUpdateDto().getFacilityPaperStatus());
                emailRQ.setTotalExposure(NumberUtil.getFormattedAmount(facilityPaper.getTotalExposureNew()));
                emailRQ.setLastCommentedUser(context.getFacilityPaperUpdateDto().getUpdatedByUserDisplayName());
                emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCreatedDate()));
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(facilityPaper.getBranchCode()).getBranchName());
                for (Facility facility : context.getFacilityPaper().getActiveFacility()) {
                    emailRQ.getFacilityDTOList().add(new FacilityDTO(facility));
                }
                context.addFacilityPaperStatusTransitionRQ(emailRQ);
            }
        }
    }

    public void rejectPaper(FacilityPaperModificationContext context) throws AppsException {
        LOG.info("Reject Facility paper : {}", context.getFacilityPaperUpdateDto());
        context.getFacilityPaper().setRejectedDate(context.getDate());
        LOG.info("Reject Facility paper : {}", context.getFacilityPaperUpdateDto());
    }

    public void setFacilityPaperJdbcDao(FacilityPaperJdbcDao facilityPaperJdbcDao) {
        this.facilityPaperJdbcDao = facilityPaperJdbcDao;
    }

}
