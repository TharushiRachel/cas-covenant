package com.itechro.cas.dao.customer;

import com.itechro.cas.model.domain.customer.CustomerRatings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRatingsDao extends JpaRepository<CustomerRatings, Integer> {

}
