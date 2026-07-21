package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomerTelephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFCustomerTelephoneDao extends JpaRepository<AFCustomerTelephone, Integer> {
}
