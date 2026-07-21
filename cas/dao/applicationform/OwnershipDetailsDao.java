package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.OwnershipDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnershipDetailsDao extends JpaRepository<OwnershipDetails, Integer> {
}
