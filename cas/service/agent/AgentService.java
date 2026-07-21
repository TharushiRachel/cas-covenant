package com.itechro.cas.service.agent;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.agent.AgentDao;
import com.itechro.cas.dao.agent.jdbc.AgentJDBCDao;
import com.itechro.cas.dao.master.RoleDao;
import com.itechro.cas.dao.master.UserDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.agent.Agent;
import com.itechro.cas.model.domain.master.User;
import com.itechro.cas.model.dto.agent.AgentDTO;
import com.itechro.cas.model.dto.agent.AgentSearchRQ;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.master.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AgentService {

    private static final Logger LOG = LoggerFactory.getLogger(AgentService.class);

    @Autowired
    private AgentJDBCDao agentJDBCDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<AgentDTO> getPagedAgents(AgentSearchRQ agentSearchRQ) {
        return this.agentJDBCDao.getPagedAgents(agentSearchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public AgentDTO getAgentDTO(Integer agentID) {
        Agent agent = this.agentDao.getOne(agentID);
        User user = userDao.getByUserRefID(agentID);

        return new AgentDTO(agent, user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AgentDTO getAgentUserDetailsByUserName(String userName) {
        User user = this.userDao.findByUserNameIgnoreCaseAndUserTypeAndStatus(userName, DomainConstants.UserType.AGENT, AppsConstants.Status.ACT);
        return new AgentDTO(null, user);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public AgentDTO addAgent(AgentDTO agentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Adding agent : {} by {}", agentDTO, credentialsDTO.getUserName());

        Date date = new Date();

        Agent agent = new Agent();
        agent.setMobileNumber(agentDTO.getMobileNumber());
        agent.setSupervisorADUserID(agentDTO.getSupervisorADUserID());
        agent.setNic(agentDTO.getNic());
        agent.setRemark(agentDTO.getRemark());
        agent.setStatus(agentDTO.getStatus());
        agent.setCreatedBy(credentialsDTO.getUserName());
        agent.setCreatedDate(date);

        agent = this.agentDao.saveAndFlush(agent);

        agentDTO.getUserDTO().setUserRefID(agent.getAgentID());
        agentDTO.getUserDTO().setUserType(DomainConstants.UserType.AGENT);
        agentDTO.getUserDTO().getRoles().add(this.roleDao.getRoleByRoleNameAndStatus("AGENT", AppsConstants.Status.ACT).getRoleID());

        UserDTO userDTO = this.userService.addUser(agentDTO.getUserDTO(), credentialsDTO, date);

        LOG.info("END : Adding agent : {} by {}", agentDTO, credentialsDTO.getUserName());

        return new AgentDTO(agent, userDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public AgentDTO updateAgent(AgentDTO agentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Updating agent : {} by {}", agentDTO, credentialsDTO.getUserName());

        Date date = new Date();

        Agent agent = this.agentDao.getOne(agentDTO.getAgentID());

        agent.setMobileNumber(agentDTO.getMobileNumber());
        agent.setSupervisorADUserID(agentDTO.getSupervisorADUserID());
        agent.setNic(agentDTO.getNic());
        agent.setRemark(agentDTO.getRemark());
        agent.setStatus(agentDTO.getStatus());
        agent.setModifiedBy(credentialsDTO.getUserName());
        agent.setModifiedDate(date);

        agent = this.agentDao.saveAndFlush(agent);

        agentDTO.getUserDTO().setUserRefID(agent.getAgentID());
        agentDTO.getUserDTO().setUserType(DomainConstants.UserType.AGENT);

        UserDTO userDTO = this.userService.updateUser(agentDTO.getUserDTO(), credentialsDTO, date);

        this.securityService.destroyUserCache();

        LOG.info("END : Updating agent : {} by {}", agentDTO, credentialsDTO.getUserName());

        return new AgentDTO(agent, userDTO);
    }

}
