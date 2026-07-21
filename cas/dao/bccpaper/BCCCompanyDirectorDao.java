package com.itechro.cas.dao.bccpaper;

import com.itechro.cas.model.domain.bccpaper.BccCompanyDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BCCCompanyDirectorDao extends JpaRepository<BccCompanyDirector, Integer> {

}
