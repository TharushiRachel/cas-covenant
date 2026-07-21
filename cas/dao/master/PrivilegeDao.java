package com.itechro.cas.dao.master;

import com.itechro.cas.model.domain.master.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeDao extends JpaRepository<Privilege, Integer> {
}
