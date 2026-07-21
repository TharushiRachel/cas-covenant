package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFCustomerAddressDao extends JpaRepository<AFCustomerAddress, Integer> {
}
