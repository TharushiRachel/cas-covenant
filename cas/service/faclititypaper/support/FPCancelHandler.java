package com.itechro.cas.service.faclititypaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.master.UserDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.domain.master.User;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.service.faclititypaper.command.FacilityPaperModificationContext;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FPCancelHandler extends FacilityPaperStatusTransitionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FPCancelHandler.class);

    protected FacilityPaperStatusTransitionRQ emailRQ;

    private UserDao userDao;

    public FPCancelHandler(FacilityPaperModificationContext context, CasProperties casProperties) {
        super(context, casProperties);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void cancelFacilityPaper(FacilityPaperModificationContext context) throws AppsException {
        LOG.info("Transfer the Facility paper status : {}", context.getFacilityPaperUpdateDto());
        String leadRefNumber = context.getFacilityPaper().getLeadRefNumber();
        if (leadRefNumber != null && context.getFacilityPaperUpdateDto().getIsFPCancelToAgent() == AppsConstants.YesNo.Y) {
            String userName = context.getFacilityPaper().getCreatedBy();
            User user = userDao.getUserByUserName(userName);
            StringBuilder assignedUserDisplayName = new StringBuilder();
            assignedUserDisplayName.append(user.getFirstName().concat(" ").concat(user.getLastName()));
            context.getFacilityPaperUpdateDto().setAssignADUserID(userName);
            context.getFacilityPaperUpdateDto().setAssignUser(userName);
            context.getFacilityPaperUpdateDto().setAssignUserID(user.getUserID());
            context.getFacilityPaperUpdateDto().setAssignUserDisplayName(assignedUserDisplayName.toString());
        }
        context.getFacilityPaper().setCanceledDate(context.getDate());
        LOG.info("Transfer the Facility paper status : {}", context.getFacilityPaperUpdateDto());
    }

    @Override
    public void sendEmailNotification(FacilityPaperModificationContext context) throws AppsException {
        if (super.getCasProperties().isAllowSendingEmail()) {
            if (context.getFacilityPaperUpdateDto().getForwardType() == DomainConstants.ForwardType.DIRECT_USER && !context.getFacilityPaperUpdateDto().getAssignUser().equals(context.getCredentialsDto().getUserName())) {
                FacilityPaper facilityPaper = context.getFacilityPaper();
                emailRQ = new FacilityPaperStatusTransitionRQ();
                emailRQ.setSolID(facilityPaper.getBranchCode());
                emailRQ.setCredentialsDTO(context.getCredentialsDto());
                emailRQ.setFacilityPaperID(facilityPaper.getFacilityPaperID());
                emailRQ.setFacilityPaperRefNumber(facilityPaper.getFpRefNumber());
                if (context.getCustomerDto() != null) {
                    emailRQ.setCustomerName(context.getCustomerDto().getCustomerName());
                } else if (context.getCasCustomerDto() != null) {
                    emailRQ.setCustomerName(context.getCasCustomerDto().getCasCustomerName());
                }
                emailRQ.setUserName(context.getFacilityPaperUpdateDto().getAssignUser());
                if (context.getFacilityPaperUpdateDto().getFpCommentDTO() != null) {
                    emailRQ.setLastComment(context.getFacilityPaperUpdateDto().getFpCommentDTO().getComment());
                }
                emailRQ.setNewStatus(context.getFacilityPaperUpdateDto().getFacilityPaperStatus());
                emailRQ.setTotalExposure(NumberUtil.getFormattedAmount(facilityPaper.getTotalExposureNew()));
                emailRQ.setLastCommentedUser(context.getFacilityPaperUpdateDto().getUpdatedByUserDisplayName());
                emailRQ.setAssignUserDisplayName(context.getFacilityPaperUpdateDto().getAssignUserDisplayName());
                emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCreatedDate()));
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(facilityPaper.getBranchCode()).getBranchName());
                for (Facility facility : context.getFacilityPaper().getActiveFacility()) {
                    emailRQ.getFacilityDTOList().add(new FacilityDTO(facility));
                }
                context.addFacilityPaperStatusTransitionRQ(emailRQ);
            }
        }
    }

}
