package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.CribDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CribDetailsDao extends JpaRepository<CribDetails, Integer> {

    List<CribDetails> findByIdentificationNo(String identificationNo);
}