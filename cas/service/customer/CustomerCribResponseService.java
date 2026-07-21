package com.itechro.cas.service.customer;

import com.itechro.cas.dao.crib.jdbc.CirbResponsesJdbcDao;
import com.itechro.cas.dao.customer.jdbc.CustomerJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.customer.CustomerCribResponse;
import com.itechro.cas.model.dto.integration.request.cribreport.CribCorporateRQ;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRetailReportRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.customer.support.SaveCorporateCribResponseHandler;
import com.itechro.cas.service.customer.support.SaveCustomerCribResponseHandler;
import com.itechro.cas.service.customer.support.SaveRetailCribResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CustomerCribResponseService {

    @Autowired
    CirbResponsesJdbcDao cirbResponsesJdbcDao;

    @Autowired
    CustomerJdbcDao customerJdbcDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveCribResponse(String responseString, CribRetailReportRQ cribRetailReportRQ, CribCorporateRQ cribCorporateRQ, CredentialsDTO credentialsDTO) throws AppsException {

        CustomerCribResponse customerCribResponse = new CustomerCribResponse();
        customerCribResponse.setResponse(responseString);

        SaveCustomerCribResponseHandler saveCustomerCribResponseHandler = null;

        if (cribRetailReportRQ != null) {
            saveCustomerCribResponseHandler = new SaveRetailCribResponseHandler();
            ((SaveRetailCribResponseHandler) saveCustomerCribResponseHandler).setCirbResponsesJdbcDao(cirbResponsesJdbcDao);
            ((SaveRetailCribResponseHandler) saveCustomerCribResponseHandler).setCribRetailReportRQ(cribRetailReportRQ);
            ((SaveRetailCribResponseHandler) saveCustomerCribResponseHandler).setCustomerJdbcDao(customerJdbcDao);
            saveCustomerCribResponseHandler.setResponseString(responseString);
            saveCustomerCribResponseHandler.setCredentialsDTO(credentialsDTO);
            saveCustomerCribResponseHandler.setDate(new Date());
            saveCustomerCribResponseHandler.saveCribResponse();
            saveCustomerCribResponseHandler.saveLiabilities();
        }

        if (cribCorporateRQ != null) {
            saveCustomerCribResponseHandler = new SaveCorporateCribResponseHandler();
            ((SaveCorporateCribResponseHandler) saveCustomerCribResponseHandler).setCirbResponsesJdbcDao(cirbResponsesJdbcDao);
            ((SaveCorporateCribResponseHandler) saveCustomerCribResponseHandler).setCribCorporateRQ(cribCorporateRQ);
            ((SaveCorporateCribResponseHandler) saveCustomerCribResponseHandler).setCustomerJdbcDao(customerJdbcDao);
            saveCustomerCribResponseHandler.setResponseString(responseString);
            saveCustomerCribResponseHandler.setCredentialsDTO(credentialsDTO);
            saveCustomerCribResponseHandler.setDate(new Date());
            saveCustomerCribResponseHandler.saveCribResponse();
            saveCustomerCribResponseHandler.saveLiabilities();
        }
    }

}
