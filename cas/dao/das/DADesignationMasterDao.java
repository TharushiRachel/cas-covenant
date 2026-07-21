package com.itechro.cas.dao.das;

import com.itechro.cas.model.domain.das.DADesignationMasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DADesignationMasterDao extends JpaRepository<DADesignationMasterData,Integer>{
    List<DADesignationMasterData> findAllByStatus(String status);

}
