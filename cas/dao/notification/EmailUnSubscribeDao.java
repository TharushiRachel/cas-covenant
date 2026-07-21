package com.itechro.cas.dao.notification;

import com.itechro.cas.model.domain.notification.EmailUnSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailUnSubscribeDao extends JpaRepository<EmailUnSubscribe, Integer> {
}
