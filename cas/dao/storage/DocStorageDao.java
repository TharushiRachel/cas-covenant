package com.itechro.cas.dao.storage;

import com.itechro.cas.model.domain.storage.DocStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocStorageDao extends JpaRepository<DocStorage, Integer> {
}
