package com.itechro.cas.dao.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.CustomerIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerIdentificationDao extends JpaRepository<CustomerIdentification, Integer> {
    CustomerIdentification findByIdentificationNumberAndStatus(String identificationNumber, AppsConstants.Status status);
}