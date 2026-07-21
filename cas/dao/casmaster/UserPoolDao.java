package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.UserPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPoolDao extends JpaRepository<UserPool, Integer>{

    List<UserPool> findByUserId(Integer userId);

}
