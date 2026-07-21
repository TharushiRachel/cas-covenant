package com.itechro.cas.dao.customer;

import com.itechro.cas.model.domain.customer.CustomerCribResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCribResponseDao extends JpaRepository<CustomerCribResponse, Integer> {
}
