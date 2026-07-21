package com.itechro.cas.dao.customer;

import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCovenantDao extends JpaRepository<CustomerCovenant, Integer> {
    List<CustomerCovenant> findByFacilityPaperFpRefNumber(String fpRefNumber);

//    CustomerCovenant findByFacilityPaperFpRefNumber(String fpRefNumber);
}
