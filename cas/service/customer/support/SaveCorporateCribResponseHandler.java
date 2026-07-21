package com.itechro.cas.service.customer.support;

import com.itechro.cas.dao.crib.jdbc.CirbResponsesJdbcDao;
import com.itechro.cas.dao.customer.jdbc.CustomerJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.integration.request.cribreport.CribCorporateRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SaveCorporateCribResponseHandler extends SaveCustomerCribResponseHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SaveRetailCribResponseHandler.class);

    private CribCorporateRQ cribCorporateRQ;

    private CirbResponsesJdbcDao cirbResponsesJdbcDao;

    private CustomerJdbcDao customerJdbcDao;

    public SaveCorporateCribResponseHandler() {
        super();
    }

    public SaveCorporateCribResponseHandler(CredentialsDTO credentialsDTO, Date date) {
        super(credentialsDTO, date);
    }

    @Override
    public void saveCribResponse() throws AppsException {
    }

    @Override
    public void saveLiabilities() throws AppsException {
    }

    public void setCribCorporateRQ(CribCorporateRQ cribCorporateRQ) {
        this.cribCorporateRQ = cribCorporateRQ;
    }

    public void setCirbResponsesJdbcDao(CirbResponsesJdbcDao cirbResponsesJdbcDao) {
        this.cirbResponsesJdbcDao = cirbResponsesJdbcDao;
    }

    public void setCustomerJdbcDao(CustomerJdbcDao customerJdbcDao) {
        this.customerJdbcDao = customerJdbcDao;
    }
}
