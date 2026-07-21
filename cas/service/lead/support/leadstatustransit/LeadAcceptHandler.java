package com.itechro.cas.service.lead.support.leadstatustransit;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.customer.CustomerBankDetailsDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.customer.CustomerBankDetail;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.dto.customer.SearchCustomerRQ;
import com.itechro.cas.model.dto.lead.LeadStatusUpdateRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.lead.support.LeadStatusHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LeadAcceptHandler extends LeadStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LeadApprovedHandler.class);

    private CustomerService customerService;

    private CustomerBankDetailsDao customerBankDetailsDao;

    public LeadAcceptHandler(Lead lead, LeadStatusUpdateRQ leadStatusUpdateRQ, CredentialsDTO credentialsDTO, CasProperties casProperties) {
        super(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setCustomerBankDetailsDao(CustomerBankDetailsDao customerBankDetailsDao) {
        this.customerBankDetailsDao = customerBankDetailsDao;
    }

    @Override
    public boolean isValidStatusTransition() {
        return lead.getLeadStatus() == DomainConstants.LeadStatus.SUBMITTED;
    }

    @Override
    public void updateLead() throws AppsException {
        lead.setAllowFinacleData(AppsConstants.YesNo.Y);
        lead.setAllowCrib(AppsConstants.YesNo.Y);
        lead.setAllowKalypto(AppsConstants.YesNo.Y);
        lead.setAssignUserID(credentialDTO.getUserName());

        if (leadStatusUpdateRQ.getCustomerBankAccountNumber() != null && StringUtils.isNotEmpty(leadStatusUpdateRQ.getCustomerBankAccountNumber())) {
            if (StringUtils.isNotEmpty(leadStatusUpdateRQ.getCustomerBankAccountNumber())) {
                lead.setCustomerBankAccountNumber(leadStatusUpdateRQ.getCustomerBankAccountNumber());
                lead.setAccountNumber(leadStatusUpdateRQ.getCustomerBankAccountNumber());

                List<CustomerBankDetail> bankDetails = this.customerBankDetailsDao.findAllByBankAccountNumberAndStatus(
                        leadStatusUpdateRQ.getCustomerBankAccountNumber(),
                        AppsConstants.Status.ACT
                );

                if (bankDetails.isEmpty()) {
                    try {
                        SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
                        searchCustomerRQ.setBankAccountNumber(leadStatusUpdateRQ.getCustomerBankAccountNumber());
                        customerService.searchCustomerFrom360(searchCustomerRQ, credentialDTO);
                    } catch (Exception e) {
                        LOG.error("ERROR : Customer couldn't find on Finacle. {}", leadStatusUpdateRQ, e);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FINACLE_CUSTOMER_CANNOT_FIND);
                    }

                    bankDetails = this.customerBankDetailsDao.findAllByBankAccountNumberAndStatus(
                            leadStatusUpdateRQ.getCustomerBankAccountNumber(),
                            AppsConstants.Status.ACT
                    );

                    if (bankDetails.isEmpty()) {
                        LOG.info("ERROR : Customer bank details not found for external lead : {}", leadStatusUpdateRQ);
                        throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_EXTERNAL_LEAD_BANK_ACCOUNT);
                    }
                }
                lead.setCustomerID(bankDetails.get(bankDetails.size() - 1).getCustomer().getCustomerID());
            }
        }
    }
}
