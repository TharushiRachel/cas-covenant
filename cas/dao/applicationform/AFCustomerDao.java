package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFCustomerDao extends JpaRepository<AFCustomer, Integer> {
}
