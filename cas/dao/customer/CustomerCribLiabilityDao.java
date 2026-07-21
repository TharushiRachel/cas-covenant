package com.itechro.cas.dao.customer;

import com.itechro.cas.model.domain.customer.CustomerCribLiability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCribLiabilityDao extends JpaRepository<CustomerCribLiability, Integer> {
}
