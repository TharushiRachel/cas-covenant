package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.UserPoolTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface UserPoolTempDao extends JpaRepository<UserPoolTemp, Integer> {
}
