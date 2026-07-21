package com.itechro.cas.dao.master;

import com.itechro.cas.model.domain.master.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemParameterDao extends JpaRepository<SystemParameter, Integer> {

    SystemParameter getSystemParameterByParamKey(String paramKey);

}
