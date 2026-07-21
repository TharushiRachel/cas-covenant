package com.itechro.cas.dao.agent;

import com.itechro.cas.model.domain.agent.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentDao extends JpaRepository<Agent, Integer> {
}
