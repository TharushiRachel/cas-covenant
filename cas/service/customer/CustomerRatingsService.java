package com.itechro.cas.service.customer;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.dao.customer.CustomerRatingsDao;
import com.itechro.cas.dao.facilitypaper.CasCustomerDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.customer.CustomerRatings;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.customer.CustomerRatingsDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.util.WebAuditUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CustomerRatingsService

{
    private static final Logger LOG = LoggerFactory.getLogger(CustomerRatingsService.class);

    @Autowired
    private CustomerRatingsDao customerRatingsDao;

    @Autowired
    private CasCustomerDao casCustomerDao;

    @Autowired
    private WebAuditJDBCDao webAuditJDBCDao;

    @Autowired
    private WebAuditService webAuditService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerRatingsDTO saveOrUpdateCustomerRatings(CustomerRatingsDTO customerRatingsDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : saveOrUpdateCustomerRatings : {} : {}", customerRatingsDTO, credentialsDTO.getUserID());
        CustomerRatings customerRatings = null;
        CustomerRatingsDTO previousCustomerRatingsDTO = null;
        Date date = new Date();

        LOG.info("customerRatingsID : {} ", customerRatingsDTO.getCustomerRatingsID());
        LOG.info("customerID : {} ", customerRatingsDTO.getCustomerID());

        boolean isNewCustomerRatings  = customerRatingsDTO.getCustomerRatingsID() == null;

        LOG.info("isNewCustomerRatings : {} ", isNewCustomerRatings);

        if (isNewCustomerRatings) {
            customerRatings = new CustomerRatings();
            customerRatings.setCreatedBy(credentialsDTO.getUserName());
            customerRatings.setCreatedDate(date);
        } else {
            customerRatings = customerRatingsDao.getOne(customerRatingsDTO.getCustomerRatingsID());
            previousCustomerRatingsDTO = new CustomerRatingsDTO(customerRatings);
            customerRatings.setModifiedBy(credentialsDTO.getUserName());
            customerRatings.setModifiedDate(date);
        }

        customerRatings.setCustomerID(customerRatingsDTO.getCustomerID());
        customerRatings.setExistingFacilitiesROA(customerRatingsDTO.getExistingFacilitiesROA());
        customerRatings.setProposedFacilitiesROA(customerRatingsDTO.getProposedFacilitiesROA());
        customerRatings.setRiskGrading(customerRatingsDTO.getRiskGrading());
        customerRatings.setRiskScore(customerRatingsDTO.getRiskScore());
        CASCustomer casCustomer = casCustomerDao.getOne(customerRatingsDTO.getCasCustomerID());
        customerRatings.setCASCustomer(casCustomer);

        LOG.info("Customer Ratings  : {} : {} : {} : {} : {} : {}", customerRatingsDTO.getCasCustomerID(), customerRatingsDTO.getCustomerID(), customerRatingsDTO,
                customerRatingsDTO.getProposedFacilitiesROA(), customerRatingsDTO.getRiskGrading(), customerRatingsDTO.getRiskScore());

        customerRatings = this.customerRatingsDao.saveAndFlush(customerRatings);

        CustomerRatingsDTO updateCustomerRatingsDTO = new CustomerRatingsDTO(customerRatings);

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructCustomerRatingsAudit(updateCustomerRatingsDTO, previousCustomerRatingsDTO, credentialsDTO, date, isNewCustomerRatings, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : saveOrUpdateCustomerRatings : {} : {}", customerRatingsDTO, credentialsDTO.getUserID());
        return updateCustomerRatingsDTO;
    }


}
