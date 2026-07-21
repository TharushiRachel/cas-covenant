package com.itechro.cas.controller.customer;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.customer.CustomerRatingsDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.customer.CustomerRatingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.prefix}/customerRatings")
public class CustomerRatingsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerRatingsController.class);

    @Autowired
    private CustomerRatingsService customerRatingsService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateCustomerRatings", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CustomerRatingsDTO> saveOrUpdateCustomerRatings(@RequestBody CustomerRatingsDTO customerRatingsDTO) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : saveOrUpdateCustomerRatings : {}, user {}", customerRatingsDTO, credentialsDTO.getUserID());
        CustomerRatingsDTO updatedCustomerRatings = this.customerRatingsService.saveOrUpdateCustomerRatings(customerRatingsDTO, credentialsDTO);
        LOG.info("END : saveOrUpdateCustomerRatings: {}, user {}", customerRatingsDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(updatedCustomerRatings, HttpStatus.OK);
    }


}
