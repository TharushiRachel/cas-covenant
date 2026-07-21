package com.itechro.cas.service.customer.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.dao.customer.CustomerCribLiabilityDao;
import com.itechro.cas.dao.customer.CustomerCribResponseDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.customer.CustomerCribResponse;
import com.itechro.cas.model.security.CredentialsDTO;

import java.util.Date;

public class SaveCustomerCribResponseHandler {

    protected String responseString;

    protected CredentialsDTO credentialsDTO;

    protected Date date;

    protected CustomerCribResponseDao customerCribResponseDao;

    protected CustomerCribLiabilityDao customerCribLiabilityDao;

    protected CustomerCribResponse customerCribResponse;

    protected ObjectMapper objectMapper = new ObjectMapper();

    public SaveCustomerCribResponseHandler() {
    }

    public SaveCustomerCribResponseHandler(CredentialsDTO credentialsDTO, Date date) {
        this.credentialsDTO = credentialsDTO;
        this.date = date;
    }


    public void saveCribResponse() throws AppsException {

    }

    public void saveLiabilities() throws AppsException {
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public void setCustomerCribResponseDao(CustomerCribResponseDao customerCribResponseDao) {
        this.customerCribResponseDao = customerCribResponseDao;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CustomerCribResponse getCustomerCribResponse() {
        return customerCribResponse;
    }

    public void setCustomerCribLiabilityDao(CustomerCribLiabilityDao customerCribLiabilityDao) {
        this.customerCribLiabilityDao = customerCribLiabilityDao;
    }
}
