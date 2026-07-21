package com.itechro.cas.dao.customer;

import com.itechro.cas.model.domain.customer.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressDao extends JpaRepository<CustomerAddress, Integer> {
}