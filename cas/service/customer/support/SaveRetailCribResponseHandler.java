package com.itechro.cas.service.customer.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.crib.jdbc.CirbResponsesJdbcDao;
import com.itechro.cas.dao.customer.jdbc.CustomerJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.customer.CustomerCribLiabilityDTO;
import com.itechro.cas.model.dto.integration.request.cribreport.CribResponseSaveRQ;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRetailReportRQ;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CreditDetailsResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CribRetailReportResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.CalendarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SaveRetailCribResponseHandler extends SaveCustomerCribResponseHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SaveRetailCribResponseHandler.class);

    private CribRetailReportRQ cribRetailReportRQ;

    private CirbResponsesJdbcDao cirbResponsesJdbcDao;

    private CustomerJdbcDao customerJdbcDao;

    public SaveRetailCribResponseHandler() {
        super();
    }

    public SaveRetailCribResponseHandler(CredentialsDTO credentialsDTO, Date date) {
        super(credentialsDTO, date);
    }

    @Override
    public void saveCribResponse() throws AppsException {
        CribResponseSaveRQ cribResponseSaveRQ = new CribResponseSaveRQ();
        cribResponseSaveRQ.setIdentificationNumber(cribRetailReportRQ.getIdentificationNumber());
        cribResponseSaveRQ.setIdentificationType(cribRetailReportRQ.getIdentificationType());
        cribResponseSaveRQ.setCustomerGender(cribRetailReportRQ.getCustomerGender());
        cribResponseSaveRQ.setCustomerName(cribRetailReportRQ.getCustomerName());
        cribResponseSaveRQ.setCreatedDate(date);
        cribResponseSaveRQ.setCredentialsDTO(credentialsDTO);
        cribResponseSaveRQ.setResponseString(responseString);
        int responseSavedFlag = cirbResponsesJdbcDao.saveCribResponseJDBC(cribResponseSaveRQ);
        LOG.info("END: Response String Saved responseSavedFlag : {} : {} : Date Time : {}", responseSavedFlag, cribResponseSaveRQ, CalendarUtil.getDefaultFormattedDateTime(date));

    }

    @Override
    public void saveLiabilities() throws AppsException {
        try {
            CribRetailReportResponse cribRetailReportResponse = objectMapper.readValue(responseString, CribRetailReportResponse.class);
            if (cribRetailReportResponse.getDetailsResponse() != null) {

                for (CreditDetailsResponse creditDetailsResponse : cribRetailReportResponse.getDetailsResponse().getConsumerCreditFacilityResponse().getMasterCreditDetailsResponse().getCreditDetailsResponses()) {
                    CustomerCribLiabilityDTO customerCribLiabilityDTO = new CustomerCribLiabilityDTO();
                    customerCribLiabilityDTO.setIdentificationType(cribRetailReportRQ.getIdentificationType());
                    customerCribLiabilityDTO.setIdentificationNumber(cribRetailReportRQ.getIdentificationNumber());
                    customerCribLiabilityDTO.setPresentOutstanding(creditDetailsResponse.getCurrentBalance());
                    customerCribLiabilityDTO.setFinancialInstitution(creditDetailsResponse.getInsCategory());
                    customerCribLiabilityDTO.setOriginalAmount(creditDetailsResponse.getSanctionedAmount());
                    customerCribLiabilityDTO.setSecurities(creditDetailsResponse.getCoverage());
                    customerCribLiabilityDTO.setArrears(creditDetailsResponse.getOverdueAmount());
                    customerCribLiabilityDTO.setSignedAs(creditDetailsResponse.getOwnerShipIndicator());
                    customerCribLiabilityDTO.setStatus(AppsConstants.Status.ACT);
                    int customerCribLiabilitySavedFlag = customerJdbcDao.saveCustomerCribLiabilitiesJDBC(customerCribLiabilityDTO, credentialsDTO, date);
                    LOG.info("END: SANCTIONED_AMOUNT : {}  Customer Liability Saved Flag : {} :  Saved by :{}", creditDetailsResponse, customerCribLiabilitySavedFlag, credentialsDTO.getUserName());
                }
            }
        } catch (Exception e) {
            LOG.info("ERROR: While mapping Customer Liabilities : {} :{}", cribRetailReportRQ, credentialsDTO.getUserName(), e);
        }

    }

    public void setCribRetailReportRQ(CribRetailReportRQ cribRetailReportRQ) {
        this.cribRetailReportRQ = cribRetailReportRQ;
    }

    public void setCirbResponsesJdbcDao(CirbResponsesJdbcDao cirbResponsesJdbcDao) {
        this.cirbResponsesJdbcDao = cirbResponsesJdbcDao;
    }

    public void setCustomerJdbcDao(CustomerJdbcDao customerJdbcDao) {
        this.customerJdbcDao = customerJdbcDao;
    }
}
